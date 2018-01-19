package com.buss.order.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.core.util.oConvertUtils;
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

import cn.redis.service.RedisService;

import com.buss.order.service.TOrderMsgServiceI;
import com.buss.param.entity.TSysParameterEntity;
import com.buss.param.service.TSysParameterServiceI;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 订单短信自动回复
 * @author onlineGenerator
 * @date 2017-02-15 15:57:12
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOrderMsgController")
public class TOrderMsgController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOrderMsgController.class);
	@Autowired
	private TOrderMsgServiceI tOrderMsgService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TSysParameterServiceI tSysParameterService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 系统参数列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tOrderMsgPage")
	public ModelAndView tOrderMsgPage(HttpServletRequest request) {
//		return new ModelAndView("com/buss/order/tOrderMsgPage");
		String retailerId = ResourceUtil.getRetailerId();
		List<TSysParameterEntity> paramList = 
			this.tOrderMsgService.findHql("from TSysParameterEntity where retailerId = ? and paraType = ? order by sortNum asc", retailerId,TSysParameterEntity.PARA_TYPE_SMS);
		request.setAttribute("paramList", paramList);
		return new ModelAndView("com/buss/order/tOrderMsgPage2");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

//	@RequestMapping(params = "datagrid")
//	public void datagrid(TSysParameterEntity tSysParameter,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		String retailerId = ResourceUtil.getRetailerId();
//		CriteriaQuery cq = new CriteriaQuery(TSysParameterEntity.class, dataGrid);
//		
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSysParameter, request.getParameterMap());
//		try{
//		//自定义追加查询条件
//			List<String> paraCodes = new ArrayList<String>();
//			paraCodes.add(GlobalConstants.ORDER_CONFIRM);
//			paraCodes.add(GlobalConstants.ORDER_PAY);
//			paraCodes.add(GlobalConstants.ORDER_SEND);
//			paraCodes.add(GlobalConstants.ORDER_APPLY);
//			paraCodes.add(GlobalConstants.ORDER_RETURNSUCC);
//			cq.in("paraCode", paraCodes.toArray());
//			cq.eq("retailerId", retailerId);
//		}catch (Exception e) {
//			throw new BusinessException(e.getMessage());
//		}
//		cq.add();
//		this.tOrderMsgService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
//	}

	/**
	 * 删除系统参数
	 * 
	 * @return
	 */
//	@RequestMapping(params = "doDel")
//	@ResponseBody
//	public AjaxJson doDel(TSysParameterEntity tSysParameter, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		tSysParameter = systemService.flushEntity(TSysParameterEntity.class, tSysParameter.getId());
//		message = "系统参数删除成功";
//		try{
//			tOrderMsgService.delete(tSysParameter);
//			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "系统参数删除失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}
//	
//	/**
//	 * 批量删除系统参数
//	 * 
//	 * @return
//	 */
//	 @RequestMapping(params = "doBatchDel")
//	@ResponseBody
//	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
//		AjaxJson j = new AjaxJson();
//		message = "系统参数删除成功";
//		try{
//			for(String id:ids.split(",")){
//				TSysParameterEntity tSysParameter = systemService.flushEntity(TSysParameterEntity.class, 
//				id
//				);
//				tOrderMsgService.delete(tSysParameter);
//				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "系统参数删除失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}


	/**
	 * 添加系统参数
	 * 
	 * @param ids
	 * @return
	 */
//	@RequestMapping(params = "doAdd")
//	@ResponseBody
//	public AjaxJson doAdd(TSysParameterEntity tSysParameter, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		String retailerId = ResourceUtil.getRetailerId();
//        tSysParameter.setRetailerId(retailerId);
//        tSysParameter.setSortNum(0);
//		message = "系统参数添加成功";
//		try{
//			tOrderMsgService.saveOrderMsg(tSysParameter);
//			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "系统参数添加失败";
//			throw new BusinessException(e.getMessage());
//		}
//		//更新系统参数的redis数据
//		redisService.initAllSysParamters();
//		j.setMsg(message);
//		return j;
//	}
	
	/**
	 * 更新系统参数
	 * 
	 * @param ids
	 * @return
	 */
//	@RequestMapping(params = "doUpdate")
//	@ResponseBody
//	public AjaxJson doUpdate(TSysParameterEntity tSysParameter, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		message = "系统参数更新成功";
//		try{
//			tOrderMsgService.updateOrderMsg(tSysParameter);
//			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "系统参数添加失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}
	

	/**
	 * 系统参数新增页面跳转
	 * 
	 * @return
	 */
//	@RequestMapping(params = "goAdd")
//	public ModelAndView goAdd(TSysParameterEntity tSysParameter, HttpServletRequest req) {
//		//客服电话，退货电话和退货地址回写
//		String retailerId = ResourceUtil.getRetailerId();
//		List<TSysParameterEntity> phones = tOrderMsgService.getParameters(retailerId);
//		if(Utility.isNotEmpty(phones)){
//			for (TSysParameterEntity tSysParameterEntity : phones) {
//				if(GlobalConstants.ORDER_PHONE.equals(tSysParameterEntity.getParaCode())){
//					req.setAttribute("phone", tSysParameterEntity.getParaValue());
//				}else if(GlobalConstants.ORDER_RETURN_PHONE.equals(tSysParameterEntity.getParaCode())){
//					req.setAttribute("returnPhone", tSysParameterEntity.getParaValue());
//				}else if(GlobalConstants.ORDER_RETURN_ADDRESS.equals(tSysParameterEntity.getParaCode())){
//					req.setAttribute("returnAddress", tSysParameterEntity.getParaValue());
//				}
//			}
//		}
//		//几种订单短信自动回复类型
//		List<HashMap<String, Object>> paraTypeList = tSTypeService.getTypeListByCode("paramType");
//		req.setAttribute("paraTypeList", paraTypeList);
//		return new ModelAndView("com/buss/order/tOrderMsg-add");
//	}
	/**
	 * 系统参数编辑页面跳转
	 * 
	 * @return
	 */
//	@RequestMapping(params = "goUpdate")
//	public ModelAndView goUpdate(TSysParameterEntity tSysParameter, HttpServletRequest req) {
//		if (StringUtil.isNotEmpty(tSysParameter.getId())) {
//			tSysParameter = tOrderMsgService.flushEntity(TSysParameterEntity.class, tSysParameter.getId());
//			
//			
//			List<HashMap<String, Object>> paraTypeList = tSTypeService.getTypeListByCode("paramType");
//			req.setAttribute("paraTypeList", paraTypeList);
//			//客服电话，退货电话和退货地址回写
//			List<TSysParameterEntity> phones = tOrderMsgService.getParameters(tSysParameter.getRetailerId());
//			if(Utility.isNotEmpty(phones)){
//				for (TSysParameterEntity tSysParameterEntity : phones) {
//					if(GlobalConstants.ORDER_PHONE.equals(tSysParameterEntity.getParaCode())){
//						tSysParameter.setPhone(tSysParameterEntity.getParaValue());
//					}else if(GlobalConstants.ORDER_RETURN_PHONE.equals(tSysParameterEntity.getParaCode())){
//						tSysParameter.setReturnPhone(tSysParameterEntity.getParaValue());
//					}else if(GlobalConstants.ORDER_RETURN_ADDRESS.equals(tSysParameterEntity.getParaCode())){
//						tSysParameter.setReturnAddress(tSysParameterEntity.getParaValue());
//					}
//				}
//			}
//			req.setAttribute("tSysParameterPage", tSysParameter);
//		}
//		
//		return new ModelAndView("com/buss/order/tOrderMsg-update");
//	}
	/**
	 * 检查参数编码
	 * 
	 * @param role
	 * @return
	 */
//	@RequestMapping(params = "doCheck")
//	@ResponseBody
//	public ValidForm doCheck(TSysParameterEntity tSysParameter, HttpServletRequest request, HttpServletResponse response) {
//		ValidForm v = new ValidForm();
//		String paraCode = oConvertUtils.getString(request.getParameter("paraCode"));
//		String paramid = oConvertUtils.getString(request.getParameter("paramid"));
//		String param = oConvertUtils.getString(request.getParameter("param"));
//		if(paraCode == param || param.equals(paraCode)){
//			return v;
//		}
//		List<TSysParameterEntity> roles = null;
//		TSUser user = ResourceUtil.getSessionUserName();
//		String hql = null;
//		hql = "from TSysParameterEntity where retailerId ='"+user.getId()+"'";
//		hql +=" and paraCode = '"+param+"'";
//		
//		roles = systemService.findByQueryString(hql);
//		if (roles.size() > 0 ) {
//			if(paramid == null || "".equals(paramid)){
//				v.setInfo("参数编码已存在");
//				v.setStatus("n");
//			}else{
//				for (TSysParameterEntity tspe : roles) {
//					if(!paramid.equals(tspe.getId())){
//						v.setInfo("参数编码已存在");
//						v.setStatus("n");
//					}
//				}
//			}
//		}
//		return v;
//	}
//	
	
//	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
//		AjaxJson j = new AjaxJson();
//		
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//			MultipartFile file = entity.getValue();// 获取上传文件对象
//			ImportParams params = new ImportParams();
//			params.setTitleRows(2);
//			params.setHeadRows(1);
//			params.setNeedSave(true);
//			try {
//				List<TSysParameterEntity> listTSysParameterEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSysParameterEntity.class,params);
//				for (TSysParameterEntity tSysParameter : listTSysParameterEntitys) {
//					tOrderMsgService.save(tSysParameter);
//				}
//				j.setMsg("文件导入成功！");
//			} catch (Exception e) {
//				j.setMsg("文件导入失败！");
//				logger.error(ExceptionUtil.getExceptionMessage(e));
//			}finally{
//				try {
//					file.getInputStream().close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return j;
//	}
	
	
	/**
	 * 获取订单自动回复模板
	 */
//	@RequestMapping(params = "getOrderMsgTemp")
//	@ResponseBody
//	public AjaxJson getOrderMsgTemp(HttpServletRequest request) {
//		TSUser user = ResourceUtil.getSessionUserName();
//		AjaxJson j = new AjaxJson();
//		String typecode = request.getParameter("typecode");
//		String phone = request.getParameter("phone");
//		String returnPhone = request.getParameter("returnPhone");
//		String returnAddress = request.getParameter("returnAddress");
//		List<String> list = GlobalConstants.orderMsgMap.get(typecode);
//		if(Utility.isNotEmpty(list)){
//			String val = list.get(0);
//			if(val.indexOf("（零售商名称）") != -1){
//				val = val.replaceAll("（零售商名称）", user.getRealName());
//			}
//			if(Utility.isNotEmpty(phone)){
//				val = val.replaceAll("（客服电话）", phone);
//			}
//			if(Utility.isNotEmpty(returnPhone)){
//				val = val.replaceAll("（退货号码）", returnPhone);
//			}
//			if(Utility.isNotEmpty(returnAddress)){
//				val = val.replaceAll("（退货地址）", returnAddress);
//			}
//			j.setObj(val);
//			j.setMsg(message);
//		}
//		return j;
//	}
	
	/**
	 * 批量更新或者保存订单短信自动回复参数
	 * @return
	 */
	@RequestMapping(params = "doBatchSaveOrUpdate")
	@ResponseBody
	public AjaxJson doBatchSaveOrUpdate(TSysParameterEntity tSysParameter, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "订单短信自动回复参数更新成功";
		try {
			tSysParameterService.doBatchSaveOrUpdate(tSysParameter);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单短信自动回复参数更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
