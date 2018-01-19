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
 * @Description: 端口扣费明细
 * @author onlineGenerator
 * @date 2017-07-27 15:52:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_app_cost_detail", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TAppCostDetailEntity implements java.io.Serializable {
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
	/**主表id*/
	@Excel(name="主表id")
	private java.lang.String appCostInfoId;
	/**账户明细id*/
	@Excel(name="账户明细id")
	private java.lang.String storeAccountDetailId;
	/**零售商id*/
	@Excel(name="零售商id")
	private java.lang.String retailerId;
	/**年月*/
	@Excel(name="年月")
	private java.lang.String yearMonthStr;
	/**店铺id*/
	@Excel(name="店铺id")
	private java.lang.String storeId;
	/**导购id*/
	@Excel(name="导购id")
	private java.lang.String guideId;
	/**导购姓名*/
	@Excel(name="导购姓名")
	private java.lang.String guideName;
	/**手机号*/
	@Excel(name="手机号")
	private java.lang.String phone;
	/**激活时间*/
	@Excel(name="激活时间")
	private java.util.Date activeTime;
	/**扣费期间*/
//	@Excel(name="扣费期间")
	/**扣费期间开始*/
	private java.util.Date chargePeriodBegin;
	/**扣费期间结束*/
	private java.util.Date chargePeriodEnd;
	/**扣费时间*/
	@Excel(name="扣费时间")
	private java.util.Date chargeTime;
	/**扣费金额*/
	@Excel(name="扣费金额")
	private java.math.BigDecimal appCost;
	/**扣费状态*/
	@Excel(name="扣费状态")
	private java.lang.String chargeStatus;
	/**失败次数*/
	@Excel(name="失败次数")
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
	 *@return: java.lang.String  主表id
	 */
	@Column(name ="APP_COST_INFO_ID",nullable=true,length=36)
	public java.lang.String getAppCostInfoId(){
		return this.appCostInfoId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表id
	 */
	public void setAppCostInfoId(java.lang.String appCostInfoId){
		this.appCostInfoId = appCostInfoId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账户明细id
	 */
	@Column(name ="STORE_ACCOUNT_DETAIL_ID",nullable=true,length=36)
	public java.lang.String getStoreAccountDetailId(){
		return this.storeAccountDetailId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账户明细id
	 */
	public void setStoreAccountDetailId(java.lang.String storeAccountDetailId){
		this.storeAccountDetailId = storeAccountDetailId;
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
	 *@return: java.lang.String  店铺id
	 */
	@Column(name ="STORE_ID",nullable=true,length=36)
	public java.lang.String getStoreId(){
		return this.storeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  店铺id
	 */
	public void setStoreId(java.lang.String storeId){
		this.storeId = storeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购id
	 */
	@Column(name ="GUIDE_ID",nullable=true,length=36)
	public java.lang.String getGuideId(){
		return this.guideId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购id
	 */
	public void setGuideId(java.lang.String guideId){
		this.guideId = guideId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购姓名
	 */
	@Column(name ="GUIDE_NAME",nullable=true,length=32)
	public java.lang.String getGuideName(){
		return this.guideName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购姓名
	 */
	public void setGuideName(java.lang.String guideName){
		this.guideName = guideName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号
	 */
	@Column(name ="PHONE",nullable=true,length=12)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  激活时间
	 */
	@Column(name ="ACTIVE_TIME",nullable=true,length=20)
	public java.util.Date getActiveTime(){
		return this.activeTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  激活时间
	 */
	public void setActiveTime(java.util.Date activeTime){
		this.activeTime = activeTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  扣费时间
	 */
	@Column(name ="CHARGE_TIME",nullable=true,length=20)
	public java.util.Date getChargeTime(){
		return this.chargeTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  扣费时间
	 */
	public void setChargeTime(java.util.Date chargeTime){
		this.chargeTime = chargeTime;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  扣费金额
	 */
	@Column(name ="APP_COST",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getAppCost(){
		return this.appCost;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  扣费金额
	 */
	public void setAppCost(java.math.BigDecimal appCost){
		this.appCost = appCost;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  扣费状态
	 */
	@Column(name ="CHARGE_STATUS",nullable=true,length=1)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  失败次数
	 */
	@Column(name ="FAIL_TIMES",nullable=true,length=3)
	public java.lang.Integer getFailTimes(){
		return this.failTimes;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  失败次数
	 */
	public void setFailTimes(java.lang.Integer failTimes){
		this.failTimes = failTimes;
	}
	@Column(name ="charge_period_begin")
	public java.util.Date getChargePeriodBegin() {
		return chargePeriodBegin;
	}

	public void setChargePeriodBegin(java.util.Date chargePeriodBegin) {
		this.chargePeriodBegin = chargePeriodBegin;
	}
	@Column(name ="charge_period_end")
	public java.util.Date getChargePeriodEnd() {
		return chargePeriodEnd;
	}

	public void setChargePeriodEnd(java.util.Date chargePeriodEnd) {
		this.chargePeriodEnd = chargePeriodEnd;
	}
}
