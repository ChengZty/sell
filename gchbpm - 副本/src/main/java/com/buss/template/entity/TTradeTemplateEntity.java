package com.buss.template.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**   
 * @Title: Entity
 * @Description: 模板与行业的关联表
 * @author onlineGenerator
 * @date 2016-04-09 12:14:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_trade_template", schema = "")
@Where(clause = "status = 'A'")
@SuppressWarnings("serial")
public class TTradeTemplateEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;

	/**模板id*/
	private java.lang.String templateId;
	/**行业id*/
	private java.lang.String tradeId;
	/**行业名称*/
	private java.lang.String tradeName;
	/**状态*/
	private java.lang.String status;

	
	
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

	@Column(name ="TEMPLATE_ID",nullable=false,length=36)
	public java.lang.String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(java.lang.String templateId) {
		this.templateId = templateId;
	}

	@Column(name ="TRADE_ID",nullable=false,length=36)
	public java.lang.String getTradeId() {
		return tradeId;
	}

	public void setTradeId(java.lang.String tradeId) {
		this.tradeId = tradeId;
	}

	@Column(name ="TRADE_NAME",nullable=false,length=36)
	public java.lang.String getTradeName() {
		return tradeName;
	}

	public void setTradeName(java.lang.String tradeName) {
		this.tradeName = tradeName;
	}

	@Column(name ="STATUS",nullable=false,length=1)
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	
	
	
}
