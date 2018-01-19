package com.buss.activity.controller;
import com.buss.activity.entity.TGiftRuleEntity;
import com.buss.activity.service.TGiftRuleServiceI;
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
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
 * @Description: 赠品规则
 * @author onlineGenerator
 * @date 2016-12-23 10:30:29
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGiftRuleController")
public class TGiftRuleController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGiftRuleController.class);

	@Autowired
	private TGiftRuleServiceI tGiftRuleService;
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
	 * 赠品规则列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("userType", user.getUserType());
		return new ModelAndView("com/buss/activity/tGiftRuleList");
	}

	/**
	 * 赠品规则审核列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "auditList")
	public ModelAndView auditList(HttpServletRequest request) {
		return new ModelAndView("com/buss/activity/tGiftRuleAuditList");
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
	public void datagrid(TGiftRuleEntity tGiftRule,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGiftRuleEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGiftRule, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGiftRuleService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除赠品规则
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
//		tGiftRule = systemService.flushEntity(TGiftRuleEntity.class, tGiftRule.getId());
		message = "赠品规则删除成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_gift_rule set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id = '")
			.append(id).append("'");
			tGiftRuleService.updateBySqlString(sql.toString());
//			tGiftRuleService.updateEntitie(tGiftRule);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "赠品规则删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除赠品规则
	 * 
	 * @return
	 */
//	 @RequestMapping(params = "doBatchDel")
//	@ResponseBody
//	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
//		AjaxJson j = new AjaxJson();
//		message = "赠品规则删除成功";
//		try{
//			for(String id:ids.split(",")){
//				TGiftRuleEntity tGiftRule = systemService.flushEntity(TGiftRuleEntity.class, 
//				id
//				);
//				tGiftRuleService.delete(tGiftRule);
//				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "赠品规则删除失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}


	/**
	 * 添加赠品规则
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TGiftRuleEntity tGiftRule, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "赠品规则添加成功";
		try{
			tGiftRuleService.saveGiftRule(tGiftRule);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "赠品规则添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新赠品规则
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TGiftRuleEntity tGiftRule, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "赠品规则更新成功";
		TGiftRuleEntity t = tGiftRuleService.get(TGiftRuleEntity.class, tGiftRule.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tGiftRule, t);
			tGiftRuleService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "赠品规则更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 赠品规则新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TGiftRuleEntity tGiftRule, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGiftRule.getId())) {
			tGiftRule = tGiftRuleService.flushEntity(TGiftRuleEntity.class, tGiftRule.getId());
			req.setAttribute("tGiftRulePage", tGiftRule);
		}
		return new ModelAndView("com/buss/activity/tGiftRule-add");
	}
	/**
	 * 赠品规则编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TGiftRuleEntity tGiftRule, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGiftRule.getId())) {
			tGiftRule = tGiftRuleService.flushEntity(TGiftRuleEntity.class, tGiftRule.getId());
			req.setAttribute("tGiftRulePage", tGiftRule);
		}
		return new ModelAndView("com/buss/activity/tGiftRule-update");
	}
	
	 /**
	  * 审核赠品规则
	  * @return
	  */
	 @RequestMapping(params = "doAudit")
	 @ResponseBody
	 public AjaxJson doAudit(String id ,HttpServletRequest request){
		 AjaxJson j = new AjaxJson();
		 message = "赠品规则审核成功";
		 try{
			 Long count = this.systemService.getCountForJdbc(" select count(1) from t_gift_rule_goods where status = 'A' and gift_Rule_Id = '"+id+"'");
				if(count!=1){
					message = "还没有选择赠品，请检查";
					j.setMsg(message);
					return j;
				}
			 TSUser user = ResourceUtil.getSessionUserName();
				StringBuffer sql = new StringBuffer("update t_gift_rule set update_by = '").append(user.getUserName()).append("',update_name = '")
					.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',audit_id = '")
					.append(user.getId()).append("',auditor = '").append(user.getRealName()).append("',audit_time = '").append(Utility.getCurrentTimestamp())
					.append("',rule_status = '2'  where id ='"+id+"'");
			 systemService.updateBySqlString(sql.toString());
			 systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "赠品规则审核失败";
			 throw new BusinessException(e.getMessage());
		 }
		 j.setMsg(message);
		 return j;
	 }
	 
	 /**
	  * 下架赠品规则
	  * @return
	  */
	 @RequestMapping(params = "doDown")
	 @ResponseBody
	 public AjaxJson doDown(String id ,HttpServletRequest request){
		 AjaxJson j = new AjaxJson();
		 message = "下架赠品规则成功";
		 try{
			 TSUser user = ResourceUtil.getSessionUserName();
				StringBuffer sql = new StringBuffer("update t_gift_rule set update_by = '").append(user.getUserName()).append("',update_name = '")
					.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',rule_status = '3'  where id ='"+id+"'");
			 systemService.updateBySqlString(sql.toString());
			 systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "下架赠品规则失败";
			 throw new BusinessException(e.getMessage());
		 }
		 j.setMsg(message);
		 return j;
	 }
}
