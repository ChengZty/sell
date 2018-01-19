package com.buss.shop.service;

import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.buss.shop.entity.TSmsPicClassEntity;


public interface TSmsPicClassServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSmsPicClassEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSmsPicClassEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSmsPicClassEntity t);
 	
 	/**
 	 * 通过分类名和零售商查询分类
 	 * @param name
 	 * @param retailerId
 	 * @return
 	 */
 	public List<TSmsPicClassEntity> getClassByNameAndRetailerId(String name, String retailerId);
}
