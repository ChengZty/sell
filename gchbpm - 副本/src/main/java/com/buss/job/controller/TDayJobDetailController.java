package com.buss.job.controller;
import java.util.ArrayList;
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
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.buss.job.entity.TDayJobDetailEntity;
import com.buss.job.vo.TDayJobDetailTotalVo;
import com.buss.store.service.TStoreServiceI;



/**   
 * @Title: Controller
 * @Description: 每日任务明细
 * @author onlineGenerator
 * @date 2016-11-04 18:25:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tDayJobDetailController")
public class TDayJobDetailController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TDayJobDetailController.class);

	@Autowired
	private SystemService systemService;
	@Autowired
	private TStoreServiceI tStoreService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 每日任务明细列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String useType = ResourceUtil.getSessionUserName().getUserType();
		if(!TSUser.USER_TYPE_01.equals(useType)){
			String storeStr = "";
			List<CommonVo> storeList = tStoreService.getStoreList();
			for (CommonVo commonVo : storeList) {
				storeStr = storeStr + commonVo.getName() + "_" + commonVo.getId() + ",";
			}
//		storeStr += " _null";
			if(storeStr.length() > 1){
				storeStr = storeStr.substring(0, storeStr.length()-1);
			}
			request.setAttribute("stores", storeStr);
		}
		request.setAttribute("userType",useType );
		request.setAttribute("initDate", DateUtils.date2Str(DateUtils.date_sdf));
		return new ModelAndView("com/buss/job/tDayJobDetailList");
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
	public void datagrid(TDayJobDetailEntity tDayJobDetail,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			Map<String,String> sqlMap = getSqlMap(request);
			
			int total = this.systemService.getCountForJdbc(sqlMap.get("sqlCount")).intValue();
			//List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			List<TDayJobDetailTotalVo> list = new ArrayList<TDayJobDetailTotalVo>();
			if(total>0){
				//resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
				list =systemService.findObjForJdbc(sqlMap.get("sql"),dataGrid.getPage(),dataGrid.getRows(),TDayJobDetailTotalVo.class);
			}
			dataGrid.setResults(list);
			dataGrid.setTotal(total);
			
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**根据reqest条件获取sql
	 * @param request
	 * @return
	 */
	private Map<String,String> getSqlMap(HttpServletRequest request){
		Map<String,String> sqlMap = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String jobDate_begin = request.getParameter("jobDate_begin");
		String jobDate_end = request.getParameter("jobDate_end");
		String guideName = request.getParameter("guideName");
		String storeId = request.getParameter("storeId");
		String toRetailerId = request.getParameter("toRetailerId");
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			jobDate_begin = DateUtils.date2Str(DateUtils.date_sdf);
			jobDate_end = jobDate_begin;
		}
		//取到查询条件的字符串
		String sqlRetailerId = " and p.to_retailer_id= '"+ retailerId +"' ";
		String sqlUserId = " and p.real_name like '%"+ guideName +"%' ";
		String sqlBegin = " and job_date >= '"+ jobDate_begin +"' ";
		String sqlEnd = " and job_date <= '"+ jobDate_end +"' ";
		String sqlStoreId = " and p.store_id= '"+ storeId +"' ";
		String sqlToRetailerId = " and p.to_retailer_id= '"+ toRetailerId +"' ";
		
		StringBuffer sql = new StringBuffer("select tsu.uersId id, job_date jobDate, guideName, storeId,storeName,toRetailerId,");
		sql.append("sum(CASE job_code WHEN 'recomendGoodsNum' THEN finished_num ELSE 0 END ) + ");
//		sql.append("sum(CASE job_code WHEN 'recomendSceneNum' THEN finished_num ELSE 0 END ) + ");
		sql.append("sum(CASE job_code WHEN 'recomendOrderNum' THEN finished_num ELSE 0 END ) + ");
		sql.append("sum(CASE job_code WHEN 'recomendNewsNum' THEN finished_num ELSE 0 END ) recomendNum ,");//每日推送
		sql.append("sum(CASE job_code WHEN 'recomendGuideNum' THEN finished_num ELSE 0 END ) + ");
		sql.append("sum(CASE job_code WHEN 'GoodsNum' THEN finished_num ELSE 0 END ) releaseNum ,");//发布总数=管家推荐总量+商品发布数
		sql.append("sum(CASE job_code WHEN 'focusCustomerNum' THEN finished_num ELSE 0 END ) + ");
		sql.append("sum(CASE job_code WHEN 'bindingCustomerNum' THEN finished_num ELSE 0 END ) customerNum , ");//每日完善 =新增待发展顾客数 +顾客资料完整度达到80%顾客数
		sql.append("sum(CASE job_code WHEN 'recomendOrderNum' THEN finished_num ELSE 0 END ) recomendOrderNum ,");//每日推送订单数
		sql.append("sum(CASE job_code WHEN 'payOrderNum' THEN finished_num ELSE 0 END ) payOrderNum ");//每日成交数
//		sql.append("sum(CASE job_code WHEN 'CustomerInfoNum' THEN finished_num ELSE 0 END ) CustomerInfoNum ");//任一完成顾客信息超过20%数
		sql.append("from ( select p.user_Id uersId, ifnull(p.real_name,'') guideName,store_id storeId,ts.name storeName,p.to_retailer_id toRetailerId from  t_person p left join t_store ts on p.store_id = ts.id "); 
		sql.append(" where p.user_type = '03' and p.status='A' "); 
		
		StringBuffer sqlCount = new StringBuffer("select count(1) from 	t_person p	LEFT JOIN t_store ts ON p.store_id = ts.id")
			.append(" where p.user_type = '03' and p.status='A' "); 
		if(!Utility.isEmpty(retailerId)){
			sql.append(sqlRetailerId);
			sqlCount.append(sqlRetailerId);
		}
		if(!Utility.isEmpty(toRetailerId)){
			sql.append(sqlToRetailerId);
			sqlCount.append(sqlToRetailerId);
		}
		if(!Utility.isEmpty(storeId)){
			sql.append(sqlStoreId);
			sqlCount.append(sqlStoreId);
		}
		if(!Utility.isEmpty(guideName)){
			sql.append(sqlUserId);
			sqlCount.append(sqlUserId);
		}
		sql.append(") tsu left join(select * from t_day_job_detail where 1=1 ");
		
		if(!Utility.isEmpty(jobDate_begin)){
			sql.append(sqlBegin);
		}
		if(!Utility.isEmpty(jobDate_end)){
			sql.append(sqlEnd);
		}
		
		sql.append("GROUP BY job_date,user_id,job_code, title) tdjd  on tdjd.user_id = tsu.uersId where 1=1 ");
		sql.append("GROUP BY jobDate, tsu.uersId order by jobDate desc");
		sqlMap.put("sql", sql.toString());
		sqlMap.put("sqlCount", sqlCount.toString());
		return sqlMap;
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TDayJobDetailEntity tDayJobDetail,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String startTime = request.getParameter("jobDate_begin");
		String endTime = request.getParameter("jobDate_end");
		String dateStr = DateUtils.date2Str(DateUtils.date_sdf);
		if(Utility.isEmpty(startTime)){
			startTime =  "";
		}
		if(Utility.isEmpty(endTime)){
			endTime = "";
		}
		
		String sql = getSqlMap(request).get("sql");
		
		List<TDayJobDetailTotalVo> list =systemService.findObjForJdbc(sql.toString(),TDayJobDetailTotalVo.class);

		
		modelMap.put(NormalExcelConstants.FILE_NAME,"每日任务明细");
		modelMap.put(NormalExcelConstants.CLASS,TDayJobDetailTotalVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("每日任务明细统计", "统计时间:" 
				+ startTime + " ~ " + endTime + "                " 
				+ "导出时间:" + dateStr + "                "
				+ "导出人:"+ResourceUtil.getSessionUserName().getRealName()+ "    ", "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
}
