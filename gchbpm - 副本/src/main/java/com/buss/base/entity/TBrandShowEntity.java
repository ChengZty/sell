package com.buss.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 零售商品牌展示
 * @author onlineGenerator
 * @date 2016-06-17 14:55:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_brand_show", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TBrandShowEntity implements java.io.Serializable {
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
	/**零售商ID*/
	private java.lang.String retailerId;
	/**品牌ID*/
	private java.lang.String brandId;
	/**满邮金额*/
	private java.math.BigDecimal freeAmount;
	/**排序*/
	private java.lang.Integer orderNum;
	/**零售商品牌基础运费*/
	private java.math.BigDecimal fare;
	/**是签约品牌（自有品牌）  1：是，0：不是，默认为0*/
	private java.lang.String isSelfBrand = "0";
	
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
	@Column(name ="FREE_AMOUNT",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getFreeAmount() {
		return freeAmount;
	}

	public void setFreeAmount(java.math.BigDecimal freeAmount) {
		this.freeAmount = freeAmount;
	}
	@Column(name ="ORDER_NUM",nullable=true)
	public java.lang.Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(java.lang.Integer orderNum) {
		this.orderNum = orderNum;
	}
	@Column(name ="fare",nullable=true)
	public java.math.BigDecimal getFare() {
		return fare;
	}

	public void setFare(java.math.BigDecimal fare) {
		this.fare = fare;
	}
	@Column(name ="IS_SELF_BRAND",nullable=false)
	public java.lang.String getIsSelfBrand() {
		return isSelfBrand;
	}

	public void setIsSelfBrand(java.lang.String isSelfBrand) {
		this.isSelfBrand = isSelfBrand;
	}
	//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  品牌名称
//	 */
//	@Column(name ="BRAND_NAME",nullable=true,length=50)
//	public java.lang.String getBrandName(){
//		return this.brandName;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  品牌名称
//	 */
//	public void setBrandName(java.lang.String brandName){
//		this.brandName = brandName;
//	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  品牌编码
//	 */
//	@Column(name ="BRAND_CODE",nullable=true,length=10)
//	public java.lang.String getBrandCode(){
//		return this.brandCode;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  品牌编码
//	 */
//	public void setBrandCode(java.lang.String brandCode){
//		this.brandCode = brandCode;
//	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  品牌图片
//	 */
//	@Column(name ="BRAND_PIC",nullable=true,length=100)
//	public java.lang.String getBrandPic(){
//		return this.brandPic;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  品牌图片
//	 */
//	public void setBrandPic(java.lang.String brandPic){
//		this.brandPic = brandPic;
//	}


}
