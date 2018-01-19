package com.buss.news.service.impl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.redis.service.RedisService;

import com.buss.count.vo.GuideNewsTotalCountVo;
import com.buss.news.entity.TNewsTypeEntity;
import com.buss.news.service.TNewsTypeServiceI;

@Service("tNewsTypeService")
@Transactional
public class TNewsTypeServiceImpl extends CommonServiceImpl implements TNewsTypeServiceI {
	@Autowired
	private RedisService redisService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TNewsTypeEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TNewsTypeEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TNewsTypeEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TNewsTypeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TNewsTypeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TNewsTypeEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TNewsTypeEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{code}",String.valueOf(t.getCode()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{order_num}",String.valueOf(t.getOrderNum()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void doCopy(TNewsTypeEntity t) throws Exception {
 		String retailerId = ResourceUtil.getRetailerId();
 		String hql = "from TNewsTypeEntity where platformType = '2' and originalId = ? and retailerId = ?";
 		//查询是否已经复制过该分类
 		List<TNewsTypeEntity> existEntityList = this.commonDao.findHql(hql, t.getId(),retailerId);
 		if(existEntityList.size()==0){//新增
 			TNewsTypeEntity tNewsType = new TNewsTypeEntity();
 			MyBeanUtils.copyBean2Bean(tNewsType, t);
 			tNewsType.setId(null);
 			tNewsType.setCreateDate(null);
 			tNewsType.setRetailerId(retailerId);
 			tNewsType.setPlatformType(TNewsTypeEntity.PLATFORM_TYPE_2);
 			tNewsType.setOriginalId(t.getId());
 			this.commonDao.save(tNewsType);
 		}else{//更新
 			TNewsTypeEntity existEntity = existEntityList.get(0);
 			existEntity.setName(t.getName());
 			existEntity.setCoverPic(t.getCoverPic());
 			existEntity.setShowType(t.getShowType());
 			existEntity.setSmallPic(t.getSmallPic());
 			existEntity.setUpdateDate(null);
 			this.commonDao.updateEntitie(existEntity);
 		}
 	}
 	
 	@Override
 	public void batchSaveByCopyNews(Set<String> newsTypeSet) {
 		if(newsTypeSet.size()>0){
 			String retailerId = ResourceUtil.getRetailerId();
 			Long count = 0L;
 			for(String code : newsTypeSet){
 				String sql = "select count(1) from t_news_type where status = 'A' and code = '"+code+"' and retailer_Id = '"+retailerId+"'";
 				count = this.getCountForJdbc(sql);
 				if(count==0){//没有该分类则新增
 					String hql = "from TNewsTypeEntity where platformType = '1' and code = ? ";
 					List<TNewsTypeEntity> existEntityList = this.commonDao.findHql(hql, code);
 					TNewsTypeEntity originalEntity = null;
 					if(existEntityList.size()>0){
 						originalEntity = existEntityList.get(0);//平台的资讯分类
 					}
 					if(Utility.isNotEmpty(originalEntity)){
 						TNewsTypeEntity tNewsType = new TNewsTypeEntity();
 						try {
							MyBeanUtils.copyBean2Bean(tNewsType, originalEntity);
						} catch (Exception e) {
							e.printStackTrace();
						}
 			 			tNewsType.setId(null);
 			 			tNewsType.setCreateDate(null);
 			 			tNewsType.setRetailerId(retailerId);
 			 			tNewsType.setPlatformType(TNewsTypeEntity.PLATFORM_TYPE_2);
 			 			tNewsType.setOriginalId(originalEntity.getId());
 			 			this.commonDao.save(tNewsType);
 					}
 				}
 				this.clearNewsTypeReids();
 			}
 		}
 	}
 	
 	@Override
 	public String getUniqueMaxCode() {
 		String code = "10";
 		Map<String, Object> map = commonDao.findOneForJdbc("select CONCAT(MAX(code+1),'') as code from t_news_type where status = 'A'");
		if(Utility.isNotEmpty(map)&&Utility.isNotEmpty(map.get("code"))){
			code =  map.get("code")+"" ;
		}
 		return code;
 	}
 	
 	/**
	 * 清除品牌缓存 key:getAllNewType* 资讯分类的新增，修改，删除
	 */
	public void clearNewsTypeReids() {
		try {
			redisService.batchDel("getAllNewType*"+ResourceUtil.getRetailerId()+"*");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}
	}

	@Override
	public CommonVo getAllNewsTypeCodeAndNameVo(){
		CommonVo vo = new CommonVo();
		StringBuffer text = new StringBuffer();
		StringBuffer value = new StringBuffer();
		String sql = "select CONCAT(code,'_',retailer_id) as field,name as text from t_news_type where status = 'A'";
		List<Map<String, Object>> list = commonDao.findForJdbc(sql);
		if(list.size()>0){
			for (Map<String, Object> map : list){
				text.append(map.get("text")).append(",");
				value.append(map.get("field")).append(",");
			}
			if(value.length()>0){
				value = value.deleteCharAt(value.length()-1);
				text = text.deleteCharAt(text.length()-1);
			}
			vo.setId(value.toString());
			vo.setName(text.toString());
		}
		return vo;
	}
	
	/**
	 * 获取资讯分类的名称
	 * @param newsTypeStr  资讯分类的code
	 * @param retailerId
	 * @return
	 */
	@Override
	public Map<String,String> getNewsTypeNames(String newsTypeStr,String retailerId){
		Map<String,String> returnMap = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select code ,name from t_news_type where status='A' and retailer_id='")
		.append(retailerId).append("'")
		.append(" and code in( ").append(newsTypeStr).append(")")
		;
		List<Map<String,Object>> list = this.findForJdbc(sb.toString());
		if(Utility.isNotEmpty(list)){
			for (Map<String, Object> obj : list) {
				if(Utility.isNotEmpty(obj)){
					String code = obj.get("code")+"";
					String name = obj.get("name")+"";
					if(Utility.isNotEmpty(code) && Utility.isNotEmpty(name)){
						returnMap.put(code, name);
					}
				}
			}
		}
		return returnMap;
		
	}
	
	/**
	 * 获取资讯分类名称
	 * @param resultList
	 * @param retailerId
	 */
	@Override
	public void fillNewsTypeNames(List<GuideNewsTotalCountVo> resultList,String retailerId){
		if(Utility.isNotEmpty(resultList)){
			StringBuilder sb = new StringBuilder();
			String[] newsTypeArr = null;
			for (GuideNewsTotalCountVo vo : resultList) {
				if(Utility.isNotEmpty(vo.getNewsType())){
					newsTypeArr = vo.getNewsType().split(",");
					for (String str : newsTypeArr) {
						sb.append(",'").append(str).append("'");
					}
				}
			}
			if(Utility.isNotEmpty(sb.toString())){
				sb.deleteCharAt(0);
				Map<String,String> typeMap = this.getNewsTypeNames(sb.toString(), retailerId);
				StringBuilder typeNameSb = new StringBuilder();
				for (GuideNewsTotalCountVo vo : resultList) {
					if(Utility.isNotEmpty(vo.getNewsType())){
						newsTypeArr = vo.getNewsType().split(",");
						for (String str : newsTypeArr) {
							if(Utility.isNotEmpty(typeMap.get(str))){
								typeNameSb.append(",").append(typeMap.get(str));
							}
						}
					}
					if(Utility.isNotEmpty(typeNameSb.toString())){
						typeNameSb.deleteCharAt(0);
						vo.setNewsType(typeNameSb.toString());//资讯分类名称
						typeNameSb.delete(0, typeNameSb.length());
					}
				}	
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}