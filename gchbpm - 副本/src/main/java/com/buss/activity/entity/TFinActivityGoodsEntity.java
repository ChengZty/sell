package com.buss.activity.entity;

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
 * @Description: 活动奖励商品
 * @author onlineGenerator
 * @date 2016-11-26 19:30:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_fin_activity_goods", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TFinActivityGoodsEntity implements java.io.Serializable {
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
	/**活动奖励ID*/
	@Excel(name="活动奖励ID")
	private java.lang.String finActId;
	/**商品ID*/
	@Excel(name="商品ID")
	private java.lang.String goodsId;
	/**商品零售商ID*/
	@Excel(name="商品零售商ID")
	private java.lang.String goodsStoreId;
	/**商品零售商类型*/
	@Excel(name="商品零售商类型")
	private java.lang.String goodsStoreType;
	/**零售商ID*/
	@Excel(name="零售商ID")
	private java.lang.String retailerId;
	/**排序*/
	@Excel(name="排序")
	private java.lang.Integer sortNum;
	
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
	 *@return: java.lang.String  活动奖励ID
	 */
	@Column(name ="FIN_ACT_ID",nullable=true,length=36)
	public java.lang.String getFinActId(){
		return this.finActId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  活动奖励ID
	 */
	public void setFinActId(java.lang.String finActId){
		this.finActId = finActId;
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
	 *@return: java.lang.String  商品零售商ID
	 */
	@Column(name ="GOODS_STORE_ID",nullable=true,length=36)
	public java.lang.String getGoodsStoreId(){
		return this.goodsStoreId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品零售商ID
	 */
	public void setGoodsStoreId(java.lang.String goodsStoreId){
		this.goodsStoreId = goodsStoreId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品零售商类型
	 */
	@Column(name ="GOODS_STORE_TYPE",nullable=true,length=1)
	public java.lang.String getGoodsStoreType(){
		return this.goodsStoreType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品零售商类型
	 */
	public void setGoodsStoreType(java.lang.String goodsStoreType){
		this.goodsStoreType = goodsStoreType;
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
	@Column(name ="sort_Num",nullable=true,length=5)
	public java.lang.Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(java.lang.Integer sortNum) {
		this.sortNum = sortNum;
	}
	
}
