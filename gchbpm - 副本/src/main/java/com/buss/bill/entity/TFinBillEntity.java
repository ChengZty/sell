package com.buss.bill.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 财务结算表
 * @author onlineGenerator
 * @date 2016-04-22 14:19:44
 * @version V1.0   
 *顾客实收已含运费
 *顾客出运费记整数，退顾客运费记负数
 */
@Entity
@Table(name = "t_fin_bill", schema = "")
@SuppressWarnings("serial")
public class TFinBillEntity implements java.io.Serializable {
	/**业务类型*/
	public static final String BUSINESS_TYPE_1 = "1";//确认收货(后改为购物付款后)
	public static final String BUSINESS_TYPE_2 = "2";//同意退货(退款)
	public static final String BUSINESS_TYPE_3 = "3";//G+卡充值
	public static final String BUSINESS_TYPE_4 = "4";//退G+卡
	/**类型*/
	public static final String TB_STATUS_1 = "1";//待结算
	public static final String TB_STATUS_2 = "2";//已结算
	/**账单编号（年月）*/
	@Excel(name="账单编号",width=10)
	private java.lang.String billNo;
	/**主键*/
	private java.lang.String id;
	/**流水时间*/
	@Excel(name="流水时间",format="yyyyMMddHHmmss",width=20)
	private java.util.Date addTime;
	/**订单号/款号单号*/
	@Excel(name="订单号/款号单号",width=20)
	private java.lang.String orderNo;
	/**订单号*/
//	@Excel(name="子订单号",width=20)
	private java.lang.String subOrderNo;
	/**业务日期*/
	@Excel(name="下单日期",format = "yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date businessDate;
	/**业务类型*/
	@Excel(name="业务类型",replace={"购物付款_1","退款_2","G+卡充值_3","退G+卡_4"})
	private java.lang.String businessType;
	/**订单金额*/
	@Excel(name="订单金额")
	private java.math.BigDecimal orderAmount;
	/**实付金额*/
	@Excel(name="实付金额")
	private java.math.BigDecimal payAmount;
	/**优惠券*/
	@Excel(name="优惠券")
	private java.math.BigDecimal ticketAmount;
	/**导购优惠*/
	@Excel(name="导购优惠")
	private java.math.BigDecimal guidePrivilegeAmount;
	/**顾客实收*/
	@Excel(name="顾客实收")
	private java.math.BigDecimal custAmount;
	/**订单件数*/
	@Excel(name="件数")
	private java.math.BigDecimal quantityAmount;
	/**运费*/
//	@Excel(name="运费")
	private java.math.BigDecimal fareAmount;
	/**零售商实收*/
//	@Excel(name="零售商实收")
	private java.math.BigDecimal storeAmount;
	/**云商实收*/
//	@Excel(name="云商实收")
	private java.math.BigDecimal cloudAmount;
	/**导购实收*/
//	@Excel(name="导购实收")
	private java.math.BigDecimal guideAmount;
	/**活动奖励*/
//	@Excel(name="活动奖励")
	private java.math.BigDecimal activityAmount;
	/**系统实收*/
//	@Excel(name="系统实收")
	private java.math.BigDecimal systemAmount;
	/**手续费*/
//	@Excel(name="手续费")
	private java.math.BigDecimal serviceCharge;
	/**帮手实收*/
//	@Excel(name="帮手实收")
	private java.math.BigDecimal helperAmount;
	/**顾客ID*/
	private java.lang.String custId;
	/**顾客姓名*/
	@Excel(name="顾客")
	private java.lang.String custName;
	/**导购ID*/
	private java.lang.String guideId;
	/**导购姓名*/
	@Excel(name="导购")
	private java.lang.String guideName;
	/**导购所属零售商ID*/
	private java.lang.String storeId;
	/**导购所属零售商姓名*/
	@Excel(name="店铺")
	private java.lang.String storeName;
	/**导购所属零售商ID*/
	private java.lang.String retailerId;
	/**导购所属零售商姓名*/
	@Excel(name="零售商")
	private java.lang.String retailerName;
	/**状态*/
	private java.lang.String tbStatus;
	
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  流水时间
	 */
	@Column(name ="ADD_TIME",nullable=true)
	public Date getAddTime(){
		return this.addTime;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  流水时间
	 */
	public void setAddTime(Date addTime){
		this.addTime = addTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="ORDER_NO",nullable=true,length=32)
	public java.lang.String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	@Column(name ="SUB_ORDER_NO",nullable=true,length=32)
	public java.lang.String getSubOrderNo() {
		return subOrderNo;
	}

	public void setSubOrderNo(java.lang.String subOrderNo) {
		this.subOrderNo = subOrderNo;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  业务日期
	 */
	@Column(name ="BUSINESS_DATE",nullable=true)
	public java.util.Date getBusinessDate(){
		return this.businessDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  业务日期
	 */
	public void setBusinessDate(java.util.Date businessDate){
		this.businessDate = businessDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务类型
	 */
	@Column(name ="BUSINESS_TYPE",nullable=true)
	public java.lang.String getBusinessType(){
		return this.businessType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型
	 */
	public void setBusinessType(java.lang.String businessType){
		this.businessType = businessType;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  订单金额
	 */
	@Column(name ="ORDER_AMOUNT",nullable=true)
	public java.math.BigDecimal getOrderAmount(){
		return this.orderAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  订单金额
	 */
	public void setOrderAmount(java.math.BigDecimal orderAmount){
		this.orderAmount = orderAmount;
	}
	@Column(name ="PAY_AMOUNT")
	public java.math.BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(java.math.BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@Column(name ="quantity_Amount",nullable=true)
	public java.math.BigDecimal getQuantityAmount() {
		return quantityAmount;
	}

	public void setQuantityAmount(java.math.BigDecimal quantityAmount) {
		this.quantityAmount = quantityAmount;
	}

	@Column(name ="fare_Amount",nullable=true)
	public java.math.BigDecimal getFareAmount() {
		return fareAmount;
	}

	public void setFareAmount(java.math.BigDecimal fareAmount) {
		this.fareAmount = fareAmount;
	}
	@Column(name ="ticket_Amount")
	public java.math.BigDecimal getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(java.math.BigDecimal ticketAmount) {
		this.ticketAmount = ticketAmount;
	}
	@Column(name ="guide_privilege_Amount")
	public java.math.BigDecimal getGuidePrivilegeAmount() {
		return guidePrivilegeAmount;
	}

	public void setGuidePrivilegeAmount(java.math.BigDecimal guidePrivilegeAmount) {
		this.guidePrivilegeAmount = guidePrivilegeAmount;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  顾客实收
	 */
	@Column(name ="CUST_AMOUNT",nullable=true)
	public java.math.BigDecimal getCustAmount(){
		return this.custAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  顾客实收
	 */
	public void setCustAmount(java.math.BigDecimal custAmount){
		this.custAmount = custAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  零售商实收
	 */
	@Column(name ="STORE_AMOUNT",nullable=true)
	public java.math.BigDecimal getStoreAmount(){
		return this.storeAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  零售商实收
	 */
	public void setStoreAmount(java.math.BigDecimal storeAmount){
		this.storeAmount = storeAmount;
	}
	@Column(name ="CLOUD_AMOUNT",nullable=true)
	public java.math.BigDecimal getCloudAmount() {
		return cloudAmount;
	}

	public void setCloudAmount(java.math.BigDecimal cloudAmount) {
		this.cloudAmount = cloudAmount;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  导购实收
	 */
	@Column(name ="GUIDE_AMOUNT",nullable=true)
	public java.math.BigDecimal getGuideAmount(){
		return this.guideAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  导购实收
	 */
	public void setGuideAmount(java.math.BigDecimal guideAmount){
		this.guideAmount = guideAmount;
	}
	@Column(name ="ACTIVITY_AMOUNT",nullable=true)
	public java.math.BigDecimal getActivityAmount() {
		return activityAmount;
	}

	public void setActivityAmount(java.math.BigDecimal activityAmount) {
		this.activityAmount = activityAmount;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  系统实收
	 */
	@Column(name ="SYSTEM_AMOUNT",nullable=true)
	public java.math.BigDecimal getSystemAmount(){
		return this.systemAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  系统实收
	 */
	public void setSystemAmount(java.math.BigDecimal systemAmount){
		this.systemAmount = systemAmount;
	}
	@Column(name ="service_Charge",nullable=true)
	public java.math.BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(java.math.BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  帮手实收
	 */
	@Column(name ="HELPER_AMOUNT",nullable=true)
	public java.math.BigDecimal getHelperAmount(){
		return this.helperAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  帮手实收
	 */
	public void setHelperAmount(java.math.BigDecimal helperAmount){
		this.helperAmount = helperAmount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  顾客ID
	 */
	@Column(name ="CUST_ID",nullable=true,length=36)
	public java.lang.String getCustId(){
		return this.custId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  顾客ID
	 */
	public void setCustId(java.lang.String custId){
		this.custId = custId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  顾客姓名
	 */
	@Column(name ="CUST_NAME",nullable=true,length=40)
	public java.lang.String getCustName(){
		return this.custName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  顾客姓名
	 */
	public void setCustName(java.lang.String custName){
		this.custName = custName;
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
	 *@return: java.lang.String  导购姓名
	 */
	@Column(name ="GUIDE_NAME",nullable=true,length=40)
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
	 *@return: java.lang.String  导购所属零售商ID
	 */
	@Column(name ="STORE_ID",nullable=true,length=36)
	public java.lang.String getStoreId(){
		return this.storeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购所属零售商ID
	 */
	public void setStoreId(java.lang.String storeId){
		this.storeId = storeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购所属零售商姓名
	 */
	@Column(name ="STORE_NAME",nullable=true,length=40)
	public java.lang.String getStoreName(){
		return this.storeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购所属零售商姓名
	 */
	public void setStoreName(java.lang.String storeName){
		this.storeName = storeName;
	}
	@Column(name ="RETAILER_ID")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Column(name ="RETAILER_NAME")
	public java.lang.String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(java.lang.String retailerName) {
		this.retailerName = retailerName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="TB_STATUS",nullable=true,length=1)
	public java.lang.String getTbStatus(){
		return this.tbStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setTbStatus(java.lang.String tbStatus){
		this.tbStatus = tbStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账单编号
	 */
	@Column(name ="BILL_NO",nullable=true,length=32)
	public java.lang.String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账单编号
	 */
	public void setBillNo(java.lang.String billNo){
		this.billNo = billNo;
	}
}
