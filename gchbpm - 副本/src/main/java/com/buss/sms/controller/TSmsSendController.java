package com.buss.sms.controller;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
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

import cn.redis.service.RedisService;

import com.buss.sms.entity.TSmsSendEntity;
import com.buss.sms.entity.TSmsSendInfoEntity;
import com.buss.sms.service.TSmsSendServiceI;
import com.buss.user.entity.TFocusCustomerEntity;
import com.buss.user.entity.vo.TFocusCustomerVo;



/**   
 * @Title: Controller
 * @Description: t_sms_send
 * @author onlineGenerator
 * @date 2017-02-15 15:07:48
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSmsSendController")
public class TSmsSendController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSmsSendController.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private TSmsSendServiceI tSmsSendService;
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
	 * t_sms_send列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tSmsSend")
	public ModelAndView tSmsSend(HttpServletRequest request) {
		String sendInfoId = request.getParameter("sendInfoId");
		TSmsSendInfoEntity smsSendInfo = this.systemService.get(TSmsSendInfoEntity.class, sendInfoId);
		request.setAttribute("smsSendInfo", smsSendInfo);
		return new ModelAndView("com/buss/sms/tSmsSendList");
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
	public void datagrid(TSmsSendEntity tSmsSend,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sendInfoId = request.getParameter("send_Info_Id");//主表ID
		String retailerId = ResourceUtil.getRetailerId();//零售商ID
		CriteriaQuery cq = new CriteriaQuery(TSmsSendEntity.class, dataGrid);
		if(Utility.isNotEmpty(sendInfoId)){
			cq.eq("sendInfoId", sendInfoId);
		}
		cq.eq("retailerId", retailerId);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSmsSend, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSmsSendService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}



	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSmsSendEntity tSmsSend,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String sendInfoId = request.getParameter("sendInfoId");
		tSmsSend.setSendInfoId(sendInfoId);
		CriteriaQuery cq = new CriteriaQuery(TSmsSendEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSmsSend, request.getParameterMap());
		List<TSmsSendEntity> tSmsSends = this.tSmsSendService.getListByCriteriaQuery(cq,true);
		modelMap.put(NormalExcelConstants.FILE_NAME,"短信发送");
		modelMap.put(NormalExcelConstants.CLASS,TSmsSendEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("短信发送列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tSmsSends);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSmsSendEntity tSmsSend,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "t_sms_send");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TSmsSendEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	/**
	 * excel导入手机号码并生成短链接,些人redis中并设置生命周期
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		String extraParam = request.getParameter("extraParam");//eb0204aa6f854814bd7c9d712b3dfd72,1702200066,402881a15a4a12e1015a4a3cb757000d,0,
		String[] arr = extraParam.split(",");//id,批次号,模板ID,发送类型(1定时发送，0及时),发送时间
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setNeedSave(true);
			List<TSmsSendEntity> tSmsSends = null;
			try{
				//保存主表和明细，同时生成端连接
				tSmsSends = tSmsSendService.importExcel(retailerId, arr,file,params);
				//将短链接与长链接写入redis中,并设置生命周期
				j.setMsg("顾客导入成功！");
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("顾客导入失败！");
				e.printStackTrace();
				logger.error(e.getMessage());
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ResourceBundle env = ResourceBundle.getBundle("env");
			long start = System.currentTimeMillis();
			Map<String, String> map = new HashMap<String, String>();
			for (TSmsSendEntity tSmsSendEntity : tSmsSends) {//单位为秒
				map.put(tSmsSendEntity.getShortUrl().replace(env.getObject("SHORT_URL").toString(), "1/"), tSmsSendEntity.getLongUrl());
			}
			redisService.batchSet(map,"NX","EX",3600*24*7);
			long end = System.currentTimeMillis();
			System.out.println("链接放入缓存成功，耗时："+(end-start));
			
		}
		return j;
	}
	
	/**
	 * 短信回调下行地址
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "getReport",produces="application/json;charset=UTF-8")
	public Map<String,Object> getReport(HttpServletRequest request,HttpServletResponse response) {
		String  params = request.getParameter("param");//下行地址返回的参数
		LogUtil.info("下行地址回调成功,参数param："+params);
		//201702201749202443,13662205744,1,20170220174940;201702201749194626,13922880483,1,20170220174940;(消息ID，手机号码，状态，时间)
		Map<String,Object> map = new HashMap<String,Object>();
		if(Utility.isNotEmpty(params)){
			StringBuilder sb = new StringBuilder();
			String[] param = params.split(";");
			for (String preMsg : param) {
				if(Utility.isNotEmpty(preMsg)){
					String[] msg = preMsg.split(",");
					sb.delete(0, sb.length());
					sb.append(" update t_sms_send set receive_Status='").append(msg[2]).append("'")
					.append(" where msg_id='").append(msg[0]).append("'");
					systemService.updateBySqlString(sb.toString());
				}
			}
		}
    	return map;
    }
		
	/**
	 * 根据筛选条件获取潜在顾客列表(选择顾客确认)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "importSendCustomer", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importSendCustomer(TFocusCustomerVo tFocusCustomer,TSmsSendInfoEntity tSmsSendInfo,HttpServletRequest request,HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("unOrder", request.getParameter("unOrder"));
		paramsMap.put("batchNo", request.getParameter("batchNo"));
		String rId = ResourceUtil.getRetailerId();//零售商ID
		List<TSmsSendEntity> tSmsSends = null;
		try {
			/*-------------初始默认值 begin---------------------*/
			tSmsSendInfo.setRetailerId(rId);
			tSmsSendInfo.setClickNumber(0);
			tSmsSendInfo.setClickRate(BigDecimal.ZERO);
			tSmsSendInfo.setBuyRate(BigDecimal.ZERO);
			tSmsSendInfo.setBuySingle(0);
			tSmsSendInfo.setClickRate(BigDecimal.ZERO);
			tSmsSendInfo.setPushCount(0);
			tSmsSendInfo.setReach(0);
			tSmsSendInfo.setReachRate(BigDecimal.ZERO);
			/*------------------end----------------------------*/
			tSmsSends = tSmsSendService.importSendCustomer(tFocusCustomer,tSmsSendInfo,paramsMap);
			j.setMsg("文件导入成功！");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("selectCount", tSmsSends.size());
			j.setAttributes(map);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("文件导入失败！");
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		try{
		ResourceBundle env = ResourceBundle.getBundle("env");
		long start = System.currentTimeMillis();
		Map<String, String> map = new HashMap<String, String>();
		for (TSmsSendEntity tSmsSendEntity : tSmsSends) {//单位为秒
			map.put(tSmsSendEntity.getShortUrl().replace(env.getObject("SHORT_URL").toString(), "1/"), tSmsSendEntity.getLongUrl());
		}
		redisService.batchSet(map,"NX","EX",3600*24*7);
		long end = System.currentTimeMillis();
		System.out.println("链接放入缓存成功，耗时："+(end-start));
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 删除短信发送
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSmsSendEntity tSmsSend, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSmsSend = systemService.flushEntity(TSmsSendEntity.class, tSmsSend.getId());
		message = "短信明细删除成功";
		try{
			//删除明细，主表推送次数-1
			this.tSmsSendService.doDel(tSmsSend);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
//	/**
//	 * 选择顾客页面确认（只保存查询条件到redis中）
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(params = "confirm", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxJson confirm(TFocusCustomerEntity tFocusCustomer,HttpServletRequest request,HttpServletResponse response) {
//		AjaxJson j = new AjaxJson();
//		String sql = org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.getSqlCondition(tFocusCustomer,  request.getParameterMap(), "");
//		String key = request.getParameter("batchNo");
// 		String value;
// 		try
//		{
// 			value = redisService.get(key);
//		  if(!Utility.isEmpty(value)) {
//			  value += sql;
//		  }else{
//			  value = "";
//		  }
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
////		redisService.set(key, value);
//		System.out.println(sql);
//		return j;
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
