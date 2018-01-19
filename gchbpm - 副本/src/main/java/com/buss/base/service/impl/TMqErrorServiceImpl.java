package com.buss.base.service.impl;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.base.entity.TMqErrorEntity;
import com.buss.base.service.TMqErrorServiceI;

@Service("tMqErrorService")
@Transactional
public class TMqErrorServiceImpl extends CommonServiceImpl implements TMqErrorServiceI {

	@Resource  
	private RabbitTemplate rabbitTemplate;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TMqErrorEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TMqErrorEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TMqErrorEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TMqErrorEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TMqErrorEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TMqErrorEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TMqErrorEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{add_time}",String.valueOf(t.getAddTime()));
 		sql  = sql.replace("#{order_id}",String.valueOf(t.getOrderId()));
 		sql  = sql.replace("#{order_type}",String.valueOf(t.getOrderType()));
 		sql  = sql.replace("#{queue_name}",String.valueOf(t.getQueueName()));
 		sql  = sql.replace("#{method}",String.valueOf(t.getMethod()));
 		sql  = sql.replace("#{mq_msg}",String.valueOf(t.getMqMsg()));
 		sql  = sql.replace("#{error_msg}",String.valueOf(t.getErrorMsg()));
 		sql  = sql.replace("#{deal_status}",String.valueOf(t.getDealStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void doDeal(TMqErrorEntity tMqError) {
 		String key = tMqError.getQueueName();//队列名称
 		try {
 			//消息内容
 			@SuppressWarnings("unchecked")
			Map<String, Object> map = MAPPER.readValue(tMqError.getMqMsg(),HashMap.class);
 			//发送MQ消息
 			rabbitTemplate.convertAndSend(key, map);
 		} catch (JsonParseException e) {
 			e.printStackTrace();
 		} catch (JsonMappingException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 		tMqError.setDealStatus(TMqErrorEntity.DEAL_STATUS_1);
 		this.commonDao.updateEntitie(tMqError);
 	
 	}
 	
 	/**
	 * 生成mq发送失败消息
	 * @param msg 错误消息
	 * @param method 发送方法
	 * @param type  业务类型  1：充值，2订单，3退货,4订单自动回复
	 * @param orderId 订单id或充值单号或退款单id
	 * @param mqMsg  mq消息
	 * @param queueName 队列名称
	 */
	@Override
	public void saveMqError(String msg,String method,String type,String orderId,String mqMsg,String queueName){
		TMqErrorEntity mqError = new TMqErrorEntity();
		mqError.setAddTime(Utility.getCurrentTimestamp());
		mqError.setErrorMsg(msg);
		mqError.setMethod(method);
		mqError.setOrderId(orderId);
		mqError.setOrderType(type);
		mqError.setMqMsg(mqMsg);
		mqError.setQueueName(queueName);
		commonDao.save(mqError);
	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
}