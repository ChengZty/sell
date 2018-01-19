package com.buss.job.controller;
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
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtil;
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

import com.buss.job.entity.TDayAwardDetailEntity;
import com.buss.job.entity.TDayJobDetailEntity;
import com.buss.job.vo.TDayAwardDetailTotalVo;
import com.buss.store.service.TStoreServiceI;



/**   
 * @Title: Controller
 * @Description: 每日奖励明细
 * @author onlineGenerator
 * @date 2016-11-04 18:25:27
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tDayAwardDetailController")
public class TDayAwardDetailController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TDayAwardDetailController.class);

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
	 * 每日奖励明细列表 页面跳转
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
		return new ModelAndView("com/buss/job/tDayAwardDetailList");
	}
	
	/**
	 * 每日奖励明细列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "mainTab")
	public ModelAndView mainTab(HttpServletRequest request) {
		return new ModelAndView("com/buss/job/tJobMainTab");
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
	public void datagrid(TDayAwardDetailEntity tDayAwardDetail,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
			Map<String,String> sqlMap = getSqlMap(request);
			
			int total = this.systemService.getCountForJdbc(sqlMap.get("sqlCount")).intValue();
			//List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			List<TDayAwardDetailTotalVo> list = new ArrayList<TDayAwardDetailTotalVo>();
			if(total>0){
				//resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
				list = systemService.findObjForJdbc(sqlMap.get("sql"),dataGrid.getPage(),dataGrid.getRows(), TDayAwardDetailTotalVo.class);
			}
			dataGrid.setResults(list);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	private Map<String,String> getSqlMap(HttpServletRequest request){
		Map<String,String> sqlMap = new HashMap<String, String>();
		//自定义追加查询条件
		String retailerId = ResourceUtil.getRetailerId();
		String guideName = request.getParameter("guideName");
		String storeId = request.getParameter("storeId");
		String jobDate_begin = request.getParameter("jobDate_begin");
		String jobDate_end = request.getParameter("jobDate_end");
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
		
		StringBuffer sql = new StringBuffer("select *,(dayCustomer+dayFirst+dayPublish+dayRecommend+bindingSum+goodsSum+recomendGuideSum+recomendSum) goldTotal from (");
		sql.append("select tsu.uersId, jobDate, guideName ,storeId, storeName,toRetailerId, ");
		sql.append("sum(CASE job_code WHEN 'dayCustomer' THEN number ELSE 0 END ) dayCustomer ,");
		sql.append("sum(CASE job_code WHEN 'dayFirst' THEN number ELSE 0 END ) dayFirst ,");
		sql.append("sum(CASE job_code WHEN 'dayPublish' THEN number ELSE 0 END ) dayPublish ,");
		sql.append("sum(CASE job_code WHEN 'dayRecommend' THEN number ELSE 0 END ) dayRecommend , ");
		sql.append("sum(CASE job_code WHEN 'bindingSum' THEN number ELSE 0 END ) bindingSum , ");
		sql.append("sum(CASE job_code WHEN 'goodsSum' THEN number ELSE 0 END ) goodsSum ,");
		sql.append("sum(CASE job_code WHEN 'recomendGuideSum' THEN number ELSE 0 END ) recomendGuideSum ,");
		sql.append("sum(CASE job_code WHEN 'recomendSum' THEN number ELSE 0 END ) recomendSum from (");
		sql.append("select p.id id, ifnull(p.real_name,'') guideName, p.user_id uersId,ts.name storeName,p.store_id storeId,p.to_retailer_id toRetailerId from  t_person p left join t_store ts on p.store_id = ts.id ");
		sql.append("where p.user_type = '03' and p.status='A' ");

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
		sql.append(") tsu left join(");
		sql.append("select id,job_code, title,sum(gold_num) gold_num,count(title) number,job_date jobDate, user_id from t_day_award_detail where 1=1 ");
		Date date = new Date();
		String dateStr = DateUtil.dateToString(date,"yyyy-MM-dd");
		if(!Utility.isEmpty(jobDate_begin)){
			sql.append(sqlBegin);
		}else{
			sql.append("and job_date >= '").append(dateStr).append("' ");
		}
		if(!Utility.isEmpty(jobDate_end)){
			sql.append(sqlEnd);
		}else{
			sql.append("and job_date <= '").append(dateStr).append("' ");
		}
		sql.append("GROUP BY user_id,  title order by jobDate desc");
		sql.append(") tdad  on tdad.user_id = tsu.uersId where 1=1  GROUP BY tsu.uersId) tdadu order by goldTotal desc");
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
		
		List<TDayAwardDetailTotalVo> list = systemService.findObjForJdbc(sql, TDayAwardDetailTotalVo.class);
		
		/*if(!Utility.isEmpty(resultList)){
			for (Map<String, Object> map : resultList) {
				TDayAwardDetailVo vo = new TDayAwardDetailVo();
				try 
				{  
		            BeanInfo beanInfo = Introspector.getBeanInfo(vo.getClass());  
		            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
		            for (PropertyDescriptor property : propertyDescriptors) {  
		                String key = property.getName();  
		                if (map.containsKey(key)) {  
		                    Object value = map.get(key);  
		                    Method setter = property.getWriteMethod();  
		                    setter.invoke(vo, value);  
		                }  
		            }  
		        } catch (Exception e) {  
		        }
		        list.add(vo);
			}
		}*/
		modelMap.put(NormalExcelConstants.FILE_NAME,"每日奖励明细");
		modelMap.put(NormalExcelConstants.CLASS,TDayAwardDetailTotalVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("每日奖励明细统计", "统计时间:" 
				+ startTime + " ~ " + endTime + "                " 
				+ "导出时间:" + dateStr + "                "
				+ "导出人:"+ResourceUtil.getSessionUserName().getRealName()+ "    ", "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
}
