package com.buss.param.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 系统参数
 * @author onlineGenerator
 * @date 2017-02-15 15:57:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sys_parameter", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TSysParameterEntity implements java.io.Serializable {
	public static final String SYS_PARAMS = "sysParams";//reids中系统参数的key
//	public static final String SYS_PARAMS_TYPE = "sysParamsType";//reids中系统参数类型的key
	public static final String PARA_TYPE_INDEX = "0";//首页
	public static final String PARA_TYPE_SMS = "1";//短信
	public static final String PARA_TYPE_GUIDE = "2";//导购提成  临时使用
	
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
	/**参数编码*/
	private java.lang.String paraCode;
	/**参数名*/
	private java.lang.String paraName;
	/**参数值*/
	private java.lang.String paraValue;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**排序，从0开始顺序排列*/
	private java.lang.Integer sortNum;
	/**类型*/
	private java.lang.String paraType;
	//以下为非数据库字段
	private List<TSysParameterEntity> paramList = new ArrayList<TSysParameterEntity>();
	/**客服电话*/
	private java.lang.String phone;
	/**退货电话*/
	private java.lang.String returnPhone;
	/**退货地址*/
	private java.lang.String returnAddress;
	
	public TSysParameterEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public TSysParameterEntity(Date createDate,String paraCode, String paraName,
			String paraValue, String retailerId) {
		super();
		this.createDate = createDate;
		this.paraCode = paraCode;
		this.paraName = paraName;
		this.paraValue = paraValue;
		this.retailerId = retailerId;
	}

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
	 *@return: java.lang.String  参数编码
	 */
	@Column(name ="PARA_CODE",nullable=true,length=32)
	public java.lang.String getParaCode(){
		return this.paraCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参数编码
	 */
	public void setParaCode(java.lang.String paraCode){
		this.paraCode = paraCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参数值
	 */
	@Column(name ="PARA_VALUE",nullable=true,length=32)
	public java.lang.String getParaValue(){
		return this.paraValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参数值
	 */
	public void setParaValue(java.lang.String paraValue){
		this.paraValue = paraValue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  零售商ID
	 */
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId(){
		return this.retailerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  零售商ID
	 */
	public void setRetailerId(java.lang.String retailerId){
		this.retailerId = retailerId;
	}
	@Column(name ="SORT_NUM")
	public java.lang.Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(java.lang.Integer sortNum) {
		this.sortNum = sortNum;
	}
	@Column(name ="PARA_NAME")
	public java.lang.String getParaName() {
		return paraName;
	}

	public void setParaName(java.lang.String paraName) {
		this.paraName = paraName;
	}
	@Column(name ="STATUS")
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	@Column(name ="PARA_TYPE")
	public java.lang.String getParaType() {
		return paraType;
	}

	public void setParaType(java.lang.String paraType) {
		this.paraType = paraType;
	}

	@Transient
	public List<TSysParameterEntity> getParamList() {
		return paramList;
	}

	public void setParamList(List<TSysParameterEntity> paramList) {
		this.paramList = paramList;
	}
	@Transient
	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	@Transient
	public java.lang.String getReturnPhone() {
		return returnPhone;
	}

	public void setReturnPhone(java.lang.String returnPhone) {
		this.returnPhone = returnPhone;
	}
	@Transient
	public java.lang.String getReturnAddress() {
		return returnAddress;
	}

	public void setReturnAddress(java.lang.String returnAddress) {
		this.returnAddress = returnAddress;
	}
	
	
}
