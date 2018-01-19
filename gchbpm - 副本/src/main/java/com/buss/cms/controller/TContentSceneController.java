package com.buss.cms.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.cms.entity.TContentSceneEntity;
import com.buss.cms.service.TContentCustomIndexServiceI;
import com.buss.cms.service.TContentSceneServiceI;



/**   
 * @Title: Controller
 * @Description: 首页场景图
 * @author onlineGenerator
 * @date 2016-09-22 22:13:31
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tContentSceneController")
public class TContentSceneController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TContentSceneController.class);

	@Autowired
	private TContentSceneServiceI tContentSceneService;
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
	 * 首页场景图列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tContentScene")
	public ModelAndView tContentScene(HttpServletRequest request) {
		return new ModelAndView("com/buss/cms/tContentSceneList");
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
	public void datagrid(TContentSceneEntity tContentScene,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			String name = request.getParameter("name");
			String sql ="SELECT t.id,t.content_id,t.retailer_id as retailerId,i.title from t_content_scene t LEFT JOIN t_scene_info i on t.scene_id = i.id"
				+" where t.status = 'A' and t.retailer_id = '"+retailerId+"' ";
			String countSql = "select count(1) from t_content_scene t LEFT JOIN t_scene_info i on t.scene_id = i.id where t.status = 'A' and t.retailer_id = '"+retailerId+"'  ";
			if(!StringUtil.isEmpty(name)){
				sql +=" and i.name like '%"+name+"%'";
				countSql +=" and i.name like '%"+name+"%'";
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除首页场景图
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TContentSceneEntity tContentScene, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tContentScene = systemService.flushEntity(TContentSceneEntity.class, tContentScene.getId());
		message = "首页场景图删除成功";
		try{
			tContentScene.setStatus("I");
			tContentSceneService.updateEntitie(tContentScene);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "首页场景图删除失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除首页场景图
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "首页场景图删除成功";
		try{
			for(String id:ids.split(",")){
				TContentSceneEntity tContentScene = systemService.flushEntity(TContentSceneEntity.class,  id );
				tContentScene.setStatus("I");
				tContentSceneService.updateEntitie(tContentScene);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "首页场景图删除失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加首页场景图
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TContentSceneEntity tContentScene, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "首页场景图添加成功";
		try{
			tContentSceneService.save(tContentScene);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "首页场景图添加失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新首页场景图
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TContentSceneEntity tContentScene, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "首页场景图更新成功";
		TContentSceneEntity t = tContentSceneService.get(TContentSceneEntity.class, tContentScene.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tContentScene, t);
			tContentSceneService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "首页场景图更新失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 首页场景图新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TContentSceneEntity tContentScene, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tContentScene.getId())) {
			tContentScene = tContentSceneService.flushEntity(TContentSceneEntity.class, tContentScene.getId());
			req.setAttribute("tContentScenePage", tContentScene);
		}
		return new ModelAndView("com/buss/cms/tContentScene-add");
	}
	
	/**
	 * 批量添加 场景
	 * @return
	 */
	 @RequestMapping(params = "doBatchAdd")
	@ResponseBody
	public AjaxJson doBatchAdd(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "场景添加成功";
		try{
			String contentId = request.getParameter("content_Id");
			String sceneIds = request.getParameter("sceneIds");
			String retailerId = ResourceUtil.getRetailerId();
			for(String sceneId:sceneIds.split(",")){
				TContentSceneEntity tContentScene = new TContentSceneEntity();
				tContentScene.setStatus(common.GlobalConstants.STATUS_ACTIVE);
				tContentScene.setSceneId(sceneId);
				tContentScene.setRetailerId(retailerId);
				tContentScene.setContentId(Long.valueOf(contentId));
				tContentSceneService.save(tContentScene);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "场景添加失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
}
