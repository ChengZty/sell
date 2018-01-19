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

import com.buss.user.vo.PersonDetailsVO;
import com.buss.user.vo.PersonLongDetailsVO;

/**   
 * @Title: Entity
 * @Description: 用户信息表
 * @author onlineGenerator
 * @date 2016-03-10 14:29:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_person", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TPersonEntity implements java.io.Serializable {
	//是否绑定导购
	public static final String IS_BIND_YES = "1";//是
	public static final String IS_BIND_NO= "0";//未
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
	/**编码*/
	private java.lang.String code;
	/**用户ID*/
	@Excel(name="用户编码",width=10)
	private java.lang.String userId;
	/**头像*/
	private java.lang.String photo;
	/**昵称*/
	@Excel(name="昵称",width=10)
	private java.lang.String name;
	/**姓名*/
	@Excel(name="姓名",width=10)
	private java.lang.String realName;
	/**性别*/
	@Excel(name="性别",replace={"男_0","女_1","_null"})
	private java.lang.String sex;
	/**手机号码*/
	@Excel(name="手机号码",width=15)
	private java.lang.String phoneNo;
	/**生日*/
	@Excel(name="生日",format = "yyyy-MM-dd",width=15)
	private java.util.Date birthday;
	/**所在省*/
	private java.lang.String provinceId;
	/**所在市*/
	private java.lang.String cityId;
	/**所在区域*/
	@Excel(name="所在区域",width=25)
	private java.lang.String area;
	/**身份证号*/
	@Excel(name="身份证号",width=20)
	private java.lang.String idCard;
	/**件数*/
//	@Excel(name="件数")
	private java.math.BigDecimal quantity;
	/**金额*/
//	@Excel(name="金额")
	private java.math.BigDecimal money;
	/**所属导购*/
	private java.lang.String toGuideId;
	/**所属导购*/
	@Excel(name="所属导购")
	private java.lang.String toGuideName;
	/**所属零售商*/
//	@Excel(name="所属零售商")
	private java.lang.String toRetailerId;
	/**VIP等级*/
//	@Excel(name="VIP等级")
	private java.lang.String vipLevel;
	/**职业*/
//	@Excel(name="职业")
	private java.lang.String profession;
	/**行业*/
//	@Excel(name="行业")
	private java.lang.String industry;
	/**身高*/
//	@Excel(name="身高")
	private java.lang.String height;
	/**体重*/
//	@Excel(name="体重")
	private java.lang.String weight;
	/**喜欢的颜色*/
//	@Excel(name="喜欢的颜色")
	private java.lang.String favouriteColor;
	/**帮手大类*/
//	@Excel(name="帮手大类")
	private java.lang.String helperType;
	/**用户类别*/
//	@Excel(name="用户类别")
	private java.lang.String userType;
	/**备注*/
//	@Excel(name="备注")
	private java.lang.String remark;
	/**是否绑定导购*/
//	@Excel(name="是否绑定导购")
	private java.lang.String isBind;
	/**是否有标签 */
	private java.lang.String hasTags;//默认是0：否
	/**签名 */
	private java.lang.String signature;
	/**星座 */
	private java.lang.String constellation;
	/**生肖 */
	private java.lang.String zodiac;
	/**能否被顾客绑定 */
	private java.lang.String canBind;//默认是1 （1：可以  ，0：不可以）类型为导购的时候才有值，为顾客的时候没有
	/**导购问题详情 */
	private PersonDetailsVO guideDetails ;
	/**顾客问题详情 */
	private PersonLongDetailsVO customerDetails ;
	/**粉丝数量 */
	private Integer fans;//专家，达人的粉丝数是关注的人的数量；管家是绑定的顾客数
	/**顾客来源 （1：APP,2:WX,3:QQ,4:后台,5:OTHER）*/ //20170104增加跟潜在顾客一致
	private java.lang.String source;
	/**登记店铺*/
	@Excel(name="登记店铺",width=15)
	private java.lang.String storeId;
	/**学历*/
	private java.lang.String education;
	/**民族*/
	private java.lang.String nation;
	/**从业时间*/
	private java.util.Date jobStartTime;
	/**常住地（所在地）*/
	private java.lang.String permanentPlace;
	/**籍贯*/
	private java.lang.String birthplace;
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
	 *@return: java.lang.String  编码
	 */
	@Column(name ="CODE",nullable=true,length=32)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编码
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	
	@Column(name ="user_Id",nullable=true,length=36)
	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	@Column(name ="photo",nullable=true)
	public java.lang.String getPhoto() {
		return photo;
	}

	public void setPhoto(java.lang.String photo) {
		this.photo = photo;
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
	@Column(name ="real_Name",nullable=true,length=32)
	public java.lang.String getRealName() {
		return realName;
	}

	public void setRealName(java.lang.String realName) {
		this.realName = realName;
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
	 *@return: java.lang.String  手机号码
	 */
	@Column(name ="PHONE_NO",nullable=true,length=20)
	public java.lang.String getPhoneNo(){
		return this.phoneNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setPhoneNo(java.lang.String phoneNo){
		this.phoneNo = phoneNo;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生日
	 */
	@Column(name ="BIRTHDAY",nullable=true,length=10)
	public java.util.Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生日
	 */
	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所在省
	 */
	@Column(name ="PROVINCE_ID",nullable=true,length=36)
	public java.lang.String getProvinceId(){
		return this.provinceId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所在省
	 */
	public void setProvinceId(java.lang.String provinceId){
		this.provinceId = provinceId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所在市
	 */
	@Column(name ="CITY_ID",nullable=true,length=36)
	public java.lang.String getCityId(){
		return this.cityId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所在市
	 */
	public void setCityId(java.lang.String cityId){
		this.cityId = cityId;
	}
	
	public java.lang.String getArea() {
		return area;
	}

	public void setArea(java.lang.String area) {
		this.area = area;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证号
	 */
	@Column(name ="ID_CARD",nullable=true,length=20)
	public java.lang.String getIdCard(){
		return this.idCard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号
	 */
	public void setIdCard(java.lang.String idCard){
		this.idCard = idCard;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  件数
	 */
	@Column(name ="QUANTITY",nullable=true,length=12)
	public java.math.BigDecimal getQuantity(){
		return this.quantity;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  件数
	 */
	public void setQuantity(java.math.BigDecimal quantity){
		this.quantity = quantity;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额
	 */
	@Column(name ="MONEY",nullable=true,length=12)
	public java.math.BigDecimal getMoney(){
		return this.money;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额
	 */
	public void setMoney(java.math.BigDecimal money){
		this.money = money;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属导购
	 */
	@Column(name ="TO_GUIDE_ID",nullable=true,length=36)
	public java.lang.String getToGuideId(){
		return this.toGuideId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属导购
	 */
	public void setToGuideId(java.lang.String toGuideId){
		this.toGuideId = toGuideId;
	}
	@Column(name ="TO_GUIDE_NAME",nullable=true,length=32)
	public java.lang.String getToGuideName() {
		return toGuideName;
	}

	public void setToGuideName(java.lang.String toGuideName) {
		this.toGuideName = toGuideName;
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
	 *@return: java.lang.String  VIP等级
	 */
	@Column(name ="VIP_LEVEL",nullable=true,length=20)
	public java.lang.String getVipLevel(){
		return this.vipLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  VIP等级
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行业
	 */
	@Column(name ="INDUSTRY",nullable=true,length=32)
	public java.lang.String getIndustry(){
		return this.industry;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行业
	 */
	public void setIndustry(java.lang.String industry){
		this.industry = industry;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身高
	 */
	@Column(name ="HEIGHT",nullable=true,length=3)
	public java.lang.String getHeight(){
		return this.height;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身高
	 */
	public void setHeight(java.lang.String height){
		this.height = height;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  体重
	 */
	@Column(name ="WEIGHT",nullable=true,length=3)
	public java.lang.String getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  体重
	 */
	public void setWeight(java.lang.String weight){
		this.weight = weight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  喜欢的颜色
	 */
	@Column(name ="FAVOURITE_COLOR",nullable=true,length=50)
	public java.lang.String getFavouriteColor(){
		return this.favouriteColor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  喜欢的颜色
	 */
	public void setFavouriteColor(java.lang.String favouriteColor){
		this.favouriteColor = favouriteColor;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  帮手大类
	 */
	@Column(name ="HELPER_TYPE",nullable=true,length=3)
	public java.lang.String getHelperType(){
		return this.helperType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  帮手大类
	 */
	public void setHelperType(java.lang.String helperType){
		this.helperType = helperType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户类别
	 */
	@Column(name ="USER_TYPE",nullable=true,length=3)
	public java.lang.String getUserType(){
		return this.userType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户类别
	 */
	public void setUserType(java.lang.String userType){
		this.userType = userType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=100)
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

	@Column(name ="is_bind",nullable=true,length=1)
	public java.lang.String getIsBind() {
		return isBind;
	}

	public void setIsBind(java.lang.String isBind) {
		this.isBind = isBind;
	}
	@Column(name ="has_tags",nullable=false,length=1)
	public java.lang.String getHasTags() {
		return hasTags;
	}

	public void setHasTags(java.lang.String hasTags) {
		this.hasTags = hasTags;
	}
	@Column(name ="signature",nullable=true,length=150)
	public java.lang.String getSignature() {
		return signature;
	}

	public void setSignature(java.lang.String signature) {
		this.signature = signature;
	}
	@Column(name ="constellation",nullable=true,length=10)
	public java.lang.String getConstellation() {
		return constellation;
	}

	public void setConstellation(java.lang.String constellation) {
		this.constellation = constellation;
	}
	@Column(name ="zodiac",nullable=true,length=2)
	public java.lang.String getZodiac() {
		return zodiac;
	}

	public void setZodiac(java.lang.String zodiac) {
		this.zodiac = zodiac;
	}
	@Column(name ="can_Bind",nullable=true,length=1)
	public java.lang.String getCanBind() {
		return canBind;
	}

	public void setCanBind(java.lang.String canBind) {
		this.canBind = canBind;
	}
	@Column(name ="fans")
	public Integer getFans() {
		return fans;
	}

	public void setFans(Integer fans) {
		this.fans = fans;
	}

	@Transient
	public PersonDetailsVO getGuideDetails() {
		return guideDetails;
	}

	public void setGuideDetails(PersonDetailsVO guideDetails) {
		this.guideDetails = guideDetails;
	}
	@Transient
	public PersonLongDetailsVO getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(PersonLongDetailsVO customerDetails) {
		this.customerDetails = customerDetails;
	}
	@Column(name ="source")
	public java.lang.String getSource() {
		return source;
	}

	public void setSource(java.lang.String source) {
		this.source = source;
	}
	@Column(name ="STORE_ID")
	public java.lang.String getStoreId() {
		return storeId;
	}

	public void setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
	}
	@Column(name ="EDUCATION",nullable=true)
	public java.lang.String getEducation() {
		return education;
	}

	public void setEducation(java.lang.String education) {
		this.education = education;
	}
	@Column(name ="NATION",nullable=true)
	public java.lang.String getNation() {
		return nation;
	}

	public void setNation(java.lang.String nation) {
		this.nation = nation;
	}
	@Column(name ="JOB_START_TIME",nullable=true)
	public java.util.Date getJobStartTime() {
		return jobStartTime;
	}

	public void setJobStartTime(java.util.Date jobStartTime) {
		this.jobStartTime = jobStartTime;
	}
	@Column(name ="PERMANENT_PLACE",nullable=true)
	public java.lang.String getPermanentPlace() {
		return permanentPlace;
	}

	public void setPermanentPlace(java.lang.String permanentPlace) {
		this.permanentPlace = permanentPlace;
	}
	@Column(name ="birthplace",nullable=true,length=50)
	public java.lang.String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(java.lang.String birthplace) {
		this.birthplace = birthplace;
	}
}
