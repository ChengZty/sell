package com.buss.goods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * 活动话题
 * @author lenovo
 *
 */
@Entity
@Table(name = "t_goods_act_news", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TGoodsActNewsEntity implements java.io.Serializable {
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
	
	/**商品活动ID**/
	private String goodsActId;
	
	/**话题ID**/
	private String newsId;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid") 
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName() {
		return createName;
	}

	public void setCreateName(java.lang.String createName) {
		this.createName = createName;
	}

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	 
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(java.lang.String updateName) {
		this.updateName = updateName;
	}

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name ="STATUS",nullable=true,length=50)
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	@Column(name ="GOODS_ACT_ID",length=36)
	public String getGoodsActId() {
		return goodsActId;
	}

	public void setGoodsActId(String goodsActId) {
		this.goodsActId = goodsActId;
	}

	@Column(name ="NEWS_ID",length=36)
	public String getNewsId() {
		return newsId;
	}

	
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
	
}
