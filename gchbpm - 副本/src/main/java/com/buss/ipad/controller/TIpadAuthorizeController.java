package com.buss.ipad.controller;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.IdUtils;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.buss.ipad.entity.TIpadAuthorizeEntity;
import com.buss.ipad.service.TIpadAuthorizeServiceI;
import common.GlobalConstants;

/**   
 * @Title: Controller
 * @Description: ipad授权表
 * @author onlineGenerator
 * @date 2016-09-02 20:39:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tIpadAuthorizeController")
public class TIpadAuthorizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TIpadAuthorizeController.class);

	@Autowired
	private TIpadAuthorizeServiceI tIpadAuthorizeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * ipad授权表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/ipad/tIpadAuthorizeList");
	}

	/**
	 * ipad授权表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toList")
	public ModelAndView toList(HttpServletRequest request) {
		String retailerId = request.getParameter("retailerId");
		request.setAttribute("retailerId", retailerId);
		return new ModelAndView("com/buss/ipad/tIpadAuthorizeListInfo");
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
	public void datagrid(TIpadAuthorizeEntity tIpadAuthorize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TIpadAuthorizeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tIpadAuthorize, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tIpadAuthorizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除ipad授权表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TIpadAuthorizeEntity tIpadAuthorize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tIpadAuthorize = systemService.flushEntity(TIpadAuthorizeEntity.class, tIpadAuthorize.getId());
		message = "ipad授权表删除成功";
		try{
			tIpadAuthorize.setStatus(GlobalConstants.STATUS_INACTIVE);
			tIpadAuthorizeService.updateEntitie(tIpadAuthorize);
//			tIpadAuthorizeService.delete(tIpadAuthorize);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "ipad授权表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除ipad授权表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "ipad授权表删除成功";
		try{
			for(String id:ids.split(",")){
				TIpadAuthorizeEntity tIpadAuthorize = systemService.flushEntity(TIpadAuthorizeEntity.class, id);
//				tIpadAuthorizeService.delete(tIpadAuthorize);
				tIpadAuthorize.setStatus(GlobalConstants.STATUS_INACTIVE);
				tIpadAuthorizeService.updateEntitie(tIpadAuthorize);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "ipad授权表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加ipad授权表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TIpadAuthorizeEntity tIpadAuthorize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "ipad授权表添加成功";
		try
		{
			TIpadAuthorizeEntity existObj =  this.tIpadAuthorizeService.findUniqueByProperty(TIpadAuthorizeEntity.class, "retailerId", tIpadAuthorize.getRetailerId());
			if(Utility.isEmpty(existObj))
			{
				TSUser user = this.systemService.get(TSUser.class, tIpadAuthorize.getRetailerId());
				tIpadAuthorize.setRetailerName(user.getRealName());
				tIpadAuthorize.setAuthorizeCode(IdUtils.createId());
				tIpadAuthorize.setUseStatus(TIpadAuthorizeEntity.USE_STATUS_INACTIVE);
				tIpadAuthorize.setStatus(GlobalConstants.STATUS_ACTIVE);
				tIpadAuthorizeService.save(tIpadAuthorize);
			}else
			{
				message = "授权码已生成，请不要重复生成";
				j.setSuccess(false);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "ipad授权表添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新ipad授权表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TIpadAuthorizeEntity tIpadAuthorize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "ipad授权表更新成功";
		TIpadAuthorizeEntity t = tIpadAuthorizeService.get(TIpadAuthorizeEntity.class, tIpadAuthorize.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tIpadAuthorize, t);
			tIpadAuthorizeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "ipad授权表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * ipad授权表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TIpadAuthorizeEntity tIpadAuthorize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tIpadAuthorize.getId())) {
			tIpadAuthorize = tIpadAuthorizeService.flushEntity(TIpadAuthorizeEntity.class, tIpadAuthorize.getId());
			req.setAttribute("tIpadAuthorizePage", tIpadAuthorize);
		}
		return new ModelAndView("com/buss/ipad/tIpadAuthorize-add");
	}
	/**
	 * ipad授权表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TIpadAuthorizeEntity tIpadAuthorize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tIpadAuthorize.getId())) {
			tIpadAuthorize = tIpadAuthorizeService.flushEntity(TIpadAuthorizeEntity.class, tIpadAuthorize.getId());
			req.setAttribute("tIpadAuthorizePage", tIpadAuthorize);
		}
		return new ModelAndView("com/buss/ipad/tIpadAuthorize-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tIpadAuthorizeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TIpadAuthorizeEntity tIpadAuthorize,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TIpadAuthorizeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tIpadAuthorize, request.getParameterMap());
		List<TIpadAuthorizeEntity> tIpadAuthorizes = this.tIpadAuthorizeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"ipad授权表");
		modelMap.put(NormalExcelConstants.CLASS,TIpadAuthorizeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("ipad授权表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tIpadAuthorizes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TIpadAuthorizeEntity tIpadAuthorize,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"ipad授权表");
    	modelMap.put(NormalExcelConstants.CLASS,TIpadAuthorizeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("ipad授权表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TIpadAuthorizeEntity> listTIpadAuthorizeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TIpadAuthorizeEntity.class,params);
				for (TIpadAuthorizeEntity tIpadAuthorize : listTIpadAuthorizeEntitys) {
					tIpadAuthorizeService.save(tIpadAuthorize);
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
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TIpadAuthorizeEntity> list() {
		List<TIpadAuthorizeEntity> listTIpadAuthorizes=tIpadAuthorizeService.getList(TIpadAuthorizeEntity.class);
		return listTIpadAuthorizes;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TIpadAuthorizeEntity task = tIpadAuthorizeService.get(TIpadAuthorizeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TIpadAuthorizeEntity tIpadAuthorize, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TIpadAuthorizeEntity>> failures = validator.validate(tIpadAuthorize);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tIpadAuthorizeService.save(tIpadAuthorize);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tIpadAuthorize.getId();
		URI uri = uriBuilder.path("/rest/tIpadAuthorizeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TIpadAuthorizeEntity tIpadAuthorize) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TIpadAuthorizeEntity>> failures = validator.validate(tIpadAuthorize);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tIpadAuthorizeService.saveOrUpdate(tIpadAuthorize);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tIpadAuthorizeService.deleteEntityById(TIpadAuthorizeEntity.class, id);
	}
}
