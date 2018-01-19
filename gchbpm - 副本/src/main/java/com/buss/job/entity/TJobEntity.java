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
 * @Description: t_job
 * @author onlineGenerator
 * @date 2016-11-05 11:54:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_job", schema = "")
@Where(clause="status = 'A'")
@SuppressWarnings("serial")
public class TJobEntity implements java.io.Serializable {
	public static final String JOB_TYPE_1 = "1";//每日任务
	public static final String JOB_TYPE_2 = "2";//成长任务
	//“每日有客”修改为“每日完善”
	//“绑定顾客总量”修改为“顾客资料完整度达到80%顾客数”
	//“夺魁总量”修改为“商品发布数”
	//“每日夺魁”修改为“每日推荐”
	
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
	/**任务类型 */
	@Excel(name="任务类型 ")
	private java.lang.String jobType;
	/**任务场景 */
	@Excel(name="任务场景 ")
	private java.lang.String jobSceneCode;
	/**任务编码  字典code:jobcode*/
	@Excel(name="任务编码")
	private java.lang.String jobCode;
	/**任务标题*/
	@Excel(name="任务标题")
	private java.lang.String title;
	/**任务规则*/
	@Excel(name="任务规则")
	private java.lang.String regulation;
	/**任务描述*/
	@Excel(name="任务描述")
	private java.lang.String description;
//	/**触发条件*/
//	@Excel(name="触发条件")
//	private java.lang.String condition;
	/**倍率*/
	@Excel(name="倍率")
	private java.lang.Integer rate;
	/**目标数量*/
	@Excel(name="目标数量")
	private java.lang.Integer targetNum;
	/**金币数*/
	@Excel(name="金币数")
	private java.lang.Integer goldNum;
	/**难易程度  越大越难*/
	private java.lang.String complexity;
	
	/**complexity
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务规则
	 */
	@Column(name ="REGULATION",nullable=true,length=30)
	public java.lang.String getRegulation() {
		return regulation;
	}

	public void setRegulation(java.lang.String regulation) {
		this.regulation = regulation;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务描述
	 */
	@Column(name ="DESCRIPTION",nullable=true,length=300)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务描述
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  倍率
	 */
	@Column(name ="RATE",nullable=true,length=10)
	public java.lang.Integer getRate(){
		return this.rate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  倍率
	 */
	public void setRate(java.lang.Integer rate){
		this.rate = rate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  目标数量
	 */
	@Column(name ="TARGET_NUM",nullable=true,length=10)
	public java.lang.Integer getTargetNum(){
		return this.targetNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  目标数量
	 */
	public void setTargetNum(java.lang.Integer targetNum){
		this.targetNum = targetNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  金币数
	 */
	@Column(name ="GOLD_NUM",nullable=true,length=10)
	public java.lang.Integer getGoldNum(){
		return this.goldNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  金币数
	 */
	public void setGoldNum(java.lang.Integer goldNum){
		this.goldNum = goldNum;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务场景 
	 */
	@Column(name ="JOB_SCENE_CODE",nullable=true,length=1)
	public java.lang.String getJobSceneCode() {
		return jobSceneCode;
	}

	public void setJobSceneCode(java.lang.String jobSceneCode) {
		this.jobSceneCode = jobSceneCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务难易程度
	 */
	@Column(name ="COMPLEXITY",nullable=true,length=1)
	public java.lang.String getComplexity() {
		return complexity;
	}

	public void setComplexity(java.lang.String complexity) {
		this.complexity = complexity;
	}
	
}
