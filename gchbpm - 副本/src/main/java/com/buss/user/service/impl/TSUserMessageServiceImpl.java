package com.buss.user.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.user.entity.TSUserMessage;
import com.buss.user.service.TSUserMessageServiceI;

@Service("tSUserMessageService")
@Transactional
public class TSUserMessageServiceImpl extends CommonServiceImpl implements TSUserMessageServiceI {
	
	/**
	 * 取最新验证码
	 * @param userName 帐号(手机号)
	 * @param userType 类型
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public TSUserMessage getLastObject(String userName,String userType){
		String hql = "FROM TSUserMessage WHERE userName= :userName AND userType= :userType ORDER BY sendDate desc";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("userName", userName);
		query.setParameter("userType", userType);
		query.setFirstResult(0).setMaxResults(1);//hql 取一条记录
		List<TSUserMessage> list = query.list();
		if(Utility.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
}