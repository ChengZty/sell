package com.buss.bill.service;
import com.buss.bill.entity.TCommissionCloudEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TCommissionCloudServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TCommissionCloudEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TCommissionCloudEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TCommissionCloudEntity t);
 	/**
 	 * 保存重写
 	 * @param tCommissionCloud
 	 */
	public void saveCommission(TCommissionCloudEntity tCommissionCloud);
	/**
	 * 修改重写
	 * @param t
	 */
	public void saveOrUpdateCommission(TCommissionCloudEntity t);
}
