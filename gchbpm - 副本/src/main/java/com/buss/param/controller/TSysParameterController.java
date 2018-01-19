package com.buss.param.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.param.entity.TSysParameterEntity;
import com.buss.param.service.TSysParameterServiceI;



/**   
 * @Title: Controller
 * @Description: 系统参数
 * @author onlineGenerator
 * @date 2017-02-15 15:57:12
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSysParameterController")
public class TSysParameterController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSysParameterController.class);

	@Autowired
	private TSysParameterServiceI tSysParameterService;
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
	 * 系统参数页面  （首页）
	 * 排序从小到大
	 * @return
	 */
	@RequestMapping(params = "paramPage")
	public ModelAndView paramPage(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		List<TSysParameterEntity> paramList = 
			this.tSysParameterService.findHql("from TSysParameterEntity where retailerId = ? and paraType = ? order by sortNum asc", retailerId,TSysParameterEntity.PARA_TYPE_INDEX);
		request.setAttribute("paramList", paramList);
		return new ModelAndView("com/buss/param/tSysParameterPage");
	}

	/**
	 * 系统参数页面  （导购提成设置）
	 * 排序从小到大
	 * @return
	 */
//	@RequestMapping(params = "paramPageOfGuide")
//	public ModelAndView paramPageOfGuide(HttpServletRequest request) {
//		String retailerId = ResourceUtil.getRetailerId();
//		List<TSysParameterEntity> paramList = 
//			this.tSysParameterService.findHql("from TSysParameterEntity where retailerId = ? and paraType = ? order by sortNum asc", retailerId,TSysParameterEntity.PARA_TYPE_GUIDE);
//		request.setAttribute("paramList", paramList);
//		return new ModelAndView("com/buss/param/guideCommissionPage");
//	}
	
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
//		CriteriaQuery cq = new CriteriaQuery(TSysParameterEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSysParameter, request.getParameterMap());
//		try{
//			String retailerId = ResourceUtil.getRetailerId();
//			cq.eq("retailerId", retailerId);//零售商id
//			cq.notEq("paraCode", GlobalConstants.ORDER_CONFIRM);
//			cq.notEq("paraCode", GlobalConstants.ORDER_PAY);
//			cq.notEq("paraCode", GlobalConstants.ORDER_SEND);
//			cq.notEq("paraCode", GlobalConstants.ORDER_APPLY);
//			cq.notEq("paraCode", GlobalConstants.ORDER_RETURNSUCC);
//			cq.notEq("paraCode", GlobalConstants.ORDER_PHONE);
//			cq.notEq("paraCode", GlobalConstants.ORDER_RETURN_PHONE);
//			cq.notEq("paraCode", GlobalConstants.ORDER_RETURN_ADDRESS);
//			
//		//自定义追加查询条件
//		}catch (Exception e) {
//			throw new BusinessException(e.getMessage());
//		}
//		cq.add();
//		this.tSysParameterService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
//	}

	/**
	 * 删除系统参数
	 * 
	 * @return
	 */
	/*@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSysParameterEntity tSysParameter, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSysParameter = systemService.flushEntity(TSysParameterEntity.class, tSysParameter.getId());
		message = "系统参数删除成功";
		try{
			tSysParameterService.delete(tSysParameter);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "系统参数删除失败";
			throw new BusinessException(e.getMessage());
		}
		//更新系统参数的redis数据
		redisService.initAllSysParamters();
		j.setMsg(message);
		return j;
	}*/
	

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
//		message = "系统参数添加成功";
//		try{
//			tSysParameterService.updateSysParameter(tSysParameter);
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
//		TSysParameterEntity t = tSysParameterService.get(TSysParameterEntity.class, tSysParameter.getId());
//		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tSysParameter, t);
//			tSysParameterService.saveOrUpdate(t);
//			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "系统参数更新失败";
//			throw new BusinessException(e.getMessage());
//		}
//		//更新系统参数的redis数据
//		redisService.initAllSysParamters();
//		j.setMsg(message);
//		return j;
//	}
	
	/**
	 * 批量更新或者保存系统参数（首页）
	 * @return
	 */
	@RequestMapping(params = "doBatchSaveOrUpdate")
	@ResponseBody
	public AjaxJson doBatchSaveOrUpdate(TSysParameterEntity tSysParameter, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "系统参数更新成功";
		try {
			tSysParameterService.doBatchSaveOrUpdate(tSysParameter);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "系统参数更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 系统参数新增页面跳转
	 * 
	 * @return
	 */
//	@RequestMapping(params = "goAdd")
//	public ModelAndView goAdd(TSysParameterEntity tSysParameter, HttpServletRequest req) {
//		if (StringUtil.isNotEmpty(tSysParameter.getId())) {
//			tSysParameter = tSysParameterService.flushEntity(TSysParameterEntity.class, tSysParameter.getId());
//			req.setAttribute("tSysParameterPage", tSysParameter);
//		}
//		List<HashMap<String, Object>> paraTypeList = tSTypeService.getTypeListByCode(TSysParameterEntity.SYS_PARAMS_TYPE);
//		req.setAttribute("paraTypeList", paraTypeList);
//		return new ModelAndView("com/buss/param/tSysParameter-add");
//	}
	/**
	 * 系统参数编辑页面跳转
	 * 
	 * @return
	 */
//	@RequestMapping(params = "goUpdate")
//	public ModelAndView goUpdate(TSysParameterEntity tSysParameter, HttpServletRequest req) {
//		if (StringUtil.isNotEmpty(tSysParameter.getId())) {
//			tSysParameter = tSysParameterService.flushEntity(TSysParameterEntity.class, tSysParameter.getId());
//			req.setAttribute("tSysParameterPage", tSysParameter);
//		}
//		List<HashMap<String, Object>> paraTypeList = tSTypeService.getTypeListByCode(TSysParameterEntity.SYS_PARAMS_TYPE);
//		req.setAttribute("paraTypeList", paraTypeList);
//		
//		return new ModelAndView("com/buss/param/tSysParameter-update");
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
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	/*@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tSysParameterController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}*/
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(params = "exportXls")
	public String exportXls(TSysParameterEntity tSysParameter,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSysParameterEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSysParameter, request.getParameterMap());
		List<TSysParameterEntity> tSysParameters = this.tSysParameterService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"系统参数");
		modelMap.put(NormalExcelConstants.CLASS,TSysParameterEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("系统参数列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tSysParameters);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}*/
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSysParameterEntity tSysParameter,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "系统参数");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TSysParameterEntity.class);
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
				List<TSysParameterEntity> listTSysParameterEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSysParameterEntity.class,params);
				for (TSysParameterEntity tSysParameter : listTSysParameterEntitys) {
					tSysParameterService.save(tSysParameter);
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
	}*/
	
	
	/**
	 * 获取订单自动回复模板
	 */
//	@RequestMapping(params = "getOrderMsgTemp")
//	@ResponseBody
//	public AjaxJson getOrderMsgTemp(HttpServletRequest request) {
//		TSUser user = ResourceUtil.getSessionUserName();
//		AjaxJson j = new AjaxJson();
//		String typecode = request.getParameter("typecode");
//		List<String> list = GlobalConstants.orderMsgMap.get(typecode);
//		if(Utility.isNotEmpty(list)){
//			String val = list.get(0);
//			if(val.indexOf("（零售商名称）") != -1){
//				val = val.replaceAll("（零售商名称）", user.getRealName());
//			}
//			j.setObj(val);
//			j.setMsg(message);
//		}
//		return j;
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
