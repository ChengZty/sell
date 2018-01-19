package com.buss.bill.service;
import com.buss.bill.entity.TCommissionStoreRuleEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

public interface TCommissionStoreRuleServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TCommissionStoreRuleEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TCommissionStoreRuleEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TCommissionStoreRuleEntity t);
 	/**
 	 *明细更新
 	 * @param dataList
 	 */
	public void saveList(List<TCommissionStoreRuleEntity> dataList);
}
