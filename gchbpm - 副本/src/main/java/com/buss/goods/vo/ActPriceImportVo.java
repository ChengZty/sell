package com.buss.goods.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class ActPriceImportVo implements java.io.Serializable{
	//商品款号
	@Excel(name="款号",width=15)
	private java.lang.String goodsCode;
	/**活动价折扣*/
	@Excel(name="活动价折扣",width=12)
	private java.math.BigDecimal discount;
	/**活动价*/
	@Excel(name="活动价",width=12)
	private java.math.BigDecimal actPrice;
	/**错误提示*/
	@Excel(name="错误提示",width=50)
	private java.lang.String errTip="";
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public java.math.BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(java.math.BigDecimal discount) {
		this.discount = discount;
	}
	public java.math.BigDecimal getActPrice() {
		return actPrice;
	}
	public void setActPrice(java.math.BigDecimal actPrice) {
		this.actPrice = actPrice;
	}
	public java.lang.String getErrTip() {
		return errTip;
	}
	public void setErrTip(java.lang.String errTip) {
		this.errTip = errTip;
	}
	
}
