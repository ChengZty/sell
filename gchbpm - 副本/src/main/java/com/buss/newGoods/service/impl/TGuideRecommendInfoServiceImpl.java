package com.buss.newGoods.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UploadFile;
import org.jeecgframework.core.util.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.newGoods.entity.TGuideRecommendDetailEntity;
import com.buss.newGoods.entity.TGuideRecommendInfoEntity;
import com.buss.newGoods.service.TGuideRecommendInfoServiceI;

import common.GlobalConstants;

@Service("tGuideRecommendInfoService")
@Transactional
public class TGuideRecommendInfoServiceImpl extends CommonServiceImpl implements TGuideRecommendInfoServiceI {

	
 	@Override
 	public void saveRecommendInfo(TGuideRecommendInfoEntity tGuideRecommendInfo) {
// 		Long count = this.commonDao.getCountForJdbc("select count(1) from t_guide_recommend_info where goods_id = '"+tGuideRecommendInfo.getGoodsId()+"' and status = 'A'");
// 		if(count==0){//1楼
// 			tGuideRecommendInfo.setFlag(TGuideRecommendInfoEntity.FIRST_FLOOR);
// 		}else if(count==1){//2楼
// 			tGuideRecommendInfo.setFlag(TGuideRecommendInfoEntity.SECOND_FLOOR);
// 		}else{
 			tGuideRecommendInfo.setFlag(TGuideRecommendInfoEntity.OTHER_FLOOR);
// 		}
 		this.commonDao.save(tGuideRecommendInfo);
 		List<TGuideRecommendDetailEntity> recommendDetailsList = tGuideRecommendInfo.getRecommendDetailsList();
 		/*TGoodsEntity goods = this.commonDao.get(TGoodsEntity.class, tGuideRecommendInfo.getGoodsId());
 		if(TGoodsEntity.GOODS_STATUS_3.equals(goods.getGoodsStatus())){//待上架状态
 			if(Utility.isNotEmpty(recommendDetailsList)){
 				boolean changed = false;//商品是否发生改变
 				for(TGuideRecommendDetailEntity tRecDetail : recommendDetailsList){
 					if(TGuideRecommendDetailEntity.TYPE_1.equals(tRecDetail.getType())){
 						if(Utility.isEmpty(goods.getSmallPic())&&TGuideRecommendDetailEntity.TYPE_1.equals(tRecDetail.getType())){
 		 					goods.setSmallPic(tRecDetail.getUrl());//更新商品小图
 		 				}
 						changed = true;//有图片就上架
 						break;
 					}
 				}
 				if(changed){
 					goods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_4);
 		 			goods.setGoodsUpdateTime(DateUtils.gettimestamp());//上架时间
 		 			Long sortNum = Long.valueOf(DateUtils.date2Str(DateUtils.gettimestamp(), DateUtils.yyyymmddhhmmss));
 		 			goods.setSortNum(sortNum);
 		 			this.commonDao.updateEntitie(goods);
 		 		}
 				
 			}
 		}*/
 		if(Utility.isNotEmpty(recommendDetailsList)){
 			//保存明细
	 			for(TGuideRecommendDetailEntity detail : recommendDetailsList){
	 				detail.setStatus("A");
	 				detail.setRecommendId(tGuideRecommendInfo.getId());
	 				detail.setGoodsId(tGuideRecommendInfo.getGoodsId());
	 				if(TGuideRecommendDetailEntity.TYPE_4.equals(detail.getType())){//视频
	 					detail.setCoverUrl(detail.getUrl()+"?vframe/png/offset/1|imageslim");//第一秒的图片作为封面图(瘦身图)只支持华东 bucket
	 					detail.setPlayTime(this.getVideoTime(detail.getUrl()));
	 				}
	 				this.commonDao.save(detail);
	 			}
 		}
 	}
 	
	/**视频外链
	 * @param url
	 * @return 时间 格式为 01:40
	 */
	private String getVideoTime(String url) {
		String videoUrl = url+"?avinfo";
		String videoInfo = UploadFile.getVideoInfo(videoUrl);
		JSONObject o =  JSONObject.parseObject(videoInfo);
		JSONArray streams = (JSONArray) o.get("streams");
		JSONObject obj = (JSONObject) streams.get(0);
		String time = obj.get("duration")+"";//时长（秒）
		float f = Float.parseFloat(time);
		int totalSecs = Math.round(f);
		int mins =totalSecs/60;
		int secs = totalSecs%60;
		String minStr = mins<10?"0"+mins:mins+"";
		String secStr = secs<10?"0"+secs:secs+"";
//		System.out.println(minStr+":"+secStr);
		return minStr+":"+secStr;
	}

 	@Override
 	public void deleteRecommendInfo(TGuideRecommendInfoEntity tGuideRecommendInfo) {
 		TGuideRecommendInfoEntity entity = this.commonDao.get(TGuideRecommendInfoEntity.class, tGuideRecommendInfo.getId());
		TGoodsEntity goods = this.commonDao.get(TGoodsEntity.class, entity.getGoodsId());
		entity.setStatus(GlobalConstants.STATUS_INACTIVE);
		this.commonDao.updateEntitie(entity);
 		List<TGuideRecommendDetailEntity> delDetailList = this.commonDao.findByProperty(TGuideRecommendDetailEntity.class, "recommendId", tGuideRecommendInfo.getId());
 		//判断更新商品小图和状态
 		this.updateGoodsPics(goods,delDetailList,tGuideRecommendInfo.getId());
 		//删除推荐明细和七牛图片
		if(Utility.isNotEmpty(delDetailList)){
			for(TGuideRecommendDetailEntity detail : delDetailList){
				detail.setStatus("I");
				commonDao.updateEntitie(detail);
				//删除七牛服务器图片
				if(StringUtil.isNotEmpty(detail.getUrl())){
					try {
						String key = detail.getUrl().replaceAll(GlobalConstants.DOMAIN, "");
						UploadFile.delete(key);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
 	}
 	
 	/**判断更新商品小图和状态
	 * @param goods
	 * @param delDetailList 要删除的导购推荐明细列表
	 * @param list 前端传过来的图片视频列表
	 * @param recommendId 主表id
	 */
	private void updateGoodsPics(TGoodsEntity goods,List<TGuideRecommendDetailEntity> delDetailList,String recommendId) {
		boolean delSmallPic = false;//默认没有删除商品小图
		if(Utility.isNotEmpty(delDetailList)){//判断删除的图片中有没有商品小图
			for(TGuideRecommendDetailEntity detail : delDetailList){
				if(TGuideRecommendDetailEntity.TYPE_1.equals(detail.getType())&&detail.getUrl().equals(goods.getSmallPic())){//图片
					delSmallPic = true;//小图被删除
				}
			}
		}
		//推荐明细第一张图，如果没有商品小图则用该图（发布会有图片，修改可能没有）
		String url = null;
		//如果没有小图或者被删除则要替换小图
		if(delSmallPic||Utility.isEmpty(goods.getSmallPic())){
			if(Utility.isEmpty(url)){//修改后没有图片了，则要获取推荐列表里最早的图片
				url = this.getSmallPicFromRecmDetailByGoodsId(goods.getId(),delDetailList);
			}
			if(Utility.isNotEmpty(url)){
				goods.setSmallPic(url);//更新商品小图
			}else{//推荐明细没有图片了，更新商品为待上架
				goods.setSmallPic(null);//更新商品小图
				goods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_3);
				goods.setUpdateDate(Utility.getCurrentTimestamp());
			}
			commonDao.updateEntitie(goods);
		}
		
	}

	/**获取导购推荐列表里面该商品最早上传的图片(并排除当前删除的图片)*/
	private String getSmallPicFromRecmDetailByGoodsId(String goodsId, List<TGuideRecommendDetailEntity> delDetailList) {
		StringBuffer sql = new StringBuffer("select url from t_guide_recommend_detail where status = 'A' and type='1' and goods_id = ?  ");
		if(Utility.isNotEmpty(delDetailList)){
			sql.append(" and id not in (");
			for(TGuideRecommendDetailEntity detail : delDetailList){
				sql.append("'").append(detail.getId()).append("',");
			}
			sql.deleteCharAt(sql.length()-1).append(")");
		}
		sql.append("ORDER BY create_date asc LIMIT 1");
		Map<String,Object> map = this.commonDao.findOneForJdbc(sql.toString(), goodsId);
		if(Utility.isNotEmpty(map)&&Utility.isNotEmpty(map.get("url"))){
			return map.get("url")+"";
		}
		return null;
	}
 	
 	@Override
 	public void updateRecommendInfo(TGuideRecommendInfoEntity tGuideRecommendInfo) {
 		this.commonDao.save(tGuideRecommendInfo);
 		List<TGuideRecommendDetailEntity> recommendDetailsList = tGuideRecommendInfo.getRecommendDetailsList();
 		updateRecommendDetailPics(tGuideRecommendInfo, recommendDetailsList);
 	}
 	
 	/**更新导购推荐图片
	 * @param tGoods
	 * @param recommendDetailsList
	 */
	private void updateRecommendDetailPics(TGuideRecommendInfoEntity tGuideRecommendInfo,List<TGuideRecommendDetailEntity> recommendDetailsList) {
		List<TGuideRecommendDetailEntity> addList = new ArrayList<TGuideRecommendDetailEntity>();//新增的
		List<TGuideRecommendDetailEntity> oldList = this.commonDao.findByProperty(TGuideRecommendDetailEntity.class, "recommendId", tGuideRecommendInfo.getId());
		List<TGuideRecommendDetailEntity> delDetailList = new ArrayList<TGuideRecommendDetailEntity>();//删除的
		if(Utility.isNotEmpty(recommendDetailsList)){
			for(TGuideRecommendDetailEntity detail : recommendDetailsList){
				if(StringUtil.isEmpty(detail.getId())){//新增的
					detail.setGoodsId(tGuideRecommendInfo.getGoodsId());
					detail.setRecommendId(tGuideRecommendInfo.getId());
					detail.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					if(TGuideRecommendDetailEntity.TYPE_4.equals(detail.getType())){//视频
						detail.setCoverUrl(detail.getUrl()+"?vframe/png/offset/1|imageslim");//第一秒的图片作为封面图(瘦身图)只支持华东 bucket
	 					detail.setPlayTime(this.getVideoTime(detail.getUrl()));
	 				}
					addList.add(detail);
				}
			}
			for(TGuideRecommendDetailEntity old:oldList){
				String delFlag = "Y";//删除标识
				for(TGuideRecommendDetailEntity detail:recommendDetailsList){
						if(StringUtil.isNotEmpty(detail.getId())&&old.getId().equals(detail.getId())){
							delFlag = "N";
							break;
						}
				}
				if("Y".equals(delFlag)){
					delDetailList.add(old);
				}
			}
		}else{
			if(Utility.isNotEmpty(oldList)){//全部删除
				delDetailList = oldList;
			}
		}
		if(Utility.isNotEmpty(addList)){
			this.commonDao.batchSave(addList);
		}
		TGoodsEntity goods = this.commonDao.get(TGoodsEntity.class, tGuideRecommendInfo.getGoodsId());
		//判断更新商品小图和状态
 		this.updateGoodsPics(goods,delDetailList,tGuideRecommendInfo.getId());
		if(Utility.isNotEmpty(delDetailList)){//剩下的是需要删除的
			for(TGuideRecommendDetailEntity deleteEntity:delDetailList){
				deleteEntity.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				this.commonDao.updateEntitie(deleteEntity);
				//删除七牛服务器图片
				if(StringUtil.isNotEmpty(deleteEntity.getUrl())){
					try {
						String key = deleteEntity.getUrl().replaceAll(GlobalConstants.DOMAIN, "");
						UploadFile.delete(key);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		addList = null;
		oldList = null;
		delDetailList = null;
	}
}