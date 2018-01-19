package com.buss.base.service;
import com.buss.base.entity.TBrandSellAreaEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBrandSellAreaServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBrandSellAreaEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBrandSellAreaEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBrandSellAreaEntity t);

	/**保存可售地区
	 * @param tBrandSellArea
	 */
	public void saveSellArea(TBrandSellAreaEntity tBrandSellArea);

	/**删除可售地区
	 * @param tBrandSellArea
	 */
	public void delSellArea(TBrandSellAreaEntity tBrandSellArea);

	/**批量删除可售地区
	 * @param ids
	 * @param brandId
	 */
	public void batchDelSellArea(String ids, String brandId);
}
