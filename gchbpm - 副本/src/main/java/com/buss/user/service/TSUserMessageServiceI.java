package com.buss.user.service;

import org.jeecgframework.core.common.service.CommonService;

import com.buss.user.entity.TSUserMessage;

public interface TSUserMessageServiceI extends CommonService{

	/**
	 * 取最新验证码
	 * @param userName 帐号(手机号)
	 * @param userType 类型
	 * @return
	 */
	TSUserMessage getLastObject(String userName, String userType);

}
