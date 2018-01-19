package com.buss.user.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.user.entity.TVipLevelEntity;
import com.buss.user.service.TVipLevelServiceI;

@Service("tVipLevelService")
@Transactional
public class TVipLevelServiceImpl extends CommonServiceImpl implements TVipLevelServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TVipLevelEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TVipLevelEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TVipLevelEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TVipLevelEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TVipLevelEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TVipLevelEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TVipLevelEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{vip_code}",String.valueOf(t.getVipCode()));
 		sql  = sql.replace("#{vip_name}",String.valueOf(t.getVipName()));
 		sql  = sql.replace("#{province_id}",String.valueOf(t.getProvinceId()));
 		sql  = sql.replace("#{city_id}",String.valueOf(t.getCityId()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public List<CommonVo> getLvlList(){
 		List<CommonVo> lis = new ArrayList<CommonVo>();
 		List<TVipLevelEntity> lvlList = this.commonDao.findByProperty(TVipLevelEntity.class, "retailerId", ResourceUtil.getRetailerId());
		if(!Utility.isEmpty(lvlList))
		{
			for(TVipLevelEntity e:lvlList){
				CommonVo vo = new CommonVo();
				vo.setId(e.getId());
				vo.setName(e.getVipName());
				lis.add(vo);
			}
		}
		return lis;
 	}
}