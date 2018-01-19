package com.buss.order.entity;
import java.lang.String;
import java.math.BigDecimal;

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
 * @Description: 订单信息
 * @author onlineGenerator
 * @date 2016-03-15 17:20:38
 * @version V1.0   
 *
 *
//订单主表
总运费 = 明细中运费之和
商品总额 =  明细中商品总额之和
 支付金额 = 订单总额 = 商品总额+总运费
支付手续费 = 支付金额 * 费率
实际结算金额 = 商品总额 (没用)
 *
 */


@Entity
@Table(name = "t_order_info", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TOrderInfoEntity implements java.io.Serializable {
	
	/**支付状态*/
	public static String ORDER_STATUS_1 = "1";//待付款
	public static String ORDER_STATUS_2 = "2";//待发货(已付款)
	public static String ORDER_STATUS_3 = "3";//已发货(待收货)
	public static String ORDER_STATUS_4 = "4";//已完成(已收货)
	public static String ORDER_STATUS_8 = "8";//已关闭,已退款（下单后已经付款，待发货的时候取消）
	public static String ORDER_STATUS_9 = "9";//已取消（下单后没有付款就取消或者24小时没付款后台取消）
	/**支付状态*/
	public static String PAY_STATUS_1 = "1";//未支付
	public static String PAY_STATUS_2 = "2";//已支付
	
	/**推荐预订单状态*/
	public static String RECOMMEND_STATUS_1 = "1";//待确认
	public static String RECOMMEND_STATUS_2 = "2";//已取消
	public static String RECOMMEND_STATUS_3 = "3";//已确认
	public static String RECOMMEND_STATUS_4 = "4";//已关闭
	
	/**发货类型*/
	public static String DELIVERY_TYPE_0 = "0";//顾客自提
	public static String DELIVERY_TYPE_1 = "1";//后台发货
	public static String DELIVERY_TYPE_2 = "2";//店铺发货
	
	/*退款状态*/
	public static String REFUND_STATUS_0 = "0";//否（默认值）
	public static String REFUND_STATUS_1 = "1";//处理中(该状态会没有了，直接就是从导购申请退款就是2)
	public static String REFUND_STATUS_2 = "2";//待商家处理(导购同意后该状态从1改为2)
	public static String REFUND_STATUS_3 = "3";//商家同意
	public static String REFUND_STATUS_4 = "4";//商家拒绝
	public static String REFUND_STATUS_5 = "5";//退款完成
	
	/*是否能整单退款状态*/
	public static String CAN_REFUND_ALL_N = "N";//不能整单退（单件退款）
	public static String CAN_REFUND_ALL_Y = "Y";//可以整单退（没有退过款）
	public static String CAN_REFUND_ALL_A = "A";//整单退款（明细全部退了也会更新为A）
	
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
	/**订单号*/
    @Excel(name="订单号")
	private java.lang.String orderNo;
	/**下单时间*/
    @Excel(name="下单时间")
	private java.util.Date orderTime;
	/**件数*/
    @Excel(name="总数量")
	private java.math.BigDecimal quantityAmount;
	/**帐号ID*/
	private java.lang.String userId;
	/**用户手机号*/
	private java.lang.String userPhone;
	/**用户姓名*/
	private java.lang.String userName;
	/**关闭时间*/
	private java.util.Date closeTime;
	/**支付状态*/
	private java.lang.String payStatus;//1未支付，2已支付
	/**支付方式   微信，支付宝*/
	@Excel(name="支付方式")
	private java.lang.String payMethod;
    /**支付帐号*/
    @Excel(name="支付帐号")
    private java.lang.String payAccount;
	/**支付时间*/
    @Excel(name="支付时间")
	private java.util.Date payTime;
    /**完成时间*/
    private java.util.Date finishedTime;
    /**结算批次（用于判断主单是否已结算过）*/
    private java.lang.String billBatch;
	/**支付单号*/
	private java.lang.String payNo;
    /**订单状态，主单只记录待付款，已付款，已收货（回显），已关闭4个针对整个主订单的状态*/
    private java.lang.String orderStatus;
	/**支付金额*/
    @Excel(name="支付金额")
	private java.math.BigDecimal payAmount;
	/**运费*/
    @Excel(name="运费")
	private java.math.BigDecimal fareAmount;
	/**支付手续费*/
    @Excel(name="支付手续费")
	private java.math.BigDecimal serviceCharge;
    /**实际结算金额*/
    @Excel(name="实际结算金额")
    private java.math.BigDecimal realIncome;
    /**商品总额*/
    @Excel(name="商品总额")
    private java.math.BigDecimal goodsAmount;
    /**订单总额*/
    @Excel(name="订单总额")
    private java.math.BigDecimal orderAmount;
	/**服务导购*/
	@Excel(name="服务导购")
	private java.lang.String toGuideId;
	/**零售商ID*/
	private java.lang.String toRetailerId;
	/**订单留言*/
	@Excel(name="订单留言")
	private java.lang.String orderMessage;
	/**订单赠送积分*/
	@Excel(name="订单赠送积分")
	private java.math.BigDecimal orderPointscount;
	/**收货人*/
	private java.lang.String reciverName;
	/**联系电话*/
	private java.lang.String reciverPhone;
	/**省*/
	private java.lang.String reciverProvince;
	/**市*/
	private java.lang.String reciverCity;
	/**区*/
	private java.lang.String reciverRegion;
	/**详细地址*/
	private java.lang.String reciverDetailInfo;
	/**邮编 弃用   20161112改为 -1标识是测试的订单*/
	private java.lang.String postCode;
	/**推送预订单的状态*/
	private java.lang.String recommendStatus;//1:待确认,2:已取消,3:已确认,4:已关闭(24h后)
	/**代金券id*/
	private java.lang.String ticketId;  
	/**代金券明细id*/
	private java.lang.String ticketDetailId;  
	/**代金券明细编码*/
	private java.lang.String ticketCode;  
	/**代金券满额*/
	private java.math.BigDecimal ticketLeastMoney;
	/**代金券减额*/
	private java.math.BigDecimal ticketDiscount;
	/**代金券实际优惠额度*/
	private java.math.BigDecimal ticketPreferential;
	/**优惠券张数*/
	private int ticketNum;
	/**G+卡折扣*/
	private java.math.BigDecimal vipDiscount;
	/**G+卡付款额*/
	private java.math.BigDecimal vipMoneyPay;
	 /**订单来源 20161231新增*/
	private String source ;//1APP,2微信，3QQ，4其他
	
	/**配送方式   0：自提，1：后台发货，2：店铺发货*/
	@Excel(name="配送方式")
	private java.lang.String deliveryType;
	/**发货时间*/
	private java.util.Date sendTime;
	/**物流编号*/
	@Excel(name="物流编号")
	private java.lang.String deliveryCode;
	/**物流称*/
	@Excel(name="物流名称")
	private java.lang.String deliveryName;
	/**快递单号*/
	@Excel(name="快递单号")
	private java.lang.String deliveryNo;
	/**退款状态*/
	private java.lang.String refundStatus;
	/**退款金额*/
	private java.math.BigDecimal refundAmount;
	/**退款数量*/
	private java.math.BigDecimal returnNum;
	/**是否能整单退款 Y:是 ,N:否，A：已全部退完*/
	private java.lang.String canRefundAll;
	/**导购优惠金额  20170810新增*/
	private java.math.BigDecimal guidePrivilege;
	/**导购备注 20170810新增*/
	private java.lang.String guideRemark;
	/**会员号 20171224新增*/
	private java.lang.String vipCode;
	
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
	 *@return: java.lang.String  订单号
	 */
	
	@Column(name ="ORDER_NO",nullable=true,length=32)
	public java.lang.String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下单时间
	 */
	
	@Column(name ="ORDER_TIME",nullable=true,length=20)
	public java.util.Date getOrderTime(){
		return this.orderTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下单时间
	 */
	public void setOrderTime(java.util.Date orderTime){
		this.orderTime = orderTime;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  件数
	 */
	
	@Column(name ="QUANTITY_AMOUNT",nullable=true,length=12)
	public java.math.BigDecimal getQuantityAmount(){
		return this.quantityAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  件数
	 */
	public void setQuantityAmount(java.math.BigDecimal quantityAmount){
		this.quantityAmount = quantityAmount;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付方式
	 */
	
	@Column(name ="PAY_METHOD",nullable=true,length=32)
	public java.lang.String getPayMethod(){
		return this.payMethod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付方式
	 */
	public void setPayMethod(java.lang.String payMethod){
		this.payMethod = payMethod;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付帐号
	 */
	
	@Column(name ="PAY_ACCOUNT",nullable=true,length=32)
	public java.lang.String getPayAccount(){
		return this.payAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付帐号
	 */
	public void setPayAccount(java.lang.String payAccount){
		this.payAccount = payAccount;
	}
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  支付时间
	 */
	
	@Column(name ="PAY_TIME",nullable=true,length=20)
	public java.util.Date getPayTime(){
		return this.payTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  支付时间
	 */
	public void setPayTime(java.util.Date payTime){
		this.payTime = payTime;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单状态
	 */
	
	@Column(name ="ORDER_STATUS",nullable=true,length=1)
	public java.lang.String getOrderStatus(){
		return this.orderStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单状态
	 */
	public void setOrderStatus(java.lang.String orderStatus){
		this.orderStatus = orderStatus;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  支付金额
	 */
	
	@Column(name ="PAY_AMOUNT",nullable=true,length=12)
	public java.math.BigDecimal getPayAmount(){
		return this.payAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  支付金额
	 */
	public void setPayAmount(java.math.BigDecimal payAmount){
		this.payAmount = payAmount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  运费
	 */
	
	@Column(name ="FARE_AMOUNT",nullable=true,length=12)
	public java.math.BigDecimal getFareAmount(){
		return this.fareAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  运费
	 */
	public void setFareAmount(java.math.BigDecimal fareAmount){
		this.fareAmount = fareAmount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  商品总额
	 */
	
	@Column(name ="GOODS_AMOUNT",nullable=true,length=12)
	public java.math.BigDecimal getGoodsAmount(){
		return this.goodsAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  商品总额
	 */
	public void setGoodsAmount(java.math.BigDecimal goodsAmount){
		this.goodsAmount = goodsAmount;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属导购
	 */
	@Column(name ="TO_GUIDE_ID",nullable=true,length=36)
	public java.lang.String getToGuideId(){
		return this.toGuideId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属导购
	 */
	public void setToGuideId(java.lang.String toGuideId){
		this.toGuideId = toGuideId;
	}
	@Column(name ="to_retailer_id")
	public java.lang.String getToRetailerId() {
		return toRetailerId;
	}

	public void setToRetailerId(java.lang.String toRetailerId) {
		this.toRetailerId = toRetailerId;
	}

	@Column(name ="user_Phone")
	public java.lang.String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(java.lang.String userPhone) {
		this.userPhone = userPhone;
	}
	@Column(name ="user_Name")
	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	@Column(name ="pay_No")
	public java.lang.String getPayNo() {
		return payNo;
	}

	public void setPayNo(java.lang.String payNo) {
		this.payNo = payNo;
	}
	@Column(name ="service_Charge")
	public java.math.BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(java.math.BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	@Column(name ="real_Income")
	public java.math.BigDecimal getRealIncome() {
		return realIncome;
	}

	public void setRealIncome(java.math.BigDecimal realIncome) {
		this.realIncome = realIncome;
	}
	@Column(name ="order_Amount")
	public java.math.BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(java.math.BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	@Column(name ="order_Message")
	public java.lang.String getOrderMessage() {
		return orderMessage;
	}

	public void setOrderMessage(java.lang.String orderMessage) {
		this.orderMessage = orderMessage;
	}
	@Column(name ="order_Pointscount")
	public java.math.BigDecimal getOrderPointscount() {
		return orderPointscount;
	}

	public void setOrderPointscount(java.math.BigDecimal orderPointscount) {
		this.orderPointscount = orderPointscount;
	}
	@Column(name ="reciver_Name")
	public java.lang.String getReciverName() {
		return reciverName;
	}

	public void setReciverName(java.lang.String reciverName) {
		this.reciverName = reciverName;
	}
	@Column(name ="reciver_Phone")
	public java.lang.String getReciverPhone() {
		return reciverPhone;
	}
	
	public void setReciverPhone(java.lang.String reciverPhone) {
		this.reciverPhone = reciverPhone;
	}
	@Column(name ="reciver_Province")
	public java.lang.String getReciverProvince() {
		return reciverProvince;
	}
	
	public void setReciverProvince(java.lang.String reciverProvince) {
		this.reciverProvince = reciverProvince;
	}
	@Column(name ="reciver_City")
	public java.lang.String getReciverCity() {
		return reciverCity;
	}

	public void setReciverCity(java.lang.String reciverCity) {
		this.reciverCity = reciverCity;
	}
	@Column(name ="reciver_Region")
	public java.lang.String getReciverRegion() {
		return reciverRegion;
	}

	public void setReciverRegion(java.lang.String reciverRegion) {
		this.reciverRegion = reciverRegion;
	}
	@Column(name ="reciver_Detail_Info")
	public java.lang.String getReciverDetailInfo() {
		return reciverDetailInfo;
	}

	public void setReciverDetailInfo(java.lang.String reciverDetailInfo) {
		this.reciverDetailInfo = reciverDetailInfo;
	}
	@Column(name ="post_Code")
	public java.lang.String getPostCode() {
		return postCode;
	}

	public void setPostCode(java.lang.String postCode) {
		this.postCode = postCode;
	}
	@Column(name ="close_Time")
	public java.util.Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(java.util.Date closeTime) {
		this.closeTime = closeTime;
	}
	@Column(name ="pay_Status")
	public java.lang.String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}
	@Column(name ="recommend_Status")
	public java.lang.String getRecommendStatus() {
		return recommendStatus;
	}

	public void setRecommendStatus(java.lang.String recommendStatus) {
		this.recommendStatus = recommendStatus;
	}
	@Column(name ="finished_Time")
	public java.util.Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(java.util.Date finishedTime) {
		this.finishedTime = finishedTime;
	}
	@Column(name ="bill_Batch")
	public java.lang.String getBillBatch() {
		return billBatch;
	}

	public void setBillBatch(java.lang.String billBatch) {
		this.billBatch = billBatch;
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
	@Column(name ="TICKET_CODE",nullable=true)
	public java.lang.String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(java.lang.String ticketCode) {
		this.ticketCode = ticketCode;
	}
	@Column(name ="TICKET_LEAST_MONEY",nullable=true)
	public java.math.BigDecimal getTicketLeastMoney() {
		return ticketLeastMoney;
	}

	public void setTicketLeastMoney(java.math.BigDecimal ticketLeastMoney) {
		this.ticketLeastMoney = ticketLeastMoney;
	}
	@Column(name ="TICKET_DISCOUNT",nullable=true)
	public java.math.BigDecimal getTicketDiscount() {
		return ticketDiscount;
	}

	public void setTicketDiscount(java.math.BigDecimal ticketDiscount) {
		this.ticketDiscount = ticketDiscount;
	}
	@Column(name ="TICKET_PREFERENTIAL",nullable=true)
	public java.math.BigDecimal getTicketPreferential() {
		return ticketPreferential;
	}

	public void setTicketPreferential(java.math.BigDecimal ticketPreferential) {
		this.ticketPreferential = ticketPreferential;
	}
	@Column(name ="vip_Discount",nullable=true)
	public java.math.BigDecimal getVipDiscount() {
		return vipDiscount;
	}

	public void setVipDiscount(java.math.BigDecimal vipDiscount) {
		this.vipDiscount = vipDiscount;
	}
	@Column(name ="vip_Money_Pay",nullable=true)
	public java.math.BigDecimal getVipMoneyPay() {
		return vipMoneyPay;
	}

	public void setVipMoneyPay(java.math.BigDecimal vipMoneyPay) {
		this.vipMoneyPay = vipMoneyPay;
	}
	@Column(name ="source",nullable=true)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	@Column(name ="delivery_Type")
	public java.lang.String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(java.lang.String deliveryType) {
		this.deliveryType = deliveryType;
	}
	@Column(name ="send_Time")
	public java.util.Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name ="delivery_Code")
	public java.lang.String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(java.lang.String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}
	@Column(name ="delivery_Name")
	public java.lang.String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(java.lang.String deliveryName) {
		this.deliveryName = deliveryName;
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
	@Column(name ="refund_Status")
	public java.lang.String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(java.lang.String refundStatus) {
		this.refundStatus = refundStatus;
	}
	@Column(name ="refund_Amount")
	public java.math.BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(java.math.BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	@Column(name ="return_num")
	public java.math.BigDecimal getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(java.math.BigDecimal returnNum) {
		this.returnNum = returnNum;
	}
	@Column(name ="guide_privilege")
	public java.math.BigDecimal getGuidePrivilege() {
		return guidePrivilege;
	}
	public void setGuidePrivilege(java.math.BigDecimal guidePrivilege) {
		this.guidePrivilege = guidePrivilege;
	}
	@Column(name ="guide_remark")
	public java.lang.String getGuideRemark() {
		return guideRemark;
	}


	public void setGuideRemark(java.lang.String guideRemark) {
		this.guideRemark = guideRemark;
	}
	@Column(name ="ticket_num")
	public int getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}
	@Column(name ="can_refund_all")
	public java.lang.String getCanRefundAll() {
		return canRefundAll;
	}

	public void setCanRefundAll(java.lang.String canRefundAll) {
		this.canRefundAll = canRefundAll;
	}

	@Column(name ="vip_code")
	public java.lang.String getVipCode() {
		return vipCode;
	}

	public void setVipCode(java.lang.String vipCode) {
		this.vipCode = vipCode;
	}
}
