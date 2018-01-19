package com.buss.words.service;
import com.buss.words.entity.TChatWordsTypeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TChatWordsTypeServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TChatWordsTypeEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TChatWordsTypeEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TChatWordsTypeEntity t);

	public void delType(TChatWordsTypeEntity tChatWordsType);
}
