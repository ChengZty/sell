package com.buss.activity.service;
import com.buss.activity.entity.TGiftRuleEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TGiftRuleServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TGiftRuleEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TGiftRuleEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TGiftRuleEntity t);

	public void saveGiftRule(TGiftRuleEntity tGiftRule);
}
