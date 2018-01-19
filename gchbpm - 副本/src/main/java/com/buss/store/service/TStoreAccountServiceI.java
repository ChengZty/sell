package com.buss.store.service;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.buss.store.entity.TStoreAccountDetailEntity;
import com.buss.store.entity.TStoreAccountInfoEntity;

public interface TStoreAccountServiceI extends CommonService{

	/**
	 * @param tStoreAccountDetail
	 */
	void doInfoUpdate(TStoreAccountDetailEntity tStoreAccountDetail);

	/**零售商充值短信条数
	 * @param tStoreAccountInfo
	 * @param tStoreAccountDetail
	 */
	String doInfoToSms(TStoreAccountInfoEntity tStoreAccountInfo,	TStoreAccountDetailEntity tStoreAccountDetail,int rechargeNumber);
	
	/**初始化零售商帐户主表
	 * @param user
	 */
	public void initStoreAccountInfo(TSUser user);
}
