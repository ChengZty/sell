package com.buss.user.service;
import com.buss.user.entity.TShopEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface TShopServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TShopEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TShopEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TShopEntity t);

	/**保存店铺并同时生成零售商的管理员帐号
	 * @param tShop
	 * @param request
	 */
	public void saveShopAndCreateUser(TShopEntity tShop,HttpServletRequest request);
}
