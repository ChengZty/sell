package com.buss.activity.controller;
import com.buss.activity.entity.TFinActivityGoodsEntity;
import com.buss.activity.service.TFinActivityGoodsServiceI;
import com.buss.bill.entity.TFinActivityEntity;
import com.buss.goods.entity.TGoodsEntity;

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
 * @Description: 活动奖励商品
 * @author onlineGenerator
 * @date 2016-11-26 19:30:22
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tFinActivityGoodsController")
public class TFinActivityGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TFinActivityGoodsController.class);

	@Autowired
	private TFinActivityGoodsServiceI tFinActivityGoodsService;
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
	 * 活动奖励商品列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("finActId", request.getParameter("fin_act_Id"));
		String platformType = request.getParameter("pType");
		String isSame = "0";//默认添加活动奖励和查看商品的不是同一个角色
		TSUser user = ResourceUtil.getSessionUserName();
		if(user.getUserType().equals(common.GlobalConstants.USER_TYPE_01)&&TFinActivityEntity.PLATFORM_TYPE_1.equals(platformType)){
			isSame = "1";
		}else if(!user.getUserType().equals(common.GlobalConstants.USER_TYPE_01)&&TFinActivityEntity.PLATFORM_TYPE_2.equals(platformType)){
			isSame = "1";
		}
		request.setAttribute("activityStatus", request.getParameter("aSts"));
		request.setAttribute("retailerId", request.getParameter("rId"));
		request.setAttribute("platformType", platformType);
		request.setAttribute("isSame", isSame);
		return new ModelAndView("com/buss/activity/tFinActivityGoodsList");
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
	public void datagrid(TFinActivityGoodsEntity tFinActivityGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
		//自定义追加查询条件
			String finActId = request.getParameter("fin_Act_Id");
			StringBuffer sql = new StringBuffer("SELECT t.id,g.id as goodsId,g.small_pic as pic,g.goods_code as goodsCode,g.goods_name as goodsName,g.current_price as currentPrice")
				.append(" from t_fin_activity_goods t LEFT JOIN t_goods g on t.goods_id = g.id and g.status = 'A' ")
				.append(" where t.status = 'A' and t.fin_act_id ='").append(finActId).append("' ");
			StringBuffer countSql = new StringBuffer("select count(1) from t_fin_activity_goods t LEFT JOIN t_goods g on t.goods_id = g.id and g.status = 'A' where t.status = 'A' ")
				.append(" and t.fin_act_id ='").append(finActId).append("' ");
			String sortName = dataGrid.getSort();
			if(Utility.isEmpty(sortName)){
				sql.append(" ORDER BY t.create_date desc");
			}else{
				sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
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
	 * 删除活动奖励商品
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动奖励商品删除成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_fin_activity_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id = '")
			.append(id).append("'");
			tFinActivityGoodsService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动奖励商品删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除活动奖励商品
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "活动奖励商品删除成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_fin_activity_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id in (");
			for(String id:ids.split(",")){
				sql.append("'").append(id).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(")");
			tFinActivityGoodsService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动奖励商品删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加活动奖励商品
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TFinActivityGoodsEntity tFinActivityGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动奖励商品添加成功";
		try{
			tFinActivityGoodsService.save(tFinActivityGoods);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动奖励商品添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新活动奖励商品
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TFinActivityGoodsEntity tFinActivityGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动奖励商品更新成功";
		TFinActivityGoodsEntity t = tFinActivityGoodsService.get(TFinActivityGoodsEntity.class, tFinActivityGoods.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tFinActivityGoods, t);
			tFinActivityGoodsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "活动奖励商品更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 活动奖励商品新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TFinActivityGoodsEntity tFinActivityGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tFinActivityGoods.getId())) {
			tFinActivityGoods = tFinActivityGoodsService.flushEntity(TFinActivityGoodsEntity.class, tFinActivityGoods.getId());
			req.setAttribute("tFinActivityGoodsPage", tFinActivityGoods);
		}
		return new ModelAndView("com/buss/activity/tFinActivityGoods-add");
	}
	/**
	 * 活动奖励商品编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TFinActivityGoodsEntity tFinActivityGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tFinActivityGoods.getId())) {
			tFinActivityGoods = tFinActivityGoodsService.flushEntity(TFinActivityGoodsEntity.class, tFinActivityGoods.getId());
			req.setAttribute("tFinActivityGoodsPage", tFinActivityGoods);
		}
		return new ModelAndView("com/buss/activity/tFinActivityGoods-update");
	}
	
	/**
	 * 批量添加活动商品
	 * @return
	 */
	 @RequestMapping(params = "doBatchAdd")
	@ResponseBody
	public AjaxJson doBatchAdd(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "活动商品添加成功";
		try{
			String msg = this.tFinActivityGoodsService.doCheckAndBatchAdd(request);
			if(Utility.isNotEmpty(msg)){
				message = msg;
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动商品添加失败";
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
		req.setAttribute("controller_name","tFinActivityGoodsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TFinActivityGoodsEntity tFinActivityGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFinActivityGoodsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinActivityGoods, request.getParameterMap());
		List<TFinActivityGoodsEntity> tFinActivityGoodss = this.tFinActivityGoodsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"活动奖励商品");
		modelMap.put(NormalExcelConstants.CLASS,TFinActivityGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("活动奖励商品列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tFinActivityGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TFinActivityGoodsEntity tFinActivityGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "活动奖励商品");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TFinActivityGoodsEntity.class);
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
				List<TFinActivityGoodsEntity> listTFinActivityGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TFinActivityGoodsEntity.class,params);
				for (TFinActivityGoodsEntity tFinActivityGoods : listTFinActivityGoodsEntitys) {
					tFinActivityGoodsService.save(tFinActivityGoods);
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
