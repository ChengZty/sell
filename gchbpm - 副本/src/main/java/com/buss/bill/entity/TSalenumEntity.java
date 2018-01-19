package com.buss.bill.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 月统计销售量
 * @author zhangdaihao
 * @date 2016-05-04 23:22:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_salenum", schema = "")
@SuppressWarnings("serial")
public class TSalenumEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**销售日期*/
	private java.util.Date date;
	/**件数销量*/
	private BigDecimal salenum;
	/**金额*/
	private BigDecimal saleprice;
	/**当前价格*/
	private BigDecimal currentPrice;
	/**商品id*/
	private java.lang.String goodsId;
	/**一级分类*/
	private java.lang.String topCategoryId;
	/**二级分类*/
	private java.lang.String subCategoryId;
	/**三级分类*/
	private java.lang.String thridCategoryId;
	/**商品名称*/
	private java.lang.String goodsName;
	/**品牌ID*/
	private java.lang.String brandId;
	/**顾客userid*/
	private java.lang.String userId;
	/**所属零售商*/
	private java.lang.String retailerId;
	/**商品类型*/
	private java.lang.String goodsType;
	/**导购ID*/
	private java.lang.String guideId;
	/**商品编码**/
	private java.lang.String goodsCode;
	/**商品图片**/
	private java.lang.String pic;
	/**商品规格**/
	private java.lang.String specId;
	/**规格一**/
	private java.lang.String specInfoOne;
	/**规格二**/
	private java.lang.String specInfoTwo;
	/**规格三**/
	private java.lang.String specInfoThrid;
	/**零售商类型**/
	private java.lang.String retailerType;
	/**订单ID**/
	private java.lang.String orderId;
	/**子订单ID	**/
	private java.lang.String subOrderId;
	
	
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
	 *@return: java.util.Date  销售日期
	 */
	@Column(name ="DATE",nullable=true)
	public java.util.Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  销售日期
	 */
	public void setDate(java.util.Date date){
		this.date = date;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  件数销量
	 */
	@Column(name ="SALENUM",nullable=true,precision=12,scale=0)
	public BigDecimal getSalenum(){
		return this.salenum;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  件数销量
	 */
	public void setSalenum(BigDecimal salenum){
		this.salenum = salenum;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  金额
	 */
	@Column(name ="SALEPRICE",nullable=true,precision=10,scale=0)
	public BigDecimal getSaleprice(){
		return this.saleprice;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  金额
	 */
	public void setSaleprice(BigDecimal saleprice){
		this.saleprice = saleprice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品id
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品id
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一级分类
	 */
	@Column(name ="TOP_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getTopCategoryId(){
		return this.topCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一级分类
	 */
	public void setTopCategoryId(java.lang.String topCategoryId){
		this.topCategoryId = topCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  二级分类
	 */
	@Column(name ="SUB_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getSubCategoryId(){
		return this.subCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  二级分类
	 */
	public void setSubCategoryId(java.lang.String subCategoryId){
		this.subCategoryId = subCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三级分类
	 */
	@Column(name ="THRID_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getThridCategoryId(){
		return this.thridCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三级分类
	 */
	public void setThridCategoryId(java.lang.String thridCategoryId){
		this.thridCategoryId = thridCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=false,length=150)
	public java.lang.String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsName(java.lang.String goodsName){
		this.goodsName = goodsName;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属零售商
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属零售商
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品类型
	 */
	@Column(name ="GOODS_TYPE",nullable=true,length=2)
	public java.lang.String getGoodsType(){
		return this.goodsType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品类型
	 */
	public void setGoodsType(java.lang.String goodsType){
		this.goodsType = goodsType;
	}
	
	@Column(name ="USER_ID",nullable=true,precision=36)
	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	@Column(name ="GUIDE_ID",nullable=true)
	public java.lang.String getGuideId() {
		return guideId;
	}

	public void setGuideId(java.lang.String guideId) {
		this.guideId = guideId;
	}
	
	@Column(name ="GOODS_CODE",nullable=true)
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@Column(name ="PIC",nullable=true)
	public java.lang.String getPic() {
		return pic;
	}

	public void setPic(java.lang.String pic) {
		this.pic = pic;
	}
	@Column(name ="SPEC_ID",nullable=true)
	public java.lang.String getSpecId() {
		return specId;
	}

	public void setSpecId(java.lang.String specId) {
		this.specId = specId;
	}
	
	@Column(name ="RETAILER_TYPE",nullable=true)
	public java.lang.String getRetailerType() {
		return retailerType;
	}

	public void setRetailerType(java.lang.String retailerType) {
		this.retailerType = retailerType;
	}
	@Column(name ="ORDER_ID",nullable=true)
	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}
	@Column(name ="SUB_ORDER_ID",nullable=true)
	public java.lang.String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}
	@Column(name ="SPEC_INFO_ONE",nullable=true)
	public java.lang.String getSpecInfoOne() {
		return specInfoOne;
	}

	public void setSpecInfoOne(java.lang.String specInfoOne) {
		this.specInfoOne = specInfoOne;
	}
	@Column(name ="SPEC_INFO_TWO",nullable=true)
	public java.lang.String getSpecInfoTwo() {
		return specInfoTwo;
	}

	public void setSpecInfoTwo(java.lang.String specInfoTwo) {
		this.specInfoTwo = specInfoTwo;
	}
	@Column(name ="SPEC_INFO_THRID",nullable=true)
	public java.lang.String getSpecInfoThrid() {
		return specInfoThrid;
	}

	public void setSpecInfoThrid(java.lang.String specInfoThrid) {
		this.specInfoThrid = specInfoThrid;
	}
	@Column(name ="CURRENT_PRICE",nullable=true)
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	
}


