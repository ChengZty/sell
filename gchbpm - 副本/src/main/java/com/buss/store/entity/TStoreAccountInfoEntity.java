package com.buss.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import org.jeecgframework.poi.excel.annotation.Excel;


/**   
 * @Title: Entity
 * @Description: 零售商账户
 * @author onlineGenerator
 * @date 2016-09-22 20:39:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_store_account_info", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TStoreAccountInfoEntity implements java.io.Serializable {
	public static final String ACCOUNT_STATUS_OK = "1";//正常
	public static final String ACCOUNT_STATUS_BAD = "0";//余额不足
	public static final String REMIND_OK = "1";//正常
	public static final String REMIND_NO = "2";//待提醒
	public static final String REMIND_YES = "3";//已提醒
	
	
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
	/**激活日期*/
	@Excel(name="激活日期",width=20,format = "yyyy-MM-dd")
	private java.util.Date activeDate;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**零售商账号*/
	@Excel(name="客户账号",width=15)
	private java.lang.String retailerName;
	/**零售商名*/
	@Excel(name="客户名称",width=15)
	private java.lang.String retailerRealname;
	/**余额*/
	@Excel(name="账户余额",width=15)
	private java.math.BigDecimal remainMoney;
	/**月端口费用 数据来源于月端口扣费主表t_app_cost_info*/
	@Excel(name="月端口费用",width=15)
	private java.math.BigDecimal monthCharge;
	/**年度工具购买费 数据来源于月端口扣费主表t_app_cost_info*/
	@Excel(name="年度工具购买费",width=15)
	private java.math.BigDecimal yearCharge;
	/**账户状态*/
	@Excel(name="账户状态",width=15,replace={"正常_1","余额不足_0"})
	private java.lang.String accountStatus;
	/**余额不足是否提醒*/
	@Excel(name="是否提醒",width=15,replace={"正常_1","未提醒_0"})
	private java.lang.String remind;
	/**备注*/
	@Excel(name="备注",width=15)
	private java.lang.String remark;
	
	
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
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}

	@Column(name ="ACTIVE_DATE",nullable=true,length=20)
	public java.util.Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(java.util.Date activeDate) {
		this.activeDate = activeDate;
	}

	@Column(name ="RETAILER_NAME",nullable=true,length=36)
	public java.lang.String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(java.lang.String retailerName) {
		this.retailerName = retailerName;
	}

	@Column(name ="RETAILER_REALNAME",nullable=true,length=36)
	public java.lang.String getRetailerRealname() {
		return retailerRealname;
	}

	public void setRetailerRealname(java.lang.String retailerRealname) {
		this.retailerRealname = retailerRealname;
	}

	@Column(name ="REMAIN_MONEY",nullable=true,length=14)
	public java.math.BigDecimal getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(java.math.BigDecimal remainMoney) {
		this.remainMoney = remainMoney;
	}
	@Column(name ="ACCOUNT_STATUS",nullable=true,length=1)
	public java.lang.String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(java.lang.String accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Column(name ="REMIND",nullable=true,length=1)
	public java.lang.String getRemind() {
		return remind;
	}

	public void setRemind(java.lang.String remind) {
		this.remind = remind;
	}

	@Column(name ="REMARK",nullable=true,length=50)
	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	@Transient
	public java.math.BigDecimal getMonthCharge() {
		return monthCharge;
	}

	public void setMonthCharge(java.math.BigDecimal monthCharge) {
		this.monthCharge = monthCharge;
	}
	@Transient
	public java.math.BigDecimal getYearCharge() {
		return yearCharge;
	}

	public void setYearCharge(java.math.BigDecimal yearCharge) {
		this.yearCharge = yearCharge;
	}
}
