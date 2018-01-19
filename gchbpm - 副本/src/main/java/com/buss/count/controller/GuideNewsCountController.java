package com.buss.count.controller;
import java.util.ArrayList;
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
import com.buss.count.vo.GuideNewsTotalCountVo;
import com.buss.count.vo.GuideViewCountVo;
import com.buss.news.service.TNewsTypeServiceI;
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
@RequestMapping("/guideNewsCountController")
public class GuideNewsCountController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GuideNewsCountController.class);

	@Autowired
	private GuideCountServiceI guideCountService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private TNewsTypeServiceI tNewsTypeService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 资讯统计列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "listNewsCount")
	public ModelAndView listNewsCount(HttpServletRequest request) {
//		guideCountService.getInitData(request);
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailer_Id", retailerId);
		guideCountService.initFirstSearchTimeRange(request);
		return new ModelAndView("com/buss/count/tNewsCountList");
	}
	
	/**
	 * 资讯汇总统计列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listNewsTotalCount")
	public ModelAndView listNewsTotalCount(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("rId", retailerId);
		guideCountService.initFirstSearchTimeRange(request);
		return new ModelAndView("com/buss/count/tNewsTotalCountList");
	}
	
	/**
	 * easyui 资讯浏览统计
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "tNewsCountDatagrid")
	public void tNewsCountDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
			Map<String,String> sqlMap = this.getQuerySql(request,dataGrid);
			String sql = sqlMap.get("sql");
			String countSql = sqlMap.get("countSql");
			List<GuideViewCountVo> resultList = new ArrayList<GuideViewCountVo>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),GuideViewCountVo.class);
			}
//			String sql = guideCountService.getNewsSql(retailerId,storeId,personId,title,startTime,endTime);
//			if(!Utility.isEmpty(resultList)){
//				String countSql = guideCountService.getNewsCountSql(retailerId,storeId,personId,title,startTime,endTime);
//				total = this.systemService.getCountForJdbc(countSql).intValue();
//			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
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
//		String personId = request.getParameter("personId");
		String guideName = request.getParameter("guideName");
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(new Date());
			endTime = DateUtils.getDataString(DateUtils.date_sdf);
		}
		StringBuffer sql = new StringBuffer("select * from (select c.news_id newsId,c.user_id userId,c.guide_name guideName,c.title,sum(c.click_num) totalClickNum,")
		.append("sum(c.still_time) totalStillTime,MAX(c.click_time) maxClickTime ,	MIN(c.click_time) minClickTime,c.store_id storeId")
		.append(" from t_news_count c where c.retailer_id = '").append(retailerId).append("'");
		StringBuffer countSql = new StringBuffer("select count(1) from (select c.news_id newsId,c.user_id userId")
		.append(" from t_news_count c where c.retailer_id = '").append(retailerId).append("'");
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
		
		sql.append(" GROUP BY c.news_id,c.user_id ) t");
		countSql.append(" GROUP BY c.news_id,c.user_id ) t");
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
	 * easyui AJAX请求数据   资讯汇总
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "tNewsTotalCountDatagrid")
	public void tNewsTotalCountDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
			String sql = guideCountService.getNewsTotalSql(request,dataGrid);
			List<GuideNewsTotalCountVo> resultList =  systemService.findObjForJdbc(sql,dataGrid.getPage(),dataGrid.getRows(),GuideNewsTotalCountVo.class);
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				String countSql = guideCountService.getNewsTotalCountSql(request);
				total = this.systemService.getCountForJdbc(countSql).intValue();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 导出excel(资讯)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportNewsXls")
	public String exportNewsXls(HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
//		String storeId = request.getParameter("storeId");//店铺id
//		String title = request.getParameter("title");//资讯标题
//		String personId = request.getParameter("personId");
//		String retailerId = ResourceUtil.getRetailerId();
//		String startTime = request.getParameter("searchTime_begin");//开始时间
//		String endTime = request.getParameter("searchTime_end");//结束时间
//		
//		String sql = guideCountService.getNewsSql(retailerId,storeId,personId,title,startTime,endTime);
		String sql = this.getQuerySql(request,dataGrid).get("sql");
		List<GuideViewCountVo> resultList =  systemService.findObjForJdbc(sql,dataGrid.getPage(),dataGrid.getRows(),GuideViewCountVo.class);
		if(Utility.isNotEmpty(resultList)){
			List<TStoreEntity> storeList = systemService.findByProperty(TStoreEntity.class, "retailerId", ResourceUtil.getRetailerId());
			for(GuideViewCountVo vo : resultList){
				for(TStoreEntity s : storeList){
					if(s.getId().equals(vo.getStoreId())){
						vo.setStoreName(s.getName());
						break;
					}
				}
			}
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"资讯导购浏览统计");
		modelMap.put(NormalExcelConstants.CLASS,GuideViewCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("资讯导购浏览统计", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		logger.info("资讯导购浏览统计");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 导出excel(资讯汇总)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportNewsTotalXls")
	public String exportNewsTotalXls(HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String retailerId = ResourceUtil.getRetailerId();
		String sql = guideCountService.getNewsTotalSql(request,dataGrid);
		List<GuideNewsTotalCountVo> resultList =  systemService.findObjForJdbc(sql,dataGrid.getPage(),dataGrid.getRows(),GuideNewsTotalCountVo.class);
		//填充资讯分类名称
		tNewsTypeService.fillNewsTypeNames(resultList, retailerId);
		modelMap.put(NormalExcelConstants.FILE_NAME,"资讯浏览统计汇总");
		modelMap.put(NormalExcelConstants.CLASS,GuideNewsTotalCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("资讯浏览统计汇总", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		logger.info("资讯浏览统计汇总");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
	
	
}
