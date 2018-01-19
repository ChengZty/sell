package com.buss.user.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 用户信息表
 * @author onlineGenerator
 * @date 2016-03-10 14:29:30
 * @version V1.0   
 *
 */
@Entity
@SuppressWarnings("serial")
public class guideTempVo implements java.io.Serializable {
	/**姓名*/
	@Excel(name="姓名",width=10)
	private java.lang.String name;
	/**性别*/
	@Excel(name="性别",replace={"男_0","女_1"})
	private java.lang.String sex;
	/**手机号码*/
	@Excel(name="手机号码",width=15)
	private java.lang.String phoneNo;
	/**生日*/
	@Excel(name="生日",format = "yyyy-MM-dd",width=15)
	private java.util.Date birthday;
	/**身份证号*/
	@Excel(name="身份证号",width=20)
	private java.lang.String idCard;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */
	public java.lang.String getPhoneNo(){
		return this.phoneNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setPhoneNo(java.lang.String phoneNo){
		this.phoneNo = phoneNo;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生日
	 */
	public java.util.Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生日
	 */
	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}
	

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证号
	 */
	@Column(name ="ID_CARD",nullable=true,length=20)
	public java.lang.String getIdCard(){
		return this.idCard;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号
	 */
	public void setIdCard(java.lang.String idCard){
		this.idCard = idCard;
	}
	
}
