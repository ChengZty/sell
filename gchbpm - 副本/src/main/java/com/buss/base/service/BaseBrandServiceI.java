package com.buss.base.service;
import com.buss.base.entity.BaseBrandEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

public interface BaseBrandServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(BaseBrandEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(BaseBrandEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(BaseBrandEntity t);

	public void saveBatch(List<BaseBrandEntity> listBaseBrandEntitys);

	public void saveBrand(BaseBrandEntity baseBrand);

	public void updateBrand(BaseBrandEntity baseBrand);
}
