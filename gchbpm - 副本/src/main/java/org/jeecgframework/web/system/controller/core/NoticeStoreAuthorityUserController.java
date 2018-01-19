package org.jeecgframework.web.system.controller.core;
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
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TStoreSNoticeAuthorityUser;
import org.jeecgframework.web.system.service.NoticeStoreAuthorityUserServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**   
 * @Title: Controller
 * @Description: 通知公告用户授权
 * @author onlineGenerator
 * @date 2016-02-26 12:47:09
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/noticeStoreAuthorityUserController")
public class NoticeStoreAuthorityUserController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NoticeStoreAuthorityUserController.class);

	@Autowired
	private NoticeStoreAuthorityUserServiceI noticeStoreAuthorityUserService;
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
	 * 通知公告用户授权列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "noticeAuthorityUser")
	public ModelAndView noticeAuthorityUser(String noticeId,String isSend,HttpServletRequest request) {
		request.setAttribute("noticeId", noticeId);
		request.setAttribute("isSend", isSend);
//		String retailerId = ResourceUtil.getRetailerId();
//		request.setAttribute("storeId", retailerId);
		return new ModelAndView("system/user/noticeStoreAuthorityUserList");
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
	public void datagrid(TStoreSNoticeAuthorityUser noticeAuthorityUser,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TStoreSNoticeAuthorityUser.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, noticeAuthorityUser, request.getParameterMap());
		try{
		//自定义追加查询条件
			String notice_Id = request.getParameter("notice_Id");
			if(Utility.isNotEmpty(notice_Id)){
				cq.eq("noticeId", notice_Id);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.noticeStoreAuthorityUserService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除通知公告用户授权
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TStoreSNoticeAuthorityUser noticeAuthorityUser, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		noticeAuthorityUser = systemService.flushEntity(TStoreSNoticeAuthorityUser.class, noticeAuthorityUser.getId());
		message = "通知公告用户授权删除成功";
		try{
			noticeStoreAuthorityUserService.delete(noticeAuthorityUser);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "通知公告用户授权删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除通知公告用户授权
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "通知公告用户授权删除成功";
		try{
			for(String id:ids.split(",")){
				TStoreSNoticeAuthorityUser noticeAuthorityUser = systemService.flushEntity(TStoreSNoticeAuthorityUser.class, 
				id
				);
				noticeStoreAuthorityUserService.delete(noticeAuthorityUser);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "通知公告用户授权删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加通知公告用户授权
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TStoreSNoticeAuthorityUser noticeAuthorityUser, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "通知公告用户授权添加成功";
		try{
			noticeStoreAuthorityUserService.save(noticeAuthorityUser);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "通知公告用户授权添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 保存通知公告用户授权
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doSave")
	@ResponseBody
	public AjaxJson doSave(String userIds ,String noticeId,String tStoreId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "通知公告用户授权保存成功";
		try
		{
			if(Utility.isNotEmpty(tStoreId)){//查询该通知下选中店铺的所有未添加的导购
				List<Map<String, Object>> list = this.noticeStoreAuthorityUserService.
				findForJdbc("select user_id from t_person where store_id =? and status = 'A' and user_type = '03' and user_id not in (select user_id from t_s_notice_store_authority_user where notice_id = ?)", tStoreId,noticeId);
				if(list.size()>0){
					for(Map<String, Object> map : list){
						TStoreSNoticeAuthorityUser authorityUser = new TStoreSNoticeAuthorityUser();
						authorityUser.setNoticeId(noticeId);
						TSUser user = this.systemService.get(TSUser.class, map.get("user_id")+"");
						authorityUser.setUser(user);
						noticeStoreAuthorityUserService.save(authorityUser);
					}
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
			}
			if(Utility.isNotEmpty(userIds)){//通过勾选指定导购添加授权用户
				String[] ids = userIds.split(",");
				if(0 < ids.length){
					for (int i = 0; i < ids.length; i++) {
						if(this.noticeStoreAuthorityUserService.checkAuthorityUser(noticeId, ids[i])){
							continue;
						}else{
							TStoreSNoticeAuthorityUser authorityUser = new TStoreSNoticeAuthorityUser();
							authorityUser.setNoticeId(noticeId);
							TSUser user = this.systemService.get(TSUser.class, ids[i]);
							authorityUser.setUser(user);
							noticeStoreAuthorityUserService.save(authorityUser);
						}
					}
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "通知公告用户授权保存失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新通知公告用户授权
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TStoreSNoticeAuthorityUser noticeAuthorityUser, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "通知公告用户授权更新成功";
		TStoreSNoticeAuthorityUser t = noticeStoreAuthorityUserService.get(TStoreSNoticeAuthorityUser.class, noticeAuthorityUser.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(noticeAuthorityUser, t);
			noticeStoreAuthorityUserService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "通知公告用户授权更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 通知公告用户授权新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TStoreSNoticeAuthorityUser noticeAuthorityUser, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(noticeAuthorityUser.getId())) {
			noticeAuthorityUser = noticeStoreAuthorityUserService.flushEntity(TStoreSNoticeAuthorityUser.class, noticeAuthorityUser.getId());
			req.setAttribute("noticeAuthorityUserPage", noticeAuthorityUser);
		}
		return new ModelAndView("system/user/noticeAuthorityUser-add");
	}
	/**
	 * 通知公告用户授权编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TStoreSNoticeAuthorityUser noticeAuthorityUser, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(noticeAuthorityUser.getId())) {
			noticeAuthorityUser = noticeStoreAuthorityUserService.flushEntity(TStoreSNoticeAuthorityUser.class, noticeAuthorityUser.getId());
			req.setAttribute("noticeAuthorityUserPage", noticeAuthorityUser);
		}
		return new ModelAndView("system/user/noticeAuthorityUser-update");
	}
	
	/**
	 * 用户选择页面跳转
	 * @return
	 */
	@RequestMapping(params = "selectUser")
	public ModelAndView selectUser(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailerId", retailerId);
		request.setAttribute("noticeId", request.getParameter("noticeId"));//通知id
		return new ModelAndView("system/user/storeUserList-select");//勾选指定导购
	}
}
