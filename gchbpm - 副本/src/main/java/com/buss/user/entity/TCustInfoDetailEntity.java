package com.buss.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 顾客资料完成明细
 * @author onlineGenerator
 * @date 2017-06-29 16:23:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_cust_info_detail", schema = "")
@SuppressWarnings("serial")
public class TCustInfoDetailEntity implements java.io.Serializable {
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
	/**主表ID*/
	@Excel(name="主表ID")
	private java.lang.String tCustInfoId;
	/**完成个数*/
	@Excel(name="完成个数")
	private java.lang.Integer finishedNum;
	/**总个数*/
	@Excel(name="总个数")
	private java.lang.Integer totalNum;
	/**百分比 没有小数*/
	@Excel(name="百分比")
	private java.lang.Integer percent;
	/**类型 J：基础资料，标签的类型为字典表中阶段标签的code*/
	@Excel(name="类型")
	private java.lang.String type;
	
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
	@Column(name ="STATUS",nullable=true,length=50)
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
	 *@return: java.lang.String  主表ID
	 */
	@Column(name ="T_CUST_INFO_ID",nullable=true,length=36)
	public java.lang.String getCustInfoId(){
		return this.tCustInfoId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表ID
	 */
	public void setCustInfoId(java.lang.String tCustInfoId){
		this.tCustInfoId = tCustInfoId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  完成个数
	 */
	@Column(name ="FINISHED_NUM",nullable=true,length=5)
	public java.lang.Integer getFinishedNum(){
		return this.finishedNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  完成个数
	 */
	public void setFinishedNum(java.lang.Integer finishedNum){
		this.finishedNum = finishedNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  总个数
	 */
	@Column(name ="TOTAL_NUM",nullable=false,length=5)
	public java.lang.Integer getTotalNum(){
		return this.totalNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  总个数
	 */
	public void setTotalNum(java.lang.Integer totalNum){
		this.totalNum = totalNum;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  百分比
	 */
	@Column(name ="PERCENT",nullable=true,length=5)
	public java.lang.Integer getPercent(){
		return this.percent;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  百分比
	 */
	public void setPercent(java.lang.Integer percent){
		this.percent = percent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */
	@Column(name ="TYPE",nullable=true,length=2)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}

}
