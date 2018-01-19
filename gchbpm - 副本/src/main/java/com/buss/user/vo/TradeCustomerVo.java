package com.buss.user.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 交易顾客vo
 * @author onlineGenerator
 * @date 2016-03-22 16:44:44
 * @version V1.0   
 *
 */
public class TradeCustomerVo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**姓名*/
	@Excel(name="姓名",width=10)
	private java.lang.String name;
	/**性别*/
	@Excel(name="性别",replace={"男_0","女_1","_null"})
	private java.lang.String sex;
	/**手机号*/
	@Excel(name="手机号码",width=15)
	private java.lang.String phoneNo;
	/**生日*/
	@Excel(name="生日",format="yyyy-MM-dd",width=15)
	private java.util.Date birthday;
	/**顾客来源：逛街、广告、微信公众号、朋友介绍*/
	@Excel(name="顾客来源",width=10)
	private java.lang.String customerSource;
	/**常用联系方式：电话、微信、短信、QQ*/
	@Excel(name="常用联系方式",width=15)
	private java.lang.String commonContact;
	/**外形：高、瘦、胖、矮、标准*/
	@Excel(name="外形",width=8)
	private java.lang.String appearance;
	/**顾客尺码 ：导购标明自己顾客尺码*/
	@Excel(name="顾客尺码",width=10)
	private java.lang.String customerSize;
	/**消费品类，消费规格*/
	@Excel(name="消费标签",width=15)
	private java.lang.String consumption;
	/**登记地区 */
	@Excel(name="登记地区",width=15)
	private java.lang.String registerArea;
	/**VIP登记店铺名称*/
	@Excel(name="登记店铺",width=15)
	private java.lang.String phoneRegShopName;//店铺名称
	/**年龄段 如80后，90后*/
	@Excel(name="年龄段",width=15)
	private java.lang.String birthdayRank;
	/**导购名字*/
	@Excel(name="导购名字",width=20)
	private java.lang.String guideName;
	/**职业*/
//	@Excel(name="职业",width=15)
	private java.lang.String profession;
	/**来源 （1：APP,2:WX,3:QQ,4:后台,5:OTHER）*/ //20161230增加
	private java.lang.String source;
	/**星座  "白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座" *///20170225新增
	@Excel(name="星座",width=15)
	private java.lang.String constellation;
	/**生肖  鼠牛虎兔龙蛇马羊猴鸡狗猪 *///20170225新增
	@Excel(name="生肖",width=15)
	private java.lang.String zodiac;
	/**手机识别地区 *///20170225新增
	@Excel(name="手机归属地",width=20)
	private java.lang.String phoneArea;
	/**备注*/
	@Excel(name="备注",width=20)
	private java.lang.String remark;
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getSex() {
		return sex;
	}
	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}
	public java.lang.String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(java.lang.String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public java.lang.String getRegisterArea() {
		return registerArea;
	}
	public void setRegisterArea(java.lang.String registerArea) {
		this.registerArea = registerArea;
	}
	public java.lang.String getPhoneRegShopName() {
		return phoneRegShopName;
	}
	public void setPhoneRegShopName(java.lang.String phoneRegShopName) {
		this.phoneRegShopName = phoneRegShopName;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public java.lang.String getBirthdayRank() {
		return birthdayRank;
	}
	public void setBirthdayRank(java.lang.String birthdayRank) {
		this.birthdayRank = birthdayRank;
	}
	public java.lang.String getGuideName() {
		return guideName;
	}
	public void setGuideName(java.lang.String guideName) {
		this.guideName = guideName;
	}
	public java.lang.String getProfession() {
		return profession;
	}
	public void setProfession(java.lang.String profession) {
		this.profession = profession;
	}
	public java.lang.String getSource() {
		return source;
	}
	public void setSource(java.lang.String source) {
		this.source = source;
	}
	public java.lang.String getConstellation() {
		return constellation;
	}
	public void setConstellation(java.lang.String constellation) {
		this.constellation = constellation;
	}
	public java.lang.String getZodiac() {
		return zodiac;
	}
	public void setZodiac(java.lang.String zodiac) {
		this.zodiac = zodiac;
	}
	public java.lang.String getPhoneArea() {
		return phoneArea;
	}
	public void setPhoneArea(java.lang.String phoneArea) {
		this.phoneArea = phoneArea;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public java.lang.String getCustomerSource() {
		return customerSource;
	}
	public void setCustomerSource(java.lang.String customerSource) {
		this.customerSource = customerSource;
	}
	public java.lang.String getCommonContact() {
		return commonContact;
	}
	public void setCommonContact(java.lang.String commonContact) {
		this.commonContact = commonContact;
	}
	public java.lang.String getAppearance() {
		return appearance;
	}
	public void setAppearance(java.lang.String appearance) {
		this.appearance = appearance;
	}
	public java.lang.String getCustomerSize() {
		return customerSize;
	}
	public void setCustomerSize(java.lang.String customerSize) {
		this.customerSize = customerSize;
	}
	public java.lang.String getConsumption() {
		return consumption;
	}
	public void setConsumption(java.lang.String consumption) {
		this.consumption = consumption;
	}
	
	
	
}
