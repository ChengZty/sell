package com.buss.goods.entity;

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
 * @Description: 商品活动明细
 * @author onlineGenerator
 * @date 2017-09-13 16:27:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_goods_act_detail", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TGoodsActDetailEntity implements java.io.Serializable {
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
	/**主表id*/
	private java.lang.String goodsActId;
	/**商品id*/
	private java.lang.String goodsId;
	/**商品款号*/
	@Excel(name="商品款号",width=15)
	private java.lang.String goodsCode;
	/**商品名称*/
	@Excel(name="商品名称",width=20)
	private java.lang.String goodsName;
	/**小图*/
//	@Excel(name="小图")
	private java.lang.String smallPic;
	/**原价*/
	@Excel(name="原价",width=12)
	private java.math.BigDecimal originalPrice;
	/**最低价折扣*/
	@Excel(name="最低价折扣",width=12)
	private java.math.BigDecimal lowestPriceDiscount;
	/**最低价*/
	@Excel(name="日常最低价",width=12)
	private java.math.BigDecimal lowestPrice;
	/**活动价折扣*/
	@Excel(name="活动价折扣",width=12)
	private java.math.BigDecimal discount;
	/**活动价*/
	@Excel(name="活动最低价",width=12)
	private java.math.BigDecimal actPrice;
	
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
	 *@return: java.lang.String  主表id
	 */
	@Column(name ="GOODS_ACT_ID",nullable=true,length=36)
	public java.lang.String getGoodsActId(){
		return this.goodsActId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表id
	 */
	public void setGoodsActId(java.lang.String goodsActId){
		this.goodsActId = goodsActId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品id
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品id
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品款号
	 */
	@Column(name ="GOODS_CODE",nullable=true,length=32)
	public java.lang.String getGoodsCode(){
		return this.goodsCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品款号
	 */
	public void setGoodsCode(java.lang.String goodsCode){
		this.goodsCode = goodsCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=32)
	public java.lang.String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsName(java.lang.String goodsName){
		this.goodsName = goodsName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  小图
	 */
	@Column(name ="SMALL_PIC",nullable=true,length=150)
	public java.lang.String getSmallPic(){
		return this.smallPic;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  小图
	 */
	public void setSmallPic(java.lang.String smallPic){
		this.smallPic = smallPic;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  原价
	 */
	@Column(name ="ORIGINAL_PRICE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getOriginalPrice(){
		return this.originalPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  原价
	 */
	public void setOriginalPrice(java.math.BigDecimal originalPrice){
		this.originalPrice = originalPrice;
	}
	@Column(name ="LOWEST_PRICE_DISCOUNT",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getLowestPriceDiscount() {
		return lowestPriceDiscount;
	}

	public void setLowestPriceDiscount(java.math.BigDecimal lowestPriceDiscount) {
		this.lowestPriceDiscount = lowestPriceDiscount;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  最低价
	 */
	@Column(name ="LOWEST_PRICE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getLowestPrice(){
		return this.lowestPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  最低价
	 */
	public void setLowestPrice(java.math.BigDecimal lowestPrice){
		this.lowestPrice = lowestPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  活动价
	 */
	@Column(name ="ACT_PRICE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getActPrice(){
		return this.actPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  活动价
	 */
	public void setActPrice(java.math.BigDecimal actPrice){
		this.actPrice = actPrice;
	}
	@Column(name ="discount",nullable=true,scale=2,length=5)
	public java.math.BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(java.math.BigDecimal discount) {
		this.discount = discount;
	}
	
}
