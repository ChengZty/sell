package com.buss.user.entity;

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
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 标签类目
 * @author onlineGenerator
 * @date 2016-04-18 17:45:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_tag_categories", schema = "")
@SuppressWarnings("serial")
public class TTagCategoriesEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**标签ID*/
	@Excel(name="标签ID")
	private java.lang.String tagId;
	/**类目ID*/
	@Excel(name="类目ID")
	private java.lang.String categryId;
	
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
	 *@return: java.lang.String  标签ID
	 */
	@Column(name ="TAG_ID",nullable=true,length=36)
	public java.lang.String getTagId(){
		return this.tagId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标签ID
	 */
	public void setTagId(java.lang.String tagId){
		this.tagId = tagId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类目ID
	 */
	@Column(name ="CATEGRY_ID",nullable=true,length=36)
	public java.lang.String getCategryId(){
		return this.categryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类目ID
	 */
	public void setCategryId(java.lang.String categryId){
		this.categryId = categryId;
	}
}
