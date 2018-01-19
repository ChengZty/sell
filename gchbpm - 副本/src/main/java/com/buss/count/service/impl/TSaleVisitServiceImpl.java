package com.buss.count.service.impl;
import com.buss.count.service.TSaleVisitServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.count.entity.TSaleVisitEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tSaleVisitService")
@Transactional
public class TSaleVisitServiceImpl extends CommonServiceImpl implements TSaleVisitServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSaleVisitEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSaleVisitEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSaleVisitEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSaleVisitEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSaleVisitEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSaleVisitEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSaleVisitEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{guide_id}",String.valueOf(t.getGuideId()));
 		sql  = sql.replace("#{guide_name}",String.valueOf(t.getGuideName()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
 		sql  = sql.replace("#{user_name}",String.valueOf(t.getUserName()));
 		sql  = sql.replace("#{pay_time}",String.valueOf(t.getPayTime()));
 		sql  = sql.replace("#{order_id}",String.valueOf(t.getOrderId()));
 		sql  = sql.replace("#{order_no}",String.valueOf(t.getOrderNo()));
 		sql  = sql.replace("#{visit_status}",String.valueOf(t.getVisitStatus()));
 		sql  = sql.replace("#{visit_one}",String.valueOf(t.getVisitOne()));
 		sql  = sql.replace("#{visit_two}",String.valueOf(t.getVisitTwo()));
 		sql  = sql.replace("#{visit_thr}",String.valueOf(t.getVisitThr()));
 		sql  = sql.replace("#{visit_fou}",String.valueOf(t.getVisitFou()));
 		sql  = sql.replace("#{visit_fiv}",String.valueOf(t.getVisitFiv()));
 		sql  = sql.replace("#{visit_process}",String.valueOf(t.getVisitProcess()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}