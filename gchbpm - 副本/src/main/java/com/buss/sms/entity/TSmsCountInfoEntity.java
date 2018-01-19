package com.buss.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: t_sms_count_info
 * @author xh
 * @date 2017-04-12 11:31:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sms_count_info", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TSmsCountInfoEntity implements java.io.Serializable {
	public static String TYPE_1 = "1";//充值
	public static String TYPE_2 = "2";//扣除
	public static String TYPE_3 = "3";//锁定
	public static String TYPE_4 = "4";//消费
	
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
	/**零售商ID*/
	private java.lang.String retailerId;
	/**零售商名称*/
	private java.lang.String retailerName;
	/**修改的短信条数*/
	private java.lang.Integer number;
	/**备注*/
	private java.lang.String remark;
	/**类型*/
	private java.lang.String type;
	/**发送主表ID*/
	private java.lang.String sendInfoId;
	/**批次号*/
	private java.lang.String batchNo;
	
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
	 *@return: java.lang.String  零售商名
	 */
	@Column(name ="RETAILER_NAME",nullable=true,length=36)
	public java.lang.String getRetailerName(){
		return this.retailerName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商名
	 */
	public void setRetailerName(java.lang.String retailerName){
		this.retailerName = retailerName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  短信条数
	 */
	@Column(name ="NUMBER",nullable=true,length=10)
	public java.lang.Integer getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  账号总条数
	 */
	public void setNumber(java.lang.Integer number){
		this.number = number;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=50)
	public java.lang.String getRemark() {
		return remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	@Column(name ="TYPE")
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}
	@Column(name ="SEND_INFO_ID")
	public java.lang.String getSendInfoId() {
		return sendInfoId;
	}

	public void setSendInfoId(java.lang.String sendInfoId) {
		this.sendInfoId = sendInfoId;
	}
	@Column(name ="BATCH_NO")
	public java.lang.String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(java.lang.String batchNo) {
		this.batchNo = batchNo;
	}
	
}
