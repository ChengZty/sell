package com.buss.goods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * 活动模版
 */
@Entity
@Table(name = "t_goods_act_template", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TGoodsActTemplateEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
	/**创建日期*/
	private java.util.Date createDate;
	/**状态*/
	private java.lang.String status;
	/**模版名称*/
	private String templateName;
	/**活动类型 MS:秒杀  XSZK:限时折扣 ZB:直播*/
	private java.lang.String actType ;
	/**模版编码*/
	private java.lang.Integer templateCode;
	/**活动图片**/
	private String picUrl;

	@Id
	@Column(name ="ID",nullable=false,length=5)
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	@Column(name ="STATUS",nullable=true,length=50)
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	@Column(name ="TEMPLATE_NAME",nullable=true,length=30)
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	@Column(name ="ACT_TYPE",nullable=true,length=10)
	public java.lang.String getActType() {
		return actType;
	}
	public void setActType(java.lang.String actType) {
		this.actType = actType;
	}
	@Column(name ="TEMPLATE_CODE",nullable=true,length=5)
	public java.lang.Integer getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(java.lang.Integer templateCode) {
		this.templateCode = templateCode;
	}
	@Column(name ="PIC_URL",nullable=true,length=150)
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
