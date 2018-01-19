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
public class TDayJobDetailVo implements java.io.Serializable {
//	/**任务编码*/
//	@Excel(name="任务名称",replace={"每日推送_dayRecommend","每日发布_dayPublish","每日完善_dayCustomer","每日推荐_dayFirst","每日成交_dayPay"},width=20)
//	private java.lang.String jobCode;
	/**任务名称*/
	@Excel(name="任务名称",width=20)
	private java.lang.String title;
	/**完成数量*/
	@Excel(name="完成数量")
	private java.lang.Integer finishedNum;
//	/**目标数量*/
//	@Excel(name="目标数量")
//	private java.lang.Integer targetNum;
//	/**任务日期*/
	@Excel(name="任务日期",format="yyyy-MM-dd",width=20)
	private java.util.Date jobDate;
	/**导购userid*/
	@Excel(name="导购")
	private java.lang.String guideName;
	/**零售商id*/
	@Excel(name="零售商")
	private java.lang.String retailerName;
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
	public java.lang.Integer getFinishedNum() {
		return finishedNum;
	}
	public void setFinishedNum(java.lang.Integer finishedNum) {
		this.finishedNum = finishedNum;
	}
//	public java.lang.Integer getTargetNum() {
//		return targetNum;
//	}
//	public void setTargetNum(java.lang.Integer targetNum) {
//		this.targetNum = targetNum;
//	}
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
