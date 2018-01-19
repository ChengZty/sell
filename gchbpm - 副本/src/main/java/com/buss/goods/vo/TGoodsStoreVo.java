package com.buss.goods.vo;

/**   
 * @Title: Entity
 * @Description: 商品规格库存vo
 * @author onlineGenerator
 * @date 2016-03-17 18:09:34
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class TGoodsStoreVo implements java.io.Serializable {
	/**商品款号*/
	private java.lang.String goodsCode;
	/**规格一*/
	private java.lang.String specificationOne;
	/**规格二*/
	private java.lang.String specificationTwo;
	/**库存*/
	private java.math.BigDecimal store;
	/**条码 一个规格对应一个条码*/
	private java.lang.String barCode;
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
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
	
	
}
