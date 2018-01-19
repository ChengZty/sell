package com.buss.base.service.impl;
import com.buss.base.service.BaseQuestionsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.base.entity.BaseQuestionsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("baseQuestionsService")
@Transactional
public class BaseQuestionsServiceImpl extends CommonServiceImpl implements BaseQuestionsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((BaseQuestionsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((BaseQuestionsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((BaseQuestionsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(BaseQuestionsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(BaseQuestionsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(BaseQuestionsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,BaseQuestionsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{question_name}",String.valueOf(t.getQuestionName()));
 		sql  = sql.replace("#{question_sort}",String.valueOf(t.getQuestionSort()));
 		sql  = sql.replace("#{answer_type}",String.valueOf(t.getAnswerType()));
 		sql  = sql.replace("#{answer_values}",String.valueOf(t.getAnswerValues()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}