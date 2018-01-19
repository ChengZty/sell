package com.buss.bill.service.impl;
import com.buss.bill.service.OrderBillDetailServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.bill.entity.OrderBillDetailEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("orderBillDetailService")
@Transactional
public class OrderBillDetailServiceImpl extends CommonServiceImpl implements OrderBillDetailServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((OrderBillDetailEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((OrderBillDetailEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((OrderBillDetailEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(OrderBillDetailEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(OrderBillDetailEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(OrderBillDetailEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,OrderBillDetailEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{ob_order_sn}",String.valueOf(t.getObOrderSn()));
 		sql  = sql.replace("#{ob_year}",String.valueOf(t.getObYear()));
 		sql  = sql.replace("#{ob_month}",String.valueOf(t.getObMonth()));
 		sql  = sql.replace("#{ob_start_date}",String.valueOf(t.getObStartDate()));
 		sql  = sql.replace("#{ob_end_date}",String.valueOf(t.getObEndDate()));
 		sql  = sql.replace("#{ob_pay_date}",String.valueOf(t.getObPayDate()));
 		sql  = sql.replace("#{ob_order_totals}",String.valueOf(t.getObOrderTotals()));
 		sql  = sql.replace("#{ob_shipping_totals}",String.valueOf(t.getObShippingTotals()));
 		sql  = sql.replace("#{ob_commis_totals}",String.valueOf(t.getObCommisTotals()));
 		sql  = sql.replace("#{ob_order_return_totals}",String.valueOf(t.getObOrderReturnTotals()));
 		sql  = sql.replace("#{ob_commis_return_totals}",String.valueOf(t.getObCommisReturnTotals()));
 		sql  = sql.replace("#{ob_result_totals}",String.valueOf(t.getObResultTotals()));
 		sql  = sql.replace("#{ob_state}",String.valueOf(t.getObState()));
 		sql  = sql.replace("#{ob_payer_id}",String.valueOf(t.getObPayerId()));
 		sql  = sql.replace("#{ob_payer_name}",String.valueOf(t.getObPayerName()));
 		sql  = sql.replace("#{ob_store_id}",String.valueOf(t.getObStoreId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}