package com.buss.cms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 导购端主页分类
 * @author onlineGenerator
 * @date 2016-04-09 12:14:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_guide_main_element", schema = "")
@Where(clause = "status = 'A'")
@SuppressWarnings("serial")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class TGuideMainElementEntity implements java.io.Serializable {
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

	/**组Code*/
	private java.lang.String groupCode;
	/**元素标题*/
	private java.lang.String elementTitle;
	/**元素code*/
	private java.lang.String elementCode;
	/**图片*/
	private java.lang.String pic;
	/**零售商ID*/
	private java.lang.String retailerId;
	/**排序*/
	private java.lang.String OrderNum;
	

	/**零售商自定义图片id*/
	private java.lang.String customId;
	/**零售商自定义图片*/
	private java.lang.String customPic;
	
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
	@Column(name ="CREATE_NAME")
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
	@Column(name ="CREATE_BY")
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
	@Column(name ="CREATE_DATE")
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
	@Column(name ="UPDATE_NAME")
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
	@Column(name ="UPDATE_BY")
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
	@Column(name ="UPDATE_DATE")
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
	@Column(name ="STATUS")
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

	@Column(name ="group_code")
	public java.lang.String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(java.lang.String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name ="element_title")
	public java.lang.String getElementTitle() {
		return elementTitle;
	}

	public void setElementTitle(java.lang.String elementTitle) {
		this.elementTitle = elementTitle;
	}

	@Column(name ="element_code")
	public java.lang.String getElementCode() {
		return elementCode;
	}

	public void setElementCode(java.lang.String elementCode) {
		this.elementCode = elementCode;
	}

	@Column(name ="pic")
	public java.lang.String getPic() {
		return pic;
	}

	public void setPic(java.lang.String pic) {
		this.pic = pic;
	}

	@Column(name ="retailer_id")
	public java.lang.String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(java.lang.String retailerId) {
		this.retailerId = retailerId;
	}

	@Column(name ="order_num")
	public java.lang.String getOrderNum() {
		return OrderNum;
	}

	public void setOrderNum(java.lang.String orderNum) {
		OrderNum = orderNum;
	}

	@Transient
	public java.lang.String getCustomId() {
		return customId;
	}

	public void setCustomId(java.lang.String customId) {
		this.customId = customId;
	}

	@Transient
	public java.lang.String getCustomPic() {
		return customPic;
	}

	public void setCustomPic(java.lang.String customPic) {
		this.customPic = customPic;
	}

	
}
