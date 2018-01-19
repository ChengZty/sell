package com.buss.bill.service.impl;
import com.buss.bill.service.THelperBillServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.bill.entity.THelperBillEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tHelperBillService")
@Transactional
public class THelperBillServiceImpl extends CommonServiceImpl implements THelperBillServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((THelperBillEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((THelperBillEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((THelperBillEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(THelperBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(THelperBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(THelperBillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,THelperBillEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{fin_bill_id}",String.valueOf(t.getFinBillId()));
 		sql  = sql.replace("#{add_time}",String.valueOf(t.getAddTime()));
 		sql  = sql.replace("#{order_no}",String.valueOf(t.getOrderNo()));
 		sql  = sql.replace("#{sub_order_no}",String.valueOf(t.getSubOrderNo()));
 		sql  = sql.replace("#{business_date}",String.valueOf(t.getBusinessDate()));
 		sql  = sql.replace("#{helper_money}",String.valueOf(t.getHelperMoney()));
 		sql  = sql.replace("#{helper_id}",String.valueOf(t.getHelperId()));
 		sql  = sql.replace("#{helper_name}",String.valueOf(t.getHelperName()));
 		sql  = sql.replace("#{helper_type}",String.valueOf(t.getHelperType()));
 		sql  = sql.replace("#{sub_category_id}",String.valueOf(t.getSubCategoryId()));
 		sql  = sql.replace("#{guide_id}",String.valueOf(t.getGuideId()));
 		sql  = sql.replace("#{store_id}",String.valueOf(t.getStoreId()));
 		sql  = sql.replace("#{to_store_goods_id}",String.valueOf(t.getToStoreGoodsId()));
 		sql  = sql.replace("#{store_type}",String.valueOf(t.getStoreType()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}