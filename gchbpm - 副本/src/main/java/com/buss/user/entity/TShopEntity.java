package com.buss.user.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 店铺
 * @author onlineGenerator
 * @date 2017-02-15 20:23:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_shop", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TShopEntity implements java.io.Serializable {
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
	/**帐号*/
	@Excel(name="帐号")
	private java.lang.String code;
	/**店铺名称*/
	@Excel(name="店铺名称")
	private java.lang.String shopName;
	/**省ID*/
	@Excel(name="省ID")
	private java.lang.String provinceId;
	/**市ID*/
	@Excel(name="市ID")
	private java.lang.String cityId;
	/**省市*/
	@Excel(name="省市")
	private java.lang.String area;
	/**详细地址*/
	@Excel(name="详细地址")
	private java.lang.String detailAddress;
	/**电话*/
	@Excel(name="电话")
	private java.lang.String phoneNo;
	/**店铺等级*/
	private java.lang.String shopLevel;
	/**有效期*/
	@Excel(name="有效期")
	private java.util.Date validPeriod;
	/**店铺状态 1：激活，0：待激活*/
	@Excel(name="店铺状态")
	private java.lang.String shopStatus;
	/**是否显示 1：显示，0：不显示*/
	@Excel(name="是否显示")
	private java.lang.String isShow;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**零售商版本*/
	private java.lang.String retailerEdition;
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
	 *@return: java.lang.String  帐号
	 */
	@Column(name ="CODE",nullable=true,length=32)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  帐号
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  店铺名称
	 */
	@Column(name ="SHOP_NAME",nullable=true,length=32)
	public java.lang.String getShopName(){
		return this.shopName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  店铺名称
	 */
	public void setShopName(java.lang.String shopName){
		this.shopName = shopName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省ID
	 */
	@Column(name ="PROVINCE_ID",nullable=true,length=36)
	public java.lang.String getProvinceId(){
		return this.provinceId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省ID
	 */
	public void setProvinceId(java.lang.String provinceId){
		this.provinceId = provinceId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  市ID
	 */
	@Column(name ="CITY_ID",nullable=true,length=36)
	public java.lang.String getCityId(){
		return this.cityId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  市ID
	 */
	public void setCityId(java.lang.String cityId){
		this.cityId = cityId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省市
	 */
	@Column(name ="AREA",nullable=true,length=32)
	public java.lang.String getArea(){
		return this.area;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省市
	 */
	public void setArea(java.lang.String area){
		this.area = area;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  详细地址
	 */
	@Column(name ="DETAIL_ADDRESS",nullable=true,length=50)
	public java.lang.String getDetailAddress(){
		return this.detailAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  详细地址
	 */
	public void setDetailAddress(java.lang.String detailAddress){
		this.detailAddress = detailAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="PHONE_NO",nullable=true,length=11)
	public java.lang.String getPhoneNo(){
		return this.phoneNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPhoneNo(java.lang.String phoneNo){
		this.phoneNo = phoneNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  店铺等级
	 */
	@Column(name ="SHOP_LEVEL",nullable=true,length=32)
	public java.lang.String getShopLevel(){
		return this.shopLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  店铺等级
	 */
	public void setShopLevel(java.lang.String shopLevel){
		this.shopLevel = shopLevel;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  有效期
	 */
	@Column(name ="VALID_PERIOD",nullable=true,length=20)
	public java.util.Date getValidPeriod(){
		return this.validPeriod;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  有效期
	 */
	public void setValidPeriod(java.util.Date validPeriod){
		this.validPeriod = validPeriod;
	}
	@Column(name ="shop_Status")
	public java.lang.String getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(java.lang.String shopStatus) {
		this.shopStatus = shopStatus;
	}
	@Column(name ="is_show")
	public java.lang.String getIsShow() {
		return isShow;
	}

	public void setIsShow(java.lang.String isShow) {
		this.isShow = isShow;
	}

	@Column(name ="retailer_Id")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Column(name = "retailer_edition")
	public String getRetailerEdition() {
		return retailerEdition;
	}

	public void setRetailerEdition(String retailerEdition) {
		this.retailerEdition = retailerEdition;
	}
}
