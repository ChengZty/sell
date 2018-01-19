package com.buss.sms.entity;

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
 * @Description: t_sms_send_template
 * @author onlineGenerator
 * @date 2017-02-25 12:31:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sms_send_template", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TSmsSendTemplateEntity implements java.io.Serializable {
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
	/**短信模板名称*/
	private java.lang.String templateName;
	/**签名Id*/
	private java.lang.String autographId;
	/**短信签名名称*/
	@Excel(name="短信签名名称")
	private java.lang.String autographName;
	/**正文内容*/
	@Excel(name="正文内容")
	private java.lang.String content;
	/**链接地址*/
	@Excel(name="链接地址")
	private java.lang.String url;
	/**结尾内容1*/
	@Excel(name="结尾内容1")
	private java.lang.String contentEnd;
	/**结尾内容2*/
	@Excel(name="结尾内容2")
	private java.lang.String contentEnd2;
	/**零售商ID*/
	private java.lang.String retailerId;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  签名ID
	 */
	@Column(name ="AUTOGRAPH_ID",nullable=true,length=36)
	public java.lang.String getAutographId() {
		return autographId;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  签名ID
	 */
	public void setAutographId(java.lang.String autographId) {
		this.autographId = autographId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生活馆名称
	 */
	@Column(name ="AUTOGRAPH_NAME",nullable=true,length=50)
	public java.lang.String getAutographName(){
		return this.autographName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生活馆名称
	 */
	public void setAutographName(java.lang.String autographName){
		this.autographName = autographName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  正文内容
	 */
	@Column(name ="CONTENT",nullable=true,length=200)
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
	 *@return: java.lang.String  链接地址
	 */
	@Column(name ="URL",nullable=true,length=150)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  链接地址
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
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
		this.contentEnd = contentEnd;
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
}
