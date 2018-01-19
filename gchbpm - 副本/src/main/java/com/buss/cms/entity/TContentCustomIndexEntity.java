package com.buss.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 零售商自定义栏目
 * @author onlineGenerator
 * @date 2016-09-23 11:32:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_content_custom_index", schema = "")
@SuppressWarnings("serial")
public class TContentCustomIndexEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Long id;
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
	/**零售商ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**内容ID*/
	@Excel(name="内容ID")
	private java.lang.Long contentId;
	/**排序*/
	@Excel(name="排序")
	private java.lang.Integer sortOrder;
	/**是否显示.*/
	@Excel(name="是否显示.")
	private java.lang.Boolean isShow;
	/**子标题*/
	@Excel(name="子标题")
	private java.lang.String subTitle;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",nullable=false,length=20)
	public java.lang.Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  主键
	 */
	public void setId(java.lang.Long id){
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  内容ID
	 */
	@Column(name ="CONTENT_ID",nullable=true,length=20)
	public java.lang.Long getContentId(){
		return this.contentId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  内容ID
	 */
	public void setContentId(java.lang.Long contentId){
		this.contentId = contentId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排序
	 */
	@Column(name ="SORT_ORDER",nullable=true,length=32)
	public java.lang.Integer getSortOrder(){
		return this.sortOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排序
	 */
	public void setSortOrder(java.lang.Integer sortOrder){
		this.sortOrder = sortOrder;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否显示.
	 */
	@Column(name ="IS_SHOW",nullable=true,length=1)
	public java.lang.Boolean getIsShow(){
		return this.isShow;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否显示.
	 */
	public void setIsShow(java.lang.Boolean isShow){
		this.isShow = isShow;
	}
	@Column(name ="SUB_TITLE",nullable=true)
	public java.lang.String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(java.lang.String subTitle) {
		this.subTitle = subTitle;
	}

}
