package com.buss.news.entity;

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
 * @Description: 资讯分类
 * @author onlineGenerator
 * @date 2016-04-09 12:14:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_news_type", schema = "")
@Where(clause = "status = 'A'")
@SuppressWarnings("serial")
public class TNewsTypeEntity implements java.io.Serializable {
	public static String IS_NEED_Y = "1";//是
	public static String IS_NEED_N = "0";//否
	public static String PLATFORM_TYPE_1 = "1";//平台
	public static String PLATFORM_TYPE_2 = "2";//零售商
	/**主键*/
	private java.lang.String id;
	/**编码*/
	@Excel(name="编码")
	private java.lang.String code;
	/**名字*/
	@Excel(name="名字")
	private java.lang.String name;
	/**排序编号*/
	@Excel(name="排序编号")
	private java.lang.Integer orderNum;
	/**图片*/
	private java.lang.String coverPic;
	/**是否必选*/
	private java.lang.String isNeed;//默认是0   ， 0：否，1：是
	/**显示类型，1：全部，2：导购，3：顾客*/
	private java.lang.String showType;
	/**小图*/
	private java.lang.String smallPic;
	//20170206新增
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
	/**类别（1：平台，2：零售商）*/
	private java.lang.String platformType;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**原来的ID*/
	private java.lang.String originalId;
	
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
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序编号
	 */
	@Column(name ="ORDER_NUM",nullable=true,length=3)
	public java.lang.Integer getOrderNum(){
		return this.orderNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序编号
	 */
	public void setOrderNum(java.lang.Integer orderNum){
		this.orderNum = orderNum;
	}
	@Column(name ="COVER_PIC",nullable=true,length=150)
	public java.lang.String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(java.lang.String coverPic) {
		this.coverPic = coverPic;
	}
	@Column(name ="is_Need",nullable=true,length=1)
	public java.lang.String getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(java.lang.String isNeed) {
		this.isNeed = isNeed;
	}
	@Column(name ="show_type")
	public java.lang.String getShowType() {
		return showType;
	}

	public void setShowType(java.lang.String showType) {
		this.showType = showType;
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
	@Column(name ="original_id")
	public java.lang.String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(java.lang.String originalId) {
		this.originalId = originalId;
	}
	
}
