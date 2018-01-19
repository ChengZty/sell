package com.buss.bill.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.bill.entity.TCommissionStoreRuleEntity;
import com.buss.bill.service.TCommissionStoreRuleServiceI;

@Service("tCommissionStoreRuleService")
@Transactional
public class TCommissionStoreRuleServiceImpl extends CommonServiceImpl implements TCommissionStoreRuleServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TCommissionStoreRuleEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TCommissionStoreRuleEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TCommissionStoreRuleEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TCommissionStoreRuleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TCommissionStoreRuleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TCommissionStoreRuleEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TCommissionStoreRuleEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{ctype}",String.valueOf(t.getCtype()));
 		sql  = sql.replace("#{system_accounting}",String.valueOf(t.getSystemAccounting()));
 		sql  = sql.replace("#{store_accounting}",String.valueOf(t.getStoreAccounting()));
 		sql  = sql.replace("#{guide_accounting}",String.valueOf(t.getGuideAccounting()));
 		sql  = sql.replace("#{helper_accounting}",String.valueOf(t.getHelperAccounting()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public void saveList(List<TCommissionStoreRuleEntity> dataList) {
		for (TCommissionStoreRuleEntity rule : dataList) {
			if(Utility.isEmpty(rule.getId())){
				this.commonDao.save(rule);
			}else{
				commonDao.saveOrUpdate(rule);
			}
		}
	}
}