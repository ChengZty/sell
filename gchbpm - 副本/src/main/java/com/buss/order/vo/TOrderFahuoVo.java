package com.buss.order.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class TOrderFahuoVo implements java.io.Serializable{

	/**主单号*/
	@Excel(name="单号",width=20,mergeRely={0},mergeVertical=true)
	private java.lang.String orderNo;
	/**子订单号*/
//	@Excel(name="子订单号",width=20)
//	private java.lang.String subOrderNo;
	/**商品名称*/
	@Excel(name="商品名称",width=30)
	private java.lang.String goodsName;
	/**款号*/
	@Excel(name="款号",width=20)
	private java.lang.String goodsCode;
	/**规格*/
	@Excel(name="规格")
	private java.lang.String specInfo;
	/**件数*/
	@Excel(name="件数")
	private java.math.BigDecimal quantity;
	/**收货人*/
	@Excel(name="收货人",mergeRely={0},mergeVertical=true)
	private java.lang.String reciverName;
	/**收货人电话*/
	@Excel(name="收货人电话",width=15,mergeRely={0},mergeVertical=true)
	private java.lang.String reciverPhone;
	/**收货地址*/
	@Excel(name="收货地址",width=30,mergeRely={0},mergeVertical=true)
	private java.lang.String reciverDetailInfo;
    /**下单时间*/
	@Excel(name="下单时间",format="yyyy-MM-dd HH:mm:ss",width=20,mergeRely={0},mergeVertical=true)
	private java.util.Date addTime;
	/**支付时间*/
	@Excel(name="支付时间",format="yyyy-MM-dd HH:mm:ss",width=20,mergeRely={0},mergeVertical=true)
	private java.util.Date payTime;
    /**买家电话*/
	@Excel(name="买家电话",width=15,mergeRely={0},mergeVertical=true)
	private java.lang.String userPhone;
    /**订单留言*/
	@Excel(name="订单留言",width=20,mergeRely={0},mergeVertical=true)
	private java.lang.String orderMessage;
	public java.lang.String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
//	public java.lang.String getSubOrderNo() {
//		return subOrderNo;
//	}
//	public void setSubOrderNo(java.lang.String subOrderNo) {
//		this.subOrderNo = subOrderNo;
//	}
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
	public java.math.BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(java.math.BigDecimal quantity) {
		this.quantity = quantity;
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
	public java.util.Date getAddTime() {
		return addTime;
	}
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	public java.lang.String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(java.lang.String userPhone) {
		this.userPhone = userPhone;
	}
	public java.lang.String getOrderMessage() {
		return orderMessage;
	}
	public void setOrderMessage(java.lang.String orderMessage) {
		this.orderMessage = orderMessage;
	}
	public java.util.Date getPayTime() {
		return payTime;
	}
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	
	
}
