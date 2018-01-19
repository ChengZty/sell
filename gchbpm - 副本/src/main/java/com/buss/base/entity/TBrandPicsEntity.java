package com.buss.base.entity;

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
import org.hibernate.annotations.Where;

import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 品牌广告图片
 * @author onlineGenerator
 * @date 2016-08-17 18:10:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_brand_pics", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TBrandPicsEntity implements java.io.Serializable {
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
	/**品牌ID*/
	private java.lang.String brandId;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**排序*/
	@Excel(name="排序")
	private java.lang.Integer orderNum;
	/**图片地址*/
	@Excel(name="图片地址")
	private java.lang.String picUrl;
	/**图片内容*/
	@Excel(name="图片内容")
	private java.lang.String picMapContent;
	/**说明*/
	@Excel(name="说明")
	private java.lang.String remark;
	
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
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="ORDER_NUM",nullable=true,length=3)
	public java.lang.Integer getOrderNum(){
		return this.orderNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setOrderNum(java.lang.Integer orderNum){
		this.orderNum = orderNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片地址
	 */
	@Column(name ="PIC_URL",nullable=true,length=150)
	public java.lang.String getPicUrl(){
		return this.picUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片地址
	 */
	public void setPicUrl(java.lang.String picUrl){
		this.picUrl = picUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片内容
	 */
	@Column(name ="PIC_MAP_CONTENT",nullable=true,length=500)
	public java.lang.String getPicMapContent(){
		return this.picMapContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片内容
	 */
	public void setPicMapContent(java.lang.String picMapContent){
		this.picMapContent = picMapContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  说明
	 */
	@Column(name ="REMARK",nullable=true,length=50)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  说明
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
}
