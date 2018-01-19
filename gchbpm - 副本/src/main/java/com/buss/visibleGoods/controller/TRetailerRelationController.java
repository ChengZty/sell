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
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
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

import com.buss.visibleGoods.entity.TRetailerGoodsConditionsEntity;
import com.buss.visibleGoods.entity.TRetailerRelationEntity;
import com.buss.visibleGoods.service.TRetailerRelationServiceI;



/**   
 * @Title: Controller
 * @Description: 零售商关系表
 * @author onlineGenerator
 * @date 2016-12-17 14:18:19
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tRetailerRelationController")
public class TRetailerRelationController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TRetailerRelationController.class);

	@Autowired
	private TRetailerRelationServiceI tRetailerRelationService;
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
	 * 设置可见商品选择某个零售商的零售商列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tRetailerList")
	public ModelAndView tRetailerList(HttpServletRequest request) {
		String retailerId = request.getParameter("rId");
		request.setAttribute("retailerId", retailerId);
		TRetailerGoodsConditionsEntity retailerGoods = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailerId);
		if(Utility.isEmpty(retailerGoods)){//初始化一条记录
			this.initRetailerGoodsConditions(retailerId);
		}
		request.setAttribute("otherRetailerType", "1");
		return new ModelAndView("com/buss/visibleGoods/tRetailerList");
	}

	/**初始化零售商商品条件
	 * @param retailerId
	 */
	private void initRetailerGoodsConditions(String retailerId) {
		TRetailerGoodsConditionsEntity retailerGoods = new TRetailerGoodsConditionsEntity();
		retailerGoods.setRetailerId(retailerId);
		retailerGoods.setCloudConditions("1<>1");
		retailerGoods.setRetailerConditions("1<>1");
		retailerGoods.setStatus("A");
		this.systemService.save(retailerGoods);
	}

	/**
	 * 设置可见商品选择某个零售商的云商列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tCloudList")
	public ModelAndView tCloudList(HttpServletRequest request) {
		String retailerId = request.getParameter("rId");
		request.setAttribute("retailerId", retailerId);
		TRetailerGoodsConditionsEntity retailerGoods = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailerId);
		if(Utility.isEmpty(retailerGoods)){//初始化一条记录
			this.initRetailerGoodsConditions(retailerId);
		}
		request.setAttribute("otherRetailerType", "2");
		return new ModelAndView("com/buss/visibleGoods/tCloudList");
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
	public void datagrid(TRetailerRelationEntity tRetailerRelation,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TRetailerRelationEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tRetailerRelation, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tRetailerRelationService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除零售商关系表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TRetailerRelationEntity tRetailerRelation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tRetailerRelation = systemService.flushEntity(TRetailerRelationEntity.class, tRetailerRelation.getId());
		message = "零售商关系表删除成功";
		try{
			tRetailerRelationService.deleteRelation(tRetailerRelation);
			//更新零售商商品条件
			tRetailerRelationService.updateRetailerGoodsConditions(tRetailerRelation.getRetailerId());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商关系表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除零售商关系表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "零售商关系表删除成功";
		try{
			for(String id:ids.split(",")){
				TRetailerRelationEntity tRetailerRelation = systemService.flushEntity(TRetailerRelationEntity.class,id);
				tRetailerRelationService.deleteRelation(tRetailerRelation);
				//更新零售商商品条件
				tRetailerRelationService.updateRetailerGoodsConditions(tRetailerRelation.getRetailerId());
			}
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商关系表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加零售商关系表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TRetailerRelationEntity tRetailerRelation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商关系表添加成功";
		try{
			tRetailerRelationService.save(tRetailerRelation);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商关系表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 批量添加零售商或者云商关系表
	 * @return
	 */
	@RequestMapping(params = "doBatchAdd")
	@ResponseBody
	public AjaxJson doBatchAdd(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商关系表添加成功";
		try{
			tRetailerRelationService.batchSave(request);
			//更新零售商商品条件
			String retailerId = request.getParameter("retailerId");
			tRetailerRelationService.updateRetailerGoodsConditions(retailerId);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商关系表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新零售商关系表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TRetailerRelationEntity tRetailerRelation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商关系表更新成功";
		TRetailerRelationEntity t = tRetailerRelationService.get(TRetailerRelationEntity.class, tRetailerRelation.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tRetailerRelation, t);
			tRetailerRelationService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "零售商关系表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 零售商关系表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TRetailerRelationEntity tRetailerRelation, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tRetailerRelation.getId())) {
			tRetailerRelation = tRetailerRelationService.flushEntity(TRetailerRelationEntity.class, tRetailerRelation.getId());
			req.setAttribute("tRetailerRelationPage", tRetailerRelation);
		}
		return new ModelAndView("com/buss/visibleGoods/tRetailerRelation-add");
	}
	/**
	 * 零售商关系表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TRetailerRelationEntity tRetailerRelation, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tRetailerRelation.getId())) {
			tRetailerRelation = tRetailerRelationService.flushEntity(TRetailerRelationEntity.class, tRetailerRelation.getId());
			req.setAttribute("tRetailerRelationPage", tRetailerRelation);
		}
		return new ModelAndView("com/buss/visibleGoods/tRetailerRelation-update");
	}
	
	
}
