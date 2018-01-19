package com.buss.bill.entity;

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
 * @Description: 导购激励活动
 * @author onlineGenerator
 * @date 2016-11-25 21:48:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_fin_activity", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TFinActivityEntity implements java.io.Serializable {
	public static final String PLATFORM_TYPE_1 = "1";//平台
	public static final String PLATFORM_TYPE_2 = "2";//零售商
	public static final String ACTIVITY_STATUS_1 = "1";//待审核
	public static final String ACTIVITY_STATUS_2 = "2";//已审核
	public static final String ACTIVITY_STATUS_3 = "3";//已下架
	public static final String ACTIVITYTYPE_1 = "1";//单品
	public static final String ACTIVITYTYPE_2 = "2";//品牌
	public static final String ACTIVITYTYPE_3 = "3";//全馆
	/**活动状态是从活动时间及审核状态和是否有效综合得出的结果*/
	public static final int ACT_STATUS_1 = 1;//待审核
	public static final int ACT_STATUS_2 = 2;//待开始（已审核）
	public static final int ACT_STATUS_3 = 3;//进行中
	public static final int ACT_STATUS_4 = 4;//已结束(包括下架)
	public static final int ACT_STATUS_5 = 5;//已作废
	/**主键*/
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
	/**活动名称*/
	@Excel(name="活动名称")
	private java.lang.String activityName;
	/**平台类别(1:平台，2：零售商，暂时没有平台的)*/
	@Excel(name="平台类别")
	private java.lang.String platformType;
	/**零售商ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**零售商类型*/
	@Excel(name="零售商类型")
	private java.lang.String retailerType;
	/**零售商名称*/
	@Excel(name="零售商名称")
	private java.lang.String retailerName;
	/**活动类别(1:单品，2：品牌，3：全馆)*/
	@Excel(name="活动类别")
	private java.lang.String activityType;
	/**品牌ID*/
	@Excel(name="品牌ID")
	private java.lang.String brandId;
	/**品牌名称*/
	@Excel(name="品牌名称")
	private java.lang.String brandName;
	/**开始时间*/
	@Excel(name="开始时间")
	private java.util.Date startTime;
	/**结束时间*/
	@Excel(name="结束时间")
	private java.util.Date endTime;
	/**活动状态（1：待审核，2：已审核，3：已下架）*/
	private java.lang.String activityStatus;
	/**活动对象类型（1：导购，2：顾客） 默认为导购*/
	private java.lang.String toUserType;
	/**审核人ID*/
	@Excel(name="审核人ID")
	private java.lang.String auditId;
	/**审核人*/
	@Excel(name="审核人")
	private java.lang.String auditor;
	/**审核时间*/
	@Excel(name="审核时间")
	private java.util.Date auditTime;
	/**资讯正文 改为活动规则*/
	@Excel(name="活动规则")
	private java.lang.String newsContext;
	/**话题id*/
	private java.lang.String newsId;
	/**话题标题 非数据库字段*/
	private java.lang.String newsTitle;
	/**话题图片 非数据库字段*/
	private java.lang.String coverPic;
	/**活动状态 非数据库字段 1：待审核，2：待开始（已审核），3：进行中，4：已结束，5：已下架*/
	//该状态有时间和审核状态
	private java.lang.String actStatus;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
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
	@Column(name ="CREATE_DATE",nullable=true,length=20)
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
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
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
	 *@return: java.lang.String  活动类别
	 */
	@Column(name ="ACTIVITY_TYPE",nullable=true,length=1)
	public java.lang.String getActivityType(){
		return this.activityType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动类别
	 */
	public void setActivityType(java.lang.String activityType){
		this.activityType = activityType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌ID
	 */
	@Column(name ="BRAND_ID",nullable=true,length=36)
	public java.lang.String getBrandId(){
		return this.brandId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品牌ID
	 */
	public void setBrandId(java.lang.String brandId){
		this.brandId = brandId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始时间
	 */
	@Column(name ="START_TIME",nullable=true,length=20)
	public java.util.Date getStartTime(){
		return this.startTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始时间
	 */
	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束时间
	 */
	@Column(name ="END_TIME",nullable=true,length=20)
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动状态
	 */
	@Column(name ="ACTIVITY_STATUS",nullable=true,length=1)
	public java.lang.String getActivityStatus(){
		return this.activityStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动状态
	 */
	public void setActivityStatus(java.lang.String activityStatus){
		this.activityStatus = activityStatus;
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
	@Column(name ="platform_Type",nullable=true)
	public java.lang.String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(java.lang.String platformType) {
		this.platformType = platformType;
	}
	@Column(name ="retailer_Id",nullable=true)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Column(name ="retailer_type",nullable=true)
	public java.lang.String getRetailerType() {
		return retailerType;
	}

	public void setRetailerType(java.lang.String retailerType) {
		this.retailerType = retailerType;
	}

	@Column(name ="retailer_Name",nullable=true)
	public java.lang.String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(java.lang.String retailerName) {
		this.retailerName = retailerName;
	}
	@Column(name ="brand_Name",nullable=true)
	public java.lang.String getBrandName() {
		return brandName;
	}

	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	@Column(name ="activity_Name",nullable=true)
	public java.lang.String getActivityName() {
		return activityName;
	}

	public void setActivityName(java.lang.String activityName) {
		this.activityName = activityName;
	}
	@Column(name ="to_User_Type",nullable=false)
	public java.lang.String getToUserType() {
		return toUserType;
	}

	public void setToUserType(java.lang.String toUserType) {
		this.toUserType = toUserType;
	}

	@Column(name ="audit_Id")
	public java.lang.String getAuditId() {
		return auditId;
	}
	public void setAuditId(java.lang.String auditId) {
		this.auditId = auditId;
	}

	@Column(name ="auditor")
	public java.lang.String getAuditor() {
		return auditor;
	}
	public void setAuditor(java.lang.String auditor) {
		this.auditor = auditor;
	}

	@Column(name ="audit_Time")
	public java.util.Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}
	@Transient
	public java.lang.String getCoverPic(){
		return this.coverPic;
	}

	public void setCoverPic(java.lang.String coverPic){
		this.coverPic = coverPic;
	}
	/**
	 *方法: 取得java.sql.Blob
	 *@return: java.sql.Blob  资讯正文
	 */
	@Column(name ="NEWS_CONTEXT",nullable=true)
	public java.lang.String getNewsContext(){
		return this.newsContext;
	}

	/**
	 *方法: 设置java.sql.Blob
	 *@param: java.sql.Blob  资讯正文
	 */
	public void setNewsContext(String newsContext){
		this.newsContext = newsContext;
	}
	@Column(name ="news_id",nullable=true)
	public java.lang.String getNewsId() {
		return newsId;
	}

	public void setNewsId(java.lang.String newsId) {
		this.newsId = newsId;
	}
	@Transient
	public java.lang.String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(java.lang.String newsTitle) {
		this.newsTitle = newsTitle;
	}
	@Transient
	public java.lang.String getActStatus() {
		return actStatus;
	}

	public void setActStatus(java.lang.String actStatus) {
		this.actStatus = actStatus;
	}
	
}
