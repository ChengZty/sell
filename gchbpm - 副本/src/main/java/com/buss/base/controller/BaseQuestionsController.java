package com.buss.base.controller;
import cn.redis.service.RedisService;

import com.buss.base.entity.BaseQuestionsEntity;
import com.buss.base.service.BaseQuestionsServiceI;
import java.util.ArrayList;
import java.util.HashSet;
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
 * @Description: 问题列表
 * @author onlineGenerator
 * @date 2016-05-31 00:06:18
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/baseQuestionsController")
public class BaseQuestionsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseQuestionsController.class);

	@Autowired
	private BaseQuestionsServiceI baseQuestionsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RedisService redisService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 问题列表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/base/baseQuestionsList");
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
	public void datagrid(BaseQuestionsEntity baseQuestions,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BaseQuestionsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baseQuestions, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.baseQuestionsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除问题列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BaseQuestionsEntity baseQuestions, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		baseQuestions = systemService.flushEntity(BaseQuestionsEntity.class, baseQuestions.getId());
		message = "问题列表删除成功";
		try{
			baseQuestions.setStatus("I");
			baseQuestionsService.updateEntitie(baseQuestions);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "问题列表删除失败";
			throw new BusinessException(e.getMessage());
		}
		/*try
		{
			//更新问题的总个数
			checkQuestionCount(baseQuestions,"D");
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}*/
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除问题列表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "问题列表删除成功";
		List<BaseQuestionsEntity> questionStages = new ArrayList<BaseQuestionsEntity>();
		try{
			for(String id:ids.split(",")){
				BaseQuestionsEntity baseQuestions = systemService.flushEntity(BaseQuestionsEntity.class,id);
				baseQuestions.setStatus("I");
				baseQuestionsService.updateEntitie(baseQuestions);
				questionStages.add(baseQuestions);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "问题列表删除失败";
			throw new BusinessException(e.getMessage());
		}
		/*try
		{
			for(BaseQuestionsEntity questionStage: questionStages){
				//更新问题的总个数
				checkQuestionCount(questionStage,"D");
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}*/
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加问题列表
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BaseQuestionsEntity baseQuestions, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "问题列表添加成功";
		try{
			String questionValue = "1";
	 		Map<String, Object> map = baseQuestionsService.findOneForJdbc("select CONCAT(MAX(question_value+1),'') as question_value from base_questions where status = 'A'");
			if(Utility.isNotEmpty(map)&&Utility.isNotEmpty(map.get("question_value"))){
				questionValue =  map.get("question_value")+"" ;
			}
			baseQuestions.setQuestionValue(StringUtil.replaceBlank(baseQuestions.getQuestionValue()));
			baseQuestions.setQuestionValue(questionValue);
			baseQuestionsService.save(baseQuestions);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "问题列表添加失败";
			throw new BusinessException(e.getMessage());
		}
		/*try
		{
			//更新问题的总个数
			this.checkQuestionCount(baseQuestions,"A");
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}*/
		j.setMsg(message);
		return j;
	}
	

	/**检查并更新redis顾客问题的个数
	 * @param questionStage
	 * @param type
	 */
	/*private void checkQuestionCount(BaseQuestionsEntity baseQuestions,String type) {
		if(!StringUtil.isEmpty(baseQuestions.getQuestionStage())||"2".equals(baseQuestions.getMaintanceType())){
			String count = redisService.get(common.GlobalConstants.Q_COUNT+"_"+baseQuestions.getQuestionStage());
			if(StringUtil.isEmpty(count)||count=="0"){
				Long num = this.systemService.getCountForJdbc("select count(1) from base_questions where maintance_type = '2' and question_stage ='"+baseQuestions.getQuestionStage()+"'");
				redisService.set(common.GlobalConstants.Q_COUNT+"_"+baseQuestions.getQuestionStage(),num+"");
			}else{
				if(type=="A"){
					redisService.incr(common.GlobalConstants.Q_COUNT+"_"+baseQuestions.getQuestionStage());
				}else if(type=="D"){
					redisService.decr(common.GlobalConstants.Q_COUNT+"_"+baseQuestions.getQuestionStage());
				}
			}
		}
		
	}*/

	/**
	 * 更新问题列表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BaseQuestionsEntity baseQuestions, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "问题列表更新成功";
		BaseQuestionsEntity t = baseQuestionsService.get(BaseQuestionsEntity.class, baseQuestions.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(baseQuestions, t);
			t.setQuestionValue(StringUtil.replaceBlank(baseQuestions.getQuestionValue()));
			baseQuestionsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "问题列表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 问题列表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BaseQuestionsEntity baseQuestions, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(baseQuestions.getId())) {
			baseQuestions = baseQuestionsService.flushEntity(BaseQuestionsEntity.class, baseQuestions.getId());
			req.setAttribute("baseQuestionsPage", baseQuestions);
		}
		return new ModelAndView("com/buss/base/baseQuestions-add");
	}
	/**
	 * 问题列表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BaseQuestionsEntity baseQuestions, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(baseQuestions.getId())) {
			baseQuestions = baseQuestionsService.flushEntity(BaseQuestionsEntity.class, baseQuestions.getId());
			req.setAttribute("baseQuestionsPage", baseQuestions);
		}
		return new ModelAndView("com/buss/base/baseQuestions-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","baseQuestionsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BaseQuestionsEntity baseQuestions,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BaseQuestionsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baseQuestions, request.getParameterMap());
		List<BaseQuestionsEntity> baseQuestionss = this.baseQuestionsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"问题列表");
		modelMap.put(NormalExcelConstants.CLASS,BaseQuestionsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("问题列表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,baseQuestionss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BaseQuestionsEntity baseQuestions,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"问题列表");
    	modelMap.put(NormalExcelConstants.CLASS,BaseQuestionsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("问题列表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<BaseQuestionsEntity> listBaseQuestionsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BaseQuestionsEntity.class,params);
				for (BaseQuestionsEntity baseQuestions : listBaseQuestionsEntitys) {
					baseQuestionsService.save(baseQuestions);
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
