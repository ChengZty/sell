package com.buss.shop.service;
import com.buss.shop.entity.TPosterEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TPosterServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPosterEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPosterEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPosterEntity t);

	/**调换上下排序的位置
	 * @param id
	 * @param type 类型 U:上移 D:下移
	 * @param sortNum 排序
	 * @param postStatus 状态
	 */
	public void doChangeSort(String id, String type, String sortNum,String postStatus);
}
