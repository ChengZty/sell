package com.buss.goods.vo;

import java.util.List;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.buss.goods.entity.TGoodsStoreEntity;

@SuppressWarnings("serial")
public class TNewGoodsImportVo implements java.io.Serializable{
	@Excel(name="商品名称",width=30)
	private java.lang.String goodsName;
	@Excel(name="品牌",width=15)
	private java.lang.String brandName;
	private java.lang.String brandId;
	private java.lang.String brandCode;
	//商品款号
	@Excel(name="款号",width=20)
	private java.lang.String goodsCode;
	//商品编号(零售商编码+品牌编码+款号)
	private java.lang.String code;
	/**原价*/
	@Excel(name="原价")
	private java.math.BigDecimal originalPrice;
	/**最低价折扣*/
	@Excel(name="最低价折扣")
	private java.math.BigDecimal lowestPriceDiscount;
	/**最低价*/
	private java.math.BigDecimal lowestPrice;
	/**一级类目*/
	private java.lang.String topCategoryName;
	private java.lang.String topCategoryId;
	/**二级类目*/
	@Excel(name="二级类目")
	private java.lang.String subCategoryName;
	private java.lang.String subCategoryId;
	/**三级类目*/
	@Excel(name="三级类目")
	private java.lang.String thridCategoryName;
	private java.lang.String thridCategoryId;
	/**规格一*/
	@Excel(name="规格一")
	private java.lang.String specificationOne;
	/**规格二*/
	@Excel(name="规格二")
	private java.lang.String specificationTwo;
	/**库存*/
	@Excel(name="库存")
	private java.math.BigDecimal store;
	/**条码 一个规格对应一个条码*/
	@Excel(name="条码",width=20)
	private java.lang.String barCode;
	@Excel(name="备注",width=50)
	private java.lang.String remark="";
	/**库存*/
	private java.math.BigDecimal goodsStock;
	
	private List<TGoodsStoreEntity> tGoodsStoreDetails;//商品库存明细
	public java.lang.String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	public java.lang.String getBrandName() {
		return brandName;
	}
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	public java.lang.String getBrandId() {
		return brandId;
	}
	public void setBrandId(java.lang.String brandId) {
		this.brandId = brandId;
	}
	public java.lang.String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(java.lang.String brandCode) {
		this.brandCode = brandCode;
	}
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public java.lang.String getCode() {
		return code;
	}
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	public java.math.BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(java.math.BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
	public java.math.BigDecimal getLowestPriceDiscount() {
		return lowestPriceDiscount;
	}
	public void setLowestPriceDiscount(java.math.BigDecimal lowestPriceDiscount) {
		this.lowestPriceDiscount = lowestPriceDiscount;
	}
	public java.math.BigDecimal getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(java.math.BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	public java.lang.String getTopCategoryName() {
		return topCategoryName;
	}
	public void setTopCategoryName(java.lang.String topCategoryName) {
		this.topCategoryName = topCategoryName;
	}
	public java.lang.String getTopCategoryId() {
		return topCategoryId;
	}
	public void setTopCategoryId(java.lang.String topCategoryId) {
		this.topCategoryId = topCategoryId;
	}
	public java.lang.String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(java.lang.String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public java.lang.String getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(java.lang.String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public java.lang.String getThridCategoryName() {
		return thridCategoryName;
	}
	public void setThridCategoryName(java.lang.String thridCategoryName) {
		this.thridCategoryName = thridCategoryName;
	}
	public java.lang.String getThridCategoryId() {
		return thridCategoryId;
	}
	public void setThridCategoryId(java.lang.String thridCategoryId) {
		this.thridCategoryId = thridCategoryId;
	}
	public List<TGoodsStoreEntity> gettGoodsStoreDetails() {
		return tGoodsStoreDetails;
	}
	public void settGoodsStoreDetails(List<TGoodsStoreEntity> tGoodsStoreDetails) {
		this.tGoodsStoreDetails = tGoodsStoreDetails;
	}
	public java.math.BigDecimal getGoodsStock() {
		return goodsStock;
	}
	public void setGoodsStock(java.math.BigDecimal goodsStock) {
		this.goodsStock = goodsStock;
	}
	public java.lang.String getSpecificationOne() {
		return specificationOne;
	}
	public void setSpecificationOne(java.lang.String specificationOne) {
		this.specificationOne = specificationOne;
	}
	public java.lang.String getSpecificationTwo() {
		return specificationTwo;
	}
	public void setSpecificationTwo(java.lang.String specificationTwo) {
		this.specificationTwo = specificationTwo;
	}
	public java.math.BigDecimal getStore() {
		return store;
	}
	public void setStore(java.math.BigDecimal store) {
		this.store = store;
	}
	public java.lang.String getBarCode() {
		return barCode;
	}
	public void setBarCode(java.lang.String barCode) {
		this.barCode = barCode;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
}
