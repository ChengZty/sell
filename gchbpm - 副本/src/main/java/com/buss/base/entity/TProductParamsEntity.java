package com.buss.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
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
@Table(name = "t_product_params", schema = "")
@SuppressWarnings("serial")
public class TProductParamsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**一级类目ID*/
	private java.lang.String categoryId;
	/**类别       1：尺码指导，2：产品信息*/
	@Excel(name="类别")
	private java.lang.String type;
	/**编码 （同一个类目下的是唯一的）*/
	@Excel(name="编码")
	private java.lang.String paramCode;
	/**参数名*/
	@Excel(name="参数名")
	private java.lang.String paramName;
	/**参数值 多个值用逗号隔开*/
	@Excel(name="参数值")
	private java.lang.String paramValues;
	/**输入类别 0：单选，1：多选，2：输入框,3:下拉框，4：图片*/
	@Excel(name="输入类别")
	private java.lang.String inputType;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String inputUnit;
	/**行排序*/
	private java.lang.Integer rowNum;
	/**行内排序*/
	private java.lang.Integer rowIndexNum;
	
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
	@Column(name ="TYPE",nullable=false,length=1)
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}
	@Column(name ="PARAM_CODE",nullable=false,length=10)
	public java.lang.String getParamCode() {
		return paramCode;
	}

	public void setParamCode(java.lang.String paramCode) {
		this.paramCode = paramCode;
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
	@Column(name ="INPUT_TYPE")
	public java.lang.String getInputType() {
		return inputType;
	}

	public void setInputType(java.lang.String inputType) {
		this.inputType = inputType;
	}
	@Column(name ="INPUT_UNIT")
	public java.lang.String getInputUnit() {
		return inputUnit;
	}

	public void setInputUnit(java.lang.String inputUnit) {
		this.inputUnit = inputUnit;
	}
	@Column(name ="ROW_NUM")
	public java.lang.Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(java.lang.Integer rowNum) {
		this.rowNum = rowNum;
	}
	@Column(name ="ROW_INDEX_NUM")
	public java.lang.Integer getRowIndexNum() {
		return rowIndexNum;
	}

	public void setRowIndexNum(java.lang.Integer rowIndexNum) {
		this.rowIndexNum = rowIndexNum;
	}
	
	
}
