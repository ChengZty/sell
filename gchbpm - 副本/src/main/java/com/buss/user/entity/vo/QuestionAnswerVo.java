package com.buss.user.entity.vo;


import com.buss.user.entity.TPersonAnswersEntity;

/**   
 * @Title: Entity
 * @Description: 用户信息表
 * @author onlineGenerator
 * @date 2016-03-10 14:29:30
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class QuestionAnswerVo implements java.io.Serializable {
	/**问题*/
	private java.lang.String questionName;
	/**问题值*/
	private java.lang.String questionValue;
	/**答案类型*/
	private java.lang.String answerType;
	/**答案值*/
	private java.lang.String answerValues;
	/**用户答案*/
	private TPersonAnswersEntity answers ;
	public java.lang.String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(java.lang.String questionName) {
		this.questionName = questionName;
	}
	public java.lang.String getQuestionValue() {
		return questionValue;
	}
	public void setQuestionValue(java.lang.String questionValue) {
		this.questionValue = questionValue;
	}
	public java.lang.String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(java.lang.String answerType) {
		this.answerType = answerType;
	}
	public java.lang.String getAnswerValues() {
		return answerValues;
	}
	public void setAnswerValues(java.lang.String answerValues) {
		this.answerValues = answerValues;
	}
	public TPersonAnswersEntity getAnswers() {
		return answers;
	}
	public void setAnswers(TPersonAnswersEntity answers) {
		this.answers = answers;
	}
	
	
}
