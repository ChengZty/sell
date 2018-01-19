package com.buss.recharge.controller;
import com.buss.recharge.entity.TRechargeEntity;
import com.buss.recharge.service.TRechargeServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 充值
 * @author onlineGenerator
 * @date 2016-09-17 20:35:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tRechargeController")
public class TRechargeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TRechargeController.class);

	@Autowired
	private TRechargeServiceI tRechargeService;
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
	 * 充值列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tRecharge")
	public ModelAndView tRecharge(HttpServletRequest req) {
		TSUser user =ResourceUtil.getSessionUserName();
		req.setAttribute("userType", user.getUserType());
		return new ModelAndView("com/buss/recharge/tRechargeList");
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
	public void datagrid(TRechargeEntity tRecharge,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TRechargeEntity.class, dataGrid);
		//查询条件组装器
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			String orderNo = request.getParameter("orderNo");
			String name = request.getParameter("name");
			String realname = request.getParameter("realname");
			String phoneNo = request.getParameter("phoneNo");
			String payTime_begin = request.getParameter("payTime_begin");
			String payTime_end = request.getParameter("payTime_end");
			String payType = request.getParameter("payType");
			String payStatus = request.getParameter("payStatus");
			int total = 0;
			StringBuffer sql = new StringBuffer("SELECT r.id,IFNULL(r.order_no,'') as orderNo,IFNULL(r.add_time,'') as addTime, r.phone_no as phoneNo,r.money,IFNULL(r.pay_money,'') as payMoney,IFNULL(r.service_charge,'') as serviceCharge,IFNULL(r.pay_type,'') as payType,")
				.append("IFNULL(r.create_name,'') as createName,IFNULL(r.pay_time,'') as payTime,r.pay_status as payStatus,IFNULL(r.discount,'') as discount,r.user_id as userId,IFNULL(r.to_guide_id,'') as guideId,")
				.append("IFNULL(u.realname,'') as realname,p.name  from t_recharge r LEFT JOIN t_s_user u ON r.to_guide_id = u.id LEFT JOIN t_person p on  r.user_id= p.user_Id where r.status = 'A'  and p.user_type = '04' ");
			StringBuffer countSql = new StringBuffer("select count(1) from t_recharge r LEFT JOIN t_s_user u ON r.to_guide_id = u.id LEFT JOIN t_person p on  r.user_id= p.user_Id where r.status = 'A' and p.user_type = '04'");
			if(StringUtil.isNotEmpty(retailerId)){
				sql.append(" and r.retailer_id ='").append(retailerId).append("'");
				countSql.append(" and r.retailer_id ='").append(retailerId).append("'");
			}
			if(StringUtil.isNotEmpty(orderNo)){
				sql.append(" and r.order_no like '%").append(orderNo).append("%'");
				countSql.append(" and r.order_no like '%").append(orderNo).append("%'");
			}
			if(StringUtil.isNotEmpty(name)){
				sql.append(" and p.name like '%").append(name).append("%'");
				countSql.append(" and p.name like '%").append(name).append("%'");
			}
			if(StringUtil.isNotEmpty(realname)){
				sql.append(" and u.realname like '%").append(realname).append("%'");
				countSql.append(" and u.realname like '%").append(realname).append("%'");
			}
			if(StringUtil.isNotEmpty(phoneNo)){
				sql.append(" and r.phone_no like '%").append(phoneNo).append("%'");
				countSql.append(" and r.phone_no like '%").append(phoneNo).append("%'");
			}
			if(StringUtil.isNotEmpty(payTime_begin)){
				sql.append(" and r.pay_time >='").append(payTime_begin).append(" 00:00:00'");
				countSql.append(" and r.pay_time >='").append(payTime_begin).append(" 00:00:00'");
			}
			if(StringUtil.isNotEmpty(payTime_end)){
				sql.append(" and r.pay_time <='").append(payTime_end).append(" 23:59:59'");
				countSql.append(" and r.pay_time <='").append(payTime_end).append(" 23:59:59'");
			}
			if(StringUtil.isNotEmpty(payType)){
				sql.append(" and r.pay_type ='").append(payType).append("'");
				countSql.append(" and r.pay_type ='").append(payType).append("'");
			}
			if(StringUtil.isNotEmpty(payStatus)){
				sql.append(" and r.pay_status ='").append(payStatus).append("'");
				countSql.append(" and r.pay_status ='").append(payStatus).append("'");
			}
			String sort = dataGrid.getSort();
			if(StringUtil.isEmpty(sort)){
				sql.append(" order by addTime desc ");
			}else{
				sql.append(" order by ").append(sort).append(" ").append(dataGrid.getOrder());
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
			sql=null;
			countSql=null;
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除充值
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TRechargeEntity tRecharge, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tRecharge = systemService.flushEntity(TRechargeEntity.class, tRecharge.getId());
		message = "充值删除成功";
		try{
			tRechargeService.delete(tRecharge);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "充值删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除充值
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "充值删除成功";
		try{
			for(String id:ids.split(",")){
				TRechargeEntity tRecharge = systemService.flushEntity(TRechargeEntity.class, 
				id
				);
				tRechargeService.delete(tRecharge);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "充值删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加充值
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TRechargeEntity tRecharge, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "充值添加成功";
		try{
			TSUser user = this.getCustomerByPhoneNo(tRecharge.getPhoneNo());
			if(Utility.isNotEmpty(user)){
				tRecharge.setUserId(user.getId());
				tRecharge.setRetailerId(user.getRetailerId());
				String msg = tRechargeService.saveRecharge(tRecharge);
				if(StringUtil.isEmpty(msg)){
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}else{
					message = msg;
				}
			}else{
				message = "系统不存在该顾客，请检查手机号是否正确";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "充值添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**通过手机号获取顾客
	 * @param phoneNo
	 * @return
	 */
	private TSUser getCustomerByPhoneNo(String phoneNo) {
		String hql = "from TSUser where status = 'A' and userType = '04' and userName = ? ";
		List<TSUser> list = this.systemService.findHql(hql, phoneNo);
		if(Utility.isNotEmpty(list)){
			TSUser user = list.get(0);
			return user;
		}
		return null;
	}

	/**
	 * 更新充值
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TRechargeEntity tRecharge, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "充值更新成功";
		TRechargeEntity t = tRechargeService.get(TRechargeEntity.class, tRecharge.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tRecharge, t);
			tRechargeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "充值更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 充值新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TRechargeEntity tRecharge, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tRecharge.getId())) {
			tRecharge = tRechargeService.flushEntity(TRechargeEntity.class, tRecharge.getId());
			req.setAttribute("tRechargePage", tRecharge);
		}
		return new ModelAndView("com/buss/recharge/tRecharge-add");
	}
	/**
	 * 充值编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TRechargeEntity tRecharge, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tRecharge.getId())) {
			tRecharge = tRechargeService.flushEntity(TRechargeEntity.class, tRecharge.getId());
			req.setAttribute("tRechargePage", tRecharge);
		}
		return new ModelAndView("com/buss/recharge/tRecharge-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tRechargeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TRechargeEntity tRecharge,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TRechargeEntity.class, dataGrid);
		cq.notEq("payType", TRechargeEntity.PAY_STATUS_3);
		String retailerId = ResourceUtil.getRetailerId();
		if(StringUtil.isNotEmpty(retailerId)){
			cq.eq("retailerId", retailerId);
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tRecharge, request.getParameterMap());
		List<TRechargeEntity> tRecharges = this.tRechargeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"充值");
		modelMap.put(NormalExcelConstants.CLASS,TRechargeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("充值列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tRecharges);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TRechargeEntity tRecharge,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "充值");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TRechargeEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
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
				List<TRechargeEntity> listTRechargeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TRechargeEntity.class,params);
				for (TRechargeEntity tRecharge : listTRechargeEntitys) {
					tRechargeService.save(tRecharge);
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
