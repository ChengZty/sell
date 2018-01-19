package com.buss.visibleGoods.service.impl;
import java.io.Serializable;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.visibleGoods.entity.TRetailerBrandCategoryEntity;
import com.buss.visibleGoods.service.TRetailerBrandCategoryServiceI;

@Service("tRetailerBrandCategoryService")
@Transactional
public class TRetailerBrandCategoryServiceImpl extends CommonServiceImpl implements TRetailerBrandCategoryServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TRetailerBrandCategoryEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TRetailerBrandCategoryEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TRetailerBrandCategoryEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TRetailerBrandCategoryEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TRetailerBrandCategoryEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TRetailerBrandCategoryEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TRetailerBrandCategoryEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{other_retailer_id}",String.valueOf(t.getOtherRetailerId()));
 		sql  = sql.replace("#{brand_id}",String.valueOf(t.getBrandId()));
 		sql  = sql.replace("#{top_category_id}",String.valueOf(t.getTopCategoryId()));
 		sql  = sql.replace("#{sub_category_id}",String.valueOf(t.getSubCategoryId()));
 		sql  = sql.replace("#{thrid_category_id}",String.valueOf(t.getThridCategoryId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void saveRetailerBrandCategory(TRetailerBrandCategoryEntity tRetailerBrandCategory) {
 		this.commonDao.save(tRetailerBrandCategory);
 	}
 	
 	@Override
 	public void doBatchDel(HttpServletRequest req) {
 		String ids = req.getParameter("ids");
 		TSUser user = ResourceUtil.getSessionUserName();
		StringBuffer sql = new StringBuffer("update t_retailer_brand_category set status = 'I',update_by = '").append(user.getUserName()).append("',update_name = '")
		.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("' where id in (");
		for(String id:ids.split(",")){
			sql.append("'").append(id).append("',");
		}
		sql = sql.deleteCharAt(sql.length()-1).append(")");
		commonDao.updateBySqlString(sql.toString());
 	}
}