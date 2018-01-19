package com.buss.activity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.buss.words.entity.TCustWordsEntity;

/**   
 * @Title: Entity
 * @Description: 活动话术
 * @author onlineGenerator
 * @date 2016-12-22 21:21:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_fin_activity_words", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TFinActivityWordsEntity implements java.io.Serializable {
	public static final String WORDS_TYPE_ACTIVITY = "1";//活动
	public static final String WORDS_TYPE_GOODS = "2";//商品
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
	/**活动ID/商品ID*/
	@Excel(name="活动ID/商品ID")
	private java.lang.String finActId;
	/**话术*/
	@Excel(name="话术")
	private java.lang.String words;
	/**排序*/
	@Excel(name="排序")
	private java.lang.Integer sortNum;
	//20170214新增
	/**话术ID*/
	private java.lang.String wordsId;
	/**话术类别:1,活动(t_activity_words)；2,商品(t_goods_words) 包括t_visible_words*/
	private java.lang.String wordsType;
	/**是否修改  非数据库字段*/
	private java.lang.String changed;
	
	private TCustWordsEntity goodsWords;
	
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
	@Column(name ="FIN_ACT_ID",nullable=true,length=36)
	public java.lang.String getFinActId() {
		return finActId;
	}

	public void setFinActId(java.lang.String finActId) {
		this.finActId = finActId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  话术
	 */
	@Column(name ="WORDS",nullable=true,length=300)
	public java.lang.String getWords(){
		return this.words;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  话术
	 */
	public void setWords(java.lang.String words){
		this.words = words;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORT_NUM",nullable=true,length=3)
	public java.lang.Integer getSortNum(){
		return this.sortNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSortNum(java.lang.Integer sortNum){
		this.sortNum = sortNum;
	}
	@Column(name ="WORDS_ID")
	public java.lang.String getWordsId() {
		return wordsId;
	}

	public void setWordsId(java.lang.String wordsId) {
		this.wordsId = wordsId;
	}
	@Column(name ="WORDS_TYPE")
	public java.lang.String getWordsType() {
		return wordsType;
	}

	public void setWordsType(java.lang.String wordsType) {
		this.wordsType = wordsType;
	}
	@Transient
	public java.lang.String getChanged() {
		return changed;
	}

	public void setChanged(java.lang.String changed) {
		this.changed = changed;
	}

	@Transient
	public TCustWordsEntity getGoodsWords() {
		return goodsWords;
	}

	public void setGoodsWords(TCustWordsEntity goodsWords) {
		this.goodsWords = goodsWords;
	}
	
	
	
}
