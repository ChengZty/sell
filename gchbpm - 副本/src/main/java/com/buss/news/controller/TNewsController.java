package com.buss.news.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.baidu.ueditor.UeditorContent;
import com.buss.news.entity.TNewsEntity;
import com.buss.news.entity.TNewsTypeEntity;
import com.buss.news.service.TNewsServiceI;
import com.buss.news.service.TNewsTypeServiceI;

import cn.redis.service.RedisService;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 资讯信息表
 * @author onlineGenerator
 * @date 2016-03-14 16:00:50
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tNewsController")
public class TNewsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TNewsController.class);

	@Autowired
	private TNewsServiceI tNewsService;
	@Autowired
	private TNewsTypeServiceI tNewsTypeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RedisService redisService;
	private String message;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final ResourceBundle env = ResourceBundle.getBundle("env");
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 平台资讯管理列表tab 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tNewsMain")
	public ModelAndView tNewsMain(HttpServletRequest request) {
		return new ModelAndView("com/buss/news/tNewsMain");
	}
	/**
	 * 零售商资讯管理列表tab 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tRetailerNewsMain")
	public ModelAndView tRetailerNewsMain(HttpServletRequest request) {
		return new ModelAndView("com/buss/news/tRetailerNewsMain");
	}
	
	/**
	 * 平台资讯信息表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "platformNewsList")
	public ModelAndView platformNewsList(HttpServletRequest request) {
		//获取话题分类
		this.tNewsService.getNewsTypeArr(request);
		//获取行业信息
		getTemplateTrade(request);
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("userType", user.getUserType());
		return new ModelAndView("com/buss/news/tNewsList");
	}
	
	/**
	 * 零售商个人资讯信息表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerNewsList")
	public ModelAndView retailerNewsList(HttpServletRequest request) {
			//获取话题分类
			this.tNewsService.getNewsTypeArr(request);
			//获取行业信息
			getTemplateTrade(request);
			request.setAttribute("rId", ResourceUtil.getRetailerId());
			String  newsUrl = env.getObject("REQUEST_URL")+"tNewsController.do?viewNewsContent&nid=";
			request.setAttribute("newsUrl",newsUrl );
		return new ModelAndView("com/buss/news/retailerNewsList");
	}
	
	/**
	 * 导购激励活动和促销活动页面选择话题 页面跳转
	 * @return
	 */
	@RequestMapping(params = "newsListForAct")
	public ModelAndView newsListForAct(HttpServletRequest request) {
			//获取话题分类
			this.tNewsService.getNewsTypeArr(request);
			//获取行业信息
			getTemplateTrade(request);
		return new ModelAndView("com/buss/news/newsListForAct");
	}

	/**
	 * 全部零售商资讯信息表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "allRetailerNewsList")
	public ModelAndView allRetailerNewsList(HttpServletRequest request) {
		//获取话题分类
		this.tNewsService.getNewsTypeArr(request);
		//获取行业信息
		getTemplateTrade(request);
		//获取所有资讯分类唯一的code和name
//		CommonVo vo = this.tNewsTypeService.getAllNewsTypeCodeAndNameVo();
//		request.setAttribute("vo", vo);
		return new ModelAndView("com/buss/news/allRetailerNewsList");
	}
	
	/**
	 * APP首页资讯列表
	 * @return
	 */
	@RequestMapping(params = "tNewsAppList")
	public ModelAndView tNewsAppList(HttpServletRequest request) {
		//获取话题分类
		this.tNewsService.getNewsTypeArr(request);
		//获取行业信息
		getTemplateTrade(request);
		request.setAttribute("content_Id", request.getParameter("content_Id"));
		return new ModelAndView("com/buss/news/tNewsAppList");
	}

	/**
	 * 查看资讯列表（选择资讯商品页面）
	 * @return
	 */
	@RequestMapping(params = "newslist")
	public ModelAndView newslist(HttpServletRequest request) {
		//获取话题分类
		this.tNewsService.getNewsTypeArr(request);
		//获取行业信息
		getTemplateTrade(request);
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isEmpty(retailerId)){
			//获取所有资讯分类唯一的code和name
//			CommonVo vo = this.tNewsTypeService.getAllNewsTypeCodeAndNameVo();
//			request.setAttribute("vo", vo);
			return new ModelAndView("com/buss/template/newsList");
		}
		request.setAttribute("retailerId", retailerId);
		return new ModelAndView("com/buss/news/newsListOfRet");
	}
	
	/**
	 * 管家课堂 /管家故事 tab页面  
	 * t  1:管家课堂,2:管家故事
	 * @return
	 */
	@RequestMapping(params = "tNewsOfTypeMain")
	public ModelAndView tNewsOfTypeMain(HttpServletRequest request) {
		//获取行业信息
		String userType = ResourceUtil.getSessionUserName().getUserType(); 
		request.setAttribute("userType", userType);
		request.setAttribute("newsType", request.getParameter("t"));
		return new ModelAndView("com/buss/news/tNewsOfTypeMain");
	}
	
	/**
	 * 管家课堂列表
	 * news_Type 6001：管家课堂,6002：管家故事
	 * isRet 0：平台,1：零售商,A:全部零售商
	 * @return
	 */
	@RequestMapping(params = "tNewsOfType")
	public ModelAndView tNewsOfType(HttpServletRequest request) {
		String newsType = request.getParameter("newsType");
		String isRet = request.getParameter("isRet");
		request.setAttribute("newsType", newsType);
		request.setAttribute("isRet", isRet);
		String userType = ResourceUtil.getSessionUserName().getUserType(); 
		request.setAttribute("userType", userType);
		if(TNewsEntity.NEWS_TYPE_COURSE.equals(newsType)){//管家课堂
			request.setAttribute("title", "管家课堂");
		}else if(TNewsEntity.NEWS_TYPE_STORY.equals(newsType)){//管家故事
			request.setAttribute("title", "管家故事");
		}

		//获取行业信息
		getTemplateTrade(request);
		
		if("0".equals(isRet)){//平台
			return new ModelAndView("com/buss/news/tNewsOfType");
		}else{//零售商
			return new ModelAndView("com/buss/news/tNewsOfTypeForRet");
		}
//		return new ModelAndView("com/buss/news/tNewsOfType");
	}

	//获取行业
	private void getTemplateTrade(HttpServletRequest request) {
		//获取行业
		String sqlTrade = "select * FROM t_s_category WHERE status='A' and level='2'";
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){
			sqlTrade = "select tsc.* FROM t_s_trade_user ttt left join t_s_category tsc on tsc.id = ttt.trade_id "+
			" WHERE tsc.status='A' and tsc.level='2' and ttt.status='A' and user_id = '"+retailerId+"'";
		}
		List<TSCategoryEntity> templateTrade = systemService.findObjForJdbc(sqlTrade, TSCategoryEntity.class);
		request.setAttribute("templateTrade", templateTrade);
	}
	
	
	/** 话题列表
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TNewsEntity tNews,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		tNewsService.getNewsDatagrid(request, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**管家课堂，管家故事
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "guideNewsDatagrid")
	public void guideNewsDatagrid(TNewsEntity tNews,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		tNewsService.getGuideNewsDatagrid(request, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui 全部零售商资讯
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
/*
	@RequestMapping(params = "datagridOfAll")
	public void datagridOfAll(TNewsEntity tNews,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
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
	}*/
	
	/**
	 * easyui app首页选中资讯
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	
	@RequestMapping(params = "datagridOfAppNews")
	public void datagridOfAppNews(TNewsEntity tNews,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TNewsEntity.class, dataGrid);
		//查询条件组装器
		String contentId = request.getParameter("content_Id");
		String retailerId = ResourceUtil.getRetailerId();
		String news_Type = request.getParameter("news_Type");
		StringBuffer sqlstr = new StringBuffer(" id not in	(select news_id	from t_content_news where retailer_id = '").append(retailerId).append("' and content_id ='")
				.append(contentId).append("' and status = 'A' )");
//		StringBuffer sqlstr = new StringBuffer(" NOT EXISTS(select	id	from t_content_news where retailer_id = '"+retailerId+"' and content_id ='"+contentId+"' and status = 'A' and news_id = this_.id)");
		if(!StringUtil.isEmpty(news_Type)){//防止查like '%2%' 的时候把like '%12%' 的结果查出来
			sqlstr.append(" and CONCAT(',',news_type,',') like '%,").append(news_Type).append(",%'");
		}else{//过滤管家课堂和管家故事
			sqlstr.append(" and news_type not in('").append(TNewsEntity.NEWS_TYPE_COURSE)//管家课堂
			.append("','").append(TNewsEntity.NEWS_TYPE_STORY).append("')");//管家故事
		}
//		sqlstr.append("and id not in (select news_id from  t_hidden_news where status = 'A' and retailer_id = '").append(retailerId).append("')");
		cq.eq("upLoaded", "Y");
		cq.eq("shopkeeper", retailerId);
//		cq.or(Restrictions.isNull("shopkeeper"), Restrictions.eq("shopkeeper", retailerId));
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tNews, request.getParameterMap(),sqlstr.toString());
		try{
			//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tNewsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除资讯信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TNewsEntity tNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tNews = systemService.flushEntity(TNewsEntity.class, tNews.getId());
		message = "资讯信息表删除成功";
		try{
			tNews.setStatus(GlobalConstants.STATUS_INACTIVE);
			tNewsService.updateEntitie(tNews);
//			tNewsService.delete(tNews);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "资讯信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除资讯信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "资讯信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TNewsEntity tNews = systemService.flushEntity(TNewsEntity.class, 
				id
				);
				tNewsService.delete(tNews);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "资讯信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加资讯信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TNewsEntity tNews, HttpServletRequest request) {
		// 话术的的行业信息
		String tradeId = oConvertUtils.getString(request.getParameter("tradeId"));
		AjaxJson j = new AjaxJson();
		message = "资讯信息添加成功";
		try{
			tNews.setNewsContext(this.clearFirstEmptyPtag(tNews.getNewsContext()));
//			tNews.setUpdateDate(Utility.getCurrentTimestamp());
			String retailerId = ResourceUtil.getRetailerId();
			if(StringUtil.isEmpty(retailerId)){
				retailerId = "admin";
			}
			tNews.setShopkeeper(retailerId);
			tNewsService.save(tNews);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			
			//保存话题的行业类型
			if (StringUtil.isNotEmpty(tradeId)) {
				tNewsService.saveTradeTemplate(tNews.getId(), tradeId);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "资讯信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{
			if("Y".equals(tNews.getUpLoaded())){//发布的
				this.updateNewsRedis(tNews.getNewsType(),tNews.getShopkeeper());
				this.updateHelperRedis(tNews.getUserId());
				this.updateGoodsRedis(tNews.getNewsType());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}
	


	/**
	 * 更新资讯小红点(管家课堂,管家故事)版本号（区分零售商）
	 * @param  newsType 资讯分类（逗号拼接） 1,2
	 * @param retailerId 
	 */
	private void updateNewsRedis(String newsType,String retailerId) {
		String key = GlobalConstants.NEWS_TYPE+"_guideApp_"+retailerId;
		String value = redisService.get(key);//在导购端资讯分类缓存只有一个小时
		if(Utility.isNotEmpty(value)){
			Map<String,Object> mapResult = new HashMap<String, Object>();
			try {
				mapResult = MAPPER.readValue(value, HashMap.class);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			if(Utility.isNotEmpty(mapResult) && !mapResult.isEmpty()){
				List<HashMap<String, Object>> typeList = (List<HashMap<String, Object>>)mapResult.get("typeList");
				if(Utility.isNotEmpty(typeList)){
					if(Utility.isNotEmpty(newsType)){
						String[] newsTypes = newsType.split(",");
						for(String s : newsTypes){
							for(HashMap<String, Object> typeVo : typeList){
								if("管家课堂".equals(typeVo.get("name")+"")&&s.equals(typeVo.get("code")+"")){
									redisService.incr(common.GlobalConstants.GUIDE_CLASS_CUSTOMER_VERSION+"_"+retailerId);//管家课堂 
								}else if("管家故事".equals(typeVo.get("name")+"")&&s.equals(typeVo.get("code")+"")){
									redisService.incr(common.GlobalConstants.GUIDE_STORY_CUSTOMER_VERSION+"_"+retailerId);//管家故事
								}
							}
						}
					}
				}
			}
			//删除资讯分类缓存
			redisService.del(key);
		}else{//从数据库去取资讯分类
			List<TNewsTypeEntity> typeList = systemService.findByProperty(TNewsTypeEntity.class, "retailerId", retailerId);
			if(Utility.isNotEmpty(typeList) && Utility.isNotEmpty(newsType)){
				String[] newsTypes = newsType.split(",");
				for(String s : newsTypes){
					for (TNewsTypeEntity tNewsTypeEntity : typeList) {
						System.out.println(tNewsTypeEntity.getName()+"_"+tNewsTypeEntity.getCode());
						if("管家课堂".equals(tNewsTypeEntity.getName()) && s.equals(tNewsTypeEntity.getCode())){
							redisService.incr(common.GlobalConstants.GUIDE_CLASS_CUSTOMER_VERSION+"_"+retailerId);//管家课堂 
						}else if("管家故事".equals(tNewsTypeEntity.getName()) && s.equals(tNewsTypeEntity.getCode())){
							redisService.incr(common.GlobalConstants.GUIDE_STORY_CUSTOMER_VERSION+"_"+retailerId);//管家故事
						}
					}
				}
			}
			
		}
	}

	/**
	 * 更新帮手资讯小红点版本号
	 */
	private void updateHelperRedis(String userId) {
		if(!Utility.isEmpty(userId)){
			redisService.incr(common.GlobalConstants.GUIDE_HELPER_VERSION);
		}
	}
	/**
	 * 更新导购端集合店小红点版本号(专家搭配资讯更新)
	 */
	private void updateGoodsRedis(String newsType) {
		if(!Utility.isEmpty(newsType)){
			String[] types = newsType.split(",");
			for(String type : types){
				if("2".equals(type)){//专家搭配
					List<Map<String, Object>> list = this.systemService.findForJdbc("select id from t_s_user where user_type = '02' and user_status = '1' and status = 'A'", null);
					if(!Utility.isEmpty(list)){
						for(Map<String, Object> map : list){
							redisService.incr(common.GlobalConstants.GUIDE_GOODS_VERSION+"_"+map.get("id"));
						}
					}
					break;
				}
			}
		}
	}
	
	/**
	 * 更新资讯信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TNewsEntity tNews, HttpServletRequest request) {
		// 话术的的行业信息
		String tradeId = oConvertUtils.getString(request.getParameter("tradeId"));
		AjaxJson j = new AjaxJson();
		message = "资讯信息更新成功";
		TNewsEntity t = tNewsService.get(TNewsEntity.class, tNews.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tNews, t);
			t.setNewsContext(this.clearFirstEmptyPtag(t.getNewsContext()));
			if(Utility.isEmpty(tNews.getNewsType())){
				t.setNewsType("");
			}
			if(Utility.isEmpty(t.getShopkeeper())){
				t.setShopkeeper("admin");
			}
			tNewsService.updateEntitie(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			
			//保存话题的行业类型
			if (StringUtil.isNotEmpty(tradeId)) {
				tNewsService.saveTradeTemplate(tNews.getId(), tradeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "资讯信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{
			if("Y".equals(t.getUpLoaded())){//发布的
				this.updateNewsRedis(t.getNewsType(),t.getShopkeeper());
				this.updateHelperRedis(tNews.getUserId());
				this.updateGoodsRedis(tNews.getNewsType());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}

	/**去掉字符串前面的空的p标签和只带<br/>的p标签
	 * @param goodsDesc
	 * @return
	 */
	private String clearFirstEmptyPtag(String goodsDesc) {
		HashMap<String,String> map = new HashMap<String,String>();
		 map.put("result", goodsDesc);
		 do {
			 map =UeditorContent.clearEmpty(map.get("result"));
			 
		} while ("N".equals(map.get("isLast")));
		return map.get("result");
	}
	
	//获取平台或者零售商的资讯分类
	public List<TNewsTypeEntity> getNewsTypeList(){
		CriteriaQuery cq = new CriteriaQuery(TNewsTypeEntity.class);
		cq.addOrder("orderNum",SortDirection.asc );
		String retailerId = ResourceUtil.getRetailerId();
		if(StringUtil.isNotEmpty(retailerId)){//零售商
			cq.eq("retailerId", retailerId);
			cq.eq("platformType", TNewsTypeEntity.PLATFORM_TYPE_2);
		}else{//平台
			cq.eq("platformType", TNewsTypeEntity.PLATFORM_TYPE_1);
		}
		cq.add();
		List<TNewsTypeEntity> newsTypeList = this.systemService.getListByCriteriaQuery(cq, false);
		return newsTypeList;
	}

	/**
	 * 资讯信息表新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TNewsEntity tNews, HttpServletRequest req) {
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode
		TSUser user = ResourceUtil.getSessionUserName();
		req.setAttribute("userType", user.getUserType());
		String newsType = req.getParameter("newsType");
		if(Utility.isNotEmpty(newsType)){
			req.setAttribute("newsType", newsType);
			return new ModelAndView("com/buss/news/tNewsOfType-add");
		}
		return new ModelAndView("com/buss/news/tNews-add");
	}
	/**
	 * 资讯信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TNewsEntity tNews, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tNews.getId())) {
			tNews = tNewsService.flushEntity(TNewsEntity.class, tNews.getId());
			req.setAttribute("tNewsPage", tNews);
		}
		String sql = "select trade_id tradeId,trade_name tradeName from t_trade_template where status='A' and template_id='"+tNews.getId()+"'";
		List<Map<String, Object>> resultList =  tNewsService.findForJdbc(sql);
		String tradeId="";
		String tradeName="";
		if(resultList.size() > 0){
			for (Map<String, Object> map : resultList) {
				tradeId +=map.get("tradeId")+",";
				tradeName +=map.get("tradeName")+",";
			}
			tradeId = tradeId.substring(0, tradeId.length()-1);
			tradeName = tradeName.substring(0, tradeName.length()-1);
		}
		req.setAttribute("tradeId", tradeId);
		req.setAttribute("tradeName", tradeName);
//		List<TNewsTypeEntity> newsTypeList = this.getNewsTypeList();
//		if(!Utility.isEmpty(newsTypeList)){
//			List<TNewsTypeEntity> newsTypeListN = new ArrayList<TNewsTypeEntity>();//非必选
//			List<TNewsTypeEntity> newsTypeListY = new ArrayList<TNewsTypeEntity>();//必选
//			for(TNewsTypeEntity entity : newsTypeList){
//				if(TNewsTypeEntity.IS_NEED_N.equals(entity.getIsNeed())){
//					newsTypeListN.add(entity);
//				}else if(TNewsTypeEntity.IS_NEED_Y.equals(entity.getIsNeed())){
//					newsTypeListY.add(entity);
//				}
//			}
//			req.setAttribute("newsTypeListN", newsTypeListN);
//		}
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		TSUser user = ResourceUtil.getSessionUserName();
		req.setAttribute("userType", user.getUserType());
		String newsType = req.getParameter("newsType");
		if(Utility.isNotEmpty(newsType)){
			req.setAttribute("newsType", newsType);
			return new ModelAndView("com/buss/news/tNewsOfType-update");
		}
		return new ModelAndView("com/buss/news/tNews-update");
	}
	
	/**
	 * 资讯信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goReview")
	public ModelAndView goReview(TNewsEntity tNews, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tNews.getId())) {
			tNews = tNewsService.flushEntity(TNewsEntity.class, tNews.getId());
			req.setAttribute("tNewsPage", tNews);
		}
//		List<TNewsTypeEntity> newsTypeList = this.getNewsTypeList();
//		if(!Utility.isEmpty(newsTypeList)){
//			List<TNewsTypeEntity> newsTypeListN = new ArrayList<TNewsTypeEntity>();//非必选
//			List<TNewsTypeEntity> newsTypeListY = new ArrayList<TNewsTypeEntity>();//必选
//			for(TNewsTypeEntity entity : newsTypeList){
//				if(TNewsTypeEntity.IS_NEED_N.equals(entity.getIsNeed())){
//					newsTypeListN.add(entity);
//				}else if(TNewsTypeEntity.IS_NEED_Y.equals(entity.getIsNeed())){
//					newsTypeListY.add(entity);
//				}
//			}
//			req.setAttribute("newsTypeListN", newsTypeListN);
//		}
		req.setAttribute("newsType", req.getParameter("newsType"));
		String newsType = req.getParameter("newsType");
		if(Utility.isNotEmpty(newsType)){
			req.setAttribute("newsType", newsType);
			return new ModelAndView("com/buss/news/tNewsOfType-review");
		}
		return new ModelAndView("com/buss/news/tNews-review");
	}
	
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tNewsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TNewsEntity tNews,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TNewsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tNews, request.getParameterMap());
		List<TNewsEntity> tNewss = this.tNewsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"资讯信息表");
		modelMap.put(NormalExcelConstants.CLASS,TNewsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("资讯信息表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tNewss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TNewsEntity tNews,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"资讯信息表");
    	modelMap.put(NormalExcelConstants.CLASS,TNewsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("资讯信息表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile file = entity.getValue();// 获取上传文件对象
				ImportParams params = new ImportParams();
				params.setTitleRows(2);
				params.setHeadRows(1);
				params.setNeedSave(true);
				try {
					List<TNewsEntity> listTNewsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TNewsEntity.class,params);
					for (TNewsEntity tNews : listTNewsEntitys) {
						tNewsService.save(tNews);
					}
					j.setMsg("文件导入成功！");
				} catch (Exception e) {
					e.printStackTrace();
					j.setMsg("文件导入失败！");
					logger.error(ExceptionUtil.getExceptionMessage(e));
				}finally{
					try {
						file.getInputStream().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			j.setMsg("文件导入失败！");
		}
		return j;
	}
	
	/**
	 * 查看图片
	 * @param request
	 * @param code
	 * @param path
	 * @param response
	 */
	@RequestMapping(params = "showPic")
	public void showPic(HttpServletRequest request,String code, String path,HttpServletResponse response){
		String defaultPath="default.jpg";
		String defaultCode="default/images/";
		//无图片情况
		if(path==null){
			path=defaultPath;
			code=defaultCode;
		}else{
			//临时图片
			if(code==null){
				code="temp/";
			}else{
				code+="/images/";
			}
		}
		FileInputStream fis = null;
		response.setContentType("image/" + FileUtils.getExtend(path));
		try {
			OutputStream out = response.getOutputStream();
			File file = new File(getUploadBasePath(request),code+path);
			if(!file.exists()||file.isDirectory()){
				file=new File(getUploadBasePath(request),defaultCode+defaultPath);
			}
			fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	
	
	//获取上传根路径
	private String getUploadBasePath(HttpServletRequest request){
		String path= this.getClass().getResource("/").getPath()+"online/template";
		return path;
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
			ftpUtil.uploadFileToFtpServer(GlobalConstants.CATEGORY_NEWS, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
							 +ResourceUtil.getConfigByName("imgRootPath")+"/"
							 +GlobalConstants.CATEGORY_NEWS+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}	
	
	/**
	 * 复制资讯分类
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doCopy")
	@ResponseBody
	public AjaxJson doCopy(TNewsEntity tNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "话题复制成功";
		TNewsEntity t = tNewsService.get(TNewsEntity.class, tNews.getId());
//		Set<String> newsTypeSet = new HashSet<String>();
		try {
			tNewsService.doCopy(t);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "话题复制失败";
			throw new BusinessException(e.getMessage());
		}
		try{
			//新增资讯中没有的分类
//			this.doAddNewsTypes(newsTypeSet);
		}catch(Exception e){
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量复制资讯分类
	 * @return
	 */
	 @RequestMapping(params = "doBatchCopy")
	@ResponseBody
	public AjaxJson doBatchCopy(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "话题复制成功";
//		Set<String> newsTypeSet = new HashSet<String>();
		try{
			for(String id:ids.split(",")){
				TNewsEntity tNews = systemService.get(TNewsEntity.class,id);
				tNewsService.doCopy(tNews);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//				String newsTypes = tNews.getNewsType();
//				if(StringUtil.isNotEmpty(newsTypes)){
//					String[] arr = newsTypes.split(",");
//					if(arr.length>0){
//						for(String code : arr){
//							newsTypeSet.add(code);
//						}
//					}
//				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "话题复制失败";
			throw new BusinessException(e.getMessage());
		}
		try{
			//新增资讯中没有的分类
//			this.doAddNewsTypes(newsTypeSet);
		}catch(Exception e){
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}

	/**批量保存复制的资讯中的资讯分类并清除资讯分类缓存
	 * @param newsTypeSet
	 */
	private void doAddNewsTypes(Set<String> newsTypeSet) {
		tNewsTypeService.batchSaveByCopyNews(newsTypeSet);
	}
	
}
