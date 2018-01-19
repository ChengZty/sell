package org.jeecgframework.web.system.pojo.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;


/**   
 * @Title: Entity
 * @Description: 公司通知点击统计
 * @author onlineGenerator
 * @date 2017-05-06 15:05:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_notice_store_count", schema = "")
@SuppressWarnings("serial")
public class TStoreSNoticeCount implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**通知id*/
	private java.lang.String noticeId;
	/**导购id*/
	private java.lang.String userId;
	/**导购昵称*/
	@Excel(name="导购姓名")
	private java.lang.String guideName;
	/**点击次数*/
	@Excel(name="点击次数")
	private java.lang.Long clickNum;
	/**停留时间*/
	@Excel(name="停留时间")
	private java.lang.Long stillTime;//单位为秒
	/**查看日期*/
	@Excel(name="查看日期",format = "yyyy-MM-dd")
	private java.util.Date clickTime;
	/**零售商id*/
	private java.lang.String retailerId;
	
	public TStoreSNoticeCount() {
		// TODO Auto-generated constructor stub
	}
	
	public TStoreSNoticeCount(String noticeId, String userId,
			Long stillTime, Date clickTime, String retailerId) {
		super();
		this.noticeId = noticeId;
		this.userId = userId;
		this.stillTime = stillTime;
		this.clickTime = clickTime;
		this.retailerId = retailerId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知id
	 */
	@Column(name ="NOTICE_ID",nullable=true,length=255)
	public java.lang.String getNoticeId(){
		return this.noticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知id
	 */
	public void setNoticeId(java.lang.String noticeId){
		this.noticeId = noticeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购id
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购id
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购昵称
	 */
	@Column(name ="GUIDE_NAME",nullable=true,length=36)
	public java.lang.String getGuideName(){
		return this.guideName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购昵称
	 */
	public void setGuideName(java.lang.String guideName){
		this.guideName = guideName;
	}
	/**
	 *方法: 取得java.lang.Long
	 *@return: java.lang.Long  点击次数
	 */
	@Column(name ="CLICK_NUM",nullable=true,length=10)
	public java.lang.Long getClickNum(){
		return this.clickNum;
	}

	/**
	 *方法: 设置java.lang.Long
	 *@param: java.lang.Long  点击次数
	 */
	public void setClickNum(java.lang.Long clickNum){
		this.clickNum = clickNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  停留时间
	 */
	@Column(name ="STILL_TIME",nullable=true)
	public java.lang.Long getStillTime(){
		return this.stillTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  停留时间
	 */
	public void setStillTime(java.lang.Long stillTime){
		this.stillTime = stillTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  最开始点击时间
	 */
	@Column(name ="CLICK_TIME",nullable=true,length=20)
	public java.util.Date getClickTime(){
		return this.clickTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  最开始点击时间
	 */
	public void setClickTime(java.util.Date clickTime){
		this.clickTime = clickTime;
	}
	@Column(name ="RETAILER_ID")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	
}
