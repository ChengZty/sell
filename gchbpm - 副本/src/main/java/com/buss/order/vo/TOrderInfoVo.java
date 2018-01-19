package com.buss.order.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class TOrderInfoVo implements java.io.Serializable{
	/**主单ID*/
	@Excel(name="主单ID",width=20)
	private java.lang.String id;
	/**主单号*/
	@Excel(name="主单号",width=20)
	private java.lang.String orderNo;
	/**买家电话*/
	@Excel(name="买家电话",width=15)
	private java.lang.String userPhone;
	/**买家昵称*/
	@Excel(name="买家昵称")
	private java.lang.String userName;
	 /**下单时间*/
    @Excel(name="下单时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date orderTime;
    /**支付方式*/
	@Excel(name="支付方式")
	private java.lang.String payMethod;
	/**流水号*/
	@Excel(name="流水号",width=20)
	private java.lang.String payNo;
    /**付款时间*/
    @Excel(name="付款时间",format="yyyy-MM-dd HH:mm:ss",width=20)
    private java.util.Date payTime;
    /**总件数*/
	@Excel(name="总件数")
	private java.math.BigDecimal quantityAmount;
	/**商品总额*/
	@Excel(name="商品总额")
	private java.math.BigDecimal goodsAmount;
	/**总运费*/
	@Excel(name="总运费")
	private java.math.BigDecimal fareAmount;
	/**订单总额*/
	@Excel(name="订单总额")
	private java.math.BigDecimal orderAmount;
	@Excel(name="支付手续费")
	private java.math.BigDecimal serviceCharge;
    /**实际结算金额*/
    @Excel(name="实际结算金额")
    private java.math.BigDecimal realIncome;
    /**现金支付金额*/
    @Excel(name="现金支付金额")
	private java.math.BigDecimal payAmount;
	/**G+卡折扣*/
    @Excel(name="G+卡折扣")
	private java.math.BigDecimal vipDiscount;
	/**G+卡付款额*/
	@Excel(name="G+卡付款总额")
	private java.math.BigDecimal vipMoneyPay;
	/**G+券优惠*/
	@Excel(name="G+券优惠")
	private java.math.BigDecimal ticketPreferential;
	/**导购ID*/
	@Excel(name="导购ID")
	private java.lang.String toGuideId;
	/**导购姓名*/
	@Excel(name="导购姓名")
	private java.lang.String realname;
	/**收货人*/
	@Excel(name="收货人")
	private java.lang.String reciverName;
	/**收货人电话*/
	@Excel(name="收货人电话",width=15)
	private java.lang.String reciverPhone;
	/**收货地址*/
	@Excel(name="收货地址",width=30)
	private java.lang.String reciverDetailInfo;
    /**买家留言*/
	@Excel(name="买家留言",width=20)
	private java.lang.String orderMessage;
	/**订单状态*/
	@Excel(name="订单状态",replace={"待给优惠_0","待付款_1","待发货_2","已发货_3","已完成_4","已取消_9","已关闭_8"})
	private java.lang.String orderStatus;
	public java.lang.String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	public java.lang.String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(java.lang.String userPhone) {
		this.userPhone = userPhone;
	}
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	public java.util.Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}
	public java.lang.String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(java.lang.String payMethod) {
		this.payMethod = payMethod;
	}
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	public java.math.BigDecimal getQuantityAmount() {
		return quantityAmount;
	}
	public void setQuantityAmount(java.math.BigDecimal quantityAmount) {
		this.quantityAmount = quantityAmount;
	}
	public java.math.BigDecimal getGoodsAmount() {
		return goodsAmount;
	}
	public void setGoodsAmount(java.math.BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	public java.math.BigDecimal getFareAmount() {
		return fareAmount;
	}
	public void setFareAmount(java.math.BigDecimal fareAmount) {
		this.fareAmount = fareAmount;
	}
	public java.math.BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(java.math.BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public java.math.BigDecimal getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(java.math.BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public java.math.BigDecimal getRealIncome() {
		return realIncome;
	}
	public void setRealIncome(java.math.BigDecimal realIncome) {
		this.realIncome = realIncome;
	}
	public java.math.BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(java.math.BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public java.math.BigDecimal getVipDiscount() {
		return vipDiscount;
	}
	public void setVipDiscount(java.math.BigDecimal vipDiscount) {
		this.vipDiscount = vipDiscount;
	}
	public java.math.BigDecimal getVipMoneyPay() {
		return vipMoneyPay;
	}
	public void setVipMoneyPay(java.math.BigDecimal vipMoneyPay) {
		this.vipMoneyPay = vipMoneyPay;
	}
	public java.math.BigDecimal getTicketPreferential() {
		return ticketPreferential;
	}
	public void setTicketPreferential(java.math.BigDecimal ticketPreferential) {
		this.ticketPreferential = ticketPreferential;
	}
	public java.lang.String getToGuideId() {
		return toGuideId;
	}
	public void setToGuideId(java.lang.String toGuideId) {
		this.toGuideId = toGuideId;
	}
	public java.lang.String getRealname() {
		return realname;
	}
	public void setRealname(java.lang.String realname) {
		this.realname = realname;
	}
	public java.lang.String getReciverName() {
		return reciverName;
	}
	public void setReciverName(java.lang.String reciverName) {
		this.reciverName = reciverName;
	}
	public java.lang.String getReciverPhone() {
		return reciverPhone;
	}
	public void setReciverPhone(java.lang.String reciverPhone) {
		this.reciverPhone = reciverPhone;
	}
	public java.lang.String getReciverDetailInfo() {
		return reciverDetailInfo;
	}
	public void setReciverDetailInfo(java.lang.String reciverDetailInfo) {
		this.reciverDetailInfo = reciverDetailInfo;
	}
	public java.lang.String getOrderMessage() {
		return orderMessage;
	}
	public void setOrderMessage(java.lang.String orderMessage) {
		this.orderMessage = orderMessage;
	}
	public java.lang.String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(java.lang.String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getPayNo() {
		return payNo;
	}
	public void setPayNo(java.lang.String payNo) {
		this.payNo = payNo;
	}
	
    
}
