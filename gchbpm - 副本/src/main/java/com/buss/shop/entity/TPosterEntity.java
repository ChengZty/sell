package com.buss.shop.entity;

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
 * @Description: 海报
 * @author onlineGenerator
 * @date 2017-03-03 12:35:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_poster", schema = "")
@Where(clause=" status='A'")
@SuppressWarnings("serial")
public class TPosterEntity implements java.io.Serializable {
	/**草稿箱*/
	public static String POST_STATUS_1 = "1";
	/**已完成(待上架)*/
	public static String POST_STATUS_2 = "2";
	/**上架中*/
	public static String POST_STATUS_3 = "3";
	/**已下架*/
	public static String POST_STATUS_4 = "4";
	
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
	/**标题*/
	@Excel(name="标题")
	private java.lang.String title;
	/**作者*/
	@Excel(name="作者")
	private java.lang.String author;
	/**封面图*/
	@Excel(name="封面图")
	private java.lang.String coverPic;
	/**正文*/
	@Excel(name="正文")
	private java.lang.String context;
	/**正文等h5*/
	@Excel(name="正文等h5")
	private java.lang.String contextHtml;
	/**零售商id*/
	@Excel(name="零售商id")
	private java.lang.String retailerId;
	/**海报状态*/
	@Excel(name="海报状态")
	private java.lang.String postStatus;
	/**排序  20170412增加*/
	private Long sortNum;
	
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
	@Column(name ="STATUS",nullable=true,length=50)
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
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=80)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作者
	 */
	@Column(name ="AUTHOR",nullable=true,length=40)
	public java.lang.String getAuthor(){
		return this.author;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作者
	 */
	public void setAuthor(java.lang.String author){
		this.author = author;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  封面图
	 */
	@Column(name ="COVER_PIC",nullable=true,length=150)
	public java.lang.String getCoverPic(){
		return this.coverPic;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  封面图
	 */
	public void setCoverPic(java.lang.String coverPic){
		this.coverPic = coverPic;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  正文
	 */
	@Column(name ="CONTEXT",nullable=true,length=500)
	public java.lang.String getContext(){
		return this.context;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  正文
	 */
	public void setContext(java.lang.String context){
		this.context = context;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  正文等h5
	 */
	@Column(name ="CONTEXT_HTML",nullable=true,length=500)
	public java.lang.String getContextHtml(){
		return this.contextHtml;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  正文等h5
	 */
	public void setContextHtml(java.lang.String contextHtml){
		this.contextHtml = contextHtml;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商id
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商id
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  海报状态
	 */
	@Column(name ="POST_STATUS",nullable=true,length=1)
	public java.lang.String getPostStatus(){
		return this.postStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  海报状态
	 */
	public void setPostStatus(java.lang.String postStatus){
		this.postStatus = postStatus;
	}
	
	@Column(name ="SORT_NUM")
	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}
	
}
