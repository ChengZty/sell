package com.buss.news.controller;
import com.buss.news.entity.THiddenNewsEntity;
import com.buss.news.service.THiddenNewsServiceI;

import java.util.ArrayList;
import java.util.List;
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
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import org.jeecgframework.core.util.ResourceUtil;
import java.util.Map;



/**   
 * @Title: Controller
 * @Description: 不可见资讯
 * @author onlineGenerator
 * @date 2016-12-06 10:37:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tHiddenNewsController")
public class THiddenNewsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(THiddenNewsController.class);

	@Autowired
	private THiddenNewsServiceI tHiddenNewsService;
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
	 * 不可见资讯列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/news/tHiddenNewsList");
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
	public void datagrid(THiddenNewsEntity tHiddenNews,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String title = request.getParameter("title");
			String newsType = request.getParameter("newsType");
			String visible = request.getParameter("visible");
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sql = new StringBuffer("SELECT n.id AS newsId,n.title,n.cover_pic as coverPic,n.author,n.news_type as newsType,h.id AS id,")
				.append( "CASE WHEN h.id is NULL THEN	'1' ELSE '0' END as visible")
				.append(" from t_news n LEFT JOIN t_hidden_news h ON n.id = h.news_id and h.`status` = 'A'").append(" and h.retailer_id ='").append(retailerId)
				.append("' WHERE n.`status` = 'A' AND n.upLoaded = 'Y' AND n.shopkeeper IS NULL");
			StringBuffer countSql = new StringBuffer("select count(1) from t_news n LEFT JOIN t_hidden_news h ON n.id = h.news_id and h.`status` = 'A'")
				.append(" and h.retailer_id ='").append(retailerId).append("' WHERE n.`status` = 'A' AND n.upLoaded = 'Y' AND n.shopkeeper IS NULL");
			if(!StringUtil.isEmpty(title)){
				sql.append(" and n.title like '%").append(title).append("%'");
				countSql.append(" and n.title like '%").append(title).append("%'");
			}
			if(!StringUtil.isEmpty(newsType)){//防止查like '%2%' 的时候把like '%12%' 的结果查出来
				sql.append(" and CONCAT(',',n.news_type,',') like '%,").append(newsType).append(",%'");
				countSql.append(" and CONCAT(',',n.news_type,',') like '%,").append(newsType).append(",%'");
			}
			if(!StringUtil.isEmpty(visible)){
				if("1".equals(visible)){
					sql.append(" and h.id is null");
					countSql.append(" and h.id is null");
				}else if("0".equals(visible)){
					sql.append(" and h.id is not null");
					countSql.append(" and h.id is not null");
				}
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
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
	 * 删除不可见资讯
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(THiddenNewsEntity tHiddenNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tHiddenNews = systemService.flushEntity(THiddenNewsEntity.class, tHiddenNews.getId());
		message = "设置可见资讯成功";
		try{
			tHiddenNews.setStatus("I");
			tHiddenNewsService.updateEntitie(tHiddenNews);
		}catch(Exception e){
			e.printStackTrace();
			message = "设置可见资讯失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除不可见资讯
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "不可见资讯删除成功";
		try{
			for(String id:ids.split(",")){
				THiddenNewsEntity tHiddenNews = systemService.flushEntity(THiddenNewsEntity.class, 
				id
				);
				tHiddenNewsService.delete(tHiddenNews);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "不可见资讯删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加不可见资讯
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(THiddenNewsEntity tHiddenNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "设置不可见资讯成功";
		try{
			tHiddenNews.setRetailerId(ResourceUtil.getRetailerId());
			tHiddenNews.setStatus("A");
			tHiddenNewsService.save(tHiddenNews);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "设置不可见资讯失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新不可见资讯
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(THiddenNewsEntity tHiddenNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "不可见资讯更新成功";
		THiddenNewsEntity t = tHiddenNewsService.get(THiddenNewsEntity.class, tHiddenNews.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tHiddenNews, t);
			tHiddenNewsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "不可见资讯更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
}
