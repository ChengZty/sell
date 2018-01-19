package com.buss.param.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.redis.service.RedisService;

import com.alibaba.fastjson.JSONArray;
import com.buss.param.entity.TSysParameterEntity;
import com.buss.param.entity.vo.TSysParameterVO;
import com.buss.param.service.TSysParameterServiceI;

@Service("tSysParameterService")
@Transactional
public class TSysParameterServiceImpl extends CommonServiceImpl implements TSysParameterServiceI {
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSysParameterEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSysParameterEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSysParameterEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSysParameterEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSysParameterEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSysParameterEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSysParameterEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{para_code}",String.valueOf(t.getParaCode()));
 		sql  = sql.replace("#{para_value}",String.valueOf(t.getParaValue()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
 	 * 获取零售商下某个系统参数
 	 * @param paraCode 
 	 * @param retailerId
 	 * @return
 	 */
 	@Override
 	public TSysParameterVO getParameterByCode(String paraCode,String retailerId){
 		String key = TSysParameterEntity.SYS_PARAMS+"_"+retailerId;
 		String value;
 		try
		{
 			value = redisService.get(key);
		  if(!Utility.isEmpty(value))
		  {
			 List<TSysParameterVO> list = JSONArray.parseArray(value, TSysParameterVO.class);
			 if(Utility.isNotEmpty(list)){
				 for (TSysParameterVO vo : list) {
					if(paraCode.equals(vo.getParaCode())){
						return vo;
					}
				}
			 }
		  }else{
			  //从数据库取
				List<TSysParameterEntity> paramList = this.commonDao.findHql("from TSysParameterEntity where retailerId = ? and paraCode = ?",retailerId,paraCode);
				if(Utility.isNotEmpty(paramList)){
					TSysParameterEntity param = paramList.get(0);
					TSysParameterVO vo = new TSysParameterVO();
					vo.setId(param.getId());
					vo.setParaCode(param.getParaCode());
					vo.setParaValue(param.getParaValue());
					return vo ;
				}
		  }
		} catch(Exception ex){
			ex.printStackTrace();
		}
 		return null;
 	}
 	
 	
 	
 	/**
 	 * 保存或更新参数值
 	 * @param tSysParameter
 	 */
 	@Override
 	public void updateSysParameter(TSysParameterEntity tSysParameter){
 		String retailerId = ResourceUtil.getRetailerId();
 		StringBuilder sb = new StringBuilder();
		sb.append("from TSysParameterEntity where paraCode in('").append(tSysParameter.getParaCode()).append("' ")
		.append(") and retailerId='").append(retailerId).append("'");
		List<TSysParameterEntity> paramList = this.commonDao.findHql(sb.toString());
		if(Utility.isNotEmpty(paramList)){
			TSysParameterEntity entity = paramList.get(0);
			entity.setParaValue(tSysParameter.getParaValue());
			entity.setUpdateDate(DateUtils.gettimestamp());
			commonDao.updateEntitie(entity);
		}else{
			tSysParameter.setRetailerId(retailerId);
			tSysParameter.setCreateDate(DateUtils.gettimestamp());
			commonDao.save(tSysParameter);
		}
 	}
 	
 	
 	@Override
 	public void doBatchSaveOrUpdate(TSysParameterEntity tSysParameter) throws Exception {
 		List<TSysParameterEntity> paramList = tSysParameter.getParamList();
 		if(paramList.size()>0){
 			TSUser user = ResourceUtil.getSessionUserName();
 			String retailerId = ResourceUtil.getRetailerId();
 			for(TSysParameterEntity entity : paramList){
 				entity.setRetailerId(retailerId);
 				entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 				if(Utility.isEmpty(entity.getId())){
 					commonDao.save(entity);
 				}else{
 					StringBuffer sql = new StringBuffer("update t_sys_parameter set update_by = '").append(user.getUserName()).append("',update_name = '")
 					.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',para_value='").append(entity.getParaValue())
 					.append("',sort_num='").append(entity.getSortNum()).append("' where id ='").append(entity.getId())
 					.append("'");
 					commonDao.updateBySqlString(sql.toString());
 				}
 			}
 			updateRetailerSysParams(paramList, retailerId);
 		}
 	}
 	
 	/**更新零售商的系统参数
 	 * @param paramList 需要更新的list
 	 * @param retailerId
 	 * @throws Exception 
 	 */
 	public void updateRetailerSysParams(List<TSysParameterEntity> paramList,String retailerId) throws Exception{
			String value = redisService.get(TSysParameterEntity.SYS_PARAMS+"_"+retailerId);
			List<TSysParameterVO> list = null;
			  if(!Utility.isEmpty(value)){//更新
				  list = JSONArray.parseArray(value, TSysParameterVO.class);
				 if(Utility.isNotEmpty(list)){
					 for(TSysParameterEntity entity : paramList){
						 boolean exist = false;
						 for (TSysParameterVO vo : list) {
							 if(entity.getParaCode().equals(vo.getParaCode())){//更新值
								vo.setParaValue(entity.getParaValue());
								exist = true;
							 }
						 }
						 if(!exist){//保存
							 TSysParameterVO vo = new TSysParameterVO();
							  vo.setId(entity.getId());
							  vo.setParaValue(entity.getParaValue());
							  vo.setParaCode(entity.getParaCode());
							  list.add(vo);
						 }
					  }
				 }
			  }else{//保存
				  list = new ArrayList<TSysParameterVO>();
				  for(TSysParameterEntity entity : paramList){
					  TSysParameterVO vo = new TSysParameterVO();
					  vo.setId(entity.getId());
					  vo.setParaValue(entity.getParaValue());
					  vo.setParaCode(entity.getParaCode());
					  list.add(vo);
				  }
			  }
			  if(list.size()>0){
				  redisService.set(TSysParameterEntity.SYS_PARAMS+"_"+retailerId, MAPPER.writeValueAsString(list));
			  }
 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
}