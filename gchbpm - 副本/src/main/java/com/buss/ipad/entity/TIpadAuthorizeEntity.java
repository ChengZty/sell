package com.buss.ipad.entity;

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
 * @Description: ipad授权表
 * @author onlineGenerator
 * @date 2016-09-02 20:39:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_ipad_authorize", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TIpadAuthorizeEntity implements java.io.Serializable {
	public static final String USE_STATUS_INACTIVE = "0";
	public static final String USE_STATUS_ACTIVE = "1";
	public static final String USE_STATUS_STOP = "2";
	
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
	/**记录状态*/
	@Excel(name="记录状态")
	private java.lang.String status;
	/**使用状态*/
	@Excel(name="使用状态")
	private java.lang.String useStatus;
	/**设备号*/
	@Excel(name="设备号")
	private java.lang.String deviceNo;
	/**授权码*/
	@Excel(name="授权码")
	private java.lang.String authorizeCode;
	/**授权日期*/
	@Excel(name="授权日期",format = "yyyy-MM-dd")
	private java.util.Date authorizeDate;
	/**零售商ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**零售商名称*/
	@Excel(name="零售商名称")
	private java.lang.String retailerName;
	
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
	 *@return: java.lang.String  记录状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  记录状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  使用状态
	 */
	@Column(name ="USE_STATUS",nullable=true,length=1)
	public java.lang.String getUseStatus(){
		return this.useStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  使用状态
	 */
	public void setUseStatus(java.lang.String useStatus){
		this.useStatus = useStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  设备号
	 */
	@Column(name ="DEVICE_NO",nullable=true,length=256)
	public java.lang.String getDeviceNo(){
		return this.deviceNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  设备号
	 */
	public void setDeviceNo(java.lang.String deviceNo){
		this.deviceNo = deviceNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  授权码
	 */
	@Column(name ="AUTHORIZE_CODE",nullable=true,length=256)
	public java.lang.String getAuthorizeCode(){
		return this.authorizeCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  授权码
	 */
	public void setAuthorizeCode(java.lang.String authorizeCode){
		this.authorizeCode = authorizeCode;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  授权日期
	 */
	@Column(name ="AUTHORIZE_DATE",nullable=true,length=32)
	public java.util.Date getAuthorizeDate(){
		return this.authorizeDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  授权日期
	 */
	public void setAuthorizeDate(java.util.Date authorizeDate){
		this.authorizeDate = authorizeDate;
	}
	@Column(name ="RETAILER_ID",nullable=true)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Column(name ="RETAILER_NAME",nullable=true)
	public java.lang.String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(java.lang.String retailerName) {
		this.retailerName = retailerName;
	}
	
}
