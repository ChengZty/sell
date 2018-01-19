package com.buss.goods.vo;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.buss.goods.entity.TGoodsPicEntity;
import com.buss.goods.entity.TGoodsStoreEntity;

@SuppressWarnings("serial")
public class TGoodsGroupVo implements java.io.Serializable{
	@Excel(name="商品名称")
	private java.lang.String goodsName;
	/**原价*/
	private java.math.BigDecimal originalPrice;
	/**现价*/
	private java.math.BigDecimal currentPrice;
//	/**最低价*/
//	private java.math.BigDecimal lowestPrice;
	/**组合价*/
	private java.math.BigDecimal groupPrice;
	/**场景名称*/
	@Excel(name="场景")
	private java.lang.String sceneTypeName;
	private java.lang.String sceneType;
	@Excel(name="商品类目")
	private java.lang.String topCategoryName;
	private java.lang.String topCategoryId;
	@Excel(name="商品类别")
	private java.lang.String subGoodsTypeName;
	private java.lang.String subGoodsType;
	@Excel(name="单品1")
	private java.lang.String goodsDetailOne;
	@Excel(name="单品2")
	private java.lang.String goodsDetailTwo;
	@Excel(name="单品3")
	private java.lang.String goodsDetailThree;
	@Excel(name="单品4")
	private java.lang.String goodsDetailFour;
	@Excel(name="单品5")
	private java.lang.String goodsDetailFive;
	@Excel(name="单品6")
	private java.lang.String goodsDetailSix;
	@Excel(name="单品7")
	private java.lang.String goodsDetailSeven;
	@Excel(name="单品8")
	private java.lang.String goodsDetailEight;
	@Excel(name="单品9")
	private java.lang.String goodsDetailNine;
	@Excel(name="单品10")
	private java.lang.String goodsDetailTen;
	/**组合单品明细列表*/
	private List<TGoodsPicEntity> tGoodsPicDetails = new ArrayList<TGoodsPicEntity>();
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
	public java.math.BigDecimal getGroupPrice() {
		return groupPrice;
	}
	public void setGroupPrice(java.math.BigDecimal groupPrice) {
		this.groupPrice = groupPrice;
	}
	public java.lang.String getSceneTypeName() {
		return sceneTypeName;
	}
	public void setSceneTypeName(java.lang.String sceneTypeName) {
		this.sceneTypeName = sceneTypeName;
	}
	public java.lang.String getSceneType() {
		return sceneType;
	}
	public void setSceneType(java.lang.String sceneType) {
		this.sceneType = sceneType;
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
	public java.lang.String getSubGoodsTypeName() {
		return subGoodsTypeName;
	}
	public void setSubGoodsTypeName(java.lang.String subGoodsTypeName) {
		this.subGoodsTypeName = subGoodsTypeName;
	}
	public java.lang.String getSubGoodsType() {
		return subGoodsType;
	}
	public void setSubGoodsType(java.lang.String subGoodsType) {
		this.subGoodsType = subGoodsType;
	}
	public java.lang.String getGoodsDetailOne() {
		return goodsDetailOne;
	}
	public void setGoodsDetailOne(java.lang.String goodsDetailOne) {
		this.goodsDetailOne = goodsDetailOne;
	}
	public java.lang.String getGoodsDetailTwo() {
		return goodsDetailTwo;
	}
	public void setGoodsDetailTwo(java.lang.String goodsDetailTwo) {
		this.goodsDetailTwo = goodsDetailTwo;
	}
	public java.lang.String getGoodsDetailThree() {
		return goodsDetailThree;
	}
	public void setGoodsDetailThree(java.lang.String goodsDetailThree) {
		this.goodsDetailThree = goodsDetailThree;
	}
	public java.lang.String getGoodsDetailFour() {
		return goodsDetailFour;
	}
	public void setGoodsDetailFour(java.lang.String goodsDetailFour) {
		this.goodsDetailFour = goodsDetailFour;
	}
	public java.lang.String getGoodsDetailFive() {
		return goodsDetailFive;
	}
	public void setGoodsDetailFive(java.lang.String goodsDetailFive) {
		this.goodsDetailFive = goodsDetailFive;
	}
	public java.lang.String getGoodsDetailSix() {
		return goodsDetailSix;
	}
	public void setGoodsDetailSix(java.lang.String goodsDetailSix) {
		this.goodsDetailSix = goodsDetailSix;
	}
	public java.lang.String getGoodsDetailSeven() {
		return goodsDetailSeven;
	}
	public void setGoodsDetailSeven(java.lang.String goodsDetailSeven) {
		this.goodsDetailSeven = goodsDetailSeven;
	}
	public java.lang.String getGoodsDetailEight() {
		return goodsDetailEight;
	}
	public void setGoodsDetailEight(java.lang.String goodsDetailEight) {
		this.goodsDetailEight = goodsDetailEight;
	}
	public java.lang.String getGoodsDetailNine() {
		return goodsDetailNine;
	}
	public void setGoodsDetailNine(java.lang.String goodsDetailNine) {
		this.goodsDetailNine = goodsDetailNine;
	}
	public java.lang.String getGoodsDetailTen() {
		return goodsDetailTen;
	}
	public void setGoodsDetailTen(java.lang.String goodsDetailTen) {
		this.goodsDetailTen = goodsDetailTen;
	}
	public List<TGoodsPicEntity> gettGoodsPicDetails() {
		return tGoodsPicDetails;
	}
	public void settGoodsPicDetails(List<TGoodsPicEntity> tGoodsPicDetails) {
		this.tGoodsPicDetails = tGoodsPicDetails;
	}
	
}
