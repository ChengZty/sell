package com.buss.order.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.order.entity.TStoreOrderInfoEntity;
import com.buss.order.service.TStoreOrderInfoServiceI;

@Service("tStoreOrderInfoService")
@Transactional
public class TStoreOrderInfoServiceImpl extends CommonServiceImpl implements TStoreOrderInfoServiceI {
	
	//线下订单明细列表
 	public List<TStoreOrderInfoEntity> getTStoreOrderInfo(HttpServletRequest request,DataGrid dataGrid,String type){
 		//自定义追加查询条件
		String retailerId = ResourceUtil.getRetailerId();
		String orderNo = request.getParameter("orderNo");//订单号
		String userName = request.getParameter("userName");//买家姓名
		String userPhone = request.getParameter("userPhone");//买家手机
		String payTime_begin = request.getParameter("payTime_begin");//支付时间
		String payTime_end = request.getParameter("payTime_end");
		String guideName = request.getParameter("guideName");//导购姓名
		String guidePhone = request.getParameter("guidePhone");//导购手机
		String storeId = request.getParameter("storeId");//导购店铺
		//设置初始化时间
		if(Utility.isEmpty(payTime_begin) && Utility.isEmpty(payTime_end)){
			payTime_begin = DateUtils.getFirstDayOfMonth(new Date());
			payTime_end = DateUtils.getDataString(DateUtils.date_sdf);
		}
		
		//sql查询
		StringBuffer sql = new StringBuffer("select tsoi.id id,tsoi.order_no orderNo,tsoi.user_name userName,");
		sql.append("tsoi.user_phone userPhone,pay_amount payAmount,quantity_amount quantityAmount,pay_time payTime,");
		sql.append("CONCAT(tsoi.pic_url,'?imageView2/1/w/80/h/80') picUrl,tp.real_name guideName,tp.phone_no guidePhone,ts.name storeName,ts.id storeId FROM ");
		sql.append("t_store_order_info tsoi left join t_person tp on tp.user_id = tsoi.to_guide_id ");
		sql.append("left join t_store ts on tp.store_id = ts.id where tsoi.status='A' ");
		//查询总数
		StringBuffer countSql = new StringBuffer("select count(1) FROM ");
		countSql.append("t_store_order_info tsoi left join t_person tp on tp.user_id = tsoi.to_guide_id ");
		countSql.append("left join t_store ts on tp.store_id = ts.id where tsoi.status='A' ");
		if(!Utility.isEmpty(retailerId)){
			sql.append(" and tsoi.to_retailer_id ='").append(retailerId).append("' ");
			countSql.append(" and tsoi.to_retailer_id ='").append(retailerId).append("' ");
		}
		if(!Utility.isEmpty(orderNo)){
			sql.append(" and tsoi.order_no like '%").append(orderNo).append("%' ");
			countSql.append(" and tsoi.order_no like '%").append(orderNo).append("%' ");
		}
		if(!Utility.isEmpty(userName)){
			sql.append(" and tsoi.user_name like '%").append(userName).append("%' ");
			countSql.append(" and tsoi.user_name like '%").append(userName).append("%' ");
		}
		if(!Utility.isEmpty(userPhone)){
			sql.append(" and tsoi.user_phone like '%").append(userPhone).append("%' ");
			countSql.append(" and tsoi.user_phone like '%").append(userPhone).append("%' ");
		}
		if(!Utility.isEmpty(payTime_begin)){
			sql.append(" and tsoi.pay_time >='").append(payTime_begin).append("' ");
			countSql.append(" and tsoi.pay_time >='").append(payTime_begin).append("' ");
		}
		if(!Utility.isEmpty(payTime_end)){
			sql.append(" and tsoi.pay_time <='").append(payTime_end).append("' ");
			countSql.append(" and tsoi.pay_time <='").append(payTime_end).append("' ");
		}
		if(!Utility.isEmpty(guideName)){
			sql.append(" and tp.real_name like '%").append(guideName).append("%' ");
			countSql.append(" and tp.real_name like '%").append(guideName).append("%' ");
		}
		if(!Utility.isEmpty(guidePhone)){
			sql.append(" and tp.phone_no like '%").append(guidePhone).append("%' ");
			countSql.append(" and tp.phone_no like '%").append(guidePhone).append("%' ");
		}
		if(!Utility.isEmpty(storeId)){
			sql.append(" and ts.id ='").append(storeId).append("' ");
			countSql.append(" and ts.id ='").append(storeId).append("' ");
		}
//		sql.append(" ORDER BY tsoi.pay_time desc");
		String sortName = dataGrid.getSort();
		if(Utility.isEmpty(sortName)){
			sql.append(" ORDER BY pay_time desc");
		}else{
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		List<TStoreOrderInfoEntity> list = new ArrayList<TStoreOrderInfoEntity>();
		try{
			int total = this.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				if("all".equals(type)){
					list =  this.findObjForJdbc(sql.toString(),TStoreOrderInfoEntity.class);
				}else{
					list =  this.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TStoreOrderInfoEntity.class);
				}
			}
			dataGrid.setResults(list);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
		return list;
 	}
	

}