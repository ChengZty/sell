package com.buss.shop.controller;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.shop.entity.TShopHomeTempEntity;
import com.buss.shop.service.TShopHomeTempServiceI;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 商城首页模版
 * @author onlineGenerator
 * @date 2017-03-03 11:57:06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tShopHomeTempController")
public class TShopHomeTempController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TShopHomeTempController.class);

	@Autowired
	private TShopHomeTempServiceI tShopHomeTempService;
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
	 * 商城首页模版列表 页面跳转(新增或编辑草稿箱的模板)
	 * @return
	 */
	@RequestMapping(params = "tShopHomeTemp")
	public ModelAndView tShopHomeTemp(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("rId", retailerId);
		TShopHomeTempEntity tShopHomeTemp = systemService.findUniqueByProperty(TShopHomeTempEntity.class, "retailerId", retailerId);
		request.setAttribute("tShopHomeTemp", tShopHomeTemp);
		return new ModelAndView("com/buss/shop/tShopHomeTemp");
	}
	
	/**
	 * 首页获取海报列表页面
	 * @return
	 */
	@RequestMapping(params = "tPosterLinkList")
	public ModelAndView tPosterLinkList(HttpServletRequest request) {
		ResourceBundle env = ResourceBundle.getBundle("env");
		String url = env.getObject("CST_REQUEST_PRE_URL")+"tPosterController.do?viewPoster&id=";
		request.setAttribute("url", url);
		return new ModelAndView("com/dagger/infoLink");
	}
	
	/**
	 * 首页获取商品列表页面
	 * @return
	 */
	@RequestMapping(params = "tGoodsLinkList")
	public ModelAndView tGoodsLinkList(HttpServletRequest request) {
//		req.setAttribute("rId", ResourceUtil.getRetailerId());
		ResourceBundle env = ResourceBundle.getBundle("env");
		String url = env.getObject("CST_REQUEST_PRE_URL")+"tPosterController.do?viewPoster&id=";
		request.setAttribute("url", url);
		return new ModelAndView("com/dagger/goodsLink");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

//	@RequestMapping(params = "datagrid")
//	public void datagrid(TShopHomeTempEntity tShopHomeTemp,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TShopHomeTempEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tShopHomeTemp, request.getParameterMap());
//		try{
//		//自定义追加查询条件
//		}catch (Exception e) {
//			throw new BusinessException(e.getMessage());
//		}
//		cq.add();
//		this.tShopHomeTempService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
//	}

	/**
	 * 删除商城首页模版
	 * @return
	 */
//	@RequestMapping(params = "doDel")
//	@ResponseBody
//	public AjaxJson doDel(TShopHomeTempEntity tShopHomeTemp, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		tShopHomeTemp = systemService.flushEntity(TShopHomeTempEntity.class, tShopHomeTemp.getId());
//		message = "商城首页模版删除成功";
//		try{
//			tShopHomeTempService.delete(tShopHomeTemp);
//			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "商城首页模版删除失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}
	
	/**
	 * 批量删除商城首页模版
	 * @return
	 */
//	 @RequestMapping(params = "doBatchDel")
//	@ResponseBody
//	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
//		AjaxJson j = new AjaxJson();
//		message = "商城首页模版删除成功";
//		try{
//			for(String id:ids.split(",")){
//				TShopHomeTempEntity tShopHomeTemp = systemService.flushEntity(TShopHomeTempEntity.class, 
//				id
//				);
//				tShopHomeTempService.delete(tShopHomeTemp);
//				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "商城首页模版删除失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}


	/**
	 * 添加商城首页模版
	 * @param ids
	 * @return
	 */
//	@RequestMapping(params = "doAdd")
//	@ResponseBody
//	public AjaxJson doAdd(TShopHomeTempEntity tShopHomeTemp, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		message = "商城首页模版添加成功";
//		try{
//			tShopHomeTempService.save(tShopHomeTemp);
//			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "商城首页模版添加失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}
	
	/**
	 * 更新商城首页模版(点击保存)
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TShopHomeTempEntity tShopHomeTemp, HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		AjaxJson j = new AjaxJson();
		message = "商城首页模版更新成功";
		try {
			if(Utility.isNotEmpty(tShopHomeTemp.getId())){
				TShopHomeTempEntity t = systemService.get(TShopHomeTempEntity.class, tShopHomeTemp.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tShopHomeTemp, t);
				systemService.updateEntitie(t);
			}else{
				tShopHomeTemp.setRetailerId(retailerId);
				tShopHomeTemp.setStatus(GlobalConstants.STATUS_ACTIVE);
				tShopHomeTemp.setPubStatus(TShopHomeTempEntity.PUB_STATUS_1);//草稿箱
				if(Utility.isEmpty(tShopHomeTemp.getId())){
					tShopHomeTemp.setCreateDate(Utility.getCurrentTimestamp());
				}
				systemService.save(tShopHomeTemp);
			}
			j.setObj(tShopHomeTemp.getId());//传入页面(第一次点击保存草稿箱执行save操作,第二次保存草稿箱执行update操作)
			
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商城首页模版更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新商城首页模版(点击完成)
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doFinish")
	@ResponseBody
	public AjaxJson doFinish(TShopHomeTempEntity tShopHomeTemp, HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		AjaxJson j = new AjaxJson();
		message = "商城首页模版更新成功";
		try {
			tShopHomeTempService.updateFinish(tShopHomeTemp, retailerId);
			j.setObj(tShopHomeTemp.getId());//传入页面(第一次点击保存草稿箱执行save操作,第二次保存草稿箱执行update操作)
			
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商城首页模版更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 商城首页模版预览
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TShopHomeTempEntity tShopHomeTemp, HttpServletRequest req) {
		req.setAttribute("content",tShopHomeTemp.getContent());
		return new ModelAndView("com/buss/shop/tShopHomeTemp-view");
	}
	
	/**
	 * 商城首页模版新增页面跳转
	 * @return
	 */
//	@RequestMapping(params = "goAdd")
//	public ModelAndView goAdd(TShopHomeTempEntity tShopHomeTemp, HttpServletRequest req) {
//		if (StringUtil.isNotEmpty(tShopHomeTemp.getId())) {
//			tShopHomeTemp = tShopHomeTempService.flushEntity(TShopHomeTempEntity.class, tShopHomeTemp.getId());
//			req.setAttribute("tShopHomeTempPage", tShopHomeTemp);
//		}
//		return new ModelAndView("com/buss/shop/tShopHomeTemp-add");
//	}
//	/**
//	 * 商城首页模版编辑页面跳转
//	 * 
//	 * @return
//	 */
//	@RequestMapping(params = "goUpdate")
//	public ModelAndView goUpdate(TShopHomeTempEntity tShopHomeTemp, HttpServletRequest req) {
//		if (StringUtil.isNotEmpty(tShopHomeTemp.getId())) {
//			tShopHomeTemp = tShopHomeTempService.flushEntity(TShopHomeTempEntity.class, tShopHomeTemp.getId());
//			req.setAttribute("tShopHomeTempPage", tShopHomeTemp);
//		}
//		return new ModelAndView("com/buss/shop/tShopHomeTemp-update");
//	}
	
	
	/**
	 * 导入功能跳转
	 * @return
	 *//*
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tShopHomeTempController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	*//**
	 * 导出excel
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(params = "exportXls")
	public String exportXls(TShopHomeTempEntity tShopHomeTemp,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TShopHomeTempEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tShopHomeTemp, request.getParameterMap());
		List<TShopHomeTempEntity> tShopHomeTemps = this.tShopHomeTempService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"商城首页模版");
		modelMap.put(NormalExcelConstants.CLASS,TShopHomeTempEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商城首页模版列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tShopHomeTemps);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	*//**
	 * 导出excel 使模板
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TShopHomeTempEntity tShopHomeTemp,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "商城首页模版");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TShopHomeTempEntity.class);
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
				List<TShopHomeTempEntity> listTShopHomeTempEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TShopHomeTempEntity.class,params);
				for (TShopHomeTempEntity tShopHomeTemp : listTShopHomeTempEntitys) {
					tShopHomeTempService.save(tShopHomeTemp);
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
}
