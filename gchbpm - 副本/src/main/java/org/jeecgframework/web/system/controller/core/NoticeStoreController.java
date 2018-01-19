package org.jeecgframework.web.system.controller.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JPushUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TStoreSNotice;
import org.jeecgframework.web.system.service.NoticeStoreAuthorityUserServiceI;
import org.jeecgframework.web.system.service.NoticeStoreService;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.JPushClient;
import common.GlobalConstants;

/**
 * 通知公告
 * @author alax
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/noticeStore")
public class NoticeStoreController extends BaseController{
	private SystemService systemService;
	@Autowired
	private NoticeStoreAuthorityUserServiceI noticeStoreAuthorityUserService;
	@Autowired
	private NoticeStoreService noticeStoreService;
	private String message;
	
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	/**
	 * 通知公告列表(管理) 页面跳转
	 *
	 * 
	 * @return
	 */
	@RequestMapping(params = "tStoreSNotice")
	public ModelAndView tStoreSNotice(HttpServletRequest request) {
		return new ModelAndView("system/user/tStoreSNoticeList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid2")
	public void datagrid2(TStoreSNotice TStoreSNotice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TStoreSNotice.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, TStoreSNotice, request.getParameterMap());
		try
		{
			TSUser user = ResourceUtil.getSessionUserName();
			String storeId = null;
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){
				storeId = user.getId();
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				storeId = user.getRetailerId();
			}
			cq.eq("storeId", storeId);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.noticeStoreService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除通知公告
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TStoreSNotice tStoreSNotice, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tStoreSNotice = systemService.flushEntity(TStoreSNotice.class, tStoreSNotice.getId());
		message = "通知公告删除成功";
		try{
//			noticeStoreService.delete(TStoreSNotice);
			tStoreSNotice.setStatus(GlobalConstants.STATUS_INACTIVE);
			this.noticeStoreService.updateEntitie(tStoreSNotice);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "通知公告删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除通知公告
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "通知公告删除成功";
		try{
			for(String id:ids.split(",")){
				TStoreSNotice tStoreSNotice = systemService.flushEntity(TStoreSNotice.class, id);
				//noticeStoreService.delete(tStoreSNotice);
				tStoreSNotice.setStatus(GlobalConstants.STATUS_INACTIVE);
				this.noticeStoreService.updateEntitie(tStoreSNotice);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "通知公告删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加通知公告
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TStoreSNotice tStoreSNotice, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "通知公告添加成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			String storeId = null;
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){
				storeId = user.getId();
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				storeId = user.getRetailerId();
			}
			tStoreSNotice.setStoreId(storeId);
			noticeStoreService.save(tStoreSNotice);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "通知公告添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新通知公告
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TStoreSNotice tStoreSNotice, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "通知公告更新成功";
		TStoreSNotice t = noticeStoreService.get(TStoreSNotice.class, tStoreSNotice.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tStoreSNotice, t);
			TSUser user = ResourceUtil.getSessionUserName();
			String storeId = null;
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){
				storeId = user.getId();
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				storeId = user.getRetailerId();
			}
			tStoreSNotice.setStoreId(storeId);
			noticeStoreService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "通知公告更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 通知公告新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TStoreSNotice tStoreSNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tStoreSNotice.getId())) {
			tStoreSNotice = noticeStoreService.flushEntity(TStoreSNotice.class, tStoreSNotice.getId());
			req.setAttribute("tSNoticePage", tStoreSNotice);
		}
		return new ModelAndView("system/user/tStoreSNotice-add");
	}
	/**
	 * 通知公告编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TStoreSNotice tStoreSNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tStoreSNotice.getId())) {
			tStoreSNotice = noticeStoreService.flushEntity(TStoreSNotice.class, tStoreSNotice.getId());
			req.setAttribute("tSNoticePage", tStoreSNotice);
		}
		return new ModelAndView("system/user/tStoreSNotice-update");
	}
	
	
	
	/**
	 * 推送消息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doSend")
	@ResponseBody
	public AjaxJson doSend(TStoreSNotice tStoreSNotice, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "推送成功";
		try 
		{
			tStoreSNotice = systemService.flushEntity(TStoreSNotice.class, tStoreSNotice.getId());
			JPushClient jpush = new JPushClient(ResourceUtil.getConfigByName("masterSecretGuide"), ResourceUtil.getConfigByName("appKeyGuide"));
			//1全体导购          2授权导购
			int errCount = 0;
			String title = "您有新的公司通知";
			if(TStoreSNotice.NOTICE_LEVEL_ALL.equals(tStoreSNotice.getNoticeLevel()))
			{
				errCount = JPushUtil.sendJPushNotificationByTags(jpush, title, tStoreSNotice.getNoticeTitle(),tStoreSNotice.getStoreId());
			}else if(TStoreSNotice.NOTICE_LEVEL_SPEC.equals(tStoreSNotice.getNoticeLevel())){
				List<String> list = this.noticeStoreAuthorityUserService.findListByNoticeId(tStoreSNotice.getId());
				if(!Utility.isEmpty(list)){
					errCount = JPushUtil.sendJPushNotificationByAlias(jpush,list, title, tStoreSNotice.getNoticeTitle());
				}
			}
			if(errCount < 1){
				tStoreSNotice.setIsSend("Y");
				noticeStoreService.saveOrUpdate(tStoreSNotice);
				systemService.addLog(message, Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
			}else{
				message = "通知公告推送失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "通知公告推送失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
}
