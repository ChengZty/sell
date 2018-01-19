package com.buss.ticket.controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.buss.store.entity.TStoreEntity;
import com.buss.ticket.entity.TTicketInfoEntity;
import com.buss.ticket.service.TTicketInfoServiceI;

import common.GlobalConstants;

/**   
 * @Title: Controller
 * @Description: 优惠券
 * @author onlineGenerator
 * @date 2016-07-18 18:08:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tTicketInfoController")
public class TTicketInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TTicketInfoController.class);

	@Autowired
	private TTicketInfoServiceI tTicketInfoService;
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
	 * 平台优惠券列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/ticket/tTicketInfoList");
	}
	
	/**
	 * 零售商优惠券列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "listOfRetailer")
	public ModelAndView listOfRetailer(HttpServletRequest request) {
		request.setAttribute("retailer_Id", ResourceUtil.getRetailerId());
		return new ModelAndView("com/buss/ticket/tTicketInfoListOfRetailer");
	}
	
	/**
	 * 零售商优惠券列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "auditList")
	public ModelAndView auditList(HttpServletRequest request) {
		request.setAttribute("retailer_Id", ResourceUtil.getRetailerId());
		return new ModelAndView("com/buss/ticket/tTicketAuditList");
	}
	
	/**
	 * 优惠券列表 页面跳转
	 * @return
	 */
	/*@RequestMapping(params = "listToBeSend")
	public ModelAndView listToBeSend(HttpServletRequest request) {
		String userIds = request.getParameter("userIds");
		String names = this.getUserNamesByIds(userIds);
		request.setAttribute("userIds", userIds);
		request.setAttribute("names", names);
		return new ModelAndView("com/buss/ticket/tTicketInfoList-send");
	}	*/
	
	/**
	 * 优惠券选中零售商列表 页面跳转
	 * @return
	 */
	/*@RequestMapping(params = "retailerList")
	public ModelAndView retailerList(HttpServletRequest request) {
		String ticketId = request.getParameter("ticketId");
		String sheetRemain = request.getParameter("sheetRemain");
		request.setAttribute("ticketId", ticketId);
		request.setAttribute("sheetRemain", sheetRemain);
		return new ModelAndView("com/buss/ticket/tTicketRetailerList");
	}

	private String getUserNamesByIds(String userIds) {
		String[] ids = userIds.split(",");
		StringBuffer sql = new StringBuffer("select real_name from t_person where status = 'A' and user_type = '03' and user_id in(");
		int i = 0;
		for(String id : ids){
			sql.append("'").append(id).append("',");
			i++;
		}
		sql.deleteCharAt(sql.length()-1).append(")");
		List<Map<String,Object>> list = this.systemService.findForJdbc(sql.toString(), null);
		String names = "";
		for(Map<String,Object> map : list){
			names +=map.get("real_name")+",";
		}
		names = names.substring(0, names.length()-1)+"(共"+i+"个人)";
		return names;
	}*/

	/**
	 * 平台或者云商查询优惠券列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TTicketInfoEntity tTicketInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TTicketInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tTicketInfo, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
			String beginTime = request.getParameter("begin_Time");
			String endTime = request.getParameter("end_Time");
			if(StringUtil.isNotEmpty(beginTime)){
				cq.ge("beginTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(beginTime+" 00:00:00"));
			}
			if(StringUtil.isNotEmpty(endTime)){
				cq.le("endTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(endTime+" 23:59:59"));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tTicketInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui 零售商查询优惠券列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagridOfRetailer")
	public void datagridOfRetailer(TTicketInfoEntity tTicketInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sql = new StringBuffer("SELECT * from (SELECT i.id,i.update_date updateDate,i.batch_no batchNo,i.ticket_name ticketName,ifnull(i.least_money,'0') leastMoney,i.face_value faceValue,")
			.append(" ifnull(i.store_id,'') storeId,i.use_type useType,i.begin_time beginTime,i.end_time endTime,i.ticket_status ticketStatus,i.auditor_id auditorId,i.retailer_id retailerId,")
			.append(" i.type,i.using_range usingRange,i.remark,CASE i.retailer_type WHEN '1' THEN i.sheet_total else r.sheet END as sheetTotal,")
			.append(" CASE i.retailer_type WHEN '1' THEN i.sheet_sent else r.sheet_give END as sheetSent,CASE i.retailer_type WHEN '1' THEN i.sheet_remain else r.sheet_remain END as sheetRemain")
			.append(" from t_ticket_info i LEFT JOIN  t_ticket_retailers r on i.id = r.ticket_id ")
			.append(" where i.status = 'A' and (i.retailer_id = '").append(retailerId).append("' or r.user_id ='").append(retailerId).append("')) t where 1=1");
		StringBuffer countSql = new StringBuffer("SELECT count(1) from ( SELECT * from (select i.batch_no batchNo,i.ticket_status ticketStatus,i.begin_time beginTime,i.end_time endTime,")
		.append("CASE i.retailer_type WHEN '1' THEN i.sheet_remain else r.sheet_remain END as sheetRemain from t_ticket_info i LEFT JOIN  t_ticket_retailers r on i.id = r.ticket_id ")
			.append(" where i.status = 'A' and (i.retailer_id = '").append(retailerId).append("' or r.user_id ='").append(retailerId).append("')) tt ) t where 1=1");
		String ticket_Status = request.getParameter("ticket_Status");
		if(StringUtil.isNotEmpty(ticket_Status)){
			sql.append(" and t.ticketStatus ='").append(ticket_Status).append("'");
			sql.append(" and t.endTime >='").append(DateUtils.date2Str(DateUtils.date_sdf)).append(" 00:00:00'");
			countSql.append(" and t.ticketStatus ='").append(ticket_Status).append("'");
			countSql.append(" and t.endTime >='").append(DateUtils.date2Str(DateUtils.date_sdf)).append(" 00:00:00'");
		}
		String userIds = request.getParameter("userIds");//选了导购分配优惠券的ID，查剩余张数大于0的优惠券，并且结束时间大衣当前日期
		if(!Utility.isEmpty(userIds)){
			sql.append(" and t.sheetRemain >0 ");
			countSql.append(" and t.sheetRemain >0 ");
		}
		String batchNo = request.getParameter("batchNo");
		String beginTime = request.getParameter("begin_Time");
		String endTime = request.getParameter("end_Time");
		if(StringUtil.isNotEmpty(batchNo)){
			sql.append(" and t.batchNo like '%").append(batchNo).append("%'");
			countSql.append(" and t.batchNo like '%").append(batchNo).append("%'");
		}
		if(StringUtil.isNotEmpty(beginTime)){
			sql.append(" and t.beginTime >= '").append(beginTime).append(" 00:00:00'");
			countSql.append(" and t.beginTime >= '").append(beginTime).append(" 00:00:00'");
		}
		if(StringUtil.isNotEmpty(endTime)){
			sql.append(" and t.endTime <= '").append(endTime).append(" 23:59:59'");
			countSql.append(" and t.endTime <= '").append(endTime).append(" 23:59:59'");
		}
		String sort = dataGrid.getSort();
		if(Utility.isNotEmpty(sort)){
			sql.append(" order by t.").append(sort).append(" ").append(dataGrid.getOrder());
		}else{
			sql.append(" order by t.updateDate desc");
		}
		List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
		int total = 0;
		if(!Utility.isEmpty(resultList)){
			total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
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
	
	/**
	 * easyui 优惠券推送列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "ticketDetailDatagrid")
	public void ticketDetailDatagrid(TTicketInfoEntity tTicketInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			StringBuffer sql = new StringBuffer("SELECT u.id,u.realname guideName,COUNT(1) total,")
			.append(" SUM(CASE t.flag WHEN 'Y' THEN 1 ELSE 0 END) usedNum")
			.append(" from t_ticket_detail t LEFT JOIN t_s_user u on t.guide_id = u.id")
			.append(" where t.ticket_id = '").append(tTicketInfo.getId()).append("' and t.status='A' ")
			;
		StringBuffer countSql = new StringBuffer("SELECT COUNT(1) from (SELECT u.id")
			.append(" from t_ticket_detail t LEFT JOIN t_s_user u on t.guide_id = u.id")
			.append(" where t.ticket_id = '").append(tTicketInfo.getId()).append("' and t.status='A' ");
		String guideName = request.getParameter("guideName");
		if(StringUtil.isNotEmpty(guideName)){
			sql.append(" and u.realname like '%").append(guideName).append("%'");
			countSql.append(" and u.realname like '%").append(guideName).append("%'");
		}
		sql.append(" GROUP BY u.id");
		countSql.append(" GROUP BY u.id ) t");
		List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
		int total = 0;
		if(!Utility.isEmpty(resultList)){
			total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
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

	/**
	 * 删除优惠券
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TTicketInfoEntity tTicketInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tTicketInfo = systemService.flushEntity(TTicketInfoEntity.class, tTicketInfo.getId());
		message = "优惠券删除成功";
		try{
			if(TTicketInfoEntity.TICKET_STATUS_1.equals(tTicketInfo.getTicketStatus())){
				tTicketInfo.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				tTicketInfoService.updateEntitie(tTicketInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}else{
				message = "优惠券不是待审核状态不能删除";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 审核优惠券
	 * @return
	 */
	@RequestMapping(params = "doAudit")
	@ResponseBody
	public AjaxJson doAudit(TTicketInfoEntity tTicketInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tTicketInfo = systemService.flushEntity(TTicketInfoEntity.class, tTicketInfo.getId());
		message = "优惠券审核成功";
		try{
			if(TTicketInfoEntity.TICKET_STATUS_1.equals(tTicketInfo.getTicketStatus())){
				TSUser user  = ResourceUtil.getSessionUserName();
				tTicketInfo.setTicketStatus(TTicketInfoEntity.TICKET_STATUS_2);
				tTicketInfo.setAuditorId(user.getId());
				tTicketInfo.setAuditor(user.getRealName());
				tTicketInfoService.updateEntitie(tTicketInfo);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}else{
				message = "优惠券不是待审核状态";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量审核优惠券
	 * @return
	 */
	 @RequestMapping(params = "doBatchAudit")
	@ResponseBody
	public AjaxJson doBatchAudit(String batchNos,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "优惠券批量审核成功";
		try{
			if(Utility.isNotEmpty(batchNos)){
				Set<String> batchNoSet = new HashSet<String>();
				for(String batchNo:batchNos.split(",")){
					batchNoSet.add(batchNo);
				}
				for(String batchNo:batchNoSet){
					List<TTicketInfoEntity> list = this.systemService.findByProperty(TTicketInfoEntity.class, "batchNo", batchNo);
					for(TTicketInfoEntity tTicketInfo : list){
						if(TTicketInfoEntity.TICKET_STATUS_1.equals(tTicketInfo.getTicketStatus())){
							TSUser user  = ResourceUtil.getSessionUserName();
							tTicketInfo.setTicketStatus(TTicketInfoEntity.TICKET_STATUS_2);
							tTicketInfo.setAuditorId(user.getId());
							tTicketInfo.setAuditor(user.getRealName());
							tTicketInfoService.updateEntitie(tTicketInfo);
							systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
						}else{
							message = "优惠券["+tTicketInfo.getTicketName()+"]不是待审核状态";
							break;
						}
					}
				}
			}else{
				message = "请选择优惠券";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券批量审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	 
	 /**
	 * 停用优惠券
	 * @return
	 */
	@RequestMapping(params = "doStop")
	@ResponseBody
	public AjaxJson doStop(TTicketInfoEntity tTicketInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tTicketInfo = systemService.flushEntity(TTicketInfoEntity.class, tTicketInfo.getId());
		message = "优惠券停用成功";
		try{
			if(TTicketInfoEntity.TICKET_STATUS_2.equals(tTicketInfo.getTicketStatus())){
				TSUser user  = ResourceUtil.getSessionUserName();
				tTicketInfo.setTicketStatus(TTicketInfoEntity.TICKET_STATUS_3);
				tTicketInfo.setAuditorId(user.getId());
				tTicketInfo.setAuditor(user.getRealName());
				tTicketInfoService.updateEntitie(tTicketInfo);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}else{
				message = "优惠券不是已审核状态";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券停用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
		
		/**
		 * 批量停用优惠券
		 * @return
		 */
		 @RequestMapping(params = "doBatchStop")
		@ResponseBody
		public AjaxJson doBatchStop(String batchNos,HttpServletRequest request){
			AjaxJson j = new AjaxJson();
			message = "优惠券批量销毁成功";
			try{
				if(Utility.isNotEmpty(batchNos)){
					Set<String> batchNoSet = new HashSet<String>();
					for(String batchNo:batchNos.split(",")){
						batchNoSet.add(batchNo);
					}
					for(String batchNo:batchNoSet){
						List<TTicketInfoEntity> list = this.systemService.findByProperty(TTicketInfoEntity.class, "batchNo", batchNo);
						for(TTicketInfoEntity tTicketInfo : list){
							if(TTicketInfoEntity.TICKET_STATUS_2.equals(tTicketInfo.getTicketStatus())){
								TSUser user  = ResourceUtil.getSessionUserName();
								tTicketInfo.setTicketStatus(TTicketInfoEntity.TICKET_STATUS_3);
								tTicketInfo.setAuditorId(user.getId());
								tTicketInfo.setAuditor(user.getRealName());
								tTicketInfoService.updateEntitie(tTicketInfo);
								systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
							}else{
								message = "["+tTicketInfo.getTicketName()+"]的优惠券不是已审核状态";
								break;
							}
						}
					}
				}else{
					message = "请选择优惠券";
				}
			}catch(Exception e){
				e.printStackTrace();
				message = "优惠券批量销毁失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
	
		/**
		 * 批量分配云商优惠券给多个零售商
		 * @return
		 */
		/* @RequestMapping(params = "doBatchDistribute")
		@ResponseBody
		public AjaxJson doBatchDistribute(HttpServletRequest request){
			AjaxJson j = new AjaxJson();
			try{
				message = this.tTicketInfoService.doBatchDistribute(request);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
				message = "优惠券批量分配失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}	*/ 
	 
	/**
	 * 批量删除优惠券
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String batchNos,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "优惠券删除成功";
		try{
			for(String batchNo:batchNos.split(",")){
				List<TTicketInfoEntity> list = this.systemService.findByProperty(TTicketInfoEntity.class, "batchNo", batchNo);
					for(TTicketInfoEntity tTicketInfo : list){
					if(TTicketInfoEntity.TICKET_STATUS_1.equals(tTicketInfo.getTicketStatus())){
						tTicketInfo.setStatus(common.GlobalConstants.STATUS_INACTIVE);
						tTicketInfoService.updateEntitie(tTicketInfo);
						systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
					}else{
						message = "优惠券["+tTicketInfo.getTicketName()+"]不是待审核状态不能删除";
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加优惠券
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TTicketInfoEntity tTicketInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "优惠券添加成功";
		try{
			this.tTicketInfoService.doAdd(tTicketInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新优惠券
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TTicketInfoEntity tTicketInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "优惠券更新成功";
		TTicketInfoEntity t = tTicketInfoService.get(TTicketInfoEntity.class, tTicketInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tTicketInfo, t);
			if(TTicketInfoEntity.TICKET_STATUS_1.equals(t.getTicketStatus())){
				String batchNo = DateUtils.getDataString(new SimpleDateFormat("yyyyMMddssmmss"));
				t.setBatchNo(batchNo);
				t.setSheetRemain(tTicketInfo.getSheetTotal());
				tTicketInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}else{
				message = "优惠券不是待审核状态不能更新";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "优惠券更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 优惠券新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TTicketInfoEntity tTicketInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tTicketInfo.getId())) {
			tTicketInfo = tTicketInfoService.flushEntity(TTicketInfoEntity.class, tTicketInfo.getId());
			req.setAttribute("tTicketInfoPage", tTicketInfo);
		}
		req.setAttribute("retailerId", ResourceUtil.getRetailerId());
		req.setAttribute("retailerType", ResourceUtil.getRetailerType());
		return new ModelAndView("com/buss/ticket/tTicketInfo-add");
	}
	
	/**
	 * 优惠券新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd2")
	public ModelAndView goAdd2(TTicketInfoEntity tTicketInfo, HttpServletRequest req) {
		tTicketInfo.setId(Utility.getUUID());
		tTicketInfo.setPicUrl(TTicketInfoEntity.DEFAULT_PIC);
		req.setAttribute("tTicketInfoPage", tTicketInfo);
		req.setAttribute("retailerId", ResourceUtil.getRetailerId());
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return new ModelAndView("com/buss/ticket/tTicketInfo-add2");
	}
	
	/**
	 * 优惠券 使用详情页面 跳转
	 * @return
	 */
	@RequestMapping(params = "viewDetail")
	public ModelAndView viewDetail(String id, HttpServletRequest req) {
		req.setAttribute("id", id);
		return new ModelAndView("com/buss/ticket/tTicketDetailList");
	}
	
	
	/**
	 * 优惠券编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TTicketInfoEntity tTicketInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tTicketInfo.getId())) {
			tTicketInfo = tTicketInfoService.flushEntity(TTicketInfoEntity.class, tTicketInfo.getId());
			req.setAttribute("tTicketInfoPage", tTicketInfo);
		}
		if("detail".equals(req.getParameter("load"))){
			req.setAttribute("isView", 1);
		}
		return new ModelAndView("com/buss/ticket/tTicketInfo-update");
	}
	
	/**
	 * 优惠券编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate2")
	public ModelAndView goUpdate2(TTicketInfoEntity tTicketInfo, HttpServletRequest req) {
		String isView = "0";//非查看页面
		if("detail".equals(req.getParameter("load"))){
			isView = "1";//是查看页面
			if(Utility.isNotEmpty(tTicketInfo.getBatchNo())){
				List<TTicketInfoEntity> list = this.systemService.findByProperty(TTicketInfoEntity.class, "batchNo", tTicketInfo.getBatchNo());
				if(list.size()>0){
					tTicketInfo = list.get(0);
					//获取所有店铺名称
					String storeNames = this.getAllTicketStoreNames(list);
					req.setAttribute("storeName", storeNames);
				}
			}
		}else{
			if (StringUtil.isNotEmpty(tTicketInfo.getId())) {
				tTicketInfo = tTicketInfoService.flushEntity(TTicketInfoEntity.class, tTicketInfo.getId());
				if(Utility.isNotEmpty(tTicketInfo.getStoreId())){
					TStoreEntity store = this.tTicketInfoService.get(TStoreEntity.class, tTicketInfo.getStoreId());
					req.setAttribute("storeName", store.getName());
				}
			}
		}
		Long goodsNum=  0L;
		Long brandsNum=  0L;
		if(!TTicketInfoEntity.USING_RANGE_1.equals(tTicketInfo.getUsingRange())){//不是全馆的
			Map<String, Object> map = this.tTicketInfoService.findOneForJdbc("SELECT (SELECT COUNT(1) from t_ticket_goods WHERE ticket_id=? and status='A' and type='1') goodsNum,(SELECT COUNT(1) from t_ticket_goods WHERE ticket_id=? and status='A' and type='2') brandsNum", tTicketInfo.getId(),tTicketInfo.getId());
			goodsNum = (Long) map.get("goodsNum");
			brandsNum = (Long) map.get("brandsNum");
		}
		req.setAttribute("goodsNum", goodsNum);
		req.setAttribute("brandsNum", brandsNum);
		req.setAttribute("tTicketInfoPage", tTicketInfo);
		req.setAttribute("isView", isView);
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return new ModelAndView("com/buss/ticket/tTicketInfo-update2");
	}
	
	/**获取店铺名称列表，用逗号拼接*/
	private String getAllTicketStoreNames(List<TTicketInfoEntity> list) {
		String storeIds = "";
		for(TTicketInfoEntity entity : list){
			if(Utility.isNotEmpty(entity.getStoreId())){
				storeIds+=",'"+entity.getStoreId()+"'";
			}
		}
		if(storeIds.length()>0){
			String sql = "select name from t_store where id in ("+storeIds.substring(1)+")";
			List<Map<String, Object>> nameList= this.systemService.findForJdbc(sql, null);
			if(nameList.size()>0){
				String storeNames = "";
				for(Map<String, Object> map : nameList){
					storeNames+=","+map.get("name")+"";
				}
				if(storeNames.length()>0){
					return storeNames.substring(1);
				}
			}
		}
		return null;
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tTicketInfoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TTicketInfoEntity tTicketInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TTicketInfoEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tTicketInfo, request.getParameterMap());
		List<TTicketInfoEntity> tTicketInfos = this.tTicketInfoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"优惠券");
		modelMap.put(NormalExcelConstants.CLASS,TTicketInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("优惠券列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tTicketInfos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TTicketInfoEntity tTicketInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"优惠券");
    	modelMap.put(NormalExcelConstants.CLASS,TTicketInfoEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("优惠券列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TTicketInfoEntity> listTTicketInfoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TTicketInfoEntity.class,params);
				for (TTicketInfoEntity tTicketInfo : listTTicketInfoEntitys) {
					tTicketInfoService.save(tTicketInfo);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	
}
