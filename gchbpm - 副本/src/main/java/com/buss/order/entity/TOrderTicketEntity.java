package com.buss.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 订单优惠券明细表
 * @author onlineGenerator
 * @date 2016-12-16 10:14:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_order_ticket", schema = "")
@SuppressWarnings("serial")
public class TOrderTicketEntity implements java.io.Serializable {
	/**下订单买*/
	public static final String ORDER_TYPE_1 = "1";
	/**一键退款*/
	public static final String ORDER_TYPE_2 = "2";
	/**使用*/
	public static final String TICKET_STATUS_1 = "1";
	/**失效*/
	public static final String TICKET_STATUS_2 = "2";
	/**id*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期(订单时间)*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**状态*/
	private java.lang.String status;
	/**订单ID/退款ID*/
	@Excel(name="订单ID/退款ID")
	private java.lang.String orderId;
	/**订单号/退款单号*/
	private java.lang.String orderNo;
	/**子单号*/
	@Excel(name="子单号")
	private java.lang.String subOrderNo;
	/**订单类型 1买2退*/
	@Excel(name="订单类型 1买2退")
	private java.lang.String orderType;
	/**券ID*/
	@Excel(name="券ID")
	private java.lang.String ticketId;
	/**券类型*/
	@Excel(name="券类型")
	private java.lang.String ticketType;
	/**零售商ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**零售商类型*/
	@Excel(name="零售商类型")
	private java.lang.String retailerType;
	/**代金券明细id*/
	@Excel(name="代金券明细id")
	private java.lang.String ticketDetailId;
	/**代金券明细编码*/
	@Excel(name="代金券明细编码")
	private java.lang.String ticketCode;
	/**代金券满额*/
	@Excel(name="代金券满额")
	private java.math.BigDecimal ticketLeastMoney;
	/**代金券减额或面额*/
	@Excel(name="代金券减额或面额")
	private java.math.BigDecimal ticketDiscount;
	/**代金券优惠额度*/
	@Excel(name="代金券优惠额度")
	private java.math.BigDecimal ticketPreferential;
	/**券状态 1使用2失效*/
	@Excel(name="券状态 1使用2失效")
	private java.lang.String ticketStatus;
	/**服务导购*/
	private java.lang.String toGuideId;
	/**店铺id*/
	private java.lang.String storeId;
	
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
	 *@return: java.lang.String  订单ID退款ID
	 */
	@Column(name ="ORDER_ID",nullable=true,length=36)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单ID退款ID
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	@Column(name ="ORDER_NO",nullable=true,length=32)
	public java.lang.String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  子单号
	 */
	@Column(name ="SUB_ORDER_NO",nullable=true,length=36)
	public java.lang.String getSubOrderNo(){
		return this.subOrderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  子单号
	 */
	public void setSubOrderNo(java.lang.String subOrderNo){
		this.subOrderNo = subOrderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单类型 1买2退
	 */
	@Column(name ="ORDER_TYPE",nullable=true,length=1)
	public java.lang.String getOrderType(){
		return this.orderType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单类型 1买2退
	 */
	public void setOrderType(java.lang.String orderType){
		this.orderType = orderType;
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
	 *@return: java.lang.String  券类型
	 */
	@Column(name ="TICKET_TYPE",nullable=true,length=1)
	public java.lang.String getTicketType(){
		return this.ticketType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  券类型
	 */
	public void setTicketType(java.lang.String ticketType){
		this.ticketType = ticketType;
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
	 *@return: java.lang.String  零售商类型
	 */
	@Column(name ="RETAILER_TYPE",nullable=true,length=1)
	public java.lang.String getRetailerType(){
		return this.retailerType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商类型
	 */
	public void setRetailerType(java.lang.String retailerType){
		this.retailerType = retailerType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代金券明细id
	 */
	@Column(name ="TICKET_DETAIL_ID",nullable=true,length=36)
	public java.lang.String getTicketDetailId(){
		return this.ticketDetailId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代金券明细id
	 */
	public void setTicketDetailId(java.lang.String ticketDetailId){
		this.ticketDetailId = ticketDetailId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代金券明细编码
	 */
	@Column(name ="TICKET_CODE",nullable=true,length=36)
	public java.lang.String getTicketCode(){
		return this.ticketCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代金券明细编码
	 */
	public void setTicketCode(java.lang.String ticketCode){
		this.ticketCode = ticketCode;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  代金券满额
	 */
	@Column(name ="TICKET_LEAST_MONEY",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getTicketLeastMoney(){
		return this.ticketLeastMoney;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  代金券满额
	 */
	public void setTicketLeastMoney(java.math.BigDecimal ticketLeastMoney){
		this.ticketLeastMoney = ticketLeastMoney;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  代金券减额或面额
	 */
	@Column(name ="TICKET_DISCOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getTicketDiscount(){
		return this.ticketDiscount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  代金券减额或面额
	 */
	public void setTicketDiscount(java.math.BigDecimal ticketDiscount){
		this.ticketDiscount = ticketDiscount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  代金券优惠额度
	 */
	@Column(name ="TICKET_PREFERENTIAL",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getTicketPreferential(){
		return this.ticketPreferential;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  代金券优惠额度
	 */
	public void setTicketPreferential(java.math.BigDecimal ticketPreferential){
		this.ticketPreferential = ticketPreferential;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  券状态 1使用2失效
	 */
	@Column(name ="TICKET_STATUS",nullable=true,length=1)
	public java.lang.String getTicketStatus(){
		return this.ticketStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  券状态 1使用2失效
	 */
	public void setTicketStatus(java.lang.String ticketStatus){
		this.ticketStatus = ticketStatus;
	}
	@Column(name ="TO_GUIDE_ID")
	public java.lang.String getToGuideId() {
		return toGuideId;
	}

	public void setToGuideId(java.lang.String toGuideId) {
		this.toGuideId = toGuideId;
	}
	@Column(name ="STORE_ID")
	public java.lang.String getStoreId() {
		return storeId;
	}

	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
}
