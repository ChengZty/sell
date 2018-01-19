package com.buss.recharge.service.impl;
import com.buss.balance.entity.TBalanceEntity;
import com.buss.bill.entity.TBillEntity;
import com.buss.recharge.service.TRechargeServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;

import com.buss.recharge.entity.TRechargeEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tRechargeService")
@Transactional
public class TRechargeServiceImpl extends CommonServiceImpl implements TRechargeServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TRechargeEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TRechargeEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TRechargeEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TRechargeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TRechargeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TRechargeEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TRechargeEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
 		sql  = sql.replace("#{phone_no}",String.valueOf(t.getPhoneNo()));
 		sql  = sql.replace("#{money}",String.valueOf(t.getMoney()));
 		sql  = sql.replace("#{pay_time}",String.valueOf(t.getPayTime()));
 		sql  = sql.replace("#{pay_type}",String.valueOf(t.getPayType()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public String saveRecharge(TRechargeEntity tRecharge) {
 		String msg = null;
 		//更新余额
 		TBalanceEntity tBalance = this.commonDao.findUniqueByProperty(TBalanceEntity.class, "userId", tRecharge.getUserId());
 		if(Utility.isEmpty(tBalance)){
 			msg = "该顾客没购买过G+卡，无法充值";
 			return msg;
 		}
 		tBalance.setAvailableBalance(tBalance.getAvailableBalance().add(tRecharge.getMoney()));
 		tBalance.setTotalBalance(tBalance.getTotalBalance().add(tRecharge.getMoney()));
 		this.commonDao.updateEntitie(tBalance);
 		//保存充值记录
 		tRecharge.setAddTime(Utility.getCurrentTimestamp());
 		tRecharge.setPayTime(Utility.getCurrentTimestamp());
		tRecharge.setPayType(TRechargeEntity.PAY_TYPE_3);
		tRecharge.setPayStatus(TRechargeEntity.PAY_STATUS_2);
		tRecharge.setStatus("A");
 		this.commonDao.save(tRecharge);
 		//保存账单记录
 		TBillEntity tBill = new TBillEntity();
 		tBill.setBillNo(commonDao.getOrderNo(common.GlobalConstants.ORDER_TYPE_BILL));
 		tBill.setBillAmount(tRecharge.getMoney());
 		tBill.setBillType(TBillEntity.BILL_TYPE_3);
 		tBill.setBusinessDate(Utility.getCurrentTimestamp());
 		tBill.setPhoneNo(tRecharge.getPhoneNo());
 		tBill.setUserId(tRecharge.getUserId());
 		tBill.setRetailerId(tRecharge.getRetailerId());
 		tBill.setStatus("A");
 		this.commonDao.save(tBill);
 		return msg;
 	}
}