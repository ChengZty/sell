package com.buss.base.service;
import com.buss.base.entity.TSceneInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TSceneInfoServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSceneInfoEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSceneInfoEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSceneInfoEntity t);

	/**保存场景
	 * @param tSceneInfo
	 */
	public void saveScene(TSceneInfoEntity tSceneInfo);

	public void updateScene(TSceneInfoEntity t);

	public void deleteScene(String id);
}
