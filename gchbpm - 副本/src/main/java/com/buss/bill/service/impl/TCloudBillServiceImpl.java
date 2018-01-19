package com.buss.bill.service.impl;
import com.buss.bill.service.TCloudBillServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.bill.entity.TCloudBillEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tCloudBillService")
@Transactional
public class TCloudBillServiceImpl extends CommonServiceImpl implements TCloudBillServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TCloudBillEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TCloudBillEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TCloudBillEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TCloudBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TCloudBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TCloudBillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TCloudBillEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{fin_bill_id}",String.valueOf(t.getFinBillId()));
 		sql  = sql.replace("#{add_time}",String.valueOf(t.getAddTime()));
 		sql  = sql.replace("#{order_no}",String.valueOf(t.getOrderNo()));
 		sql  = sql.replace("#{sub_order_no}",String.valueOf(t.getSubOrderNo()));
 		sql  = sql.replace("#{business_date}",String.valueOf(t.getBusinessDate()));
 		sql  = sql.replace("#{cloud_money}",String.valueOf(t.getCloudMoney()));
 		sql  = sql.replace("#{store_id}",String.valueOf(t.getStoreId()));
 		sql  = sql.replace("#{to_store_goods_id}",String.valueOf(t.getToStoreGoodsId()));
 		sql  = sql.replace("#{to_store_goods_name}",String.valueOf(t.getToStoreGoodsName()));
 		sql  = sql.replace("#{top_category_id}",String.valueOf(t.getTopCategoryId()));
 		sql  = sql.replace("#{sub_category_id}",String.valueOf(t.getSubCategoryId()));
 		sql  = sql.replace("#{thrid_category_id}",String.valueOf(t.getThridCategoryId()));
 		sql  = sql.replace("#{brand_id}",String.valueOf(t.getBrandId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}