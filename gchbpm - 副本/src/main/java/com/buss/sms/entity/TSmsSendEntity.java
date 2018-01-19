package com.buss.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: t_sms_send
 * @author onlineGenerator
 * @date 2017-02-25 12:31:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sms_send", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TSmsSendEntity implements java.io.Serializable {
	/**id*/
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
	/**发送主表ID*/
	private java.lang.String sendInfoId;
	/**批次号*/
	private java.lang.String batchNo;
	/**手机号码*/
	@Excel(name="手机号",width=15)
	private java.lang.String phone;
	/**姓名*/
	@Excel(name="姓名",width=15)
	private java.lang.String name;
	/**短信平台消息id*/
	private java.lang.String msgId;
	/**发送内容*/
	private java.lang.String sendContent;
	/**发送时间*/
	private java.util.Date sendTime;
	/**时间发送方式，0即时发送，1定时发送*/
	private java.lang.String sendTimeType;
	/**长链接*/
	private java.lang.String longUrl;
	/**短链接*/
	private java.lang.String shortUrl;
	/**发送状态 保存时默认为空字符串  0发送失败，1发送成功*/
	@Excel(name="发送状态",replace={"成功_1","失败_0"},width=15)
	private java.lang.String sendStatus;
	/**接收状态 保存时默认为空字符串  0接收失败，1接收成功*/
	@Excel(name="接收状态",replace={"成功_1","失败_0"},width=15)
	private java.lang.String receiveStatus;
	/**是否点击  0未点击，1已点击*/
	@Excel(name="是否点击",replace={"已点击_1","未点击_0"},width=15)
	private java.lang.String isClick;
	/**点击次数*/
	@Excel(name="点击次数",width=15)
	private java.lang.Integer clickNumber;
	/**所属零售商*/
	private java.lang.String retailerId;
	/**顾客ID*/
	private java.lang.String custId;
	/**类型：0:无效号码 1 ：无反应顾客 2：点击顾客 3：交易顾客*/
	private java.lang.String type;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
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
	@Column(name ="CREATE_DATE",nullable=true)
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
	@Column(name ="UPDATE_DATE",nullable=true)
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
	 *@return: java.lang.String  主表ID
	 */
	@Column(name ="SEND_INFO_ID",nullable=true,length=36)
	public java.lang.String getSendInfoId(){
		return this.sendInfoId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表ID
	 */
	public void setSendInfoId(java.lang.String sendInfoId){
		this.sendInfoId = sendInfoId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批次号
	 */
	@Column(name ="BATCH_NO",nullable=true,length=20)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */
	@Column(name ="PHONE",nullable=true)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="NAME",nullable=true)
	public java.lang.String getName() {
		return name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  短信平台消息id
	 */
	@Column(name ="MSG_ID",nullable=true,length=36)
	public java.lang.String getMsgId(){
		return this.msgId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  短信平台消息id
	 */
	public void setMsgId(java.lang.String msgId){
		this.msgId = msgId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送内容
	 */
	@Column(name ="SEND_CONTENT",nullable=true)
	public java.lang.String getSendContent(){
		return this.sendContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送内容
	 */
	public void setSendContent(java.lang.String sendContent){
		this.sendContent = sendContent;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发送时间
	 */
	@Column(name ="SEND_TIME",nullable=true)
	public java.util.Date getSendTime(){
		return this.sendTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发送时间
	 */
	public void setSendTime(java.util.Date sendTime){
		this.sendTime = sendTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  时间发送方式，0即时发送，1定时发送
	 */
	@Column(name ="SEND_TIME_TYPE",nullable=true,length=1)
	public java.lang.String getSendTimeType(){
		return this.sendTimeType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  时间发送方式，0即时发送，1定时发送
	 */
	public void setSendTimeType(java.lang.String sendTimeType){
		this.sendTimeType = sendTimeType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  长链接
	 */
	@Column(name ="LONG_URL",nullable=true,length=200)
	public java.lang.String getLongUrl(){
		return this.longUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  长链接
	 */
	public void setLongUrl(java.lang.String longUrl){
		this.longUrl = longUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  短链接
	 */
	@Column(name ="SHORT_URL",nullable=true,length=200)
	public java.lang.String getShortUrl(){
		return this.shortUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  短链接
	 */
	public void setShortUrl(java.lang.String shortUrl){
		this.shortUrl = shortUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送状态
	 */
	@Column(name ="SEND_STATUS",nullable=true,length=2)
	public java.lang.String getSendStatus(){
		return this.sendStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送状态
	 */
	public void setSendStatus(java.lang.String sendStatus){
		this.sendStatus = sendStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接收状态
	 */
	@Column(name ="RECEIVE_STATUS",nullable=true,length=1)
	public java.lang.String getReceiveStatus(){
		return this.receiveStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接收状态
	 */
	public void setReceiveStatus(java.lang.String receiveStatus){
		this.receiveStatus = receiveStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否点击
	 */
	@Column(name ="IS_CLICK",nullable=true,length=1)
	public java.lang.String getIsClick(){
		return this.isClick;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否点击
	 */
	public void setIsClick(java.lang.String isClick){
		this.isClick = isClick;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  点击次数
	 */
	@Column(name ="CLICK_NUMBER")
	public java.lang.Integer getClickNumber() {
		return clickNumber;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  点击次数
	 */
	public void setClickNumber(java.lang.Integer clickNumber) {
		this.clickNumber = clickNumber;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属零售商
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类：3交易顾客，4点击顾客，1无反应顾客，0无效号码
	 */
	@Column(name ="TYPE",nullable=true,length=1)
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  顾客ID
	 */
	@Column(name ="CUST_ID",nullable=true,length=36)
	public java.lang.String getCustId() {
		return custId;
	}

	public void setCustId(java.lang.String custId) {
		this.custId = custId;
	}
}
