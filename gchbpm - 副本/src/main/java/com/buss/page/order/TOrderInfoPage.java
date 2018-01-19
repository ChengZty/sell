
package com.buss.page.order;
import com.buss.order.entity.TOrderDetailEntity;

import java.util.List;
import java.util.ArrayList;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

/**   
 * @Title: Entity
 * @Description: 订单信息
 * @author onlineGenerator
 * @date 2016-03-15 17:20:38
 * @version V1.0   
 *
 */
public class TOrderInfoPage implements java.io.Serializable {
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
	/**订单号*/
    @Excel(name="订单号")
	private java.lang.String orderNo;
	/**下单时间*/
    @Excel(name="下单时间",format = "yyyy-MM-dd")
	private java.util.Date orderTime;
	/**件数*/
    @Excel(name="件数")
	private java.math.BigDecimal quantity;
	/**买家帐号*/
    @Excel(name="买家帐号")
	private java.lang.String buyerAccount;
	/**收货地址*/
    @Excel(name="收货地址")
	private java.lang.String address;
	/**支付方式*/
    @Excel(name="支付方式")
	private java.lang.String payMethod;
	/**支付帐号*/
    @Excel(name="支付帐号")
	private java.lang.String payAccount;
	/**支付时间*/
    @Excel(name="支付时间",format = "yyyy-MM-dd")
	private java.util.Date payTime;
	/**订单状态*/
    @Excel(name="订单状态")
	private java.lang.String orderStatus;
	/**支付金额*/
    @Excel(name="支付金额")
	private java.math.BigDecimal payAmount;
	/**运费*/
    @Excel(name="运费")
	private java.math.BigDecimal fareAmount;
	/**商品总额*/
    @Excel(name="商品总额")
	private java.math.BigDecimal goodsAmount;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	 *@return: java.lang.String  订单号
	 */
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下单时间
	 */
	public java.util.Date getOrderTime(){
		return this.orderTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下单时间
	 */
	public void setOrderTime(java.util.Date orderTime){
		this.orderTime = orderTime;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  件数
	 */
	public java.math.BigDecimal getQuantity(){
		return this.quantity;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  件数
	 */
	public void setQuantity(java.math.BigDecimal quantity){
		this.quantity = quantity;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  买家帐号
	 */
	public java.lang.String getBuyerAccount(){
		return this.buyerAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  买家帐号
	 */
	public void setBuyerAccount(java.lang.String buyerAccount){
		this.buyerAccount = buyerAccount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货地址
	 */
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付方式
	 */
	public java.lang.String getPayMethod(){
		return this.payMethod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付方式
	 */
	public void setPayMethod(java.lang.String payMethod){
		this.payMethod = payMethod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付帐号
	 */
	public java.lang.String getPayAccount(){
		return this.payAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付帐号
	 */
	public void setPayAccount(java.lang.String payAccount){
		this.payAccount = payAccount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  支付时间
	 */
	public java.util.Date getPayTime(){
		return this.payTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  支付时间
	 */
	public void setPayTime(java.util.Date payTime){
		this.payTime = payTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单状态
	 */
	public java.lang.String getOrderStatus(){
		return this.orderStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单状态
	 */
	public void setOrderStatus(java.lang.String orderStatus){
		this.orderStatus = orderStatus;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  支付金额
	 */
	public java.math.BigDecimal getPayAmount(){
		return this.payAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  支付金额
	 */
	public void setPayAmount(java.math.BigDecimal payAmount){
		this.payAmount = payAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  运费
	 */
	public java.math.BigDecimal getFareAmount(){
		return this.fareAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  运费
	 */
	public void setFareAmount(java.math.BigDecimal fareAmount){
		this.fareAmount = fareAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  商品总额
	 */
	public java.math.BigDecimal getGoodsAmount(){
		return this.goodsAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  商品总额
	 */
	public void setGoodsAmount(java.math.BigDecimal goodsAmount){
		this.goodsAmount = goodsAmount;
	}

	/**保存-订单明细*/
    @ExcelCollection(name="订单明细")
	private List<TOrderDetailEntity> tOrderDetails = new ArrayList<TOrderDetailEntity>();
		public List<TOrderDetailEntity> getTOrderDetails() {
		return tOrderDetails;
		}
		public void setTOrderDetails(List<TOrderDetailEntity> tOrderDetails) {
		this.tOrderDetails = tOrderDetails;
		}
}
