package com.buss.bill.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 结算明细
 * @author onlineGenerator
 * @date 2016-04-13 19:03:18
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_order_bill_detail", schema = "")
@SuppressWarnings("serial")
public class TOrderBillDetailEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**订单编号*/
	private java.lang.String obOrderSn;
	/**年份*/
	private java.lang.Integer obYear;
	/**月份*/
	private java.lang.Integer obMonth;
	/**开始日期*/
	private java.util.Date obStartDate;
	/**结束日期*/
	private java.util.Date obEndDate;
	/**出账日期*/
	private java.util.Date obPayDate;
	/**订单金额*/
	private java.math.BigDecimal obOrderTotals;
	/**运费*/
	@Excel(name="运费")
	private java.math.BigDecimal obShippingTotals;
	/**佣金金额*/
	@Excel(name="佣金金额")
	private java.math.BigDecimal obCommisTotals;
	/**退款金额*/
	@Excel(name="退款金额")
	private java.math.BigDecimal obOrderReturnTotals;
	/**退还佣金*/
	@Excel(name="退还佣金")
	private java.math.BigDecimal obCommisReturnTotals;
	/**本期应结*/
	@Excel(name="本期应结")
	private java.math.BigDecimal obResultTotals;
	/**状态*/
	@Excel(name="状态")
	private java.lang.String obState;
	/**结算对象ID*/
	@Excel(name="结算对象ID")
	private java.lang.String obPayerId;
	/**结算对象名称*/
	@Excel(name="结算对象名称")
	private java.lang.String obPayerName;
	/**所属零售商*/
	@Excel(name="所属零售商")
	private java.lang.String obStoreId;
	
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
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="OB_ORDER_SN",nullable=true,length=36)
	public java.lang.String getObOrderSn(){
		return this.obOrderSn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setObOrderSn(java.lang.String obOrderSn){
		this.obOrderSn = obOrderSn;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  年份
	 */
	@Column(name ="OB_YEAR",nullable=true,length=4)
	public java.lang.Integer getObYear(){
		return this.obYear;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  年份
	 */
	public void setObYear(java.lang.Integer obYear){
		this.obYear = obYear;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  月份
	 */
	@Column(name ="OB_MONTH",nullable=true,length=2)
	public java.lang.Integer getObMonth(){
		return this.obMonth;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  月份
	 */
	public void setObMonth(java.lang.Integer obMonth){
		this.obMonth = obMonth;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始日期
	 */
	@Column(name ="OB_START_DATE",nullable=true,length=50)
	public java.util.Date getObStartDate(){
		return this.obStartDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始日期
	 */
	public void setObStartDate(java.util.Date obStartDate){
		this.obStartDate = obStartDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束日期
	 */
	@Column(name ="OB_END_DATE",nullable=true,length=50)
	public java.util.Date getObEndDate(){
		return this.obEndDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束日期
	 */
	public void setObEndDate(java.util.Date obEndDate){
		this.obEndDate = obEndDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  出账日期
	 */
	@Column(name ="OB_PAY_DATE",nullable=true,length=20)
	public java.util.Date getObPayDate(){
		return this.obPayDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  出账日期
	 */
	public void setObPayDate(java.util.Date obPayDate){
		this.obPayDate = obPayDate;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  订单金额
	 */
	@Column(name ="OB_ORDER_TOTALS",nullable=true,scale=2,length=12)
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
	 *@return: java.math.BigDecimal  运费
	 */
	@Column(name ="OB_SHIPPING_TOTALS",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getObShippingTotals(){
		return this.obShippingTotals;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  运费
	 */
	public void setObShippingTotals(java.math.BigDecimal obShippingTotals){
		this.obShippingTotals = obShippingTotals;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  佣金金额
	 */
	@Column(name ="OB_COMMIS_TOTALS",nullable=true,scale=2,length=12)
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
	 *@return: java.math.BigDecimal  退款金额
	 */
	@Column(name ="OB_ORDER_RETURN_TOTALS",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getObOrderReturnTotals(){
		return this.obOrderReturnTotals;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  退款金额
	 */
	public void setObOrderReturnTotals(java.math.BigDecimal obOrderReturnTotals){
		this.obOrderReturnTotals = obOrderReturnTotals;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  退还佣金
	 */
	@Column(name ="OB_COMMIS_RETURN_TOTALS",nullable=true,scale=2,length=12)
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
	 *@return: java.math.BigDecimal  本期应结
	 */
	@Column(name ="OB_RESULT_TOTALS",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getObResultTotals(){
		return this.obResultTotals;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期应结
	 */
	public void setObResultTotals(java.math.BigDecimal obResultTotals){
		this.obResultTotals = obResultTotals;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="OB_STATE",nullable=true,length=1)
	public java.lang.String getObState(){
		return this.obState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setObState(java.lang.String obState){
		this.obState = obState;
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
	 *@return: java.lang.String  所属零售商
	 */
	@Column(name ="OB_STORE_ID",nullable=true,length=36)
	public java.lang.String getObStoreId(){
		return this.obStoreId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属零售商
	 */
	public void setObStoreId(java.lang.String obStoreId){
		this.obStoreId = obStoreId;
	}
}
