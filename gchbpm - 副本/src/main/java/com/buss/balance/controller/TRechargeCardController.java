package com.buss.balance.controller;
import com.buss.balance.entity.TRechargeCardEntity;
import com.buss.balance.service.TRechargeCardServiceI;
import common.GlobalConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.InputStream;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;



/**   
 * @Title: Controller
 * @Description: 面额
 * @author onlineGenerator
 * @date 2016-09-19 12:00:26
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tRechargeCardController")
public class TRechargeCardController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TRechargeCardController.class);

	@Autowired
	private TRechargeCardServiceI tRechargeCardService;
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
	 * 面额列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tRechargeCard")
	public ModelAndView tRechargeCard(HttpServletRequest request) {
		return new ModelAndView("com/buss/balance/tRechargeCardList");
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
	public void datagrid(TRechargeCardEntity tRechargeCard,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TRechargeCardEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tRechargeCard, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tRechargeCardService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除面额
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TRechargeCardEntity tRechargeCard, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tRechargeCard = systemService.flushEntity(TRechargeCardEntity.class, tRechargeCard.getId());
		message = "面额删除成功";
		try{
			tRechargeCard.setStatus("I");
			tRechargeCardService.updateEntitie(tRechargeCard);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "面额删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除面额
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "面额删除成功";
		try{
			for(String id:ids.split(",")){
				TRechargeCardEntity tRechargeCard = systemService.flushEntity(TRechargeCardEntity.class, id );
				tRechargeCard.setStatus("I");
				tRechargeCardService.updateEntitie(tRechargeCard);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "面额删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加面额
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TRechargeCardEntity tRechargeCard, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "面额添加成功";
		try{
			tRechargeCardService.save(tRechargeCard);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "面额添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新面额
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TRechargeCardEntity tRechargeCard, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "面额更新成功";
		TRechargeCardEntity t = tRechargeCardService.get(TRechargeCardEntity.class, tRechargeCard.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tRechargeCard, t);
			tRechargeCardService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "面额更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 面额新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TRechargeCardEntity tRechargeCard, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tRechargeCard.getId())) {
			tRechargeCard = tRechargeCardService.flushEntity(TRechargeCardEntity.class, tRechargeCard.getId());
			req.setAttribute("tRechargeCardPage", tRechargeCard);
		}
		return new ModelAndView("com/buss/balance/tRechargeCard-add");
	}
	/**
	 * 面额编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TRechargeCardEntity tRechargeCard, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tRechargeCard.getId())) {
			tRechargeCard = tRechargeCardService.flushEntity(TRechargeCardEntity.class, tRechargeCard.getId());
			req.setAttribute("tRechargeCardPage", tRechargeCard);
		}
		return new ModelAndView("com/buss/balance/tRechargeCard-update");
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
			ftpUtil.uploadFileToFtpServer(GlobalConstants.CARD_PICS, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
							 +ResourceUtil.getConfigByName("imgRootPath")+"/"
							 +GlobalConstants.CARD_PICS+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}	
}
