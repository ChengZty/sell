package com.buss.base.service;
import com.buss.base.entity.BaseActivityEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BaseActivityServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(BaseActivityEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(BaseActivityEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(BaseActivityEntity t);

	/**修改活动并更新活动商品缓存
	 * @param baseActivity
	 */
	public void doUpdate(BaseActivityEntity baseActivity) throws Exception;

	/**删除活动并删除活动商品缓存
	 * @param baseActivity
	 */
	public void doDel(BaseActivityEntity baseActivity);
}
