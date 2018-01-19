package com.buss.order.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class TOrderDetailVo implements java.io.Serializable{
	private java.lang.String id;
	private java.lang.String flag;
	private java.lang.String goodsPic;
	/**主单ID*/
	@Excel(name="主单ID",width=35,mergeRely={1},mergeVertical=true)
	private java.lang.String orderId;
	/**主单号*/
	@Excel(name="主单号",width=20,mergeRely={1},mergeVertical=true)
	private java.lang.String orderNo;
	/**商品名称*/
	@Excel(name="商品名称",width=30)
	private java.lang.String goodsName;
	/**款号*/
	@Excel(name="款号",width=20)
	private java.lang.String goodsCode;
	/**规格*/
	@Excel(name="规格",width=20)
	private java.lang.String specInfo;
	/**原价*/
	private java.math.BigDecimal priceOriginal;
	/**现价*/
	private java.math.BigDecimal currentPrice;
	/**结算价(单价)*/
	@Excel(name="售价",width=15)
	private java.math.BigDecimal priceNow;
	/**件数*/
	@Excel(name="数量")
	private java.math.BigDecimal quantity;
	/**运费*/
	private java.math.BigDecimal fare;
	/**商品总价*/
	@Excel(name="商品金额")
	private java.math.BigDecimal goodsAmount;
	/**订单总额*/
	@Excel(name="订单总额",mergeRely={1},mergeVertical=true)
	private java.math.BigDecimal orderAmount;
	/**优惠价*/
	@Excel(name="优惠金额",mergeRely={1},mergeVertical=true)
	private java.math.BigDecimal ticketPreferential;
	/**实付总额*/
	@Excel(name="实付总额",mergeRely={1},mergeVertical=true)
	private java.math.BigDecimal payAmount;
	/**现金支付总额*/
//	@Excel(name="现金支付总额")
	private java.math.BigDecimal salesPrice;
	/**G+卡付款总额*/
//	@Excel(name="G+卡付款总额")
	private java.math.BigDecimal vipMoneyPay;
	/**买家电话*/
	@Excel(name="买家电话",width=15,mergeRely={1},mergeVertical=true)
	private java.lang.String userPhone;
    /**下单时间*/
    @Excel(name="下单时间",format="yyyy-MM-dd HH:mm:ss",width=20,mergeRely={1},mergeVertical=true)
	private java.util.Date addTime;
    /**付款时间*/
    @Excel(name="付款时间",format="yyyy-MM-dd HH:mm:ss",width=20,mergeRely={1},mergeVertical=true)
    private java.util.Date payTime;
    /**支付方式*/
    @Excel(name="支付方式",mergeRely={1},mergeVertical=true)
	private java.lang.String payMethod;
    /**流水号*/
	@Excel(name="流水号",width=20,mergeRely={1},mergeVertical=true)
	private java.lang.String payNo;
	/**收货人*/
	@Excel(name="收货人",mergeRely={1},mergeVertical=true)
	private java.lang.String reciverName;
	/**收货人电话*/
	@Excel(name="收货人电话",width=15,mergeRely={1},mergeVertical=true)
	private java.lang.String reciverPhone;
	/**收货地址*/
	@Excel(name="收货地址",width=30,mergeRely={1},mergeVertical=true)
	private java.lang.String reciverDetailInfo;
    
    /**买家留言*/
	@Excel(name="买家留言",width=20,mergeRely={1},mergeVertical=true)
	private java.lang.String orderMessage;
	/**运送方式*/
	private java.lang.String deliveryType;
	/**物流名称*/
	@Excel(name="物流名称",width=15,mergeRely={1},mergeVertical=true)
	private java.lang.String deliveryName;
	/**快递单号*/
	@Excel(name="快递单号",width=15,mergeRely={1},mergeVertical=true)
	private java.lang.String deliveryNo;
	/**导购姓名*/
	@Excel(name="导购姓名",width=15,mergeRely={1},mergeVertical=true)
	private java.lang.String guideName;
	/**导购手机*/
	@Excel(name="导购手机",width=15,mergeRely={1},mergeVertical=true)
	private java.lang.String guidePhone;
	/**店铺*/
	@Excel(name="店铺",width=20,mergeRely={1},mergeVertical=true)
	private java.lang.String storeName;
	/**零售商ID*/
	@Excel(name="零售商ID",width=35,mergeRely={1},mergeVertical=true)
	private java.lang.String toRetailerId;
	/**零售商名称*/
	@Excel(name="零售商名称",width=15,mergeRely={1},mergeVertical=true)
	private java.lang.String retailerName;
	/**订单状态*/
	@Excel(name="订单状态",replace={"待付款_1","待发货_2","已发货_3","已完成_4","已取消_9","已退款_8"},mergeRely={1},mergeVertical=true)
	private java.lang.String orderStatus;
	public java.lang.String getOrderId() {
		return orderId;
	}
	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}
	public java.lang.String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	public java.lang.String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public java.lang.String getSpecInfo() {
		return specInfo;
	}
	public void setSpecInfo(java.lang.String specInfo) {
		this.specInfo = specInfo;
	}
	public java.math.BigDecimal getPriceOriginal() {
		return priceOriginal;
	}
	public void setPriceOriginal(java.math.BigDecimal priceOriginal) {
		this.priceOriginal = priceOriginal;
	}
	public java.math.BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(java.math.BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public java.math.BigDecimal getPriceNow() {
		return priceNow;
	}
	public void setPriceNow(java.math.BigDecimal priceNow) {
		this.priceNow = priceNow;
	}
	public java.math.BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(java.math.BigDecimal quantity) {
		this.quantity = quantity;
	}
	public java.math.BigDecimal getFare() {
		return fare;
	}
	public void setFare(java.math.BigDecimal fare) {
		this.fare = fare;
	}
	public java.math.BigDecimal getGoodsAmount() {
		return goodsAmount;
	}
	public void setGoodsAmount(java.math.BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	public java.math.BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(java.math.BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public java.math.BigDecimal getTicketPreferential() {
		return ticketPreferential;
	}
	public void setTicketPreferential(java.math.BigDecimal ticketPreferential) {
		this.ticketPreferential = ticketPreferential;
	}
	public java.math.BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(java.math.BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public java.math.BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(java.math.BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	public java.math.BigDecimal getVipMoneyPay() {
		return vipMoneyPay;
	}
	public void setVipMoneyPay(java.math.BigDecimal vipMoneyPay) {
		this.vipMoneyPay = vipMoneyPay;
	}
	public java.lang.String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(java.lang.String userPhone) {
		this.userPhone = userPhone;
	}
	public java.util.Date getAddTime() {
		return addTime;
	}
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	public java.lang.String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(java.lang.String payMethod) {
		this.payMethod = payMethod;
	}
	public java.lang.String getPayNo() {
		return payNo;
	}
	public void setPayNo(java.lang.String payNo) {
		this.payNo = payNo;
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
	public java.lang.String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(java.lang.String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public java.lang.String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(java.lang.String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public java.lang.String getDeliveryNo() {
		return deliveryNo;
	}
	public void setDeliveryNo(java.lang.String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	public java.lang.String getToRetailerId() {
		return toRetailerId;
	}
	public void setToRetailerId(java.lang.String toRetailerId) {
		this.toRetailerId = toRetailerId;
	}
	public java.lang.String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(java.lang.String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public java.lang.String getGuideName() {
		return guideName;
	}
	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	public java.lang.String getGuidePhone() {
		return guidePhone;
	}
	public void setGuidePhone(java.lang.String guidePhone) {
		this.guidePhone = guidePhone;
	}
	public java.lang.String getStoreName() {
		return storeName;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getFlag() {
		return flag;
	}
	public void setFlag(java.lang.String flag) {
		this.flag = flag;
	}
	public java.lang.String getGoodsPic() {
		return goodsPic;
	}
	public void setGoodsPic(java.lang.String goodsPic) {
		this.goodsPic = goodsPic;
	}
	public java.lang.String getRetailerName() {
		return retailerName;
	}
	public void setRetailerName(java.lang.String retailerName) {
		this.retailerName = retailerName;
	}
    
}
