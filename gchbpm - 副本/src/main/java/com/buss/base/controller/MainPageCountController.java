package com.buss.base.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.base.entity.BaseActivityEntity;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.user.entity.TFocusCustomerEntity;


/**   
 * @Title: Controller
 * @Description: 主页统计改版
 * @author onlineGenerator
 * @date 2017-11-06 15:29:52
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/mainPageCountController")
public class MainPageCountController extends BaseController {
	private static final Logger logger = Logger.getLogger(MainPageCountController.class);

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
	 * 页面跳转 模板
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/base/baseActivityList");
	}
	
	/**
	 * 获取待发展顾客，交易顾客 资料数
	 * @return
	 */
	@RequestMapping(params = "getCustInfoNum")
	@ResponseBody
	public AjaxJson getCustInfoNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "获取顾客资料数成功";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int tradeNum = 0;//交易顾客数量
			int otherNum = 0;//非交易顾客数量
			int allNum = 0;//非交易顾客数量
			StringBuffer sql = new StringBuffer("SELECT SUM(IF(type='").append(TFocusCustomerEntity.TYPE_3).append("',1,0)) tradeNum,SUM(IF(type<>'")
					.append(TFocusCustomerEntity.TYPE_3).append("' or type is null,1,0)) otherNum FROM t_focus_customer WHERE `status` = 'A' ");
			String retalerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retalerId)){
				sql.append("and to_retailer_id = '").append(retalerId).append("' ");
			}
			map = this.systemService.findOneForJdbc(sql.toString(), null);
			if(map.size()>0){
				String tradeNumStr= map.get("tradeNum")+"";
				if(Utility.isNotEmpty(tradeNumStr)){
					tradeNum = Integer.valueOf(tradeNumStr);
				}
				String otherNumStr= map.get("otherNum")+"";
				if(Utility.isNotEmpty(otherNumStr)){
					otherNum = Integer.valueOf(otherNumStr);
				}
			}
			map.put("tradeNum", tradeNum);
			map.put("otherNum", otherNum);
			allNum = tradeNum+otherNum;
			map.put("allNum",allNum);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "获取顾客资料数失败";
		}
		j.setObj(map);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 获取商品各状态数量
	 * @return
	 */
	@RequestMapping(params = "getGoodsStatusNum")
	@ResponseBody
	public AjaxJson getGoodsStatusNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "获取商品各状态数量成功";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			StringBuffer sql = new StringBuffer("SELECT goods_status,COUNT(1) ct from t_goods where `status` = 'A' and goods_type='1' and new_goods_type = ? ");
			String retalerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retalerId)){
				sql.append("and retailer_id = '").append(retalerId).append("' ");
			}
			sql.append("GROUP BY goods_status ");
			List<Map<String, Object>> mapList = this.systemService.findForJdbc(sql.toString(), TGoodsEntity.NEW_GOODS_TYPE_2);
			int goods0Num = 0;//草稿箱商品数量
			int goods3Num = 0;//待上架商品数量
			int goods4Num = 0;//销售中商品数量
			int goods5Num = 0;//已下架商品数量
			int allGoodsNum = 0;//总商品数量
			if(Utility.isNotEmpty(mapList)){
				for(Map<String, Object> goodsMap : mapList){
					String goodsStatus = goodsMap.get("goods_status")+"";
					String num = goodsMap.get("ct")+"";
					if(TGoodsEntity.GOODS_STATUS_0.equals(goodsStatus)){
						goods0Num = Integer.valueOf(num);
					}else if(TGoodsEntity.GOODS_STATUS_3.equals(goodsStatus)){
						goods3Num = Integer.valueOf(num);
					}else if(TGoodsEntity.GOODS_STATUS_4.equals(goodsStatus)){
						goods4Num = Integer.valueOf(num);
					}else if(TGoodsEntity.GOODS_STATUS_5.equals(goodsStatus)){
						goods5Num = Integer.valueOf(num);
					}
				}
			}
			allGoodsNum = goods0Num+goods3Num+goods4Num+goods5Num;
			map.put("goods0Num", goods0Num);
			map.put("goods3Num", goods3Num);
			map.put("goods4Num", goods4Num);
			map.put("goods5Num", goods5Num);
			map.put("allGoodsNum", allGoodsNum);
			
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "获取商品各状态数量失败";
		}
		j.setObj(map);
		j.setMsg(message);
		return j;
	}


	/**
	 * 获取今日成交导购数 
	 * totalGuaid：成交导购总数
	 */
	@RequestMapping(params = "countGuaidSaled")
	@ResponseBody
	public AjaxJson countGuaidSaled(HttpServletRequest request) {

		message = "获取导购总数成功";
		Integer totalGuaid = 0;
		AjaxJson j = new AjaxJson();
		String today = this.getToday();
		try {
			String retalerId = ResourceUtil.getRetailerId();
			String sql = "SELECT count(DISTINCT guide_id) FROM t_salenum WHERE  date BETWEEN '"
					+ today + " 00:00:00' AND NOW()";
			if (Utility.isNotEmpty(retalerId)) {
				sql += " AND retailer_id = '" + retalerId + "'";
			}
			totalGuaid = this.systemService.getCountForJdbc(sql).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			message = "获取导购总数失败";
			j.setSuccess(false);
		}

		j.setObj(totalGuaid);
		j.setMsg(message);
		return j;
	}

	/**
	 * 今日线上单数 totalOrder :订单总数
	 */
	@RequestMapping(params = "countOrderOnline")
	@ResponseBody
	public AjaxJson countOrderOnline(HttpServletRequest request) {

		message = "获取线上订单数成功";
		Integer totalOrder = 0;
		AjaxJson j = new AjaxJson();
		String today = this.getToday();

		try {
			String retalerId = ResourceUtil.getRetailerId();
			String sql = "SELECT count(id) FROM t_order_info WHERE STATUS='A' and order_status NOT IN (1,9) ";
			if (Utility.isNotEmpty(retalerId)) {
				sql += " AND to_retailer_id = '" + retalerId + "'";
			}
			sql += " AND create_date BETWEEN '" + today
					+ " 00:00:00' AND NOW()";
			totalOrder = this.systemService.getCountForJdbc(sql).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			message = "获取线上订单数失败";
		}
		j.setObj(totalOrder);
		j.setMsg(message);
		return j;
	}

	/**
	 * 今日线下单数
	 * 
	 */
	@RequestMapping(params = "countOrderUnderLine")
	@ResponseBody
	public AjaxJson countOrderUnderLine(HttpServletRequest request) {
		message = "获取线下订单数成功";
		Integer totalOrder = 0;
		AjaxJson j = new AjaxJson();
		String today = this.getToday();

		try {
			String retalerId = ResourceUtil.getRetailerId();
			String sql = "SELECT count(id) FROM  t_store_order_info WHERE STATUS='A'  AND "
					+ " create_date BETWEEN '" + today + " 00:00:00' AND NOW()";

			if (Utility.isNotEmpty(retalerId)) {
				sql += " AND to_retailer_id = '" + retalerId + "'";
			}
			totalOrder = this.systemService.getCountForJdbc(sql).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			message = "获取线下订单数失败";
		}
		j.setObj(totalOrder);
		j.setMsg(message);
		return j;
	}
    //获取今天的日期天数  格式为 2017-01-01
	private String getToday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/**
	 * 查询线上话题数
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "onLineNewsNum")
	@ResponseBody
	public AjaxJson onLineNewsNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String retailerId = ResourceUtil.getRetailerId();
		message = "查询线上话题数成功";
		try {
			String sql = "select count(1) from t_news  where status='A' and news_type not in ('6001','6002') ";
			if(Utility.isNotEmpty(retailerId)){
				sql += "and shopkeeper='"+retailerId+"'";
			}else {
				sql += "and shopkeeper='admin'";
			}


			int total = this.systemService.getCountForJdbc(sql).intValue();
			j.setObj(total);
		} catch (Exception e) {
			e.printStackTrace();
			message = "查询线上话题数失败";
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 查询有点击话题数
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "clickNewsNum")
	@ResponseBody
	public AjaxJson clickNewsNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String retailerId = ResourceUtil.getRetailerId();
		message = "查询有点击话题数成功";
		try {
			String sql = "select count(DISTINCT id) count from t_news where status='A' and news_type not in ('6001','6002') ";
			if(Utility.isNotEmpty(retailerId)){
				sql += "and shopkeeper='"+retailerId+"' "+
					"and id in (select DISTINCT news_id  from t_news_count where retailer_id='"+retailerId+"')";
				
			}else {
				sql += "and shopkeeper='admin' "+
				"and id in (select DISTINCT news_id  from t_news_count )";
			}

			int total = this.systemService.getCountForJdbc(sql).intValue();
			j.setObj(total);
		} catch (Exception e) {
			e.printStackTrace();
			message = "查询有点击话题数失败";
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 查询话术总量
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "wordsTotal")
	@ResponseBody
	public AjaxJson wordsTotal(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String retailerId = ResourceUtil.getRetailerId();
		message = "查询话术总量成功";
		try {
			String sql = "select count(1) from t_cust_words where status = 'A' ";
			if(Utility.isNotEmpty(retailerId)){
				sql += "and retailer_id='"+retailerId+"' ";
				
			}else {
				sql += "and retailer_id = 'admin' ";
			}

			int total = this.systemService.getCountForJdbc(sql).intValue();
			j.setObj(total);
		} catch (Exception e) {
			e.printStackTrace();
			message = "查询话术总量失败";
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 查询商品话术总量(商品数)
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "hasWordsGoodsNum")
	@ResponseBody
	public AjaxJson hasWordsGoodsNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String retailerId = ResourceUtil.getRetailerId();
		message = "查询商品话术总量成功";
		try {
			/*String sql = "select count(DISTINCT id) from t_goods tg where status = 'A' ";
			if(Utility.isNotEmpty(retailerId)){
				sql += "and retailer_id='"+retailerId+"' ";
				
			}else {
				sql += "and retailer_id = 'admin' ";
			}
			sql += "and id in (select fin_act_id goods_id from t_fin_activity_words where status = 'A' GROUP BY goods_id union  ";
			sql += "select goods_act_id goods_id from t_goods_act_words where status = 'A' GROUP BY goods_id) ";*/
			
			String sql = "select count(1) from t_cust_words where status = 'A' and top_type_id='101' ";
			if(Utility.isNotEmpty(retailerId)){
				sql += "and retailer_id='"+retailerId+"' ";
			}else {
				sql += "and retailer_id = 'admin' ";
			}
			int total = this.systemService.getCountForJdbc(sql).intValue();
			j.setObj(total);
		} catch (Exception e) {
			e.printStackTrace();
			message = "查询商品话术总量失败";
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 查询销售话术总量(商品数)
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "hasMarketWordsGoodsNum")
	@ResponseBody
	public AjaxJson hasMarketWordsGoodsNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String retailerId = ResourceUtil.getRetailerId();
		message = "查询销售话术总量成功";
		try {
			String sql = "select count(DISTINCT tg.id) from t_goods tg left join  t_goods_act_words tgaw "+
				"on tg.id = tgaw.goods_act_id where tg.status = 'A' and tgaw.status = 'A' ";
			if(Utility.isNotEmpty(retailerId)){
				sql += "and retailer_id='"+retailerId+"' ";
				
			}else {
				sql += "and retailer_id = 'admin' ";
			}
			int total = this.systemService.getCountForJdbc(sql).intValue();
			j.setObj(total);
		} catch (Exception e) {
			e.printStackTrace();
			message = "查询销售话术总量失败";
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 统计现有激活的导购数（XX人）
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "hasActiveGuideCount")
	@ResponseBody
	public AjaxJson hasActiveGuideCount(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "现激活的导购数成功";
		Integer total = 0;
		String retalerId = ResourceUtil.getRetailerId();
		try {
			String sql = "select count(1) from t_s_user where user_type = '03' and user_status='1'";
			if(Utility.isNotEmpty(retalerId)){
				sql += "and retailer_id='"+retalerId+"' ";
			}else {
				sql += "and retailer_id = 'admin' ";
			}
			total = this.systemService.getCountForJdbc(sql).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "现激活的导购数失败";
		}
		j.setObj(total);
		j.setMsg(message);
		return j;
	}
	/**
	 * 统计总导购数（XX人）
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "hasGuideCount")
	@ResponseBody
	public AjaxJson hasGuideCount(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "总导购数成功";
		Integer total = 0;
		String retalerId = ResourceUtil.getRetailerId();
		try {
			String active = "select count(1) from t_s_user where user_type = '03' and user_status in ('0','1','2','3') ";
//			String inactive = "select count(1) from t_s_user where user_type = '03' and user_status='0'";
			if(Utility.isNotEmpty(retalerId)){
				active += "and retailer_id='"+retalerId+"' ";
//				inactive += "and retailer_id='"+retalerId+"' ";
			}
			total = this.systemService.getCountForJdbc(active).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "总导购数失败";
		}
		j.setObj(total);
		j.setMsg(message);
		return j;
	}
	/**
	 * 统计退出停用的导购数（XX人）
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "hasInactiveGuideCount")
	@ResponseBody
	public AjaxJson hasInactiveGuideCount(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "停用导购数成功";
		Integer total = 0;
		String retalerId = ResourceUtil.getRetailerId();
		try {
			String sql = "select count(1) from t_s_user where user_type = '03' and user_status in ('0','2')";
			if(Utility.isNotEmpty(retalerId)){
				sql += "and retailer_id='"+retalerId+"' ";
			}else {
				sql += "and retailer_id = 'admin' ";
			}
			total = this.systemService.getCountForJdbc(sql).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "停用导购数失败";
		}
		j.setObj(total);
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 汇总话题数据获取
	 */
	@RequestMapping(params = "getTopicModuleNum")
	@ResponseBody
	public AjaxJson getTopicModuleNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> map = new HashMap<String, Object>();
		AjaxJson onLineNewsNumJ = onLineNewsNum(request); //查询线上话题数
		AjaxJson clickNewsNumJ = clickNewsNum(request);  //查询有点击话题数
		
		map.put("onLineNewsNumJ", onLineNewsNumJ);
		map.put("clickNewsNumJ", clickNewsNumJ);
		
		j.setObj(map);
		
		return j;
	}
	
	/**
	 * 汇总话术数据获取
	 */
	@RequestMapping(params = "getWordsModuleNum")
	@ResponseBody
	public AjaxJson getWordsModuleNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> map = new HashMap<String, Object>();
		AjaxJson wordsTotalJ = wordsTotal(request); //查询话术总量
		AjaxJson hasWordsGoodsNumJ = hasWordsGoodsNum(request);  //查询商品话术总量(商品数)
		AjaxJson hasMarketWordsGoodsNumJ = hasMarketWordsGoodsNum(request);  //查询销售话术总量(商品数)
		
		map.put("wordsTotalJ", wordsTotalJ);
		map.put("hasWordsGoodsNumJ", hasWordsGoodsNumJ);
		map.put("hasMarketWordsGoodsNumJ", hasMarketWordsGoodsNumJ);
		j.setObj(map);
		
		return j;
	}
	
	/**
	 * 汇总订单数据获取
	 */
	@RequestMapping(params = "getOrderModuleNum")
	@ResponseBody
	public AjaxJson getOrderModuleNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> map = new HashMap<String, Object>();
		
		AjaxJson countOrderUnderLineJ = countOrderUnderLine(request); //获取今日线下订单
		AjaxJson countOrderOnlineJ = countOrderOnline(request);  //获取今日线上订单
		AjaxJson countGuaidSaledJ = countGuaidSaled(request);  //获取今日成交导购数
		
		map.put("countOrderUnderLineJ", countOrderUnderLineJ);
		map.put("countOrderOnlineJ", countOrderOnlineJ);
		map.put("countGuaidSaledJ", countGuaidSaledJ);
		
		j.setObj(map);
		
		return j;
	}
	
	/**
	 * 汇总导购数据获取
	 */
	@RequestMapping(params = "getGuideModuleNum")
	@ResponseBody
	public AjaxJson getGuideModuleNum(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> map = new HashMap<String, Object>();
		
		AjaxJson hasActiveGuideCountJ = hasActiveGuideCount(request); //统计现有激活的导购数
		AjaxJson hasGuideCountJ = hasGuideCount(request);  //统计总导购数
		AjaxJson hasInactiveGuideCountJ = hasInactiveGuideCount(request);  //统计退出停用的导购数
		
		map.put("hasActiveGuideCountJ", hasActiveGuideCountJ);
		map.put("hasGuideCountJ", hasGuideCountJ);
		map.put("hasInactiveGuideCountJ", hasInactiveGuideCountJ);
		
		j.setObj(map);
		
		return j;
	}
}
