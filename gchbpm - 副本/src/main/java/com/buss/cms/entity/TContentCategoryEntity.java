package com.buss.cms.entity;

import java.util.ArrayList;
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
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSTerritory;

/**   
 * @Title: Entity
 * @Description: 内容管理
 * @author onlineGenerator
 * @date 2016-09-22 22:13:54
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_content_category", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TContentCategoryEntity implements java.io.Serializable {
	/**id*/
	private Long id;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private java.lang.String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name="创建日期")
	private java.util.Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private java.lang.String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name="更新日期")
	private java.util.Date updateDate;
	/**父类目ID*/
	@Excel(name="父类目ID")
	private TContentCategoryEntity TContentCategoryEntity;
	/**分类名称*/
	@Excel(name="分类名称")
	private java.lang.String name;
	/**排列序号取*/
	@Excel(name="排列序号取")
	private java.lang.Integer sortOrder;
	/**1和0*/
	@Excel(name="1和0")
	private Boolean isParent;
	/**状态*/
	@Excel(name="状态")
	private java.lang.String status;
	/**内容类型*/
	private java.lang.String categoryType;
	/**内容编码*/
	private java.lang.String code;
	
	private List<TContentCategoryEntity> tscms = new ArrayList<TContentCategoryEntity>();
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",nullable=false,length=20)
	public Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(java.lang.Long id){
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
	@Column(name ="CREATE_DATE",nullable=true)
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
	@Column(name ="UPDATE_DATE",nullable=true)
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
	 *@return: java.lang.String  分类名称
	 */
	@Column(name ="NAME",nullable=true,length=50)
	public java.lang.String getName(){
		return this.name;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@ForeignKey(name="null")//取消hibernate的外键生成
	public TContentCategoryEntity getTContentCategoryEntity() {
		return TContentCategoryEntity;
	}

	public void setTContentCategoryEntity(
			TContentCategoryEntity tContentCategoryEntity) {
		TContentCategoryEntity = tContentCategoryEntity;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排列序号取
	 */
	@Column(name ="SORT_ORDER",nullable=true,length=10)
	public java.lang.Integer getSortOrder(){
		return this.sortOrder;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排列序号取
	 */
	public void setSortOrder(java.lang.Integer sortOrder){
		this.sortOrder = sortOrder;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  1和0
	 */
	@Column(name ="IS_PARENT",nullable=true,length=10)
	public Boolean getIsParent(){
		return this.isParent;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  1和0
	 */
	public void setIsParent(Boolean isParent){
		this.isParent = isParent;
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
  @Column(name ="CATEGORY_TYPE",nullable=true)
   public java.lang.String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(java.lang.String categoryType) {
		this.categoryType = categoryType;
	}

@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TContentCategoryEntity")
	@Where(clause="status='A'")
	public List<TContentCategoryEntity> getTscms() {
		return tscms;
	}

	public void setTscms(List<TContentCategoryEntity> tscms) {
		this.tscms = tscms;
	}
	@Column(name ="code",nullable=true)
	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}
	
}
