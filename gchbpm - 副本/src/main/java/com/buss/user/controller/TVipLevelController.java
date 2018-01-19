package com.buss.user.controller;
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
import org.jeecgframework.core.util.DateUtils;
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

import com.buss.user.entity.TVipLevelEntity;
import com.buss.user.service.TVipLevelServiceI;



/**   
 * @Title: Controller
 * @Description: 顾客VIP等级
 * @author onlineGenerator
 * @date 2016-03-29 14:11:30
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tVipLevelController")
public class TVipLevelController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TVipLevelController.class);

	@Autowired
	private TVipLevelServiceI tVipLevelService;
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
	 * 顾客VIP等级列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/tVipLevelList");
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
	public void datagrid(TVipLevelEntity tVipLevel,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TVipLevelEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tVipLevel, request.getParameterMap());
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
		this.tVipLevelService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	

	/**
	 * 删除顾客VIP等级
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TVipLevelEntity tVipLevel, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tVipLevel = systemService.flushEntity(TVipLevelEntity.class, tVipLevel.getId());
		message = "顾客VIP等级删除成功";
		try{
			tVipLevel.setStatus(common.GlobalConstants.STATUS_INACTIVE );
			tVipLevel.setUpdateDate(DateUtils.gettimestamp());
			tVipLevelService.saveOrUpdate(tVipLevel);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客VIP等级删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除顾客VIP等级
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "顾客VIP等级删除成功";
		try{
			for(String id:ids.split(",")){
				TVipLevelEntity tVipLevel = systemService.flushEntity(TVipLevelEntity.class,  id );
				tVipLevel.setStatus(common.GlobalConstants.STATUS_INACTIVE );
				tVipLevel.setUpdateDate(DateUtils.gettimestamp());
				tVipLevelService.saveOrUpdate(tVipLevel);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客VIP等级删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加顾客VIP等级
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TVipLevelEntity tVipLevel, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客VIP等级添加成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			tVipLevel.setProvinceId(user.getProvinceId());
			tVipLevel.setCityId(user.getCityId());
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
					tVipLevel.setRetailerId(user.getId());
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				tVipLevel.setRetailerId(user.getRetailerId());
			}
			tVipLevelService.save(tVipLevel);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客VIP等级添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新顾客VIP等级
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TVipLevelEntity tVipLevel, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客VIP等级更新成功";
		TVipLevelEntity t = tVipLevelService.get(TVipLevelEntity.class, tVipLevel.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tVipLevel, t);
			tVipLevelService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "顾客VIP等级更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 顾客VIP等级新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TVipLevelEntity tVipLevel, HttpServletRequest req) {
		return new ModelAndView("com/buss/user/tVipLevel-add");
	}
	/**
	 * 顾客VIP等级编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TVipLevelEntity tVipLevel, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tVipLevel.getId())) {
			tVipLevel = tVipLevelService.flushEntity(TVipLevelEntity.class, tVipLevel.getId());
			req.setAttribute("tVipLevelPage", tVipLevel);
		}
		return new ModelAndView("com/buss/user/tVipLevel-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tVipLevelController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TVipLevelEntity tVipLevel,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TVipLevelEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tVipLevel, request.getParameterMap());
		List<TVipLevelEntity> tVipLevels = this.tVipLevelService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"顾客VIP等级");
		modelMap.put(NormalExcelConstants.CLASS,TVipLevelEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("顾客VIP等级列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tVipLevels);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TVipLevelEntity tVipLevel,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"顾客VIP等级");
    	modelMap.put(NormalExcelConstants.CLASS,TVipLevelEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("顾客VIP等级列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TVipLevelEntity> listTVipLevelEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TVipLevelEntity.class,params);
				for (TVipLevelEntity tVipLevel : listTVipLevelEntitys) {
					tVipLevelService.save(tVipLevel);
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
