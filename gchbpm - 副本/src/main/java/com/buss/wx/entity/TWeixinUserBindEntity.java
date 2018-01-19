package com.buss.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 微信卡片绑定表
 * @author onlineGenerator
 * @date 2018-01-05 11:40:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_weixin_user_bind", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TWeixinUserBindEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**状态*/
	private java.lang.String status;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**导购ID*/
	private java.lang.String guideId;
	/**微信用户id*/
	private java.lang.String wxUserId;
	/**顾客ID*/
	private java.lang.String customerId;
	/**绑定日期*/
	private java.util.Date bindDate;
	
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商ID
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商ID
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购ID
	 */
	@Column(name ="GUIDE_ID",nullable=true,length=36)
	public java.lang.String getGuideId(){
		return this.guideId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购ID
	 */
	public void setGuideId(java.lang.String guideId){
		this.guideId = guideId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  微信用户id
	 */
	@Column(name ="WX_USER_ID",nullable=true,length=36)
	public java.lang.String getWxUserId(){
		return this.wxUserId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  微信用户id
	 */
	public void setWxUserId(java.lang.String wxUserId){
		this.wxUserId = wxUserId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  顾客ID
	 */
	@Column(name ="CUSTOMER_ID",nullable=true,length=36)
	public java.lang.String getCustomerId(){
		return this.customerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  顾客ID
	 */
	public void setCustomerId(java.lang.String customerId){
		this.customerId = customerId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  绑定日期
	 */
	@Column(name ="BIND_DATE",nullable=true,length=20)
	public java.util.Date getBindDate(){
		return this.bindDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  绑定日期
	 */
	public void setBindDate(java.util.Date bindDate){
		this.bindDate = bindDate;
	}
}
