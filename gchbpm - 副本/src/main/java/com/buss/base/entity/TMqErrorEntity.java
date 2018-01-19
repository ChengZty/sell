package com.buss.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**   
 * @Title: Entity
 * @Description: mq错误信息表
 * @author onlineGenerator
 * @date 2017-01-05 20:35:32
 * @version V1.0   
 *
 */
@Entity
@Table(name="t_mq_error")
public class TMqErrorEntity implements java.io.Serializable {
	private static final long serialVersionUID = 792352673312776592L;
	/**订单类型*/
	public static final String ORDER_TYPE_1 = "1";//充值单
	public static final String ORDER_TYPE_2 = "2";//订单
	public static final String ORDER_TYPE_3 = "3";//退款单
	public static final String ORDER_TYPE_4 = "4";//订单短信自动回复
	/**处理状态*/
	public static final String DEAL_STATUS_0 = "0";//待处理
	public static final String DEAL_STATUS_1 = "1";//处理成功
	public static final String DEAL_STATUS_2 = "2";//处理失败

	private String id;
	/*添加时间*/
	private java.util.Date addTime;
	/*订单ID*/
	private String orderId;
	/*订单类型*/
	private String orderType;
	/*方法名称*/
	private String method;
	/*mq发送的消息*/
	private String mqMsg;
	/*错误信息*/
	private String errorMsg;
	/*队列名称  应该存key的值*/
	private String queueName;
	/*处理状态*/
	private String dealStatus;
	/*处理时间*/
	private java.util.Date dealTime;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name ="ADD_TIME")
	public java.util.Date getAddTime() {
		return addTime;
	}
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	@Column(name ="ORDER_ID")
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Column(name ="ORDER_TYPE")
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	@Column(name ="METHOD")
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	@Column(name ="MQ_MSG")
	public String getMqMsg() {
		return mqMsg;
	}
	public void setMqMsg(String mqMsg) {
		this.mqMsg = mqMsg;
	}
	@Column(name ="ERROR_MSG")
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	@Column(name ="QUEUE_NAME")
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	@Column(name ="DEAL_STATUS")
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	@Column(name ="DEAL_TIME")
	public java.util.Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(java.util.Date dealTime) {
		this.dealTime = dealTime;
	}
	
}
