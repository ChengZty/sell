package com.buss.visibleGoods.service;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

import com.buss.visibleGoods.entity.TRetailerBrandCategoryEntity;

public interface TRetailerBrandCategoryServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TRetailerBrandCategoryEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TRetailerBrandCategoryEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TRetailerBrandCategoryEntity t);

	/**添加零售商品牌分类选择 并更新零售商商品条件
	 * @param tRetailerBrandCategory
	 */
	public void saveRetailerBrandCategory(TRetailerBrandCategoryEntity tRetailerBrandCategory);

	/**批量删除
	 * @param ids
	 */
	public void doBatchDel(HttpServletRequest req);
}
