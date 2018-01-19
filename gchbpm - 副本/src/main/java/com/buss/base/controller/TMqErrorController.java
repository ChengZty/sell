package com.buss.base.controller;
import com.buss.base.entity.TMqErrorEntity;
import com.buss.base.service.TMqErrorServiceI;
import com.buss.news.entity.TNewsEntity;

import java.util.ArrayList;
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
 * @Description: MQ通知
 * @author onlineGenerator
 * @date 2017-01-10 18:08:07
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tMqErrorController")
public class TMqErrorController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TMqErrorController.class);

	@Autowired
	private TMqErrorServiceI tMqErrorService;
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
	 * MQ通知列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/base/tMqErrorList");
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
	public void datagrid(TMqErrorEntity tMqError,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		String sql = "SELECT id,add_time as addTime,order_id as orderId,order_type as orderType,method,mq_msg as mqMsg,error_msg as errorMsg,"
//			+"queue_name as queueName,deal_status as dealStatus,ifnull(deal_time,'') as dealTime from t_mq_error";
//		String countSql = "SELECT count(1) from t_mq_error";
//
//		List<Map<String, Object>> resultList = systemService.findForJdbc( sql, dataGrid.getPage(), dataGrid.getRows());
//		int total = 0;
//		if(!Utility.isEmpty(resultList)){
//			total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
//		}else{
//			resultList = new ArrayList<Map<String,Object>>();
//		}
//		dataGrid.setResults(resultList);
//		dataGrid.setTotal(total);
		CriteriaQuery cq = new CriteriaQuery(TMqErrorEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tMqError, request.getParameterMap());
		try{
		//自定义追加查询条件
//			cq.eq("dealStatus", "0");
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tMqErrorService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 处理MQ通知
	 * @return
	 */
	@RequestMapping(params = "doDeal")
	@ResponseBody
	public AjaxJson doDeal(TMqErrorEntity tMqError, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tMqError = systemService.flushEntity(TMqErrorEntity.class, tMqError.getId());
		tMqError.setDealTime(Utility.getCurrentTimestamp());
		message = "MQ通知处理成功";
		try{
			tMqErrorService.doDeal(tMqError);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "MQ通知处理失败";
			tMqError.setDealStatus(TMqErrorEntity.DEAL_STATUS_2);
			tMqErrorService.updateEntitie(tMqError);
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除MQ通知
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "MQ通知删除成功";
		try{
			for(String id:ids.split(",")){
				TMqErrorEntity tMqError = systemService.flushEntity(TMqErrorEntity.class, 
				id
				);
				tMqErrorService.delete(tMqError);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "MQ通知删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加MQ通知
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TMqErrorEntity tMqError, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "MQ通知添加成功";
		try{
			tMqErrorService.save(tMqError);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "MQ通知添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新MQ通知
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TMqErrorEntity tMqError, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "MQ通知更新成功";
		TMqErrorEntity t = tMqErrorService.get(TMqErrorEntity.class, tMqError.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tMqError, t);
			tMqErrorService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "MQ通知更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * MQ通知新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TMqErrorEntity tMqError, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tMqError.getId())) {
			tMqError = tMqErrorService.flushEntity(TMqErrorEntity.class, tMqError.getId());
			req.setAttribute("tMqErrorPage", tMqError);
		}
		return new ModelAndView("com/buss/base/tMqError-add");
	}
	/**
	 * MQ通知编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TMqErrorEntity tMqError, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tMqError.getId())) {
			tMqError = tMqErrorService.flushEntity(TMqErrorEntity.class, tMqError.getId());
			req.setAttribute("tMqErrorPage", tMqError);
		}
		return new ModelAndView("com/buss/base/tMqError-update");
	}
	
	
}
