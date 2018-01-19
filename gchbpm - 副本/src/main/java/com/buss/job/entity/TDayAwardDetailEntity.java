package com.buss.job.entity;

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
 * @Description: 每日奖励明细
 * @author onlineGenerator
 * @date 2016-11-04 18:25:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_day_award_detail", schema = "")
@Where(clause="status = 'A'")
@SuppressWarnings("serial")
public class TDayAwardDetailEntity implements java.io.Serializable {
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
	/**任务类型*/
	@Excel(name="任务类型")
	private java.lang.String jobType;
	/**任务编码*/
	@Excel(name="任务编码")
	private java.lang.String jobCode;
	/**任务标题*/
	@Excel(name="任务标题")
	private java.lang.String title;
	/**领取金币数*/
	@Excel(name="领取金币数")
	private java.lang.Integer goldNum;
	/**领金币时间*/
	@Excel(name="领金币时间")
	private java.util.Date jobDate;
	/**导购userid*/
	@Excel(name="导购userid")
	private java.lang.String userId;
	/**零售商id*/
	@Excel(name="零售商id")
	private java.lang.String retailerId;
	
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务类型
	 */
	@Column(name ="JOB_TYPE",nullable=true,length=1)
	public java.lang.String getJobType(){
		return this.jobType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务类型
	 */
	public void setJobType(java.lang.String jobType){
		this.jobType = jobType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务编码
	 */
	@Column(name ="JOB_CODE",nullable=true,length=30)
	public java.lang.String getJobCode(){
		return this.jobCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务编码
	 */
	public void setJobCode(java.lang.String jobCode){
		this.jobCode = jobCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务标题
	 */
	@Column(name ="TITLE",nullable=true,length=30)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  领取金币数
	 */
	@Column(name ="GOLD_NUM",nullable=true,length=10)
	public java.lang.Integer getGoldNum(){
		return this.goldNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  领取金币数
	 */
	public void setGoldNum(java.lang.Integer goldNum){
		this.goldNum = goldNum;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  领金币时间
	 */
	@Column(name ="JOB_DATE",nullable=true)
	public java.util.Date getJobDate(){
		return this.jobDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  领金币时间
	 */
	public void setJobDate(java.util.Date jobDate){
		this.jobDate = jobDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购userid
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购userid
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商id
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商id
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
}
