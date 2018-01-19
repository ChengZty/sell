package com.buss.bill.controller;
import com.buss.bill.entity.TCloudBillEntity;
import com.buss.bill.service.TCloudBillServiceI;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller
 * @Description: 云商结算明细
 * @author onlineGenerator
 * @date 2016-05-20 16:50:59
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tCloudBillController")
public class TCloudBillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TCloudBillController.class);

	@Autowired
	private TCloudBillServiceI tCloudBillService;
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
	 * 云商结算明细列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/bill/tCloudBillList");
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
	public void datagrid(TCloudBillEntity tCloudBill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TCloudBillEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tCloudBill, request.getParameterMap());
		StringBuffer sqlSum = new StringBuffer("SELECT IFNULL(SUM(money),0) as money,IFNULL(SUM(cloud_money),0) as cloudMoney from t_cloud_bill where 1=1");
		try{
		//自定义追加查询条件
//			TSUser user = ResourceUtil.getSessionUserName();
			String retailerId = ResourceUtil.getRetailerId();
//			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
//				retailerId = user.getId();
//			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
//				user = this.systemService.get(TSUser.class, user.getRetailerId());
//				retailerId = user.getId();
//			}
			if(!StringUtil.isEmpty(retailerId)){
//				if(TSUser.RETAILER_TYPE_REAL.equals(user.getRetailerType())){//零售商
//					cq.eq("storeId", retailerId);
//					cq.add(Restrictions.or(Restrictions.eq("toStoreGoodsId", retailerId),Restrictions.isNull("toStoreGoodsId")));
//					sqlSum.append(" and store_Id='").append(retailerId).append("' and (to_Store_Goods_Id = '").append(retailerId).append("' or to_Store_Goods_Id is null)");
//				}else if(TSUser.RETAILER_TYPE_GOODS.equals(user.getRetailerType())){//云商
					cq.eq("storeId", retailerId);
					sqlSum.append(" and store_Id='").append(retailerId).append("'");
//				}
			}
			String addTime_begin = request.getParameter("addTime_begin");
			String addTime_end = request.getParameter("addTime_end");
			String orderNo = request.getParameter("orderNo");
			String subOrderNo = request.getParameter("subOrderNo");
			String businessDate_begin = request.getParameter("businessDate_begin");
			String businessDate_end = request.getParameter("businessDate_end");
			if(!StringUtil.isEmpty(addTime_begin)){
				cq.ge("addTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(addTime_begin+" 00:00:00"));
				sqlSum.append(" and add_Time>='").append(addTime_begin).append(" 00:00:00' ");
			}
			if(!StringUtil.isEmpty(addTime_end)){
				cq.le("addTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(addTime_end+" 23:59:59"));
				sqlSum.append(" and add_Time<='").append(addTime_end).append(" 23:59:59' ");
			}
			if(!StringUtil.isEmpty(orderNo)){
				cq.like("orderNo", "%"+orderNo+"%");
				sqlSum.append(" and order_No like '%").append(orderNo).append("%'");
			}
			if(!StringUtil.isEmpty(subOrderNo)){
				cq.like("subOrderNo", "%"+subOrderNo+"%");
				sqlSum.append(" and sub_Order_No like '%").append(subOrderNo).append("%'");
			}
			if(!StringUtil.isEmpty(businessDate_begin)){
				cq.ge("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_begin+" 00:00:00"));
				sqlSum.append(" and business_Date>='").append(businessDate_begin).append(" 00:00:00' ");
			}
			if(!StringUtil.isEmpty(businessDate_end)){
				cq.le("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_end+" 23:59:59"));
				sqlSum.append(" and business_Date<='").append(businessDate_end).append(" 23:59:59' ");
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tCloudBillService.getDataGridReturn(cq, true);
		Map<String, Object>  map  = this.systemService.findOneForJdbc(sqlSum.toString(), null);
		if(null != map && map.size()>0){
			dataGrid.setFooter("subOrderNo:合计,money:"+map.get("money")+",cloudMoney:"+map.get("cloudMoney"));
		}else{
			dataGrid.setFooter("subOrderNo:合计,money:0,cloudMoney:0");
		}
		TagUtil.datagrid(response, dataGrid);
	}


	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TCloudBillEntity tCloudBill,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TCloudBillEntity.class, dataGrid);
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tCloudBill, request.getParameterMap());
		try{
			//自定义追加查询条件
			TSUser user = ResourceUtil.getSessionUserName();
			String retailerId = "";
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
				retailerId = user.getId();
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				user = this.systemService.get(TSUser.class, user.getRetailerId());
				retailerId = user.getId();
			}
			if(!StringUtil.isEmpty(retailerId)){
				if(TSUser.RETAILER_TYPE_REAL.equals(user.getRetailerType())){//零售商
					cq.eq("storeId", retailerId);
					cq.add(Restrictions.or(Restrictions.eq("toStoreGoodsId", retailerId),Restrictions.isNull("toStoreGoodsId")));
				}else if(TSUser.RETAILER_TYPE_GOODS.equals(user.getRetailerType())){//云商
					cq.eq("toStoreGoodsId", retailerId);
				}
			}
			String addTime_begin = request.getParameter("addTime_begin");
			String addTime_end = request.getParameter("addTime_end");
			String orderNo = request.getParameter("orderNo");
			String subOrderNo = request.getParameter("subOrderNo");
			String businessDate_begin = request.getParameter("businessDate_begin");
			String businessDate_end = request.getParameter("businessDate_end");
			if(!StringUtil.isEmpty(addTime_begin)){
				cq.ge("addTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(addTime_begin+" 00:00:00"));
			}
			if(!StringUtil.isEmpty(addTime_end)){
				cq.le("addTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(addTime_end+" 23:59:59"));
			}
			if(!StringUtil.isEmpty(orderNo)){
				cq.eq("orderNo", orderNo);
			}
			if(!StringUtil.isEmpty(subOrderNo)){
				cq.eq("subOrderNo", subOrderNo);
			}
			if(!StringUtil.isEmpty(businessDate_begin)){
				cq.ge("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_begin+" 00:00:00"));
			}
			if(!StringUtil.isEmpty(businessDate_end)){
				cq.le("businessDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(businessDate_end+" 23:59:59"));
			}
			dataGrid.setSort("addTime");
			dataGrid.setOrder(SortDirection.desc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		List<TCloudBillEntity> tCloudBills = this.tCloudBillService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"零售商账单");
		modelMap.put(NormalExcelConstants.CLASS,TCloudBillEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("零售商账单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tCloudBills);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
}
