package com.buss.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "t_focus_customer_middle", schema = "")
@Where(clause = " status = 'A'")
@SuppressWarnings("serial")
public class TFocusCustomerMiddleEntity implements java.io.Serializable {
	
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
	@Excel(name="性别",replace={"男_0","女_1"})
	private java.lang.String sex;
	/**手机号*/
	@Excel(name="手机号码",width=15)
	private java.lang.String phoneNo;
	/**登记地区 *///20170225新增
	@Excel(name="登记地区",width=15)
	private java.lang.String registerArea;
	/**登记店铺*/
	@Excel(name="登记店铺",width=15)
	private java.lang.String phoneRegShop;
	/**生日*/
	@Excel(name="生日",format="yyyy-MM-dd",width=15)
	private java.util.Date birthday;
	/**年龄段 如80后，90后*/
	@Excel(name="年龄段",width=15)
	private java.lang.String birthdayRank;
	/**所属零售商*/
	private java.lang.String toRetailerId;
	/**顾客VIP等级*/
	@Excel(name="顾客VIP等级",width=15)
	private java.lang.String vipLevel;
	/**星座  "白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座" *///20170225新增
	@Excel(name="星座",width=15)
	private java.lang.String constellation;
	/**生肖  鼠牛虎兔龙蛇马羊猴鸡狗猪 *///20170225新增
	@Excel(name="生肖",width=15)
	private java.lang.String zodiac;
	/**备注*/
//	@Excel(name="备注",width=20)
	private java.lang.String remark;
	/**推送总量*///20170225新增
	@Excel(name="推送总量",width=15)
	private java.lang.Integer pushCount;
	/**点击次数*///20170225新增
	@Excel(name="点击次数",width=15)
	private java.lang.Integer clickNumber;
	/**购买次数*///20170225新增
	@Excel(name="购买次数",width=15)
	private java.lang.Integer buyCount;
	/**批次号*/
	private java.lang.String batchNo;
	/**类型 0:无效号码 1 ：无反应顾客 2：点击顾客 3：交易顾客*/ //20170225新增
	private java.lang.String type;
	/**顾客ID*/
	private java.lang.String custId;
	//以下为非数据库字段
	/**顾客VIP等级ID*/
	private java.lang.String vipLevelId;
	
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
	@Column(name ="PHONE_REG_SHOP")
	public java.lang.String getPhoneRegShop() {
		return phoneRegShop;
	}

	public void setPhoneRegShop(java.lang.String phoneRegShop) {
		this.phoneRegShop = phoneRegShop;
	}
	@Column(name ="BATCH_NO")
	public java.lang.String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(java.lang.String batchNo) {
		this.batchNo = batchNo;
	}
	
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	@Transient
	public java.lang.String getVipLevelId() {
		return vipLevelId;
	}

	public void setVipLevelId(java.lang.String vipLevelId) {
		this.vipLevelId = vipLevelId;
	}
	@Column(name ="CUST_ID")
	public java.lang.String getCustId() {
		return custId;
	}

	public void setCustId(java.lang.String custId) {
		this.custId = custId;
	}
	
}
