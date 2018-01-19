package com.buss.shop.service;
import com.buss.shop.entity.TShopHomeTempEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TShopHomeTempServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TShopHomeTempEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TShopHomeTempEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TShopHomeTempEntity t);
 	/**
 	 * 更新已完成的首页模板
 	 * @param tShopHomeTemp
 	 * @param retailerId
 	 * @throws Exception
 	 */
	void updateFinish(TShopHomeTempEntity tShopHomeTemp, String retailerId)
			throws Exception;
}
