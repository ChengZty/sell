package com.buss.activity.service.impl;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.activity.entity.TFinActivityGoodsEntity;
import com.buss.activity.service.TFinActivityGoodsServiceI;
import com.buss.bill.entity.TFinActivityEntity;
import com.buss.bill.service.TFinActivityServiceI;

import common.DBUtil;
import common.GlobalConstants;

@Service("tFinActivityGoodsService")
@Transactional
public class TFinActivityGoodsServiceImpl extends CommonServiceImpl implements TFinActivityGoodsServiceI {
	@Autowired
	private TFinActivityServiceI tFinActivityService;

	@Override
	public String doCheckAndBatchAdd(HttpServletRequest request) throws SQLException {
		String msg = null;
		String goodsIds = request.getParameter("goodsIds");
		String finActId = request.getParameter("finActId");
		Set<String> goodsIdSet = new HashSet<String>();//可添加的且未重复的商品id
		TFinActivityEntity tFinActivity = this.commonDao.get(TFinActivityEntity.class, finActId);
		//获取活动时间冲突的商品列表
		List<TFinActivityGoodsEntity> otherFinActDetailList = this.tFinActivityService.getOtherFinActDetailList(tFinActivity, tFinActivity.getRetailerId());
			
		for(String goodsId:goodsIds.split(",")){
			boolean exist = false;
			for(TFinActivityGoodsEntity detail : otherFinActDetailList){
				if(goodsId.equals(detail.getGoodsId())){
					exist = true;
					break;
				}
			}
			if(!exist){
				goodsIdSet.add(goodsId);
			}
			if(exist&&Utility.isEmpty(msg)){//存在冲突的商品
				msg = "部分活动时间冲突的商品已过滤";
			}
		}
		if(Utility.isNotEmpty(goodsIdSet)){
			String goodsIdStr = "";
			for(String goodsId : goodsIdSet){
				goodsIdStr+=",'"+goodsId+"'";
			}
			List<Map<String, Object>> mapList = this.commonDao.findForJdbc("select g.id,g.retailer_id,g.retailer_type from t_goods g where g.id in ("+goodsIdStr.substring(1)+")", null);
			if(Utility.isNotEmpty(mapList)&&mapList.size()>0){
				List<Map<String,Object>> dataMapList = new ArrayList<Map<String,Object>>();
				TSUser user = ResourceUtil.getSessionUserName();
				Date createDate = Utility.getCurrentTimestamp();
				for(Map<String,Object> map : mapList){
					Map<String,Object> dataMap = new HashMap<String,Object>();
					dataMap.put("id", Utility.getUUID());
					dataMap.put("create_name",user.getRealName());
					dataMap.put("create_by",user.getUserName());
					dataMap.put("create_date",createDate);
					dataMap.put("update_date",createDate);
					dataMap.put("status",GlobalConstants.STATUS_ACTIVE);
					dataMap.put("fin_act_id", tFinActivity.getId());
					dataMap.put("goods_id", map.get("id"));
					dataMap.put("goods_store_id", map.get("retailer_id"));
					dataMap.put("goods_store_type", map.get("retailer_type"));
					dataMap.put("retailer_id", tFinActivity.getRetailerId());
					dataMapList.add(dataMap);
				}
				if(Utility.isNotEmpty(dataMapList)){
					Map<String,Object> resultMap= DBUtil.insertAll("t_fin_activity_goods", dataMapList);
					int n = (Integer) resultMap.get("affectRowCount");
					System.out.println("成功插入"+n+"条");
				}
			}
			
			
			/* 报错，不用此方法
			 StringBuffer sql = new StringBuffer("insert into t_fin_activity_goods  ")
					.append("(id,create_name,create_by,create_date,update_date,status,fin_act_id,goods_id,goods_store_id,goods_store_type,retailer_id) ")
					.append("SELECT REPLACE(UUID(),'-','') id,")
					.append("'").append(user.getRealName()).append("' create_name,")
					.append("'").append(user.getUserName()).append("' create_by,")
					.append("'").append(timestamp).append("' create_date,")
					.append("'").append(timestamp).append("' update_date,")
					.append("'A' status,")
					.append("'").append(finActId).append("' fin_act_id,")
					.append("g.id goods_id,")
					.append("g.retailer_id,")
					.append("g.Retailer_Type,")
					.append("'").append(tFinActivity.getRetailerId()).append("' ")
					.append("from t_goods g where g.id in (").append(goodsIdStr.substring(1)).append(")");
			this.commonDao.updateBySqlString(sql.toString());
			*/
		}
		return msg;
	}
	
}