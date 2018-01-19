package com.buss.ticket.entity;

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
 * @Description: 云商劵分配表
 * @author onlineGenerator
 * @date 2016-07-20 10:08:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_ticket_retailers", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TTicketRetailersEntity implements java.io.Serializable {
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
	/**券ID*/
	private java.lang.String ticketId;
	/**零售商ID*/
	private java.lang.String userId;
	/**分配人ID*/
	@Excel(name="分配人ID")
	private java.lang.String senderId;
	/**分配时间*/
	@Excel(name="分配时间",format = "yyyy-MM-dd")
	private java.util.Date addTime;
	/**总张数*/
	@Excel(name="总张数")
	private java.lang.Integer sheet;
	/**发放张数*/
	@Excel(name="发放张数")
	private java.lang.Integer sheetGive;
	/**剩余张数*/
	@Excel(name="剩余张数")
	private java.lang.Integer sheetRemain;
	
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
	 *@return: java.lang.String  券ID
	 */
	@Column(name ="TICKET_ID",nullable=true,length=36)
	public java.lang.String getTicketId(){
		return this.ticketId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  券ID
	 */
	public void setTicketId(java.lang.String ticketId){
		this.ticketId = ticketId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购ID
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分配人ID
	 */
	@Column(name ="SENDER_ID",nullable=true,length=36)
	public java.lang.String getSenderId(){
		return this.senderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分配人ID
	 */
	public void setSenderId(java.lang.String senderId){
		this.senderId = senderId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  分配时间
	 */
	@Column(name ="ADD_TIME",nullable=true,length=20)
	public java.util.Date getAddTime(){
		return this.addTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  分配时间
	 */
	public void setAddTime(java.util.Date addTime){
		this.addTime = addTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  总张数
	 */
	@Column(name ="SHEET",nullable=true,length=10)
	public java.lang.Integer getSheet(){
		return this.sheet;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  总张数
	 */
	public void setSheet(java.lang.Integer sheet){
		this.sheet = sheet;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  发放张数
	 */
	@Column(name ="SHEET_GIVE",nullable=true,length=10)
	public java.lang.Integer getSheetGive(){
		return this.sheetGive;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  发放张数
	 */
	public void setSheetGive(java.lang.Integer sheetGive){
		this.sheetGive = sheetGive;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  剩余张数
	 */
	@Column(name ="SHEET_REMAIN",nullable=true,length=10)
	public java.lang.Integer getSheetRemain(){
		return this.sheetRemain;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  剩余张数
	 */
	public void setSheetRemain(java.lang.Integer sheetRemain){
		this.sheetRemain = sheetRemain;
	}
}
