package com.buss.activity.controller;
import java.io.IOException;
import java.util.ArrayList;
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
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.buss.activity.entity.TGiftRuleGoodsEntity;
import com.buss.activity.service.TGiftRuleGoodsServiceI;
import com.buss.goods.entity.TGoodsEntity;



/**   
 * @Title: Controller
 * @Description: 赠品规则商品
 * @author onlineGenerator
 * @date 2016-12-23 10:30:36
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGiftRuleGoodsController")
public class TGiftRuleGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGiftRuleGoodsController.class);

	@Autowired
	private TGiftRuleGoodsServiceI tGiftRuleGoodsService;
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
	 * 赠品规则商品列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("giftRuleId", request.getParameter("grId"));
		request.setAttribute("ruleStatus", request.getParameter("rSts"));
		request.setAttribute("retailerId", request.getParameter("rId"));
		return new ModelAndView("com/buss/activity/tGiftRuleGoodsList");
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
	public void datagrid(TGiftRuleGoodsEntity tGiftRuleGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String giftRuleId = request.getParameter("grId");
		try{
			StringBuffer sql = new StringBuffer("SELECT t.id,g.id as goodsId,g.small_pic as pic,g.goods_code as goodsCode,g.goods_name as goodsName,g.current_price as currentPrice")
			.append(" from t_gift_rule_goods t LEFT JOIN t_goods g on t.goods_id = g.id and g.status = 'A' ")
			.append(" where t.status = 'A' and t.gift_rule_id ='").append(giftRuleId).append("' ");
		StringBuffer countSql = new StringBuffer("select count(1) from t_gift_rule_goods t LEFT JOIN t_goods g on t.goods_id = g.id and g.status = 'A'  where t.status = 'A' ")
			.append(" and t.gift_rule_id ='").append(giftRuleId).append("' ");
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
	 * 删除赠品
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "赠品删除成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_gift_rule_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id = '")
			.append(id).append("'");
			tGiftRuleGoodsService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "赠品删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除赠品
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "赠品删除成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_gift_rule_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id in (");
			for(String id:ids.split(",")){
				sql.append("'").append(id).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(")");
			tGiftRuleGoodsService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "赠品删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量添加活动商品
	 * @return
	 */
	 @RequestMapping(params = "doBatchAdd")
	@ResponseBody
	public AjaxJson doBatchAdd(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "赠品添加成功";
		try{
			String goodsIds = request.getParameter("goodsIds");
			String giftRuleId = request.getParameter("giftRuleId");
			String retailerId = ResourceUtil.getRetailerId();
			for(String goodsId:goodsIds.split(",")){
				TGoodsEntity goods = this.systemService.get(TGoodsEntity.class, goodsId);
				TGiftRuleGoodsEntity tGiftRuleGoods = new TGiftRuleGoodsEntity();
				tGiftRuleGoods.setStatus(common.GlobalConstants.STATUS_ACTIVE);
				tGiftRuleGoods.setGiftRuleId(giftRuleId);
				tGiftRuleGoods.setGoodsId(goodsId);
				tGiftRuleGoods.setGoodsStoreId(goods.getRetailerId());
				tGiftRuleGoods.setGoodsStoreType(goods.getRetailerType());
				tGiftRuleGoods.setRetailerId(retailerId);
				tGiftRuleGoodsService.save(tGiftRuleGoods);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "赠品添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新赠品规则商品
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TGiftRuleGoodsEntity tGiftRuleGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "赠品规则商品更新成功";
		TGiftRuleGoodsEntity t = tGiftRuleGoodsService.get(TGiftRuleGoodsEntity.class, tGiftRuleGoods.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tGiftRuleGoods, t);
			tGiftRuleGoodsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "赠品规则商品更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 赠品规则商品新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TGiftRuleGoodsEntity tGiftRuleGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGiftRuleGoods.getId())) {
			tGiftRuleGoods = tGiftRuleGoodsService.flushEntity(TGiftRuleGoodsEntity.class, tGiftRuleGoods.getId());
			req.setAttribute("tGiftRuleGoodsPage", tGiftRuleGoods);
		}
		return new ModelAndView("com/buss/activity/tGiftRuleGoods-add");
	}
	/**
	 * 赠品规则商品编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TGiftRuleGoodsEntity tGiftRuleGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGiftRuleGoods.getId())) {
			tGiftRuleGoods = tGiftRuleGoodsService.flushEntity(TGiftRuleGoodsEntity.class, tGiftRuleGoods.getId());
			req.setAttribute("tGiftRuleGoodsPage", tGiftRuleGoods);
		}
		return new ModelAndView("com/buss/activity/tGiftRuleGoods-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tGiftRuleGoodsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TGiftRuleGoodsEntity tGiftRuleGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TGiftRuleGoodsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGiftRuleGoods, request.getParameterMap());
		List<TGiftRuleGoodsEntity> tGiftRuleGoodss = this.tGiftRuleGoodsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"赠品规则商品");
		modelMap.put(NormalExcelConstants.CLASS,TGiftRuleGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("赠品规则商品列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tGiftRuleGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TGiftRuleGoodsEntity tGiftRuleGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "赠品规则商品");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TGiftRuleGoodsEntity.class);
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
				List<TGiftRuleGoodsEntity> listTGiftRuleGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TGiftRuleGoodsEntity.class,params);
				for (TGiftRuleGoodsEntity tGiftRuleGoods : listTGiftRuleGoodsEntitys) {
					tGiftRuleGoodsService.save(tGiftRuleGoods);
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
