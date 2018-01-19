package com.buss.base.controller;
import com.buss.base.entity.TAllGoodsParamsEntity;
import com.buss.base.service.TAllGoodsParamsServiceI;
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
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSTerritory;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.vo.AreaVo;
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
 * @Description: 产品参数表
 * @author onlineGenerator
 * @date 2016-04-23 14:51:35
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tAllGoodsParamsController")
public class TAllGoodsParamsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TAllGoodsParamsController.class);

	@Autowired
	private TAllGoodsParamsServiceI tAllGoodsParamsService;
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
	 * 产品参数表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String category_Id = request.getParameter("category_Id");
		request.setAttribute("category_Id", category_Id);
		return new ModelAndView("com/buss/base/tAllGoodsParamsList");
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
	public void datagrid(TAllGoodsParamsEntity tAllGoodsParams,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TAllGoodsParamsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tAllGoodsParams, request.getParameterMap());
		try{
		//自定义追加查询条件
			String category_Id = request.getParameter("category_Id");
			if(!StringUtil.isEmpty(category_Id)){
				cq.eq("categoryId", category_Id);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tAllGoodsParamsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除产品参数表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TAllGoodsParamsEntity tAllGoodsParams, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tAllGoodsParams = systemService.flushEntity(TAllGoodsParamsEntity.class, tAllGoodsParams.getId());
		message = "产品参数表删除成功";
		try{
			tAllGoodsParamsService.delete(tAllGoodsParams);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "产品参数表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除产品参数表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "产品参数表删除成功";
		try{
			for(String id:ids.split(",")){
				TAllGoodsParamsEntity tAllGoodsParams = systemService.flushEntity(TAllGoodsParamsEntity.class, 
				id
				);
				tAllGoodsParamsService.delete(tAllGoodsParams);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "产品参数表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加产品参数表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TAllGoodsParamsEntity tAllGoodsParams, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "产品参数表添加成功";
		try{
			tAllGoodsParamsService.save(tAllGoodsParams);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "产品参数表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新产品参数表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TAllGoodsParamsEntity tAllGoodsParams, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "产品参数表更新成功";
		TAllGoodsParamsEntity t = tAllGoodsParamsService.get(TAllGoodsParamsEntity.class, tAllGoodsParams.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tAllGoodsParams, t);
			tAllGoodsParamsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "产品参数表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 产品参数表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TAllGoodsParamsEntity tAllGoodsParams, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tAllGoodsParams.getId())) {
			tAllGoodsParams = tAllGoodsParamsService.flushEntity(TAllGoodsParamsEntity.class, tAllGoodsParams.getId());
			req.setAttribute("tAllGoodsParamsPage", tAllGoodsParams);
		}
		req.setAttribute("category_Id", req.getParameter("category_Id"));
		return new ModelAndView("com/buss/base/tAllGoodsParams-add");
	}
	/**
	 * 产品参数表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TAllGoodsParamsEntity tAllGoodsParams, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tAllGoodsParams.getId())) {
			tAllGoodsParams = tAllGoodsParamsService.flushEntity(TAllGoodsParamsEntity.class, tAllGoodsParams.getId());
			req.setAttribute("tAllGoodsParamsPage", tAllGoodsParams);
		}
		return new ModelAndView("com/buss/base/tAllGoodsParams-update");
	}
	
	@RequestMapping(params = "findParams")
	@ResponseBody
	public AjaxJson findParams(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
//		List<TAllGoodsParamsEntity> list = tAllGoodsParamsService.findByProperty(TAllGoodsParamsEntity.class, "categoryId", request.getParameter("category_id"));
		String hql = "from TAllGoodsParamsEntity where categoryId='"+request.getParameter("category_id")+"' order by sortNum asc";
		List<TAllGoodsParamsEntity> list = tAllGoodsParamsService.findByQueryString(hql);
		j.setObj(list);
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tAllGoodsParamsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TAllGoodsParamsEntity tAllGoodsParams,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TAllGoodsParamsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tAllGoodsParams, request.getParameterMap());
		List<TAllGoodsParamsEntity> tAllGoodsParamss = this.tAllGoodsParamsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"产品参数表");
		modelMap.put(NormalExcelConstants.CLASS,TAllGoodsParamsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("产品参数表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tAllGoodsParamss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TAllGoodsParamsEntity tAllGoodsParams,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"产品参数表");
    	modelMap.put(NormalExcelConstants.CLASS,TAllGoodsParamsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("产品参数表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TAllGoodsParamsEntity> listTAllGoodsParamsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TAllGoodsParamsEntity.class,params);
				for (TAllGoodsParamsEntity tAllGoodsParams : listTAllGoodsParamsEntitys) {
					tAllGoodsParamsService.save(tAllGoodsParams);
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
