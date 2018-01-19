package com.buss.user.controller;
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
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
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

import com.buss.user.entity.TProfessionalEntity;
import com.buss.user.service.TProfessionalServiceI;

/**   
 * @Title: Controller
 * @Description: 职称
 * @author onlineGenerator
 * @date 2016-05-14 00:02:38
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tProfessionalController")
public class TProfessionalController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TProfessionalController.class);

	@Autowired
	private TProfessionalServiceI tProfessionalService;
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
	 * 职称列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/tProfessionalList");
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
	public void datagrid(TProfessionalEntity tProfessional,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TProfessionalEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tProfessional, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tProfessionalService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除职称
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TProfessionalEntity tProfessional, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tProfessional = systemService.flushEntity(TProfessionalEntity.class, tProfessional.getId());
		message = "职称删除成功";
		try{
			tProfessional.setStatus(common.GlobalConstants.STATUS_INACTIVE);
			tProfessional.setUpdateDate(DateUtils.gettimestamp());
			tProfessionalService.updateEntitie(tProfessional);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "职称删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除职称
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "职称删除成功";
		try{
			for(String id:ids.split(",")){
				TProfessionalEntity tProfessional = systemService.flushEntity(TProfessionalEntity.class, id	);
				tProfessional.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				tProfessional.setUpdateDate(DateUtils.gettimestamp());
				tProfessionalService.updateEntitie(tProfessional);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "职称删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加职称
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TProfessionalEntity tProfessional, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "职称添加成功";
		try{
			tProfessionalService.save(tProfessional);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "职称添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新职称
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TProfessionalEntity tProfessional, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "职称更新成功";
		TProfessionalEntity t = tProfessionalService.get(TProfessionalEntity.class, tProfessional.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tProfessional, t);
			tProfessionalService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "职称更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 获取职称
	 */
	@RequestMapping(params = "getProfessional")
	@ResponseBody
	public AjaxJson getProfessional( HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String topCategoryId = request.getParameter("topCategoryId");
		String helperType = request.getParameter("helperType");
		String hql ="from TProfessionalEntity where topCategoryId='"+topCategoryId+"' and helperType='"+helperType+"'";
		List<TProfessionalEntity> list = systemService.findByQueryString(hql);
		j.setObj(list);
		j.setMsg(message);
		return j;
	}

	/**
	 * 职称新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TProfessionalEntity tProfessional, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tProfessional.getId())) {
			tProfessional = tProfessionalService.flushEntity(TProfessionalEntity.class, tProfessional.getId());
			req.setAttribute("tProfessionalPage", tProfessional);
		}
		String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
		List<TSCategoryEntity> categoryList = systemService.findListbySql(sql);
		req.setAttribute("categoryList", categoryList);
		return new ModelAndView("com/buss/user/tProfessional-add");
	}
	/**
	 * 职称编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TProfessionalEntity tProfessional, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tProfessional.getId())) {
			tProfessional = tProfessionalService.flushEntity(TProfessionalEntity.class, tProfessional.getId());
			req.setAttribute("tProfessionalPage", tProfessional);
		}
		String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
		List<TSCategoryEntity> categoryList = systemService.findListbySql(sql);
		req.setAttribute("categoryList", categoryList);
		return new ModelAndView("com/buss/user/tProfessional-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tProfessionalController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TProfessionalEntity tProfessional,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TProfessionalEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tProfessional, request.getParameterMap());
		List<TProfessionalEntity> tProfessionals = this.tProfessionalService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"职称");
		modelMap.put(NormalExcelConstants.CLASS,TProfessionalEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("职称列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tProfessionals);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TProfessionalEntity tProfessional,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"职称");
    	modelMap.put(NormalExcelConstants.CLASS,TProfessionalEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("职称列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TProfessionalEntity> listTProfessionalEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TProfessionalEntity.class,params);
				for (TProfessionalEntity tProfessional : listTProfessionalEntitys) {
					tProfessionalService.save(tProfessional);
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
	public List<TProfessionalEntity> list() {
		List<TProfessionalEntity> listTProfessionals=tProfessionalService.getList(TProfessionalEntity.class);
		return listTProfessionals;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TProfessionalEntity task = tProfessionalService.get(TProfessionalEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TProfessionalEntity tProfessional, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TProfessionalEntity>> failures = validator.validate(tProfessional);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tProfessionalService.save(tProfessional);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tProfessional.getId();
		URI uri = uriBuilder.path("/rest/tProfessionalController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TProfessionalEntity tProfessional) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TProfessionalEntity>> failures = validator.validate(tProfessional);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tProfessionalService.saveOrUpdate(tProfessional);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tProfessionalService.deleteEntityById(TProfessionalEntity.class, id);
	}
}
