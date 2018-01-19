package com.buss.bill.service.impl;
import com.buss.bill.service.TActivityBillServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.bill.entity.TActivityBillEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tActivityBillService")
@Transactional
public class TActivityBillServiceImpl extends CommonServiceImpl implements TActivityBillServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TActivityBillEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TActivityBillEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TActivityBillEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TActivityBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TActivityBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TActivityBillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TActivityBillEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{fin_bill_id}",String.valueOf(t.getFinBillId()));
 		sql  = sql.replace("#{add_time}",String.valueOf(t.getAddTime()));
 		sql  = sql.replace("#{order_time}",String.valueOf(t.getOrderTime()));
 		sql  = sql.replace("#{business_date}",String.valueOf(t.getBusinessDate()));
 		sql  = sql.replace("#{business_type}",String.valueOf(t.getBusinessType()));
 		sql  = sql.replace("#{order_no}",String.valueOf(t.getOrderNo()));
 		sql  = sql.replace("#{sub_order_no}",String.valueOf(t.getSubOrderNo()));
 		sql  = sql.replace("#{goods_id}",String.valueOf(t.getGoodsId()));
 		sql  = sql.replace("#{to_store_goods_id}",String.valueOf(t.getToStoreGoodsId()));
 		sql  = sql.replace("#{store_type}",String.valueOf(t.getStoreType()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{guide_id}",String.valueOf(t.getGuideId()));
 		sql  = sql.replace("#{fin_act_id}",String.valueOf(t.getFinActId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}