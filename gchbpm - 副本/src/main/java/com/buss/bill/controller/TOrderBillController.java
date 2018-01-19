package com.buss.bill.controller;
import com.buss.bill.entity.TOrderBillEntity;
import com.buss.bill.service.TOrderBillServiceI;
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
import org.jeecgframework.web.system.pojo.base.TSUser;
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
 * @Description: 订单结算表
 * @author onlineGenerator
 * @date 2016-04-06 16:12:10
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOrderBillController")
public class TOrderBillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOrderBillController.class);

	@Autowired
	private TOrderBillServiceI tOrderBillService;
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
	 * 订单结算表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "main")
	public ModelAndView main(HttpServletRequest request) {
		return new ModelAndView("com/buss/bill/tOrderBillMain");
	}
	
	
	
	
	/**
	 * 结算明细列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "storeList")
	public ModelAndView storeList(HttpServletRequest request) {
		return new ModelAndView("com/buss/bill/tOrderStoreBillList");
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
	public void datagrid(TOrderBillEntity tOrderBill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOrderBillEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOrderBill, request.getParameterMap());
		try{
		//自定义追加查询条件
			String obState = request.getParameter("obState");
			if(StringUtil.isNotEmpty(obState)){
				cq.eq("obState", obState);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOrderBillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 零售商账单列表
	 * @param tOrderBill
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridStore")
	public void datagridStore(TOrderBillEntity tOrderBill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOrderBillEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOrderBill, request.getParameterMap());
		try{
			  TSUser user = ResourceUtil.getSessionUserName();
			if(Utility.isNotEmpty(user)){
				cq.eq("obStoreId", user.getId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOrderBillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	

	/**
	 * 删除订单结算表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOrderBillEntity tOrderBill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOrderBill = systemService.flushEntity(TOrderBillEntity.class, tOrderBill.getId());
		message = "订单结算表删除成功";
		try{
			tOrderBillService.delete(tOrderBill);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单结算表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除订单结算表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "订单结算表删除成功";
		try{
			for(String id:ids.split(",")){
				TOrderBillEntity tOrderBill = systemService.flushEntity(TOrderBillEntity.class, 
				id
				);
				tOrderBillService.delete(tOrderBill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "订单结算表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加订单结算表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TOrderBillEntity tOrderBill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "订单结算表添加成功";
		try{
			tOrderBillService.save(tOrderBill);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单结算表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新订单结算表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TOrderBillEntity tOrderBill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "订单结算表更新成功";
		TOrderBillEntity t = tOrderBillService.get(TOrderBillEntity.class, tOrderBill.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tOrderBill, t);
			tOrderBillService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "订单结算表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 订单结算表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOrderBillEntity tOrderBill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOrderBill.getId())) {
			tOrderBill = tOrderBillService.flushEntity(TOrderBillEntity.class, tOrderBill.getId());
			req.setAttribute("tOrderBillPage", tOrderBill);
		}
		return new ModelAndView("com/buss/bill/tOrderBill-add");
	}
	/**
	 * 订单结算表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOrderBillEntity tOrderBill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOrderBill.getId())) {
			tOrderBill = tOrderBillService.flushEntity(TOrderBillEntity.class, tOrderBill.getId());
			req.setAttribute("tOrderBillPage", tOrderBill);
		}
		return new ModelAndView("com/buss/bill/tOrderBill-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tOrderBillController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOrderBillEntity tOrderBill,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOrderBillEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOrderBill, request.getParameterMap());
		List<TOrderBillEntity> tOrderBills = this.tOrderBillService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"订单结算表");
		modelMap.put(NormalExcelConstants.CLASS,TOrderBillEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("订单结算表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tOrderBills);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByStore")
	public String exportXlsByStore(TOrderBillEntity tOrderBill,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOrderBillEntity.class, dataGrid);
		TSUser user = ResourceUtil.getSessionUserName();
		cq.eq("obStoreId", user.getId());
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOrderBill, request.getParameterMap());
		List<TOrderBillEntity> tOrderBills = this.tOrderBillService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"订单结算表");
		modelMap.put(NormalExcelConstants.CLASS,TOrderBillEntity.class);
		ExportParams params = new ExportParams("订单结算表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息");
		String exclusions[] = new String[]{"结算日期","结算月份","付款日期","支付备注"};
		params.setExclusions(exclusions);
		modelMap.put(NormalExcelConstants.PARAMS,params);
		modelMap.put(NormalExcelConstants.DATA_LIST,tOrderBills);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
	
	 
	
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOrderBillEntity tOrderBill,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"订单结算表");
    	modelMap.put(NormalExcelConstants.CLASS,TOrderBillEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("订单结算表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TOrderBillEntity> listTOrderBillEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TOrderBillEntity.class,params);
				for (TOrderBillEntity tOrderBill : listTOrderBillEntitys) {
					tOrderBillService.save(tOrderBill);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
