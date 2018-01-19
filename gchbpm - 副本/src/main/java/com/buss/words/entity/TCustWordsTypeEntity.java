package com.buss.words.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 顾客话术类别
 * @author onlineGenerator
 * @date 2017-02-10 14:31:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_cust_words_type", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TCustWordsTypeEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
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
	/** 上级 */
	private TCustWordsTypeEntity parent;
	/**上级ID*/
//	@Excel(name="上级ID")
//	private java.lang.Integer pid;
	/**类型名称*/
	@Excel(name="类型名称")
	private java.lang.String name;
	/**层级 1:一级，2：二级*/
	private java.lang.Integer level;
	/**节日类型 一级分类为节假日且二级分类不是周末的时候才需要录入*/
	private java.lang.String festivalType;//1：阳历，2：农历
	/**节日日期 一级分类为节假日且二级分类不是周末的时候才需要录入*/
	private java.lang.String monthDay;//格式：01-01
	/**是否有图片，默认N*/
	private java.lang.String hasPics;//格式：Y/N 20170724新增
	
	private List<TCustWordsTypeEntity> list;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",nullable=false)
	public java.lang.Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.Integer id){
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
	 *@return: java.lang.String  上级ID
	 */
//	@Column(name ="PID",nullable=true,length=36)
//	public java.lang.Integer getPid(){
//		return this.pid;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  上级ID
//	 */
//	public void setPid(java.lang.Integer pid){
//		this.pid = pid;
//	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  层级
	 */
	@Column(name ="LEVEL",nullable=true,length=10)
	public java.lang.Integer getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  层级
	 */
	public void setLevel(java.lang.Integer level){
		this.level = level;
	}
	@Column(name ="FESTIVAL_TYPE",nullable=true)
	public java.lang.String getFestivalType() {
		return festivalType;
	}

	public void setFestivalType(java.lang.String festivalType) {
		this.festivalType = festivalType;
	}
	@Column(name ="MONTH_DAY",nullable=true)
	public java.lang.String getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(java.lang.String monthDay) {
		this.monthDay = monthDay;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
//	@ForeignKey(name="null")//取消hibernate的外键生成
	@JsonIgnore
	public TCustWordsTypeEntity getParent() {
		return parent;
	}

	public void setParent(TCustWordsTypeEntity parent) {
		this.parent = parent;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	@Where(clause="status='A'")
	@JsonIgnore
	public List<TCustWordsTypeEntity> getList() {
		return list;
	}

	public void setList(List<TCustWordsTypeEntity> list) {
		this.list = list;
	}
	@Column(name ="HAS_PICS")
	public java.lang.String getHasPics() {
		return hasPics;
	}

	public void setHasPics(java.lang.String hasPics) {
		this.hasPics = hasPics;
	}
	
	
}
