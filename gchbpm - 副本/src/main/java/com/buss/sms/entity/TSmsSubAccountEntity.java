package com.buss.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: t_sms_sub_account
 * @author onlineGenerator
 * @date 2017-02-25 12:31:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sms_sub_account", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TSmsSubAccountEntity implements java.io.Serializable {
	public static final String REMIND_OK = "1";//正常
	public static final String REMIND_NO = "2";//待提醒
	public static final String REMIND_YES = "3";//已提醒
	/**id*/
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
	/**短信子账号*/
//	private java.lang.String smsName;
	/**短信子账号密码*/
//	private java.lang.String smsPassword;
	/**账号总条数*/
	private java.lang.Integer smsNumber;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**短信产品ID*/
//	private java.lang.String productId;
	/**公司签名*/
	private java.lang.String companyAutographName;
	/**锁定条数*/
	private java.lang.Integer lockingNumber;
	/**变动条数 瞬时字段*/
	private java.lang.Integer modifyNumber;
	/**短信不足是否提醒*/
	private java.lang.String remind;
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
	@Column(name ="CREATE_DATE",nullable=true)
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
	@Column(name ="UPDATE_DATE",nullable=true)
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
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  账号总条数
	 */
	@Column(name ="SMS_NUMBER",nullable=true,length=10)
	public java.lang.Integer getSmsNumber(){
		return this.smsNumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  账号总条数
	 */
	public void setSmsNumber(java.lang.Integer smsNumber){
		this.smsNumber = smsNumber;
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
	 *@return: java.lang.String  公司签名名称
	 */
	@Column(name ="COMPANY_AUTOGRAPH_NAME",nullable=true,length=50)
	public java.lang.String getCompanyAutographName() {
		return companyAutographName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司签名名称
	 */
	public void setCompanyAutographName(java.lang.String companyAutographName) {
		this.companyAutographName = companyAutographName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  锁定条数
	 */
	@Column(name ="LOCKING_NUMBER",nullable=true,length=10)
	public java.lang.Integer getLockingNumber() {
		return lockingNumber;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  锁定条数
	 */
	public void setLockingNumber(java.lang.Integer lockingNumber) {
		this.lockingNumber = lockingNumber;
	}
	@Transient
	public java.lang.Integer getModifyNumber() {
		return modifyNumber;
	}

	public void setModifyNumber(java.lang.Integer modifyNumber) {
		this.modifyNumber = modifyNumber;
	}
	@Column(name ="remind")
	public java.lang.String getRemind() {
		return remind;
	}

	public void setRemind(java.lang.String remind) {
		this.remind = remind;
	}

	
	
}
