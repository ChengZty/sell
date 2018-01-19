package com.buss.user.entity.vo;


/**   
 * @Title: Entity
 * @Description: 答案列表
 * @author onlineGenerator
 * @date 2016-05-31 01:22:09
 * @version V1.0   
 *
 */
public class TPersonAnswersVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**问题名称*/
	private java.lang.String questionName;
	/**答案*/
	private java.lang.String answerValue;
	
	public java.lang.String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(java.lang.String questionName) {
		this.questionName = questionName;
	}
	public java.lang.String getAnswerValue() {
		return answerValue;
	}
	public void setAnswerValue(java.lang.String answerValue) {
		this.answerValue = answerValue;
	}
	
	
}
