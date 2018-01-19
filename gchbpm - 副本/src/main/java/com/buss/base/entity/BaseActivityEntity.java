package com.buss.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 活动
 * @author onlineGenerator
 * @date 2016-03-31 15:29:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "base_activity", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class BaseActivityEntity implements java.io.Serializable {
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
	/**子标题*/
	@Excel(name="子标题")
	private java.lang.String subTitle;
	/**图片*/
	@Excel(name="图片")
	private java.lang.String picUrl;
	/**所有零售商id*/
	private java.lang.String retailerId;
	/**用户类型*/
	private java.lang.String userType;//01后台，02零售商，03导购，04顾客，05零售商员工
	/**开始时间*/
	private java.util.Date startTime;
	/**结束时间*/
	private java.util.Date endTime;
//	/**活动状态      0未开始、1已开始、2已结束*/
//	private java.lang.String activityStatus;
	/**栏目ID*/
	private java.lang.Long contentId;
	/**封面图片*/
	@Excel(name="封面图片")
	private java.lang.String coverPic;
	/**资讯正文*/
	@Excel(name="资讯正文")
	private java.lang.String newsContext;
	
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动名称
	 */
	@Column(name ="ACTIVITY_NAME",nullable=true,length=32)
	public java.lang.String getActivityName(){
		return this.activityName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动名称
	 */
	public void setActivityName(java.lang.String activityName){
		this.activityName = activityName;
	}
	@Column(name ="SUB_TITLE",nullable=true,length=32)
	public java.lang.String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(java.lang.String subTitle) {
		this.subTitle = subTitle;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片
	 */
	@Column(name ="PIC_URL",nullable=true,length=150)
	public java.lang.String getPicUrl(){
		return this.picUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片
	 */
	public void setPicUrl(java.lang.String picUrl){
		this.picUrl = picUrl;
	}
	@Column(name ="retailer_id",nullable=true)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Column(name ="user_type",nullable=true,length=2)
	public java.lang.String getUserType() {
		return userType;
	}

	public void setUserType(java.lang.String userType) {
		this.userType = userType;
	}
	@Column(name ="START_TIME",nullable=true,length=20)
	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	@Column(name ="END_TIME",nullable=true,length=20)
	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
//	@Column(name ="ACTIVITY_STATUS",nullable=true,length=1)
//	public java.lang.String getActivityStatus() {
//		return activityStatus;
//	}
//
//	public void setActivityStatus(java.lang.String activityStatus) {
//		this.activityStatus = activityStatus;
//	}
	@Column(name ="CONTENT_ID",nullable=true,length=36)
	public java.lang.Long getContentId() {
		return contentId;
	}

	public void setContentId(java.lang.Long contentId) {
		this.contentId = contentId;
	}
	@Column(name ="COVER_PIC",nullable=true,length=155)
	public java.lang.String getCoverPic(){
		return this.coverPic;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  封面图片
	 */
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
	
}
