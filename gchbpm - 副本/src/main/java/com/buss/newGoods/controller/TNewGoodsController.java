package com.buss.newGoods.controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.LogUtil;
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
import org.jeecgframework.web.system.service.CategoryServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

import com.buss.base.entity.TProductParamsEntity;
import com.buss.base.service.TProductParamsServiceI;
import com.buss.bill.entity.TFinActivityEntity;
import com.buss.goods.entity.TGoodsDescEntity;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.goods.entity.TGoodsStoreEntity;
import com.buss.goods.entity.TProductInfoEntity;
import com.buss.goods.entity.TSpecHeadersEntity;
import com.buss.goods.entity.TVisibleCategriesEntity;
import com.buss.goods.service.TGoodsServiceI;
import com.buss.goods.vo.GoodsStockImportVo;
import com.buss.goods.vo.GoodsWordsImportVo;
import com.buss.goods.vo.LowestPriceImportVo;
import com.buss.goods.vo.TGoodsSimpleVo;
import com.buss.goods.vo.TNewGoodsImportVo;
import com.buss.newGoods.entity.TGuideRecommendDetailEntity;
import com.buss.visibleGoods.entity.TRetailerGoodsConditionsEntity;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 新商品表
 * @author onlineGenerator
 * @date 2016-03-17 20:05:06
 * @version V1.0   
 *
 */
/**
 * @author lenovo
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tNewGoodsController")
public class TNewGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TNewGoodsController.class);

	@Autowired
	private TGoodsServiceI tGoodsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CategoryServiceI categoryService;
	@Autowired
	private TProductParamsServiceI tProductParamsService;
	@Resource  
	private RabbitTemplate rabbitTemplate; 
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 系统商品表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String goods_status = request.getParameter("goods_status");
		if(StringUtil.isNotEmpty(goods_status)){
			request.setAttribute("goods_status", goods_status);
			return new ModelAndView("com/buss/newGoods/tNewGoodsList");
		}
		return new ModelAndView("com/buss/newGoods/tNewGoodsList-all");
	}
	
	/**
	 * g+零售商商品表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public ModelAndView retailerList(HttpServletRequest request) {
		String goods_status = request.getParameter("goods_status");
		if(StringUtil.isNotEmpty(goods_status)){
			request.setAttribute("goods_status", goods_status);
			return new ModelAndView("com/buss/newGoods/tNewGoodsListOfRetailer");
		}
		String retailerEdition = ResourceUtil.getRetailerEdition();
		request.setAttribute("retailerEdition", retailerEdition);//零售商版本
//		if(TSUser.RETAILER_TYPE_REAL.equals(retailerType)){//零售商
//			return new ModelAndView("com/buss/newGoods/tNewGoodsMain"); 没有可见商品了，直接跳入商品列表
			return new ModelAndView("com/buss/newGoods/tNewGoodsListOfRetailer-all");
//		}else{//云商
//			return new ModelAndView("com/buss/newGoods/tNewGoodsListOfRetailer-all");
//		}
	}
	
	/**
	 * d+零售商商品表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retList")
	public ModelAndView retList(HttpServletRequest request) {
		String goods_status = request.getParameter("goods_status");
		if(StringUtil.isNotEmpty(goods_status)){
			request.setAttribute("goods_status", goods_status);
			return new ModelAndView("com/buss/newGoods/tNewGoodsListOfRetailer");
		}
		String retailerEdition = ResourceUtil.getRetailerEdition();
		request.setAttribute("retailerEdition", retailerEdition);//零售商版本
		return new ModelAndView("com/buss/newGoods/tNewGoodsListOfRetailer-all");
	}
	
	/**
	 * 零售商全部商品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerAllGoodsList")
	public ModelAndView retailerAllGoodsList(HttpServletRequest request) {
		return new ModelAndView("com/buss/newGoods/tNewGoodsListOfRetailer-all");
	}
	
	/**
	 * 零售商全部可见商品（含云仓）列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerAllVisiableGoodsList")
	public ModelAndView retailerAllVisiableGoodsList(HttpServletRequest request) {
		return new ModelAndView("com/buss/newGoods/tNewGoodsListOfRetailerAndOther");
	}
	
	
	/**
	 * 管家推荐商品表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tNewGoodsListForGuideRem")
	public ModelAndView tNewGoodsListForGuideRem(HttpServletRequest request) {
		TSUser user =ResourceUtil.getSessionUserName();
		request.setAttribute("userType", user.getUserType());
		return new ModelAndView("com/buss/newGoods/tNewGoodsListForGuideRem");
	}
	
	/**
	 * 系统推荐商品表列表 页面跳转（暂时未做）
	 * @return
	 */
	@RequestMapping(params = "tNewGoodsListForSysRem")
	public ModelAndView tNewGoodsListForSysRem(HttpServletRequest request) {
		TSUser user =ResourceUtil.getSessionUserName();
		request.setAttribute("userType", user.getUserType());
		return new ModelAndView("com/buss/newGoods/tNewGoodsListForSysRem");
	}
	
	/**
	 * 资讯查询单品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tSingleNewGoodsListOfNews")
	public ModelAndView tSingleNewGoodsListOfNews(HttpServletRequest req) {
		req.setAttribute("newsId", req.getParameter("newsId"));
		return new ModelAndView("com/buss/newGoods/tSingleNewGoodsListOfNews");
	}
	/**
	 * 活动奖励查询单品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "finActGoodsList")
	public ModelAndView finActGoodsList(HttpServletRequest req) {
		req.setAttribute("finActId", req.getParameter("finActId"));
		req.setAttribute("platformType", req.getParameter("pType"));
		req.setAttribute("retailerId", req.getParameter("rId"));
		return new ModelAndView("com/buss/newGoods/tSingleNewGoodsListOfFinAct");
	}
	/**
	 * 赠品查询单品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "giftRuleGoodsList")
	public ModelAndView giftRuleGoodsList(HttpServletRequest req) {
		req.setAttribute("giftRuleId", req.getParameter("giftRuleId"));
		req.setAttribute("retailerId", req.getParameter("rId"));
		return new ModelAndView("com/buss/newGoods/tSingleNewGoodsListOfGift");
	}
	/**
	 * 优惠券查询单品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "ticketGoodsList")
	public ModelAndView ticketGoodsList(HttpServletRequest req) {
		req.setAttribute("ticketId", req.getParameter("ticketId"));
		req.setAttribute("rId", req.getParameter("rId"));//零售商ID
		return new ModelAndView("com/buss/newGoods/tSingleNewGoodsListOfTicket");
	}
	
	/**
	 * 商品列表(商品话术菜单)
	 * @return
	 */
	@RequestMapping(params = "wordsGoodsList")
	public ModelAndView wordsGoodsList(HttpServletRequest req) {
		TSUser user =ResourceUtil.getSessionUserName();
		req.setAttribute("userType", user.getUserType());
		return new ModelAndView("com/buss/newGoods/tWordsGoodsList");
	}

	/**
	 * 单品TAB列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tNewGoodsTabs")
	public ModelAndView tNewGoodsTabs(HttpServletRequest req) {
		return new ModelAndView("com/buss/newGoods/tNewGoodsTabs");
	}
	
	/**
	 * 商品详情列表（编辑查看零售商商品的4个真）
	 * @return
	 */
	@RequestMapping(params = "detailList")
	public ModelAndView detailList(HttpServletRequest req) {
		return new ModelAndView("com/buss/newGoods/tNewGoodsDetailList");
	}
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		TSUser user = ResourceUtil.getSessionUserName();
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		Map<String, String[]> map = request.getParameterMap();
		//查询条件组装器
		try{
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retailerId)){//零售商或者他录的用户
				cq.eq("retailerId", retailerId);
			}
			cq.eq("goodsType", com.buss.goods.entity.TGoodsEntity.GOODS_TYPE_1);//单品
			cq.eq("newGoodsType", com.buss.goods.entity.TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
		//自定义追加查询条件
			String goods_status = request.getParameter("goods_status");
			if(StringUtil.isNotEmpty(goods_status)){
				cq.eq("goodsStatus", goods_status);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map);
		
		this.tGoodsService.getDataGridReturn(cq, true);
		List<TGoodsEntity> list = dataGrid.getResults();
		if(Utility.isNotEmpty(list)){
			for(TGoodsEntity entity : list){
				entity.setSmallPic(entity.getSmallPic()+"?imageView2/1/w/80/h/80");
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 可见商品列表 （勾选的云商和其他零售商的上架商品）
	 * 按新的商品查询方式查询
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagridOfVisiable")
	public void datagridOfVisiable(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		Map<String, String[]> map = request.getParameterMap();
		//查询条件组装器
		try{
			String retailerId = ResourceUtil.getRetailerId();
			cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);//上架的
			cq.eq("goodsType", TGoodsEntity.GOODS_TYPE_1);//单品
			cq.eq("newGoodsType", TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
			cq.add();
			if(StringUtil.isEmpty(retailerId)){//资讯查询所有单品
				org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map);
			}else{//零售商查询单品
				TRetailerGoodsConditionsEntity goodsCondition = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailerId);
				if(Utility.isEmpty(goodsCondition)){
					cq.eq("retailerId", retailerId);
				}else{//按新的查询方式查询
					StringBuffer condtions = new StringBuffer(" (").append(goodsCondition.getRetailerConditions()).append(" OR ").append(goodsCondition.getCloudConditions()).append(")");
					org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
				}
				/*
				//可见品牌
				List<TBrandShowEntity> brandList = null;
				StringBuffer brandIds = null;
				brandList = tBrandShowService.getShowBrands(retailerId);
				 if(Utility.isNotEmpty(brandList)){
					 brandIds = new StringBuffer(" and this_.brand_id in(");
					 for(TBrandShowEntity showBrand : brandList ){
						 brandIds.append("'").append(showBrand.getBrandId()).append("',");
					 }
					 brandIds = brandIds.deleteCharAt(brandIds.length()-1).append(") ");
				 }
				StringBuffer condtions = new StringBuffer();
				//云仓商品
				condtions.append(" (retailer_Id = '").append(retailerId).append("' or (this_.goods_status = '4' and this_.`status` = 'A' and this_.Retailer_Type = '2'");
				if(brandIds!=null){
					condtions.append(brandIds);
				}
				condtions.append("))");
				List<TExcludeRuleEntity> ruleList = null;
				ruleList = systemService.findByProperty(TExcludeRuleEntity.class, "retailerId", retailerId);
				if(!Utility.isEmpty(ruleList)){
					condtions.append(" AND  NOT EXISTS ( select	id	from t_goods where retailer_Type = '2' and status = 'A' and id = this_.id and (");
					for(TExcludeRuleEntity rule:ruleList){
						condtions.append(" ").append(rule.getSqlStr()).append(" or ");
					}
					condtions.delete(condtions.length()-3, condtions.length());
					condtions.append(") )");
				}
				org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
				*/
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map);
		
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * easyui 资讯商品管理，选择资讯商品
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagridOfNews")
	public void datagridOfNews(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		Map<String, String[]> map = request.getParameterMap();
		String news_Id = request.getParameter("news_Id");
		//查询条件组装器
		try{
			String condtions =" NOT EXISTS ( select	1	from t_news_goods where  status = 'A' and goods_id = this_.id and news_id = '"+news_Id+"' )";
			cq.eq("goodsType", com.buss.goods.entity.TGoodsEntity.GOODS_TYPE_1);//单品
			cq.eq("newGoodsType", com.buss.goods.entity.TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
			cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);
			cq.eq("retailerId", ResourceUtil.getRetailerId());
			cq.add();
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	/**
	 * easyui 活动奖励查询商品
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagridOfFinAct")
	public void datagridOfFinAct(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		Map<String, String[]> map = request.getParameterMap();
		String finActId = request.getParameter("finActId");
		String platformType = request.getParameter("pType");//1:平台，2：零售商
		String retailerId = request.getParameter("rId");
		if(Utility.isEmpty(retailerId)){
			retailerId = ResourceUtil.getRetailerId();
		}
		//查询条件组装器
		try{
			
			StringBuffer condtions = new StringBuffer(" NOT EXISTS ( select	1	from t_fin_activity_goods where  status = 'A' and goods_id = this_.id and fin_Act_Id = '")
									.append(finActId).append("' )");
			cq.eq("goodsType", com.buss.goods.entity.TGoodsEntity.GOODS_TYPE_1);//单品
			cq.eq("newGoodsType", com.buss.goods.entity.TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
			cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);
			if(Utility.isNotEmpty(retailerId)){
				if(TFinActivityEntity.PLATFORM_TYPE_1.equals(platformType)){
					TRetailerGoodsConditionsEntity goodsCondition = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailerId);
					if(Utility.isEmpty(goodsCondition)){
//						condtions.append(" and 1<>1 ");
						cq.eq("retailerId", retailerId);
					}else{//按新的查询方式查询
						condtions.append(" and (retailer_id = '").append(retailerId).append("' OR ").append(goodsCondition.getRetailerConditions()).append(" OR ").append(goodsCondition.getCloudConditions()).append(")");
						org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
					}
				}else{
					cq.eq("retailerId", retailerId);
				}
			}
			cq.add();
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui 赠品规则查询商品
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagridOfGift")
	public void datagridOfGift(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		Map<String, String[]> map = request.getParameterMap();
		String giftRuleId = request.getParameter("giftRuleId");
		String retailerId = request.getParameter("rId");
		if(Utility.isEmpty(retailerId)){
			retailerId = ResourceUtil.getRetailerId();
		}
		//查询条件组装器
		try{
			
			StringBuffer condtions = new StringBuffer(" NOT EXISTS ( select	1	from t_gift_rule_goods where  status = 'A' and goods_id = this_.id and gift_rule_id = '")
									.append(giftRuleId).append("' )");
			cq.eq("goodsType", com.buss.goods.entity.TGoodsEntity.GOODS_TYPE_1);//单品
			cq.eq("newGoodsType", com.buss.goods.entity.TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
			cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);
			if(Utility.isNotEmpty(retailerId)){
				TRetailerGoodsConditionsEntity goodsCondition = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailerId);
				if(Utility.isEmpty(goodsCondition)){
					cq.eq("retailerId", retailerId);
				}else{//按新的查询方式查询
					condtions.append(" and (retailer_id = '").append(retailerId).append("' OR ").append(goodsCondition.getRetailerConditions()).append(" OR ").append(goodsCondition.getCloudConditions()).append(")");
					org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
				}
			}
			cq.add();
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	/**
	 * easyui AJAX请求数据 优惠券商品
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagridOfTicket")
	public void datagridOfTicket(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		Map<String, String[]> map = request.getParameterMap();
		String ticketId = request.getParameter("ticketId");
		String rId = request.getParameter("rId");
		//查询条件组装器
		try{
//			String retailerId = ResourceUtil.getRetailerId();
			String condtions =" NOT EXISTS ( select	1	from t_ticket_goods where  status = 'A' and goods_id = this_.id and ticket_Id = '"+ticketId+"' )";
			cq.eq("goodsType", com.buss.goods.entity.TGoodsEntity.GOODS_TYPE_1);//单品
			cq.eq("newGoodsType", com.buss.goods.entity.TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
			cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);
			if(Utility.isNotEmpty(rId)){
				cq.eq("retailerId", rId);
			}
			cq.add();
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**自定义查询列表
	 * @param request
	 * @param response
	 * @param grid
	 * @return
	 */
	@RequestMapping(params = "mydatagrid")
	@ResponseBody
	public DataGridJson mydatagrid(HttpServletRequest request, HttpServletResponse response,DataGridJson grid) {
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();//零售商id
//			String searchText = request.getParameter("searchText");//搜索内容
			String fields = "id,title,goods_name,current_price,small_pic,brand_name,original_price";
			StringBuffer sqlCount = new StringBuffer("select count(1) from t_goods where status = 'A' ");
			sqlCount.append(" and goods_status = '4'")//上架中的
			.append(" and new_Goods_Type = '2'")//上架中的
			.append(" and retailer_id = '").append(retailerId).append("'");
			String keywords = request.getParameter("keywords");
			if(Utility.isNotEmpty(keywords)){
				sqlCount.append(" and (title like '%").append(keywords).append("%' or goods_name like '%").append(keywords).append("%')");
			}
			Long total = systemService.getCountForJdbc(sqlCount.toString());
			List list = systemService.findForJdbc(sqlCount.toString().replace("count(1)", fields), grid.getPage(), grid.getRows());
			DataGridJson.setDataGrid(grid,list,total.intValue());
		}catch (Exception e) {
			e.printStackTrace();
			grid.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		return grid;
	}
	
	/**
	 *  管家推荐菜单 商品列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridForGuideRem")
	public void datagridForGuideRem(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			String retailerId = ResourceUtil.getRetailerId();
			String title = request.getParameter("title");//商品短标题
			String goodsName = request.getParameter("goodsName");//商品名称
			String goodsCode = request.getParameter("goodsCode");//款号
			String brandName = request.getParameter("brandName");//品牌
			String goodsStatus = request.getParameter("goodsStatus");//商品状态
			String hasRecmmend = request.getParameter("hasRecmmend");//是否有推荐
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			sql.append("SELECT t.* from (SELECT g.id,CONCAT(g.small_pic,'?imageView2/1/w/80/h/80') smallPic,IFNULL(g.title,'') title,g.goods_name goodsName,g.goods_code goodsCode,g.brand_name brandName,")
				.append("g.thrid_category_id thridCategoryId,g.retailer_id retailerId,g.retailer_name retailerName,g.sort_num sortNum,")
				.append("g.update_date updateDate,g.goods_status  goodsStatus,IF(COUNT(r.id)>0,1,0) hasRecmmend")
				.append(" from t_goods g LEFT JOIN t_guide_recommend_info r on g.id = r.goods_id and r.`status` = 'A'")
				.append(" where g.`status` = 'A'");
			countSql.append("SELECT count(1) from (SELECT g.id,IF(COUNT(r.id)>0,1,0) hasRecmmend")
				.append(" from t_goods g LEFT JOIN t_guide_recommend_info r on g.id = r.goods_id and r.`status` = 'A'")
				.append(" where g.`status` = 'A'");
			if(Utility.isNotEmpty(retailerId)){
				sql.append(" and g.retailer_id = '").append(retailerId).append("' ");
				countSql.append(" and g.retailer_id = '").append(retailerId).append("' ");
			}
			if(Utility.isNotEmpty(title)){
				sql.append(" and g.title like '%").append(title).append("%' ");
				countSql.append(" and g.title like '%").append(title).append("%' ");
			}
			if(Utility.isNotEmpty(goodsName)){
				sql.append(" and g.goods_name like '%").append(goodsName).append("%' ");
				countSql.append(" and g.goods_name like '%").append(goodsName).append("%' ");
			}
			if(Utility.isNotEmpty(goodsCode)){
				sql.append(" and g.goods_code like '%").append(goodsCode).append("%' ");
				countSql.append(" and g.goods_code like '%").append(goodsCode).append("%' ");
			}
			if(Utility.isNotEmpty(brandName)){
				sql.append(" and g.brand_name like '%").append(brandName).append("%' ");
				countSql.append(" and g.brand_name like '%").append(brandName).append("%' ");
			}
			if(Utility.isNotEmpty(goodsStatus)){
				sql.append(" and g.goods_status = '").append(goodsStatus).append("' ");
				countSql.append(" and g.goods_status = '").append(goodsStatus).append("' ");
			}
			sql.append(" and g.goods_type = '").append(TGoodsEntity.GOODS_TYPE_1).append("' and g.new_goods_type = '")//单品
				.append(TGoodsEntity.NEW_GOODS_TYPE_2).append("'")//新模块商品
				.append(" GROUP BY g.id ) t where 1=1");
			countSql.append(" and g.goods_type = '").append(TGoodsEntity.GOODS_TYPE_1).append("' and g.new_goods_type = '")//单品
			.append(TGoodsEntity.NEW_GOODS_TYPE_2).append("'")//新模块商品
			.append(" GROUP BY g.id ) t where 1=1");
			if(Utility.isNotEmpty(hasRecmmend)){
				sql.append(" and t.hasRecmmend = ").append(hasRecmmend);
				countSql.append(" and t.hasRecmmend = ").append(hasRecmmend);
			}

			//排序
			String sort = dataGrid.getSort();
			if(StringUtil.isEmpty(sort)){
				sql.append("order by create_date desc ");
			}else{
				sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder());
			}
			
			List<Map<String, Object>> resultList = null;
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
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
	 *  商品话术菜单 商品列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridForGoodsWords")
	public void datagridForGoodsWords(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			String retailerId = ResourceUtil.getRetailerId();
			String title = request.getParameter("title");//商品短标题
			String wordsId = request.getParameter("wordsId"); //话术id
			String goodsName = request.getParameter("goodsName");//商品名称
			String goodsCode = request.getParameter("goodsCode");//款号
			String brandName = request.getParameter("brandName");//品牌
			String goodsStatus = request.getParameter("goodsStatus");//商品状态
			String hasWords = request.getParameter("hasWords");//是否有话术
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			sql.append("SELECT * from ( SELECT g.id,g.small_pic smallPic,IFNULL(g.title, '') title,g.goods_name goodsName,g.goods_code goodsCode,g.brand_name brandName,")
					.append("g.thrid_category_id thridCategoryId,g.retailer_id retailerId,g.retailer_name retailerName,")
				.append("g.sort_num sortNum,g.update_date updateDate,g.goods_status goodsStatus,IF (COUNT(w.id) > 0, 1, 0) hasWords FROM t_goods g")
				.append(" LEFT JOIN t_fin_activity_words w ON g.id = w.fin_act_id AND w.`status` = 'A' AND w.words_type = 2 WHERE	g.`status` = 'A' ")
				.append("AND g.retailer_id = '").append(retailerId).append("'")
				.append("AND g.goods_type = '").append(TGoodsEntity.GOODS_TYPE_1).append("'	AND g.new_goods_type = '").append(TGoodsEntity.NEW_GOODS_TYPE_2).append("'  AND NOT EXISTS (")
				.append("SELECT fin_act_id id FROM t_fin_activity_words WHERE STATUS = 'A' AND fin_act_id = g.id AND words_id = '").append(wordsId).append("')  GROUP BY	g.id  ) tb where 1 = 1 ");
			countSql.append("SELECT count(1) from ( SELECT g.id,g.small_pic smallPic,IFNULL(g.title, '') title,g.goods_name goodsName,g.goods_code goodsCode,g.brand_name brandName,")
				.append("g.thrid_category_id thridCategoryId,g.retailer_id retailerId,g.retailer_name retailerName,")
				.append("g.sort_num sortNum,g.update_date updateDate,g.goods_status goodsStatus,IF (COUNT(w.id) > 0, 1, 0) hasWords FROM t_goods g")
				.append(" LEFT JOIN t_fin_activity_words w ON g.id = w.fin_act_id AND w.`status` = 'A' AND w.words_type = 2 WHERE	g.`status` = 'A' ")
				.append("AND g.retailer_id = '").append(retailerId).append("'")
				.append("AND g.goods_type = '").append(TGoodsEntity.GOODS_TYPE_1).append("'	AND g.new_goods_type = '").append(TGoodsEntity.NEW_GOODS_TYPE_2).append("'  AND NOT EXISTS ( ")
				.append(" SELECT fin_act_id id	FROM t_fin_activity_words WHERE	STATUS = 'A' AND fin_act_id = g.id AND words_id = '").append(wordsId).append("')  GROUP BY	g.id  ) as tb  where 1 = 1 ");
//			if(Utility.isNotEmpty(retailerId)){
//				sql.append(" and tb.retailerId = '").append(retailerId).append("' ");
//				countSql.append(" and tb.retailerId = '").append(retailerId).append("' ");
//			}
			if(Utility.isNotEmpty(title)){
				sql.append(" and tb.title like '%").append(title).append("%' ");
				countSql.append(" and tb.title like '%").append(title).append("%' ");
			}
			if(Utility.isNotEmpty(goodsName)){
				sql.append(" and tb.goodsName like '%").append(goodsName).append("%' ");
				countSql.append(" and tb.goodsName like '%").append(goodsName).append("%' ");
			}
			if(Utility.isNotEmpty(goodsCode)){
				sql.append(" and tb.goodsCode like '%").append(goodsCode).append("%' ");
				countSql.append(" and tb.goodsCode like '%").append(goodsCode).append("%' ");
			}
			if(Utility.isNotEmpty(brandName)){
				sql.append(" and tb.brandName like '%").append(brandName).append("%' ");
				countSql.append(" and tb.brandName like '%").append(brandName).append("%' ");
			}
			if(Utility.isNotEmpty(goodsStatus)){
				sql.append(" and tb.goodsStatus = '").append(goodsStatus).append("' ");
				countSql.append(" and tb.goodsStatus = '").append(goodsStatus).append("' ");
			}
			if(Utility.isNotEmpty(hasWords)){
				sql.append(" and tb.hasWords = '").append(hasWords).append("' ");
				countSql.append(" and tb.hasWords = '").append(hasWords).append("' ");
			}
			String sortName = dataGrid.getSort();
			if (Utility.isEmpty(sortName)) {
				sql.append(" ORDER BY tb.updateDate desc");
			} else {
				sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
			}
			List<Map<String, Object>> resultList = null;
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
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
	 * 删除商品表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
//		tGoods = systemService.getEntity(TGoodsEntity.class, tGoods.getId());
		message = "商品删除成功";
		try{
			tGoodsService.deleteGoods(tGoods.getId());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品删除失败";
			logger.error(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		categoryService.clearCategoryRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除商品表
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "商品表删除成功";
		try{
			for(String id:ids.split(",")){
				tGoodsService.deleteGoods(id);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "商品表删除失败";
			logger.error(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		categoryService.clearCategoryRedis();
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加商品表
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TGoodsEntity tGoods,TSpecHeadersEntity tSpecHeader, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品添加成功";
		try{
			//可视类目数据存入list
			TSUser user = ResourceUtil.getSessionUserName();
			if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){//零售商员工
				user = this.systemService.get(TSUser.class, user.getRetailerId());
			}
			//自己查询拼接一级分类
			String sql  = "select parent_id from t_s_category where id='"+tGoods.getSubCategoryId()+"' ";
			List<Map<String,Object>> listMap = systemService.findForJdbc(sql, null);
			tGoods.setTopCategoryId(listMap.get(0).get("parent_id").toString());
			
			tGoods.setRetailerId(user.getId());
			tGoods.setRetailerType(user.getRetailerType());
			tGoods.setRetailerCode(user.getUserCode());
			tGoods.setRetailerName(user.getRealName());
			tGoods.setCityId(user.getCityId());
			tGoods.setProvinceId(user.getProvinceId());
			tGoods.setCode(tGoods.getRetailerCode()+tGoods.getBrandCode()+tGoods.getGoodsCode());
//			String checkExist = "select count(id) from TGoodsEntity where goodsType='1' and status = 'A' and code ='"+tGoods.getCode()+"' ";
			String sql1 = "select count(id) from t_goods where  status = 'A' and goods_code = '"+ tGoods.getGoodsCode()+"' and retailer_id = '"+user.getId()+"'";
			Long n1 = this.systemService.getCountForJdbc(sql1);
//			Long n =  this.systemService.singleResult(checkExist);
//			checkExist = null;
			if(n1>0){
				message = "商品款号"+tGoods.getGoodsCode()+"已存在";
				j.setMsg(message);
				j.setSuccess(false);
				return j;
			}else{
				List<TGoodsStoreEntity> storeList = tGoods.gettGoodsStoreDetails();
				//校验条码
				if(Utility.isNotEmpty(storeList)){
					Map<String,Object> resultMap = this.checkBarCode(storeList,user.getId());
					Long n2 = Long.valueOf(resultMap.get("count")+"");
					if(n2>0){
						message = resultMap.get("msg")+"";
						j.setMsg(message);
						j.setSuccess(false);
						return j;
					}
				}
				if(Utility.isEmpty(tGoods.getFarePreferentialType())){
					tGoods.setFarePreferentialType(TGoodsEntity.FARE_PREFERENTIAL_TYPE_0);//默认没有运费优惠
				}
				if(Utility.isEmpty(tGoods.getGoodsStock())){
					tGoods.setGoodsStock(null);
				}
				tGoodsService.saveNewGoods(tGoods,tSpecHeader);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			message = "商品添加失败";
			logger.error(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		categoryService.clearCategoryRedis();
		j.setMsg(message);
		return j;
	}
	
	/**检验条码是否重复，有重复的则提示*/
	private Map<String,Object> checkBarCode(List<TGoodsStoreEntity> storeList, String retailerId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Long n = 0L;
		String msg = null;
		Set<String> barCodeSet = new HashSet<String>();
		for(TGoodsStoreEntity sotre : storeList){
			String barCode = sotre.getBarCode();
			if(Utility.isEmpty(sotre.getId())&&Utility.isNotEmpty(barCode)){//新增的条码
				if(barCodeSet.contains(barCode)){
					n = 1L;
					msg = "条码"+barCode+"重复";
					resultMap.put("count", n);
					resultMap.put("msg", msg);
					return resultMap;
				}else{
					barCodeSet.add(barCode);
				}
			}
		}
		if(Utility.isNotEmpty(barCodeSet)){
			StringBuffer sql = new StringBuffer("select bar_code from t_goods_store where  status = 'A'")
						.append(" and retailer_id = '").append(retailerId).append("' ")
						.append(" and bar_code in (");
			for(String barCode : barCodeSet){
				sql.append("'").append(barCode).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(")");
			//查出系统中存在的条码
			List<Map<String, Object>> list = this.systemService.findForJdbc(sql.toString(), null);
			if(Utility.isNotEmpty(list)){
				n = 1L;
				msg = "系统中已经存在条码";
				for(Map<String, Object> map : list){
					msg+=map.get("bar_code")+",";
				}
				msg = msg.substring(0, msg.length()-1);
			}
		}
		resultMap.put("count", n);
		resultMap.put("msg", msg);
		return resultMap;
	}

	/**可视类目数据存入list
	 * @param newVisibleCatgs
	 * @param tGoods
	 * @param list
	 * @return
	 */
	private void fillVisibleCategories(String newVisibleCatgs,TGoodsEntity tGoods,List<TVisibleCategriesEntity> list) {
		if(StringUtil.isNotEmpty(newVisibleCatgs)){
			String[] visibleCategry = newVisibleCatgs.split(",");
			int n = visibleCategry.length;
			/*for(int i=0;i<n/3;i++){
				TVisibleCategriesEntity entity = new TVisibleCategriesEntity();
				entity.setThridCategoryId(visibleCategry[3*i]);
				entity.setSubCategoryId(visibleCategry[3*i+1]);
				entity.setTopCategoryId(visibleCategry[3*i+2]);
				entity.setSceneType(tGoods.getSceneType());
				list.add(entity);
			}*/
			for(int i=0;i<n/2;i++){
				TVisibleCategriesEntity entity = new TVisibleCategriesEntity();
				entity.setSubCategoryId(visibleCategry[2*i]);
				entity.setTopCategoryId(visibleCategry[2*i+1]);
				entity.setSceneType(tGoods.getSceneType());
				list.add(entity);
			}
		}
	}

	/**
	 * 商品下架，活动商品列表中的商品要删除，并取消相关订单
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doDown")
	@ResponseBody
	public AjaxJson doDown(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品下架成功";
		try{
			tGoods = systemService.flushEntity(TGoodsEntity.class, tGoods.getId());
			tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_5);
			tGoods.setUpdateDate(DateUtils.gettimestamp());
			tGoodsService.doDown(tGoods);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品下架失败";
			throw new BusinessException(e.getMessage());
		}
		try {
			//发送mq消息 关闭零售商商品相关的订单
			ResourceBundle rabbitmq = ResourceBundle.getBundle("rabbitmq");
			String key = rabbitmq.getString("close.goods.order.key");
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("goodsId", tGoods.getId());
			map.put("retailerId", tGoods.getRetailerId());
			rabbitTemplate.convertAndSend(key, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 商品置顶
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doToTop")
	@ResponseBody
	public AjaxJson doToTop(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "置顶成功";
		try{
			Timestamp time = DateUtils.gettimestamp();
			String sortNum = DateUtils.date2Str(time, DateUtils.yyyymmddhhmmss);
			systemService.updateBySqlString("update t_goods set update_date = '"+time+"',sort_num = '"+sortNum+"' where id ='"+id+"'");
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "置顶失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 商品排序
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doChangeSort")
	@ResponseBody
	public AjaxJson doChangeSort(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		try{
			String id = request.getParameter("id");
			String type = request.getParameter("type");
			String sortNum = request.getParameter("sortNum");
			if("U".equals(type)){//上移
				message = "上移成功";
			}else if("D".equals(type)){
				message = "下移成功";
			}
			this.tGoodsService.doChangeSort(id,type,sortNum);
//			if(result==2){
//				message = "已经到顶了";
//			}else if(result==3){
//				message = "已经到底了";
//			}
//			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "排序失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量下架
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doBatchDown")
	@ResponseBody
	public AjaxJson doBatchDown(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "批量下架成功";
		try{
//			TSUser user = ResourceUtil.getSessionUserName();
//			StringBuffer sql = new StringBuffer("update t_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
//			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',goods_status = '5' where id in (");
//			for(String id:ids.split(",")){
//				sql.append("'").append(id).append("',");
//			}
//			sql = sql.deleteCharAt(sql.length()-1).append(")");
//			systemService.updateBySqlString(sql.toString());
			for(String id:ids.split(",")){
				TGoodsEntity tGoods = systemService.flushEntity(TGoodsEntity.class, id);
				tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_5);
				tGoods.setUpdateDate(DateUtils.gettimestamp());
				//下架并删除活动中的相关商品，取消相关未付款的订单，如果录入了库存则要还库存
				tGoodsService.doDown(tGoods);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "批量下架失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量发布(录入了图片的才能发布)
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doBatchPublish")
	@ResponseBody
	public AjaxJson doBatchPublish(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			int n = 0;
			StringBuffer querySql = new StringBuffer("SELECT  t.goods_id,COUNT(t.goods_id) as typeCount from ")
			.append("(SELECT goods_id,type from t_guide_recommend_detail where goods_id in (");
			for(String id:ids.split(",")){
				querySql.append("'").append(id).append("',");
			}
			querySql = querySql.deleteCharAt(querySql.length()-1).append(")").append("and status = 'A' and type = '1' GROUP BY goods_id,type ) t GROUP BY t.goods_id");
			List<Map<String,Object>> list = systemService.findForJdbc(querySql.toString(), null);
			if(Utility.isNotEmpty(list)){
				Set<String> goodsIds = new HashSet<String>();
				for(Map<String,Object> map : list){
					Long count = (Long) map.get("typeCount");
					if(count>0){
						goodsIds.add(map.get("goods_id")+"");
						n++;
					}
				}
				if(Utility.isNotEmpty(goodsIds)){
					TSUser user = ResourceUtil.getSessionUserName();
					Timestamp time = DateUtils.gettimestamp();
					String sortNum = DateUtils.date2Str(time, DateUtils.yyyymmddhhmmss);
					StringBuffer sql = new StringBuffer("update t_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
					.append(user.getRealName()).append("',update_date ='").append(time).append("',goods_update_time ='")
					.append(time).append("',sort_num =").append(sortNum).append(",goods_status = '4' where id in (");
					for(String id : goodsIds){
						sql.append("'").append(id).append("',");
					}
					sql = sql.deleteCharAt(sql.length()-1).append(")");
					systemService.updateBySqlString(sql.toString());
				}
			}
			message = "批量发布成功"+n+"条商品";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "批量发布失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 更新商品表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品更新成功";
		TGoodsEntity t = tGoodsService.get(TGoodsEntity.class, tGoods.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tGoods, t);
			t.setTitle(tGoods.getTitle());
			t.setSmallPic(tGoods.getSmallPic());
			if(Utility.isEmpty(tGoods.getGoodsStock())){
				t.setGoodsStock(null);
			}
			t.setCode(t.getRetailerCode()+t.getBrandCode()+t.getGoodsCode());
			List<TGoodsStoreEntity> storeList = tGoods.gettGoodsStoreDetails();
			//校验条码
			if(Utility.isNotEmpty(storeList)){
				Map<String,Object> resultMap = this.checkBarCode(storeList,t.getRetailerId());
				Long n2 = Long.valueOf(resultMap.get("count")+"");
				if(n2>0){
					message = resultMap.get("msg")+"";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
			}
			if(Utility.isEmpty(t.getFarePreferentialType())){
				t.setFarePreferentialType(TGoodsEntity.FARE_PREFERENTIAL_TYPE_0);//默认没有运费优惠
			}
			tGoodsService.updateNewGoods(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品更新失败";
			j.setSuccess(false);
			logger.error(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		categoryService.clearCategoryRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新零售商商品详情(4个真)
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdateDetail")
	@ResponseBody
	public AjaxJson doUpdateDetail(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品详情更新成功";
		try {
			tGoodsService.updateNewGoodsDetail(tGoods);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品详情更新失败";
			j.setSuccess(false);
			logger.info(e.getMessage());
			System.out.print("商品详情更新失败:"+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 商品表新增页面跳转到选择分类
	 * 
	 * @return
	 */
	@RequestMapping(params = "goStep1")
	public ModelAndView goStep1(HttpServletRequest req) {
		String id = req.getParameter("id");
		String retailerId = ResourceUtil.getRetailerId();
		String hql = "from TSCategoryEntity where level = 1 and retailerId in ('admin','"+retailerId+"') order by sort asc";
		List<TSCategoryEntity> catList = systemService.findHql(hql);
		req.setAttribute("catList",catList);
		req.setAttribute("id",id);
		return new ModelAndView("com/buss/newGoods/tNewGoods-step1");
	}
	
	
	/**
	 * 商品表编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TGoodsEntity tGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGoods.getId())) {
			tGoods = tGoodsService.flushEntity(TGoodsEntity.class, tGoods.getId());
			//导购推荐
			List<TGuideRecommendDetailEntity> recommendDetailsList =this.systemService.findByQueryString("from TGuideRecommendDetailEntity where goodsId ='"+tGoods.getId()+"' and status='A' order by idx asc");
			req.setAttribute("recommendDetailsList", recommendDetailsList);
			tGoods.settGoodsPicDetails(tGoodsService.getGoodsPics(tGoods.getId()));
			TSCategoryEntity cat1 = categoryService.get(TSCategoryEntity.class, tGoods.getTopCategoryId());
			TSCategoryEntity cat2 = categoryService.get(TSCategoryEntity.class, tGoods.getSubCategoryId());
			TSCategoryEntity cat3 = categoryService.get(TSCategoryEntity.class, tGoods.getThridCategoryId());
			if(!Utility.isEmpty(cat1)){
				req.setAttribute("topCategory", cat1.getName());
			}
			if(!Utility.isEmpty(cat2)){
				req.setAttribute("subCategory", cat2.getName());
			}
			if(!Utility.isEmpty(cat3)){
				req.setAttribute("thridCategory", cat3.getName());
			}
			List<TVisibleCategriesEntity> visiblelist = this.systemService.findByProperty(TVisibleCategriesEntity.class, "goodsId",	tGoods.getId());
			if(!Utility.isEmpty(visiblelist)){
				String categries = "";
				for(TVisibleCategriesEntity entity : visiblelist){
					categries += entity.getTopCategoryId()+",";
					categries += entity.getSubCategoryId()+",";
				}
				tGoods.setCategries(categries);
			}
			//产品参数
			List<TProductParamsEntity> productParamslist = tProductParamsService.findProductParamsByTopCategoryId(tGoods.getSubCategoryId());
			req.setAttribute("productParamslist", productParamslist);
			//产品信息
			List<TProductInfoEntity> productInfoList = this.systemService.findByProperty(TProductInfoEntity.class, "goodsId", tGoods.getId());
			tGoods.setProductInfoList(productInfoList);
			req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
			String retailerCode = ResourceUtil.getRetailerCode(systemService);
			req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
			req.setAttribute("tGoodsPage", tGoods);
		}
		String retailerEdition = ResourceUtil.getRetailerEdition();//零售商版本
		TGoodsDescEntity desc = null;
		List<TGoodsDescEntity> descList = this.systemService.findHql("from TGoodsDescEntity where goodsId = ? and type = '0'", tGoods.getId());
		if(descList.size()>0){
			desc = descList.get(0);
		}
		
		String sql = "select distinct tag from t_cust_words_tags tcwt  left join t_guide_recommend_words_push tgrwp on tgrwp.words_id = tcwt.goods_words_id "+
		"where tcwt.status='A' and tgrwp.status='A' and goods_id='"+tGoods.getId()+"'";
		List<Map<String,Object>> tagsList = this.systemService.findForJdbc(sql, null);

		req.setAttribute("tagsList", tagsList);
		req.setAttribute("desc", desc);
		if(TSUser.RETAILER_EDITION_1.equals(retailerEdition)){//标准版
			int num = this.getNumOfMainPics(tGoods);
			req.setAttribute("mainPicsNum", num);
			 return new ModelAndView("com/buss/newGoods/tNewGoods-update3");
		}else if(TSUser.RETAILER_EDITION_2.equals(retailerEdition)){//企业版
			String retailerType = ResourceUtil.getRetailerType();
			if(TSUser.RETAILER_TYPE_GOODS.equals(retailerType)){//云商录入页面  云商要统一改为零售商
//				List<TGoodsDescEntity> descList = this.systemService.findByProperty(TGoodsDescEntity.class, "goodsId", tGoods.getId());
//				req.setAttribute("descList", descList);
				return new ModelAndView("com/buss/newGoods/tNewGoods-update2");
			}
		}
		//企业版 零售商商录入页面
		return new ModelAndView("com/buss/newGoods/tNewGoods-update");
	}
	
	/**获取商品对应分类的零售商话术和平台可见话术
	 * @param tGoods
	 * @return
	 */
	private Long getGoodsWordsTotalNum(TGoodsEntity tGoods) {
		String retailerId = ResourceUtil.getRetailerId();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from t_goods_words where `status`='A' and (retailer_id = '").append(retailerId).append("' or id in(SELECT v.words_id from t_visible_words v where v.`status` = 'A' and v.retailer_id = '")
			.append(retailerId).append("' and v.words_type='2'))").append(" and top_category_id = '").append(tGoods.getTopCategoryId())
			.append("' and sub_category_id = '").append(tGoods.getSubCategoryId()).append("' and thrid_category_id = '").append(tGoods.getThridCategoryId()).append("'")
			;
		Long n = this.systemService.getCountForJdbc(sql.toString());
		return n;
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
	
	
	
		/**
	 * 商品修改完分类后到编辑页面的跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdateNext")
	public ModelAndView goUpdateNext(TGoodsEntity tGoods, HttpServletRequest req) {
		String topCategoryId = req.getParameter("topCategoryId");
		String subCategoryId = req.getParameter("subCategoryId");
		String thridCategoryId = req.getParameter("thridCategoryId");
		req.setAttribute("topCategoryId", topCategoryId);
		req.setAttribute("subCategoryId", subCategoryId);
		req.setAttribute("thridCategoryId", thridCategoryId);
		if (StringUtil.isNotEmpty(tGoods.getId())) {
			tGoods = tGoodsService.get(TGoodsEntity.class, tGoods.getId());
			//导购推荐
			List<TGuideRecommendDetailEntity> recommendDetailsList =this.systemService.findByQueryString("from TGuideRecommendDetailEntity where goodsId ='"+tGoods.getId()+"' and status='A' order by createDate asc");
			req.setAttribute("recommendDetailsList", recommendDetailsList);
//			tGoods.settGoodsAttrDetails(tGoodsService.getGoodsAttrs(tGoods.getId()));
			tGoods.settGoodsPicDetails(tGoodsService.getGoodsPics(tGoods.getId()));
			TSCategoryEntity cat1 = categoryService.get(TSCategoryEntity.class, topCategoryId);
			TSCategoryEntity cat2 = categoryService.get(TSCategoryEntity.class, subCategoryId);
			TSCategoryEntity cat3 = categoryService.get(TSCategoryEntity.class, thridCategoryId);
			if(!Utility.isEmpty(cat1)){
				req.setAttribute("topCategory", cat1.getName());
			}
			if(!Utility.isEmpty(cat2)){
				req.setAttribute("subCategory", cat2.getName());
			}
			if(!Utility.isEmpty(cat3)){
				req.setAttribute("thridCategory", cat3.getName());
			}
			List<TVisibleCategriesEntity> visiblelist = this.systemService.findByProperty(TVisibleCategriesEntity.class, "goodsId",	tGoods.getId());
			if(!Utility.isEmpty(visiblelist)){
				String categries = "";
				for(TVisibleCategriesEntity entity : visiblelist){
					categries += entity.getTopCategoryId()+",";
					categries += entity.getSubCategoryId()+",";
				}
				tGoods.setCategries(categries);
			}
			tGoods.setTopCategoryId(topCategoryId);
			tGoods.setSubCategoryId(subCategoryId);
			tGoods.setThridCategoryId(thridCategoryId);
			req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
			String retailerCode = ResourceUtil.getRetailerCode(systemService);
			req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
			req.setAttribute("tGoodsPage", tGoods);
		}
		String retailerType = ResourceUtil.getRetailerType();
		if(TSUser.RETAILER_TYPE_GOODS.equals(retailerType)){//云商录入页面
			 List<TGoodsDescEntity> descList = this.systemService.findByProperty(TGoodsDescEntity.class, "goodsId", tGoods.getId());
			 req.setAttribute("descList", descList);
			return new ModelAndView("com/buss/newGoods/tNewGoods-update2");
		}
		return new ModelAndView("com/buss/newGoods/tNewGoods-update");
	}
	
	/**
	 * 零售商编辑商品4个真的页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdateDetail")
	public ModelAndView goUpdateDetail(TGoodsEntity tGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGoods.getId())) {
			tGoods = tGoodsService.flushEntity(TGoodsEntity.class, tGoods.getId());
			tGoods.settGoodsPicDetails(tGoodsService.getGoodsPics(tGoods.getId()));
			TSCategoryEntity cat1 = categoryService.get(TSCategoryEntity.class, tGoods.getTopCategoryId());
			TSCategoryEntity cat2 = categoryService.get(TSCategoryEntity.class, tGoods.getSubCategoryId());
			TSCategoryEntity cat3 = categoryService.get(TSCategoryEntity.class, tGoods.getThridCategoryId());
			if(!Utility.isEmpty(cat1)){
				req.setAttribute("topCategory", cat1.getName());
			}
			if(!Utility.isEmpty(cat2)){
				req.setAttribute("subCategory", cat2.getName());
			}
			if(!Utility.isEmpty(cat3)){
				req.setAttribute("thridCategory", cat3.getName());
			}
			req.setAttribute("tGoodsPage", tGoods);
		}
//		if("1".equals(tGoods.getHasZhen())){//有4个真
//			 List<TGoodsDescEntity> descList = this.systemService.findByProperty(TGoodsDescEntity.class, "goodsId", tGoods.getId());
//			 req.setAttribute("descList", descList);
//		}
		return new ModelAndView("com/buss/newGoods/tNewGoodsDetail-update");
	}
	
	/**
	 * 商品库存编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdateStore")
	public ModelAndView goUpdateStore(TGoodsEntity tGoods, HttpServletRequest req) {
			tGoods = tGoodsService.flushEntity(TGoodsEntity.class, tGoods.getId());
			req.setAttribute("tGoodsPage", tGoods);
//			List<BaseActivityEntity> activityList = this.baseActivityService.getList(BaseActivityEntity.class);
//			req.setAttribute("activityList", activityList);
			TSCategoryEntity cat = categoryService.flushEntity(TSCategoryEntity.class, tGoods.getThridCategoryId());
			req.setAttribute("thridCategory", cat.getName());
			req.setAttribute("type", req.getParameter("type"));
		return new ModelAndView("com/buss/goods/tGoodsStore-update");
	}
	

	/**
	 * 商品查看页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TGoodsEntity tGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGoods.getId())) {
			tGoods = tGoodsService.flushEntity(TGoodsEntity.class, tGoods.getId());
			//导购推荐
			List<TGuideRecommendDetailEntity> recommendDetailsList =this.systemService.findByQueryString("from TGuideRecommendDetailEntity where goodsId ='"+tGoods.getId()+"' and status='A' order by idx asc");
			req.setAttribute("recommendDetailsList", recommendDetailsList);
			tGoods.settGoodsStoreDetails(tGoodsService.getGoodsStores(tGoods.getId()));
			tGoods.settGoodsPicDetails(tGoodsService.getGoodsPics(tGoods.getId()));
			TSCategoryEntity cat1 = categoryService.get(TSCategoryEntity.class, tGoods.getTopCategoryId());
			TSCategoryEntity cat2 = categoryService.get(TSCategoryEntity.class, tGoods.getSubCategoryId());
			TSCategoryEntity cat3 = categoryService.get(TSCategoryEntity.class, tGoods.getThridCategoryId());
			if(!Utility.isEmpty(cat1)){
				req.setAttribute("topCategory", cat1.getName());
			}
			if(!Utility.isEmpty(cat2)){
				req.setAttribute("subCategory", cat2.getName());
			}
			if(!Utility.isEmpty(cat3)){
				req.setAttribute("thridCategory", cat3.getName());
			}
			req.setAttribute("tGoodsPage", tGoods);
			
			TSpecHeadersEntity tSpecHeader = systemService.findUniqueByProperty(TSpecHeadersEntity.class, "goodsId", tGoods.getId());
			req.setAttribute("tSpecHeader", tSpecHeader);
			//产品参数
			List<TProductParamsEntity> productParamslist = tProductParamsService.findProductParamsByTopCategoryId(tGoods.getSubCategoryId());
			req.setAttribute("productParamslist", productParamslist);
			productParamslist = null;
			//产品信息
			List<TProductInfoEntity> productInfoList = this.systemService.findByProperty(TProductInfoEntity.class, "goodsId", tGoods.getId());
			tGoods.setProductInfoList(productInfoList);
			productInfoList = null;
			String retailerEdition = ResourceUtil.getRetailerEdition();//零售商版本
			TGoodsDescEntity desc = null;
			List<TGoodsDescEntity> descList = this.systemService.findHql("from TGoodsDescEntity where goodsId = ? and type = '0'", tGoods.getId());
			if(descList.size()>0){
				desc = descList.get(0);
			}
			String sql = "select distinct tag from t_cust_words_tags tcwt  left join t_guide_recommend_words_push tgrwp on tgrwp.words_id = tcwt.goods_words_id "+
					"where tcwt.status='A' and tgrwp.status='A' and goods_id='"+tGoods.getId()+"'";
			List<Map<String,Object>> tagsList = this.systemService.findForJdbc(sql, null);

			req.setAttribute("tagsList", tagsList);
			req.setAttribute("desc", desc);
			if(TSUser.RETAILER_EDITION_1.equals(retailerEdition)){//标准版
//				 TGoodsDescEntity desc = this.systemService.findUniqueByProperty(TGoodsDescEntity.class, "goodsId", tGoods.getId());
//				 req.setAttribute("desc", desc);
				 return new ModelAndView("com/buss/newGoods/tNewGoods-view3");
			}else if(TSUser.RETAILER_EDITION_2.equals(retailerEdition)){//企业版
				String retailerType = ResourceUtil.getRetailerType();
				if(TSUser.RETAILER_TYPE_GOODS.equals(retailerType)){//云商录入页面
//					List<TGoodsDescEntity> descList = this.systemService.findByProperty(TGoodsDescEntity.class, "goodsId", tGoods.getId());
//					 req.setAttribute("descList", descList);
					return new ModelAndView("com/buss/newGoods/tNewGoods-view2");
				}
			}
		}
		//企业版 零售商商页面
		return new ModelAndView("com/buss/newGoods/tNewGoods-view");
	}
	
	
	

	
	/**
	 * 加载明细列表[库存明细]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tGoodsStoreDetailsForUpdate")
	public ModelAndView tGoodsStoreDetailsForUpdate(String id, HttpServletRequest req) {
		String hql0 = "from TGoodsStoreEntity where 1 = 1 and status = 'A' AND goodsId = ? ";
		try{
			List<TGoodsStoreEntity> tGoodsStoreDetailsList = systemService.findHql(hql0,id);
			TSpecHeadersEntity tSpecHeader = systemService.findUniqueByProperty(TSpecHeadersEntity.class, "goodsId", id);
			if(Utility.isEmpty(tSpecHeader)){
				tSpecHeader = new TSpecHeadersEntity();
				tSpecHeader.setHeaderOne("颜色");
				tSpecHeader.setHeaderTwo("尺码");
				tSpecHeader.setHeaderThree("规格3");
			}
			req.setAttribute("tGoodsStoreDetails", tGoodsStoreDetailsList);
			req.setAttribute("tSpecHeader", tSpecHeader);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/buss/goods/tGoodsStoreDetailsForUpdate");
	}
	
	/**
	 * g+商品表新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TGoodsEntity tGoods, HttpServletRequest req) {
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		String retailerEdition = ResourceUtil.getRetailerEdition();//零售商版本
		if(TSUser.RETAILER_EDITION_1.equals(retailerEdition)){//标准版
			return new ModelAndView("com/buss/newGoods/tNewGoods-add3");
		}else if(TSUser.RETAILER_EDITION_2.equals(retailerEdition)){//企业版
			String retailerType = ResourceUtil.getRetailerType();
			if(TSUser.RETAILER_TYPE_GOODS.equals(retailerType)){//云商录入页面
				return new ModelAndView("com/buss/newGoods/tNewGoods-add2");
			}
		}
		String msg = req.getParameter("addOK");
		if(Utility.isNotEmpty(msg)){//用于添加成功后的信息显示
			req.setAttribute("msg", "商品添加成功");
		}
		//企业版零售商商录入页面
		return new ModelAndView("com/buss/newGoods/tNewGoods-add");
	}
	
	/**
	 * d+商品表新增页面跳转
	 * 权限判断不能区分该请求来源于菜单还是tab页，所以修改方法名
	 * @return
	 */
	@RequestMapping(params = "goAddFromRet")
	public ModelAndView goAddFromRet(TGoodsEntity tGoods, HttpServletRequest req) {
		return goAdd(tGoods, req);
	}
	
	
	/**
	 * 修改活动价页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdateActivityPrice")
	public ModelAndView goUpdateActivityPrice(String id, HttpServletRequest req) {
		if(Utility.isNotEmpty(id)){
			TGoodsEntity tGoods = this.tGoodsService.get(TGoodsEntity.class, id);
			req.setAttribute("tGoods", tGoods);
		}
		return new ModelAndView("com/buss/newGoods/tNewGoods-changeActivityPrice");
	}
	
	/**
	 * 修改活动价
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doChangeActivityPrice")
	@ResponseBody
	public AjaxJson doChangeActivityPrice(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动价修改成功";
		try{
			TGoodsEntity t = this.systemService.get(TGoodsEntity.class, tGoods.getId());
			tGoodsService.doChangeActivityPrice(t,tGoods.getActivityPrice());
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动价修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 修改价格
	 * @param request(id,field,prePrice,currPrice)
	 * @return
	 */
	@RequestMapping(params = "doChangePrice")
	@ResponseBody
	public AjaxJson doChangePrice(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "价格修改成功";
		try{
			tGoodsService.doChangePrice(request);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "价格修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 修改属性
	 * @param request(id,field,val)
	 * @return
	 */
	@RequestMapping(params = "doChangeProperty")
	@ResponseBody
	public AjaxJson doChangeProperty(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "修改成功";
		try{
			tGoodsService.doChangeProperty(request);
//			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 导入最低价功能跳转
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tNewGoodsController");
		String uploadType = req.getParameter("uploadType");
		req.setAttribute("extraParam",uploadType);
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 下载最低价excel模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "downloadLowestPirceTemp")
	public ModelAndView downloadLowestPirceTemp( HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\templates");
		String fileName = "商品最低价导入模板.xlsx";
		String filePath = path + "\\" + fileName;
		FileUtils.downLoadFile(filePath, response, request, fileName, "xlsx");
		return null;
	}
	
	/**
	 * 下载话术excel模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "downloadWordsTemp")
	public ModelAndView downloadWordsTemp( HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\templates");
		String fileName = "商品话术导入模板.xlsx";
		String filePath = path + "\\" + fileName;
		FileUtils.downLoadFile(filePath, response, request, fileName, "xlsx");
		return null;
	}
	
	
	/**批量导入
	 * lowestPirce 最低价
	 * words 话术
	 * @param request
	 * @param response
	 * @return
	 */
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
			params.setNeedSave(false);
			try {
				String uploadType = request.getParameter("extraParam");//lowestPirce,words
				System.out.println(uploadType);
				if("lowestPirce".equals(uploadType)){//最低价
					List<LowestPriceImportVo> lowestPriceList = ExcelImportUtil.importExcel(file.getInputStream(),LowestPriceImportVo.class,params);
					if(lowestPriceList.size()>0){
						message = this.tGoodsService.batchChangePirce(lowestPriceList);
						systemService.addLog("批量导入最低价成功", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
						j.setMsg(message);
					}else{
						j.setMsg("最低价导入文件为空！");
					}
				}else if("words".equals(uploadType)){//商品话术
					List<GoodsWordsImportVo> wordsList = ExcelImportUtil.importExcel(file.getInputStream(),GoodsWordsImportVo.class,params);
					if(wordsList.size()>0){
						message = this.tGoodsService.batchSaveGoodsWords(wordsList);
						systemService.addLog("批量导入话术成功", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
						j.setMsg(message);
					}else{
						j.setMsg("话术导入文件为空！");
					}
				}
			} catch (Exception e) {
				j.setMsg("文件导入失败，请检查单元格格式和模版格式是否一致！");
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
	 * 导出商品excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TGoodsEntity tGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		List<TGoodsSimpleVo> goodsList = null;
		try{
			goodsList = this.getGoodsSimpleVoSql(request);
		}catch(Exception e){
			e.printStackTrace();
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"商品列表");
		modelMap.put(NormalExcelConstants.CLASS,TGoodsSimpleVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,goodsList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	
	/**获取商品基本信息
	 * @param request
	 * @return
	 */
	private List<TGoodsSimpleVo> getGoodsSimpleVoSql(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		String goodsName = request.getParameter("goodsName");
		String goodsCode = request.getParameter("goodsCode");
		String brandName = request.getParameter("brandName");
		String goodsStatus = request.getParameter("goodsStatus");
		StringBuffer sql = new StringBuffer("select g.goods_name goodsName,")
				.append("goods_code goodsCode,")
				.append("brand_name brandName,")
				.append("original_price originalPrice,")
				.append("lowest_price lowestPrice,")
				.append("goods_stock goodsStock,")
				.append("goods_status goodsStatus ")
				.append("from t_goods g where g.status = 'A' and g.retailer_id ='")
				.append(retailerId).append("' ")
				;
		if(StringUtil.isNotEmpty(goodsName)){
			sql.append(" and g.goods_name like '%").append(goodsName).append("%'");
		}
		if(StringUtil.isNotEmpty(goodsCode)){
			sql.append(" and g.goods_code like '%").append(goodsCode).append("%'");
		}
		if(StringUtil.isNotEmpty(brandName)){
			sql.append(" and g.brand_name like '%").append(brandName).append("%'");
		}
		if(StringUtil.isNotEmpty(goodsStatus)){
			sql.append(" and g.goods_status = '").append(goodsStatus).append("'");
		}
		return this.tGoodsService.findObjForJdbc(sql.toString(), TGoodsSimpleVo.class);
	}
	
	/**
	 * 商品导入功能跳转 20171108新增
	 * @return
	 */
	@RequestMapping(params = "uploadGoods")
	public ModelAndView uploadGoods(HttpServletRequest req) {
		return new ModelAndView("common/upload/goods_upload");
	}
	
	/**
	 * 商品批量导入 20171108新增
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importGoodsExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importGoodsExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		Long t1 = System.currentTimeMillis();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile file = entity.getValue();// 获取上传文件对象
				FileInputStream is = (FileInputStream) file.getInputStream();
				//导入数据，做非空校验及数字校验
				Long tt1 = System.currentTimeMillis();
				Map<String,Object> map = loadGoodsExecl(is);
				Long tt2 = System.currentTimeMillis();
				System.out.println("excel非空校验共耗时====="+(tt2-tt1)+"ms");
				Object errMsg = map.get("errMsg");//错误信息
				Object errKey = map.get("errKey");//错误excel上传七牛的key
				if(Utility.isNotEmpty(errMsg)){
					j.setMsg(errMsg+"");
					if(Utility.isNotEmpty(errKey)){
						j.setObj(errKey);//从七牛下载错误信息excel
						j.setSuccess(false);
					}
				}else if(Utility.isNotEmpty(errKey)){
					j.setObj(errKey);//从七牛下载错误信息excel
					j.setSuccess(false);
				}else{
					//校验并批量保存商品相关信息
					List<TNewGoodsImportVo> importList = (List<TNewGoodsImportVo>) map.get("importList");//导入数据列表
					TSpecHeadersEntity headers = (TSpecHeadersEntity)map.get("headers");//规格标题
					
					//批量校验并保存导入的商品基础数据
					Map<String,String> resultMap = tGoodsService.batchSaveNewGoods(importList,headers);
					String result = resultMap.get("result");
					errKey = resultMap.get("errKey");
					if("OK".equals(result)){//导入成功
						String successCount = resultMap.get("successCount");
						j.setMsg(successCount+"条记录已成功导入");
					}else{
						String errCount = resultMap.get("errCount");
						j.setObj(errKey);//从七牛下载错误信息excel
						j.setMsg(errCount+"条有问题的记录导出中...");
						j.setSuccess(false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			j.setMsg("文件导入失败，请检查单元格格式和模版格式是否一致！");
		}
		Long t2 = System.currentTimeMillis();
		System.out.println("商品导入结果========="+j.getMsg());
		System.out.println("商品导入和非空校验共耗时====="+(t2-t1)+"ms");
		return j;
	}
	
	/**获取导入的excel的商品和规格及库存列表
	 * @param is
	 * @return List<TNewGoodsImportVo> importList 无错误的时候返回数据列表
	 * errMsg 超过3000记录的时候会返回该key
	 * errKey 导入记录有错误的时候会返回该key，用于导出错误信息列表
	 * @throws IOException
	 */
	public Map<String,Object> loadGoodsExecl(FileInputStream is) throws IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		String errMsg = null;//错误消息
		// 根据指定的文件输入流导入Excel从而产生Workbook对象
		Workbook wb;
		try {
//			Long ct1 = System.currentTimeMillis();
			wb = WorkbookFactory.create(is);
//			Long ct2 = System.currentTimeMillis();
//			System.out.println("FileInputStream转Workbook耗时====="+(ct2-ct1)+"ms");
			// 获取Excel文档中的第一个表单
			Sheet sht0 = wb.getSheetAt(0);//商品sheet页
			// 对Sheet中的每一行进行迭代
			int n0 = sht0.getLastRowNum();//从0开始
			System.out.println("sht0检测总RowNum:"+(n0+1));
			if(n0>3000){
				errMsg = "单次上传请勿超过3000条记录,检测到共有"+n0+"条记录";
				map.put("errMsg", errMsg);
				return map;
			}
			int rowNum0 = 0;//基础信息行数
			boolean hasError = false;//是否有错误信息
			if(n0>0){
				List<TNewGoodsImportVo> importList = new ArrayList<TNewGoodsImportVo>();//基础数据
				TSpecHeadersEntity headers = new TSpecHeadersEntity();//规格头信息
				for(int i=0;i<=n0;i++){
					Row r = sht0.getRow(i);
					if(r==null){
						break;
					}
					Cell c0 = r.getCell(0);//商品名称
					Cell c1 = r.getCell(1);//品牌
					Cell c2 = r.getCell(2);//款号
					Cell c3 = r.getCell(3);//原价
					Cell c4 = r.getCell(4);//最低价折扣
					Cell c5 = r.getCell(5);//二级类目
					Cell c6 = r.getCell(6);//三级类目
					Cell c7 = r.getCell(7);//规格1
					Cell c8 = r.getCell(8);//规格2
					Cell c9 = r.getCell(9);//库存
					Cell c10 = r.getCell(10);//条码
					if(i==0){//判断模版列数和获取规格列名
						int colums = r.getLastCellNum();//列数
						if(colums!=11){
							errMsg = "请下载新模板进行导入";
							map.put("errMsg", errMsg);
							return map;
						}
						headers.setHeaderOne(c7.getStringCellValue());//规格1
						headers.setHeaderTwo(c8.getStringCellValue());//规格2
					}
					if(i>0){
						String v0 = c0+"";
						String v1 = c1+"";
						String v2 = c2+"";
						String v3 = c3+"";
						String v4 = c4+"";
						String v5 = c5+"";
						String v6 = c6+"";
						String v7 = c7+"";
						String v8 = c8+"";
						String v9 = c9+"";
						String v10 = c10+"";
						if((c0==null||Utility.isEmpty(v0))
								&&(c1==null||Utility.isEmpty(v1))
								&&(c2==null||Utility.isEmpty(v2))
								&&(c3==null||Utility.isEmpty(v3))
								&&(c4==null||Utility.isEmpty(v4))
								&&(c5==null||Utility.isEmpty(v5))
								&&(c6==null||Utility.isEmpty(v6))
								&&(c7==null||Utility.isEmpty(v7))
								&&(c8==null||Utility.isEmpty(v8))
								&&(c9==null||Utility.isEmpty(v9))
								&&(c10==null||Utility.isEmpty(v10))
							){
							//全部列为空则不继续判断
							break;
						}
						rowNum0++;
						TNewGoodsImportVo baseVo = new TNewGoodsImportVo();//库存vo
						if(Utility.isNotEmpty(v0)){//商品名称
							baseVo.setGoodsName(v0.trim());
						}
						if(Utility.isNotEmpty(v1)){//品牌
							baseVo.setBrandName(v1.trim());
						}else{
							baseVo.setRemark(baseVo.getRemark()+"，品牌不能为空");
							hasError = true;
						}
						if(Utility.isNotEmpty(v2)){//款号
							baseVo.setGoodsCode(v2.trim());
						}else{
							baseVo.setRemark(baseVo.getRemark()+"，款号不能为空");
							hasError = true;
						}
						
						if(Utility.isNotEmpty(v3)){//原价
							int n = v3.indexOf(".")+1;
							int l = v3.length();
							if(l-n>2&&n>0){
								baseVo.setRemark(baseVo.getRemark()+"，原价不能超过2位小数");
								hasError = true;
							}else{
								try {
									baseVo.setOriginalPrice(new BigDecimal(v3));
								} catch (Exception e) {
									baseVo.setRemark(baseVo.getRemark()+"，原价不是数字");
									hasError = true;
									e.printStackTrace();
								}
							}
						}else{
							baseVo.setRemark(baseVo.getRemark()+"，原价不能为空");
							hasError = true;
						}
						if(Utility.isNotEmpty(v4)){//最低折扣
							BigDecimal lowestPriceDiscount = BigDecimal.ZERO;
							try {
								lowestPriceDiscount= new BigDecimal(v4.trim());
							} catch (Exception e1) {
								baseVo.setRemark(baseVo.getRemark()+"，最低折扣不是有效数字");
								e1.printStackTrace();
							}
							if(lowestPriceDiscount.compareTo(BigDecimal.ONE)>0||lowestPriceDiscount.compareTo(BigDecimal.ZERO)<0){
								baseVo.setRemark(baseVo.getRemark()+"，最低折扣不是有效数字");//大于1或者小于0
								hasError = true;
							}
							int n = v4.indexOf(".")+1;
							int l = v4.length();
							if(l-n>2&&n>0){
								baseVo.setRemark(baseVo.getRemark()+"，最低折扣不能超过2位数字");
								hasError = true;
							}else{
								try {
									baseVo.setLowestPriceDiscount(lowestPriceDiscount.setScale(2, BigDecimal.ROUND_HALF_UP));
								} catch (Exception e1) {
									baseVo.setRemark(baseVo.getRemark()+"，最低折扣不是有效数字");
									e1.printStackTrace();
								}
								try {
									//保留2为小数
									baseVo.setLowestPrice(baseVo.getOriginalPrice().multiply(lowestPriceDiscount).setScale(2, BigDecimal.ROUND_HALF_UP));
								} catch (Exception e) {
									hasError = true;
									LogUtil.error("商品批量导入第"+(i+1)+"行出现错误："+e.getMessage());
									e.printStackTrace();
								}
							}
						}else{
							baseVo.setLowestPriceDiscount(BigDecimal.ZERO);
							baseVo.setLowestPrice(BigDecimal.ZERO);
						}
						if(Utility.isNotEmpty(v5)){//二级类目
							baseVo.setSubCategoryName(v5.trim());
						}else{
							baseVo.setRemark(baseVo.getRemark()+"，二级类目不能为空");
							hasError = true;
						}
						if(Utility.isNotEmpty(v6)){//三级类目
							baseVo.setThridCategoryName(v6.trim());
						}else{
							baseVo.setRemark(baseVo.getRemark()+"，三级类目不能为空");
							hasError = true;
						}
						if(Utility.isNotEmpty(v7)){//规格一
							baseVo.setSpecificationOne(v7.trim());
						}else{
							baseVo.setRemark(baseVo.getRemark()+"，"+headers.getHeaderOne()+"不能为空");
							hasError = true;
						}
						if(Utility.isNotEmpty(v8)){//规格二
							baseVo.setSpecificationTwo(v8.trim());
						}
						if(Utility.isNotEmpty(v9)){//库存
							//TODO 判断整数
							int n = v9.trim().indexOf(".");
							if(n>0){
								v9=v9.substring(0, n);
							}
							try {
								baseVo.setStore(new BigDecimal(v9));
							} catch (Exception e) {
								baseVo.setRemark(baseVo.getRemark()+headers.getHeaderOne()+"，库存不是数字");
								hasError = true;
								e.printStackTrace();
							}
						}
						
						if(Utility.isNotEmpty(v10)){//条码
							baseVo.setBarCode(v10.trim());
						}
						importList.add(baseVo);
					}
				}
				if(hasError){
					for(int i=0;i<importList.size();i++){
						TNewGoodsImportVo vo = importList.get(i);
						if(Utility.isEmpty(vo.getRemark())){
							importList.remove(vo);//去掉没有错误的记录
							i--;
						}else{
							vo.setRemark(vo.getRemark().substring(1));
						}
					}
					errMsg = importList.size()+"条有问题的记录导出中...";
					map.put("errMsg", errMsg);
					//上传七牛
					String title = "商品导入错误提示（请在原导入表中修改好后再次导入）";
					String key = this.tGoodsService.uploadExcelFileToQN(TNewGoodsImportVo.class,importList,null,"goodsUpload",title);
					map.put("errKey", key);
				}else{
					map.put("headers", headers);//规格标题
					map.put("importList", importList);//导入记录
				}
			}
			System.out.println("sht0有效数据总RowNum:"+rowNum0);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally {
			is.close();
		}
		return map;
	}
	
	 protected boolean isIE(HttpServletRequest request) {
	        return (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 || request
	            .getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0) ? true : false;
	 }
	 
	/**
	 * 下载库存excel模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "downloadStockTemp")
	public ModelAndView downloadStockTemp( HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\templates");
		String fileName = "商品库存导入模板.xlsx";
		String filePath = path + "\\" + fileName;
		FileUtils.downLoadFile(filePath, response, request, fileName, "xlsx");
		return null;
	}
	
	/**批量导入库存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "imporStockExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson imporStockExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile file = entity.getValue();// 获取上传文件对象
				FileInputStream is = (FileInputStream) file.getInputStream();
				//导入数据，做非空校验及数字校验
//				Long tt1 = System.currentTimeMillis();
				Map<String,Object> map = loadStockExecl(is);
//				Long tt2 = System.currentTimeMillis();
//				System.out.println("excel非空校验共耗时====="+(tt2-tt1)+"ms");
				Object errMsg = map.get("errMsg");//错误信息
				Object errKey = map.get("errKey");//错误excel上传七牛的key
				if(Utility.isNotEmpty(errMsg)){
					j.setMsg(errMsg+"");
					if(Utility.isNotEmpty(errKey)){
						j.setObj(errKey);//从七牛下载错误信息excel
						j.setSuccess(false);
					}
				}else if(Utility.isNotEmpty(errKey)){
					j.setObj(errKey);//从七牛下载错误信息excel
					j.setSuccess(false);
				}else{
					//校验并批量保存商品相关信息
					@SuppressWarnings("unchecked")
					List<GoodsStockImportVo> importList = (List<GoodsStockImportVo>) map.get("importList");//导入数据列表
					
					//批量更新商品库存
					Map<String,String> resultMap = tGoodsService.batchUpdateGoodsStock(importList);
					String result = resultMap.get("result");
					errKey = resultMap.get("errKey");
					if("OK".equals(result)){//导入成功
						String successCount = resultMap.get("successCount");
						j.setMsg(successCount+"条记录已成功导入");
					}else{
						String errCount = resultMap.get("errCount");
						j.setObj(errKey);//从七牛下载错误信息excel
						j.setMsg(errCount+"条有问题的记录导出中...");
						j.setSuccess(false);
					}
				}
			}
		} catch (Exception e) {
			j.setMsg("文件导入失败，请检查单元格格式和模版格式是否一致！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		return j;
	}
	
	/**获取导入的excel的条码库存列表
	 * @param is
	 * @return List<GoodsStockImportVo> importList 无错误的时候返回数据列表
	 * errMsg 超过3000记录的时候会返回该key
	 * errKey 导入记录有错误的时候会返回该key，用于导出错误信息列表
	 * @throws IOException
	 */
	public Map<String,Object> loadStockExecl(FileInputStream is) throws IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		String errMsg = null;//错误消息
		// 根据指定的文件输入流导入Excel从而产生Workbook对象
		Workbook wb;
		try {
			wb = WorkbookFactory.create(is);
			// 获取Excel文档中的第一个表单
			Sheet sht0 = wb.getSheetAt(0);//商品sheet页
			// 对Sheet中的每一行进行迭代
			int n0 = sht0.getLastRowNum();//从0开始
			System.out.println("sht0检测总RowNum:"+(n0+1));
			if(n0>3000){
				errMsg = "单次上传请勿超过3000条记录,检测到共有"+n0+"条记录";
				map.put("errMsg", errMsg);
				return map;
			}
			int rowNum0 = 0;//基础信息行数
			boolean hasError = false;//是否有错误信息
			if(n0>0){
				List<GoodsStockImportVo> importList = new ArrayList<GoodsStockImportVo>();
				for(int i=0;i<=n0;i++){
					Row r = sht0.getRow(i);
					if(r==null){
						break;
					}
					Cell c0 = r.getCell(0);//条码
					Cell c1 = r.getCell(1);//库存
					if(i==0){//导入列名
						//
					}
					if(i>0){
						String v0 = c0+"";
						String v1 = c1+"";
						if((c0==null||Utility.isEmpty(v0))&&(c1==null||Utility.isEmpty(v1))){
							//全部列为空则不继续判断
							break;
						}
						rowNum0++;
						GoodsStockImportVo baseVo = new GoodsStockImportVo();
						if(Utility.isNotEmpty(v0)){//条码
							baseVo.setBarCode(v0.trim());
						}else{
							baseVo.setRemark(baseVo.getRemark()+"，条码不能为空");
							hasError = true;
						}
						if(Utility.isNotEmpty(v1)){//库存
							//TODO 判断整数
							int n = v1.trim().indexOf(".");
							if(n>0){
								v1=v1.substring(0, n);
							}
							try {
								BigDecimal stock = new BigDecimal(v1);
								if(stock.compareTo(BigDecimal.ZERO)<0){
									baseVo.setRemark(baseVo.getRemark()+"，库存不是有效数字");
									hasError = true;
								}else{
									baseVo.setStock(stock);
								}
							} catch (Exception e) {
								baseVo.setRemark(baseVo.getRemark()+"，库存不是有效数字");
								hasError = true;
								e.printStackTrace();
							}
						}else{
							baseVo.setRemark(baseVo.getRemark()+"，库存不能为空");
							hasError = true;
						}
						importList.add(baseVo);
					}
				}
				if(hasError){
					for(int i=0;i<importList.size();i++){
						GoodsStockImportVo vo = importList.get(i);
						if(Utility.isEmpty(vo.getRemark())){
							importList.remove(vo);//去掉没有错误的记录
							i--;
						}else{
							vo.setRemark(vo.getRemark().substring(1));
						}
					}
					errMsg = importList.size()+"条有问题的记录导出中...";
					map.put("errMsg", errMsg);
					//上传七牛
					String title = "库存导入错误提示（请在原导入表中修改好后再次导入）";
					String key = this.tGoodsService.uploadExcelFileToQN(GoodsStockImportVo.class,importList,null,"goodsStockUpload",title);
					map.put("errKey", key);
				}else{
					map.put("importList", importList);//导入记录
				}
			}
			System.out.println("sht0有效数据总RowNum:"+rowNum0);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally {
			is.close();
		}
		return map;
	}
}
