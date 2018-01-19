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
 * @Description: 活动奖励明细
 * @author onlineGenerator
 * @date 2016-11-30 20:16:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_activity_bill", schema = "")
@SuppressWarnings("serial")
public class TActivityBillEntity implements java.io.Serializable {
	public static final String BUSINESS_TYPE_1 = "1";//确认收货（后改为付款）
	public static final String BUSINESS_TYPE_2 = "2";//同意退货(退款)
	/**主键*/
	private java.lang.String id;
	/**财务结算ID*/
	private java.lang.String finBillId;
	/**添加时间*/
	private java.util.Date addTime;
	/**下单时间*/
	private java.util.Date orderTime;
	/**业务日期*/
	@Excel(name="业务日期")
	private java.util.Date businessDate;
	/**业务类型*/
	@Excel(name="业务类型")
	private java.lang.String businessType;
	/**订单号*/
	private java.lang.String orderNo;
	/**子单号*/
	@Excel(name="子单号")
	private java.lang.String subOrderNo;
	/**商品ID*/
	private java.lang.String goodsId;
	/**商品所属零售商*/
	private java.lang.String toStoreGoodsId;
	/**商品所属零售商*/
	@Excel(name="商品所属零售商")
	private java.lang.String toStoreName;
	/**所属零售商类型*/
	private java.lang.String storeType;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**导购ID*/
	private java.lang.String guideId;
	/**导购名称*/
	@Excel(name="导购")
	private java.lang.String guideName;
	/**活动ID*/
	private java.lang.String finActId;
	/**活动名称*/
	@Excel(name="活动名称")
	private java.lang.String finActTitle;
	/**活动奖励*/
	@Excel(name="活动奖励")
	private java.math.BigDecimal money;
	
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
	 *@return: java.lang.String  财务结算ID
	 */
	@Column(name ="FIN_BILL_ID",nullable=true,length=36)
	public java.lang.String getFinBillId(){
		return this.finBillId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  财务结算ID
	 */
	public void setFinBillId(java.lang.String finBillId){
		this.finBillId = finBillId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  添加时间
	 */
	@Column(name ="ADD_TIME",nullable=true,length=20)
	public java.util.Date getAddTime(){
		return this.addTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  添加时间
	 */
	public void setAddTime(java.util.Date addTime){
		this.addTime = addTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下单时间
	 */
	@Column(name ="ORDER_TIME",nullable=true,length=20)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  业务日期
	 */
	@Column(name ="BUSINESS_DATE",nullable=true,length=20)
	public java.util.Date getBusinessDate(){
		return this.businessDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  业务日期
	 */
	public void setBusinessDate(java.util.Date businessDate){
		this.businessDate = businessDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务类型
	 */
	@Column(name ="BUSINESS_TYPE",nullable=true,length=1)
	public java.lang.String getBusinessType(){
		return this.businessType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型
	 */
	public void setBusinessType(java.lang.String businessType){
		this.businessType = businessType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单号
	 */
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
	@Column(name ="SUB_ORDER_NO",nullable=true,length=32)
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
	 *@return: java.lang.String  商品ID
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品ID
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品所属零售商
	 */
	@Column(name ="TO_STORE_GOODS_ID",nullable=true,length=36)
	public java.lang.String getToStoreGoodsId(){
		return this.toStoreGoodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品所属零售商
	 */
	public void setToStoreGoodsId(java.lang.String toStoreGoodsId){
		this.toStoreGoodsId = toStoreGoodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属零售商类型
	 */
	@Column(name ="STORE_TYPE",nullable=true,length=32)
	public java.lang.String getStoreType(){
		return this.storeType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属零售商类型
	 */
	public void setStoreType(java.lang.String storeType){
		this.storeType = storeType;
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
	 *@return: java.lang.String  导购ID
	 */
	@Column(name ="GUIDE_ID",nullable=true,length=36)
	public java.lang.String getGuideId(){
		return this.guideId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购ID
	 */
	public void setGuideId(java.lang.String guideId){
		this.guideId = guideId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  活动ID
	 */
	@Column(name ="FIN_ACT_ID",nullable=true,length=36)
	public java.lang.String getFinActId(){
		return this.finActId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动ID
	 */
	public void setFinActId(java.lang.String finActId){
		this.finActId = finActId;
	}
	@Column(name ="money",nullable=true)
	public java.math.BigDecimal getMoney() {
		return money;
	}

	public void setMoney(java.math.BigDecimal money) {
		this.money = money;
	}
	@Column(name ="to_store_name",nullable=true)
	public java.lang.String getToStoreName() {
		return toStoreName;
	}

	public void setToStoreName(java.lang.String toStoreName) {
		this.toStoreName = toStoreName;
	}
	@Column(name ="guide_name",nullable=true)
	public java.lang.String getGuideName() {
		return guideName;
	}

	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	@Column(name ="fin_act_title",nullable=true)
	public java.lang.String getFinActTitle() {
		return finActTitle;
	}

	public void setFinActTitle(java.lang.String finActTitle) {
		this.finActTitle = finActTitle;
	}
	
}
