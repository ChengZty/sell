package com.buss.member.controller;
import com.buss.member.entity.TMembershipEntity;
import com.buss.member.service.TMembershipServiceI;
import common.GlobalConstants;

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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSDepart;
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



/**   
 * @Title: Controller
 * @Description: 会员资格
 * @author onlineGenerator
 * @date 2016-09-19 12:06:06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tMembershipController")
public class TMembershipController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TMembershipController.class);

	@Autowired
	private TMembershipServiceI tMembershipService;
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
	 * 会员资格列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tMembership")
	public ModelAndView tMembership(HttpServletRequest request) {
		return new ModelAndView("com/buss/member/tMembershipList");
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
	public void datagrid(TMembershipEntity tMembership,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TMembershipEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tMembership, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tMembershipService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除会员资格
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TMembershipEntity tMembership, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tMembership = systemService.flushEntity(TMembershipEntity.class, tMembership.getId());
		message = "会员资格删除成功";
		try{
			tMembership.setStatus("I");
			tMembershipService.updateEntitie(tMembership);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "会员资格删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除会员资格
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "会员资格删除成功";
		try{
			for(String id:ids.split(",")){
				TMembershipEntity tMembership = systemService.flushEntity(TMembershipEntity.class,  id );
				tMembership.setStatus("I");
				tMembershipService.updateEntitie(tMembership);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "会员资格删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加会员资格
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TMembershipEntity tMembership, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "会员资格添加成功";
		try{
			tMembershipService.save(tMembership);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "会员资格添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新会员资格
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TMembershipEntity tMembership, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "会员资格更新成功";
		TMembershipEntity t = tMembershipService.get(TMembershipEntity.class, tMembership.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tMembership, t);
			t.setFaceValueEnd(tMembership.getFaceValueEnd());
			t.setFaceValueStart(tMembership.getFaceValueStart());
			tMembershipService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "会员资格更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 会员资格新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TMembershipEntity tMembership, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tMembership.getId())) {
			tMembership = tMembershipService.flushEntity(TMembershipEntity.class, tMembership.getId());
			req.setAttribute("tMembershipPage", tMembership);
		}
		return new ModelAndView("com/buss/member/tMembership-add");
	}
	/**
	 * 会员资格编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TMembershipEntity tMembership, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tMembership.getId())) {
			tMembership = tMembershipService.flushEntity(TMembershipEntity.class, tMembership.getId());
			req.setAttribute("tMembershipPage", tMembership);
		}
		return new ModelAndView("com/buss/member/tMembership-update");
	}
	
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadPic", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadPic(TSCategoryEntity category,HttpServletRequest request) throws Exception {
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
			ftpUtil.uploadFileToFtpServer(GlobalConstants.MEMBERSHIP, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
							 +ResourceUtil.getConfigByName("imgRootPath")+"/"
							 +GlobalConstants.MEMBERSHIP+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}
}
