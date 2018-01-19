package com.buss.goods.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.buss.goods.entity.TExcludeRuleEntity;
import com.buss.goods.service.TExcludeRuleServiceI;



/**   
 * @Title: Controller
 * @Description: 互斥规则
 * @author onlineGenerator
 * @date 2016-03-29 16:05:39
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tExcludeRuleController")
public class TExcludeRuleController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TExcludeRuleController.class);

	@Autowired
	private TExcludeRuleServiceI tExcludeRuleService;
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
	 * 互斥规则列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/goods/tExcludeRuleList");
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
	public void datagrid(TExcludeRuleEntity tExcludeRule,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TExcludeRuleEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tExcludeRule, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user = ResourceUtil.getSessionUserName();
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商
				cq.eq("retailerId", user.getId());
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				cq.eq("retailerId", user.getRetailerId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tExcludeRuleService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除互斥规则
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TExcludeRuleEntity tExcludeRule, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tExcludeRule = systemService.flushEntity(TExcludeRuleEntity.class, tExcludeRule.getId());
		message = "互斥规则删除成功";
		try{
			tExcludeRule.setStatus(common.GlobalConstants.STATUS_INACTIVE );
			tExcludeRule.setUpdateDate(DateUtils.gettimestamp());
			tExcludeRuleService.saveOrUpdate(tExcludeRule);
//			tExcludeRuleService.delete(tExcludeRule);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "互斥规则删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除互斥规则
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "互斥规则删除成功";
		try{
			for(String id:ids.split(",")){
				TExcludeRuleEntity tExcludeRule = systemService.flushEntity(TExcludeRuleEntity.class, id );
				tExcludeRule.setStatus(common.GlobalConstants.STATUS_INACTIVE );
				tExcludeRule.setUpdateDate(DateUtils.gettimestamp());
				tExcludeRuleService.saveOrUpdate(tExcludeRule);
//				tExcludeRuleService.delete(tExcludeRule);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "互斥规则删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加互斥规则
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TExcludeRuleEntity tExcludeRule, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "互斥规则添加成功";
		try{
//			TSUser user = ResourceUtil.getSessionUserName();
//			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
//				if(StringUtil.isNotEmpty(user.getRetailerId())){
//					tExcludeRule.setRetailerId(user.getRetailerId());
//				}else{
//					tExcludeRule.setRetailerId(user.getId());
//				}
//			}
			tExcludeRule.setSqlStr(this.getConditionSql(tExcludeRule));
			tExcludeRuleService.save(tExcludeRule);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "互斥规则添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新互斥规则
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TExcludeRuleEntity tExcludeRule, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "互斥规则更新成功";
		TExcludeRuleEntity t = tExcludeRuleService.get(TExcludeRuleEntity.class, tExcludeRule.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tExcludeRule, t);
			t.setSqlStr(this.getConditionSql(tExcludeRule));
			tExcludeRuleService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "互斥规则更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
public String getConditionSql(TExcludeRuleEntity tExcludeRule) {
	StringBuffer sql = new StringBuffer("(");
	if(StringUtil.isNotEmpty(tExcludeRule.getThridCategoryId())){
		sql.append(" Thrid_Category_Id = '").append(tExcludeRule.getThridCategoryId()).append("'");
	}else if(StringUtil.isNotEmpty(tExcludeRule.getSubCategoryId())){
		sql.append(" Sub_Category_Id ='").append(tExcludeRule.getSubCategoryId()).append("'");
	}else if(StringUtil.isNotEmpty(tExcludeRule.getTopCategoryId())){
		sql.append(" Top_Category_Id = '").append(tExcludeRule.getTopCategoryId()).append("'");
	}else{
		sql.append(" 1=1 ");
	}
	if(StringUtil.isNotEmpty(tExcludeRule.getBrandId())){
		String[] strArray = tExcludeRule.getBrandId().split(",");
		sql.append(" and brand_Id in (");
		for(String brandId:strArray){
			sql.append("'").append(brandId).append("',");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
	}
	sql.append(")");
	return sql.toString();
}
	
	
	/**
	 * 互斥规则新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TExcludeRuleEntity tExcludeRule, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tExcludeRule.getId())) {
			tExcludeRule = tExcludeRuleService.flushEntity(TExcludeRuleEntity.class, tExcludeRule.getId());
			req.setAttribute("tExcludeRulePage", tExcludeRule);
		}
		return new ModelAndView("com/buss/goods/tExcludeRule");
	}
	/**
	 * 互斥规则编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TExcludeRuleEntity tExcludeRule, HttpServletRequest req) {
		String load = req.getParameter("load");
		if (StringUtil.isNotEmpty(tExcludeRule.getId())) {
			tExcludeRule = tExcludeRuleService.flushEntity(TExcludeRuleEntity.class, tExcludeRule.getId());
			req.setAttribute("tExcludeRulePage", tExcludeRule);
		}
		if("detail".equals(load)){
			return new ModelAndView("com/buss/goods/tExcludeRule-view");
		}
		return new ModelAndView("com/buss/goods/tExcludeRule-update");
	}
	
	/**
	 * 用户选择品牌跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "findBrands")
	public ModelAndView findBrands(HttpServletRequest request) {
		//--author：zhoujf-----start----date:20150531--------for: 编辑用户，选择角色,弹出的角色列表页面，默认没选中
		String ids = request.getParameter("ids");
		String brandNames = request.getParameter("brandNames");
		String retailer_Id = request.getParameter("retailer_Id");
		request.setAttribute("ids", ids);
		request.setAttribute("brandNames", brandNames);
		request.setAttribute("retailer_Id", retailer_Id);
		return new ModelAndView("com/buss/goods/brands");
	}
	
	/**
	 * 用户选择零售商跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "findRetailers")
	public ModelAndView findRetailers(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("com/buss/goods/retailers");
		String ids = oConvertUtils.getString(request.getParameter("ids"));
		mv.addObject("ids", ids);
		return mv;
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tExcludeRuleController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TExcludeRuleEntity tExcludeRule,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TExcludeRuleEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tExcludeRule, request.getParameterMap());
		List<TExcludeRuleEntity> tExcludeRules = this.tExcludeRuleService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"互斥规则");
		modelMap.put(NormalExcelConstants.CLASS,TExcludeRuleEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("互斥规则列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tExcludeRules);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TExcludeRuleEntity tExcludeRule,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"互斥规则");
    	modelMap.put(NormalExcelConstants.CLASS,TExcludeRuleEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("互斥规则列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TExcludeRuleEntity> listTExcludeRuleEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TExcludeRuleEntity.class,params);
				for (TExcludeRuleEntity tExcludeRule : listTExcludeRuleEntitys) {
					tExcludeRuleService.save(tExcludeRule);
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
