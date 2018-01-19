package org.jeecgframework.web.system.pojo.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 用户登录记录表
 * @author zhangdaihao
 * @date 2016-03-17 16:54:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_user_login", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TSUserLogin implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**用户名*/
	private java.lang.String userName;
	/**机构*/
	private java.lang.String imei;
	/**ipAddress*/
	private java.lang.String ipAddress;
	/**ipAddress*/
	private Timestamp loginDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户名
	 */
	@Column(name ="USER_NAME",nullable=true,length=20)
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构
	 */
	@Column(name ="IMEI",nullable=true,length=36)
	public java.lang.String getImei(){
		return this.imei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构
	 */
	public void setImei(java.lang.String imei){
		this.imei = imei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ipAddress
	 */
	@Column(name ="IP_ADDRESS",nullable=true,length=50)
	public java.lang.String getIpAddress(){
		return this.ipAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ipAddress
	 */
	public void setIpAddress(java.lang.String ipAddress){
		this.ipAddress = ipAddress;
	}
	@Column(name = "LOGIN_DATE", nullable = false, length = 35)
	public Timestamp getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Timestamp loginDate) {
		this.loginDate = loginDate;
	}
	
}
