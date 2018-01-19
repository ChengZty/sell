package com.buss.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 关注顾客(潜在顾客)
 * @author onlineGenerator
 * @date 2016-03-22 16:44:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_focus_customer", schema = "")
@Where(clause = " status = 'A'")
@SuppressWarnings("serial")
public class TFocusCustomerEntity implements java.io.Serializable {
	/**基础资料问题总个数(只算涉及资料完成度的)*/
	public static final int QUESTIONS_NUM = 11;//20171117调整为11个基础资料问题
	public static String IS_USE_APP_YES = "1";//使用app
	public static String IS_USE_APP_NO = "0";//未使用app
	
	public static String SOURCE_1 = "1";//APP录入
	public static String SOURCE_2 = "2";//微信录入
	public static String SOURCE_3 = "3";//QQ录入
	public static String SOURCE_4 = "4";//后台录入
	public static String SOURCE_5 = "5";//其他
	//0:无效号码 1 ：无反应顾客 2：点击顾客 3：交易顾客
	public static String TYPE_0 = "0";//无效号码 
	public static String TYPE_1 = "1";//无反应顾客
	public static String TYPE_2 = "2";//点击顾客
	public static String TYPE_3 = "3";//交易顾客
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**状态*/
	private java.lang.String status;
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
//	@Excel(name="添加的导购名字",width=20)
	private java.lang.String addGuideName;
	/**添加的零售商*/
	private java.lang.String addRetailerId;
	/**所属零售商*/
	private java.lang.String toRetailerId;
	/**分配的导购ID*/
	private java.lang.String toGuideId;
	/**分配的导购名字*/
	private java.lang.String toGuideName;
	/**顾客VIP等级ID*/
	private java.lang.String vipLevelId;
	/**顾客VIP等级*/
//	@Excel(name="顾客VIP等级",width=15)
	private java.lang.String vipLevel;
	/**职业*/
//	@Excel(name="职业",width=15)
	private java.lang.String profession;
	/**职位*/
	private java.lang.String position;
	/**身高*/
	private java.lang.String height;
	/**体重*/
	private java.lang.String weight;
	/**喜欢的颜色*/
	private java.lang.String color;
	/**是否在使用app*/
	private java.lang.String isUseApp;//默认是0  0：未使用，1：使用中
	/**微信openId*/ //20161230增加
	private java.lang.String openid;
	/**微信头像*/ //20161230增加
	private java.lang.String headimgurl;
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
	/**经济实力ID *///20170225新增
	private java.lang.String finAbilityId;
	/**经济实力 *///20170225新增
//	@Excel(name="经济实力",width=15)
	private java.lang.String finAbilityName;
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
	/**类型 0:无效号码 1 ：无反应顾客 2：点击顾客 3：交易顾客*/ //20170225新增
	private java.lang.String type;
	/**省*/
	private java.lang.String province;
	/**市*/
	private java.lang.String city;
	/**运营商*/
	private java.lang.String company;
	/**是否是退订用户  0：否，1：是*/
	private java.lang.String unOrder;//默认为0
	/**实体店消费(有，无)*/
	private java.lang.String isStoreConsum;
	/**微信联系(能，不能)*/
	private java.lang.String isWxContact;
	//20170628新增
	/**顾客来源：逛街、广告、微信公众号、朋友介绍*/
	private java.lang.String customerSource;
	/**常用联系方式：电话、微信、短信、QQ*/
	private java.lang.String commonContact;
	/**外形：高、瘦、胖、矮、标准*/
	private java.lang.String appearance;
	/**顾客尺码 ：导购标明自己顾客尺码*/
	private java.lang.String customerSize;
	/**消费品类，消费规格*/
	private java.lang.String consumption;

	
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
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="SEX",nullable=true,length=1)
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号
	 */
	@Column(name ="PHONE_NO",nullable=true,length=20)
	public java.lang.String getPhoneNo(){
		return this.phoneNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号
	 */
	public void setPhoneNo(java.lang.String phoneNo){
		this.phoneNo = phoneNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生日
	 */
	@Column(name ="BIRTHDAY",nullable=true,length=10)
	public java.util.Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生日
	 */
	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  添加的导购
	 */
	@Column(name ="ADD_GUIDE_ID",nullable=true,length=36)
	public java.lang.String getAddGuideId(){
		return this.addGuideId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  添加的导购
	 */
	public void setAddGuideId(java.lang.String addGuideId){
		this.addGuideId = addGuideId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  添加的零售商
	 */
	@Column(name ="ADD_RETAILER_ID",nullable=true,length=36)
	public java.lang.String getAddRetailerId(){
		return this.addRetailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  添加的零售商
	 */
	public void setAddRetailerId(java.lang.String addRetailerId){
		this.addRetailerId = addRetailerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属零售商
	 */
	@Column(name ="TO_RETAILER_ID",nullable=true,length=36)
	public java.lang.String getToRetailerId(){
		return this.toRetailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属零售商
	 */
	public void setToRetailerId(java.lang.String toRetailerId){
		this.toRetailerId = toRetailerId;
	}
	
	@Column(name ="VIP_LEVEL_ID",nullable=true,length=36)
	public java.lang.String getVipLevelId() {
		return vipLevelId;
	}

	public void setVipLevelId(java.lang.String vipLevelId) {
		this.vipLevelId = vipLevelId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  顾客VIP等级
	 */
	@Column(name ="VIP_LEVEL",nullable=true,length=20)
	public java.lang.String getVipLevel(){
		return this.vipLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  顾客VIP等级
	 */
	public void setVipLevel(java.lang.String vipLevel){
		this.vipLevel = vipLevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职业
	 */
	@Column(name ="PROFESSION",nullable=true,length=32)
	public java.lang.String getProfession(){
		return this.profession;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职业
	 */
	public void setProfession(java.lang.String profession){
		this.profession = profession;
	}
	@Column(name ="birthday_rank",nullable=true,length=20)
	public java.lang.String getBirthdayRank() {
		return birthdayRank;
	}

	public void setBirthdayRank(java.lang.String birthdayRank) {
		this.birthdayRank = birthdayRank;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=150)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	@Column(name ="IS_USE_APP",nullable=true)
	public java.lang.String getIsUseApp() {
		return isUseApp;
	}

	public void setIsUseApp(java.lang.String isUseApp) {
		this.isUseApp = isUseApp;
	}
	@Column(name ="add_Guide_Name")
	public java.lang.String getAddGuideName() {
		return addGuideName;
	}

	public void setAddGuideName(java.lang.String addGuideName) {
		this.addGuideName = addGuideName;
	}
	@Column(name ="to_Guide_Id")
	public java.lang.String getToGuideId() {
		return toGuideId;
	}

	public void setToGuideId(java.lang.String toGuideId) {
		this.toGuideId = toGuideId;
	}
	@Column(name ="to_Guide_Name")
	public java.lang.String getToGuideName() {
		return toGuideName;
	}

	public void setToGuideName(java.lang.String toGuideName) {
		this.toGuideName = toGuideName;
	}
	@Column(name ="openid")
	public java.lang.String getOpenid() {
		return openid;
	}

	public void setOpenid(java.lang.String openid) {
		this.openid = openid;
	}
	@Column(name ="headimgurl")
	public java.lang.String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(java.lang.String headimgurl) {
		this.headimgurl = headimgurl;
	}
	@Column(name ="source")
	public java.lang.String getSource() {
		return source;
	}

	public void setSource(java.lang.String source) {
		this.source = source;
	}
	@Column(name ="constellation")
	public java.lang.String getConstellation() {
		return constellation;
	}

	public void setConstellation(java.lang.String constellation) {
		this.constellation = constellation;
	}
	@Column(name ="zodiac")
	public java.lang.String getZodiac() {
		return zodiac;
	}

	public void setZodiac(java.lang.String zodiac) {
		this.zodiac = zodiac;
	}
	@Column(name ="register_Area")
	public java.lang.String getRegisterArea() {
		return registerArea;
	}

	public void setRegisterArea(java.lang.String registerArea) {
		this.registerArea = registerArea;
	}
	@Column(name ="phone_Area")
	public java.lang.String getPhoneArea() {
		return phoneArea;
	}

	public void setPhoneArea(java.lang.String phoneArea) {
		this.phoneArea = phoneArea;
	}
	@Column(name ="fin_Ability_Id")
	public java.lang.String getFinAbilityId() {
		return finAbilityId;
	}

	public void setFinAbilityId(java.lang.String finAbilityId) {
		this.finAbilityId = finAbilityId;
	}
	@Column(name ="fin_Ability_Name")
	public java.lang.String getFinAbilityName() {
		return finAbilityName;
	}

	public void setFinAbilityName(java.lang.String finAbilityName) {
		this.finAbilityName = finAbilityName;
	}
	@Column(name ="buy_count")
	public java.lang.Integer getBuyCount() {
		return buyCount;
	}
	
	public void setBuyCount(java.lang.Integer buyCount) {
		this.buyCount = buyCount;
	}
	@Column(name ="push_Count")
	public java.lang.Integer getPushCount() {
		return pushCount;
	}
	public void setPushCount(java.lang.Integer pushCount) {
		this.pushCount = pushCount;
	}
	@Column(name ="click_Number")
	public java.lang.Integer getClickNumber() {
		return clickNumber;
	}

	public void setClickNumber(java.lang.Integer clickNumber) {
		this.clickNumber = clickNumber;
	}
	@Column(name ="type")
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}
	@Column(name ="province")
	public java.lang.String getProvince() {
		return province;
	}

	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	@Column(name ="city")
	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}
	@Column(name ="company")
	public java.lang.String getCompany() {
		return company;
	}

	public void setCompany(java.lang.String company) {
		this.company = company;
	}
	@Column(name ="un_order")
	public java.lang.String getUnOrder() {
		return unOrder;
	}

	public void setUnOrder(java.lang.String unOrder) {
		this.unOrder = unOrder;
	}
	@Column(name ="position")
	public java.lang.String getPosition() {
		return position;
	}

	public void setPosition(java.lang.String position) {
		this.position = position;
	}
	@Column(name ="height")
	public java.lang.String getHeight() {
		return height;
	}

	public void setHeight(java.lang.String height) {
		this.height = height;
	}
	@Column(name ="weight")
	public java.lang.String getWeight() {
		return weight;
	}

	public void setWeight(java.lang.String weight) {
		this.weight = weight;
	}
	@Column(name ="color")
	public java.lang.String getColor() {
		return color;
	}

	public void setColor(java.lang.String color) {
		this.color = color;
	}
	@Column(name ="IS_STORE_CONSUM")
	public java.lang.String getIsStoreConsum() {
		return isStoreConsum;
	}

	public void setIsStoreConsum(java.lang.String isStoreConsum) {
		this.isStoreConsum = isStoreConsum;
	}
	@Column(name ="is_wx_contact")
	public java.lang.String getIsWxContact() {
		return isWxContact;
	}

	public void setIsWxContact(java.lang.String isWxContact) {
		this.isWxContact = isWxContact;
	}
	@Column(name ="PHONE_REG_SHOP")
	public java.lang.String getPhoneRegShop() {
		return phoneRegShop;
	}

	public void setPhoneRegShop(java.lang.String phoneRegShop) {
		this.phoneRegShop = phoneRegShop;
	}
	@Column(name ="PHONE_REG_SHOP_NAME")
	public java.lang.String getPhoneRegShopName() {
		return phoneRegShopName;
	}

	public void setPhoneRegShopName(java.lang.String phoneRegShopName) {
		this.phoneRegShopName = phoneRegShopName;
	}
	@Column(name ="CUSTOMER_SOURCE")
	public java.lang.String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(java.lang.String customerSource) {
		this.customerSource = customerSource;
	}
	@Column(name ="COMMON_CONTACT")
	public java.lang.String getCommonContact() {
		return commonContact;
	}

	public void setCommonContact(java.lang.String commonContact) {
		this.commonContact = commonContact;
	}
	@Column(name ="APPEARANCE")
	public java.lang.String getAppearance() {
		return appearance;
	}

	public void setAppearance(java.lang.String appearance) {
		this.appearance = appearance;
	}
	@Column(name ="CUSTOMER_SIZE")
	public java.lang.String getCustomerSize() {
		return customerSize;
	}

	public void setCustomerSize(java.lang.String customerSize) {
		this.customerSize = customerSize;
	}
	@Column(name ="CONSUMPTION")
	public java.lang.String getConsumption() {
		return consumption;
	}

	public void setConsumption(java.lang.String consumption) {
		this.consumption = consumption;
	}
	
}
