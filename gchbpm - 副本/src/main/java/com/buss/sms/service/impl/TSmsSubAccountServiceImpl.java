package com.buss.sms.service.impl;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.sms.entity.TSmsCountInfoEntity;
import com.buss.sms.entity.TSmsSubAccountEntity;
import com.buss.sms.service.TSmsSubAccountServiceI;

@Service("tSmsSubAccountService")
@Transactional
public class TSmsSubAccountServiceImpl extends CommonServiceImpl implements TSmsSubAccountServiceI {

	@Override
	public void saveAccount(TSmsSubAccountEntity tSmsSubAccount) {
		tSmsSubAccount.setLockingNumber(0);
		tSmsSubAccount.setRemind(TSmsSubAccountEntity.REMIND_OK);
		commonDao.save(tSmsSubAccount);
		String retailerId = tSmsSubAccount.getRetailerId();
		TSmsCountInfoEntity tSmsCountInfoEntity = new TSmsCountInfoEntity();
		tSmsCountInfoEntity.setRetailerId(retailerId);
		String sql= "select username from t_s_user where id = '" + retailerId + "'";
		Map<String, Object> countMap = commonDao.findOneForJdbc(sql.toString(), null);
		String retailerName = countMap.get("username").toString();
		tSmsCountInfoEntity.setRetailerName(retailerName);
		int number = tSmsSubAccount.getSmsNumber();
		tSmsCountInfoEntity.setNumber(number);
		tSmsCountInfoEntity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
		if(number >= 0){
			tSmsCountInfoEntity.setType(TSmsCountInfoEntity.TYPE_1);
			tSmsCountInfoEntity.setRemark("平台充值短信");
		}
		commonDao.save(tSmsCountInfoEntity);
	}
	
	@Override
	public void updateAccount(TSmsSubAccountEntity tSmsSubAccount) throws Exception {
		TSmsSubAccountEntity t = commonDao.get(TSmsSubAccountEntity.class, tSmsSubAccount.getId());
		String retailerId = tSmsSubAccount.getRetailerId();
		TSmsCountInfoEntity tSmsCountInfoEntity = new TSmsCountInfoEntity();
//		tSmsCountInfoEntity.setBatchNo(batchNo);
		tSmsCountInfoEntity.setRetailerId(retailerId);
		String sql= "select username from t_s_user where id = '" + retailerId + "'";
		Map<String, Object> countMap = commonDao.findOneForJdbc(sql.toString(), null);
		String retailerName = countMap.get("username").toString();
		tSmsCountInfoEntity.setRetailerName(retailerName);
		int number = tSmsSubAccount.getModifyNumber();//修改的条数
		tSmsCountInfoEntity.setNumber(number);
		tSmsCountInfoEntity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
		if(number >= 0){
			tSmsCountInfoEntity.setType(TSmsCountInfoEntity.TYPE_1);
			tSmsCountInfoEntity.setRemark("平台充值短信");
		}else{
			tSmsCountInfoEntity.setType(TSmsCountInfoEntity.TYPE_2);
			tSmsCountInfoEntity.setRemark("平台扣除短信");
		}
		commonDao.save(tSmsCountInfoEntity);
		//更新总数
		int smsNumner = number + t.getSmsNumber();
		tSmsSubAccount.setSmsNumber(smsNumner);
		MyBeanUtils.copyBeanNotNull2Bean(tSmsSubAccount, t);
		commonDao.saveOrUpdate(t);
		
	}
}