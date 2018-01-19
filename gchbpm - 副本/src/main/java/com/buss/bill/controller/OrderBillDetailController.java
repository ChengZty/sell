package com.buss.bill.controller;
import java.io.IOException;
import java.util.ArrayList;
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
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.buss.bill.entity.OrderBillDetailEntity;
import com.buss.bill.service.OrderBillDetailServiceI;



/**   
 * @Title: Controller
 * @Description: 结算明细
 * @author onlineGenerator
 * @date 2016-04-12 16:31:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/orderBillDetailController")
public class OrderBillDetailController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OrderBillDetailController.class);

	@Autowired
	private OrderBillDetailServiceI orderBillDetailService;
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
	 * 
	 * 零售商订单结算主页 
	 * 账单明细
	 * @return
	 */
	@RequestMapping(params = "storeBillList")
	public ModelAndView storeBillList(HttpServletRequest request) {
		return new ModelAndView("com/buss/bill/orderBillDetailStoreMain");
	}
	
	/**
	 * 
	 * 零售商订单结算明细页 
	 * 账单明细
	 * @return
	 */
	@RequestMapping(params = "storeDataList")
	public ModelAndView storeDataList(HttpServletRequest request) {
		String obState = request.getParameter("obState");
		request.setAttribute("obState", obState);
		return new ModelAndView("com/buss/bill/orderBillDetailStoreData");
	}
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "storeDatagrid")
	public void storeDatagrid(OrderBillDetailEntity orderBillDetail,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OrderBillDetailEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, orderBillDetail, request.getParameterMap());
		try
		{
			String obState = request.getParameter("obState");
			if(StringUtil.isNotEmpty(obState)){
				cq.eq("obState", obState);
			}
			TSUser user = ResourceUtil.getSessionUserName();
			if(Utility.isNotEmpty(user)){
				cq.eq("obStoreId", user.getId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.orderBillDetailService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	
	/**
	 * 零售商账单
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/bill/orderBillDetailList");
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
	public void datagrid(OrderBillDetailEntity orderBillDetail,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OrderBillDetailEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, orderBillDetail, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.orderBillDetailService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除结算明细
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(OrderBillDetailEntity orderBillDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		orderBillDetail = systemService.flushEntity(OrderBillDetailEntity.class, orderBillDetail.getId());
		message = "结算明细删除成功";
		try{
			orderBillDetailService.delete(orderBillDetail);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "结算明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除结算明细
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "结算明细删除成功";
		try{
			for(String id:ids.split(",")){
				OrderBillDetailEntity orderBillDetail = systemService.flushEntity(OrderBillDetailEntity.class, 
				id
				);
				orderBillDetailService.delete(orderBillDetail);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "结算明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加结算明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(OrderBillDetailEntity orderBillDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "结算明细添加成功";
		try{
			orderBillDetailService.save(orderBillDetail);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "结算明细添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新结算明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(OrderBillDetailEntity orderBillDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "结算明细更新成功";
		OrderBillDetailEntity t = orderBillDetailService.get(OrderBillDetailEntity.class, orderBillDetail.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(orderBillDetail, t);
			orderBillDetailService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "结算明细更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 结算明细新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(OrderBillDetailEntity orderBillDetail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(orderBillDetail.getId())) {
			orderBillDetail = orderBillDetailService.flushEntity(OrderBillDetailEntity.class, orderBillDetail.getId());
			req.setAttribute("orderBillDetailPage", orderBillDetail);
		}
		return new ModelAndView("com/buss/bill/orderBillDetail-add");
	}
	/**
	 * 结算明细编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(OrderBillDetailEntity orderBillDetail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(orderBillDetail.getId())) {
			orderBillDetail = orderBillDetailService.flushEntity(OrderBillDetailEntity.class, orderBillDetail.getId());
			req.setAttribute("orderBillDetailPage", orderBillDetail);
		}
		return new ModelAndView("com/buss/bill/orderBillDetail-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","orderBillDetailController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(OrderBillDetailEntity orderBillDetail,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(OrderBillDetailEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, orderBillDetail, request.getParameterMap());
		List<OrderBillDetailEntity> orderBillDetails = this.orderBillDetailService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"结算明细");
		modelMap.put(NormalExcelConstants.CLASS,OrderBillDetailEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("结算明细列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,orderBillDetails);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(OrderBillDetailEntity orderBillDetail,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"结算明细");
    	modelMap.put(NormalExcelConstants.CLASS,OrderBillDetailEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("结算明细列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
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
				List<OrderBillDetailEntity> listOrderBillDetailEntitys = ExcelImportUtil.importExcel(file.getInputStream(),OrderBillDetailEntity.class,params);
				for (OrderBillDetailEntity orderBillDetail : listOrderBillDetailEntitys) {
					orderBillDetailService.save(orderBillDetail);
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
