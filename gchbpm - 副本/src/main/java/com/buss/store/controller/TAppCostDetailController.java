package com.buss.store.controller;
import com.buss.store.entity.TAppCostDetailEntity;
import com.buss.store.service.TAppCostDetailServiceI;
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
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 端口扣费明细
 * @author onlineGenerator
 * @date 2017-07-27 15:52:47
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tAppCostDetailController")
public class TAppCostDetailController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TAppCostDetailController.class);

	@Autowired
	private TAppCostDetailServiceI tAppCostDetailService;
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
	 * 端口扣费明细列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tAppCostDetail")
	public ModelAndView tAppCostDetail(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("rId", retailerId);
		return new ModelAndView("com/buss/store/tAppCostDetailList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TAppCostDetailEntity tAppCostDetail,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TAppCostDetailEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tAppCostDetail, request.getParameterMap());
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
		this.tAppCostDetailService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	
	
	/**
	 * 端口扣费明细编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TAppCostDetailEntity tAppCostDetail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tAppCostDetail.getId())) {
			tAppCostDetail = tAppCostDetailService.flushEntity(TAppCostDetailEntity.class, tAppCostDetail.getId());
			req.setAttribute("tAppCostDetailPage", tAppCostDetail);
		}
		return new ModelAndView("com/buss/store/tAppCostDetail-update");
	}
	
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TAppCostDetailEntity tAppCostDetail,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TAppCostDetailEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tAppCostDetail, request.getParameterMap());
		List<TAppCostDetailEntity> tAppCostDetails = this.tAppCostDetailService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"端口扣费明细");
		modelMap.put(NormalExcelConstants.CLASS,TAppCostDetailEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("端口扣费明细列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tAppCostDetails);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
}
