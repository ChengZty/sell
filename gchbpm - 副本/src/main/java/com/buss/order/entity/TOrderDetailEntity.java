package com.buss.order.entity;

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
 * @Description: 订单明细
 * @author onlineGenerator
 * @date 2016-03-15 17:45:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_order_detail", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TOrderDetailEntity implements java.io.Serializable {
	/*订单状态*/
//	public static String ORDER_STATUS_0 = "0";//待给优惠
	public static String ORDER_STATUS_1 = "1";//待付款
	public static String ORDER_STATUS_2 = "2";//待发货(已付款)
	public static String ORDER_STATUS_3 = "3";//已发货(待收货)
	public static String ORDER_STATUS_4 = "4";//已完成(已收货)
	public static String ORDER_STATUS_8 = "8";//已关闭，已退款（下单后已经付款，待发货的时候取消）
	public static String ORDER_STATUS_9 = "9";//已取消（下单后没有付款就取消或者24小时没付款后台取消）
	/*评价状态*/
	public static String EVALUATION_STATUS_0 = "0";//未评价
	public static String EVALUATION_STATUS_1 = "1";//已评价
	/*退货状态*/
	public static String RETURN_STATUS_0 = "0";//否
	public static String RETURN_STATUS_1 = "1";//退货中
	public static String RETURN_STATUS_2 = "2";//（导购）同意退货
	public static String RETURN_STATUS_3 = "3";//（导购）拒绝退货
	public static String RETURN_STATUS_4 = "4";//（零售商）拒绝退货
	public static String RETURN_STATUS_5 = "5";//（退货完成（零售商同意退货）
	
	/*货品所属零售商类型*/
	public static String STORE_TYPE_1 = "1";//零售商 人货
	public static String STORE_TYPE_2 = "2";//零售商 货
	public static String STORE_TYPE_3 = "3";//零售商 人
	/*导购所属零售商类型*/
	public static String RETAILER_TYPE_1 = "1";//零售商 人货
	public static String RETAILER_TYPE_2 = "2";//零售商 货
	public static String RETAILER_TYPE_3 = "3";//零售商 人

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
	/**帐号ID*/
	private java.lang.String userId;
	/**用户手机号*/
	private java.lang.String userPhone;
    /**支付帐号*/
    private java.lang.String payAccount;
	/**主单ID*/
	private java.lang.String orderId;
	/**订单号*/
	private java.lang.String orderNo;
	/**子订单号*/
	private java.lang.String subOrderNo;
	/**订单创建时间*/
	private java.util.Date addTime;
	/**订单完成时间*/
	private java.util.Date finishedTime;
	/**商品ID*/
	private java.lang.String goodsId;
	/**商品款号*/
	private java.lang.String goodsCode;
	/**商品规格ID*/
	private java.lang.String specId;
	/**规格*/
	private java.lang.String specInfo;
	/**类目一*/
	private java.lang.String topCategoryId;
	/**类目二*/
	private java.lang.String subCategoryId;
	/**类目三*/
	private java.lang.String thridCategoryId;
	/**品牌*/
	private java.lang.String brandId;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String goodsName;
	/**件数*/
	@Excel(name="件数")
	private java.math.BigDecimal quantity;
	/**原价*/
	@Excel(name="原价")
	private java.math.BigDecimal priceOriginal;
	/**现价*/
	@Excel(name="现价")
	private java.math.BigDecimal currentPrice;
	/**组合价*/
	@Excel(name="组合价")
	private java.math.BigDecimal priceCombination;
	/**结算价(单价)*/
	@Excel(name="结算价(单价)")
	private java.math.BigDecimal priceNow;
	/**商品总额*/
    @Excel(name="总价")
    private java.math.BigDecimal goodsAmount;
	/**商品描述*/
	@Excel(name="商品描述")
	private java.lang.String goodsDescription;
	/**图片*/
	@Excel(name="图片")
	private java.lang.String goodsPic;
	/**运费*/
	@Excel(name="运费")
	private java.math.BigDecimal fare;
	
	/**关闭时间*/
	private java.util.Date closeTime;
	/**支付状态*/
	private java.lang.String payStatus;//1待支付，2支付成功，3支付失败
	/**支付时间*/
	@Excel(name="支付时间",format = "yyyy-MM-dd")
	private java.util.Date payTime;
	/**折扣*/
	@Excel(name="折扣")
	private java.math.BigDecimal discount;
	/**商品总优惠价*/
	@Excel(name="优惠价")
	private java.math.BigDecimal pricePreferential;//该商品总优惠价(结算价相对于原价的优惠后乘以件数的总优惠)
	/**券的总优惠*/
	private java.math.BigDecimal ticketPreferential;//该商品券的总优惠价(券均摊后同订单明细多件商品的总优惠)
	/**现金支付总额*/
	private java.math.BigDecimal salesPrice;
	/**G+卡付款总额*/
	private java.math.BigDecimal vipMoneyPay;
	/**订单状态*/
	@Excel(name="订单状态")
	private java.lang.String orderStatus;
	/**所属导购ID*/
	@Excel(name="所属导购ID")
	private java.lang.String toGuideId;
	/**导购所属零售商ID*/
	@Excel(name="导购所属零售商ID")
	private java.lang.String toRetailerId;
	/**导购所属零售商类型*/
	private java.lang.String retailerType;
	/**货品所属零售商*/
	private java.lang.String toStoreGoodsId;
	/**货品所属零售商类别*/
	private java.lang.String storeType;//字典  1：零售商 人货，2：零售商 货 ，3：零售商 人
	/**评价状态*/
	private java.lang.String evaluationStatus;
	/**退货状态*/
	private java.lang.String returnStatus ;
	/**退款金额*/
	private java.math.BigDecimal refundAmount;
	/**退货数量*/
	private java.math.BigDecimal returnNum;
	
	/**延迟收货时间*/
	private java.lang.Integer delayDays;
	/**是否为新物流状态*/
	private java.lang.String deliveryNew;
	/**物流最新更新时间*/
	private java.util.Date deliveryUpdateTime;
	/**物流状态*/
	private java.lang.String deliveryStatus;
	/**物流消息*/
	private java.lang.String deliveryMsg;
	/**组合商品ID*/
	private java.lang.String commodityId;
	/**商品类型*/
	private java.lang.String goodsType;//单品：1 组合：2 搭配：3
	/**评星*/
	private java.lang.Integer scores;
	/**是否更换地区*/
	private java.lang.String replacementArea;
	/**是否是特殊商品（Y/N，默认为N）*/
//	private String isSpecial ;
	/**赠品规则id*/
	private String giftRuleId ;//如果是赠品，则该值不会为空
	/**导购激励活动id 20171024新增*/
	private String finActId ;
	/**顾客促销活动id 20171024新增*/
	private String goodsActId ;
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
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	@Column(name ="PAY_ACCOUNT",nullable=true,length=32)
	public java.lang.String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(java.lang.String payAccount) {
		this.payAccount = payAccount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主单ID
	 */
	@Column(name ="ORDER_ID",nullable=true,length=36)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主单ID
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单号
	 */
	@Column(name ="SUB_ORDER_NO",nullable=true,length=32)
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
	 *@return: java.lang.String  商品ID
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品ID
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	@Column(name ="GOODS_CODE")
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=50)
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
	 *@return: java.math.BigDecimal  件数
	 */
	@Column(name ="QUANTITY",nullable=true,length=12)
	public java.math.BigDecimal getQuantity(){
		return this.quantity;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  件数
	 */
	public void setQuantity(java.math.BigDecimal quantity){
		this.quantity = quantity;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  原价
	 */
	@Column(name ="PRICE_ORIGINAL",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getPriceOriginal(){
		return this.priceOriginal;
	}
	public void setPriceOriginal(java.math.BigDecimal priceOriginal){
		this.priceOriginal = priceOriginal;
	}

	@Column(name ="CURRENT_PRICE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getCurrentPrice(){
		return this.currentPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  现价
	 */
	public void setCurrentPrice(java.math.BigDecimal currentPrice){
		this.currentPrice = currentPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  组合价
	 */
	@Column(name ="PRICE_COMBINATION",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getPriceCombination(){
		return this.priceCombination;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  组合价
	 */
	public void setPriceCombination(java.math.BigDecimal priceCombination){
		this.priceCombination = priceCombination;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  现价
	 */
	@Column(name ="PRICE_NOW",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getPriceNow(){
		return this.priceNow;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  现价
	 */
	public void setPriceNow(java.math.BigDecimal priceNow){
		this.priceNow = priceNow;
	}
	@Column(name ="goods_amount",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(java.math.BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品描述
	 */
	@Column(name ="GOODS_DESCRIPTION",nullable=true,length=200)
	public java.lang.String getGoodsDescription(){
		return this.goodsDescription;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品描述
	 */
	public void setGoodsDescription(java.lang.String goodsDescription){
		this.goodsDescription = goodsDescription;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片
	 */
	@Column(name ="GOODS_PIC",nullable=true,length=100)
	public java.lang.String getGoodsPic(){
		return this.goodsPic;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片
	 */
	public void setGoodsPic(java.lang.String goodsPic){
		this.goodsPic = goodsPic;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  运费
	 */
	@Column(name ="FARE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getFare(){
		return this.fare;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  运费
	 */
	public void setFare(java.math.BigDecimal fare){
		this.fare = fare;
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  折扣
	 */
	@Column(name ="DISCOUNT",nullable=true,length=5)
	public java.math.BigDecimal getDiscount(){
		return this.discount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  折扣
	 */
	public void setDiscount(java.math.BigDecimal discount){
		this.discount = discount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  优惠价
	 */
	@Column(name ="PRICE_PREFERENTIAL",nullable=true,length=12)
	public java.math.BigDecimal getPricePreferential(){
		return this.pricePreferential;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  优惠价
	 */
	public void setPricePreferential(java.math.BigDecimal pricePreferential){
		this.pricePreferential = pricePreferential;
	}
	@Column(name ="TICKET_PREFERENTIAL",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getTicketPreferential() {
		return ticketPreferential;
	}

	public void setTicketPreferential(java.math.BigDecimal ticketPreferential) {
		this.ticketPreferential = ticketPreferential;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单状态
	 */
	@Column(name ="ORDER_STATUS",nullable=true,length=2)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属零售商
	 */
	@Column(name ="TO_RETAILER_ID",nullable=true,length=36)
	public java.lang.String getToRetailerId(){
		return this.toRetailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属零售商
	 */
	public void setToRetailerId(java.lang.String toRetailerId){
		this.toRetailerId = toRetailerId;
	}
	@Column(name ="retailer_Type")
	public java.lang.String getRetailerType() {
		return retailerType;
	}

	public void setRetailerType(java.lang.String retailerType) {
		this.retailerType = retailerType;
	}

	@Column(name ="user_Phone")
	public java.lang.String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(java.lang.String userPhone) {
		this.userPhone = userPhone;
	}
	@Column(name ="order_No")
	public java.lang.String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	@Column(name ="add_Time")
	public java.util.Date getAddTime() {
		return addTime;
	}

	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	@Column(name ="finished_Time")
	public java.util.Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(java.util.Date finishedTime) {
		this.finishedTime = finishedTime;
	}
	@Column(name ="spec_Id")
	public java.lang.String getSpecId() {
		return specId;
	}

	public void setSpecId(java.lang.String specId) {
		this.specId = specId;
	}
	@Column(name ="Spec_Info")
	public java.lang.String getSpecInfo() {
		return specInfo;
	}

	public void setSpecInfo(java.lang.String specInfo) {
		this.specInfo = specInfo;
	}
	@Column(name ="top_Category_Id")
	public java.lang.String getTopCategoryId() {
		return topCategoryId;
	}

	public void setTopCategoryId(java.lang.String topCategoryId) {
		this.topCategoryId = topCategoryId;
	}
	@Column(name ="sub_Category_Id")
	public java.lang.String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(java.lang.String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	@Column(name ="thrid_Category_Id")
	public java.lang.String getThridCategoryId() {
		return thridCategoryId;
	}

	public void setThridCategoryId(java.lang.String thridCategoryId) {
		this.thridCategoryId = thridCategoryId;
	}
	@Column(name ="brand_Id")
	public java.lang.String getBrandId() {
		return brandId;
	}

	public void setBrandId(java.lang.String brandId) {
		this.brandId = brandId;
	}
	
	@Column(name ="sales_Price")
	public java.math.BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(java.math.BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	@Column(name ="vip_Money_Pay",nullable=true)
	public java.math.BigDecimal getVipMoneyPay() {
		return vipMoneyPay;
	}

	public void setVipMoneyPay(java.math.BigDecimal vipMoneyPay) {
		this.vipMoneyPay = vipMoneyPay;
	}
	@Column(name ="to_Store_Goods_Id")
	public java.lang.String getToStoreGoodsId() {
		return toStoreGoodsId;
	}

	public void setToStoreGoodsId(java.lang.String toStoreGoodsId) {
		this.toStoreGoodsId = toStoreGoodsId;
	}
	@Column(name ="store_Type")
	public java.lang.String getStoreType() {
		return storeType;
	}

	public void setStoreType(java.lang.String storeType) {
		this.storeType = storeType;
	}
	@Column(name ="evaluation_Status")
	public java.lang.String getEvaluationStatus() {
		return evaluationStatus;
	}

	public void setEvaluationStatus(java.lang.String evaluationStatus) {
		this.evaluationStatus = evaluationStatus;
	}
	
	
	@Column(name ="return_Status")
	public java.lang.String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(java.lang.String returnStatus) {
		this.returnStatus = returnStatus;
	}
	@Column(name ="refund_amount")
	public java.math.BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(java.math.BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	@Column(name ="return_Num")
	public java.math.BigDecimal getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(java.math.BigDecimal returnNum) {
		this.returnNum = returnNum;
	}
	
	@Column(name ="delay_days")
	public java.lang.Integer getDelayDays() {
		return delayDays;
	}

	public void setDelayDays(java.lang.Integer delayDays) {
		this.delayDays = delayDays;
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
	@Column(name ="delivery_new")
	public java.lang.String getDeliveryNew() {
		return deliveryNew;
	}

	public void setDeliveryNew(java.lang.String deliveryNew) {
		this.deliveryNew = deliveryNew;
	}
	@Column(name ="delivery_update_time")
	public java.util.Date getDeliveryUpdateTime() {
		return deliveryUpdateTime;
	}
	public void setDeliveryUpdateTime(java.util.Date deliveryUpdateTime) {
		this.deliveryUpdateTime = deliveryUpdateTime;
	}
	@Column(name ="delivery_status")
	public java.lang.String getDeliveryStatus() {
		return deliveryStatus;
	}
	
	public void setDeliveryStatus(java.lang.String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	@Column(name ="delivery_msg")
	public java.lang.String getDeliveryMsg() {
		return deliveryMsg;
	}
	
	public void setDeliveryMsg(java.lang.String deliveryMsg) {
		this.deliveryMsg = deliveryMsg;
	}
	@Column(name ="commodity_Id")
	public java.lang.String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(java.lang.String commodityId) {
		this.commodityId = commodityId;
	}
	@Column(name ="goods_type")
	public java.lang.String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(java.lang.String goodsType) {
		this.goodsType = goodsType;
	}
	@Column(name ="scores")
	public java.lang.Integer getScores() {
		return scores;
	}

	public void setScores(java.lang.Integer scores) {
		this.scores = scores;
	}
	
	@Column(name ="replacement_Area",nullable=true,length=2)
	public java.lang.String getReplacementArea() {
		return replacementArea;
	}

	public void setReplacementArea(java.lang.String replacementArea) {
		this.replacementArea = replacementArea;
	}
	
	@Column(name ="GIFT_RULE_ID",nullable=false)
	public String getGiftRuleId() {
		return giftRuleId;
	}

	public void setGiftRuleId(String giftRuleId) {
		this.giftRuleId = giftRuleId;
	}
	@Column(name ="fin_act_id")
	public String getFinActId() {
		return finActId;
	}

	public void setFinActId(String finActId) {
		this.finActId = finActId;
	}
	@Column(name ="goods_act_id")
	public String getGoodsActId() {
		return goodsActId;
	}

	public void setGoodsActId(String goodsActId) {
		this.goodsActId = goodsActId;
	}

	@Column(name ="vip_code")
	public java.lang.String getVipCode() {
		return vipCode;
	}

	public void setVipCode(java.lang.String vipCode) {
		this.vipCode = vipCode;
	}
}
