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
public class TJobDetailCountVo implements java.io.Serializable {
	private java.lang.String id;
	
	/**创建人名称*/
	@Excel(name="任务日期",width=25)
	private java.lang.String jobDate;
	/**创建人登录名称*/
	@Excel(name="任务组名称",width=20)
	private java.lang.String modelName;
	/**更新人名称*/
	@Excel(name="任务名称",width=20)
	private java.lang.String jobTitle;
	/**更新人登录名称*/
	@Excel(name="任务内容",width=20)
	private java.lang.String jobDescription;
	/**状态*/
	private java.lang.String storeId;
	/**状态*/
	@Excel(name="店铺名称",width=20)
	private java.lang.String storeName;
	
	/**任务参数id*/
	@Excel(name="导购名称",width=20)
	private java.lang.String guideName;
	/**零售商id*/
	@Excel(name="导购手机号",width=20)
	private java.lang.String mobilePhone;
	/**店铺id*/
	@Excel(name="任务完成进度",width=20)
	private java.lang.String pace;
	/**进度类型 1完成， 0未完成*/
	@Excel(name="任务完成情况",width=20)
	private java.lang.String paceType;
	/**获得的金币总数*/
	@Excel(name="领取金币",width=20)
	private java.lang.Integer goldTotal;
	/**扣除金币数*/
	@Excel(name="罚除金币",width=20)
	private java.lang.Long goldSub;
	/**当前导购金币数*/
	@Excel(name="金币数量",width=20)
	private java.lang.Integer goldPerson;
	
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getJobDate() {
		return jobDate;
	}
	public void setJobDate(java.lang.String jobDate) {
		this.jobDate = jobDate;
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
	public java.lang.String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(java.lang.String jobDescription) {
		this.jobDescription = jobDescription;
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
	public java.lang.String getPace() {
		return pace;
	}
	public void setPace(java.lang.String pace) {
		this.pace = pace;
	}
	public java.lang.String getPaceType() {
		return paceType;
	}
	public void setPaceType(java.lang.String paceType) {
		this.paceType = paceType;
	}
	public java.lang.Integer getGoldTotal() {
		return goldTotal;
	}
	public void setGoldTotal(java.lang.Integer goldTotal) {
		this.goldTotal = goldTotal;
	}
	public java.lang.Long getGoldSub() {
		return goldSub;
	}
	public void setGoldSub(java.lang.Long goldSub) {
		this.goldSub = goldSub;
	}
	public java.lang.Integer getGoldPerson() {
		return goldPerson;
	}
	public void setGoldPerson(java.lang.Integer goldPerson) {
		this.goldPerson = goldPerson;
	}
	
}
