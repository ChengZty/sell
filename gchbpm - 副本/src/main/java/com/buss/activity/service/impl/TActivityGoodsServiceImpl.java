package com.buss.activity.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.activity.entity.TActivityGoodsEntity;
import com.buss.activity.service.TActivityGoodsServiceI;
import com.buss.base.entity.BaseActivityEntity;
import com.buss.goods.entity.TGoodsEntity;

import cn.redis.service.RedisService;

@Service("tActivityGoodsService")
@Transactional
public class TActivityGoodsServiceImpl extends CommonServiceImpl implements TActivityGoodsServiceI {
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
 	
 	@SuppressWarnings("unchecked")
	@Override
 	public void doAdd(String goodsId, BaseActivityEntity actEntity,	String contentId, String retailerId) throws Exception {
 		TGoodsEntity goods = this.commonDao.get(TGoodsEntity.class, goodsId);
		TActivityGoodsEntity tActivityGoods = new TActivityGoodsEntity();
		tActivityGoods.setStatus(common.GlobalConstants.STATUS_ACTIVE);
		tActivityGoods.setUpdateDate(Utility.getCurrentTimestamp());
		tActivityGoods.setActivityId(actEntity.getId());
		tActivityGoods.setContentId(Long.valueOf(contentId));
		tActivityGoods.setGoodsId(goodsId);
		tActivityGoods.setGoodsStoreId(goods.getRetailerId());
		tActivityGoods.setGoodsStoreType(goods.getRetailerType());
		tActivityGoods.setRetailerId(retailerId);
		tActivityGoods.setSource(TActivityGoodsEntity.SOURCE_1);
		commonDao.save(tActivityGoods);
//		String sql = "SELECT count(1) from t_activity_goods where retailer_id = '"+retailerId+"' and status = 'A' and goods_id = '"+tActivityGoods.getGoodsId()+"'";
//		Long count = this.commonDao.getCountForJdbc(sql);//该商品参与的活动个数
		Long t1 = actEntity.getEndTime().getTime();
		Long t2 = System.currentTimeMillis();
		Long t = (t1-t2)/1000;//距离活动结束时间 秒
		if(t>0){//活动还没结束并且没有活动添加该商品
			List<Map<String,Object>> list = null;
			String val = this.redisService.get(common.GlobalConstants.ACT_GOODS+retailerId+"_"+tActivityGoods.getActivityId());
			if(Utility.isEmpty(val)){//没有活动中的商品
				list = new ArrayList<Map<String,Object>>();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("goodsId", goodsId);
//				map.put("activityId", actEntity.getId());
				map.put("addTime", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
//				map.put("startTime", DateUtil.dateToString(actEntity.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
//				map.put("endTime", DateUtil.dateToString(actEntity.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
				map.put("startTimeStamp", actEntity.getStartTime().getTime());
				map.put("endTimeStamp", actEntity.getEndTime().getTime());
				list.add(map);
				this.redisService.set(common.GlobalConstants.ACT_GOODS+"_"+retailerId+"_"+tActivityGoods.getActivityId(), MAPPER.writeValueAsString(list),t.intValue());
			}else{
				list = (List<Map<String,Object>>)MAPPER.readValue(val,MAPPER.getTypeFactory().constructParametricType(ArrayList.class, Map.class));
				Boolean exist = false;//默认不存在该活动商品
				for(Map<String,Object> map : list){
					if((map.get("goodsId")+"").equals(goodsId)){
						exist = true;//存在
						break;
					}
				}
				if(!exist){//不存在则添加进去
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("goodsId", goodsId);
//					map.put("activityId", actEntity.getId());
					map.put("addTime", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					map.put("startTimeStamp", actEntity.getStartTime().getTime());
					map.put("endTimeStamp", actEntity.getEndTime().getTime());
					list.add(map);
					this.redisService.set(common.GlobalConstants.ACT_GOODS+"_"+retailerId+"_"+tActivityGoods.getActivityId(), MAPPER.writeValueAsString(list),t.intValue());
				}
			}
		}
 	}
 	
 	@Override
 	public void doDel(TActivityGoodsEntity tActivityGoods)throws Exception {
 		tActivityGoods.setStatus(common.GlobalConstants.STATUS_INACTIVE);
 		this.commonDao.updateEntitie(tActivityGoods);
 		if(TActivityGoodsEntity.SOURCE_1.equals(tActivityGoods.getSource())){//来源于活动
 			BaseActivityEntity actEntity = commonDao.get(BaseActivityEntity.class, tActivityGoods.getActivityId());
 			String retailerId = actEntity.getRetailerId();
// 		String sql = "SELECT count(1) from t_activity_goods where retailer_id = '"+retailerId+"' and status = 'A' and goods_id = '"+tActivityGoods.getGoodsId()+"'";
// 		Long count = this.commonDao.getCountForJdbc(sql);//该商品参与的活动个数
// 		if(count==0){//该商品没有参与的活动，则要从redis中删除
 			String val = this.redisService.get(common.GlobalConstants.ACT_GOODS+retailerId+"_"+tActivityGoods.getActivityId());
 			if(Utility.isNotEmpty(val)){
 				@SuppressWarnings("unchecked")
 				List<Map<String,Object>> list = (List<Map<String,Object>>)MAPPER.readValue(val,MAPPER.getTypeFactory().constructParametricType(ArrayList.class, Map.class));
 				for(Map<String,Object> map : list){
 					if((map.get("goodsId")+"").equals(tActivityGoods.getGoodsId())){
 						list.remove(map);//删除该商品
 						break;
 					}
 				}
 				Long t1 = actEntity.getEndTime().getTime();
 				Long t2 = System.currentTimeMillis();
 				Long t = (t1-t2)/1000;//距离活动结束时间 秒
 				if(t>0){//活动还没结束，结束了会自动从redis中删除
 					this.redisService.set(common.GlobalConstants.ACT_GOODS+"_"+retailerId+"_"+tActivityGoods.getActivityId(), MAPPER.writeValueAsString(list),t.intValue());
 				}
 			}
// 		}
 		}
 	}
 	
 	@Override
 	public void flushRedisAllActivityGoods() throws Exception {
 		//查询所有零售商
// 		List<TSUser> retailers = this.commonDao.findHql("from TSUser where userType = ?", TSUser.USER_TYPE_02);
 		List<Map<String, Object>> retailers = this.commonDao.findForJdbc("select id from t_s_user where status = 'A' and user_type = ?", TSUser.USER_TYPE_02);
 		if(retailers.size()>0){
 			for(Map<String, Object> retailerMap : retailers){
 				String retailerId = retailerMap.get("id")+"";
 				//查询零售商的所有未结束的活动(加入真选私惠的活动contentId不为null)
 				List<BaseActivityEntity> activityList = this.commonDao.findHql("from BaseActivityEntity where retailerId = ? and contentId is not null and endTime > ?",
 																	retailerId,Utility.getCurrentTimestamp());
 				if(activityList.size()>0){
 					for(BaseActivityEntity activity:activityList){
 						LogUtil.info("+++++++++++++++++++++未结束的活动ID:"+activity.getId());
 						//零售商活动对应商品list
 						List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
 						//每个活动中的商品
// 						List<TActivityGoodsEntity> activityGoodsList = this.commonDao.findHql("from TActivityGoodsEntity where activityId = ? and goodsStoreId = ?", activity.getId(),retailerId);
 						List<TActivityGoodsEntity> activityGoodsList = this.commonDao.findHql("from TActivityGoodsEntity where activityId = ? and retailerId = ?", activity.getId(),retailerId);
 						if(activityGoodsList.size()>0){
 							for(TActivityGoodsEntity activityGoods:activityGoodsList){
 								Map<String,Object> map = new HashMap<String, Object>();
 								map.put("goodsId", activityGoods.getGoodsId());
// 								map.put("activityId", activity.getId());
 								map.put("addTime", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
 								map.put("startTimeStamp", activity.getStartTime().getTime());
 								map.put("endTimeStamp", activity.getEndTime().getTime());
 								list.add(map);
 							}
 						}
 						Long t1 = activity.getEndTime().getTime();
 						Long t2 = System.currentTimeMillis();
 						Long t = (t1-t2)/1000;//距离活动结束时间 秒
 						if(list.size()>0){
 							this.redisService.set(common.GlobalConstants.ACT_GOODS+retailerId+"_"+activity.getId(), MAPPER.writeValueAsString(list),t.intValue());
 						}
 					}
 				}
 			}
 		}
 		
 	}
}