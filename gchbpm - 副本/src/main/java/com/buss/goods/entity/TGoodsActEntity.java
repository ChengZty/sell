package com.buss.goods.entity;

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
 * @Description: 商品活动管理
 * @author onlineGenerator
 * @date 2017-09-13 15:20:19
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_goods_act", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TGoodsActEntity implements java.io.Serializable {
	public static final String VALID_Y = "Y";//有效
	public static final String VALID_N = "N";//无效(下架)
	public static final String AUDIT_STATUS_1 = "1";//待审核
	public static final String AUDIT_STATUS_2 = "2";//已审核
	public static final String AUDIT_STATUS_3 = "3";//已作废
	
	/**活动状态是从活动时间及审核状态和是否有效综合得出的结果*/
	public static final int ACT_STATUS_1 = 1;//待审核
	public static final int ACT_STATUS_2 = 2;//待开始（已审核）
	public static final int ACT_STATUS_3 = 3;//进行中
	public static final int ACT_STATUS_4 = 4;//已结束(包括下架)
//	public static final String ACT_STATUS_5 = "5";//已下架
	
	/** 活动类型 MS:秒杀  XSZK:限时折扣 ZB:直播 */
	public static final String ACT_TYPE_MS = "MS" ;
	public static final String ACT_TYPE_XSZK = "XSZK" ;
	public static final String ACT_TYPE_ZB = "ZB" ;
	
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
	/**活动名称*/
	@Excel(name="活动名称")
	private java.lang.String title;
	/**开始时间*/
	@Excel(name="开始时间")
	private java.util.Date beginTime;
	/**结束时间*/
	@Excel(name="结束时间")
	private java.util.Date endTime;
	/**是否有效 Y：有效，N：无效*/
	private java.lang.String valid;
	/**审核人*/
	private java.lang.String auditor;
	/**审核时间*/
	private java.util.Date auditTime;
	/**审核状态 1：待审核，2：已审核， 3：已作废*/
	private java.lang.String auditStatus;
	/**活动状态 非数据库字段 1：待审核，2：待开始（已审核），3：进行中，4：已结束，5：已下架*/
	//该状态有时间和审核状态已经是否有效字段共同确定状态
	private java.lang.String actStatus;
	/**零售商id*/
	private java.lang.String retailerId;
	/**活动规则*/
	private java.lang.String remark;
	/**话题id*/
	private java.lang.String newsId;
	/**活动图片*/
	private java.lang.String coverPic;
	/**直播间地址*/
	private java.lang.String liveUrl;
	/**活动类型 MS:秒杀  XSZK:限时折扣 ZB:直播*/
	private java.lang.String actType ;
	/**活动模版id*/
	private java.lang.Integer templateId;
	/**模版模版 非数据库字段*/
	private java.lang.String templateName;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
//	@GeneratedValue(generator = "paymentableGenerator")
//	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
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
	 *@return: java.lang.String  活动名称
	 */
	@Column(name ="TITLE",nullable=false,length=32)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动名称
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始时间
	 */
	@Column(name ="BEGIN_TIME",nullable=false,length=20)
	public java.util.Date getBeginTime(){
		return this.beginTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始时间
	 */
	public void setBeginTime(java.util.Date beginTime){
		this.beginTime = beginTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束时间
	 */
	@Column(name ="END_TIME",nullable=false,length=20)
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否有效
	 */
	@Column(name ="VALID",nullable=true,length=1)
	public java.lang.String getValid(){
		return this.valid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否有效
	 */
	public void setValid(java.lang.String valid){
		this.valid = valid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	@Column(name ="AUDITOR",nullable=true,length=32)
	public java.lang.String getAuditor(){
		return this.auditor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setAuditor(java.lang.String auditor){
		this.auditor = auditor;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核时间
	 */
	@Column(name ="AUDIT_TIME",nullable=true,length=20)
	public java.util.Date getAuditTime(){
		return this.auditTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核时间
	 */
	public void setAuditTime(java.util.Date auditTime){
		this.auditTime = auditTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核状态
	 */
	@Column(name ="AUDIT_STATUS",nullable=true,length=1)
	public java.lang.String getAuditStatus(){
		return this.auditStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核状态
	 */
	public void setAuditStatus(java.lang.String auditStatus){
		this.auditStatus = auditStatus;
	}
	@Transient
	public java.lang.String getActStatus() {
		return actStatus;
	}

	public void setActStatus(java.lang.String actStatus) {
		this.actStatus = actStatus;
	}
	@Column(name ="RETAILER_ID")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Column(name ="remark")
	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	@Column(name ="news_id")
	public java.lang.String getNewsId() {
		return newsId;
	}

	public void setNewsId(java.lang.String newsId) {
		this.newsId = newsId;
	}
	@Column(name ="cover_pic")
	public java.lang.String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(java.lang.String coverPic) {
		this.coverPic = coverPic;
	}

	@Column(name ="live_url")
	public java.lang.String getLiveUrl() {
		return liveUrl;
	}

	public void setLiveUrl(java.lang.String liveUrl) {
		this.liveUrl = liveUrl;
	}
	
	@Column(name="act_type")
	public java.lang.String getActType() {
		return actType;
	}

	public void setActType(java.lang.String actType) {
		this.actType = actType;
	}
	@Column(name="template_id")
	public java.lang.Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(java.lang.Integer templateId) {
		this.templateId = templateId;
	}

	@Transient
	public java.lang.String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(java.lang.String templateName) {
		this.templateName = templateName;
	}
	
	
}
