package com.buss.count.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.event.StoreEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TStoreSNoticeCount;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.buss.count.vo.CustInfoCompleteVo;
import com.buss.count.vo.GuideCountVo;
import com.buss.store.entity.TStoreEntity;
import com.buss.store.service.TStoreServiceI;
import com.buss.user.entity.TCustInfoCompleteEntity;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 导购统计
 * @author onlineGenerator
 * @date 2016-04-09 12:14:42
 * @version V1.0   
 *
 */
/**
 * @author lenovo
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/guideCountController")
public class GuideCountController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GuideCountController.class);
	
	@Autowired
	private TStoreServiceI tStoreService;
	
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
	 * 导购统计 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {

		String storeStr = "";
		List<CommonVo> storeList = tStoreService.getStoreList();
		for (CommonVo commonVo : storeList) {
			storeStr = storeStr + commonVo.getName() + "_" + commonVo.getId() + ",";
		}
		storeStr += " _null";
		/*if(storeStr.length() > 1){
			storeStr = storeStr.substring(0, storeStr.length()-1);
		}*/
		request.setAttribute("stores", storeStr);
		return new ModelAndView("com/buss/count/guideCountList");
	}

	/**
	 * 顾客资料完成列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tCustInfoCompleteList")
	public ModelAndView tCustInfoComplete(HttpServletRequest request) {
		request.setAttribute("rId", ResourceUtil.getRetailerId());
		return new ModelAndView("com/buss/count/tCustInfoCompleteList");
	}
	
	/**
	 * easyui 导购统计
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(GuideCountVo vo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
			Map<String,String> slqMap = this.getGuideSqlMap(request);
			String sql = slqMap.get("sql");		//查询表格分页数据
			String sqlCount = slqMap.get("sqlCount");	//查询总条数
			String sqlSum = slqMap.get("sqlSum");	//查询底部统计数据
			String sort = dataGrid.getSort();
			if(StringUtil.isEmpty(sort)){
				sql += " order by dealNum desc";
			}else{
				//sql += " order by "+dataGrid.getSort()+" desc";
				//ZDF  +  ",dealNum  desc"
				sql += " order by "+dataGrid.getSort()+ " " +dataGrid.getOrder();
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
//				String countSql = this.getCountSql(request);
				total = this.systemService.getCountForJdbc(sqlCount).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
			Map<String, Object>  map  = this.systemService.findOneForJdbc(sqlSum.toString(), null);
			if(null != map && map.size()>0){
				dataGrid.setFooter("storeId:合计,appClick:"+(map.get("appClickSum")==null ? 0 :map.get("appClickSum"))+",goodsClick:"+(map.get("goodsClickSum")==null ? 0 :map.get("goodsClickSum"))
						+",goodsPushNum:"+(map.get("goodsPushSum")==null ? 0 :map.get("goodsPushSum"))+",orderPushNum:"+(map.get("orderPushSum")==null ? 0 :map.get("orderPushSum"))
						+",newClickNum:"+(map.get("newClickSum")==null ? 0 :map.get("newClickSum"))+",newsPushNum:"+(map.get("newsPushSum")==null ? 0 :map.get("newsPushSum"))
						+",toBePayNum:"+(map.get("toBePaySum")==null ? 0 :map.get("toBePaySum"))+",dealNum:"+(map.get("dealNumSum")==null ? 0 :map.get("dealNumSum"))
						+",actOrderNum:"+(map.get("actOrderNumSum")==null ? 0 :map.get("actOrderNumSum"))+",actOrderPrice:"+(map.get("actOrderPriceSum")==null ? 0 :map.get("actOrderPriceSum"))
						+",dealMoney:"+(map.get("dealMoneySum")==null ? 0 :map.get("dealMoneySum"))+",quantityAmount:"+(map.get("quantityAmountSum")==null ? 0 :map.get("quantityAmountSum"))
						+",refundAmount:"+(map.get("refundAmountSum")==null ? 0 :map.get("refundAmountSum"))
						);
			}else{
				dataGrid.setFooter("storeId:合计,appClick:0,goodsClick:0,goodsPushNum:0,orderPushNum:0,newClickNum:0,newsPushNum:0,toBePayNum:0,dealNum:0,dealMoney:0,refundAmount:0");
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 顾客资料完成度报表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagridOfCustInfo")
	public void datagridOfCustInfo(TCustInfoCompleteEntity tCustInfoComplete,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
//			Map<String,String> slqMap = this.getCustInfoSqlMap(request,dataGrid);
//			int total = this.systemService.getCountForJdbc(slqMap.get("sqlCount")).intValue();
//			List<Map<String, Object>> resultList = null;
//			if(total>0){
//				resultList =  systemService.findForJdbc(slqMap.get("sql"),dataGrid.getPage(),dataGrid.getRows());
//			}else{
//				resultList = new ArrayList<Map<String,Object>>();
//			}
			Map<String, Object> map = getCustInfoMap(request, dataGrid, true);
			dataGrid.setResults((List) map.get("list"));
			dataGrid.setTotal(Integer.valueOf(map.get("count")+""));
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**获取 顾客资料完成度 的map
	 * @param request
	 * @param dataGrid
	 * @param isPagenation 是否分页(导出的时候不需要分页)
	 * @return
	 */
	private Map<String, Object> getCustInfoMap(HttpServletRequest request ,DataGrid dataGrid,boolean isPagenation) {
		Map<String, Object> map = new HashMap<String, Object>();
		String retailerId = ResourceUtil.getRetailerId();
		String storeId = request.getParameter("storeId");
		StringBuffer sqlList = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		sqlList.append("SELECT p.user_Id guideId,p.real_Name guideName,ifnull(p.store_id, '') storeId,count(c.phone_no) totalNum ")
			.append("FROM t_person p LEFT JOIN t_focus_customer c ON ( p.user_Id = c.add_guide_id OR p.user_Id = c.to_Guide_Id ) and c.to_retailer_id='")
			.append(retailerId).append("' and c. STATUS = 'A' ").append("WHERE p.to_retailer_id = '").append(retailerId).append("' and p.user_type = '03' and p.`status` = 'A' ");
		sqlCount.append("SELECT count(1)")
			.append(" from t_person p ")//添加或者分配的导购
			.append(" where  p.to_retailer_id = '").append(retailerId)
			.append("' and p.user_type = '03' and p.`status` = 'A' ");
			if(Utility.isNotEmpty(storeId)){
				sqlList.append(" and p.store_id ='").append(storeId).append("' ");
				sqlCount.append(" and p.store_id ='").append(storeId).append("'");
			}
			sqlList.append("GROUP BY guideId");
			String sort = dataGrid.getSort();
			if(Utility.isNotEmpty(sort)&&"guideName,storeId,totalNum".contains(sort)){
				sqlList.append(" order by ").append(sort).append(" ").append(dataGrid.getOrder());
			}
			
			int total = this.systemService.getCountForJdbc(sqlCount.toString()).intValue();
			List<CustInfoCompleteVo> resultList = new ArrayList<CustInfoCompleteVo>();
			if(total>0){
				if(isPagenation){
					resultList =  systemService.findObjForJdbc(sqlList.toString(),dataGrid.getPage(),dataGrid.getRows(),CustInfoCompleteVo.class);
				}else{
					resultList =  systemService.findObjForJdbc(sqlList.toString(),CustInfoCompleteVo.class);
				}
			}
			if(Utility.isNotEmpty(resultList)){
				StringBuffer sqlPercent = new StringBuffer();
				sqlPercent.append("SELECT ttt.guideId,")
				.append("MAX(CASE WHEN ttt.percent is null THEN ttt.num ELSE 0 END) p0,")
				.append("MAX(CASE ttt.percent WHEN 10 THEN ttt.num ELSE 0 END) p10,")
				.append("MAX(CASE ttt.percent WHEN 20 THEN ttt.num ELSE 0 END) p20,")
				.append("MAX(CASE ttt.percent WHEN 30 THEN ttt.num ELSE 0 END) p30,")
				.append("MAX(CASE ttt.percent WHEN 40 THEN ttt.num ELSE 0 END) p40,")
				.append("MAX(CASE ttt.percent WHEN 50 THEN ttt.num ELSE 0 END) p50,")
				.append("MAX(CASE ttt.percent WHEN 60 THEN ttt.num ELSE 0 END) p60,")
				.append("MAX(CASE ttt.percent WHEN 70 THEN ttt.num ELSE 0 END) p70,")
				.append("MAX(CASE ttt.percent WHEN 80 THEN ttt.num ELSE 0 END) p80,")
				.append("MAX(CASE ttt.percent WHEN 90 THEN ttt.num ELSE 0 END) p90,")
				.append("MAX(CASE ttt.percent WHEN 100 THEN ttt.num ELSE 0 END) p100 ")
				.append("from (SELECT ic.guide_id guideId,ic.percent,count(ic.phone_no) num ")
				//部分顾客可能已经被删除，但是资料完善度表中数据未删，需要过滤这样的顾客
				.append("FROM t_cust_info_complete ic join t_focus_customer fc on ic.customer_id = fc.id and fc.`status` ='A' and fc.to_retailer_id='").append(retailerId)
				.append("' where ic.status='A' and ic.retailer_id = '").append(retailerId).append("' and ic.guide_id IN (");
				for(CustInfoCompleteVo vo : resultList){
					sqlPercent.append("'").append(vo.getGuideId()).append("',");
				}
				sqlPercent.deleteCharAt(sqlPercent.length()-1).append(") AND ic.phone_no <> '' GROUP BY ic.guide_id,ic.percent) ttt GROUP BY guideId");
				List<CustInfoCompleteVo> percentList = systemService.findObjForJdbc(sqlPercent.toString(), CustInfoCompleteVo.class);
				if(Utility.isNotEmpty(percentList)){
					for(CustInfoCompleteVo cVo : resultList){
						for(CustInfoCompleteVo pVo : percentList){
							if(cVo.getGuideId().equals(pVo.getGuideId())){
								try {
									MyBeanUtils.copyBeanNotNull2Bean(pVo, cVo);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				for(CustInfoCompleteVo cVo : resultList){
					//手机通讯录导入的顾客资料完成度是没有记录的，按0%算
					cVo.setP0(cVo.getTotalNum()-cVo.getP100()-cVo.getP90()-cVo.getP80()-cVo.getP70()-cVo.getP60()-cVo.getP50()-cVo.getP40()-cVo.getP30()-cVo.getP20()-cVo.getP10());
				}
			}
			map.put("list", resultList);
			map.put("count", total);
		return map;
	}
	
	/**获取 顾客资料完成度 sql 的map
	 * @param request
	 * @return
	 */
	private Map<String, String> getCustInfoSqlMap(HttpServletRequest request ,DataGrid dataGrid) {
		Map<String, String> sqlMap = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String storeId = request.getParameter("storeId");
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		sql.append("SELECT ttt.guideId,ttt.guideName,ttt.storeId,SUM(ttt.num) totalNum,")
			.append("MAX(CASE WHEN ttt.percent is null THEN ttt.num ELSE 0 END) p0,")
			.append("MAX(CASE ttt.percent WHEN 10 THEN ttt.num ELSE 0 END) p10,")
			.append("MAX(CASE ttt.percent WHEN 20 THEN ttt.num ELSE 0 END) p20,")
			.append("MAX(CASE ttt.percent WHEN 30 THEN ttt.num ELSE 0 END) p30,")
			.append("MAX(CASE ttt.percent WHEN 40 THEN ttt.num ELSE 0 END) p40,")
			.append("MAX(CASE ttt.percent WHEN 50 THEN ttt.num ELSE 0 END) p50,")
			.append("MAX(CASE ttt.percent WHEN 60 THEN ttt.num ELSE 0 END) p60,")
			.append("MAX(CASE ttt.percent WHEN 70 THEN ttt.num ELSE 0 END) p70,")
			.append("MAX(CASE ttt.percent WHEN 80 THEN ttt.num ELSE 0 END) p80,")
			.append("MAX(CASE ttt.percent WHEN 90 THEN ttt.num ELSE 0 END) p90,")
			.append("MAX(CASE ttt.percent WHEN 100 THEN ttt.num ELSE 0 END) p100")
			.append(" from ( SELECT tt.guideId,tt.guideName,tt.storeId,tt.percent,sum(tt.num) num from (SELECT t.guideId,t.guideName,t.storeId,t.percent,SUM(IF(t.phoneNo is null,0,1)) num from (")
			.append(" SELECT p.user_Id guideId,p.real_Name guideName,ifnull(p.store_id,'') storeId,c.phone_no phoneNo,ci.percent percent")
			.append(" from t_person p LEFT JOIN t_focus_customer c on (p.user_Id = c.add_guide_id or p.user_Id = c.to_Guide_Id) and c.status='A'")//添加或者分配的顾客
			.append(" LEFT JOIN t_cust_info_complete ci on p.user_Id = ci.guide_id and c.phone_no = ci.phone_no and ci.status='A'")
			.append(" where p.to_retailer_id = '").append(retailerId)
			.append("' and p.user_type = '03' and p.`status` = 'A' ");
			sqlCount.append("SELECT count(1)")
			.append(" from t_person p ")//添加或者分配的顾客
			.append(" where  p.to_retailer_id = '").append(retailerId)
			.append("' and p.user_type = '03' and p.`status` = 'A' ");
			if(Utility.isNotEmpty(storeId)){
				sql.append(" and p.store_id ='").append(storeId).append("'");
				sqlCount.append(" and p.store_id ='").append(storeId).append("'");
				sql.append(" order by guideId  desc");  //排序
			}
			sql.append(") t GROUP BY guideId,guideName,percent,phoneNo ) tt GROUP BY guideId,guideName,percent) ttt GROUP BY guideId,guideName");
			String sort = dataGrid.getSort();
			if(Utility.isNotEmpty(sort)){
				sql.append(" order by ").append(sort).append(" ").append(dataGrid.getOrder());
			}
			
			sqlMap.put("sql", sql.toString());
			sqlMap.put("sqlCount", sqlCount.toString());
		return sqlMap;
	}

	/**拼装统计sql
	 * @param request
	 * @return
	 */
	private Map<String, String> getGuideSqlMap(HttpServletRequest request) {
		Map<String, String> sqlMap = new HashMap<String, String>();
		String guideName = request.getParameter("guideName");
		String storeId = request.getParameter("storeId");
		String beginTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		String sqlStoreId = " and store_id= '"+ storeId +"' ";
		String sqlUserId = " and p.real_name like '%"+ guideName +"%' ";
		
		String retailerId = ResourceUtil.getRetailerId();
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlSum = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		if(!Utility.isEmpty(retailerId)){
			sql.append("SELECT u.id as userId,p.real_Name as guideName,IFNULL(t3.dealNum, 0) as dealNum,IFNULL(t3.dealMoney, 0) as dealMoney,IFNULL(t3.quantityAmount, 0) AS quantityAmount,")
				.append(" IFNULL(t1.appClick,0) as appClick,IFNULL(t4.actOrderNum, 0) AS actOrderNum,IFNULL(t4.actOrderPrice, 0) AS actOrderPrice, ")
				.append(" IFNULL(t2.goodsClick,0) as goodsClick,IFNULL(t3.toBePayNum,0) as toBePayNum,p.store_id storeId,ts.name storeName, IFNULL(trr.refundAmount, 0) as refundAmount, ")
				.append(" IFNULL(goodsPush, 0) AS goodsPushNum,IFNULL(newClick, 0) AS newClickNum,IFNULL(newsPush, 0) AS newsPushNum ,IFNULL(orderPush, 0) AS orderPushNum ")
				.append(" from t_s_user u LEFT JOIN t_person p on u.id = p.user_Id left join t_store ts on p.store_id = ts.id ")
				.append(" LEFT JOIN (SELECT COUNT(1) as appClick,user_id from t_s_use_app where user_type = '03' and retailer_id='").append(retailerId).append("' ");
			sqlCount.append("SELECT count(1) from t_s_user u LEFT JOIN t_person p on u.id = p.user_Id left join t_store ts on p.store_id = ts.id ")
					.append("  where  u.retailer_id='").append(retailerId).append("' and u.`status` ='A' and u.user_status in('1','2') and u.user_type = '03' ");
			sqlSum.append("SELECT sum(IFNULL(t3.dealNum, 0)) AS dealNumSum,sum(IFNULL(t3.dealMoney, 0)) AS dealMoneySum,sum(IFNULL(t3.quantityAmount, 0)) AS quantityAmountSum,")
				.append("sum(IFNULL(t4.actOrderNum, 0)) AS actOrderNumSum,sum(IFNULL(t4.actOrderPrice, 0)) AS actOrderPriceSum,sum(IFNULL(t1.appClick, 0)) AS appClickSum,sum(IFNULL(t2.goodsClick, 0)) AS goodsClickSum,")
				.append("sum(IFNULL(t3.toBePayNum, 0)) AS toBePaySum,sum(IFNULL(trr.refundAmount, 0)) AS refundAmountSum,sum(IFNULL(goodsPush, 0)) AS goodsPushSum,	")
				.append("sum(IFNULL(newClick, 0)) AS newClickSum,sum(IFNULL(newsPush, 0)) AS newsPushSum,sum(IFNULL(orderPush, 0)) AS orderPushSum	")
				.append(" from t_s_user u LEFT JOIN t_person p on u.id = p.user_Id left join t_store ts on p.store_id = ts.id ")
				.append(" LEFT JOIN (SELECT COUNT(1) as appClick,user_id from t_s_use_app where user_type = '03' and retailer_id='").append(retailerId).append("' ");
			if(!Utility.isEmpty(beginTime)){
				sql.append(" and use_time >= '").append(beginTime).append(" 00:00:00'");
				sqlSum.append(" and use_time >= '").append(beginTime).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(endTime)){
				sql.append(" and use_time <= '").append(endTime).append(" 23:59:59'");
				sqlSum.append(" and use_time <= '").append(endTime).append(" 23:59:59'");
			}
			sql.append(" GROUP BY user_id) t1 on u.id = t1.user_id")
			.append(" LEFT JOIN (SELECT COUNT(1) as goodsClick,user_id from t_goods_browse where retailer_id='").append(retailerId).append("' ");
			sqlSum.append(" GROUP BY user_id) t1 on u.id = t1.user_id")
			.append(" LEFT JOIN (SELECT COUNT(1) as goodsClick,user_id from t_goods_browse where retailer_id='").append(retailerId).append("' ");
			if(!Utility.isEmpty(beginTime)){
				sql.append(" and browse_time >= '").append(beginTime).append(" 00:00:00'");
				sqlSum.append(" and browse_time >= '").append(beginTime).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(endTime)){
				sql.append(" and browse_time <= '").append(endTime).append(" 23:59:59'");
				sqlSum.append(" and browse_time <= '").append(endTime).append(" 23:59:59'");
			}
			sql.append(" GROUP BY user_id) t2 on u.id = t2.user_id")
				.append(" LEFT JOIN (SELECT id, to_guide_id, sum(case order_status when '1' then 1 else 0 end) toBePayNum, ")
				.append(" sum(case order_status when '2' then 1 when '3' then 1 when '4' then 1 else 0 end) dealNum  , ")
				.append(" sum(case order_status when '2'  then pay_amount when '3' then pay_amount when '4' then pay_amount when '8' then pay_amount else 0 end) dealMoney, ")
				.append(" sum(case order_status when '2'  then quantity_amount when '3' then quantity_amount when '4' then quantity_amount when '8' then quantity_amount else 0 end) quantityAmount ")
				.append(" FROM t_order_info  where to_retailer_id='").append(retailerId)
				.append("' and status ='A' ");
			sqlSum.append(" GROUP BY user_id) t2 on u.id = t2.user_id")
				.append(" LEFT JOIN (SELECT id, to_guide_id, sum(case order_status when '1' then 1 else 0 end) toBePayNum, ")
				.append(" sum(case order_status when '2' then 1 when '3' then 1 when '4' then 1 else 0 end) dealNum  , ")
				.append(" sum(case order_status when '2'  then pay_amount when '3' then pay_amount when '4' then pay_amount when '8' then pay_amount else 0 end) dealMoney, ")
				.append(" sum(case order_status when '2'  then quantity_amount when '3' then quantity_amount when '4' then quantity_amount when '8' then quantity_amount else 0 end) quantityAmount ")
				.append(" FROM t_order_info  where to_retailer_id='").append(retailerId)
				.append("' and status ='A' ");
			if(!Utility.isEmpty(beginTime)){
				sql.append(" and pay_time >= '").append(beginTime).append(" 00:00:00'");
				sqlSum.append(" and pay_time >= '").append(beginTime).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(endTime)){
				sql.append(" and pay_time <= '").append(endTime).append(" 23:59:59'");
				sqlSum.append(" and pay_time <= '").append(endTime).append(" 23:59:59'");
			}
			sql.append(" GROUP BY  to_guide_id) t3 on u.id = t3.to_guide_id");
			sqlSum.append(" GROUP BY  to_guide_id) t3 on u.id = t3.to_guide_id");
			
			sql.append(" LEFT JOIN (select guide_id,count(DISTINCT order_id) actOrderNum,sum(saleprice) actOrderPrice ")
			.append("from t_salenum WHERE retailer_id='").append(retailerId).append("' and goods_act_id is not null ");
			sqlSum.append(" LEFT JOIN (select guide_id,count(DISTINCT order_id) actOrderNum,sum(saleprice) actOrderPrice ")
			.append("from t_salenum WHERE retailer_id='").append(retailerId).append("' and goods_act_id is not null ");
			if(!Utility.isEmpty(beginTime)){
				sql.append(" and date >= '").append(beginTime).append(" 00:00:00'");
				sqlSum.append(" and date >= '").append(beginTime).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(endTime)){
				sql.append(" and date <= '").append(endTime).append(" 23:59:59'");
				sqlSum.append(" and date <= '").append(endTime).append(" 23:59:59'");
			}
			sql.append(" GROUP BY guide_id ) t4 ON u.id = t4.guide_id ");
			sqlSum.append(" GROUP BY guide_id ) t4 ON u.id = t4.guide_id ");
			
			sql.append(" LEFT JOIN (SELECT id, to_guide_id, sum(refund_amount)  refundAmount ")
				.append(" FROM t_refund_return  where retailer_id='").append(retailerId)
				.append("' and status ='A' and refund_status='5' ");
			sqlSum.append(" LEFT JOIN (SELECT id, to_guide_id, sum(refund_amount)  refundAmount ")
				.append(" FROM t_refund_return  where retailer_id='").append(retailerId)
				.append("' and status ='A' and refund_status='5' ");
			if(!Utility.isEmpty(beginTime)){
				sql.append(" and create_date >= '").append(beginTime).append(" 00:00:00'");
				sqlSum.append(" and create_date >= '").append(beginTime).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(endTime)){
				sql.append(" and create_date <= '").append(endTime).append(" 23:59:59'");
				sqlSum.append(" and create_date <= '").append(endTime).append(" 23:59:59'");
			}
			sql.append(" GROUP BY  to_guide_id) trr on u.id = trr.to_guide_id")
				.append(" LEFT JOIN (SELECT count(1) AS goodsPush, user_id FROM t_goods_push_count where retailer_id='").append(retailerId).append("' ");
			sqlSum.append(" GROUP BY  to_guide_id) trr on u.id = trr.to_guide_id")
				.append(" LEFT JOIN (SELECT count(1) AS goodsPush, user_id FROM t_goods_push_count where retailer_id='").append(retailerId).append("' ");
			if(!Utility.isEmpty(beginTime)){
				sql.append(" and push_time >= '").append(beginTime).append(" 00:00:00'");
				sqlSum.append(" and push_time >= '").append(beginTime).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(endTime)){
				sql.append(" and push_time <= '").append(endTime).append(" 23:59:59'");
				sqlSum.append(" and push_time <= '").append(endTime).append(" 23:59:59'");
			}
			sql.append("  GROUP BY user_id ) tgc ON u.id = tgc.user_id")
				.append(" LEFT JOIN ( SELECT sum(click_num) AS newClick, user_id FROM t_news_count where retailer_id='").append(retailerId).append("' ");
			sqlSum.append("  GROUP BY user_id ) tgc ON u.id = tgc.user_id")
				.append(" LEFT JOIN ( SELECT sum(click_num) AS newClick, user_id FROM t_news_count where retailer_id='").append(retailerId).append("' ");
			if(!Utility.isEmpty(beginTime)){
				sql.append(" and click_time >= '").append(beginTime).append(" 00:00:00'");
				sqlSum.append(" and click_time >= '").append(beginTime).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(endTime)){
				sql.append(" and click_time <= '").append(endTime).append(" 23:59:59'");
				sqlSum.append(" and click_time <= '").append(endTime).append(" 23:59:59'");
			}
			sql.append("  GROUP BY user_id ) tnc ON u.id = tnc.user_id")
				.append(" LEFT JOIN ( SELECT sum(push_num) AS newsPush, user_id FROM t_news_push_count where retailer_id='").append(retailerId).append("' ");
			sqlSum.append("  GROUP BY user_id ) tnc ON u.id = tnc.user_id")
				.append(" LEFT JOIN ( SELECT sum(push_num) AS newsPush, user_id FROM t_news_push_count where retailer_id='").append(retailerId).append("' ");
			if(!Utility.isEmpty(beginTime)){
				sql.append(" and push_time >= '").append(beginTime).append(" 00:00:00'");
				sqlSum.append(" and push_time >= '").append(beginTime).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(endTime)){
				sql.append(" and push_time <= '").append(endTime).append(" 23:59:59'");
				sqlSum.append(" and push_time <= '").append(endTime).append(" 23:59:59'");
			}
			sql.append("  GROUP BY  user_id ) tnpc ON u.id = tnpc.user_id")
				.append(" LEFT JOIN ( SELECT sum(push_num) AS orderPush, user_id FROM t_order_push_count WHERE  retailer_id='").append(retailerId).append("' ");
			sqlSum.append("  GROUP BY  user_id ) tnpc ON u.id = tnpc.user_id")
				.append(" LEFT JOIN ( SELECT sum(push_num) AS orderPush, user_id FROM t_order_push_count WHERE  retailer_id='").append(retailerId).append("' ");
			if(!Utility.isEmpty(beginTime)){
				sql.append(" and push_time >= '").append(beginTime).append(" 00:00:00'");
				sqlSum.append(" and push_time >= '").append(beginTime).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(endTime)){
				sql.append(" and push_time <= '").append(endTime).append(" 23:59:59'");
				sqlSum.append(" and push_time <= '").append(endTime).append(" 23:59:59'");
			}
			sql.append(" GROUP BY user_id ) tdjd ON u.id = tdjd.user_id")
				.append(" where u.retailer_id='").append(retailerId).append("' and u.`status` ='A' and u.user_status in('1','2') and u.user_type = '03'");
			sqlSum.append(" GROUP BY user_id ) tdjd ON u.id = tdjd.user_id")
				.append(" where u.retailer_id='").append(retailerId).append("' and u.`status` ='A' and u.user_status in('1','2') and u.user_type = '03'");
			if(!Utility.isEmpty(storeId)){
				sql.append(sqlStoreId);
				sqlCount.append(sqlStoreId);
				sqlSum.append(sqlStoreId);
			}
			if(!Utility.isEmpty(guideName)){
				sql.append(sqlUserId);
				sqlCount.append(sqlUserId);
				sqlSum.append(sqlUserId);
			}
		}
		sqlMap.put("sql", sql.toString());
		sqlMap.put("sqlCount", sqlCount.toString());
		sqlMap.put("sqlSum", sqlSum.toString());
		return sqlMap;
	}

	/**
	 * 导购统计 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(GuideCountVo guideCountVo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		Map<String,String> slqMap = this.getGuideSqlMap(request);
		String sql = slqMap.get("sql");
		String beginTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		List<GuideCountVo> resultList =  systemService.findObjForJdbc(sql,dataGrid.getPage(),dataGrid.getRows(),GuideCountVo.class);
		modelMap.put(NormalExcelConstants.FILE_NAME,"导购统计");
		modelMap.put(NormalExcelConstants.CLASS,GuideCountVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("导购统计", "统计时间： "+beginTime+"~"+endTime+
				"                          导出人:"+ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 导购统计 导出excel
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "exportCustInfoCompleteXls")
	public String exportCustInfoCompleteXls(HttpServletRequest request,HttpServletResponse response	, DataGrid dataGrid,ModelMap modelMap) {
//		List<CustInfoCompleteVo> resultList =  systemService.findObjForJdbc(this.getCustInfoSqlMap(request,dataGrid).get("sql"),CustInfoCompleteVo.class);
		List<CustInfoCompleteVo> resultList =  (List<CustInfoCompleteVo>) this.getCustInfoMap(request,dataGrid, false).get("list");
		if(Utility.isNotEmpty(resultList)){
			List<TStoreEntity> storeList = this.tStoreService.findByProperty(TStoreEntity.class, "retailerId", ResourceUtil.getRetailerId());
			if(Utility.isNotEmpty(storeList)){//替换storeId为店铺名称
				for(CustInfoCompleteVo vo : resultList){
					if(Utility.isNotEmpty(vo.getStoreId())){
						for(TStoreEntity store : storeList){
							if(store.getId().equals(vo.getStoreId())){
								vo.setStoreId(store.getName());
								break;
							}
						}
					}
				}
			}
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"顾客资料完成度统计");
		modelMap.put(NormalExcelConstants.CLASS,CustInfoCompleteVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("顾客资料完成度统计", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
	/**
	 * 公司通知点击统计列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "noticeStoreList")
	public ModelAndView noticeStoreList(HttpServletRequest request) {
		String noticeId = request.getParameter("noticeId");
		String noticeLevel = request.getParameter("noticeLevel");
		request.setAttribute("noticeId", noticeId);
		request.setAttribute("noticeLevel", noticeLevel);
		return new ModelAndView("com/buss/count/noticeStoreList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "noticeStoreDatagrid")
	public void noticeStoreDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
			String noticeId = request.getParameter("noticeId");
			String noticeLevel = request.getParameter("noticeLevel");//1全体导购，2指定导购
			String guideName = request.getParameter("guideName");//查询条件
			String retailerId = ResourceUtil.getRetailerId();
			
			String sql = this.getNoticeStoreSql(noticeId,retailerId,noticeLevel,guideName);
			List<TStoreSNoticeCount> resultList =  systemService.findObjForJdbc(sql,dataGrid.getPage(),dataGrid.getRows(),TStoreSNoticeCount.class);
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				String countSql = this.getCountSql(noticeId,retailerId,noticeLevel,guideName);
				total = this.systemService.getCountForJdbc(countSql).intValue();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**拼装统计sql
	 * @param noticeId
	 * @param retailerId
	 * @param noticeLevel 1全体导购，2指定导购
	 * @return
	 */
	private String getNoticeStoreSql(String noticeId,String retailerId,String noticeLevel,String guideName) {
		StringBuffer sb = new StringBuffer();
		if("1".equals(noticeLevel)){
			sb.append(" SELECT")
			.append(" if(p.name is null or p.name = '',p.phone_no,p.name) guideName,")
			.append(" ifnull(c.click_num,0) clickNum,")
			.append(" ifnull(c.still_time,null) stillTime,")
			.append(" ifnull(c.click_time,null) clickTime")
			.append(" FROM")
			.append(" t_person p LEFT JOIN t_s_notice_store_count c ON p.user_Id = c.user_id AND c.notice_id='").append(noticeId).append("'")
			.append(" WHERE p.`status`='A'")
			.append(" AND p.to_retailer_id='").append(retailerId).append("'")
			.append(" AND p.user_type='").append(GlobalConstants.USER_TYPE_03).append("'");
			if(Utility.isNotEmpty(guideName)){
				sb.append(" AND p.name like '%").append(guideName).append("%'");
			}
		} else if("2".equals(noticeLevel)){
			sb.append(" SELECT")
			.append(" if(p.name is null or p.name = '',p.phone_no,p.name) guideName,")
			.append(" ifnull(c.click_num,0) clickNum,")
			.append(" ifnull(c.still_time,null) stillTime,")
			.append(" ifnull(c.click_time,null) clickTime")
			.append(" from t_s_notice_store_authority_user a INNER JOIN t_person p ON a.user_id = p.user_Id ")
			.append(" LEFT JOIN  t_s_notice_store_count c ON a.user_Id = c.user_id AND c.notice_id='").append(noticeId).append("'")
			.append(" where p.user_type='03' AND p.to_retailer_id='").append(retailerId).append("'")
			.append(" AND a.notice_id='").append(noticeId).append("'");
			if(Utility.isNotEmpty(guideName)){
				sb.append(" AND p.name like '%").append(guideName).append("%'");
			}
		}
		return sb.toString();
	}
	
	/**拼装统计countSql
	 * @param noticeId
	 * @param retailerId
	 * @param noticeLevel 1全体导购，2指定导购
	 * @return
	 */
	private String getCountSql(String noticeId,String retailerId,String noticeLevel,String guideName) {
		StringBuffer sb = new StringBuffer();
		if("1".equals(noticeLevel)){
			sb.append(" SELECT count(1)")
			.append(" FROM")
			.append(" t_person p LEFT JOIN t_s_notice_store_count c ON p.user_Id = c.user_id AND c.notice_id='").append(noticeId).append("'")
			.append(" WHERE p.`status`='A'")
			.append(" AND p.to_retailer_id='").append(retailerId).append("'")
			.append(" AND p.user_type='").append(GlobalConstants.USER_TYPE_03).append("'");
			if(Utility.isNotEmpty(guideName)){
				sb.append(" AND p.name like '%").append(guideName).append("%'");
			}
		} else if("2".equals(noticeLevel)){
			sb.append(" SELECT count(1)")
			.append(" from t_s_notice_store_authority_user a INNER JOIN t_person p ON a.user_id = p.user_Id ")
			.append(" LEFT JOIN  t_s_notice_store_count c ON a.user_Id = c.user_id AND c.notice_id='").append(noticeId).append("'")
			.append(" where p.user_type='03' AND p.to_retailer_id='").append(retailerId).append("'")
			.append(" AND a.notice_id='").append(noticeId).append("'");
			if(Utility.isNotEmpty(guideName)){
				sb.append(" AND p.name like '%").append(guideName).append("%'");
			}
		}
		
		return sb.toString();
	}
	
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "noticeStoreExportXls")
	public String noticeStoreExportXls(GuideCountVo guideCountVo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String noticeId = request.getParameter("noticeId");
		String noticeLevel = request.getParameter("noticeLevel");//1全体导购，2指定导购
		String guideName = request.getParameter("guideName");//导购名称
		String retailerId = ResourceUtil.getRetailerId();
		String sql = this.getNoticeStoreSql(noticeId,retailerId,noticeLevel,guideName);
		List<TStoreSNoticeCount> resultList =  systemService.findObjForJdbc(sql,dataGrid.getPage(),dataGrid.getRows(),TStoreSNoticeCount.class);
		modelMap.put(NormalExcelConstants.FILE_NAME,"公司通知点击统计");
		modelMap.put(NormalExcelConstants.CLASS,TStoreSNoticeCount.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("公司通知点击统计", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,resultList);
		logger.info("导购统计导出");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
	
	/**
	 * 商品，资讯，活动，公司通知导购浏览列表 页面统计跳转
	 * @return
	 */
	@RequestMapping(params = "mainTab")
	public ModelAndView mainTab(HttpServletRequest request) {
		return new ModelAndView("com/buss/count/tGuideCountMainTab");
	}
	
	
	
	/**
	 * 商品，资讯导购推送页面统计跳转
	 * @return
	 */
	@RequestMapping(params = "mainTotalTab")
	public ModelAndView mainTotalTab(HttpServletRequest request) {
		return new ModelAndView("com/buss/count/tGuideCountTotalTab");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
