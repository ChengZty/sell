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
public class GuideActivityTotalVo implements java.io.Serializable {
	/**uuid*/
	private java.lang.String id;
	/**标题*/
	@Excel(name="活动标题",width=40)
	private java.lang.String title;
	/**活动开始时间*/
	@Excel(name="活动开始时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date startTime;
	/**活动结束时间*/
	@Excel(name="活动结束时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date endTime;
	/**最后点击时间*/
	@Excel(name="最后点击时间",format="yyyy-MM-dd HH:mm:ss",width=20)
	private java.util.Date clickTime;
	/**总点击数*/
	@Excel(name="点击次数")
	private java.math.BigDecimal clickNum;
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getTitle() {
		return title;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	public java.util.Date getStartTime() {
		return startTime;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public java.util.Date getClickTime() {
		return clickTime;
	}
	public void setClickTime(java.util.Date clickTime) {
		this.clickTime = clickTime;
	}
	public java.math.BigDecimal getClickNum() {
		return clickNum;
	}
	public void setClickNum(java.math.BigDecimal clickNum) {
		this.clickNum = clickNum;
	}
	
}
