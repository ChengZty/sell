package com.buss.count.controller;
import com.buss.count.entity.TStoreGuideMonTargetEntity;
import com.buss.count.entity.TStoreMonTargetEntity;
import com.buss.count.service.TStoreMonTargetServiceI;
import com.buss.store.entity.TStoreEntity;
import com.buss.store.service.TStoreServiceI;

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
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
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
 * @Description: 店铺月目标
 * @author onlineGenerator
 * @date 2017-05-17 11:28:10
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tStoreMonTargetController")
public class TStoreMonTargetController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TStoreMonTargetController.class);

	@Autowired
	private TStoreMonTargetServiceI tStoreMonTargetService;
	@Autowired
	private TStoreServiceI tStoreService;
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
	 * 店铺月目标列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tStoreMonTarget")
	public ModelAndView tStoreMonTarget(HttpServletRequest request) {
		return new ModelAndView("com/buss/count/tStoreMonTargetList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TStoreMonTargetEntity tStoreMonTarget,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TStoreMonTargetEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tStoreMonTarget, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tStoreMonTargetService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	

	
	/**
	 * 设置导购月目标和店铺月目标（从导购目标合计而来）
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TStoreMonTargetEntity tStoreMonTarget, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购月目标设置成功";
		try {
			tStoreMonTargetService.doUpdate(tStoreMonTarget);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "导购月目标设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 店铺月目标新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TStoreMonTargetEntity tStoreMonTarget, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tStoreMonTarget.getId())) {
			tStoreMonTarget = tStoreMonTargetService.flushEntity(TStoreMonTargetEntity.class, tStoreMonTarget.getId());
			req.setAttribute("tStoreMonTargetPage", tStoreMonTarget);
		}
		return new ModelAndView("com/buss/count/tStoreMonTarget-add");
	}
	/**
	 * 选择店铺和月份页面跳转
	 * @return
	 */
	@RequestMapping(params = "goChooseStore")
	public ModelAndView goChooseStore(HttpServletRequest req) {
		req.setAttribute("storeList", tStoreService.getStoreList());
		req.setAttribute("targetMonth", DateUtils.date2Str(DateUtils.yyyy_MM));
		return new ModelAndView("com/buss/count/tStoreMonTarget-chooseStore");
	}
	/**
	 * 店铺导购月目标编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TStoreMonTargetEntity tStoreMonTarget, HttpServletRequest req) {
		//获取导购月目标列表
		tStoreMonTargetService.getGuideTargetList(tStoreMonTarget,req);
		return new ModelAndView("com/buss/count/tStoreMonTarget-update");
	}
	
	/**
	 * 导入功能跳转
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tStoreMonTargetController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TStoreMonTargetEntity tStoreMonTarget,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TStoreMonTargetEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tStoreMonTarget, request.getParameterMap());
		List<TStoreMonTargetEntity> tStoreMonTargets = this.tStoreMonTargetService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"店铺月目标");
		modelMap.put(NormalExcelConstants.CLASS,TStoreMonTargetEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("店铺月目标列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tStoreMonTargets);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TStoreMonTargetEntity tStoreMonTarget,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "店铺月目标");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TStoreMonTargetEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
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
				List<TStoreMonTargetEntity> listTStoreMonTargetEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TStoreMonTargetEntity.class,params);
				for (TStoreMonTargetEntity tStoreMonTarget : listTStoreMonTargetEntitys) {
					tStoreMonTargetService.save(tStoreMonTarget);
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
