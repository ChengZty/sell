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
public class GuideGoodsTotalCountVo implements java.io.Serializable {
	/**uuid*/
	private java.lang.String id;
	/**商品名称*/
	private java.lang.String goodsName;
	/**一级分类*/
	@Excel(name="一级类目",width=20)
	private java.lang.String topCategoryId;
	/**二级分类*/
	@Excel(name="二级类目",width=20)
	private java.lang.String subCategoryId;
	/**品牌名称*/
	@Excel(name="品牌",width=20)
	private java.lang.String brandName;
	/**三级分类*/
	private java.lang.String thridCategoryId;
	/**商品款号*/
	@Excel(name="商品款号",width=40)
	private java.lang.String code;
	/**点击次数*/
	@Excel(name="点击次数",width=15)
	private java.lang.Long totalClickNum;
	/**商品推送数量*/
	@Excel(name="商品推送数量",width=15)
	private java.lang.Long totalPushNum;
	/**管家点评数量*/
	@Excel(name="管家点评数量",width=15)
	private java.lang.Long totalPublishNum;
	/**加代购车数量*/
	@Excel(name="加代购车数量",width=15)
	private java.lang.Long totalCartNum;
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
	public java.lang.Long getTotalClickNum() {
		return totalClickNum;
	}
	public void setTotalClickNum(java.lang.Long totalClickNum) {
		this.totalClickNum = totalClickNum;
	}
	public java.lang.Long getTotalPushNum() {
		return totalPushNum;
	}
	public void setTotalPushNum(java.lang.Long totalPushNum) {
		this.totalPushNum = totalPushNum;
	}
	public java.lang.Long getTotalPublishNum() {
		return totalPublishNum;
	}
	public void setTotalPublishNum(java.lang.Long totalPublishNum) {
		this.totalPublishNum = totalPublishNum;
	}
	public java.lang.Long getTotalCartNum() {
		return totalCartNum;
	}
	public void setTotalCartNum(java.lang.Long totalCartNum) {
		this.totalCartNum = totalCartNum;
	}
	public java.util.Date getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(java.util.Date searchTime) {
		this.searchTime = searchTime;
	}
	
	
	
	
}
