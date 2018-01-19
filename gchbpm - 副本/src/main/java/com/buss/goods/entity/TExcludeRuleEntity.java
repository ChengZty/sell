package com.buss.goods.entity;

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
 * @Description: 互斥规则
 * @author onlineGenerator
 * @date 2016-03-29 16:05:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_exclude_rule", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TExcludeRuleEntity implements java.io.Serializable {
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
	/**一级分类ID*/
	private java.lang.String topCategoryId;
	/**二级分类ID*/
	private java.lang.String subCategoryId;
	/**三级分类ID*/
	private java.lang.String thridCategoryId;
	/**一级分类名称*/
	@Excel(name="一级分类名称")
	private java.lang.String topCategoryName;
	/**二级分类名称*/
	@Excel(name="二级分类名称")
	private java.lang.String subCategoryName;
	/**三级分类名称*/
	@Excel(name="三级分类名称")
	private java.lang.String thridCategoryName;
	/**品牌ID*/
	private java.lang.String brandId;
	/**品牌名称*/
	@Excel(name="品牌名称")
	private java.lang.String brandName;
	/**所属零售商ID*/
	private java.lang.String retailerId;
	/**所属零售商*/
	private java.lang.String retailerName;
	/**sql*/
	private java.lang.String sqlStr;
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
	 *@return: java.lang.String  二级分类ID
	 */
	@Column(name ="SUB_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getSubCategoryId(){
		return this.subCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  二级分类ID
	 */
	public void setSubCategoryId(java.lang.String subCategoryId){
		this.subCategoryId = subCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三级分类ID
	 */
	@Column(name ="THRID_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getThridCategoryId(){
		return this.thridCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三级分类ID
	 */
	public void setThridCategoryId(java.lang.String thridCategoryId){
		this.thridCategoryId = thridCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一级分类名称
	 */
	@Column(name ="TOP_CATEGORY_NAME",nullable=true,length=32)
	public java.lang.String getTopCategoryName(){
		return this.topCategoryName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一级分类名称
	 */
	public void setTopCategoryName(java.lang.String topCategoryName){
		this.topCategoryName = topCategoryName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  二级分类名称
	 */
	@Column(name ="SUB_CATEGORY_NAME",nullable=true,length=32)
	public java.lang.String getSubCategoryName(){
		return this.subCategoryName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  二级分类名称
	 */
	public void setSubCategoryName(java.lang.String subCategoryName){
		this.subCategoryName = subCategoryName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三级分类名称
	 */
	@Column(name ="THRID_CATEGORY_NAME",nullable=true,length=32)
	public java.lang.String getThridCategoryName(){
		return this.thridCategoryName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三级分类名称
	 */
	public void setThridCategoryName(java.lang.String thridCategoryName){
		this.thridCategoryName = thridCategoryName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌ID
	 */
	@Column(name ="BRAND_ID",nullable=true,length=500)
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
	 *@return: java.lang.String  品牌名称
	 */
	@Column(name ="BRAND_NAME",nullable=true,length=500)
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
	@Column(name ="RETAILER_NAME",nullable=true,length=32)
	public java.lang.String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(java.lang.String retailerName) {
		this.retailerName = retailerName;
	}

	@Column(name ="SQL_STR",nullable=true,length=500)
	public java.lang.String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(java.lang.String sqlStr) {
		this.sqlStr = sqlStr;
	}
}
