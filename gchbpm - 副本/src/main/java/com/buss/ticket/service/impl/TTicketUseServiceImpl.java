package com.buss.ticket.service.impl;
import com.buss.ticket.service.TTicketUseServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.ticket.entity.TTicketUseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tTicketUseService")
@Transactional
public class TTicketUseServiceImpl extends CommonServiceImpl implements TTicketUseServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TTicketUseEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TTicketUseEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TTicketUseEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TTicketUseEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TTicketUseEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TTicketUseEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TTicketUseEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{add_time}",String.valueOf(t.getAddTime()));
 		sql  = sql.replace("#{ticket_id}",String.valueOf(t.getTicketId()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{ticket_detail_id}",String.valueOf(t.getTicketDetailId()));
 		sql  = sql.replace("#{ticket_preferential}",String.valueOf(t.getTicketPreferential()));
 		sql  = sql.replace("#{sub_order_no}",String.valueOf(t.getSubOrderNo()));
 		sql  = sql.replace("#{to_goods_store_id}",String.valueOf(t.getToGoodsStoreId()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
 		sql  = sql.replace("#{user_name}",String.valueOf(t.getUserName()));
 		sql  = sql.replace("#{use_type}",String.valueOf(t.getUseType()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}