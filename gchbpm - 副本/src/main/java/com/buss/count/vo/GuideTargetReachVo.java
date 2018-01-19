package com.buss.count.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: vo
 * @Description: 导购月目标达成统计
 * @author onlineGenerator
 * @date 2016-03-14 16:00:50
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class GuideTargetReachVo implements java.io.Serializable {
	private java.lang.String id;
	/**月份*/
	@Excel(name="月份",width=10)
	private java.lang.String targetMonth;
	/**店铺id*/
	private java.lang.String storeId;
	/**店铺名称*/
	@Excel(name="店铺名称",width=15)
	private java.lang.String storeName;
	/**店铺月目标*/
	@Excel(name="店铺月目标",width=15)
	private java.math.BigDecimal storeTargetMoney;
	/**店铺实际业绩*/
	@Excel(name="店铺实际业绩",width=15)
	private java.math.BigDecimal storeTotalMoney;
	/**店铺目标达成率*/
	@Excel(name="店铺目标达成率",width=15,suffix="%")
	private java.math.BigDecimal storeReachRate;
	/**导购id*/
	private java.lang.String guideId;
	/**导购姓名*/
	@Excel(name="导购姓名",width=10)
	private java.lang.String guideName;
	/**导购月目标*/
	@Excel(name="导购月目标",width=15)
	private java.math.BigDecimal guideTargetMoney;
	/**实体店业绩*/
	@Excel(name="导购实体店业绩",width=15)
	private java.math.BigDecimal offlineMoney;
	/**线上业绩*/
	@Excel(name="导购线上业绩",width=15)
	private java.math.BigDecimal onlineMoney;
	/**导购总业绩*/
	@Excel(name="导购总业绩",width=15)
	private java.math.BigDecimal guideTotalMoney;
	/**导购目标达成率*/
	@Excel(name="导购目标达成率",width=15,suffix="%")
	private java.math.BigDecimal guideReachRate;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getTargetMonth() {
		return targetMonth;
	}
	public void setTargetMonth(java.lang.String targetMonth) {
		this.targetMonth = targetMonth;
	}
	public java.lang.String getStoreId() {
		return storeId;
	}
	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	public java.lang.String getStoreName() {
		return storeName;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	public java.math.BigDecimal getStoreTargetMoney() {
		return storeTargetMoney;
	}
	public void setStoreTargetMoney(java.math.BigDecimal storeTargetMoney) {
		this.storeTargetMoney = storeTargetMoney;
	}
	public java.math.BigDecimal getStoreTotalMoney() {
		return storeTotalMoney;
	}
	public void setStoreTotalMoney(java.math.BigDecimal storeTotalMoney) {
		this.storeTotalMoney = storeTotalMoney;
	}
	public java.math.BigDecimal getStoreReachRate() {
		return storeReachRate;
	}
	public void setStoreReachRate(java.math.BigDecimal storeReachRate) {
		this.storeReachRate = storeReachRate;
	}
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
	public java.math.BigDecimal getGuideTargetMoney() {
		return guideTargetMoney;
	}
	public void setGuideTargetMoney(java.math.BigDecimal guideTargetMoney) {
		this.guideTargetMoney = guideTargetMoney;
	}
	public java.math.BigDecimal getOfflineMoney() {
		return offlineMoney;
	}
	public void setOfflineMoney(java.math.BigDecimal offlineMoney) {
		this.offlineMoney = offlineMoney;
	}
	public java.math.BigDecimal getOnlineMoney() {
		return onlineMoney;
	}
	public void setOnlineMoney(java.math.BigDecimal onlineMoney) {
		this.onlineMoney = onlineMoney;
	}
	public java.math.BigDecimal getGuideTotalMoney() {
		return guideTotalMoney;
	}
	public void setGuideTotalMoney(java.math.BigDecimal guideTotalMoney) {
		this.guideTotalMoney = guideTotalMoney;
	}
	public java.math.BigDecimal getGuideReachRate() {
		return guideReachRate;
	}
	public void setGuideReachRate(java.math.BigDecimal guideReachRate) {
		this.guideReachRate = guideReachRate;
	}
	
}
