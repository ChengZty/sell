package com.buss.shop.service;

import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.buss.shop.entity.TSmsPicInfoEntity;


public interface TSmsPicInfoServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSmsPicInfoEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSmsPicInfoEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSmsPicInfoEntity t);
 	
 	/**
 	 * 获取零售商的图片
 	 * @param retailerId
 	 * @return
 	 */
 	public List<TSmsPicInfoEntity> getPicByClassAndPage(String retailerId,String classId,int page,int rows);

 	/**
 	 * 获取零售商的图片
 	 * @param retailerId
 	 * @return
 	 */
 	public int updatePicStatusByClass(String classId,String unclassified);

 	/**
 	 *删除图片
 	 * @param retailerId
 	 * @return
 	 */
 	public int delPicStatusByClass(String classId);

	public void doBatchDel(String ids);

}
