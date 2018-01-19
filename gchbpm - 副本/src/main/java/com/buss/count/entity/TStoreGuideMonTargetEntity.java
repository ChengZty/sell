package com.buss.count.entity;

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
 * @Description: 导购月目标
 * @author onlineGenerator
 * @date 2017-05-17 11:28:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_store_guide_mon_target", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TStoreGuideMonTargetEntity implements java.io.Serializable {
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
	/**月目标ID*/
	@Excel(name="月目标ID")
	private java.lang.String monTargetId;
	/**月份*/
	@Excel(name="月份")
	private java.lang.String targetMonth;
	/**零售商ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**店铺ID*/
	@Excel(name="店铺ID")
	private java.lang.String storeId;
	/**导购userId*/
	private java.lang.String guideId;
	/**导购userId*/
	@Excel(name="导购姓名")
	private java.lang.String guideName;
	/**目标金额*/
	@Excel(name="目标金额")
	private java.math.BigDecimal targetMoney;
	/**线上销售金额*/
	@Excel(name="线上销售金额")
	private java.math.BigDecimal onlineMoney;
	/**实体销售金额*/
	@Excel(name="实体销售金额")
	private java.math.BigDecimal offlineMoney;
	/**达成率*/
	@Excel(name="达成率")
	private java.math.BigDecimal reachRate;
	/**线上件数*/
	@Excel(name="线上件数")
	private java.math.BigDecimal onlineQuantity;
	/**线上单数*/
	@Excel(name="线上单数")
	private java.math.BigDecimal onlineSingular;
	/**实体件数*/
	@Excel(name="实体件数")
	private java.math.BigDecimal offlineQuantity;
	/**实体单数*/
	@Excel(name="实体单数")
	private java.math.BigDecimal offlineSingular;
	
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
	@Column(name ="STATUS",nullable=false,length=1)
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
	 *@return: java.lang.String  月目标ID
	 */
	@Column(name ="MON_TARGET_ID",nullable=false,length=36)
	public java.lang.String getMonTargetId(){
		return this.monTargetId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  月目标ID
	 */
	public void setMonTargetId(java.lang.String monTargetId){
		this.monTargetId = monTargetId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  月份
	 */
	@Column(name ="TARGET_MONTH",nullable=false,length=20)
	public java.lang.String getTargetMonth(){
		return this.targetMonth;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  月份
	 */
	public void setTargetMonth(java.lang.String targetMonth){
		this.targetMonth = targetMonth;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商ID
	 */
	@Column(name ="RETAILER_ID",nullable=false,length=36)
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
	 *@return: java.lang.String  店铺ID
	 */
	@Column(name ="STORE_ID",nullable=false,length=36)
	public java.lang.String getStoreId(){
		return this.storeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  店铺ID
	 */
	public void setStoreId(java.lang.String storeId){
		this.storeId = storeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购userId
	 */
	@Column(name ="GUIDE_ID",nullable=false,length=36)
	public java.lang.String getGuideId(){
		return this.guideId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购userId
	 */
	public void setGuideId(java.lang.String guideId){
		this.guideId = guideId;
	}
	@Column(name ="GUIDE_NAME",nullable=false,length=36)
	public java.lang.String getGuideName() {
		return guideName;
	}

	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  目标金额
	 */
	@Column(name ="TARGET_MONEY",nullable=false,scale=2,length=12)
	public java.math.BigDecimal getTargetMoney(){
		return this.targetMoney;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  目标金额
	 */
	public void setTargetMoney(java.math.BigDecimal targetMoney){
		this.targetMoney = targetMoney;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  线上销售金额
	 */
	@Column(name ="ONLINE_MONEY",nullable=false,scale=2,length=12)
	public java.math.BigDecimal getOnlineMoney(){
		return this.onlineMoney;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  线上销售金额
	 */
	public void setOnlineMoney(java.math.BigDecimal onlineMoney){
		this.onlineMoney = onlineMoney;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  实体销售金额
	 */
	@Column(name ="OFFLINE_MONEY",nullable=false,scale=2,length=12)
	public java.math.BigDecimal getOfflineMoney(){
		return this.offlineMoney;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  实体销售金额
	 */
	public void setOfflineMoney(java.math.BigDecimal offlineMoney){
		this.offlineMoney = offlineMoney;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  达成率
	 */
	@Column(name ="REACH_RATE",nullable=false,scale=2,length=5)
	public java.math.BigDecimal getReachRate(){
		return this.reachRate;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  达成率
	 */
	public void setReachRate(java.math.BigDecimal reachRate){
		this.reachRate = reachRate;
	}
	@Column(name ="ONLINE_QUANTITY",nullable=false,scale=0,length=12)
	public java.math.BigDecimal getOnlineQuantity() {
		return onlineQuantity;
	}

	public void setOnlineQuantity(java.math.BigDecimal onlineQuantity) {
		this.onlineQuantity = onlineQuantity;
	}
	@Column(name ="ONLINE_SINGULAR",nullable=false,scale=0,length=12)
	public java.math.BigDecimal getOnlineSingular() {
		return onlineSingular;
	}

	public void setOnlineSingular(java.math.BigDecimal onlineSingular) {
		this.onlineSingular = onlineSingular;
	}
	@Column(name ="OFFLINE_QUANTITY",nullable=false,scale=0,length=12)
	public java.math.BigDecimal getOfflineQuantity() {
		return offlineQuantity;
	}

	public void setOfflineQuantity(java.math.BigDecimal offlineQuantity) {
		this.offlineQuantity = offlineQuantity;
	}
	@Column(name ="OFFLINE_SINGULAR",nullable=false,scale=0,length=12)
	public java.math.BigDecimal getOfflineSingular() {
		return offlineSingular;
	}

	public void setOfflineSingular(java.math.BigDecimal offlineSingular) {
		this.offlineSingular = offlineSingular;
	}
	
}
