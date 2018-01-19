package com.buss.count.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.count.service.GuideCountServiceI;
import com.buss.count.vo.GuideCountVo;
import com.buss.store.service.TStoreServiceI;
import com.buss.user.service.TPersonServiceI;
import com.buss.words.entity.TCustWordsEntity;

@Service("guideCountService")
@Transactional
public class GuideCountServiceImpl extends CommonServiceImpl implements GuideCountServiceI {

	@Autowired
	public TStoreServiceI tStoreService;
	
	@Autowired
	public TPersonServiceI tPersonService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((GuideCountVo)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((GuideCountVo)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((GuideCountVo)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(GuideCountVo t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(GuideCountVo t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(GuideCountVo t){
	 	return true;
 	}
 	/**
 	 * 获取初始化数据
 	 * @param request
 	 */
 	@Override
 	public void getInitData(HttpServletRequest request){
		StringBuilder storesSb = new StringBuilder();
		List<CommonVo> list = tStoreService.getStoreList();
		if(Utility.isNotEmpty(list)){
			for (CommonVo vo : list) {
				storesSb.append(",").append(vo.getName()).append("_").append(vo.getId());
			}
			storesSb.deleteCharAt(0);
		}
		StringBuilder storesSb1 = new StringBuilder();
		List<CommonVo> persons = tPersonService.getPersonList(TSUser.USER_TYPE_03);
		if(Utility.isNotEmpty(persons)){
			for (CommonVo vo : persons) {
				storesSb1.append(",").append(vo.getName()).append("_").append(vo.getId());
			}
			storesSb1.deleteCharAt(0);
		}
		request.setAttribute("stores", storesSb.toString());//店铺
		request.setAttribute("persons", storesSb1.toString());//导购
	}
 	
 	@Override
 	public void initFirstSearchTimeRange(HttpServletRequest request) {
		request.setAttribute("searchTime_begin", DateUtils.getFirstDayOfMonth(new Date()));
		request.setAttribute("searchTime_end", DateUtils.getDataString(DateUtils.date_sdf));
 	}
 	
 	
 	@Override
 	public String getNoticeCountSql(String retailerId,String storeId,String personId,String title,String startTime,String endTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT count(1)")
		.append(" FROM (SELECT")
		.append(" n.id notice_id,")
		.append(" p.user_Id")
		.append(" FROM")
		.append(" t_s_notice_store n ,t_person p")
		.append(" WHERE n.`status` = 'A'")
		.append(" AND p.`status`='A'")
		.append(" AND n.store_id='").append(retailerId).append("'")
		.append(" AND n.notice_level= '1'")//全体导购
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND n.notice_title like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id = '").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" union all ")
		.append(" SELECT ")
		.append(" n.id notice_id,")
		.append(" p.user_Id")
		.append(" FROM")
		.append(" t_s_notice_store n ,t_s_notice_store_authority_user a,t_person p")
		.append(" WHERE n.`status`='A'")
		.append(" AND p.`status`='A'")
		.append(" AND n.id = a.notice_id")
		.append(" AND p.user_Id = a.user_id")
		.append(" AND n.store_id='").append(retailerId).append("'")
		.append(" AND n.notice_level= '2'")//指定导购
		.append(" AND p.user_Id = a.user_id")
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND n.notice_title like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id = '").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" )t ")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" c.notice_id,")
		.append(" c.user_id,")
		.append(" SUM(c.click_num) total_click_num,")
		.append(" SUM(c.still_time) total_still_time,")
		.append(" MAX(c.click_time) max_click_time,")
		.append(" MIN(c.click_time) min_click_time")
		.append(" FROM")
		.append(" t_s_notice_store_count c")
		.append(" WHERE c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.notice_id ,c.user_id")
		.append(" ) t2")
		.append(" ON t.notice_id = t2.notice_id AND t.user_Id = t2.user_id ")
		.append(" where 1=1 ");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND t2.min_click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND t2.max_click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" AND (t2.total_click_num >0 or t2.total_still_time >0)");
		return sb.toString();
	}
	
	
	/**拼装统计sql
	 * @param noticeId
	 * @param retailerId
	 * @param noticeLevel 1全体导购，2指定导购
	 * @return
	 */
 	@Override
	public String getNoticeSql(String retailerId,String storeId,String personId,String title,String startTime,String endTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" t.notice_title title,")//公司通知标题
		.append(" ifnull(t.real_name,'') guideName,")//导购昵称
		.append(" t.id personId,")
		.append(" ifnull(s.id,'') storeId,")//店铺id
		.append(" ifnull(s.name,'') storeName,")//导购所属店铺名称
		.append(" ifnull(t2.total_click_num,0) totalClickNum,")//总点击数量 
		.append(" ifnull(t2.total_still_time,0) totalStillTime,")//总停留时间
		.append(" ifnull(t2.max_click_time,null) maxClickTime,")//最近点击时间
		.append(" ifnull(t2.min_click_time,null) minClickTime")//第一次点击时间
		.append(" FROM (SELECT")
		.append(" n.id notice_id,")
		.append(" n.notice_title ,")
		.append(" p.real_name,")
		.append(" p.id,")
		.append(" p.store_id,")//店铺id
		.append(" p.user_Id")
		.append(" FROM")
		.append(" t_s_notice_store n ,t_person p")
		.append(" WHERE n.`status` = 'A'")
		.append(" AND p.`status`='A'")
		.append(" AND n.store_id='").append(retailerId).append("'")
		.append(" AND n.notice_level= '1'")//全体导购
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND n.notice_title like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id = '").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" union all ")
		.append(" SELECT ")
		.append(" n.id notice_id,")
		.append(" n.notice_title ,")
		.append(" p.real_name,")
		.append(" p.id,")
		.append(" p.store_id,")//店铺id
		.append(" p.user_Id")
		.append(" FROM")
		.append(" t_s_notice_store n ,t_s_notice_store_authority_user a,t_person p")
		.append(" WHERE n.`status`='A'")
		.append(" AND p.`status`='A'")
		.append(" AND n.id = a.notice_id")
		.append(" AND p.user_Id = a.user_id")
		.append(" AND n.store_id='").append(retailerId).append("'")
		.append(" AND n.notice_level= '2'")//指定导购
		.append(" AND p.user_Id = a.user_id")
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND n.notice_title like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id = '").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" )t ")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" c.notice_id,")
		.append(" c.user_id,")
		.append(" SUM(c.click_num) total_click_num,")
		.append(" SUM(c.still_time) total_still_time,")
		.append(" MAX(c.click_time) max_click_time,")
		.append(" MIN(c.click_time) min_click_time")
		.append(" FROM")
		.append(" t_s_notice_store_count c")
		.append(" WHERE c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.notice_id ,c.user_id")
		.append(" ) t2")
		.append(" ON t.notice_id = t2.notice_id AND t.user_Id = t2.user_id ")
		.append(" LEFT JOIN t_store s ON t.store_id = s.id ")
		.append(" where 1=1 ");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND t2.min_click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND t2.max_click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" AND (t2.total_click_num >0 or t2.total_still_time >0)");
		sb.append(" ORDER BY  t.notice_id DESC,t.user_Id DESC ");
		return sb.toString();
	}
 	
	@Override
	public String getNewsCountSql(String retailerId,String storeId,String personId,String title,String startTime,String endTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT count(1)")
		.append(" FROM (SELECT")
		.append(" n.id news_id,")
		.append(" p.user_Id")
		.append(" FROM")
		.append(" t_news n ,t_person p")
		.append(" WHERE n.`status` = 'A'")
		.append(" AND p.`status`='A'")
		.append(" AND n.shopkeeper='").append(retailerId).append("'")
		.append(" AND n.upLoaded='Y'")
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND n.title like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id = '").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" )t ")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" c.news_id,")
		.append(" c.user_id,")
		.append(" SUM(c.click_num) total_click_num,")
		.append(" SUM(c.still_time) total_still_time,")
		.append(" MAX(c.click_time) max_click_time,")
		.append(" MIN(c.click_time) min_click_time")
		.append(" FROM")
		.append(" t_news_count c")
		.append(" WHERE c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.news_id ,c.user_id")
		.append(" ) t2")
		.append(" ON t.news_id = t2.news_id AND t.user_Id = t2.user_id ")
		.append(" where 1=1 ");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND t2.min_click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND t2.max_click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" AND (t2.total_click_num >0 or t2.total_still_time >0)");
		return sb.toString();
	}
	
	/**拼装统计sql
	 * @param noticeId
	 * @param retailerId
	 * @return
	 */
	@Override
	public String getNewsSql(String retailerId,String storeId,String personId,String title,String startTime,String endTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" t.title,")//资讯标题
		.append(" ifnull(t.real_name,'') guideName,")//导购昵称
		.append(" t.id personId,")
		.append(" ifnull(s.id,'') storeId,")//店铺id
		.append(" ifnull(s.name,'') storeName,")//导购所属店铺名称
		.append(" ifnull(t2.total_click_num,0) totalClickNum,")//总点击数量 
		.append(" ifnull(t2.total_still_time,0) totalStillTime,")//总停留时间
		.append(" ifnull(t2.max_click_time,null) maxClickTime,")//最近点击时间
		.append(" ifnull(t2.min_click_time,null) minClickTime")//第一次点击时间
		.append(" FROM (SELECT")
		.append(" n.id news_id,")
		.append(" n.title,")
		.append(" p.real_name,")
		.append(" p.id,")
		.append(" p.store_id,")//店铺id
		.append(" p.user_Id")
		.append(" FROM")
		.append(" t_news n ,t_person p")
		.append(" WHERE n.`status` = 'A'")
		.append(" AND p.`status`='A'")
		.append(" AND n.shopkeeper='").append(retailerId).append("'")
		.append(" AND n.upLoaded='Y'")
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND n.title like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id = '").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" ORDER BY n.id,p.real_name");
		sb.append(" )t ")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" c.news_id,")
		.append(" c.user_id,")
		.append(" SUM(c.click_num) total_click_num,")
		.append(" SUM(c.still_time) total_still_time,")
		.append(" MAX(c.click_time) max_click_time,")
		.append(" MIN(c.click_time) min_click_time")
		.append(" FROM")
		.append(" t_news_count c")
		.append(" WHERE c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.news_id ,c.user_id")
		.append(" ) t2")
		.append(" ON t.news_id = t2.news_id AND t.user_Id = t2.user_id ")
		.append(" LEFT JOIN t_store s ON t.store_id = s.id ")
		.append(" where 1=1 ");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND t2.min_click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND t2.max_click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" AND (t2.total_click_num >0 or t2.total_still_time >0)");
		sb.append(" ORDER BY  t.news_id DESC,t.user_Id DESC ");
		
		return sb.toString();
	}
 	
	/**拼装统计sql
	 * @param noticeId
	 * @param retailerId
	 * @return
	 */
	@Override
	public String getNewsTotalSql(HttpServletRequest request,DataGrid dataGrid) {
		String retailerId = ResourceUtil.getRetailerId();
		String title = request.getParameter("title");//资讯标题
		String newsType = request.getParameter("newsType");//资讯分类
		String startTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(Utility.getCurrentTimestamp());
			endTime = DateUtils.getDate("yyyy-MM-dd");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" IFNULL(t.collect_num,0) collectNum,")
		.append(" IFNULL(t2.comment_num,0) commentNum,")
		.append(" IFNULL(t3.qq_push_num,0) qqPushNum,")
		.append(" IFNULL(t3.wx_push_num,0) wxPushNum,")
		.append(" IFNULL(t3.wcm_push_num,0) wcmPushNum,")
		.append(" IFNULL(t3.cont_push_num,0) contPushNum,")
		.append(" IFNULL(t3.total_push_num,0) totalPushNum,")
		.append(" IFNULL(t4.click_num,0) clickNum,")
		.append(" IFNULL(t1.good_num,0) goodNum,")
		.append(" IFNULL(t1.no_sense_num,0) noSenseNum,")
		.append(" n.title,")
		.append(" n.news_type newsType")
		.append(" FROM t_news n")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" c.news_id,")
		.append(" count(c.news_id) collect_num")
		.append(" FROM")
		.append(" t_news_collect c")
		.append(" WHERE c.`status` = 'A'")
		.append(" AND c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.collect_Time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.collect_Time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.news_id ")
		.append(" )t ON n.id = t.news_id")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" f.NID,")
		.append(" sum(case f.feel WHEN 1 THEN 1 ELSE 0 end) good_num,")
		.append(" sum(case f.feel WHEN 0 THEN 1 ELSE 0 end) no_sense_num")
		.append(" FROM")
		.append(" t_news_feel f")
		.append(" WHERE f.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND f.time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND f.time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY f.NID ")
		.append(" )t1 ON n.id = t1.NID")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" c.NID,")
		.append(" count(c.NID) comment_num")
		.append(" FROM")
		.append(" t_news_comment c")
		.append(" WHERE c.`status` = 'A'")
		.append(" AND c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.NID")
		.append(" )t2 ON n.id = t2.NID")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" sum(CASE p.push_type WHEN 'QQ' THEN 1 ELSE 0 END) qq_push_num,")
		.append(" sum(CASE p.push_type WHEN 'weChart' THEN 1 ELSE 0 END) wx_push_num,")
		.append(" sum(CASE p.push_type WHEN 'contact' THEN 1 ELSE 0 END) cont_push_num,")
		.append(" sum(CASE p.push_type WHEN 'weChartM' THEN 1 ELSE 0 END) wcm_push_num,")// -- 朋友圈
		.append(" count(1) total_push_num,")
		.append(" p.news_id")
		.append(" FROM")
		.append(" 	t_news_push_count p")
		.append(" WHERE p.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND p.push_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND p.push_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY p.news_id")
		.append(" )t3 ON n.id = t3.news_id")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" c.news_id,")
		.append(" count(1) click_num")
		.append(" FROM")
		.append(" t_news_count c")
		.append(" WHERE c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.news_id")
		.append(" )t4 ON n.id = t4.news_id")
		.append(" WHERE n.`status`='A'")
		.append(" AND n.shopkeeper = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND n.title like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(newsType)){
			sb.append(" AND concat(',',n.news_type,',') like '%,").append(newsType).append(",%'");
		}
		sb.append(" AND ( ")
		.append(" t.collect_num > 0 ")
		.append(" or t2.comment_num >0 ")
		.append(" or t3.qq_push_num > 0")
		.append(" or t3.wx_push_num > 0")
		.append(" or t3.wcm_push_num > 0")
		.append(" or t3.cont_push_num > 0")
		.append(" or t4.click_num > 0")
		.append(" or t1.good_num > 0")
		.append(" or t1.no_sense_num > 0")
		.append(" )");
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
//			sb.append(" ORDER BY t.totalClickNum desc");
		}else{
			sb.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		return sb.toString();
	}
 	
	/**拼装统计sql
	 * @param noticeId
	 * @param retailerId
	 * @return
	 */
	@Override
	public String getNewsTotalCountSql(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		String title = request.getParameter("title");//资讯标题
		String newsType = request.getParameter("newsType");//资讯分类
		String startTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(Utility.getCurrentTimestamp());
			endTime = DateUtils.getDate("yyyy-MM-dd");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT count(1)")
		.append(" FROM t_news n")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" c.news_id,")
		.append(" count(c.news_id) collect_num")
		.append(" FROM")
		.append(" t_news_collect c")
		.append(" WHERE c.`status` = 'A'")
		.append(" AND c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.collect_Time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.collect_Time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.news_id ")
		.append(" )t ON n.id = t.news_id")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" f.NID,")
		.append(" sum(case f.feel WHEN 1 THEN 1 ELSE 0 end) good_num,")
		.append(" sum(case f.feel WHEN 0 THEN 1 ELSE 0 end) no_sense_num")
		.append(" FROM")
		.append(" t_news_feel f")
		.append(" WHERE f.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND f.time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND f.time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY f.NID ")
		.append(" )t1 ON n.id = t1.NID")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" c.NID,")
		.append(" count(c.NID) comment_num")
		.append(" FROM")
		.append(" t_news_comment c")
		.append(" WHERE c.`status` = 'A'")
		.append(" AND c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.NID")
		.append(" )t2 ON n.id = t2.NID")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" sum(CASE p.push_type WHEN 'QQ' THEN 1 ELSE 0 END) qq_push_num,")
		.append(" sum(CASE p.push_type WHEN 'weChart' THEN 1 ELSE 0 END) wx_push_num,")
		.append(" sum(CASE p.push_type WHEN 'contact' THEN 1 ELSE 0 END) cont_push_num,")
		.append(" sum(CASE p.push_type WHEN 'weChartM' THEN 1 ELSE 0 END) wcm_push_num,")// -- 朋友圈
		.append(" count(1) total_push_num,")
		.append(" p.news_id")
		.append(" FROM")
		.append(" 	t_news_push_count p")
		.append(" WHERE p.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND p.push_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND p.push_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY p.news_id")
		.append(" )t3 ON n.id = t3.news_id")
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" c.news_id,")
		.append(" count(1) click_num")
		.append(" FROM")
		.append(" t_news_count c")
		.append(" WHERE c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.news_id")
		.append(" )t4 ON n.id = t4.news_id")
		.append(" WHERE n.`status`='A'")
		.append(" AND n.shopkeeper = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND n.title like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(newsType)){
			sb.append(" AND concat(',',n.news_type,',') like '%,").append(newsType).append(",%'");
		}
		sb.append(" AND ( ")
		.append(" t.collect_num > 0 ")
		.append(" or t2.comment_num >0 ")
		.append(" or t3.qq_push_num > 0")
		.append(" or t3.wx_push_num > 0")
		.append(" or t3.wcm_push_num > 0")
		.append(" or t3.cont_push_num > 0")
		.append(" or t4.click_num > 0")
		.append(" or t1.good_num > 0")
		.append(" or t1.no_sense_num > 0")
		.append(" )");
		return sb.toString();
	}
	
	@Override
	public String getGoodsCountSql(String retailerId,String storeId,String personId,String code,String goodsName,String brandName,String startTime,String endTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT count(1)")
		.append(" FROM (SELECT ")
		.append(" g.id goods_id,")
		.append(" p.user_Id")
		.append(" FROM")
		.append(" t_goods g,t_person p")
		.append(" WHERE g.`status` = 'A'")
		.append(" AND p.`status`='A'")
		.append(" AND g.retailer_id='").append(retailerId).append("'")
		.append(" AND g.goods_status='4'")
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(code)){
			sb.append(" AND  g.`code` like '%").append(code).append("%'");
		}
		if(Utility.isNotEmpty(goodsName)){
			sb.append(" AND  g.goods_name like '%").append(goodsName).append("%'");
		}
		if(Utility.isNotEmpty(brandName)){
			sb.append(" AND  g.brand_name like '%").append(brandName).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id = '").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" )t ")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" c.goods_id,")
		.append(" c.user_id,")
		.append(" MAX(c.click_time) max_click_time,")
		.append(" MIN(c.click_time) min_click_time")
		.append(" FROM")
		.append(" t_goods_count c")
		.append(" WHERE c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.goods_id ,c.user_id")
		.append(" ) t2")
		.append(" ON t.goods_id = t2.goods_id AND t.user_Id = t2.user_id ")
		.append(" where 1=1 ");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND t2.min_click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND t2.max_click_time <='").append(endTime).append(" 23:59:59'");
		}
		return sb.toString();
	}
	
	/**拼装统计sql
	 * @param noticeId
	 * @param retailerId
	 * @param noticeLevel 1全体导购，2指定导购
	 * @return
	 */
	@Override
	public String getGoodsSql(String retailerId,String storeId,String personId,String code,String startTime,String endTime,String goodsName,String brandName) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" t.goods_name goodsName,")
		.append(" t.goods_code code,")
		.append(" t.brand_name brandName,")
		.append(" t.top_category_id topCategoryId,")
		.append(" t.sub_category_id subCategoryId ,")
		.append(" t.thrid_category_id thridCategoryId,")
		.append(" ifnull(t.real_name,'') guideName,")//导购昵称
		.append(" t.id personId,")
		.append(" ifnull(s.id,'') storeId,")//店铺id
		.append(" ifnull(s.name,'') storeName,")//导购所属店铺名称
		.append(" ifnull(t2.total_click_num,0) totalClickNum,")//总点击数量 
		.append(" ifnull(t2.total_still_time,0) totalStillTime,")//总停留时间
		.append(" ifnull(t2.max_click_time,null) maxClickTime,")//最近点击时间
		.append(" ifnull(t2.min_click_time,null) minClickTime")//第一次点击时间
		.append(" FROM (SELECT ")
		.append(" g.id goods_id,")
		.append(" g.goods_code,")
		.append(" g.goods_name,")
		.append(" g.brand_name,")
		.append(" g.top_category_id,")
		.append(" g.sub_category_id,")
		.append(" g.thrid_category_id,")
		.append(" p.real_name,")
		.append(" p.id,")
		.append(" p.user_Id,")
		.append(" p.store_id")
		.append(" FROM")
		.append(" t_goods g,t_person p")
		.append(" WHERE g.`status` = 'A'")
		.append(" AND p.`status`='A'")
		.append(" AND g.retailer_id='").append(retailerId).append("'")
		.append(" AND g.goods_status='4'")
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(code)){
			sb.append(" AND  g.goods_code like '%").append(code).append("%'");
		}
		if(Utility.isNotEmpty(goodsName)){
			sb.append(" AND  g.goods_name like '%").append(goodsName).append("%'");
		}
		if(Utility.isNotEmpty(brandName)){
			sb.append(" AND  g.brand_name like '%").append(brandName).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id = '").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" ORDER BY g.id,p.real_name");
		sb.append(" )t ")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" c.goods_id,")
		.append(" c.user_id,")
		.append(" SUM(c.click_num) total_click_num,")
		.append(" SUM(c.still_time) total_still_time,")
		.append(" MAX(c.click_time) max_click_time,")
		.append(" MIN(c.click_time) min_click_time")
		.append(" FROM")
		.append(" t_goods_count c")
		.append(" WHERE c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.goods_id ,c.user_id")
		.append(" ) t2")
		.append(" ON t.goods_id = t2.goods_id AND t.user_Id = t2.user_id ")
		.append(" LEFT JOIN t_store s ON t.store_id = s.id ")
		.append(" where 1=1 ");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND t2.min_click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND t2.max_click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" AND ( ")
		.append(" t2.total_click_num > 0 ")
		.append(" or t2.total_still_time > 0")
		.append(" )");
		sb.append(" ORDER BY  t.goods_id DESC,t.user_Id DESC ");
		
		return sb.toString();
	}
 	
	@Override
	public String getActivityCountSql(String retailerId,String storeId,String personId,String title,String startTime,String endTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT count(1)")
		.append(" FROM (SELECT")
		.append(" a.id news_id,")
		.append(" p.user_Id")
		.append(" FROM")
		.append(" t_fin_activity a ,t_person p")
		.append(" WHERE a.`status` = 'A'")
		.append(" AND p.`status`='A'")
		.append(" AND a.retailer_id='").append(retailerId).append("'")
		.append(" AND a.activity_status = '2'")
		.append(" AND a.to_user_type = '1'")
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND a.activity_name like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id ='").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" )t ")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" c.activity_id,")
		.append(" c.user_id,")
		.append(" SUM(c.click_num) total_click_num,")
		.append(" SUM(c.still_time) total_still_time,")
		.append(" MAX(c.click_time) max_click_time,")
		.append(" MIN(c.click_time) min_click_time")
		.append(" FROM")
		.append(" t_activity_count c")
		.append(" WHERE c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.activity_id ,c.user_id")
		.append(" ) t2")
		.append(" ON t.news_id = t2.activity_id AND t.user_Id = t2.user_id ")
		.append(" where 1=1 ");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND t2.min_click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND t2.max_click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" AND (t2.total_click_num >0 or t2.total_still_time>0)");
		return sb.toString();
	}
	
	
	/**拼装统计sql
	 * @param noticeId
	 * @param retailerId
	 * @param noticeLevel 1全体导购，2指定导购
	 * @return
	 */
	@Override
	public String getActivitySql(String retailerId,String storeId,String personId,String title,String startTime,String endTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" t.activity_name title,")//活动标题
		.append(" ifnull(t.real_name,'') guideName,")//导购昵称
		.append(" t.id personId,")
		.append(" ifnull(s.id,'') storeId,")//店铺id
		.append(" ifnull(s.name,'') storeName,")//导购所属店铺名称
		.append(" ifnull(t2.total_click_num,0) totalClickNum,")//总点击数量 
		.append(" ifnull(t2.total_still_time,0) totalStillTime,")//总停留时间
		.append(" ifnull(t2.max_click_time,null) maxClickTime,")//最近点击时间
		.append(" ifnull(t2.min_click_time,null) minClickTime")//第一次点击时间
		.append(" FROM (SELECT")
		.append(" a.id news_id,")
		.append(" a.activity_name ,")
		.append(" p.real_name,")
		.append(" p.id,")
		.append(" p.store_id,")//店铺id
		.append(" p.user_Id")
		.append(" FROM")
		.append(" t_fin_activity a ,t_person p")
		.append(" WHERE a.`status` = 'A'")
		.append(" AND p.`status`='A'")
		.append(" AND a.retailer_id='").append(retailerId).append("'")
		.append(" AND a.activity_status = '2'")
		.append(" AND a.to_user_type = '1'")
		.append(" AND p.to_retailer_id='").append(retailerId).append("'")
		.append(" AND p.user_type='03' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND a.activity_name like '%").append(title).append("%'");
		}
		if(Utility.isNotEmpty(personId)){
			sb.append(" AND p.id ='").append(personId).append("'");
		}
		if(Utility.isNotEmpty(storeId)){
			sb.append(" AND p.store_id='").append(storeId).append("'");//店铺
		}
		sb.append(" ORDER BY a.id,p.real_name");
		sb.append(" )t ")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" c.activity_id,")
		.append(" c.user_id,")
		.append(" SUM(c.click_num) total_click_num,")
		.append(" SUM(c.still_time) total_still_time,")
		.append(" MAX(c.click_time) max_click_time,")
		.append(" MIN(c.click_time) min_click_time")
		.append(" FROM")
		.append(" t_activity_count c")
		.append(" WHERE c.retailer_id='").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.activity_id ,c.user_id")
		.append(" ) t2")
		.append(" ON t.news_id = t2.activity_id AND t.user_Id = t2.user_id ")
		.append(" LEFT JOIN t_store s ON t.store_id = s.id ")
		.append(" where 1=1 ");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND t2.min_click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND t2.max_click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" AND (t2.total_click_num >0 or t2.total_still_time>0)");
		sb.append(" ORDER BY t.news_id DESC,t.user_Id DESC ");
		return sb.toString();
	}
 	/**
 	 * 商品汇总统计
 	 */
	@Override
	public String getGoodsTotalSql(HttpServletRequest request,DataGrid dataGrid){
		String retailerId = ResourceUtil.getRetailerId();
		String code = request.getParameter("code");//商品编码
		String brandName = request.getParameter("brandName");
		String topCategoryId = request.getParameter("topCategoryId");
		String subCategoryId = request.getParameter("subCategoryId");
		String startTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(Utility.getCurrentTimestamp());
			endTime = DateUtils.getDate("yyyy-MM-dd");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT")
		.append(" g.id goodsId ,")
		.append(" g.goods_code code,")
		.append(" g.goods_name goodsName,")
		.append(" g.brand_name brandName,")
		.append(" g.top_category_id topCategoryId,")
		.append(" g.sub_category_id subCategoryId,")
		.append(" g.thrid_category_id thridCategoryId,")
		.append(" ifNull(t.total_click_num,0) totalClickNum,")
		.append(" ifNull(t2.total_push_num,0) totalPushNum,")
		.append(" ifNull(t3.total_publish_num,0) totalPublishNum,")
		.append(" ifNull(t4.total_cart_num,0) totalCartNum")
		.append(" FROM")
		.append(" t_goods g") 
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" count(c.click_num) total_click_num,")
		.append(" c.goods_id")
		.append(" FROM")
		.append(" t_goods_count c")
		.append(" WHERE c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.goods_id")
		.append(" )t ON g.id = t.goods_id")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" count(p.push_num) total_push_num,")
		.append(" p.goods_id")
		.append(" FROM")
		.append(" t_goods_push_count p")
		.append(" WHERE  p.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND p.push_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND p.push_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY p.goods_id")
		.append(" )t2 ON g.id = t2.goods_id")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" count(1) total_publish_num,")
		.append(" r.goods_id")
		.append(" FROM")
		.append(" t_guide_recommend_info r")
		.append(" WHERE r.`status` = 'A' ")
		.append(" and	 r.retailer_id = '").append(retailerId).append("'")
		.append(" AND r.guide_id IS NOT NULL");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND r.create_date >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND r.create_date <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY r.goods_id")
		.append(" )t3 ON g.id = t3.goods_id")
		.append(" LEFT JOIN ")
		.append(" 	(")
		.append(" SELECT")
		.append(" count(1) total_cart_num,")
		.append(" c.goods_id")
		.append(" FROM")
		.append(" 	t_goods_cart c")
		.append(" WHERE c.`status` = 'A' ")
		.append(" and c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.add_Time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.add_Time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.goods_id")
		.append(" )t4 ON g.id = t4.goods_id")
		.append(" WHERE g.`status`='A'")
		.append(" AND g.retailer_id='").append(retailerId).append("'");
//		.append(" AND g.goods_status='4'");
		if(Utility.isNotEmpty(code)){
			sb.append(" AND g.goods_code like '%").append(code).append("%'");
		}
		if(Utility.isNotEmpty(brandName)){
			sb.append(" AND g.brand_name like '%").append(brandName).append("%'");
		}
		if(Utility.isNotEmpty(topCategoryId)){
			sb.append(" AND g.top_category_id = '").append(topCategoryId).append("'");
		}
		if(Utility.isNotEmpty(subCategoryId)){
			sb.append(" AND g.sub_category_id = '").append(subCategoryId).append("'");
		}
		sb.append(" AND ( ")
		.append(" t.total_click_num > 0 ")
		.append(" or t2.total_push_num >0 ")
		.append(" or t3.total_publish_num > 0")
		.append(" or t4.total_cart_num > 0")
		.append(" )");
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
//			sb.append(" ORDER BY t.totalClickNum desc");
		}else{
			sb.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		return sb.toString();
	}
	
	/**
	 * 商品汇总统计
	 */
	@Override
	public String getGoodsTotalCountSql(HttpServletRequest request){
		String retailerId = ResourceUtil.getRetailerId();
		String code = request.getParameter("code");//商品编码
		String brandName = request.getParameter("brandName");
		String topCategoryId = request.getParameter("topCategoryId");
		String subCategoryId = request.getParameter("subCategoryId");
		String startTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(Utility.getCurrentTimestamp());
			endTime = DateUtils.getDate("yyyy-MM-dd");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT count(1)")
		.append(" FROM")
		.append(" t_goods g") 
		.append(" LEFT JOIN (")
		.append(" SELECT")
		.append(" count(c.click_num) total_click_num,")
		.append(" c.goods_id")
		.append(" FROM")
		.append(" t_goods_count c")
		.append(" WHERE c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.goods_id")
		.append(" )t ON g.id = t.goods_id")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" count(p.push_num) total_push_num,")
		.append(" p.goods_id")
		.append(" FROM")
		.append(" t_goods_push_count p")
		.append(" WHERE  p.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND p.push_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND p.push_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY p.goods_id")
		.append(" )t2 ON g.id = t2.goods_id")
		.append(" LEFT JOIN ")
		.append(" (")
		.append(" SELECT")
		.append(" count(1) total_publish_num,")
		.append(" r.goods_id")
		.append(" FROM")
		.append(" t_guide_recommend_info r")
		.append(" WHERE r.`status` = 'A' ")
		.append(" and	 r.retailer_id = '").append(retailerId).append("'")
		.append(" AND r.guide_id IS NOT NULL");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND r.create_date >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND r.create_date <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY r.goods_id")
		.append(" )t3 ON g.id = t3.goods_id")
		.append(" LEFT JOIN ")
		.append(" 	(")
		.append(" SELECT")
		.append(" count(1) total_cart_num,")
		.append(" c.goods_id")
		.append(" FROM")
		.append(" 	t_goods_cart c")
		.append(" WHERE c.`status` = 'A' ")
		.append(" and c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.add_Time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.add_Time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY c.goods_id")
		.append(" )t4 ON g.id = t4.goods_id")
		.append(" WHERE g.`status`='A'")
		.append(" AND g.retailer_id='").append(retailerId).append("'")
		.append(" AND g.goods_status='4'");
		if(Utility.isNotEmpty(code)){
			sb.append(" AND g.code like '%").append(code).append("%'");
		}
		if(Utility.isNotEmpty(brandName)){
			sb.append(" AND g.brand_name like '%").append(brandName).append("%'");
		}
		if(Utility.isNotEmpty(topCategoryId)){
			sb.append(" AND g.top_category_id = '").append(topCategoryId).append("'");
		}
		if(Utility.isNotEmpty(subCategoryId)){
			sb.append(" AND g.sub_category_id = '").append(subCategoryId).append("'");
		}
		sb.append(" AND ( ")
		.append(" t.total_click_num > 0 ")
		.append(" or t2.total_push_num >0 ")
		.append(" or t3.total_publish_num > 0")
		.append(" or t4.total_cart_num > 0")
		.append(" )");
		return sb.toString();
	}
	
	
	/**拼装统计sql
	 * @param retailerId 零售商ID
	 * @param startTime  活动开始时间
	 * @param endTime	活动结束时间
	 * @param title 	活动标题
	 * @return
	 */
//	@Override
//	public String getActivityTotalSql(String retailerId, String startTime, String endTime, String title) {
//		//取到查询条件的字符串
//		String sqlRetailerId = " and retailer_id= '"+ retailerId +"' ";
//		String sqlTital = " and title like '%"+ title +"%' ";
//		String sqlStartTime = " and end_time >= '"+ startTime +" 00:00:00' ";
//		String sqlEndTime = " and start_time <= '"+ endTime +" 23:59:59' ";
//		
//		StringBuffer sb = new StringBuffer();
//		sb.append("select id,  title, clickNum, clickTime, start_time startTime, end_time endTime FROM ( ");
//		sb.append("select  activity_id, title, SUM(click_num) clickNum, click_time clickTime from t_activity_count ");
//		sb.append("where 1=1 ");
//		if(!Utility.isEmpty(retailerId)){
//			sb.append(sqlRetailerId);
//		}
//		if(!Utility.isEmpty(title)){
//			sb.append(sqlTital);
//		}
//		sb.append("group by activity_id ");
//		sb.append(") tac left join t_fin_activity tfa on tac.activity_id = tfa.id ");
//		sb.append("where 1=1 ");
//		if(!Utility.isEmpty(startTime) && !"null".equals(startTime)){
//			sb.append(sqlStartTime);
//		}
//		if(!Utility.isEmpty(endTime) && !"null".equals(endTime)){
//			sb.append(sqlEndTime);
//		}
//		
//		return sb.toString();
//	}
 	
	//获取活动点击数据
	@Override
	public String getActivityTotalSql(HttpServletRequest request,DataGrid dataGrid){
		String title = request.getParameter("title");// 活动标题
		String retailerId = ResourceUtil.getRetailerId();
		String startTime = request.getParameter("searchTime_begin");// 开始时间
		String endTime = request.getParameter("searchTime_end");// 结束时间
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(Utility.getCurrentTimestamp());
			endTime = DateUtils.getDate("yyyy-MM-dd");
		}
		StringBuffer sb = new StringBuffer();
		/*sb.append(" SELECT ")
		.append(" a.id , ")
		.append(" a.title title, ")
		.append(" ifNull(t.total_click_num,0) totalClickNum")
		.append(" FROM ")
		.append(" t_goods_act a  ")
		.append(" LEFT JOIN ")
		.append(" ( ")
		.append(" SELECT ")
		.append(" count(c.click_num) total_click_num, ")
		.append(" c.activity_id ")
		.append(" FROM ")
		.append(" t_activity_count c ")
		.append(" WHERE c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY activity_id ")
		.append(" )t ON a.id = t.activity_id ")
		.append(" WHERE a.`status` = 'A' ")
		.append(" AND a.retailer_id='").append(retailerId).append("'")
		.append(" AND a.audit_status = '2' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND a.title like '%").append(title).append("%'");
		}
		sb.append(" AND t.total_click_num > 0");
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
//			sb.append(" ORDER BY t.totalClickNum desc");
		}else{
			sb.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}*/
		
		sb.append("select tga.id,tga.title title,count(tscc.id) totalClickNum ")
		.append("from t_goods_act tga left join t_share_click_count tscc on tga.id=tscc.activity_id AND tscc.share_type='3' ");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND tscc.click_time >='").append(startTime).append(" 00:00:00' ");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND tscc.click_time <='").append(endTime).append(" 23:59:59' ");
		}
		sb.append("where tga.status = 'A' AND tga.retailer_id='").append(retailerId).append("' ")
		.append("AND tga.audit_status = '2' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND tga.title like '%").append(title).append("%' ");
		}
		sb.append("group by tga.id ");
		return sb.toString();
	}

	@Override
	public String getActivityTotalCountSql(HttpServletRequest request){
		String title = request.getParameter("title");// 活动标题
		String retailerId = ResourceUtil.getRetailerId();
		String startTime = request.getParameter("searchTime_begin");// 开始时间
		String endTime = request.getParameter("searchTime_end");// 结束时间
		String notInitSearch = request.getParameter("notInitSearch");//是否初始化的查询（为null则按默认条件查询）
		if(notInitSearch==null){//第一次加载页面按默认条件查询（默认为当月第一天和当天）
			startTime = DateUtils.getFirstDayOfMonth(Utility.getCurrentTimestamp());
			endTime = DateUtils.getDate("yyyy-MM-dd");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT count(1)")
		.append(" FROM ")
		.append(" t_fin_activity a  ")
		.append(" LEFT JOIN ")
		.append(" ( ")
		.append(" SELECT ")
		.append(" count(c.click_num) total_click_num, ")
		.append(" c.activity_id ")
		.append(" FROM ")
		.append(" t_activity_count c ")
		.append(" WHERE c.retailer_id = '").append(retailerId).append("'");
		if(Utility.isNotEmpty(startTime)){
			sb.append(" AND c.click_time >='").append(startTime).append(" 00:00:00'");
		}
		if(Utility.isNotEmpty(endTime)){
			sb.append(" AND c.click_time <='").append(endTime).append(" 23:59:59'");
		}
		sb.append(" GROUP BY activity_id ")
		.append(" )t ON a.id = t.activity_id ")
		.append(" WHERE a.`status` = 'A' ")
		.append(" AND a.retailer_id='").append(retailerId).append("'")
		.append(" AND a.activity_status = '2' ")
		.append(" AND a.to_user_type = '1' ");
		if(Utility.isNotEmpty(title)){
			sb.append(" AND a.activity_name like '%").append(title).append("%'");
		}
		sb.append(" AND t.total_click_num > 0");
		return sb.toString();
	}
 	

	public void getGuideGoldCountSql(HttpServletRequest request, DataGrid dataGrid){
		String guideName = request.getParameter("guideName");
		String phoneNo = request.getParameter("phoneNo");
		String createDate_begin = request.getParameter("createDate_begin");
		String createDate_end = request.getParameter("createDate_end");
		String storeId = request.getParameter("storeId");
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isEmpty(createDate_begin)){
			createDate_begin = DateUtils.getFirstDayOfMonth(new Date());
		}
		if(Utility.isEmpty(createDate_end)){
			createDate_end = DateUtils.dateToString(new Date());
		}
		
		StringBuffer sqlCount = new StringBuffer("select count(1) count from t_s_user tsu LEFT JOIN ");
		sqlCount.append("t_person tp on tp.user_Id=tsu.id WHERE tsu.STATUS = 'A' and tsu.user_status = '1' AND tsu.user_type = '03' ");
		StringBuffer sql = new StringBuffer("SELECT tsu.id id,ifnull(tdad.goldNum,0) goldNum,tsu.realname guideName,tsu.mobilePhone phoneNo, ");
		sql.append("tp.store_id storeId,ifnull(tdad.job_date,now()) createDate,ifnull(tgh.count,0) holiday ");
		sql.append("FROM t_s_user tsu LEFT JOIN t_person tp on tp.user_Id=tsu.id LEFT JOIN (");
		sql.append("select user_id,job_date,sum(gold_num) goldNum  from t_day_award_detail where 1=1 ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and retailer_id ='"+retailerId+"' ");
			sqlCount.append("and retailer_id ='"+retailerId+"' ");
		}
		sql.append("AND create_date >= '"+createDate_begin+" 00:00:00' ");
		sql.append("AND create_date <= '"+createDate_end+" 23:59:59' ");
		sql.append("group by user_id ");
		sql.append(") tdad ON tdad.user_id = tsu.id LEFT JOIN (");
		sql.append("select guide_id,count(1) count from t_guide_holiday where 1=1 ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and retailer_id ='"+retailerId+"' ");
		}
		sql.append("AND holiday >= '"+createDate_begin+" 00:00:00' ");
		sql.append("AND holiday <= '"+createDate_end+" 23:59:59' ");
		sql.append("group by guide_id ");
		sql.append(") tgh ON tgh.guide_id = tsu.id WHERE ");
		sql.append("tsu.STATUS = 'A' and tsu.user_status = '1' ");
		sql.append("AND tsu.user_type = '03'");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and tsu.retailer_id ='"+retailerId+"' ");
		}
		if(Utility.isNotEmpty(guideName)){
			sql.append("and tsu.realname like '%"+guideName+"%' ");
			sqlCount.append("and tsu.realname like '%"+guideName+"%' ");
		}
		if(Utility.isNotEmpty(phoneNo)){
			sql.append("and tsu.mobilePhone like '%"+phoneNo+"%' ");
			sqlCount.append("and tsu.mobilePhone like '%"+phoneNo+"%' ");
		}
		if(Utility.isNotEmpty(storeId)){
			sql.append("and tp.store_id ='"+storeId+"' ");
			sqlCount.append("and tp.store_id ='"+storeId+"' ");
		}
		sql.append(" GROUP BY tsu.id");
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
			sql.append(" ORDER BY goldNum desc");
		}else{
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		
		List<Map<String,Object>> list = null;
		int total = this.getCountForJdbc(sqlCount.toString()).intValue();
		if(total > 0){
			list = this.findForJdbc(sql.toString(), dataGrid.getPage(),dataGrid.getRows());
		}else{
			list = new ArrayList<Map<String,Object>>();
		}
		dataGrid.setResults(list);
		dataGrid.setTotal(total);
	}
}