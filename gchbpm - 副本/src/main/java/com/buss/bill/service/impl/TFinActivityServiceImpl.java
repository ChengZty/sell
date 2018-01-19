package com.buss.bill.service.impl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.activity.entity.TFinActivityGoodsEntity;
import com.buss.bill.entity.TFinActivityEntity;
import com.buss.bill.service.TFinActivityServiceI;
import com.buss.goods.entity.TGoodsActDetailEntity;
import com.buss.goods.entity.TGoodsActEntity;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.goods.vo.ActPriceImportVo;

import common.DBUtil;
import common.GlobalConstants;

@Service("tFinActivityService")
@Transactional
public class TFinActivityServiceImpl extends CommonServiceImpl implements TFinActivityServiceI {
 	
 	@Override
 	public String saveFinActivity(TFinActivityEntity tFinActivity) throws SQLException {
 		String msg = null;
 		TSUser user = ResourceUtil.getSessionUserName();
 		if(user.getUserType().equals(common.GlobalConstants.USER_TYPE_01)){
 			tFinActivity.setPlatformType("1");
 		}else{
 			if(user.getUserType().equals(common.GlobalConstants.USER_TYPE_02)){
 				tFinActivity.setRetailerId(user.getId());
 				tFinActivity.setRetailerType(user.getRetailerType());
 				tFinActivity.setRetailerName(user.getRealName());
 			}else if(user.getUserType().equals(common.GlobalConstants.USER_TYPE_05)){
 				TSUser retailer = this.commonDao.get(TSUser.class, user.getRetailerId());
 				tFinActivity.setRetailerId(retailer.getId());
 				tFinActivity.setRetailerType(retailer.getRetailerType());
 				tFinActivity.setRetailerName(retailer.getRealName());
 			}
 			tFinActivity.setPlatformType(TFinActivityEntity.PLATFORM_TYPE_2);
 		}
 		this.commonDao.save(tFinActivity);
 		
 		if(!TFinActivityEntity.ACTIVITYTYPE_1.equals(tFinActivity.getActivityType())){//品牌或者全馆
				//管理该品牌所有商品到
 			String sql = null;
 			List<Map<String,Object>> goodsList = null;
 			if(TFinActivityEntity.ACTIVITYTYPE_2.equals(tFinActivity.getActivityType())){//品牌
 				sql = "select id,retailer_id,retailer_type from t_goods where status = 'A' and retailer_id = ? and brand_id = ? and goods_status = ?";
 				goodsList = this.findForJdbc(sql, tFinActivity.getRetailerId(),tFinActivity.getBrandId(),TGoodsEntity.GOODS_STATUS_4);
 			}else if(TFinActivityEntity.ACTIVITYTYPE_3.equals(tFinActivity.getActivityType())){//全馆
 				sql = "select id,retailer_id,retailer_type from t_goods where status = 'A' and retailer_id = ? and goods_status = ?";
 				goodsList = this.findForJdbc(sql, tFinActivity.getRetailerId(),TGoodsEntity.GOODS_STATUS_4);
 			}
 			//获取活动时间冲突的商品列表
 			List<TFinActivityGoodsEntity> otherFinActDetailList = this.getOtherFinActDetailList(tFinActivity, tFinActivity.getRetailerId());
 			//校验并删除存在冲突的商品
 			msg = this.doCheckAndDel(goodsList,otherFinActDetailList);
			if(Utility.isNotEmpty(goodsList)&&goodsList.size()>0){
				List<Map<String,Object>> dataMapList = new ArrayList<Map<String,Object>>();
				Date createDate = Utility.getCurrentTimestamp();
				for(Map<String,Object> map : goodsList){
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
			}else{
				msg = "没有符合条件的商品";
			}
		}
 		return msg;
 	}
 	
 	
 	/**校验并删除时间冲突的商品
 	 * @param goodsList
 	 * @param otherFinActDetailList
 	 * @return
 	 */
 	private String doCheckAndDel(List<Map<String, Object>> goodsList,	List<TFinActivityGoodsEntity> otherFinActDetailList) {
 		String msg = null;
 		if(Utility.isNotEmpty(goodsList)&&Utility.isNotEmpty(otherFinActDetailList)){
 			for(int i=0;i<goodsList.size();i++){
 				Map<String, Object> map = goodsList.get(i);
				boolean exist = false;
				for(TFinActivityGoodsEntity detail : otherFinActDetailList){
					if((map.get("id")+"").equals(detail.getGoodsId())){
						goodsList.remove(map);
						i--;
						exist = true;
						break;
					}
				}
				if(exist&&Utility.isEmpty(msg)){//存在冲突的商品
					msg = "部分活动时间冲突的商品已过滤";
				}
			}
 		}
		return msg;
	}

 	/**获取活动时间冲突的商品列表
 	 * @param tFinActivity
 	 * @param retailerId
 	 * @return
 	 */
 	@Override
 	public List<TFinActivityGoodsEntity> getOtherFinActDetailList(TFinActivityEntity tFinActivity,String retailerId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT d.id,d.goods_id goodsId from t_fin_activity a JOIN t_fin_activity_goods d on a.id = d.fin_act_id")
		.append(" where a.status='A'  and d.status='A' and a.activity_status <>'").append(TFinActivityEntity.ACTIVITY_STATUS_3)//排除下架的
		.append("'  and a.retailer_id='").append(retailerId).append("' and d.fin_act_id <> '").append(tFinActivity.getId())
		.append("' and ((a.start_time BETWEEN '").append(DateUtil.dateToStringWithTime(tFinActivity.getStartTime()))
		.append("' AND '").append(DateUtil.dateToStringWithTime(tFinActivity.getEndTime())).append("') OR ")
		.append("(a.end_time BETWEEN '").append(DateUtil.dateToStringWithTime(tFinActivity.getStartTime()))
		.append("' AND '").append(DateUtil.dateToStringWithTime(tFinActivity.getEndTime())).append("') OR")
		.append("(a.start_time < '").append(DateUtil.dateToStringWithTime(tFinActivity.getStartTime()))
		.append("' AND a.end_time > '").append(DateUtil.dateToStringWithTime(tFinActivity.getEndTime())).append("')")
		.append(") ");
		List<TFinActivityGoodsEntity> list = this.commonDao.findObjForJdbc(sql.toString(), TFinActivityGoodsEntity.class);
		return list;
	}
 	
 	
 	
 	
 	
 	
 	
 	
}