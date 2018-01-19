package com.buss.user.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.count.vo.GuideGoodsCountVo;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.shop.entity.TPosterEntity;
import com.buss.sms.entity.TSmsAutographEntity;
import com.buss.sms.entity.TSmsSendInfoEntity;
import com.buss.sms.entity.TSmsSendTemplateEntity;
import com.buss.sms.entity.TSmsSubAccountEntity;
import com.buss.sms.service.TSmsAutographServiceI;
import com.buss.sms.service.TSmsSendInfoServiceI;
import com.buss.store.entity.TStoreEntity;
import com.buss.store.service.TStoreServiceI;
import com.buss.user.entity.TSContactEntity;
import com.buss.user.entity.TVipLevelEntity;
import com.buss.user.vo.GuideContactCountVo;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 导购回访的后台显示
 * @author onlineGenerator
 * @date 2017-02-18 12:15:41
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSContactController")
public class TSContactController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSContactController.class);
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
	 * 导购回访 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "allList")
	public ModelAndView allList(HttpServletRequest request) {
		String storeStr = "";
		
		List<CommonVo> storeList = tStoreService.getStoreList();
		for (CommonVo commonVo : storeList) {
			storeStr = storeStr + commonVo.getName() + "_" + commonVo.getId() + ",";
		}
		/*if(storeStr.length() > 1){
			storeStr = storeStr.substring(0, storeStr.length()-1);
		}*/
		storeStr += " _null";
		request.setAttribute("stores", storeStr);
		
		return new ModelAndView("com/buss/user/tSContactList");
	}
	/**
	 * t_s_contact 列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "goContactList")
	public ModelAndView goContactList(HttpServletRequest request) {
		String sendInfoId = request.getParameter("guideId");
		List<TSContactEntity> tSContacts = this.systemService.findByProperty(TSContactEntity.class, "userId", sendInfoId)  ;
		TSContactEntity tSContact = new TSContactEntity();
		if(tSContacts.size() > 0){
			tSContact = tSContacts.get(0);
		}else{
			tSContact.setId(sendInfoId);
			tSContact.setUserId(sendInfoId);
		}
		
		
		request.setAttribute("tSContact", tSContact);
		return new ModelAndView("com/buss/user/tSContactInfoList");
	}
	
	/**
	 * easyui AJAX请求数据 零售商查询短信列表数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TSmsSendInfoEntity tSmsSendInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			Map<String,String> sqlMap = getSqlMap(request);
			
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : resultList) {
				if("null".equals(map.get("storeId"))){
					map.put("storeId", null);
				}
			}
			//查询数据的条数
			int total = this.systemService.getCountForJdbc(sqlMap.get("countSql").toString()).intValue();
			if(total>0){
				//根据条数分页查询数据
				String sql = sqlMap.get("sql").toString();
				String sort = dataGrid.getSort();
				if(StringUtil.isEmpty(sort)){
					sql += " order by number desc";
				}else{
					sql += " order by "+sort+" desc";
				}
				resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
			}
			
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	//封装查询的sql语句
	private Map<String,String> getSqlMap(HttpServletRequest request){
		//自定义追加查询条件
		String retailerId = ResourceUtil.getRetailerId();
		String guideName = request.getParameter("guideName");
		String concatTime_begin = request.getParameter("concatTime_begin");
		String concatTime_end = request.getParameter("concatTime_end");
		String store_id = request.getParameter("storeId");
		
		//取到查询条件的字符串
		String sqlRetailerId = " and retailer_id= '"+ retailerId +"' ";
		String sqlToRetailerId = " and to_retailer_id= '"+ retailerId +"' ";
		String sqlGuideName = " and real_name like '%"+ guideName +"%' ";
		String sqlBegin = " and concat_time >= '"+ concatTime_begin +" 00:00:00' ";
		String sqlEnd = " and concat_time <= '"+ concatTime_end +" 23:59:59' ";
		String sqlstoreId = " and store_id= '"+ store_id +"' ";
		
		StringBuffer countSql = new StringBuffer("select count(1) from t_person where status = 'A' and user_type='03' ");
		
		StringBuffer sql = new StringBuffer("select guideId id, storeId, storeName, guideName, IFNULL(qqNumber,0) qqNumber, ");
		sql.append("IFNULL(weChartNumber,0) weChartNumber, IFNULL(msgNumber,0) msgNumber, ");
		sql.append("IFNULL(phoneNumber,0) phoneNumber, IFNULL(qqCount,0) qqCount, ");
		sql.append("IFNULL(weChartCount,0) weChartCount, IFNULL(msgCount,0) msgCount,");
		sql.append("IFNULL(phoneCount,0) phoneCount,IFNULL(userCount,0) userCount, ");
		sql.append("( IFNULL(qqNumber,0) + IFNULL(weChartNumber,0) + IFNULL(msgNumber,0) + IFNULL(phoneNumber,0) ) number ");
		sql.append("from ( ");
		sql.append("SELECT user_id guideId, store_id storeId,ts.name storeName, real_name guideName ");
		sql.append("FROM t_person p left join t_store ts on p.store_id = ts.id "); 
		sql.append(" WHERE p.STATUS = 'A' AND p.user_type = '03' "); 
		if(!Utility.isEmpty(retailerId)){
			sql.append(sqlToRetailerId);
			countSql.append(sqlToRetailerId);
		}
		if(!Utility.isEmpty(store_id)){
			sql.append(sqlstoreId);
			countSql.append(sqlstoreId);
		}
		if(!Utility.isEmpty(guideName)){
			sql.append(sqlGuideName);
			countSql.append(sqlGuideName);
		}
		sql.append(") tp left join ( ");
		sql.append("SELECT user_id,count(customerId) userCount, ");
		sql.append("sum(qqN) qqNumber,sum(weChartN) weChartNumber, ");
		sql.append("count(CASE WHEN qqN > 0 THEN 1 END) qqCount, ");
		sql.append("sum(msgN) msgNumber,sum(phoneN) phoneNumber, ");
		sql.append("count(CASE WHEN weChartN > 0 THEN 1 END) weChartCount, ");
		sql.append("count(CASE WHEN msgN > 0 THEN 1 END) msgCount, ");
		sql.append("count(CASE WHEN phoneN > 0 THEN 1 END) phoneCount ");
		sql.append("FROM ( SELECT user_id , customer_id customerId, ");
		sql.append("count(CASE type WHEN 'QQ' THEN 1 END) qqN, ");
		sql.append("count(CASE type WHEN 'weChart' THEN 1 END ) weChartN, ");
		sql.append("count(CASE type WHEN 'msg' THEN 1 END) msgN, ");
		sql.append("count( CASE type WHEN 'phone' THEN 1 END ) phoneN ");
		sql.append("FROM t_s_contact WHERE STATUS = 'A' ");
		if(!Utility.isEmpty(retailerId)){
			sql.append(sqlRetailerId);
		}
		Date date = new Date();
		String dateStr = DateUtil.dateToString(date,"yyyy-MM-dd");
		if(!Utility.isEmpty(concatTime_begin)){
			sql.append(sqlBegin);
		}else{
			sql.append("and concat_time >= '").append(dateStr).append(" 00:00:00' ");
		}
		if(!Utility.isEmpty(concatTime_end)){
			sql.append(sqlEnd);
		}else{
			sql.append("and concat_time <= '").append(dateStr).append(" 23:59:59' ");
		}
		sql.append("GROUP BY user_id, customer_id");
		sql.append(") us GROUP BY user_id ");
		sql.append(") tsc on tsc.user_id = tp.guideId");
		
		Map<String,String> sqlMap = new HashMap<String, String>();
		sqlMap.put("sql", sql.toString());
		sqlMap.put("countSql", countSql.toString());
		
		return sqlMap;
	}
	
	@RequestMapping(params = "tSContactList")
	public void tSContactList(TSContactEntity tSContact,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		try{
			//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			String guideId = request.getParameter("guideId");
			String userId = request.getParameter("user_id");
			String guideName = request.getParameter("guide_name");
			String customerName = request.getParameter("customer.name");
			String type = request.getParameter("type");
			String concatTime_begin = request.getParameter("concat_time_begin");
			String concatTime_end = request.getParameter("concat_time_end");
			
			//取到查询条件的字符串
			String sqlGuideId = "and user_id= '"+ guideId +"' ";
			String sqlRetailerId = "and retailer_id= '"+ retailerId +"' ";
			String sqlGuideName = "and guide_name like '%"+ guideName +"%' ";
			String sqlCustomerName = "and customer_name like '%"+ customerName +"%' ";
			String sqlType = "and type like '%"+ type +"%' ";
			String sqlBegin = "and concat_time >= '"+ concatTime_begin +" 00:00:00' ";
			String sqlEnd = "and concat_time <= '"+ concatTime_end +" 23:59:59' ";
			
			StringBuffer countSql = new StringBuffer("select count(1) from t_s_contact where status = 'A' ");
			StringBuffer sql = new StringBuffer("select * from t_s_contact where status = 'A' ");
			if(!Utility.isEmpty(guideId)){
				sql.append(sqlGuideId);
				countSql.append(sqlGuideId);
			}else if(!Utility.isEmpty(userId)){
					sql.append(sqlGuideId);
					countSql.append(sqlGuideId);
				}
			if(!Utility.isEmpty(retailerId)){
				sql.append(sqlRetailerId);
				countSql.append(sqlRetailerId);
			}
			if(!Utility.isEmpty(type)){
				sql.append(sqlType);
				countSql.append(sqlType);
			}
			if(!Utility.isEmpty(guideName)){
				sql.append(sqlGuideName);
				countSql.append(sqlGuideName);
			}
			if(!Utility.isEmpty(customerName)){
				sql.append(sqlCustomerName);
				countSql.append(sqlCustomerName);
			}
//			Date date = new Date();
//			String dateStr = DateUtil.dateToString(date,"yyyy-MM-dd");
			if(!Utility.isEmpty(concatTime_begin)){
				sql.append(sqlBegin);
				countSql.append(sqlBegin);
			}/*else{
				sql.append("and concat_time >= '").append(dateStr).append(" 00:00:00' ");
				countSql.append("and concat_time >= '").append(dateStr).append(" 00:00:00' ");
			}*/
			if(!Utility.isEmpty(concatTime_end)){
				sql.append(sqlEnd);
				countSql.append(sqlEnd);
			}/*else{
				sql.append("and concat_time <= '").append(dateStr).append(" 23:59:59' ");
				countSql.append("and concat_time <= '").append(dateStr).append(" 23:59:59' ");
			}*/
			sql.append(" order by concat_time desc ");
		
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			
			//查询数据的条数
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				//根据条数分页查询
				resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
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
	public String exportGoodsXls(HttpServletRequest request,HttpServletResponse response ,DataGrid dataGrid,ModelMap modelMap) {
		
		Map<String,String> sqlMap = getSqlMap(request);

		String concatTime_begin = request.getParameter("concatTime_begin");
		String concatTime_end = request.getParameter("concatTime_end");
		Date date = new Date();
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String dateStr = DateUtil.dateToString(date,"yyyy-MM-dd");
		if(Utility.isEmpty(concatTime_end)){
			concatTime_begin = concatTime_end = dateStr;
		}
		

		List<GuideContactCountVo> resultList =  systemService.findObjForJdbc(sqlMap.get("sql"),dataGrid.getPage(),dataGrid.getRows(),GuideContactCountVo.class);
		
		modelMap.put(NormalExcelConstants.FILE_NAME,"导购回访统计");
		modelMap.put(NormalExcelConstants.CLASS,GuideContactCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("导购回访统计", "统计时间:" 
				+ concatTime_begin + " ~ " + concatTime_end + "                " 
				+ "导出时间:" + timeFormat.format(date) + "                "
				+ "导出人:"+ResourceUtil.getSessionUserName().getRealName()+ "    ", "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		logger.info("导购回访统计");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
}
