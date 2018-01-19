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

import com.buss.bill.entity.TCommissionStoreRuleEntity;
import com.buss.bill.service.TCommissionStoreRuleServiceI;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 零售商实收分配定义表
 * @author onlineGenerator
 * @date 2016-04-06 11:05:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tCommissionStoreRuleController")
public class TCommissionStoreRuleController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TCommissionStoreRuleController.class);

	@Autowired
	private TCommissionStoreRuleServiceI tCommissionStoreRuleService;
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
	 * 零售商实收分配定义表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/bill/tCommissionStoreRuleList");
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
	public void datagrid(TCommissionStoreRuleEntity tCommissionStoreRule,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TCommissionStoreRuleEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tCommissionStoreRule, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tCommissionStoreRuleService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	


	/**
	 * 添加零售商实收分配定义表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TCommissionStoreRuleEntity tCommissionStoreRule, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商实收分配定义表添加成功";
		try{
			tCommissionStoreRuleService.save(tCommissionStoreRule);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商实收分配定义表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新零售商实收分配定义表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TCommissionStoreRuleEntity tCommissionStoreRule, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商实收分配定义表更新成功";
		TCommissionStoreRuleEntity t = tCommissionStoreRuleService.get(TCommissionStoreRuleEntity.class, tCommissionStoreRule.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tCommissionStoreRule, t);
			tCommissionStoreRuleService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "零售商实收分配定义表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 零售商实收分配定义表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(String sid, HttpServletRequest req) {
		List<TCommissionStoreRuleEntity> dataList =  new ArrayList<TCommissionStoreRuleEntity>();
		if (StringUtil.isNotEmpty(sid)) {
			/*List<TSType> typeList = TSTypegroup.allTypes.get("commitype");
			List<TCommissionStoreRuleEntity> list = tCommissionStoreRuleService.findByProperty(TCommissionStoreRuleEntity.class, "sid", sid);
			if(Utility.isEmpty(list)){
				list = new ArrayList<TCommissionStoreRuleEntity>();
			}
			for (TSType tsType : typeList)
			{
				boolean flag = true;
				for(TCommissionStoreRuleEntity rule : list){
					if(tsType.getTypecode().equals(rule.getCtype()))
					{
						flag = false;
						rule.setCtypeName(tsType.getTypename());
						rule.setSid(sid);
						dataList.add(rule);
						break;
					}
				}
				if(flag){
					TCommissionStoreRuleEntity rule  = new TCommissionStoreRuleEntity();
					rule.setCtype(tsType.getTypecode());
					rule.setCtypeName(tsType.getTypename());
					rule.setSid(sid);
					rule.setStatus(GlobalConstants.STATUS_ACTIVE);
					dataList.add(rule);
				}
			}*/
		}
		req.setAttribute("dataList", dataList);
		return new ModelAndView("com/buss/bill/tCommissionStoreRule-add");
	}
	
	/**
	 * 零售商实收分配定义表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TCommissionStoreRuleEntity tCommissionStoreRule, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tCommissionStoreRule.getId())) {
			tCommissionStoreRule = tCommissionStoreRuleService.flushEntity(TCommissionStoreRuleEntity.class, tCommissionStoreRule.getId());
			req.setAttribute("tCommissionStoreRulePage", tCommissionStoreRule);
		}
		return new ModelAndView("com/buss/bill/tCommissionStoreRule-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tCommissionStoreRuleController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TCommissionStoreRuleEntity tCommissionStoreRule,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TCommissionStoreRuleEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tCommissionStoreRule, request.getParameterMap());
		List<TCommissionStoreRuleEntity> tCommissionStoreRules = this.tCommissionStoreRuleService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"零售商实收分配定义表");
		modelMap.put(NormalExcelConstants.CLASS,TCommissionStoreRuleEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("零售商实收分配定义表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tCommissionStoreRules);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TCommissionStoreRuleEntity tCommissionStoreRule,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"零售商实收分配定义表");
    	modelMap.put(NormalExcelConstants.CLASS,TCommissionStoreRuleEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("零售商实收分配定义表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TCommissionStoreRuleEntity> listTCommissionStoreRuleEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TCommissionStoreRuleEntity.class,params);
				for (TCommissionStoreRuleEntity tCommissionStoreRule : listTCommissionStoreRuleEntitys) {
					tCommissionStoreRuleService.save(tCommissionStoreRule);
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
	 * 更新零售商实收分配定义表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddOrUpdate")
	@ResponseBody
	public AjaxJson doAddOrUpdate(TCommissionStoreRuleEntity tCommissionStoreRule, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商实收分配定义表更新成功";
//		TCommissionStoreRuleEntity t = tCommissionStoreRuleService.get(TCommissionStoreRuleEntity.class, tCommissionStoreRule.getId());
		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tCommissionStoreRule, t);
//			tCommissionStoreRuleService.saveOrUpdate(t);
			List<TCommissionStoreRuleEntity> dataList = tCommissionStoreRule.getCinfoList();
			tCommissionStoreRuleService.saveList(dataList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "零售商实收分配定义表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
}
