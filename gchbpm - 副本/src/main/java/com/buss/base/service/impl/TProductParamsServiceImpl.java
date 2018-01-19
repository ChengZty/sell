package com.buss.base.service.impl;
import com.buss.base.service.TProductParamsServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.base.entity.TProductParamsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("tProductParamsService")
@Transactional
public class TProductParamsServiceImpl extends CommonServiceImpl implements TProductParamsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TProductParamsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TProductParamsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TProductParamsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TProductParamsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TProductParamsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TProductParamsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TProductParamsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{category_id}",String.valueOf(t.getCategoryId()));
 		sql  = sql.replace("#{param_name}",String.valueOf(t.getParamName()));
 		sql  = sql.replace("#{param_values}",String.valueOf(t.getParamValues()));
// 		sql  = sql.replace("#{multi_select}",String.valueOf(t.getMultiSelect()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public List<TProductParamsEntity> findProductParamsByTopCategoryId(	String topCategoryId) {
 		List<TProductParamsEntity> list = new ArrayList<TProductParamsEntity>();
 		String hql = "from TProductParamsEntity where categoryId='"+topCategoryId+"' order by rowNum,rowIndexNum asc";
 		list = this.commonDao.findByQueryString(hql);
 		return list;
 	}
}