package com.buss.words.service;
import com.buss.words.entity.TVisibleWordsEntity;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface TVisibleWordsServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TVisibleWordsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TVisibleWordsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TVisibleWordsEntity t);

	public void getRetailerVisibleWords(HttpServletRequest request, DataGrid dataGrid);
}
