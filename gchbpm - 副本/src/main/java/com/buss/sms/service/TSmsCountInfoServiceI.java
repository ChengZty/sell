package com.buss.sms.service;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TSmsCountInfoServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
}
