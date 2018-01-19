package com.buss.sms.controller;
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
import com.buss.sms.service.TSmsAutographServiceI;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: t_sms_autograph
 * @author onlineGenerator
 * @date 2017-03-01 14:57:12
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSmsAutographController")
public class TSmsAutographController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSmsAutographController.class);

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
	 * t_sms_autograph列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tSmsAutographList")
	public ModelAndView tSmsAutograph(HttpServletRequest request) {
		String retailerId = request.getParameter("retailerId");
		request.setAttribute("retailerId", retailerId);
		return new ModelAndView("com/buss/sms/tSmsAutographList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSmsAutographEntity tSmsAutograph,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSmsAutographEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSmsAutograph, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSmsAutographService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除签名
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSmsAutographEntity tSmsAutograph, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSmsAutograph = systemService.flushEntity(TSmsAutographEntity.class, tSmsAutograph.getId());
		message = "签名删除成功";
		try{
			tSmsAutograph.setStatus(GlobalConstants.STATUS_INACTIVE);
			tSmsAutographService.updateEntitie(tSmsAutograph);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "签名删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除签名
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "签名删除成功";
		try{
			for(String id:ids.split(",")){
				TSmsAutographEntity tSmsAutograph = systemService.flushEntity(TSmsAutographEntity.class, 
				id
				);
				tSmsAutograph.setStatus(GlobalConstants.STATUS_INACTIVE);
				tSmsAutographService.updateEntitie(tSmsAutograph);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "签名删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加t_sms_autograph
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSmsAutographEntity tSmsAutograph, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "签名添加成功";
		try{
			tSmsAutographService.save(tSmsAutograph);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "签名添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新签名
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSmsAutographEntity tSmsAutograph, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "签名更新成功";
		TSmsAutographEntity t = tSmsAutographService.get(TSmsAutographEntity.class, tSmsAutograph.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSmsAutograph, t);
			tSmsAutographService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "签名更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * t_sms_autograph新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSmsAutographEntity tSmsAutograph, HttpServletRequest req) {
		String retailerId = req.getParameter("retailerId");
		req.setAttribute("retailerId", retailerId);
		if (StringUtil.isNotEmpty(tSmsAutograph.getId())) {
			tSmsAutograph = tSmsAutographService.flushEntity(TSmsAutographEntity.class, tSmsAutograph.getId());
			req.setAttribute("tSmsAutographPage", tSmsAutograph);
		}
		return new ModelAndView("com/buss/sms/tSmsAutograph-add");
	}
	/**
	 * t_sms_autograph编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSmsAutographEntity tSmsAutograph, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSmsAutograph.getId())) {
			tSmsAutograph = tSmsAutographService.flushEntity(TSmsAutographEntity.class, tSmsAutograph.getId());
			req.setAttribute("tSmsAutographPage", tSmsAutograph);
		}
		return new ModelAndView("com/buss/sms/tSmsAutograph-update");
	}
	
}
