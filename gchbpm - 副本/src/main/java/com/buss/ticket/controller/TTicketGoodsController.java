package com.buss.ticket.controller;
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

import com.buss.goods.entity.TGoodsEntity;
import com.buss.ticket.entity.TTicketGoodsEntity;
import com.buss.ticket.service.TTicketGoodsServiceI;



/**   
 * @Title: Controller
 * @Description: 优惠券商品
 * @author onlineGenerator
 * @date 2016-12-13 10:24:14
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tTicketGoodsController")
public class TTicketGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TTicketGoodsController.class);

	@Autowired
	private TTicketGoodsServiceI tTicketGoodsService;
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
	 * 优惠券商品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("ticket_Id", request.getParameter("ticket_Id"));
		request.setAttribute("tktStatus", request.getParameter("tktStatus"));//审核状态
		String type = request.getParameter("type");
		String isView = request.getParameter("isView");//是否是查看
		request.setAttribute("type", type);//类型
		request.setAttribute("isView", isView);//类型
		if(TTicketGoodsEntity.TYPE_GOODS.equals(type)){
			request.setAttribute("title", "商品列表");
		}else if(TTicketGoodsEntity.TYPE_BRANDS.equals(type)){
			request.setAttribute("title", "品牌列表");
		}
		request.setAttribute("rId", request.getParameter("rId"));
		return new ModelAndView("com/buss/ticket/tTicketGoodsList");
	}

	/**
	 * easyui 商品列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TTicketGoodsEntity tTicketGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String ticketId = request.getParameter("ticket_id");
			StringBuffer sql = new StringBuffer("SELECT t.id,g.id as goodsId,g.small_pic as pic,g.goods_code as goodsCode,g.goods_name as goodsName")
			.append(" from t_ticket_goods t LEFT JOIN t_goods g on t.goods_id = g.id ")
			.append(" where t.status = 'A' and t.ticket_id ='").append(ticketId).append("' and t.type='1'");
		StringBuffer countSql = new StringBuffer("select count(1) from t_ticket_goods t LEFT JOIN t_goods g on t.goods_id = g.id  where t.status = 'A' ")
			.append(" and t.ticket_id ='").append(ticketId).append("' and t.type='1'");
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
	 * easyui 品牌列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridBrand")
	public void datagridBrand(TTicketGoodsEntity tTicketGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String ticketId = request.getParameter("ticket_id");
			StringBuffer sql = new StringBuffer("SELECT t.id,b.id as goodsId,b.brand_pic as pic,b.brand_name as goodsName")
			.append(" from t_ticket_goods t LEFT JOIN base_brand b on t.goods_id = b.id ")
			.append(" where t.status = 'A' and t.ticket_id ='").append(ticketId).append("' and t.type='2'");
		StringBuffer countSql = new StringBuffer("select count(1) from t_ticket_goods t LEFT JOIN base_brand b on t.goods_id = b.id  where t.status = 'A' ")
			.append(" and t.ticket_id ='").append(ticketId).append("' and t.type='1'");
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
	 * 删除优惠券商品
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "优惠券商品删除成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_ticket_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id = '")
			.append(id).append("'");
			tTicketGoodsService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券商品删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除优惠券商品
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "优惠券商品删除成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_ticket_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id in (");
			for(String id:ids.split(",")){
				sql.append("'").append(id).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(")");
			tTicketGoodsService.updateBySqlString(sql.toString());
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券商品删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加优惠券商品
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TTicketGoodsEntity tTicketGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "优惠券商品添加成功";
		try{
			tTicketGoodsService.save(tTicketGoods);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券商品添加失败";
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
		message = "优惠券商品添加成功";
		try{
			String goodsIds = request.getParameter("goodsIds");
			String ticketId = request.getParameter("ticketId");
			String type = request.getParameter("type");
			String retailerId = ResourceUtil.getRetailerId();
			for(String goodsId:goodsIds.split(",")){
				TTicketGoodsEntity tTicketGoods = new TTicketGoodsEntity();
				tTicketGoods.setStatus(common.GlobalConstants.STATUS_ACTIVE);
				tTicketGoods.setTicketId(ticketId);
				tTicketGoods.setGoodsId(goodsId);
				tTicketGoods.setRetailerId(retailerId);
				tTicketGoods.setType(type);
				tTicketGoodsService.save(tTicketGoods);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "优惠券商品添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新优惠券商品
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TTicketGoodsEntity tTicketGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "优惠券商品更新成功";
		TTicketGoodsEntity t = tTicketGoodsService.get(TTicketGoodsEntity.class, tTicketGoods.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tTicketGoods, t);
			tTicketGoodsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "优惠券商品更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 优惠券商品新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TTicketGoodsEntity tTicketGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tTicketGoods.getId())) {
			tTicketGoods = tTicketGoodsService.flushEntity(TTicketGoodsEntity.class, tTicketGoods.getId());
			req.setAttribute("tTicketGoodsPage", tTicketGoods);
		}
		return new ModelAndView("com/buss/ticket/tTicketGoods-add");
	}
	/**
	 * 优惠券商品编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TTicketGoodsEntity tTicketGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tTicketGoods.getId())) {
			tTicketGoods = tTicketGoodsService.flushEntity(TTicketGoodsEntity.class, tTicketGoods.getId());
			req.setAttribute("tTicketGoodsPage", tTicketGoods);
		}
		return new ModelAndView("com/buss/ticket/tTicketGoods-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tTicketGoodsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TTicketGoodsEntity tTicketGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TTicketGoodsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tTicketGoods, request.getParameterMap());
		List<TTicketGoodsEntity> tTicketGoodss = this.tTicketGoodsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"优惠券商品");
		modelMap.put(NormalExcelConstants.CLASS,TTicketGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("优惠券商品列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tTicketGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TTicketGoodsEntity tTicketGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "优惠券商品");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TTicketGoodsEntity.class);
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
				List<TTicketGoodsEntity> listTTicketGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TTicketGoodsEntity.class,params);
				for (TTicketGoodsEntity tTicketGoods : listTTicketGoodsEntitys) {
					tTicketGoodsService.save(tTicketGoods);
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
	
	/**
	 * 查询优惠券 商品/品牌的总个数
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "getTotalNum")
	@ResponseBody
	public AjaxJson getTotalNum(TTicketGoodsEntity tTicketGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			Long n = this.tTicketGoodsService.getCountForJdbc("select count(1) from t_ticket_goods where ticket_id='"+tTicketGoods.getTicketId()
					+"' and type='"+tTicketGoods.getType()+"' and status = 'A'");
			j.setObj(n);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return j;
	}
	
	
	
}
