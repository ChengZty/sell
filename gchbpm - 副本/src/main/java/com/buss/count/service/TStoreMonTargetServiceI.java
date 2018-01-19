package com.buss.count.service;
import com.buss.count.entity.TStoreMonTargetEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface TStoreMonTargetServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TStoreMonTargetEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TStoreMonTargetEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TStoreMonTargetEntity t);

	/**获取店铺导购列表的月目标
	 * @param tStoreMonTarget
	 * @param req
	 */
	public void getGuideTargetList(TStoreMonTargetEntity tStoreMonTarget,HttpServletRequest req);

	/**设置导购月目标和店铺月目标（从导购目标合计而来）
	 * @param tStoreMonTarget
	 */
	public void doUpdate(TStoreMonTargetEntity tStoreMonTarget);
}
