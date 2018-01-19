package com.buss.job.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 每日任务统计
 * @author onlineGenerator
 * @date 2016-11-04 18:25:40
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class TJobGrowVo implements java.io.Serializable {
	private java.lang.String id;
	private java.lang.String userId;

	@Excel(name="导购",width=20)
	private java.lang.String guideName;
	@Excel(name="所属导购",width=20)
	private java.lang.String storeName;
	@Excel(name="商品发布数",width=20)
	private java.math.BigDecimal recomendGuideSum;
	@Excel(name="成交总量",width=20)
	private java.math.BigDecimal payOrderSum;
	@Excel(name="推送总量",width=20)
	private java.math.BigDecimal recomendSum;
	@Excel(name="管家点评总量",width=20)
	private java.math.BigDecimal goodsSum;
	@Excel(name="顾客资料完整度达到80%顾客数",width=20)
	private java.math.BigDecimal bindingSum;
	private java.lang.String storeId;
	private java.lang.String  toRetailerId;
	
	public java.lang.String getStoreId() {
		return storeId;
	}
	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getUserId() {
		return userId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	public java.lang.String getStoreName() {
		return storeName;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	public java.lang.String getGuideName() {
		return guideName;
	}
	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	public java.math.BigDecimal getRecomendGuideSum() {
		return recomendGuideSum;
	}
	public void setRecomendGuideSum(java.math.BigDecimal recomendGuideSum) {
		this.recomendGuideSum = recomendGuideSum;
	}
	public java.math.BigDecimal getPayOrderSum() {
		return payOrderSum;
	}
	public void setPayOrderSum(java.math.BigDecimal payOrderSum) {
		this.payOrderSum = payOrderSum;
	}
	public java.math.BigDecimal getRecomendSum() {
		return recomendSum;
	}
	public void setRecomendSum(java.math.BigDecimal recomendSum) {
		this.recomendSum = recomendSum;
	}
	public java.math.BigDecimal getGoodsSum() {
		return goodsSum;
	}
	public void setGoodsSum(java.math.BigDecimal goodsSum) {
		this.goodsSum = goodsSum;
	}
	public java.math.BigDecimal getBindingSum() {
		return bindingSum;
	}
	public void setBindingSum(java.math.BigDecimal bindingSum) {
		this.bindingSum = bindingSum;
	}
	public java.lang.String getToRetailerId() {
		return toRetailerId;
	}
	public void setToRetailerId(java.lang.String toRetailerId) {
		this.toRetailerId = toRetailerId;
	}
}
