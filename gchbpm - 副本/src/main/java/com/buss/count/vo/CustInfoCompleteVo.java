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
public class CustInfoCompleteVo implements java.io.Serializable {
	/**导购Id*/
	private java.lang.String guideId;
	/**导购名称*/
	@Excel(name="导购姓名",width=16)
	private java.lang.String guideName;
	/**店铺Id*/
	@Excel(name="店铺",width=20)
	private java.lang.String storeId;
	/**顾客总数*/
	@Excel(name="顾客总数",width=10)
	private java.lang.Long totalNum;
	/**0%*/
	@Excel(name="0%",width=10)
	private java.lang.Long p0 = 0L;
	/**10%*/
	@Excel(name="10%",width=10)
	private java.lang.Long p10 = 0L;
	/**20%*/
	@Excel(name="20%",width=10)
	private java.lang.Long p20 = 0L;
	/**30%*/
	@Excel(name="30%",width=10)
	private java.lang.Long p30 = 0L;
	/**40%*/
	@Excel(name="40%",width=10)
	private java.lang.Long p40 = 0L;
	/**50%*/
	@Excel(name="50%",width=10)
	private java.lang.Long p50 = 0L;
	/**60%*/
	@Excel(name="60%",width=10)
	private java.lang.Long p60 = 0L;
	/**70%*/
	@Excel(name="70%",width=10)
	private java.lang.Long p70 = 0L;
	/**80%*/
	@Excel(name="80%",width=10)
	private java.lang.Long p80 = 0L;
	/**90%*/
	@Excel(name="90%",width=10)
	private java.lang.Long p90 = 0L;
	/**100%*/
	@Excel(name="100%",width=10)
	private java.lang.Long p100 = 0L;
	public java.lang.String getGuideId() {
		return guideId;
	}
	public void setGuideId(java.lang.String guideId) {
		this.guideId = guideId;
	}
	public java.lang.String getGuideName() {
		return guideName;
	}
	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	public java.lang.String getStoreId() {
		return storeId;
	}
	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	public java.lang.Long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(java.lang.Long totalNum) {
		this.totalNum = totalNum;
	}
	public java.lang.Long getP0() {
		return p0;
	}
	public void setP0(java.lang.Long p0) {
		this.p0 = p0;
	}
	public java.lang.Long getP10() {
		return p10;
	}
	public void setP10(java.lang.Long p10) {
		this.p10 = p10;
	}
	public java.lang.Long getP20() {
		return p20;
	}
	public void setP20(java.lang.Long p20) {
		this.p20 = p20;
	}
	public java.lang.Long getP30() {
		return p30;
	}
	public void setP30(java.lang.Long p30) {
		this.p30 = p30;
	}
	public java.lang.Long getP40() {
		return p40;
	}
	public void setP40(java.lang.Long p40) {
		this.p40 = p40;
	}
	public java.lang.Long getP50() {
		return p50;
	}
	public void setP50(java.lang.Long p50) {
		this.p50 = p50;
	}
	public java.lang.Long getP60() {
		return p60;
	}
	public void setP60(java.lang.Long p60) {
		this.p60 = p60;
	}
	public java.lang.Long getP70() {
		return p70;
	}
	public void setP70(java.lang.Long p70) {
		this.p70 = p70;
	}
	public java.lang.Long getP80() {
		return p80;
	}
	public void setP80(java.lang.Long p80) {
		this.p80 = p80;
	}
	public java.lang.Long getP90() {
		return p90;
	}
	public void setP90(java.lang.Long p90) {
		this.p90 = p90;
	}
	public java.lang.Long getP100() {
		return p100;
	}
	public void setP100(java.lang.Long p100) {
		this.p100 = p100;
	}
}
