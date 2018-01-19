package com.buss.visibleGoods.service;
import com.buss.visibleGoods.entity.TRetailerRelationEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface TRetailerRelationServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TRetailerRelationEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TRetailerRelationEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TRetailerRelationEntity t);

	public void batchSave(HttpServletRequest request);
	
	/**更新零售商商品条件
	 * @param retailerId
	 */
	public void updateRetailerGoodsConditions(String retailerId);

	public void deleteRelation(TRetailerRelationEntity tRetailerRelation);
}
