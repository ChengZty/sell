package com.buss.ticket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 优惠券使用
 * @author onlineGenerator
 * @date 2016-08-08 17:53:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_ticket_use", schema = "")
@SuppressWarnings("serial")
public class TTicketUseEntity implements java.io.Serializable {
	public static final String USE_TYPE_1 = "1";//收货
	public static final String USE_TYPE_2 = "2";//退款
	/**主键*/
	private java.lang.String id;
	/**创建时间*/
	private java.util.Date addTime;
	/**完成日期*/
	private java.util.Date businessDate;
	/**优惠券ID*/
	private java.lang.String ticketId;
	/**零售商ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**优惠券明细ID*/
	private java.lang.String ticketDetailId;
	/**实际优惠额度*/
	@Excel(name="实际优惠额度")
	private java.math.BigDecimal ticketPreferential;
	/**订单号/退款单号*/
	@Excel(name="订单号/退款单号")
	private java.lang.String orderNo;
	/**子订单号*/
	@Excel(name="子订单号")
	private java.lang.String subOrderNo;
	/**货品所属零售商ID*/
	private java.lang.String toGoodsStoreId;
	/**用户ID*/
	private java.lang.String userId;
	/**用户姓名*/
	@Excel(name="用户姓名")
	private java.lang.String userName;
	/**使用类型*/
	@Excel(name="使用类型")
	private java.lang.String useType;
	/**服务导购*/
	private java.lang.String toGuideId;
	/**导购店铺id*/
	private java.lang.String storeId;
	/**导购姓名*/
	private java.lang.String guideName; 
	/**店铺名称*/
	private java.lang.String storeName; 
	
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="ADD_TIME",nullable=true,length=20)
	public java.util.Date getAddTime(){
		return this.addTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setAddTime(java.util.Date addTime){
		this.addTime = addTime;
	}
	@Column(name ="BUSINESS_DATE",nullable=true,length=20)
	public java.util.Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(java.util.Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  优惠券ID
	 */
	@Column(name ="TICKET_ID",nullable=true,length=36)
	public java.lang.String getTicketId(){
		return this.ticketId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  优惠券ID
	 */
	public void setTicketId(java.lang.String ticketId){
		this.ticketId = ticketId;
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
	 *@return: java.lang.String  优惠券明细ID
	 */
	@Column(name ="TICKET_DETAIL_ID",nullable=true,length=36)
	public java.lang.String getTicketDetailId(){
		return this.ticketDetailId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  优惠券明细ID
	 */
	public void setTicketDetailId(java.lang.String ticketDetailId){
		this.ticketDetailId = ticketDetailId;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  实际优惠额度
	 */
	@Column(name ="TICKET_PREFERENTIAL",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getTicketPreferential(){
		return this.ticketPreferential;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  实际优惠额度
	 */
	public void setTicketPreferential(java.math.BigDecimal ticketPreferential){
		this.ticketPreferential = ticketPreferential;
	}
	@Column(name ="ORDER_NO",nullable=true,length=20)
	public java.lang.String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  子订单号
	 */
	@Column(name ="SUB_ORDER_NO",nullable=true,length=20)
	public java.lang.String getSubOrderNo(){
		return this.subOrderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  子订单号
	 */
	public void setSubOrderNo(java.lang.String subOrderNo){
		this.subOrderNo = subOrderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货品所属零售商ID
	 */
	@Column(name ="TO_GOODS_STORE_ID",nullable=true,length=36)
	public java.lang.String getToGoodsStoreId(){
		return this.toGoodsStoreId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  货品所属零售商ID
	 */
	public void setToGoodsStoreId(java.lang.String toGoodsStoreId){
		this.toGoodsStoreId = toGoodsStoreId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户ID
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户姓名
	 */
	@Column(name ="USER_NAME",nullable=true,length=32)
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户姓名
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  使用类型
	 */
	@Column(name ="USE_TYPE",nullable=true,length=1)
	public java.lang.String getUseType(){
		return this.useType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  使用类型
	 */
	public void setUseType(java.lang.String useType){
		this.useType = useType;
	}
	@Column(name ="to_Guide_Id",nullable=true)
	public java.lang.String getToGuideId() {
		return toGuideId;
	}
	public void setToGuideId(java.lang.String toGuideId) {
		this.toGuideId = toGuideId;
	}
	@Column(name ="store_Id",nullable=true)
	public java.lang.String getStoreId() {
		return storeId;
	}
	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	@Transient
	public java.lang.String getGuideName() {
		return guideName;
	}

	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	@Transient
	public java.lang.String getStoreName() {
		return storeName;
	}

	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	
}
