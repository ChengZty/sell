package org.jeecgframework.web.system.pojo.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * 系统用户表
 *  @author  
 */
@Entity
@Table(name = "t_s_user")
@Where(clause = " status = 'A' ")
public class TSUser  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	//用户状态
	public static final Short USER_STATUS_ALL = -1;//超级管理员
	public static final Short USER_STATUS_ACTIVE = 1;//激活
	public static final Short USER_STATUS_INACTIVE = 0;//停用
	public static final Short USER_STATUS_FIXED = 2;//锁定
	public static final Short USER_STATUS_WAIT_ACTIVE = 3;//待激活
	public static final Short USER_STATUS_DESTROY = 4;//已注销
	public static final String RETAILER_TYPE_REAL = "1";//零售商 人货
	public static final String RETAILER_TYPE_GOODS = "2";//零售商 货
	public static final String RETAILER_TYPE_POPLE = "3";//零售商 人
	//和字典表一致
	public static final String USER_TYPE_01 = "01";//后台
	public static final String USER_TYPE_02 = "02";//零售商
	public static final String USER_TYPE_05 = "05";//零售商员工
	public static final String USER_TYPE_03 = "03";//导购
	public static final String USER_TYPE_04 = "04";//顾客
	public static final String USER_TYPE_06 = "06";//交易顾客(tperson表是06,user表还是04)
	public static final String USER_TYPE_07 = "07";//尖刀产品顾客
	//零售商版本
	public static final String RETAILER_EDITION_1 = "1";//标准版
	public static final String RETAILER_EDITION_2 = "2";//企业版
	
	private java.lang.String id;
	private String signatureFile;// 签名文件(头像url)
	private String mobilePhone;// 手机
	private String officePhone;// 办公电话
	private String email;// 邮箱
	/**创建时间*/
	private java.util.Date createDate;
	/**创建人ID*/
	private java.lang.String createBy;
	/**创建人名称*/
	private java.lang.String createName;
	/**修改时间*/
	private java.util.Date updateDate;
	/**修改人*/
	private java.lang.String updateBy;
	/**修改人名称*/
	private java.lang.String updateName;
	/**状态*/
	private java.lang.String status;
	/**用户类型*/
	private java.lang.String userType;//01:后台，02：零售商，03：导购，04：顾客，05：零售商员工,06 ： 游客
	/**零售商类型*/
	private java.lang.String retailerType;//1:零售商 人货，2：零售商 货，3：零售商 人
	/**省*/
	private java.lang.String provinceId;
	/**市*/
	private java.lang.String cityId;
	/**区域*/
	private java.lang.String area;
	/**零售商*/
	private java.lang.String retailerId;
	private String userName;// 用户名
	
	/**
	 * （后台还零售商6位数字比如420101）  
	 */
	private String userCode;// 用户编码（后台和零售商6位数字）
	private String realName;// 真实姓名
	private String userKey;// 用户角色
	private String password;//用户密码
	private Short userStatus;// 状态1：正常,0：禁用,-1：超级管理员，2:锁定，3待激活
	private byte[] signature;// 签名文件
    //	private TSDepart TSDepart = new TSDepart();// 部门
//	private TSDepart currentDepart = new TSDepart();// 当前部门
	private String imei;//手机设备号
	private String imUserName;//IM用户名
	private String imUserPwd;//IM密码
	/**第三方授权ID*/
	private String thirdPartyId;
	/**第三方来源*/
	private String thirdPartySource;
	/**第三方授权微信ID*/
	private String thirdPartyWx;
	/**第三方授权微信ID*/
	private String thirdPartyQq;
	/**零售商版本  1：标准版，2：企业版*/  //20170303新增
	private String retailerEdition;
	/**激活时间*/	//20170725新增
	private java.util.Date activeTime;
	/**停用时间*/	//20170725新增
	private java.util.Date inactiveTime;
	/**有效时间（导购）*/	//20170816新增
	private java.util.Date effectiveTime;
	
	
	public TSUser() {
		// TODO Auto-generated constructor stub
	}
	
	public TSUser(String mobilePhone, String userType, String userName,
			String password) {
		super();
		this.mobilePhone = mobilePhone;
		this.userType = userType;
		this.userName = userName;
		this.password = password;
	}

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	@Column(name = "signatureFile", length = 100)
	public String getSignatureFile() {
		return this.signatureFile;
	}

	public void setSignatureFile(String signatureFile) {
		this.signatureFile = signatureFile;
	}

	@Column(name = "mobilePhone", length = 30)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "officePhone", length = 20)
	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="create_date",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人ID
	 */
	@Column(name ="create_by",nullable=true,length=32)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人ID
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="create_name",nullable=true,length=32)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="update_date",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人ID
	 */
	@Column(name ="update_by",nullable=true,length=32)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人ID
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人名称
	 */
	@Column(name ="update_name",nullable=true,length=32)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}

	@Column(name ="province_id",nullable=true,length=36)
	public java.lang.String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(java.lang.String provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name ="city_id",nullable=true,length=36)
	public java.lang.String getCityId() {
		return cityId;
	}

	public void setCityId(java.lang.String cityId) {
		this.cityId = cityId;
	}

	@Column(name ="status",nullable=true,length=1)
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	@Column(name ="user_type",nullable=true,length=2)
	public java.lang.String getUserType() {
		return userType;
	}

	public void setUserType(java.lang.String userType) {
		this.userType = userType;
	}
	@Column(name ="retailer_type",nullable=true,length=1)
	public java.lang.String getRetailerType() {
		return retailerType;
	}

	public void setRetailerType(java.lang.String retailerType) {
		this.retailerType = retailerType;
	}

	@Column(name ="area",nullable=true,length=50)
	public java.lang.String getArea() {
		return area;
	}

	public void setArea(java.lang.String area) {
		this.area = area;
	}

	@Column(name ="retailer_id",nullable=true,length=36)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}
	@Column(name = "signature",length=3000)
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	@Column(name = "userkey", length = 50)
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	@Column(name = "user_status")
	public Short getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Short userStatus) {
		this.userStatus = userStatus;
	}
	
	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
//	@JsonIgnore    //getList查询转换为列表时处理json转换异常
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "departid")
//	public TSDepart getTSDepart() {
//		return this.TSDepart;
//	}
//
//	public void setTSDepart(TSDepart TSDepart) {
//		this.TSDepart = TSDepart;
//	}
	@Column(name = "username", nullable = false, length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "user_Code", nullable = false, length = 6)
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "realname", length = 50)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
//    @Transient
//    public TSDepart getCurrentDepart() {
//        return currentDepart;
//    }
//
//    public void setCurrentDepart(TSDepart currentDepart) {
//        this.currentDepart = currentDepart;
//    }

	@Column(name ="imei",nullable=true)
    public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	@Column(name ="im_User_Name",nullable=true)
	public String getImUserName() {
		return imUserName;
	}

	public void setImUserName(String imUserName) {
		this.imUserName = imUserName;
	}
	@Column(name ="im_User_Pwd",nullable=true)
	public String getImUserPwd() {
		return imUserPwd;
	}

	public void setImUserPwd(String imUserPwd) {
		this.imUserPwd = imUserPwd;
	}
	@Column(name = "third_party_id", length = 50)
	public String getThirdPartyId() {
		return thirdPartyId;
	}
	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}
	@Column(name = "third_party_source", length = 20)
	public String getThirdPartySource() {
		return thirdPartySource;
	}
	public void setThirdPartySource(String thirdPartySource) {
		this.thirdPartySource = thirdPartySource;
	}
	@Column(name = "third_party_wx", length = 50)
	public String getThirdPartyWx() {
		return thirdPartyWx;
	}
	public void setThirdPartyWx(String thirdPartyWx) {
		this.thirdPartyWx = thirdPartyWx;
	}
	@Column(name = "third_party_qq", length = 50)
	public String getThirdPartyQq() {
		return thirdPartyQq;
	}
	public void setThirdPartyQq(String thirdPartyQq) {
		this.thirdPartyQq = thirdPartyQq;
	}
	@Column(name = "retailer_edition")
	public String getRetailerEdition() {
		return retailerEdition;
	}

	public void setRetailerEdition(String retailerEdition) {
		this.retailerEdition = retailerEdition;
	}
	@Column(name = "active_time")
	public java.util.Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(java.util.Date activeTime) {
		this.activeTime = activeTime;
	}
	@Column(name = "inactive_time")
	public java.util.Date getInactiveTime() {
		return inactiveTime;
	}

	public void setInactiveTime(java.util.Date inactiveTime) {
		this.inactiveTime = inactiveTime;
	}
	@Column(name = "effective_time")
	public java.util.Date getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(java.util.Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
}