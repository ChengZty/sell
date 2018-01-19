package com.buss.job.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 每日任务明细
 * @author onlineGenerator
 * @date 2016-11-04 18:25:40
 * @version V1.0   
 *
 */
public class TDayJobDetailTotalVo implements java.io.Serializable {
//	/**目标数量*/
	private java.lang.String id;

	@Excel(name="导购",width=20)
	private java.lang.String guideName;
	@Excel(name="导购所属店铺",width=20)
	private java.lang.String storeName;
	@Excel(name="每日推送",width=20)
	private java.math.BigDecimal recomendNum;
	@Excel(name="每日发布",width=20)
	private java.math.BigDecimal releaseNum;
	@Excel(name="每日完善",width=20)
	private java.math.BigDecimal customerNum;
	@Excel(name="每日成交",width=20)
	private java.math.BigDecimal payOrderNum;
//	/**任务日期*/
	@Excel(name="任务日期",format="yyyy-MM-dd",width=20)
	private java.util.Date jobDate;
	private java.lang.String  toRetailerId;

	private java.lang.String storeId;
	public java.lang.String getStoreId() {
		return storeId;
	}
	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	

	public java.util.Date getJobDate() {
		return jobDate;
	}
	public void setJobDate(java.util.Date jobDate) {
		this.jobDate = jobDate;
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
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.math.BigDecimal getRecomendNum() {
		return recomendNum;
	}
	public void setRecomendNum(java.math.BigDecimal recomendNum) {
		this.recomendNum = recomendNum;
	}
	public java.math.BigDecimal getReleaseNum() {
		return releaseNum;
	}
	public void setReleaseNum(java.math.BigDecimal releaseNum) {
		this.releaseNum = releaseNum;
	}
	public java.math.BigDecimal getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(java.math.BigDecimal customerNum) {
		this.customerNum = customerNum;
	}
	public java.math.BigDecimal getPayOrderNum() {
		return payOrderNum;
	}
	public void setPayOrderNum(java.math.BigDecimal payOrderNum) {
		this.payOrderNum = payOrderNum;
	}
	public java.lang.String getToRetailerId() {
		return toRetailerId;
	}
	public void setToRetailerId(java.lang.String toRetailerId) {
		this.toRetailerId = toRetailerId;
	}
	
}
