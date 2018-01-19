package com.buss.user.entity;

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
 * @Description: 顾客VIP等级
 * @author onlineGenerator
 * @date 2016-03-29 14:23:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_vip_level", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TVipLevelEntity implements java.io.Serializable {
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
	/**VIP编码*/
	@Excel(name="VIP编码")
	private java.lang.String vipCode;
	/**VIP名称*/
	@Excel(name="VIP名称")
	private java.lang.String vipName;
	/**所属省*/
	private java.lang.String provinceId;
	/**所属市*/
	private java.lang.String cityId;
	/**所属零售商*/
	private java.lang.String retailerId;
	/**折扣*/
	@Excel(name="折扣")
	private java.math.BigDecimal discount;
	
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
	 *@return: java.lang.String  VIP编码
	 */
	@Column(name ="VIP_CODE",nullable=true,length=32)
	public java.lang.String getVipCode(){
		return this.vipCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  VIP编码
	 */
	public void setVipCode(java.lang.String vipCode){
		this.vipCode = vipCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  VIP名称
	 */
	@Column(name ="VIP_NAME",nullable=true,length=32)
	public java.lang.String getVipName(){
		return this.vipName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  VIP名称
	 */
	public void setVipName(java.lang.String vipName){
		this.vipName = vipName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属省
	 */
	@Column(name ="PROVINCE_ID",nullable=true,length=36)
	public java.lang.String getProvinceId(){
		return this.provinceId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属省
	 */
	public void setProvinceId(java.lang.String provinceId){
		this.provinceId = provinceId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属市
	 */
	@Column(name ="CITY_ID",nullable=true,length=36)
	public java.lang.String getCityId(){
		return this.cityId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属市
	 */
	public void setCityId(java.lang.String cityId){
		this.cityId = cityId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属零售商
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属零售商
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  折扣
	 */
	@Column(name ="DISCOUNT",nullable=true,scale=2,length=5)
	public java.math.BigDecimal getDiscount(){
		return this.discount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  折扣
	 */
	public void setDiscount(java.math.BigDecimal discount){
		this.discount = discount;
	}
}
