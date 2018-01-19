package com.buss.base.controller;
import com.buss.base.entity.TBrandSellAreaEntity;
import com.buss.base.service.TBrandSellAreaServiceI;

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
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.TerritoryServiceI;
import org.jeecgframework.web.system.vo.AreaVo;
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
 * @Description: 品牌可售区域
 * @author onlineGenerator
 * @date 2017-03-17 20:01:28
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBrandSellAreaController")
public class TBrandSellAreaController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBrandSellAreaController.class);

	@Autowired
	private TBrandSellAreaServiceI tBrandSellAreaService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TerritoryServiceI territoryService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 品牌可售区域列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request,String brandId) {
//		String retailerId = ResourceUtil.getRetailerId();
//		checkInit(brandId,retailerId);//检查是否有初始化品牌可售区域
//		Long is_all_province = this.tBrandSellAreaService.getCountForJdbc("select count(1) from t_brand_sell_area where status = 'A' and is_all_province = '1' and retailer_id = '"+retailerId+"'");
		request.setAttribute("brandId", brandId);
		return new ModelAndView("com/buss/base/tBrandSellAreaList");
	}

	/**检查并设置默认品牌可售区域
	 * @param brandId
	 * @param retailerId
	 */
//	private void checkInit(String brandId,String retailerId) {
//		Long count = this.tBrandSellAreaService.getCountForJdbc("select count(1) from t_brand_sell_area where status = 'A' and retailer_id = '"+retailerId+"'");
//		if(count==0){//初始化一条记录（默认全国区域可售）
//			TBrandSellAreaEntity tBrandSellArea = new TBrandSellAreaEntity();
//			tBrandSellArea.setStatus(common.GlobalConstants.STATUS_ACTIVE);
//			tBrandSellArea.setIsAllProvince("1");
//			tBrandSellArea.setBrandId(brandId);
//			tBrandSellArea.setRetailerId(retailerId);
//			this.tBrandSellAreaService.save(tBrandSellArea);
//		}
//	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBrandSellAreaEntity tBrandSellArea,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TBrandSellAreaEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBrandSellArea, request.getParameterMap());
		try{
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sql = new StringBuffer("SELECT a.id,(SELECT territoryname from t_s_territory where id = province_id) provinceName,")
					.append(" a.province_id provinceId,a.city_id cityId,IFNULL((SELECT territoryname from t_s_territory where id = city_id),'') cityName")
					.append(" from t_brand_sell_area a where `status` = 'A' and a.is_all_province = '0'")
					.append(" and a.retailer_id = '").append(retailerId).append("'");
			StringBuffer countSql = new StringBuffer("select count(1) from t_brand_sell_area a where `status` = 'A' and a.is_all_province = '0'")
			.append(" and a.retailer_id = '").append(retailerId).append("'");
//			cq.eq("retailerId", retailerId);
//			cq.eq("isAllProvince", "0");//默认显示零售商自己添加的可售区域
			List<Map<String, Object>> resultList = systemService.findForJdbc( sql.toString(), dataGrid.getPage(), dataGrid.getRows());
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
//		cq.add();
//		this.tBrandSellAreaService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除品牌可售区域,如果全部删除后会默认初始化一条全国可售的记录
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBrandSellAreaEntity tBrandSellArea, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBrandSellArea = systemService.flushEntity(TBrandSellAreaEntity.class, tBrandSellArea.getId());
		message = "品牌可售区域删除成功";
		try{
			tBrandSellAreaService.delSellArea(tBrandSellArea);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "品牌可售区域删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除品牌可售区域，如果全部删除后会默认初始化一条全国可售的记录
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,String brandId,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "品牌可售区域删除成功";
		try{
				tBrandSellAreaService.batchDelSellArea(ids,brandId);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "品牌可售区域删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加品牌可售区域
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBrandSellAreaEntity tBrandSellArea, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "品牌可售区域添加成功";
		try{
			if(Utility.isEmpty(tBrandSellArea.getCityId())){
				tBrandSellArea.setCityId(null);
			}
			this.tBrandSellAreaService.saveSellArea(tBrandSellArea);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "品牌可售区域添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新品牌可售区域
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBrandSellAreaEntity tBrandSellArea, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "品牌可售区域更新成功";
		TBrandSellAreaEntity t = tBrandSellAreaService.get(TBrandSellAreaEntity.class, tBrandSellArea.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBrandSellArea, t);
			tBrandSellAreaService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "品牌可售区域更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 品牌可售区域新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(String brandId, HttpServletRequest req) {
		req.setAttribute("brandId", brandId);
		List<AreaVo> list = territoryService.getAreaList();
		req.setAttribute("areaList", list);
		return new ModelAndView("com/buss/base/tBrandSellArea-add");
	}
	/**
	 * 品牌可售区域编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBrandSellAreaEntity tBrandSellArea, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBrandSellArea.getId())) {
			tBrandSellArea = tBrandSellAreaService.flushEntity(TBrandSellAreaEntity.class, tBrandSellArea.getId());
			req.setAttribute("tBrandSellAreaPage", tBrandSellArea);
		}
		return new ModelAndView("com/buss/base/tBrandSellArea-update");
	}
	
}
