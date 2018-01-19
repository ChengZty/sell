package com.buss.goods.vo;
import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class TGoodsSimpleVo implements java.io.Serializable{
	@Excel(name="商品名称",width=20)
	private java.lang.String goodsName;
	@Excel(name="款号",width=15)
	private java.lang.String goodsCode;
	@Excel(name="品牌",width=15)
	private java.lang.String brandName;
	@Excel(name="原价",width=10)
	private java.math.BigDecimal originalPrice;
	@Excel(name="最低价",width=10)
	private java.math.BigDecimal lowestPrice;
	@Excel(name="库存",width=10)
	private java.math.BigDecimal goodsStock;
	@Excel(name="状态",width=10,replace={"草稿箱中_0","待上架_3","销售中_4","已下架_5"})
	private java.lang.String goodsStatus;
	public java.lang.String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public java.lang.String getBrandName() {
		return brandName;
	}
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	public java.math.BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(java.math.BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
	public java.math.BigDecimal getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(java.math.BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	public java.math.BigDecimal getGoodsStock() {
		return goodsStock;
	}
	public void setGoodsStock(java.math.BigDecimal goodsStock) {
		this.goodsStock = goodsStock;
	}
	public java.lang.String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(java.lang.String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
	
}
