package com.buss.balance.controller;
import com.buss.balance.entity.TCardRateEntity;
import com.buss.balance.service.TCardRateServiceI;

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
 * @Description: G卡提成比例
 * @author onlineGenerator
 * @date 2016-12-02 19:42:14
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tCardRateController")
public class TCardRateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TCardRateController.class);

	@Autowired
	private TCardRateServiceI tCardRateService;
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
	 * G卡提成比例列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/balance/tCardRateList");
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
	public void datagrid(TCardRateEntity tCardRate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			StringBuffer sql = new StringBuffer("select u.id AS retailerId,ifnull(u.realname,'') AS retailerName,ifnull(r.create_Date,'') as createDate,r.id,")
			.append("ifnull(r.retailer_rate,'') as retailerRate,ifnull(r.guide_rate,'') as guideRate")
			.append(" from t_s_user u left join t_card_rate r ON u.id = r.retailer_id  ")
			.append("  WHERE u.user_status = '1' AND u.user_type = '02' AND u.status = 'A' AND u.retailer_type = '1' ");
		StringBuffer countSql = new StringBuffer("select count(1) from t_s_user u WHERE u.user_status = '1' AND u.user_type = '02' AND u.status = 'A' AND u.retailer_type = '1'");
		String retailerName = request.getParameter("retailerName");
		if(!Utility.isEmpty(retailerName)){
			sql.append(" and u.realname like '%").append(retailerName).append("%'");
			countSql.append(" and u.realname like '%").append(retailerName).append("%'");
		}
		String sort = dataGrid.getSort();
		if(StringUtil.isEmpty(sort)){
				sql.append(" order by r.createDate asc");
		}else{
			sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder());
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
	 * 删除G卡提成比例
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TCardRateEntity tCardRate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tCardRate = systemService.flushEntity(TCardRateEntity.class, tCardRate.getId());
		message = "G卡提成比例删除成功";
		try{
			tCardRate.setStatus("I");
			tCardRateService.updateEntitie(tCardRate);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "G卡提成比例删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除G卡提成比例
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "G卡提成比例删除成功";
		try{
			for(String id:ids.split(",")){
				TCardRateEntity tCardRate = systemService.flushEntity(TCardRateEntity.class, 
				id
				);
				tCardRateService.delete(tCardRate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "G卡提成比例删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加G卡提成比例
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TCardRateEntity tCardRate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "G卡提成比例添加成功";
		try{
			tCardRateService.save(tCardRate);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "G卡提成比例添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新G卡提成比例
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TCardRateEntity tCardRate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "G+卡提成比例设置更新成功";
		try {
			tCardRateService.updateEntitie(tCardRate);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "G+卡提成比例设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量更新G卡提成比例
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doBatchUpdate")
	@ResponseBody
	public AjaxJson doBatchUpdate(TCardRateEntity tCardRate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "G+卡提成比例设置更新成功";
		try {
			tCardRateService.saveOrUpdateCardRate(tCardRate);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "G+卡提成比例设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * G卡提成比例新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TCardRateEntity tCardRate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tCardRate.getId())) {
			tCardRate = tCardRateService.flushEntity(TCardRateEntity.class, tCardRate.getId());
			req.setAttribute("tCardRatePage", tCardRate);
		}
		return new ModelAndView("com/buss/balance/tCardRate-add");
	}
	
	/**
	 * G卡提成比例新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TCardRateEntity tCardRate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tCardRate.getId())) {
			tCardRate = tCardRateService.flushEntity(TCardRateEntity.class, tCardRate.getId());
			TSUser user = this.systemService.get(TSUser.class, tCardRate.getRetailerId());
			tCardRate.setRetailerName(user.getRealName());
			req.setAttribute("tCardRatePage", tCardRate);
		}
		return new ModelAndView("com/buss/balance/tCardRate-update");
	}
	
	/**
	 * G卡提成比例编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goBatchUpdate")
	public ModelAndView goBatchUpdate(TCardRateEntity tCardRate, HttpServletRequest req) {
//			tCardRate = tCardRateService.flushEntity(TCardRateEntity.class, tCardRate.getId());
		StringBuffer  sql = new StringBuffer("select u.id AS retailerId,u.realname AS retailerName,r.id,r.retailer_rate as retailerRate,r.guide_rate as guideRate ")
				.append(" from t_s_user u left join t_card_rate r ON u.id = r.retailer_id  ")
				.append("  WHERE u.user_status = '1' AND u.user_type = '02' AND u.status = 'A' AND u.retailer_type = '1' ");
			List<Map<String,Object>> mapList = this.systemService.findForJdbc(sql.toString(), null);
			List<TCardRateEntity> cardRateList = new ArrayList<TCardRateEntity>();
			for (Map<String, Object> m : mapList) {
				try {
					TCardRateEntity cardRate = new TCardRateEntity();
					MyBeanUtils.copyMap2Bean_Nobig(cardRate, m);
					cardRateList.add(cardRate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			req.setAttribute("cardRateList", cardRateList);
		return new ModelAndView("com/buss/balance/tCardRate-batchUpdate");
	}
	
}
