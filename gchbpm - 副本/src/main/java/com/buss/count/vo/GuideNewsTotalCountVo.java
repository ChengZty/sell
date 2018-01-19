package com.buss.count.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: vo
 * @Description: 资讯汇总统计
 * @author onlineGenerator
 * @date 2016-03-14 16:00:50
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class GuideNewsTotalCountVo implements java.io.Serializable {
	/**uuid*/
	private java.lang.String id;
	/**资讯分类id*/
	@Excel(name="资讯分类",width=40)
	private java.lang.String newsType;
	/**资讯标题*/
	@Excel(name="资讯标题",width=40)
	private java.lang.String title;
	/**点击次数*/
	@Excel(name="点击次数",width=15)
	private java.lang.Long clickNum;
	/**点赞数量*/
	@Excel(name="点赞数量",width=15)
	private java.math.BigDecimal goodNum;
	/**无感数量*/
	@Excel(name="无感数量",width=15)
	private java.math.BigDecimal noSenseNum;
	/**收藏数量*/
	@Excel(name="收藏数量",width=15)
	private java.lang.Long collectNum;
	/**评论数量*/
	@Excel(name="评论数量",width=15)
	private java.lang.Long commentNum;
	/**推送数量*/
	@Excel(name="推送数量",width=15)
	private java.lang.Long totalPushNum;
	/**通讯录推送数量*/
	@Excel(name="通讯录推送数量",width=15)
	private java.math.BigDecimal contPushNum;
	/**微信推送数量*/
	@Excel(name="微信推送数量",width=15)
	private java.math.BigDecimal wxPushNum;
	/**朋友圈数量*/
	@Excel(name="朋友圈推送数量",width=15)
	private java.math.BigDecimal wcmPushNum;
	/**QQ推送数量*/
	@Excel(name="QQ推送数量",width=15)
	private java.math.BigDecimal qqPushNum;
	/**查询时间*/
	private java.util.Date searchTime;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.util.Date getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(java.util.Date searchTime) {
		this.searchTime = searchTime;
	}
	public java.lang.String getNewsType() {
		return newsType;
	}
	public void setNewsType(java.lang.String newsType) {
		this.newsType = newsType;
	}
	public java.lang.String getTitle() {
		return title;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	public java.math.BigDecimal getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(java.math.BigDecimal goodNum) {
		this.goodNum = goodNum;
	}
	public java.math.BigDecimal getNoSenseNum() {
		return noSenseNum;
	}
	public void setNoSenseNum(java.math.BigDecimal noSenseNum) {
		this.noSenseNum = noSenseNum;
	}
	public java.lang.Long getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(java.lang.Long commentNum) {
		this.commentNum = commentNum;
	}
	public java.math.BigDecimal getContPushNum() {
		return contPushNum;
	}
	public void setContPushNum(java.math.BigDecimal contPushNum) {
		this.contPushNum = contPushNum;
	}
	public java.math.BigDecimal getWxPushNum() {
		return wxPushNum;
	}
	public void setWxPushNum(java.math.BigDecimal wxPushNum) {
		this.wxPushNum = wxPushNum;
	}
	public java.math.BigDecimal getWcmPushNum() {
		return wcmPushNum;
	}
	public void setWcmPushNum(java.math.BigDecimal wcmPushNum) {
		this.wcmPushNum = wcmPushNum;
	}
	public java.math.BigDecimal getQqPushNum() {
		return qqPushNum;
	}
	public void setQqPushNum(java.math.BigDecimal qqPushNum) {
		this.qqPushNum = qqPushNum;
	}
	public java.lang.Long getClickNum() {
		return clickNum;
	}
	public void setClickNum(java.lang.Long clickNum) {
		this.clickNum = clickNum;
	}
	public java.lang.Long getCollectNum() {
		return collectNum;
	}
	public void setCollectNum(java.lang.Long collectNum) {
		this.collectNum = collectNum;
	}
	public java.lang.Long getTotalPushNum() {
		return totalPushNum;
	}
	public void setTotalPushNum(java.lang.Long totalPushNum) {
		this.totalPushNum = totalPushNum;
	}
	
	
	
	
}
