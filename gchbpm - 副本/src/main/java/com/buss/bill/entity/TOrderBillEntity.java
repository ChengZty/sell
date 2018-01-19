package com.buss.bill.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 订单结算表
 * @author onlineGenerator
 * @date 2016-04-12 16:36:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_order_bill", schema = "")
@SuppressWarnings("serial")
public class TOrderBillEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**结算单编号*/
	@Excel(name="结算单编号",width=150)
	private java.lang.String orderBillSn;
	/**开始日期*/
	@Excel(name="开始日期",format = "yyyy-MM-dd")
	private java.util.Date startTime;
	/**结束日期*/
	@Excel(name="结束日期",format = "yyyy-MM-dd")
	private java.util.Date endTime;
	/**订单金额*/
	@Excel(name="订单金额")
	private java.math.BigDecimal obOrderTotals;
	/**运费金额*/
	@Excel(name="运费金额")
	private java.math.BigDecimal osShippingTotals;
	/**佣金金额*/
	@Excel(name="佣金金额")
	private java.math.BigDecimal obCommisTotals;
	/**退单金额*/
	@Excel(name="退单金额")
	private java.math.BigDecimal obOrderReturnTotals;
	/**退还佣金*/
	@Excel(name="退还佣金")
	private java.math.BigDecimal obCommisReturnTotals;
	/**应结金额*/
	@Excel(name="应结金额")
	private java.math.BigDecimal obResultTotals;
	/**生成结算单日期*/
	@Excel(name="结算日期",format = "yyyy-MM-dd")
	private java.util.Date obCreateDate;
	/**结算单年月份*/
	@Excel(name="结算月份")
	private java.lang.String obCreateMonth;
	/**结算状态*/
	@Excel(name="结算状态",replace={"1_已结算","2_待结算"})
	private java.lang.String obState;
	/**付款日期*/
	@Excel(name="付款日期",format = "yyyy-MM-dd")
	private java.util.Date obPayDate;
	/**支付备注*/
	@Excel(name="支付备注")
	private java.lang.String obPayContent;
	/**结算对象ID*/
	private java.lang.String obPayerId;
	/**结算对象名称*/
	@Excel(name="结算对象名称")
	private java.lang.String obPayerName;
	/**手机号*/
	@Excel(name="手机号")
	private java.lang.String obPayerPhone;
	/**零售商ID*/
	private java.lang.String obStoreId;
	/**零售商名称*/
	@Excel(name="零售商名称")
	private java.lang.String obStoreName;
	
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
	 *@return: java.lang.String  结算单编号
	 */
	@Column(name ="ORDER_BILL_SN",nullable=true,length=32)
	public java.lang.String getOrderBillSn(){
		return this.orderBillSn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算单编号
	 */
	public void setOrderBillSn(java.lang.String orderBillSn){
		this.orderBillSn = orderBillSn;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始日期
	 */
	@Column(name ="START_TIME",nullable=true,length=32)
	public java.util.Date getStartTime(){
		return this.startTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始日期
	 */
	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束日期
	 */
	@Column(name ="END_TIME",nullable=true,length=32)
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束日期
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  订单金额
	 */
	@Column(name ="OB_ORDER_TOTALS",nullable=true,length=32)
	public java.math.BigDecimal getObOrderTotals(){
		return this.obOrderTotals;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  订单金额
	 */
	public void setObOrderTotals(java.math.BigDecimal obOrderTotals){
		this.obOrderTotals = obOrderTotals;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  运费金额
	 */
	@Column(name ="OS_SHIPPING_TOTALS",nullable=true,length=32)
	public java.math.BigDecimal getOsShippingTotals(){
		return this.osShippingTotals;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  运费金额
	 */
	public void setOsShippingTotals(java.math.BigDecimal osShippingTotals){
		this.osShippingTotals = osShippingTotals;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  佣金金额
	 */
	@Column(name ="OB_COMMIS_TOTALS",nullable=true,length=32)
	public java.math.BigDecimal getObCommisTotals(){
		return this.obCommisTotals;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  佣金金额
	 */
	public void setObCommisTotals(java.math.BigDecimal obCommisTotals){
		this.obCommisTotals = obCommisTotals;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  退单金额
	 */
	@Column(name ="OB_ORDER_RETURN_TOTALS",nullable=true,length=32)
	public java.math.BigDecimal getObOrderReturnTotals(){
		return this.obOrderReturnTotals;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  退单金额
	 */
	public void setObOrderReturnTotals(java.math.BigDecimal obOrderReturnTotals){
		this.obOrderReturnTotals = obOrderReturnTotals;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  退还佣金
	 */
	@Column(name ="OB_COMMIS_RETURN_TOTALS",nullable=true,length=32)
	public java.math.BigDecimal getObCommisReturnTotals(){
		return this.obCommisReturnTotals;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  退还佣金
	 */
	public void setObCommisReturnTotals(java.math.BigDecimal obCommisReturnTotals){
		this.obCommisReturnTotals = obCommisReturnTotals;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  应结金额
	 */
	@Column(name ="OB_RESULT_TOTALS",nullable=true,length=32)
	public java.math.BigDecimal getObResultTotals(){
		return this.obResultTotals;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  应结金额
	 */
	public void setObResultTotals(java.math.BigDecimal obResultTotals){
		this.obResultTotals = obResultTotals;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生成结算单日期
	 */
	@Column(name ="OB_CREATE_DATE",nullable=true,length=32)
	public java.util.Date getObCreateDate(){
		return this.obCreateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生成结算单日期
	 */
	public void setObCreateDate(java.util.Date obCreateDate){
		this.obCreateDate = obCreateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算单年月份
	 */
	@Column(name ="OB_CREATE_MONTH",nullable=true,length=32)
	public java.lang.String getObCreateMonth(){
		return this.obCreateMonth;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算单年月份
	 */
	public void setObCreateMonth(java.lang.String obCreateMonth){
		this.obCreateMonth = obCreateMonth;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算状态
	 */
	@Column(name ="OB_STATE",nullable=true,length=1)
	public java.lang.String getObState(){
		return this.obState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算状态
	 */
	public void setObState(java.lang.String obState){
		this.obState = obState;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  付款日期
	 */
	@Column(name ="OB_PAY_DATE",nullable=true,length=32)
	public java.util.Date getObPayDate(){
		return this.obPayDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  付款日期
	 */
	public void setObPayDate(java.util.Date obPayDate){
		this.obPayDate = obPayDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付备注
	 */
	@Column(name ="OB_PAY_CONTENT",nullable=true,length=32)
	public java.lang.String getObPayContent(){
		return this.obPayContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付备注
	 */
	public void setObPayContent(java.lang.String obPayContent){
		this.obPayContent = obPayContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算对象ID
	 */
	@Column(name ="OB_PAYER_ID",nullable=true,length=36)
	public java.lang.String getObPayerId(){
		return this.obPayerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算对象ID
	 */
	public void setObPayerId(java.lang.String obPayerId){
		this.obPayerId = obPayerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算对象名称
	 */
	@Column(name ="OB_PAYER_NAME",nullable=true,length=50)
	public java.lang.String getObPayerName(){
		return this.obPayerName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算对象名称
	 */
	public void setObPayerName(java.lang.String obPayerName){
		this.obPayerName = obPayerName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号
	 */
	@Column(name ="OB_PAYER_PHONE",nullable=true,length=20)
	public java.lang.String getObPayerPhone(){
		return this.obPayerPhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号
	 */
	public void setObPayerPhone(java.lang.String obPayerPhone){
		this.obPayerPhone = obPayerPhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商ID
	 */
	@Column(name ="OB_STORE_ID",nullable=true,length=36)
	public java.lang.String getObStoreId(){
		return this.obStoreId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商ID
	 */
	public void setObStoreId(java.lang.String obStoreId){
		this.obStoreId = obStoreId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商名称
	 */
	@Column(name ="OB_STORE_NAME",nullable=true,length=50)
	public java.lang.String getObStoreName(){
		return this.obStoreName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商名称
	 */
	public void setObStoreName(java.lang.String obStoreName){
		this.obStoreName = obStoreName;
	}
}
