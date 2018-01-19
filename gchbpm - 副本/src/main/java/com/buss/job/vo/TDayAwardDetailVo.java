package com.buss.job.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 每日奖励明细
 * @author onlineGenerator
 * @date 2016-11-04 18:25:27
 * @version V1.0   
 *
 */
public class TDayAwardDetailVo implements java.io.Serializable {
	/**任务类型*/
	@Excel(name="任务类型",replace={"每日任务_1","成长任务_2"})
	private java.lang.String jobType;
//	/**任务编码*/
//	@Excel(name="任务名称",replace={"推送总量_recomendSum","顾客资料完整度达到80%顾客数_bindingSum","管家点评总量_goodsSum","商品发布数_recomendGuideSum","成交总量_payOrderSum"},width=20)
//	private java.lang.String jobCode;
	/**任务名称*/
	@Excel(name="任务名称")
	private java.lang.String title;
	/**领取金币数*/
	@Excel(name="领取金币数")
	private java.lang.Integer goldNum;
	/**领金币时间*/
	@Excel(name="领金币时间",format="yyyy-MM-dd",width=20)
	private java.util.Date jobDate;
	/**导购userid*/
	@Excel(name="导购")
	private java.lang.String guideName;
	/**零售商*/
	@Excel(name="零售商")
	private java.lang.String retailerName;
	public java.lang.String getJobType() {
		return jobType;
	}
	public void setJobType(java.lang.String jobType) {
		this.jobType = jobType;
	}
//	public java.lang.String getJobCode() {
//		return jobCode;
//	}
//	public void setJobCode(java.lang.String jobCode) {
//		this.jobCode = jobCode;
//	}
	public java.lang.String getTitle() {
		return title;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	public java.lang.Integer getGoldNum() {
		return goldNum;
	}
	public void setGoldNum(java.lang.Integer goldNum) {
		this.goldNum = goldNum;
	}
	public java.util.Date getJobDate() {
		return jobDate;
	}
	public void setJobDate(java.util.Date jobDate) {
		this.jobDate = jobDate;
	}
	public java.lang.String getGuideName() {
		return guideName;
	}
	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	public java.lang.String getRetailerName() {
		return retailerName;
	}
	public void setRetailerName(java.lang.String retailerName) {
		this.retailerName = retailerName;
	}
	
	
}
