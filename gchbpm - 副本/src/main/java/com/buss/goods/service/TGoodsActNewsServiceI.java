package com.buss.goods.service;

import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.springframework.stereotype.Service;

import com.buss.goods.entity.TGoodsActNewsEntity;

//@Service("tGoodsActNewsServiceI")
public interface TGoodsActNewsServiceI extends CommonService {

    public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TGoodsActNewsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TGoodsActNewsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TGoodsActNewsEntity t);
	
}
