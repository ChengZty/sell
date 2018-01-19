package com.buss.refund.controller;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.buss.base.entity.TMqErrorEntity;
import com.buss.base.service.TMqErrorServiceI;
import com.buss.order.entity.TOrderDetailEntity;
import com.buss.order.entity.TOrderLogEntity;
import com.buss.order.service.TOrderInfoServiceI;
import com.buss.refund.entity.TRefundReturnEntity;
import com.buss.refund.service.TRefundReturnServiceI;
import com.buss.user.entity.TSUserMessage;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 退款退货
 * @author onlineGenerator
 * @date 2016-04-07 17:44:41
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tRefundReturnController")
public class TRefundReturnController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TRefundReturnController.class);

	@Autowired
	private TRefundReturnServiceI tRefundReturnService;
	@Autowired
	private SystemService systemService;
	private String message;
	@Autowired
	private TOrderInfoServiceI tOrderInfoService;
	@Autowired
	private TMqErrorServiceI tMqErrorService;
	@Resource  
	private RabbitTemplate rabbitTemplate;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 退款退货列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())||common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){//零售商
			return new ModelAndView("com/buss/refund/tRefundReturnList-retailer");
		}
		return new ModelAndView("com/buss/refund/tRefundReturnList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TRefundReturnEntity tRefundReturn,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 查询条件组装器
		try {
			Map<String,String> sqlMap = this.getQuerySql(request,dataGrid);
			String sql = sqlMap.get("sql");
			String countSql = sqlMap.get("countSql");
			List<TRefundReturnEntity> resultList = new ArrayList<TRefundReturnEntity>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TRefundReturnEntity.class);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**获取查询的sql和countSql
	 * @param request
	 * @param dataGrid
	 * @return
	 */
	private Map<String,String> getQuerySql(HttpServletRequest request,DataGrid dataGrid) {
		Map<String,String> map = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String refundNo = request.getParameter("refundNo");//退款单号
		String orderId = request.getParameter("orderId");//订单id
		String orderNo = request.getParameter("orderNo");//订单号
		String startTime = request.getParameter("addTime_begin");//申请时间
		String endTime = request.getParameter("addTime_end");
		String userName = request.getParameter("userName");//买家姓名
		String userPhone = request.getParameter("userPhone");//买家手机
		String guideName = request.getParameter("guideName");//导购姓名
		String guidePhone = request.getParameter("guidePhone");//导购手机
		String refundStatus = request.getParameter("refundStatus");//退款状态
		StringBuffer sql = new StringBuffer("SELECT r.id,r.add_time addTime,r.order_no orderNo,r.refund_no refundNo,r.user_name userName,r.user_phone userPhone,r.retailer_id retailerId,")
		.append("r.order_amount orderAmount,r.refund_amount refundAmount,r.refund_amount_real refundAmountReal,r.seller_status sellerStatus,r.seller_time sellerTime,r.refund_status refundStatus,")
		.append("r.retailer_phone retailerPhone,ifnull(r.fare,0) fare,r.refund_source refundSource,p.real_name guideName,p.phone_no guidePhone,r.guide_time,s.`name` storeName,")
		.append("r.admin_remark adminRemark,r.delivery_no deliveryNo,r.receive_remark receiveRemark,r.order_id orderId,o.pay_account payAccount,r.pay_Method_Name payMethodName")
		.append("  from t_refund_return r  LEFT JOIN t_person p ON r.retailer_id = p.to_retailer_id and r.to_guide_id = p.user_id LEFT JOIN t_store s on p.store_id = s.id LEFT JOIN t_order_info o on r.order_id = o.id where r.status = 'A'");
		StringBuffer countSql = new StringBuffer("select count(1) from t_refund_return r LEFT JOIN t_s_user u on r.to_guide_id = u.id LEFT JOIN t_order_info o on r.order_id = o.id where r.status = 'A'");
		if(Utility.isNotEmpty(retailerId)){
			sql.append(" and r.retailer_id = '").append(retailerId).append("'");
			countSql.append(" and r.retailer_id = '").append(retailerId).append("'");
		}
		if(Utility.isNotEmpty(refundStatus)){
			sql.append(" and r.refund_status = '").append(refundStatus).append("'");
			countSql.append(" and r.refund_status = '").append(refundStatus).append("'");
		}
		if(Utility.isNotEmpty(orderId)){
			sql.append(" and r.order_id = '").append(orderId).append("'");
			countSql.append(" and r.order_id = '").append(orderId).append("'");
		}
		if(Utility.isNotEmpty(refundNo)){
			sql.append(" and r.refund_no like '%").append(refundNo).append("%'");
			countSql.append(" and r.refund_no like '%").append(refundNo).append("%'");
		}
		if(Utility.isNotEmpty(orderNo)){
			sql.append(" and r.order_no like '%").append(orderNo).append("%'");
			countSql.append(" and r.order_no like '%").append(orderNo).append("%'");
		}
		if(Utility.isNotEmpty(startTime)){
			sql.append(" and r.add_time >= '").append(startTime).append(" 00:00:00'");
			countSql.append(" and r.add_time >= '").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sql.append(" and r.add_time <= '").append(endTime).append(" 23:59:59'");
			countSql.append(" and r.add_time <= '").append(endTime).append(" 23:59:59'");
		}
		if(Utility.isNotEmpty(userName)){
			sql.append(" and r.user_name like '%").append(userName).append("%'");
			countSql.append(" and r.user_name like '%").append(userName).append("%'");
		}
		if(Utility.isNotEmpty(userPhone)){
			sql.append(" and r.user_phone like '%").append(userPhone).append("%'");
			countSql.append(" and r.user_phone like '%").append(userPhone).append("%'");
		}
		if(Utility.isNotEmpty(guideName)){
			sql.append(" and p.real_name like '%").append(guideName).append("%'");
			countSql.append(" and p.real_name like '%").append(guideName).append("%'");
		}
		if(Utility.isNotEmpty(guidePhone)){
			sql.append(" and p.phone_no like '%").append(guidePhone).append("%'");
			countSql.append(" and p.phone_no like '%").append(guidePhone).append("%'");
		}
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
			sql.append(" ORDER BY addTime desc");
		}else{
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		map.put("sql", sql.toString());
		map.put("countSql", countSql.toString());
		return map;
	}
	
	
	/**
	 * 不同意退款
	 * @return
	 */
	@RequestMapping(params = "doNotAgree")
	@ResponseBody
	public AjaxJson doNotAgree(TRefundReturnEntity tRefundReturn, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "退款不同意成功";
		try{
			tRefundReturnService.doNotAgree(tRefundReturn);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "退款不同意失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 系统人员 进入退款
	 * @return
	 */
	@RequestMapping(params = "goToPay")
	public ModelAndView goToPay(TRefundReturnEntity tRefundReturn, HttpServletRequest req) {
		String type = req.getParameter("operateType");//0:拒绝退款，1：同意退款
		if (StringUtil.isNotEmpty(tRefundReturn.getId())) {
			tRefundReturn = this.tRefundReturnService.get(TRefundReturnEntity.class, tRefundReturn.getId());
			req.setAttribute("tRefundReturn", tRefundReturn);
		}
		if("0".equals(type)){
			return new ModelAndView("com/buss/refund/tRefundReturn-disAgree");
		}
		return new ModelAndView("com/buss/refund/tRefundReturn-pay");
	}
	
	/**
	 * 零售商退款(记录退款方式和流水号以及时间)
	 * @return
	 */
	@RequestMapping(params = "doPay")
	@ResponseBody
	public AjaxJson doPay(TRefundReturnEntity tRefundReturn, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> map = null;
		message = "退款成功"+(tRefundReturn.getRefundAmount()==null?0:tRefundReturn.getRefundAmount())+"元";
		try{
			map = tRefundReturnService.doPay(tRefundReturn);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "退款失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		if(map!=null){//发送通知插入月销量记录（类型为退款，金额为负数）
			sendMqMsg("tRefundReturn.getId()", map);
		}
		
		//退货成功自动回复
		if(Utility.isNotEmpty(tRefundReturn.getUserId())){
			TSUser user = ResourceUtil.getSessionUserName();
			TSUser user1 = systemService.get(TSUser.class, tRefundReturn.getUserId());
			if(GlobalConstants.USER_TYPE_04.equals(user1.getUserType())){
				Map<String, Object> params = new HashMap<String, Object>();
				try{
					String phone = "13662205744";//客服电话
					String orderNo = tRefundReturn.getOrderNo();
					BigDecimal refundAmount = tRefundReturn.getRefundAmount();
					String msg = "您好，您的订单"+orderNo+"退款申请已处理，商品退款："+refundAmount+"元，支付宝/财付通/银行卡原路退款预计1-7个工作日到账，请您留意最近的账户资金变化。如有疑问联系客服：（客服电话）："+phone+"【autoName】";
					params.put("retailerId", tRefundReturn.getRetailerId());
					params.put("phoneNo", tRefundReturn.getUserPhone());
					params.put("msg", msg);
					params.put("sendTime", Utility.getCurrentTimestamp());
					params.put("userType", user.getUserType());
					params.put("msgType", TSUserMessage.MSG_TYPE_6);//退货成功
					ResourceBundle rabbitmq = ResourceBundle.getBundle("rabbitmq");
					String key = rabbitmq.getString("send.order.msg.mq.queue.key");
					rabbitTemplate.convertAndSend(key, params);
				}catch(Exception ex){
					ex.printStackTrace();
					String msg = "====平台退款成功短信自动回复mq调用失败method:doPay===";
					String mqMsg = "";
					try {
						mqMsg = MAPPER.writeValueAsString(params);
					} catch (Exception e) {
						e.printStackTrace();
					}
					tMqErrorService.saveMqError(msg, "doPay", TMqErrorEntity.ORDER_TYPE_4, tRefundReturn.getId(), "send.order.msg.mq.queue.key",mqMsg);
				}
			}
		}	
		return j;
	}
	
	//发送mq消息通知计算退款
			private void sendMqMsg(String id,Map<String,Object> map) {
				try{
					ResourceBundle rabbitmq = ResourceBundle.getBundle("rabbitmq");
					String key = rabbitmq.getString("rfd.order.mq.key");
					rabbitTemplate.convertAndSend(key, map);
				}catch(Exception ex){
					ex.printStackTrace();
					LogUtil.info("====退款失败method:doPay===tRefundReturn："+id);
					TMqErrorEntity mqError = new TMqErrorEntity();
					mqError.setAddTime(Utility.getCurrentTimestamp());
					mqError.setErrorMsg("====退款失败method:doPay===");
					mqError.setMethod("doPay");
					mqError.setQueueName("item.updatePayByRefund");
					mqError.setOrderId(id);
					mqError.setOrderType(TMqErrorEntity.ORDER_TYPE_3);
					mqError.setMqMsg(JSONUtils.toJSONString(map));
					this.systemService.save(mqError);
				}
			}
		

	/**
	 * 退款退货编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TRefundReturnEntity tRefundReturn, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tRefundReturn.getId())) {
			tRefundReturn = tRefundReturnService.flushEntity(TRefundReturnEntity.class, tRefundReturn.getId());
			req.setAttribute("tRefundReturnPage", tRefundReturn);
		}
		return new ModelAndView("com/buss/refund/tRefundReturn-update");
	}
	
	/**
	 * 退款退货查看页面跳转
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(String id, HttpServletRequest req) {
		TRefundReturnEntity tRefundReturn = tRefundReturnService.flushEntity(TRefundReturnEntity.class, id);
		List<TOrderDetailEntity> tOrderDetailList = null;
		List<TOrderLogEntity> logList = null;
		if(Utility.isEmpty(tRefundReturn.getSubOrderNo())){//整单退
			tOrderDetailList = tOrderInfoService.findByProperty(TOrderDetailEntity.class, "orderId", tRefundReturn.getOrderId());
		}else{//单件退
			tOrderDetailList = new ArrayList<TOrderDetailEntity>();
			TOrderDetailEntity detail = this.systemService.findUniqueByProperty(TOrderDetailEntity.class, "subOrderNo", tRefundReturn.getSubOrderNo() );
//			detail.setGoodsAmount(tRefundReturn.getOrderAmount());
//			detail.setQuantity(tRefundReturn.getGoodsNum());
			tOrderDetailList.add(detail);
		}
		logList = this.systemService.findHql("from TOrderLogEntity where orderId=? order by logTime desc", id);
		//退款日志
		req.setAttribute("tOrderDetailList", tOrderDetailList);
		req.setAttribute("tRefundReturnPage", tRefundReturn);
		req.setAttribute("logList", logList);
		return new ModelAndView("com/buss/refund/tRefundDetail-view");
	}
	
	//首页提醒（待发货明细数量，待退款数量）
	 @RequestMapping(params = "getNoticeCount")
		@ResponseBody
		public AjaxJson getNoticeCount(){
			AjaxJson j = new AjaxJson();
			HashMap<String, Object> map  = new HashMap<String, Object>();
			TSUser user = ResourceUtil.getSessionUserName();
			Long fahuoCount = this.tOrderInfoService.getToBeFahuoCount(user);
			Long refundCount = this.tRefundReturnService.getToBeRefundCount(user);
			if(fahuoCount>99){
				map.put("fahuoCount", "99+");
			}else{
				map.put("fahuoCount", fahuoCount);
			}
			if(refundCount>99){
				map.put("refundCount", "99+");
			}else{
				map.put("refundCount", refundCount);
			}
			j.setObj(map);
			return j;
		}
	
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TRefundReturnEntity tRefundReturn,HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid,ModelMap modelMap) {
		Map<String,String> sqlMap = this.getQuerySql(request,dataGrid);
		String sql = sqlMap.get("sql");
		List<TRefundReturnEntity> resultList = systemService.findObjForJdbc(sql.toString(),TRefundReturnEntity.class);
		modelMap.put(NormalExcelConstants.FILE_NAME,"退款退货");
		modelMap.put(NormalExcelConstants.CLASS,TRefundReturnEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("退款退货列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
}
