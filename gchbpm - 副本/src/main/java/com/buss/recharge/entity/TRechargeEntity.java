package com.buss.recharge.entity;

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
 * @Description: 充值
 * @author onlineGenerator
 * @date 2016-09-17 20:35:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_recharge", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TRechargeEntity implements java.io.Serializable {
	public static final String PAY_TYPE_1 = "1";//支付宝
	public static final String PAY_TYPE_2 = "2";//微信
	public static final String PAY_TYPE_3 = "3";//平台
	public static final String PAY_STATUS_1 = "1";//待付款
	public static final String PAY_STATUS_2 = "2";//付款成功
	public static final String PAY_STATUS_3 = "3";//付款失败
	/**主键*/
	@Excel(name="订单ID",width=20)
	private java.lang.String id;
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
	/**充值单号*/
	@Excel(name="充值单号",width=20)
	private java.lang.String orderNo;
	/**帐号ID*/
	@Excel(name="用户ID",width=20)
	private java.lang.String userId;
	/**帐号ID*/
	@Excel(name="导购ID",width=20)
	private java.lang.String toGuideId;
	/**零售商ID*/
	@Excel(name="零售商ID",width=20)
	private java.lang.String retailerId;
	/**手机号*/
	@Excel(name="手机号",width=20)
	private java.lang.String phoneNo;
	/**充值金额*/
	@Excel(name="充值金额")
	private java.math.BigDecimal money;
	/**支付金额*/
	@Excel(name="支付金额")
	private java.math.BigDecimal payMoney;
	/**下单时间*/
	@Excel(name="下单时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date addTime;
	/**充值时间*/
	@Excel(name="充值时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date payTime;
	/**创建人名称*/
	@Excel(name="充值人",width=20)
	private java.lang.String createName;
	/**支付方式            1.支付宝 2.微信 3.平台             银联（暂不考虑）*/
	@Excel(name="支付方式",width=15,replace={"支付宝_1","微信_2","平台_2"})
	private java.lang.String payType;
	/**折扣*/
	@Excel(name="折扣")
	private java.math.BigDecimal discount;
	/**充值卡ID*/
	private java.lang.String cardId;
	/**支付状态            1.待付款 2.支付成功 3.支付失败*/
	@Excel(name="支付状态",width=15,replace={"待付款_1","支付成功_2","支付失败_2"})
	private java.lang.String payStatus;
	/**备注*/
	@Excel(name="备注",width=35)
	private java.lang.String remark;
	/**支付手续费*/
	private java.math.BigDecimal serviceCharge;
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
	@Column(name ="TO_GUIDE_ID",nullable=true,length=36)
	public java.lang.String getToGuideId() {
		return toGuideId;
	}

	public void setToGuideId(java.lang.String toGuideId) {
		this.toGuideId = toGuideId;
	}

	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额
	 */
	@Column(name ="MONEY",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getMoney(){
		return this.money;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额
	 */
	public void setMoney(java.math.BigDecimal money){
		this.money = money;
	}
	@Column(name ="PAY_MONEY",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(java.math.BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  充值日期
	 */
	@Column(name ="PAY_TIME",nullable=true,length=20)
	public java.util.Date getPayTime(){
		return this.payTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  充值日期
	 */
	public void setPayTime(java.util.Date payTime){
		this.payTime = payTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付方式
	 */
	@Column(name ="PAY_TYPE",nullable=true,length=1)
	public java.lang.String getPayType(){
		return this.payType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付方式
	 */
	public void setPayType(java.lang.String payType){
		this.payType = payType;
	}
	@Column(name ="DISCOUNT",nullable=true,scale=3,length=5)
	public java.math.BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(java.math.BigDecimal discount) {
		this.discount = discount;
	}
	@Column(name ="CARD_ID",nullable=true,length=36)
	public java.lang.String getCardId() {
		return cardId;
	}

	public void setCardId(java.lang.String cardId) {
		this.cardId = cardId;
	}
	@Column(name ="ORDER_NO",nullable=true,length=36)
	public java.lang.String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name ="ADD_TIME",nullable=true,length=20)
	public java.util.Date getAddTime() {
		return addTime;
	}

	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	@Column(name ="PAY_STATUS",nullable=true,length=1)
	public java.lang.String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}
	@Column(name ="remark",nullable=true,length=300)
	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	@Column(name ="service_charge",nullable=true)
	public java.math.BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(java.math.BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
}
