package com.buss.order.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class TOrderDeliveryVo implements java.io.Serializable{

	/**订单号*/
	@Excel(name="订单号",width=20)
	private java.lang.String orderNo;
	/**物流类型*/
	private java.lang.String deliveryType;
	/**物流公司*/
	@Excel(name="物流公司",width=20)
	private java.lang.String deliveryName;
	/**快递单号*/
	@Excel(name="物流单号",width=20)
	private java.lang.String deliveryNo;
	public java.lang.String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
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
	public java.lang.String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(java.lang.String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	
	
}
