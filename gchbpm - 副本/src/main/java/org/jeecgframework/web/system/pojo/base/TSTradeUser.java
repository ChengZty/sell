package org.jeecgframework.web.system.pojo.base;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * 用户行业关联表
 *  @author  
 */
@Entity
@Table(name = "t_s_trade_user", schema = "")
@Where(clause = " status = 'A' ")
@SuppressWarnings("serial")
public class TSTradeUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**ID*/
	private java.lang.String id;
	/**创建人ID*/
	private java.lang.String userId;
	/**创建人名称*/
	private java.lang.String userName;
	/**修改人*/
	private java.lang.String tradeId;
	/**修改人名称*/
	private java.lang.String tradeName;
	/**状态*/
	private java.lang.String status;


	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=36)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	@Column(name ="user_id",nullable=false,length=36)
	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	@Column(name ="user_name",nullable=false,length=36)
	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	@Column(name ="trade_id",nullable=false,length=36)
	public java.lang.String getTradeId() {
		return tradeId;
	}

	public void setTradeId(java.lang.String tradeId) {
		this.tradeId = tradeId;
	}

	@Column(name ="trade_name",nullable=false,length=36)
	public java.lang.String getTradeName() {
		return tradeName;
	}

	public void setTradeName(java.lang.String tradeName) {
		this.tradeName = tradeName;
	}

	@Column(name ="status",nullable=false,length=1)
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}
}