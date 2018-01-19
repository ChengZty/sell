package com.buss.balance.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.balance.entity.TCardRateEntity;
import com.buss.balance.service.TCardRateServiceI;

@Service("tCardRateService")
@Transactional
public class TCardRateServiceImpl extends CommonServiceImpl implements TCardRateServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TCardRateEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TCardRateEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TCardRateEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TCardRateEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TCardRateEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TCardRateEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TCardRateEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{retailer_rate}",String.valueOf(t.getRetailerRate()));
 		sql  = sql.replace("#{guide_rate}",String.valueOf(t.getGuideRate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void saveOrUpdateCardRate(TCardRateEntity tCardRate) {
 		List<TCardRateEntity> rateList = tCardRate.getRateList();
 		for(TCardRateEntity entity : rateList){
 			if(Utility.isEmpty(entity.getId())){
 				entity.setStatus("A");
 				this.commonDao.save(entity);
 			}else{
 				TSUser user = ResourceUtil.getSessionUserName();
 				StringBuffer updateSql = new StringBuffer("update t_card_rate set update_by = '").append(user.getUserName()).append("',update_name = '")
 				.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',retailer_rate =")
 				.append(entity.getRetailerRate()).append(",guide_rate = ").append(entity.getGuideRate()).append(" where id ='").append(entity.getId()).append("'");
 				this.commonDao.updateBySqlString(updateSql.toString());
 			}
 		}
 		
 	}
}