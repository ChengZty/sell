package com.buss.goods.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class LowestPriceImportVo implements java.io.Serializable{
	//商品款号
	@Excel(name="款号")
	private java.lang.String goodsCode;
	/**最低价折扣*/
	@Excel(name="最低价折扣")
	private java.math.BigDecimal lowestDiscount;
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public java.math.BigDecimal getLowestDiscount() {
		return lowestDiscount;
	}
	public void setLowestDiscount(java.math.BigDecimal lowestDiscount) {
		this.lowestDiscount = lowestDiscount;
	}
}
