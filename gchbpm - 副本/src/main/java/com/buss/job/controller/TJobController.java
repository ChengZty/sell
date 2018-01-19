package com.buss.job.controller;
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

import com.buss.job.entity.TJobEntity;
import com.buss.job.service.TJobServiceI;



/**   
 * @Title: Controller
 * @Description: 任务维护
 * @author onlineGenerator
 * @date 2016-11-05 11:54:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tJobController")
public class TJobController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TJobController.class);

	@Autowired
	private TJobServiceI tJobService;
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
	 * 任务维护列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/job/tJobList");
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
	public void datagrid(TJobEntity tJob,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TJobEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tJob, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tJobService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除任务维护
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TJobEntity tJob, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tJob = systemService.flushEntity(TJobEntity.class, tJob.getId());
		message = "任务维护删除成功";
		try{
			tJob.setStatus("I");
			tJobService.updateEntitie(tJob);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务维护删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除任务维护
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "任务维护删除成功";
		try{
			for(String id:ids.split(",")){
				TJobEntity tJob = systemService.flushEntity(TJobEntity.class, id);
				tJob.setStatus("I");
				tJobService.updateEntitie(tJob);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "任务维护删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加任务维护
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TJobEntity tJob, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "任务维护添加成功";
		try{
			tJobService.save(tJob);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务维护添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新任务维护
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TJobEntity tJob, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "任务维护更新成功";
		TJobEntity t = tJobService.get(TJobEntity.class, tJob.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tJob, t);
			tJobService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "任务维护更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 任务维护新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TJobEntity tJob, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tJob.getId())) {
			tJob = tJobService.flushEntity(TJobEntity.class, tJob.getId());
			req.setAttribute("tJobPage", tJob);
		}
		return new ModelAndView("com/buss/job/tJob-add");
	}
	/**
	 * 任务维护编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TJobEntity tJob, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tJob.getId())) {
			tJob = tJobService.flushEntity(TJobEntity.class, tJob.getId());
			req.setAttribute("tJobPage", tJob);
		}
		return new ModelAndView("com/buss/job/tJob-update");
	}
	
}
