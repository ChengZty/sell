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
 * @Description: 问题列表
 * @author onlineGenerator
 * @date 2016-05-31 01:02:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "base_questions", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class BaseQuestionsEntity implements java.io.Serializable {
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
	/**问题*/
	@Excel(name="问题")
	private java.lang.String questionName;
	/**问题值*/
	@Excel(name="问题值")
	private java.lang.String questionValue;
	/**答案类型*/
	@Excel(name="答案类型")
	private java.lang.String answerType;
	/**答案*/
	@Excel(name="答案")
	private java.lang.String answerValues;
	/**维护类别,1：导购，2：顾客*/
	@Excel(name="维护类别")
	private java.lang.String maintanceType;
	/**排序*/
	@Excel(name="排序")
	private java.lang.Integer questionSort;
	/**资料类型,来源于字典code: qest_info_type*/
	@Excel(name="资料类型")
	private java.lang.String infoType;
	
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
	 *@return: java.lang.String  问题
	 */
	@Column(name ="QUESTION_NAME",nullable=true,length=50)
	public java.lang.String getQuestionName(){
		return this.questionName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题
	 */
	public void setQuestionName(java.lang.String questionName){
		this.questionName = questionName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  问题值
	 */
	@Column(name ="QUESTION_VALUE",nullable=true,length=5)
	public java.lang.String getQuestionValue(){
		return this.questionValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题值
	 */
	public void setQuestionValue(java.lang.String questionValue){
		this.questionValue = questionValue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  答案类型
	 */
	@Column(name ="ANSWER_TYPE",nullable=true,length=1)
	public java.lang.String getAnswerType(){
		return this.answerType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  答案类型
	 */
	public void setAnswerType(java.lang.String answerType){
		this.answerType = answerType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  答案
	 */
	@Column(name ="ANSWER_VALUES",nullable=true,length=500)
	public java.lang.String getAnswerValues(){
		return this.answerValues;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  答案
	 */
	public void setAnswerValues(java.lang.String answerValues){
		this.answerValues = answerValues;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  维护类别
	 */
	@Column(name ="MAINTANCE_TYPE",nullable=true,length=1)
	public java.lang.String getMaintanceType(){
		return this.maintanceType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  维护类别
	 */
	public void setMaintanceType(java.lang.String maintanceType){
		this.maintanceType = maintanceType;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="QUESTION_SORT",nullable=true,length=3)
	public java.lang.Integer getQuestionSort(){
		return this.questionSort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setQuestionSort(java.lang.Integer questionSort){
		this.questionSort = questionSort;
	}

	@Column(name ="INFO_TYPE",nullable=true,length=3)
	public java.lang.String getInfoType() {
		return infoType;
	}

	public void setInfoType(java.lang.String infoType) {
		this.infoType = infoType;
	}
	
	
}
