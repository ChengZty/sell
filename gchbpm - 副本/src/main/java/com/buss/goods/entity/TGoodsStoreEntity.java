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
 * @Description: 商品规格库存信息表
 * @author onlineGenerator
 * @date 2016-03-17 18:09:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_goods_store", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TGoodsStoreEntity implements java.io.Serializable {
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
	/**商品ID*/
	@Excel(name="商品ID")
	private java.lang.String goodsId;
	/**规格一*/
	@Excel(name="规格一")
	private java.lang.String specificationOne;
	/**规格二*/
	@Excel(name="规格二")
	private java.lang.String specificationTwo;
	/**规格三*/
	@Excel(name="规格三")
	private java.lang.String specificationThree;
	/**规格四*/
	@Excel(name="规格四")
	private java.lang.String specificationFour;
	/**预警库存*/
	private java.math.BigDecimal alarmGoodsStock;
	/**库存*/
	@Excel(name="库存")
	private java.math.BigDecimal store;
	/**条码 一个规格对应一个条码*/
	@Excel(name="条码")
	private java.lang.String barCode;
	/**零售商id*/
	private java.lang.String retailerId;
	/**是否修改  非数据库字段*/
	private java.lang.String changed;
	
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
	 *@return: java.lang.String  商品ID
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品ID
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格一
	 */
	@Column(name ="SPECIFICATION_ONE",nullable=true,length=50)
	public java.lang.String getSpecificationOne(){
		return this.specificationOne;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格一
	 */
	public void setSpecificationOne(java.lang.String specificationOne){
		this.specificationOne = specificationOne;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格二
	 */
	@Column(name ="SPECIFICATION_TWO",nullable=true,length=50)
	public java.lang.String getSpecificationTwo(){
		return this.specificationTwo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格二
	 */
	public void setSpecificationTwo(java.lang.String specificationTwo){
		this.specificationTwo = specificationTwo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格三
	 */
	@Column(name ="SPECIFICATION_THREE",nullable=true,length=50)
	public java.lang.String getSpecificationThree(){
		return this.specificationThree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格三
	 */
	public void setSpecificationThree(java.lang.String specificationThree){
		this.specificationThree = specificationThree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格四
	 */
	@Column(name ="SPECIFICATION_FOUR",nullable=true,length=50)
	public java.lang.String getSpecificationFour(){
		return this.specificationFour;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格四
	 */
	public void setSpecificationFour(java.lang.String specificationFour){
		this.specificationFour = specificationFour;
	}
	@Column(name ="ALARM_GOODS_STOCK",nullable=true,length=12)
	public java.math.BigDecimal getAlarmGoodsStock() {
		return alarmGoodsStock;
	}

	public void setAlarmGoodsStock(java.math.BigDecimal alarmGoodsStock) {
		this.alarmGoodsStock = alarmGoodsStock;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  库存
	 */
	@Column(name ="STORE",nullable=true,length=12)
	public java.math.BigDecimal getStore(){
		return this.store;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  库存
	 */
	public void setStore(java.math.BigDecimal store){
		this.store = store;
	}
	@Column(name ="bar_Code")
	public java.lang.String getBarCode() {
		return barCode;
	}

	public void setBarCode(java.lang.String barCode) {
		this.barCode = barCode;
	}
	@Column(name ="retailer_id")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Transient
	public java.lang.String getChanged() {
		return changed;
	}

	public void setChanged(java.lang.String changed) {
		this.changed = changed;
	}
	
}
