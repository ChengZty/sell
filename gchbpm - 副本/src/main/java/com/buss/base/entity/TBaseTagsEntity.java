package com.buss.base.entity;

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
 * @Description: 标签
 * @author onlineGenerator
 * @date 2016-12-24 15:07:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_base_tags", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TBaseTagsEntity implements java.io.Serializable {
	public static final String TO_USER_TYPE_GUIDE = "1"; //导购
	public static final String TO_USER_TYPE_CUST = "2"; //顾客
	public final static String VALID_Y = "Y";//可用
	public final static String VALID_N = "N";//停用
	public final static String TAG_STAGE_J = "J";//顾客基础资料(共用)
	public final static String TAG_STAGE_2 = "2";//生活属性(共用)
	public final static String TAG_STAGE_3 = "3";//消费属性(共用)
	public final static String TAG_STAGE_4 = "4";//公司自定义标签 20171012新增 
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
	/**标签类别 2：生活属性，3：消费属性，4：公司自定义标签*/
	@Excel(name="标签类别")
	private java.lang.String tagStage;
	/**对象类型 1：导购，2：顾客*/
	@Excel(name="对象类型")
	private java.lang.String toUserType;
	/**标题*/
	@Excel(name="标题")
	private java.lang.String tagTitle;
	/**编码*/
	@Excel(name="编码")
	private java.lang.String tagCode;
	/**标签类型*/
	@Excel(name="标签类型")
	private java.lang.String tagType;
	/**标签值*/
	@Excel(name="标签值")
	private java.lang.String tagValues;
	/**排序*/
	@Excel(name="排序")
	private java.lang.Integer tagSort;
	/**零售商id 平台的为admin*/
	private java.lang.String retailerId;
	/**是否可用  默认Y:可用，N:停用*/
	private java.lang.String valid;
	
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
	 *@return: java.lang.String  阶段
	 */
	@Column(name ="TAG_STAGE",nullable=true,length=3)
	public java.lang.String getTagStage(){
		return this.tagStage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  阶段
	 */
	public void setTagStage(java.lang.String tagStage){
		this.tagStage = tagStage;
	}
	@Column(name ="TO_USER_TYPE",nullable=true,length=1)
	public java.lang.String getToUserType() {
		return toUserType;
	}

	public void setToUserType(java.lang.String toUserType) {
		this.toUserType = toUserType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TAG_TITLE",nullable=true,length=32)
	public java.lang.String getTagTitle(){
		return this.tagTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTagTitle(java.lang.String tagTitle){
		this.tagTitle = tagTitle;
	}
	@Column(name ="TAG_CODE",nullable=true,length=6)
	public java.lang.String getTagCode() {
		return tagCode;
	}

	public void setTagCode(java.lang.String tagCode) {
		this.tagCode = tagCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */
	@Column(name ="TAG_TYPE",nullable=true,length=1)
	public java.lang.String getTagType(){
		return this.tagType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setTagType(java.lang.String tagType){
		this.tagType = tagType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标签值
	 */
	@Column(name ="TAG_VALUES",nullable=true,length=500)
	public java.lang.String getTagValues(){
		return this.tagValues;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标签值
	 */
	public void setTagValues(java.lang.String tagValues){
		this.tagValues = tagValues;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="TAG_SORT",nullable=true,length=5)
	public java.lang.Integer getTagSort(){
		return this.tagSort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setTagSort(java.lang.Integer tagSort){
		this.tagSort = tagSort;
	}
	@Column(name ="retailer_Id")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Column(name ="valid")
	public java.lang.String getValid() {
		return valid;
	}

	public void setValid(java.lang.String valid) {
		this.valid = valid;
	}
	
}
