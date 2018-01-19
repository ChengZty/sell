package com.buss.goods.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 商品明细图片
 * @author onlineGenerator
 * @date 2016-03-17 18:09:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_goods_pic", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TGoodsPicEntity implements java.io.Serializable {
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
	/**产品ID*/
	@Excel(name="产品ID")
	private java.lang.String goodsId;
	/**单品ID*/
	@Excel(name="单品ID")
	private java.lang.String detailGoodsId;
	/**单品组合价*/
	@Excel(name="组合价")
	private java.math.BigDecimal groupPrice = BigDecimal.ZERO;
	/*下面的字段都改为连表查*/
	/**图片地址*/
	@Excel(name="图片地址")
	private java.lang.String picUrl;
	@Excel(name="商品款号")
	private java.lang.String goodsCode;
	/**单品最低价*/
	private java.math.BigDecimal lowestPrice;
	/**原价*/
	private java.math.BigDecimal originalPrice;
	/**现价*/
	private java.math.BigDecimal currentPrice;
	/**品牌编码*/
	private java.lang.String brandCode;
	
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
	 *@return: java.lang.String  图片地址
	 */
	@Transient
	public java.lang.String getPicUrl(){
		return this.picUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片地址
	 */
	public void setPicUrl(java.lang.String picUrl){
		this.picUrl = picUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组合价
	 */
	@Column(name ="GROUP_PRICE",nullable=true,length=32)
	public java.math.BigDecimal getGroupPrice(){
		return this.groupPrice==null?BigDecimal.ZERO:groupPrice.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组合价
	 */
	public void setGroupPrice(java.math.BigDecimal groupPrice){
		this.groupPrice = groupPrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产品id
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品id
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	@Transient
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}

	@Column(name ="DETAIL_GOODS_ID",nullable=true,length=36)
	public java.lang.String getDetailGoodsId() {
		return detailGoodsId;
	}

	public void setDetailGoodsId(java.lang.String detailGoodsId) {
		this.detailGoodsId = detailGoodsId;
	}
	@Transient
	public java.math.BigDecimal getLowestPrice() {
		return lowestPrice==null?null:lowestPrice.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public void setLowestPrice(java.math.BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	@Transient
	public java.math.BigDecimal getOriginalPrice() {
		return originalPrice==null?null:originalPrice.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public void setOriginalPrice(java.math.BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
	@Transient
	public java.math.BigDecimal getCurrentPrice() {
		return currentPrice==null?null:currentPrice.setScale(0,BigDecimal.ROUND_HALF_UP);
	}

	public void setCurrentPrice(java.math.BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	@Transient
	public java.lang.String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(java.lang.String brandCode) {
		this.brandCode = brandCode;
	}
	
}
