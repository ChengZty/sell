package com.buss.base.entity;

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
 * @Description: 产品参数表
 * @author onlineGenerator
 * @date 2016-04-23 14:51:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_all_goods_params", schema = "")
@SuppressWarnings("serial")
public class TAllGoodsParamsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**类目ID*/
	private java.lang.String categoryId;
	/**参数名*/
	@Excel(name="参数名")
	private java.lang.String paramName;
	/**参数值*/
	@Excel(name="参数值")
	private java.lang.String paramValues;
	/**多选*/
	@Excel(name="多选")
	private java.lang.String multiSelect;
	/**排序*/
	private java.lang.Integer sortNum;
	
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
	 *@return: java.lang.String  类目ID
	 */
	@Column(name ="CATEGORY_ID",nullable=false,length=36)
	public java.lang.String getCategoryId(){
		return this.categoryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类目ID
	 */
	public void setCategoryId(java.lang.String categoryId){
		this.categoryId = categoryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参数名
	 */
	@Column(name ="PARAM_NAME",nullable=false,length=32)
	public java.lang.String getParamName(){
		return this.paramName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参数名
	 */
	public void setParamName(java.lang.String paramName){
		this.paramName = paramName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参数值
	 */
	@Column(name ="PARAM_VALUES",nullable=false,length=500)
	public java.lang.String getParamValues(){
		return this.paramValues;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参数值
	 */
	public void setParamValues(java.lang.String paramValues){
		this.paramValues = paramValues;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  多选
	 */
	@Column(name ="MULTI_SELECT",nullable=false,length=1)
	public java.lang.String getMultiSelect(){
		return this.multiSelect;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  多选
	 */
	public void setMultiSelect(java.lang.String multiSelect){
		this.multiSelect = multiSelect;
	}
	@Column(name ="sort_num",nullable=false,length=3)
	public java.lang.Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(java.lang.Integer sortNum) {
		this.sortNum = sortNum;
	}
	
}
