package com.buss.news.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 资讯信息表
 * @author onlineGenerator
 * @date 2016-03-14 16:00:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_news", schema = "")
@Where(clause="status = 'A'")
@SuppressWarnings("serial")
public class TNewsEntity implements java.io.Serializable {
	/**管家课堂分类id*/
	public static String NEWS_TYPE_COURSE = "6001";//管家课堂t_template_type表的id
	/**管家故事分类id*/
	public static String NEWS_TYPE_STORY = "6002";//管家故事t_template_type表的id
	
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
	/**资讯标题*/
	@Excel(name="资讯标题")
	private java.lang.String title;
	/**资讯作者*/
	@Excel(name="资讯作者")
	private java.lang.String author;
	/**资讯分类  1:管家课堂，2：管家故事，3:品牌话题，4：商品话题，5：活动话题 （20170610修改）*/
	@Excel(name="资讯分类")
	private java.lang.String newsType;
	/**封面图片*/
	@Excel(name="封面图片")
	private java.lang.String coverPic;
	/**标题图片*/
	@Excel(name="标题图片")
	private java.lang.String titlePic;
	/**资讯正文*/
	@Excel(name="资讯正文")
	private java.lang.String newsContext;
	/**零售商*/
	@Excel(name="零售商")
	private java.lang.String shopkeeper;
	/**是否已发布,Y:是，N:否*/
	@Excel(name="是否已发布")
	private java.lang.String upLoaded;
	/**标签*/
	@Excel(name="标签")
	private java.lang.String tags;
	/**资讯作者ID*/
	private java.lang.String userId;
	/**图片商品内容*/
	private java.lang.String picMapContent;
	/**无感次数*/
	private java.lang.Integer noSenseNum;
	/**点赞次数*/
	private java.lang.Integer goodNum;
	/**资讯点击量*/
	private java.lang.Integer clickNum;
	/**原来的ID*/
	private java.lang.String originalId;

	/**行业名*/
	private java.lang.String tradeName;
	
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
	 *@return: java.lang.String  资讯标题
	 */
	@Column(name ="TITLE",nullable=true,length=80)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  资讯标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  资讯作者
	 */
	@Column(name ="AUTHOR",nullable=true,length=40)
	public java.lang.String getAuthor(){
		return this.author;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  资讯作者
	 */
	public void setAuthor(java.lang.String author){
		this.author = author;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  资讯分类
	 */
	@Column(name ="NEWS_TYPE",nullable=true,length=100)
	public java.lang.String getNewsType(){
		return this.newsType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  资讯分类
	 */
	public void setNewsType(java.lang.String newsType){
		this.newsType = newsType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  封面图片
	 */
	@Column(name ="COVER_PIC",nullable=true,length=155)
	public java.lang.String getCoverPic(){
		return this.coverPic;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  封面图片
	 */
	public void setCoverPic(java.lang.String coverPic){
		this.coverPic = coverPic;
	}
	@Column(name ="title_Pic",nullable=true,length=155)
	public java.lang.String getTitlePic() {
		return titlePic;
	}

	public void setTitlePic(java.lang.String titlePic) {
		this.titlePic = titlePic;
	}

	/**
	 *方法: 取得java.sql.Blob
	 *@return: java.sql.Blob  资讯正文
	 */
	@Column(name ="NEWS_CONTEXT",nullable=true)
	public java.lang.String getNewsContext(){
		return this.newsContext;
	}

	/**
	 *方法: 设置java.sql.Blob
	 *@param: java.sql.Blob  资讯正文
	 */
	public void setNewsContext(String newsContext){
		this.newsContext = newsContext;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商
	 */
	@Column(name ="SHOPKEEPER",nullable=true,length=36)
	public java.lang.String getShopkeeper(){
		return this.shopkeeper;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商
	 */
	public void setShopkeeper(java.lang.String shopkeeper){
		this.shopkeeper = shopkeeper;
	}
	@Column(name ="upLoaded",nullable=true,length=2)
	public java.lang.String getUpLoaded() {
		return upLoaded;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否发布
	*/
	public void setUpLoaded(java.lang.String upLoaded) {
		this.upLoaded = upLoaded;
	}

	@Column(name ="tags",nullable=true,length=800)
	public java.lang.String getTags() {
		return tags;
	}
	public void setTags(java.lang.String tags) {
		this.tags = tags;
	}
	@Column(name ="user_Id",nullable=true,length=36)
	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	@Column(name ="pic_map_content",nullable=true)
	public java.lang.String getPicMapContent() {
		return picMapContent;
	}

	public void setPicMapContent(java.lang.String picMapContent) {
		this.picMapContent = picMapContent;
	}
	@Column(name ="no_sense_num")
	public java.lang.Integer getNoSenseNum() {
		return noSenseNum;
	}

	public void setNoSenseNum(java.lang.Integer noSenseNum) {
		this.noSenseNum = noSenseNum;
	}
	@Column(name ="good_num")
	public java.lang.Integer getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(java.lang.Integer goodNum) {
		this.goodNum = goodNum;
	}
	@Column(name ="CLICK_NUM",nullable=true)
	public java.lang.Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(java.lang.Integer clickNum) {
		this.clickNum = clickNum;
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
	
}
