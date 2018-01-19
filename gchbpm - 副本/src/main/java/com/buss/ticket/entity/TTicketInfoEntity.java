package com.buss.ticket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 优惠券 20170626逻辑调整
 * @author onlineGenerator
 * @date 2016-07-18 18:08:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_ticket_info", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TTicketInfoEntity implements java.io.Serializable {
	public static final String TICKET_STATUS_1 = "1";//待审核
	public static final String TICKET_STATUS_2 = "2";//已审核
	public static final String TICKET_STATUS_3 = "3";//已停用
	
	public static final String TYPE_1 = "1";//红包
	public static final String TYPE_2 = "2";//现金券
	public static final String TYPE_3 = "3";//折扣券
	
	public static final String USING_RANGE_1 = "1";//全馆
	public static final String USING_RANGE_2 = "2";//品牌/单品
//	public static final String USING_RANGE_3 = "3";//单品
	
	public static final String DEFAULT_PIC = "http://img7.guanjiaapp.net/image/img_coupon.png";//券的图片
	
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
	/**批次号*/
	private java.lang.String batchNo;
	/**券名*/
	private java.lang.String ticketName;
	/**用券最低金额/件数*/
	@Excel(name="用券最低金额/件数")
	private java.math.BigDecimal leastMoney;
	/**面值/折扣*/
	@Excel(name="面值/折扣")
	private java.math.BigDecimal faceValue;
	/**用券类型 1:金额，2：件数*/
	@Excel(name="用券类型")
	private java.lang.String useType;
	/**起始时间*/
	@Excel(name="起始时间",format = "yyyy-MM-dd")
	private java.util.Date beginTime;
	/**结束时间*/
	@Excel(name="结束时间",format = "yyyy-MM-dd")
	private java.util.Date endTime;
	/**审核状态*/
	@Excel(name="审核状态")
	private java.lang.String ticketStatus;
	/**操作人ID  审核，停用的操作者ID*/
	private java.lang.String auditorId;
	/**操作人 审核，停用的操作者*/
	private java.lang.String auditor;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**零售商类型*/
	private java.lang.String retailerType;
	/**店铺ID*/
	private java.lang.String storeId;
	/**总张数*/
	private java.lang.Integer sheetTotal;
	/**已分配张数 改为  已领取张数*/
	private java.lang.Integer sheetSent;
	/**已使用张数*/
	private java.lang.Integer sheetUsed;
	/**限制使用张数(为0则表示不限制)*/
	private java.lang.Integer sheetLimit;
	/**剩余张数 改为 剩余领取张数*/
	private java.lang.Integer sheetRemain;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String remark;
	/**类型(1：红包，2：优惠券，3：折扣券)*/
	private java.lang.String type;
	/**适用范围(1:全馆，2：品牌/单品)*/
	@Excel(name="适用范围")
	private java.lang.String usingRange;
	/**品牌ID*/
	private java.lang.String brandId;
	/**品牌名称*/
	@Excel(name="品牌名称")
	private java.lang.String brandName;
	/**一级类目ID*/
	@Excel(name="一级类目ID")
	private java.lang.String topCategoryId;
	/**二级类目ID*/
	@Excel(name="二级类目ID")
	private java.lang.String subCategoryId;
	/**三级类目ID*/
	@Excel(name="三级类目ID")
	private java.lang.String thridCategoryId;
	/**一级类目*/
	@Excel(name="一级类目")
	private java.lang.String topCategoryName;
	/**二级类目*/
	@Excel(name="二级类目")
	private java.lang.String subCategoryName;
	/**三级类目*/
	@Excel(name="三级类目")
	private java.lang.String thridCategoryName;
	/**缩略图*/
	private java.lang.String picUrl;
	/**推送标题*/
	private java.lang.String pushTitle;
	/**推送副标题*/
	private java.lang.String pushSubtitle;
	//店铺ID 非数据库字段
	private java.lang.String storeIds;
	
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
	 *@return: java.lang.String  批次号
	 */
	@Column(name ="BATCH_NO",nullable=true,length=50)
	public java.lang.String getBatchNo(){
		return this.batchNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批次号
	 */
	public void setBatchNo(java.lang.String batchNo){
		this.batchNo = batchNo;
	}
	@Column(name ="TICKET_NAME",nullable=true,length=50)
	public java.lang.String getTicketName() {
		return ticketName;
	}

	public void setTicketName(java.lang.String ticketName) {
		this.ticketName = ticketName;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  满额
	 */
	@Column(name ="LEAST_MONEY",nullable=true,length=10)
	public java.math.BigDecimal getLeastMoney(){
		return this.leastMoney;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  满额
	 */
	public void setLeastMoney(java.math.BigDecimal leastMoney){
		this.leastMoney = leastMoney;
	}
	@Column(name ="FACE_VALUE",nullable=true,length=10)
	public java.math.BigDecimal getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(java.math.BigDecimal faceValue) {
		this.faceValue = faceValue;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  起始时间
	 */
	@Column(name ="BEGIN_TIME",nullable=true,length=20)
	public java.util.Date getBeginTime(){
		return this.beginTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  起始时间
	 */
	public void setBeginTime(java.util.Date beginTime){
		this.beginTime = beginTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束时间
	 */
	@Column(name ="END_TIME",nullable=true,length=20)
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
	 *@return: java.lang.String  审核状态
	 */
	@Column(name ="TICKET_STATUS",nullable=true,length=1)
	public java.lang.String getTicketStatus(){
		return this.ticketStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核状态
	 */
	public void setTicketStatus(java.lang.String ticketStatus){
		this.ticketStatus = ticketStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人ID
	 */
	@Column(name ="AUDITOR_ID",nullable=true,length=36)
	public java.lang.String getAuditorId(){
		return this.auditorId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人ID
	 */
	public void setAuditorId(java.lang.String auditorId){
		this.auditorId = auditorId;
	}
	@Column(name ="AUDITOR")
	public java.lang.String getAuditor() {
		return auditor;
	}

	public void setAuditor(java.lang.String auditor) {
		this.auditor = auditor;
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
	@Column(name ="RETAILER_TYPE",nullable=true)
	public java.lang.String getRetailerType() {
		return retailerType;
	}

	public void setRetailerType(java.lang.String retailerType) {
		this.retailerType = retailerType;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  总张数
	 */
	@Column(name ="SHEET_TOTAL",nullable=true,length=8)
	public java.lang.Integer getSheetTotal(){
		return this.sheetTotal;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  总张数
	 */
	public void setSheetTotal(java.lang.Integer sheetTotal){
		this.sheetTotal = sheetTotal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */
	@Column(name ="TYPE",nullable=true,length=1)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  已分配张数
	 */
	@Column(name ="SHEET_SENT",nullable=true,length=8)
	public java.lang.Integer getSheetSent(){
		return this.sheetSent;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  已分配张数
	 */
	public void setSheetSent(java.lang.Integer sheetSent){
		this.sheetSent = sheetSent;
	}
	@Column(name ="SHEET_USED",nullable=true,length=8)
	public java.lang.Integer getSheetUsed() {
		return sheetUsed;
	}

	public void setSheetUsed(java.lang.Integer sheetUsed) {
		this.sheetUsed = sheetUsed;
	}
	@Column(name ="SHEET_LIMIT",nullable=true,length=8)
	public java.lang.Integer getSheetLimit() {
		return sheetLimit;
	}

	public void setSheetLimit(java.lang.Integer sheetLimit) {
		this.sheetLimit = sheetLimit;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  剩余张数
	 */
	@Column(name ="SHEET_REMAIN",nullable=true,length=8)
	public java.lang.Integer getSheetRemain(){
		return this.sheetRemain;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  剩余张数
	 */
	public void setSheetRemain(java.lang.Integer sheetRemain){
		this.sheetRemain = sheetRemain;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=100)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	@Column(name ="using_range",nullable=true,length=1)
	public java.lang.String getUsingRange() {
		return usingRange;
	}

	public void setUsingRange(java.lang.String usingRange) {
		this.usingRange = usingRange;
	}
	@Column(name ="brand_id",nullable=true)
	public java.lang.String getBrandId() {
		return brandId;
	}

	public void setBrandId(java.lang.String brandId) {
		this.brandId = brandId;
	}
	@Column(name ="brand_name",nullable=true)
	public java.lang.String getBrandName() {
		return brandName;
	}

	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	@Column(name ="top_category_id",nullable=true)
	public java.lang.String getTopCategoryId() {
		return topCategoryId;
	}

	public void setTopCategoryId(java.lang.String topCategoryId) {
		this.topCategoryId = topCategoryId;
	}
	@Column(name ="sub_category_id",nullable=true)
	public java.lang.String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(java.lang.String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	@Column(name ="thrid_category_id",nullable=true)
	public java.lang.String getThridCategoryId() {
		return thridCategoryId;
	}

	public void setThridCategoryId(java.lang.String thridCategoryId) {
		this.thridCategoryId = thridCategoryId;
	}
	@Column(name ="top_category_name",nullable=true)
	public java.lang.String getTopCategoryName() {
		return topCategoryName;
	}

	public void setTopCategoryName(java.lang.String topCategoryName) {
		this.topCategoryName = topCategoryName;
	}
	@Column(name ="sub_category_name",nullable=true)
	public java.lang.String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(java.lang.String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	@Column(name ="thrid_category_name",nullable=true)
	public java.lang.String getThridCategoryName() {
		return thridCategoryName;
	}
	public void setThridCategoryName(java.lang.String thridCategoryName) {
		this.thridCategoryName = thridCategoryName;
	}
	@Column(name ="use_type")
	public java.lang.String getUseType() {
		return useType;
	}

	public void setUseType(java.lang.String useType) {
		this.useType = useType;
	}
	@Column(name ="pic_url")
	public java.lang.String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(java.lang.String picUrl) {
		this.picUrl = picUrl;
	}

	@Column(name ="store_id")
	public java.lang.String getStoreId() {
		return storeId;
	}

	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	@Column(name ="push_title")
	public java.lang.String getPushTitle() {
		return pushTitle;
	}

	public void setPushTitle(java.lang.String pushTitle) {
		this.pushTitle = pushTitle;
	}
	@Column(name ="push_subtitle")
	public java.lang.String getPushSubtitle() {
		return pushSubtitle;
	}

	public void setPushSubtitle(java.lang.String pushSubtitle) {
		this.pushSubtitle = pushSubtitle;
	}
	@Transient
	public java.lang.String getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(java.lang.String storeIds) {
		this.storeIds = storeIds;
	}
	
}
