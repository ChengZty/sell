package com.buss.goods.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class GoodsStockImportVo implements java.io.Serializable{
	private java.lang.String id;
	private java.lang.String goodsId;
	/**条码*/
	@Excel(name="条码",width=20)
	private java.lang.String barCode;
	/**库存*/
	@Excel(name="库存",width=8)
	private java.math.BigDecimal stock;
	/**库存变动数量*/
	private java.math.BigDecimal stockChanged;
	/**备注*/
	@Excel(name="备注",width=50)
	private java.lang.String remark="";
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(java.lang.String goodsId) {
		this.goodsId = goodsId;
	}
	public java.lang.String getBarCode() {
		return barCode;
	}
	public void setBarCode(java.lang.String barCode) {
		this.barCode = barCode;
	}
	public java.math.BigDecimal getStock() {
		return stock;
	}
	public void setStock(java.math.BigDecimal stock) {
		this.stock = stock;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public java.math.BigDecimal getStockChanged() {
		return stockChanged;
	}
	public void setStockChanged(java.math.BigDecimal stockChanged) {
		this.stockChanged = stockChanged;
	}
	
}
