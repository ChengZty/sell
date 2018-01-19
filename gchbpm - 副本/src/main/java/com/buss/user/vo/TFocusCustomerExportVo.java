package com.buss.user.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 关注顾客(潜在顾客)
 * @author onlineGenerator
 * @date 2016-03-22 16:44:44
 * @version V1.0   
 *
 */
public class TFocusCustomerExportVo implements java.io.Serializable {
	/**姓名*/
	@Excel(name="姓名",width=10)
	private java.lang.String name;
	/**性别*/
	@Excel(name="性别",replace={"男_0","女_1","_null"})
	private java.lang.String sex;
	/**手机号*/
	@Excel(name="手机号码",width=15)
	private java.lang.String phoneNo;
	/**登记地区 *///20170225新增
	@Excel(name="登记地区",width=15)
	private java.lang.String registerArea;
	/**VIP登记店铺*/
	private java.lang.String phoneRegShop;//店铺id
	/**VIP登记店铺名称*/
	@Excel(name="登记店铺",width=15)
	private java.lang.String phoneRegShopName;//店铺名称
	/**生日*/
	@Excel(name="生日",format="yyyy-MM-dd",width=15)
	private java.util.Date birthday;
	/**年龄段 如80后，90后*/
	@Excel(name="年龄段",width=15)
	private java.lang.String birthdayRank;
	/**添加的导购*/
	private java.lang.String addGuideId;
	/**添加的导购名字*/
	@Excel(name="添加的导购名字",width=20)
	private java.lang.String addGuideName;
	/**添加的零售商*/
	private java.lang.String addRetailerId;
	/**所属零售商*/
	private java.lang.String toRetailerId;
	/**分配的导购ID*/
	private java.lang.String toGuideId;
	/**分配的导购名字*/
	@Excel(name="分配的导购名字",width=20)
	private java.lang.String toGuideName;
	/**顾客VIP等级ID*/
	private java.lang.String vipLevelId;
	/**顾客VIP等级*/
//	@Excel(name="顾客VIP等级",width=15)
	private java.lang.String vipLevel;
	@Excel(name="星座",width=15)
	private java.lang.String constellation;
	@Excel(name="生肖",width=15)
	private java.lang.String zodiac;
	/**手机识别地区 *///20170225新增
	@Excel(name="手机归属地",width=20)
	private java.lang.String phoneArea;
	/**推送总量*///20170225新增
	@Excel(name="推送总量",width=15)
	private java.lang.Integer pushCount;
	/**点击次数*///20170225新增
	@Excel(name="点击次数",width=15)
	private java.lang.Integer clickNumber;
	/**购买次数*///20170225新增
	@Excel(name="购买次数",width=15)
	private java.lang.Integer buyCount;
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
	public java.lang.String getPhoneRegShop() {
		return phoneRegShop;
	}
	public void setPhoneRegShop(java.lang.String phoneRegShop) {
		this.phoneRegShop = phoneRegShop;
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
	public java.lang.String getAddGuideId() {
		return addGuideId;
	}
	public void setAddGuideId(java.lang.String addGuideId) {
		this.addGuideId = addGuideId;
	}
	public java.lang.String getAddGuideName() {
		return addGuideName;
	}
	public void setAddGuideName(java.lang.String addGuideName) {
		this.addGuideName = addGuideName;
	}
	public java.lang.String getAddRetailerId() {
		return addRetailerId;
	}
	public void setAddRetailerId(java.lang.String addRetailerId) {
		this.addRetailerId = addRetailerId;
	}
	public java.lang.String getToRetailerId() {
		return toRetailerId;
	}
	public void setToRetailerId(java.lang.String toRetailerId) {
		this.toRetailerId = toRetailerId;
	}
	public java.lang.String getToGuideId() {
		return toGuideId;
	}
	public void setToGuideId(java.lang.String toGuideId) {
		this.toGuideId = toGuideId;
	}
	public java.lang.String getToGuideName() {
		return toGuideName;
	}
	public void setToGuideName(java.lang.String toGuideName) {
		this.toGuideName = toGuideName;
	}
	public java.lang.String getVipLevelId() {
		return vipLevelId;
	}
	public void setVipLevelId(java.lang.String vipLevelId) {
		this.vipLevelId = vipLevelId;
	}
	public java.lang.String getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(java.lang.String vipLevel) {
		this.vipLevel = vipLevel;
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
	public java.lang.Integer getPushCount() {
		return pushCount;
	}
	public void setPushCount(java.lang.Integer pushCount) {
		this.pushCount = pushCount;
	}
	public java.lang.Integer getClickNumber() {
		return clickNumber;
	}
	public void setClickNumber(java.lang.Integer clickNumber) {
		this.clickNumber = clickNumber;
	}
	public java.lang.Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(java.lang.Integer buyCount) {
		this.buyCount = buyCount;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	
}
