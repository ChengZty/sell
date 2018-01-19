package com.buss.count.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: vo
 * @Description: 导购浏览统计
 * @author onlineGenerator
 * @date 2016-03-14 16:00:50
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class GuideGoodsCountVo implements java.io.Serializable {
	/**uuid*/
	private java.lang.String id;
	/**商品款号*/
	@Excel(name="商品款号",width=20)
	private java.lang.String code;
	/**商品名称*/
	@Excel(name="商品名称",width=20)
	private java.lang.String goodsName;
	/**品牌名称*/
	@Excel(name="品牌",width=20)
	private java.lang.String brandName;
	/**一级分类*/
	private java.lang.String topCategoryId;
	/**二级分类*/
	private java.lang.String subCategoryId;
	/**三级分类*/
	private java.lang.String thridCategoryId;
	/**导购所属店铺id*/
	private java.lang.String storeId;
	/**导购所属店铺*/
	@Excel(name="导购所属店铺",width=20)
	private java.lang.String storeName;
	/**导购名称*/
	@Excel(name="导购姓名",width=20)
	private java.lang.String guideName;
	/**总点击数*/
	@Excel(name="点击次数")
	private java.math.BigDecimal totalClickNum;
	/**第一次点击时间*/
	@Excel(name="第一次点击时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date minClickTime;
	/**最近点击时间*/
	@Excel(name="最近一次点击时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date maxClickTime;
	/**总停留时间*/
	@Excel(name="总停留时间(s)",width=20)
	private java.math.BigDecimal totalStillTime;//单位为秒
	
	/**查询时间*/
	private java.util.Date searchTime;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getCode() {
		return code;
	}
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	public java.lang.String getGuideName() {
		return guideName;
	}
	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	public java.lang.String getStoreName() {
		return storeName;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	public java.math.BigDecimal getTotalClickNum() {
		return totalClickNum;
	}
	public void setTotalClickNum(java.math.BigDecimal totalClickNum) {
		this.totalClickNum = totalClickNum;
	}
	public java.math.BigDecimal getTotalStillTime() {
		return totalStillTime;
	}
	public void setTotalStillTime(java.math.BigDecimal totalStillTime) {
		this.totalStillTime = totalStillTime;
	}
	public java.util.Date getMaxClickTime() {
		return maxClickTime;
	}
	public void setMaxClickTime(java.util.Date maxClickTime) {
		this.maxClickTime = maxClickTime;
	}
	public java.util.Date getMinClickTime() {
		return minClickTime;
	}
	public void setMinClickTime(java.util.Date minClickTime) {
		this.minClickTime = minClickTime;
	}
	public java.lang.String getStoreId() {
		return storeId;
	}
	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
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
	public java.util.Date getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(java.util.Date searchTime) {
		this.searchTime = searchTime;
	}
	
	
	
	
}
