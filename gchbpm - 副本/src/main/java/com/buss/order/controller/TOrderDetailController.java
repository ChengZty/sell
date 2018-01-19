package com.buss.order.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.goods.entity.TGoodsEntity;
import com.buss.order.entity.TOrderDetailEntity;
import com.buss.order.entity.TOrderInfoEntity;
import com.buss.order.entity.TOrderLogEntity;
import com.buss.order.service.TOrderDetailServiceI;
import com.buss.order.service.TOrderInfoServiceI;
import com.buss.order.vo.TOrderDetailVo;
import com.buss.order.vo.TOrderFahuoVo;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 订单明细
 * @author onlineGenerator
 * @date 2016-03-15 17:45:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOrderDetailController")
public class TOrderDetailController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOrderDetailController.class);

	@Autowired
	private TOrderDetailServiceI tOrderDetailService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TOrderInfoServiceI tOrderInfoService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 订单明细列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String order_status = request.getParameter("order_status");
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("start_time", DateUtils.getFirstDayOfMonth(new Date()));
		request.setAttribute("end_time", DateUtils.getDataString(DateUtils.date_sdf));
		if(StringUtil.isNotEmpty(order_status)){
			request.setAttribute("order_status", order_status);
		}else{
			if(GlobalConstants.USER_TYPE_01.equals(user.getUserType())){//后台
				return new ModelAndView("com/buss/order/tOrderDetailList-all");
			}
			String retialerId = ResourceUtil.getRetailerId();
			if("2c92808655c8cf010155e2fabca90090".equals(retialerId)){
				request.setAttribute("isGCH", "Y");
			}
			return new ModelAndView("com/buss/order/tOrderDetailOfRetailerList-all");
		}
		//待发货
		if(com.buss.order.entity.TOrderDetailEntity.ORDER_STATUS_2.equals(order_status)
//				&&(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())||common.GlobalConstants.USER_TYPE_05.equals(user.getUserType()))
				){
			return new ModelAndView("com/buss/order/tOrderDetailsList-fahuo");
		}
		return new ModelAndView("com/buss/order/tOrderDetailList");
	}

	/**
	 * 订单明细列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tOrderDetailsMain")
	public ModelAndView tOrderDetailsMain(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
//		if(!GlobalConstants.USER_TYPE_01.equals(user.getUserType())){
			Long count = this.tOrderInfoService.getToBeFahuoCount(user);
			if(count>99){
				request.setAttribute("count", "99+");
			}else{
				request.setAttribute("count", count);
			}
//		}
		return new ModelAndView("com/buss/order/tOrderDetailsMain");
	}
	/**
	 * easyui查询订单明细信息列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TOrderDetailEntity tOrderDetail,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
		String retailerId = ResourceUtil.getRetailerId();
		String order_status = request.getParameter("order_status");//单独状态的tab页
		String orderStatus = request.getParameter("orderStatus");//全部订单明细下拉框
		String orderNo = request.getParameter("orderNo");//订单号
		String userPhone = request.getParameter("userPhone");//买家手机
		String query_payTime_begin = request.getParameter("payTime_begin");//支付时间
		String query_payTime_end = request.getParameter("payTime_end");
		String query_addTime_begin = request.getParameter("addTime_begin");//下单时间
		String query_addTime_end = request.getParameter("addTime_end");
		String deliveryType = request.getParameter("deliveryType");//发货类型
		String guidePhone = request.getParameter("guidePhone");//导购手机
		String guideName = request.getParameter("guideName");//导购姓名
		String storeName = request.getParameter("storeName");//导购所在店铺
		StringBuffer sql = this.getSqlHead();//获取sql查询的字段
		if(Utility.isEmpty(query_payTime_begin)){
			query_payTime_begin = DateUtils.getFirstDayOfMonth(new Date()).substring(0, 10);
		}
		if(Utility.isEmpty(query_payTime_end)){
			query_payTime_end = DateUtils.getDataString(DateUtils.date_sdf).substring(0, 10);
		}
		if(Utility.isEmpty(query_addTime_begin)){
			query_addTime_begin = DateUtils.getFirstDayOfMonth(new Date()).substring(0, 10);
		}
		if(Utility.isEmpty(query_addTime_end)){
			query_addTime_end = DateUtils.getDataString(DateUtils.date_sdf).substring(0, 10);
		}
		StringBuffer countSql = new StringBuffer("select count(1) FROM t_order_info i LEFT JOIN t_order_detail d ON d.order_id = i.id ");
		countSql.append("LEFT JOIN t_person p ON i.to_retailer_id = p.to_retailer_id and i.to_guide_id = p.user_id LEFT JOIN t_store s on p.store_id = s.id  WHERE i.status = 'A' and d.status = 'A' ");
		if(!Utility.isEmpty(retailerId)){
			sql.append(" and i.to_retailer_id ='").append(retailerId).append("' ");
			countSql.append(" and i.to_retailer_id ='").append(retailerId).append("' ");
			sql.append(" and p.to_retailer_id ='").append(retailerId).append("' ");
			countSql.append(" and p.to_retailer_id ='").append(retailerId).append("' ");
		}
		if(!Utility.isEmpty(storeName)){
			sql.append(" and s.name like '%").append(storeName).append("%' ");
			countSql.append(" and s.name like '%").append(storeName).append("%' ");
		}
		if(StringUtil.isEmpty(order_status)){//查看全部订单
			if(!StringUtil.isEmpty(orderStatus)){//下拉框订单状态
				sql.append(" and i.order_status ='").append(orderStatus).append("'");
				countSql.append(" and i.order_status ='").append(orderStatus).append("'");
			}
		}else{
			sql.append(" and i.order_status ='").append(order_status).append("'");
			countSql.append(" and i.order_status ='").append(order_status).append("'");
			if(TOrderDetailEntity.ORDER_STATUS_2.equals(order_status)){//待发货
				//排除已经申请退款的
				sql.append(" and i.refund_status ='").append(TOrderInfoEntity.REFUND_STATUS_0).append("' ");
				countSql.append(" and i.refund_status ='").append(TOrderInfoEntity.REFUND_STATUS_0).append("'");
//				//只查询后台发货的（自提和店铺发货的不用管）
//				sql.append(" and d.delivery_type ='").append(TOrderDetailEntity.DELIVERY_TYPE_1).append("' ");
//				countSql.append(" and d.delivery_type ='").append(TOrderDetailEntity.DELIVERY_TYPE_1).append("' ");
			}
		}
		if(!StringUtil.isEmpty(orderNo)){
			sql.append(" and i.order_no like '%").append(orderNo).append("%'");
			countSql.append(" and i.order_no like '%").append(orderNo).append("%'");
		}
		if(!StringUtil.isEmpty(userPhone)){
			sql.append(" and i.user_Phone like '%").append(userPhone).append("%'");
			countSql.append(" and i.user_Phone like '%").append(userPhone).append("%'");
		}
		if(!StringUtil.isEmpty(deliveryType)){
			sql.append(" and i.delivery_type = '").append(deliveryType).append("'");
			countSql.append(" and i.delivery_type = '").append(deliveryType).append("'");
		}
		if(!StringUtil.isEmpty(guideName)){
			sql.append(" and p.real_Name like '%").append(guideName).append("%'");
			countSql.append(" and p.real_Name like '%").append(guideName).append("%'");
		}
		if(!StringUtil.isEmpty(guidePhone)){
			sql.append(" and p.phone_no like '%").append(guidePhone).append("%'");
			countSql.append(" and p.phone_no like '%").append(guidePhone).append("%'");
		}
		
		//全部订单，待付款订单和取消订单用生成订单时间查询
		if("1".equals(order_status) || "9".equals(order_status) || StringUtil.isEmpty(order_status)){
			if(StringUtil.isEmpty(order_status)){  //全部订单查询的是创建时间
				query_addTime_begin = query_payTime_begin;
				query_addTime_end = query_payTime_end;
			}
			if(StringUtil.isNotEmpty(query_addTime_begin)){
				sql.append(" and i.create_date >='").append(query_addTime_begin).append(" 00:00:00'");
				countSql.append(" and i.create_date >='").append(query_addTime_begin).append(" 00:00:00'");
			}
			if(StringUtil.isNotEmpty(query_addTime_end)){
				sql.append(" and i.create_date <='").append(query_addTime_end).append(" 23:59:59'");
				countSql.append(" and i.create_date <='").append(query_addTime_end).append(" 23:59:59'");
			}
		}else {//其他订单使用支付时间查询
			if(StringUtil.isNotEmpty(query_payTime_begin)){
				sql.append(" and i.pay_time >='").append(query_payTime_begin).append(" 00:00:00'");
				countSql.append(" and i.pay_time >='").append(query_payTime_begin).append(" 00:00:00'");
			}
			if(StringUtil.isNotEmpty(query_payTime_end)){
				sql.append(" and i.pay_time <='").append(query_payTime_end).append(" 23:59:59'");
				countSql.append(" and i.pay_time <='").append(query_payTime_end).append(" 23:59:59'");
			}
		}
		
		String sort = dataGrid.getSort();
		if(StringUtil.isEmpty(sort)){
			sql.append(" order by orderNo desc");
		}else{
			sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder()).append(",orderNo desc");
		}
		
		int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
		List<TOrderDetailVo> list = new ArrayList<TOrderDetailVo>();
		if(total>0){
			list =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TOrderDetailVo.class);
		}
		sql=null;
		countSql=null;
		dataGrid.setResults(list);
		dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**	 获取查询字段	 */
	private StringBuffer getSqlHead(){
		StringBuffer sql = new StringBuffer("select d.id,i.post_code as flag,i.id as orderId,i.order_no as orderNo,d.create_date as createDate,d.update_date as updateDate,d.sub_order_no AS subOrderNo,")
		.append("CONCAT(d.goods_pic,'?imageView2/1/w/80/h/80') AS goodsPic,d.goods_name AS goodsName,d.goods_code as goodsCode,d.spec_info specInfo,d.price_original as originalPrice,d.current_price as currentPrice,d.price_now AS priceNow,d.quantity,d.fare,i.order_amount orderAmount,")
		.append("ifnull(i.ticket_preferential,0)+i.guide_privilege AS ticketPreferential,(case when i.pay_time is null then 0.00 else i.pay_amount end) payAmount,d.goods_Amount AS goodsAmount,ifnull(d.sales_price,0) AS salesPrice,ifnull(d.vip_money_pay,0) AS vipMoneyPay,")
		.append("(d.fare+d.goods_amount) as totalPay,i.user_Name as userName,i.to_guide_id as guideId,p.real_Name guideName,p.phone_no guidePhone,s.`name` storeName,i.to_retailer_id toRetailerId,")
		.append("i.reciver_name AS reciverName,i.reciver_phone AS reciverPhone,CONCAT(ifnull(i.reciver_province,''),ifnull(i.reciver_city,''),ifnull(i.reciver_region,''),ifnull(i.reciver_detail_info,'')) AS reciverDetailInfo,")
		.append("ifnull(i.order_message,'') AS orderMessage,ifnull(i.pay_method,'') AS payMethod,i.pay_time AS payTime,ifnull(i.user_Phone,'') AS userPhone,i.order_time AS addTime,i.order_status AS orderStatus,")
		.append("IFNULL(i.close_Time,'') AS closeTime,IFNULL(i.delivery_type,'') as deliveryType,IFNULL(i.delivery_name,'') deliveryName,IFNULL(i.delivery_no,'') deliveryNo")
		.append(" FROM t_order_info i LEFT JOIN t_order_detail d ON d.order_id = i.id LEFT JOIN t_person p ON i.to_retailer_id = p.to_retailer_id and i.to_guide_id = p.user_id LEFT JOIN t_store s on p.store_id = s.id ")
		.append("  WHERE i.status = 'A' and d.status = 'A'");
		return sql;
	};
	
	/**
	 * 批量删除订单明细
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "订单明细删除成功";
		try{
			for(String id:ids.split(",")){
				TOrderDetailEntity tOrderDetail = systemService.flushEntity(TOrderDetailEntity.class, 
				id
				);
				tOrderDetailService.delete(tOrderDetail);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "订单明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加订单明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TOrderDetailEntity tOrderDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "订单明细添加成功";
		try{
			tOrderDetailService.save(tOrderDetail);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单明细添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新订单明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TOrderDetailEntity tOrderDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "订单明细更新成功";
		TOrderDetailEntity t = tOrderDetailService.get(TOrderDetailEntity.class, tOrderDetail.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tOrderDetail, t);
			tOrderDetailService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "订单明细更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 订单明细新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOrderDetailEntity tOrderDetail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOrderDetail.getId())) {
			tOrderDetail = tOrderDetailService.flushEntity(TOrderDetailEntity.class, tOrderDetail.getId());
			req.setAttribute("tOrderDetailPage", tOrderDetail);
		}
		return new ModelAndView("com/buss/order/tOrderDetail-add");
	}
	
	
	
	 
	 
	/**
	 * 订单明细查看
	 * 
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TOrderDetailEntity tOrderDetail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOrderDetail.getId())) {
			tOrderDetail = tOrderDetailService.get(TOrderDetailEntity.class, tOrderDetail.getId());
		}else if(StringUtil.isNotEmpty(tOrderDetail.getSubOrderNo())){
			tOrderDetail = tOrderDetailService.findUniqueByProperty(TOrderDetailEntity.class, "subOrderNo", tOrderDetail.getSubOrderNo());
		}
		TGoodsEntity goods = this.systemService.get(TGoodsEntity.class, tOrderDetail.getGoodsId());
		TOrderInfoEntity tOrderInfo = this.systemService.get(TOrderInfoEntity.class, tOrderDetail.getOrderId());
		List<TOrderLogEntity> logList = this.systemService.findHql("from TOrderLogEntity where orderId=? order by logTime desc", tOrderDetail.getId());
		req.setAttribute("tOrderDetailPage", tOrderDetail);
		req.setAttribute("goods", goods);
		req.setAttribute("tOrderInfo", tOrderInfo);
		req.setAttribute("logList", logList);
		return new ModelAndView("com/buss/order/tOrderDetail-view");
	}
	
	 
	/**
	 * 订单明细编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOrderDetailEntity tOrderDetail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOrderDetail.getId())) {
			tOrderDetail = tOrderDetailService.flushEntity(TOrderDetailEntity.class, tOrderDetail.getId());
			req.setAttribute("tOrderDetailPage", tOrderDetail);
		}
		return new ModelAndView("com/buss/order/tOrderDetail-update");
	}
	
	/**
	 * 添加订单明细
	 * 20161112增加，把post_code（订单已不用该字段）设置为-1标识为测试订单（用于结算对账区分）
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doTest")
	@ResponseBody
	public AjaxJson doTest(String orderId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "设置为测试订单成功";
		try{
			systemService.updateBySqlString("update t_order_info set post_code = '-1' where id = '"+orderId+"'");
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "设置为测试订单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 导出订单明细excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportDetailsXls")
	public String exportDetailsXls(HttpServletRequest request,HttpServletResponse response	, DataGrid dataGrid,ModelMap modelMap) {
		StringBuffer sql = getSqlHead();
		String exlTitle = "订单明细信息";
		String isTest = request.getParameter("isTest");//1为测试订单，0为正常订单
		String retailerId = ResourceUtil.getRetailerId();
		String orderStatus = request.getParameter("orderStatus");//全部订单明细下拉框
		String orderNo = request.getParameter("orderNo");//订单号
		String userPhone = request.getParameter("userPhone");//买家手机
		String query_payTime_begin = request.getParameter("payTime_begin");//支付时间
		String query_payTime_end = request.getParameter("payTime_end");
		String guidePhone = request.getParameter("guidePhone");//导购手机
		String guideName = request.getParameter("guideName");//导购姓名
		if(!Utility.isEmpty(retailerId)){
			sql.append(" and i.to_retailer_Id = '").append(retailerId).append("' ");
		}
		if("1".equals(isTest)){
			sql.append(" and i.post_code = '-1'");
			exlTitle = "测试订单信息";
		}else{
//			sql.append(" and i.post_code <> '-1'");
			sql.append(" AND (i.post_code is null or i.post_code <> '-1' )");
		}
		if(!StringUtil.isEmpty(orderNo)){
			sql.append(" and i.order_no like '%").append(orderNo).append("%'");
		}
		if(!StringUtil.isEmpty(userPhone)){
			sql.append(" and i.user_Phone like '%").append(userPhone).append("%'");
		}
		if(!StringUtil.isEmpty(guideName)){
			sql.append(" and p.real_Name like '%").append(guideName).append("%'");
		}
		if(!StringUtil.isEmpty(guidePhone)){
			sql.append(" and p.phone_no like '%").append(guidePhone).append("%'");
		}
		if(StringUtil.isNotEmpty(query_payTime_begin)){
			sql.append(" and i.pay_time >='").append(query_payTime_begin).append(" 00:00:00'");
		}
		if(StringUtil.isNotEmpty(query_payTime_end)){
			sql.append(" and i.pay_time <='").append(query_payTime_end).append(" 23:59:59'");
		}
		if(!StringUtil.isEmpty(orderStatus)){
			sql.append(" and i.order_status = '").append(orderStatus).append("'");
		}else{
			//过滤预订单的记录
			sql.append(" and i.order_Status is not null");
		}
		sql.append(" order by i.order_no desc");
		List<TOrderDetailVo> list = systemService.findObjForJdbc(sql.toString(), TOrderDetailVo.class);
		if(list.size()>0){
			List<TSUser> retailers = this.systemService.findHql("from TSUser where user_type = ?", TSUser.USER_TYPE_02);
			if(retailers.size()>0){
				for(TOrderDetailVo vo:list){
					if(Utility.isNotEmpty(vo.getToRetailerId())){
						for(TSUser u : retailers){
							if(u.getId().equals(vo.getToRetailerId())){
								vo.setRetailerName(u.getRealName());
								break;
							}
						}
					}
				}
				
			}
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,exlTitle);
		modelMap.put(NormalExcelConstants.CLASS,TOrderDetailVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(exlTitle, "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		logger.info(retailerId+"导出订单明细excel");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 后台导出订单主单excel
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(params = "exportInfoXls")
	public String exportInfoXls(HttpServletRequest request,HttpServletResponse response	, DataGrid dataGrid,ModelMap modelMap) {
		StringBuffer sql = new StringBuffer("select i.id as id,i.order_no as orderNo,i.user_name as userName,i.user_Phone as userPhone,i.reciver_name as reciverName,i.reciver_phone as reciverPhone,CONCAT(i.reciver_province,i.reciver_city,i.reciver_region,i.reciver_detail_info) as reciverDetailInfo,")
			.append(" i.order_message as orderMessage,i.order_time as orderTime,i.pay_time as payTime,i.quantity_amount as quantityAmount,i.goods_amount as goodsAmount,i.fare_amount as fareAmount,i.order_amount as orderAmount,i.pay_method as payMethod,i.pay_no as payNo,")
			.append(" i.service_charge as serviceCharge,i.real_income as realIncome,i.pay_amount as payAmount,CASE i.vip_discount WHEN 1 THEN null else i.vip_discount END as vipDiscount,i.vip_money_pay as vipMoneyPay,i.ticket_preferential as ticketPreferential,i.order_status as orderStatus,")
			.append(" i.to_guide_id as toGuideId,u.realname from t_order_info i LEFT JOIN t_s_user u on i.to_guide_id = u.id")
			.append(" where 1=1 and i.status = 'A'");
		String exlTitle = "订单信息";
		String retailerId = ResourceUtil.getRetailerId();
		String isTest = request.getParameter("isTest");//1为测试订单，0为正常订单
		String orderStatus = request.getParameter("orderStatus");//全部订单明细下拉框
		String orderNo = request.getParameter("orderNo");//订单号
		String userPhone = request.getParameter("userPhone");//买家手机
		String query_payTime_begin = request.getParameter("payTime_begin");//支付时间
		String query_payTime_end = request.getParameter("payTime_end");
		String guidePhone = request.getParameter("guidePhone");//导购手机
		if(!Utility.isEmpty(retailerId)){
			sql.append(" and i.to_retailer_Id = '").append(retailerId).append("' ");
		}
		if("1".equals(isTest)){
			sql.append(" and i.post_code = '-1'");
			exlTitle = "测试订单信息";
		}else{
//			sql.append(" and i.post_code <> '-1'");
			sql.append(" AND (i.post_code is null or i.post_code <> '-1' )");
		}
		if(!StringUtil.isEmpty(orderNo)){
			sql.append(" and i.order_no like '%").append(orderNo).append("%'");
		}
		if(!StringUtil.isEmpty(userPhone)){
			sql.append(" and i.user_Phone like '%").append(userPhone).append("%'");
		}
		if(!StringUtil.isEmpty(guidePhone)){
			sql.append(" and u.username like '%").append(guidePhone).append("%'");
		}
		if(StringUtil.isNotEmpty(query_payTime_begin)){
			sql.append(" and i.pay_time >='").append(query_payTime_begin).append(" 00:00:00'");
		}
		if(StringUtil.isNotEmpty(query_payTime_end)){
			sql.append(" and i.pay_time <='").append(query_payTime_end).append(" 23:59:59'");
		}
		if(!StringUtil.isEmpty(orderStatus)){
			sql.append(" and i.order_status = '").append(orderStatus).append("'");
		}else{
			//过滤预订单的记录
			sql.append(" and i.order_Status is not null");
		}
		sql.append(" order by i.order_time desc,i.order_no desc");
		List<TOrderInfoVo> list = systemService.findObjForJdbc(sql.toString(), TOrderInfoVo.class);
//		List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(), null);
//		List<TOrderInfoVo> list = new ArrayList<TOrderInfoVo>();
//		if(!Utility.isEmpty(resultList)){
//			for (Map<String, Object> map : resultList) {
//				TOrderInfoVo vo = new TOrderInfoVo();
//				try 
//				{  
//		            BeanInfo beanInfo = Introspector.getBeanInfo(vo.getClass());  
//		            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
//		            for (PropertyDescriptor property : propertyDescriptors) {  
//		                String key = property.getName();  
//		                if (map.containsKey(key)) {  
//		                    Object value = map.get(key);  
//		                    Method setter = property.getWriteMethod();  
//		                    setter.invoke(vo, value);  
//		                }  
//		            }  
//		        } catch (Exception e) {  
//		        	LogUtil.info("TOrderInfoVo transMap2Bean Error " + e);  
//		        }
//		        list.add(vo);
//			}
//		}
		modelMap.put(NormalExcelConstants.FILE_NAME,exlTitle);
		modelMap.put(NormalExcelConstants.CLASS,TOrderInfoVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(exlTitle, "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		logger.info(retailerId+"导出订单主表excel");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	*/
	/**
	 * 零售商导出发货明细excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsForFahuo")
	public String exportXlsForFahuo(HttpServletRequest request,HttpServletResponse response	, DataGrid dataGrid,ModelMap modelMap) {
		StringBuffer sql = new StringBuffer("select i.order_no as orderNo,i.reciver_name as reciverName,i.reciver_phone as reciverPhone,CONCAT(ifnull(i.reciver_province,''),ifnull(i.reciver_city,''),ifnull(i.reciver_region,''),ifnull(i.reciver_detail_info,'')) as reciverDetailInfo,")
			.append(" i.user_Phone as userPhone,i.order_message as orderMessage,i.pay_time as payTime,d.sub_order_no as subOrderNo,i.order_time as addTime,d.goods_name as goodsName,d.goods_code as goodsCode,d.Spec_info as specInfo,d.quantity")
			.append(" from t_order_info i LEFT JOIN t_order_detail d on i.id = d.order_id ")
			.append(" LEFT JOIN t_s_user u on i.to_guide_id = u.id ")
			.append(" where 1=1 and i.status = 'A'");
		String retailerId = ResourceUtil.getRetailerId();
		String orderNo = request.getParameter("orderNo");//订单号
		String userPhone = request.getParameter("userPhone");//买家手机
		String query_payTime_begin = request.getParameter("payTime_begin");//支付时间
		String query_payTime_end = request.getParameter("payTime_end");
		String deliveryType = request.getParameter("deliveryType");//发货类型
		String guidePhone = request.getParameter("guidePhone");//导购手机
		if(!Utility.isEmpty(retailerId)){
			sql.append(" and i.to_retailer_Id = '").append(retailerId).append("'");
		}
		//待发货
		sql.append(" and i.order_status = '").append(TOrderDetailEntity.ORDER_STATUS_2).append("'");
		//排除已经申请退款的
		sql.append(" and i.refund_status ='").append(TOrderInfoEntity.REFUND_STATUS_0).append("' ");
		if(!StringUtil.isEmpty(orderNo)){
			sql.append(" and i.order_no like '%").append(orderNo).append("%'");
		}
		if(!StringUtil.isEmpty(userPhone)){
			sql.append(" and i.user_Phone like '%").append(userPhone).append("%'");
		}
		if(!StringUtil.isEmpty(deliveryType)){
			sql.append(" and i.delivery_type = '").append(deliveryType).append("'");
		}
		if(!StringUtil.isEmpty(guidePhone)){
			sql.append(" and u.username like '%").append(guidePhone).append("%'");
		}
		if(StringUtil.isNotEmpty(query_payTime_begin)){
			sql.append(" and i.pay_time >='").append(query_payTime_begin).append(" 00:00:00'");
		}
		if(StringUtil.isNotEmpty(query_payTime_end)){
			sql.append(" and i.pay_time <='").append(query_payTime_end).append(" 23:59:59'");
		}
		sql.append(" order by orderNo desc");
		List<TOrderFahuoVo> list = systemService.findObjForJdbc(sql.toString(), TOrderFahuoVo.class);
//		List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(), null);
//		List<TOrderFahuoVo> list = new ArrayList<TOrderFahuoVo>();
//		if(!Utility.isEmpty(resultList)){
//			for (Map<String, Object> map : resultList) {
//				TOrderFahuoVo vo = new TOrderFahuoVo();
//				try 
//				{  
//		            BeanInfo beanInfo = Introspector.getBeanInfo(vo.getClass());  
//		            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
//		            for (PropertyDescriptor property : propertyDescriptors) {  
//		                String key = property.getName();  
//		                if (map.containsKey(key)) {  
//		                    Object value = map.get(key);  
//		                    Method setter = property.getWriteMethod();  
//		                    setter.invoke(vo, value);  
//		                }  
//		            }  
//		        } catch (Exception e) {  
//		        	LogUtil.info("TOrderFahuoVo transMap2Bean Error " + e);  
//		        }
//		        list.add(vo);
//			}
//		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"发货列表");
		modelMap.put(NormalExcelConstants.CLASS,TOrderFahuoVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("发货列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 物流回写
	 * @return
	 */
	/*@RequestMapping(params = "callback")
	public void callback(HttpServletRequest request,HttpServletResponse response) {
		NoticeResponse resp = new NoticeResponse();
		resp.setResult(false);
		resp.setReturnCode("500");
		resp.setMessage("物流保存失败");
		try {
			String param = request.getParameter("param");
			NoticeRequest nReq = JacksonHelper.fromJSON(param,NoticeRequest.class);
			Result result = nReq.getLastResult();
			String deliveryNo = result.getNu();
			String deliveryNew = "Y";
			String deliveryStatus = result.getState();
			String delivery_msg = Result.getBackState(deliveryStatus);
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE t_order_detail t SET t.delivery_new ='")
			   .append(deliveryNew).append("',")
			   .append(" t.delivery_status ='").append(deliveryStatus)
			   .append("',").append(" t.delivery_update_time ='")
			   .append(DateUtils.getDate("yyyy-MM-dd HH:mm:ss")).append("',")
			   .append(" t.delivery_msg = '").append(delivery_msg).append("' ")
			   .append(" where t.delivery_no ='").append(deliveryNo)
			   .append("'  and t.`status` = 'A'");
			this.systemService.updateBySqlString(sql.toString());
			sql = null;
			resp.setResult(true);
			resp.setReturnCode("200");
			resp.setMessage("成功");
			response.getWriter().print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
		} catch (Exception e) {
			resp.setMessage("物流保存失败" + e.getMessage());
			try 
			{
				response.getWriter().print(JacksonHelper.toJSON(resp));//保存失败，服务端等30分钟会重复推送。
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}*/




	

	

 	
 	
 	
}