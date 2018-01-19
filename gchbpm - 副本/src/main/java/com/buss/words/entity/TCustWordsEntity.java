package com.buss.words.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 顾客话术
 * 商品话术也合并到一起 ，撩客话术合并到顾客话术里面 20170927
 * @author onlineGenerator
 * @date 2017-02-10 17:47:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_cust_words", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TCustWordsEntity implements java.io.Serializable {
	public static final String TYPE_1 = "1";//文字话术
	public static final String TYPE_2 = "2";//图片话术
	public static final String IS_SHOW_Y = "Y";//可见
	public static final String IS_SHOW_N = "N";//不可见
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
//	@Excel(name="一级分类ID")
	private java.lang.String topTypeId;
	/**二级分类ID*/
//	@Excel(name="二级分类ID")
	private java.lang.String subTypeId;
	/**三级分类ID*/
//	@Excel(name="三级分类ID")
	private java.lang.String thridTypeId;
	/**内容*/
//	@Excel(name="内容")
	private java.lang.String content;
	/**平台类别*/
	private java.lang.String platformType;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**类型，1文字话术，2图片话术*/
	private java.lang.String type;// 20170724新增
	/**类型，Y可见，N不可见*/
	private java.lang.String isShow;// 20170808新增 20171116弃用
	/**原来的ID*/
	private java.lang.String originalId;
	/**图片列表*/
//	private List<TCustWordsPicsEntity> picList;
	
	//非数据库字段
	private java.lang.String tradeName;// 行业
	//引入表TCustWordsTags的tag字段
	private java.lang.String  tags;
	//非数据库字段
	private java.lang.String categoryName;//二级分类的行业名
	//非数据库字段
	private java.lang.String typeName;//二级分类的类型名
	//非数据库字段
	private java.lang.String subName;//二级分类的类型名
	
	
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
	@Column(name ="STATUS",nullable=true,length=32)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  一级分类ID
	 */
	@Column(name ="TOP_TYPE_ID",nullable=true,length=36)
	public java.lang.String getTopTypeId(){
		return this.topTypeId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  一级分类ID
	 */
	public void setTopTypeId(java.lang.String topTypeId){
		this.topTypeId = topTypeId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  二级分类ID
	 */
	@Column(name ="SUB_TYPE_ID",nullable=true,length=36)
	public java.lang.String getSubTypeId(){
		return this.subTypeId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  二级分类ID
	 */
	public void setSubTypeId(java.lang.String subTypeId){
		this.subTypeId = subTypeId;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  三级分类ID
	 */
	@Column(name ="THRID_TYPE_ID",nullable=true,length=36)
	public java.lang.String getThridTypeId(){
		return this.thridTypeId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  三级分类ID
	 */
	public void setThridTypeId(java.lang.String thridTypeId){
		this.thridTypeId = thridTypeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="CONTENT",nullable=true,length=300)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  平台类别
	 */
	@Column(name ="PLATFORM_TYPE",nullable=true,length=1)
	public java.lang.String getPlatformType(){
		return this.platformType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  平台类别
	 */
	public void setPlatformType(java.lang.String platformType){
		this.platformType = platformType;
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
	@Column(name ="type")
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}
//	@Transient
//	public List<TCustWordsPicsEntity> getPicList() {
//		return picList;
//	}
//
//	public void setPicList(List<TCustWordsPicsEntity> picList) {
//		this.picList = picList;
//	}
	@Column(name ="is_show")
	public java.lang.String getIsShow() {
		return isShow;
	}

	public void setIsShow(java.lang.String isShow) {
		this.isShow = isShow;
	}
	@Column(name ="original_id")
	public java.lang.String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(java.lang.String originalId) {
		this.originalId = originalId;
	}
	@Transient
	public java.lang.String getTradeName() {
		return tradeName;
	}

	public void setTradeName(java.lang.String tradeName) {
		this.tradeName = tradeName;
	}
	@Transient
	public java.lang.String getTags() {
		return tags;
	}
	public void setTags(java.lang.String tags) {
		this.tags = tags;
	}

	@Transient
	public java.lang.String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	@Transient
	public java.lang.String getTypeName() {
		return typeName;
	}

	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}

	@Transient
	public java.lang.String getSubName() {
		return subName;
	}

	public void setSubName(java.lang.String subName) {
		this.subName = subName;
	}
	
}
