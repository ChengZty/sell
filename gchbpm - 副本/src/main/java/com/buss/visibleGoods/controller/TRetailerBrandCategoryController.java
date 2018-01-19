package com.buss.visibleGoods.controller;
import java.io.IOException;
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
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
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

import com.buss.visibleGoods.entity.TRetailerBrandCategoryEntity;
import com.buss.visibleGoods.service.TRetailerBrandCategoryServiceI;
import com.buss.visibleGoods.service.TRetailerRelationServiceI;



/**   
 * @Title: Controller
 * @Description: 零售商品牌分类选择
 * @author onlineGenerator
 * @date 2016-12-19 10:54:37
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tRetailerBrandCategoryController")
public class TRetailerBrandCategoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TRetailerBrandCategoryController.class);

	@Autowired
	private TRetailerBrandCategoryServiceI tRetailerBrandCategoryService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TRetailerRelationServiceI tRetailerRelationService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 零售商品牌分类选择列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("otherRetailerId", request.getParameter("otherRetailerId"));
		request.setAttribute("retailerId", request.getParameter("retailerId"));
		return new ModelAndView("com/buss/visibleGoods/tRetailerBrandCategoryList");
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
	public void datagrid(TRetailerBrandCategoryEntity tRetailerBrandCategory,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TRetailerBrandCategoryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tRetailerBrandCategory, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tRetailerBrandCategoryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	
	/**
	 * 批量删除零售商品牌分类选择
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "零售商品牌分类选择删除成功";
		try{
			tRetailerBrandCategoryService.doBatchDel(request);
			//更新零售商商品条件
			String retailerId = request.getParameter("retailerId");
	 		tRetailerRelationService.updateRetailerGoodsConditions(retailerId);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商品牌分类选择删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加零售商品牌分类选择 并更新零售商商品条件
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TRetailerBrandCategoryEntity tRetailerBrandCategory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商品牌分类选择添加成功";
		try{
			tRetailerBrandCategoryService.saveRetailerBrandCategory(tRetailerBrandCategory);
			tRetailerRelationService.updateRetailerGoodsConditions(tRetailerBrandCategory.getRetailerId());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商品牌分类选择添加失败";
			throw new BusinessException(e.getMessage());
		}
		//更新零售商商品条件
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新零售商品牌分类选择
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TRetailerBrandCategoryEntity tRetailerBrandCategory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商品牌分类选择更新成功";
		TRetailerBrandCategoryEntity t = tRetailerBrandCategoryService.get(TRetailerBrandCategoryEntity.class, tRetailerBrandCategory.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tRetailerBrandCategory, t);
			tRetailerBrandCategoryService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "零售商品牌分类选择更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 零售商品牌分类选择新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TRetailerBrandCategoryEntity tRetailerBrandCategory, HttpServletRequest req) {
		tRetailerBrandCategory.setOtherRetailerId(req.getParameter("otherRetailerId"));
		tRetailerBrandCategory.setRetailerId(req.getParameter("retailerId"));
		req.setAttribute("tRetailerBrandCategoryPage", tRetailerBrandCategory);
		String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
		List<TSCategoryEntity> categoryList = systemService.findListbySql(sql);
		req.setAttribute("categoryList", categoryList);
		return new ModelAndView("com/buss/visibleGoods/tRetailerBrandCategory-add");
	}
	/**
	 * 零售商品牌分类选择编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TRetailerBrandCategoryEntity tRetailerBrandCategory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tRetailerBrandCategory.getId())) {
			tRetailerBrandCategory = tRetailerBrandCategoryService.flushEntity(TRetailerBrandCategoryEntity.class, tRetailerBrandCategory.getId());
			req.setAttribute("tRetailerBrandCategoryPage", tRetailerBrandCategory);
		}
		return new ModelAndView("com/buss/visibleGoods/tRetailerBrandCategory-update");
	}
	
}
