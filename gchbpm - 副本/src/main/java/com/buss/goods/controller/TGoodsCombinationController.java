package com.buss.goods.controller;
import java.io.IOException;
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
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
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

import com.buss.goods.entity.TGoodsAttrEntity;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.goods.service.TGoodsServiceI;
import com.buss.goods.vo.TGoodsGroupVo;



/**   
 * @Title: Controller
 * @Description: 商品表
 * @author onlineGenerator
 * @date 2016-03-17 16:58:48
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGoodsCombinationController")
public class TGoodsCombinationController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGoodsCombinationController.class);

	@Autowired
	private TGoodsServiceI tGoodsService;
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
	 * 商品表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String goods_status = request.getParameter("goods_status");
		if(StringUtil.isNotEmpty(goods_status)){
			request.setAttribute("goods_status", goods_status);
			if(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_4.equals(goods_status)){
				return new ModelAndView("com/buss/goods/tGoodsCombinationList-sell");
//			}else if(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_1.equals(goods_status)){
//				return new ModelAndView("com/buss/goods/tGoodsCombinationList-toApprove");
			}
			return new ModelAndView("com/buss/goods/tGoodsCombinationList");
		}
		return new ModelAndView("com/buss/goods/tGoodsCombinationList-all");
	}
	
	/**
	 * 零售商商品表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public ModelAndView retailerList(HttpServletRequest request) {
		String goods_status = request.getParameter("goods_status");
		if(StringUtil.isNotEmpty(goods_status)){
			request.setAttribute("goods_status", goods_status);
			if(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_4.equals(goods_status)){
				return new ModelAndView("com/buss/goods/tGoodsCombinationList-sell");
			}
			return new ModelAndView("com/buss/goods/tGoodsCombinationListOfRetailer");
		}
		return new ModelAndView("com/buss/goods/tGoodsCombinationList-all");
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
	public void datagrid(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, request.getParameterMap());
		try{
		//自定义追加查询条件
			cq.eq("goodsType", com.buss.goods.entity.TGoodsEntity.GOODS_TYPE_2);//组合
			cq.eq("newGoodsType", com.buss.goods.entity.TGoodsEntity.NEW_GOODS_TYPE_1);//旧模块商品
			String goods_status = request.getParameter("goods_status");
			if(StringUtil.isNotEmpty(goods_status)){
				cq.eq("goodsStatus", goods_status);
			}
			String retailerId = ResourceUtil.getRetailerId();
			if(StringUtil.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
				cq.eq("groupSource", TGoodsEntity.GROUP_SOURCE_1);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除商品表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tGoods = systemService.flushEntity(TGoodsEntity.class, tGoods.getId());
		message = "商品表删除成功";
		try{
			tGoodsService.delete(tGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除商品表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "商品表删除成功";
		try{
			for(String id:ids.split(",")){
				TGoodsEntity tGoods = systemService.flushEntity(TGoodsEntity.class, 
				id
				);
				tGoodsService.delete(tGoods);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "商品表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	
	/**
	 * 下架
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doDown")
	@ResponseBody
	public AjaxJson doDown(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "组合下架成功";
		try{
			tGoods = systemService.flushEntity(TGoodsEntity.class, tGoods.getId());
			tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_5);
			tGoods.setIsShow("N");
			tGoods.setUpdateDate(DateUtils.gettimestamp());
//			tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());
			tGoodsService.saveOrUpdate(tGoods);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "组合下架失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 上架
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doUp")
	@ResponseBody
	public AjaxJson doUp(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "组合上架成功";
		try{
			tGoods = systemService.flushEntity(TGoodsEntity.class, tGoods.getId());
			tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_4);
			tGoods.setUpdateDate(DateUtils.gettimestamp());
			tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());
			Long sortNum = Long.valueOf(DateUtils.date2Str(DateUtils.gettimestamp(), DateUtils.yyyymmddhhmmss));
			tGoods.setSortNum(sortNum);
			tGoodsService.saveOrUpdate(tGoods);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "组合上架失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 审核不通过
	 * 
	 * @param id
	 * @return
	 */
	/*@RequestMapping(params = "doNotAudit")
	@ResponseBody
	public AjaxJson doNotAudit(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "组合审核不通过成功";
		try{
			tGoods = systemService.flushEntity(TGoodsEntity.class, tGoods.getId());
			tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_2);
			tGoods.setUpdateDate(DateUtils.gettimestamp());
//			tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());
			tGoodsService.saveOrUpdate(tGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "组合审核不通过失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}*/
	
	

	/**
	 * 商品表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TGoodsEntity tGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGoods.getId())) {
			tGoods = tGoodsService.flushEntity(TGoodsEntity.class, tGoods.getId());
		}
		TSUser user = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_01.equals(user.getUserType())){//后台人员
			req.setAttribute("groupSource", TGoodsEntity.GROUP_SOURCE_2);
		}else{//零售商或者他录的用户
			req.setAttribute("groupSource", TGoodsEntity.GROUP_SOURCE_1);
		}
		req.setAttribute("tGoodsPage", tGoods);
		String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
		List<TSCategoryEntity> categoryList = systemService.findListbySql(sql);
		req.setAttribute("categoryList", categoryList);
		return new ModelAndView("com/buss/goods/tGoodsCombination-add");
	}
	
	/**
	 * 加载明细列表[关键词明细]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tGoodsAttrDetails")
	public ModelAndView tGoodsAttrDetails(String id, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = id;
		//===================================================================================
		//查询-订单明细
	    String hql0 = "from TGoodsAttrEntity where 1 = 1 and status = 'A' AND goodsId = ? ";
	    try{
	    	List<TGoodsAttrEntity> tGoodsAttrDetailsList = systemService.findHql(hql0,id0);
			req.setAttribute("tGoodsAttrDetails", tGoodsAttrDetailsList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/buss/goods/tGoodsAttrDetails");
	}
	
	/**
	 * 商品表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TGoodsEntity tGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGoods.getId())) {
			tGoods = tGoodsService.flushEntity(TGoodsEntity.class, tGoods.getId());
//			tGoods.settGoodsAttrDetails(tGoodsService.getGoodsAttrs(tGoods.getId()));
//			tGoods.settGoodsStoreDetails(tGoodsService.getGoodsStores(tGoods.getId()));
			tGoods.settGoodsPicDetails(tGoodsService.getGoodsPics(tGoods.getId()));
			tGoods.setDesc(tGoodsService.getGoodsDesc(tGoods.getId()));
			int num = this.getNumOfMainPics(tGoods);
			req.setAttribute("mainPicsNum", num);
			req.setAttribute("tGoodsPage", tGoods);
			String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null ";
			String sql2 = "select id,name from t_s_category where status = 'A' and parent_id ='"+tGoods.getTopCategoryId()+"'";
			String sql3 = "select id,name from t_s_category where status = 'A' and parent_id ='"+tGoods.getSubCategoryId()+"'";
			List<TSCategoryEntity> categoryList = systemService.findListbySql(sql);
			List<TSCategoryEntity> subCategoryList = systemService.findListbySql(sql2);
			List<TSCategoryEntity> thirdCategoryList = systemService.findListbySql(sql3);
			req.setAttribute("categoryList", categoryList);
			req.setAttribute("subCategoryList", subCategoryList);
			req.setAttribute("thirdCategoryList", thirdCategoryList);
			TSUser user = ResourceUtil.getSessionUserName();
			if(common.GlobalConstants.USER_TYPE_01.equals(user.getUserType())){
				req.setAttribute("listPage", "list");
			}else if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())||common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				req.setAttribute("listPage", "retailerList");
			}
		}
		return new ModelAndView("com/buss/goods/tGoodsCombination-update");
	}
	
	/**
	 * 商品表编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TGoodsEntity tGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGoods.getId())) {
			tGoods = tGoodsService.flushEntity(TGoodsEntity.class, tGoods.getId());
//			tGoods.settGoodsAttrDetails(tGoodsService.getGoodsAttrs(tGoods.getId()));
			tGoods.settGoodsPicDetails(tGoodsService.getGoodsPics(tGoods.getId()));
			tGoods.setDesc(tGoodsService.getGoodsDesc(tGoods.getId()));
			req.setAttribute("tGoodsPage", tGoods);
			TSCategoryEntity category = systemService.get(TSCategoryEntity.class, tGoods.getTopCategoryId());
			req.setAttribute("category", category.getName());
			TSCategoryEntity subCategory = systemService.get(TSCategoryEntity.class, tGoods.getSubCategoryId());
			req.setAttribute("subCategory", subCategory.getName());
			TSCategoryEntity thridCategory = systemService.get(TSCategoryEntity.class, tGoods.getThridCategoryId());
			req.setAttribute("thridCategory", thridCategory.getName());
		}
		return new ModelAndView("com/buss/goods/tGoodsCombination-view");
	}
	
	/**
	 * 商品表编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goSetAd")
	public ModelAndView goSetAd(String id,String coverPic, HttpServletRequest req) {
		req.setAttribute("id", req.getParameter("id"));
		req.setAttribute("coverPic", req.getParameter("coverPic"));
		return new ModelAndView("com/buss/goods/tGoodsCombination-ad");
	}
	
	/**
	 * 设置广告位
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doSaveAd")
	@ResponseBody
	public AjaxJson doSaveAd(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "设置广告位成功";
		try{
			TGoodsEntity tgoods = systemService.get(TGoodsEntity.class, tGoods.getId());
			tgoods.setCoverPic(tGoods.getCoverPic());
			tgoods.setIsShow("Y");
			tgoods.setUpdateDate(DateUtils.gettimestamp());
//			tgoods.setGoodsUpdateTime(DateUtils.gettimestamp());
			tGoodsService.saveOrUpdate(tgoods);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "设置广告位失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 取消广告位
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "goCancelAd")
	@ResponseBody
	public AjaxJson goCancelAd(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "取消广告位成功";
		try{
			tGoods = systemService.flushEntity(TGoodsEntity.class, tGoods.getId());
			tGoods.setIsShow("N");
			tGoods.setCoverPic(null);
			tGoods.setUpdateDate(DateUtils.gettimestamp());
//			tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());
			tGoodsService.saveOrUpdate(tGoods);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "取消广告位失败";
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
		req.setAttribute("controller_name","tGoodsCombinationController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TGoodsEntity tGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, request.getParameterMap());
		List<TGoodsEntity> tGoodss = this.tGoodsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"商品表");
		modelMap.put(NormalExcelConstants.CLASS,TGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 下载excel模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "downloadTemp")
	public ModelAndView downloadTemp( HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\templates");
		String fileName = "组合导入模板-衣.xlsx";
		String filePath = path + "\\" + fileName;
		FileUtils.downLoadFile(filePath, response, request, fileName, "xlsx");
		return null;
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
			params.setTitleRows(0);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TGoodsGroupVo> listTGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TGoodsGroupVo.class,params);
				if(listTGoodsEntitys.size()<501){
					String msg = tGoodsService.saveBatchOfGroup(listTGoodsEntitys);
					if(StringUtil.isEmpty(msg)){
						j.setMsg("文件导入成功！");
					}else{
						j.setMsg(msg);
					}
				}else{
					j.setMsg("单次上传不能超过500条，请分批上传");
				}
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
	
	public int getNumOfMainPics(TGoodsEntity tGoods){
		int num = 0;
		if(StringUtil.isNotEmpty(tGoods.getPicOne())){
			num++;
		}
		if(StringUtil.isNotEmpty(tGoods.getPicTwo())){
			num++;
		}
		if(StringUtil.isNotEmpty(tGoods.getPicThree())){
			num++;
		}
		if(StringUtil.isNotEmpty(tGoods.getPicFour())){
			num++;
		}
		if(StringUtil.isNotEmpty(tGoods.getPicFive())){
			num++;
		}
		return num;
	}
}
