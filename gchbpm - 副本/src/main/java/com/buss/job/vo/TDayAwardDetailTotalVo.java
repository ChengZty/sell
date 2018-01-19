package com.buss.job.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 每日奖励明细
 * @author onlineGenerator
 * @date 2016-11-04 18:25:40
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class TDayAwardDetailTotalVo implements java.io.Serializable {
	private java.lang.String id;
	private java.lang.String userId;

	@Excel(name="导购",width=20)
	private java.lang.String guideName;
	@Excel(name="所属店铺",width=20)
	private java.lang.String storeName;
	@Excel(name="金币总量",width=20)
	private java.math.BigDecimal goldTotal;
	@Excel(name="每日完善",width=20)
	private java.math.BigDecimal dayCustomer;
	@Excel(name="每日点评",width=20)
	private java.math.BigDecimal dayFirst;
	@Excel(name="每日发布",width=20)
	private java.math.BigDecimal dayPublish;
	@Excel(name="每日推送",width=20)
	private java.math.BigDecimal dayRecommend;
	@Excel(name="顾客资料完整度达到80%顾客数",width=20)
	private java.math.BigDecimal bindingSum;
	@Excel(name="管家点评总量",width=20)
	private java.math.BigDecimal goodsSum;
	@Excel(name="商品发布数",width=20)
	private java.math.BigDecimal recomendGuideSum;
	@Excel(name="推送总量",width=20)
	private java.math.BigDecimal recomendSum;
	/**任务日期*/
	@Excel(name="任务日期",width=20,format="yyyy-MM-dd")
	private java.util.Date jobDate;
	private java.lang.String  toRetailerId;

	private java.lang.String storeId;
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
	public java.math.BigDecimal getGoldTotal() {
		return goldTotal;
	}
	public void setGoldTotal(java.math.BigDecimal goldTotal) {
		this.goldTotal = goldTotal;
	}
	public java.math.BigDecimal getDayCustomer() {
		return dayCustomer;
	}
	public void setDayCustomer(java.math.BigDecimal dayCustomer) {
		this.dayCustomer = dayCustomer;
	}
	public java.math.BigDecimal getDayFirst() {
		return dayFirst;
	}
	public void setDayFirst(java.math.BigDecimal dayFirst) {
		this.dayFirst = dayFirst;
	}
	public java.math.BigDecimal getDayPublish() {
		return dayPublish;
	}
	public void setDayPublish(java.math.BigDecimal dayPublish) {
		this.dayPublish = dayPublish;
	}
	public java.math.BigDecimal getDayRecommend() {
		return dayRecommend;
	}
	public void setDayRecommend(java.math.BigDecimal dayRecommend) {
		this.dayRecommend = dayRecommend;
	}
	public java.math.BigDecimal getBindingSum() {
		return bindingSum;
	}
	public void setBindingSum(java.math.BigDecimal bindingSum) {
		this.bindingSum = bindingSum;
	}
	public java.math.BigDecimal getGoodsSum() {
		return goodsSum;
	}
	public void setGoodsSum(java.math.BigDecimal goodsSum) {
		this.goodsSum = goodsSum;
	}
	public java.math.BigDecimal getRecomendGuideSum() {
		return recomendGuideSum;
	}
	public void setRecomendGuideSum(java.math.BigDecimal recomendGuideSum) {
		this.recomendGuideSum = recomendGuideSum;
	}
	public java.math.BigDecimal getRecomendSum() {
		return recomendSum;
	}
	public void setRecomendSum(java.math.BigDecimal recomendSum) {
		this.recomendSum = recomendSum;
	}
	public java.util.Date getJobDate() {
		return jobDate;
	}
	public void setJobDate(java.util.Date jobDate) {
		this.jobDate = jobDate;
	}
	public java.lang.String getToRetailerId() {
		return toRetailerId;
	}
	public void setToRetailerId(java.lang.String toRetailerId) {
		this.toRetailerId = toRetailerId;
	}
	
}
