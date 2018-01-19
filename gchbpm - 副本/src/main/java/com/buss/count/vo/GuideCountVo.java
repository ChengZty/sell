package com.buss.count.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: vo
 * @Description: 导购统计
 * @author onlineGenerator
 * @date 2016-03-14 16:00:50
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class GuideCountVo implements java.io.Serializable {
	/**userId*/
	private java.lang.String userId;
	/**导购名称*/
	@Excel(name="导购姓名",width=16)
	private java.lang.String guideName;
	/**导购名称*/
	@Excel(name="导购所在店铺",width=20)
	private java.lang.String storeName;
	/**app点击数*/
	@Excel(name="app点击数",width=16)
	private java.lang.Long appClick;
	/**商品点击数*/
	@Excel(name="商品点击数",width=16)
	private java.lang.Long goodsClick;
	/**商品点击数*/
	@Excel(name="商品推送数",width=16)
	private java.lang.Long goodsPushNum;
	/**订单点击数*/
	@Excel(name="订单推送数",width=16)
	private java.math.BigDecimal orderPushNum;
	/**商品点击数*/
	@Excel(name="资讯点击数",width=16)
	private java.math.BigDecimal newClickNum;
	/**商品点击数*/
	@Excel(name="资讯推送数",width=16)
	private java.math.BigDecimal newsPushNum;
	/**待付款数*/
	@Excel(name="待付款数",width=16)
	private java.math.BigDecimal toBePayNum;
	/**成交单数*/
	@Excel(name="成交单数",width=16)
	private java.math.BigDecimal dealNum;
	/**成交总金额*/
	@Excel(name="成交总金额",width=16)
	private java.math.BigDecimal dealMoney;
	/**成交总件数*/
	@Excel(name="成交总件数",width=16)
	private java.math.BigDecimal quantityAmount;
	/**退款总金额*/
	@Excel(name="退款总金额",width=16)
	private java.math.BigDecimal refundAmount;
	
	public java.lang.String getUserId() {
		return userId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	public java.lang.String getGuideName() {
		return guideName;
	}
	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	public java.lang.String getStoreName() {
		return storeName;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	public java.lang.Long getAppClick() {
		return appClick;
	}
	public void setAppClick(java.lang.Long appClick) {
		this.appClick = appClick;
	}
	public java.lang.Long getGoodsClick() {
		return goodsClick;
	}
	public void setGoodsClick(java.lang.Long goodsClick) {
		this.goodsClick = goodsClick;
	}
	public java.math.BigDecimal getToBePayNum() {
		return toBePayNum;
	}
	public void setToBePayNum(java.math.BigDecimal toBePayNum) {
		this.toBePayNum = toBePayNum;
	}
	public java.math.BigDecimal getDealNum() {
		return dealNum;
	}
	public void setDealNum(java.math.BigDecimal dealNum) {
		this.dealNum = dealNum;
	}
	public java.lang.Long getGoodsPushNum() {
		return goodsPushNum;
	}
	public void setGoodsPushNum(java.lang.Long goodsPushNum) {
		this.goodsPushNum = goodsPushNum;
	}
	public java.math.BigDecimal getNewClickNum() {
		return newClickNum;
	}
	public void setNewClickNum(java.math.BigDecimal newClickNum) {
		this.newClickNum = newClickNum;
	}
	public java.math.BigDecimal getNewsPushNum() {
		return newsPushNum;
	}
	public void setNewsPushNum(java.math.BigDecimal newsPushNum) {
		this.newsPushNum = newsPushNum;
	}
	public java.math.BigDecimal getDealMoney() {
		return dealMoney;
	}
	public void setDealMoney(java.math.BigDecimal dealMoney) {
		this.dealMoney = dealMoney;
	}
	public java.math.BigDecimal getQuantityAmount() {
		return quantityAmount;
	}
	public void setQuantityAmount(java.math.BigDecimal quantityAmount) {
		this.quantityAmount = quantityAmount;
	}
	public java.math.BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(java.math.BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public java.math.BigDecimal getOrderPushNum() {
		return orderPushNum;
	}
	public void setOrderPushNum(java.math.BigDecimal orderPushNum) {
		this.orderPushNum = orderPushNum;
	}
	
}
