package com.buss.count.service.impl;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.count.service.ClientDataCountServiceI;

@Service("clientDataCountServiceI")
@Transactional
public class ClientCountServiceImpl extends CommonServiceImpl implements ClientDataCountServiceI {
	DecimalFormat df = new DecimalFormat("######0.00");  
	//获取导购数量信息
	@Override
	public List<Map<String, Object>> getGuideNumList(HttpServletRequest request, String type) {
		List<Map<String, Object>> guideNumList = null;
		String retailerId = ResourceUtil.getRetailerId();
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		if(Utility.isEmpty(start_time)){
			start_time = DateUtils.getFirstDayOfMonth(new Date());
		}
		if(Utility.isEmpty(end_time)){
			end_time = DateUtils.getDataString(DateUtils.date_sdf);
		}
		StringBuffer sql=new StringBuffer("select ts.id id,ifnull(tsp.count,0) count from t_store ts left join (");
		sql.append("select tp.store_id store_id,count(1) count from t_s_user tsu LEFT JOIN t_person tp on tp.user_id = tsu.id ");
		sql.append("where tsu.status='A' and tp.status='A' and tsu.user_type='03' ");
		StringBuffer totalSql = new StringBuffer("select count(1) from t_s_user where status='A' and user_type='03' ");

		if(Utility.isNotEmpty(retailerId)){
			sql.append("and tsu.retailer_id='"+retailerId+"' ");
			totalSql.append("and retailer_id='"+retailerId+"' ");
		}
		if("all".equals(type)){//所有的导购数
//			if(Utility.isNotEmpty(end_time)){
//				sql.append("and tsu.create_date<='"+end_time+" 23:59:59' ");
//				totalSql.append("and create_date<='"+end_time+" 23:59:59' ");
//			}
		}else if("g+".equals(type)){//使用G+的导购数
			sql.append("and tsu.user_status in ('1','2')");//激活，锁定
			totalSql.append("and user_status in ('1','2')");//激活，锁定
			/*if(Utility.isNotEmpty(end_time)){
				sql.append("and tsu.active_time<='"+end_time+" 23:59:59' ");
				totalSql.append("and active_time<='"+end_time+" 23:59:59' ");
			}*/
		}else if("add".equals(type)){//新增导购数
			if(Utility.isNotEmpty(start_time)){
				sql.append("and tsu.create_date>='"+start_time+" 00:00:00' ");
				totalSql.append("and create_date>='"+start_time+" 00:00:00' ");
			}
			if(Utility.isNotEmpty(end_time)){
				sql.append("and tsu.create_date<='"+end_time+" 23:59:59' ");
				totalSql.append("and create_date<='"+end_time+" 23:59:59' ");
			}
		}else if("out".equals(type)){  //退出导购数--停用和注销的导购数
			sql.append(" and tsu.user_status in ('0','4') ");
			totalSql.append(" and user_status in ('0','4') ");
			if(Utility.isNotEmpty(start_time)){
				sql.append("and tsu.update_date>='"+start_time+" 00:00:00' ");
				totalSql.append("and update_date>='"+start_time+" 00:00:00' ");
			}
			if(Utility.isNotEmpty(end_time)){
				sql.append("and tsu.update_date<='"+end_time+" 23:59:59' ");
				totalSql.append("and update_date<='"+end_time+" 23:59:59' ");
			}
		}
		
		sql.append("group BY tp.store_id ) tsp on ts.id = tsp.store_id ");
		sql.append("where ts.status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and ts.retailer_id='"+retailerId+"' ");
		}
		sql.append(" ORDER BY ts.id ");
		
		guideNumList = this.findForJdbc(sql.toString(), null);
		int totalNum = this.getCountForJdbc(totalSql.toString()).intValue();
		Map<String,Object> totalMap = new HashMap<String,Object>();
		if(totalNum == 0){
			totalMap.put("totalNum", "0");
		}else{
			totalMap.put("totalNum", totalNum);
		}
		guideNumList.add(totalMap);
	
		return guideNumList;
	}
	

	//获取导购点击APP数信息
	@Override
	public List<Map<String, Object>> getClickGuideNumList(HttpServletRequest request) {
		List<Map<String, Object>> clickGuideNumList = null;
		String retailerId = ResourceUtil.getRetailerId();
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		if(Utility.isEmpty(start_time)){
			start_time = DateUtils.getFirstDayOfMonth(new Date());
		}
		if(Utility.isEmpty(end_time)){
			end_time = DateUtils.getDataString(DateUtils.date_sdf);
		}

		StringBuffer totalSql = new StringBuffer("select sum(tsua.count) count from t_person tp left join ( select user_id,DATE_FORMAT(use_time, '%Y-%m-%d') dt,1 count from t_s_use_app ")
				.append("where retailer_id = '"+retailerId+"' ");
		StringBuffer sql=new StringBuffer("select ts.id id,ifnull(tsp.count,0) count from t_store ts left join (")
			.append("select tp.store_id store_id,sum(tsua.count) count from t_person tp left join ( ")
			.append("SELECT t.user_id,SUM(count) count from (select user_id,DATE_FORMAT(use_time, '%Y-%m-%d') dt,1 count from t_s_use_app  ")
			.append("where retailer_id = '"+retailerId+"' ");
		if(Utility.isNotEmpty(start_time)){
			sql.append("and use_time>='"+start_time+" 00:00:00' ");
			totalSql.append("and use_time>='"+start_time+" 00:00:00' ");
		}
		if(Utility.isNotEmpty(end_time)){
			sql.append("and use_time<='"+end_time+" 23:59:59' ");
			totalSql.append("and use_time<='"+end_time+" 23:59:59' ");
		}
		sql.append("GROUP BY dt,user_id ) t GROUP BY t.user_id ) tsua  on tsua.user_id = tp.user_id where tp.status='A' ");
		totalSql.append("GROUP BY dt,user_id ) tsua ON tsua.user_id = tp.user_id where tp.status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and tp.to_retailer_id ='"+retailerId+"' ");
			totalSql.append("and tp.to_retailer_id ='"+retailerId+"' ");
		}
		sql.append("GROUP BY tp.store_id ");
		sql.append(") tsp  on ts.id = tsp.store_id  where ts.status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and ts.retailer_id='"+retailerId+"' ");
		}
		sql.append("ORDER BY ts.id");
		
		clickGuideNumList = this.findForJdbc(sql.toString(), null);
//		double  days = 1.0;
		int days = 1;//统计时间段天数间隔
//		try {
//			days = (double) ((sdf2.parse(end_time).getTime() - sdf2.parse(start_time).getTime()) / (1000*3600*24))+1;
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		//多天计算平均的点击数量
		if(Utility.isNotEmpty(start_time) && Utility.isNotEmpty(end_time)){
			days = DateUtil.daysBetween(DateUtil.stringToDate(start_time), DateUtil.stringToDate(end_time))+1;
			if(days==0){
				days=1;
			}
			for (Map<String, Object> map : clickGuideNumList) {
				String countSr = map.get("count").toString();
				double count = Double.valueOf(countSr)/days;
//				double count = Integer.parseInt(countSr)/days;
				map.put("count", df.format(count));
			}
		}
		double totalNumStr = this.getCountForJdbc(totalSql.toString()).doubleValue();
		Map<String,Object> totalMap = new HashMap<String,Object>();
		double totalNum = 0.0;
		totalNum = totalNumStr/days;
		totalMap.put("totalNum", df.format(totalNum));
		clickGuideNumList.add(totalMap);
		return clickGuideNumList;
	}

	//获取成交导购数
	@Override
	public List<Map<String, Object>> getGuideOrderList(HttpServletRequest request) {
		List<Map<String, Object>> guideOrderList = null;
		String retailerId = ResourceUtil.getRetailerId();
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		if(Utility.isEmpty(start_time)){
			start_time = DateUtils.getFirstDayOfMonth(new Date());
		}
		if(Utility.isEmpty(end_time)){
			end_time = DateUtils.getDataString(DateUtils.date_sdf);
		}
		
		StringBuffer sql=new StringBuffer("select ts.id id,ifnull(tsp.count,0) count from t_store ts left join (");
		sql.append("select store_id,count(1) count from ");
		sql.append("(select tp.store_id store_id,toi.to_guide_id from t_order_info toi ");
		sql.append("LEFT JOIN t_person tp on tp.user_id = toi.to_guide_id where tp.status='A' and toi.status='A' ");
		StringBuffer totalSql=new StringBuffer("select count(1) from (");
		totalSql.append("select tp.store_id store_id,toi.to_guide_id from t_order_info toi ");
		totalSql.append("LEFT JOIN t_person tp on tp.user_id = toi.to_guide_id where tp.status='A' and toi.status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and toi.to_retailer_id='"+retailerId+"' ");
			totalSql.append("and toi.to_retailer_id='"+retailerId+"' ");
		}
		if(Utility.isNotEmpty(start_time)){
			sql.append("and pay_time>='"+start_time+" 00:00:00' ");
			totalSql.append("and pay_time>='"+start_time+" 00:00:00' ");
		}
		if(Utility.isNotEmpty(end_time)){
			sql.append("and pay_time<='"+end_time+" 23:59:59' ");
			totalSql.append("and pay_time<='"+end_time+" 23:59:59' ");
		}
		sql.append("group by to_guide_id) temp group by store_id ");
		sql.append(") tsp  on ts.id = tsp.store_id  where ts.status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and ts.retailer_id='"+retailerId+"' ");
		}
		sql.append(" ORDER BY ts.id ");
		
		totalSql.append("group by to_guide_id) temp");
		
		guideOrderList = this.findForJdbc(sql.toString(), null);
		int totalNum = this.getCountForJdbc(totalSql.toString()).intValue();
		Map<String,Object> totalMap = new HashMap<String,Object>();
		totalMap.put("totalNum", totalNum);
		guideOrderList.add(totalMap);
		
		return guideOrderList;
	}

	//成交单数
	@Override
	public List<Map<String, Object>> getPayOrderNumList(HttpServletRequest request) {
		List<Map<String, Object>> payOrderNumList = null;
		String retailerId = ResourceUtil.getRetailerId();
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		if(Utility.isEmpty(start_time)){
			start_time = DateUtils.getFirstDayOfMonth(new Date());
		}
		if(Utility.isEmpty(end_time)){
			end_time = DateUtils.getDataString(DateUtils.date_sdf);
		}
		
		StringBuffer sql=new StringBuffer("select ts.id id,ifnull(tsp.count,0) orderNum,ifnull(tsp.quantityAmount,0) quantityAmount,");
		sql.append("ifnull(tsp.payAmount,0) payAmount from t_store ts left join ( ");
		sql.append("select tp.store_id store_id,COUNT(1) count,sum(quantity_amount) quantityAmount,sum(pay_amount) payAmount ");
		sql.append("from t_order_info toi LEFT JOIN t_person tp on tp.user_id = toi.to_guide_id where tp.status='A' and toi.status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and toi.to_retailer_id='"+retailerId+"' ");
		}
		if(Utility.isNotEmpty(start_time)){
			sql.append("and pay_time>='"+start_time+" 00:00:00' ");
		}
		if(Utility.isNotEmpty(end_time)){
			sql.append("and pay_time<='"+end_time+" 23:59:59' ");
		}
		sql.append("group BY tp.store_id ) tsp  on ts.id = tsp.store_id  where ts.status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and ts.retailer_id='"+retailerId+"' ");
		}
		sql.append(" ORDER BY ts.id ");
		payOrderNumList = this.findForJdbc(sql.toString(), null);
		
		//拼接平均导购产出
		int payOrderTotal = 0;
		int orderGoodsTotal = 0;
		double orderMoneyTotal = 0;
		double orderAverTotal = 0.0;
		List<Map<String,Object>> guideNumList = new ArrayList<Map<String,Object>>();
 		guideNumList = this.getGuideNumList(request,"g+"); //使用G+的导购数
 		int i=0;
 		//多天算平均值
		for (Map<String, Object> map : payOrderNumList) {
			double guideNum = Double.parseDouble(guideNumList.get(i).get("count").toString());
			int payOrder = Integer.parseInt(map.get("orderNum").toString());
			int orderGoods = Integer.parseInt(map.get("quantityAmount").toString());
			double orderMoney = Double.parseDouble(map.get("payAmount").toString());
			payOrderTotal += payOrder;
			orderGoodsTotal += orderGoods;
			orderMoneyTotal += orderMoney;
			double orderAver = 0.0;
			if(guideNum != 0){
				orderAver = payOrder*1.0/guideNum;
			}
			map.put("orderAver", df.format(orderAver));
			map.put("payAmount", df.format(orderMoney));
			i++;
		}

		int guideNum = Integer.parseInt(guideNumList.get(guideNumList.size()-1).get("totalNum").toString());
		if(guideNum != 0){
			orderAverTotal = payOrderTotal*1.0/guideNum;
		}
		Map<String,Object> totalMap = new HashMap<String,Object>();
		totalMap.put("payOrderTotal", payOrderTotal);
		totalMap.put("orderGoodsTotal", orderGoodsTotal);
		totalMap.put("orderMoneyTotal", df.format(orderMoneyTotal));
		totalMap.put("orderAverTotal", df.format(orderAverTotal));
		payOrderNumList.add(totalMap);
		
		return payOrderNumList;
	}
	//非实体营业时间成交单数
	public List<Map<String, Object>> getOutTimeOrderNumList(HttpServletRequest request){
		List<Map<String, Object>> outTimeOrderNumList = null;
		String retailerId = ResourceUtil.getRetailerId();
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		if(Utility.isEmpty(start_time)){
			start_time = DateUtils.getFirstDayOfMonth(new Date());
		}
		if(Utility.isEmpty(end_time)){
			end_time = DateUtils.getDataString(DateUtils.date_sdf);
		}
		
		StringBuffer sql=new StringBuffer("select ts.id id,ifnull(tsp.count,0) count from t_store ts left join (");
		StringBuffer totalSql=new StringBuffer("select COUNT(1) from t_order_info toi LEFT JOIN t_person tp on tp.user_id = toi.to_guide_id ");
		totalSql.append("where tp.status='A' and toi.status='A' ");
		
		sql.append("select tp.store_id store_id,COUNT(1) count from t_order_info toi LEFT JOIN t_person tp ");
		sql.append("on tp.user_id = toi.to_guide_id where tp.status='A' and toi.status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and toi.to_retailer_id='"+retailerId+"' ");
			totalSql.append("and toi.to_retailer_id='"+retailerId+"' ");
		}
		if(Utility.isNotEmpty(start_time)){
			sql.append("and pay_time>='"+start_time+" 00:00:00' ");
			totalSql.append("and pay_time>='"+start_time+" 00:00:00' ");
		}
		if(Utility.isNotEmpty(end_time)){
			sql.append("and pay_time<='"+end_time+" 23:59:59' ");
			totalSql.append("and pay_time<='"+end_time+" 23:59:59' ");
		}
		
		sql.append("and (date_format(pay_time,'%H:%i:%S') <= '10:00:00' ");
		sql.append("or date_format(pay_time,'%H:%i:%S') >= '21:00:00') group BY tp.store_id ");
		totalSql.append("and (date_format(pay_time,'%H:%i:%S') <= '10:00:00' ");
		totalSql.append("or date_format(pay_time,'%H:%i:%S') >= '21:00:00') ");
		
		sql.append(") tsp  on ts.id = tsp.store_id  where ts.status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and ts.retailer_id='"+retailerId+"' ");
		}
		sql.append(" ORDER BY ts.id ");

		outTimeOrderNumList = this.findForJdbc(sql.toString(), null);
		int totalNum = this.getCountForJdbc(totalSql.toString()).intValue();
		Map<String,Object> totalMap = new HashMap<String,Object>();
		totalMap.put("totalNum", totalNum);
		outTimeOrderNumList.add(totalMap);

		return outTimeOrderNumList;
	}
	
	//获取 待发展 、 已成交 （待发展+ 已成交）顾客数
	@Override
	public List<Map<String, Object>> countCustomer(HttpServletRequest request,
			Integer type) {
		String addRetailerId = ResourceUtil.getRetailerId();
//		String createDateBegin = request.getParameter("start_time");
		String createDateEnd = request.getParameter("end_time");
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		Integer totalNum = 0;
		
		StringBuilder sb = new StringBuilder();
		StringBuilder totalsb = new StringBuilder();

		sb.append("select ts.id storeId,ifnull(tsp.count,0) count from t_store ts left join ( select phone_reg_shop store_id,count(1) count from t_focus_customer WHERE STATUS ='A' ");
		totalsb.append("select count(1) from t_focus_customer where  STATUS ='A' ");
		if (Utility.isNotEmpty(addRetailerId)) {
			sb.append("AND to_retailer_id = '").append(addRetailerId).append("' ");
			totalsb.append("AND to_retailer_id = '").append(addRetailerId).append("' ");
		}
		if (type ==1) {//查询待发展顾客
			sb.append("AND (type <> '3' or type is null) ");
			totalsb.append("AND (type <> '3' or type is null) ");
		} else if (type == 2) {//查询已成交的顾客
			sb.append("AND type = '3' ");
			totalsb.append("AND type = '3' ");
		} else if (type == 3){//查询待发展 + 已成交的顾客
			/*sb.append("AND type in ('','3')");
			totalsb.append("AND type in ('','3')");*/
		}
		//不用过滤创建时间大于开始时间的数据--注释
		/*if (Utility.isNotEmpty(createDateBegin)) {
			if(type != 3){
				totalsb.append("AND create_date >= '").append(createDateBegin).append(" 00:00:00' ");
				sb.append("AND create_date >= '").append(createDateBegin).append(" 00:00:00' ");
			}
		}*/
		if (Utility.isNotEmpty(createDateEnd)) {
			sb.append("AND create_date <= '").append(createDateEnd).append(" 23:59:59' ");
			totalsb.append("AND create_date <= '").append(createDateEnd).append(" 23:59:59' ");
		}
		sb.append("GROUP BY phone_reg_shop) tsp on ts.id = tsp.store_id where ts.status='A' ");
		if (Utility.isNotEmpty(addRetailerId)) {
			sb.append("AND ts.retailer_id='").append(addRetailerId).append("' ");
		}
		sb.append("ORDER BY ts.id");
		totalNum = this.getCountForJdbc(totalsb.toString()).intValue();

		resultList = this.findForJdbc(sb.toString());

		map.put("totalNum", totalNum);
		resultList.add(map);
		return resultList;
	}
	
	/**
	 * 获取不同占比下的顾客数
	 */
	@Override
	public List<Map<String, Object>> countCustomerPercent(HttpServletRequest request, Integer beginPercent,Integer endPercent) {
		String retailerId = ResourceUtil.getRetailerId();
//		String createDateBegin = request.getParameter("start_time");
		String createDateEnd = request.getParameter("end_time");
		StringBuilder sb = new StringBuilder();
		StringBuilder totalSb = new StringBuilder();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map  = new HashMap<String,Object>();
		Integer totalNum = 0;  
		
		sb.append("SELECT ts.id storeId, ifnull(tsp.count, 0) count FROM t_store ts LEFT JOIN ( SELECT phone_reg_shop store_id,count(1) count ")
		.append("FROM t_focus_customer tfc JOIN t_cust_info_complete tcic ON tfc.id = tcic.customer_id ")
		.append("WHERE  tfc.`status` = 'A' and tcic.`status` = 'A' ");
		
		totalSb.append(" SELECT  count(1) count FROM t_focus_customer tfc JOIN t_cust_info_complete tcic ")
		.append("ON tfc.id = tcic.customer_id ")
		.append("WHERE tfc.`status` = 'A' AND tcic.`status` = 'A' ");
		if (beginPercent == 0) { //顾客资料完整度为0的顾客数
			sb.append("AND percent >=").append(beginPercent).append(" ");
			totalSb.append("AND percent >=").append(beginPercent).append(" ");
		}else{
			sb.append("AND percent >").append(beginPercent).append(" ");
			totalSb.append("AND percent >").append(beginPercent).append(" ");
		}
		sb.append("AND percent <= ").append(endPercent).append(" ");
		totalSb.append("AND percent <= ").append(endPercent).append(" ");
		/*if (Utility.isNotEmpty(createDateBegin)) {
			sb.append("AND create_date >= '").append(createDateBegin).append(" 00:00:00' ");
			totalSb.append("AND create_date >= '").append(createDateBegin).append(" 00:00:00' ");
		}*/
		if (Utility.isNotEmpty(createDateEnd)) {
			sb.append("AND tcic.create_date <= '").append(createDateEnd).append(" 23:59:59' ");
			totalSb.append("AND tcic.create_date <= '").append(createDateEnd).append(" 23:59:59' ");
		}
		if (Utility.isNotEmpty(retailerId)) {
			sb.append(" AND to_retailer_id = '").append(retailerId).append("' ");
			totalSb.append(" AND to_retailer_id = '").append(retailerId).append("' ");
		}
		sb.append("GROUP BY phone_reg_shop ) tsp ON ts.id = tsp.store_id WHERE ts. STATUS = 'A' ");
		if (Utility.isNotEmpty(retailerId)) {
			sb.append(" AND ts.retailer_id = '").append(retailerId).append("' ");
		}
		sb.append("ORDER BY ts.id");
		totalNum = this.getCountForJdbc(totalSb.toString()).intValue();
		resultList = this.findForJdbc(sb.toString(),null);
		map.put("totalNum",totalNum);
		resultList.add(map);
		return resultList;
	}
	
	//根据需求查询商品数量
	@Override
	public List<Map<String, Object>> countGoods(HttpServletRequest request,
			Integer type) {
		String retailerId = ResourceUtil.getRetailerId();
//		String createDateBegin = request.getParameter("start_time");
		String createDateEnd = request.getParameter("end_time");
		StringBuilder sb = new StringBuilder();
		StringBuilder totalSb = new StringBuilder();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map  = new HashMap<String,Object>();
		Integer totalNum = 0; 
		
		sb.append("SELECT t.id storeId, ifnull(cp.count,0) count from t_store t LEFT JOIN ( SELECT count(1) count, retailer_id FROM t_goods WHERE `status` = 'A' and goods_type='1' ");
		totalSb.append("SELECT count(1) count FROM t_goods WHERE `status` = 'A' and goods_type='1' ");
		if (type == 1) {//查询待发布商品总数
			sb.append("AND goods_status = '3' ");
			totalSb.append("AND goods_status = '3' ");
		}else if (type == 2) {//查询已发布的商品总数
			sb.append("AND goods_status = '4' ");
			totalSb.append("AND goods_status = '4' ");
		}else if (type == 3) {//查询待发布和已发布的商品总数
			sb.append("AND goods_status in ('4','3') ");
			totalSb.append("AND goods_status in ('4','3') ");
		}
		/*if (Utility.isNotEmpty(createDateBegin)) {
			sb.append("AND create_date >= '").append(createDateBegin).append(" 00:00:00' ");
			totalSb.append("AND create_date >= '").append(createDateBegin).append(" 00:00:00' ");
		}*/
		if (Utility.isNotEmpty(createDateEnd)) {
			sb.append("AND create_date <= '").append(createDateEnd).append(" 23:59:59' ");
			totalSb.append("AND create_date <= '").append(createDateEnd).append(" 23:59:59' ");
		}
		if (Utility.isNotEmpty(retailerId)) {
			sb.append(" AND retailer_id = '").append(retailerId).append("' ");
			totalSb.append(" AND retailer_id = '").append(retailerId).append("' ");
		}
		sb.append("GROUP BY retailer_id ) cp ON t.retailer_id = cp.retailer_id where t.STATUS = 'A' ");
		if (Utility.isNotEmpty(retailerId)) {
			sb.append(" AND t.retailer_id = '").append(retailerId).append("' ");
		}
		sb.append("ORDER BY t.id");
		
		totalNum = this.getCountForJdbc(totalSb.toString()).intValue();
		resultList = this.findForJdbc(sb.toString(),null);
		map.put("totalNum",totalNum);
		//查询已发布的商品时查询含话术的商品占比
		if(type == 2){
			StringBuilder sbGoodsWords = new StringBuilder("select count(DISTINCT tfaw.fin_act_id) count from ");
			sbGoodsWords.append("t_goods tg right join t_fin_activity_words tfaw on tg.id = tfaw.fin_act_id ")
				.append("where tg.status='A' and tfaw.status='A' AND goods_status = '4' ");
			/*if (Utility.isNotEmpty(createDateBegin)) {
				sbGoodsWords.append("AND tg.create_date >= '").append(createDateBegin).append(" 00:00:00' ");
			}*/
			if (Utility.isNotEmpty(createDateEnd)) {
				sbGoodsWords.append("AND tg.create_date <= '").append(createDateEnd).append(" 23:59:59' ");
			}
			if (Utility.isNotEmpty(retailerId)) {
				sbGoodsWords.append(" AND retailer_id = '").append(retailerId).append("' ");
			}
			int goodsWordsTotalNum = this.getCountForJdbc(sbGoodsWords.toString()).intValue();
			double percent = 0.0;
			if(totalNum > 0){
				percent = goodsWordsTotalNum*100.0/totalNum;
			}
			map.put("percent", df.format(percent)+"%");
		}
		resultList.add(map);
		
		return resultList;
	}
	
	//查询含有话术的商品占比  -- 没在用
	@Override
	public List<Map<String, Object>> countHasWordsPercent(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		String createDateBegin = request.getParameter("start_time");
		String createDateEnd = request.getParameter("end_time");
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map  = new HashMap<String,Object>();
		StringBuilder wordsSql = new StringBuilder(); //查询含有话术的商品数量
		StringBuilder totalSql = new StringBuilder(); //查询所有的已发布的商品数
		StringBuilder storeSql = new StringBuilder(); //查询所有的零售商
		
		totalSql.append("SELECT count(1) count FROM t_goods WHERE `status` = 'A' AND publish_status = '1' ");
		wordsSql.append("SELECT count(DISTINCT fin_act_id) count FROM t_fin_activity_words f left JOIN t_goods g ON f.fin_act_id = g.id WHERE g. STATUS = 'A' AND f. STATUS = 'A' AND words_id IS NOT NULL AND g.publish_status = '1' ");
		storeSql.append("SELECT id storeId FROM t_store WHERE STATUS = 'A' ");
		if (Utility.isNotEmpty(retailerId)) {
			totalSql.append(" AND retailer_id = '").append(retailerId).append("' ");
			wordsSql.append(" AND retailer_id = '").append(retailerId).append("' ");
			storeSql.append(" AND retailer_id = '").append(retailerId).append("' ");
		}
		if (Utility.isNotEmpty(createDateBegin)) {
			totalSql.append("AND create_date >= '").append(createDateBegin).append(" 00:00:00' ");
			wordsSql.append("AND f.create_date >= '").append(createDateBegin).append(" 00:00:00' ");
		} 
		if (Utility.isNotEmpty(createDateEnd)) {
			totalSql.append("AND create_date <= '").append(createDateEnd).append(" 23:59:59' ");
			wordsSql.append("AND f.create_date <= '").append(createDateEnd).append(" 23:59:59' ");
		}
		storeSql.append("ORDER BY id ");
		Integer totalNum = this.getCountForJdbc(totalSql.toString()).intValue();
		Integer totalWords = this.getCountForJdbc(wordsSql.toString()).intValue();
		String percent = getPercent(totalWords, totalNum);
		resultList = this.findForJdbc(storeSql.toString(),null);
		
		map.put("percent",percent);
		resultList.add(map);
		return resultList;
	}

	//计算平均值
	private static String getPercent(int x ,int y){
	  String percent="";//接受百分比的值
	  if (x == 0 || y == 0){
		  return "0.00%";
	  }
	  double baiy=x*1.0;
	  double baiz=y*1.0;
	  
	  double fen=baiy/baiz;
	  
	  DecimalFormat df1 = new DecimalFormat("##.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
	   //baifenbi=nf.format(fen);   
	  percent= df1.format(fen);  
	  if(".00%".equals(percent)){percent="0%";}
	  if("100.00%".equals(percent)){percent="100%";}
	  if(percent.endsWith(".00%")){percent=percent.substring(0, percent.length()-4)+"%";} //if去0

	   return percent;
	}
	//已发布的话题数
	@Override
	public List<Map<String, Object>> hasUploadedNumList(HttpServletRequest request) {
		Integer totalNum = 0;
		String retalerId = ResourceUtil.getRetailerId();
//		String createDateBegin = request.getParameter("start_time");
		String createDateEnd = request.getParameter("end_time");
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append( " select ts.id id,count(1) count from t_store ts left join (select id,shopkeeper from t_news where upLoaded = 'Y' ");
		if(Utility.isNotEmpty(retalerId)){
			stringBuilder.append(" and shopkeeper = '").append(retalerId).append("'");
		}
		/*if (Utility.isNotEmpty(createDateBegin)) {
			stringBuilder.append(" AND create_date >= '").append(createDateBegin).append(" 00:00:00'  ");
		}*/
		if (Utility.isNotEmpty(createDateEnd)) {
			stringBuilder.append(" AND create_date <= '").append(createDateEnd).append(" 23:59:59'  ");
		}
		stringBuilder.append("	) tn on tn.shopkeeper = ts.retailer_id where ts.retailer_id = '").append(retalerId).append("'").append(" GROUP BY ts.id ORDER BY ts.id ");
		
		String countSql="select count(1) from t_news where upLoaded = 'Y' ";
		if(Utility.isNotEmpty(retalerId)){
			countSql +=" and shopkeeper = '"+retalerId+"'";
		}
		totalNum= this.getCountForJdbc(countSql).intValue();
		if(totalNum > 0){
			resultList = this.findForJdbc(stringBuilder.toString());
		}
		Map<String,Object> totalMap = new HashMap<String,Object>();
		totalMap.put("totalNum", totalNum);
		resultList.add(totalMap);
		return resultList;
	}
	//有点击的话题数
	@Override
	public List<Map<String, Object>> hasClickNumList(HttpServletRequest request) {
		Integer totalNum = 0;
		String retalerId = ResourceUtil.getRetailerId();
		String createDateBegin = request.getParameter("start_time");
		String createDateEnd = request.getParameter("end_time");
		List<Map<String,Object>> resultList = null;
		StringBuilder stringBuilder = new StringBuilder("");
		StringBuilder sb = new StringBuilder("select count(1) from( select news_id from t_news_count where 1=1 ");
		stringBuilder.append( "select ts.id,ifnull(count(news_id),0) count from t_store ts left join (select store_id,news_id from t_news_count where 1=1");
		if(Utility.isNotEmpty(retalerId)){
			stringBuilder.append(" and retailer_id = '").append(retalerId).append("'");
			sb.append(" and retailer_id = '").append(retalerId).append("' ");
		}
		if (Utility.isNotEmpty(createDateBegin)) {
			stringBuilder.append(" and click_time >= '").append(createDateBegin).append(" 00:00:00' ");
			sb.append(" and click_time >= '").append(createDateBegin).append(" 00:00:00' ");
		}
		if (Utility.isNotEmpty(createDateEnd)) {
			stringBuilder.append("and click_time <= '").append(createDateEnd).append(" 23:59:59' ");	
			sb.append("and click_time <= '").append(createDateEnd).append(" 23:59:59' ");	
		}
		stringBuilder.append("GROUP BY  store_id,news_id) tnc on ts.id = tnc.store_id where 1=1 ");
		if(Utility.isNotEmpty(retalerId)){
			stringBuilder.append("and ts.retailer_id = '").append(retalerId).append("' ");
		}
		stringBuilder.append(" GROUP BY ts.id ORDER BY ts.id");
		sb.append(" GROUP BY news_id) temp");
		
		totalNum = this.getCountForJdbc(sb.toString()).intValue();
		resultList = this.findForJdbc(stringBuilder.toString(),null);
		Map<String,Object> totalMap = new HashMap<String,Object>();
		totalMap.put("totalNum", totalNum);
		resultList.add(totalMap);
		return resultList;
	}


	//有推送的话题数
	@Override
	public List<Map<String, Object>> hasIssendList(HttpServletRequest request) {
		Integer totalNum = 0;
		String retalerId = ResourceUtil.getRetailerId();
		String createDateBegin = request.getParameter("start_time");
		String createDateEnd = request.getParameter("end_time");
		List<Map<String,Object>> resultList = null;
		StringBuilder stringBuilder = new StringBuilder("");
		StringBuilder sb = new StringBuilder("select count(1) from( select news_id from t_news_push_count where 1=1 ");
		stringBuilder.append( "select ts.id,ifnull(count(news_id),0) count from t_store ts left join (select store_id,news_id from t_news_push_count  where 1=1 ");
		if(Utility.isNotEmpty(retalerId)){
			stringBuilder.append("and retailer_id = '").append(retalerId).append("' ");
			sb.append("and retailer_id = '").append(retalerId).append("' ");
		}
		if (Utility.isNotEmpty(createDateBegin)) {
			stringBuilder.append(" and push_time >= '").append(createDateBegin).append(" 00:00:00' ");
			sb.append(" and push_time >= '").append(createDateBegin).append(" 00:00:00' ");
		}
		if (Utility.isNotEmpty(createDateEnd)) {
			stringBuilder.append("and push_time <= '").append(createDateEnd).append(" 23:59:59' ");	
			sb.append("and push_time <= '").append(createDateEnd).append(" 23:59:59' ");	
		}
		stringBuilder.append(" GROUP BY  store_id,news_id) tnc on ts.id = tnc.store_id where 1=1 ");
		if(Utility.isNotEmpty(retalerId)){
			stringBuilder.append("and ts.retailer_id = '").append(retalerId).append("' ");
		}
		stringBuilder.append(" GROUP BY ts.id ORDER BY ts.id");
		sb.append(" GROUP BY news_id) temp");
		totalNum = this.getCountForJdbc(sb.toString()).intValue();
		resultList = this.findForJdbc(stringBuilder.toString());
		Map<String,Object> totalMap = new HashMap<String,Object>();
		totalMap.put("totalNum", totalNum);
		resultList.add(totalMap);
		return resultList;
	}

	//撩客话术量
	@Override
	public List<Map<String, Object>> hasCustWordsNumList(HttpServletRequest request) {
		Integer totalNum = 0;
		String retalerId = ResourceUtil.getRetailerId();
//		String createDateBegin = request.getParameter("start_time");
		String createDateEnd = request.getParameter("end_time");
		List<Map<String,Object>> resultList =null;
		StringBuilder stringBuilder = new StringBuilder("");
		StringBuilder sb =  new StringBuilder("");
		stringBuilder.append( " SELECT  t_store.id, count(t_cust_words.id) As count FROM t_cust_words, t_store  WHERE 1=1 ");
		sb.append( "select max(count) as total from ( SELECT t_store.id, count(t_cust_words.id) count FROM t_cust_words,t_store WHERE 1=1 ");
		if(Utility.isNotEmpty(retalerId)){
			stringBuilder.append(" and  t_cust_words.retailer_id  = '").append(retalerId).append("'").append(" AND  t_store.retailer_id='").append(retalerId).append("'");
			sb.append(" and  t_cust_words.retailer_id  = '").append(retalerId).append("'").append(" AND t_cust_words.retailer_id = t_store.retailer_id");
		}
		stringBuilder.append( " and t_cust_words.top_type_id = '102'  AND t_cust_words.sub_type_id = '0' ");
		sb.append( " and t_cust_words.top_type_id = '102' AND t_cust_words.sub_type_id = '0'");
		
		/*if (Utility.isNotEmpty(createDateBegin)) {
			stringBuilder.append(" AND create_date >= '").append(createDateBegin).append(" 00:00:00' ");
			sb.append(" AND create_date >= '").append(createDateBegin).append(" 00:00:00' ");
		}*/
		if (Utility.isNotEmpty(createDateEnd)) {
			stringBuilder.append(" AND t_cust_words.create_date <= '").append(createDateEnd).append(" 23:59:59' ");	
			sb.append(" AND t_cust_words.create_date <= '").append(createDateEnd).append(" 23:59:59' ");	
		}
		stringBuilder.append(" GROUP BY t_store.id ORDER BY t_store.id");
		sb.append(" GROUP BY t_store.id ORDER BY t_store.id) As c");
		totalNum = this.getCountForJdbc(sb.toString()).intValue();
		resultList = this.findForJdbc(stringBuilder.toString());
		Map<String,Object> totalMap = new HashMap<String,Object>();
		totalMap.put("totalNum", totalNum);
		resultList.add(totalMap);
		return resultList;
	}
	

	//完成任务的导购数
	public List<Map<String, Object>> finishJobGuideNumList(HttpServletRequest request){
		String retalerId = ResourceUtil.getRetailerId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd MM:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String createDateBegin = request.getParameter("start_time");
		String createDateEnd = request.getParameter("end_time");
		List<Map<String,Object>> resultList =null;
		StringBuilder sql = new StringBuilder("select ts.id id,tjm.start_time startTime,tjm.end_time endTime,ifnull(sum(tsp.count),0) count from t_store ts ");
		sql.append("left join t_job_model tjm on ts.job_model_id = tjm.id left join (");
		sql.append("select id,model_id, 1 count from t_job_param where status='A' ");
		if(Utility.isNotEmpty(retalerId)){
			sql.append("and retailer_id = '"+retalerId+"'");
		}
		sql.append(") tsp  on ts.job_model_id = tsp.model_id  where ts.status='A' ");
		if(Utility.isNotEmpty(retalerId)){
			sql.append("and ts.retailer_id = '"+retalerId+"' ");
		}
		sql.append("group by ts.id ORDER BY ts.id");

		resultList =this.findForJdbc(sql.toString());
		int totalFinish=0;
		double totalDays=0.0;
		for (Map<String, Object> map : resultList) {
			String id = map.get("id").toString();
			if(Utility.isNotEmpty(map.get("startTime"))){
				String startTime = map.get("startTime").toString();
				String endTime = map.get("endTime").toString();
				String count = map.get("count").toString();
				try {
					Date checkDateBegin = sdf2.parse(createDateBegin);
					Date checkDateEnd = sdf2.parse(createDateEnd);
					Date startDate = sdf.parse(startTime);
					Date endDate = sdf.parse(endTime);
					if(checkDateBegin.getTime() > startDate.getTime()){
						startDate = checkDateBegin;
					}
					if(checkDateEnd.getTime() < endDate.getTime()){
						endDate = checkDateEnd;
					}
					StringBuilder countSql =  new StringBuilder("select count(1) from (");
					countSql.append("select store_id,count(1) count from t_job_detail_count where pace_type=1 ");
					if(Utility.isNotEmpty(retalerId)){
						countSql.append("and retailer_id = '"+retalerId+"'");
					}
					if(Utility.isNotEmpty(id)){
						countSql.append("and store_id = '"+id+"'");
					}
					if (Utility.isNotEmpty(startDate)) {
						countSql.append(" and update_date >= '").append(sdf2.format(startDate)).append(" 00:00:00' ");
					}
					if (Utility.isNotEmpty(endDate)) {
						countSql.append("and update_date <= '").append(sdf2.format(endDate)).append(" 23:59:59' ");	
					}
					countSql.append("group by guide_id,DATE_FORMAT(start_time,'%Y-%m-%d')) temp where count>=" + count);
					int totalNum = this.getCountForJdbc(countSql.toString()).intValue();
					totalFinish +=totalNum;
					
					double days = (double) ((endDate.getTime() - startDate.getTime()) / (1000*3600*24))+1;
					totalDays +=days;
					
					double percent = 0.0;
					if(days > 0){
						percent = totalNum/days;
					}
					map.put("percent", df.format(percent));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				map.put("percent", 0.0);
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(totalDays > 0){
			map.put("percent", df.format(totalFinish/totalDays));
		}else{
			map.put("percent", 0.0);
		}
		resultList.add(map);
		
		return resultList;
	}

	//线上任务数  --截止日期时的任务数
	public List<Map<String, Object>> onlineJobNumList(HttpServletRequest request){
		String retalerId = ResourceUtil.getRetailerId();
		List<Map<String,Object>> resultList =null;
		String end_time = request.getParameter("end_time");
		StringBuilder sql = new StringBuilder("select ts.id id,ifnull(sum(tsp.count),0) count from t_store ts left join (");
		StringBuilder countSql = new StringBuilder("select ifnull(sum(tsp.count),0) count from t_store ts left join (");
		sql.append("select tjp.id id,model_id, 1 count from t_job_param tjp join t_job_model tjm on tjp.model_id = tjm.id ");
		countSql.append("select tjp.id id,model_id, 1 count from t_job_param tjp join t_job_model tjm on tjp.model_id = tjm.id ");

		sql.append("WHERE tjp.STATUS = 'A' and tjm.STATUS = 'A' ");
		countSql.append("WHERE tjp.STATUS = 'A' and tjm.STATUS = 'A' ");
		if(Utility.isNotEmpty(end_time)){
			sql.append("and tjm.start_time <= '"+end_time+" 12:00:00'");
			sql.append("and tjm.end_time >= '"+end_time+" 12:00:00'");
			countSql.append("and tjm.start_time <= '"+end_time+" 12:00:00'");
			countSql.append("and tjm.end_time >= '"+end_time+" 12:00:00'");
		}
		if(Utility.isNotEmpty(retalerId)){
			sql.append("and tjp.retailer_id = '"+retalerId+"'");
			countSql.append("and tjp.retailer_id = '"+retalerId+"'");
		}
		sql.append(") tsp  on ts.job_model_id = tsp.model_id  where ts.status='A' ");
		countSql.append(") tsp  on ts.job_model_id = tsp.model_id  where ts.status='A' ");
		if(Utility.isNotEmpty(retalerId)){
			sql.append("and ts.retailer_id = '"+retalerId+"' ");
			countSql.append("and ts.retailer_id = '"+retalerId+"' ");
		}
		sql.append(" group by ts.id ORDER BY ts.id");
		countSql.append(" ORDER BY ts.id");

		resultList =this.findForJdbc(sql.toString());
		int totalNum = this.getCountForJdbc(countSql.toString()).intValue();
		Map<String,Object> totalMap = new HashMap<String,Object>();
		totalMap.put("totalNum", totalNum);
		resultList.add(totalMap);
		
		return resultList;
	}
	
	//任务完成度统计   --截止日期时的任务统计
	public List<Map<String, Object>> jobFinishCountList(HttpServletRequest request){
		String retalerId = ResourceUtil.getRetailerId();
		List<Map<String,Object>> resultList =null;
		String end_time = request.getParameter("end_time");
		//店铺要做的任务数统计
		StringBuilder sql = new StringBuilder("select ts.id storeId,tjp.job_id jobId,sum(tjp.job_num) jobNum ");
		sql.append("from t_store ts left join t_job_param tjp on ts.job_model_id = tjp.model_id ");
		sql.append("left join t_job_model tjm on tjp.model_id = tjm.id WHERE ts.STATUS = 'A' ");
		sql.append("AND tjm.STATUS = 'A' AND tjp.STATUS = 'A' ");
		if(Utility.isNotEmpty(retalerId)){
			sql.append("and ts.retailer_id = '"+retalerId+"'");
		}
		if(Utility.isNotEmpty(end_time)){
			sql.append("and tjm.start_time <= '"+end_time+" 12:00:00'");
			sql.append("and tjm.end_time >= '"+end_time+" 12:00:00'");
		}
		sql.append(" group by ts.id,tjp.job_id  ORDER BY ts.id");
		resultList =this.findForJdbc(sql.toString());
		//店铺完成的任务数统计
		StringBuilder countSql = new StringBuilder("select tjdc.store_id storeId,tjp.id paramId,tjp.job_id jobId,sum(tjdc.pace) pace ");
		countSql.append("from t_store ts join t_job_model tjm on ts.job_model_id = tjm.id ");
		countSql.append("JOIN t_job_param tjp ON tjp.model_id = tjm.id ");
		countSql.append("join t_job_detail_count tjdc on tjp.id = tjdc.param_id and ts.id=tjdc.store_id ");
		countSql.append("where ts.STATUS = 'A' and tjp.status='A' and tjdc.status='A' and tjm.status='A' ");
		if(Utility.isNotEmpty(retalerId)){
			countSql.append("and ts.retailer_id = '"+retalerId+"'");
			countSql.append("and tjdc.retailer_id = '"+retalerId+"'");
		}
		if(Utility.isNotEmpty(end_time)){
			countSql.append("and tjm.start_time <= '"+end_time+" 12:00:00'");
			countSql.append("and tjm.end_time >= '"+end_time+" 12:00:00'");
			countSql.append("and tjdc.start_time <= '"+end_time+" 12:00:00'");
			countSql.append("and tjdc.end_time >= '"+end_time+" 12:00:00'");
		}
		countSql.append(" group by tjdc.store_id,tjp.job_id");
		List<Map<String,Object>> doesJobNumList =this.findForJdbc(countSql.toString());
		//获取使用G+的导购数
		List<Map<String,Object>> guideNumList = this.getGuideNumList(request,"g+"); 
		Map<String,String> storeGuideMap = new HashMap<String,String>();
		//将导购数按店铺存到map中
		for (Map<String, Object> map : guideNumList) {
			if(Utility.isNotEmpty(map.get("id"))){
				storeGuideMap.put(map.get("id").toString(), map.get("count").toString());
			}
		}

		Map<String, Map<String, Object>> totalMap = new HashMap<String, Map<String, Object>>();
		//遍历店铺的任务
		for (Map<String, Object> map : resultList) {
			String storeId = map.get("storeId").toString();
			String jobId = map.get("jobId").toString();
			int guideNum = Integer.parseInt(storeGuideMap.get(storeId).toString()); //店铺下的导购数
			double jobTotalNum = 0.0;  //店铺下的导购总共要完成的次数
			int doesjobNum = 0;  //店铺下的导购完成的次数
			if(Utility.isNotEmpty(jobId)){
				double jobNum = Double.parseDouble(map.get("jobNum").toString()); //任务要完成的次数
				jobTotalNum = jobNum*guideNum;
				map.put("jobTotalNum", jobTotalNum);
				for (Map<String, Object> jobMap : doesJobNumList) {
					String storeIdMap = jobMap.get("storeId").toString();
					String jobIdMap = jobMap.get("jobId").toString();
					if(storeId.equals(storeIdMap) && jobId.equals(jobIdMap)){
						if(Utility.isNotEmpty(jobMap.get("pace"))){
							doesjobNum = Integer.parseInt(jobMap.get("pace").toString()); //任务完成的次数
						}
					}
				}
				map.put("doesjobNum", doesjobNum);
				double percent = 0.0;
				if(jobTotalNum>0){
					percent = doesjobNum/jobTotalNum;
				}
				map.put("percent", df.format(percent*100)+"%");
				//合计统计
				Map<String, Object> jobMap = totalMap.get(jobId);
				if(Utility.isEmpty(jobMap)){
					jobMap = new HashMap<String, Object>();
					jobMap.put("jobId", jobId);
					jobMap.put("jobTotalNum", jobTotalNum);
					jobMap.put("doesjobNum", doesjobNum);
				}else{
					jobTotalNum = Double.parseDouble(jobMap.get("jobTotalNum").toString())+jobTotalNum;
					doesjobNum = Integer.parseInt(jobMap.get("doesjobNum").toString())+doesjobNum;
					jobMap.put("jobTotalNum", jobTotalNum);
					jobMap.put("doesjobNum", doesjobNum);
				}
				double percentTotal = 0.0;
				if(jobTotalNum>0){
					percentTotal = doesjobNum/jobTotalNum;
				}
				jobMap.put("percent", df.format(percentTotal*100)+"%");
				totalMap.put(jobId, jobMap);
			}else{  //没有任务组
			}
		}
		for (String key : totalMap.keySet()) {
			Map<String, Object> jobMap = totalMap.get(key);
			jobMap.put("storeId", "total");
			resultList.add(jobMap);
		}
		return resultList;
	}
}