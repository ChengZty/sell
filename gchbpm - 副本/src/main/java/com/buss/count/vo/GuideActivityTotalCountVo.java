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
public class GuideActivityTotalCountVo implements java.io.Serializable {
	/**uuid*/
	private java.lang.String id;
	/**标题*/
	@Excel(name="活动标题",width=40)
	private java.lang.String title;
	/**总点击数*/
	@Excel(name="点击次数")
	private java.lang.Long totalClickNum;
	
	/**查询时间*/
	private java.util.Date searchTime;
	
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
	public java.util.Date getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(java.util.Date searchTime) {
		this.searchTime = searchTime;
	}
	public java.lang.Long getTotalClickNum() {
		return totalClickNum;
	}
	public void setTotalClickNum(java.lang.Long totalClickNum) {
		this.totalClickNum = totalClickNum;
	}
	
	
	
	
	
}
