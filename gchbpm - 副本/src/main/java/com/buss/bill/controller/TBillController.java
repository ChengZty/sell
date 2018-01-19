package com.buss.bill.controller;
import com.buss.bill.entity.TBillEntity;
import com.buss.bill.service.TBillServiceI;

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
 * @Description: 账单
 * @author onlineGenerator
 * @date 2016-09-17 20:30:30
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBillController")
public class TBillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBillController.class);

	@Autowired
	private TBillServiceI tBillService;
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
	 * 账单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBill")
	public ModelAndView tBill(HttpServletRequest request) {
		return new ModelAndView("com/buss/bill/tBillList");
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
	public void datagrid(TBillEntity tBill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TBillEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBill, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			String name = request.getParameter("name");
			String phoneNo = request.getParameter("phoneNo");
			String businessDate_begin = request.getParameter("businessDate_begin");
			String businessDate_end = request.getParameter("businessDate_end");
			String billType = request.getParameter("billType");
			String orderNo = request.getParameter("orderNo");
			String sql ="SELECT b.id,ifnull(b.bill_no,'') as billNo,b.business_date as businessDate, b.phone_no as phoneNo,b.bill_amount as billAmount,b.bill_type as billType,ifnull(b.order_id,'') as orderId, "
				+" b.user_id as userId,ifnull(b.order_no,'') as orderNo,p.name from t_bill b LEFT JOIN t_person p on  b.user_id= p.user_Id where b.status = 'A' and p.user_type = '04' ";
			if(StringUtil.isNotEmpty(retailerId)){
				sql +=" and b.retailer_id ='"+retailerId+"'";
			}
			if(StringUtil.isNotEmpty(name)){
				sql +=" and p.name like '%"+name+"%'";
			}
			if(StringUtil.isNotEmpty(orderNo)){
				sql +=" and b.order_no like '%"+orderNo+"%'";
			}
			if(StringUtil.isNotEmpty(phoneNo)){
				sql +=" and b.phone_no = '"+phoneNo+"'";
			}
			if(StringUtil.isNotEmpty(billType)){
				sql +=" and b.bill_type = '"+billType+"'";
			}
			if(StringUtil.isNotEmpty(businessDate_begin)){
				sql +=" and b.business_date >='"+businessDate_begin+" 00:00:00'";
			}
			if(StringUtil.isNotEmpty(businessDate_end)){
				sql +=" and b.business_date <='"+businessDate_end+" 23:59:59'";
			}
			String sort = dataGrid.getSort();
			if(StringUtil.isEmpty(sort)){
				sql +=" order by b.business_date desc ";
			}else{
				sql +=" order by "+sort+" "+dataGrid.getOrder();
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				String countSql = "select count(1) from t_bill b LEFT JOIN t_person p on b.user_id= p.user_Id where b.status = 'A' and p.user_type = '04' ";
				if(StringUtil.isNotEmpty(retailerId)){
					countSql +=" and b.retailer_id ='"+retailerId+"'";
				}
				if(StringUtil.isNotEmpty(name)){
					countSql +=" and p.name like '%"+name+"%'";
				}
				if(StringUtil.isNotEmpty(orderNo)){
					countSql +=" and b.order_no like '%"+orderNo+"%'";
				}
				if(StringUtil.isNotEmpty(phoneNo)){
					countSql +=" and b.phone_no = '"+phoneNo+"'";
				}
				if(StringUtil.isNotEmpty(billType)){
					sql +=" and b.bill_type = '"+billType+"'";
				}
				if(StringUtil.isNotEmpty(businessDate_begin)){
					sql +=" and b.business_date >='"+businessDate_begin+" 00:00:00'";
				}
				if(StringUtil.isNotEmpty(businessDate_end)){
					sql +=" and b.business_date <='"+businessDate_end+" 23:59:59'";
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
//		this.tBillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除账单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBillEntity tBill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBill = systemService.flushEntity(TBillEntity.class, tBill.getId());
		message = "账单删除成功";
		try{
			tBillService.delete(tBill);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "账单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除账单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "账单删除成功";
		try{
			for(String id:ids.split(",")){
				TBillEntity tBill = systemService.flushEntity(TBillEntity.class, 
				id
				);
				tBillService.delete(tBill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "账单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加账单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBillEntity tBill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "账单添加成功";
		try{
			tBillService.save(tBill);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "账单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新账单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBillEntity tBill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "账单更新成功";
		TBillEntity t = tBillService.get(TBillEntity.class, tBill.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBill, t);
			tBillService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "账单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 账单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBillEntity tBill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBill.getId())) {
			tBill = tBillService.flushEntity(TBillEntity.class, tBill.getId());
			req.setAttribute("tBillPage", tBill);
		}
		return new ModelAndView("com/buss/bill/tBill-add");
	}
	/**
	 * 账单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBillEntity tBill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBill.getId())) {
			tBill = tBillService.flushEntity(TBillEntity.class, tBill.getId());
			req.setAttribute("tBillPage", tBill);
		}
		return new ModelAndView("com/buss/bill/tBill-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBillController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBillEntity tBill,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBillEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBill, request.getParameterMap());
		List<TBillEntity> tBills = this.tBillService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"账单");
		modelMap.put(NormalExcelConstants.CLASS,TBillEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("账单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBills);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBillEntity tBill,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "账单");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBillEntity.class);
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
				List<TBillEntity> listTBillEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBillEntity.class,params);
				for (TBillEntity tBill : listTBillEntitys) {
					tBillService.save(tBill);
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
