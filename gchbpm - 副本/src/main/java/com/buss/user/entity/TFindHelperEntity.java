package com.buss.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 管家找帮手
 * @author zhangdaihao
 * @date 2016-05-02 16:40:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_find_helper", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TFindHelperEntity implements java.io.Serializable {
	//帮手大类
	public static final String HELPER_TYPE_SPE = "1";//专家
	public static final String HELPER_TYPE_LIKE = "2";//达人
	public static final String HELPER_TYPE_EQ = "3";//同门
	
	/**id*/
	private java.lang.String id;
	/**导购userId*/
	private java.lang.String toGuideId;
	/**顾客userId*/
	private java.lang.String userId;
	/**帮手userId*/
	private java.lang.String helperId;
	/**帮手姓名*/
	private java.lang.String helperName;
	/**帮手类型*/
	private java.lang.String helperType;//
	/**二级分类*/
	private java.lang.String subCategoryId;
	/**添加时间*/
	private java.util.Date addTime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  导购userId
	 */
	@Column(name ="TO_GUIDE_ID",nullable=true,length=36)
	public java.lang.String getToGuideId(){
		return this.toGuideId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  导购userId
	 */
	public void setToGuideId(java.lang.String toGuideId){
		this.toGuideId = toGuideId;
	}
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  帮手userId
	 */
	@Column(name ="HELPER_ID",nullable=true,length=36)
	public java.lang.String getHelperId(){
		return this.helperId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  帮手userId
	 */
	public void setHelperId(java.lang.String helperId){
		this.helperId = helperId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  帮手姓名
	 */
	@Column(name ="HELPER_NAME",nullable=true,length=32)
	public java.lang.String getHelperName(){
		return this.helperName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  帮手姓名
	 */
	public void setHelperName(java.lang.String helperName){
		this.helperName = helperName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  人员类型
	 */
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  二级分类
	 */
	@Column(name ="SUB_CATEGORY_ID",nullable=true,length=36)
	public java.lang.String getSubCategoryId(){
		return this.subCategoryId;
	}
	@Column(name ="HELPER_TYPE",nullable=true,length=3)
	public java.lang.String getHelperType() {
		return helperType;
	}

	public void setHelperType(java.lang.String helperType) {
		this.helperType = helperType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  二级分类
	 */
	public void setSubCategoryId(java.lang.String subCategoryId){
		this.subCategoryId = subCategoryId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  添加时间
	 */
	@Column(name ="ADD_TIME",nullable=true)
	public java.util.Date getAddTime(){
		return this.addTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  添加时间
	 */
	public void setAddTime(java.util.Date addTime){
		this.addTime = addTime;
	}
}
