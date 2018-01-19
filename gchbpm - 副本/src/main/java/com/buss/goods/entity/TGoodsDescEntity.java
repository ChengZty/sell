package com.buss.goods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 商品描述
 * @author onlineGenerator
 * @date 2016-03-24 15:58:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_goods_desc", schema = "")
@SuppressWarnings("serial")
public class TGoodsDescEntity implements java.io.Serializable {
	public static String TYPE_0 = "0";//商品详情描述
	public static String TYPE_1 = "1";//真特色
	public static String TYPE_2 = "2";//真用途
	public static String TYPE_3 = "3";//真权威
	public static String TYPE_4 = "4";//真服务
	/**主键*/
	private java.lang.String id;
	/**商品ID*/
	@Excel(name="商品ID")
	private java.lang.String goodsId;
	/**商品描述*/
	@Excel(name="商品描述")
	private java.lang.String goodsDesc;
	//20161029新增类型
	/**类别   0：以前的商品，
	 * 上新商品云仓的商品（1：真特色，2：真用途，3：真权威，4：真服务）
	 * 上新商品零售商用不到
	 * */
	private java.lang.String type = "0";//初始化为0，上新的商品都有值
	
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品描述
	 */
	@Column(name ="GOODS_DESC")
	public java.lang.String getGoodsDesc(){
		return this.goodsDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品描述
	 */
	public void setGoodsDesc(java.lang.String goodsDesc){
		this.goodsDesc = goodsDesc;
	}
	@Column(name ="type")
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}
	
}
