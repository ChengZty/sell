package com.buss.news.service;
import com.buss.news.entity.TNewsEntity;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface TNewsServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TNewsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TNewsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TNewsEntity t);

	public void doCopy(TNewsEntity t)  throws Exception;
	
	//获取话题列表
	public void getNewsDatagrid(HttpServletRequest request, DataGrid dataGrid);

	//获取管家课堂，管家故事列表
	public void getGuideNewsDatagrid(HttpServletRequest request, DataGrid dataGrid);

	//保存或修改话题的关联行业
	public void saveTradeTemplate(String tNewsID, String tradeIDs);

	public void getNewsTypeArr(HttpServletRequest request);
}
