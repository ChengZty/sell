package com.buss.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 订单日志
 * @author onlineGenerator
 * @date 2016-04-06 15:24:05
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_order_log", schema = "")
@SuppressWarnings("serial")
public class TOrderLogEntity implements java.io.Serializable {
	public static String TYPE_1 = "1";//订单
	public static String TYPE_2 = "2";//退款
	/**主键*/
	private java.lang.String id;
	/**订单id*/
	private java.lang.String orderId;
	/**订单号*/
	private java.lang.String orderNo;
	/**处理时间*/
	private java.util.Date logTime;
	/**角色名称*/
	private java.lang.String logRoleName;
	/**订单状态*/
	private java.lang.String logOrderStatus;
	/**日志描述*/
	private java.lang.String logMsg;
	/**操作人*/
	private java.lang.String logUserName;
	/**类型   默认为1：订单，2：退款*/
	private java.lang.String type = "1";
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	@Column(name ="ORDER_ID")
	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}
	@Column(name ="ORDER_NO")
	public java.lang.String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  处理时间
	 */
	@Column(name ="LOG_TIME",nullable=true,length=20)
	public java.util.Date getLogTime(){
		return this.logTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  处理时间
	 */
	public void setLogTime(java.util.Date logTime){
		this.logTime = logTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  角色名称
	 */
	@Column(name ="LOG_ROLE_NAME",nullable=true,length=50)
	public java.lang.String getLogRoleName(){
		return this.logRoleName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  角色名称
	 */
	public void setLogRoleName(java.lang.String logRoleName){
		this.logRoleName = logRoleName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  子订单状态
	 */
	@Column(name ="LOG_ORDER_STATUS",nullable=true,length=2)
	public java.lang.String getLogOrderStatus(){
		return this.logOrderStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  子订单状态
	 */
	public void setLogOrderStatus(java.lang.String logOrderStatus){
		this.logOrderStatus = logOrderStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  日志描述
	 */
	@Column(name ="LOG_MSG",nullable=true,length=500)
	public java.lang.String getLogMsg(){
		return this.logMsg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  日志描述
	 */
	public void setLogMsg(java.lang.String logMsg){
		this.logMsg = logMsg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人
	 */
	@Column(name ="LOG_USER_NAME",nullable=true,length=32)
	public java.lang.String getLogUserName(){
		return this.logUserName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人
	 */
	public void setLogUserName(java.lang.String logUserName){
		this.logUserName = logUserName;
	}
	@Column(name ="TYPE")
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}
	
}
