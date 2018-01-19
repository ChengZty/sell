package com.buss.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: t_sms_send_report
 * @author onlineGenerator
 * @date 2017-02-25 12:31:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sms_send_report", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TSmsSendReportEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**状态*/
	private java.lang.String status;
	/**手机号码*/
	private java.lang.String phone;
	/**短信平台消息id*/
	private java.lang.String msgId;
	/**回调时间*/
	private java.util.Date sendTime;
	/**标记，是否处理  0未处理，1已处理*/
	private java.lang.String flag;
	/**回调状态*/
	private java.lang.String receiveStatus;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	@Column(name ="status")
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	@Column(name ="phone")
	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	@Column(name ="msg_id")
	public java.lang.String getMsgId() {
		return msgId;
	}

	public void setMsgId(java.lang.String msgId) {
		this.msgId = msgId;
	}
	@Column(name ="send_time")
	public java.util.Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}
	@Column(name ="flag")
	public java.lang.String getFlag() {
		return flag;
	}

	public void setFlag(java.lang.String flag) {
		this.flag = flag;
	}
	@Column(name ="receive_status")
	public java.lang.String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(java.lang.String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	
}
