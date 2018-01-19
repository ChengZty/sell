package com.buss.job.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 导购请假记录表
 * @author onlineGenerator
 * @date 2017-12-21 11:57:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_guide_holiday", schema = "")
@SuppressWarnings("serial")
public class TGuideHolidayEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**请假日期*/
	private java.util.Date holiday;
	/**导购id */
	private java.lang.String guideId;
	/**导购名*/
	private java.lang.String guideName;
	/**店铺id*/
	private java.lang.String storeId;
	/**店铺名*/
	private java.lang.String storeName;
	/**零售商id*/
	private java.lang.String retailerId;
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	
	@Column(name ="holiday")
	public java.util.Date getHoliday() {
		return holiday;
	}

	public void setHoliday(java.util.Date holiday) {
		this.holiday = holiday;
	}

	@Column(name ="guide_id")
	public java.lang.String getGuideId() {
		return guideId;
	}

	public void setGuideId(java.lang.String guideId) {
		this.guideId = guideId;
	}

	@Column(name ="guide_name")
	public java.lang.String getGuideName() {
		return guideName;
	}

	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}

	@Column(name ="store_id")
	public java.lang.String getStoreId() {
		return storeId;
	}

	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}

	@Column(name ="store_name")
	public java.lang.String getStoreName() {
		return storeName;
	}

	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}

	@Column(name ="retailer_id")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	
}
