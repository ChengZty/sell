package com.buss.cms.service;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.buss.cms.entity.TContentCustomIndexEntity;
import com.buss.cms.entity.TGuideMainElementEntity;

public interface TContentCustomIndexServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TContentCustomIndexEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TContentCustomIndexEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TContentCustomIndexEntity t);
 	/**
 	 * 
 	 * @param cmsList
 	 * @param retailerId
 	 */
	public void insterCmsItems(String retailerId);
	
	/**
	 * 清除 APP首页缓存 getHomePage* redis缓存
	 */
	void clearHomePageRedis();

	//获取导购主页元素信息
	public void getGuideMainElementDatagrid(HttpServletRequest request, DataGrid dataGrid);

	public void guideMaimElement(HttpServletRequest request);
}
