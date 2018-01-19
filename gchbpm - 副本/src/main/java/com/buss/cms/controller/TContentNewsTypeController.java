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

import com.buss.cms.entity.TContentNewsTypeEntity;
import com.buss.cms.service.TContentCustomIndexServiceI;
import com.buss.cms.service.TContentNewsTypeServiceI;



/**   
 * @Title: Controller
 * @Description: 首页资讯分类选择
 * @author onlineGenerator
 * @date 2016-09-22 22:13:25
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tContentNewsTypeController")
public class TContentNewsTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TContentNewsTypeController.class);

	@Autowired
	private TContentNewsTypeServiceI tContentNewsTypeService;
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
	 * 首页资讯分类选择列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tContentNewsType")
	public ModelAndView tContentNewsType(HttpServletRequest request) {
		return new ModelAndView("com/buss/cms/tContentNewsTypeList");
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
	public void datagrid(TContentNewsTypeEntity tContentNewsType,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			String name = request.getParameter("name");
			String sql ="SELECT t.id,t.content_id as contentId,t.retailer_id as retailerId,n.name from t_content_news_type t LEFT JOIN t_template_type n on t.news_type_id = n.id"
				+" where t.status = 'A' and t.retailer_id = '"+retailerId+"' ";
			String countSql = "select count(1) from t_content_news_type t LEFT JOIN t_template_type n on t.news_type_id = n.id where t.status = 'A' and t.retailer_id = '"+retailerId+"'  ";
			if(!StringUtil.isEmpty(name)){
				sql +=" and n.name like '%"+name+"%'";
				countSql +=" and n.name like '%"+name+"%'";
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
	 * 删除首页资讯分类选择
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TContentNewsTypeEntity tContentNewsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tContentNewsType = systemService.flushEntity(TContentNewsTypeEntity.class, tContentNewsType.getId());
		message = "首页资讯分类选择删除成功";
		try{
			tContentNewsType.setStatus("I");
			tContentNewsTypeService.updateEntitie(tContentNewsType);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "首页资讯分类选择删除失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除首页资讯分类选择
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "首页资讯分类选择删除成功";
		try{
			for(String id:ids.split(",")){
				TContentNewsTypeEntity tContentNewsType = systemService.flushEntity(TContentNewsTypeEntity.class,  id );
				tContentNewsType.setStatus("I");
				tContentNewsTypeService.updateEntitie(tContentNewsType);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "首页资讯分类选择删除失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加首页资讯分类选择
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TContentNewsTypeEntity tContentNewsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "首页资讯分类选择添加成功";
		try{
			tContentNewsTypeService.save(tContentNewsType);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "首页资讯分类选择添加失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量添加活动商品
	 * @return
	 */
	 @RequestMapping(params = "doBatchAdd")
	@ResponseBody
	public AjaxJson doBatchAdd(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "首页资讯添加成功";
		try{
			String newsTypeIds = request.getParameter("newsTypeIds");
			String contentId = request.getParameter("contentId");
			String retailerId = ResourceUtil.getRetailerId();
			for(String newsTypeId:newsTypeIds.split(",")){
				TContentNewsTypeEntity tContentNewsType = new TContentNewsTypeEntity();
				tContentNewsType.setStatus(common.GlobalConstants.STATUS_ACTIVE);
				tContentNewsType.setNewsTypeId(newsTypeId);
				tContentNewsType.setContentId(Long.valueOf(contentId));
				tContentNewsType.setRetailerId(retailerId);
				tContentNewsTypeService.save(tContentNewsType);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "首页资讯添加失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
}
