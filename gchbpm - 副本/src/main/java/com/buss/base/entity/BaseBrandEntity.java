package com.buss.base.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 品牌
 * @author onlineGenerator
 * @date 2016-03-08 20:08:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "base_brand", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class BaseBrandEntity implements java.io.Serializable {
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
	/**品牌编码*/
	@Excel(name="品牌编码")
	private java.lang.String brandCode;
	/**品牌名称*/
	@Excel(name="品牌名称",width=20)
	private java.lang.String brandName;
	/**品牌类别*/
//	@Excel(name="品牌类别",width=15)
//	private java.lang.String brandType;
	/**图片*/
	private java.lang.String brandPic;
	/**品牌大图*/
	private java.lang.String bigPic;
	/**排序*/
	@Excel(name="排序")
	private java.lang.Integer sortNo;
	/**状态*/
	private java.lang.String status;
	/**状态*/
	@Excel(name="一句话",width=30)
	private java.lang.String brandSummary;
	/**零售商ID*/
	private java.lang.String retailerId;
	//品牌广告图片列表
	private List<TBrandPicsEntity> detailPics = new ArrayList<TBrandPicsEntity>();
	private TBrandDescEntity brandDesc = new TBrandDescEntity();
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
	 *@return: java.lang.String  品牌名称
	 */
	@Column(name ="BRAND_NAME",nullable=true,length=50)
	public java.lang.String getBrandName(){
		return this.brandName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品牌名称
	 */
	public void setBrandName(java.lang.String brandName){
		this.brandName = brandName;
	}
	@Column(name ="BRAND_CODE",nullable=true,length=10)
	public java.lang.String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(java.lang.String brandCode) {
		this.brandCode = brandCode;
	}

//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  品牌类别
//	 */
//	@Column(name ="BRAND_TYPE",nullable=true,length=50)
//	public java.lang.String getBrandType(){
//		return this.brandType;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  品牌类别
//	 */
//	public void setBrandType(java.lang.String brandType){
//		this.brandType = brandType;
//	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片
	 */
	@Column(name ="BRAND_PIC",nullable=true,length=150)
	public java.lang.String getBrandPic(){
		return this.brandPic;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片
	 */
	public void setBrandPic(java.lang.String brandPic){
		this.brandPic = brandPic;
	}
	@Column(name ="BIG_PIC",nullable=true,length=150)
	public java.lang.String getBigPic() {
		return bigPic;
	}

	public void setBigPic(java.lang.String bigPic) {
		this.bigPic = bigPic;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORT_NO",nullable=true,length=5)
	public java.lang.Integer getSortNo(){
		return this.sortNo;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSortNo(java.lang.Integer sortNo){
		this.sortNo = sortNo;
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
	@Column(name ="brand_summary")
	public java.lang.String getBrandSummary() {
		return brandSummary;
	}

	public void setBrandSummary(java.lang.String brandSummary) {
		this.brandSummary = brandSummary;
	}

	@Transient
	public TBrandDescEntity getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(TBrandDescEntity brandDesc) {
		this.brandDesc = brandDesc;
	}

	@Transient
	public List<TBrandPicsEntity> getDetailPics() {
		return detailPics;
	}

	public void setDetailPics(List<TBrandPicsEntity> detailPics) {
		this.detailPics = detailPics;
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
	
}
