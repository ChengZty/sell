package com.buss.user.controller;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.user.entity.TFocusCustomerMiddleEntity;
import com.buss.user.service.TFocusCustomerMiddleServiceI;



/**   
 * @Title: Controller
 * @Description: 关注顾客
 * @author onlineGenerator
 * @date 2016-03-22 16:44:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tFocusCustomerMiddleController")
public class TFocusCustomerMiddleController extends BaseController {
	private static final Logger logger = Logger.getLogger(TFocusCustomerMiddleController.class);

	@Autowired
	private TFocusCustomerMiddleServiceI tFocusCustomerMiddleService;
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
	 * 临时顾客管理（ 选择顾客页面跳转）
	 * @return
	 */
	@RequestMapping(params = "listOfNewSelect")
	public ModelAndView listOfNewSelect(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailer_Id", retailerId);
		String retailerEdition = ResourceUtil.getRetailerEdition();
		request.setAttribute("edition", retailerEdition);
		//批次号
		String batchNo = request.getParameter("batchNo");
		request.setAttribute("batchNo", batchNo);
		return new ModelAndView("com/buss/sms/tFocusCustomerListOfSelect");
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
	public void datagrid(TFocusCustomerMiddleEntity tFocusCustomer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TFocusCustomerMiddleEntity.class, dataGrid);
		String phoneNo = request.getParameter("phoneNo");//手机号码
		//不能修改paramMap的值，可以修改新的对象的值
		Map<String,String[]> newParamMap = new HashMap<String, String[]>();
		Map<String,String[]> paramMap = request.getParameterMap();
		newParamMap.putAll(paramMap);
		//拼接电话号码的sql
		StringBuilder sqlStrSb = new StringBuilder();
		if(Utility.isNotEmpty(phoneNo)){
			newParamMap.remove("phoneNo");//删除原来的条件
			tFocusCustomer.setPhoneNo(null);//设为null则后面不会添加
			sqlStrSb.append(" phone_no like '").append(phoneNo).append("%'");//按手机号码前几位进行查询
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFocusCustomer, newParamMap,sqlStrSb.toString());
		String rId = ResourceUtil.getRetailerId();
		String batchNo = request.getParameter("batch_No");
		String types = request.getParameter("types");//顾客类型( 1 ：无反应顾客 2：点击顾客 3：交易顾客) 例如：1,2,3,
		try{
		//自定义追加查询条件
			if(Utility.isNotEmpty(rId)){
				cq.eq("toRetailerId", rId);
			}
			cq.eq("batchNo", batchNo);
			if(Utility.isNotEmpty(types)){
				cq.in("type", types.split(","));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tFocusCustomerMiddleService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
		
	}

	 /**
	 * 按筛选条件添加顾客
	 * 
	 * @return
	 */
	 @RequestMapping(params = "addCst")
	@ResponseBody
	public AjaxJson addCst(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "新增顾客";
		try{
			String rId = ResourceUtil.getRetailerId();
			int size = tFocusCustomerMiddleService.updateAndAddTFocusCustomerMiddle(request,rId);
			message += (size + "条");
		}catch(Exception e){
			e.printStackTrace();
			message = "按筛选条件添加顾客失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	 
	
	 /**
	 * 按筛选条件删除顾客
	 * 
	 * @return
	 */
	 @RequestMapping(params = "delCst")
	@ResponseBody
	public AjaxJson delCst(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "删除顾客";
		try{
			String rId = ResourceUtil.getRetailerId();
			int size = tFocusCustomerMiddleService.updateAndDelTFocusCustomerMiddle(request,rId);
			message += (size + "条");
		}catch(Exception e){
			e.printStackTrace();
			message = "按筛选条件删除顾客失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	 /**
	 * 批量删除关注顾客
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	 @ResponseBody
	 public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "批量删除顾客";
		try{
			int size = tFocusCustomerMiddleService.updateAndDelCst(ids);
			message += (size + "条");
		}catch(Exception e){
			e.printStackTrace();
			message = "批量删除顾客失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	 }
 
	/**
	 * 关注顾客查看页面跳转
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TFocusCustomerMiddleEntity tFocusCustomer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tFocusCustomer.getId())) {
			tFocusCustomer = tFocusCustomerMiddleService.flushEntity(TFocusCustomerMiddleEntity.class, tFocusCustomer.getId());
			req.setAttribute("tFocusCustomerPage", tFocusCustomer);
		}
		return new ModelAndView("com/buss/user/tFocusCustomerMiddle-view");
	}
	
	 /**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TFocusCustomerMiddleEntity tFocusCustomer,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFocusCustomerMiddleEntity.class, dataGrid);
		String batchNo = request.getParameter("batchNo");
		String rId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(rId)){
			cq.eq("toRetailerId", rId);
		}
		cq.eq("batchNo", batchNo);//批次号
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFocusCustomer, request.getParameterMap());
		List<TFocusCustomerMiddleEntity> tFocusCustomers = this.tFocusCustomerMiddleService.getListByCriteriaQuery(cq,true);
		modelMap.put(NormalExcelConstants.FILE_NAME,"潜在顾客");
		modelMap.put(NormalExcelConstants.CLASS,TFocusCustomerMiddleEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("潜在顾客表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tFocusCustomers);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
	
	
	
	
	
	
	
	
}

