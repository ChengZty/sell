package com.buss.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 月端口扣费主表
 * @author onlineGenerator
 * @date 2017-07-27 15:52:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_app_cost_info", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TAppCostInfoEntity implements java.io.Serializable {
	public static final String CHARGE_STATUS_0 = "0";//待扣费
	public static final String CHARGE_STATUS_1 = "1";//扣费成功
	public static final String CHARGE_STATUS_2 = "2";//扣费失败
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
	/**零售商id*/
	@Excel(name="零售商id")
	private java.lang.String retailerId;
	/**年月*/
	@Excel(name="年月")
	private java.lang.String yearMonthStr;
	/**扣费状态*/
	@Excel(name="扣费状态")
	private java.lang.String chargeStatus;
	/**账户年费（待扣）*/
	@Excel(name="账户年费")
	private java.math.BigDecimal accountYearCost;
	/**月端口费（待扣）*/
	@Excel(name="月端口费")
	private java.math.BigDecimal appMonthCost;
	/**实际月端口费*/
	private java.math.BigDecimal realAppMonthCost;
	/**月端口数（待扣）*/
	@Excel(name="月端口数")
	private java.lang.Integer appMonthCount;
	/**激活时间*/
	@Excel(name="激活时间")
	private java.util.Date activeTime;
	/**年费扣费失败次数*/
	private java.lang.Integer failTimes;
	
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
	 *@return: java.lang.String  零售商id
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商id
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  年月
	 */
	@Column(name ="YEAR_MONTH_STR",nullable=true,length=7)
	public java.lang.String getYearMonthStr(){
		return this.yearMonthStr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年月
	 */
	public void setYearMonthStr(java.lang.String yearMonthStr){
		this.yearMonthStr = yearMonthStr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  扣费状态
	 */
	@Column(name ="COST_STATUS",nullable=true,length=1)
	public java.lang.String getChargeStatus(){
		return this.chargeStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  扣费状态
	 */
	public void setChargeStatus(java.lang.String chargeStatus){
		this.chargeStatus = chargeStatus;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  账户年费
	 */
	@Column(name ="ACCOUNT_YEAR_COST",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getAccountYearCost(){
		return this.accountYearCost;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  账户年费
	 */
	public void setAccountYearCost(java.math.BigDecimal accountYearCost){
		this.accountYearCost = accountYearCost;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  月端口费
	 */
	@Column(name ="APP_MONTH_COST",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getAppMonthCost(){
		return this.appMonthCost;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  月端口费
	 */
	public void setAppMonthCost(java.math.BigDecimal appMonthCost){
		this.appMonthCost = appMonthCost;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  月端口数
	 */
	@Column(name ="APP_MONTH_COUNT",nullable=true,length=8)
	public java.lang.Integer getAppMonthCount(){
		return this.appMonthCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  月端口数
	 */
	public void setAppMonthCount(java.lang.Integer appMonthCount){
		this.appMonthCount = appMonthCount;
	}
	@Column(name ="ACTIVE_TIME")
	public java.util.Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(java.util.Date activeTime) {
		this.activeTime = activeTime;
	}
	@Column(name ="FAIL_TIMES")
	public java.lang.Integer getFailTimes() {
		return failTimes;
	}

	public void setFailTimes(java.lang.Integer failTimes) {
		this.failTimes = failTimes;
	}
	@Column(name ="real_app_month_cost")
	public java.math.BigDecimal getRealAppMonthCost() {
		return realAppMonthCost;
	}

	public void setRealAppMonthCost(java.math.BigDecimal realAppMonthCost) {
		this.realAppMonthCost = realAppMonthCost;
	}
	
}
