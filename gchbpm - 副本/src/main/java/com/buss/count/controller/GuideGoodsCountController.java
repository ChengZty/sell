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
import org.jeecgframework.web.system.service.CategoryServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.buss.count.service.GuideCountServiceI;
import com.buss.count.vo.GuideGoodsCountVo;
import com.buss.count.vo.GuideGoodsTotalCountVo;
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
@RequestMapping("/guideGoodsCountController")
public class GuideGoodsCountController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GuideGoodsCountController.class);

	@Autowired
	private GuideCountServiceI guideCountService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private CategoryServiceI tSCategoryService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 商品统计列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listGoodsCount")
	public ModelAndView listGoodsCount(HttpServletRequest request) {
//		guideCountService.getInitData(request);
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailer_Id", retailerId);
		guideCountService.initFirstSearchTimeRange(request);
		return new ModelAndView("com/buss/count/tGoodsCountList");
	}
	
	/**
	 * 商品统计列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listGoodsTotalCount")
	public ModelAndView listGoodsTotalCount(HttpServletRequest request) {
		guideCountService.initFirstSearchTimeRange(request);
		return new ModelAndView("com/buss/count/tGoodsTotalCountList");
	}
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "tGoodsCountDatagrid")
	public void tGoodsCountDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
			Map<String,String> sqlMap = this.getQuerySql(request,dataGrid);
			String sql = sqlMap.get("sql");
			String countSql = sqlMap.get("countSql");
			List<GuideGoodsCountVo> resultList = new ArrayList<GuideGoodsCountVo>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),GuideGoodsCountVo.class);
			}
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
		String startTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		String code = request.getParameter("code");//款号
		String goodsName = request.getParameter("goodsName");//名称
		String brandName = request.getParameter("brandName");//名称
		String storeId = request.getParameter("storeId");//店铺id
		String guideName = request.getParameter("guideName");
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(new Date());
			endTime = DateUtils.getDataString(DateUtils.date_sdf);
		}
		StringBuffer sql = new StringBuffer("select * from (select c.goods_id goodsId,c.user_id userId,c.code,c.goods_name goodsName,c.guide_name guideName,c.brand_name brandName,")
		.append("sum(c.click_num) totalClickNum,sum(c.still_time) totalStillTime,MAX(c.click_time) maxClickTime ,MIN(c.click_time) minClickTime,c.store_id storeId")
		.append(" from t_goods_count c where c.retailer_id = '").append(retailerId).append("'");
		StringBuffer countSql = new StringBuffer("select count(1) from (select c.goods_id goodsId,c.user_id userId")
		.append(" from t_goods_count c where c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(code)){
			sql.append(" and c.code like '%").append(code).append("%'");
			countSql.append(" and c.code like '%").append(code).append("%'");
		}
		if(Utility.isNotEmpty(goodsName)){
			sql.append(" and c.goods_name like '%").append(goodsName).append("%'");
			countSql.append(" and c.goods_name like '%").append(goodsName).append("%'");
		}
		if(Utility.isNotEmpty(brandName)){
			sql.append(" and c.brand_name like '%").append(brandName).append("%'");
			countSql.append(" and c.brand_name like '%").append(brandName).append("%'");
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
		
		sql.append(" GROUP BY c.goods_id,c.user_id ) t");
		countSql.append(" GROUP BY c.goods_id,c.user_id ) t");
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
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "tGoodsTotalCountDatagrid")
	public void tGoodsTotalCountDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
			String sql = guideCountService.getGoodsTotalSql(request,dataGrid);
			List<GuideGoodsTotalCountVo> resultList =  systemService.findObjForJdbc(sql,dataGrid.getPage(),dataGrid.getRows(),GuideGoodsTotalCountVo.class);
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				String countSql = guideCountService.getGoodsTotalCountSql(request);
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
	 * 导出excel(商品)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportGoodsXls")
	public String exportGoodsXls(HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String sql = this.getQuerySql(request,dataGrid).get("sql");
		List<GuideGoodsCountVo> resultList =  systemService.findObjForJdbc(sql,dataGrid.getPage(),dataGrid.getRows(),GuideGoodsCountVo.class);
		if(Utility.isNotEmpty(resultList)){
			List<TStoreEntity> storeList = systemService.findByProperty(TStoreEntity.class, "retailerId", ResourceUtil.getRetailerId());
			for(GuideGoodsCountVo vo : resultList){
				for(TStoreEntity s : storeList){
					if(s.getId().equals(vo.getStoreId())){
						vo.setStoreName(s.getName());
						break;
					}
				}
			}
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"商品导购浏览统计");
		modelMap.put(NormalExcelConstants.CLASS,GuideGoodsCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品导购浏览统计", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		logger.info("商品导购浏览统计");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 导出excel(商品)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportGoodsTotalXls")
	public String exportGoodsTotalXls(HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String sql = guideCountService.getGoodsTotalSql(request,dataGrid);
		List<GuideGoodsTotalCountVo> resultList =  systemService.findObjForJdbc(sql,dataGrid.getPage(),dataGrid.getRows(),GuideGoodsTotalCountVo.class);
		//查询商品分类名称
		tSCategoryService.fillCategoryName(resultList);
		modelMap.put(NormalExcelConstants.FILE_NAME,"商品浏览统计汇总");
		modelMap.put(NormalExcelConstants.CLASS,GuideGoodsTotalCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品浏览统计汇总", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		logger.info("商品浏览统计汇总");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
}
