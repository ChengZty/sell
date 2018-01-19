package com.buss.store.controller;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.count.service.GuideCountServiceI;
import com.buss.sms.entity.TSmsSubAccountEntity;
import com.buss.store.entity.TAppCostInfoEntity;
import com.buss.store.entity.TStoreAccountDetailEntity;
import com.buss.store.entity.TStoreAccountInfoEntity;
import com.buss.store.service.TStoreAccountServiceI;



/**   
 * @Title: Controller
 * @Description: 客户账户信息
 * @author onlineGenerator
 * @date 2016-09-17 20:20:21
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tStoreAccountController")
public class TStoreAccountController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TStoreAccountController.class);

	@Autowired
	private SystemService systemService;
	@Autowired
	private GuideCountServiceI guideCountService;
	@Autowired
	private TStoreAccountServiceI tStoreAccountService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 余额列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tStoreAccountInfo")
	public ModelAndView tStoreAccountInfo(HttpServletRequest request) {
		return new ModelAndView("com/buss/store/tStoreAccountInfoList");
	}
	
	/**
	 * 余额信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tStoreAccountDetail")
	public ModelAndView tStoreAccountDetail(HttpServletRequest request) {
		String rid  = request.getParameter("rid");
		request.setAttribute("rid", rid);
		if("".equals(rid) || null == rid){
			rid = ResourceUtil.getRetailerId();
		}

		//查询账户信息
		TStoreAccountInfoEntity tStoreAccountInfo = systemService.findUniqueByProperty(TStoreAccountInfoEntity.class, "retailerId",rid);
		BigDecimal preCost = BigDecimal.ZERO;
		String money = "当前余额：";
		if(Utility.isNotEmpty(tStoreAccountInfo)){
			String retailerRealname = tStoreAccountInfo.getRetailerRealname();
			String retailerName =  tStoreAccountInfo.getRetailerName();
			String rname = retailerRealname+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+retailerName;
			request.setAttribute("rname", rname);
			BigDecimal remainMoney = tStoreAccountInfo.getRemainMoney();//账户余额
			String sql = "SELECT sum(account_year_cost)+sum(app_month_cost) preCost from t_app_cost_info where status ='A' and retailer_id = ? and charge_status = ?";//待扣费总费用（端口服务费+年费）
			Map<String, Object> map = this.systemService.findOneForJdbc(sql, rid,TAppCostInfoEntity.CHARGE_STATUS_0);
			if(Utility.isNotEmpty(map)&&Utility.isNotEmpty(map.get("preCost"))){
				preCost = (BigDecimal) map.get("preCost");
			}
			int r=remainMoney.subtract(preCost).compareTo(BigDecimal.ZERO); //和0，Zero比较
			if(r<0){//余额不够预计扣费
				money = money +"<span style='color: red'>"+remainMoney+"</span>元 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预计扣费：<span style='color: red'>"+preCost+"</span>元";
			}else{
				money = money +""+remainMoney+"元 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预计扣费：<span style='color: red'>"+preCost+"</span>元";
			}
		}else{
			money = money+"0.00元";
		}
		request.setAttribute("money", money);
		request.setAttribute("monthCharge", preCost);
		//查询短信账户信息 
		TSmsSubAccountEntity tSmsSubAccountEntity = systemService.findUniqueByProperty(TSmsSubAccountEntity.class, "retailerId",rid);
		Integer smsNumber = 0;
		if(tSmsSubAccountEntity == null){
			smsNumber = 0;
		}else{
			smsNumber = tSmsSubAccountEntity.getSmsNumber();
			if(smsNumber == null){
				smsNumber = 0;
			}
		}
		request.setAttribute("smsNumber", smsNumber);
		guideCountService.initFirstSearchTimeRange(request);
		return new ModelAndView("com/buss/store/tStoreAccountDetailList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TStoreAccountInfoEntity tStoreAccountInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			Map<String,String> sqlMap = this.getQuerySql(request,dataGrid);
			String sql = sqlMap.get("sql");
			String countSql = sqlMap.get("countSql");
			List<TStoreAccountInfoEntity> resultList = new ArrayList<TStoreAccountInfoEntity>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TStoreAccountInfoEntity.class);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			logger.error(e.getMessage());
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
		String retailerName = request.getParameter("retailerName");//帐号
		String retailerRealname = request.getParameter("retailerRealname");//名称
		String accountStatus = request.getParameter("accountStatus");//账户状态
		String remind = request.getParameter("remind");//提醒状态
		StringBuffer sql = new StringBuffer("select s.id,s.create_date createDate,s.retailer_id retailerId,s.retailer_name retailerName,s.retailer_realname retailerRealname,s.active_date activeDate,")
			.append("s.remain_money remainMoney,s.account_status accountStatus,s.remind,s.remark,IFNULL(a.yearCharge,0) yearCharge,IFNULL(a.monthCharge,0) monthCharge")
			.append("  from t_store_account_info s LEFT JOIN ")
			.append(" (SELECT retailer_id,SUM(account_year_cost) yearCharge,SUM(app_month_cost) monthCharge from t_app_cost_info where status = 'A' and charge_status='")
			.append(TAppCostInfoEntity.CHARGE_STATUS_0).append("' GROUP BY retailer_id) a on s.retailer_id = a.retailer_id")
			.append(" where s.status = 'A' ")
		;
		StringBuffer countSql = new StringBuffer("select count(1) from t_store_account_info s where s.status = 'A'");
		if(Utility.isNotEmpty(retailerName)){
			sql.append(" and s.retailer_Name like '%").append(retailerName).append("%'");
			countSql.append(" and s.retailer_Name like '%").append(retailerName).append("%'");
		}
		if(Utility.isNotEmpty(retailerRealname)){
			sql.append(" and s.retailer_Realname like '%").append(retailerRealname).append("%'");
			countSql.append(" and s.retailer_Realname like '%").append(retailerRealname).append("%'");
		}
		if(Utility.isNotEmpty(accountStatus)){
			sql.append(" and s.account_Status = '").append(accountStatus).append("'");
			countSql.append(" and s.account_Status = '").append(accountStatus).append("'");
		}
		if(Utility.isNotEmpty(remind)){
			sql.append(" and s.remind = '").append(remind).append("'");
			countSql.append(" and s.remind = '").append(remind).append("'");
		}
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
//			sql.append(" ORDER BY s.totalClickNum desc");
		}else{
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		map.put("sql", sql.toString());
		map.put("countSql", countSql.toString());
		return map;
	}

	//账户余额明细的初始化界面
	@RequestMapping(params = "datagridDetail")
	public void datagridDetail(TStoreAccountDetailEntity tStoreAccountDetailEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		//获取参数信息
		String  rid  = request.getParameter("rid");
		String  type  = request.getParameter("type");
		String createDate_begin = request.getParameter("createDate_begin");//支付时间
		String createDate_end = request.getParameter("createDate_end");
		String retailerId = ResourceUtil.getRetailerId();
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			createDate_begin = DateUtils.getFirstDayOfMonth(new Date());
			createDate_end = DateUtils.getDataString(DateUtils.date_sdf);
		}
		CriteriaQuery cq = new CriteriaQuery(TStoreAccountDetailEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tStoreAccountDetailEntity);
		try{
		//自定义追加查询条件
			if(Utility.isNotEmpty(type)){
				cq.eq("type", type);
			}
			if(StringUtil.isNotEmpty(createDate_begin)){
				cq.ge("createDate",DateUtil.stringToDate(createDate_begin+" 00:00:00"));
			}
			if(StringUtil.isNotEmpty(createDate_end)){
				cq.le("createDate",DateUtil.stringToDate(createDate_end+" 23:59:59"));
			}
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
			if(Utility.isNotEmpty(rid) && !"".equals(rid)){
				cq.eq("retailerId", rid);
				retailerId = rid;
			}
			//查询条件组装器  多字段排序  
			Map<String,Object> map = new LinkedHashMap<String,Object>();  
			map.put("createDate", SortDirection.desc);  
			map.put("remainMoney", SortDirection.asc); 
			cq.setOrder(map);  
			          
//			Map<String,Object> map1 = new HashMap<String,Object>();  
//			map1.put("remainMoney", "asc");  
//			cq.setOrder(map1);  
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		
		//统计时间段内的账户充值扣款信息
		TStoreAccountInfoEntity tStoreAccountInfo = systemService.findUniqueByProperty(TStoreAccountInfoEntity.class, "retailerId",retailerId);
		String countMsg = "";
		if(Utility.isNotEmpty(tStoreAccountInfo)){
			
			
			String sql = "select retailer_id,sum(operate_money) money,count(operate_money) number, type "+
			" from t_store_account_detail where status='A' "+
			"and retailer_id='"+retailerId+"' "+
			" AND create_date >='"+createDate_begin+" 00:00:00'"+
			" AND create_date <='"+createDate_end+" 23:59:59'"+
			" GROUP BY type;";
			String s1 = "充值";
			String s2 = "扣费";
			List<Map<String,Object>> list = systemService.findForJdbc(sql, null);
			
			if(list.size() < 1){
				s1 = "充值0笔 金额0.00元";
				s2 = "扣费0笔 金额0.00元";
			}else{
				for (Map<String, Object> map : list) {
					String ty = map.get("type")+"";
					if(TStoreAccountDetailEntity.TYPE_1.equals(ty)){//充值
						s1 = s1+map.get("number")+"笔 金额"+map.get("money")+"元;";
					}else if(TStoreAccountDetailEntity.TYPE_0.equals(ty)){
						s2 = s2+map.get("number")+"笔 金额"+map.get("money")+"元;";
					}
				}

				s1 = s1.length() == 2 ?"充值0笔 金额0.00元" : s1;
				s2 = s2.length() == 2 ?"扣费0笔 金额0.00元" : s2;
			}
			
			countMsg = "*本次查询时间："+createDate_begin+"~"+createDate_end+ ";"+ s1+ s2;
		}
		request.setAttribute("countMsg", countMsg);
		dataGrid.setFooter("operateMoney:"+countMsg);

		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	
	/**
	 * 余额编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdateInfo")
	public ModelAndView goUpdate(TStoreAccountInfoEntity tStoreAccountInfoEntity, HttpServletRequest req) {
		String id  = tStoreAccountInfoEntity.getId();
		String type  = req.getParameter("type");
		if (StringUtil.isNotEmpty(id)) {
			tStoreAccountInfoEntity = systemService.flushEntity(TStoreAccountInfoEntity.class, id);
			req.setAttribute("tStoreAccountInfo", tStoreAccountInfoEntity);
		}
		//通过type 确定充值还是扣费
		req.setAttribute("type", type);
		return new ModelAndView("com/buss/store/tStoreAccountInfoUpdate");
	}
	
	/**
	 * 零售商充值短信条数页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goInfoToSms")
	public ModelAndView goInfoToSms(HttpServletRequest req) {
		String retailerId = ResourceUtil.getRetailerId();
		if (StringUtil.isNotEmpty(retailerId)) {
			TStoreAccountInfoEntity tStoreAccountInfoEntity = systemService.findUniqueByProperty(TStoreAccountInfoEntity.class,"retailerId", retailerId);
			req.setAttribute("tStoreAccountInfo", tStoreAccountInfoEntity);
		}
		
		return new ModelAndView("com/buss/store/tStoreAccountToSms");
	}
	/**
	 * 更新余额界面
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doInfoUpdate")
	@ResponseBody
	public AjaxJson doInfoUpdate(TStoreAccountDetailEntity tStoreAccountDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "客户账户余额更新成功";
		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tBalance, tStoreAccountInfo);
			this.tStoreAccountService.doInfoUpdate(tStoreAccountDetail);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			message = "客户账户余额更新成功！";
		} catch (Exception e) {
			e.printStackTrace();
			message = "客户账户余额更新失败";
			logger.error(e.getMessage() + message);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	

	/**
	 * 零售商充值短信条数
	 * @return
	 */
	@RequestMapping(params = "doInfoToSms")
	@ResponseBody
	public AjaxJson doInfoToSms(TStoreAccountDetailEntity tStoreAccountDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "充值短信条数成功";
		String retailerId = tStoreAccountDetail.getRetailerId();
		TStoreAccountInfoEntity tStoreAccountInfo = systemService.findUniqueByProperty(TStoreAccountInfoEntity.class,"retailerId", retailerId);
		int rechargeNumber = Integer.valueOf(request.getParameter("smsNumber"));
		try {
			BigDecimal operateMoney = tStoreAccountDetail.getOperateMoney(); //充值金额
			int r=tStoreAccountInfo.getRemainMoney().subtract(operateMoney).compareTo(BigDecimal.ZERO); //和0，Zero比较
			if(r<0){//小于0
				//TODO 发送短信提醒
				message = "余额不足";
				j.setMsg(message);
				return j;
			}else{
				String msg = this.tStoreAccountService.doInfoToSms(tStoreAccountInfo,tStoreAccountDetail,rechargeNumber);
				if(Utility.isNotEmpty(msg)){
					message = msg;
				}
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "充值短信条数失败";
			logger.error(e.getMessage() + message);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	//根据客户名获取客户的账户信息
	@RequestMapping(params = "getTStoreAccountInfo")
	@ResponseBody
	public AjaxJson getTStoreAccountInfo( HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "查询账户信息成功";
		
		String retailerName = request.getParameter("retailerName");
		try {
			TStoreAccountInfoEntity tStoreAccountInfo = systemService.findUniqueByProperty(TStoreAccountInfoEntity.class,"retailerName", retailerName);
			if(tStoreAccountInfo == null){
				message = "账户信息不存在";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
			j.setObj(tStoreAccountInfo);
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			message = "账户信息不存在";
			j.setSuccess(false);
			logger.error(e.getMessage()+message);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	/**
	 * 导出客户账户总表的excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportInfoXls")
	public String exportInfoXls(TStoreAccountInfoEntity tStoreAccountInfoEntity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		Map<String,String> sqlMap = this.getQuerySql(request,dataGrid);
		String sql = sqlMap.get("sql");
		List<TStoreAccountInfoEntity> resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TStoreAccountInfoEntity.class);
		modelMap.put(NormalExcelConstants.FILE_NAME,"客户账户列表");
		modelMap.put(NormalExcelConstants.CLASS,TStoreAccountInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("客户账户列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出账户余额详情excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportDetailXls")
	public String exportDetailXls(TStoreAccountDetailEntity tStoreAccountDetail,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//获取参数信息
		String  rid  = request.getParameter("rid");
		String  type  = request.getParameter("type");
		String createDate_begin = request.getParameter("createDate_begin");//支付时间
		String createDate_end = request.getParameter("createDate_end");
		String retailerId = ResourceUtil.getRetailerId();
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			createDate_begin = DateUtils.getFirstDayOfMonth(new Date());
			createDate_end = DateUtils.getDataString(DateUtils.date_sdf);
		}
		CriteriaQuery cq = new CriteriaQuery(TStoreAccountDetailEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tStoreAccountDetail);
		//自定义追加查询条件
			if(Utility.isNotEmpty(type)){
				cq.eq("type", type);
			}
			if(StringUtil.isNotEmpty(createDate_begin)){
				cq.ge("createDate",DateUtil.stringToDate(createDate_begin+" 00:00:00"));
			}
			if(StringUtil.isNotEmpty(createDate_end)){
				cq.le("createDate",DateUtil.stringToDate(createDate_end+" 23:59:59"));
			}
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
			if(Utility.isNotEmpty(rid) && !"".equals(rid)){
				cq.eq("retailerId", rid);
				retailerId = rid;
			}
			cq.add();
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tStoreAccountDetail, request.getParameterMap());
		List<TStoreAccountDetailEntity> tBalances = this.systemService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"账户明细详情");
		modelMap.put(NormalExcelConstants.CLASS,TStoreAccountDetailEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("账户明细详情", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBalances);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
}
