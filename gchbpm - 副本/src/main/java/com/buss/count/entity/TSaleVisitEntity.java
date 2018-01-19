package com.buss.count.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 售后回访
 * @author onlineGenerator
 * @date 2017-06-19 16:05:24
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sale_visit", schema = "")
@SuppressWarnings("serial")
public class TSaleVisitEntity implements java.io.Serializable {
	public static final String  VISIT_STATUS_1="1";//当天致谢
	public static final String  VISIT_STATUS_2="2";//收货关怀
	public static final String  VISIT_STATUS_3="3";//注意事项
	public static final String  VISIT_STATUS_4="4";//使用关怀
	public static final String  VISIT_STATUS_5="5";//再次推荐
	public static final String  VISIT_STATUS_6="6";//完成
	public static final String  ORDER_TYPE_ONLINE="1";//线上订单
	public static final String  ORDER_TYPE_OFFLINE="2";//线下订单
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
	/**导购ID*/
	private java.lang.String guideId;
	/**导购姓名*/
	@Excel(name="导购姓名")
	private java.lang.String guideName;
	/**顾客Id*/
	private java.lang.String userId;
	/**顾客姓名*/
	@Excel(name="顾客姓名")
	private java.lang.String userName;
	/**支付时间*/
	@Excel(name="支付时间",width=20,format="yyyy-MM-dd hh:mm:ss")
	private java.util.Date payTime;
	/**订单ID*/
	private java.lang.String orderId;
	/**订单号*/
	@Excel(name="订单号",width=18)
	private java.lang.String orderNo;
	/**回访状态*/
	@Excel(name="回访状态",replace={"当天致谢_1","收货关怀_2","注意事项_3","使用关怀_4","再次推荐_5","完成_6"})
	private java.lang.String visitStatus;
	/**当天致谢*/
	@Excel(name="当天致谢",width=20,format="yyyy-MM-dd hh:mm:ss")
	private java.util.Date visitOne;
	/**收货关怀*/
	@Excel(name="收货关怀",width=20,format="yyyy-MM-dd hh:mm:ss")
	private java.util.Date visitTwo;
	/**注意事项*/
	@Excel(name="注意事项",width=20,format="yyyy-MM-dd hh:mm:ss")
	private java.util.Date visitThr;
	/**使用关怀*/
	@Excel(name="使用关怀",width=20,format="yyyy-MM-dd hh:mm:ss")
	private java.util.Date visitFou;
	/**再次推荐*/
	@Excel(name="再次推荐",width=20,format="yyyy-MM-dd hh:mm:ss")
	private java.util.Date visitFiv;
	/**维护进度*/
	@Excel(name="维护进度",suffix="%")
	private java.lang.Double visitProcess;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**订单类型 1：线上订单，2：店铺订单 默认为1*/
	@Excel(name="订单类型",replace={"g+订单_1","店铺订单_2"})
	private java.lang.String orderType;
	
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
	 *@return: java.lang.String  导购ID
	 */
	@Column(name ="GUIDE_ID",nullable=true,length=36)
	public java.lang.String getGuideId(){
		return this.guideId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购ID
	 */
	public void setGuideId(java.lang.String guideId){
		this.guideId = guideId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购姓名
	 */
	@Column(name ="GUIDE_NAME",nullable=true,length=32)
	public java.lang.String getGuideName(){
		return this.guideName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购姓名
	 */
	public void setGuideName(java.lang.String guideName){
		this.guideName = guideName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  顾客Id
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  顾客Id
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  顾客姓名
	 */
	@Column(name ="USER_NAME",nullable=true,length=32)
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  顾客姓名
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  支付时间
	 */
	@Column(name ="PAY_TIME",nullable=true,length=20)
	public java.util.Date getPayTime(){
		return this.payTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  支付时间
	 */
	public void setPayTime(java.util.Date payTime){
		this.payTime = payTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单ID
	 */
	@Column(name ="ORDER_ID",nullable=true,length=36)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单ID
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单号
	 */
	@Column(name ="ORDER_NO",nullable=true,length=20)
	public java.lang.String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回访状态
	 */
	@Column(name ="VISIT_STATUS",nullable=false,length=1)
	public java.lang.String getVisitStatus(){
		return this.visitStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回访状态
	 */
	public void setVisitStatus(java.lang.String visitStatus){
		this.visitStatus = visitStatus;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  当天致谢
	 */
	@Column(name ="VISIT_ONE",nullable=true,length=20)
	public java.util.Date getVisitOne(){
		return this.visitOne;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  当天致谢
	 */
	public void setVisitOne(java.util.Date visitOne){
		this.visitOne = visitOne;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  收货关怀
	 */
	@Column(name ="VISIT_TWO",nullable=true,length=20)
	public java.util.Date getVisitTwo(){
		return this.visitTwo;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  收货关怀
	 */
	public void setVisitTwo(java.util.Date visitTwo){
		this.visitTwo = visitTwo;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  注意事项
	 */
	@Column(name ="VISIT_THR",nullable=true,length=20)
	public java.util.Date getVisitThr(){
		return this.visitThr;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  注意事项
	 */
	public void setVisitThr(java.util.Date visitThr){
		this.visitThr = visitThr;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  使用关怀
	 */
	@Column(name ="VISIT_FOU",nullable=true,length=20)
	public java.util.Date getVisitFou(){
		return this.visitFou;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  使用关怀
	 */
	public void setVisitFou(java.util.Date visitFou){
		this.visitFou = visitFou;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  再次推荐
	 */
	@Column(name ="VISIT_FIV",nullable=true,length=20)
	public java.util.Date getVisitFiv(){
		return this.visitFiv;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  再次推荐
	 */
	public void setVisitFiv(java.util.Date visitFiv){
		this.visitFiv = visitFiv;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  维护进度
	 */
	@Column(name ="VISIT_PROCESS",nullable=true,scale=2,length=6)
	public java.lang.Double getVisitProcess(){
		return this.visitProcess;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  维护进度
	 */
	public void setVisitProcess(java.lang.Double visitProcess){
		this.visitProcess = visitProcess;
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
	@Column(name ="ORDER_TYPE",nullable=true,length=36)
	public java.lang.String getOrderType() {
		return orderType;
	}

	public void setOrderType(java.lang.String orderType) {
		this.orderType = orderType;
	}
}
