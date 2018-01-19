package com.buss.goods.controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.CategoryServiceI;
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

import com.buss.base.entity.BaseActivityEntity;
import com.buss.goods.entity.TExcludeRuleEntity;
import com.buss.goods.entity.TGoodsAttrEntity;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.goods.entity.TGoodsStoreEntity;
import com.buss.goods.entity.TSpecHeadersEntity;
import com.buss.goods.entity.TVisibleCategriesEntity;
import com.buss.goods.service.TGoodsServiceI;
import com.buss.goods.vo.TGoodsImportVo;
import com.buss.goods.vo.TGoodsSellVo;
import com.buss.goods.vo.TGoodsStoreVo;
import com.buss.visibleGoods.entity.TRetailerGoodsConditionsEntity;

import cn.redis.service.RedisService;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 商品表
 * @author onlineGenerator
 * @date 2016-03-17 20:05:06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGoodsController")
public class TGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGoodsController.class);

	@Autowired
	private TGoodsServiceI tGoodsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CategoryServiceI categoryService;
	@Autowired
	private RedisService redisService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 系统商品表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String goods_status = request.getParameter("goods_status");
		if(StringUtil.isNotEmpty(goods_status)){
			request.setAttribute("goods_status", goods_status);
			if(TGoodsEntity.GOODS_STATUS_4.equals(goods_status)){
				return new ModelAndView("com/buss/goods/tGoodsList-sell");
//			}else if(TGoodsEntity.GOODS_STATUS_1.equals(goods_status)){
//				return new ModelAndView("com/buss/goods/tGoodsList-toApprove");
			}
			return new ModelAndView("com/buss/goods/tGoodsList");
		}
		return new ModelAndView("com/buss/goods/tGoodsList-all");
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
			if(TGoodsEntity.GOODS_STATUS_4.equals(goods_status)){
				return new ModelAndView("com/buss/goods/tGoodsListOfRetailer-sell");
			}
			return new ModelAndView("com/buss/goods/tGoodsListOfRetailer");
		}
		return new ModelAndView("com/buss/goods/tGoodsListOfRetailer-all");
	}
	
	/**
	 * 云仓商品表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "cloudList")
	public ModelAndView cloudList(HttpServletRequest request) {
		return new ModelAndView("com/buss/goods/tGoodsListOfcloud");
	}
	
	/**
	 * 零售商商品库存查询列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "storeList")
	public ModelAndView storeList(HttpServletRequest request) {
		String type = request.getParameter("type");
		String change = request.getParameter("change");
		//type=0:库存为0,type=1:库存低于预警库存(到具体规格),type=2:某些规格为0的断码
		request.setAttribute("type", type);
		if("1".equals(change)){//修改库存的时候返回列表
			this.changeNum(type,request);
		}
		if("0".equals(type)){//库存为0
			return new ModelAndView("com/buss/goods/stockZeroList");
		}else if("1".equals(type)){//库存低于预警库存
			return new ModelAndView("com/buss/goods/stockAlarmList");
		}else{//某些规格为0的断码
			return new ModelAndView("com/buss/goods/stockLessList");
		}
	}
	
	/**
	 * 组合单品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "detailList")
	public ModelAndView detailList(HttpServletRequest request) {
		request.setAttribute("retailer_Id", request.getParameter("retailer_Id"));
		request.setAttribute("group_Source", request.getParameter("group_Source"));
		request.setAttribute("goods_Type", request.getParameter("goods_Type"));
		return new ModelAndView("com/buss/goods/tGoodsDetailList");
	}
	/**
	 * 场景选择上新单品列表
	 * @return
	 */
	@RequestMapping(params = "singleList")
	public ModelAndView singleList(HttpServletRequest request) {
		request.setAttribute("retailer_Id", request.getParameter("retailer_Id"));
		return new ModelAndView("com/buss/goods/tGoodsSingleList");
	}
	
	/**
	 * 组合单品列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toGoodsList")
	public ModelAndView toGoodsList(HttpServletRequest request) {
		return new ModelAndView("com/buss/goods/tGoods-List");
	}

	
	/**
	 * 活动商品，资讯分类商品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "goodsList")
	public ModelAndView goodsList(HttpServletRequest request) {
		String retailer_Type = request.getParameter("retailer_Type");//1：零售商，2：云商
		String activityId = request.getParameter("activityId");//活动ID,资讯ID
//		String contentId = request.getParameter("contentId");
		request.setAttribute("retailer_Type", retailer_Type);
		request.setAttribute("activityId", activityId);
		return new ModelAndView("com/buss/goods/goodsList-sell");
	}
	
	/**
	 * //当红人气/热销商品,一键齐全,推荐 页面跳转
	 * @return
	 */
	@RequestMapping(params = "hotGoodsList")
	public ModelAndView hotGoodsList(HttpServletRequest request) {
		String content_Id = request.getParameter("content_Id");
		request.setAttribute("content_Id", content_Id);
		return new ModelAndView("com/buss/goods/hotGoodsList-sell");
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
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		//查询条件组装器
		try{
			cq.eq("goodsType", TGoodsEntity.GOODS_TYPE_1);//单品
			cq.eq("newGoodsType", TGoodsEntity.NEW_GOODS_TYPE_1);//旧模块商品
		//自定义追加查询条件
			String goods_status = request.getParameter("goods_status");
			if(StringUtil.isNotEmpty(goods_status)){
				cq.eq("goodsStatus", goods_status);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		String retailerId = ResourceUtil.getRetailerId();
		String sys_recommend = request.getParameter("sys_recommend");
		Map<String, String[]> map = request.getParameterMap();
		//【录入商品】系统推荐查 询商品，包括云仓的（除开相斥原则的）销售中的商品
		if(StringUtil.isNotEmpty(sys_recommend)){
			StringBuffer condtions = new StringBuffer();
			//可见商品条件
			TRetailerGoodsConditionsEntity goodsCondition = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailerId);
			if(Utility.isEmpty(goodsCondition)){
				cq.eq("retailerId", retailerId);
			}else{//按新的查询方式查询
				condtions.append(" (retailer_id = '").append(retailerId).append("'  OR ").append(goodsCondition.getRetailerConditions()).append(" OR ").append(goodsCondition.getCloudConditions()).append(")");
			}
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
		}else{
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map);
		}
		
		cq.add();
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 通过活动/资讯分类的activity_Id 选择商品（包括组合）
	 * 改为查询新品
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid2")
	public void datagrid2(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
			TSUser user = ResourceUtil.getSessionUserName();
//			String retailerType = request.getParameter("retailer_Type");
			String activity_Id = request.getParameter("activity_Id");//活动ID,资讯ID（base_activity的ID）
			String retailerId = null;
			CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		try{
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
				retailerId = user.getId();
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				retailerId = user.getRetailerId();
			}else{//后台查询活动商品，则零售商ID通过活动ID去查询，(系统默认的活动没有零售商ID)
				BaseActivityEntity activity = this.systemService.get(BaseActivityEntity.class, activity_Id);
				if(!Utility.isEmpty(activity)){
					retailerId = activity.getRetailerId();
				}
			}
			//可见商品条件
//			TRetailerGoodsConditionsEntity goodsCondition = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailerId);
			//查询条件组装器
			Map<String, String[]> map = request.getParameterMap();
			//【云仓商品】
			StringBuffer condtions = new StringBuffer();
//			if(TGoodsEntity.RETAILER_TYPE_2.equals(retailerType)){//云仓的
//				if(Utility.isEmpty(goodsCondition)){
//					condtions.append(" 1<>1 ");
//				}else{//按新的查询方式查询
//					condtions.append(" ( ").append(goodsCondition.getRetailerConditions()).append(" OR ").append(goodsCondition.getCloudConditions()).append(")");
//				}
//			}else 
//			if(TGoodsEntity.RETAILER_TYPE_1.equals(retailerType)){//本地仓商品(包括单品和组合)20161123修改
//				cq.eq("retailerId", retailerId);
				
//			}else{//资讯分类 查询商品没有retailerType
//				if(Utility.isEmpty(goodsCondition)){
//				}else{//按新的查询方式查询
//					condtions.append(" (retailer_id = '").append(retailerId).append("'  OR ").append(goodsCondition.getRetailerConditions()).append(" OR ").append(goodsCondition.getCloudConditions()).append(")");
//				}
//			}
			cq.eq("retailerId", retailerId);
			//过滤已经添加的商品
			if(Utility.isEmpty(condtions)){
				condtions.append(" id not in (select goods_id from t_activity_goods where status = 'A' and activity_Id = '").append(activity_Id).append("') ");
			}else{
				condtions.append(" and id not in (select goods_id from t_activity_goods where status = 'A' and activity_Id = '").append(activity_Id).append("') ");
			}
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
				cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);
				cq.eq("newGoodsType", TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
				cq.eq("goodsType", TGoodsEntity.GOODS_TYPE_1);//20170103修改
//			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 当红人气/热销商品,一键齐全,推荐 选择商品（包括组合）没有活动ID或者资讯分类ID
	 * 改为查询新品
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid3")
	public void datagrid3(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String retailerId = ResourceUtil.getRetailerId();
		String content_Id = request.getParameter("content_Id");
		String conId_condion = "";
		if(StringUtil.isNotEmpty(content_Id)){
			conId_condion = " and content_Id = '"+content_Id+"' ";
		}
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
	try{
		//查询条件组装器
		Map<String, String[]> map = request.getParameterMap();
		//【云仓商品】
		StringBuffer condtions = new StringBuffer();
		condtions.append("  NOT EXISTS(select 1 from t_activity_goods a where a.status = 'A' and source ='3' "+conId_condion+" and a.retailer_Id = '")
		.append(retailerId).append("' and a.goods_id = this_.id )");
//		TRetailerGoodsConditionsEntity goodsCondition = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailerId);
//		if(Utility.isEmpty(goodsCondition)){
			cq.eq("retailerId", retailerId);
//		}else{//按新的查询方式查询
//			condtions.append(" and (retailer_id = '").append(retailerId).append("'  OR ").append(goodsCondition.getRetailerConditions()).append(" OR ").append(goodsCondition.getCloudConditions()).append(")");
//		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
		cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);
		cq.eq("newGoodsType", TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
//		}
	}catch (Exception e) {
		throw new BusinessException(e.getMessage());
	}
	cq.add();
	this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	
	/**
	 * easyui 查询组合中单品列表(按新的查询方式查询)
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridOfdetail")
	public void datagridOfdetail(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		//查询条件组装器
		String retailer_Id = request.getParameter("retailer_Id");
//		String group_Source = request.getParameter("group_Source");
		String goods_Type = request.getParameter("goods_Type");//组合和混搭会有这个值
		if(Utility.isEmpty(retailer_Id)){
			retailer_Id = ResourceUtil.getRetailerId();
		}
		try{
			cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);//上架的
			cq.eq("goodsType", TGoodsEntity.GOODS_TYPE_1);//单品
			if(!StringUtil.isEmpty(goods_Type)){
				cq.eq("newGoodsType", TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
			}else{
				cq.eq("newGoodsType", TGoodsEntity.NEW_GOODS_TYPE_1);//旧模块商品
			}
			Map<String, String[]> map = request.getParameterMap();
//			StringBuffer condtions = new StringBuffer();
			if(!StringUtil.isEmpty(goods_Type)){//新模块商品
//				TRetailerGoodsConditionsEntity goodsCondition = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailer_Id);
//				if(Utility.isEmpty(goodsCondition)){
					cq.eq("retailerId", retailer_Id);
//				}else{//按新的查询方式查询
//					condtions.append(" (retailer_id = '").append(retailer_Id).append("'  OR ").append(goodsCondition.getRetailerConditions()).append(" OR ").append(goodsCondition.getCloudConditions()).append(")");
//				}
			}
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map);
//			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	//场景查询单品列表
	@RequestMapping(params = "datagridOfsingle")
	public void datagridOfsingle(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		//查询条件组装器
		Map<String, String[]> map = request.getParameterMap();
		try{
		//自定义追加查询条件
			String retailer_Id = request.getParameter("retailer_Id");
			cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);//上架的
			cq.eq("goodsType", TGoodsEntity.GOODS_TYPE_1);//单品
			cq.eq("newGoodsType", TGoodsEntity.NEW_GOODS_TYPE_2);//新模块商品
			if(StringUtil.isNotEmpty(retailer_Id)){//资讯查询所有单品
				cq.eq("retailerId", retailer_Id);
			}
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map);
//			else{//零售商查询单品（相斥原则）
//				StringBuffer condtions = new StringBuffer();
//				TRetailerGoodsConditionsEntity goodsCondition = this.systemService.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailer_Id);
//				if(Utility.isEmpty(goodsCondition)){
//				}else{//按新的查询方式查询
//					condtions.append(" (retailer_id = '").append(retailer_Id).append("'  OR ").append(goodsCondition.getRetailerConditions()).append(" OR ").append(goodsCondition.getCloudConditions()).append(")");
//				}
//				org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
//			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 云商商品列表(暂时不管旧商品)
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	
	@RequestMapping(params = "datagridOfCloud")
	public void datagridOfCloud(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUserName();
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		@SuppressWarnings("unchecked")
		Map<String, String[]> map = request.getParameterMap();
		//相斥原则
		List<TExcludeRuleEntity> ruleList = null;
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
				ruleList = systemService.findByProperty(TExcludeRuleEntity.class, "retailerId", user.getId());
		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
			ruleList = systemService.findByProperty(TExcludeRuleEntity.class, "retailerId", user.getRetailerId());
		}
		StringBuffer condtions = new StringBuffer();
		condtions.append(" retailer_Type = '").append(TGoodsEntity.RETAILER_TYPE_2).append("' and status = 'A' ");
		if(!Utility.isEmpty(ruleList)){//排除相斥的商品
			condtions.append(" AND  NOT EXISTS ( select	1	from t_goods where goods_status = '4' and goods_type ='1' and retailer_Type = '2' and status = 'A' and id = this_.id and (");
			for(TExcludeRuleEntity rule:ruleList){
				condtions.append(" ").append(rule.getSqlStr()).append(" or ");
			}
			condtions.delete(condtions.length()-3, condtions.length());
			condtions.append(") )");
		}
		//系统推荐查询商品，包括云仓的（除开相斥原则的）
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions.toString());
		try{
			//自定义追加查询条件
			cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);
			cq.eq("goodsType", TGoodsEntity.GOODS_TYPE_1);//单品
			cq.eq("retailerType", TGoodsEntity.RETAILER_TYPE_2);//零售商 货(云商)
			cq.eq("newGoodsType", TGoodsEntity.NEW_GOODS_TYPE_1);//旧模块商品
			
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "storeDatagrid")
	public void storeDatagrid(TGoodsEntity tGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUserName();
		String retailerId = "";
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
			retailerId = user.getId();
		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
			retailerId = user.getRetailerId();
		}
		String type = request.getParameter("type");//type=0:库存为0,type=1:库存低于预警库存(到具体规格),type=2:某些规格为0的断码
		String condtions = "";
		if("0".equals(type)){//库存为零的
			condtions = "retailer_id ='"+retailerId+"' and goods_stock = 0 ";
		}else if("1".equals(type)){//低于预警库存的（某些具体规格，同一个商品的只算一次）
			condtions = getCondition(retailerId,type);
		}else{//type=2卖断货的(某些规格卖断货，同一个商品的只算一次)
			condtions = getCondition(retailerId,type);
		}
		CriteriaQuery cq = new CriteriaQuery(TGoodsEntity.class, dataGrid);
		//查询条件组装器
		Map<String, String[]> map = request.getParameterMap();
		//系统推荐查询商品，包括云仓的（除开相斥原则的）
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoods, map,condtions);
		try{
			//自定义追加查询条件
			if(Utility.isEmpty(condtions)){
				cq.eq("retailerId", retailerId);
				cq.eq("goodsType", TGoodsEntity.GOODS_TYPE_1);//单品
				cq.eq("goodsStatus", TGoodsEntity.GOODS_STATUS_4);
				cq.eq("newGoodsType", TGoodsEntity.NEW_GOODS_TYPE_1);//旧模块商品
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGoodsService.getDataGridReturn(cq, true);
		List<TGoodsEntity> list = dataGrid.getResults();
		if(Utility.isNotEmpty(list)){
			for(TGoodsEntity entity : list){
				entity.setSmallPic(entity.getSmallPic()+"?imageView2/1/w/80/h/80");
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	private String getCondition(String retailerId,String type) {
		String condtion = "";
		String sql = "select distinct(s.goods_id) as goodsId from t_goods t LEFT JOIN t_goods_store s ON t.id = s.goods_id where t.retailer_id ='"+retailerId
		+"' and t.status = 'A' and s.status ='A' and t.goods_type ='1' and t.goods_status = '4' ";
		if("1".equals(type)){//type=1:库存低于预警库存(到具体规格)
			sql +=" and s.alarm_goods_stock > s.store ";
		}else{//type=2:某些规格为0的断码
			sql +=" and s.store = 0 ";
		}
		List<Map<String, Object>> goodsIds = this.systemService.findForJdbc(sql, null);
		if(!Utility.isEmpty(goodsIds)){
			condtion = "id in(";
			for(Map<String, Object> map : goodsIds){
				Object goodsId = map.get("goodsId");
				condtion +="'"+goodsId+"',";
			}
			condtion = condtion.substring(0, condtion.length()-1);
			condtion +=")";
		}else{//没查到结果
			condtion = " 1=2 ";
		}
		return condtion;
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
		message = "商品表删除成功";
		try{
			tGoodsService.deleteGoods(tGoods.getId());
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
//				TGoodsEntity tGoods = systemService.getEntity(TGoodsEntity.class,	id	);
				tGoodsService.deleteGoods(id);
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

	/**校验三级类目是否存在
	 * @param topCategoryId
	 * @param subCategoryId
	 * @param thridCategoryId
	 * @return
	 */
	private HashMap<String,String> checkExist( String subCategoryId,	String thridCategoryId) {
		HashMap<String,String> map =  new HashMap<String, String>();
		TSCategoryEntity third = this.systemService.get(TSCategoryEntity.class, thridCategoryId);
		TSCategoryEntity sub = this.systemService.get(TSCategoryEntity.class, subCategoryId);
		List<TSCategoryEntity> list = this.systemService.findByProperty(TSCategoryEntity.class, "parent.code", sub.getCode());
		for(TSCategoryEntity entity : list){
			if(third.getName().equals(entity.getName())){
				map.put("isExist", "Y");
				map.put("thirdCatgId", entity.getId());
				return map;
			}else{
				continue;
			}
		}
		map.put("isExist", "N");
		map.put("msg", "可见类目"+sub.getName()+" 下面不存在 "+third.getName());
		return map;
	}

	/**
	 * 商品下架，活动商品列表中的商品要删除
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
			tGoods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_5);
			tGoods.setUpdateDate(DateUtils.gettimestamp());
//			tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());
			tGoodsService.doDown(tGoods);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品下架失败";
			throw new BusinessException(e.getMessage());
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
	 * 审核不通过
	 * 
	 * @param id
	 * @return
	 */
	/*@RequestMapping(params = "doNotAudit")
	@ResponseBody
	public AjaxJson doNotAudit(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品审核不通过成功";
		try{
			tGoods = systemService.flushEntity(TGoodsEntity.class, tGoods.getId());
			tGoods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_2);
			tGoods.setUpdateDate(DateUtils.gettimestamp());
//			tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());
			tGoodsService.saveOrUpdate(tGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品审核不通过失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}*/
	
	/**
	 * 上架 并更新类目库存
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doUp")
	@ResponseBody
	public AjaxJson doUp(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品上架成功";
		try{
			tGoods = systemService.get(TGoodsEntity.class, tGoods.getId());
//			tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());
			tGoodsService.doUp(tGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品上架失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{
			//更新导购端集合店小红点版本号(商品上架)
			updateGoodsRedis(tGoods.getRetailerId());
		}catch (Exception e) {
			e.printStackTrace();
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
	public ModelAndView goStep1(TGoodsEntity tGoods, HttpServletRequest req) {
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
		cq.eq("level", 1);
		cq.addOrder("sort", SortDirection.asc);
		cq.add();
		List<TSCategoryEntity> catList = systemService.getListByCriteriaQuery(cq, false);
		req.setAttribute("catList",catList);
		return new ModelAndView("com/buss/goods/tGoods-step1");
	}
	
	/**
	 * 商品互斥选择分类
	 * 
	 * @return
	 */
	@RequestMapping(params = "getCategorys")
	public ModelAndView getCategorys(TGoodsEntity tGoods, HttpServletRequest req) {
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
		cq.eq("level", 1);
		cq.addOrder("sort", SortDirection.asc);
		cq.add();
		List<TSCategoryEntity> catList = systemService.getListByCriteriaQuery(cq, false);
		req.setAttribute("catList",catList);
		return new ModelAndView("com/buss/goods/getCategorys");
	}
	
	
	/**
	 * 商品表新增页面跳转到选择分类
	 * 
	 * @return
	 */
	@RequestMapping(params = "findChildClass")
	@ResponseBody
	public String  findChildClass(String id, HttpServletRequest req) {
		String sql = " select id,name from t_s_category where  parent_id='"+id+"' and status = 'A'  order by sort asc ";
		List<Object[]> list = systemService.findListbySql(sql);
		String jsonStr = JSONHelper.toJSONString(list);
		return jsonStr;
	}
	

	/**
	 * 商品表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TGoodsEntity tGoods, HttpServletRequest req) {
		req.setAttribute("tGoodsPage", tGoods);
		TSCategoryEntity cat = categoryService.get(TSCategoryEntity.class, tGoods.getThridCategoryId());
		req.setAttribute("thridCategory", cat.getName());
		return new ModelAndView("com/buss/goods/tGoods-add");
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
			int num = this.getNumOfMainPics(tGoods);
			req.setAttribute("mainPicsNum", num);
			List<TVisibleCategriesEntity> visiblelist = this.systemService.findByProperty(TVisibleCategriesEntity.class, "goodsId",	tGoods.getId());
			if(!Utility.isEmpty(visiblelist)){
				String categries = "";
				for(TVisibleCategriesEntity entity : visiblelist){
					categries += entity.getTopCategoryId()+",";
					categries += entity.getSubCategoryId()+",";
				}
				tGoods.setCategries(categries);
			}
			req.setAttribute("tGoodsPage", tGoods);
		}
		return new ModelAndView("com/buss/goods/tGoods-update");
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
	 * 商品推荐编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdateRecommondPics")
	public ModelAndView goUpdateRecommondPics(TGoodsEntity tGoods, HttpServletRequest req) {
		tGoods = tGoodsService.flushEntity(TGoodsEntity.class, tGoods.getId());
		req.setAttribute("tGoodsPage", tGoods);
//		tGoods.settSysRecmdPics(this.systemService.findByProperty(TSysRecmdPicsEntity.class, "goodsId", tGoods.getId()));
//		tGoods.settGuideRecmdPics(this.systemService.findByProperty(TGuideRecmdPicsEntity.class, "goodsId", tGoods.getId()));
//		if(Utility.isEmpty(tGoods.gettGuideRecmdPics())){
//			req.setAttribute("guideRecmdPicsNum", 0);
//		}else{
//			req.setAttribute("guideRecmdPicsNum", tGoods.gettGuideRecmdPics().size());
//		}
		return new ModelAndView("com/buss/goods/tGoodsRecommondPics-update");
	}
	
	
	/**
	 * 更新商品表
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdateStore")
	@ResponseBody
	public AjaxJson doUpdateStore(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品库存更新成功";
		try {
				tGoodsService.updateGoodsStore(tGoods);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品库存更新失败";
			j.setSuccess(false);
			logger.info(e.getMessage());
			System.out.print("商品库存更新失败:"+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新商品推荐
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdateRecommondPics")
	@ResponseBody
	public AjaxJson doUpdateRecommondPics(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品推荐更新成功";
		try {
				tGoodsService.updateRecommondPics(tGoods);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品推荐更新失败";
			j.setSuccess(false);
			logger.info(e.getMessage());
			System.out.print("商品推荐更新失败:"+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**获取最新的库存数量
	 * @param type
	 */
	private void changeNum(String type,HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		String retailerId = "";
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
			retailerId= user.getId();
		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
			retailerId = user.getRetailerId();
		}
		if(!StringUtil.isEmpty(retailerId)){
			//库存为零的
			String sql0 = "select count(1) from t_goods where retailer_id ='"+retailerId+"' and goods_status = '4' and goods_stock =0 and goods_type ='1' and status = 'A'";
			//卖断货的(某些规格卖断货，同一个商品的只算一次)
			String sql1 = "select count(distinct(s.goods_id)) from t_goods t LEFT JOIN t_goods_store s ON t.id = s.goods_id where t.retailer_id ='"+retailerId
				+"' and t.status = 'A' and s.status ='A' and t.goods_type ='1' and goods_status = '4'  and s.store = 0";
			//低于预警库存的(具体到规格,同一个商品的只算一次)
			String sql2 = "select count(distinct(s.goods_id)) from t_goods t LEFT JOIN t_goods_store s ON t.id = s.goods_id where t.retailer_id ='"+retailerId
			+"' and t.status = 'A' and s.status ='A' and t.goods_type ='1' and t.goods_status = '4'  and s.alarm_goods_stock > s.store ";
//			String sql2 = "select count(1) from t_goods where retailer_id ='"+retailerId+"' and goods_type ='1' and goods_status = '4'  and alarm_goods_stock > goods_stock and status = 'A'";
			if("0".equals(type)){//库存为0
				Long stockZeroNow = this.systemService.getCountForJdbc(sql0);
				if(stockZeroNow>99){
					request.setAttribute("stockZeroNow", "99+");
				}else{
					request.setAttribute("stockZeroNow", stockZeroNow);
				}
			}else if("1".equals(type)){//库存低于预警库存
				Long stockAlarmNow = this.systemService.getCountForJdbc(sql2);
				if(stockAlarmNow>99){
					request.setAttribute("stockAlarmNow", "99+");
				}else{
					request.setAttribute("stockAlarmNow", stockAlarmNow);
				}
			}else{//某些规格为0的断码
				Long stockLessNow = this.systemService.getCountForJdbc(sql1);
				if(stockLessNow>99){
					request.setAttribute("stockLessNow", "99+");
				}else{
					request.setAttribute("stockLessNow", stockLessNow);
				}
			}
		}
		
	}

	/**
	 * 角色列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "categryList")
	public ModelAndView categryList(HttpServletRequest request) {
		String goodsId = request.getParameter("goodsId");
		request.setAttribute("goodsId", goodsId);
		return new ModelAndView("com/buss/goods/categryList");
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
//			tGoods.settGoodsAttrDetails(tGoodsService.getGoodsAttrs(tGoods.getId()));
			tGoods.settGoodsStoreDetails(tGoodsService.getGoodsStores(tGoods.getId()));
			tGoods.settGoodsPicDetails(tGoodsService.getGoodsPics(tGoods.getId()));
			tGoods.setDesc(tGoodsService.getGoodsDesc(tGoods.getId()));
//			tGoods.settSysRecmdPics(this.systemService.findByProperty(TSysRecmdPicsEntity.class, "goodsId", tGoods.getId()));
//			tGoods.settGuideRecmdPics(this.systemService.findByProperty(TGuideRecmdPicsEntity.class, "goodsId", tGoods.getId()));
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
			if(Utility.isEmpty(tSpecHeader)){
				tSpecHeader = new TSpecHeadersEntity();
				tSpecHeader.setHeaderOne("颜色");
				tSpecHeader.setHeaderTwo("尺码");
				tSpecHeader.setHeaderThree("规格3");
			}
			req.setAttribute("tSpecHeader", tSpecHeader);
		}
		return new ModelAndView("com/buss/goods/tGoods-view");
	}
	
	/**
	 * 加载明细列表[关键词明细]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tGoodsAttrDetails")
	public ModelAndView tGoodsAttrDetails(String id, HttpServletRequest req) {
	    String hql0 = "from TGoodsAttrEntity where 1 = 1 and status = 'A' AND goodsId = ? ";
	    try{
	    	List<TGoodsAttrEntity> tGoodsAttrDetailsList = systemService.findHql(hql0,id);
	    	if(tGoodsAttrDetailsList!=null){
	 			int n = tGoodsAttrDetailsList.size()%3;
	 			if(n!=0){
	 				for(int i=n;i<3;i++){
	 					TGoodsAttrEntity attr = new TGoodsAttrEntity();
	 					tGoodsAttrDetailsList.add(attr);
	 				}
	 			}
	 		}
			req.setAttribute("tGoodsAttrDetails", tGoodsAttrDetailsList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/buss/goods/tGoodsAttrDetails");
	}
	
	/**
	 * 加载明细列表[关键词明细]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tGoodsAttrDetailsForUpdate")
	public ModelAndView tGoodsAttrDetailsForUpdate(String id, HttpServletRequest req) {
		String hql0 = "from TGoodsAttrEntity where 1 = 1 and status = 'A' AND goodsId = ? ";
		try{
			List<TGoodsAttrEntity> tGoodsAttrDetailsList = systemService.findHql(hql0,id);
			if(tGoodsAttrDetailsList!=null){
				int n = tGoodsAttrDetailsList.size()%3;
				if(n!=0){
					for(int i=n;i<3;i++){
						TGoodsAttrEntity attr = new TGoodsAttrEntity();
						tGoodsAttrDetailsList.add(attr);
					}
				}
			}
			req.setAttribute("tGoodsAttrDetails", tGoodsAttrDetailsList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/buss/goods/tGoodsAttrDetailsForUpdate");
	}
	
	/**
	 * 加载明细列表[库存明细]
	 * @return
	 */
	@RequestMapping(params = "tGoodsStoreDetails")
	public ModelAndView tGoodsStoreDetails(String id, HttpServletRequest req) {
		String hql0 = "from TGoodsStoreEntity where 1 = 1 and status = 'A' AND goodsId = ? ";
		try{
//			String topCategoryId = req.getParameter("topCategoryId");
//			TSCategoryEntity cat = this.systemService.get(TSCategoryEntity.class, topCategoryId);
//			if("衣".equals(cat.getName())){
//				req.setAttribute("isY", "1");
//			}else{
//				req.setAttribute("isY", "0");
//			}
			List<TGoodsStoreEntity> tGoodsStoreDetailsList = systemService.findHql(hql0,id);
			req.setAttribute("tGoodsStoreDetails", tGoodsStoreDetailsList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/buss/goods/tGoodsStoreDetails");
	}
	
	/**
	 * 加载明细列表[库存明细]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tGoodsStoreDetailsForUpdate")
	public ModelAndView tGoodsStoreDetailsForUpdate(String id, String isView,HttpServletRequest req) {
		String hql0 = "from TGoodsStoreEntity where  goodsId = ? order by specificationOne,specificationTwo desc";
		try{
			List<TGoodsStoreEntity> tGoodsStoreDetailsList = systemService.findHql(hql0,id);
			TSpecHeadersEntity tSpecHeader = systemService.findUniqueByProperty(TSpecHeadersEntity.class, "goodsId", id);
			if(Utility.isEmpty(tSpecHeader)){
				tSpecHeader = new TSpecHeadersEntity();
				tSpecHeader.setHeaderOne("颜色");
				tSpecHeader.setHeaderTwo("尺码");
//				tSpecHeader.setHeaderThree("规格3");
				tSpecHeader.setGoodsId(id);
				this.systemService.save(tSpecHeader);
			}
			req.setAttribute("tGoodsStoreDetails", tGoodsStoreDetailsList);
			req.setAttribute("tSpecHeader", tSpecHeader);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		req.setAttribute("isView", isView);
		return new ModelAndView("com/buss/goods/tGoodsStoreDetailsForUpdate");
	}
	
	/**
	 * 上传小图
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadSmallPic", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadSmallPic(HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		FtpUtil ftpUtil = FtpUtil.getInstance();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();// 获取上传文件对象
			String fileName = mf.getOriginalFilename();// 获取文件名
			String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
			String myfilename="";
			String noextfilename="";//不带扩展名
			noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+StringUtil.random(8);//自定义文件名称
			myfilename = noextfilename+"."+extend;//自定义文件名称
			InputStream is = mf.getInputStream();
			ftpUtil.uploadFileToFtpServer(GlobalConstants.GOODS_SMALL_PICS, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
							 +ResourceUtil.getConfigByName("imgRootPath")+"/"
							 +GlobalConstants.GOODS_SMALL_PICS+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}	
	/**
	 * 上传主图
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadMainPic", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadMainPic(TSCategoryEntity category,HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		FtpUtil ftpUtil = FtpUtil.getInstance();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();// 获取上传文件对象
			String fileName = mf.getOriginalFilename();// 获取文件名
			String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
			String myfilename="";
			String noextfilename="";//不带扩展名
			noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+StringUtil.random(8);//自定义文件名称
			myfilename = noextfilename+"."+extend;//自定义文件名称
			InputStream is = mf.getInputStream();
			ftpUtil.uploadFileToFtpServer(GlobalConstants.GOODS_MAIN_PICS, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
			+ResourceUtil.getConfigByName("imgRootPath")+"/"
			+GlobalConstants.GOODS_MAIN_PICS+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}	
	/**
	 * 上传主图
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadGuideRecomdPics", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadGuideRecomdPics(TSCategoryEntity category,HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		FtpUtil ftpUtil = FtpUtil.getInstance();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();// 获取上传文件对象
			String fileName = mf.getOriginalFilename();// 获取文件名
			String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
			String myfilename="";
			String noextfilename="";//不带扩展名
			noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+StringUtil.random(8);//自定义文件名称
			myfilename = noextfilename+"."+extend;//自定义文件名称
			InputStream is = mf.getInputStream();
			ftpUtil.uploadFileToFtpServer(GlobalConstants.GUIDE_RECMD_PICS, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
			+ResourceUtil.getConfigByName("imgRootPath")+"/"
			+GlobalConstants.GUIDE_RECMD_PICS+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}	
		
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tGoodsController");
		String newGoodsType = req.getParameter("newGoodsType");
		req.setAttribute("extraParam",newGoodsType);
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
		List<TGoodsSellVo> goodsList = null;
		try{
			goodsList = this.tGoodsService.getSellGoodsBySql(request);
		}catch(Exception e){
			e.printStackTrace();
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"在售商品列表");
		modelMap.put(NormalExcelConstants.CLASS,TGoodsSellVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("在售商品列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,goodsList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

//	@RequestMapping(params = "exportXlsByT")
//	public String exportXlsByT(TGoodsEntity tGoods,HttpServletRequest request,HttpServletResponse response
//			, DataGrid dataGrid,ModelMap modelMap) {
//    	modelMap.put(NormalExcelConstants.FILE_NAME,"商品表");
//    	modelMap.put(NormalExcelConstants.CLASS,TGoodsEntity.class);
//    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
//    	"导出信息"));
//    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
//    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
//	}

	/**
	 * 下载excel模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "downloadTemp")
	public ModelAndView downloadTemp( HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\templates");
		String fileName = "商品导入模板.xlsx";
		String filePath = path + "\\" + fileName;
		FileUtils.downLoadFile(filePath, response, request, fileName, "xlsx");
		return null;
	}
	
	
	/**
	 * 商品批量导入 20171108弃用
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		Long t1 = System.currentTimeMillis();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile file = entity.getValue();// 获取上传文件对象
				FileInputStream is = (FileInputStream) file.getInputStream();
				Map<String,Object> map = loadExecl(is);
				Object errMsg = map.get("errMsg");//
				if(Utility.isNotEmpty(errMsg)){
					j.setMsg(errMsg+"");
				}else{
					//校验并批量保存商品相关信息
					String msg = tGoodsService.batchSaveGoods(map);
					if(StringUtil.isEmpty(msg)){
						j.setMsg("文件导入成功！");
					}else{
						j.setMsg(msg);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			j.setMsg("文件导入失败！");
		}
		Long t2 = System.currentTimeMillis();
		
		System.out.println("商品导入结果========="+j.getMsg());
		System.out.println("商品导入共耗时=="+(t2-t1)+"ms");
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
	
	/**
	 * 获取预警，断码，零库存数量
	 */
	@RequestMapping(params = "getStore")
	@ResponseBody
	public AjaxJson getStore(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUserName();
		HashMap<String,Object> storeMap = new HashMap<String, Object>();
		String retailerId = "";
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
			retailerId= user.getId();
		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
			retailerId = user.getRetailerId();
		}
		if(!StringUtil.isEmpty(retailerId)){
			//库存为零的
			String sql0 = "select count(1) from t_goods where retailer_id ='"+retailerId+"' and goods_status = '4' and goods_stock =0 and goods_type ='1' and status = 'A'";
			//卖断货的(某些规格卖断货，同一个商品的只算一次)
			String sql1 = "select count(distinct(s.goods_id)) from t_goods t LEFT JOIN t_goods_store s ON t.id = s.goods_id where t.retailer_id ='"+retailerId
				+"' and t.status = 'A' and s.status ='A' and t.goods_type ='1' and t.goods_status = '4'  and s.store = 0";
			//低于预警库存的(具体到规格,同一个商品的只算一次)
			String sql2 = "select count(distinct(s.goods_id)) from t_goods t LEFT JOIN t_goods_store s ON t.id = s.goods_id where t.retailer_id ='"+retailerId
			+"' and t.status = 'A' and s.status ='A' and t.goods_type ='1' and t.goods_status = '4'  and s.alarm_goods_stock > s.store ";
//			String sql2 = "select count(1) from t_goods where retailer_id ='"+retailerId+"' and goods_type ='1' and goods_status = '4'  and alarm_goods_stock > goods_stock and status = 'A'";
			Long stockZero = this.systemService.getCountForJdbc(sql0);
			Long stockLess = this.systemService.getCountForJdbc(sql1);
			Long stockAlarm = this.systemService.getCountForJdbc(sql2);
			if(stockZero>99){
				storeMap.put("stockZero", "99+");
			}else{
				storeMap.put("stockZero", stockZero);
			}
			if(stockLess>99){
				storeMap.put("stockLess", "99+");
			}else{
				storeMap.put("stockLess", stockLess);
			}
			if(stockAlarm>99){
				storeMap.put("stockAlarm", "99+");
			}else{
				storeMap.put("stockAlarm", stockAlarm);
			}
		}
		j.setObj(storeMap);
		return j;
	}
	
	/**
	 
	@RequestMapping(params = "goChangeActivity")
	public ModelAndView goChangeActivity(TGoodsEntity tGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGoods.getId())) {
			req.setAttribute("id", tGoods.getId());
			req.setAttribute("retailerId", tGoods.getRetailerId());
			tGoods = this.tGoodsService.get(TGoodsEntity.class, tGoods.getId());
//			List<BaseActivityEntity> activityList = this.baseActivityService.findByProperty(BaseActivityEntity.class, "retailerId", tGoods.getRetailerId());
			List<BaseActivityEntity> activityList = this.baseActivityService.getList(BaseActivityEntity.class);
			req.setAttribute("activity", tGoods.getActivity());
			req.setAttribute("activityName", tGoods.getActivityName());
			req.setAttribute("activityList", activityList);
		}
		return new ModelAndView("com/buss/goods/tGoods-changeActivity");
	}
	
	@RequestMapping(params = "doChangeActivity")
	@ResponseBody
	public AjaxJson doChangeActivity(TGoodsEntity tGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "修改活动成功";
		try{
			tGoodsService.doChangeActivity(tGoods);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "修改活动失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{
			//更新导购端集合店小红点版本号(活动位商品更新)
			updateGoodsRedis(tGoods.getRetailerId());
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	*/
	
	private void updateGoodsRedis(String retailerId){
		TSUser user = systemService.get(TSUser.class, retailerId);
		if(TSUser.RETAILER_TYPE_REAL.equals(user.getRetailerType())){//零售商
			redisService.incr(common.GlobalConstants.GUIDE_GOODS_VERSION+"_"+retailerId);
		}else if(TSUser.RETAILER_TYPE_GOODS.equals(user.getRetailerType())){//云商
			List<Map<String, Object>> list = this.systemService.findForJdbc("select id from t_s_user where user_type = '02' and retailer_Type = '1' and user_status = '1' and status = 'A'", null);
			if(!Utility.isEmpty(list)){
				for(Map<String, Object> map : list){
					redisService.incr(common.GlobalConstants.GUIDE_GOODS_VERSION+"_"+map.get("id"));
				}
			}
		}
	}
	
	
	/**获取导入的excel的商品和规格及库存列表
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public Map<String,Object> loadExecl(FileInputStream is) throws IOException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<TGoodsImportVo> baseList = new ArrayList<TGoodsImportVo>();//基础数据
		List<TGoodsStoreVo> storeList = new ArrayList<TGoodsStoreVo>();//库存数据
		TSpecHeadersEntity headers = new TSpecHeadersEntity();//规格头信息
//		Set<String> brandNameSet = new HashSet<String>();//品牌set
		Set<String> goodsCodeSet = new HashSet<String>();//款号set
		Set<String> barCodeSet = new HashSet<String>();//条码set
//		Set<String> topCatSet = new HashSet<String>();//一级类目set
//		Set<String> subCatSet = new HashSet<String>();//二级类目set
//		Set<String> thirdCatSet = new HashSet<String>();//三级类目set
		String errMsg = null;//错误消息
		// 根据指定的文件输入流导入Excel从而产生Workbook对象
		Workbook wb;
		try {
			wb = WorkbookFactory.create(is);
			// 获取Excel文档中的第一个表单
			Sheet sht0 = wb.getSheetAt(0);//商品sheet页
			Sheet sht1 = wb.getSheetAt(1);//规格sheet页
			// 对Sheet中的每一行进行迭代
			int n0 = sht0.getLastRowNum();//从0开始
			int n1 = sht1.getLastRowNum();
			System.out.println("sht0检测总RowNum:"+(n0+1));
			System.out.println("sht1检测总RowNum:"+(n1+1));
			//初始化baseList，brandNameSet，goodsCodeSet，topCatSet，subCatSet，thirdCatSet
			if(n0>499){
				errMsg = "单次上传请勿超过500款商品,检测到基础信息sheet页有"+n0+"条记录";
				map.put("errMsg", errMsg);
				return map;
			}
			int rowNum0 = 0;//基础信息行数
			int rowNum1 = 0;//规格库存行数
			for(int i=0;i<=n0;i++){
				Row r = sht0.getRow(i);
				if(i>0){
					Cell c0 = r.getCell(0);//商品名称
					Cell c1 = r.getCell(1);//品牌
					Cell c2 = r.getCell(2);//款号
					Cell c3 = r.getCell(3);//原价
					Cell c4 = r.getCell(4);//最低价
					Cell c5 = r.getCell(5);//一级类目
					Cell c6 = r.getCell(6);//二级类目
					Cell c7 = r.getCell(7);//三级类目
					if((c0==null||Cell.CELL_TYPE_BLANK==c0.getCellType())
							&&(c1==null||Cell.CELL_TYPE_BLANK==c1.getCellType())
							&&(c2==null||Cell.CELL_TYPE_BLANK==c2.getCellType())
							&&(c3==null||Cell.CELL_TYPE_BLANK==c3.getCellType())
							&&(c4==null||Cell.CELL_TYPE_BLANK==c4.getCellType())
							&&(c5==null||Cell.CELL_TYPE_BLANK==c5.getCellType())
							&&(c6==null||Cell.CELL_TYPE_BLANK==c6.getCellType())
							&&(c7==null||Cell.CELL_TYPE_BLANK==c7.getCellType())){
							break;
						}
					rowNum0++;
					TGoodsImportVo baseVo = new TGoodsImportVo();//库存vo
					if(c0!=null){
						c0.setCellType(Cell.CELL_TYPE_STRING);
						baseVo.setGoodsName(c0.getStringCellValue());
					}
					if(c1!=null){
						c1.setCellType(Cell.CELL_TYPE_STRING);
						String v = c1.getStringCellValue();
						baseVo.setBrandName(v);
//						brandNameSet.add(v);
					}else{
						errMsg = "基础信息sheet页第"+(i+1)+"行品牌不能为空";
						map.put("errMsg", errMsg);
						return map;
					}
					
					if(c2!=null){
						c2.setCellType(Cell.CELL_TYPE_STRING);
						String v = c2.getStringCellValue();
						baseVo.setGoodsCode(v);
						if(goodsCodeSet.contains(v)){
							errMsg = "基础信息sheet页第"+(i+1)+"行款号"+v+"重复";
							map.put("errMsg", errMsg);
							return map;
						}else{
							goodsCodeSet.add(v);
						}
					}else{
						errMsg = "基础信息sheet页第"+(i+1)+"行款号不能为空";
						map.put("errMsg", errMsg);
						return map;
					}
					
					if(c3!=null){
						if(Cell.CELL_TYPE_NUMERIC!=c3.getCellType()){
							errMsg = "基础信息sheet页第"+(i+1)+"行原价不是数字";
							map.put("errMsg", errMsg);
							return map;
						}else{
							double s = c3.getNumericCellValue();
							baseVo.setOriginalPrice(new BigDecimal(s));
						}
					}else{
						errMsg = "基础信息sheet页第"+(i+1)+"行原价不能为空";
						map.put("errMsg", errMsg);
						return map;
					}
					
					if(c4!=null){
						if(Cell.CELL_TYPE_BLANK==c4.getCellType()){//空字符串
							baseVo.setLowestPriceDiscount(BigDecimal.ZERO);
							baseVo.setLowestPrice(BigDecimal.ZERO);
						}else{
							if(Cell.CELL_TYPE_NUMERIC!=c4.getCellType()){
								errMsg = "基础信息sheet页第"+(i+1)+"行最低折扣不是数字";
								map.put("errMsg", errMsg);
								return map;
							}else{
								double s = c4.getNumericCellValue();
								if(s>1||s<0.01){
									errMsg = "基础信息sheet页第"+(i+1)+"行最低折扣不是有效数字";
									map.put("errMsg", errMsg);
									return map;
								}else{
									String str = String.valueOf(s);
									int n = str.indexOf(".")+1;
							        int l = str.length();
							        if(l-n>2){
							        	errMsg = "基础信息sheet页第"+(i+1)+"行最低折扣不能超过2位数字";
										map.put("errMsg", errMsg);
										return map;
							        }else{
							        	baseVo.setLowestPriceDiscount(new BigDecimal(s));
							        	baseVo.setLowestPrice(baseVo.getOriginalPrice().multiply(baseVo.getLowestPriceDiscount())
							        			.setScale(2, BigDecimal.ROUND_HALF_UP));
							        }
								}
							}
						}
					}else{
						baseVo.setLowestPriceDiscount(BigDecimal.ZERO);
						baseVo.setLowestPrice(BigDecimal.ZERO);
					}
					
					if(c5!=null){
						c5.setCellType(Cell.CELL_TYPE_STRING);
						String v = c5.getStringCellValue();
						baseVo.setTopCategoryName(v);
//						topCatSet.add(v);
					}else{
						errMsg = "基础信息sheet页第"+(i+1)+"行一级类目不能为空";
						map.put("errMsg", errMsg);
						return map;
					}
					
					if(c6!=null){
						c6.setCellType(Cell.CELL_TYPE_STRING);
						String v = c6.getStringCellValue();
						baseVo.setSubCategoryName(v);
//						subCatSet.add(v);
					}else{
						errMsg = "基础信息sheet页第"+(i+1)+"行二级类目不能为空";
						map.put("errMsg", errMsg);
						return map;
					}
					
					if(c7!=null){
						c7.setCellType(Cell.CELL_TYPE_STRING);
						String v = c7.getStringCellValue();
						baseVo.setThridCategoryName(v);
//						thirdCatSet.add(v);
					}else{
						errMsg = "基础信息sheet页第"+(i+1)+"行三级类目不能为空";
						map.put("errMsg", errMsg);
						return map;
					}
					baseList.add(baseVo);
				}
			}
			
			//初始化storeList和headers
			for(int i=0;i<=n1;i++){
				Row r = sht1.getRow(i);
				if(i==0){
					int colums = r.getLastCellNum();//列数
					if(colums!=5){
						errMsg = "请下载新模板进行导入";
						map.put("errMsg", errMsg);
						return map;
					}
//					System.out.println(r.getCell(0).getStringCellValue());//款号
//					System.out.println(r.getCell(1).getStringCellValue());//颜色
//					System.out.println(r.getCell(2).getStringCellValue());//尺码
//					System.out.println(r.getCell(3).getStringCellValue());//库存
//					System.out.println(r.getCell(4).getStringCellValue());//条码
					headers.setHeaderOne(r.getCell(1).getStringCellValue());//规格1
					headers.setHeaderTwo(r.getCell(2).getStringCellValue());//规格2
				}else{
					Cell c0 = r.getCell(0);//款号
					Cell c1 = r.getCell(1);//颜色
					Cell c2 = r.getCell(2);//尺码
					Cell c3 = r.getCell(3);//库存
					Cell c4 = r.getCell(4);//条码
					if((c0==null||Cell.CELL_TYPE_BLANK==c0.getCellType())
						&&(c1==null||Cell.CELL_TYPE_BLANK==c1.getCellType())
						&&(c2==null||Cell.CELL_TYPE_BLANK==c2.getCellType())
						&&(c3==null||Cell.CELL_TYPE_BLANK==c3.getCellType())
						&&(c4==null||Cell.CELL_TYPE_BLANK==c4.getCellType())){
						break;
					}
					rowNum1++;
					TGoodsStoreVo storeVo = new TGoodsStoreVo();//库存vo
					if(c0!=null){
						c0.setCellType(Cell.CELL_TYPE_STRING);
						storeVo.setGoodsCode(c0.getStringCellValue());
					}
					
					if(c1!=null){
						c1.setCellType(Cell.CELL_TYPE_STRING);
						storeVo.setSpecificationOne(c1.getStringCellValue());
					}else{
						errMsg = "规格库存sheet页第"+(i+1)+"行"+headers.getHeaderOne()+"不能为空";
						map.put("errMsg", errMsg);
						return map;
					}
					
					if(c2!=null){
						c2.setCellType(Cell.CELL_TYPE_STRING);
						storeVo.setSpecificationTwo(c2.getStringCellValue());
					}
					
					if(c3!=null){
						if(Cell.CELL_TYPE_BLANK!=c3.getCellType()){
							if(Cell.CELL_TYPE_NUMERIC!=c3.getCellType()){
								errMsg = "规格库存sheet页第"+(i+1)+"行库存不是数字";
								map.put("errMsg", errMsg);
								return map;
							}else{
								double s = c3.getNumericCellValue();
								storeVo.setStore(new BigDecimal(s));
							}
						}
					}
					if(c4!=null&&Cell.CELL_TYPE_BLANK!=c4.getCellType()){
						c4.setCellType(Cell.CELL_TYPE_STRING);
						String v = c4.getStringCellValue();
						storeVo.setBarCode(v);
						if(barCodeSet.contains(v)){
							errMsg = "规格库存sheet页第"+(i+1)+"行条码"+v+"重复";
							map.put("errMsg", errMsg);
							return map;
						}else{
							barCodeSet.add(v);
						}
					}
					storeList.add(storeVo);
				}
			}
			System.out.println("sht0有效数据总RowNum:"+rowNum0);
			System.out.println("sht1有效数据总RowNum:"+rowNum1);
			map.put("baseList", baseList);
			map.put("storeList", storeList);
			map.put("headers", headers);
//			map.put("brandNameSet", brandNameSet);
			map.put("goodsCodeSet", goodsCodeSet);
			map.put("barCodeSet", barCodeSet);
//			map.put("topCatSet", topCatSet);
//			map.put("subCatSet", subCatSet);
//			map.put("thirdCatSet", thirdCatSet);
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
