package com.buss.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**   
 * @Title: Entity
 * @Description: 手机归属地
 * @author onlineGenerator
 * @date 2016-09-06 10:49:59
 * @version V1.0   
 *
 */
@Entity
@Table(name = "base_mobile_area", schema = "")
@SuppressWarnings("serial")
public class BaseMobileAreaEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Long id;
	/**手机号 前7位*/
	private java.lang.String mobileNumber;
	/**地区1 省*/
	private java.lang.String mobileArea1;
	/**地区1 市*/
	private java.lang.String mobileArea2;
	/**号码类型*/
	private java.lang.String mobileType;
	/**地区编码*/
	private java.lang.String areaCode;
	/**邮编*/
	private java.lang.String postCode;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
//	@GeneratedValue(generator = "paymentableGenerator")
//	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=12)
	public java.lang.Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.Long id){
		this.id = id;
	}
	@Column(name ="mobile_number",nullable=false,length=7)
	public java.lang.String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(java.lang.String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	@Column(name ="mobile_area1",nullable=false)
	public java.lang.String getMobileArea1() {
		return mobileArea1;
	}

	public void setMobileArea1(java.lang.String mobileArea1) {
		this.mobileArea1 = mobileArea1;
	}
	@Column(name ="mobile_area2",nullable=false)
	public java.lang.String getMobileArea2() {
		return mobileArea2;
	}

	public void setMobileArea2(java.lang.String mobileArea2) {
		this.mobileArea2 = mobileArea2;
	}
	@Column(name ="mobile_type",nullable=false)
	public java.lang.String getMobileType() {
		return mobileType;
	}

	public void setMobileType(java.lang.String mobileType) {
		this.mobileType = mobileType;
	}
	@Column(name ="area_code",nullable=false)
	public java.lang.String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode;
	}
	@Column(name ="post_code",nullable=false)
	public java.lang.String getPostCode() {
		return postCode;
	}

	public void setPostCode(java.lang.String postCode) {
		this.postCode = postCode;
	}
	
	
}
