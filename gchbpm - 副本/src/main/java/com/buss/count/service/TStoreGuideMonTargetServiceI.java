package com.buss.count.service;
import com.buss.count.entity.TStoreGuideMonTargetEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TStoreGuideMonTargetServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TStoreGuideMonTargetEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TStoreGuideMonTargetEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TStoreGuideMonTargetEntity t);

	/**删除导购月目标并更新店铺月目标
	 * @param tStoreGuideMonTarget
	 */
	public void doDel(TStoreGuideMonTargetEntity tStoreGuideMonTarget);

	/**更新导购月目标并更新店铺月目标
	 * @param tStoreGuideMonTarget
	 */
	public void doUpdate(TStoreGuideMonTargetEntity tStoreGuideMonTarget);
}
