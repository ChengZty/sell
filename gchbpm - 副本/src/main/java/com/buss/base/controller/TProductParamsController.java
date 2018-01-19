package com.buss.base.controller;
import com.buss.base.entity.TAllGoodsParamsEntity;
import com.buss.base.entity.TProductParamsEntity;
import com.buss.base.service.TProductParamsServiceI;

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
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 产品信息参数
 * @author onlineGenerator
 * @date 2016-04-23 14:51:35
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tProductParamsController")
public class TProductParamsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TProductParamsController.class);

	@Autowired
	private TProductParamsServiceI tProductParamsService;
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
	 * 产品信息参数列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String category_Id = request.getParameter("category_Id");
		request.setAttribute("category_Id", category_Id);
		return new ModelAndView("com/buss/base/tProductParamsList");
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
	public void datagrid(TProductParamsEntity tProductParams,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TProductParamsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tProductParams, request.getParameterMap());
		try{
		//自定义追加查询条件
			String category_Id = request.getParameter("category_Id");
			if(!StringUtil.isEmpty(category_Id)){
				cq.eq("categoryId", category_Id);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tProductParamsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除产品信息参数表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TProductParamsEntity tProductParams, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tProductParams = systemService.flushEntity(TProductParamsEntity.class, tProductParams.getId());
		message = "产品信息参数删除成功";
		try{
			tProductParamsService.delete(tProductParams);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "产品信息参数删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除产品信息参数
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "产品信息参数删除成功";
		try{
			for(String id:ids.split(",")){
				TProductParamsEntity tProductParams = systemService.flushEntity(TProductParamsEntity.class, 
				id
				);
				tProductParamsService.delete(tProductParams);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "产品信息参数删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加产品信息参数
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TProductParamsEntity tProductParams, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "产品信息参数添加成功";
		try{
			tProductParams.setParamCode(tProductParams.getRowNum()+"_"+tProductParams.getRowIndexNum());
			Boolean flag = checkCodeExist(tProductParams);
			if(flag){
				message = "存在相同的排序和行内排序组合";
			}else{
				tProductParams.setParamValues(StringUtil.replaceBlank(tProductParams.getParamValues()));
				tProductParamsService.save(tProductParams);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "产品信息参数添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新产品信息参数
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TProductParamsEntity tProductParams, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "产品信息参数更新成功";
		TProductParamsEntity t = tProductParamsService.get(TProductParamsEntity.class, tProductParams.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tProductParams, t);
			t.setParamCode(tProductParams.getRowNum()+"_"+tProductParams.getRowIndexNum());
			Boolean flag = checkCodeExist(t);
			if(flag){
				message = "存在相同的排序和行内排序组合";
			}else{
				t.setParamValues(StringUtil.replaceBlank(t.getParamValues()));
				tProductParamsService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "产品信息参数更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 产品信息参数新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TProductParamsEntity tProductParams, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tProductParams.getId())) {
			tProductParams = tProductParamsService.flushEntity(TProductParamsEntity.class, tProductParams.getId());
			req.setAttribute("tProductParamsPage", tProductParams);
		}
		req.setAttribute("category_Id", req.getParameter("category_Id"));
		return new ModelAndView("com/buss/base/tProductParams-add");
	}
	/**
	 * 产品信息参数编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TProductParamsEntity tProductParams, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tProductParams.getId())) {
			tProductParams = tProductParamsService.flushEntity(TProductParamsEntity.class, tProductParams.getId());
			req.setAttribute("tProductParamsPage", tProductParams);
		}
		return new ModelAndView("com/buss/base/tProductParams-update");
	}
	/*通过一级类目ID获取参数列表*/
	@RequestMapping(params = "findProductParamsByTopCategoryId")
	@ResponseBody
	public AjaxJson findProductParamsByTopCategoryId(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		List<TProductParamsEntity> list = tProductParamsService.findProductParamsByTopCategoryId(request.getParameter("category_id"));
		j.setObj(list);
		j.setMsg(message);
		return j;
	}
	
	/*检查code是否存在*/
	private boolean checkCodeExist(TProductParamsEntity tProductParams) {
		boolean flag = false;
		String id = tProductParams.getId();
		String categoryId = tProductParams.getCategoryId();
		String code = tProductParams.getParamCode();
		String countSql = "select count(1) from t_product_params where category_id ='"+categoryId+"' and param_code='"+code+"'";
		if(StringUtil.isNotEmpty(id)){
			countSql +=" and id <> '"+id+"'";
		}
		Long existNum  = tProductParamsService.getCountForJdbc(countSql);
		if(existNum>0){//存在
			flag=true;
		}
		return flag;
	}
	
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tAllGoodsParamsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TProductParamsEntity tProductParams,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TProductParamsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tProductParams, request.getParameterMap());
		List<TProductParamsEntity> tAllGoodsParamss = this.tProductParamsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"产品信息参数");
		modelMap.put(NormalExcelConstants.CLASS,TProductParamsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("产品信息参数列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tAllGoodsParamss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TProductParamsEntity tProductParams,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"产品信息参数");
    	modelMap.put(NormalExcelConstants.CLASS,TProductParamsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("产品信息参数列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TProductParamsEntity> listTAllGoodsParamsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TProductParamsEntity.class,params);
				for (TProductParamsEntity tProductParams : listTAllGoodsParamsEntitys) {
					tProductParamsService.save(tProductParams);
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
