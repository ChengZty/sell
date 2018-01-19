package com.buss.user.controller;
import com.buss.user.entity.TFinAbilityEntity;
import com.buss.user.service.TFinAbilityServiceI;
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
 * @Description: 经济实力
 * @author onlineGenerator
 * @date 2017-02-25 20:28:07
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tFinAbilityController")
public class TFinAbilityController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TFinAbilityController.class);

	@Autowired
	private TFinAbilityServiceI tFinAbilityService;
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
	 * 经济实力列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tFinAbility")
	public ModelAndView tFinAbility(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/tFinAbilityList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TFinAbilityEntity tFinAbility,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TFinAbilityEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinAbility, request.getParameterMap());
		try{
		//自定义追加查询条件
			String rId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(rId)){
				cq.eq("retailerId", rId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tFinAbilityService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除经济实力
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TFinAbilityEntity tFinAbility, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tFinAbility = systemService.flushEntity(TFinAbilityEntity.class, tFinAbility.getId());
		message = "经济实力删除成功";
		try{
			tFinAbility.setStatus(common.GlobalConstants.STATUS_INACTIVE );
			tFinAbilityService.updateEntitie(tFinAbility);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "经济实力删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除经济实力
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "经济实力删除成功";
		try{
			for(String id:ids.split(",")){
				TFinAbilityEntity tFinAbility = systemService.flushEntity(TFinAbilityEntity.class, 
				id
				);
				tFinAbilityService.delete(tFinAbility);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "经济实力删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加经济实力
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TFinAbilityEntity tFinAbility, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "经济实力添加成功";
		try{
			String rId = ResourceUtil.getRetailerId();
			tFinAbility.setRetailerId(rId);
			tFinAbility.setStatus(common.GlobalConstants.STATUS_ACTIVE);
			tFinAbilityService.save(tFinAbility);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "经济实力添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新经济实力
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TFinAbilityEntity tFinAbility, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "经济实力更新成功";
		TFinAbilityEntity t = tFinAbilityService.get(TFinAbilityEntity.class, tFinAbility.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tFinAbility, t);
			tFinAbilityService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "经济实力更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 经济实力新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TFinAbilityEntity tFinAbility, HttpServletRequest req) {
		req.setAttribute("retailerId", ResourceUtil.getRetailerId());
		return new ModelAndView("com/buss/user/tFinAbility-add");
	}
	/**
	 * 经济实力编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TFinAbilityEntity tFinAbility, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tFinAbility.getId())) {
			tFinAbility = tFinAbilityService.flushEntity(TFinAbilityEntity.class, tFinAbility.getId());
			req.setAttribute("tFinAbilityPage", tFinAbility);
		}
		return new ModelAndView("com/buss/user/tFinAbility-update");
	}
	
	/**
	 * 导入功能跳转
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tFinAbilityController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TFinAbilityEntity tFinAbility,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFinAbilityEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinAbility, request.getParameterMap());
		List<TFinAbilityEntity> tFinAbilitys = this.tFinAbilityService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"经济实力");
		modelMap.put(NormalExcelConstants.CLASS,TFinAbilityEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("经济实力列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tFinAbilitys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TFinAbilityEntity tFinAbility,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "经济实力");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TFinAbilityEntity.class);
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
				List<TFinAbilityEntity> listTFinAbilityEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TFinAbilityEntity.class,params);
				for (TFinAbilityEntity tFinAbility : listTFinAbilityEntitys) {
					tFinAbilityService.save(tFinAbility);
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
