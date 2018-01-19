package com.buss.order.service;
import com.buss.order.entity.TOrderInfoEntity;
import com.buss.order.entity.TOrderDetailEntity;
import com.buss.order.vo.TOrderDeliveryVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;

import java.io.Serializable;

public interface TOrderInfoServiceI extends CommonService{
 	
	public void doPay(TOrderInfoEntity tOrderInfo);
	public void doUpdateAddress(TOrderInfoEntity tOrderInfo);
	public Long getToBeFahuoCount(TSUser user);
	
	/**批量发货并更新订单和明细的状态和发货信息，记录订单日志
	 * @param deliveryList
	 * @return Map<String,Object>  key为guideIds和orderList(物流发货的订单)
	 */
	public Map<String,Object> doBatchFahuo(List<TOrderDeliveryVo> deliveryList);
	
	/**fa发送发货短信
	 * @param tOrderInfo
	 */
	public void sendFahuoMsg(TOrderInfoEntity tOrderInfo);
	
	/**订阅物流
	 * @param tOrderInfo 物流发货的订单
	 */
	public void subscription(TOrderInfoEntity tOrderInfo);
	
	/**通过excel导入批量发货并更新订单和明细的状态和发货信息，记录订单日志
	 * @param voList 导入的list
	 * @param mapDelivery 物流map key:deliveryName,value:deliveryCode
	 * @return Map<String,Object> key为guideIds和orderList(物流发货的订单)
	 */
	public Map<String, Object> doBatchFahuoByExcel(List<TOrderDeliveryVo> voList, Map<String, String> mapDelivery);
	
	/**取消订单，并还库存等业务
	 * @param id
	 */
	public void doCancelOrder(String id);
	
}
