package com.buss.news.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.news.entity.TNewsGoodsEntity;
import com.buss.news.service.TNewsGoodsServiceI;



/**   
 * @Title: Controller
 * @Description: 资讯商品
 * @author onlineGenerator
 * @date 2016-11-02 12:17:45
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tNewsGoodsController")
public class TNewsGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TNewsGoodsController.class);

	@Autowired
	private TNewsGoodsServiceI tNewsGoodsService;
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
	 * 资讯商品列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String userType = ResourceUtil.getSessionUserName().getUserType();
		request.setAttribute("news_Id", request.getParameter("news_Id"));
		request.setAttribute("user_Type", userType);
		return new ModelAndView("com/buss/news/tNewsGoodsList");
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
	public void datagrid(TNewsGoodsEntity tNewsGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TActivityGoodsEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tActivityGoods, request.getParameterMap());
		try{
		//自定义追加查询条件
			String newsId = request.getParameter("news_Id");
			String goodsName = request.getParameter("goodsName");
			String goodsCode = request.getParameter("goodsCode");
			String sortName = dataGrid.getSort();
			String sql ="SELECT n.id,g.goods_name as goodsName,g.goods_code as goodsCode,g.small_pic as smallPic,g.original_price as originalPrice, g.current_price as currentPrice,ifnull(g.activity_price,'') as activityPrice "
				+" from t_news_goods n LEFT JOIN t_goods g on n.goods_id = g.id "
				+" where n.status = 'A' and g.status = 'A'  and g.new_goods_type='2' ";
			String countSql = "select count(1) from t_news_goods n LEFT JOIN t_goods g on n.goods_id = g.id  where n.status = 'A' and g.status = 'A'  and g.new_goods_type='2' ";
			if(!StringUtil.isEmpty(newsId)){
				sql +=" and n.news_id = '"+newsId+"'";
				countSql +=" and n.news_id = '"+newsId+"'";
			}
			if(!StringUtil.isEmpty(goodsName)){
				sql +=" and g.goods_name like '%"+goodsName+"%'";
				countSql +=" and g.goods_name like '%"+goodsName+"%'";
			}
			if(!StringUtil.isEmpty(goodsCode)){
				sql +=" and g.goods_code like '%"+goodsCode+"%'";
				countSql +=" and g.goods_code like '%"+goodsCode+"%'";
			}
			if(Utility.isEmpty(sortName)){
				sql +=" ORDER BY g.original_price desc";
			}else{
				sql+= " ORDER BY "+sortName+" "+dataGrid.getOrder();
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
	 * 删除资讯商品
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TNewsGoodsEntity tNewsGoods, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tNewsGoods = systemService.flushEntity(TNewsGoodsEntity.class, tNewsGoods.getId());
		message = "资讯商品删除成功";
		try{
			tNewsGoods.setStatus("I");
			tNewsGoodsService.updateEntitie(tNewsGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "资讯商品删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除资讯商品
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "资讯商品删除成功";
		try{
			for(String id:ids.split(",")){
				TNewsGoodsEntity tNewsGoods = systemService.flushEntity(TNewsGoodsEntity.class, id);
				tNewsGoods.setStatus("I");
				tNewsGoodsService.updateEntitie(tNewsGoods);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "资讯商品删除失败";
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
			message = "资讯商品添加成功";
			try{
				String newsId = request.getParameter("newsId");
				String goodsIds = request.getParameter("goodsIds");
				for(String goodsId:goodsIds.split(",")){
					TNewsGoodsEntity tNewsGoods = new TNewsGoodsEntity();
					tNewsGoods.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					tNewsGoods.setUpdateDate(Utility.getCurrentTimestamp());
					tNewsGoods.setNewsId(newsId);
					tNewsGoods.setGoodsId(goodsId);
					tNewsGoodsService.save(tNewsGoods);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
			}catch(Exception e){
				e.printStackTrace();
				message = "资讯商品添加失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
	
}
