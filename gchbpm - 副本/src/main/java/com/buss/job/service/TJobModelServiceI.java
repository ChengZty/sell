package com.buss.job.service;
import java.util.Set;

import com.buss.job.entity.TJobModelEntity;

import org.jeecgframework.core.common.service.CommonService;

public interface TJobModelServiceI extends CommonService{
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public String doAddJobModel(TJobModelEntity jobModel,Set<String> tStoreJobModel,String storeIdSql);
 	
 	
 	public String doUpdateJobModel(TJobModelEntity jobModel,Set<String> tStoreJobModel,String storeIdSql);
 	
 	
 	public String doDelJobModel(String jobModelId);
}
