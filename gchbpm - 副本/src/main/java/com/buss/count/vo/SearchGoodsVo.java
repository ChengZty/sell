package com.buss.count.vo;


/**   
 * @Title: vo
 * @Description: 导购浏览统计
 * @author onlineGenerator
 * @date 2016-03-14 16:00:50
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class SearchGoodsVo implements java.io.Serializable {
	/**商品名称*/
	private java.lang.String goodsName;
	/**商品款号*/
	private java.lang.String code;
	/**品牌名称*/
	private java.lang.String brandName;
	/**一级分类*/
	private java.lang.String topCategoryId;
	/**二级分类*/
	private java.lang.String subCategoryId;
	/**三级分类*/
	private java.lang.String thridCategoryId;
	/*开始时间*/
	private java.lang.String startTime;
	/**结束时间*/
	private java.lang.String endTime;
	/**零售商id*/
	private java.lang.String retailerId;
	
	public SearchGoodsVo() {
		// TODO Auto-generated constructor stub
	}
	
	public SearchGoodsVo(String goodsName, String code, String brandName,
			String topCategoryId, String subCategoryId, String thridCategoryId,
			String startTime, String endTime,String retailerId) {
		super();
		this.goodsName = goodsName;
		this.code = code;
		this.brandName = brandName;
		this.topCategoryId = topCategoryId;
		this.subCategoryId = subCategoryId;
		this.thridCategoryId = thridCategoryId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.retailerId = retailerId;
	}

	public java.lang.String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	public java.lang.String getCode() {
		return code;
	}
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	public java.lang.String getBrandName() {
		return brandName;
	}
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	public java.lang.String getTopCategoryId() {
		return topCategoryId;
	}
	public void setTopCategoryId(java.lang.String topCategoryId) {
		this.topCategoryId = topCategoryId;
	}
	public java.lang.String getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(java.lang.String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public java.lang.String getThridCategoryId() {
		return thridCategoryId;
	}
	public void setThridCategoryId(java.lang.String thridCategoryId) {
		this.thridCategoryId = thridCategoryId;
	}
	public java.lang.String getStartTime() {
		return startTime;
	}
	public void setStartTime(java.lang.String startTime) {
		this.startTime = startTime;
	}
	public java.lang.String getEndTime() {
		return endTime;
	}
	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime;
	}

	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	
	
	
	
	
	
}
