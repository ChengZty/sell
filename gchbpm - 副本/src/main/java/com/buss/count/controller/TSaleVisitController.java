package com.buss.count.controller;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.buss.count.entity.TSaleVisitEntity;
import com.buss.count.service.TSaleVisitServiceI;



/**   
 * @Title: Controller
 * @Description: 售后回访
 * @author onlineGenerator
 * @date 2017-06-19 16:05:24
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSaleVisitController")
public class TSaleVisitController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSaleVisitController.class);

	@Autowired
	private TSaleVisitServiceI tSaleVisitService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 售后回访列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tSaleVisit")
	public ModelAndView tSaleVisit(HttpServletRequest request) {
		return new ModelAndView("com/buss/count/tSaleVisitList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSaleVisitEntity tSaleVisit,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSaleVisitEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSaleVisit, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSaleVisitService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 售后回访编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSaleVisitEntity tSaleVisit, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSaleVisit.getId())) {
			tSaleVisit = tSaleVisitService.flushEntity(TSaleVisitEntity.class, tSaleVisit.getId());
			req.setAttribute("tSaleVisitPage", tSaleVisit);
		}
		return new ModelAndView("com/buss/count/tSaleVisit-update");
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSaleVisitEntity tSaleVisit,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSaleVisitEntity.class, dataGrid);
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){
			cq.eq("retailerId", retailerId);
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSaleVisit, request.getParameterMap());
		List<TSaleVisitEntity> tSaleVisits = this.tSaleVisitService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"售后回访");
		modelMap.put(NormalExcelConstants.CLASS,TSaleVisitEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("售后回访列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tSaleVisits);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSaleVisitEntity tSaleVisit,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "售后回访");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TSaleVisitEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	
}
