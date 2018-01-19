package com.buss.sms.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: t_sms_send_info
 * @author onlineGenerator
 * @date 2017-02-25 12:31:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sms_send_info", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TSmsSendInfoEntity implements java.io.Serializable {
	public static final String SENTTIMETYPE_0 = "0";//发送时间类型0即时发送，1定时发送
	public static final String SENTTIMETYPE_1 = "1";//发送时间类型0即时发送，1定时发送
	/**未发送*/
	public static final String SEND_STATUS_0 = "0";//未发送
	/**提交中*/
	public static final String SEND_STATUS_1 = "1";//提交中
	/**提交成功*/
	public static final String SEND_STATUS_2 = "2";//提交成功
	/**提交失败*/
	public static final String SEND_STATUS_3 = "3";//提交失败
	/**发送成功*/
	public static final String SEND_STATUS_4 = "4";//发送成功
	/**审核失败*/
	public static final String SEND_STATUS_5 = "5";//审核失败
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
	/**批次号*/
	@Excel(name="批次号")
	private java.lang.String batchNo;
	/**标题*/
	@Excel(name="标题")
	private java.lang.String title;
	/**短信模板ID*/
	private java.lang.String templateId;
	/**短信模板名称*/
	@Excel(name="短信模板名称")
	private java.lang.String templateName;
	/**短信签名ID*/
	@Excel(name="短信签名ID")
	private java.lang.String autographId;
	/**短信签名*/
	@Excel(name="短信签名")
	private java.lang.String autographName;
	/**正文内容*/
	@Excel(name="正文内容")
	private java.lang.String content;
	/**结尾内容1*/
	@Excel(name="结尾内容1")
	private java.lang.String contentEnd;
	/**结尾内容2*/
	@Excel(name="结尾内容2")
	private java.lang.String contentEnd2;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**发送状态 0：未发送，1：提交中，2：提交成功，3：提交失败，4：发送成功，5：审核失败*/
	@Excel(name="发送状态")
	private java.lang.String sendStatus;
	/**发送时间*/
	@Excel(name="发送时间")
	private java.util.Date sendTime;
	/**时间发送方式，0即时发送，1定时发送*/
	@Excel(name="时间发送方式，0即时发送，1定时发送")
	private java.lang.String sendTimeType;
	/**点击次数*/
	@Excel(name="点击次数")
	private java.lang.Integer clickNumber;
	/**点击率*/
	@Excel(name="点击率")
	private BigDecimal clickRate;
	/**推送总量*/
	@Excel(name="推送总量")
	private java.lang.Integer pushCount;
	/**推送类型 0:海报，1：商品*/
	@Excel(name="推送类型")
	private java.lang.String pushType;
	/**推送链接*/
	@Excel(name="推送链接")
	private java.lang.String pushUrl;
	/**送达量*/
	@Excel(name="送达量")
	private java.lang.Integer reach;
	/**送达率*/
	@Excel(name="送达率")
	private BigDecimal reachRate;
	/**购买单量*/
	@Excel(name="购买单量")
	private java.lang.Integer buySingle;
	/**购买率*/
	@Excel(name="购买率")
	private BigDecimal buyRate;
	/**资料可信度*/
	@Excel(name="资料可信度")
	private java.lang.String reliability;
	/**内容吸引度*/
	@Excel(name="内容吸引度")
	private java.lang.String attractionDegree;
	/**营销成功率*/
	@Excel(name="营销成功率")
	private java.lang.String successDegree;
	/**短信内容占用条数*/
	@Excel(name="短信内容占用条数")
	private java.lang.Integer msgNum;
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
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=20)
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
	 *@return: java.lang.String  短信模板ID
	 */
	@Column(name ="TEMPLATE_ID",nullable=true,length=36)
	public java.lang.String getTemplateId(){
		return this.templateId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  短信模板ID
	 */
	public void setTemplateId(java.lang.String templateId){
		this.templateId = templateId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  短信模板名称
	 */
	@Column(name ="TEMPLATE_NAME",nullable=true,length=50)
	public java.lang.String getTemplateName(){
		return this.templateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  短信模板名称
	 */
	public void setTemplateName(java.lang.String templateName){
		this.templateName = templateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  签名名称Id
	 */
	@Column(name ="AUTOGRAPH_ID",nullable=true,length=36)
	public java.lang.String getAutographId() {
		return autographId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  签名名称Id
	 */
	public void setAutographId(java.lang.String autographId) {
		this.autographId = autographId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  签名名称
	 */
	@Column(name ="AUTOGRAPH_NAME",nullable=true,length=50)
	public java.lang.String getAutographName(){
		return this.autographName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  签名名称
	 */
	public void setAutographName(java.lang.String autographName){
		this.autographName = autographName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  正文内容
	 */
	@Column(name ="CONTENT",nullable=true)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  正文内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结尾内容1
	 */
	@Column(name ="CONTENT_END",nullable=true,length=200)
	public java.lang.String getContentEnd(){
		return this.contentEnd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结尾内容1
	 */
	public void setContentEnd(java.lang.String contentEnd){
		this.contentEnd = Utility.isEmpty(contentEnd)?"":contentEnd;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结尾内容2
	 */
	@Column(name ="CONTENT_END2",nullable=true,length=200)
	public java.lang.String getContentEnd2(){
		return this.contentEnd2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结尾内容2
	 */
	public void setContentEnd2(java.lang.String contentEnd2){
		this.contentEnd2 = contentEnd2;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送状态
	 */
	@Column(name ="SEND_STATUS",nullable=true,length=1)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  点击次数
	 */
	@Column(name ="CLICK_NUMBER",nullable=true,length=10)
	public java.lang.Integer getClickNumber(){
		return this.clickNumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  点击次数
	 */
	public void setClickNumber(java.lang.Integer clickNumber){
		this.clickNumber = clickNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  点击率
	 */
	@Column(name ="CLICK_RATE",nullable=true,scale=2,length=5)
	public BigDecimal getClickRate(){
		return this.clickRate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  点击率
	 */
	public void setClickRate(BigDecimal clickRate){
		this.clickRate = clickRate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  推送总量
	 */
	@Column(name ="PUSH_COUNT",nullable=true,length=10)
	public java.lang.Integer getPushCount(){
		return this.pushCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  推送总量
	 */
	public void setPushCount(java.lang.Integer pushCount){
		this.pushCount = pushCount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  推送类型
	 */
	@Column(name ="PUSH_TYPE",nullable=true,length=1)
	public java.lang.String getPushType(){
		return this.pushType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  推送类型
	 */
	public void setPushType(java.lang.String pushType){
		this.pushType = pushType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  推送链接
	 */
	@Column(name ="PUSH_URL",nullable=true)
	public java.lang.String getPushUrl(){
		return this.pushUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  推送链接
	 */
	public void setPushUrl(java.lang.String pushUrl){
		this.pushUrl = pushUrl;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  送达量
	 */
	@Column(name ="REACH",nullable=true,length=10)
	public java.lang.Integer getReach(){
		return this.reach;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  送达量
	 */
	public void setReach(java.lang.Integer reach){
		this.reach = reach;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  送达率
	 */
	@Column(name ="REACH_RATE",nullable=true,scale=2,length=5)
	public BigDecimal getReachRate(){
		return this.reachRate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  送达率
	 */
	public void setReachRate(BigDecimal reachRate){
		this.reachRate = reachRate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  购买单量
	 */
	@Column(name ="BUY_SINGLE",nullable=true,length=10)
	public java.lang.Integer getBuySingle(){
		return this.buySingle;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  购买单量
	 */
	public void setBuySingle(java.lang.Integer buySingle){
		this.buySingle = buySingle;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  购买率
	 */
	@Column(name ="BUY_RATE",nullable=true,scale=2,length=5)
	public BigDecimal getBuyRate(){
		return this.buyRate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  购买率
	 */
	public void setBuyRate(BigDecimal buyRate){
		this.buyRate = buyRate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  资料可信度
	 */
	@Column(name ="RELIABILITY",nullable=true,length=5)
	public java.lang.String getReliability(){
		return this.reliability;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  资料可信度
	 */
	public void setReliability(java.lang.String reliability){
		this.reliability = reliability;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容吸引度
	 */
	@Column(name ="ATTRACTION_DEGREE",nullable=true,length=5)
	public java.lang.String getAttractionDegree(){
		return this.attractionDegree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容吸引度
	 */
	public void setAttractionDegree(java.lang.String attractionDegree){
		this.attractionDegree = attractionDegree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  营销成功率
	 */
	@Column(name ="SUCCESS_DEGREE",nullable=true,scale=2,length=5)
	public java.lang.String getSuccessDegree(){
		return this.successDegree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  营销成功率
	 */
	public void setSuccessDegree(java.lang.String successDegree){
		this.successDegree = successDegree;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  短信内容占条数
	 */
	@Column(name ="MSG_NUM",nullable=true,length=10)
	public java.lang.Integer getMsgNum() {
		return msgNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  短信内容占条数
	 */
	public void setMsgNum(java.lang.Integer msgNum) {
		this.msgNum = msgNum;
	}
}
