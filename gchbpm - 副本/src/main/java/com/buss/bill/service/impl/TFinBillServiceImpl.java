package com.buss.bill.service.impl;
import com.buss.bill.service.TFinBillServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.bill.entity.TFinBillEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tFinBillService")
@Transactional
public class TFinBillServiceImpl extends CommonServiceImpl implements TFinBillServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TFinBillEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TFinBillEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TFinBillEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TFinBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TFinBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TFinBillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TFinBillEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{add_time}",String.valueOf(t.getAddTime()));
 		sql  = sql.replace("#{order_No}",String.valueOf(t.getOrderNo()));
 		sql  = sql.replace("#{business_date}",String.valueOf(t.getBusinessDate()));
 		sql  = sql.replace("#{business_type}",String.valueOf(t.getBusinessType()));
 		sql  = sql.replace("#{order_amount}",String.valueOf(t.getOrderAmount()));
 		sql  = sql.replace("#{cloud_amount}",String.valueOf(t.getCloudAmount()));
 		sql  = sql.replace("#{cust_amount}",String.valueOf(t.getCustAmount()));
 		sql  = sql.replace("#{store_amount}",String.valueOf(t.getStoreAmount()));
 		sql  = sql.replace("#{guide_amount}",String.valueOf(t.getGuideAmount()));
 		sql  = sql.replace("#{system_amount}",String.valueOf(t.getSystemAmount()));
 		sql  = sql.replace("#{helper_amount}",String.valueOf(t.getHelperAmount()));
 		sql  = sql.replace("#{cust_id}",String.valueOf(t.getCustId()));
 		sql  = sql.replace("#{cust_name}",String.valueOf(t.getCustName()));
 		sql  = sql.replace("#{guide_id}",String.valueOf(t.getGuideId()));
 		sql  = sql.replace("#{guide_name}",String.valueOf(t.getGuideName()));
 		sql  = sql.replace("#{store_id}",String.valueOf(t.getStoreId()));
 		sql  = sql.replace("#{store_name}",String.valueOf(t.getStoreName()));
 		sql  = sql.replace("#{tb_status}",String.valueOf(t.getTbStatus()));
 		sql  = sql.replace("#{bill_no}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}