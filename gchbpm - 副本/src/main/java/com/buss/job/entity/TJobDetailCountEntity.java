package com.buss.job.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 每日任务明细
 * @author onlineGenerator
 * @date 2016-11-04 18:25:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_job_detail_count", schema = "")
@Where(clause="status = 'A'")
@SuppressWarnings("serial")
public class TJobDetailCountEntity implements java.io.Serializable {
	public static final String JOB_COUNT_CYCLE_YES = "1";//循环
	public static final String JOB_COUNT_CYCLE_NO = "0";//不循环
	public static final String JOB_COUNT_PACE_OK = "1";//任务完成
	public static final String JOB_COUNT_PACE_NO = "0";//任务未完成
	public static final String JOB_COUNT_FINISH_OK = "1";//统计结束
	public static final String JOB_COUNT_FINISH_NO = "0";//统计未结束

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
	
	/**任务参数id*/
	private java.lang.String paramId;
	/**零售商id*/
	private java.lang.String retailerId;
	/**店铺id*/
	private java.lang.String storeId;
	/**导购userid*/
	private java.lang.String guideId;
	/**任务开始日期*/
	private java.util.Date startTime;
	/**任务结束日期*/
	private java.util.Date endTime;
	/**循环 1是，0否*/
	private java.lang.String cycle;
	/**完成了几次*/
	private java.lang.Integer pace;
	/**任务需完成次数*/
	private java.lang.Integer jobNum;
	/**进度类型 1完成， 0未完成*/
	private java.lang.String paceType;
	/**每次获得的金币数*/
	private java.lang.Integer goldGet;
	/**最多领金币的次数*/
	private java.lang.Integer goldTime;
	/**获得的金币总数*/
	private java.lang.Integer goldTotal;
	/**扣除金币数*/
	private java.lang.Integer goldSub;
	/**当前导购金币数*/
	private java.lang.Integer goldPerson;
	/**是否统计结束 1完成，0未完成*/
	private java.lang.String finish;
	
	
	
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

	@Column(name ="PARAM_ID",nullable=true)
	public java.lang.String getParamId() {
		return paramId;
	}

	public void setParamId(java.lang.String paramId) {
		this.paramId = paramId;
	}

	@Column(name ="RETAILER_ID",nullable=true)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}

	@Column(name ="STORE_ID",nullable=true)
	public java.lang.String getStoreId() {
		return storeId;
	}

	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}

	@Column(name ="GUIDE_ID",nullable=true)
	public java.lang.String getGuideId() {
		return guideId;
	}

	public void setGuideId(java.lang.String guideId) {
		this.guideId = guideId;
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

	@Column(name ="CYCLE",nullable=true)
	public java.lang.String getCycle() {
		return cycle;
	}

	public void setCycle(java.lang.String cycle) {
		this.cycle = cycle;
	}

	@Column(name ="PACE",nullable=true)
	public java.lang.Integer getPace() {
		return pace;
	}

	public void setPace(java.lang.Integer pace) {
		this.pace = pace;
	}

	@Column(name ="JOB_NUM",nullable=true)
	public java.lang.Integer getJobNum() {
		return jobNum;
	}

	public void setJobNum(java.lang.Integer jobNum) {
		this.jobNum = jobNum;
	}

	@Column(name ="PACE_TYPE",nullable=true)
	public java.lang.String getPaceType() {
		return paceType;
	}

	public void setPaceType(java.lang.String paceType) {
		this.paceType = paceType;
	}

	@Column(name ="GOLD_GET",nullable=true)
	public java.lang.Integer getGoldGet() {
		return goldGet;
	}

	public void setGoldGet(java.lang.Integer goldGet) {
		this.goldGet = goldGet;
	}

	@Column(name ="GOLD_TIME",nullable=true)
	public java.lang.Integer getGoldTime() {
		return goldTime;
	}

	public void setGoldTime(java.lang.Integer goldTime) {
		this.goldTime = goldTime;
	}

	@Column(name ="GOLD_TOTAL",nullable=true)
	public java.lang.Integer getGoldTotal() {
		return goldTotal;
	}

	public void setGoldTotal(java.lang.Integer goldTotal) {
		this.goldTotal = goldTotal;
	}

	@Column(name ="GOLD_SUB",nullable=true)
	public java.lang.Integer getGoldSub() {
		return goldSub;
	}

	public void setGoldSub(java.lang.Integer goldSub) {
		this.goldSub = goldSub;
	}

	@Column(name ="GOLD_PERSON",nullable=true)
	public java.lang.Integer getGoldPerson() {
		return goldPerson;
	}

	public void setGoldPerson(java.lang.Integer goldPerson) {
		this.goldPerson = goldPerson;
	}

	@Column(name ="FINISH",nullable=true)
	public java.lang.String getFinish() {
		return finish;
	}

	public void setFinish(java.lang.String finish) {
		this.finish = finish;
	}
	
}
