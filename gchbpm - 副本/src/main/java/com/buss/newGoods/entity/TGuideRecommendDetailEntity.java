package com.buss.newGoods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 管家推荐明细表
 * @author onlineGenerator
 * @date 2016-10-25 10:12:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_guide_recommend_detail", schema = "")
@Where(clause="status = 'A'")
@SuppressWarnings("serial")
public class TGuideRecommendDetailEntity implements java.io.Serializable {
	public static String TYPE_1 = "1";//正面
	public static String TYPE_2 = "2";//搭配
	public static String TYPE_3 = "3";//细节
	public static String TYPE_4 = "4";//视频
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
	/**推荐主表ID*/
	private java.lang.String recommendId;
	/**商品ID*/
	private java.lang.String goodsId;
	/**类别（1：正面，2：搭配，3：细节，4：视频）*/
	private java.lang.String type;
	/**视频封面图*/
	private java.lang.String coverUrl;
	/**图片或视频地址*/
	private java.lang.String url;
	/**视频播放时长*/
	private java.lang.String playTime="";
	/**排序*/
	private Long idx=0L;
	
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
	 *@return: java.lang.String  推荐主表ID
	 */
	@Column(name ="RECOMMEND_ID",nullable=true,length=36)
	public java.lang.String getRecommendId(){
		return this.recommendId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  推荐主表ID
	 */
	public void setRecommendId(java.lang.String recommendId){
		this.recommendId = recommendId;
	}
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(java.lang.String goodsId) {
		this.goodsId = goodsId;
	}
	@Column(name ="TYPE",nullable=true,length=1)
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}
	@Column(name ="URL",nullable=true,length=150)
	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	@Column(name ="COVER_URL",nullable=true,length=150)
	public java.lang.String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(java.lang.String coverUrl) {
		this.coverUrl = coverUrl;
	}
	@Column(name ="PLAY_TIME")
	public java.lang.String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(java.lang.String playTime) {
		this.playTime = playTime;
	}

	@Column(name ="idx")
	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}
	
	
	
}
