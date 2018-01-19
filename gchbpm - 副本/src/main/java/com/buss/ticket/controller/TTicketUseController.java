package com.buss.ticket.controller;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
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

import com.buss.ticket.entity.TTicketUseEntity;
import com.buss.ticket.service.TTicketUseServiceI;

/**   
 * @Title: Controller
 * @Description: 优惠券使用
 * @author onlineGenerator
 * @date 2016-08-08 17:44:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tTicketUseController")
public class TTicketUseController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TTicketUseController.class);

	@Autowired
	private TTicketUseServiceI tTicketUseService;
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
	 * 优惠券使用列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/ticket/tTicketUseList");
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
	public void datagrid(TTicketUseEntity tTicketUse,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 查询条件组装器
		try {
			Map<String,String> sqlMap = this.getQuerySql(request,dataGrid);
			String sql = sqlMap.get("sql");
			String countSql = sqlMap.get("countSql");
			List<TTicketUseEntity> resultList = new ArrayList<TTicketUseEntity>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TTicketUseEntity.class);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**获取查询的sql和countSql
	 * @param request
	 * @param dataGrid
	 * @return
	 */
	private Map<String,String> getQuerySql(HttpServletRequest request,DataGrid dataGrid) {
		Map<String,String> map = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String orderNo = request.getParameter("orderNo");//订单/退款单号
		String useType = request.getParameter("useType");//使用类型
		String guideName = request.getParameter("guideName");//导购姓名
//		String guidePhone = request.getParameter("guidePhone");//导购手机
		StringBuffer sql = new StringBuffer("SELECT tu.add_time addTime,tu.business_date businessDate,tu.id,tu.order_no orderNo,tu.sub_order_no subOrderNo,tu.ticket_preferential ticketPreferential,")
		.append("tu.user_id userId,tu.user_name userName,tu.use_type useType,IFNULL(p.real_Name,'') guideName,IFNULL(s.`name`,'') storeName ")
		.append("from t_ticket_use tu LEFT JOIN t_person p ON tu.retailer_id = p.to_retailer_id and tu.to_guide_id = p.user_id LEFT JOIN t_store s on p.store_id = s.id where 1=1");
		StringBuffer countSql = new StringBuffer("select count(1) from t_ticket_use tu LEFT JOIN t_person p ON tu.retailer_id = p.to_retailer_id and tu.to_guide_id = p.user_id LEFT JOIN t_store s on p.store_id = s.id where 1=1 ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append(" and tu.retailer_id = '").append(retailerId).append("'");
			countSql.append(" and tu.retailer_id = '").append(retailerId).append("'");
		}
		if(Utility.isNotEmpty(useType)){
			sql.append(" and tu.use_type = '").append(useType).append("'");
			countSql.append(" and tu.use_type = '").append(useType).append("'");
		}
		if(Utility.isNotEmpty(orderNo)){
			sql.append(" and tu.order_no like '%").append(orderNo).append("%'");
			countSql.append(" and tu.order_no like '%").append(orderNo).append("%'");
		}
//		if(Utility.isNotEmpty(startTime)){
//			sql.append(" and r.add_time >= '").append(startTime).append(" 00:00:00'");
//			countSql.append(" and r.add_time >= '").append(startTime).append(" 00:00:00'");
//		}
//		if(Utility.isNotEmpty(endTime)){
//			sql.append(" and r.add_time <= '").append(endTime).append(" 23:59:59'");
//			countSql.append(" and r.add_time <= '").append(endTime).append(" 23:59:59'");
//		}
//		if(Utility.isNotEmpty(userPhone)){
//			sql.append(" and r.user_phone like '%").append(userPhone).append("%'");
//			countSql.append(" and r.user_phone like '%").append(userPhone).append("%'");
//		}
		if(Utility.isNotEmpty(guideName)){
			sql.append(" and p.real_Name like '%").append(guideName).append("%'");
			countSql.append(" and p.real_Name like '%").append(guideName).append("%'");
		}
//		if(Utility.isNotEmpty(guidePhone)){
//			sql.append(" and p.phone_no like '%").append(guidePhone).append("%'");
//			countSql.append(" and p.phone_no like '%").append(guidePhone).append("%'");
//		}
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
			sql.append(" ORDER BY addTime desc");
		}else{
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		map.put("sql", sql.toString());
		map.put("countSql", countSql.toString());
		return map;
	}

	/**
	 * 删除优惠券使用
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TTicketUseEntity tTicketUse, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tTicketUse = systemService.flushEntity(TTicketUseEntity.class, tTicketUse.getId());
		message = "优惠券使用删除成功";
		try{
			tTicketUseService.delete(tTicketUse);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券使用删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除优惠券使用
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "优惠券使用删除成功";
		try{
			for(String id:ids.split(",")){
				TTicketUseEntity tTicketUse = systemService.flushEntity(TTicketUseEntity.class, 
				id
				);
				tTicketUseService.delete(tTicketUse);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券使用删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加优惠券使用
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TTicketUseEntity tTicketUse, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "优惠券使用添加成功";
		try{
			tTicketUseService.save(tTicketUse);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券使用添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新优惠券使用
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TTicketUseEntity tTicketUse, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "优惠券使用更新成功";
		TTicketUseEntity t = tTicketUseService.get(TTicketUseEntity.class, tTicketUse.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tTicketUse, t);
			tTicketUseService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "优惠券使用更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 优惠券使用新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TTicketUseEntity tTicketUse, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tTicketUse.getId())) {
			tTicketUse = tTicketUseService.flushEntity(TTicketUseEntity.class, tTicketUse.getId());
			req.setAttribute("tTicketUsePage", tTicketUse);
		}
		return new ModelAndView("com/buss/ticket/tTicketUse-add");
	}
	/**
	 * 优惠券使用编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TTicketUseEntity tTicketUse, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tTicketUse.getId())) {
			tTicketUse = tTicketUseService.flushEntity(TTicketUseEntity.class, tTicketUse.getId());
			req.setAttribute("tTicketUsePage", tTicketUse);
		}
		return new ModelAndView("com/buss/ticket/tTicketUse-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tTicketUseController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TTicketUseEntity tTicketUse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TTicketUseEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tTicketUse, request.getParameterMap());
		List<TTicketUseEntity> tTicketUses = this.tTicketUseService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"优惠券使用");
		modelMap.put(NormalExcelConstants.CLASS,TTicketUseEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("优惠券使用列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tTicketUses);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TTicketUseEntity tTicketUse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"优惠券使用");
    	modelMap.put(NormalExcelConstants.CLASS,TTicketUseEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("优惠券使用列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TTicketUseEntity> listTTicketUseEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TTicketUseEntity.class,params);
				for (TTicketUseEntity tTicketUse : listTTicketUseEntitys) {
					tTicketUseService.save(tTicketUse);
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
	public List<TTicketUseEntity> list() {
		List<TTicketUseEntity> listTTicketUses=tTicketUseService.getList(TTicketUseEntity.class);
		return listTTicketUses;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TTicketUseEntity task = tTicketUseService.get(TTicketUseEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TTicketUseEntity tTicketUse, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TTicketUseEntity>> failures = validator.validate(tTicketUse);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tTicketUseService.save(tTicketUse);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tTicketUse.getId();
		URI uri = uriBuilder.path("/rest/tTicketUseController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TTicketUseEntity tTicketUse) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TTicketUseEntity>> failures = validator.validate(tTicketUse);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tTicketUseService.saveOrUpdate(tTicketUse);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tTicketUseService.deleteEntityById(TTicketUseEntity.class, id);
	}
}
