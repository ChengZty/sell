package com.buss.user.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 导购标签
 * @author onlineGenerator
 * @date 2016-04-18 16:38:48
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_person_tags", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TPersonTagsEntity implements java.io.Serializable {
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
	/**标签 1：专家，2：达人*/
	private java.lang.String tagCode;
	/**导购用户ID*/
	private java.lang.String userId;
	/**类目*/
	private java.lang.String categries;
	/**证书图片*/
	private java.lang.String picUrl;
	/**一级类目ID*/
	private java.lang.String topCategoryId;
	/**一级类目名*/
	private java.lang.String topCategoryName;
	/**职称编码*/
	private java.lang.String professionalCode;
	/**职称名字*/
	private java.lang.String professionalName;
	
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
	 *@return: java.lang.String  标签
	 */
	@Column(name ="TAG_CODE",nullable=true,length=2)
	public java.lang.String getTagCode(){
		return this.tagCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标签
	 */
	public void setTagCode(java.lang.String tagCode){
		this.tagCode = tagCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购用户ID
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购用户ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	@Transient
	public java.lang.String getCategries() {
		return categries;
	}

	public void setCategries(java.lang.String categries) {
		this.categries = categries;
	}
	@Column(name ="pic_url",nullable=true,length=800)
	public java.lang.String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(java.lang.String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一级类目ID
	 */
	@Column(name ="TOP_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getTopCategoryId(){
		return this.topCategoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一级类目ID
	 */
	public void setTopCategoryId(java.lang.String topCategoryId){
		this.topCategoryId = topCategoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  一级类目名
	 */
	@Column(name ="TOP_CATEGORY_NAME",nullable=true,length=36)
	public java.lang.String getTopCategoryName(){
		return this.topCategoryName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  一级类目名
	 */
	public void setTopCategoryName(java.lang.String topCategoryName){
		this.topCategoryName = topCategoryName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职称编码
	 */
	@Column(name ="PROFESSIONAL_CODE",nullable=true,length=10)
	public java.lang.String getProfessionalCode(){
		return this.professionalCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职称编码
	 */
	public void setProfessionalCode(java.lang.String professionalCode){
		this.professionalCode = professionalCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职称名字
	 */
	@Column(name ="PROFESSIONAL_NAME",nullable=true,length=32)
	public java.lang.String getProfessionalName(){
		return this.professionalName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职称名字
	 */
	public void setProfessionalName(java.lang.String professionalName){
		this.professionalName = professionalName;
	}
}
