package com.buss.words.controller;
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
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.words.entity.TChatWordsTypeEntity;
import com.buss.words.service.TChatWordsTypeServiceI;



/**   
 * @Title: Controller
 * @Description: 撩客话术类别
 * @author onlineGenerator
 * @date 2017-02-10 14:31:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tChatWordsTypeController")
public class TChatWordsTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TChatWordsTypeController.class);

	@Autowired
	private TChatWordsTypeServiceI tChatWordsTypeService;
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
	 * 撩客话术类别列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/words/tChatWordsTypeList");
	}

	
	@RequestMapping(params = "datagrid")
	public void datagrid(TChatWordsTypeEntity tChatWordsType,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TChatWordsTypeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tChatWordsType, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isEmpty(retailerId)){
				retailerId="admin";
			}
			cq.eq("retailerId", retailerId);
			String sort = dataGrid.getSort();
			if(Utility.isEmpty(sort)){
				cq.addOrder("id", SortDirection.asc);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tChatWordsTypeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除撩客话术类别
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TChatWordsTypeEntity tChatWordsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tChatWordsType = systemService.flushEntity(TChatWordsTypeEntity.class, tChatWordsType.getId());
		message = "撩客话术类别删除成功";
		try{
			tChatWordsTypeService.delType(tChatWordsType);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "撩客话术类别删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除撩客话术类别
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "撩客话术类别删除成功";
		try{
			for(String id:ids.split(",")){
				TChatWordsTypeEntity tChatWordsType = systemService.flushEntity(TChatWordsTypeEntity.class, Integer.valueOf(id));
				tChatWordsType.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				tChatWordsTypeService.updateEntitie(tChatWordsType);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "撩客话术类别删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加撩客话术类别
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TChatWordsTypeEntity tChatWordsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "撩客话术类别添加成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isEmpty(retailerId)){
				retailerId = "admin";
			}
			tChatWordsType.setRetailerId(retailerId);
			tChatWordsTypeService.save(tChatWordsType);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "撩客话术类别添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新撩客话术类别
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TChatWordsTypeEntity tChatWordsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "撩客话术类别更新成功";
		TChatWordsTypeEntity t = tChatWordsTypeService.get(TChatWordsTypeEntity.class, tChatWordsType.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tChatWordsType, t);
			tChatWordsTypeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "撩客话术类别更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 撩客话术类别新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TChatWordsTypeEntity tChatWordsType, HttpServletRequest req) {
		return new ModelAndView("com/buss/words/tChatWordsType-add");
	}
	/**
	 * 撩客话术类别编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TChatWordsTypeEntity tChatWordsType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tChatWordsType.getId())) {
			tChatWordsType = tChatWordsTypeService.flushEntity(TChatWordsTypeEntity.class, tChatWordsType.getId());
			req.setAttribute("tChatWordsTypePage", tChatWordsType);
		}
		return new ModelAndView("com/buss/words/tChatWordsType-update");
	}
	
	
}
