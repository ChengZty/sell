package com.buss.ticket.controller;
import com.buss.ticket.entity.TTicketSendEntity;
import com.buss.ticket.service.TTicketSendServiceI;
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

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller
 * @Description: 分配券
 * @author onlineGenerator
 * @date 2016-07-20 10:08:34
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tTicketSendController")
public class TTicketSendController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TTicketSendController.class);

	@Autowired
	private TTicketSendServiceI tTicketSendService;
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
	 * 分配券列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("user_Id", request.getParameter("user_Id"));
		return new ModelAndView("com/buss/ticket/tTicketSendList");
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
	public void datagrid(TTicketSendEntity tTicketSend,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TTicketSendEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tTicketSend, request.getParameterMap());
		try{
		//自定义追加查询条件
			String user_Id = request.getParameter("user_Id");
			String sql ="select i.id,i.ticket_name as ticketName,u.realname,s.sheet,s.sheet_give as sheetGive,s.sheet_remain as sheetRemain,s.add_time as addTime"
				+" from t_ticket_send s LEFT JOIN t_ticket_info i on s.ticket_id = i.id LEFT JOIN t_s_user u on s.sender_id = u.id  where s.`status` = 'A' and i.`status` = 'A' and u.`status`='A' and s.user_Id ='"+user_Id+"'  ";
			String sort = dataGrid.getSort();
			if(StringUtil.isNotEmpty(sort)){
				sql += " order by "+sort+" "+dataGrid.getOrder();
			}else{
				sql +=" order by s.add_time desc";
			}
//			String brandName = request.getParameter("brandName");
//			if(!Utility.isEmpty(brandName)){
//				sql +=" and b.brand_name = '"+brandName+"'";
//			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				String countSql = "select count(1) from t_ticket_send s where s.status = 'A' and s.user_Id ='"+user_Id+"'";
//				if(!Utility.isEmpty(brandName)){
//					countSql +=" and b.brand_name = '"+brandName+"'";
//				}
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
//		this.tTicketSendService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除分配券
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TTicketSendEntity tTicketSend, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tTicketSend = systemService.flushEntity(TTicketSendEntity.class, tTicketSend.getId());
		message = "分配券删除成功";
		try{
			tTicketSendService.delete(tTicketSend);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "分配券删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除分配券
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "分配券删除成功";
		try{
			for(String id:ids.split(",")){
				TTicketSendEntity tTicketSend = systemService.flushEntity(TTicketSendEntity.class, 
				id
				);
				tTicketSendService.delete(tTicketSend);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "分配券删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加分配券
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TTicketSendEntity tTicketSend, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "分配券添加成功";
		try{
			tTicketSendService.save(tTicketSend);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "分配券添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量分配券
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doBatchGive")
	@ResponseBody
	public AjaxJson doBatchGive(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "批量分配券成功";
		try{
			String userIds = request.getParameter("userIds");//导购ID
			String ticketIds = request.getParameter("ticketIds");//优惠券ID_券的零售商类型
			String sheetNum = request.getParameter("sheetNum");//人均张数
			tTicketSendService.doBatchGive(userIds,ticketIds,sheetNum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "批量分配券失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新分配券
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TTicketSendEntity tTicketSend, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "分配券更新成功";
		TTicketSendEntity t = tTicketSendService.get(TTicketSendEntity.class, tTicketSend.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tTicketSend, t);
			tTicketSendService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "分配券更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 分配券新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TTicketSendEntity tTicketSend, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tTicketSend.getId())) {
			tTicketSend = tTicketSendService.flushEntity(TTicketSendEntity.class, tTicketSend.getId());
			req.setAttribute("tTicketSendPage", tTicketSend);
		}
		return new ModelAndView("com/buss/ticket/tTicketSend-add");
	}
	/**
	 * 分配券编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TTicketSendEntity tTicketSend, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tTicketSend.getId())) {
			tTicketSend = tTicketSendService.flushEntity(TTicketSendEntity.class, tTicketSend.getId());
			req.setAttribute("tTicketSendPage", tTicketSend);
		}
		return new ModelAndView("com/buss/ticket/tTicketSend-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tTicketSendController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TTicketSendEntity tTicketSend,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TTicketSendEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tTicketSend, request.getParameterMap());
		List<TTicketSendEntity> tTicketSends = this.tTicketSendService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"分配券");
		modelMap.put(NormalExcelConstants.CLASS,TTicketSendEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("分配券列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tTicketSends);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TTicketSendEntity tTicketSend,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"分配券");
    	modelMap.put(NormalExcelConstants.CLASS,TTicketSendEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("分配券列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TTicketSendEntity> listTTicketSendEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TTicketSendEntity.class,params);
				for (TTicketSendEntity tTicketSend : listTTicketSendEntitys) {
					tTicketSendService.save(tTicketSend);
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
	public List<TTicketSendEntity> list() {
		List<TTicketSendEntity> listTTicketSends=tTicketSendService.getList(TTicketSendEntity.class);
		return listTTicketSends;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TTicketSendEntity task = tTicketSendService.get(TTicketSendEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TTicketSendEntity tTicketSend, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TTicketSendEntity>> failures = validator.validate(tTicketSend);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tTicketSendService.save(tTicketSend);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tTicketSend.getId();
		URI uri = uriBuilder.path("/rest/tTicketSendController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TTicketSendEntity tTicketSend) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TTicketSendEntity>> failures = validator.validate(tTicketSend);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tTicketSendService.saveOrUpdate(tTicketSend);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tTicketSendService.deleteEntityById(TTicketSendEntity.class, id);
	}
}
