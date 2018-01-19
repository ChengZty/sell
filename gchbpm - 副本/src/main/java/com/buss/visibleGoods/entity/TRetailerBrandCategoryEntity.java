package com.buss.visibleGoods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 零售商品牌分类选择
 * @author onlineGenerator
 * @date 2016-12-19 10:54:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_retailer_brand_category", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TRetailerBrandCategoryEntity implements java.io.Serializable {
	/**id*/
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
	/**其他零售商ID*/
	private java.lang.String otherRetailerId;
	/**品牌ID*/
	@Excel(name="品牌ID")
	private java.lang.String brandId;
	/**一级分类*/
	@Excel(name="一级分类")
	private java.lang.String topCategoryId;
	/**二级分类*/
	@Excel(name="二级分类")
	private java.lang.String subCategoryId;
	/**三级分类*/
	@Excel(name="三级分类")
	private java.lang.String thridCategoryId;
	
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
	 *@return: java.lang.String  其他零售商ID
	 */
	@Column(name ="OTHER_RETAILER_ID",nullable=true,length=36)
	public java.lang.String getOtherRetailerId(){
		return this.otherRetailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  其他零售商ID
	 */
	public void setOtherRetailerId(java.lang.String otherRetailerId){
		this.otherRetailerId = otherRetailerId;
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
}
