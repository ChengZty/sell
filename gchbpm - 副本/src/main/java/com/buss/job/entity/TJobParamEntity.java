package com.buss.job.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: t_job
 * @author onlineGenerator
 * @date 2016-11-05 11:54:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_job_param", schema = "")
@Where(clause="status = 'A'")
@SuppressWarnings("serial")
public class TJobParamEntity implements java.io.Serializable {
	public static final String JOB_PARAM_CYCLE_YES = "1";//循环
	public static final String JOB_PARAM_CYCLE_NO = "0";//不循环
	
	/**id*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**状态*/
	private java.lang.String status;
	/**任务id */
	@Excel(name="任务id ")
	private java.lang.String jobId;
	/**任务编码*/
	@Excel(name="任务名称")
	private java.lang.String jobTitle;
	/**任务描述*/
	@Excel(name="任务描述")
	private java.lang.String jobDescription;
	/**任务组ID*/
	@Excel(name="任务组ID")
	private java.lang.String modelId;
	/**任务是否循环*/
	@Excel(name="任务是否循环")
	private java.lang.String cycle;
	/**任务组ID*/
	@Excel(name="任务周期")
	private java.lang.String dayTime;
	/**开始时间*/
	@Excel(name="开始时间")
	private java.util.Date startTime;
	/**结束时间*/
	@Excel(name="结束时间")
	private java.util.Date endTime;
	/**任务组ID*/
	@Excel(name="任务完成次数")
	private java.lang.String jobNum;
	/**任务组ID*/
	@Excel(name="金币数")
	private java.lang.String goldNum;
	/**任务组ID*/
	@Excel(name="领金币的次数")
	private java.lang.String goldTime;
	/**任务组ID*/
	@Excel(name="处罚金币数")
	private java.lang.String punish;
	/**任务组ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**任务标题*/
	private java.lang.String regulation;
	/**任务场景*/
	private java.lang.String jobSceneCode;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}

	@Column(name ="JOB_ID",nullable=true,length=36)
	public java.lang.String getJobId() {
		return jobId;
	}

	public void setJobId(java.lang.String jobId) {
		this.jobId = jobId;
	}

	@Column(name ="JOB_TITLE",nullable=true,length=36)
	public java.lang.String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(java.lang.String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name ="JOB_DESCRIPTION",nullable=true,length=300)
	public java.lang.String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(java.lang.String jobDescription) {
		this.jobDescription = jobDescription;
	}

	@Column(name ="MODEL_ID",nullable=true,length=36)
	public java.lang.String getModelId() {
		return modelId;
	}

	public void setModelId(java.lang.String modelId) {
		this.modelId = modelId;
	}

	@Column(name ="CYCLE",nullable=true,length=1)
	public java.lang.String getCycle() {
		return cycle;
	}

	public void setCycle(java.lang.String cycle) {
		this.cycle = cycle;
	}

	@Column(name ="DAY_TIME",nullable=true,length=5)
	public java.lang.String getDayTime() {
		return dayTime;
	}

	public void setDayTime(java.lang.String dayTime) {
		this.dayTime = dayTime;
	}

	@Column(name ="START_TIME",nullable=true)
	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	@Column(name ="END_TIME",nullable=true)
	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	@Column(name ="JOB_NUM",nullable=true,length=5)
	public java.lang.String getJobNum() {
		return jobNum;
	}

	public void setJobNum(java.lang.String jobNum) {
		this.jobNum = jobNum;
	}

	@Column(name ="GOLD_NUM",nullable=true,length=5)
	public java.lang.String getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(java.lang.String goldNum) {
		this.goldNum = goldNum;
	}

	@Column(name ="GOLD_TIME",nullable=true,length=5)
	public java.lang.String getGoldTime() {
		return goldTime;
	}

	public void setGoldTime(java.lang.String goldTime) {
		this.goldTime = goldTime;
	}

	@Column(name ="PUNISH",nullable=true,length=5)
	public java.lang.String getPunish() {
		return punish;
	}

	public void setPunish(java.lang.String punish) {
		this.punish = punish;
	}

	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}

	@Transient
	public java.lang.String getRegulation() {
		return regulation;
	}

	public void setRegulation(java.lang.String regulation) {
		this.regulation = regulation;
	}

	@Transient
	public java.lang.String getJobSceneCode() {
		return jobSceneCode;
	}

	public void setJobSceneCode(java.lang.String jobSceneCode) {
		this.jobSceneCode = jobSceneCode;
	}

}
