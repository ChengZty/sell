package com.buss.sms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.sms.entity.TSmsAutographEntity;
import com.buss.sms.entity.TSmsSendTemplateEntity;
import com.buss.sms.service.TSmsAutographServiceI;
import com.buss.sms.service.TSmsSendTemplateServiceI;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: t_sms_send_template
 * @author onlineGenerator
 * @date 2017-02-15 15:03:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSmsSendTemplateController")
public class TSmsSendTemplateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSmsSendTemplateController.class);

	@Autowired
	private TSmsSendTemplateServiceI tSmsSendTemplateService;
	
	@Autowired
	private TSmsAutographServiceI tSmsAutographService;
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
	 * t_sms_send_template列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tSmsSendTemplate")
	public ModelAndView tSmsSendTemplate(HttpServletRequest request) {
		return new ModelAndView("com/buss/sms/tSmsSendTemplateList");
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
	public void datagrid(TSmsSendTemplateEntity tSmsSendTemplate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String retailerId = ResourceUtil.getRetailerId();
		CriteriaQuery cq = new CriteriaQuery(TSmsSendTemplateEntity.class, dataGrid);
		cq.eq("retailerId", retailerId);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSmsSendTemplate, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSmsSendTemplateService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除t_sms_send_template
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSmsSendTemplateEntity tSmsSendTemplate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSmsSendTemplate = systemService.flushEntity(TSmsSendTemplateEntity.class, tSmsSendTemplate.getId());
		message = "短信模板删除成功";
		try{
			tSmsSendTemplate.setStatus(GlobalConstants.STATUS_INACTIVE);
			tSmsSendTemplateService.updateEntitie(tSmsSendTemplate);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信模板删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除短信模板
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "短信模板删除成功";
		try{
			for(String id:ids.split(",")){
				TSmsSendTemplateEntity tSmsSendTemplate = systemService.flushEntity(TSmsSendTemplateEntity.class, 
				id
				);
				tSmsSendTemplate.setStatus(GlobalConstants.STATUS_INACTIVE);
				tSmsSendTemplateService.updateEntitie(tSmsSendTemplate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "短信模板删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加短信模板
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSmsSendTemplateEntity tSmsSendTemplate, HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		tSmsSendTemplate.setRetailerId(retailerId);
		AjaxJson j = new AjaxJson();
		message = "短信模板添加成功";
		try{
			tSmsSendTemplateService.save(tSmsSendTemplate);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信模板添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新t_sms_send_template
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSmsSendTemplateEntity tSmsSendTemplate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "短信模板更新成功";
		TSmsSendTemplateEntity t = tSmsSendTemplateService.get(TSmsSendTemplateEntity.class, tSmsSendTemplate.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSmsSendTemplate, t);
			tSmsSendTemplateService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "短信模板更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * t_sms_send_template新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSmsSendTemplateEntity tSmsSendTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSmsSendTemplate.getId())) {
			tSmsSendTemplate = tSmsSendTemplateService.flushEntity(TSmsSendTemplateEntity.class, tSmsSendTemplate.getId());
			req.setAttribute("tSmsSendTemplatePage", tSmsSendTemplate);
		}
		List<TSmsAutographEntity> autographList = getAutographList();
		req.setAttribute("autographList", autographList);
		return new ModelAndView("com/buss/sms/tSmsSendTemplate-add");
	}
	/**
	 * t_sms_send_template编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSmsSendTemplateEntity tSmsSendTemplate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSmsSendTemplate.getId())) {
			tSmsSendTemplate = tSmsSendTemplateService.flushEntity(TSmsSendTemplateEntity.class, tSmsSendTemplate.getId());
			req.setAttribute("tSmsSendTemplatePage", tSmsSendTemplate);
		}
		List<TSmsAutographEntity> autographList = getAutographList();
		req.setAttribute("autographList", autographList);
		return new ModelAndView("com/buss/sms/tSmsSendTemplate-update");
	}
	
	/**
	 * 根据模板ID查询模板明细
	 * @return
	 */
	 @RequestMapping(params = "getTemplateIdById")
	@ResponseBody
	public AjaxJson getTemplateIdById(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try{
			String templateId = request.getParameter("templateId");
			TSmsSendTemplateEntity tsst = systemService.get(TSmsSendTemplateEntity.class, templateId);
			j.setObj(tsst);
		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		
		return j;
	}
	 
	 public List<TSmsAutographEntity> getAutographList(){
			String retailerId = ResourceUtil.getRetailerId();
			CriteriaQuery cq = new CriteriaQuery(TSmsAutographEntity.class);
			cq.eq("retailerId", retailerId);
			cq.eq("autographStatus", TSmsAutographEntity.AUTOGRAPH_STATUS_1);//审核通过的
			cq.add();
			List<TSmsAutographEntity> autographList = this.tSmsAutographService.getListByCriteriaQuery(cq, false);
			return autographList;
			
		}
}
