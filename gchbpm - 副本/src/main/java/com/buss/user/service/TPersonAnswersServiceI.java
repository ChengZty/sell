package com.buss.user.service;
import org.jeecgframework.core.common.service.CommonService;

import com.buss.user.entity.TPersonAnswersEntity;
import com.buss.user.entity.vo.TPersonAnswersVO;

import java.io.Serializable;
import java.util.List;

public interface TPersonAnswersServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPersonAnswersEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPersonAnswersEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPersonAnswersEntity t);

	/**更新或者保存答案列表
	 * @param tPersonAnswers
	 * @param mthd
	 */
	public void saveOrUpdateAnswers(TPersonAnswersEntity tPersonAnswers);
	/**
	 * 获取某个人的问题和答案
	 * @param userId
	 * @return
	 */
	List<TPersonAnswersVO> getVoList(String userId);
}
