package com.buss.refund.service.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.order.entity.TOrderInfoEntity;
import com.buss.order.entity.TOrderLogEntity;
import com.buss.order.entity.TOrderTicketEntity;
import com.buss.refund.entity.TRefundReturnEntity;
import com.buss.refund.service.TRefundReturnServiceI;
import com.buss.ticket.entity.TTicketDetailEntity;
import com.buss.ticket.entity.TTicketInfoEntity;

import cn.redis.service.RedisService;
import common.GlobalConstants;

@Service("tRefundReturnService")
@Transactional
public class TRefundReturnServiceImpl extends CommonServiceImpl implements TRefundReturnServiceI {
	@Autowired
	private RedisService redisService;

	@Override
	public void doNotAgree(TRefundReturnEntity tRefundReturn) {
		TRefundReturnEntity entity = this.commonDao.get(TRefundReturnEntity.class, tRefundReturn.getId());
		if(TRefundReturnEntity.REFUND_STATUS_2.equals(entity.getRefundStatus())){
			entity.setSellerRemark(tRefundReturn.getSellerRemark());//拒绝退款备注
			entity.setSellerStatus(TRefundReturnEntity.SELLER_STATUS_3);
			entity.setRefundStatus(TRefundReturnEntity.REFUND_STATUS_4);
			entity.setSellerTime(DateUtils.gettimestamp());
			this.updateEntitie(entity);
		}
		//记录退款日志
		saveLog(entity,TRefundReturnEntity.REFUND_STATUS_4);
		//回写订单退款状态，并把订单改为已完成状态
		TOrderInfoEntity order = this.commonDao.get(TOrderInfoEntity.class, entity.getOrderId());
		order.setCanRefundAll(TOrderInfoEntity.CAN_REFUND_ALL_N);
		order.setRefundAmount(order.getRefundAmount().subtract(entity.getRefundAmount()));//减去申请退款金额
		order.setReturnNum(order.getReturnNum().subtract(entity.getGoodsNum()));//减去申请退货数量
		if(order.getReturnNum().compareTo(BigDecimal.ZERO)==0){//
			order.setCanRefundAll(TOrderInfoEntity.CAN_REFUND_ALL_Y);//可以整单退
		}
		if(Utility.isNotEmpty(entity.getSubOrderNo())){//单件退
			//更新订单明细的退货退款
			String updateSql = "update t_order_detail set refund_amount = refund_amount - "+entity.getRefundAmount()
					+",return_num = return_num - "+entity.getGoodsNum()+" where status = 'A' and sub_order_no = '"+entity.getSubOrderNo()+"'";
			this.commonDao.updateBySqlString(updateSql);
			//记录工作台订单红点
			this.redisService.set(GlobalConstants.GUIDE_OD_RED_R+"_"+order.getToGuideId(),GlobalConstants.SIGN_YES );
		}else{//整单退
			//更新订单明细的退货退款
			String updateSql = "update t_order_detail set refund_amount = 0,return_num = 0 where status = 'A' and order_id = '"+entity.getOrderId()+"'";
			this.commonDao.updateBySqlString(updateSql);
			//记录工作台订单红点
			this.redisService.set(GlobalConstants.GUIDE_OD_RED_R+"_"+order.getToGuideId(),GlobalConstants.SIGN_YES );
		}
		//更新订单主表的退货退款
		this.commonDao.updateEntitie(order);
	}
	
	//零售商同意退款
	@Override
	public Map<String,Object> doPay(TRefundReturnEntity tRefundReturn) {
		TRefundReturnEntity entity = this.commonDao.get(TRefundReturnEntity.class, tRefundReturn.getId());
		if(TRefundReturnEntity.REFUND_STATUS_2.equals(entity.getRefundStatus())){
			entity.setRefundAmountReal(tRefundReturn.getRefundAmountReal());//实际退款金额
			entity.setPayMethodName(tRefundReturn.getPayMethodName());//退款方式
			entity.setPayNo(tRefundReturn.getPayNo());
			entity.setRefundStatus(TRefundReturnEntity.REFUND_STATUS_5);
			entity.setSellerTime(Utility.getCurrentTimestamp());
			entity.setAdminTime(Utility.getCurrentTimestamp());
			entity.setAdminRemark(tRefundReturn.getAdminRemark());
			this.commonDao.updateEntitie(entity);
			//记录退款日志
			saveLog(tRefundReturn,TRefundReturnEntity.REFUND_STATUS_5);
			if(TRefundReturnEntity.REFUND_SOURCE_2.equals(entity.getRefundSource())){//待发货申请的退款，判断是否需要关闭订单
				TOrderInfoEntity order = this.commonDao.get(TOrderInfoEntity.class, entity.getOrderId());
				if(Utility.isNotEmpty(tRefundReturn.getSubOrderNo())){//单件退
					if(TOrderInfoEntity.CAN_REFUND_ALL_A.equals(order.getCanRefundAll())){//全部已申请退款
						//判断已退款完成的总数是和订单的总件数一致
						String sql = "select ifnull(sum(goods_num),0) num from t_refund_return where order_id = ? and status='A' and id <> ? and refund_status = ?";
						Map<String, Object> map = this.commonDao.findOneForJdbc(sql, order.getId(),tRefundReturn.getId(),TRefundReturnEntity.REFUND_STATUS_5);
						BigDecimal refundNum = BigDecimal.ZERO;//已经退的总件数
						if(Utility.isNotEmpty(map)){
							refundNum = new BigDecimal(map.get("num")+"");
						}
						if(refundNum.add(entity.getGoodsNum()).compareTo(order.getQuantityAmount())==0){
							order.setOrderStatus(TOrderInfoEntity.ORDER_STATUS_8);//待发货全部退完明细，关闭订单
							order.setCloseTime(Utility.getCurrentTimestamp());
							this.commonDao.updateEntitie(order);
							//记录工作台订单红点
							this.redisService.set(GlobalConstants.GUIDE_OD_RED_9+"_"+order.getToGuideId(),GlobalConstants.SIGN_YES );
						}
					}
				}else{//整单退
					order.setOrderStatus(TOrderInfoEntity.ORDER_STATUS_8);//待发货全部退完明细，关闭订单
					order.setCloseTime(Utility.getCurrentTimestamp());
					this.commonDao.updateEntitie(order);
					//记录工作台订单红点
					this.redisService.set(GlobalConstants.GUIDE_OD_RED_9+"_"+order.getToGuideId(),GlobalConstants.SIGN_YES );
				}
				
			}
			//保存退款券的失效记录
			this.saveOrderTicket(entity);
			Map<String,Object> map = new HashMap<String, Object>();
			TSUser user = ResourceUtil.getSessionUserName();
			map.put("refundId", tRefundReturn.getId());
			map.put("operatorId", user.getId());
			map.put("operatorName", user.getRealName());
			return map;
		}
		return null;
	}
	
	
	/**保存退款的订单券的
	 * @param entity
	 */
	private void saveOrderTicket(TRefundReturnEntity entity) {
		String ticketId = entity.getTicketId();
		if(Utility.isNotEmpty(ticketId)){
			TTicketInfoEntity ticketInfo = this.commonDao.get(TTicketInfoEntity.class, ticketId);
			TTicketDetailEntity ticketDetail = this.commonDao.get(TTicketDetailEntity.class, entity.getTicketDetailId());
			TOrderTicketEntity orderTicket = new TOrderTicketEntity();
			orderTicket.setOrderId(entity.getId());
			orderTicket.setOrderNo(entity.getRefundNo());
			orderTicket.setOrderType(TOrderTicketEntity.ORDER_TYPE_2);//买
			orderTicket.setTicketId(ticketInfo.getId());
			orderTicket.setTicketType(ticketInfo.getType());//券的类型:1,红包,2现金券,3折扣券
			orderTicket.setTicketDetailId(entity.getTicketDetailId());
			orderTicket.setTicketCode(ticketDetail.getTicketCode());
			orderTicket.setTicketLeastMoney(ticketInfo.getLeastMoney());//满额
			orderTicket.setTicketDiscount(ticketInfo.getFaceValue());//减额
			orderTicket.setTicketPreferential(entity.getTicketPreferential());//实际优惠额度
			orderTicket.setTicketStatus(TOrderTicketEntity.TICKET_STATUS_2);//失效
			orderTicket.setRetailerId(entity.getRetailerId());//所属零售商id
			orderTicket.setToGuideId(entity.getToGuideId());
			orderTicket.setStoreId(ticketInfo.getStoreId());//店铺id
			orderTicket.setCreateDate(new Date());
			orderTicket.setStatus(GlobalConstants.STATUS_ACTIVE);
			commonDao.save(orderTicket);
		}
		
	}
	
	/**记录退款日志
	 * @param tRefundReturn
	 * @param refundStatus
	 */
	private void saveLog(TRefundReturnEntity tRefundReturn,String refundStatus){
		TSUser user = ResourceUtil.getSessionUserName();
		TOrderLogEntity orderLog = new TOrderLogEntity();
		orderLog.setLogTime(DateUtils.gettimestamp());
		orderLog.setLogOrderStatus(refundStatus);
		orderLog.setOrderId(tRefundReturn.getId());
		orderLog.setOrderNo(tRefundReturn.getRefundNo());
		orderLog.setLogUserName(user.getRealName());
		orderLog.setLogRoleName("后台");
		orderLog.setType(TOrderLogEntity.TYPE_2);//退款
		this.commonDao.save(orderLog);
	}


	@Override
	public Long getToBeRefundCount(TSUser user) {
		Long count = 0L;
		if(GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商（查询导购同意退款的）
			count = this.commonDao.getCountForJdbc("select count(1) from t_refund_return where status ='A' and refund_status = '"+TRefundReturnEntity.REFUND_STATUS_2+"' and  to_store_goods_Id ='"
					+user.getId()+"'");
		}else if(GlobalConstants.USER_TYPE_05.equals(user.getUserType())){//员工
			count = this.commonDao.getCountForJdbc("select count(1) from t_refund_return where status ='A' and refund_status = '"+TRefundReturnEntity.REFUND_STATUS_2+"' and  to_store_goods_Id ='"
					+user.getRetailerId()+"'");
		}else{//后台（查询零售商同意退款的）
//			count = this.commonDao.getCountForJdbc("select count(1) from t_refund_return where status ='A' and refund_status = '"+TRefundReturnEntity.REFUND_STATUS_3+"' ");
		}
		return count;
	}
}