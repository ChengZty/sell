package com.buss.user.entity.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 关注顾客(潜在顾客)
 * @author onlineGenerator
 * @date 2016-03-22 16:44:44
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class TFocusCustomerVo implements java.io.Serializable {
	/**姓名*/
	@Excel(name="姓名",width=10)
	private java.lang.String name;
	/**性别*/
	@Excel(name="性别",replace={"男_0","女_1"})
	private java.lang.String sex;
	/**手机号*/
	@Excel(name="手机号码",width=15)
	private java.lang.String phoneNo;
	/**生日*/
	@Excel(name="生日",format="yyyy-MM-dd",width=15)
	private java.util.Date birthday;
	/**登记地区 *///20170225新增
	@Excel(name="登记地区",width=15)
	private java.lang.String registerArea;
	/**登记店铺*/
	@Excel(name="登记店铺",width=15)
	private java.lang.String phoneRegShop;
	/**年龄段 如80后，90后*/
//	@Excel(name="年龄段",width=15)
	private java.lang.String birthdayRank;
	/**顾客VIP等级ID*/
	private java.lang.String vipLevelId;
	/**星座 *///20170225新增
//	@Excel(name="星座",width=15)
	private java.lang.String constellation;
	/**生肖 *///20170225新增
//	@Excel(name="生肖",width=15)
	private java.lang.String zodiac;
	/**顾客VIP等级*/
	@Excel(name="顾客VIP等级",width=20)
	private java.lang.String vipLevel;
	/**经济实力ID *///20170225新增
	private java.lang.String finAbilityId;
	/**经济实力 *///20170225新增
//	@Excel(name="经济实力",width=15)
	private java.lang.String finAbilityName;
	/**手机识别地区 *///20170225新增
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
	public java.lang.String getPhoneRegShop() {
		return phoneRegShop;
	}
	public void setPhoneRegShop(java.lang.String phoneRegShop) {
		this.phoneRegShop = phoneRegShop;
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
	public java.lang.String getFinAbilityId() {
		return finAbilityId;
	}
	public void setFinAbilityId(java.lang.String finAbilityId) {
		this.finAbilityId = finAbilityId;
	}
	public java.lang.String getFinAbilityName() {
		return finAbilityName;
	}
	public void setFinAbilityName(java.lang.String finAbilityName) {
		this.finAbilityName = finAbilityName;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public java.lang.String getPhoneArea() {
		return phoneArea;
	}
	public void setPhoneArea(java.lang.String phoneArea) {
		this.phoneArea = phoneArea;
	}
}
