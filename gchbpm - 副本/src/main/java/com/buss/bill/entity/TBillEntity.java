package com.buss.bill.entity;

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
 * @Description: 账单
 * @author onlineGenerator
 * @date 2016-09-17 20:30:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_bill", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TBillEntity implements java.io.Serializable {
	public static final String BILL_TYPE_1 = "1";//充G+卡值
	public static final String BILL_TYPE_2 = "2";//G+卡购物
	public static final String BILL_TYPE_3 = "3";//平台充值
	public static final String BILL_TYPE_4 = "4";//其他支付
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
	@Excel(name="状态")
	private java.lang.String status;
	/**流水号*/
	@Excel(name="流水号")
	private java.lang.String billNo;
	/**帐号ID*/
	@Excel(name="帐号ID")
	private java.lang.String userId;
	/**手机号*/
	@Excel(name="手机号")
	private java.lang.String phoneNo;
	/**对方手机号*/
	@Excel(name="对方手机号")
	private java.lang.String otherPhoneNo;
	/**所属导购*/
	private java.lang.String toGuideId;
	/**零售商ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**交易日期*/
	@Excel(name="交易日期")
	private java.util.Date businessDate;
	/**交易金额*/
	@Excel(name="交易金额")
	private java.math.BigDecimal billAmount;
	/**交易类型        */
	@Excel(name="交易类型")
	private java.lang.String billType;
	/**订单ID/充值卡ID）*/
	private java.lang.String orderId;
	/**预订单号*/
	@Excel(name="订单号")
	private java.lang.String orderNo;//充值时存放预订单号或者购物的订单号
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
	 *@return: java.lang.String  流水号
	 */
	@Column(name ="BILL_NO",nullable=true,length=32)
	public java.lang.String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流水号
	 */
	public void setBillNo(java.lang.String billNo){
		this.billNo = billNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  帐号ID
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  帐号ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号
	 */
	@Column(name ="PHONE_NO",nullable=true,length=11)
	public java.lang.String getPhoneNo(){
		return this.phoneNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号
	 */
	public void setPhoneNo(java.lang.String phoneNo){
		this.phoneNo = phoneNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  对方手机号
	 */
	@Column(name ="OTHER_PHONE_NO",nullable=true,length=11)
	public java.lang.String getOtherPhoneNo(){
		return this.otherPhoneNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  对方手机号
	 */
	public void setOtherPhoneNo(java.lang.String otherPhoneNo){
		this.otherPhoneNo = otherPhoneNo;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  交易日期
	 */
	@Column(name ="BUSINESS_DATE",nullable=true,length=20)
	public java.util.Date getBusinessDate(){
		return this.businessDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  交易日期
	 */
	public void setBusinessDate(java.util.Date businessDate){
		this.businessDate = businessDate;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  交易金额
	 */
	@Column(name ="BILL_AMOUNT",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getBillAmount(){
		return this.billAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  交易金额
	 */
	public void setBillAmount(java.math.BigDecimal billAmount){
		this.billAmount = billAmount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  交易类型
	 */
	@Column(name ="BILL_TYPE",nullable=true,length=1)
	public java.lang.String getBillType(){
		return this.billType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  交易类型
	 */
	public void setBillType(java.lang.String billType){
		this.billType = billType;
	}
	@Column(name ="ORDER_ID",nullable=true,length=36)
	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}
	@Column(name ="ORDER_NO",nullable=true)
	public java.lang.String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name ="TO_GUIDE_ID",nullable=true,length=36)
	public java.lang.String getToGuideId() {
		return toGuideId;
	}

	public void setToGuideId(java.lang.String toGuideId) {
		this.toGuideId = toGuideId;
	}
	
}
