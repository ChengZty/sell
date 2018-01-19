package com.buss.job.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.buss.count.service.GuideCountServiceI;
import com.buss.job.entity.TJobModelEntity;
import com.buss.job.vo.TGuideGoldCountVo;
import com.buss.job.vo.TJobDayDetailCountVo;
import com.buss.job.vo.TJobDetailCountVo;
import com.buss.store.service.TStoreServiceI;



/**   
 * @Title: Controller
 * @Description: 任务模板维护
 * @author onlineGenerator
 * @date 2016-11-05 11:54:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tJobDetailCountController")
public class TJobDetailCountController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TJobDetailCountController.class);

	@Autowired
	private TStoreServiceI tStoreService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private GuideCountServiceI guideCountService;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 任务维护列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/job/tJobDetailMainListTab");
	}
	
	
	
	/**
	 * 任务日报表
	 * @return
	 */
	@RequestMapping(params = "dayList")
	public ModelAndView dayList(HttpServletRequest request) {
		String storeStr = "";
		List<CommonVo> storeList = tStoreService.getStoreList();
		for (CommonVo commonVo : storeList) {
			storeStr = storeStr + commonVo.getName() + "_" + commonVo.getId() + ",";
		}
		if(Utility.isNotEmpty(storeStr)){
			storeStr = (String) storeStr.subSequence(0, storeStr.length()-1);
		}
		request.setAttribute("searchTime", DateUtils.getDataString(DateUtils.date_sdf));
		request.setAttribute("stores", storeStr);
		return new ModelAndView("com/buss/job/tJobDetailDayCountList");
	}
	
	
	
	/**
	 * 任务汇总统计
	 * @return
	 */
	@RequestMapping(params = "rangeList")
	public ModelAndView rangeList(HttpServletRequest request) {
		String storeStr = "";
		List<CommonVo> storeList = tStoreService.getStoreList();
		for (CommonVo commonVo : storeList) {
			storeStr = storeStr + commonVo.getName() + "_" + commonVo.getId() + ",";
		}
		if(Utility.isNotEmpty(storeStr)){
			storeStr = (String) storeStr.subSequence(0, storeStr.length()-1);
		}
		request.setAttribute("stores", storeStr);
		guideCountService.initFirstSearchTimeRange(request);
		return new ModelAndView("com/buss/job/tJobDetailCountList");
	}
	
	/**
	 * 任务汇总统计
	 * @return
	 */
	@RequestMapping(params = "rangeGoldList")
	public ModelAndView rangeGoldList(HttpServletRequest request) {
		String storeStr = "";
		List<CommonVo> storeList = tStoreService.getStoreList();
		for (CommonVo commonVo : storeList) {
			storeStr = storeStr + commonVo.getName() + "_" + commonVo.getId() + ",";
		}
		if(Utility.isNotEmpty(storeStr)){
			storeStr = (String) storeStr.subSequence(0, storeStr.length()-1);
		}
		request.setAttribute("stores", storeStr);
		guideCountService.initFirstSearchTimeRange(request);
		return new ModelAndView("com/buss/job/tGuideGoldCountList");
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
	public void datagrid(TJobModelEntity tJobModel,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//获取查询sql语句
		Map<String,String> sqlMap = getSqlMap(request,dataGrid);
		
		int total = 0;
		try {
			total = this.systemService.getCountForJdbc(sqlMap.get("countSql").toString()).intValue();
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
			if(total > 0){
				resultList = this.systemService.findForJdbcParam(sqlMap.get("sql").toString(), dataGrid.getPage(),dataGrid.getRows(), null);
			}
			dataGrid.setResults(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}

		dataGrid.setTotal(total);
		
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datagridGold")
	public void datagridGold(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		guideCountService.getGuideGoldCountSql(request, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}

	private Map<String,String> getSqlMap(HttpServletRequest request,DataGrid dataGrid){
		Map<String,String> sqlMap = new HashMap<String, String>();
		
		String startTime = request.getParameter("jobDate_begin");
		String endTime = request.getParameter("jobDate_end");
		String jobTitle = request.getParameter("jobTitle");
		String storeId = request.getParameter("storeId");
		String modelName = request.getParameter("modelName");
		String guideName = request.getParameter("guideName");
		String mobilePhone = request.getParameter("mobilePhone");
		String paceType = request.getParameter("paceType");
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(new Date());
			endTime = DateUtils.getDataString(DateUtils.date_sdf);
		}
 		String retailerId = ResourceUtil.getRetailerId();
		
 		StringBuffer sqlbf = new StringBuffer("select tjdc.id id,tjdc.retailer_id retailerId,tjdc.store_id storeId, ts.name storeName, tjdc.guide_id guideId, ");
 		sqlbf.append("CONCAT(DATE_FORMAT(tjdc.start_time, '%Y-%m-%d' ),'~',DATE_FORMAT(tjdc.end_time, '%Y-%m-%d' )) jobDate, CONCAT(tjdc.pace,'/',tjdc.job_num) as pace, tjdc.job_num jobNum,tjdc.pace_type paceType, tjdc.gold_total goldTotal, ")
 		.append("case tjdc.finish when '1' then tjdc.gold_sub * 1 else 0 end goldSub, tjdc.gold_person goldPerson,tjp.job_title jobTitle,tjm.name modelName, ")
 		.append("tjp.job_description jobDescription,p.phone_no mobilePhone,p.real_Name guideName ")
 		.append("from t_job_detail_count tjdc left join t_job_param tjp on tjdc.param_id=tjp.id left join t_person p ")
 		.append("on tjdc.guide_id = p.user_id  and tjdc.retailer_id=p.to_retailer_id  ")
 		.append("JOIN t_s_user tsu ON tsu.id = p.user_id and tsu.user_status  in ('"+TSUser.USER_STATUS_ACTIVE+"','"+TSUser.USER_STATUS_FIXED+"') ")
 		.append("left join t_job_model tjm on tjm.id = tjp.model_id left join t_store ts on ts.id = tjdc.store_id ")
 		.append("where tjdc.status='A' and tjdc.retailer_id='" + retailerId +"' ");
 		
 		StringBuffer countSqlbf = new StringBuffer("select count(*) from t_job_detail_count tjdc left join t_job_param tjp on tjdc.param_id=tjp.id left join t_person p ")
 		.append("on tjdc.guide_id = p.user_id  and tjdc.retailer_id=p.to_retailer_id  ")
 		.append("JOIN t_s_user tsu ON tsu.id = p.user_id and tsu.user_status  in ('"+TSUser.USER_STATUS_ACTIVE+"','"+TSUser.USER_STATUS_FIXED+"') ")
 		.append("left join t_job_model tjm on tjm.id = tjp.model_id left join t_store ts on ts.id = tjdc.store_id ")
 		.append("where tjdc.status='A' and tjdc.retailer_id='" + retailerId +"' ");
		
		
		if(Utility.isNotEmpty(startTime)){
			sqlbf.append("and tjdc.start_time >='"+startTime+" 00:00:00' ");
			countSqlbf.append("and tjdc.start_time >='"+startTime+" 00:00:00' ");
		}
		if(Utility.isNotEmpty(endTime)){
			sqlbf.append("and tjdc.start_time <='"+endTime+" 23:59:59' ");
			countSqlbf.append("and tjdc.start_time <='"+endTime+" 23:59:59' ");
		}
		if(Utility.isNotEmpty(jobTitle)){
			sqlbf.append("and tjp.job_title like '%"+jobTitle+"%' ");
			countSqlbf.append("and tjp.job_title like '%"+jobTitle+"%' ");
		}
		if(Utility.isNotEmpty(storeId)){
			sqlbf.append("and tjdc.store_id ='"+storeId+"' ");
			countSqlbf.append("and tjdc.store_id ='"+storeId+"' ");
		}
		if(Utility.isNotEmpty(modelName)){
			sqlbf.append("and tjm.name like '%"+modelName+"%' ");
			countSqlbf.append("and tjm.name like '%"+modelName+"%' ");
		}
		if(Utility.isNotEmpty(guideName)){
			sqlbf.append("and p.real_Name like '%"+guideName+"%' ");
			countSqlbf.append("and p.real_Name like '%"+guideName+"%' ");
		}
		if(Utility.isNotEmpty(mobilePhone)){
			sqlbf.append("and p.phone_no like '%"+mobilePhone+"%' ");
			countSqlbf.append("and p.phone_no like '%"+mobilePhone+"%' ");
		}
		if(Utility.isNotEmpty(paceType)){
			sqlbf.append("and tjdc.pace_type ='"+paceType+"'");
			countSqlbf.append("and tjdc.pace_type ='"+paceType+"'");
		}
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
			sqlbf.append(" ORDER BY jobDate desc");
		}else{
			sqlbf.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		sqlMap.put("sql", sqlbf.toString());
		sqlMap.put("countSql", countSqlbf.toString());
		return sqlMap;
	}
	
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid,ModelMap modelMap) {

		String dateStr = DateUtils.date2Str(DateUtils.date_sdf);
		String sql = getSqlMap(request,dataGrid).get("sql");
		
		List<TJobDetailCountVo> list =systemService.findObjForJdbc(sql, TJobDetailCountVo.class);
		modelMap.put(NormalExcelConstants.FILE_NAME,"任务统计");
		modelMap.put(NormalExcelConstants.CLASS,TJobDetailCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("导购任务统计", "导出时间:" + dateStr+ "                "
				+ "导出人:"+ResourceUtil.getSessionUserName().getRealName()+ "    ", "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
	
	/**
	 * easyui AJAX请求数据
	 * 导购任务日报表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "dayDatagrid")
	public void dayDatagrid(TJobModelEntity tJobModel,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//获取查询sql语句
		/*Map<String,String> sqlMap = getDaySqlMap(request,dataGrid);
		
		int total = 0;
		try {
			total = this.systemService.getCountForJdbc(sqlMap.get("countSql").toString()).intValue();
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
			if(total > 0){
				resultList = this.systemService.findForJdbcParam(sqlMap.get("sql").toString(), dataGrid.getPage(),dataGrid.getRows(), null);
			}
			dataGrid.setResults(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}*/
		Map<String,Object> resultMap = getDaySqlMap2(request,dataGrid,"list");

		dataGrid.setResults((ArrayList)resultMap.get("resultList"));
		dataGrid.setTotal(Integer.parseInt(resultMap.get("total").toString()));
		
		TagUtil.datagrid(response, dataGrid);
	}
	//每日任务统计 - 通过连接店铺任务组连接表确定店铺的任务组
	private Map<String,Object> getDayJobSqlMap(HttpServletRequest request,DataGrid dataGrid,String type){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String searchTime = request.getParameter("searchTime");
		if(Utility.isEmpty(searchTime)){
			searchTime=DateUtils.getDataString(DateUtils.date_sdf);
		}
		String jobTitle = request.getParameter("jobTitle");
		String storeId = request.getParameter("storeId");
		String modelName = request.getParameter("modelName");
		String guideName = request.getParameter("guideName");
		String paceType = request.getParameter("paceType");
 		String retailerId = ResourceUtil.getRetailerId();
 		
 		//查询语句拼接
 		StringBuffer sql = new StringBuffer("select tsu.id id,concat(DATE_FORMAT(tsj.start_time,'%Y-%m-%d'),'~',DATE_FORMAT(tsj.end_time,'%Y-%m-%d')) jobDate, ");
 		sql.append(" '").append(searchTime).append("' AS searchTime, ");
 		sql.append(" tsu.realname guideName,tsu.username userName,tsu.mobilePhone mobilePhone,ts.id storeId,ts.name storeName,tsj.model_name modelName, ");
 		sql.append(" tjp.job_title jobTitle,tjp.job_num jobNum,tjdc.pace pace,tjdc.pace/tjp.job_num myPercent,tjdc.pace_type paceType ");
 		sql.append(" from t_store ts JOIN t_store_jobmodel tsj on tsj.store_id=ts.id ");
 		sql.append(" JOIN t_job_param tjp on tjp.model_id=tsj.model_id ");
 		sql.append(" JOIN t_person tp on tp.store_id=ts.id ");
 		sql.append(" JOIN t_s_user tsu  ON tsu.id = tp.user_id ");
 		sql.append(" left JOIN t_job_detail_count tjdc ON tjdc.param_id = tjp.id and tp.user_id=tjdc.guide_id");
 		sql.append(" where ts.status='A' and tp.status='A' and tsu.status='A' ");
 		
 		StringBuffer sqlCount = new StringBuffer(" select count(1) count from t_store ts JOIN t_store_jobmodel tsj on tsj.store_id=ts.id ");
 		sqlCount.append(" JOIN t_job_param tjp on tjp.model_id=tsj.model_id ");
 		sqlCount.append(" JOIN t_person tp on tp.store_id=ts.id ");
 		sqlCount.append(" JOIN t_s_user tsu  ON tsu.id = tp.user_id ");
 		sqlCount.append(" left JOIN t_job_detail_count tjdc ON tjdc.param_id = tjp.id and tp.user_id=tjdc.guide_id");
 		sqlCount.append(" where ts.status='A' and tp.status='A' and tsu.status='A' ");
 		
 		if(Utility.isNotEmpty(retailerId)){
 			sql.append(" and ts.retailer_id='"+retailerId+"' ");
 			sqlCount.append(" and ts.retailer_id='"+retailerId+"' ");
		}
 		if(Utility.isNotEmpty(searchTime)){
 			sql.append(" and '"+searchTime+" 12:00:00' >= tsj.start_time ");
 			sql.append(" and '"+searchTime+" 12:00:00' <= tsj.end_time ");
 			sql.append(" and DATE_FORMAT(tjdc.update_date,'%Y-%m-%d') = '"+searchTime+"' ");
 			sqlCount.append(" and '"+searchTime+" 12:00:00' >= tsj.start_time ");
 			sqlCount.append(" and '"+searchTime+" 12:00:00' <= tsj.end_time ");
 			sqlCount.append(" and DATE_FORMAT(tjdc.update_date,'%Y-%m-%d') = '"+searchTime+"' ");
		}
 		if(Utility.isNotEmpty(jobTitle)){
 			sql.append(" and tjp.job_title like '%"+jobTitle+"%' ");
 			sqlCount.append(" and tjp.job_title like '%"+jobTitle+"%' ");
		}
		if(Utility.isNotEmpty(storeId)){
			sql.append(" and ts.id  ='"+storeId+"' ");
			sqlCount.append(" and ts.id  ='"+storeId+"' ");
		}
		if(Utility.isNotEmpty(modelName)){
			sql.append(" and tsj.model_name like '%"+modelName+"%' ");
			sqlCount.append(" and tsj.model_name like '%"+modelName+"%' ");
		}
		if(Utility.isNotEmpty(guideName)){
			sql.append(" and tsu.realname like '%"+guideName+"%' ");
			sqlCount.append(" and tsu.realname like '%"+guideName+"%' ");
		}
		if(Utility.isNotEmpty(paceType)){
			sql.append(" and tjdc.pace_type = '"+paceType+"' ");
			sqlCount.append(" and tjdc.pace_type = '"+paceType+"' ");
		}
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
			sql.append(" ORDER BY tsu.id desc");
		}else{
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		int total = this.systemService.getCountForJdbc(sqlCount.toString()).intValue();
		if(total > 0){
			if("all".equals(type)){
				resultList = this.systemService.findForJdbc(sql.toString(), null);
			}else if("list".equals(type)){
				resultList = this.systemService.findForJdbcParam(sql.toString(), dataGrid.getPage(),dataGrid.getRows(), null);
			}
		}
		
		resultMap.put("resultList", resultList);
		resultMap.put("total", total);
		return resultMap;
	}
	
	//每日任务统计 - 
	private Map<String,Object> getDaySqlMap2(HttpServletRequest request,DataGrid dataGrid,String type){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		String searchTime = request.getParameter("searchTime");
		if(Utility.isEmpty(searchTime)){
			searchTime=DateUtils.getDataString(DateUtils.date_sdf);
		}
		String jobTitle = request.getParameter("jobTitle");
		String storeId = request.getParameter("storeId");
		String modelName = request.getParameter("modelName");
		String guideName = request.getParameter("guideName");
 		String retailerId = ResourceUtil.getRetailerId();
 		
 		StringBuffer sql = new StringBuffer("SELECT ts.retailer_id retailerId, p.user_Id guideId, tjp.id paramId,");
 		sql.append(" '").append(searchTime).append("' AS searchTime, ")
 		.append(" ts.id storeId, ts.name storeName, p.real_Name guideName, p.phone_no mobilePhone,")
 		.append(" tjm.name modelName, tjp.job_title jobTitle, tjp.job_num jobNum ")
 		.append(" FROM t_store ts RIGHT JOIN t_person p ON p.store_id = ts.id ")
 		.append(" AND ts.retailer_id = p.to_retailer_id AND p.status = 'A' ")
 		.append(" JOIN t_store_jobmodel tsj on tsj.store_id=ts.id ")
 		.append(" JOIN t_s_user tsu ON tsu.id = p.user_id and tsu.user_status  in ('"+TSUser.USER_STATUS_ACTIVE+"','"+TSUser.USER_STATUS_FIXED+"')")
 		.append(" JOIN t_job_model tjm ON ts.job_model_id = tjm.id AND tjm.status = 'A' ")
 		.append(" LEFT JOIN t_job_param tjp ON tjp.model_id = tjm.id AND tjp.status = 'A' ")
 		.append(" WHERE ts.retailer_id = '"+retailerId+"'");

 		StringBuffer sqlbfCount = new StringBuffer(" SELECT count(1) ");
 		sqlbfCount.append(" FROM t_store ts RIGHT JOIN t_person p ON p.store_id = ts.id ")
 		.append(" AND ts.retailer_id = p.to_retailer_id AND p.status = 'A' ")
 		.append(" JOIN t_store_jobmodel tsj on tsj.store_id=ts.id ")
 		//只统计激活状态的导购任务
 		.append(" JOIN t_s_user tsu ON tsu.id = p.user_id and tsu.user_status  in ('"+TSUser.USER_STATUS_ACTIVE+"','"+TSUser.USER_STATUS_FIXED+"')")
 		.append(" JOIN t_job_model tjm ON ts.job_model_id = tjm.id AND tjm.status = 'A' ")
 		.append(" LEFT JOIN t_job_param tjp ON tjp.model_id = tjm.id AND tjp.status = 'A' ")
 		.append(" WHERE ts.retailer_id = '"+retailerId+"' ");
 		if(Utility.isNotEmpty(searchTime)){
 			sql.append(" and '"+searchTime+" 12:00:00' >= tsj.start_time ");
 			sql.append(" and '"+searchTime+" 12:00:00' <= tsj.end_time ");
 			sqlbfCount.append(" and '"+searchTime+" 12:00:00' >= tsj.start_time ");
 			sqlbfCount.append(" and '"+searchTime+" 12:00:00' <= tsj.end_time ");
		}
		if(Utility.isNotEmpty(jobTitle)){
			sql.append(" and tjp.job_title like '%"+jobTitle+"%'  ");
			sqlbfCount.append(" and tjp.job_title like '%"+jobTitle+"%'  ");
		}
		if(Utility.isNotEmpty(storeId)){
			sql.append(" and ts.id  ='"+storeId+"'  ");
			sqlbfCount.append(" and ts.id  ='"+storeId+"'  ");
		}
		if(Utility.isNotEmpty(modelName)){
			sql.append(" and tjm.NAME like '%"+modelName+"%'  ");
			sqlbfCount.append(" and tjm.NAME like '%"+modelName+"%'  ");
		}
		if(Utility.isNotEmpty(guideName)){
			sql.append(" and p.real_Name like '%"+guideName+"%'  ");
			sqlbfCount.append(" and p.real_Name like '%"+guideName+"%'  ");
		}
		/*String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
		sqlbf.append(" ORDER BY p.id desc");
		}else{
			sqlbf.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}*/

		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		if("all".equals(type)){
			resultList = this.systemService.findForJdbc(sql.toString(), null);
		}else if("list".equals(type)){
			resultList = this.systemService.findForJdbcParam(sql.toString(), dataGrid.getPage(),dataGrid.getRows(), null);
		}
		
		//查到数据
		if(Utility.isNotEmpty(resultList)){
			StringBuffer sqlUnion = new StringBuffer("SELECT t.guide_id guideId,t.param_id paramId,count(1) AS pace FROM t_job_detail t ");
			//循环遍历结果，拼接 累计任务完成数
			Set<String> set = new HashSet<String>();
			for (Map<String, Object> map : resultList) {
				String guideId = (String) map.get("guideId");
				set.add(guideId);
			}
			sqlUnion.append("where t.job_date = '"+searchTime+"' AND t.guide_id IN (");
			String guideIds = "";
			for (String string : set) {
				guideIds =  guideIds +"'"+string+"',";
			}
			guideIds= guideIds.substring(0, guideIds.length()-1);
			sqlUnion.append(guideIds+") GROUP BY t.guide_id,t.param_id");
			List<Map<String,Object>> resultList2 = this.systemService.findForJdbc(sqlUnion.toString(), null);
			//将两次查询的结果合并
			for (Map<String, Object> map : resultList) {
				String guideId = map.get("guideId").toString();
				String paramId = map.get("paramId").toString();
				map.put("pace", "0");
				map.put("myPercent", "0%");
				map.put("paceType", 0);
				map.put("jobDate", searchTime+"~"+searchTime);
				for (Map<String, Object> map2 : resultList2) {
					String guideId2 = map2.get("guideId").toString();
					String paramId2 = map2.get("paramId").toString();
					if(guideId.equals(guideId2) && paramId.equals(paramId2)){
						String pace = map2.get("pace").toString();
						map.put("pace", pace);
						int myPercent =  Integer.parseInt(pace)*100 / Integer.parseInt(map.get("jobNum").toString());
						map.put("myPercent", myPercent+"%");
						map.put("paceType", myPercent >= 100 ? 1:0);
						continue;
					}
				}
			}
		}
		
		int total = this.systemService.getCountForJdbc(sqlbfCount.toString()).intValue();
		resultMap.put("resultList", resultList);
		resultMap.put("total", total);
		return resultMap;
	}
	private Map<String,String> getDaySqlMap(HttpServletRequest request,DataGrid dataGrid){
		Map<String,String> sqlMap = new HashMap<String, String>();
		
		String searchTime = request.getParameter("searchTime");
		if(Utility.isEmpty(searchTime)){
			searchTime=DateUtils.getDataString(DateUtils.date_sdf);
		}
		String jobTitle = request.getParameter("jobTitle");
		String storeId = request.getParameter("storeId");
		String modelName = request.getParameter("modelName");
		String guideName = request.getParameter("guideName");
		String paceType = request.getParameter("paceType");
 		String retailerId = ResourceUtil.getRetailerId();
 		
 		StringBuffer sqlbf = new StringBuffer("select  cc.*,CONCAT(convert(cc.pace/cc.jobNum*100,decimal(10,0)),'%') as myPercent  ");
 		sqlbf.append(" from ( select ts.retailer_id retailerId,ts.id storeId,p.id guideId, '").append(searchTime).append("'  as searchTime, ");
 		sqlbf.append(" 	CASE tjp.cycle WHEN tjp.cycle='1' and datediff('").append(searchTime).append("',tjp.create_date)%tjp.day_time = 0 ");
 		sqlbf.append(" 	THEN	CONCAT(DATE_FORMAT(date_sub('").append(searchTime).append("',interval tjp.day_time day),'%Y-%m-%d'),'~',DATE_FORMAT('").append(searchTime).append("','%Y-%m-%d'))  ");
 		sqlbf.append(" 	WHEN tjp.cycle='1' and datediff('").append(searchTime).append("',tjp.create_date)%tjp.day_time > 0  ");
 		sqlbf.append(" 	THEN	CONCAT(DATE_FORMAT(DATE_ADD(tjp.create_date,interval (FLOOR(datediff('").append(searchTime).append("',tjp.create_date)/tjp.day_time)) DAY),'%Y-%m-%d'),  ");
 		sqlbf.append(" 	'~',DATE_FORMAT(DATE_ADD(tjp.create_date,interval  (CEILING(datediff('").append(searchTime).append("',tjp.create_date)/tjp.day_time)) DAY) ,'%Y-%m-%d'))  ");
 		sqlbf.append(" 	ELSE CONCAT(tjp.start_time,'~',tjp.end_time) END	jobDate, ");
 		sqlbf.append(" 	p.real_Name guideName,p.phone_no mobilePhone,ts.NAME storeName,tjm.NAME modelName, ");
 		sqlbf.append(" 	tjp.job_title jobTitle,tjp.job_num jobNum,  ");
 		sqlbf.append(" 	(SELECT count(1) FROM t_job_detail tjd WHERE tjd.retailer_id=ts.retailer_id and  tjd.guide_id = p.user_Id   ");
 		sqlbf.append(" 	AND tjd.param_id = tjp.id AND tjd.job_date = '").append(searchTime).append("') AS myDayPace,   ");
 		sqlbf.append(" 	CASE tjp.cycle WHEN tjp.cycle='1' and datediff('").append(searchTime).append("',tjp.create_date)%tjp.day_time = 0   ");
 		sqlbf.append(" 	THEN (SELECT count(1) FROM t_job_detail tjd WHERE tjd.retailer_id=ts.retailer_id and tjd.guide_id = p.user_Id   ");
 		sqlbf.append(" 	AND tjd.job_date <= DATE_FORMAT('").append(searchTime).append("','%Y-%m-%d')  ");
 		sqlbf.append(" 	and tjd.job_date >= DATE_FORMAT(date_sub('").append(searchTime).append("',interval tjp.day_time day),'%Y-%m-%d') AND tjd.param_id = tjp.id)   ");
 		sqlbf.append(" 	WHEN tjp.cycle='1' and datediff('").append(searchTime).append("',tjp.create_date)%tjp.day_time <> 0   ");
 		sqlbf.append(" 	THEN	(SELECT count(1) FROM t_job_detail tjd WHERE tjd.retailer_id=ts.retailer_id and tjd.guide_id = p.user_Id    ");
 		sqlbf.append(" 	AND tjd.job_date <= DATE_ADD(tjp.create_date,interval  (CEILING(datediff('").append(searchTime).append("',tjp.create_date)/tjp.day_time)) DAY)    ");
 		sqlbf.append(" 	and tjd.job_date >= DATE_ADD(tjp.create_date,interval (FLOOR(datediff('").append(searchTime).append("',tjp.create_date)/tjp.day_time)) DAY)  AND tjd.param_id = tjp.id)    ");
 		sqlbf.append(" 	ELSE (SELECT count(1) FROM t_job_detail tjd WHERE tjd.retailer_id=ts.retailer_id and tjd.guide_id = p.user_Id   ");
 		sqlbf.append(" 	AND tjd.job_date <= tjp.end_time and tjd.job_date >= tjp.start_time AND tjd.param_id = tjp.id) END	pace  ");
 		sqlbf.append(" 	FROM t_store ts RIGHT JOIN t_person p on p.store_id = ts.id and ts.retailer_id = p.to_retailer_id   ");
 		sqlbf.append(" 	JOIN t_job_model tjm on ts.job_model_id = tjm.id and tjm.`status` = 'A'  ");
 		sqlbf.append(" 	LEFT JOIN t_job_param tjp ON tjp.model_id = tjm.id and tjp.`status`= 'A'   ");
 		sqlbf.append(" 	WHERE ts.retailer_id = '").append(retailerId).append("' AND ts.`status` = 'A' AND p.`status` = 'A' AND p.user_type = '03'  ) cc  where 1=1 ");
		
		if(Utility.isNotEmpty(jobTitle)){
			sqlbf.append(" and  jobTitle like '%"+jobTitle+"%'  ");
		}
		if(Utility.isNotEmpty(storeId)){
			sqlbf.append(" and storeId  ='"+storeId+"'  ");
		}
		if(Utility.isNotEmpty(modelName)){
			sqlbf.append(" and modelName like '%"+modelName+"%'  ");
		}
		if(Utility.isNotEmpty(guideName)){
			sqlbf.append(" and guideName like '%"+guideName+"%'  ");
		}
		if(Utility.isNotEmpty(paceType)  && "1".equals(paceType)){
			sqlbf.append(" AND cc.jobNum <= cc.pace ");
		}else if(Utility.isNotEmpty(paceType)  && "0".equals(paceType)){
			sqlbf.append(" AND cc.jobNum > cc.pace ");
		}
 		
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
			sqlbf.append(" ORDER BY guideId desc");
		}else if("paceType".equals(sortName)){
			sqlbf.append(" ORDER BY myPercent ").append(" ").append(dataGrid.getOrder());
		}else{
			sqlbf.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		StringBuffer countSqlbf = new StringBuffer("select count(1)  from (");
		countSqlbf.append(sqlbf).append(") a");
		
		
		sqlMap.put("sql", sqlbf.toString());
		sqlMap.put("countSql", countSqlbf.toString());
		return sqlMap;
	}
	
	/**
	 * 导出excel
	 * 导购任务日报表导出
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "exportDayXls")
	public String exportDayXls(HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid,ModelMap modelMap) {

		String dateStr = DateUtils.date2Str(DateUtils.date_sdf);
		String sql = getDaySqlMap(request,dataGrid).get("sql");

//		List<TJobDayDetailCountVo> list =systemService.findObjForJdbc(sql, TJobDayDetailCountVo.class);
		
		Map<String,Object> resultMap = getDaySqlMap2(request,dataGrid,"all");
		List<Map<String,Object>> resultList = (List<Map<String, Object>>) resultMap.get("resultList");
		List<TJobDayDetailCountVo> list = new ArrayList<TJobDayDetailCountVo>();
		//将两次查询的结果合并
		for (Map<String, Object> map : resultList) {
			TJobDayDetailCountVo tJobDayDetailCountVo = new TJobDayDetailCountVo();
//				tJobDayDetailCountVo.setId(map.get("id").toString());
				tJobDayDetailCountVo.setSearchTime(map.get("searchTime").toString());
				tJobDayDetailCountVo.setJobDate(map.get("jobDate").toString());
				tJobDayDetailCountVo.setStoreName(map.get("storeName").toString());
				tJobDayDetailCountVo.setGuideName(map.get("guideName").toString());
				tJobDayDetailCountVo.setMobilePhone(map.get("mobilePhone").toString());
				tJobDayDetailCountVo.setModelName(map.get("modelName").toString());
				tJobDayDetailCountVo.setJobTitle(map.get("jobTitle").toString());
				tJobDayDetailCountVo.setJobNum(map.get("jobNum").toString());
				tJobDayDetailCountVo.setMyDayPace(Long.valueOf(map.get("pace").toString()));
				tJobDayDetailCountVo.setPace(Long.valueOf(map.get("pace").toString()));
				tJobDayDetailCountVo.setMyPercent(map.get("myPercent").toString());
				list.add(tJobDayDetailCountVo);
		}
		
		
		modelMap.put(NormalExcelConstants.FILE_NAME,"任务统计");
		modelMap.put(NormalExcelConstants.CLASS,TJobDayDetailCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("导购任务统计日报表", "导出时间:" + dateStr+ "                "
				+ "导出人:"+ResourceUtil.getSessionUserName().getRealName()+ "    ", "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 导出excel
	 * 导购金币导出
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "ExportXlsGold")
	public String ExportXlsGold(HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid,ModelMap modelMap) {
		String createDate_begin = request.getParameter("createDate_begin");
		String createDate_end = request.getParameter("createDate_end");
		guideCountService.getGuideGoldCountSql(request, dataGrid);
		List<Map<String,Object>> resultList = (List<Map<String, Object>>) dataGrid.getResults();
		List<CommonVo> storeList = tStoreService.getStoreList();
		Map<String,Object> storeMap = new HashMap<String,Object>();
		for (CommonVo commonVo : storeList) {
			storeMap.put(commonVo.getId(), commonVo.getName());
		}
		
		String dateStr = DateUtils.date2Str(DateUtils.date_sdf);
		String sql = getDaySqlMap(request,dataGrid).get("sql");
		List<TGuideGoldCountVo> list = new ArrayList<TGuideGoldCountVo>();
		//将两次查询的结果合并
		for (Map<String, Object> map : resultList) {
			TGuideGoldCountVo GuideGoldCountVo = new TGuideGoldCountVo();
			GuideGoldCountVo.setId(map.get("Id").toString());
			GuideGoldCountVo.setStoreId(storeMap.get(map.get("storeId")).toString());
			GuideGoldCountVo.setGuideName(map.get("guideName").toString());
			GuideGoldCountVo.setPhoneNo(map.get("phoneNo").toString());
			GuideGoldCountVo.setGoldNum(map.get("goldNum").toString());
			list.add(GuideGoldCountVo);
		}
		
		modelMap.put(NormalExcelConstants.FILE_NAME,"导购金币统计");
		modelMap.put(NormalExcelConstants.CLASS,TGuideGoldCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("导购金币统计表", "导出时间:" + dateStr+ "                " +
				"统计时间："+createDate_begin+"~"+createDate_end+ "                " +
				"导出人:"+ResourceUtil.getSessionUserName().getRealName()+ "    ", "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
}
