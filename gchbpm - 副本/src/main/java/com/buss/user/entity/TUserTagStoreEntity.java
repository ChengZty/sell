package com.buss.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 用户标签库
 * @author onlineGenerator
 * @date 2016-05-31 01:22:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_user_tag_store", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TUserTagStoreEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**用户ID 20170630改为customerId(所有顾客都有，但是userId只是注册的顾客才有)*/
	private java.lang.String customerId;
	/**手机号*/
	private java.lang.String phoneNo;
	/**对象类型 1：导购，2：顾客*/
	private java.lang.String toUserType;
	/**标签编码*/
	private java.lang.String tagCode;
	/**标签值*/
	private java.lang.String tagValues;
	/**标签阶段,来源于字典code: qst_stage*/
	private java.lang.String tagStage;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**导购ID*/
	private java.lang.String guideId;
	/**状态*/
	private java.lang.String status;
	/**标签列表*/
//	private List<TUserTagStoreEntity> tagStoreList = new ArrayList<TUserTagStoreEntity>();
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户ID
	 */
	@Column(name ="customer_Id",nullable=true,length=36)
	public java.lang.String getCustomerId(){
		return this.customerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户ID
	 */
	public void setCustomerId(java.lang.String customerId){
		this.customerId = customerId;
	}
	@Column(name ="TO_USER_TYPE",nullable=false)
	public java.lang.String getToUserType() {
		return toUserType;
	}

	public void setToUserType(java.lang.String toUserType) {
		this.toUserType = toUserType;
	}
	@Column(name ="TAG_CODE",nullable=false)
	public java.lang.String getTagCode() {
		return tagCode;
	}

	public void setTagCode(java.lang.String tagCode) {
		this.tagCode = tagCode;
	}
	@Column(name ="TAG_VALUES",nullable=true)
	public java.lang.String getTagValues() {
		return tagValues;
	}

	public void setTagValues(java.lang.String tagValues) {
		this.tagValues = tagValues;
	}
	@Column(name ="TAG_STAGE",nullable=true)
	public java.lang.String getTagStage() {
		return tagStage;
	}

	public void setTagStage(java.lang.String tagStage) {
		this.tagStage = tagStage;
	}
	@Column(name ="PHONE_NO")
	public java.lang.String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(java.lang.String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Column(name ="RETAILER_ID")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Column(name ="GUIDE_ID")
	public java.lang.String getGuideId() {
		return guideId;
	}

	public void setGuideId(java.lang.String guideId) {
		this.guideId = guideId;
	}
	@Column(name ="status")
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}	
	
//	@Transient
//	public List<TUserTagStoreEntity> getTagStoreList() {
//		return tagStoreList;
//	}
//
//	public void setTagStoreList(List<TUserTagStoreEntity> tagStoreList) {
//		this.tagStoreList = tagStoreList;
//	}
	
	
}
