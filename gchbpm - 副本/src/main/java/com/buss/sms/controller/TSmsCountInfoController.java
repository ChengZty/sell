package com.buss.sms.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.buss.sms.entity.TSmsCountInfoEntity;
import com.buss.user.entity.TSUserMessage;



/**   
 * @Title: Controller
 * @Description: t_sms_sub_account
 * @author onlineGenerator
 * @date 2017-02-13 18:42:58
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSmsCountInfoController")
public class TSmsCountInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSmsCountInfoController.class);

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
	 * 短信条数详情tab页面跳转
	 * @return
	 */
	@RequestMapping(params = "allList")
	public ModelAndView allList(HttpServletRequest request) {
		return new ModelAndView("com/buss/sms/tSmsCountTabs");
//		String retailerId = ResourceUtil.getRetailerId();
//		if(Utility.isNotEmpty(retailerId)){
//			return new ModelAndView("com/buss/sms/tSmsCountInfoListOfRet");
//		}
//		return new ModelAndView("com/buss/sms/tSmsCountInfoList");
	}
	
	/**
	 * 批量发送短信条数详情列表页面跳转
	 * @return
	 */
	@RequestMapping(params = "batchPushList")
	public ModelAndView batchPushList(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){
			return new ModelAndView("com/buss/sms/tSmsCountInfoListOfRet");
		}
		return new ModelAndView("com/buss/sms/tSmsCountInfoList");
	}
	
	/**
	 * 单条发送短信条数详情列表（验证码，订单等短信）页面跳转
	 * @return
	 */
	@RequestMapping(params = "singlePushList")
	public ModelAndView singlePushList(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){
			return new ModelAndView("com/buss/sms/tSmsSingleListOfRet");
		}
		return new ModelAndView("com/buss/sms/tSmsSingleList");
	}


	@RequestMapping(params = "datagrid")
	public void datagrid(TSmsCountInfoEntity tSmsCountInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSmsCountInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSmsCountInfo, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
		
	}
	@RequestMapping(params = "datagrid2")
	public void datagrid2(TSUserMessage tSmsUserMessage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUserMessage.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSmsUserMessage, request.getParameterMap());
		try{
			//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
		
	}

	
}
