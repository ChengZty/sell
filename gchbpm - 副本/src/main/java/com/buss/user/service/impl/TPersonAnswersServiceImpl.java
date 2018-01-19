package com.buss.user.service.impl;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.user.entity.TPersonAnswersEntity;
import com.buss.user.entity.vo.TPersonAnswersVO;
import com.buss.user.service.TPersonAnswersServiceI;

@Service("tPersonAnswersService")
@Transactional
public class TPersonAnswersServiceImpl extends CommonServiceImpl implements TPersonAnswersServiceI {

	@Override
	public void saveOrUpdateAnswers(TPersonAnswersEntity tPersonAnswers) {
		List<TPersonAnswersEntity> answerList = tPersonAnswers.getAnswerList();
			for(TPersonAnswersEntity answer : answerList){
//				if(!Utility.isEmpty(answer)){
					if(StringUtil.isEmpty(answer.getId())){
						if("null".equals(answer.getAnswerValue())){
							answer.setAnswerValue(null);
						}
						commonDao.save(answer);
					}else{
						if(null==answer.getAnswerValue()){
							commonDao.updateBySqlString("update t_person_answers set user_Id = '"+answer.getUserId()+"',question_Value = '"+answer.getQuestionValue()
									+"',answer_Value = null where id ='"+answer.getId()+"'");
						}else if(!StringUtil.isEmpty(answer.getAnswerValue())){
							commonDao.updateBySqlString("update t_person_answers set user_Id = '"+answer.getUserId()+"',question_Value = '"+answer.getQuestionValue()
									+"',answer_Value = '"+answer.getAnswerValue()+"' where id ='"+answer.getId()+"'");
						}
					}
//				}
			}
		
	}

	@Override
	public boolean doAddSql(TPersonAnswersEntity t) {
		return false;
	}

	@Override
	public boolean doUpdateSql(TPersonAnswersEntity t) {
		return false;
	}

	@Override
	public boolean doDelSql(TPersonAnswersEntity t) {
		return false;
	}
	
	/**
	 * 获取某个人的问题和答案
	 * @param userId
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TPersonAnswersVO> getVoList(String userId){
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT")
		.append(" bq.question_name questionName,")
		.append(" a.answer_value answerValue")
		.append(" FROM")
		.append(" base_questions bq,")
		.append(" t_person_answers a")
		.append(" WHERE a.question_value = bq.question_value")
		.append(" AND a.user_id='").append(userId).append("'");
		
		Query query = this.getSession().createSQLQuery(sb.toString())
		.setResultTransformer(Transformers.aliasToBean(TPersonAnswersVO.class));
		return query.list();
	}
	
	
	
	
	
	
	
	
	
	
	
	
 	
}