package com.buss.count.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.count.entity.TStoreGuideMonTargetEntity;
import com.buss.count.service.TStoreGuideMonTargetServiceI;
import com.buss.count.vo.GuideTargetReachVo;



/**   
 * @Title: Controller
 * @Description: 导购月目标
 * @author onlineGenerator
 * @date 2017-05-17 11:28:27
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tStoreGuideMonTargetController")
public class TStoreGuideMonTargetController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TStoreGuideMonTargetController.class);

	@Autowired
	private TStoreGuideMonTargetServiceI tStoreGuideMonTargetService;
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
	 * 导购月目标列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "targetList")
	public ModelAndView targetList(HttpServletRequest request) {
		request.setAttribute("retailer_Id", ResourceUtil.getRetailerId());
		return new ModelAndView("com/buss/count/tStoreGuideMonTargetList");
	}
	
	/**
	 * 导购月目标达成列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "targetReachList")
	public ModelAndView targetReachList(HttpServletRequest request) {
		request.setAttribute("retailer_Id", ResourceUtil.getRetailerId());
		request.setAttribute("targetMonth_begin", DateUtils.getFirstDayOfMonth(new Date()));
		request.setAttribute("targetMonth_end", DateUtils.getDataString(DateUtils.date_sdf));
		return new ModelAndView("com/buss/count/tStoreGuideReachList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TStoreGuideMonTargetEntity tStoreGuideMonTarget,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TStoreGuideMonTargetEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tStoreGuideMonTarget, request.getParameterMap());
		try{
			String retailerId = ResourceUtil.getRetailerId();
			if(retailerId!=null){
				cq.eq("retailerId", retailerId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tStoreGuideMonTargetService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据 导购月目标达成报表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagridForReach")
	public void datagridForReach(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			String sql = this.getSql(request);//获取查询的sql
			String countSql = this.getCountSql(request);//获取查询的sql
			List<Map<String, Object>> resultList = null;
			int total = this.systemService.getCountForJdbc(countSql).intValue();
			if(total>0){
				resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
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

	//获取查询的sql
	private String getSql(HttpServletRequest request) {
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		String retailerId = ResourceUtil.getRetailerId();
		String targetMonth_begin = request.getParameter("targetMonth_begin");//销售明细日期
		String targetMonth_end = request.getParameter("targetMonth_end");//销售明细日期
		String storeId = request.getParameter("storeId");//店铺ID
		String guideName = request.getParameter("guideName");//导购姓名
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			targetMonth_begin = DateUtils.getFirstDayOfMonth(new Date());
			targetMonth_end = DateUtils.getDataString(DateUtils.date_sdf);
		}
		String targetMonth = this.getMonthRange(targetMonth_begin,targetMonth_end);//获取月份
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT gmt.id,smt.target_month targetMonth,smt.store_id storeId,s.`name` storeName,smt.target_money storeTargetMoney,IFNULL(tt.storeTotalMoney,0) storeTotalMoney,")
			.append("IFNULL(ROUND(IFNULL(tt.storeTotalMoney,0)*100/smt.target_money,2),0) storeReachRate,gmt.guide_id guideId,gmt.guide_name guideName,")
			.append("gmt.target_money guideTargetMoney,IFNULL(t.onlineMoney,0) onlineMoney,IFNULL(t.offlineMoney,0) offlineMoney,")
			.append("IFNULL(t.guideTotalMoney,0) guideTotalMoney,IFNULL(ROUND(IFNULL(t.guideTotalMoney,0)*100/gmt.target_money,2),0) guideReachRate")
			.append(" from t_store_mon_target smt")
			.append(" LEFT JOIN t_store s on smt.store_id = s.id")
			.append(" LEFT JOIN t_store_guide_mon_target gmt on smt.id = gmt.mon_target_id")
			.append(" LEFT JOIN")
			.append("(SELECT d.target_month,d.store_id,SUM(d.online_money+d.offline_money) storeTotalMoney")//按月合计每个店铺的业绩
			.append(" from t_day_sales_record d")
			.append(" WHERE d.`status` = 'A'");
		if(Utility.isNotEmpty(targetMonth_begin)){
			sql.append(" and d.date_time >='").append(targetMonth_begin).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(targetMonth_end)){
			sql.append(" and d.date_time <='").append(targetMonth_end).append(" 23:59:59'");
		}
		sql.append(" GROUP BY d.target_month,d.store_id")
			.append(") tt on gmt.store_id = tt.store_id and gmt.target_month = tt.target_month")
			.append(" LEFT JOIN")
			.append("(SELECT d.target_month,d.guide_id,SUM(d.online_money) onlineMoney,SUM(d.offline_money) offlineMoney,SUM(d.online_money+d.offline_money) guideTotalMoney")//按月合计每个导购的业绩
			.append(" from t_day_sales_record d")
			.append(" WHERE d.`status` = 'A'");
		if(Utility.isNotEmpty(targetMonth_begin)){
			sql.append(" and d.date_time >='").append(targetMonth_begin).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(targetMonth_end)){
			sql.append(" and d.date_time <='").append(targetMonth_end).append(" 23:59:59'");
		}
		sql.append(" GROUP BY d.target_month,d.guide_id")
			.append(") t on gmt.guide_id = t.guide_id and gmt.target_month = t.target_month")
			.append(" WHERE smt.`status` = 'A' and gmt.`status` = 'A'")
			.append(" and smt.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(storeId)){
			sql.append(" and smt.store_id = '").append(storeId).append("'");
		}
		if(Utility.isNotEmpty(guideName)){
			sql.append(" and gmt.guide_name like '%").append(guideName).append("%'");
		}
		if(Utility.isNotEmpty(targetMonth)){
			sql.append(" and smt.target_month in (").append(targetMonth).append(")");
		}
		sql.append(" ORDER BY smt.target_month desc,smt.store_id desc");
		return sql.toString();
	}

	//获取查询总数的sql
	private String getCountSql(HttpServletRequest request) {
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		String retailerId = ResourceUtil.getRetailerId();
		String targetMonth_begin = request.getParameter("targetMonth_begin");//销售明细日期
		String targetMonth_end = request.getParameter("targetMonth_end");//销售明细日期
		String storeId = request.getParameter("storeId");//店铺ID
		String guideName = request.getParameter("guideName");//导购姓名
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			targetMonth_begin = DateUtils.getFirstDayOfMonth(new Date());
			targetMonth_end = DateUtils.getDataString(DateUtils.date_sdf);
		}
		String targetMonth = this.getMonthRange(targetMonth_begin,targetMonth_end);//获取月份
		StringBuffer countSql = new StringBuffer();
		countSql.append(" SELECT count(1) from t_store_mon_target smt")
			.append(" LEFT JOIN t_store s on smt.store_id = s.id")
			.append(" LEFT JOIN t_store_guide_mon_target gmt on smt.id = gmt.mon_target_id")
			.append(" LEFT JOIN")
			.append("(SELECT d.target_month,d.store_id,SUM(d.online_money+d.offline_money) storeTotalMoney")//按月合计每个店铺的业绩
			.append(" from t_day_sales_record d")
			.append(" WHERE d.`status` = 'A'");
		if(Utility.isNotEmpty(targetMonth_begin)){
			countSql.append(" and d.date_time >='").append(targetMonth_begin).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(targetMonth_end)){
			countSql.append(" and d.date_time <='").append(targetMonth_end).append(" 23:59:59'");
		}
		countSql.append(" GROUP BY d.target_month,d.store_id")
			.append(") tt on gmt.store_id = tt.store_id and gmt.target_month = tt.target_month")
			.append(" LEFT JOIN")
			.append("(SELECT d.target_month,d.guide_id,SUM(d.online_money) onlineMoney,SUM(d.offline_money) offlineMoney,SUM(d.online_money+d.offline_money) guideTotalMoney")//按月合计每个导购的业绩
			.append(" from t_day_sales_record d")
			.append(" WHERE d.`status` = 'A'");
		if(Utility.isNotEmpty(targetMonth_begin)){
			countSql.append(" and d.date_time >='").append(targetMonth_begin).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(targetMonth_end)){
			countSql.append(" and d.date_time <='").append(targetMonth_end).append(" 23:59:59'");
		}
		countSql.append(" GROUP BY d.target_month,d.guide_id")
		.append(") t on gmt.guide_id = t.guide_id and gmt.target_month = t.target_month")
		.append(" WHERE smt.`status` = 'A' and gmt.`status` = 'A'")
		.append(" and smt.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(storeId)){
			countSql.append(" and smt.store_id = '").append(storeId).append("'");
		}
		if(Utility.isNotEmpty(guideName)){
			countSql.append(" and gmt.guide_name like '%").append(guideName).append("%'");
		}
		if(Utility.isNotEmpty(targetMonth)){
			countSql.append(" and smt.target_month in (").append(targetMonth).append(")");
		}
		return countSql.toString();
	}
	
	/**获取月份范围，返回格式如 '2017-05','2017-06',0表示没有符合的月份，null则表示全部，如有一个参数则只查这个月的数据
	 * @param targetMonth_begin 如 '2017-05-01'
	 * @param targetMonth_end 如 '2017-06-05'
	 * @return 
	 */
	private String getMonthRange(String targetMonth_begin,String targetMonth_end) {
		String monthRange = "";
		if(Utility.isNotEmpty(targetMonth_begin)&&Utility.isNotEmpty(targetMonth_end)){
			int startYmd = Integer.valueOf(targetMonth_begin.replaceAll("-", ""));//如20170501
			int endYmd = Integer.valueOf(targetMonth_end.replaceAll("-", ""));//如20171020
			int start_Ym = startYmd/100;//如201705
			int end_Ym = endYmd/100;//如201710
			if(startYmd>endYmd){
				monthRange = "0";
			}else{//开始年月日小于等于结束年月日
				int start_year = start_Ym/100;//如2017
				int end_year = end_Ym/100;//如2017
				for(int k =start_year;k<=end_year;k++){
					int start_month = start_Ym%100;//如5
					int end_month = end_Ym%100;//如10
					int start_m = start_month;//当前循环的开始月份
					int end_m = end_month;//当前循环的结束月份
					if(start_year!=end_year){//跨年
						if(k ==start_year){//第一次循环的时候月份从起始月到12
							end_m=12;
						}
						if(k>start_year&&k<end_year){//第一次和最后一次之间循环的时候月份从1到12
							start_m = 1;
							end_m = 12;
						}
						if(k ==end_year){//最后一次循环的时候月份从1月到结束月份
							start_m = 1;
							end_m = end_month;
						}
					}
					for(int i =start_m;i<=end_m;i++){
						monthRange+="'"+k+"-"+(i>9?i:"0"+i)+"',";
					}
				}
				monthRange = monthRange.substring(0, monthRange.length()-1);
			}
		}else if(Utility.isNotEmpty(targetMonth_begin)){
			monthRange = "'"+targetMonth_begin.substring(0, 7)+"'";
		}else if(Utility.isNotEmpty(targetMonth_end)){
			monthRange = "'"+targetMonth_end.substring(0, 7)+"'";
		}else{
			monthRange = null;
		}
		return monthRange;
	}

	/**
	 * 删除导购月目标并更新店铺月目标
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TStoreGuideMonTargetEntity tStoreGuideMonTarget, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		message = "导购月目标删除成功";
		try{
			tStoreGuideMonTarget = systemService.flushEntity(TStoreGuideMonTargetEntity.class,tStoreGuideMonTarget.getId());
			tStoreGuideMonTargetService.doDel(tStoreGuideMonTarget);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "导购月目标删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除导购月目标并更新店铺月目标
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "导购月目标删除成功";
		try{
			for(String id:ids.split(",")){
				TStoreGuideMonTargetEntity tStoreGuideMonTarget = systemService.flushEntity(TStoreGuideMonTargetEntity.class,id);
				tStoreGuideMonTargetService.doDel(tStoreGuideMonTarget);
			}
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "导购月目标删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加导购月目标
	 * @param ids
	 * @return
	 */
	/*@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TStoreGuideMonTargetEntity tStoreGuideMonTarget, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购月目标添加成功";
		try{
			tStoreGuideMonTargetService.save(tStoreGuideMonTarget);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "导购月目标添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}*/
	
	/**
	 * 更新导购月目标并更新店铺月目标
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TStoreGuideMonTargetEntity tStoreGuideMonTarget, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购月目标更新成功";
		try {
			tStoreGuideMonTargetService.doUpdate(tStoreGuideMonTarget);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "导购月目标更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 导购月目标新增页面跳转
	 * @return
	 */
	/*@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TStoreGuideMonTargetEntity tStoreGuideMonTarget, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tStoreGuideMonTarget.getId())) {
			tStoreGuideMonTarget = tStoreGuideMonTargetService.flushEntity(TStoreGuideMonTargetEntity.class, tStoreGuideMonTarget.getId());
			req.setAttribute("tStoreGuideMonTargetPage", tStoreGuideMonTarget);
		}
		return new ModelAndView("com/buss/count/tStoreGuideMonTarget-add");
	}*/
	/**
	 * 导购月目标编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TStoreGuideMonTargetEntity tStoreGuideMonTarget, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tStoreGuideMonTarget.getId())) {
			tStoreGuideMonTarget = tStoreGuideMonTargetService.flushEntity(TStoreGuideMonTargetEntity.class, tStoreGuideMonTarget.getId());
			req.setAttribute("tStoreGuideMonTargetPage", tStoreGuideMonTarget);
		}
		return new ModelAndView("com/buss/count/tStoreGuideMonTarget-update");
	}
	
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		List<GuideTargetReachVo> tStoreGuideReachList = this.tStoreGuideMonTargetService.findObjForJdbc(this.getSql(request), GuideTargetReachVo.class);
		modelMap.put(NormalExcelConstants.FILE_NAME,"导购目标达成报表");
		modelMap.put(NormalExcelConstants.CLASS,GuideTargetReachVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("导购目标达成报表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tStoreGuideReachList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
}
