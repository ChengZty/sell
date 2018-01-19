package com.buss.bill.entity;

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
 * @Description: 云商实收分配定义明细表
 * @author onlineGenerator
 * @date 2016-04-06 16:36:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_commission_cloud_info", schema = "")
@SuppressWarnings("serial")
@Where(clause = " status = 'A' ")
public class TCommissionCloudInfoEntity implements java.io.Serializable {
	/**佣金分配类型*/
	public static final String CTYPE_1 = "1";//未绑定管家
	public static final String CTYPE_2 = "2";//签约管家
	public static final String CTYPE_3 = "3";//非签约管家
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
	/**云商分配表ID*/
	@Excel(name="云商分配表ID")
	private java.lang.String cid;
	/**分配类型*/
	@Excel(name="分配类型")
	private java.lang.String ctype;
	/**分配类型名称*/
	private java.lang.String ctypeName;
	/**系统占比*/
	@Excel(name="系统占比")
	private java.math.BigDecimal systemAccounting;
	/**零售商占比*/
	@Excel(name="零售商占比")
	private java.math.BigDecimal storeAccounting;
	/**导购占比*/
	@Excel(name="导购占比")
	private java.math.BigDecimal guideAccounting;
	/**找帮手消费占比*/
	@Excel(name="找帮手消费占比")
	private java.math.BigDecimal helperAccounting;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String remark;
	
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
	 *@return: java.lang.String  云商分配表ID
	 */
	@Column(name ="CID",nullable=true,length=36)
	public java.lang.String getCid(){
		return this.cid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  云商分配表ID
	 */
	public void setCid(java.lang.String cid){
		this.cid = cid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分配类型
	 */
	@Column(name ="CTYPE",nullable=true,length=1)
	public java.lang.String getCtype(){
		return this.ctype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分配类型
	 */
	public void setCtype(java.lang.String ctype){
		this.ctype = ctype;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  系统占比
	 */
	@Column(name ="SYSTEM_ACCOUNTING",nullable=true,scale=4,length=12)
	public java.math.BigDecimal getSystemAccounting(){
		return this.systemAccounting;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  系统占比
	 */
	public void setSystemAccounting(java.math.BigDecimal systemAccounting){
		this.systemAccounting = systemAccounting;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  零售商占比
	 */
	@Column(name ="STORE_ACCOUNTING",nullable=true,scale=4,length=12)
	public java.math.BigDecimal getStoreAccounting(){
		return this.storeAccounting;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  零售商占比
	 */
	public void setStoreAccounting(java.math.BigDecimal storeAccounting){
		this.storeAccounting = storeAccounting;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  导购占比
	 */
	@Column(name ="GUIDE_ACCOUNTING",nullable=true,scale=4,length=12)
	public java.math.BigDecimal getGuideAccounting(){
		return this.guideAccounting;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  导购占比
	 */
	public void setGuideAccounting(java.math.BigDecimal guideAccounting){
		this.guideAccounting = guideAccounting;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  找帮手消费占比
	 */
	@Column(name ="HELPER_ACCOUNTING",nullable=true,scale=4,length=12)
	public java.math.BigDecimal getHelperAccounting(){
		return this.helperAccounting;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  找帮手消费占比
	 */
	public void setHelperAccounting(java.math.BigDecimal helperAccounting){
		this.helperAccounting = helperAccounting;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=32)
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
	@Transient
	public java.lang.String getCtypeName() {
		return ctypeName;
	}

	public void setCtypeName(java.lang.String ctypeName) {
		this.ctypeName = ctypeName;
	}
}
