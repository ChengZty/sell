package com.buss.activity.entity;

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
 * @Description: 赠品规则
 * @author onlineGenerator
 * @date 2016-12-23 10:30:29
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_gift_rule", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TGiftRuleEntity implements java.io.Serializable {
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
	/**规则名称*/
	@Excel(name="规则名称")
	private java.lang.String ruleName;
	/**零售商ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**零售商名称*/
	@Excel(name="零售商名称")
	private java.lang.String retailerName;
	/**金额*/
	@Excel(name="金额")
	private java.math.BigDecimal money;
	/**规则类别*/
	@Excel(name="规则类别")
	private java.lang.String ruleType;
	/**规则状态*/
	@Excel(name="规则状态")
	private java.lang.String ruleStatus;
	/**审核人ID*/
	@Excel(name="审核人ID")
	private java.lang.String auditId;
	/**审核人*/
	@Excel(name="审核人")
	private java.lang.String auditor;
	/**审核时间*/
	@Excel(name="审核时间")
	private java.util.Date auditTime;
	/**描述*/
	@Excel(name="描述")
	private java.lang.String description;
	
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
	 *@return: java.lang.String  规则名称
	 */
	@Column(name ="RULE_NAME",nullable=true,length=50)
	public java.lang.String getRuleName(){
		return this.ruleName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规则名称
	 */
	public void setRuleName(java.lang.String ruleName){
		this.ruleName = ruleName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商ID
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商ID
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商名称
	 */
	@Column(name ="RETAILER_NAME",nullable=true,length=36)
	public java.lang.String getRetailerName(){
		return this.retailerName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商名称
	 */
	public void setRetailerName(java.lang.String retailerName){
		this.retailerName = retailerName;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额
	 */
	@Column(name ="MONEY",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getMoney(){
		return this.money;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额
	 */
	public void setMoney(java.math.BigDecimal money){
		this.money = money;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规则类别
	 */
	@Column(name ="RULE_TYPE",nullable=true,length=1)
	public java.lang.String getRuleType(){
		return this.ruleType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规则类别
	 */
	public void setRuleType(java.lang.String ruleType){
		this.ruleType = ruleType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规则状态
	 */
	@Column(name ="RULE_STATUS",nullable=true,length=1)
	public java.lang.String getRuleStatus(){
		return this.ruleStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规则状态
	 */
	public void setRuleStatus(java.lang.String ruleStatus){
		this.ruleStatus = ruleStatus;
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
	@Column(name ="description")
	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	
}
