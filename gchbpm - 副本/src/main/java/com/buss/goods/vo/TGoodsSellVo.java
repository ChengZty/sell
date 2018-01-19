package com.buss.goods.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class TGoodsSellVo implements java.io.Serializable{
	@Excel(name="类目",width=20)
	private java.lang.String category;
	@Excel(name="品牌",width=20)
	private java.lang.String brandName;
	@Excel(name="款号",width=20)
	private java.lang.String goodsCode;
	@Excel(name="名称",width=20)
	private java.lang.String goodsName;
	@Excel(name="原价")
	private java.math.BigDecimal originalPrice;
	@Excel(name="现价")
	private java.math.BigDecimal currentPrice;
	@Excel(name="最低价")
	private java.math.BigDecimal lowestPrice;
	@Excel(name="商品类别")
	private java.lang.String subGoodsType;
	@Excel(name="运费",width=15)
	private java.lang.String fareType;
	@Excel(name="运费优惠",width=20)
	private java.lang.String farePref;
//	@Excel(name="规格")
	private java.lang.String specInfo;
	
	@Excel(name="库存")
	private java.math.BigDecimal goodsStock;
	@Excel(name="销量")
	private java.math.BigDecimal salesVolume;
	public java.lang.String getCategory() {
		return category;
	}
	public void setCategory(java.lang.String category) {
		this.category = category;
	}
	public java.lang.String getBrandName() {
		return brandName;
	}
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public java.lang.String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	public java.math.BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(java.math.BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
	public java.math.BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(java.math.BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public java.math.BigDecimal getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(java.math.BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	public java.lang.String getSubGoodsType() {
		return subGoodsType;
	}
	public void setSubGoodsType(java.lang.String subGoodsType) {
		this.subGoodsType = subGoodsType;
	}
	public java.lang.String getFareType() {
		return fareType;
	}
	public void setFareType(java.lang.String fareType) {
		this.fareType = fareType;
	}
	public java.lang.String getFarePref() {
		return farePref;
	}
	public void setFarePref(java.lang.String farePref) {
		this.farePref = farePref;
	}
	public java.lang.String getSpecInfo() {
		return specInfo;
	}
	public void setSpecInfo(java.lang.String specInfo) {
		this.specInfo = specInfo;
	}
	public java.math.BigDecimal getGoodsStock() {
		return goodsStock;
	}
	public void setGoodsStock(java.math.BigDecimal goodsStock) {
		this.goodsStock = goodsStock;
	}
	public java.math.BigDecimal getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(java.math.BigDecimal salesVolume) {
		this.salesVolume = salesVolume;
	}
	
	
	
}
