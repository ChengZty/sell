package com.buss.price.controller;
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

import com.buss.price.entity.TPriceChangeEntity;
import com.buss.price.service.TPriceChangeServiceI;



/**   
 * @Title: Controller
 * @Description: 价格变动历史
 * @author onlineGenerator
 * @date 2016-04-02 15:06:02
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPriceChangeController")
public class TPriceChangeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPriceChangeController.class);

	@Autowired
	private TPriceChangeServiceI tPriceChangeService;
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
	 * 价格变动历史列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/price/tPriceChangeList");
	}
	
	/**
	 * 调价历史TAB页面跳转
	 * @return
	 */
	@RequestMapping(params = "tPriceChangeTabs")
	public ModelAndView tPriceChangeTabs(HttpServletRequest request) {
		return new ModelAndView("com/buss/price/tPriceChangeTabs");
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
	public void datagrid(TPriceChangeEntity tPriceChange,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPriceChangeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPriceChange, request.getParameterMap());
		try{
		//自定义追加查询条件
			TSUser user = ResourceUtil.getSessionUserName();
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
					cq.eq("retailerId", user.getId());
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				cq.eq("retailerId", user.getRetailerId());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPriceChangeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除价格变动历史
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPriceChangeEntity tPriceChange, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPriceChange = systemService.flushEntity(TPriceChangeEntity.class, tPriceChange.getId());
		message = "价格变动历史删除成功";
		try{
			tPriceChangeService.delete(tPriceChange);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "价格变动历史删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除价格变动历史
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "价格变动历史删除成功";
		try{
			for(String id:ids.split(",")){
				TPriceChangeEntity tPriceChange = systemService.flushEntity(TPriceChangeEntity.class, 
				id
				);
				tPriceChangeService.delete(tPriceChange);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "价格变动历史删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加价格变动历史
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPriceChangeEntity tPriceChange, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "价格变动历史添加成功";
		try{
			tPriceChangeService.save(tPriceChange);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "价格变动历史添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新价格变动历史
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPriceChangeEntity tPriceChange, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "价格变动历史更新成功";
		TPriceChangeEntity t = tPriceChangeService.get(TPriceChangeEntity.class, tPriceChange.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPriceChange, t);
			tPriceChangeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "价格变动历史更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 价格变动历史新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TPriceChangeEntity tPriceChange, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPriceChange.getId())) {
			tPriceChange = tPriceChangeService.flushEntity(TPriceChangeEntity.class, tPriceChange.getId());
			req.setAttribute("tPriceChangePage", tPriceChange);
		}
		return new ModelAndView("com/buss/price/tPriceChange-add");
	}
	/**
	 * 价格变动历史编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPriceChangeEntity tPriceChange, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPriceChange.getId())) {
			tPriceChange = tPriceChangeService.flushEntity(TPriceChangeEntity.class, tPriceChange.getId());
			req.setAttribute("tPriceChangePage", tPriceChange);
		}
		return new ModelAndView("com/buss/price/tPriceChange-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tPriceChangeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPriceChangeEntity tPriceChange,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPriceChangeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPriceChange, request.getParameterMap());
		List<TPriceChangeEntity> tPriceChanges = this.tPriceChangeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"价格变动历史");
		modelMap.put(NormalExcelConstants.CLASS,TPriceChangeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("价格变动历史列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPriceChanges);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPriceChangeEntity tPriceChange,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"价格变动历史");
    	modelMap.put(NormalExcelConstants.CLASS,TPriceChangeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("价格变动历史列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TPriceChangeEntity> listTPriceChangeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TPriceChangeEntity.class,params);
				for (TPriceChangeEntity tPriceChange : listTPriceChangeEntitys) {
					tPriceChangeService.save(tPriceChange);
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
