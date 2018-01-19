package com.buss.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 店铺订单实体，比订单表多一个图片字段和customer_id
 * @author onlineGenerator
 * @date 2016-03-15 17:20:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_store_order_info", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TStoreOrderInfoEntity implements java.io.Serializable {
	/**订单状态*/
	public static String ORDER_STATUS_1 = "1";//待付款
	public static String ORDER_STATUS_2 = "2";//待发货(已付款)
	public static String ORDER_STATUS_3 = "3";//已发货(待收货)(明细状态都已发货)
	public static String ORDER_STATUS_4 = "4";//已完成(已收货)(明细状态都已收货)
	public static String ORDER_STATUS_8 = "8";//已关闭(待发货取消订单)
	public static String ORDER_STATUS_9 = "9";//已取消(待付款取消订单)
	
	/**推荐预订单状态*/
//	public static String RECOMMEND_STATUS_0 = "0";//刚生成的预订单
	public static String RECOMMEND_STATUS_1 = "1";//待确认
	public static String RECOMMEND_STATUS_2 = "2";//已取消
	public static String RECOMMEND_STATUS_3 = "3";//已确认
	public static String RECOMMEND_STATUS_4 = "4";//已关闭
	
	/*退款状态*/
	public static String REFUND_STATUS_0 = "0";//否（默认值）
	public static String REFUND_STATUS_1 = "1";//处理中(该状态会没有了，直接就是从导购申请退款就是2)
	public static String REFUND_STATUS_2 = "2";//待商家处理(导购同意后该状态从1改为2)
	public static String REFUND_STATUS_3 = "3";//商家同意
	public static String REFUND_STATUS_4 = "4";//商家拒绝
	public static String REFUND_STATUS_5 = "5";//退款完成
	
	/*物流方式状态*/
	public static String DELIVERY_TYPE_0 = "0";//无需物流
	public static String DELIVERY_TYPE_1 = "1";//需物流
	
	/**订单来源 1APP,2微信，3QQ，4其他*/
	public static String SOURCE_1 = "1";//APP
	public static String SOURCE_2 = "2";//微信
	public static String SOURCE_3 = "3";//QQ
	public static String SOURCE_4 = "4";//其他
	
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
	@Excel(name="订单号",width=25)
	private java.lang.String orderNo;
	/**下单时间*/
	private java.util.Date orderTime;
	/**关闭时间*/
	private java.util.Date closeTime;
	/**件数   成交件数*/
	@Excel(name="成交件数",width=15)
	private java.math.BigDecimal quantityAmount;
	/**帐号ID*/
	private java.lang.String userId;
	/**IM 帐号*/
	private String imUserName;
	/**用户姓名*/
	@Excel(name="顾客姓名",width=15)
	private java.lang.String userName;
	/**用户手机号*/
	@Excel(name="顾客手机号",width=15)
	private java.lang.String userPhone;
	/**支付方式*/
	private java.lang.String payMethod;
    /**支付帐号*/
    private java.lang.String payAccount;
	/**支付时间  成交时间*/
	@Excel(name="成交时间",width=25)
	private java.util.Date payTime;
	/**支付单号*/
	private java.lang.String payNo;
	/**支付状态*/
	private java.lang.String payStatus;//1.待支付 2.支付成功 3.支付失败
    /**订单状态*/
    private java.lang.String orderStatus;
	/**支付金额   成交金额*/
	@Excel(name="成交金额",width=15)
	private java.math.BigDecimal payAmount;
	/**运费*/
	private java.math.BigDecimal fareAmount;
	/**支付手续费*/
	private java.math.BigDecimal serviceCharge;
    /**实际结算金额*/
    private java.math.BigDecimal realIncome;
    /**商品总额*/
    private java.math.BigDecimal goodsAmount;
    /**订单总额*/
    private java.math.BigDecimal orderAmount;
	/**服务导购*/
	private java.lang.String toGuideId;
	/**订单留言*/
	private java.lang.String orderMessage;
	/**订单赠送积分*/
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
	/**邮编*/
	private java.lang.String postCode;
	/**推送预订单的标志*/
	private java.lang.String flag;  //Y代表是
	/**推送预订单的状态*/
	private java.lang.String recommendStatus;//1:待确认,2:已取消,3:已确认,4:已关闭(24h后)
	/**是否找管家*/
	private java.lang.String isFind;  //Y代表是，N代表否,
	/**是否给优惠*/
	private java.lang.String isGive;  //Y代表是，N代表否
	/**代金券id*/
	private java.lang.String ticketId;  
	/**代金券明细id*/
	private java.lang.String ticketDetailId;  
	/**代金券明细编码*/
	private java.lang.String ticketCode;  
	/**代金券满额*/
	private java.math.BigDecimal ticketLeastMoney;
	/**代金券减额或折扣*/
	private java.math.BigDecimal ticketDiscount;
	/**代金券实际优惠额度*/
	private java.math.BigDecimal ticketPreferential;
	/**优惠券张数*/
	private int ticketNum;
	
	/**G+卡vip折扣*/
	private java.math.BigDecimal vipDiscount;
	/**G+卡vip付款额*/
	private java.math.BigDecimal vipMoneyPay;
    /**结算批次（用于判断主单是否已结算过）*/
    private java.lang.String billBatch;
	/**赠品规则id*/
	private String giftRuleId ;//如果是赠品，则该值不会为空
	/**订单来源*/
	private String source ;//1APP,2微信，3QQ，4其他
	
	/**发货时间*/
	private java.util.Date sendTime;
	/**配送方式*/
	private java.lang.String deliveryType;//0无需物流,1仓库发货，2店铺发货
	/**物流编号*/
	private java.lang.String deliveryCode;
	/**物流称*/
	private java.lang.String deliveryName;
	/**快递单号*/
	private java.lang.String deliveryNo;
	/**零售商*/
	private java.lang.String toRetailerId;
	/**退款状态*/
	private java.lang.String refundStatus;
	/**退款金额*/
	private java.math.BigDecimal refundAmount;
	/**退款数量*/
	private java.math.BigDecimal returnNum;
	/**是否能整单退款 Y:是， N:否，A：已全部退完*/
	private java.lang.String canRefundAll;
	/**导购优惠金额  20170810新增*/
	private java.math.BigDecimal guidePrivilege;
	/**导购备注 20170810新增*/
	private java.lang.String guideRemark;
	/**待发展顾客id 20171028新增*/
	private java.lang.String customerId;
	/**店铺订单明细图片第一张图*/
	private java.lang.String picUrl;
	

	/**导购姓名*/
	@Excel(name="导购姓名",width=15)
	private java.lang.String guideName;
	/**导购手机号*/
	@Excel(name="导购手机号",width=15)
	private java.lang.String guidePhone;
	/**店铺ID*/
	private java.lang.String storeId;
	/**店铺名称*/
	@Excel(name="导购所在店铺",width=20)
	private java.lang.String storeName;
	
	public TStoreOrderInfoEntity() {
	}
	


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
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
	@Temporal(TemporalType.TIMESTAMP)
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
	
	@Column(name ="CLOSE_TIME",nullable=true,length=20)
	@Temporal(TemporalType.TIMESTAMP)
	public java.util.Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(java.util.Date closeTime) {
		this.closeTime = closeTime;
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
	
	@Column(name ="PAY_AMOUNT",nullable=true,scale=2,length=12)
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
	
	@Column(name ="FARE_AMOUNT",nullable=true,scale=2,length=12)
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
	
	@Column(name ="GOODS_AMOUNT",nullable=true,scale=2,length=12)
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
	@Column(name ="flag")
	public java.lang.String getFlag() {
		return flag;
	}

	public void setFlag(java.lang.String flag) {
		this.flag = flag;
	}
	@Column(name ="IS_FIND")
	public java.lang.String getIsFind() {
		return isFind;
	}

	public void setIsFind(java.lang.String isFind) {
		this.isFind = isFind;
	}
	@Column(name ="IS_GIVE")
	public java.lang.String getIsGive() {
		return isGive;
	}

	public void setIsGive(java.lang.String isGive) {
		this.isGive = isGive;
	}
	
	
	@Column(name ="recommend_Status")
	public java.lang.String getRecommendStatus() {
		return recommendStatus;
	}

	public void setRecommendStatus(java.lang.String recommendStatus) {
		this.recommendStatus = recommendStatus;
	}
	
	@Column(name ="PAY_STATUS")
	public java.lang.String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}
	@Column(name ="im_user_name",nullable=true)
	public String getImUserName() {
		return imUserName;
	}

	public void setImUserName(String imUserName) {
		this.imUserName = imUserName;
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
	@Column(name ="TICKET_NUM")
	public int getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}


	@Column(name ="VIP_DISCOUNT",nullable=true)
	public java.math.BigDecimal getVipDiscount() {
		return vipDiscount;
	}

	public void setVipDiscount(java.math.BigDecimal vipDiscount) {
		this.vipDiscount = vipDiscount;
	}
	@Column(name ="VIP_MONEY_PAY",nullable=true)
	public java.math.BigDecimal getVipMoneyPay() {
		return vipMoneyPay;
	}

	public void setVipMoneyPay(java.math.BigDecimal vipMoneyPay) {
		this.vipMoneyPay = vipMoneyPay;
	}
	@Column(name ="bill_Batch")
	public java.lang.String getBillBatch() {
		return billBatch;
	}

	public void setBillBatch(java.lang.String billBatch) {
		this.billBatch = billBatch;
	}

	@Column(name ="GIFT_RULE_ID")
	public String getGiftRuleId() {
		return giftRuleId;
	}


	public void setGiftRuleId(String giftRuleId) {
		this.giftRuleId = giftRuleId;
	}
	@Column(name ="SOURCE")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	@Column(name ="send_time",nullable=true,length=20)
	public java.util.Date getSendTime() {
		return sendTime;
	}

	@Column(name ="TO_RETAILER_ID",nullable=true,length=36)
	public java.lang.String getToRetailerId() {
		return toRetailerId;
	}


	public void setToRetailerId(java.lang.String toRetailerId) {
		this.toRetailerId = toRetailerId;
	}


	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}
	@Column(name ="delivery_Type")
	public java.lang.String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(java.lang.String deliveryType) {
		this.deliveryType = deliveryType;
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
	@Column(name ="can_Refund_All")
	public java.lang.String getCanRefundAll() {
		return canRefundAll;
	}
	public void setCanRefundAll(java.lang.String canRefundAll) {
		this.canRefundAll = canRefundAll;
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
	@Column(name ="customer_Id")
	public java.lang.String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(java.lang.String customerId) {
		this.customerId = customerId;
	}
	@Column(name ="pic_Url")
	public java.lang.String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(java.lang.String picUrl) {
		this.picUrl = picUrl;
	}

	@Transient
	public java.lang.String getStoreId() {
		return storeId;
	}
	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	@Transient
	public java.lang.String getStoreName() {
		return storeName;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
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
	
}
