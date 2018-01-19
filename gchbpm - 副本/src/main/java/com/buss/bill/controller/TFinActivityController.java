package com.buss.bill.controller;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.bill.entity.TFinActivityEntity;
import com.buss.bill.service.TFinActivityServiceI;
import com.buss.goods.entity.TGoodsActEntity;
import com.buss.news.entity.TNewsEntity;



/**   
 * @Title: Controller
 * @Description: 激励活动
 * @author onlineGenerator
 * @date 2016-11-25 21:48:57
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tFinActivityController")
public class TFinActivityController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TFinActivityController.class);

	@Autowired
	private TFinActivityServiceI tFinActivityService;
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
	 * 激励活动列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_01.equals(user.getUserType())){//后台
			return new ModelAndView("com/buss/bill/tFinActivityList");
		}else{
			return new ModelAndView("com/buss/bill/tFinActivityOfRetailerList");
		}
	}

	/**
	 * 导购活动审核列表Tab 页面跳转
	 * @return
	 */
	@RequestMapping(params = "auditListTab")
	public ModelAndView auditListTab(HttpServletRequest request) {
		return new ModelAndView("com/buss/bill/tFinActivityAuditListTab");
	}
	
	/**
	 * 激励活动审核列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "auditList")
	public ModelAndView auditList(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		String platformType = "2";;//默认为零售商
		if(user.getUserType().equals(common.GlobalConstants.USER_TYPE_01)){
			platformType = "1";//平台
			request.setAttribute("platformType", platformType);
			return new ModelAndView("com/buss/bill/tFinActivityAuditAllList");
		}
		request.setAttribute("platformType", platformType);
		return new ModelAndView("com/buss/bill/tFinActivityAuditList");
	}
	/**
	 * 激励活动审核列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "invalidList")
	public ModelAndView invalidList(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		String platformType = "2";;//默认为零售商
		if(user.getUserType().equals(common.GlobalConstants.USER_TYPE_01)){
			platformType = "1";//平台
			request.setAttribute("platformType", platformType);
			request.setAttribute("actType", 5);
			return new ModelAndView("com/buss/bill/tFinActivityAuditAllList");
		}
		request.setAttribute("platformType", platformType);
		request.setAttribute("actType", 5);
		return new ModelAndView("com/buss/bill/tFinActivityAuditList");
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
	public void datagrid(TFinActivityEntity tFinActivity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			Map<String,String> sqlMap = this.getSqlMap(request,dataGrid);
			String sql = sqlMap.get("sql");
			String sqlCount = sqlMap.get("sqlCount");
			List<TFinActivityEntity> resultList = new ArrayList<TFinActivityEntity>();
			int total = this.systemService.getCountForJdbc(sqlCount.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TFinActivityEntity.class);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	private Map<String, String> getSqlMap(HttpServletRequest request, DataGrid dataGrid) {
		Map<String,String> map = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String activityType = request.getParameter("activityType");//活动类别
		String actType = request.getParameter("actType");//作废活动
//		String activityStatus = request.getParameter("activityStatus");//活动状态
		String actStatus = request.getParameter("actStatus");//活动状态
		StringBuffer sql = new StringBuffer("SELECT a.id,a.create_date createDate,a.update_date updateDate,a.activity_name activityName,a.activity_type activityType,a.brand_name brandName,a.start_time startTime,a.end_time endTime,")
			.append("a.platform_type platformType,a.retailer_id retailerId,a.retailer_name retailerName,a.auditor,a.audit_time auditTime,a.activity_status activityStatus, ");
		sql.append("CASE WHEN a.activity_status ='").append(TFinActivityEntity.ACTIVITY_STATUS_1).append("' THEN '").append(TFinActivityEntity.ACT_STATUS_1).append("' ")//待审核
			.append("WHEN a.activity_status ='").append(TFinActivityEntity.ACT_STATUS_5).append("' THEN '").append(TFinActivityEntity.ACT_STATUS_5).append("' ")//已作废
			.append("WHEN (a.activity_status = '").append(TFinActivityEntity.ACTIVITY_STATUS_3).append("' OR (NOW() > a.start_time and NOW() > a.end_time AND a.activity_status = '").append(TFinActivityEntity.ACTIVITY_STATUS_2).append("')) THEN '").append(TFinActivityEntity.ACT_STATUS_4).append("' ")//已结束(包括下架)
			.append("WHEN NOW() < a.start_time AND NOW() < a.end_time AND a.activity_status = '").append(TFinActivityEntity.ACTIVITY_STATUS_2).append("' THEN '").append(TFinActivityEntity.ACT_STATUS_2).append("' ")//待开始
			.append("WHEN NOW() BETWEEN a.start_time AND a.end_time AND a.activity_status = '").append(TFinActivityEntity.ACTIVITY_STATUS_2).append("' THEN '").append(TFinActivityEntity.ACT_STATUS_3).append("' ELSE '").append(TFinActivityEntity.ACT_STATUS_4).append("' END actStatus ")//进行中
			.append(" FROM t_fin_activity a WHERE a.`status` = 'A' ");
		StringBuffer sqlCount = new StringBuffer("select count(1) FROM t_fin_activity a WHERE a.`status` = 'A' ");
		if((TFinActivityEntity.ACT_STATUS_5+"").equals(actType) ){  //查询已作废导购活动
			sql.append("AND a.activity_status = '5' ");
			sqlCount.append("AND a.activity_status = '5' ");
		}else{//查询非已作废导购活动
			sql.append("AND a.activity_status <> '5' ");
			sqlCount.append("AND a.activity_status <> '5' ");
		}
		if(Utility.isNotEmpty(retailerId)){
			sql.append("AND a.retailer_id = '").append(retailerId).append("'");
			sqlCount.append("AND a.retailer_id = '").append(retailerId).append("'");
		}
		if(Utility.isNotEmpty(activityType)){
			sql.append(" and a.activity_type = '").append(activityType).append("'");
			sqlCount.append(" and a.activity_type = '").append(activityType).append("'");
		}
//		if(Utility.isNotEmpty(activityStatus)){
//			sql.append(" and a.activity_status = '").append(activityStatus).append("'");
//			sqlCount.append(" and a.activity_status = '").append(activityStatus).append("'");
//		}
		if(Utility.isNotEmpty(actStatus)){
			sql.append(getActStatusCondition(actStatus));
			sqlCount.append(getActStatusCondition(actStatus));
		}
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
			sql.append(" ORDER BY a.update_date desc");
		}else{
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		map.put("sql", sql.toString());
		map.put("sqlCount", sqlCount.toString());
		return map;
	}

	/**获取活动状态查询对应的条件*/
	private String getActStatusCondition(String actStatus) {
		int sts = Integer.valueOf(actStatus);
		String condition = null;
			switch (sts) {
			case TGoodsActEntity.ACT_STATUS_1:
				condition = " AND a.activity_status ='"+TFinActivityEntity.ACTIVITY_STATUS_1+"'";
				break;
			case TGoodsActEntity.ACT_STATUS_2:
				condition = " AND NOW() < a.start_time AND NOW() < a.end_time  AND a.activity_status = '"+TFinActivityEntity.ACTIVITY_STATUS_2+"'";
				break;
			case TGoodsActEntity.ACT_STATUS_3:
				condition = " AND NOW() BETWEEN a.start_time AND a.end_time AND a.activity_status = '"+TFinActivityEntity.ACTIVITY_STATUS_2+"'";
				break;
			case TGoodsActEntity.ACT_STATUS_4:
				condition = " AND (a.activity_status = '"+TFinActivityEntity.ACTIVITY_STATUS_3
					+"' OR (NOW() > a.start_time AND NOW() > a.end_time AND a.activity_status = '"+TFinActivityEntity.ACTIVITY_STATUS_2+"'))";
				break;
			default:
				break;
			}
			return condition;
	}
	
	/**
	 * 删除激励活动
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TFinActivityEntity tFinActivity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tFinActivity = systemService.flushEntity(TFinActivityEntity.class, tFinActivity.getId());
		message = "活动删除成功";
		try{
			tFinActivity.setStatus("I");
			tFinActivityService.updateEntitie(tFinActivity);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除激励活动
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "激励活动删除成功";
		try{
			for(String id:ids.split(",")){
				TFinActivityEntity tFinActivity = systemService.flushEntity(TFinActivityEntity.class, 
				id
				);
				tFinActivityService.delete(tFinActivity);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "激励活动删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加激励活动(导购激励活动)
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TFinActivityEntity tFinActivity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "激励活动添加成功";
		try{
			//如果类型为品牌或者全馆商品，则把对应的销售中的商品查询出来并保存到明细表
			String msg = tFinActivityService.saveFinActivity(tFinActivity);
			if(Utility.isNotEmpty(msg)){
				message = msg;
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "激励活动添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新激励活动
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TFinActivityEntity tFinActivity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "激励活动更新成功";
		TFinActivityEntity t = tFinActivityService.get(TFinActivityEntity.class, tFinActivity.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tFinActivity, t);
			tFinActivityService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "激励活动更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 激励活动新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TFinActivityEntity tFinActivity, HttpServletRequest req) {
		String platformType = req.getParameter("platformType");
		if("1".equals(platformType)){
			return new ModelAndView("com/buss/bill/tFinActivity-add2");
		}
		req.setAttribute("retailerId", ResourceUtil.getRetailerId());
		return new ModelAndView("com/buss/bill/tFinActivity-add");
	}
	/**
	 * 激励活动编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TFinActivityEntity tFinActivity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tFinActivity.getId())) {
			tFinActivity = tFinActivityService.flushEntity(TFinActivityEntity.class, tFinActivity.getId());
			if(Utility.isNotEmpty(tFinActivity.getNewsId())){
				TNewsEntity news = this.systemService.get(TNewsEntity.class, tFinActivity.getNewsId());
				if(Utility.isNotEmpty(news)){//话题不为空设置封面图标题等
					tFinActivity.setCoverPic(news.getCoverPic());
					tFinActivity.setNewsTitle(news.getTitle());
				}
			}
			req.setAttribute("tFinActivityPage", tFinActivity);
		}
		return new ModelAndView("com/buss/bill/tFinActivity-update");
	}
	/**
	 * 激励活动查看页面跳转
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TFinActivityEntity tFinActivity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tFinActivity.getId())) {
			tFinActivity = tFinActivityService.flushEntity(TFinActivityEntity.class, tFinActivity.getId());
			if(Utility.isNotEmpty(tFinActivity.getNewsId())){
				TNewsEntity news = this.systemService.get(TNewsEntity.class, tFinActivity.getNewsId());
				if(Utility.isNotEmpty(news)){
					tFinActivity.setCoverPic(news.getCoverPic());
					tFinActivity.setNewsTitle(news.getTitle());
				}
			}
			req.setAttribute("tFinActivityPage", tFinActivity);
		}
		return new ModelAndView("com/buss/bill/tFinActivity-view");
	}
	
	 /**
	  * 审核活动
	  * @return
	  */
	 @RequestMapping(params = "doAudit")
	 @ResponseBody
	 public AjaxJson doAudit(String id ,HttpServletRequest request){
		 AjaxJson j = new AjaxJson();
		 message = "活动审核成功";
		 try{
			 TFinActivityEntity tFinActivity = systemService.flushEntity(TFinActivityEntity.class, id);
			 if(TFinActivityEntity.ACTIVITY_STATUS_1.equals(tFinActivity.getActivityStatus())){
					if(TFinActivityEntity.ACTIVITYTYPE_1.equals(tFinActivity.getActivityType())){//单品
						Long count = this.systemService.getCountForJdbc(" select count(1) from t_fin_activity_goods where status = 'A' and fin_Act_Id = '"+tFinActivity.getId()+"'");
						if(count==0){
							message = "还没有选择商品，请检查";
							j.setMsg(message);
							return j;
						}
					}else if(TFinActivityEntity.ACTIVITYTYPE_2.equals(tFinActivity.getActivityType())
							&&Utility.isEmpty(tFinActivity.getBrandId())){
						message = "还没有选择品牌，请检查";
						j.setMsg(message);
						return j;
					}
					TSUser user  = ResourceUtil.getSessionUserName();
					tFinActivity.setActivityStatus(TFinActivityEntity.ACTIVITY_STATUS_2);
					tFinActivity.setAuditId(user.getId());
					tFinActivity.setAuditor(user.getRealName());
					tFinActivity.setAuditTime(Utility.getCurrentTimestamp());
					tFinActivityService.updateEntitie(tFinActivity);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
				}else{
					message = "活动不是待审核状态";
				}
//			 TSUser user = ResourceUtil.getSessionUserName();
//				StringBuffer sql = new StringBuffer("update t_fin_activity set update_by = '").append(user.getUserName()).append("',update_name = '")
//					.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',audit_id = '")
//					.append(user.getId()).append("',auditor = '").append(user.getRealName()).append("',audit_time = '").append(Utility.getCurrentTimestamp())
//					.append("',activity_status = '2'  where id ='"+id+"'");
//			 systemService.updateBySqlString(sql.toString());
//			 systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "活动审核失败";
			 throw new BusinessException(e.getMessage());
		 }
		 j.setMsg(message);
		 return j;
	 }
	 
	 /**
	  * 下架活动
	  * @return
	  */
	 @RequestMapping(params = "doDown")
	 @ResponseBody
	 public AjaxJson doDown(String id ,HttpServletRequest request){
		 AjaxJson j = new AjaxJson();
		 message = "下架活动成功";
		 try{
			 TSUser user = ResourceUtil.getSessionUserName();
			 StringBuffer sql = new StringBuffer("update t_fin_activity set update_by = '").append(user.getUserName()).append("',update_name = '")
						.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',activity_status = '3'  where id ='"+id+"'");
			 systemService.updateBySqlString(sql.toString());
			 systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "下架活动失败";
			 throw new BusinessException(e.getMessage());
		 }
		 j.setMsg(message);
		 return j;
	 }
	
	 /**
	  * 导购活动作废
	  * @return
	  */
	 @RequestMapping(params = "doInvalid")
	 @ResponseBody
	 public AjaxJson doInvalid(String id ,HttpServletRequest request){
		 AjaxJson j = new AjaxJson();
		 message = "导购活动作废成功";
		 try{
			 TSUser user = ResourceUtil.getSessionUserName();
			 StringBuffer sql = new StringBuffer("update t_fin_activity set update_by = '").append(user.getUserName()).append("',update_name = '")
				.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp())
				.append("',activity_status = '"+TFinActivityEntity.ACT_STATUS_5+"'  where id ='"+id+"'");
			 systemService.updateBySqlString(sql.toString());
			 systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "导购活动作废失败";
			 throw new BusinessException(e.getMessage());
		 }
		 j.setMsg(message);
		 return j;
	 }
	 
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tFinActivityController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TFinActivityEntity tFinActivity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFinActivityEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinActivity, request.getParameterMap());
		List<TFinActivityEntity> tFinActivitys = this.tFinActivityService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"激励活动");
		modelMap.put(NormalExcelConstants.CLASS,TFinActivityEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("激励活动列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tFinActivitys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TFinActivityEntity tFinActivity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "激励活动");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TFinActivityEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
}
