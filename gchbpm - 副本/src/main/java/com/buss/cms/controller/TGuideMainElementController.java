package com.buss.cms.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.base.entity.BaseBrandEntity;
import com.buss.cms.entity.TGuideMainElementEntity;
import com.buss.cms.service.TContentCustomIndexServiceI;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 内容管理
 * @author onlineGenerator
 * @date 2016-09-22 22:13:54
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGuideMainElementController")
public class TGuideMainElementController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGuideMainElementController.class);

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
	 * 内容管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goGuideMainElementList")
	public ModelAndView tContentCategory(HttpServletRequest request, HttpServletResponse response) {
		//零售商ID
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isEmpty(retailerId)){
			request.setAttribute("userType","01");
		}else {
			request.setAttribute("userType","02");
		}
		
		return new ModelAndView("com/buss/cms/tGuideMainElementList");
	}

	/**
	 * easyui AJAX请求数据       话术列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		tContentCustomIndexService.getGuideMainElementDatagrid(request, dataGrid);
		List<TGuideMainElementEntity> list = dataGrid.getResults();
		if(Utility.isNotEmpty(list)){
			for(TGuideMainElementEntity entity : list){
				if("SHANGPIN".equals(entity.getGroupCode())){
					entity.setPic(entity.getPic()+"?imageView2/1/w/130/h/80");
					entity.setCustomPic(entity.getCustomPic()+"?imageView2/1/w/130/h/80");
				}else if("HUATI".equals(entity.getGroupCode())){
					entity.setPic(entity.getPic()+"?imageView2/1/w/130/h/80");
					entity.setCustomPic(entity.getCustomPic()+"?imageView2/1/w/130/h/80");
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}
    
	/**
	 *内容列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TGuideMainElementEntity tGuideMainElementEntity, HttpServletRequest req) {
		String functionid = req.getParameter("id");
		if (functionid != null) {
			tGuideMainElementEntity = systemService.flushEntity(TGuideMainElementEntity.class, functionid);
			req.setAttribute("tGuideMainElement", tGuideMainElementEntity);
		}
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return new ModelAndView("com/buss/cms/tGuideMainElement-update");
	}
	
	
	/**
	 * 内容更新
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TGuideMainElementEntity tGuideMainElementEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			message = "内容: " + tGuideMainElementEntity.getElementTitle() + "更新成功";
			tContentCustomIndexService.guideMaimElement(request);
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			message = "内容: " + tGuideMainElementEntity.getElementTitle() + "更新失败";
			e.printStackTrace();
		}
        j.setMsg(message);
		return j;
	}
	/**
	 * 内容更新
	 */
	@RequestMapping(params = "doChangeOrderNum")
	@ResponseBody
	public AjaxJson doChangeOrderNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			message = "导购端主页分类更新成功";
			tContentCustomIndexService.guideMaimElement(request);
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			message = "导购端主页分类更新失败";
			e.printStackTrace();
		}
        j.setMsg(message);
		return j;
	}
	/**
	 * 删除APP首页分类图片
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "删除图片成功";
		//零售商ID
		String retailerId = ResourceUtil.getRetailerId();
		
		String id = request.getParameter("id");
		TGuideMainElementEntity  tGuideMainElementEntity = null;
		if (Utility.isNotEmpty(id)) {
			tGuideMainElementEntity = systemService.flushEntity(TGuideMainElementEntity.class, id);
		}
		String userId = null;
		if(Utility.isNotEmpty(tGuideMainElementEntity)){
			userId = tGuideMainElementEntity.getRetailerId();
		}
		String sql = "update t_guide_main_element set status='I' where id='"+id+"'";
		
		if(Utility.isNotEmpty(retailerId)){//零售商删除
			if(retailerId.equals(userId)){//自己删除
				systemService.updateBySqlString(sql);
			}else{//删除默认图片
				message = "不能删除默认图片";
			}
		}else{//g+删除
			message = "只能更新默认图片";
		}
        j.setMsg(message);
		return j;
	}
}