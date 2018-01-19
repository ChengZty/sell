package com.buss.goods.service;

import java.io.Serializable;

import com.buss.goods.entity.TGoodsActStoreEntity;


public interface TGoodsActStoreServiceI {
	 public <T> void delete(T entity);
	 	
	 	public <T> Serializable save(T entity);
	 	
	 	public <T> void saveOrUpdate(T entity);
	 	
	 	/**
		 * 默认按钮-sql增强-新增操作
		 * @param id
		 * @return
		 */
	 	public boolean doAddSql(TGoodsActStoreEntity t);
	 	/**
		 * 默认按钮-sql增强-更新操作
		 * @param id
		 * @return
		 */
	 	public boolean doUpdateSql(TGoodsActStoreEntity t);
	 	/**
		 * 默认按钮-sql增强-删除操作
		 * @param id
		 * @return
		 */
	 	public boolean doDelSql(TGoodsActStoreEntity t);
}
