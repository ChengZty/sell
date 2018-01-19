package com.buss.paytype.entity;

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
 * @Description: 支付方式
 * @author onlineGenerator
 * @date 2016-03-16 16:23:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "base_pay_type", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class BasePayTypeEntity implements java.io.Serializable {
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
	/**支付方式*/
	@Excel(name="支付方式")
	private java.lang.String payType;
	/**支付编码*/
	@Excel(name="支付编码")
	private java.lang.String payCode;
	/**是否启用*/
	@Excel(name="是否启用")
	private java.lang.String isOpen;
	/**支付图标*/
	@Excel(name="支付图标")
	private java.lang.String payIcon;
	/**费率(单位‰)*/
    private java.math.BigDecimal rate;
	
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
	 *@return: java.lang.String  支付方式
	 */
	@Column(name ="PAY_TYPE",nullable=true,length=50)
	public java.lang.String getPayType(){
		return this.payType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付方式
	 */
	public void setPayType(java.lang.String payType){
		this.payType = payType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付编码
	 */
	@Column(name ="PAY_CODE",nullable=true,length=40)
	public java.lang.String getPayCode(){
		return this.payCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付编码
	 */
	public void setPayCode(java.lang.String payCode){
		this.payCode = payCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否启用
	 */
	@Column(name ="IS_OPEN",nullable=true,length=2)
	public java.lang.String getIsOpen(){
		return this.isOpen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否启用
	 */
	public void setIsOpen(java.lang.String isOpen){
		this.isOpen = isOpen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付图标
	 */
	@Column(name ="PAY_ICON",nullable=true,length=255)
	public java.lang.String getPayIcon(){
		return this.payIcon;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付图标
	 */
	public void setPayIcon(java.lang.String payIcon){
		this.payIcon = payIcon;
	}
	@Column(name ="rate")
	public java.math.BigDecimal getRate() {
		return rate;
	}

	public void setRate(java.math.BigDecimal rate) {
		this.rate = rate;
	}
	
}
