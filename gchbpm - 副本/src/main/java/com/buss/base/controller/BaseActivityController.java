package com.buss.base.controller;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtil;
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

import com.buss.base.entity.BaseActivityEntity;
import com.buss.base.service.BaseActivityServiceI;
import com.buss.cms.service.TContentCustomIndexServiceI;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 活动
 * @author onlineGenerator
 * @date 2016-03-31 15:29:52
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/baseActivityController")
public class BaseActivityController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseActivityController.class);

	@Autowired
	private BaseActivityServiceI baseActivityService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TContentCustomIndexServiceI tContentCustomIndexService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 活动列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/base/baseActivityList");
	}
	
	/**
	 * 零售商活动列表菜单 页面跳转
	 * @return
	 */
	@RequestMapping(params = "listOfRetailer")
	public ModelAndView listOfRetailer(HttpServletRequest request) {
		return new ModelAndView("com/buss/base/baseActivityListOfRetailer");
	}
	
	/**
	 * 零售商选择活动列表菜单 页面跳转
	 * @return
	 */
	@RequestMapping(params = "listOfRetailerTobeAdd")
	public ModelAndView listOfRetailerTobeAdd(HttpServletRequest request) {
		request.setAttribute("rId", request.getParameter("rId"));
		request.setAttribute("cId", request.getParameter("cId"));
		return new ModelAndView("com/buss/base/baseActivityListOfRetailer3");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(BaseActivityEntity baseActivity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BaseActivityEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baseActivity, request.getParameterMap());
		try{
		//自定义追加查询条件
			String content_Id = request.getParameter("content_Id");
			TSUser user = ResourceUtil.getSessionUserName();
			String retailerId = null;
			if(common.GlobalConstants.USER_TYPE_02.endsWith(user.getUserType())){
				retailerId = user.getId();
			}else if(common.GlobalConstants.USER_TYPE_05.endsWith(user.getUserType())){
				retailerId = user.getRetailerId();
			}
			if(!StringUtil.isEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
				cq.eq("userType", common.GlobalConstants.USER_TYPE_02);
			}
			if(!StringUtil.isEmpty(content_Id)){
				cq.eq("contentId", Long.valueOf(content_Id));
			}
			String start_Time = request.getParameter("start_Time");
			String end_Time = request.getParameter("end_Time");
			if(StringUtil.isNotEmpty(start_Time)){
				cq.ge("startTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(start_Time+" 00:00:00"));
			}
			if(StringUtil.isNotEmpty(end_Time)){
				cq.le("endTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(end_Time+" 23:59:59"));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.baseActivityService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyui 查询未添加的活动
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid2")
	public void datagrid2(BaseActivityEntity baseActivity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BaseActivityEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baseActivity, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = request.getParameter("rId");
//			String contentId = request.getParameter("cId");
			cq.eq("retailerId", retailerId);
			cq.eq("userType", common.GlobalConstants.USER_TYPE_02);
			cq.isNull("contentId");
			cq.ge("endTime", Utility.getCurrentTimestamp());
//			cq.notEq("contentId", Long.valueOf(contentId));
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.baseActivityService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除活动并删除活动商品缓存
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BaseActivityEntity baseActivity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动删除成功";
		try{
			baseActivityService.doDel(baseActivity);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除活动
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "活动删除成功";
		try{
			for(String id:ids.split(",")){
				BaseActivityEntity baseActivity = systemService.flushEntity(BaseActivityEntity.class,  id );
				baseActivity.setStatus(common.GlobalConstants.STATUS_INACTIVE );
				baseActivity.setUpdateDate(DateUtils.gettimestamp());
				baseActivityService.saveOrUpdate(baseActivity);
//				baseActivityService.delete(baseActivity);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "活动删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加活动
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BaseActivityEntity baseActivity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动添加成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			if(StringUtil.isEmpty(retailerId)){
				baseActivity.setUserType(common.GlobalConstants.USER_TYPE_01);
			}else{
				baseActivity.setRetailerId(retailerId);
				baseActivity.setUserType(common.GlobalConstants.USER_TYPE_02);
			}
			baseActivityService.save(baseActivity);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 添加活动
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doBatchAdd")
	@ResponseBody
	public AjaxJson doBatchAdd(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动添加成功";
		try{
			String contentId = request.getParameter("contentId");
			String activityIds = request.getParameter("activityIds");
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update base_activity set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',content_id = '").append(contentId)
			.append("' where id in (");
			for(String id:activityIds.split(",")){
				sql.append("'").append(id).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(")");
			systemService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 更新活动并刷新活动商品缓存
	 * @param baseActivity
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BaseActivityEntity baseActivity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动更新成功";
		try {
			baseActivityService.doUpdate(baseActivity);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "活动更新失败";
			throw new BusinessException(e.getMessage());
		}
		//清除APP首页缓存
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 活动新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BaseActivityEntity baseActivity, HttpServletRequest req) {
		TSUser user = ResourceUtil.getSessionUserName();
		if(TSUser.USER_TYPE_01.equals(user.getUserType())){
			req.setAttribute("source", 1);//后台
		}else if(TSUser.USER_TYPE_02.equals(user.getUserType())||TSUser.USER_TYPE_05.equals(user.getUserType())){
			req.setAttribute("source", 2);//零售商或者其员工
		}
		String contentId = req.getParameter("contentId");
		req.setAttribute("contentId", contentId);
		return new ModelAndView("com/buss/base/baseActivity-add");
	}
	/**
	 * 活动编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BaseActivityEntity baseActivity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(baseActivity.getId())) {
			baseActivity = baseActivityService.flushEntity(BaseActivityEntity.class, baseActivity.getId());
			req.setAttribute("baseActivityPage", baseActivity);
		}
		TSUser user = ResourceUtil.getSessionUserName();
		if(TSUser.USER_TYPE_01.equals(user.getUserType())){
			req.setAttribute("source", 1);//后台
		}else if(TSUser.USER_TYPE_02.equals(user.getUserType())||TSUser.USER_TYPE_05.equals(user.getUserType())){
			req.setAttribute("source", 2);//零售商或者其员工
		}
		return new ModelAndView("com/buss/base/baseActivity-update");
	}

	/**
	 * 活动查看页面跳转
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(BaseActivityEntity baseActivity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(baseActivity.getId())) {
			baseActivity = baseActivityService.flushEntity(BaseActivityEntity.class, baseActivity.getId());
			req.setAttribute("baseActivityPage", baseActivity);
		}
		return new ModelAndView("com/buss/base/baseActivity-view");
	}
	
	/**
	 * 上传图片
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadPic", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadPic(HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		FtpUtil ftpUtil = FtpUtil.getInstance();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();// 获取上传文件对象
			String fileName = mf.getOriginalFilename();// 获取文件名
			String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
			String myfilename="";
			String noextfilename="";//不带扩展名
			noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+StringUtil.random(8);//自定义文件名称
			myfilename = noextfilename+"."+extend;//自定义文件名称
			InputStream is = mf.getInputStream();
			ftpUtil.uploadFileToFtpServer(GlobalConstants.BASE_ACTIVITY_PICS, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
							 +ResourceUtil.getConfigByName("imgRootPath")+"/"
							 +GlobalConstants.BASE_ACTIVITY_PICS+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}	
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","baseActivityController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BaseActivityEntity baseActivity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BaseActivityEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baseActivity, request.getParameterMap());
		List<BaseActivityEntity> baseActivitys = this.baseActivityService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"活动");
		modelMap.put(NormalExcelConstants.CLASS,BaseActivityEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("活动列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,baseActivitys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BaseActivityEntity baseActivity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"活动");
    	modelMap.put(NormalExcelConstants.CLASS,BaseActivityEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("活动列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<BaseActivityEntity> listBaseActivityEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BaseActivityEntity.class,params);
				for (BaseActivityEntity baseActivity : listBaseActivityEntitys) {
					baseActivityService.save(baseActivity);
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
