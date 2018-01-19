package com.buss.wx.controller;
import com.buss.wx.entity.TWeixinUserBindEntity;
import com.buss.wx.service.TWeixinUserBindServiceI;
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
 * @Description: 微信卡片绑定表
 * @author onlineGenerator
 * @date 2018-01-05 11:40:01
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tWeixinUserBindController")
public class TWeixinUserBindController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TWeixinUserBindController.class);

	@Autowired
	private TWeixinUserBindServiceI tWeixinUserBindService;
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
	 * 微信卡片绑定表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tWeixinUserBind")
	public ModelAndView tWeixinUserBind(HttpServletRequest request) {
		return new ModelAndView("com/buss/wx/tWeixinUserBindList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TWeixinUserBindEntity tWeixinUserBind,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TWeixinUserBindEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tWeixinUserBind, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tWeixinUserBindService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}



	/**
	 * 添加微信卡片绑定表
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TWeixinUserBindEntity tWeixinUserBind, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "微信卡片绑定表添加成功";
		try{
			tWeixinUserBindService.save(tWeixinUserBind);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信卡片绑定表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新微信卡片绑定表
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TWeixinUserBindEntity tWeixinUserBind, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "微信卡片绑定表更新成功";
		TWeixinUserBindEntity t = tWeixinUserBindService.get(TWeixinUserBindEntity.class, tWeixinUserBind.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tWeixinUserBind, t);
			tWeixinUserBindService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "微信卡片绑定表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 微信卡片绑定表新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TWeixinUserBindEntity tWeixinUserBind, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tWeixinUserBind.getId())) {
			tWeixinUserBind = tWeixinUserBindService.flushEntity(TWeixinUserBindEntity.class, tWeixinUserBind.getId());
			req.setAttribute("tWeixinUserBindPage", tWeixinUserBind);
		}
		return new ModelAndView("com/buss/wx/tWeixinUserBind-add");
	}
	/**
	 * 微信卡片绑定表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TWeixinUserBindEntity tWeixinUserBind, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tWeixinUserBind.getId())) {
			tWeixinUserBind = tWeixinUserBindService.flushEntity(TWeixinUserBindEntity.class, tWeixinUserBind.getId());
			req.setAttribute("tWeixinUserBindPage", tWeixinUserBind);
		}
		return new ModelAndView("com/buss/wx/tWeixinUserBind-update");
	}
	
	/**
	 * 导入功能跳转
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tWeixinUserBindController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TWeixinUserBindEntity tWeixinUserBind,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TWeixinUserBindEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tWeixinUserBind, request.getParameterMap());
		List<TWeixinUserBindEntity> tWeixinUserBinds = this.tWeixinUserBindService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"微信卡片绑定表");
		modelMap.put(NormalExcelConstants.CLASS,TWeixinUserBindEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信卡片绑定表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tWeixinUserBinds);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TWeixinUserBindEntity tWeixinUserBind,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "微信卡片绑定表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TWeixinUserBindEntity.class);
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
				List<TWeixinUserBindEntity> listTWeixinUserBindEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TWeixinUserBindEntity.class,params);
				for (TWeixinUserBindEntity tWeixinUserBind : listTWeixinUserBindEntitys) {
					tWeixinUserBindService.save(tWeixinUserBind);
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
