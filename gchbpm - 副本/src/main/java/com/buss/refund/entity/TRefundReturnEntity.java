package com.buss.refund.entity;
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
 * @Description: 退款退货
 * @author onlineGenerator
 * @date 2016-04-07 17:44:41
 * @version V1.0   
 *退款金额已判断是否需要退运费，交易金额暂时为订单明细里运费+现金支付+G+卡支付金额
 *退款金额和退G+卡金额已经包含运费
 */
@Entity
@Table(name = "t_refund_return", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TRefundReturnEntity implements java.io.Serializable {
	//导购处理状态
	public static String SELLER_STATUS_1 = "1";//待审核
	public static String SELLER_STATUS_2 = "2";//同意
	public static String SELLER_STATUS_3 = "3";//不同意

	//退款处理状态
	public static String REFUND_STATUS_1 = "1";//处理中(该状态会没有了，直接就是从导购申请退款就是2)
	public static String REFUND_STATUS_2 = "2";//待商家处理(导购同意后该状态从1改为2)
//	public static String REFUND_STATUS_3 = "3";//商家同意(该状态已去掉，商家同意了相当于退款完成)
	public static String REFUND_STATUS_4 = "4";//商家拒绝
	public static String REFUND_STATUS_5 = "5";//退款完成
	//退款来源
	public static String REFUND_SOURCE_1 = "1";//1：已发货后退款（不退运费）
	public static String REFUND_SOURCE_2 = "2";//2：待发货的时候退款（要退运费）
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
	/**订单ID*/
	@Excel(name="商户订单号")
	private java.lang.String orderId;
	/**主订单号*/
	@Excel(name="订单号")
	private java.lang.String orderNo;
	/**订单号*/
	private java.lang.String subOrderNo;
	   /**结算批次（用于判断主单是否已结算过）*/
    private java.lang.String billBatch;
	/**退款号*/
	@Excel(name="退款号")
	private java.lang.String refundNo;
	/**属零售商ID*/
	private java.lang.String retailerId;
	/**货品所属零售商名称*/
	private java.lang.String retailerType;
	/**货品所属零售商ID*/
	private java.lang.String toStoreGoodsId;
	/**货品所属零售商类型*/
	private java.lang.String storeType;
	/**货品所属零售商名称*/
	@Excel(name="零售商名称")
	private java.lang.String retailerName;
	/**零售商手机*/
	private java.lang.String retailerPhone;
	/**用户ID*/
	private java.lang.String userId;
	/**用户姓名*/
	@Excel(name="用户姓名")
	private java.lang.String userName;
	/**用户手机*/
	@Excel(name="用户手机")
	private java.lang.String userPhone;
	/**导购姓名*/
	@Excel(name="导购姓名")
	private java.lang.String guideName;
	/**导购手机*/
	@Excel(name="导购手机")
	private java.lang.String guidePhone;
	/**规格库存ID*/
	private java.lang.String specGoodsId;
	/**货品ID*/
	private java.lang.String goodsId;
	/**商品名称*/
//	@Excel(name="商品名称")
	private java.lang.String goodsName;
	/**商品图片*/
	private java.lang.String goodsPic;
	/**商品数量*/
//	@Excel(name="商品数量")
	private java.math.BigDecimal goodsNum;
	/**交易金额*/
	@Excel(name="交易金额")
	private java.math.BigDecimal orderAmount;
	/**付款帐号  订单主表字段 20170824添加*/
	@Excel(name="付款帐号")
	private java.lang.String payAccount;
	/**需退的运费*/
//	@Excel(name="运费")
	private java.math.BigDecimal fare;
	/**退款金额*/
	@Excel(name="申请退款金额")
	private java.math.BigDecimal refundAmount;
	/**实际退款金额*/
	@Excel(name="实际退款金额")
	private java.math.BigDecimal refundAmountReal;
	/**退G+卡金额（含运费）*/
//	@Excel(name="退G+卡金额")
	private java.math.BigDecimal cardAmount;
	/**导购处理状态*/
//	@Excel(name="导购处理状态")
	private java.lang.String sellerStatus;
	/**零售商退款状态*/
	@Excel(name="退款状态")
	private java.lang.String refundStatus;
	/**添加时间*/
	private java.util.Date addTime;
	/**卖家处理时间(零售商处理时间)*/
	@Excel(name="卖家处理时间",format = "yyyy-MM-dd hh:ss:mm")
	private java.util.Date sellerTime;
	/**(线下退款完成的时间)*/
	private java.util.Date adminTime;
	/**原因ID*/
	private java.lang.String reasonId;
	/**原因内容*/
	private java.lang.String reasonInfo;
	/**图片*/
	private java.lang.String picInfo;
	/**买家备注*/
	private java.lang.String buyerRemark;
	/**拒绝退款备注*/
	private java.lang.String sellerRemark;
	/**退款备注*/
	private java.lang.String adminRemark;
	/**快递单号*/
//	@Excel(name="快递单号")
	private java.lang.String deliveryNo;
	/**延迟天数*/
	private java.lang.Integer delayDays;
	/**导购处理人*/
	private java.lang.String toGuideId;
	/**导购处理时间*/
	private java.util.Date guideTime;
	/**收货备注*/
	private java.lang.String receiveRemark;
	/**退款方式*/
	private java.lang.String payMethodName;
	/**交易号*/
	private java.lang.String payNo;
	/**退款来源 默认为1：发货后退款，不退运费，2：待发货的时候退款，退运费*/
	private java.lang.String refundSource;
	/**代金券id*/
	private java.lang.String ticketId;  
	/**代金券明细id*/
	private java.lang.String ticketDetailId; 
	/**代金券实际优惠额度*/
	private java.math.BigDecimal ticketPreferential;
	/**店铺名称*/
	private java.lang.String storeName; 
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
	 *@return: java.lang.String  订单ID
	 */
	@Column(name ="ORDER_ID",nullable=true,length=36)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单ID
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主订单号
	 */
	@Column(name ="ORDER_NO",nullable=true,length=40)
	public java.lang.String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主订单号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单号
	 */
	@Column(name ="SUB_ORDER_NO",nullable=true,length=40)
	public java.lang.String getSubOrderNo(){
		return this.subOrderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单号
	 */
	public void setSubOrderNo(java.lang.String subOrderNo){
		this.subOrderNo = subOrderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  退款号
	 */
	@Column(name ="REFUND_NO",nullable=true,length=40)
	public java.lang.String getRefundNo(){
		return this.refundNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  退款号
	 */
	public void setRefundNo(java.lang.String refundNo){
		this.refundNo = refundNo;
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
	
	@Column(name ="RETAILER_NAME",nullable=true,length=50)
	public java.lang.String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(java.lang.String retailerName) {
		this.retailerName = retailerName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商手机
	 */
	@Column(name ="RETAILER_PHONE",nullable=true,length=20)
	public java.lang.String getRetailerPhone(){
		return this.retailerPhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商手机
	 */
	public void setRetailerPhone(java.lang.String retailerPhone){
		this.retailerPhone = retailerPhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户ID
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户姓名
	 */
	@Column(name ="USER_NAME",nullable=true,length=32)
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户姓名
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户手机
	 */
	@Column(name ="USER_PHONE",nullable=true,length=20)
	public java.lang.String getUserPhone(){
		return this.userPhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户手机
	 */
	public void setUserPhone(java.lang.String userPhone){
		this.userPhone = userPhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格库存ID
	 */
	@Column(name ="SPEC_GOODS_ID",nullable=true,length=36)
	public java.lang.String getSpecGoodsId(){
		return this.specGoodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格库存ID
	 */
	public void setSpecGoodsId(java.lang.String specGoodsId){
		this.specGoodsId = specGoodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=100)
	public java.lang.String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsName(java.lang.String goodsName){
		this.goodsName = goodsName;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  商品数量
	 */
	@Column(name ="GOODS_NUM",nullable=true,length=5)
	public java.math.BigDecimal getGoodsNum(){
		return this.goodsNum;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  商品数量
	 */
	public void setGoodsNum(java.math.BigDecimal goodsNum){
		this.goodsNum = goodsNum;
	}
	
	@Column(name ="order_amount",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(java.math.BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	@Column(name ="fare",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getFare() {
		return this.fare;
	}

	public void setFare(java.math.BigDecimal fare) {
		this.fare = fare;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  退款金额
	 */
	@Column(name ="REFUND_AMOUNT",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getRefundAmount(){
		return this.refundAmount;
//		return this.refundAmount==null?null:refundAmount.setScale(0,java.math.BigDecimal.ROUND_HALF_UP);
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  退款金额
	 */
	public void setRefundAmount(java.math.BigDecimal refundAmount){
		this.refundAmount = refundAmount;
	}
	@Column(name ="REFUND_AMOUNT_REAL",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getRefundAmountReal() {
		return refundAmountReal;
	}

	public void setRefundAmountReal(java.math.BigDecimal refundAmountReal) {
		this.refundAmountReal = refundAmountReal;
	}

	@Column(name ="CARD_AMOUNT",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(java.math.BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  卖家处理状态
	 */
	@Column(name ="SELLER_STATUS",nullable=true,length=1)
	public java.lang.String getSellerStatus(){
		return this.sellerStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  卖家处理状态
	 */
	public void setSellerStatus(java.lang.String sellerStatus){
		this.sellerStatus = sellerStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  退款状态
	 */
	@Column(name ="REFUND_STATUS",nullable=true,length=1)
	public java.lang.String getRefundStatus(){
		return this.refundStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  退款状态
	 */
	public void setRefundStatus(java.lang.String refundStatus){
		this.refundStatus = refundStatus;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  添加时间
	 */
	@Column(name ="ADD_TIME",nullable=true,length=20)
	public java.util.Date getAddTime(){
		return this.addTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  添加时间
	 */
	public void setAddTime(java.util.Date addTime){
		this.addTime = addTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  卖家处理时间
	 */
	@Column(name ="SELLER_TIME",nullable=true,length=20)
	public java.util.Date getSellerTime(){
		return this.sellerTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  卖家处理时间
	 */
	public void setSellerTime(java.util.Date sellerTime){
		this.sellerTime = sellerTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  管理员处理时间
	 */
	@Column(name ="ADMIN_TIME",nullable=true,length=20)
	public java.util.Date getAdminTime(){
		return this.adminTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  管理员处理时间
	 */
	public void setAdminTime(java.util.Date adminTime){
		this.adminTime = adminTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原因ID
	 */
	@Column(name ="REASON_ID",nullable=true,length=36)
	public java.lang.String getReasonId(){
		return this.reasonId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原因ID
	 */
	public void setReasonId(java.lang.String reasonId){
		this.reasonId = reasonId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原因内容
	 */
	@Column(name ="REASON_INFO",nullable=true,length=100)
	public java.lang.String getReasonInfo(){
		return this.reasonInfo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原因内容
	 */
	public void setReasonInfo(java.lang.String reasonInfo){
		this.reasonInfo = reasonInfo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片
	 */
	@Column(name ="PIC_INFO",nullable=true,length=150)
	public java.lang.String getPicInfo(){
		return this.picInfo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片
	 */
	public void setPicInfo(java.lang.String picInfo){
		this.picInfo = picInfo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  买家备注
	 */
	@Column(name ="BUYER_REMARK",nullable=true,length=300)
	public java.lang.String getBuyerRemark(){
		return this.buyerRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  买家备注
	 */
	public void setBuyerRemark(java.lang.String buyerRemark){
		this.buyerRemark = buyerRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  卖家备注
	 */
	@Column(name ="SELLER_REMARK",nullable=true,length=300)
	public java.lang.String getSellerRemark(){
		return this.sellerRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  卖家备注
	 */
	public void setSellerRemark(java.lang.String sellerRemark){
		this.sellerRemark = sellerRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  管理员备注
	 */
	@Column(name ="ADMIN_REMARK",nullable=true,length=300)
	public java.lang.String getAdminRemark(){
		return this.adminRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  管理员备注
	 */
	public void setAdminRemark(java.lang.String adminRemark){
		this.adminRemark = adminRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  快递单号
	 */
	@Column(name ="DELIVERY_NO",nullable=true,length=32)
	public java.lang.String getDeliveryNo(){
		return this.deliveryNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  快递单号
	 */
	public void setDeliveryNo(java.lang.String deliveryNo){
		this.deliveryNo = deliveryNo;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  延迟天数
	 */
	@Column(name ="DELAY_DAYS",nullable=true,length=2)
	public java.lang.Integer getDelayDays(){
		return this.delayDays;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  延迟天数
	 */
	public void setDelayDays(java.lang.Integer delayDays){
		this.delayDays = delayDays;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货备注
	 */
	@Column(name ="RECEIVE_REMARK",nullable=true,length=300)
	public java.lang.String getReceiveRemark(){
		return this.receiveRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货备注
	 */
	public void setReceiveRemark(java.lang.String receiveRemark){
		this.receiveRemark = receiveRemark;
	}
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(java.lang.String goodsId) {
		this.goodsId = goodsId;
	}
	@Column(name ="GOODS_PIC",nullable=true,length=150)
	public java.lang.String getGoodsPic() {
		return goodsPic;
	}

	public void setGoodsPic(java.lang.String goodsPic) {
		this.goodsPic = goodsPic;
	}
	@Column(name ="TO_GUIDE_ID",nullable=true,length=36)
	public java.lang.String getToGuideId() {
		return toGuideId;
	}

	public void setToGuideId(java.lang.String toGuideId) {
		this.toGuideId = toGuideId;
	}
	@Column(name ="GUIDE_TIME",nullable=true,length=20)
	public java.util.Date getGuideTime() {
		return guideTime;
	}

	public void setGuideTime(java.util.Date guideTime) {
		this.guideTime = guideTime;
	}
	@Column(name ="bill_Batch",nullable=true)
	public java.lang.String getBillBatch() {
		return billBatch;
	}

	public void setBillBatch(java.lang.String billBatch) {
		this.billBatch = billBatch;
	}
	
	@Column(name ="pay_Method_Name")
	public java.lang.String getPayMethodName() {
		return payMethodName;
	}

	public void setPayMethodName(java.lang.String payMethodName) {
		this.payMethodName = payMethodName;
	}
	@Column(name ="pay_No")
	public java.lang.String getPayNo() {
		return payNo;
	}

	public void setPayNo(java.lang.String payNo) {
		this.payNo = payNo;
	}
	@Column(name ="refund_Source")
	public java.lang.String getRefundSource() {
		return refundSource;
	}

	public void setRefundSource(java.lang.String refundSource) {
		this.refundSource = refundSource;
	}
	@Column(name ="TICKET_ID",nullable=true)
	public java.lang.String getTicketId() {
		return ticketId;
	}
	public void setTicketId(java.lang.String ticketId) {
		this.ticketId = ticketId;
	}
	@Column(name ="TICKET_DETAIL_ID",nullable=true)
	public java.lang.String getTicketDetailId() {
		return ticketDetailId;
	}
	public void setTicketDetailId(java.lang.String ticketDetailId) {
		this.ticketDetailId = ticketDetailId;
	}
	@Column(name ="TICKET_PREFERENTIAL",nullable=true)
	public java.math.BigDecimal getTicketPreferential() {
		return ticketPreferential;
	}
	public void setTicketPreferential(java.math.BigDecimal ticketPreferential) {
		this.ticketPreferential = ticketPreferential;
	}
	@Column(name ="retailer_Type",nullable=true)
	public java.lang.String getRetailerType() {
		return retailerType;
	}

	public void setRetailerType(java.lang.String retailerType) {
		this.retailerType = retailerType;
	}
	@Column(name ="to_Store_Goods_Id",nullable=true)
	public java.lang.String getToStoreGoodsId() {
		return toStoreGoodsId;
	}

	public void setToStoreGoodsId(java.lang.String toStoreGoodsId) {
		this.toStoreGoodsId = toStoreGoodsId;
	}
	@Column(name ="store_Type",nullable=true)
	public java.lang.String getStoreType() {
		return storeType;
	}

	public void setStoreType(java.lang.String storeType) {
		this.storeType = storeType;
	}
	@Transient
	public java.lang.String getGuideName() {
		return guideName;
	}

	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	@Transient
	public java.lang.String getGuidePhone() {
		return guidePhone;
	}

	public void setGuidePhone(java.lang.String guidePhone) {
		this.guidePhone = guidePhone;
	}
	@Transient
	public java.lang.String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(java.lang.String payAccount) {
		this.payAccount = payAccount;
	}
	@Transient
	public java.lang.String getStoreName() {
		return storeName;
	}

	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	
}
