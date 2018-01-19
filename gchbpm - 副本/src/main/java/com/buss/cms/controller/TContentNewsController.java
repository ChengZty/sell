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
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.cms.entity.TContentNewsEntity;
import com.buss.cms.service.TContentCustomIndexServiceI;
import com.buss.cms.service.TContentNewsServiceI;



/**   
 * @Title: Controller
 * @Description: 首页资讯表
 * @author onlineGenerator
 * @date 2016-09-22 22:13:38
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tContentNewsController")
public class TContentNewsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TContentNewsController.class);

	@Autowired
	private TContentNewsServiceI tContentNewsService;
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
	 * 首页资讯表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tContentNews")
	public ModelAndView tContentNews(HttpServletRequest request) {
		return new ModelAndView("com/buss/cms/tContentNewsList");
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
	public void datagrid(TContentNewsEntity tContentNews,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TContentNewsEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tContentNews, request.getParameterMap());
		try{
			//自定义追加查询条件
				String content_Id = request.getParameter("content_Id");
				String title = request.getParameter("title");
				String retailerId = ResourceUtil.getRetailerId();
				String sql ="SELECT t.id,t.content_id as contentId,t.retailer_id as retailerId,n.title from t_content_news t LEFT JOIN t_news n on t.news_id = n.id"
					+" where t.status = 'A' and t.retailer_id = '"+retailerId+"'  and t.content_id='"+content_Id+"' ";
				String countSql = "select count(1) from t_content_news t LEFT JOIN t_news n on t.news_id = n.id where t.status = 'A' and t.retailer_id = '"+retailerId+"'  and t.content_id='"+content_Id+"'";
				if(!StringUtil.isEmpty(title)){
					sql +=" and n.title like '%"+title+"%'";
					countSql +=" and n.title like '%"+title+"%'";
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
	 * 删除首页资讯表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TContentNewsEntity tContentNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tContentNews = systemService.flushEntity(TContentNewsEntity.class, tContentNews.getId());
		message = "首页资讯表删除成功";
		try{
			tContentNews.setStatus("I");
			tContentNewsService.updateEntitie(tContentNews);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "首页资讯表删除失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除首页资讯表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "首页资讯删除成功";
		try{
			for(String id:ids.split(",")){
				TContentNewsEntity tContentNews = systemService.flushEntity(TContentNewsEntity.class, id);
				tContentNews.setStatus("I");
				tContentNewsService.updateEntitie(tContentNews);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "首页资讯删除失败";
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
				String contentId = request.getParameter("content_Id");
				String newsIds = request.getParameter("newsIds");
				TSUser user = ResourceUtil.getSessionUserName();
				String retailerId = null;
				if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
					retailerId = user.getId();
				}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
					retailerId = user.getRetailerId();
				}
				for(String newsId:newsIds.split(",")){
					TContentNewsEntity tContentNews = new TContentNewsEntity();
					tContentNews.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					tContentNews.setContentId(Long.valueOf(contentId));
					tContentNews.setNewsId(newsId);
					tContentNews.setRetailerId(retailerId);
					tContentNewsService.save(tContentNews);
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
	
	/**
	 * 更新首页资讯表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TContentNewsEntity tContentNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "首页资讯表更新成功";
		TContentNewsEntity t = tContentNewsService.get(TContentNewsEntity.class, tContentNews.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tContentNews, t);
			tContentNewsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "首页资讯表更新失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
}
