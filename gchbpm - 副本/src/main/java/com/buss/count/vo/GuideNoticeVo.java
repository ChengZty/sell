package com.buss.count.vo;


/**   
 * @Title: vo
 * @Description: 公司通知点击报表
 * @author onlineGenerator
 * @date 2016-03-14 16:00:50
 * @version V1.0   
 *
 */
public class GuideNoticeVo implements java.io.Serializable{
	/**userId*/
	private java.lang.String userId;
	/**导购名称*/
	private java.lang.String guideName;
	/**点击数*/
	private java.lang.Integer clickNum;
	/**停留时间*/
	private java.lang.Integer viewTime;
	/**查看日期*/
	private java.util.Date viewDate;
	
	public java.lang.String getUserId() {
		return userId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	public java.lang.String getGuideName() {
		return guideName;
	}
	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	public java.lang.Integer getClickNum() {
		return clickNum;
	}
	public void setClickNum(java.lang.Integer clickNum) {
		this.clickNum = clickNum;
	}
	public java.lang.Integer getViewTime() {
		return viewTime;
	}
	public void setViewTime(java.lang.Integer viewTime) {
		this.viewTime = viewTime;
	}
	public java.util.Date getViewDate() {
		return viewDate;
	}
	public void setViewDate(java.util.Date viewDate) {
		this.viewDate = viewDate;
	}
	
	
}
