package com.buss.job.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 导购金币汇总
 * @author onlineGenerator
 * @date 2016-11-04 18:25:40
 * @version V1.0   
 *
 */
public class TGuideGoldCountVo implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**完成数量*/
	@Excel(name="导购店铺",width=25)
	private java.lang.String storeId;
	/**导购userid*/
	@Excel(name="导购",width=25)
	private java.lang.String guideName;
	/**导购userid*/
	@Excel(name="导购手机号",width=25)
	private java.lang.String phoneNo;
	/**金币数*/
	@Excel(name="获取金币数",width=20,isStatistics = true)
	private java.lang.String goldNum;
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getStoreId() {
		return storeId;
	}
	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	public java.lang.String getGuideName() {
		return guideName;
	}
	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	public java.lang.String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(java.lang.String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public java.lang.String getGoldNum() {
		return goldNum;
	}
	public void setGoldNum(java.lang.String goldNum) {
		this.goldNum = goldNum;
	}
}
