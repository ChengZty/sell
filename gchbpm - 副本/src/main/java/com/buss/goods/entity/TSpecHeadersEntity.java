package com.buss.goods.entity;

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
 * @Description: 规格表头名称
 * @author onlineGenerator
 * @date 2016-04-08 14:08:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_spec_headers", schema = "")
@SuppressWarnings("serial")
public class TSpecHeadersEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**表头1名称*/
	private java.lang.String headerOne;
	/**表头2名称*/
	private java.lang.String headerTwo;
	/**表头3名称*/
	private java.lang.String headerThree;
	/**商品ID*/
	@Excel(name="商品ID")
	private java.lang.String goodsId;
	
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
	 *@return: java.lang.String  表头1名称
	 */
	@Column(name ="HEADER_ONE",nullable=true,length=32)
	public java.lang.String getHeaderOne(){
		return this.headerOne;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表头1名称
	 */
	public void setHeaderOne(java.lang.String headerOne){
		this.headerOne = headerOne;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表头2名称
	 */
	@Column(name ="HEADER_TWO",nullable=true,length=32)
	public java.lang.String getHeaderTwo(){
		return this.headerTwo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表头2名称
	 */
	public void setHeaderTwo(java.lang.String headerTwo){
		this.headerTwo = headerTwo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表头3名称
	 */
	@Column(name ="HEADER_THREE",nullable=true,length=32)
	public java.lang.String getHeaderThree(){
		return this.headerThree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表头3名称
	 */
	public void setHeaderThree(java.lang.String headerThree){
		this.headerThree = headerThree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品ID
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品ID
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
}
