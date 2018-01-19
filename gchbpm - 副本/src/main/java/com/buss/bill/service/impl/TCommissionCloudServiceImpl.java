package com.buss.bill.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.bill.entity.TCommissionCloudEntity;
import com.buss.bill.entity.TCommissionCloudInfoEntity;
import com.buss.bill.service.TCommissionCloudServiceI;

@Service("tCommissionCloudService")
@Transactional
public class TCommissionCloudServiceImpl extends CommonServiceImpl implements TCommissionCloudServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TCommissionCloudEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TCommissionCloudEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TCommissionCloudEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TCommissionCloudEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TCommissionCloudEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TCommissionCloudEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TCommissionCloudEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{top_category_id}",String.valueOf(t.getTopCategoryId()));
 		sql  = sql.replace("#{sub_category_id}",String.valueOf(t.getSubCategoryId()));
 		sql  = sql.replace("#{thrid_category_id}",String.valueOf(t.getThridCategoryId()));
 		sql  = sql.replace("#{top_category_name}",String.valueOf(t.getTopCategoryName()));
 		sql  = sql.replace("#{sub_category_name}",String.valueOf(t.getSubCategoryName()));
 		sql  = sql.replace("#{thrid_category_name}",String.valueOf(t.getThridCategoryName()));
 		sql  = sql.replace("#{brand_id}",String.valueOf(t.getBrandId()));
 		sql  = sql.replace("#{brand_name}",String.valueOf(t.getBrandName()));
 		sql  = sql.replace("#{commission}",String.valueOf(t.getCommission()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
 	 * 佣金分配规则保存
 	 * @param t
 	 */
 	public void saveCommission(TCommissionCloudEntity t){
 		this.commonDao.save(t);
 		String cid = t.getId();
 		List<TCommissionCloudInfoEntity> infoList = t.getCinfoList();
 		if(null != infoList){
 			for (TCommissionCloudInfoEntity info : infoList) {
 				info.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 				info.setCid(cid);
 			}
 			this.commonDao.batchSave(infoList);
 		}
 	}

	
 	/**
 	 * 修改
 	 */
	@Override
	public void saveOrUpdateCommission(TCommissionCloudEntity t) {
		this.commonDao.saveOrUpdate(t);
 		List<TCommissionCloudInfoEntity> infoList = t.getCinfoList();
 		if(null != infoList){
 			for (TCommissionCloudInfoEntity info : infoList) {
 				if(Utility.isEmpty(info.getId())){
 					this.commonDao.save(info);
 				}else{
 					this.commonDao.saveOrUpdate(info);	
 				}
 				
 			}
 		}
	}
}