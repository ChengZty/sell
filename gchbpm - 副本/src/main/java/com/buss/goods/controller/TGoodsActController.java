package com.buss.goods.controller;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.activity.entity.TGoodsActWordsEntity;
import com.buss.bill.entity.TFinActivityEntity;
import com.buss.goods.entity.TGoodsActDetailEntity;
import com.buss.goods.entity.TGoodsActEntity;
import com.buss.goods.entity.TGoodsActNewsEntity;
import com.buss.goods.entity.TGoodsActStoreEntity;
import com.buss.goods.entity.TGoodsActTemplateEntity;
import com.buss.goods.service.TGoodsActDetailServiceI;
import com.buss.goods.service.TGoodsActServiceI;
import com.buss.goods.service.TGoodsActStoreServiceI;
import com.buss.goods.service.TGoodsServiceI;
import com.buss.goods.vo.ActPriceImportVo;
import com.buss.goods.vo.AllActGoodsVo;


/**   
 * @Title: Controller
 * @Description: 商品活动
 * @author onlineGenerator
 * @date 2017-09-13 15:20:19
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGoodsActController")
public class TGoodsActController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGoodsActController.class);

	@Autowired
	private TGoodsActServiceI tGoodsActService;
	@Autowired
	private TGoodsActDetailServiceI tGoodsActDetailService;
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
	 * 商品活动列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("downloadKey", request.getParameter("downloadKey"));
		request.setAttribute("msg", request.getParameter("msg"));
		return new ModelAndView("com/buss/goods/tGoodsActList");
	}

	/**
	 * 关联话题  页面跳转
	 */
	@RequestMapping(params = "relateTopic")
	public  ModelAndView relateTopic(HttpServletRequest request){
		request.setAttribute("act_id", request.getParameter("act_id"));
		return new ModelAndView("com/buss/goods/tGoodsRelateTopic");
	}
	
	/**
	 * 活动话题列表
	 * SELECT  T
	 */
	@RequestMapping(params = "newsDatagrid")
	public void newsDatagrid(HttpServletRequest request , HttpServletResponse response, DataGrid dataGrid){
		
		String actId = request.getParameter("act_id");
		String title = request.getParameter("title");
		String sortName = dataGrid.getSort();
		this.tGoodsActService.newsDatagrid(actId, title,sortName, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 活动话题列表跳转
	 * actNewsList
	 */
	@RequestMapping(params = "actNewsList")
	public ModelAndView actNewsList(HttpServletRequest request){
		request.setAttribute("actId", request.getParameter("actId"));
		return new ModelAndView("com/buss/goods/tGoodsActNewsList");
	}
	
	/**
	 * 添加活动话题列表
	 * actNewsDatagrid
	 */
	@RequestMapping(params = "actNewsDatagrid")
	public void actNewsDatagrid(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {

		String sortName = dataGrid.getSort();
		String actId = request.getParameter("actId");
		String title = request.getParameter("title");
		this.tGoodsActService.actNewsDatagrid(actId,title, sortName, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 删除话题
	 * deletenews
	 */
	@RequestMapping(params = "deleteNews")
	@ResponseBody
	public AjaxJson deleteNews(TGoodsActNewsEntity tGoodsActNews ,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String Id = tGoodsActNews.getId();
		String actId = request.getParameter("actId");
		String message = tGoodsActService.deleteNews(Id, actId);
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 添加关联话题 doBatchAdd
	 */
	@RequestMapping(params = "doBatchAdd")
	@ResponseBody
	public AjaxJson doBatchAdd(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String actId = request.getParameter("actId");
		String newsIds = request.getParameter("newsIds");
		message = tGoodsActService.doBatchAdd(actId, newsIds);
		j.setMsg(message);
		return j;
	}

	/**
	 * 商品活动审核列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "auditList")
	public ModelAndView auditList(HttpServletRequest request) {
		request.setAttribute("auditStatus", "");
		return new ModelAndView("com/buss/goods/tGoodsActList-audit");
	}
	
	/**
	 * 导购活动审核列表Tab 页面跳转
	 * @return
	 */
	@RequestMapping(params = "auditListTab")
	public ModelAndView auditListTab(HttpServletRequest request) {
		return new ModelAndView("com/buss/goods/tGoodsActAuditListTab");
	}

	/**
	 * 所有活动商品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "allActGoodsList")
	public ModelAndView allActGoodsList(HttpServletRequest request) {
		return new ModelAndView("com/buss/goods/allActGoodsList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TGoodsActEntity tGoodsAct,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try {
			Map<String, String> sqlMap = this.getSqlMap(request, dataGrid);
			String sql = sqlMap.get("sql");
			String sqlCount = sqlMap.get("sqlCount");
			List<TGoodsActEntity> resultList = new ArrayList<TGoodsActEntity>();
			int total = this.systemService.getCountForJdbc(sqlCount.toString()).intValue();
			if (total > 0) {
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TGoodsActEntity.class);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagridOfAllActGoods")
	public void datagridOfAllActGoods(TGoodsActEntity tGoodsAct,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			Map<String,String> sqlMap = this.getAllActGoodsSqlMap(request,dataGrid);
			String sql = sqlMap.get("sql");
			String sqlCount = sqlMap.get("sqlCount");
			List<AllActGoodsVo> resultList = new ArrayList<AllActGoodsVo>();
			int total = this.systemService.getCountForJdbc(sqlCount.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),AllActGoodsVo.class);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}


	private Map<String, String> getSqlMap(HttpServletRequest request, DataGrid dataGrid) {
		Map<String, String> map = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String title = request.getParameter("title");// 活动名称
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String actStatus = request.getParameter("actStatus");// 活动状态
		String actType = request.getParameter("actType");//作废活动
		StringBuffer sql = new StringBuffer("SELECT a.id,a.title,a.begin_time beginTime,a.end_time endTime,a.valid,a.auditor,a.audit_time auditTime,a.audit_status auditStatus,")
		.append("CASE WHEN a.audit_status ='").append(TGoodsActEntity.AUDIT_STATUS_1).append("' THEN '").append(TGoodsActEntity.ACT_STATUS_1).append("' ")//待审核
		.append("WHEN (a.valid = 'N' OR (NOW() > a.end_time AND a.audit_status = '").append(TGoodsActEntity.AUDIT_STATUS_2).append("')) THEN '").append(TGoodsActEntity.ACT_STATUS_4).append("' ")//已结束(包括下架)
		.append("WHEN NOW() < a.begin_time  AND a.audit_status = '").append(TGoodsActEntity.AUDIT_STATUS_2).append("' THEN '").append(TGoodsActEntity.ACT_STATUS_2).append("' ")//待开始
		.append("WHEN NOW() BETWEEN a.begin_time AND a.end_time AND a.audit_status = '").append(TGoodsActEntity.AUDIT_STATUS_2).append("' THEN '").append(TGoodsActEntity.ACT_STATUS_3).append("' END actStatus ")//进行中
				.append("FROM t_goods_act a WHERE a.`status` = 'A' ")
		.append("AND a.retailer_id = '").append(retailerId).append("'")
		;
		StringBuffer sqlCount = new StringBuffer("select count(1) FROM t_goods_act a WHERE a.`status` = 'A' AND a.retailer_id = '").append(retailerId).append("'");
		if((TGoodsActEntity.AUDIT_STATUS_3+"").equals(actType) ){  //查询已作废导购活动
			sql.append("AND a.audit_status = '3' ");
			sqlCount.append("AND a.audit_status = '3' ");
		}else{//查询非已作废导购活动
			sql.append("AND a.audit_status <> '3' ");
			sqlCount.append("AND a.audit_status <> '3' ");
		}
		if (Utility.isNotEmpty(title)) {
			sql.append(" and a.title like '%").append(title).append("%'");
			sqlCount.append(" and a.title like '%").append(title).append("%'");
		}
		if (Utility.isNotEmpty(beginTime)) {
			sql.append(" and a.begin_time >= '").append(beginTime).append(" 00:00:00'");
			sqlCount.append(" and a.begin_time >= '").append(beginTime).append(" 00:00:00'");
		}
		if (Utility.isNotEmpty(endTime)) {
			sql.append(" and a.end_time <= '").append(endTime).append(" 23:59:59'");
			sqlCount.append(" and a.end_time <= '").append(endTime).append(" 23:59:59'");
		}
		if (Utility.isNotEmpty(actStatus)) {
			sql.append(getActStatusCondition(actStatus));
			sqlCount.append(getActStatusCondition(actStatus));
		}
		String sortName = dataGrid.getSort();
		if (Utility.isEmpty(sortName)) {
			sql.append(" ORDER BY a.update_date desc");
		} else {
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		map.put("sql", sql.toString());
		map.put("sqlCount", sqlCount.toString());
		return map;
	}

	private Map<String, String> getAllActGoodsSqlMap(HttpServletRequest request, DataGrid dataGrid) {
		Map<String, String> map = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String title = request.getParameter("title");// 活动名称
		String goodsName = request.getParameter("goodsName");// 商品名称
		String goodsCode = request.getParameter("goodsCode");// 商品款号
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String actStatus = request.getParameter("actStatus");// 活动状态
		StringBuffer sql = new StringBuffer("SELECT d.id,a.title,a.begin_time beginTime,a.end_time endTime,a.valid,a.auditor,a.audit_time auditTime,a.audit_status auditStatus,")
		.append("CASE WHEN a.audit_status ='").append(TGoodsActEntity.AUDIT_STATUS_1).append("' THEN '").append(TGoodsActEntity.ACT_STATUS_1).append("' ")//待审核
		.append("WHEN (a.valid = 'N' OR (NOW() > a.begin_time AND NOW() > a.end_time AND a.audit_status = '").append(TGoodsActEntity.AUDIT_STATUS_2).append("')) THEN '").append(TGoodsActEntity.ACT_STATUS_4).append("' ")//已结束(包括下架)
		.append("WHEN NOW() < a.begin_time AND NOW() < a.end_time AND a.audit_status = '").append(TGoodsActEntity.AUDIT_STATUS_2).append("' THEN '").append(TGoodsActEntity.ACT_STATUS_2).append("' ")//待开始
		.append("WHEN NOW() BETWEEN a.begin_time AND a.end_time AND a.audit_status = '").append(TGoodsActEntity.AUDIT_STATUS_2).append("' THEN '").append(TGoodsActEntity.ACT_STATUS_3).append("' ELSE '").append(TGoodsActEntity.ACT_STATUS_4).append("' END actStatus, ")//进行中
				.append("d.goods_act_id goodsActId,d.goods_id goodsId,d.goods_code goodsCode,d.goods_name goodsName,d.small_pic smallPic,")
				.append("d.original_price originalPrice,d.lowest_price lowestPrice,d.act_price actPrice ")
				.append("FROM t_goods_act a LEFT JOIN t_goods_act_detail d on a.id = d.goods_act_id and d.`status` = 'A' WHERE a.`status` = 'A' ")
		.append("AND a.retailer_id = '").append(retailerId).append("'")
		;
		StringBuffer sqlCount = new StringBuffer("select count(1) FROM t_goods_act a LEFT JOIN t_goods_act_detail d on a.id = d.goods_act_id and d.`status` = 'A' WHERE a.`status` = 'A' AND a.retailer_id = '").append(retailerId).append("'");
		if (Utility.isNotEmpty(title)) {
			sql.append(" and a.title like '%").append(title).append("%'");
			sqlCount.append(" and a.title like '%").append(title).append("%'");
		}
		if (Utility.isNotEmpty(beginTime)) {
			sql.append(" and a.begin_time >= '").append(beginTime).append(" 00:00:00'");
			sqlCount.append(" and a.begin_time >= '").append(beginTime).append(" 00:00:00'");
		}
		if (Utility.isNotEmpty(endTime)) {
			sql.append(" and a.end_time <= '").append(endTime).append(" 23:59:59'");
			sqlCount.append(" and a.end_time <= '").append(endTime).append(" 23:59:59'");
		}
		if (Utility.isNotEmpty(actStatus)) {
			sql.append(getActStatusCondition(actStatus));
			sqlCount.append(getActStatusCondition(actStatus));
		}
		if (Utility.isNotEmpty(goodsName)) {
			sql.append(" and d.goods_name like '%").append(goodsName).append("%'");
			sqlCount.append(" and d.goods_name like '%").append(goodsName).append("%'");
		}
		if (Utility.isNotEmpty(goodsCode)) {
			sql.append(" and d.goods_code like '%").append(goodsCode).append("%'");
			sqlCount.append(" and d.goods_code like '%").append(goodsCode).append("%'");
		}
		String sortName = dataGrid.getSort();
		if (Utility.isEmpty(sortName)) {
			sql.append(" ORDER BY a.update_date desc");
		} else {
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		map.put("sql", sql.toString());
		map.put("sqlCount", sqlCount.toString());
		return map;
	}

	/** 获取活动状态查询对应的条件 */
	private String getActStatusCondition(String actStatus) {
		int sts = Integer.valueOf(actStatus);
		String condition = null;
		switch (sts) {
		case TGoodsActEntity.ACT_STATUS_1:
				condition = " AND a.audit_status ='"+TGoodsActEntity.AUDIT_STATUS_1+"'";
			break;
		case TGoodsActEntity.ACT_STATUS_2:
				condition = " AND NOW() < a.begin_time AND NOW() < a.end_time AND a.audit_status = '"+TGoodsActEntity.AUDIT_STATUS_2+"'";
			break;
		case TGoodsActEntity.ACT_STATUS_3:
				condition = " AND NOW() BETWEEN a.begin_time AND a.end_time AND a.audit_status = '"+TGoodsActEntity.AUDIT_STATUS_2
							+"' AND a.valid = '"+TGoodsActEntity.VALID_Y+"'";
			break;
		case TGoodsActEntity.ACT_STATUS_4:
				condition = " AND (a.valid = '"+TGoodsActEntity.VALID_N+"' OR (NOW() > a.begin_time AND NOW() > a.end_time AND a.audit_status = '"+TGoodsActEntity.AUDIT_STATUS_2+"'))";
			break;
		default:
			break;
		}
		return condition;
	}

	/**
	 * 删除商品活动和商品明细
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TGoodsActEntity tGoodsAct, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品活动删除成功";
		try {
			tGoodsActService.doDelAct(tGoodsAct);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品活动删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 商品话术列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goodsConWordsList")
	public ModelAndView goodsConWordsList(HttpServletRequest request,String actId) {
		request.setAttribute("actId", actId);
		return new ModelAndView("com/buss/template/templateGoodsConWordsList");
	}
	
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */

	@RequestMapping(params = "actWordsGrid")
	public void actWordsGrid(TGoodsActEntity tGoodsAct,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
			//查询活动话术列表
			String actId = request.getParameter("actId");
			String content = request.getParameter("content");
			String sql ="SELECT t_goods_act_words.id, t_cust_words.content FROM t_goods_act_words, t_cust_words WHERE t_goods_act_words.words_id = t_cust_words.id AND  t_goods_act_words.goods_act_id = '"+actId+"' AND t_goods_act_words.status = 'A' ";
			String countSql = "SELECT count(1) FROM t_goods_act_words, t_cust_words WHERE t_goods_act_words.words_id = t_cust_words.id AND  t_goods_act_words.goods_act_id = '"+actId+"' AND t_goods_act_words.status = 'A' ";
			if(Utility.isNotEmpty(content)){
				sql += "AND t_cust_words.content LIKE '%"+content+"%'";
				countSql += "AND t_cust_words.content LIKE '%"+content+"%'";
			}
			String sortName = dataGrid.getSort();
			if (Utility.isEmpty(sortName)) {
				sql += " ORDER BY t_goods_act_words.update_date desc";
			}else {
				sql += " ORDER BY " + sortName + " " + dataGrid.getOrder();
			}
			List<Map<String,Object>> resultList = systemService.findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
			int total = 0; 
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid); 
	}
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */

	@RequestMapping(params = "actWordsOfGrid")
	public void actWordsOfGrid(TGoodsActEntity tGoodsAct,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			String retailerId = ResourceUtil.getRetailerId();
			String content = request.getParameter("content");
			String actId = request.getParameter("actId");
			//查询活动话术列表
			String sql ="SELECT id,content FROM t_cust_words where `status` ='A' and sub_type_id = '-7' and retailer_id = '"+retailerId+"' and id not in ( SELECT w.words_id from t_goods_act_words w WHERE w.`status` ='A' and goods_act_id = '"+actId+"') ";
			String countSql = "SELECT count(1) FROM t_cust_words where `status` ='A' and sub_type_id = '-7' and retailer_id = '"+retailerId+"' and id not in ( SELECT w.words_id from t_goods_act_words w WHERE w.`status` ='A' and goods_act_id = '"+actId+"') ";
			if(Utility.isNotEmpty(content)){
				sql += "AND t_cust_words.content LIKE '%"+content+"%'";
				countSql += "AND t_cust_words.content LIKE '%"+content+"%'";
			}
			List<Map<String,Object>> resultList = systemService.findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
			int total = 0; 
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid); 
	}
	/**
	 * 批量添加活动商品关联话术
	 * @return
	 */
	 @RequestMapping(params = "doBatchActAdd")
	@ResponseBody
	public AjaxJson doBatchActAdd(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "活动话术添加成功";
		try{
			String actId = request.getParameter("actId");
			String goodsIds = request.getParameter("goodsIds");//
			for(String goodsId:goodsIds.split(",")){
				TGoodsActWordsEntity tActWords = new TGoodsActWordsEntity();
				tActWords.setStatus("A");
				tActWords.setGoodsActid(actId);
				tActWords.setWordsId(goodsId);
				tGoodsActService.save(tActWords);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "活动话术添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
		/**
		 * 删除商品活动关联话术
		* @return
		 */
		@RequestMapping(params = "doBatchActWordsDel")
		@ResponseBody
		public AjaxJson doBatchActWordsDel(TGoodsActWordsEntity tActWords, HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			message = "商品活动话术删除成功";
			try {
				tGoodsActService.doDelActWords(tActWords);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "商品活动话术删除失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
		/**
		 * 资讯查询单品列表 页面跳转
		 * @return
		 */
		@RequestMapping(params = "tSingleNewGoodsListOfWords")
		public ModelAndView tSingleNewGoodsListOfWords(HttpServletRequest req) {
			req.setAttribute("actId", req.getParameter("actId"));
			return new ModelAndView("com/buss/newGoods/tSingleNewGoodsListOfWords");
		}

	/**
	 * 添加促销活动
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TGoodsActEntity tGoodsAct, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		//判断直播房间号，去掉前后空格
		if(Utility.isNotEmpty(tGoodsAct.getLiveUrl())){
			tGoodsAct.setLiveUrl(tGoodsAct.getLiveUrl().trim());
		}
		
		try {
			List<ActPriceImportVo> failList =  this.tGoodsActService.doAdd(tGoodsAct, request) ;
			
			message = "商品活动添加成功";
			if (failList.size() > 0) {// 导出失败的数据
				if (message == null) {
					message = failList.size() + "条有问题的记录导出中...";
				} else {
					message += "," + failList.size() + "条有问题的记录导出中...";
				}
//				String key = "d-act-price-"+ResourceUtil.getRetailerId()+System.currentTimeMillis();
				// System.out.println("*********************"+key);
				String title = "商品活动价导入错误提示";
				String key = this.tGoodsService.uploadExcelFileToQN(ActPriceImportVo.class, failList, null, "uploadGoodsActPrice",title);
				j.setObj(key);
				// exportXlsxByFailList(request, response, key);
//				this.redisService.set(key, MAPPER.writeValueAsString(failList),3600*24);
//				failList.clear();
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品活动添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新商品活动
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TGoodsActEntity tGoodsAct, HttpServletRequest request) {
		//判断直播房间号，去掉前后空格
		if(Utility.isNotEmpty(tGoodsAct.getLiveUrl())){
			tGoodsAct.setLiveUrl(tGoodsAct.getLiveUrl().trim());
		}
		AjaxJson j = new AjaxJson();
		message = "商品活动更新成功";
		try {
			this.tGoodsActService.doUpdate(tGoodsAct, request);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品活动更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 商品活动新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TGoodsActEntity tGoodsAct, HttpServletRequest req) {
		req.setAttribute("id", Utility.getUUID());
		return new ModelAndView("com/buss/goods/tGoodsAct-add");
	}

	/**
	 * 商品活动编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TGoodsActEntity tGoodsAct, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGoodsAct.getId())) {
			tGoodsAct = tGoodsActService.flushEntity(TGoodsActEntity.class, tGoodsAct.getId());
			if (Utility.isNotEmpty(tGoodsAct.getTemplateId())) {//模版
				TGoodsActTemplateEntity template = this.systemService.get(TGoodsActTemplateEntity.class, tGoodsAct.getTemplateId());
				if(Utility.isNotEmpty(template)){
					tGoodsAct.setTemplateName(template.getTemplateName());
				}
			}
			req.setAttribute("tGoodsActPage", tGoodsAct);
			List<TGoodsActStoreEntity> tGoodsActStoreList = systemService.findByProperty(TGoodsActStoreEntity.class, "goodsActId", tGoodsAct.getId());
			req.setAttribute("tGoodsActStoreIds", getGoodsActStoreIds(tGoodsActStoreList)) ;
		}
		req.setAttribute("view", req.getParameter("view"));
		req.setAttribute("page", req.getParameter("page"));
		return new ModelAndView("com/buss/goods/tGoodsAct-update");
	}

	private String getGoodsActStoreIds(List<TGoodsActStoreEntity> tGoodsActStoreList) {
		StringBuilder goodsActStoreIds = new StringBuilder("") ;
		if(tGoodsActStoreList == null || tGoodsActStoreList.size()==0 ){
			return goodsActStoreIds.toString() ;
		}
		for(TGoodsActStoreEntity tGoodsActStore : tGoodsActStoreList){
			goodsActStoreIds.append(tGoodsActStore.getStoreId()).append(",") ;
		}
		return goodsActStoreIds.delete(goodsActStoreIds.length()-1, goodsActStoreIds.length()).toString() ;
	}

	/**
	 * 审核商品活动
	 * @return
	 */
	@RequestMapping(params = "doAudit")
	@ResponseBody
	public AjaxJson doAudit(TGoodsActEntity tGoodsAct, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品活动审核成功";
		try {
			// 审核并把活动商品信息放入redis
			String msg = this.tGoodsActService.doAudit(tGoodsAct);
			if (msg != null) {
				message = msg;
			} else {
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品活动审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 下架商品活动
	 * @return
	 */
	@RequestMapping(params = "doDown")
	@ResponseBody
	public AjaxJson doDown(TGoodsActEntity tGoodsAct, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tGoodsAct = systemService.flushEntity(TGoodsActEntity.class, tGoodsAct.getId());
		message = "商品活动下架成功";
		try {
			// 下架并把活动商品信息从redis删除
			String msg = this.tGoodsActService.doDown(tGoodsAct);
			if (msg != null) {
				message = msg;
			} else {
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品活动下架失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 导入功能跳转
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tGoodsActController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TGoodsActEntity tGoodsAct,HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid,ModelMap modelMap) {
		Map<String,String> sqlMap = this.getAllActGoodsSqlMap(request,dataGrid);
		String sql = sqlMap.get("sql");
		String sqlCount = sqlMap.get("sqlCount");
		List<AllActGoodsVo> resultList = new ArrayList<AllActGoodsVo>();
		int total = this.systemService.getCountForJdbc(sqlCount.toString()).intValue();
		if (total > 0) {
			resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),AllActGoodsVo.class);
		}
		modelMap.put(NormalExcelConstants.FILE_NAME, "活动商品明细");
		modelMap.put(NormalExcelConstants.CLASS, AllActGoodsVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("活动商品明细列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, resultList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	  * 促销活动作废
	  *（ 修改audit_status状态为3 ）20180105
	  * @return
	  */
	 @RequestMapping(params = "doInvalid")
	 @ResponseBody
	 public AjaxJson doInvalid(String id){
		 AjaxJson j = new AjaxJson();
		 message = "促销活动作废成功";
		 try{
			 this.tGoodsActService.doInvalid(id);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "促销活动作废失败";
			 throw new BusinessException(e.getMessage());
		 }
		 j.setMsg(message);
		 return j;
	 }
	 
	 /**
		 * 激励活动审核列表 页面跳转
		 * （ 查询audit_status状态为3 ）20180105
		 * @return
		 */
		@RequestMapping(params = "invalidList")
		public ModelAndView invalidList(HttpServletRequest request) {
			request.setAttribute("actType", "3");
			return new ModelAndView("com/buss/goods/tGoodsActList-audit");
		}

		
		/**
		  * 获取活动模版 20180111
		  * @return
		  */
		 @RequestMapping(params = "getGoodsActTemplate")
		 @ResponseBody
		 public AjaxJson getGoodsActTemplate(String actType){
			 AjaxJson j = new AjaxJson();
			 message = "获取活动模版成功";
			 try{
				 List<TGoodsActTemplateEntity> list = this.systemService.findHql("from TGoodsActTemplateEntity where actType = ?", actType);
				 j.setObj(list);
			 }catch(Exception e){
				 e.printStackTrace();
				 message = "获取活动模版失败";
				 throw new BusinessException(e.getMessage());
			 }
			 j.setMsg(message);
			 return j;
		 }
}
