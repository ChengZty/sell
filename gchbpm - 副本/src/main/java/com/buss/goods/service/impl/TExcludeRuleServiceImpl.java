package com.buss.goods.service.impl;
import com.buss.goods.service.TExcludeRuleServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.goods.entity.TExcludeRuleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tExcludeRuleService")
@Transactional
public class TExcludeRuleServiceImpl extends CommonServiceImpl implements TExcludeRuleServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TExcludeRuleEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TExcludeRuleEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TExcludeRuleEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TExcludeRuleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TExcludeRuleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TExcludeRuleEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TExcludeRuleEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{top_category_id}",String.valueOf(t.getTopCategoryId()));
 		sql  = sql.replace("#{sub_category_id}",String.valueOf(t.getSubCategoryId()));
 		sql  = sql.replace("#{thrid_category_id}",String.valueOf(t.getThridCategoryId()));
 		sql  = sql.replace("#{top_category_name}",String.valueOf(t.getTopCategoryName()));
 		sql  = sql.replace("#{sub_category_name}",String.valueOf(t.getSubCategoryName()));
 		sql  = sql.replace("#{thrid_category_name}",String.valueOf(t.getThridCategoryName()));
 		sql  = sql.replace("#{brand_id}",String.valueOf(t.getBrandId()));
 		sql  = sql.replace("#{brand_name}",String.valueOf(t.getBrandName()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}