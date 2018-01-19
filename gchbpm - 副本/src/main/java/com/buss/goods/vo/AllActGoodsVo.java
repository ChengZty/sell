package com.buss.goods.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 商品活动明细
 * @author onlineGenerator
 * @date 2017-09-13 16:27:06
 * @version V1.0   
 *
 */
public class AllActGoodsVo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**主键*/
	private java.lang.String id;
	/**主表id*/
	private java.lang.String goodsActId;
	/**商品id*/
	private java.lang.String goodsId;
	/**商品款号*/
	@Excel(name="商品款号",width=15)
	private java.lang.String goodsCode;
	/**商品名称*/
	@Excel(name="商品名称",width=25)
	private java.lang.String goodsName;
	/**小图*/
//	@Excel(name="小图")
	private java.lang.String smallPic;
	/**原价*/
	@Excel(name="原价",width=12)
	private java.math.BigDecimal originalPrice;
	/**最低价*/
	@Excel(name="日常最低价",width=12)
	private java.math.BigDecimal lowestPrice;
	/**活动价*/
	@Excel(name="活动最低价",width=12)
	private java.math.BigDecimal actPrice;
	/**活动名称*/
	@Excel(name="活动名称",width=25)
	private java.lang.String title;
	/**开始时间*/
	@Excel(name="开始时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date beginTime;
	/**结束时间*/
	@Excel(name="结束时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date endTime;
	/**审核人*/
	@Excel(name="审核人",width=12)
	private java.lang.String auditor;
	/**审核时间*/
	@Excel(name="审核时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date auditTime;
	/**审核状态 1：待审核，2：已审核*/
	private java.lang.String auditStatus;
	/**活动状态 非数据库字段 1：待审核，2：待开始（已审核），3：进行中，4：已结束(包括已下架）*/
	//该状态有时间和审核状态已经是否有效字段共同确定状态
	@Excel(name="活动状态",width=12,replace={"待审核_1","待开始_2","进行中_3","已结束_4"})
	private java.lang.String actStatus;
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getGoodsActId() {
		return goodsActId;
	}
	public void setGoodsActId(java.lang.String goodsActId) {
		this.goodsActId = goodsActId;
	}
	public java.lang.String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(java.lang.String goodsId) {
		this.goodsId = goodsId;
	}
	public java.lang.String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(java.lang.String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public java.lang.String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	public java.lang.String getSmallPic() {
		return smallPic;
	}
	public void setSmallPic(java.lang.String smallPic) {
		this.smallPic = smallPic;
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
	public java.math.BigDecimal getActPrice() {
		return actPrice;
	}
	public void setActPrice(java.math.BigDecimal actPrice) {
		this.actPrice = actPrice;
	}
	public java.lang.String getTitle() {
		return title;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	public java.util.Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public java.lang.String getAuditor() {
		return auditor;
	}
	public void setAuditor(java.lang.String auditor) {
		this.auditor = auditor;
	}
	public java.util.Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}
	public java.lang.String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(java.lang.String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public java.lang.String getActStatus() {
		return actStatus;
	}
	public void setActStatus(java.lang.String actStatus) {
		this.actStatus = actStatus;
	}
	
}
