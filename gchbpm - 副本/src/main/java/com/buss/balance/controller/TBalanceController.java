package com.buss.balance.controller;
import com.buss.balance.entity.TBalanceEntity;
import com.buss.balance.service.TBalanceServiceI;

import java.util.ArrayList;
import java.util.List;
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
 * @Description: 余额
 * @author onlineGenerator
 * @date 2016-09-17 20:20:21
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBalanceController")
public class TBalanceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBalanceController.class);

	@Autowired
	private TBalanceServiceI tBalanceService;
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
	 * 余额列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBalance")
	public ModelAndView tBalance(HttpServletRequest request) {
		return new ModelAndView("com/buss/balance/tBalanceList");
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
	public void datagrid(TBalanceEntity tBalance,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TBalanceEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBalance, request.getParameterMap());
		try{
			String retailerId = ResourceUtil.getRetailerId();
			String name = request.getParameter("name");
			String phoneNo = request.getParameter("phoneNo");
			StringBuffer sql = new StringBuffer("select b.total_balance as totalBalance,b.available_balance as availableBalance,b.frozen_balance as frozenBalance,b.balance_status as balanceStatus, ")
				.append(" b.discount,p.name,p.phone_no as phoneNo from t_balance b LEFT JOIN t_person p on  b.user_id= p.user_Id where b.status = 'A' and p.user_type = '04'  ");
			if(StringUtil.isNotEmpty(retailerId)){
				sql.append(" and b.retailer_id ='").append(retailerId).append("'");
			}
			if(StringUtil.isNotEmpty(name)){
				sql.append(" and p.name like '%").append(name).append("%'");
			}
			if(StringUtil.isNotEmpty(phoneNo)){
				sql.append(" and p.phone_no = '").append(phoneNo).append("'");
			}
			String sort = dataGrid.getSort();
			if(StringUtil.isEmpty(sort)){
				sql.append(" order by b.create_date desc");
			}else{
				sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder());
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			sql=null;
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				String countSql = "select count(1) from t_balance b LEFT JOIN t_person p on  b.user_id= p.user_Id where b.status = 'A' and p.user_type = '04' ";
				if(StringUtil.isNotEmpty(retailerId)){
					countSql +=" and b.retailer_id ='"+retailerId+"'";
				}
				if(StringUtil.isNotEmpty(name)){
					countSql +=" and p.name like '%"+name+"%'";
				}
				if(StringUtil.isNotEmpty(phoneNo)){
					countSql +=" and p.phone_no = '"+phoneNo+"'";
				}
				total = this.systemService.getCountForJdbc(countSql).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
//		cq.add();
//		this.tBalanceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除余额
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBalanceEntity tBalance, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBalance = systemService.flushEntity(TBalanceEntity.class, tBalance.getId());
		message = "余额删除成功";
		try{
			tBalanceService.delete(tBalance);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "余额删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除余额
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "余额删除成功";
		try{
			for(String id:ids.split(",")){
				TBalanceEntity tBalance = systemService.flushEntity(TBalanceEntity.class, 
				id
				);
				tBalanceService.delete(tBalance);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "余额删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加余额
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBalanceEntity tBalance, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "余额添加成功";
		try{
			tBalanceService.save(tBalance);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "余额添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新余额
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBalanceEntity tBalance, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "余额更新成功";
		TBalanceEntity t = tBalanceService.get(TBalanceEntity.class, tBalance.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBalance, t);
			tBalanceService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "余额更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 余额新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBalanceEntity tBalance, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBalance.getId())) {
			tBalance = tBalanceService.flushEntity(TBalanceEntity.class, tBalance.getId());
			req.setAttribute("tBalancePage", tBalance);
		}
		return new ModelAndView("com/buss/balance/tBalance-add");
	}
	/**
	 * 余额编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBalanceEntity tBalance, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBalance.getId())) {
			tBalance = tBalanceService.flushEntity(TBalanceEntity.class, tBalance.getId());
			req.setAttribute("tBalancePage", tBalance);
		}
		return new ModelAndView("com/buss/balance/tBalance-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBalanceController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBalanceEntity tBalance,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBalanceEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBalance, request.getParameterMap());
		List<TBalanceEntity> tBalances = this.tBalanceService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"余额");
		modelMap.put(NormalExcelConstants.CLASS,TBalanceEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("余额列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBalances);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBalanceEntity tBalance,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "余额");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBalanceEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TBalanceEntity> listTBalanceEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBalanceEntity.class,params);
				for (TBalanceEntity tBalance : listTBalanceEntitys) {
					tBalanceService.save(tBalance);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
