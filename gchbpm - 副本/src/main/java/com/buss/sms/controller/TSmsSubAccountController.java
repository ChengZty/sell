package com.buss.sms.controller;
import java.util.Date;
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
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.sms.entity.TSmsCountInfoEntity;
import com.buss.sms.entity.TSmsSubAccountEntity;
import com.buss.sms.service.TSmsSendInfoServiceI;
import com.buss.sms.service.TSmsSubAccountServiceI;
import common.GlobalConstants;



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
@RequestMapping("/tSmsSubAccountController")
public class TSmsSubAccountController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSmsSubAccountController.class);

	@Autowired
	private TSmsSubAccountServiceI tSmsSubAccountService;
	@Autowired
	private TSmsSendInfoServiceI tSmsSendInfoService;
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
	 * 短信设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tSmsSubAccount")
	public ModelAndView tSmsSubAccount(HttpServletRequest request) {
		return new ModelAndView("com/buss/sms/tSmsSubAccountList");
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
	public void datagrid(TSmsSubAccountEntity tSmsSubAccount,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSmsSubAccountEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSmsSubAccount, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSmsSubAccountService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	

	/**
	 * 添加短信设置
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSmsSubAccountEntity tSmsSubAccount, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "短信账号添加成功";
		try{
			this.tSmsSubAccountService.saveAccount(tSmsSubAccount);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信账号添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新短信设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSmsSubAccountEntity tSmsSubAccount, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "短信账号更新成功";
		try {
			this.tSmsSubAccountService.updateAccount(tSmsSubAccount);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "短信账号更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新短信剩余条数
	 * @param ids
	 * @return
	 */
//	@RequestMapping(params = "doUpdateSurplusNumber")
//	@ResponseBody
//	public AjaxJson doUpdateSurplusNumber(HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		message = "短信账号剩余条数更新成功";
////		TSmsSubAccountEntity t = tSmsSubAccountService.get(TSmsSubAccountEntity.class, tSmsSubAccount.getId());
//		List<TSmsSubAccountEntity> list = systemService.findByProperty(TSmsSubAccountEntity.class, "status", GlobalConstants.STATUS_ACTIVE);
//		try {
//			for (TSmsSubAccountEntity t : list) {
////				String surplusNumber = tSmsSendInfoService.balance(t.getRetailerId());
////				t.setSmsNumber(Integer.parseInt(surplusNumber));//短信剩余条数;
//				tSmsSubAccountService.saveOrUpdate(t);
//				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//			}
//		} catch (Exception e) {
//			j.setSuccess(false);
//			e.printStackTrace();
//			message = "短信账号剩余条数更新失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}

	/**
	 * 短信设置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSmsSubAccountEntity tSmsSubAccount, HttpServletRequest req) {
		String retailerId = req.getParameter("retailerId");
		/*if (StringUtil.isNotEmpty(tSmsSubAccount.getId())) {
			tSmsSubAccount = tSmsSubAccountService.flushEntity(TSmsSubAccountEntity.class, tSmsSubAccount.getId());
			req.setAttribute("tSmsSubAccountPage", tSmsSubAccount);
		}*/
		req.setAttribute("retailerId", retailerId);
		TSmsSubAccountEntity  subAccount = systemService.findUniqueByProperty(TSmsSubAccountEntity.class, "retailerId", retailerId);
		if (Utility.isNotEmpty(subAccount)) {
			req.setAttribute("tSmsSubAccountPage", subAccount);
			return new ModelAndView("com/buss/sms/tSmsSubAccount-update");
		}else{
			return new ModelAndView("com/buss/sms/tSmsSubAccount-add");
		}
		
		
	}
	/**
	 * 短信设置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSmsSubAccountEntity tSmsSubAccount, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSmsSubAccount.getId())) {
			tSmsSubAccount = tSmsSubAccountService.flushEntity(TSmsSubAccountEntity.class, tSmsSubAccount.getId());
			req.setAttribute("tSmsSubAccountPage", tSmsSubAccount);
		}
		return new ModelAndView("com/buss/sms/tSmsSubAccount-update");
	}

	

	/**
	 * 零售商列表
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public ModelAndView storeUserList(HttpServletRequest request) {
		String smsNum = tSmsSendInfoService.balance();
		request.setAttribute("smsNum", smsNum);
		return new ModelAndView("com/buss/sms/retailerList");
	}

	/**
	 * easyui AJAX请求零售商列表数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "retailerListDatagrid")
	public void retailerListDatagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String username = request.getParameter("username");
		String userCode = request.getParameter("userCode");
		String realName = request.getParameter("realName");
		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer("SELECT count(1) from t_s_user tsu ");
		countSql.append(" LEFT JOIN t_sms_sub_account tssa ON tsu.id = tssa.retailer_id")
		.append(" where tsu.`status` = '").append(GlobalConstants.STATUS_ACTIVE).append("'")
		.append(" AND tsu.user_type = '").append(common.GlobalConstants.USER_TYPE_02).append("'");
//		.append(" AND tsu.user_status = '").append(org.jeecgframework.web.system.pojo.base.TSUser.USER_STATUS_ACTIVE).append("'")
//		.append(" AND tsu.retailer_type = '").append(TSUser.RETAILER_TYPE_REAL).append("'");
		
		sql.append(" SELECT ")
		.append(" tsu.id AS id, ")
		.append(" tsu.username AS username, ")
		.append(" tsu.user_code AS userCode, ")
		.append(" tsu.realname AS realName, ")
		.append(" ifnull(tssa.sms_number,'') AS smsNumber, ")
		.append(" ifnull(tssa.locking_number,'') AS lockingNumber, ")
		.append(" ifnull(tssa.company_autograph_name,'') AS companyAutographName ")
		.append(" FROM ")
		.append(" t_s_user tsu ")
		.append(" LEFT JOIN t_sms_sub_account tssa ON tsu.id = tssa.retailer_id")
		.append(" where tsu.`status` = '").append(GlobalConstants.STATUS_ACTIVE).append("'")
		.append(" AND tsu.user_type = '").append(common.GlobalConstants.USER_TYPE_02).append("'");
//		.append(" AND tsu.user_status = '").append(org.jeecgframework.web.system.pojo.base.TSUser.USER_STATUS_ACTIVE).append("'")
//		.append(" AND tsu.retailer_type = '").append(TSUser.RETAILER_TYPE_REAL).append("'");
		
		
		if(Utility.isNotEmpty(username)){
			sql.append(" AND tsu.username like '%").append(username).append("%'");
			countSql.append(" AND tsu.username like '%").append(username).append("%'");
		}
		if(Utility.isNotEmpty(userCode)){
			sql.append(" AND tsu.user_code like '%").append(userCode).append("%'");
			countSql.append(" AND tsu.user_code like '%").append(userCode).append("%'");
		}
		if(Utility.isNotEmpty(realName)){
			sql.append(" AND tsu.realname like '%").append(realName).append("%'");
			countSql.append(" AND tsu.realname like '%").append(realName).append("%'");
		}
		int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
		List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
		sql=null;
		dataGrid.setResults(resultList);
		dataGrid.setTotal(total);
		TagUtil.datagrid(response, dataGrid);
	}
}
