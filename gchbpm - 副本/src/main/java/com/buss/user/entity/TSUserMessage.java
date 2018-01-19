package com.buss.user.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 短信验证码记录表
 * @author zhangdaihao
 * @date 2016-03-17 16:54:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_user_message", schema = "")
@SuppressWarnings("serial")
public class TSUserMessage implements java.io.Serializable {
	/**验证码*/
	public static final String MSG_TYPE_1 = "1";
	/**确认订单*/
	public static final String MSG_TYPE_2 = "2";
	/**支付*/
	public static final String MSG_TYPE_3 = "3";
	/**发货*/
	public static final String MSG_TYPE_4 = "4";
	/**申请退款*/
	public static final String MSG_TYPE_5 = "5";
	/**退货成功*/
	public static final String MSG_TYPE_6 = "6";
	
	/**id*/
	private java.lang.String id;
	/**用户名*/
	private java.lang.String userName;
	/**用户类型*/
	private java.lang.String userType;
	/**imei*/
	private java.lang.String imei;
	/**ipAddress*/
	private java.lang.String ipAddress;
	/**发送日期*/
	private Timestamp sendDate;
	/**短信内容（验证码）*/
	private java.lang.String content;
	/**发送短信的类型*/
	private java.lang.String msgType;//发送短信的类型：1验证码，2确认订单，3.支付，4发货，5申请退款，6退货成功
	/**零售商id*/
	private java.lang.String retailerId;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户名
	 */
	@Column(name ="USER_NAME",nullable=true,length=20)
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构
	 */
	@Column(name ="IMEI",nullable=true,length=36)
	public java.lang.String getImei(){
		return this.imei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构
	 */
	public void setImei(java.lang.String imei){
		this.imei = imei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ipAddress
	 */
	@Column(name ="IP_ADDRESS",nullable=true,length=50)
	public java.lang.String getIpAddress(){
		return this.ipAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ipAddress
	 */
	public void setIpAddress(java.lang.String ipAddress){
		this.ipAddress = ipAddress;
	}
	@Column(name ="USER_TYPE",nullable=true,length=1)
	public java.lang.String getUserType() {
		return userType;
	}

	public void setUserType(java.lang.String userType) {
		this.userType = userType;
	}
	@Column(name = "SEND_DATE", nullable = true, length = 35)
	public Timestamp getSendDate() {
		return sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}
	
	@Column(name = "CONTENT", nullable = true, length = 35)
	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}
	@Column(name = "MSG_TYPE")
	public java.lang.String getMsgType() {
		return msgType;
	}

	public void setMsgType(java.lang.String msgType) {
		this.msgType = msgType;
	}
	@Column(name = "RETAILER_ID")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	
}
