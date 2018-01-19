package com.buss.user.vo;

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
public class GuideContactCountVo implements java.io.Serializable {
	/**uuid*/
	private java.lang.String id;
	/**导购名称*/
	@Excel(name="导购姓名",width=12)
	private java.lang.String guideName;
	/**导购所属店铺*/
	@Excel(name="导购所属店铺",width=20)
	private java.lang.String storeName;
	/**QQ维护次数*/
	@Excel(name="QQ维护次数",width=16)
	private java.math.BigDecimal qqNumber;
	/**QQ维护人数*/
	@Excel(name="QQ维护人数",width=16)
	private java.lang.Long qqCount;
	/**微信维护次数*/
	@Excel(name="微信维护次数",width=16)
	private java.math.BigDecimal weChartNumber;
	/**微信维护人数*/
	@Excel(name="微信维护人数",width=16)
	private java.lang.Long weChartCount;
	/**短信维护次数*/
	@Excel(name="短信维护次数",width=16)
	private java.math.BigDecimal msgNumber;
	/**短信维护人数*/
	@Excel(name="短信维护人数",width=16)
	private java.lang.Long msgCount;
	/**电话维护次数*/
	@Excel(name="电话维护次数",width=16)
	private java.math.BigDecimal phoneNumber;
	/**电话维护人数*/
	@Excel(name="电话维护人数",width=16)
	private java.lang.Long phoneCount;
	/**维护次数合计*/
	@Excel(name="维护次数合计",width=16)
	private java.math.BigDecimal number;
	/**维护次数合计*/
	@Excel(name="维护人数合计",width=16)
	private java.lang.Long userCount;
	
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
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
	public java.math.BigDecimal getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(java.math.BigDecimal qqNumber) {
		this.qqNumber = qqNumber;
	}
	public java.lang.Long getQqCount() {
		return qqCount;
	}
	public void setQqCount(java.lang.Long qqCount) {
		this.qqCount = qqCount;
	}
	public java.math.BigDecimal getWeChartNumber() {
		return weChartNumber;
	}
	public void setWeChartNumber(java.math.BigDecimal weChartNumber) {
		this.weChartNumber = weChartNumber;
	}
	public java.lang.Long getWeChartCount() {
		return weChartCount;
	}
	public void setWeChartCount(java.lang.Long weChartCount) {
		this.weChartCount = weChartCount;
	}
	public java.math.BigDecimal getMsgNumber() {
		return msgNumber;
	}
	public void setMsgNumber(java.math.BigDecimal msgNumber) {
		this.msgNumber = msgNumber;
	}
	public java.lang.Long getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(java.lang.Long msgCount) {
		this.msgCount = msgCount;
	}
	public java.math.BigDecimal getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(java.math.BigDecimal phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public java.lang.Long getPhoneCount() {
		return phoneCount;
	}
	public void setPhoneCount(java.lang.Long phoneCount) {
		this.phoneCount = phoneCount;
	}
	public java.math.BigDecimal getNumber() {
		return number;
	}
	public void setNumber(java.math.BigDecimal number) {
		this.number = number;
	}
	public java.lang.Long getUserCount() {
		return userCount;
	}
	public void setUserCount(java.lang.Long userCount) {
		this.userCount = userCount;
	}
	
}
