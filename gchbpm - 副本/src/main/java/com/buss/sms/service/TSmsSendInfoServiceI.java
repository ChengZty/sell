package com.buss.sms.service;
import com.buss.sms.entity.TSmsSendInfoEntity;
import com.buss.sms.entity.TSmsSubAccountEntity;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TSmsSendInfoServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSmsSendInfoEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSmsSendInfoEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSmsSendInfoEntity t);
	 /**
	  * 查询平台短信剩余条数
	  */
	public String balance();

	/**批量删除短信和短信明细
	 * @param ids
	 */
	public void doBatchDel(String ids);

	public void doAdd(TSmsSendInfoEntity tSmsSendInfo, int smsNumber, TSmsSubAccountEntity account);

	public void doUpdate(TSmsSendInfoEntity tSmsSendInfoEntity, int smsNumber);

	/**
	 * 服务重启后需要检查并重新启动设置定时发送短信的定时任务
	 */
	public void sendMsgByTimer();
}
