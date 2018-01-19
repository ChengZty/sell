package com.buss.goods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 类目库存
 * @author onlineGenerator
 * @date 2016-05-13 21:25:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_category_store", schema = "")
@SuppressWarnings("serial")
public class TCategoryStoreEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**一级类目*/
	private java.lang.String topCategoryId;
	/**二级类目*/
	private java.lang.String subCategoryId;
	/**三级类目*/
	private java.lang.String thridCategoryId;
	/**零售商*/
	private java.lang.String retailerId;
	/**零售商类别*/
	private java.lang.String retailType;
	/**库存*/
	private java.math.BigDecimal store;
	
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
	 *@return: java.lang.String  一级类目
	 */
	@Column(name ="TOP_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getTopCategoryId(){
		return this.topCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一级类目
	 */
	public void setTopCategoryId(java.lang.String topCategoryId){
		this.topCategoryId = topCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  二级类目
	 */
	@Column(name ="SUB_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getSubCategoryId(){
		return this.subCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  二级类目
	 */
	public void setSubCategoryId(java.lang.String subCategoryId){
		this.subCategoryId = subCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三级类目
	 */
	@Column(name ="THRID_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getThridCategoryId(){
		return this.thridCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三级类目
	 */
	public void setThridCategoryId(java.lang.String thridCategoryId){
		this.thridCategoryId = thridCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商类别
	 */
	@Column(name ="RETAIL_TYPE",nullable=true,length=1)
	public java.lang.String getRetailType(){
		return this.retailType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商类别
	 */
	public void setRetailType(java.lang.String retailType){
		this.retailType = retailType;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  库存
	 */
	@Column(name ="STORE",nullable=true,length=12)
	public java.math.BigDecimal getStore(){
		return this.store;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  库存
	 */
	public void setStore(java.math.BigDecimal store){
		this.store = store;
	}
}
