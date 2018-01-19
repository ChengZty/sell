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
import com.buss.job.entity.TJobGrowEntity;
import com.buss.job.vo.TJobGrowVo;
import com.buss.store.service.TStoreServiceI;



/**   
 * @Title: Controller
 * @Description: 成长任务
 * @author onlineGenerator
 * @date 2016-11-05 14:54:26
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tJobGrowController")
public class TJobGrowController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TJobGrowController.class);

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
	 * 成长任务列表 页面跳转
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
		return new ModelAndView("com/buss/job/tJobGrowList");
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
	public void datagrid(TJobGrowEntity tJobGrow,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			
			Map<String,String> sqlMap = getSqlMap(request);
			int total = this.systemService.getCountForJdbc(sqlMap.get("sqlCount")).intValue();
//			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			List<TJobGrowVo> list = new ArrayList<TJobGrowVo>();
			if(total>0){
//				resultList =  systemService.findForJdbc(sqlMap.get("sql"),dataGrid.getPage(),dataGrid.getRows());
				list =systemService.findObjForJdbc(sqlMap.get("sql"),dataGrid.getPage(),dataGrid.getRows(),TJobGrowVo.class);
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
		String retailerId = ResourceUtil.getRetailerId();
		String guideName = request.getParameter("guideName");
		String storeId = request.getParameter("storeId");
		String toRetailerId = request.getParameter("toRetailerId");
		
		//取到查询条件的字符串
		String sqlRetailerId = " and to_retailer_id= '"+ retailerId +"' ";
		String sqlUserId = " and p.real_name like '%"+ guideName +"%' ";
		String sqlStoreId = " and store_id= '"+ storeId +"' ";
		String sqlToRetailerId = " and p.to_retailer_id= '"+ toRetailerId +"' ";
		
		StringBuffer sql = new StringBuffer("select tsu.uersId id,guideName, storeId, storeName,toRetailerId,");
		sql.append("sum(CASE job_code WHEN 'recomendGuideSum' THEN finished_num ELSE 0 END  ) recomendGuideSum ,");
		sql.append("sum(CASE job_code WHEN 'payOrderSum'  THEN finished_num ELSE 0 END  ) payOrderSum ,");
		sql.append("sum(CASE job_code WHEN 'recomendSum' THEN finished_num ELSE 0 END  ) recomendSum ,");
		sql.append("sum(CASE job_code WHEN 'goodsSum' THEN finished_num ELSE 0 END ) goodsSum ,");
		sql.append("sum(CASE job_code WHEN 'bindingSum' THEN finished_num ELSE 0 END ) bindingSum ");
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
		sql.append(") tsu left join(");
		sql.append("select * from t_job_grow GROUP BY user_id, title");
		sql.append(") tjg  on tjg.user_id = tsu.uersId  GROUP BY uersId ");
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

		String dateStr = DateUtils.date2Str(DateUtils.date_sdf);
		String sql = getSqlMap(request).get("sql");
		
		List<TJobGrowVo> list =systemService.findObjForJdbc(sql, TJobGrowVo.class);
		modelMap.put(NormalExcelConstants.FILE_NAME,"成长任务");
		modelMap.put(NormalExcelConstants.CLASS,TJobGrowVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("成长任务统计", "导出时间:" + dateStr+ "                "
				+ "导出人:"+ResourceUtil.getSessionUserName().getRealName()+ "    ", "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
}
