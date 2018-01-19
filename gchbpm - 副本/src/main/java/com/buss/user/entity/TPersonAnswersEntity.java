package com.buss.user.entity;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 答案列表
 * @author onlineGenerator
 * @date 2016-05-31 01:22:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_person_answers", schema = "")
@SuppressWarnings("serial")
public class TPersonAnswersEntity implements java.io.Serializable {
	public static final String MTHD_S = "s"; 
	public static final String MTHD_U = "u"; 
	/**主键*/
	private java.lang.String id;
	/**用户ID*/
	private java.lang.String userId;
	/**问题值*/
	private java.lang.String questionValue;
	/**答案*/
	private java.lang.String answerValue;
	/**资料类型,来源于字典code: qest_info_type*/
	private java.lang.String infoType;
	/**答案列表*/
	private List<TPersonAnswersEntity> answerList = new ArrayList<TPersonAnswersEntity>();
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
	 *@return: java.lang.String  用户ID
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  问题值
	 */
	@Column(name ="QUESTION_VALUE",nullable=true,length=5)
	public java.lang.String getQuestionValue(){
		return this.questionValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  问题值
	 */
	public void setQuestionValue(java.lang.String questionValue){
		this.questionValue = questionValue;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  答案
	 */
	@Column(name ="ANSWER_VALUE",nullable=true,length=200)
	public java.lang.String getAnswerValue(){
		return this.answerValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  答案
	 */
	public void setAnswerValue(java.lang.String answerValue){
		this.answerValue = answerValue;
	}
	@Column(name ="INFO_TYPE",nullable=true,length=3)
	public java.lang.String getInfoType() {
		return infoType;
	}

	public void setInfoType(java.lang.String infoType) {
		this.infoType = infoType;
	}
	@Transient
	public List<TPersonAnswersEntity> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<TPersonAnswersEntity> answerList) {
		this.answerList = answerList;
	}
	
}
