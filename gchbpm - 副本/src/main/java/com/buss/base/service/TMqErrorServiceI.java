package com.buss.base.service;
import com.buss.base.entity.TMqErrorEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TMqErrorServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TMqErrorEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TMqErrorEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TMqErrorEntity t);

	public void doDeal(TMqErrorEntity tMqError);
 	
 	/**
	 * 生成mq发送失败消息
	 * @param msg 错误消息
	 * @param method 发送方法
	 * @param type  业务类型  1：充值，2订单，3退货,4订单自动回复
	 * @param orderId 订单id或充值单号或退款单id
	 * @param mqMsg  mq消息
	 * @param queueName 队列名称
	 */
	void saveMqError(String msg, String method, String type, String orderId,
			String mqMsg,String queueName);
}
