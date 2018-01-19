package com.buss.base.service.impl;
import cn.redis.service.RedisService;

import com.buss.activity.entity.TActivityGoodsEntity;
import com.buss.base.service.BaseActivityServiceI;

import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.MyBeanUtils;

import com.buss.base.entity.BaseActivityEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

@Service("baseActivityService")
@Transactional
public class BaseActivityServiceImpl extends CommonServiceImpl implements BaseActivityServiceI {
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((BaseActivityEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((BaseActivityEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((BaseActivityEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(BaseActivityEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(BaseActivityEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(BaseActivityEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,BaseActivityEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{activity_name}",String.valueOf(t.getActivityName()));
// 		sql  = sql.replace("#{activity_value}",String.valueOf(t.getActivityValue()));
 		sql  = sql.replace("#{pic_url}",String.valueOf(t.getPicUrl()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void doUpdate(BaseActivityEntity baseActivity) throws Exception{
 		BaseActivityEntity activity = commonDao.get(BaseActivityEntity.class, baseActivity.getId());
 		MyBeanUtils.copyBeanNotNull2Bean(baseActivity, activity);
 		commonDao.updateEntitie(activity);
		Long t1 = activity.getEndTime().getTime();
		Long t2 = System.currentTimeMillis();
		Long t = (t1-t2)/1000;//距离活动结束时间 秒
		String retailerId = activity.getRetailerId();
		if(t>0){//活动未过期则刷新该活动的缓存
			//零售商活动对应商品list
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			//每个活动中的商品
			List<TActivityGoodsEntity> activityGoodsList = this.commonDao.findHql("from TActivityGoodsEntity where activityId = ? and goodsStoreId = ?", activity.getId(),retailerId);
			if(activityGoodsList.size()>0){
				for(TActivityGoodsEntity activityGoods:activityGoodsList){
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("goodsId", activityGoods.getGoodsId());
//					map.put("activityId", activity.getId());
					map.put("updateTime", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					map.put("startTimeStamp", activity.getStartTime().getTime());
					map.put("endTimeStamp", activity.getEndTime().getTime());
					list.add(map);
				}
			}
			if(list.size()>0){
				this.redisService.set(common.GlobalConstants.ACT_GOODS+retailerId+"_"+activity.getId(), MAPPER.writeValueAsString(list),t.intValue());
			}
		}else{//修改货活动过期则删除缓存
			this.redisService.del(common.GlobalConstants.ACT_GOODS+retailerId+"_"+activity.getId());
		}
 	}
 	
 	@Override
 	public void doDel(BaseActivityEntity baseActivity) {
 		BaseActivityEntity activity = commonDao.getEntity(BaseActivityEntity.class, baseActivity.getId());
 		activity.setStatus(common.GlobalConstants.STATUS_INACTIVE );
 		commonDao.updateEntitie(activity);
 		this.redisService.del(common.GlobalConstants.ACT_GOODS+activity.getRetailerId()+"_"+activity.getId());
 	}
}