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
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
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

import com.buss.bill.entity.TCommissionCloudEntity;
import com.buss.bill.entity.TCommissionCloudInfoEntity;
import com.buss.bill.service.TCommissionCloudInfoServiceI;
import com.buss.bill.service.TCommissionCloudServiceI;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 云商实收分配定义表
 * @author onlineGenerator
 * @date 2016-04-06 16:34:21
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tCommissionCloudController")
public class TCommissionCloudController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TCommissionCloudController.class);

	@Autowired
	private TCommissionCloudServiceI tCommissionCloudService;
	@Autowired
	private TCommissionCloudInfoServiceI tCommissionCloudInfoService;
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
	 * 零售商列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
//		getTopCatList(request);
//		return new ModelAndView("com/buss/bill/tCommissionCloudList");
		return new ModelAndView("com/buss/bill/tCommissionCloudList2");
	}
	
	/**
	 * 结算规则列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cloudRuleList")
	public ModelAndView cloudRuleList(HttpServletRequest request) {
		getTopCatList(request);
		request.setAttribute("store_Id", request.getParameter("store_Id"));
		return new ModelAndView("com/buss/bill/tCommissionCloudList");
//		return new ModelAndView("com/buss/bill/tCommissionCloudList2");
	}
	
	
	/**
	 * 获取一级分类
	 * @param request
	 */
	private void getTopCatList(HttpServletRequest request){
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
		cq.eq("level", 1);
		cq.addOrder("sort", SortDirection.asc);
		cq.add();
		List<TSCategoryEntity> catList = systemService.getListByCriteriaQuery(cq, false);
		request.setAttribute("catList",catList);
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
	public void datagrid(TCommissionCloudEntity tCommissionCloud,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TCommissionCloudEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tCommissionCloud, request.getParameterMap());
		try{
		//自定义追加查询条件
			String store_Id = request.getParameter("store_Id");
			cq.eq("storeId", store_Id);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tCommissionCloudService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除云商实收分配定义表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TCommissionCloudEntity tCommissionCloud, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tCommissionCloud = systemService.flushEntity(TCommissionCloudEntity.class, tCommissionCloud.getId());
		message = "云商实收分配定义表删除成功";
		try{
			tCommissionCloudService.delete(tCommissionCloud);
			
			
			tCommissionCloud.setStatus(common.GlobalConstants.STATUS_INACTIVE);
			this.tCommissionCloudService.updateEntitie(tCommissionCloud);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "云商实收分配定义表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除云商实收分配定义表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "云商实收分配定义表删除成功";
		try{
			for(String id:ids.split(",")){
				TCommissionCloudEntity tCommissionCloud = systemService.flushEntity(TCommissionCloudEntity.class, 
				id
				);
//				tCommissionCloudService.delete(tCommissionCloud);
				tCommissionCloud.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				this.tCommissionCloudService.updateEntitie(tCommissionCloud);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "云商实收分配定义表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加云商实收分配定义表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TCommissionCloudEntity tCommissionCloud, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "云商实收分配定义表添加成功";
		try
		{
			String sql = "select count(1) from t_commission_cloud where top_category_id='"+tCommissionCloud.getTopCategoryId()
			+"' and sub_category_id ='"+tCommissionCloud.getSubCategoryId()
			+"' and thrid_category_id='"+tCommissionCloud.getThridCategoryId()
			+"'  and brand_id='"+tCommissionCloud.getBrandId()+"' and status = 'A'";
			Long count = this.systemService.getCountForJdbc(sql);
			sql = null;
			if(1 > count)
			{
				tCommissionCloudService.saveCommission(tCommissionCloud);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				j.setSuccess(true);
			}else{
				message="该分配定义已存在，请不要重复添加";
				j.setSuccess(false);
			}
		}catch(Exception e){
			j.setSuccess(false);
			e.printStackTrace();
			message = "云商实收分配定义表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新云商实收分配定义表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TCommissionCloudEntity tCommissionCloud, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "云商实收分配定义表更新成功";
		TCommissionCloudEntity t = tCommissionCloudService.get(TCommissionCloudEntity.class, tCommissionCloud.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tCommissionCloud, t);
//			tCommissionCloudService.saveOrUpdate(t);
			tCommissionCloudService.saveOrUpdateCommission(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "云商实收分配定义表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 云商实收分配定义表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TCommissionCloudEntity tCommissionCloud, HttpServletRequest req) {
		String storeId = req.getParameter("store_Id");
		req.setAttribute("storeId", storeId);
//		if (StringUtil.isNotEmpty(tCommissionCloud.getId())) {
//			tCommissionCloud = tCommissionCloudService.flushEntity(TCommissionCloudEntity.class, tCommissionCloud.getId());
//			req.setAttribute("tCommissionCloudPage", tCommissionCloud);
//		}
		getTopCatList(req);
		getDicts(req);
		return new ModelAndView("com/buss/bill/tCommissionCloud-add");
	}
	
	/**
	 * 云商实收分配定义批量新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goBatchAdd")
	public ModelAndView goBatchAdd(TCommissionCloudEntity tCommissionCloud, HttpServletRequest req) {
		getTopCatList(req);
		getDicts(req);
		return new ModelAndView("com/buss/bill/tCommissionCloud-batchAdd");
	}
	
	/**
	 * 批量添加云商实收分配定义表
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doBatchAdd")
	@ResponseBody
	public AjaxJson doBatchAdd(TCommissionCloudEntity tCommissionCloud, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "云商实收分配定义添加成功";
		try
		{
			String[] thirdCategoryNames = tCommissionCloud.getThridCategoryName().split(",");
			String[] thirdCategoryIds = tCommissionCloud.getThridCategoryId().split(",");
			Long count = 0L;
			int n = thirdCategoryIds.length;
			if(n>0){
				StringBuffer ids = new StringBuffer();
				for(String id:thirdCategoryIds){
					ids.append("'").append(id).append("',");
				}
				ids.deleteCharAt(ids.length()-1);
				StringBuffer countSql = new StringBuffer();
				countSql.append("select count(1) from t_commission_cloud where top_category_id='").append(tCommissionCloud.getTopCategoryId())
				.append("' and sub_category_id ='").append(tCommissionCloud.getSubCategoryId()).append("' and thrid_category_id in(").append(ids)
				.append(") and brand_id='").append(tCommissionCloud.getBrandId()).append("' and status = 'A'");
				count= this.systemService.getCountForJdbc(countSql.toString());
				ids = null;
				countSql = null;
				if(count<1)
				{
					for(int i=0;i<n;i++){
						tCommissionCloud.setThridCategoryId(thirdCategoryIds[i]);
						tCommissionCloud.setThridCategoryName(thirdCategoryNames[i]);
						tCommissionCloudService.saveCommission(tCommissionCloud);
					}
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
					j.setSuccess(true);
				}else{
					message = "分配定义已存在，请检查";
					j.setSuccess(false);
				}
			}
		}catch(Exception e){
			j.setSuccess(false);
			e.printStackTrace();
			message = "云商实收分配定义添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 佣金分配类型
	 * @param req
	 */
    private void  getDicts(HttpServletRequest req){
//    	List<TSType> typeList = TSTypegroup.allTypes.get("commitype");
//    	req.setAttribute("typeList", typeList);
    }
	
	/**
	 * 云商实收分配定义表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TCommissionCloudEntity tCommissionCloud, HttpServletRequest req) {
		/*if (StringUtil.isNotEmpty(tCommissionCloud.getId())) {
			tCommissionCloud = tCommissionCloudService.flushEntity(TCommissionCloudEntity.class, tCommissionCloud.getId());
			req.setAttribute("tCommissionCloudPage", tCommissionCloud);
			List<TCommissionCloudInfoEntity> dataList = new ArrayList<TCommissionCloudInfoEntity>();
			List<TCommissionCloudInfoEntity> cinfoList = tCommissionCloudInfoService.findByProperty(TCommissionCloudInfoEntity.class, "cid", tCommissionCloud.getId());
			List<TSType> typeList = TSTypegroup.allTypes.get("commitype");
			if(Utility.isEmpty(cinfoList)){
				cinfoList = new ArrayList<TCommissionCloudInfoEntity>();
			}
			for (TSType tsType : typeList)
			{
				boolean flag = true;
				for(TCommissionCloudInfoEntity info : cinfoList){
					if(tsType.getTypecode().equals(info.getCtype()))
					{
						flag = false;
						info.setCtypeName(tsType.getTypename());
						info.setCtype(tsType.getTypecode());
						dataList.add(info);
						break;
					}
				}
				if(flag){
					TCommissionCloudInfoEntity info  = new TCommissionCloudInfoEntity();
					info.setCtype(tsType.getTypecode());
					info.setCtypeName(tsType.getTypename());
					info.setCid(tCommissionCloud.getId());
					info.setStatus(GlobalConstants.STATUS_ACTIVE);
					dataList.add(info);
				}
			}
			tCommissionCloud.setCinfoList(dataList);
//			getTopCatList(req);
//			getSubCatList(req,tCommissionCloud.getTopCategoryId());
//			getThridCatList(req,tCommissionCloud.getSubCategoryId());
			//getDicts(req);
		}*/
		return new ModelAndView("com/buss/bill/tCommissionCloud-update");
	}
	
//	private void getThridCatList(HttpServletRequest req, String subCategoryId) {
//		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
//		cq.eq("pid", subCategoryId);
//		cq.add();
//		List<TSCategoryEntity> catList = systemService.getListByCriteriaQuery(cq, false);
//		req.setAttribute("thridCatList",catList);
//	}
//
//	private void getSubCatList(HttpServletRequest req, String getTopCategoryId) {
//		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
//		cq.eq("pid", getTopCategoryId);
//		cq.add();
//		List<TSCategoryEntity> catList = systemService.getListByCriteriaQuery(cq, false);
//		req.setAttribute("subCatList",catList);
//	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tCommissionCloudController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TCommissionCloudEntity tCommissionCloud,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TCommissionCloudEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tCommissionCloud, request.getParameterMap());
		List<TCommissionCloudEntity> tCommissionClouds = this.tCommissionCloudService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"云商实收分配定义表");
		modelMap.put(NormalExcelConstants.CLASS,TCommissionCloudEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("云商实收分配定义表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tCommissionClouds);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TCommissionCloudEntity tCommissionCloud,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"云商实收分配定义表");
    	modelMap.put(NormalExcelConstants.CLASS,TCommissionCloudEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("云商实收分配定义表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TCommissionCloudEntity> listTCommissionCloudEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TCommissionCloudEntity.class,params);
				for (TCommissionCloudEntity tCommissionCloud : listTCommissionCloudEntitys) {
					tCommissionCloudService.save(tCommissionCloud);
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
	
	/**
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(params = "goReview")
	public ModelAndView goReview(TCommissionCloudEntity tCommissionCloud, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tCommissionCloud.getId())) {
			tCommissionCloud = tCommissionCloudService.flushEntity(TCommissionCloudEntity.class, tCommissionCloud.getId());
			req.setAttribute("tCommissionCloudPage", tCommissionCloud);
			List<TCommissionCloudInfoEntity> dataList = new ArrayList<TCommissionCloudInfoEntity>();
			List<TCommissionCloudInfoEntity> cinfoList = tCommissionCloudInfoService.findByProperty(TCommissionCloudInfoEntity.class, "cid", tCommissionCloud.getId());
			/*List<TSType> typeList = TSTypegroup.allTypes.get("commitype");
			if(Utility.isEmpty(cinfoList)){
				cinfoList = new ArrayList<TCommissionCloudInfoEntity>();
			}
			for (TSType tsType : typeList)
			{
				boolean flag = true;
				for(TCommissionCloudInfoEntity info : cinfoList){
					if(tsType.getTypecode().equals(info.getCtype()))
					{
						flag = false;
						info.setCtypeName(tsType.getTypename());
						info.setCtype(tsType.getTypecode());
						dataList.add(info);
						break;
					}
				}
				if(flag){
					TCommissionCloudInfoEntity info  = new TCommissionCloudInfoEntity();
					info.setCtype(tsType.getTypecode());
					info.setCtypeName(tsType.getTypename());
					info.setCid(tCommissionCloud.getId());
					info.setStatus(GlobalConstants.STATUS_ACTIVE);
					dataList.add(info);
				}
			}*/
			tCommissionCloud.setCinfoList(dataList);
			//getTopCatList(req);
			//getSubCatList(req,tCommissionCloud.getTopCategoryId());
			//getThridCatList(req,tCommissionCloud.getSubCategoryId());
		}
		return new ModelAndView("com/buss/bill/tCommissionCloud-review");
	}
	
}
