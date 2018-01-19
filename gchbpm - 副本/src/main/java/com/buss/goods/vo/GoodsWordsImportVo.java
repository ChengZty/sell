package com.buss.goods.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

@SuppressWarnings("serial")
public class GoodsWordsImportVo implements java.io.Serializable{
	//商品款号
	@Excel(name="款号")
	private java.lang.String goodsCode;
	/**话术*/
	@Excel(name="话术")
	private java.lang.String words;
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public java.lang.String getWords() {
		return words;
	}
	public void setWords(java.lang.String words) {
		this.words = words;
	}
	
}
