package com.buss.activity.service.impl;
import com.buss.activity.service.TGiftRuleServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.buss.activity.entity.TGiftRuleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tGiftRuleService")
@Transactional
public class TGiftRuleServiceImpl extends CommonServiceImpl implements TGiftRuleServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TGiftRuleEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TGiftRuleEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TGiftRuleEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TGiftRuleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TGiftRuleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TGiftRuleEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TGiftRuleEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{rule_name}",String.valueOf(t.getRuleName()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{retailer_name}",String.valueOf(t.getRetailerName()));
 		sql  = sql.replace("#{money}",String.valueOf(t.getMoney()));
 		sql  = sql.replace("#{rule_type}",String.valueOf(t.getRuleType()));
 		sql  = sql.replace("#{rule_status}",String.valueOf(t.getRuleStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void saveGiftRule(TGiftRuleEntity tGiftRule) {
 		TSUser user = ResourceUtil.getSessionUserName();
 		if(user.getUserType().equals(common.GlobalConstants.USER_TYPE_02)){
			tGiftRule.setRetailerId(user.getId());
			tGiftRule.setRetailerName(user.getRealName());
		}else if(user.getUserType().equals(common.GlobalConstants.USER_TYPE_05)){
			TSUser retailer = this.commonDao.get(TSUser.class, user.getRetailerId());
			tGiftRule.setRetailerId(retailer.getId());
			tGiftRule.setRetailerName(retailer.getRealName());
		}
 		this.commonDao.save(tGiftRule);
 	}
 	
}