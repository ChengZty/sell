package com.buss.bill.controller;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.buss.bill.entity.TFinBillEntity;
import com.buss.bill.service.TFinBillServiceI;
import com.buss.order.vo.TOrderDetailVo;



/**   
 * @Title: Controller
 * @Description: 财务结算表
 * @author onlineGenerator
 * @date 2016-04-22 14:19:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tFinBillController")
public class TFinBillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TFinBillController.class);

	@Autowired
	private TFinBillServiceI tFinBillService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 财务结算表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/bill/tFinBillList");
	}
	
	
	/**
	 * 零售商财务结算表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "storeList")
	public ModelAndView storeList(HttpServletRequest request) {
		request.setAttribute("retailerId", ResourceUtil.getRetailerId());
		return new ModelAndView("com/buss/bill/tFinBillStoreList");
	}
	
	

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TFinBillEntity tFinBill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TFinBillEntity.class, dataGrid);
		StringBuffer countSql = new StringBuffer("select IFNULL(sum(t.order_amount),0) as orderAmount,IFNULL(sum(t.pay_amount),0) as payAmount,IFNULL(sum(t.cust_amount),0) as custAmount,")
						.append("IFNULL(sum(t.quantity_amount),0) as quantityAmount ,IFNULL(sum(t.ticket_amount),0) as ticketAmount,IFNULL(sum(t.guide_privilege_amount),0) as guidePrivilegeAmount")
						.append(" from  t_fin_bill t where 1=1");
		//查询条件组装器
		//org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinBill, request.getParameterMap());
		try{
		//自定义追加查询条件
			String billNo = request.getParameter("billNo");
			String orderNo = request.getParameter("orderNo");
			String businessDate_begin = request.getParameter("businessDate_begin");
			String businessDate_end = request.getParameter("businessDate_end");
			String businessType = request.getParameter("businessType");
			String rId = request.getParameter("rId");//零售商id
			String guideId = request.getParameter("guideId");
			String guideName = request.getParameter("guideName");
			String storeName = request.getParameter("storeName");
			if(StringUtil.isNotEmpty(billNo)){
				cq.like("billNo", "%"+billNo+"%");
				countSql.append(" and bill_No like '%").append(billNo).append("%' ");
			}
			if(StringUtil.isNotEmpty(orderNo)){
				cq.like("orderNo", "%"+orderNo+"%");
				countSql.append(" and order_no like '%").append(orderNo).append("%' ");
			}
			if(StringUtil.isNotEmpty(storeName)){
				cq.like("storeName", "%"+storeName+"%");
				countSql.append(" and store_name like '%").append(storeName).append("%' ");
			}
			if(StringUtil.isNotEmpty(businessDate_begin)){
				cq.ge("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_begin+" 00:00:00"));
				countSql.append(" and business_Date>='").append(businessDate_begin).append(" 00:00:00' ");
			}
			if(StringUtil.isNotEmpty(businessDate_end)){
				cq.le("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_end+" 23:59:59"));
				countSql.append(" and business_Date<='").append(businessDate_end).append(" 23:59:59' ");
			}
			if(StringUtil.isNotEmpty(businessType)){
				cq.eq("businessType", businessType);
				countSql.append(" and business_type='").append(businessType).append("' ");
			}
			if(StringUtil.isNotEmpty(guideId)){
				cq.eq("guideId", guideId);
				countSql.append(" and guide_id='").append(guideId).append("' ");
			}
			if(StringUtil.isNotEmpty(guideName)){
				cq.like("guideName", "%"+guideName+"%");
				countSql.append(" and guide_name like '%").append(guideName).append("%' ");
			}
			if(StringUtil.isNotEmpty(rId)){
				cq.eq("retailerId", rId);
				countSql.append(" and retailer_id='").append(rId).append("' ");
			}
			
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tFinBillService.getDataGridReturn(cq, true);
		Map<String, Object>  map  = this.systemService.findOneForJdbc(countSql.toString(), null);
		if(null != map && map.size()>0){
			dataGrid.setFooter("businessType:合计,orderAmount:"+map.get("orderAmount")+",payAmount:"+map.get("payAmount")
					+",custAmount:"+map.get("custAmount")+",quantityAmount:"+map.get("quantityAmount")
					+",ticketAmount:"+map.get("ticketAmount")+",guidePrivilegeAmount:"+map.get("guidePrivilegeAmount"));
		}else{
			dataGrid.setFooter("businessType:合计,orderAmount:0,payAmount:0,custAmount:0,quantityAmount:0,ticketAmount:0,guidePrivilegeAmount:0");
		}
		TagUtil.datagrid(response, dataGrid);
	}

	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	/*@RequestMapping(params = "storeDatagrid")
	public void storeDatagrid(TFinBillEntity tFinBill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TFinBillEntity.class, dataGrid);
		StringBuffer countSql = new StringBuffer("select IFNULL(sum(t.cust_amount),0) as custAmount,IFNULL(sum(t.store_amount),0) as storeAmount ,IFNULL(sum(t.guide_amount),0) as guideAmount,IFNULL(sum(t.system_amount),0) as systemAmount,IFNULL(sum(t.helper_amount),0) as helperAmount from  t_fin_bill t where 1=1");
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			String retailerId = "";
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
				retailerId = user.getId();
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				retailerId = user.getRetailerId();
			}
			String query_addTime_begin = request.getParameter("addTime_begin");
			String query_addTime_end = request.getParameter("addTime_end");
			String orderNo = request.getParameter("orderNo");
			String businessType = request.getParameter("businessType");
			String guideId = request.getParameter("guideId");
			if(StringUtil.isNotEmpty(query_addTime_begin)){
				cq.ge("addTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_addTime_begin));
				countSql.append(" and add_time>='").append(query_addTime_begin).append(" 00:00:00' ");
			}
			if(StringUtil.isNotEmpty(query_addTime_end)){
				cq.le("addTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_addTime_end));
				countSql.append(" and add_time<='").append(query_addTime_end).append(" 23:59:59' ");
			}
			if(StringUtil.isNotEmpty(orderNo)){
				cq.like("orderNo", "%"+orderNo+"%");
				countSql.append(" and order_no like '%").append(orderNo).append("%' ");
			}
			if(StringUtil.isNotEmpty(businessType)){
				cq.eq("businessType", businessType);
				countSql.append(" and business_type='").append(businessType).append("' ");
			}
			if(StringUtil.isNotEmpty(guideId)){
				cq.eq("guideId", guideId);
				countSql.append(" and guide_id='").append(guideId).append("' ");
			}
			cq.eq("storeId", retailerId);
//			cq.or(Restrictions.eq("storeId", retailerId), Restrictions.eq("goodsStoreId", retailerId));
//			countSql.append(" and (store_id='").append(retailerId).append("' or goods_store_id ='").append(retailerId).append("')");
			countSql.append(" and store_id='").append(retailerId).append("' ");
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tFinBillService.getDataGridReturn(cq, true);
		Map<String, Object>  map  = this.systemService.findOneForJdbc(countSql.toString(), null);
		if(null != map && map.size()>0){
			dataGrid.setFooter("frozenAmount:"+map.get("frozenAmount")+",custAmount:"+map.get("custAmount")
					+",storeAmount:"+map.get("storeAmount")+",guideAmount:"+map.get("guideAmount")
					+",systemAmount:"+map.get("systemAmount")
					+",helperAmount:"+map.get("helperAmount")
					+",orderAmount:合计");
		}else{
			dataGrid.setFooter("frozenAmount:0,custAmount:0"
					+",storeAmount:0,guideAmount:0"
					+",systemAmount:0"
					+",helperAmount:0"
					+",orderAmount:合计");
		}
		TagUtil.datagrid(response, dataGrid);
	}*/
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TFinBillEntity tFinBill,HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFinBillEntity.class, dataGrid);
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinBill, request.getParameterMap());
		try{
			String billNo = request.getParameter("billNo");
			String orderNo = request.getParameter("orderNo");
			String businessDate_begin = request.getParameter("businessDate_begin");
			String businessDate_end = request.getParameter("businessDate_end");
			String businessType = request.getParameter("businessType");
			String rId = request.getParameter("rId");//零售商id
			String guideId = request.getParameter("guideId");
			String guideName = request.getParameter("guideName");
			String storeName = request.getParameter("storeName");
			if(StringUtil.isNotEmpty(billNo)){
				cq.like("billNo", "%"+billNo+"%");
			}
			if(StringUtil.isNotEmpty(orderNo)){
				cq.like("orderNo", "%"+orderNo+"%");
			}
			if(StringUtil.isNotEmpty(storeName)){
				cq.like("storeName", "%"+storeName+"%");
			}
			if(StringUtil.isNotEmpty(businessDate_begin)){
				cq.ge("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_begin+" 00:00:00"));
			}
			if(StringUtil.isNotEmpty(businessDate_end)){
				cq.le("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_end+" 23:59:59"));
			}
			if(StringUtil.isNotEmpty(businessType)){
				cq.eq("businessType", businessType);
			}
			if(StringUtil.isNotEmpty(guideId)){
				cq.eq("guideId", guideId);
			}
			if(StringUtil.isNotEmpty(guideName)){
				cq.like("guideName", "%"+guideName+"%");
			}
			if(StringUtil.isNotEmpty(rId)){
				cq.eq("retailerId", rId);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cq.add();
		List<TFinBillEntity> tFinBills = this.tFinBillService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"财务账单");
		modelMap.put(NormalExcelConstants.CLASS,TFinBillEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("财务账单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tFinBills);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 导出导购excel
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(params = "exportGuideXls")
	public String exportGuideXls(TFinBillEntity tFinBill,HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFinBillEntity.class, dataGrid);
		StringBuffer sql = new StringBuffer("select * from t_fin_bill f where 1=1 limit 10");
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinBill, request.getParameterMap());
		try{
			String billNo = request.getParameter("billNo");
			String orderNo = request.getParameter("orderNo");
			String businessDate_begin = request.getParameter("businessDate_begin");
			String businessDate_end = request.getParameter("businessDate_end");
			String businessType = request.getParameter("businessType");
			String rId = request.getParameter("rId");
			String guideId = request.getParameter("guideId");
			
			if(StringUtil.isNotEmpty(billNo)){
				cq.eq("billNo", billNo);
			}
			if(StringUtil.isNotEmpty(orderNo)){
				cq.eq("orderNo", orderNo);
			}
			if(StringUtil.isNotEmpty(businessDate_begin)){
				cq.ge("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_begin+" 00:00:00"));
			}
			if(StringUtil.isNotEmpty(businessDate_end)){
				cq.le("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_end+" 23:59:59"));
			}
			if(StringUtil.isNotEmpty(businessType)){
				cq.eq("businessType", businessType);
			}
			if(StringUtil.isNotEmpty(guideId)){
				cq.eq("guideId", guideId);
			}
			if(StringUtil.isNotEmpty(rId)){
				cq.eq("retailerId", rId);
			}
			dataGrid.setSort("addTime");
			dataGrid.setOrder(SortDirection.desc);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(), null);
		List<TFinBillEntity> list = new ArrayList<TFinBillEntity>();
		if(!Utility.isEmpty(resultList)){
			for (Map<String, Object> map : resultList) {
				TFinBillEntity vo = new TFinBillEntity();
				try 
				{  
		            BeanInfo beanInfo = Introspector.getBeanInfo(vo.getClass());  
		            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
		            for (PropertyDescriptor property : propertyDescriptors) {  
		                String key = property.getName();  
		                if (map.containsKey(key)) {  
		                    Object value = map.get(key);  
		                    Method setter = property.getWriteMethod();  
		                    setter.invoke(vo, value);  
		                }  
		            }  
		        } catch (Exception e) {  
		        	LogUtil.info("TOrderDetailVo transMap2Bean Error " + e);  
		        }
		        list.add(vo);
			}
		}
//		List<TFinBillEntity> tFinBills = this.tFinBillService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"财务账单");
		modelMap.put(NormalExcelConstants.CLASS,TFinBillEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("财务账单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}*/
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(params = "storExportXls")
	public String storExportXls(TFinBillEntity tFinBill,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFinBillEntity.class, dataGrid);
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinBill, request.getParameterMap());
		TSUser user = ResourceUtil.getSessionUserName();
		String retailerId = ResourceUtil.getRetailerId();
		String query_addTime_begin = request.getParameter("addTime_begin");
		String query_addTime_end = request.getParameter("addTime_end");
		String orderNo = request.getParameter("orderNo");
		String businessType = request.getParameter("businessType");
		String guideId = request.getParameter("guideId");
		try {
			if(StringUtil.isNotEmpty(query_addTime_begin)){
				cq.ge("addTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_addTime_begin));
			}
			if(StringUtil.isNotEmpty(query_addTime_end)){
				cq.le("addTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_addTime_end));
			}
			if(StringUtil.isNotEmpty(orderNo)){
				cq.eq("orderNo", orderNo);
			}
			if(StringUtil.isNotEmpty(businessType)){
				cq.eq("businessType", businessType);
			}
			if(StringUtil.isNotEmpty(guideId)){
				cq.eq("guideId", guideId);
			}
			cq.eq("retailerId", retailerId);
			cq.add();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TFinBillEntity> tFinBills = this.tFinBillService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"财务结算表");
		modelMap.put(NormalExcelConstants.CLASS,TFinBillEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("财务结算表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tFinBills);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}*/
	
	
}
