package com.buss.store.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 店铺图片
 * @author onlineGenerator
 * @date 2016-09-22 20:39:18
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_store_pics", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TStorePicsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**状态*/
	@Excel(name="状态")
	private java.lang.String status;
	/**图片地址*/
	@Excel(name="图片地址")
	private java.lang.String picUrl;
	/**店铺ID*/
	@Excel(name="店铺ID")
	private java.lang.String storeId;
	
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
	 *@return: java.lang.String  图片地址
	 */
	@Column(name ="PIC_URL",nullable=true,length=150)
	public java.lang.String getPicUrl(){
		return this.picUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片地址
	 */
	public void setPicUrl(java.lang.String picUrl){
		this.picUrl = picUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  店铺ID
	 */
	@Column(name ="STORE_ID",nullable=true,length=36)
	public java.lang.String getStoreId(){
		return this.storeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  店铺ID
	 */
	public void setStoreId(java.lang.String storeId){
		this.storeId = storeId;
	}
}
