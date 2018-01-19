package com.buss.shop.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.shop.entity.TSmsPicClassEntity;
import com.buss.shop.service.TSmsPicClassServiceI;




@Service("tSmsPicClassService")
@Transactional
public class TSmsPicClassServiceImpl extends CommonServiceImpl implements TSmsPicClassServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSmsPicClassEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSmsPicClassEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSmsPicClassEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSmsPicClassEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSmsPicClassEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSmsPicClassEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSmsPicClassEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{count}",String.valueOf(t.getCount()));
 		return sql;
 	}

 	public List<TSmsPicClassEntity> getClassByNameAndRetailerId(String name, String retailerId){
 		List<TSmsPicClassEntity> tSmsPicClassList = new ArrayList<TSmsPicClassEntity>();
 		
 		String sql = "from TSmsPicClassEntity where name = '" + name + "' and  retailerId = '" + retailerId + "' and status = 'A' ";
 		tSmsPicClassList = this.findHql(sql);
 		//TSmsPicClassEntity tSmsPicClassEntity = (TSmsPicClassEntity) this.executeSqlReturnKey(sql, null);
 		return tSmsPicClassList;
 	}
}