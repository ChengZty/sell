package com.buss.order.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.balance.entity.TBalanceEntity;
import com.buss.bill.entity.TBillEntity;
import com.buss.kuaidi.pojo.TaskRequest;
import com.buss.kuaidi.pojo.TaskResponse;
import com.buss.kuaidi.postOrder.HttpRequest;
import com.buss.kuaidi.postOrder.JacksonHelper;
import com.buss.order.entity.TOrderInfoEntity;
import com.buss.order.entity.TOrderLogEntity;
import com.buss.order.entity.TPayRecordEntity;
import com.buss.order.service.TOrderInfoServiceI;
import com.buss.order.vo.TOrderDeliveryVo;
import com.buss.param.entity.vo.TSysParameterVO;
import com.buss.param.service.TSysParameterServiceI;
import com.buss.sms.entity.TSmsSendInfoEntity;
import com.buss.user.entity.TSUserMessage;

import cn.redis.service.RedisService;
import common.GlobalConstants;


@Service("tOrderInfoService")
@Transactional
public class TOrderInfoServiceImpl extends CommonServiceImpl implements TOrderInfoServiceI {
	@Resource  
	private RabbitTemplate rabbitTemplate; 
	@Autowired
	private TSysParameterServiceI tSysParameterService;
	@Autowired
	private RedisService redisService;
	
	private final String ZFB_ORDER_KEY = "item.updateZFBPayRollbackByOrder";
	private final String WX_ORDER_KEY = "item.updateWXPayRollbackByOrder";
	private final String RETURN_GOODS_STOCK_KEY = "item.returnGoodsStock";
	
 	
 	/* 微信或者支付宝模拟付款
 	 * @see com.buss.order.service.TOrderInfoServiceI#doPay(com.buss.order.entity.TOrderInfoEntity)
 	 */
 	@Override
 	public void doPay(TOrderInfoEntity tOrderInfo) {
 		TOrderInfoEntity order = this.flushEntity(TOrderInfoEntity.class, tOrderInfo.getId());
 		String key = ZFB_ORDER_KEY;//支付宝
 		order.setPayMethod("支付宝");
		if("3".equals(tOrderInfo.getPayMethod())){//3：微信，4：支付宝
			key = WX_ORDER_KEY;//微信
			order.setPayMethod("微信");
		}
		BigDecimal rate = new BigDecimal(6);//支付费率
		BigDecimal payAmount = Utility.isNotEmpty(order.getPayAmount())?order.getPayAmount():BigDecimal.ZERO;//支付宝付款金额
		order.setOrderStatus(TOrderInfoEntity.ORDER_STATUS_2);
		order.setServiceCharge(rate.multiply(payAmount).divide(new BigDecimal(1000)).setScale(2, BigDecimal.ROUND_HALF_UP));//支付手续费
		order.setRealIncome(payAmount.subtract(order.getServiceCharge()));//实际结算金额 = 支付金额-支付手续费
		order.setPayTime(Utility.getCurrentTimestamp());
		order.setPayStatus("2");//支付成功
//			order.setPayAccount(payAccount);//支付帐号
		order.setBillBatch(DateUtils.formatDate(new Date(), "yyyyMMddHHmm")+"00");//用于结算的批次号
		order.setPostCode("-1");//设置为测试订单
		commonDao.updateEntitie(order);
		
		//如果使用了G+卡支付则，扣减卡包 总余额和冻结金额,并生成账单
//		updateGuideCard(order);
		
		//更新订单明细状态为已付款
		StringBuilder sbdetail = new StringBuilder();
		sbdetail.append(" UPDATE t_order_detail set order_status='2',pay_status='2',pay_Time='")
		.append(DateUtils.datetimeFormat.format(order.getPayTime())).append("'")
		.append(" WHERE order_id='").append(order.getId()).append("'");
		commonDao.updateBySqlString(sbdetail.toString());
		if(Utility.isNotEmpty(order.getUserId())){
			TSUser user = this.commonDao.get(TSUser.class, order.getUserId());
			/**类型 0:无效号码 1 ：无反应顾客 2：点击顾客 3：交易顾客*/
			//潜在顾客变为已购买顾客，购买次数+1(尖刀)
			if(common.GlobalConstants.USER_TYPE_04.equals(user.getUserType())){//尖刀产品顾客和G+顾客
				//查出最近的购买的短信记录，并更新短信类型为购买顾客，同时更新潜在顾客为购买顾客
				String sql = "select s.id,s.cust_id,s.type,s.send_info_id from t_sms_send_info i left join t_sms_send s on i.id = s.send_info_id where i.status = 'A'"
					+" and i.retailer_id = ? and s.status = 'A' and s.phone = ? order by i.batch_no desc";
				List<Map<String, Object>> list = this.commonDao.findForJdbc(sql, user.getRetailerId(),user.getMobilePhone());
				if(list.size()>0){
					String id = list.get(0).get("id")+"";//明细ID
//				String custId = list.get(0).get("cust_id")+"";
					String send_info_id = list.get(0).get("send_info_id")+"";
					String type = list.get(0).get("type")+"";
					if(Utility.isNotEmpty(type)&&!"3".equals(type)){//不是交易顾客，更新为交易顾客，更新
						this.commonDao.updateBySqlString("update t_sms_send set type = '3' where id='"+id+"'");
						TSmsSendInfoEntity smsSendInfo = this.commonDao.get(TSmsSendInfoEntity.class, send_info_id);
						this.updateBuyRate(smsSendInfo);//更新购买率
						this.commonDao.updateEntitie(smsSendInfo);
					}
				}
				this.commonDao.updateBySqlString("update t_focus_customer set type = '3',buy_count = IFNULL(buy_count,0)+1 where phone_no ='"
						+user.getMobilePhone()+"' and to_retailer_id = '"+user.getRetailerId()+"'");
				LogUtil.info("--------------顾客已购买，手机号是：-------------"+user.getMobilePhone());
			}
		}
		
		//订单支付宝支付发送mq消息
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderId", order.getId());
		rabbitTemplate.convertAndSend(key, map);
		
		//记录付款日志
 		TPayRecordEntity payRecord = new TPayRecordEntity();
 		payRecord.setOrderNo(order.getOrderNo());
 		payRecord.setPayAmount(order.getPayAmount());
 		this.commonDao.save(payRecord);
 	}
 	
 	/**更新购买量，购买率和营销成功度
	 * @param smsSendInfo
	 */
	private void updateBuyRate(TSmsSendInfoEntity tSmsInfo) {
		tSmsInfo.setBuySingle(tSmsInfo.getBuySingle()+1);//购买量+1
		double rate = tSmsInfo.getBuySingle().doubleValue()*100/tSmsInfo.getPushCount();//营销成功度  购买量/推送量
		String rateResult = null;//营销成功度
		if(rate<8){//低
			rateResult = "弱";
		}else if(rate>=8&rate<20){//中
			rateResult = "中";
		}else{//高
			rateResult = "强";
		}
		LogUtil.info("-------------购买量为："+tSmsInfo.getBuySingle());
		//更新购买率
		tSmsInfo.setBuyRate(new BigDecimal(rate).setScale(2,BigDecimal.ROUND_HALF_UP));
		tSmsInfo.setSuccessDegree(rateResult);
	}
 	
 	/**
	 * 更新G+卡总金额，余额和冻结金额
	 * @param order
	 */
	private void updateGuideCard(TOrderInfoEntity order) {
		if(Utility.isNotEmpty(order.getVipMoneyPay()) && order.getVipMoneyPay().compareTo(BigDecimal.ZERO) > 0){
			TBalanceEntity balance = commonDao.findUniqueByProperty(TBalanceEntity.class,"userId",order.getUserId());
			if(Utility.isNotEmpty(balance)){
				balance.setFrozenBalance(balance.getFrozenBalance().subtract(order.getVipMoneyPay()));//冻结金额
				balance.setTotalBalance(balance.getTotalBalance().subtract(order.getVipMoneyPay()));//总金额
				commonDao.updateEntitie(balance);
				
				//生成G+卡账单记录
				TBillEntity bill = new TBillEntity();
				String billNo = commonDao.getOrderNo(common.GlobalConstants.ORDER_TYPE_BILL);
				bill.setBillNo(billNo);//账单流水号
				bill.setUserId(balance.getUserId());
				bill.setPhoneNo(order.getUserPhone());
				bill.setRetailerId(balance.getRetailerId());
				bill.setBusinessDate(new Date());
				bill.setBillAmount(order.getVipMoneyPay());//G+卡交易金额
				bill.setBillType(TBillEntity.BILL_TYPE_2);//  1.充值、2.购物、3.转账（暂不考虑）
				bill.setOrderId(order.getId());//订单id
				bill.setOrderNo(order.getOrderNo());
				bill.setToGuideId(order.getToGuideId());//所属导购
				bill.setStatus(GlobalConstants.STATUS_ACTIVE);
				commonDao.save(bill);
				
				//其他支付生成账单
				TBillEntity bill1 = new TBillEntity();
				String billNo1 = commonDao.getOrderNo(common.GlobalConstants.ORDER_TYPE_BILL);
				bill1.setBillNo(billNo1);//账单流水号
				bill1.setUserId(order.getUserId());
				bill1.setPhoneNo(order.getUserPhone());
				bill1.setRetailerId(balance.getRetailerId());//所属零售商
				bill1.setBusinessDate(new Date());
				bill1.setBillAmount(order.getPayAmount());//其他支付交易金额
				bill1.setBillType(TBillEntity.BILL_TYPE_4);//  1.充值、2.购物、3.平台充值，4.其他支付
				bill1.setOrderId(order.getId());//订单id
				bill1.setOrderNo(order.getOrderNo());
				bill1.setToGuideId(order.getToGuideId());//所属导购
				bill1.setStatus(GlobalConstants.STATUS_ACTIVE);
				commonDao.save(bill1);
			}
		}
	}
 	
 
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOrderInfoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{order_no}",String.valueOf(t.getOrderNo()));
 		sql  = sql.replace("#{order_time}",String.valueOf(t.getOrderTime()));
 		sql  = sql.replace("#{quantity_amount}",String.valueOf(t.getQuantityAmount()));
 		sql  = sql.replace("#{pay_method}",String.valueOf(t.getPayMethod()));
 		sql  = sql.replace("#{pay_account}",String.valueOf(t.getPayAccount()));
 		sql  = sql.replace("#{pay_time}",String.valueOf(t.getPayTime()));
 		sql  = sql.replace("#{pay_amount}",String.valueOf(t.getPayAmount()));
 		sql  = sql.replace("#{fare_amount}",String.valueOf(t.getFareAmount()));
 		sql  = sql.replace("#{goods_amount}",String.valueOf(t.getGoodsAmount()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void doUpdateAddress(TOrderInfoEntity tOrderInfo) {
 		TOrderInfoEntity entity = this.commonDao.get(TOrderInfoEntity.class, tOrderInfo.getId());
 		entity.setReciverPhone(tOrderInfo.getReciverPhone());
 		entity.setReciverProvince(tOrderInfo.getReciverProvince());
 		entity.setReciverCity(tOrderInfo.getReciverCity());
 		entity.setReciverRegion(tOrderInfo.getReciverRegion());
 		entity.setReciverDetailInfo(tOrderInfo.getReciverDetailInfo());
 		commonDao.updateEntitie(entity);
 	}
 	
 	//获取待发货订单明细个数(只查询后台发货的)
	 public Long getToBeFahuoCount(TSUser user) {
		Long count = 0L;
		if(GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商
			count = this.commonDao.getCountForJdbc("select count(1) from t_order_info where status ='A' and order_status = '2' and refund_status = '0' and  to_retailer_Id ='"
					+user.getId()+"' and delivery_type ='1'");
		}else if(GlobalConstants.USER_TYPE_05.equals(user.getUserType())){//员工
			count = this.commonDao.getCountForJdbc("select count(1) from t_order_info where status ='A' and order_status = '2' and refund_status = '0' and  to_retailer_Id ='"
					+user.getRetailerId()+"' and delivery_type ='1'");
		}else{//后台
			count = this.commonDao.getCountForJdbc("select count(1) from t_order_info where status ='A' and order_status = '2' and refund_status = '0' and delivery_type ='1'");
		}
		return count;
	}
	 
	 /* 
	 * 批量发货并更新订单和明细的状态和发货信息，记录订单日志
	 */
	@Override
	public Map<String,Object> doBatchFahuo(List<TOrderDeliveryVo> deliveryList) {
		 Map<String,Object> map = new HashMap<String, Object>();
		 Set<String> guideIds= new HashSet<String>();//导购id集合
		 List<TOrderInfoEntity> orderList = new ArrayList<TOrderInfoEntity>();//物流发货的订单列表
		 TSUser loginUser = ResourceUtil.getSessionUserName();
		 for(TOrderDeliveryVo vo : deliveryList){
			 TOrderInfoEntity orderInfo = this.commonDao.findUniqueByProperty(TOrderInfoEntity.class, "orderNo", vo.getOrderNo());
			 if(TOrderInfoEntity.ORDER_STATUS_2.equals(orderInfo.getOrderStatus())){//待发货
				 orderInfo.setDeliveryType(vo.getDeliveryType());
				 orderInfo.setOrderStatus(TOrderInfoEntity.ORDER_STATUS_3);
				 orderInfo.setSendTime(Utility.getCurrentTimestamp());//发货时间
				 if(TOrderInfoEntity.DELIVERY_TYPE_0.equals(vo.getDeliveryType())){//顾客自提
 					orderInfo.setDeliveryNo("无");
 				 }else{//后台发货
 					String[] com =vo.getDeliveryName().split(","); //格式为code,name
 					orderInfo.setDeliveryCode(com[0]);
 					orderInfo.setDeliveryName(com[1]);
 					orderInfo.setDeliveryNo(vo.getDeliveryNo());
 					orderList.add(orderInfo);
 				 }
				 this.commonDao.updateEntitie(orderInfo);
				 //更新明细状态
				 this.updateBySqlString("update t_order_detail set order_status = '3' where order_id = '"+orderInfo.getId()+"'");
				 //记录订单日志
				 this.saveLog(orderInfo,loginUser);
				//记录工作台订单红点
				this.redisService.set(GlobalConstants.GUIDE_OD_RED_3+"_"+orderInfo.getToGuideId(),GlobalConstants.SIGN_YES );
				 
				 String guideId= orderInfo.getToGuideId();
				 if(StringUtil.isNotEmpty(guideId)){
					 guideIds.add(guideId);
				 }
			 }
		 }
		 map.put("guideIds", guideIds);
		 map.put("orderList", orderList);
		 return map;
	}

	//记录订单日志
	private void saveLog(TOrderInfoEntity orderInfo,TSUser user) {
		TOrderLogEntity orderLog = new TOrderLogEntity();
		orderLog.setLogTime(DateUtils.gettimestamp());
		orderLog.setLogOrderStatus(orderInfo.getOrderStatus());
		orderLog.setOrderId(orderInfo.getId());
		orderLog.setOrderNo(orderInfo.getOrderNo());
		orderLog.setLogUserName(user.getRealName());
		orderLog.setLogRoleName("后台");
		orderLog.setType(TOrderLogEntity.TYPE_1);
		this.commonDao.save(orderLog);
	}

	@Override
	public void sendFahuoMsg(TOrderInfoEntity orderInfo) {
		//判断是否发送短信
		 String guideId= orderInfo.getToGuideId();
		 if(StringUtil.isNotEmpty(guideId)&&StringUtil.isNotEmpty(orderInfo.getUserId())&&TOrderInfoEntity.DELIVERY_TYPE_1.equals(orderInfo.getDeliveryType())){
			 this.sendMsgToGuide(orderInfo);
			 TSysParameterVO param = tSysParameterService.getParameterByCode(GlobalConstants.ORDER_SEND, orderInfo.getToRetailerId());
			 if(Utility.isNotEmpty(param) && Utility.isNotEmpty(param.getParaValue())){
				 this.sendMsgToCust(orderInfo,param);
			 }else{
				 LogUtil.info("零售商未配置短信自动回复的内容,短信未发送");
			 }
		 }
	}
	
	/**给导购发送发货短信*/
	private void sendMsgToGuide(TOrderInfoEntity orderInfo) {
		String msg = null;
		 TSUser user = commonDao.get(TSUser.class,orderInfo.getToGuideId());
//		 String phone = "13265488209";//客服电话
		 msg = "您好，您的g+订单号（订单号）已发货（物流单号：（物流单号））。【autoName】";
		 msg = msg.replaceAll("（订单号）", orderInfo.getOrderNo());
		 msg = msg.replaceAll("（物流单号）",orderInfo.getDeliveryNo());
		 
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("retailerId", orderInfo.getToRetailerId());
		 params.put("phoneNo", user.getUserName());
		 params.put("msg", msg);
		 params.put("sendTime", Utility.getCurrentTimestamp());
		 params.put("userType", user.getUserType());
		 params.put("msgType", TSUserMessage.MSG_TYPE_4);//发货
		 ResourceBundle rabbitmq = ResourceBundle.getBundle("rabbitmq");
		 String key = rabbitmq.getString("send.order.msg.mq.queue.key");
		 rabbitTemplate.convertAndSend(key, params);
		
	}

	/**给顾客发送发货短信*/
	private void sendMsgToCust(TOrderInfoEntity orderInfo,TSysParameterVO param) {
		 String msg = null;
		 TSUser user = commonDao.get(TSUser.class,orderInfo.getUserId());
//	String phone = "15112345601";//客服电话
//			msg = "您好，您的订单号（订单号）已发货（物流单号：（物流单号））。如有疑问联系客服："+phone+"【autoName】";
		 msg = param.getParaValue()+"【autoName】";
		 msg = msg.replaceAll("（订单号）", orderInfo.getOrderNo());
		 msg = msg.replaceAll("（物流单号）",orderInfo.getDeliveryNo());
		 
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("retailerId", orderInfo.getToRetailerId());
		 params.put("phoneNo", user.getUserName());
		 params.put("msg", msg);
		 params.put("sendTime", Utility.getCurrentTimestamp());
		 params.put("userType", user.getUserType());
		 params.put("msgType", TSUserMessage.MSG_TYPE_4);//发货
		 ResourceBundle rabbitmq = ResourceBundle.getBundle("rabbitmq");
		 String key = rabbitmq.getString("send.order.msg.mq.queue.key");
		 rabbitTemplate.convertAndSend(key, params);
	}

	/**
	 * 物流订阅
	 * @param deliveryCode 物流编码
	 * @param deliveryNo 物流单号
	 */
 	public void subscription(TOrderInfoEntity orderInfo){
 		String deliveryCode = orderInfo.getDeliveryCode();
 		String deliveryNo = orderInfo.getDeliveryNo();
 		if(Utility.isNotEmpty(deliveryCode)&&Utility.isNotEmpty(deliveryNo)){
 			TaskRequest req = new TaskRequest();
 			req.setCompany(deliveryCode);
 			req.setNumber(deliveryNo);
 			req.getParameters().put("callbackurl",ResourceUtil.getConfigByName("callbackurl"));
 			req.setKey(ResourceUtil.getConfigByName("expressKey"));
 			HashMap<String, String> p = new HashMap<String, String>(); 
 			p.put("schema", "json");
 			p.put("param", JacksonHelper.toJSON(req));
 			try {
 				LogUtil.info(p);
 				String ret = HttpRequest.postData(ResourceUtil.getConfigByName("expressUrl"), p, "UTF-8");
 				TaskResponse resp = JacksonHelper.fromJSON(ret, TaskResponse.class);
 				LogUtil.info(resp);
 				if(resp.getResult()==true){
 					LogUtil.info(deliveryCode+":"+deliveryNo+"订阅成功");
 				}else{
 					LogUtil.info(deliveryCode+":"+deliveryNo+"订阅失败");
 				}
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
 		}
 	}
	
 	@Override
 	public Map<String, Object> doBatchFahuoByExcel(List<TOrderDeliveryVo> voList, Map<String, String> mapDelivery) {
 		Map<String,Object> map = new HashMap<String, Object>();
		 Set<String> guideIds= new HashSet<String>();//导购id集合
		 List<TOrderInfoEntity> orderList = new ArrayList<TOrderInfoEntity>();//物流发货的订单列表
		 TSUser loginUser = ResourceUtil.getSessionUserName();
		 for(TOrderDeliveryVo vo : voList){
			 TOrderInfoEntity orderInfo = this.commonDao.findUniqueByProperty(TOrderInfoEntity.class, "orderNo", vo.getOrderNo());
//			 if(TOrderInfoEntity.ORDER_STATUS_2.equals(orderInfo.getOrderStatus())){//待发货前面已经校验过
				 orderInfo.setDeliveryType(TOrderInfoEntity.DELIVERY_TYPE_1);//后台发货
				 orderInfo.setOrderStatus(TOrderInfoEntity.ORDER_STATUS_3);
					orderInfo.setDeliveryCode(mapDelivery.get(vo.getDeliveryName()));
					orderInfo.setDeliveryName(vo.getDeliveryName());
					orderInfo.setDeliveryNo(vo.getDeliveryNo());
					orderList.add(orderInfo);
				 this.commonDao.updateEntitie(orderInfo);
				 //更新明细状态
				 this.updateBySqlString("update t_order_detail set order_status = '3' where order_Id = '"+orderInfo.getId()+"'");
				 //记录订单日志
				 this.saveLog(orderInfo,loginUser);
				 String guideId= orderInfo.getToGuideId();
				 if(StringUtil.isNotEmpty(guideId)){
					 guideIds.add(guideId);
				 }
//			 }
		 }
		 map.put("guideIds", guideIds);
		 map.put("orderList", orderList);
		 return map;
 	}
	
	@Override
	public void doCancelOrder(String id) {
		TOrderInfoEntity orderInfo = this.get(TOrderInfoEntity.class, id);
		if(orderInfo!=null&&TOrderInfoEntity.ORDER_STATUS_1.equals(orderInfo.getOrderStatus())){
			orderInfo.setOrderStatus(TOrderInfoEntity.ORDER_STATUS_9);
			this.commonDao.updateEntitie(orderInfo);
			//发送mq消息还库存
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("orderId", id);
			rabbitTemplate.convertAndSend(RETURN_GOODS_STOCK_KEY, map);
		}
	}
	
	
	
	
	
	
	
	
	
	
	

}