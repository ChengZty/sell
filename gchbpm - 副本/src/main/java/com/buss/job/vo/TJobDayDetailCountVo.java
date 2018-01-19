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
@SuppressWarnings("serial")
public class TJobDayDetailCountVo implements java.io.Serializable {
	private java.lang.String id;
	
	/**任务查询日期*/
	@Excel(name="日期",width=20)
	private java.lang.String searchTime;
	/**任务周期*/
	@Excel(name="任务周期",width=40)
	private  java.lang.String  jobDate;
	/**店铺名称*/
	@Excel(name="店铺名称",width=20)
	private java.lang.String storeName;
	/**导购名称*/
	@Excel(name="导购名称",width=20)
	private java.lang.String guideName;
	/**导购手机号*/
	private java.lang.String mobilePhone;
	/**任务组名称*/
	@Excel(name="任务组名称",width=20)
	private java.lang.String modelName;
	/**任务名称*/
	@Excel(name="任务名称",width=20)
	private java.lang.String jobTitle;
	/**任务名称*/
	@Excel(name="任务目标数",width=20)
	private java.lang.String jobNum;
	/**任务名称*/
	@Excel(name="当日任务完成数",width=20)
	private java.lang.Long myDayPace;
	/**任务名称*/
	@Excel(name="累计任务完成数",width=20)
	private java.lang.Long pace;
	/**任务名称*/
	@Excel(name="累计任务完成率",width=20)
	private java.lang.String myPercent;
	@Excel(name="任务完成状态",width=20)
	private java.lang.String paceType;
	
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(java.lang.String searchTime) {
		this.searchTime = searchTime;
	}
	public java.lang.String getJobDate() {
		return jobDate;
	}
	public void setJobDate(java.lang.String jobDate) {
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
	public java.lang.String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(java.lang.String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public java.lang.String getModelName() {
		return modelName;
	}
	public void setModelName(java.lang.String modelName) {
		this.modelName = modelName;
	}
	public java.lang.String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(java.lang.String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public java.lang.String getJobNum() {
		return jobNum;
	}
	public void setJobNum(java.lang.String jobNum) {
		this.jobNum = jobNum;
	}
	public java.lang.Long getMyDayPace() {
		return myDayPace;
	}
	public void setMyDayPace(java.lang.Long myDayPace) {
		this.myDayPace = myDayPace;
	}
	public java.lang.Long getPace() {
		return pace;
	}
	public void setPace(java.lang.Long pace) {
		this.pace = pace;
	}
	public void setPaceType(java.lang.String paceType) {
		this.paceType = paceType;
	}
	public java.lang.String getMyPercent() {
		return myPercent;
	}
	public void setMyPercent(java.lang.String myPercent) {
		this.myPercent = myPercent;
	}
	public java.lang.String getPaceType() {
		if(Long.valueOf(jobNum) <= Long.valueOf(pace)){
			paceType = "已完成";
		}else if(Long.valueOf(jobNum)  >   Long.valueOf(pace)){
			paceType = "未完成";
		}else{
			paceType = "";
		}
		return paceType;
	}
}
