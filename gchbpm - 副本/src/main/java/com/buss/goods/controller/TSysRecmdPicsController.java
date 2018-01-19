package com.buss.goods.controller;
import com.buss.goods.entity.TSysRecmdPicsEntity;
import com.buss.goods.service.TSysRecmdPicsServiceI;

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
 * @Description: t_sys_recmd_pics
 * @author onlineGenerator
 * @date 2016-11-24 20:51:28
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSysRecmdPicsController")
public class TSysRecmdPicsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSysRecmdPicsController.class);

	@Autowired
	private TSysRecmdPicsServiceI tSysRecmdPicsService;
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
	 * t_sys_recmd_pics列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String goodsId = request.getParameter("goods_Id");
		request.setAttribute("goods_Id", goodsId);
		return new ModelAndView("com/buss/goods/tSysRecmdPicsList");
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
	public void datagrid(TSysRecmdPicsEntity tSysRecmdPics,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			//自定义追加查询条件
			String goodsId = request.getParameter("goods_Id");
				String sql ="select p.id,g.goods_code goodsCode,g.goods_name goodsName,g.small_pic smallPic,g.goods_type goodsType,'0' delFlag"
					+" from t_sys_recmd_pics p LEFT JOIN t_goods g on p.recmd_goods_id = g.id where p.status = 'A' and p.goods_id='"+goodsId+"'";
				String countSql = "select count(1) from t_sys_recmd_pics p LEFT JOIN t_goods g on p.recmd_goods_id = g.id where p.status = 'A' and p.goods_id='"+goodsId+"'";
				String goodsCode = request.getParameter("goodsCode");
				String goodsName = request.getParameter("goodsName");
				if(!Utility.isEmpty(goodsCode)){
					sql +=" and g.goods_code like '%"+goodsCode+"%'";
					countSql +=" and g.goods_code like '%"+goodsCode+"%'";
				}
				if(!Utility.isEmpty(goodsName)){
					sql +=" and g.goods_name like '%"+goodsName+"%'";
					countSql +=" and g.goods_name like '%"+goodsName+"%'";
				}
//				List<Map<String, Object>> resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
				List<TSysRecmdPicsEntity> list = systemService.findObjForJdbc(sql, dataGrid.getPage(), dataGrid.getRows(), TSysRecmdPicsEntity.class);
				int total = 0;
				if(!Utility.isEmpty(list)){
					total = this.systemService.getCountForJdbc(countSql).intValue();
				}
				dataGrid.setResults(list);
				dataGrid.setTotal(total);
			}catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除t_sys_recmd_pics
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSysRecmdPicsEntity tSysRecmdPics, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSysRecmdPics = systemService.flushEntity(TSysRecmdPicsEntity.class, tSysRecmdPics.getId());
		message = "t_sys_recmd_pics删除成功";
		try{
			tSysRecmdPicsService.delete(tSysRecmdPics);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "t_sys_recmd_pics删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除t_sys_recmd_pics
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "t_sys_recmd_pics删除成功";
		try{
			for(String id:ids.split(",")){
				TSysRecmdPicsEntity tSysRecmdPics = systemService.flushEntity(TSysRecmdPicsEntity.class, 
				id
				);
				tSysRecmdPicsService.delete(tSysRecmdPics);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "t_sys_recmd_pics删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加t_sys_recmd_pics
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSysRecmdPicsEntity tSysRecmdPics, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "t_sys_recmd_pics添加成功";
		try{
			tSysRecmdPicsService.save(tSysRecmdPics);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "t_sys_recmd_pics添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新t_sys_recmd_pics
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSysRecmdPicsEntity tSysRecmdPics, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "t_sys_recmd_pics更新成功";
		TSysRecmdPicsEntity t = tSysRecmdPicsService.get(TSysRecmdPicsEntity.class, tSysRecmdPics.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSysRecmdPics, t);
			tSysRecmdPicsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "t_sys_recmd_pics更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * t_sys_recmd_pics新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSysRecmdPicsEntity tSysRecmdPics, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSysRecmdPics.getId())) {
			tSysRecmdPics = tSysRecmdPicsService.flushEntity(TSysRecmdPicsEntity.class, tSysRecmdPics.getId());
			req.setAttribute("tSysRecmdPicsPage", tSysRecmdPics);
		}
		return new ModelAndView("com/buss/goods/tSysRecmdPics-add");
	}
	/**
	 * t_sys_recmd_pics编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSysRecmdPicsEntity tSysRecmdPics, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSysRecmdPics.getId())) {
			tSysRecmdPics = tSysRecmdPicsService.flushEntity(TSysRecmdPicsEntity.class, tSysRecmdPics.getId());
			req.setAttribute("tSysRecmdPicsPage", tSysRecmdPics);
		}
		return new ModelAndView("com/buss/goods/tSysRecmdPics-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tSysRecmdPicsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSysRecmdPicsEntity tSysRecmdPics,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSysRecmdPicsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSysRecmdPics, request.getParameterMap());
		List<TSysRecmdPicsEntity> tSysRecmdPicss = this.tSysRecmdPicsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"t_sys_recmd_pics");
		modelMap.put(NormalExcelConstants.CLASS,TSysRecmdPicsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("t_sys_recmd_pics列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tSysRecmdPicss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSysRecmdPicsEntity tSysRecmdPics,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "t_sys_recmd_pics");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TSysRecmdPicsEntity.class);
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
				List<TSysRecmdPicsEntity> listTSysRecmdPicsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSysRecmdPicsEntity.class,params);
				for (TSysRecmdPicsEntity tSysRecmdPics : listTSysRecmdPicsEntitys) {
					tSysRecmdPicsService.save(tSysRecmdPics);
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
