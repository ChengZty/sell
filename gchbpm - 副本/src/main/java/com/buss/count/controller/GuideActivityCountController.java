package com.buss.count.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.buss.count.service.GuideCountServiceI;
import com.buss.count.vo.GuideActivityCountVo;
import com.buss.count.vo.GuideActivityTotalCountVo;
import com.buss.count.vo.GuideViewCountVo;
import com.buss.store.entity.TStoreEntity;

/**   
 * @Title: Controller
 * @Description: 导购统计
 * @author onlineGenerator
 * @date 2016-04-09 12:14:42
 * @version V1.0   
 *
 */
/**
 * @author lenovo
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/guideActivityCountController")
public class GuideActivityCountController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GuideActivityCountController.class);

	@Autowired
	private GuideCountServiceI guideCountService;

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
	 * 活动统计列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listActivityCount")
	public ModelAndView listActivityCount(HttpServletRequest request) {
//		 guideCountService.getInitData(request);
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailer_Id", retailerId);
		guideCountService.initFirstSearchTimeRange(request);
		return new ModelAndView("com/buss/count/tActivityCountList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "tActivityCountDatagrid")
	public void tActivityCountDatagrid(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		// 查询条件组装器
		try {
			Map<String,String> sqlMap = this.getQuerySql(request,dataGrid);
			String sql = sqlMap.get("sql");
			String countSql = sqlMap.get("countSql");
			List<GuideViewCountVo> resultList = new ArrayList<GuideViewCountVo>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),GuideViewCountVo.class);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**获取查询的sql和countSql
	 * @param request
	 * @param dataGrid
	 * @return
	 */
	private Map<String,String> getQuerySql(HttpServletRequest request,DataGrid dataGrid) {
		Map<String,String> map = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String title = request.getParameter("title");//资讯标题
		String startTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		String storeId = request.getParameter("storeId");//店铺id
		String guideName = request.getParameter("guideName");
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(new Date());
			endTime = DateUtils.getDataString(DateUtils.date_sdf);
		}
		StringBuffer sql = new StringBuffer("select * from (select c.activity_id activityId,c.user_id userId,c.guide_name guideName,c.title,sum(c.click_num) totalClickNum,")
		.append("sum(c.still_time) totalStillTime,MAX(c.click_time) maxClickTime ,	MIN(c.click_time) minClickTime,c.store_id storeId")
		.append(" from t_activity_count c where c.retailer_id = '").append(retailerId).append("'");
		StringBuffer countSql = new StringBuffer("select count(1) from (select c.activity_id activityId,c.user_id userId")
		.append(" from t_activity_count c where c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(title)){
			sql.append(" and c.title like '%").append(title).append("%'");
			countSql.append(" and c.title like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(startTime)){
			sql.append(" and c.click_time >= '").append(startTime).append(" 00:00:00'");
			countSql.append(" and c.click_time >= '").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sql.append(" and c.click_time <= '").append(endTime).append(" 23:59:59'");
			countSql.append(" and c.click_time <= '").append(endTime).append(" 23:59:59'");
		}
		if(Utility.isNotEmpty(storeId)){
			sql.append(" and c.store_id = '").append(storeId).append("'");
			countSql.append(" and c.store_id = '").append(storeId).append("'");
		}
		if(Utility.isNotEmpty(guideName)){
			sql.append(" and c.guide_name like '%").append(guideName).append("%'");
			countSql.append(" and c.guide_name like '%").append(guideName).append("%'");
		}
		
		sql.append(" GROUP BY c.activity_id,c.user_id ) t");
		countSql.append(" GROUP BY c.activity_id,c.user_id ) t");
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
			sql.append(" ORDER BY t.totalClickNum desc");
		}else{
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		map.put("sql", sql.toString());
		map.put("countSql", countSql.toString());
		return map;
	}

	/**
	 * easyui AJAX请求数据
	 * 活动浏览统计汇总
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "tActivityTotalDatagrid")
	public void tActivityTotalDatagrid(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		// 查询条件组装器
		try {
			Map<String,String> sqlMap = this.getActSqlMap(request,dataGrid);
			String countSql = sqlMap.get("sqlCount");
			String sql = sqlMap.get("sql");
			int total = this.systemService.getCountForJdbc(countSql).intValue();
			List<GuideActivityTotalCountVo> resultList = null;
			if (total>0) {
				resultList = systemService.findObjForJdbc(sql, dataGrid.getPage(),dataGrid.getRows(), GuideActivityTotalCountVo.class);
			}else{
				resultList = new ArrayList<GuideActivityTotalCountVo>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**获取活动浏览统计sql*/
	private Map<String, String> getActSqlMap(HttpServletRequest request,DataGrid dataGrid) {
		Map<String,String> sqlMap = new HashMap<String, String>();
		String title = request.getParameter("title");// 活动标题
		String retailerId = ResourceUtil.getRetailerId();
		String startTime = request.getParameter("searchTime_begin");// 开始时间
		String endTime = request.getParameter("searchTime_end");// 结束时间
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(Utility.getCurrentTimestamp());
			endTime = DateUtils.getDate("yyyy-MM-dd");
		}
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		sql.append("SELECT a.id,a.title,(SELECT count(1) from t_share_click_count c where c.retailer_id = a.retailer_id and c.activity_id=a.id and c.share_type='3' ");
		if(Utility.isNotEmpty(startTime)){
			sql.append(" AND c.click_time >='").append(startTime).append(" 00:00:00' ");
		}
		if(Utility.isNotEmpty(endTime)){
			sql.append(" AND c.click_time <='").append(endTime).append(" 23:59:59' ");
		}
		sql.append(") totalClickNum from t_goods_act a ")
			.append("where a.status = 'A' AND a.retailer_id='").append(retailerId).append("' ")
			.append("AND a.audit_status = '2' ");
		sqlCount.append("SELECT count(1) from t_goods_act a ")
			.append("where a.status = 'A' AND a.retailer_id='").append(retailerId).append("' ")
			.append("AND a.audit_status = '2' ");
		if(Utility.isNotEmpty(title)){
			sql.append(" AND a.title like '%").append(title).append("%' ");
			sqlCount.append(" AND a.title like '%").append(title).append("%' ");
		}
		sql.append("order by a.begin_time desc");
		sqlMap.put("sql", sql.toString());
		sqlMap.put("sqlCount", sqlCount.toString());
		return sqlMap;
	}

	/**
	 * 活动点击详情列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "viewClickInfo")
	public ModelAndView viewClickInfo(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		String actId = request.getParameter("actId");
		String searchTime_begin = request.getParameter("searchTime_begin");
		String searchTime_end = request.getParameter("searchTime_end");
		request.setAttribute("retailerId", retailerId);
		request.setAttribute("actId", actId);
		request.setAttribute("searchTime_begin", searchTime_begin);
		request.setAttribute("searchTime_end", searchTime_end);
		return new ModelAndView("com/buss/count/viewActivityClickInfo");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "tActivityClickDatagrid")
	public void tActivityClickDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//活动id
		String actId = request.getParameter("actId");
		String guideName = request.getParameter("guideName");
		String searchTime_begin = request.getParameter("searchTime_begin");
		String searchTime_end = request.getParameter("searchTime_end");
		String sql = "select guide_id id,guide_name guideName,count(1) clickNum from t_share_click_count where activity_id = '"+actId+"' and share_type='3' ";
		String sqlCount = "select count(distinct guide_id)  from t_share_click_count where activity_id = '"+actId+"' and share_type='3' ";
		if(Utility.isNotEmpty(guideName)){
			sql += "and guide_name like '%"+guideName+"%' ";
			sqlCount += "and guide_name like '%"+guideName+"%' ";
		}
		if(Utility.isNotEmpty(searchTime_begin)){
			sql += "and click_time >= '"+searchTime_begin+" 00:00:00' ";
			sqlCount += "and click_time >= '"+searchTime_begin+" 00:00:00' ";
		}
		if(Utility.isNotEmpty(searchTime_end)){
			sql += "and click_time <= '"+searchTime_end+" 23:59:59' ";
			sqlCount += "and click_time <= '"+searchTime_end+" 23:59:59' ";
		}
		sql += " group by guide_id";
		try {
			int total = this.systemService.getCountForJdbc(sqlCount.toString()).intValue();
			List<Map<String,Object>> resultsList = new ArrayList<Map<String,Object>>();
			if(total > 0){
				resultsList = systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
			}
			dataGrid.setResults(resultsList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 导出excel(活动)
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportActivityXls")
	public String exportActivityXls(HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		String sql = this.getQuerySql(request,dataGrid).get("sql");
		List<GuideActivityCountVo> resultList = systemService.findObjForJdbc(sql, dataGrid.getPage(), dataGrid.getRows(),GuideActivityCountVo.class);
		if(Utility.isNotEmpty(resultList)){
			List<TStoreEntity> storeList = systemService.findByProperty(TStoreEntity.class, "retailerId", ResourceUtil.getRetailerId());
			for(GuideActivityCountVo vo : resultList){
				for(TStoreEntity s : storeList){
					if(s.getId().equals(vo.getStoreId())){
						vo.setStoreName(s.getName());
						break;
					}
				}
			}
		}
		modelMap.put(NormalExcelConstants.FILE_NAME, "活动导购浏览统计");
		modelMap.put(NormalExcelConstants.CLASS, GuideActivityCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("活动导购浏览统计",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),	"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, resultList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel(活动)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportActivityTotalXls")
	public String exportActivityTotalXls(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		String sql = guideCountService.getActivityTotalSql(request,dataGrid);
		List<GuideActivityTotalCountVo> resultList = systemService
				.findObjForJdbc(sql, dataGrid.getPage(), dataGrid.getRows(),
						GuideActivityTotalCountVo.class);
		modelMap.put(NormalExcelConstants.FILE_NAME, "活动浏览统计汇总");
		modelMap.put(NormalExcelConstants.CLASS,
				GuideActivityTotalCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("活动浏览统计汇总",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, resultList);
		logger.info("活动导购浏览统计");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 活动统计列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listActivityTotalCount")
	public ModelAndView listActivityTotalCount(HttpServletRequest request) {
		guideCountService.initFirstSearchTimeRange(request);
		return new ModelAndView("com/buss/count/tActivityTotalCount");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	// @RequestMapping(params = "tActivityTotalDatagrid")
	// public void tActivityTotalDatagrid(HttpServletRequest request,
	// HttpServletResponse response, DataGrid dataGrid) {
	// //查询条件组装器
	// try{
	// String title = request.getParameter("title");//活动标题
	// String retailerId = ResourceUtil.getRetailerId();
	// String startTime = request.getParameter("searchTime_begin");
	// String endTime = request.getParameter("searchTime_end");
	// //封装sql语句
	// String sql =
	// guideCountService.getActivityTotalSql(retailerId,startTime,endTime,title);
	//
	// List<Map<String, Object>> resultList = new
	// ArrayList<Map<String,Object>>();
	// //查询数据的条数
	// int total = systemService.findForJdbc(sql).size();
	// if(total>0){
	// //根据条数分页查询数据
	// resultList =
	// systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
	// }
	// dataGrid.setResults(resultList);
	// dataGrid.setTotal(total);
	// }catch (Exception e) {
	// throw new BusinessException(e.getMessage());
	// }
	// TagUtil.datagrid(response, dataGrid);
	// }

	/**
	 * 导出excel(活动)
	 * 
	 * @param request
	 * @param response
	 */
	// @RequestMapping(params = "exportActivityTotalXls")
	// public String exportActivityTotalXls(HttpServletRequest
	// request,HttpServletResponse response , DataGrid dataGrid,ModelMap
	// modelMap) {
	// String title = request.getParameter("title");//活动标题
	// String retailerId = ResourceUtil.getRetailerId();
	// String startTime = request.getParameter("searchTime_begin");
	// String endTime = request.getParameter("searchTime_end");
	// //封装sql语句
	// String sql =
	// guideCountService.getActivityTotalSql(retailerId,startTime,endTime,title);
	//
	// List<GuideActivityTotalVo> resultList =
	// systemService.findObjForJdbc(sql,GuideActivityTotalVo.class);
	// modelMap.put(NormalExcelConstants.FILE_NAME,"活动浏览统计汇总");
	// modelMap.put(NormalExcelConstants.CLASS,GuideActivityTotalVo.class);
	// modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("活动浏览统计汇总",
	// "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
	// "导出信息"));
	// modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
	// logger.info("活动浏览统计汇总");
	// return NormalExcelConstants.JEECG_EXCEL_VIEW;
	// }
}
