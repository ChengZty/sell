package com.buss.sms.service;
import org.jeecgframework.core.common.service.CommonService;

import com.buss.sms.entity.TSmsSubAccountEntity;

public interface TSmsSubAccountServiceI extends CommonService{

	void saveAccount(TSmsSubAccountEntity tSmsSubAccount);

	void updateAccount(TSmsSubAccountEntity tSmsSubAccount) throws Exception;
	
}
