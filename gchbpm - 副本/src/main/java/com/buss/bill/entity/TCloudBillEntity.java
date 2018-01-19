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
 * @Description: 云商结算明细
 * @author onlineGenerator
 * @date 2016-05-20 16:50:59
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_cloud_bill", schema = "")
@SuppressWarnings("serial")
public class TCloudBillEntity implements java.io.Serializable {
	public static final String BUSINESS_TYPE_1 = "1";//确认收货
	public static final String BUSINESS_TYPE_2 = "2";//同意退货(退款)
	/**主键*/
	private java.lang.String id;
	/**财务结算表ID*/
	private java.lang.String finBillId;
	/**流水时间*/
	private java.util.Date addTime;
	/**订/退款单号*/
	@Excel(name="订/退款单号",width=20)
	private java.lang.String orderNo;
	/**子订单编号*/
	@Excel(name="子订单编号",width=20)
	private java.lang.String subOrderNo;
	/**完成日期*/
	@Excel(name="完成日期",format = "yyyy-MM-dd")
	private java.util.Date businessDate;
	/**金额（退款金额（退款），或者商品总额（收货））*/
	@Excel(name="金额",width=10)
	private java.math.BigDecimal money;
	/**云商实收*/
	@Excel(name="零售商实收",width=10)
	private java.math.BigDecimal cloudMoney;
	/**运费*/
	@Excel(name="运费",width=10)
	private java.math.BigDecimal fareMoney;
	/**提成的属零售商ID*/
	private java.lang.String storeId;
	/**货品所属零售商*/
	private java.lang.String toStoreGoodsId;
	/**提成的零售商名字*/
	@Excel(name="零售商",width=20)
	private java.lang.String toStoreGoodsName;
	/**一级分类ID*/
	private java.lang.String topCategoryId;
	/**一级分类ID*/
	private java.lang.String subCategoryId;
	/**一级分类ID*/
	private java.lang.String thridCategoryId;
	/**品牌ID*/
	private java.lang.String brandId;
	/**零售商类型*/
	private java.lang.String toStoreType;
	/**业务类型*/
	private java.lang.String businessType;
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
	 *@return: java.lang.String  财务结算表ID
	 */
	@Column(name ="FIN_BILL_ID",nullable=true,length=36)
	public java.lang.String getFinBillId(){
		return this.finBillId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  财务结算表ID
	 */
	public void setFinBillId(java.lang.String finBillId){
		this.finBillId = finBillId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  流水时间
	 */
	@Column(name ="ADD_TIME",nullable=true,length=20)
	public java.util.Date getAddTime(){
		return this.addTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  流水时间
	 */
	public void setAddTime(java.util.Date addTime){
		this.addTime = addTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单退款单号
	 */
	@Column(name ="ORDER_NO",nullable=true,length=40)
	public java.lang.String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单退款单号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  子订单编号
	 */
	@Column(name ="SUB_ORDER_NO",nullable=true,length=40)
	public java.lang.String getSubOrderNo(){
		return this.subOrderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  子订单编号
	 */
	public void setSubOrderNo(java.lang.String subOrderNo){
		this.subOrderNo = subOrderNo;
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  云商实收
	 */
	@Column(name ="CLOUD_MONEY",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getCloudMoney(){
		return this.cloudMoney;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  云商实收
	 */
	public void setCloudMoney(java.math.BigDecimal cloudMoney){
		this.cloudMoney = cloudMoney;
	}
	@Column(name ="FARE_MONEY",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getFareMoney() {
		return fareMoney;
	}

	public void setFareMoney(java.math.BigDecimal fareMoney) {
		this.fareMoney = fareMoney;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购所属零售商ID
	 */
	@Column(name ="STORE_ID",nullable=true,length=36)
	public java.lang.String getStoreId(){
		return this.storeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购所属零售商ID
	 */
	public void setStoreId(java.lang.String storeId){
		this.storeId = storeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货品所属零售商
	 */
	@Column(name ="TO_STORE_GOODS_ID",nullable=true,length=36)
	public java.lang.String getToStoreGoodsId(){
		return this.toStoreGoodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  货品所属零售商
	 */
	public void setToStoreGoodsId(java.lang.String toStoreGoodsId){
		this.toStoreGoodsId = toStoreGoodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货品所属零售商名字
	 */
	@Column(name ="TO_STORE_GOODS_NAME",nullable=true,length=50)
	public java.lang.String getToStoreGoodsName(){
		return this.toStoreGoodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  货品所属零售商名字
	 */
	public void setToStoreGoodsName(java.lang.String toStoreGoodsName){
		this.toStoreGoodsName = toStoreGoodsName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一级分类ID
	 */
	@Column(name ="TOP_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getTopCategoryId(){
		return this.topCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一级分类ID
	 */
	public void setTopCategoryId(java.lang.String topCategoryId){
		this.topCategoryId = topCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一级分类ID
	 */
	@Column(name ="SUB_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getSubCategoryId(){
		return this.subCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一级分类ID
	 */
	public void setSubCategoryId(java.lang.String subCategoryId){
		this.subCategoryId = subCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一级分类ID
	 */
	@Column(name ="THRID_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getThridCategoryId(){
		return this.thridCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一级分类ID
	 */
	public void setThridCategoryId(java.lang.String thridCategoryId){
		this.thridCategoryId = thridCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌ID
	 */
	@Column(name ="BRAND_ID",nullable=true,length=36)
	public java.lang.String getBrandId(){
		return this.brandId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品牌ID
	 */
	public void setBrandId(java.lang.String brandId){
		this.brandId = brandId;
	}
	@Column(name ="money")
	public java.math.BigDecimal getMoney() {
		return money;
	}

	public void setMoney(java.math.BigDecimal money) {
		this.money = money;
	}
	@Column(name ="to_Store_Type")
	public java.lang.String getToStoreType() {
		return toStoreType;
	}

	public void setToStoreType(java.lang.String toStoreType) {
		this.toStoreType = toStoreType;
	}
	@Column(name ="business_Type")
	public java.lang.String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(java.lang.String businessType) {
		this.businessType = businessType;
	}
	
}
