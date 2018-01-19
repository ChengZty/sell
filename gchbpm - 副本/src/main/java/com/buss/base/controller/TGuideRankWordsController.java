package com.buss.base.controller;
import com.buss.base.entity.TGuideRankWordsEntity;
import com.buss.base.service.TGuideRankWordsServiceI;
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
 * @Description: 导购排名话术
 * @author onlineGenerator
 * @date 2016-11-02 19:55:00
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGuideRankWordsController")
public class TGuideRankWordsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGuideRankWordsController.class);

	@Autowired
	private TGuideRankWordsServiceI tGuideRankWordsService;
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
	 * 导购排名话术列表(零售商) 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/base/tGuideRankWordsList");
	}
	
	/**
	 * 导购排名话术列表(后台) 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "allList")
	public ModelAndView allList(HttpServletRequest request) {
		request.setAttribute("retailer_Id", request.getParameter("retailer_Id"));//后台查询零售商导购话术
		return new ModelAndView("com/buss/base/tGuideRankWordsList-all");
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
	public void datagrid(TGuideRankWordsEntity tGuideRankWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGuideRankWordsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGuideRankWords, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = request.getParameter("retailer_Id");//后台查询零售商导购话术
			if(StringUtil.isEmpty(retailerId)){
				retailerId = ResourceUtil.getRetailerId();
			}
			cq.eq("retailerId", retailerId);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGuideRankWordsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除导购排名话术
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TGuideRankWordsEntity tGuideRankWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tGuideRankWords = systemService.flushEntity(TGuideRankWordsEntity.class, tGuideRankWords.getId());
		message = "导购排名话术删除成功";
		try{
			tGuideRankWords.setStatus("I");
			tGuideRankWordsService.updateEntitie(tGuideRankWords);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "导购排名话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除导购排名话术
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "导购排名话术删除成功";
		try{
			for(String id:ids.split(",")){
				TGuideRankWordsEntity tGuideRankWords = systemService.flushEntity(TGuideRankWordsEntity.class, id);
				tGuideRankWords.setStatus("I");
				tGuideRankWordsService.updateEntitie(tGuideRankWords);
				tGuideRankWordsService.delete(tGuideRankWords);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "导购排名话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加导购排名话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TGuideRankWordsEntity tGuideRankWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购排名话术添加成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			tGuideRankWords.setRetailerId(retailerId);
			tGuideRankWordsService.save(tGuideRankWords);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "导购排名话术添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新导购排名话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TGuideRankWordsEntity tGuideRankWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购排名话术更新成功";
		TGuideRankWordsEntity t = tGuideRankWordsService.get(TGuideRankWordsEntity.class, tGuideRankWords.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tGuideRankWords, t);
			tGuideRankWordsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "导购排名话术更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 导购排名话术新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TGuideRankWordsEntity tGuideRankWords, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGuideRankWords.getId())) {
			tGuideRankWords = tGuideRankWordsService.flushEntity(TGuideRankWordsEntity.class, tGuideRankWords.getId());
			req.setAttribute("tGuideRankWordsPage", tGuideRankWords);
		}
		return new ModelAndView("com/buss/base/tGuideRankWords-add");
	}
	/**
	 * 导购排名话术编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TGuideRankWordsEntity tGuideRankWords, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGuideRankWords.getId())) {
			tGuideRankWords = tGuideRankWordsService.flushEntity(TGuideRankWordsEntity.class, tGuideRankWords.getId());
			req.setAttribute("tGuideRankWordsPage", tGuideRankWords);
		}
		return new ModelAndView("com/buss/base/tGuideRankWords-update");
	}
	
}
