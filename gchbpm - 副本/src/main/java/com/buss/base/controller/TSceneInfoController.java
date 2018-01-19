package com.buss.base.controller;
import cn.redis.service.RedisService;

import com.buss.base.entity.TSceneInfoEntity;
import com.buss.base.entity.TScenePicsEntity;
import com.buss.base.service.TSceneInfoServiceI;
import com.buss.user.entity.TPersonTagsEntity;
import common.GlobalConstants;

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
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.InputStream;
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
 * @Description: 场景
 * @author onlineGenerator
 * @date 2016-09-06 10:49:59
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSceneInfoController")
public class TSceneInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSceneInfoController.class);

	@Autowired
	private TSceneInfoServiceI tSceneInfoService;
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
	 * 场景列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/base/tSceneInfoList");
	}
	
	/**
	 * 场景列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tSceneAppList")
	public ModelAndView tSceneAppList(HttpServletRequest request) {
		return new ModelAndView("com/buss/base/tSceneAppList");
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
	public void datagrid(TSceneInfoEntity tSceneInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSceneInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSceneInfo, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(StringUtil.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSceneInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除场景
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSceneInfoEntity tSceneInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
//		tSceneInfo = systemService.flushEntity(TSceneInfoEntity.class, tSceneInfo.getId());
		message = "场景删除成功";
		try{
			tSceneInfoService.deleteScene(tSceneInfo.getId());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "场景删除失败";
			throw new BusinessException(e.getMessage());
		}
		this.clearSceneRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除场景
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "场景删除成功";
		try{
			for(String id:ids.split(",")){
				tSceneInfoService.deleteScene(id);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "场景删除失败";
			throw new BusinessException(e.getMessage());
		}
		this.clearSceneRedis();
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加场景
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSceneInfoEntity tSceneInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "场景添加成功";
		try{
			tSceneInfoService.saveScene(tSceneInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "场景添加失败";
			throw new BusinessException(e.getMessage());
		}
		this.clearSceneRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新场景
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSceneInfoEntity tSceneInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "场景更新成功";
		TSceneInfoEntity t = tSceneInfoService.get(TSceneInfoEntity.class, tSceneInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSceneInfo, t);
			tSceneInfoService.updateScene(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "场景更新失败";
			throw new BusinessException(e.getMessage());
		}
		this.clearSceneRedis();
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 场景新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSceneInfoEntity tSceneInfo, HttpServletRequest req) {
		TSUser user = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商
			req.setAttribute("retailerId", user.getId());
		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
			req.setAttribute("retailerId", user.getRetailerId());
		}
		return new ModelAndView("com/buss/base/tSceneInfo-add");
	}
	/**
	 * 场景编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSceneInfoEntity tSceneInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSceneInfo.getId())) {
			tSceneInfo = tSceneInfoService.flushEntity(TSceneInfoEntity.class, tSceneInfo.getId());
			List<TScenePicsEntity> detailPics = this.systemService.findHql("from TScenePicsEntity where sceneId =? and status = 'A' order by orderNum asc",tSceneInfo.getId() );
			req.setAttribute("tSceneInfoPage", tSceneInfo);
			req.setAttribute("picNums", detailPics.size());
			req.setAttribute("detailPics", detailPics);
		}
		return new ModelAndView("com/buss/base/tSceneInfo-update");
	}
	
	/**
	 * 场景查看页面跳转
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TSceneInfoEntity tSceneInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSceneInfo.getId())) {
			tSceneInfo = tSceneInfoService.flushEntity(TSceneInfoEntity.class, tSceneInfo.getId());
			List<TScenePicsEntity> detailPics = this.systemService.findHql("from TScenePicsEntity where sceneId =? and status = 'A' order by orderNum asc",tSceneInfo.getId() );
			req.setAttribute("tSceneInfoPage", tSceneInfo);
			req.setAttribute("picNums", detailPics.size());
			req.setAttribute("detailPics", detailPics);
		}
		return new ModelAndView("com/buss/base/tSceneInfo-view");
	}
	
	/**
	 * 上传场景图片
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
			ftpUtil.uploadFileToFtpServer(GlobalConstants.SCENE, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
							 +ResourceUtil.getConfigByName("imgRootPath")+"/"
							 +GlobalConstants.SCENE+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}
	
	
	/**
	 * 清除 场景 getSceneList*  redis缓存
	 */
	public void clearSceneRedis() {
		try {
			redisService.batchDel("getSceneList*"+ResourceUtil.getRetailerId()+"*");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}
	}
}
