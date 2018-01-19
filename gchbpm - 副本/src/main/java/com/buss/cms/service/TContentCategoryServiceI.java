package com.buss.cms.service;
import com.buss.cms.entity.TContentCategoryEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

public interface TContentCategoryServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TContentCategoryEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TContentCategoryEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TContentCategoryEntity t);
 	/**
 	 * 根据父节点id查询列表
 	 * @param parentId
 	 * @return
 	 */
	public List<TContentCategoryEntity> queryListByWhere(Long parentId);

	public void myInsert(TContentCategoryEntity tContentCategoryEntity);
}
