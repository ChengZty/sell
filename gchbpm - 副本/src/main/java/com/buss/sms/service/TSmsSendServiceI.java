package com.buss.sms.service;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.web.multipart.MultipartFile;

import com.buss.sms.entity.TSmsSendEntity;
import com.buss.sms.entity.TSmsSendInfoEntity;
import com.buss.user.entity.vo.TFocusCustomerVo;

public interface TSmsSendServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSmsSendEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSmsSendEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSmsSendEntity t);
 	
 	/**导入手机号
 	 * @throws Exception 
 	 * @throws IOException */
 	public List<TSmsSendEntity> importExcel(String retailerId,String[] arr,MultipartFile file,ImportParams params) throws IOException, Exception;
 	/**
 	 * 条件筛选的手机号
 	 * @param retailerId
 	 * @throws Exception 
 	 */
 	public List<TSmsSendEntity> importSendCustomer(TFocusCustomerVo tfcv,TSmsSendInfoEntity tssie,Map<String, String> paramsMap) throws Exception;

	/**删除明细，主表推送总数-1
	 * @param tSmsSend
	 */
	public void doDel(TSmsSendEntity tSmsSend);
}
