package com.buss.store.service.impl;
import java.math.BigDecimal;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.sms.entity.TSmsSubAccountEntity;
import com.buss.store.entity.TAppCostInfoEntity;
import com.buss.store.entity.TStoreAccountDetailEntity;
import com.buss.store.entity.TStoreAccountInfoEntity;
import com.buss.store.service.TStoreAccountServiceI;

@Service("tStoreAccountService")
@Transactional
public class TStoreAccountServiceImpl extends CommonServiceImpl implements TStoreAccountServiceI {

	@Override
	public void doInfoUpdate(TStoreAccountDetailEntity tStoreAccountDetail) {
		String retailerId = tStoreAccountDetail.getRetailerId();
		TStoreAccountInfoEntity tStoreAccountInfo = commonDao.findUniqueByProperty(TStoreAccountInfoEntity.class,"retailerId", retailerId);
		BigDecimal operateMoney = tStoreAccountDetail.getOperateMoney(); //充值金额
		String type  = tStoreAccountDetail.getType();  //得到类型（1,充值， 0扣费）
		if(TStoreAccountDetailEntity.TYPE_1.equals(type)){  //充值
			tStoreAccountInfo.setRemainMoney(tStoreAccountInfo.getRemainMoney().add(operateMoney));//更新余额
			this.checkAndUpdateAccountStatus(tStoreAccountInfo);
			
		}else if(TStoreAccountDetailEntity.TYPE_0.equals(type)){//扣费
			tStoreAccountInfo.setRemainMoney(tStoreAccountInfo.getRemainMoney().subtract(operateMoney));//更新余额
			this.checkAndUpdateAccountStatus(tStoreAccountInfo);
		}

		//更新账户详细和记录的余额
		tStoreAccountDetail.setRemainMoney(tStoreAccountInfo.getRemainMoney());
		//保存账户更新记录信息
		commonDao.save(tStoreAccountDetail);
		
	}
	
	/**判断帐户余额是否足够预扣费并更新状态
	 * @param tStoreAccountInfo
	 */
	private void checkAndUpdateAccountStatus(TStoreAccountInfoEntity tStoreAccountInfo) {
		BigDecimal remainMoney = tStoreAccountInfo.getRemainMoney();//账户余额
		String sql = "SELECT sum(account_year_cost)+sum(app_month_cost) preCost from t_app_cost_info where status ='A' and retailer_id = ? and charge_status = ?";//待扣费总费用（端口服务费+年费）
		Map<String, Object> map = this.commonDao.findOneForJdbc(sql, tStoreAccountInfo.getRetailerId(),TAppCostInfoEntity.CHARGE_STATUS_0);
		BigDecimal preCost = BigDecimal.ZERO;
		if(Utility.isNotEmpty(map)&&Utility.isNotEmpty(map.get("preCost"))){
			preCost = (BigDecimal) map.get("preCost");
		}
		//修改账户状态
		int r=remainMoney.subtract(preCost).compareTo(BigDecimal.ZERO); //和0，Zero比较
		if(r>=0){//大于0，账户余额充足
			tStoreAccountInfo.setAccountStatus(TStoreAccountInfoEntity.ACCOUNT_STATUS_OK);
			//修改提醒状态 为正常状态
			tStoreAccountInfo.setRemind(TStoreAccountInfoEntity.REMIND_OK);
		}else {//小于等于0，余额不足
			tStoreAccountInfo.setAccountStatus(TStoreAccountInfoEntity.ACCOUNT_STATUS_BAD);
			//修改提醒状态 为正常状态
			tStoreAccountInfo.setRemind(TStoreAccountInfoEntity.REMIND_NO);
		}
		//更新账户余额和状态信息
		commonDao.updateEntitie(tStoreAccountInfo);
	}
	
	@Override
	public String doInfoToSms(TStoreAccountInfoEntity tStoreAccountInfo,TStoreAccountDetailEntity tStoreAccountDetail,int rechargeNumber) {
		//查询短信账户信息 
		TSmsSubAccountEntity tSmsSubAccount = commonDao.findUniqueByProperty(TSmsSubAccountEntity.class, "retailerId",tStoreAccountDetail.getRetailerId());
		if(Utility.isNotEmpty(tSmsSubAccount)){
			tStoreAccountInfo.setRemainMoney(tStoreAccountInfo.getRemainMoney().subtract(tStoreAccountDetail.getOperateMoney()));//更新余额
			int smsNumber = tSmsSubAccount.getSmsNumber();
			smsNumber = smsNumber + rechargeNumber;
			//判断帐户余额是否足够预扣费并更新状态
			this.checkAndUpdateAccountStatus(tStoreAccountInfo);
			//更新账户详细和记录的余额
			tStoreAccountDetail.setRemainMoney(tStoreAccountInfo.getRemainMoney());
			tSmsSubAccount.setSmsNumber(smsNumber);
			//保存账户更新记录信息
			commonDao.save(tStoreAccountDetail);
			//更新账户余额和状态信息
			commonDao.saveOrUpdate(tSmsSubAccount);
		}else{
			return "请联系管理员开通短信帐号";
		}
		return null;
	}
	
	@Override
	public void initStoreAccountInfo(TSUser user) {
		TStoreAccountInfoEntity storeAccountInfo = new TStoreAccountInfoEntity();
		storeAccountInfo.setActiveDate(Utility.getCurrentTimestamp());//激活日期
		storeAccountInfo.setCreateName("system");
		storeAccountInfo.setStatus("A");
		storeAccountInfo.setId(Utility.getUUID());
		storeAccountInfo.setCreateDate(Utility.getCurrentTimestamp());
//		storeAccountInfo.setMonthCharge(appCostInfo.getAppMonthCost());
//		storeAccountInfo.setYearCharge(appCostInfo.getAccountYearCost());
		storeAccountInfo.setRemainMoney(BigDecimal.ZERO);
		storeAccountInfo.setAccountStatus(TStoreAccountInfoEntity.ACCOUNT_STATUS_OK);//正常
		storeAccountInfo.setRemind(TStoreAccountInfoEntity.REMIND_OK);//正常
		storeAccountInfo.setRetailerId(user.getId());//零售商id
		storeAccountInfo.setRetailerName(user.getUserName());//用户名
		storeAccountInfo.setRetailerRealname(user.getRealName());//真实姓名
		this.commonDao.save(storeAccountInfo);
	}	
	
}