package com.buss.base.service;
import com.buss.base.entity.TBrandShowEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

public interface TBrandShowServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBrandShowEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBrandShowEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBrandShowEntity t);

	/**修改品牌广告
	 * @param tBrandShow
	 */
	public void updateBrandInfo(TBrandShowEntity tBrandShow);

	/**获取可见品牌列表
	 * @param retailerId
	 * @return
	 */
	List<TBrandShowEntity> getShowBrands(String retailerId);
}
