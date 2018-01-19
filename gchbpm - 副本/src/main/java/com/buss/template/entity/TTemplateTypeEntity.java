package com.buss.template.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 模板分类
 * @author onlineGenerator
 * @date 2016-04-09 12:14:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_template_type", schema = "")
@Where(clause = "status = 'A'")
@SuppressWarnings("serial")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class TTemplateTypeEntity implements java.io.Serializable {
	public static String PLATFORM_TYPE_1 = "1";//平台
	public static String PLATFORM_TYPE_2 = "2";//零售商
	public static String TEMPLATE_TYPE_1 = "1";//话术
	public static String TEMPLATE_TYPE_2 = "2";//话题
	public static String FESTIVAL_TYPE_1 = "1";//阳历
	public static String FESTIVAL_TYPE_2 = "2";//阴历
	public static String WORDS_TYPE_1 = "101";//商品话术
	public static String WORDS_TYPE_2 = "102";//顾客话术
	
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

	/**模板类型*/
	private java.lang.String templateType;
	/**编码*/
	@Excel(name="编码")
	private java.lang.String code;
	/**名字*/
	@Excel(name="名字")
	private java.lang.String name;
	/**级别*/
	@Excel(name="级别")
	private java.lang.String level;
	/**序列*/
	@Excel(name="序列")
	private java.lang.String orderNum;
	/**上级ID*/
//	private java.lang.String parentId;
	/**节日类型*/
	private java.lang.String festivalType;//1：阳历，2：农历
	/**日期格式*/
	private java.lang.String monthDay;//格式：01-01
	/**是否有图片，默认N*/
	private java.lang.String hasPics;//格式：Y/N 
	/**图片*/
	private java.lang.String coverPic;
	/**小图*/
	private java.lang.String smallPic;
	/**类别（1：平台，2：零售商）*/
	private java.lang.String platformType;
	/**零售商ID*/
	private java.lang.String retailerId;
	
	/**上级分类*/
	private TTemplateTypeEntity parent;
	
	/**子分类*/
	private List<TTemplateTypeEntity> list;
	
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
	 *@return: java.lang.String  编码
	 */
	@Column(name ="CODE",nullable=true)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编码
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名字
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名字
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	


	@Column(name ="template_type",nullable=true,length=32)
	public java.lang.String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(java.lang.String templateType) {
		this.templateType = templateType;
	}

	@Column(name ="level",nullable=true,length=11)
	public java.lang.String getLevel() {
		return level;
	}

	public void setLevel(java.lang.String level) {
		this.level = level;
	}

//	@Column(name ="parent_id",nullable=true,length=32)
//	public java.lang.String getParentId() {
//		return parentId;
//	}
//
//	public void setParentId(java.lang.String parentId) {
//		this.parentId = parentId;
//	}

	@Column(name ="ORDER_NUM",nullable=true,length=11)
	public java.lang.String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(java.lang.String orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name ="festival_type",nullable=true,length=32)
	public java.lang.String getFestivalType() {
		return festivalType;
	}

	public void setFestivalType(java.lang.String festivalType) {
		this.festivalType = festivalType;
	}

	@Column(name ="month_day",nullable=true,length=32)
	public java.lang.String getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(java.lang.String monthDay) {
		this.monthDay = monthDay;
	}

	@Column(name ="has_pics",nullable=true,length=32)
	public java.lang.String getHasPics() {
		return hasPics;
	}

	public void setHasPics(java.lang.String hasPics) {
		this.hasPics = hasPics;
	}
	@Column(name ="COVER_PIC",nullable=true,length=150)
	public java.lang.String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(java.lang.String coverPic) {
		this.coverPic = coverPic;
	}
	@Column(name ="small_Pic")
	public java.lang.String getSmallPic() {
		return smallPic;
	}

	public void setSmallPic(java.lang.String smallPic) {
		this.smallPic = smallPic;
	}
	@Column(name ="platform_type")
	public java.lang.String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(java.lang.String platformType) {
		this.platformType = platformType;
	}
	@Column(name ="retailer_id")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	
	/**
	 * 方法: 取得TTemplateTypeEntity
	 * 
	 * @return: TTemplateTypeEntity 上级code
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID",referencedColumnName = "id")
	public TTemplateTypeEntity getParent() {
		return this.parent;
	}

	/**
	 * 方法: 设置TTemplateTypeEntity
	 * 
	 * @param: TTemplateTypeEntity 上级
	 */
	public void setParent(TTemplateTypeEntity parent) {
		this.parent = parent;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent")
	@Where(clause="status = 'A'")
	public List<TTemplateTypeEntity> getList() {
		return list;
	}

	public void setList(List<TTemplateTypeEntity> list) {
		this.list = list;
	}
}
