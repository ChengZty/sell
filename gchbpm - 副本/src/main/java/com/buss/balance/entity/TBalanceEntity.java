package com.buss.balance.entity;

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
 * @Description: 余额
 * @author onlineGenerator
 * @date 2016-09-17 20:20:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_balance", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TBalanceEntity implements java.io.Serializable {
	public static final String BALANCE_STATUS_1 = "1";//有效
	public static final String BALANCE_STATUS_2 = "2";//失效
	public static final String BALANCE_STATUS_3 = "3";//冻结
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
	/**账号ID*/
	private java.lang.String userId;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**余额*/
	@Excel(name="余额")
	private java.math.BigDecimal totalBalance;
	/**可用余额*/
	@Excel(name="可用余额")
	private java.math.BigDecimal availableBalance;
	/**冻结余额*/
	@Excel(name="冻结余额")
	private java.math.BigDecimal frozenBalance;
	/**支付密码*/
	private java.lang.String password;
	/**状态    默认为1   1.有效、2.失效、3.冻结*/
	@Excel(name="状态")
	private java.lang.String balanceStatus;
	/**折扣*/
	private java.math.BigDecimal discount;
	/**充值卡ID*/
	private java.lang.String cardId;
	
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
	 *@return: java.lang.String  账号ID
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账号ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	@Column(name ="RETAILER_ID",nullable=true,length=36)
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  余额
	 */
	@Column(name ="TOTAL_BALANCE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getTotalBalance(){
		return this.totalBalance;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  余额
	 */
	public void setTotalBalance(java.math.BigDecimal totalBalance){
		this.totalBalance = totalBalance;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  可用余额
	 */
	@Column(name ="AVAILABLE_BALANCE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getAvailableBalance(){
		return this.availableBalance;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  可用余额
	 */
	public void setAvailableBalance(java.math.BigDecimal availableBalance){
		this.availableBalance = availableBalance;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  冻结余额
	 */
	@Column(name ="FROZEN_BALANCE",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getFrozenBalance(){
		return this.frozenBalance;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  冻结余额
	 */
	public void setFrozenBalance(java.math.BigDecimal frozenBalance){
		this.frozenBalance = frozenBalance;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付密码
	 */
	@Column(name ="PASSWORD",nullable=true,length=32)
	public java.lang.String getPassword(){
		return this.password;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付密码
	 */
	public void setPassword(java.lang.String password){
		this.password = password;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="BALANCE_STATUS",nullable=true,length=1)
	public java.lang.String getBalanceStatus(){
		return this.balanceStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setBalanceStatus(java.lang.String balanceStatus){
		this.balanceStatus = balanceStatus;
	}
	@Column(name ="DISCOUNT",nullable=true,scale=3,length=5)
	public java.math.BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(java.math.BigDecimal discount) {
		this.discount = discount;
	}
	@Column(name ="CARD_ID",nullable=true,length=36)
	public java.lang.String getCardId() {
		return cardId;
	}

	public void setCardId(java.lang.String cardId) {
		this.cardId = cardId;
	}
	
}
