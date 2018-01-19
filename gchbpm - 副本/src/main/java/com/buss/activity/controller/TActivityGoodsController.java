package com.buss.activity.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.activity.entity.TActivityGoodsEntity;
import com.buss.activity.service.TActivityGoodsServiceI;
import com.buss.base.entity.BaseActivityEntity;
import com.buss.cms.service.TContentCustomIndexServiceI;

/**   
 * @Title: Controller
 * @Description: 活动商品
 * @author onlineGenerator
 * @date 2016-08-15 21:15:46
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tActivityGoodsController")
public class TActivityGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TActivityGoodsController.class);

	@Autowired
	private TActivityGoodsServiceI tActivityGoodsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TContentCustomIndexServiceI tContentCustomIndexService;
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
	 * （商品活动，多多益善促销）活动商品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("rId", request.getParameter("rId"));
		request.setAttribute("activity_Id", request.getParameter("activity_Id"));
		request.setAttribute("content_Id", request.getParameter("c_Id"));
		return new ModelAndView("com/buss/activity/tActivityGoodsList");
	}

	/**
	 * （商品活动，多多益善促销）云仓活动商品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "listOfCloud")
	public ModelAndView listOfCloud(HttpServletRequest request) {
		request.setAttribute("rId", request.getParameter("rId"));
		request.setAttribute("activity_Id", request.getParameter("activity_Id"));
		request.setAttribute("content_Id", request.getParameter("c_Id"));
		return new ModelAndView("com/buss/activity/tActivityGoodsListOfCloud");
	}
	
	/**
	 * 资讯分类商品列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "newsTypeGoodslist")
	public ModelAndView newsTypeGoodslist(HttpServletRequest request) {
		request.setAttribute("news_type_Id", request.getParameter("news_type_Id"));
		request.setAttribute("content_Id", request.getParameter("content_Id"));
		request.setAttribute("rId", request.getParameter("rId"));
		return new ModelAndView("com/buss/activity/newsTypeGoodsList");
	}

	
	/**
	 * easyui AJAX请求数据  活动商品列表(真选私惠) (source=1)
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TActivityGoodsEntity tActivityGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TActivityGoodsEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tActivityGoods, request.getParameterMap());
		try{
		//自定义追加查询条件
			//1：零售商，2：云商
			String type = request.getParameter("type");
			String rId = request.getParameter("rId");
			String activityId = request.getParameter("activity_Id");
			String goodsName = request.getParameter("goodsName");
			String goodsCode = request.getParameter("goodsCode");
			
			StringBuffer sql = new StringBuffer("SELECT a.id,a.activity_id as activityId,g.id as goodsId,g.goods_name as goodsName,IFNULL(g.goods_code,'') AS goodsCode,g.current_price as currentPrice,IFNULL(g.activity_price,'') as activityPrice")
				.append(" from t_activity_goods a  LEFT JOIN t_goods g on a.goods_id = g.id  where a.status = 'A' and a.source = '1' and a.activity_id='").append(activityId).append("' ");
			StringBuffer countSql = new StringBuffer("select count(1) from t_activity_goods a LEFT JOIN t_goods g on a.goods_id = g.id  where a.status = 'A' and a.source = '1' and a.activity_id='").append(activityId).append("' ");
			if("1".equals(type)){//零售商
				sql.append(" and a.goods_store_id = '").append(rId).append("'");
				countSql.append(" and a.goods_store_id = '").append(rId).append("'");
			}
//			else if("2".equals(type)){//云商
//				sql.append(" and a.goods_store_id <> '").append(rId).append("'");
//				countSql.append(" and a.goods_store_id <> '").append(rId).append("'");
//			}
			if(!StringUtil.isEmpty(goodsName)){
				sql.append(" and g.goods_name like '%"+goodsName+"%'");
				countSql.append(" and g.goods_name like '%"+goodsName+"%'");
			}
			if(!StringUtil.isEmpty(goodsCode)){
				sql.append(" and g.goods_code like '%"+goodsCode+"%'");
				countSql.append(" and g.goods_code like '%"+goodsCode+"%'");
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
	 * easyui AJAX请求数据  资讯分类商品列表（source=2）
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	
	@RequestMapping(params = "datagrid2")
	public void datagrid2(TActivityGoodsEntity tActivityGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TActivityGoodsEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tActivityGoods, request.getParameterMap());
		try{
			//自定义追加查询条件
			String activityId = request.getParameter("activity_Id");//（base_activity的ID）
			String goodsName = request.getParameter("goodsName");
			String goodsCode = request.getParameter("goodsCode");
			String sql ="SELECT a.id,a.activity_id as activityId,g.goods_name as goodsName,IFNULL(g.goods_code,'') AS goodsCode"
				+" from t_activity_goods a  LEFT JOIN t_goods g on a.goods_id = g.id  "//and g.status = 'A' and g.goods_status = '4' 
				+" where a.status = 'A' and a.source = '2' and  a.activity_id='"+activityId+"'  ";
			String countSql = "select count(1) from t_activity_goods a LEFT JOIN t_goods g on a.goods_id = g.id where a.status = 'A' and a.source = '2'  and a.activity_id='"+activityId+"'  ";
			if(!StringUtil.isEmpty(goodsName)){
				sql +=" and g.goods_name like '%"+goodsName+"%'";
				countSql +=" and g.goods_name like '%"+goodsName+"%'";
			}
			if(!StringUtil.isEmpty(goodsCode)){
				sql +=" and g.goods_code like '%"+goodsCode+"%'";
				countSql +=" and g.goods_code like '%"+goodsCode+"%'";
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql).intValue();
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
	 * easyui AJAX请求数据  当红人气/热销商品,一键齐全,推荐 商品列表（source=3）
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid3")
	public void datagrid3(TActivityGoodsEntity tActivityGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TActivityGoodsEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tActivityGoods, request.getParameterMap());
		try{
			//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			String contentId = request.getParameter("content_Id");
			String goodsName = request.getParameter("goodsName");
			String goodsCode = request.getParameter("goodsCode");
			String sql ="SELECT a.id,a.activity_id as activityId,g.goods_name as goodsName,IFNULL(g.goods_code,'') AS goodsCode,IFNULL(a.order_num,'') AS orderNum"
				+" from t_activity_goods a  LEFT JOIN t_goods g on a.goods_id = g.id " //and g.status = 'A' and g.goods_status = '4'
				+" where a.status = 'A' and a.source = '3' and  a.content_id='"+contentId+"' and a.retailer_id = '"+retailerId+"' ";//OR (g.goods_type = '2' and a.activity_id = '"+activityId+"')
			String countSql = "select count(1) from t_activity_goods a LEFT JOIN t_goods g on a.goods_id = g.id "
				+" where a.status = 'A' and a.source = '3'  and a.content_id='"+contentId+"' and a.retailer_id = '"+retailerId+"'  ";
			if(!StringUtil.isEmpty(goodsName)){
				sql +=" and g.goods_name like '%"+goodsName+"%'";
				countSql +=" and g.goods_name like '%"+goodsName+"%'";
			}
			if(!StringUtil.isEmpty(goodsCode)){
				sql +=" and g.goods_code like '%"+goodsCode+"%'";
				countSql +=" and g.goods_code like '%"+goodsCode+"%'";
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql).intValue();
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
	 * 删除活动商品
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TActivityGoodsEntity tActivityGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动商品删除成功";
		try{
			tActivityGoods = systemService.flushEntity(TActivityGoodsEntity.class, tActivityGoods.getId());
			tActivityGoodsService.doDel(tActivityGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动商品删除失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除活动商品
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "活动商品删除成功";
		try{
			for(String id:ids.split(",")){
				TActivityGoodsEntity tActivityGoods = systemService.flushEntity(TActivityGoodsEntity.class,id);
				tActivityGoodsService.doDel(tActivityGoods);
			}
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动商品删除失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}

		/**
		 * 批量添加活动商品，并且把活动商品存入redis
		 * @return
		 */
		 @RequestMapping(params = "doBatchAdd")
		@ResponseBody
		public AjaxJson doBatchAdd(HttpServletRequest request){
			AjaxJson j = new AjaxJson();
			message = "活动商品添加成功";
			try{
				String contentId = request.getParameter("content_Id");//栏目ID
				String activityId = request.getParameter("activityId");//活动ID
				BaseActivityEntity actEntity = systemService.get(BaseActivityEntity.class, activityId);
				String goodsIds = request.getParameter("goodsIds");//商品IDs
				String retailerId = ResourceUtil.getRetailerId();
				if(Utility.isNotEmpty(goodsIds)&&goodsIds.split(",").length>0){
					for(String goodsId:goodsIds.split(",")){
						//保存活动商品，并且把活动商品存入redis
						tActivityGoodsService.doAdd(goodsId,actEntity,contentId,retailerId);
					}
				}
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
				message = "活动商品添加失败";
				throw new BusinessException(e.getMessage());
			}
			tContentCustomIndexService.clearHomePageRedis();
			j.setMsg(message);
			return j;
		}

		/**
		 * 资讯分类 添加活动商品
		 * @return
		 */
		 @RequestMapping(params = "doBatchAddByNewsType")
		@ResponseBody
		public AjaxJson doBatchAddByNewsType(HttpServletRequest request){
			AjaxJson j = new AjaxJson();
			message = "资讯分类商品添加成功";
			try{
				String contentId = request.getParameter("content_Id");
				String activityId = request.getParameter("activityId");
				String goodsIds = request.getParameter("goodsIds");
				String retailerId = ResourceUtil.getRetailerId();
				for(String goodsId:goodsIds.split(",")){
					TActivityGoodsEntity tActivityGoods = new TActivityGoodsEntity();
					tActivityGoods.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					tActivityGoods.setActivityId(activityId);
					tActivityGoods.setContentId(Long.valueOf(contentId));
					tActivityGoods.setGoodsId(goodsId);
					tActivityGoods.setRetailerId(retailerId);
					tActivityGoods.setSource(TActivityGoodsEntity.SOURCE_2);
					tActivityGoodsService.save(tActivityGoods);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
			}catch(Exception e){
				e.printStackTrace();
				message = "活动商品添加失败";
				throw new BusinessException(e.getMessage());
			}
			tContentCustomIndexService.clearHomePageRedis();
			j.setMsg(message);
			return j;
		}
	 
		 /**
		  * 当红人气/热销商品,一键齐全,推荐  添加商品
		  * @return
		  */
		 @RequestMapping(params = "doBatchAddByHotGoods")
		 @ResponseBody
		 public AjaxJson doBatchAddByHotGoods(HttpServletRequest request){
			 AjaxJson j = new AjaxJson();
			 message = "资讯分类商品添加成功";
			 try{
				 String retailerId = ResourceUtil.getRetailerId();
				 String contentId = request.getParameter("content_Id");
				 String goodsIds = request.getParameter("goodsIds");
				 for(String goodsId:goodsIds.split(",")){
					 TActivityGoodsEntity tActivityGoods = new TActivityGoodsEntity();
					 tActivityGoods.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					 tActivityGoods.setContentId(Long.valueOf(contentId));
					 tActivityGoods.setGoodsId(goodsId);
					 tActivityGoods.setSource(TActivityGoodsEntity.SOURCE_3);
					 tActivityGoods.setRetailerId(retailerId);
					 tActivityGoodsService.save(tActivityGoods);
					 systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				 }
			 }catch(Exception e){
				 e.printStackTrace();
				 message = "活动商品添加失败";
				 throw new BusinessException(e.getMessage());
			 }
			 tContentCustomIndexService.clearHomePageRedis();
			 j.setMsg(message);
			 return j;
		 }
		 
	/**
	 * 活动商品编辑排序
	 * @return
	 */
	@RequestMapping(params = "goUpdateOrderNum")
	public ModelAndView goUpdateOrderNum(TActivityGoodsEntity tActivityGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tActivityGoods.getId())) {
			tActivityGoods = tActivityGoodsService.flushEntity(TActivityGoodsEntity.class, tActivityGoods.getId());
			req.setAttribute("tActivityGoods", tActivityGoods);
		}
		return new ModelAndView("com/buss/base/tActivityGoods-update");
	}
	
	/**
	 * 活动商品排序
	 * @return
	 */
	@RequestMapping(params = "doUpdateOrderNum")
	@ResponseBody
	public AjaxJson doUpdateOrderNum(TActivityGoodsEntity tActivityGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "排序修改成功";
		try{
			if(StringUtil.isNotEmpty(tActivityGoods.getOrderNum())){
				tActivityGoodsService.executeSql("update t_activity_goods set order_num = ? where id = ?", tActivityGoods.getOrderNum(),tActivityGoods.getId());
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "排序修改失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 刷新reids所有的活动商品
	 * @return
	 */
	 @RequestMapping(params = "flushRedisAllActivityGoods")
	@ResponseBody
	public AjaxJson flushRedisAllActivityGoods(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "刷新所有活动商品缓存成功";
		try{
			tActivityGoodsService.flushRedisAllActivityGoods();
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "刷新所有活动商品缓存失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
