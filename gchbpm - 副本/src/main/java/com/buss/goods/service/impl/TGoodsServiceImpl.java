package com.buss.goods.service.impl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UploadFile;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.baidu.ueditor.UeditorContent;
import com.buss.activity.entity.TFinActivityWordsEntity;
import com.buss.base.entity.BaseBrandEntity;
import com.buss.goods.entity.TCategoryStoreEntity;
import com.buss.goods.entity.TGoodsDescEntity;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.goods.entity.TGoodsPicEntity;
import com.buss.goods.entity.TGoodsStoreEntity;
import com.buss.goods.entity.TProductInfoEntity;
import com.buss.goods.entity.TSpecHeadersEntity;
import com.buss.goods.service.TGoodsServiceI;
import com.buss.goods.vo.GoodsStockImportVo;
import com.buss.goods.vo.GoodsWordsImportVo;
import com.buss.goods.vo.LowestPriceImportVo;
import com.buss.goods.vo.TGoodsGroupVo;
import com.buss.goods.vo.TGoodsImportVo;
import com.buss.goods.vo.TGoodsSellVo;
import com.buss.goods.vo.TGoodsStoreVo;
import com.buss.goods.vo.TNewGoodsImportVo;
import com.buss.newGoods.entity.TGuideRecommendDetailEntity;
import com.buss.price.entity.TGroupPriceChangeEntity;
import com.buss.price.entity.TPriceChangeEntity;
import com.qiniu.util.Auth;

import cn.redis.service.RedisService;
import common.DBUtil;
import common.GlobalConstants;

/**
 * @author liuxing
 *
 */
@Service("tGoodsService")
@Transactional
public class TGoodsServiceImpl extends CommonServiceImpl implements TGoodsServiceI {
	@Autowired
	private RedisService redisService;
//	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private static final String ACCESS_KEY = ResourceUtil.getConfigByName("QN_ACCESS_KEY");;
	private static final String SECRET_KEY = ResourceUtil.getConfigByName("QN_SECRET_KEY");
	//要上传的空间
	private static final String bucket = ResourceUtil.getConfigByName("QN_bucketname");
	
 	@Override
 	public void doUp(TGoodsEntity tGoods) {
 		tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_4);
		tGoods.setUpdateDate(DateUtils.gettimestamp());
		tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());//上架时间
		Long sortNum = Long.valueOf(DateUtils.date2Str(DateUtils.gettimestamp(), DateUtils.yyyymmddhhmmss));
		tGoods.setSortNum(sortNum);
		this.commonDao.saveOrUpdate(tGoods);
		//更新类目库存
 		TCategoryStoreEntity store = new TCategoryStoreEntity();
 		String hql = "from TCategoryStoreEntity where topCategoryId ='"+tGoods.getTopCategoryId()+"' and subCategoryId ='"+tGoods.getSubCategoryId()
 			+"' and thridCategoryId ='"+tGoods.getThridCategoryId()+"' and retailerId = '"+tGoods.getRetailerId()+"'";
 		List<TCategoryStoreEntity> storeList = this.commonDao.findByQueryString(hql);
 		if(storeList.size()==0){
 			store.setRetailerId(tGoods.getRetailerId());
 			TSUser retailer = this.commonDao.get(TSUser.class, tGoods.getRetailerId());
 			store.setRetailType(retailer.getRetailerType());
 			store.setTopCategoryId(tGoods.getTopCategoryId());
 			store.setSubCategoryId(tGoods.getSubCategoryId());
 			store.setThridCategoryId(tGoods.getThridCategoryId());
 			store.setStore(tGoods.getGoodsStock());
 			this.commonDao.save(store);
 		}else{
 			store = storeList.get(0);
 			store.setStore((Utility.isEmpty(store.getStore())?BigDecimal.ZERO:store.getStore()).add(tGoods.getGoodsStock()));
 			this.commonDao.updateEntitie(store);
 		}
 	}
 	
 	/* 
 	 * 下架并删除活动中的相关商品，取消相关未付款的订单，如果录入了库存则要还库存
 	 */
 	@Override
 	public void doDown(TGoodsEntity tGoods) {
 		/*//删除活动商品
 		this.updateBySqlString("update t_activity_goods set status = 'I' where goods_id ='"+tGoods.getId()+"'");
 		//查询商品相关的订单
 		List<Map<String, Object>> orderList = this.commonDao.findForJdbc("select DISTINCT(order_id) oId from t_order_detail where order_status = '1' and `status` = 'A' and goods_id=?", tGoods.getId());
 		BigDecimal store = BigDecimal.ZERO;
 		for(Map<String, Object> map : orderList){
 			Object oId =map.get("oId");
 			if(Utility.isNotEmpty(oId)){
 				//取消订单
 				this.updateBySqlString("update t_order_info set order_status = '9',update_date='"+Utility.getCurrentTimestamp()+"' where id ='"+oId+"'");
 				this.updateBySqlString("update t_order_detail set order_status = '9',update_date='"+Utility.getCurrentTimestamp()+"' where order_id ='"+oId+"' and status='A'");
 				//库存明细
 				List<Map<String, Object>> list = this.findForJdbc("select d.goods_id,d.spec_id,s.store,d.quantity from t_order_detail d LEFT JOIN t_goods_store s on d.spec_id = s.id where d.order_id =? and d.status='A'", oId);
 				for(Map<String, Object> storeMap : list){
 					if(Utility.isNotEmpty(storeMap.get("store"))){//有录入库存则还库存
 						this.updateBySqlString("update t_goods_store set store = store + "+storeMap.get("store")+",update_date='"+Utility.getCurrentTimestamp()+"' where id ='"+storeMap.get("spec_id")+"' ");
 						store = store.add(new BigDecimal(storeMap.get("store")+""));
 					}
 				}
 				//关闭微信和支付宝的订单
 				AliPay.closeAlipayOrder(oId.toString());
 				WeCharPay.closeWeChatOrder(oId.toString());
 			}
 		}
 		if(Utility.isNotEmpty(tGoods.getGoodsStock())){//有录入库存则要还库存
 			tGoods.setGoodsStock(tGoods.getGoodsStock().add(store));
 		}*/
 		//更新状态和库存
 		this.updateEntitie(tGoods);
 	}
 	
 	
	
	/** 
	 * 保存商品（包括d+商品）和组合(组合的款号为单品款号的拼接)
	 */
	@Override
	public void saveNewGoods(TGoodsEntity tGoods,TSpecHeadersEntity tSpecHeader) {
		tGoods.setId(Utility.getUUID());
		tGoods.setBrandName(Utility.getReplacedSingleQuotes(tGoods.getBrandName()));
		tGoods.setCurrentPrice(tGoods.getOriginalPrice());//接口都是取的现价，避免接口改动
		tGoods.setLowestPrice(Utility.isEmpty(tGoods.getLowestPrice())?BigDecimal.ZERO:tGoods.getLowestPrice());
		tGoods.setLowestPriceDiscount(tGoods.getLowestPriceDiscount().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP ));
		//推荐图片
		List<TGuideRecommendDetailEntity> recommendDetailsList = tGoods.getRecommendDetailsList();
		String retailerEdition = ResourceUtil.getRetailerEdition();
		if(retailerEdition.equals(TSUser.RETAILER_EDITION_1)){//标准版
			TGoodsDescEntity descDetail = tGoods.getDesc();//20170302尖刀 零售商 商品描述
			if(Utility.isNotEmpty(descDetail)&&Utility.isNotEmpty(descDetail.getGoodsDesc())){//有录入商品详情
				//保存描述    20170302尖刀 零售商 商品描述
				descDetail.setGoodsDesc(this.clearFirstEmptyPtag(descDetail.getGoodsDesc()));
				descDetail.setGoodsId(tGoods.getId());
				this.commonDao.save(descDetail);
			}
		}
		if(TGoodsEntity.PUBLISH_STATUS_1.equals(tGoods.getPublishStatus())){//发布
			tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_4);//上架
			tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());//上架时间
			//图片少于5张则是待上架
			if(Utility.isEmpty(recommendDetailsList)||recommendDetailsList.size()<5){
				tGoods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_3);//待上架
				tGoods.setGoodsUpdateTime(null);
			}
			if(retailerEdition.equals(TSUser.RETAILER_EDITION_1)){
				tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_4);//上架
			}
		}else if(TGoodsEntity.PUBLISH_STATUS_2.equals(tGoods.getPublishStatus())){
			tGoods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_0);//草稿箱中
//		}else if(TGoodsEntity.PUBLISH_STATUS_3.equals(tGoods.getPublishStatus())){
//			tGoods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_3);//待上架
		}
		
		//设置小图
		if(Utility.isEmpty(tGoods.getSmallPic())&&Utility.isNotEmpty(recommendDetailsList)){
			tGoods.setSmallPic(recommendDetailsList.get(0).getUrl());
		}
		//组合明细
//		List<TGoodsPicEntity> tGoodsPicDetails = tGoods.gettGoodsPicDetails();
//		if(Utility.isNotEmpty(tGoodsPicDetails)){
//			tGoods.setCurrentPrice(tGoods.getGroupPrice());//默认现价=组合价
//		}
		//活动价
//		if(Utility.isEmpty(tGoods.getActivityPrice())){
//			tGoods.setActivityPrice(tGoods.getCurrentPrice());
//		}
		Long sortNum = Long.valueOf(DateUtils.date2Str(DateUtils.gettimestamp(), DateUtils.yyyymmddhhmmss));
		tGoods.setSortNum(sortNum);
		//保存导购推荐商品图片
		if(Utility.isNotEmpty(recommendDetailsList)){
			for (int i = 0; i < recommendDetailsList.size(); i++) {
				TGuideRecommendDetailEntity tRecDetail = recommendDetailsList.get(i);
				tRecDetail.setGoodsId(tGoods.getId());
				tRecDetail.setStatus(GlobalConstants.STATUS_ACTIVE);
				this.commonDao.save(tRecDetail);
			}
		}
		
		this.save(tGoods);
		recommendDetailsList = null;
		//保存描述(上新云仓商品描述 真特色、真用途、真权威、真服务)
//		List<TGoodsDescEntity> descList = tGoods.getDescList();
//		if(Utility.isNotEmpty(descList)){
//			for(TGoodsDescEntity desc : descList){
//				desc.setGoodsDesc(this.clearFirstEmptyPtag(desc.getGoodsDesc()));
//				desc.setGoodsId(tGoods.getId());
//				this.commonDao.save(desc);
//			}
//		}
//		descList = null;
		//d+保存产品信息
		List<TProductInfoEntity> productInfoList = tGoods.getProductInfoList();
		if(Utility.isNotEmpty(productInfoList)){
			for(TProductInfoEntity tProduct : productInfoList){
				tProduct.setStatus(common.GlobalConstants.STATUS_ACTIVE);
				tProduct.setGoodsId(tGoods.getId());
				this.commonDao.save(tProduct);
			}
		}
		productInfoList = null;
		//保存规格表头
		if(Utility.isNotEmpty(tSpecHeader)){
			tSpecHeader.setGoodsId(tGoods.getId());
			this.commonDao.save(tSpecHeader);
		}
		//保存规格库存
		List<TGoodsStoreEntity> tGoodsStoreDetails = tGoods.gettGoodsStoreDetails();
		if(Utility.isNotEmpty(tGoodsStoreDetails)){
			for(TGoodsStoreEntity store:tGoodsStoreDetails){
				if(Utility.isNotEmpty(store.getSpecificationOne())&&!GlobalConstants.STATUS_INACTIVE.equals(store.getStatus())){
					store.setGoodsId(tGoods.getId());
					store.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					store.setRetailerId(tGoods.getRetailerId());
					this.commonDao.save(store);
				}
			}
		}
		tGoodsStoreDetails = null;
		//保存单品明细（组合）
//		if(Utility.isNotEmpty(tGoodsPicDetails)){
//			for(TGoodsPicEntity pic:tGoodsPicDetails){
//				pic.setGoodsId(tGoods.getId());
//				pic.setStatus("A");
//				this.commonDao.save(pic);
//			}
//		}
//		tGoodsPicDetails = null;
		//保存商品话术
		List<TFinActivityWordsEntity> tGoodsWordsDetails = tGoods.gettGoodsWordsDetails();
		if(Utility.isNotEmpty(tGoodsWordsDetails)){
			for(TFinActivityWordsEntity goodsWords: tGoodsWordsDetails){
				if(Utility.isNotEmpty(goodsWords.getWords())){
					goodsWords.setFinActId(tGoods.getId());
					this.commonDao.save(goodsWords);
				}
			}
			tGoodsWordsDetails = null;
		}
		//更新组合中的商品明细
		this.updateGoodsPicDetails(tGoods);
	}
	
	
	/**
	 * 更新可视类目
	 * 
	 * @param request
	 * @return
	 */
	/*private void updateVisibleCategs(TGoodsEntity tGoods,List<TVisibleCategriesEntity> list) {
		if(!Utility.isEmpty(list)){
			for(TVisibleCategriesEntity entity : list){
				entity.setGoodsId(tGoods.getId());
			}
			List<TVisibleCategriesEntity> visibleList = this.commonDao.findByProperty(TVisibleCategriesEntity.class, "goodsId",	tGoods.getId());
			if(!Utility.isEmpty(visibleList)){
				this.commonDao.deleteAllEntitie(visibleList);
			}
			this.commonDao.batchSave(list);
		}
	}*/
 	
	
	/*@Override
 	public List<TGoodsAttrEntity> getGoodsAttrs(String goodsId){
 		List<TGoodsAttrEntity> tGoodsAttrDetails = new ArrayList<TGoodsAttrEntity>();
 		tGoodsAttrDetails = this.commonDao.findByProperty(TGoodsAttrEntity.class, "goodsId", goodsId);
 		return tGoodsAttrDetails;
 	}*/
	
	@Override
 	public List<TGoodsStoreEntity> getGoodsStores(String goodsId){
 		List<TGoodsStoreEntity> tGoodsStoreDetails = new ArrayList<TGoodsStoreEntity>();
 		tGoodsStoreDetails = this.commonDao.findByProperty(TGoodsStoreEntity.class, "goodsId", goodsId);
 		return tGoodsStoreDetails;
 	}
	
	@Override
 	public List<TGoodsPicEntity> getGoodsPics(String goodsId){
 		String sql = "select p.id,p.goods_id AS goodsId,p.detail_goods_id AS detailGoodsId,p.group_price as groupPrice,g.small_pic AS picUrl,g.goods_code as goodsCode,g.lowest_price AS lowestPrice,"
 				+"g.original_price AS originalPrice,g.current_price AS currentPrice,g.brand_code as brandCode FROM t_goods_pic p LEFT JOIN t_goods g ON p.detail_goods_id = g.id WHERE p.`status` = 'A' and p.goods_id='"+goodsId+"'";
 		Query query = this.getSession().createSQLQuery(sql);
 		query.setResultTransformer(Transformers.aliasToBean(TGoodsPicEntity.class));
 		sql = null;
 		return query.list();
 	}
	
	@Override
	public TGoodsDescEntity getGoodsDesc(String goodsId){
		TGoodsDescEntity tGoodsDesc = new TGoodsDescEntity();
		tGoodsDesc = this.commonDao.findUniqueByProperty(TGoodsDescEntity.class, "goodsId", goodsId);
 		return tGoodsDesc;
 	}

	@Override
	public void deleteGoods(String goodsId) {
		TSUser user = ResourceUtil.getSessionUserName();
		String sql_goods = this.commonDao.getDeleteSqlForUpdate("t_goods","id", goodsId, user);
		String sql_goods_attrs = this.commonDao.getDeleteSqlForUpdate("t_goods_attr","goods_id", goodsId, user);
		String sql_goods_stores = this.commonDao.getDeleteSqlForUpdate("t_goods_store","goods_id", goodsId, user);
		String sql_goods_pics = this.commonDao.getDeleteSqlForUpdate("t_goods_pic","goods_id", goodsId, user);
		this.commonDao.updateBySqlString(sql_goods);
		this.commonDao.updateBySqlString(sql_goods_attrs);
		this.commonDao.updateBySqlString(sql_goods_stores);
		this.commonDao.updateBySqlString(sql_goods_pics);
	}

	
	
	@Override
	public void updateNewGoods(TGoodsEntity tGoods) {
		tGoods.setBrandName(Utility.getReplacedSingleQuotes(tGoods.getBrandName()));
		tGoods.setCurrentPrice(tGoods.getOriginalPrice());//接口都是取的现价，避免接口改动
		tGoods.setLowestPrice(Utility.isEmpty(tGoods.getLowestPrice())?BigDecimal.ZERO:tGoods.getLowestPrice());
		tGoods.setLowestPriceDiscount(tGoods.getLowestPriceDiscount().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP ));
		//图片
		List<TGuideRecommendDetailEntity> recommendDetailsList = tGoods.getRecommendDetailsList();
		String retailerEdition = ResourceUtil.getRetailerEdition();
		if(retailerEdition.equals(TSUser.RETAILER_EDITION_1)){//标准版
			TGoodsDescEntity descDetail = tGoods.getDesc();//20170302尖刀 零售商 商品描述
//			tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_3);//待上架
			if(Utility.isNotEmpty(descDetail)&&Utility.isNotEmpty(descDetail.getGoodsDesc())){//有录入商品详情
				//保存描述    20170302尖刀 零售商 商品描述
				descDetail.setGoodsDesc(this.clearFirstEmptyPtag(descDetail.getGoodsDesc()));
				if(Utility.isEmpty(descDetail.getId())){
					this.commonDao.save(descDetail);
				}else{
					this.commonDao.updateEntitie(descDetail);
				}
			}
		}
		if(TGoodsEntity.PUBLISH_STATUS_1.equals(tGoods.getPublishStatus())){
			tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_4);//上架
			//图片少于5张则是待上架
			if(Utility.isEmpty(recommendDetailsList)||recommendDetailsList.size()<5){
				tGoods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_3);//待上架
				tGoods.setGoodsUpdateTime(null);
			}
			if(retailerEdition.equals(TSUser.RETAILER_EDITION_1)){
				tGoods.setGoodsStatus(com.buss.goods.entity.TGoodsEntity.GOODS_STATUS_4);//上架
			}
		}else if(TGoodsEntity.PUBLISH_STATUS_2.equals(tGoods.getPublishStatus())){
			tGoods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_0);//草稿箱中
//		}else if(TGoodsEntity.PUBLISH_STATUS_3.equals(tGoods.getPublishStatus())){
//			tGoods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_3);//待上架
		}
//		if(TGoodsEntity.GOODS_TYPE_2.equals(tGoods.getGoodsType())){
//			tGoods.setCurrentPrice(tGoods.getGroupPrice());//默认现价=组合价
//		}
		if(TGoodsEntity.GOODS_STATUS_4.equals(tGoods.getGoodsStatus())){
			tGoods.setGoodsUpdateTime(DateUtils.gettimestamp());//上架时间
		}
		Long sortNum = Long.valueOf(DateUtils.date2Str(DateUtils.gettimestamp(), DateUtils.yyyymmddhhmmss));
		tGoods.setSortNum(sortNum);
		//更新导购推荐的图片
		this.updateNewGoodsPics(tGoods,recommendDetailsList);
		this.updateEntitie(tGoods);
		//更新可视类目
//		this.updateVisibleCategs(tGoods,list);
		//活动价发生改变则记录
//			if(tGoods.getPreActivityPrice().compareTo(tGoods.getActivityPrice())!=0){
//				this.recordPriceChange(tGoods,TPriceChangeEntity.PRICETYPE_2);
//			}
		/*//单品
		if(TGoodsEntity.GOODS_TYPE_1.equals(tGoods.getGoodsType())){
			//现价发生改变则记录
			if(tGoods.getPrePrice().compareTo(tGoods.getCurrentPrice())!=0){
				this.recordPriceChange(tGoods,TPriceChangeEntity.PRICETYPE_1);
			}
			//最低价发生改变则记录
			if(tGoods.getPreLowestPrice().compareTo(tGoods.getLowestPrice())!=0){
				this.recordPriceChange(tGoods,TPriceChangeEntity.PRICETYPE_3);
			}
		}else{//组合
			//组合价格变动则进行记录
			List<TGoodsPicEntity> changeList = this.getChangeList(tGoods);
			if(!Utility.isEmpty(changeList)){
				this.recordGroupPriceChange(tGoods,changeList);
			}
		}*/
		//更新库存
		this.updateGoodsStore(tGoods);
		//更新话术
		this.updateGoodsWords(tGoods);
		//更新组合中的商品明细
		this.updateGoodsPicDetails(tGoods);
	}
	

	/**更新话术
	 * @param tGoods
	 */
	private void updateGoodsWords(TGoodsEntity tGoods) {
		List<TFinActivityWordsEntity> tGoodsWordsDetails = tGoods.gettGoodsWordsDetails();
		List<TFinActivityWordsEntity> oldWords = this.commonDao.findByProperty(TFinActivityWordsEntity.class, "finActId", tGoods.getId());
		if(Utility.isNotEmpty(tGoodsWordsDetails)){
			for(TFinActivityWordsEntity old:oldWords){
				String delFlag = "Y";//删除标识
				for(TFinActivityWordsEntity detail:tGoodsWordsDetails){
						if(StringUtil.isNotEmpty(detail.getId())&&old.getId().equals(detail.getId())){
							delFlag = "N";
							if("Y".equals(detail.getChanged())){//更新
								old.setWords(detail.getWords());
								old.setSortNum(detail.getSortNum());
								this.commonDao.updateEntitie(old);
							}
							break;
						}
				}
				if("Y".equals(delFlag)){
					old.setStatus(common.GlobalConstants.STATUS_INACTIVE);
					this.commonDao.updateEntitie(old);
				}
			}
			for(TFinActivityWordsEntity detail:tGoodsWordsDetails){
				if(Utility.isEmpty(detail.getId())&&Utility.isNotEmpty(detail.getWords())){//新增的
					detail.setFinActId(tGoods.getId());
					this.commonDao.save(detail);
				}
			}
		}else{//全部删除
			for(TFinActivityWordsEntity old:oldWords){
				old.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				this.commonDao.updateEntitie(old);
			}
		}
		
	}
	
	/**更新组合中的商品明细
	 * @param tGoods
	 */
	private void updateGoodsPicDetails(TGoodsEntity tGoods) {
		List<TGoodsPicEntity> tGoodsPicDetails = tGoods.gettGoodsPicDetails();
		if(Utility.isNotEmpty(tGoodsPicDetails)){
			for(TGoodsPicEntity detail:tGoodsPicDetails){
				if(StringUtil.isEmpty(detail.getId())){//新增的
					detail.setGoodsId(tGoods.getId());
					detail.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					this.commonDao.save(detail);
				}
			}
			List<TGoodsPicEntity> oldPics = this.commonDao.findByProperty(TGoodsPicEntity.class, "goodsId", tGoods.getId());
			List<TGoodsPicEntity> deleteEntityList = new ArrayList<TGoodsPicEntity>();//删除的
			for(TGoodsPicEntity old:oldPics){
				String delFlag = "Y";//删除标识
				for(TGoodsPicEntity detail:tGoodsPicDetails){
						if(StringUtil.isNotEmpty(detail.getId())&&old.getId().equals(detail.getId())){
							delFlag = "N";
							if(old.getGroupPrice().compareTo(detail.getGroupPrice())!=0){//修改
								old.setGroupPrice(detail.getGroupPrice());
								this.commonDao.updateEntitie(old);
							}
							break;
						}
				}
				if("Y".equals(delFlag)){
					deleteEntityList.add(old);
				}
			}
			if(Utility.isNotEmpty(deleteEntityList)){//剩下的是需要删除的
				for(TGoodsPicEntity deleteEntity:deleteEntityList){
					deleteEntity.setStatus(common.GlobalConstants.STATUS_INACTIVE);
					this.commonDao.updateEntitie(deleteEntity);
				}
			}
		}
	}


	/**更新导购推荐的图片
	 * @param tGoods
	 * @param recommendDetailsList
	 */
	private void updateNewGoodsPics(TGoodsEntity tGoods,List<TGuideRecommendDetailEntity> recommendDetailsList) {
		List<TGuideRecommendDetailEntity> addList = new ArrayList<TGuideRecommendDetailEntity>();//新增的
		List<TGuideRecommendDetailEntity> oldList = this.commonDao.findByProperty(TGuideRecommendDetailEntity.class, "goodsId", tGoods.getId());
		List<TGuideRecommendDetailEntity> deleteEntityList = new ArrayList<TGuideRecommendDetailEntity>();//删除的
		List<TGuideRecommendDetailEntity> updateSortList = new ArrayList<TGuideRecommendDetailEntity>();//更新排序
		Set<String> guideRecommendInfoIds = new HashSet<String>();
		if(Utility.isNotEmpty(recommendDetailsList)){
			tGoods.setSmallPic(recommendDetailsList.get(0).getUrl());//更新小图
			for(TGuideRecommendDetailEntity detail : recommendDetailsList){
				if(StringUtil.isEmpty(detail.getId())){//新增的
					detail.setGoodsId(tGoods.getId());
					detail.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					addList.add(detail);
				}
			}
			for(TGuideRecommendDetailEntity old:oldList){
				String delFlag = "Y";//删除标识
				for(TGuideRecommendDetailEntity detail:recommendDetailsList){
						if(StringUtil.isNotEmpty(detail.getId())&&old.getId().equals(detail.getId())){
							delFlag = "N";
							updateSortList.add(detail);//添加到修改排序列表
							break;
						}
				}
				if("Y".equals(delFlag)){//添加到删除列表
					if(Utility.isNotEmpty(old.getRecommendId())){
						guideRecommendInfoIds.add(old.getRecommendId());
					}
					deleteEntityList.add(old);
				}
			}
		}else{//全部删除
			//删除推荐明细及推荐主表记录
			if(Utility.isNotEmpty(oldList)){
				deleteEntityList = oldList;
				for(TGuideRecommendDetailEntity old:oldList){
					if(Utility.isNotEmpty(old.getRecommendId())){
						guideRecommendInfoIds.add(old.getRecommendId());
					}
				}
			}
		}
		if(Utility.isNotEmpty(addList)){//后台新增导购推荐图片
			this.commonDao.batchSave(addList);
		}
		if(Utility.isNotEmpty(deleteEntityList)){//是需要删除的
			for(TGuideRecommendDetailEntity deleteEntity:deleteEntityList){
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
		if(guideRecommendInfoIds.size()>0){//有删除管家推荐的图片，需要判断明细是否全部删除
			for(String recommendId : guideRecommendInfoIds){//循环推荐主表id
				boolean exist = false;
				for(TGuideRecommendDetailEntity detail : recommendDetailsList){
					if(recommendId.equals(detail.getRecommendId())){//如果明细还存在推荐主表id则标识明细未删完，否则要删除推荐主表记录
						exist = true;
						break;
					}
				}
				if(!exist){//需要删除
					this.commonDao.updateBySqlString("update t_guide_recommend_info set status = 'I',update_date = '"
							+Utility.getCurrentTimestamp()+"' where id = '"+recommendId+"'");
				}
			}
		}
		//更新排序
		if(updateSortList.size()>0){
			StringBuffer idStr = new StringBuffer();
			StringBuffer updateSortSql = new StringBuffer("update t_guide_recommend_detail set idx = CASE id ");
			for(TGuideRecommendDetailEntity vo : updateSortList){
				idStr.append(",'").append(vo.getId()).append("'");
				updateSortSql.append(" WHEN '").append(vo.getId()).append("' THEN ").append(vo.getIdx());
			}
			updateSortSql.append(" END WHERE id in (").append(idStr.substring(1)).append(")");
			this.commonDao.updateBySqlString(updateSortSql.toString());
		}
		addList = null;
		oldList = null;
		deleteEntityList = null;
		updateSortList = null;
	}

	/**
	 * 
	 *获取变动列表
	 * @param tGoods
	 * @return
	 */
	private List<TGoodsPicEntity> getChangeList(TGoodsEntity tGoods) {
		
		 List<TGoodsPicEntity> currDetails = tGoods.gettGoodsPicDetails();
		    if(Utility.isEmpty(currDetails)){
		    	currDetails =  new ArrayList<TGoodsPicEntity>();
		    }
		    List<TGoodsPicEntity> oldDetails = new ArrayList<TGoodsPicEntity>();
		    List<TGoodsPicEntity> preDetails = this.commonDao.findByProperty(TGoodsPicEntity.class, "goodsId", tGoods.getId());
			List<TGoodsPicEntity> changeList = new ArrayList<TGoodsPicEntity>();//都有但是价格有变化
			
			//新增元素
			for(TGoodsPicEntity curDetail:currDetails){
				if(Utility.isEmpty(curDetail.getId()))
				{
					TGoodsPicEntity entity = new TGoodsPicEntity();
					entity.setId("新增");
					entity.setGoodsCode(curDetail.getGoodsCode());
					entity.setOriginalPrice(BigDecimal.ZERO);
					entity.setCurrentPrice(curDetail.getGroupPrice());
					changeList.add(entity);
				}else{
					oldDetails.add(curDetail);
				}
			}
			
			for(TGoodsPicEntity preDetail:preDetails){
				String flag = "N";//没有相同明细
				for(TGoodsPicEntity oldDetail:oldDetails){
					if(preDetail.getId().equals(oldDetail.getId())){//都有，且价格有变动
						flag = "Y";
						BigDecimal preGroupPrice = preDetail.getGroupPrice();
						preGroupPrice=preGroupPrice==null?BigDecimal.ZERO:preGroupPrice;
						BigDecimal curGroupPrice = oldDetail.getGroupPrice();
						curGroupPrice=curGroupPrice==null?BigDecimal.ZERO:curGroupPrice;
						if(preGroupPrice.compareTo(curGroupPrice)!=0){
							TGoodsPicEntity entity = new TGoodsPicEntity();
//							entity.setId(preDetail.getId());
							entity.setId("修改");
							entity.setGoodsCode(oldDetail.getGoodsCode());
							entity.setOriginalPrice(preDetail.getGroupPrice());
							entity.setCurrentPrice(oldDetail.getGroupPrice());
							changeList.add(entity);
						}else{
							//
						}
						break;
					}
				}
				if("N"==flag){//比原来少的部分
					TGoodsPicEntity entity = new TGoodsPicEntity();
					TGoodsEntity g = this.get(TGoodsEntity.class, preDetail.getDetailGoodsId());
					entity.setId("删除");
					entity.setGoodsCode(g.getGoodsCode());
					entity.setOriginalPrice(preDetail.getGroupPrice());
					entity.setCurrentPrice(BigDecimal.ZERO);
					changeList.add(entity);
				}
			}
		return 	changeList;
	}

	//现价发生改变则记录(type 1:现价，2：活动价)
	private void recordPriceChange(TGoodsEntity tGoods,String type) {
			TPriceChangeEntity priceChange = new TPriceChangeEntity();
			priceChange.setGoodsId(tGoods.getId());
			priceChange.setGoodsCode(tGoods.getGoodsCode());
			priceChange.setPriceType(type);
			if(TPriceChangeEntity.PRICETYPE_1.equals(type)){
				priceChange.setPrePrice(tGoods.getPrePrice());
				priceChange.setCurrentPrice(tGoods.getCurrentPrice());
			}else if(TPriceChangeEntity.PRICETYPE_2.equals(type)){
				priceChange.setPrePrice(tGoods.getPreActivityPrice());
				priceChange.setCurrentPrice(tGoods.getActivityPrice());
			}else if(TPriceChangeEntity.PRICETYPE_3.equals(type)){
				priceChange.setPrePrice(tGoods.getPreLowestPrice());
				priceChange.setCurrentPrice(tGoods.getLowestPrice());
			}
			priceChange.setStatus(common.GlobalConstants.STATUS_ACTIVE);
			priceChange.setRetailerId(tGoods.getRetailerId());
			this.commonDao.save(priceChange);
	}
	
	//组合价发生改变则记录
	private void recordGroupPriceChange(TGoodsEntity tGoods, List<TGoodsPicEntity> changeList) {
			TGroupPriceChangeEntity groupPriceChange = new TGroupPriceChangeEntity();
			groupPriceChange.setGoodsId(tGoods.getId());
			groupPriceChange.setGoodsName(tGoods.getGoodsName());
			groupPriceChange.setPrePrice(tGoods.getPrePrice());
			groupPriceChange.setCurrentPrice(tGoods.getGroupPrice());
			groupPriceChange.setStatus(common.GlobalConstants.STATUS_ACTIVE);
			groupPriceChange.setRetailerId(tGoods.getRetailerId());
			groupPriceChange.setGroupSource(tGoods.getGroupSource());
			groupPriceChange.setDetailJson(this.getDetailJson(changeList));
			this.commonDao.save(groupPriceChange);
	}
	
	/**获取组合明细价格变动的json
	 * @param tGoods
	 * @param t
	 * @return
	 */
	private String getDetailJson(List<TGoodsPicEntity> changeList) {
		/*List<TGoodsPicEntity> currDetails = new ArrayList<TGoodsPicEntity>();
		for(TGoodsPicEntity e : tGoods.gettGoodsPicDetails()){
			TGoodsPicEntity entity = new TGoodsPicEntity();
			entity = e;
			currDetails.add(entity);
		}
		List<TGoodsPicEntity> preDetails = this.commonDao.findByProperty(TGoodsPicEntity.class, "goodsId", tGoods.getId());
		List<TGoodsPicEntity> changeList = new ArrayList<TGoodsPicEntity>();//都有但是价格有变化
		
		HashMap<String,Object> map = new HashMap<String,Object>();
			for(TGoodsPicEntity preDetail:preDetails){
				String flag = "N";//没有相同明细
				for(TGoodsPicEntity curDetail:currDetails){
					if(preDetail.getId().equals(curDetail.getId())){//都有，且价格有变动
						flag = "Y";
						BigDecimal preGroupPrice = preDetail.getGroupPrice();
						preGroupPrice=preGroupPrice==null?BigDecimal.ZERO:preGroupPrice;
						BigDecimal curGroupPrice = curDetail.getGroupPrice();
						curGroupPrice=curGroupPrice==null?BigDecimal.ZERO:curGroupPrice;
						if(preGroupPrice.compareTo(curGroupPrice)!=0){
							TGoodsPicEntity entity = new TGoodsPicEntity();
							entity.setId(preDetail.getId());
							entity.setGoodsCode(curDetail.getGoodsCode());
							entity.setOriginalPrice(preDetail.getGroupPrice());
							entity.setCurrentPrice(curDetail.getGroupPrice());
							changeList.add(entity);
						}else{
							//
						}
						currDetails.remove(curDetail);
						break;
					}
				}
				if(flag=="N"){//比原来少的部分
					TGoodsPicEntity entity = new TGoodsPicEntity();
					entity.setId("删除");
					entity.setGoodsCode(preDetail.getGoodsCode());
					entity.setOriginalPrice(preDetail.getGroupPrice());
					entity.setCurrentPrice(BigDecimal.ZERO);
					changeList.add(entity);
				}
			}
			if(!Utility.isEmpty(currDetails)){//比原来多的部分
				for(TGoodsPicEntity curDetail:currDetails){
					TGoodsPicEntity entity = new TGoodsPicEntity();
					entity.setId("新增");
					entity.setGoodsCode(curDetail.getGoodsCode());
					entity.setOriginalPrice(BigDecimal.ZERO);
					entity.setCurrentPrice(curDetail.getGroupPrice());
					changeList.add(entity);
				}
			}*/
		    HashMap<String,Object> map = new HashMap<String,Object>();
			if(!Utility.isEmpty(changeList)){
				for(TGoodsPicEntity detail:changeList){
					map.put(detail.getId()+"——"+detail.getGoodsCode(), "价格："+detail.getOriginalPrice()+"→"+detail.getCurrentPrice());
				}
			}
			if(!Utility.isEmpty(map)){
				return JSONUtils.toJSONString(map);
			}
		return null;
	}

	/**去掉字符串前面的空的p标签和只带<br/>的p标签
	 * @param goodsDesc
	 * @return
	 */
	private String clearFirstEmptyPtag(String goodsDesc) {
		HashMap<String,String> map = new HashMap<String,String>();
		 map.put("result", goodsDesc);
		 do {
			 map =UeditorContent.clearEmpty(map.get("result"));
			 
		} while ("N".equals(map.get("isLast")));
		return map.get("result");
	}

	@Override
	public void updateGoodsStore(TGoodsEntity tGoods) {
		//保存库存
		List<TGoodsStoreEntity> tGoodsStoreDetails = tGoods.gettGoodsStoreDetails();
		if(Utility.isNotEmpty(tGoodsStoreDetails)){
			for(TGoodsStoreEntity store:tGoodsStoreDetails){
				if(Utility.isEmpty(store.getId())){//新增的库存(排除点了新增然后又删除该记录的数据)
					if(Utility.isNotEmpty(store.getSpecificationOne())&&!GlobalConstants.STATUS_INACTIVE.equals(store.getStatus())){
						store.setGoodsId(tGoods.getId());
						store.setStatus(GlobalConstants.STATUS_ACTIVE);
						store.setRetailerId(tGoods.getRetailerId());
						this.commonDao.save(store);
					}
				}else if(Utility.isNotEmpty(store.getSpecificationOne())&&"Y".equals(store.getChanged())){//修改或者删除库存
					if(Utility.isNotEmpty(store.getBarCode())){
						Long count = this.commonDao.getCountForJdbc("select count(1) from t_goods_store where status='A' and retailer_id='"
								+tGoods.getRetailerId()+"' and bar_Code='"+store.getBarCode()+"' and id <>'"+store.getId()+"'");
						if(count>0){
							LogUtil.info("条码"+store.getBarCode()+"已存在");
						}else{
							this.commonDao.updateEntitie(store);
						}
					}else{
						this.commonDao.updateEntitie(store);
					}
				}
			}
			if(Utility.isNotEmpty(tGoods.getGoodsStock())){
				String updateSql = "update t_goods set goods_stock ='"+tGoods.getGoodsStock()+"',goods_update_time = now() where id='"+tGoods.getId()+"'";
				this.updateBySqlString(updateSql);
			}
		}
	}
	/* 修改商品推荐
	 */
	@Override
	public void updateRecommondPics(TGoodsEntity tGoods) {
		//保存系统推荐商品
		/*List<TSysRecmdPicsEntity> sysRecmdPics = tGoods.gettSysRecmdPics();
		List<TSysRecmdPicsEntity> oldSysRecmdPics = commonDao.findByProperty(TSysRecmdPicsEntity.class, "goodsId", tGoods.getId());
		List<TSysRecmdPicsEntity> addEntityList = new ArrayList<TSysRecmdPicsEntity>();//新增的
		if(Utility.isNotEmpty(oldSysRecmdPics)){
//			List<TSysRecmdPicsEntity> deleteEntityList = new ArrayList<TSysRecmdPicsEntity>();//删除的
				for(TSysRecmdPicsEntity old:oldSysRecmdPics){
					String delFlag = "Y";//删除标识
					for(TSysRecmdPicsEntity pic:sysRecmdPics){
						if(StringUtil.isNotEmpty(pic.getId())&&old.getId().equals(pic.getId())){
							delFlag = "N";
							break;
						}
					}
					if("Y".equals(delFlag)){
						old.setStatus("I");
						this.commonDao.updateEntitie(old);
//						deleteEntityList.add(old);
					}
			}
		}
		if(Utility.isNotEmpty(sysRecmdPics)){
			for(TSysRecmdPicsEntity pic:sysRecmdPics){
				if(StringUtil.isEmpty(pic.getId())){
					pic.setGoodsId(tGoods.getId());
					pic.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					addEntityList.add(pic);
				}
			}
			if(Utility.isNotEmpty(addEntityList)){
				this.commonDao.batchSave(sysRecmdPics);
			}
		}
		
		//保存导购推荐商品
		List<TGuideRecmdPicsEntity> guideRecmdPics = tGoods.gettGuideRecmdPics();
		List<TGuideRecmdPicsEntity> addGuidePicsList = new ArrayList<TGuideRecmdPicsEntity>();//新增的
		List<TGuideRecmdPicsEntity> oldguideRecmdPics = commonDao.findByProperty(TGuideRecmdPicsEntity.class, "goodsId", tGoods.getId());
		if(Utility.isNotEmpty(oldguideRecmdPics)){
//			List<TSysRecmdPicsEntity> deleteEntityList = new ArrayList<TSysRecmdPicsEntity>();//删除的
				for(TGuideRecmdPicsEntity old:oldguideRecmdPics){
					String delFlag = "Y";//删除标识
					for(TGuideRecmdPicsEntity pic:guideRecmdPics){
						if(StringUtil.isNotEmpty(pic.getId())&&old.getId().equals(pic.getId())){
							delFlag = "N";
							break;
						}
					}
					if("Y".equals(delFlag)){
						old.setStatus("I");
						this.commonDao.updateEntitie(old);
//						deleteEntityList.add(old);
					}
			}
		}
		if(Utility.isNotEmpty(guideRecmdPics)){
			for(TGuideRecmdPicsEntity pic:guideRecmdPics){
				if(StringUtil.isEmpty(pic.getId())){
					pic.setGoodsId(tGoods.getId());
					pic.setStatus(common.GlobalConstants.STATUS_ACTIVE);
					addGuidePicsList.add(pic);
				}
			}
			if(Utility.isNotEmpty(addGuidePicsList)){
				this.commonDao.batchSave(addGuidePicsList);
			}
		}*/
	}
	
	@Override
	public String saveBatchOfGroup(List<TGoodsGroupVo> listTGoodsEntitys) {
		String msg = null;
		//场景列表
		TSTypegroup sceneGroup = this.commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", "sceneType");
		List<TSType> sceneList = sceneGroup==null?null: sceneGroup.getTSTypes();
		//商品类别
		TSTypegroup goodsTypeGroup = this.commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", "subgdstype");
		List<TSType> goodsTypeList = goodsTypeGroup==null?null: goodsTypeGroup.getTSTypes();
		//一级类目
		List<TSCategoryEntity> topCategoryList = this.commonDao.findByProperty(TSCategoryEntity.class, "level", 1);
		TSUser nowUser = ResourceUtil.getSessionUserName();
		TSUser user = null;//后台人员
		if(common.GlobalConstants.USER_TYPE_02.equals(nowUser.getUserType())){//零售商
			user = nowUser;
		}else if(common.GlobalConstants.USER_TYPE_05.equals(nowUser.getUserType())){//零售商员工
			user = this.commonDao.get(TSUser.class, nowUser.getRetailerId());
		}
		//校验场景
		msg = this.checkScene2(listTGoodsEntitys,sceneList);
		if(msg!=null){
			sceneList = null;
			return msg;
		}
		
		//校验商品类目
		msg = this.checkTopCatgy2(listTGoodsEntitys,topCategoryList);
		if(msg!=null){
			return msg;
		}
		//校验商品类别
		msg = this.checkGoodsType2(listTGoodsEntitys,goodsTypeList);
		if(msg!=null){
			goodsTypeList = null;
			return msg;
		}
		//校验所有单品
		msg = this.checkGoodsDetails(listTGoodsEntitys,user);
		if(msg!=null){
			return msg;
		}
		//校验都通过，保存商品组合
		this.batchSaveGoodsGroup(listTGoodsEntitys,user);
		return msg;
	};
	
	private void batchSaveGoodsGroup(List<TGoodsGroupVo> listTGoodsEntitys, TSUser user) {
		for(TGoodsGroupVo vo : listTGoodsEntitys){
			TGoodsEntity tGoods = new TGoodsEntity();
			BeanUtils.copyProperties(vo, tGoods);
			tGoods.setStatus(common.GlobalConstants.STATUS_ACTIVE);
			if(user!=null){//零售商
				tGoods.setRetailerId(user.getId());
				tGoods.setRetailerCode(user.getUserCode());
				tGoods.setRetailerType(user.getRetailerType());
				tGoods.setRetailerName(user.getRealName());
				tGoods.setCityId(user.getCityId());
				tGoods.setProvinceId(user.getProvinceId());
			}else{
				user = ResourceUtil.getSessionUserName();
				tGoods.setCityId(user.getCityId());
				tGoods.setProvinceId(user.getProvinceId());
			}
			tGoods.setGoodsStatus(TGoodsEntity.GOODS_STATUS_0);//草稿箱中
			tGoods.setGoodsType(TGoodsEntity.GOODS_TYPE_2);//组合
			this.save(tGoods);
			//保存单品明细（组合）
			List<TGoodsPicEntity> tGoodsPicDetails = vo.gettGoodsPicDetails();
			if(Utility.isNotEmpty(tGoodsPicDetails)){
				for(TGoodsPicEntity pic:tGoodsPicDetails){
					pic.setGoodsId(tGoods.getId());
					pic.setStatus("A");
					this.commonDao.save(pic);
				}
			}
		}
		
	}

	/**校验单品编码是否相同，系统是否存在，如果存在，对应的商品信息存入商品明细
	 * @param listTGoodsEntitys
	 * @param user
	 * @return
	 */
	private String checkGoodsDetails(List<TGoodsGroupVo> listTGoodsEntitys,	TSUser user) {
		//同行单品编号不能相同
		int i=1;
		for(TGoodsGroupVo vo : listTGoodsEntitys){
			i++;
			HashSet<String> rowCodeSet = new HashSet<String>();//同一行的编码
			if(!StringUtil.isEmpty(vo.getGoodsDetailOne())){
				String str = vo.getGoodsDetailOne().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品1格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品1的编码："+arr1+"重复";
						}else{
							rowCodeSet.add(arr1);
						}
					}else{
						return "第"+i+"行系统中单品1的价格："+arr2+"不是数字";
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailTwo())){
				String str = vo.getGoodsDetailTwo().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品2格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品2的编码："+arr1+"重复";
						}else{
							rowCodeSet.add(arr1);
						}
					}else{
						return "第"+i+"行系统中单品2的价格："+arr2+"不是数字";
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailThree())){
				String str = vo.getGoodsDetailThree().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品3格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品3的编码："+arr1+"重复";
						}else{
							rowCodeSet.add(arr1);
						}
					}else{
						return "第"+i+"行系统中单品3的价格："+arr2+"不是数字";
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailFour())){
				String str = vo.getGoodsDetailFour().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品4格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品4的编码："+arr1+"重复";
						}else{
							rowCodeSet.add(arr1);
						}
					}else{
						return "第"+i+"行系统中单品4的价格："+arr2+"不是数字";
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailFive())){
				String str = vo.getGoodsDetailFive().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品5格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品5的编码："+arr1+"重复";
						}else{
							rowCodeSet.add(arr1);
						}
					}else{
						return "第"+i+"行系统中单品5的价格："+arr2+"不是数字";
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailSix())){
				String str = vo.getGoodsDetailSix().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品6格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品6的编码："+arr1+"重复";
						}else{
							rowCodeSet.add(arr1);
						}
					}else{
						return "第"+i+"行系统中单品6的价格："+arr2+"不是数字";
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailSeven())){
				String str = vo.getGoodsDetailSeven().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品7格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品7的编码："+arr1+"重复";
						}else{
							rowCodeSet.add(arr1);
						}
					}else{
						return "第"+i+"行系统中单品7的价格："+arr2+"不是数字";
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailEight())){
				String str = vo.getGoodsDetailEight().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品8格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品8的编码："+arr1+"重复";
						}else{
							rowCodeSet.add(arr1);
						}
					}else{
						return "第"+i+"行系统中单品8的价格："+arr2+"不是数字";
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailNine())){
				String str = vo.getGoodsDetailNine().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品9格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品9的编码："+arr1+"重复";
						}else{
							rowCodeSet.add(arr1);
						}
					}else{
						return "第"+i+"行系统中单品9的价格："+arr2+"不是数字";
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailTen())){
				String str = vo.getGoodsDetailTen().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行单品10格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(StringUtil.isFloatNumeric(arr2)){
						if(rowCodeSet.contains(arr1)){
							return "第"+i+"行系统中单品10的编码："+arr1+"重复";
						}else{
							rowCodeSet.clear();
						}
					}else{
						return "第"+i+"行系统中单品10的价格："+arr2+"不是数字";
					}
				}
			}
		}
		//所有的编号必须是系统中登录人能看到的商品
		//所有该零售商（或者所有）的在售商品（单品）编号  此处不同考虑相斥
		String sql = "select code,goods_code,id,original_price,lowest_price,pic_one from t_goods where ";  
		if(user!=null){
			sql +=" retailer_Id ='"+user.getId()+"' and ";
		}
		sql += " goods_type = '"+TGoodsEntity.GOODS_TYPE_1+"' and goods_status = '"+TGoodsEntity.GOODS_STATUS_4+"' and status = 'A'";
		List<Object> list = this.commonDao.findListbySql(sql);
		int n = 1;
		//编码存在的话商品明细记入相关商品信息
		for(TGoodsGroupVo vo : listTGoodsEntitys){
			List<TGoodsPicEntity> tGoodsPicDetails = new ArrayList<TGoodsPicEntity>();
			BigDecimal groupPrice = BigDecimal.ZERO;
			BigDecimal originalPrice = BigDecimal.ZERO;
			n++;
			if(!StringUtil.isEmpty(vo.getGoodsDetailOne())){
				String str = vo.getGoodsDetailOne().replace("，", ",");
				String[] arr = str.split(",");
					String arr1 = arr[0];
					String arr2 = arr[1];
					TGoodsPicEntity pic = null;
					for(Object code:list){
						if(code.getClass().isArray()){
							Object[] goods_arr = (Object[]) code;
							if(arr1.equals(goods_arr[0])){
								pic = new TGoodsPicEntity();
								pic.setGroupPrice(new BigDecimal(arr2));
								pic.setGoodsCode(goods_arr[1].toString());
								pic.setDetailGoodsId(goods_arr[2].toString());
								pic.setOriginalPrice((BigDecimal) (goods_arr[3]));
								pic.setLowestPrice((BigDecimal) goods_arr[4]);
								pic.setPicUrl(goods_arr[5].toString());
								tGoodsPicDetails.add(pic);
								groupPrice = groupPrice.add(pic.getGroupPrice());
								originalPrice = originalPrice.add(pic.getOriginalPrice());
								break;
							}else{
								continue;
							}
						}
					}
					if(Utility.isEmpty(pic)){
						return "第"+n+"行系统中不存在单品1的编码："+arr1+"或者该编码的商品未上架";
					}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailTwo())){
				String str = vo.getGoodsDetailTwo().replace("，", ",");
				String[] arr = str.split(",");
				String arr1 = arr[0];
				String arr2 = arr[1];
				TGoodsPicEntity pic = null;
				for(Object code:list){
					if(code.getClass().isArray()){
						Object[] goods_arr = (Object[]) code;
						if(arr1.equals(goods_arr[0])){
							pic = new TGoodsPicEntity();
							pic.setGroupPrice(new BigDecimal(arr2));
							pic.setGoodsCode(goods_arr[1].toString());
							pic.setDetailGoodsId(goods_arr[2].toString());
							pic.setOriginalPrice((BigDecimal) goods_arr[3]);
							pic.setLowestPrice((BigDecimal) goods_arr[4]);
							pic.setPicUrl(goods_arr[5].toString());
							tGoodsPicDetails.add(pic);
							groupPrice = groupPrice.add(pic.getGroupPrice());
							originalPrice = originalPrice.add(pic.getOriginalPrice());
							break;
						}else{
							continue;
						}
					}
				}
				if(Utility.isEmpty(pic)){
					return "第"+n+"行系统中不存在单品2的编码："+arr1+"或者该编码的商品未上架";
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailThree())){
				String str = vo.getGoodsDetailThree().replace("，", ",");
				String[] arr = str.split(",");
				String arr1 = arr[0];
				String arr2 = arr[1];
				TGoodsPicEntity pic = null;
				for(Object code:list){
					if(code.getClass().isArray()){
						Object[] goods_arr = (Object[]) code;
						if(arr1.equals(goods_arr[0])){
							pic = new TGoodsPicEntity();
							pic.setGroupPrice(new BigDecimal(arr2));
							pic.setGoodsCode(goods_arr[1].toString());
							pic.setDetailGoodsId(goods_arr[2].toString());
							pic.setOriginalPrice((BigDecimal) goods_arr[3]);
							pic.setLowestPrice((BigDecimal) goods_arr[4]);
							pic.setPicUrl(goods_arr[5].toString());
							tGoodsPicDetails.add(pic);
							groupPrice = groupPrice.add(pic.getGroupPrice());
							originalPrice = originalPrice.add(pic.getOriginalPrice());
							break;
						}else{
							continue;
						}
					}
				}
				if(Utility.isEmpty(pic)){
					return "第"+n+"行系统中不存在单品3的编码："+arr1+"或者该编码的商品未上架";
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailFour())){
				String str = vo.getGoodsDetailFour().replace("，", ",");
				String[] arr = str.split(",");
				String arr1 = arr[0];
				String arr2 = arr[1];
				TGoodsPicEntity pic = null;
				for(Object code:list){
					if(code.getClass().isArray()){
						Object[] goods_arr = (Object[]) code;
						if(arr1.equals(goods_arr[0])){
							pic = new TGoodsPicEntity();
							pic.setGroupPrice(new BigDecimal(arr2));
							pic.setGoodsCode(goods_arr[1].toString());
							pic.setDetailGoodsId(goods_arr[2].toString());
							pic.setOriginalPrice((BigDecimal) goods_arr[3]);
							pic.setLowestPrice((BigDecimal) goods_arr[4]);
							pic.setPicUrl(goods_arr[5].toString());
							tGoodsPicDetails.add(pic);
							groupPrice = groupPrice.add(pic.getGroupPrice());
							originalPrice = originalPrice.add(pic.getOriginalPrice());
							break;
						}else{
							continue;
						}
					}
				}
				if(Utility.isEmpty(pic)){
					return "第"+n+"行系统中不存在单品4的编码："+arr1+"或者该编码的商品未上架";
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailFive())){
				String str = vo.getGoodsDetailFive().replace("，", ",");
				String[] arr = str.split(",");
				String arr1 = arr[0];
				String arr2 = arr[1];
				TGoodsPicEntity pic = null;
				for(Object code:list){
					if(code.getClass().isArray()){
						Object[] goods_arr = (Object[]) code;
						if(arr1.equals(goods_arr[0])){
							pic = new TGoodsPicEntity();
							pic.setGroupPrice(new BigDecimal(arr2));
							pic.setGoodsCode(goods_arr[1].toString());
							pic.setDetailGoodsId(goods_arr[2].toString());
							pic.setOriginalPrice((BigDecimal) goods_arr[3]);
							pic.setLowestPrice((BigDecimal) goods_arr[4]);
							pic.setPicUrl(goods_arr[5].toString());
							tGoodsPicDetails.add(pic);
							groupPrice.add(Utility.isEmpty(pic.getGroupPrice())?BigDecimal.ZERO:pic.getGroupPrice());
							originalPrice = originalPrice.add(pic.getOriginalPrice());
							break;
						}else{
							continue;
						}
					}
				}
				if(Utility.isEmpty(pic)){
					return "第"+n+"行系统中不存在单品5的编码："+arr1+"或者该编码的商品未上架";
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailSix())){
				String str = vo.getGoodsDetailSix().replace("，", ",");
				String[] arr = str.split(",");
				String arr1 = arr[0];
				String arr2 = arr[1];
				TGoodsPicEntity pic = null;
				for(Object code:list){
					if(code.getClass().isArray()){
						Object[] goods_arr = (Object[]) code;
						if(arr1.equals(goods_arr[0])){
							pic = new TGoodsPicEntity();
							pic.setGroupPrice(new BigDecimal(arr2));
							pic.setGoodsCode(goods_arr[1].toString());
							pic.setDetailGoodsId(goods_arr[2].toString());
							pic.setOriginalPrice((BigDecimal) goods_arr[3]);
							pic.setLowestPrice((BigDecimal) goods_arr[4]);
							pic.setPicUrl(goods_arr[5].toString());
							tGoodsPicDetails.add(pic);
							groupPrice = groupPrice.add(pic.getGroupPrice());
							originalPrice = originalPrice.add(pic.getOriginalPrice());
							break;
						}else{
							continue;
						}
					}
				}
				if(Utility.isEmpty(pic)){
					return "第"+n+"行系统中不存在单品6的编码："+arr1+"或者该编码的商品未上架";
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailSeven())){
				String str = vo.getGoodsDetailSeven().replace("，", ",");
				String[] arr = str.split(",");
				String arr1 = arr[0];
				String arr2 = arr[1];
				TGoodsPicEntity pic = new TGoodsPicEntity();
				for(Object code:list){
					if(code.getClass().isArray()){
						Object[] goods_arr = (Object[]) code;
						if(arr1.equals(goods_arr[0])){
							pic.setGroupPrice(new BigDecimal(arr2));
							pic.setGoodsCode(goods_arr[1].toString());
							pic.setDetailGoodsId(goods_arr[2].toString());
							pic.setOriginalPrice((BigDecimal) goods_arr[3]);
							pic.setLowestPrice((BigDecimal) goods_arr[4]);
							pic.setPicUrl(goods_arr[5].toString());
							tGoodsPicDetails.add(pic);
							groupPrice = groupPrice.add(pic.getGroupPrice());
							originalPrice = originalPrice.add(pic.getOriginalPrice());
							break;
						}else{
							continue;
						}
					}
				}
				if(Utility.isEmpty(pic)){
					return "第"+n+"行系统中不存在单品7的编码："+arr1+"或者该编码的商品未上架";
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailEight())){
				String str = vo.getGoodsDetailEight().replace("，", ",");
				String[] arr = str.split(",");
				String arr1 = arr[0];
				String arr2 = arr[1];
				TGoodsPicEntity pic = null;
				for(Object code:list){
					if(code.getClass().isArray()){
						Object[] goods_arr = (Object[]) code;
						if(arr1.equals(goods_arr[0])){
							pic = new TGoodsPicEntity();
							pic.setGroupPrice(new BigDecimal(arr2));
							pic.setGoodsCode(goods_arr[1].toString());
							pic.setDetailGoodsId(goods_arr[2].toString());
							pic.setOriginalPrice((BigDecimal) goods_arr[3]);
							pic.setLowestPrice((BigDecimal) goods_arr[4]);
							pic.setPicUrl(goods_arr[5].toString());
							tGoodsPicDetails.add(pic);
							groupPrice = groupPrice.add(pic.getGroupPrice());
							originalPrice = originalPrice.add(pic.getOriginalPrice());
							break;
						}else{
							continue;
						}
					}
				}
				if(Utility.isEmpty(pic)){
					return "第"+n+"行系统中不存在单品8的编码："+arr1+"或者该编码的商品未上架";
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailNine())){
				String str = vo.getGoodsDetailNine().replace("，", ",");
				String[] arr = str.split(",");
				String arr1 = arr[0];
				String arr2 = arr[1];
				TGoodsPicEntity pic = null;
				for(Object code:list){
					if(code.getClass().isArray()){
						Object[] goods_arr = (Object[]) code;
						if(arr1.equals(goods_arr[0])){
							pic = new TGoodsPicEntity();
							pic.setGroupPrice(new BigDecimal(arr2));
							pic.setGoodsCode(goods_arr[1].toString());
							pic.setDetailGoodsId(goods_arr[2].toString());
							pic.setOriginalPrice((BigDecimal) goods_arr[3]);
							pic.setLowestPrice((BigDecimal) goods_arr[4]);
							pic.setPicUrl(goods_arr[5].toString());
							tGoodsPicDetails.add(pic);
							groupPrice.add(pic.getGroupPrice());
							originalPrice = originalPrice.add(pic.getOriginalPrice());
							break;
						}else{
							continue;
						}
					}
				}
				if(Utility.isEmpty(pic)){
					return "第"+n+"行系统中不存在单品9的编码："+arr1+"或者该编码的商品未上架";
				}
			}
			if(!StringUtil.isEmpty(vo.getGoodsDetailTen())){
				String str = vo.getGoodsDetailTen().replace("，", ",");
				String[] arr = str.split(",");
				String arr1 = arr[0];
				String arr2 = arr[1];
				TGoodsPicEntity pic = null;
				for(Object code:list){
					if(code.getClass().isArray()){
						Object[] goods_arr = (Object[]) code;
						if(arr1.equals(goods_arr[0])){
							pic = new TGoodsPicEntity();
							pic.setGroupPrice(new BigDecimal(arr2));
							pic.setGoodsCode(goods_arr[1].toString());
							pic.setDetailGoodsId(goods_arr[2].toString());
							pic.setOriginalPrice((BigDecimal) goods_arr[3]);
							pic.setLowestPrice((BigDecimal) goods_arr[4]);
							pic.setPicUrl(goods_arr[5].toString());
							tGoodsPicDetails.add(pic);
							groupPrice = groupPrice.add(pic.getGroupPrice());
							originalPrice = originalPrice.add(pic.getOriginalPrice());
							break;
						}else{
							continue;
						}
					}
				}
				if(Utility.isEmpty(pic)){
					return "第"+n+"行系统中不存在单品10的编码："+arr1+"或者该编码的商品未上架";
				}
			}
			if(!Utility.isEmpty(tGoodsPicDetails)){
				vo.settGoodsPicDetails(tGoodsPicDetails);
				vo.setGroupPrice(groupPrice);
				vo.setOriginalPrice(originalPrice);
				vo.setCurrentPrice(groupPrice);
			}
		}
		
		return null;
	}

	
	private String checkGoodsType2(List<TGoodsGroupVo> listTGoodsEntitys,List<TSType> goodsTypeList) {
		int i=1;
		for(TGoodsGroupVo vo : listTGoodsEntitys){
			i++;
			if(!StringUtil.isEmpty(vo.getSubGoodsTypeName())){
				for(TSType goodsType : goodsTypeList){
					if(goodsType.getTypename().equals(vo.getSubGoodsTypeName())){
						vo.setSubGoodsType(goodsType.getTypecode());
						break;
					}else{
						continue;
					}
				}
				if(StringUtil.isEmpty(vo.getSubGoodsType())){
					return"第"+i+"行系统中不存在商品类别："+vo.getSubGoodsTypeName();
				}
			}
		}
		return null;
	}

	private String checkTopCatgy2(List<TGoodsGroupVo> listTGoodsEntitys,	List<TSCategoryEntity> topCategoryList) {
		int i=1;
		for(TGoodsGroupVo vo : listTGoodsEntitys){
			i++;
			if(StringUtil.isEmpty(vo.getTopCategoryName())){
				return "第"+i+"行商品类目不能为空";
			}else{
				for(TSCategoryEntity cat : topCategoryList){
					if(cat.getName().equals(vo.getTopCategoryName())){
						vo.setTopCategoryId(cat.getId());
						break;
					}else{
						continue;
					}
				}
				if(StringUtil.isEmpty(vo.getTopCategoryId())){
					return "第"+i+"行系统中不存在商品类目："+vo.getTopCategoryName();
				}
			}
		}
		return null;
	}

	private String checkScene2(List<TGoodsGroupVo> listTGoodsEntitys,List<TSType> sceneList) {
		int i=1;
		for(TGoodsGroupVo vo : listTGoodsEntitys){
			i++;
			if(StringUtil.isEmpty(vo.getSceneTypeName())){
				return "第"+i+"行场景不能为空";
			}else{
				for(TSType scene : sceneList){
					if(scene.getTypename().equals(vo.getSceneTypeName())){
						vo.setSceneType(scene.getTypecode());
						break;
					}else{
						continue;
					}
				}
				if(StringUtil.isEmpty(vo.getSceneType())){
					return "第"+i+"行系统中不存在场景："+vo.getSceneTypeName();
				}
			}
		}
		return null;
	}
	
	/**校验商品code和规格sku条码*/
	private String checkDataBaseCodeAndBarCode(HashSet<String> codeSet,HashSet<String> barCodeSet,TSUser user) {
		String sql = "select goods_code,code from t_goods where  retailer_Id ='"+user.getId()+"' and goods_type = '1' and status = 'A'";//所有该零售商的商品（单品）款号和编号
		List<Object> list = this.commonDao.findListbySql(sql);
		if(Utility.isNotEmpty(list)){
			for(Object code:list){
				if(code.getClass().isArray()){
					Object[] arr = (Object[]) code;
					if(codeSet.contains(arr[1].toString())){
						return "系统中已经存在该款号："+arr[0].toString();
					}
				}
			}
		}
		if(Utility.isNotEmpty(barCodeSet)){
			String sql2 = "select bar_code from t_goods_store where  retailer_Id ='"+user.getId()+"' and status = 'A' and bar_code is not null";//所有该零售商的条码
			List<Object> list2 = this.commonDao.findListbySql(sql2);
			if(Utility.isNotEmpty(list2)){
				for(Object barCode:list2){
					if(barCodeSet.contains(barCode.toString())){
						return "系统中已经存在该条码："+barCode;
					}
				}
			}
		}
		return null;
	}

	/*private String checkVisibleCatgy(List<TGoodsVo> listTGoodsEntitys,	HashMap<String, CategoryVo> topCategoryMap,	List<CategoryVo> subCategoryList) {
		int i=1;
		for(TGoodsVo vo : listTGoodsEntitys){
			i++;
			List<TVisibleCategriesEntity> list = new ArrayList<TVisibleCategriesEntity>();
			if(!StringUtil.isEmpty(vo.getVisbCategoryOne())){
				String str = vo.getVisbCategoryOne().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行可见类目1格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
//					String arr3 = arr[2];
					if(topCategoryMap.containsKey(arr1)){
						String topCatId = topCategoryMap.get(arr1).getId();
						String subCatId = null;
						for(CategoryVo subCat : subCategoryList){
							if(arr2.equals(subCat.getName())&&topCatId.equals(subCat.getParentId())){
								subCatId = subCat.getId();
								TVisibleCategriesEntity entity = new TVisibleCategriesEntity();
								entity.setTopCategoryId(topCatId);
								entity.setSubCategoryId(subCatId);
								list.add(entity);
								break;
							}
						}
						if(StringUtil.isEmpty(subCatId)){
							return "第"+i+"行系统中不存在可见类目1中的"+arr2;
						}
					}else{
						return "第"+i+"行系统中不存在可见类目1中的"+arr1;
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getVisbCategoryTwo())){
				String str = vo.getVisbCategoryTwo().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行可见类目2格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(topCategoryMap.containsKey(arr1)){
						String topCatId = topCategoryMap.get(arr1).getId();
						String subCatId = null;
						for(CategoryVo subCat : subCategoryList){
							if(arr2.equals(subCat.getName())&&topCatId.equals(subCat.getParentId())){
								subCatId = subCat.getId();
								TVisibleCategriesEntity entity = new TVisibleCategriesEntity();
								entity.setTopCategoryId(topCatId);
								entity.setSubCategoryId(subCatId);
								list.add(entity);
								break;
							}
						}
						if(StringUtil.isEmpty(subCatId)){
							return "第"+i+"行系统中不存在可见类目2中的"+arr2;
						}
					}else{
						return "第"+i+"行系统中不存在可见类目2中的"+arr1;
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getVisbCategoryThree())){
				String str = vo.getVisbCategoryThree().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行可见类目3格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(topCategoryMap.containsKey(arr1)){
						String topCatId = topCategoryMap.get(arr1).getId();
						String subCatId = null;
						for(CategoryVo subCat : subCategoryList){
							if(arr2.equals(subCat.getName())&&topCatId.equals(subCat.getParentId())){
								subCatId = subCat.getId();
								TVisibleCategriesEntity entity = new TVisibleCategriesEntity();
								entity.setTopCategoryId(topCatId);
								entity.setSubCategoryId(subCatId);
								list.add(entity);
								break;
							}
						}
						if(StringUtil.isEmpty(subCatId)){
							return "第"+i+"行系统中不存在可见类目3中的"+arr2;
						}
					}else{
						return "第"+i+"行系统中不存在可见类目3中的"+arr1;
					}
				}
			}
			if(!StringUtil.isEmpty(vo.getVisbCategoryFour())){
				String str = vo.getVisbCategoryFour().replace("，", ",");
				String[] arr = str.split(",");
				if(arr.length!=2){
					return "第"+i+"行可见类目4格式不对";
				}else{
					String arr1 = arr[0];
					String arr2 = arr[1];
					if(topCategoryMap.containsKey(arr1)){
						String topCatId = topCategoryMap.get(arr1).getId();
						String subCatId = null;
						for(CategoryVo subCat : subCategoryList){
							if(arr2.equals(subCat.getName())&&topCatId.equals(subCat.getParentId())){
								subCatId = subCat.getId();
								TVisibleCategriesEntity entity = new TVisibleCategriesEntity();
								entity.setTopCategoryId(topCatId);
								entity.setSubCategoryId(subCatId);
								list.add(entity);
								break;
							}
						}
						if(StringUtil.isEmpty(subCatId)){
							return "第"+i+"行系统中不存在可见类目4中的"+arr2;
						}
					}else{
						return "第"+i+"行系统中不存在可见类目4中的"+arr1;
					}
				}
			}
			if(Utility.isNotEmpty(list)){
				vo.settVisibleCatgys(list);
			}
		}
		return null;
	}*/

	/**校验可见类目下面是否存在一样名字的三级类目
	 * @param subCatId
	 * @param thridCategoryName
	 * @return
	 */
	private HashMap<String,String> checkExist(String subCatId, String thridCategoryName) {
		HashMap<String,String> map =  new HashMap<String, String>();
		TSCategoryEntity sub = this.commonDao.get(TSCategoryEntity.class, subCatId);
		List<TSCategoryEntity> list = this.commonDao.findByProperty(TSCategoryEntity.class, "parent.code", sub.getCode());
		for(TSCategoryEntity entity : list){
			if(thridCategoryName.equals(entity.getName())){
				map.put("isExist", "Y");
				map.put("thirdCatgId", entity.getId());
				return map;
			}else{
				continue;
			}
		}
		map.put("isExist", "N");
		return map;
	}

	private String checkThirdCatgy(List<TGoodsImportVo> listTGoodsEntitys,	List<CategoryVo> thirdCategoryList) {
		int i=1;
		for(TGoodsImportVo vo : listTGoodsEntitys){
			i++;
			if(StringUtil.isEmpty(vo.getThridCategoryName())){
				return "基础信息sheet页第"+i+"行三级类目不能为空";
			}else{
				for(CategoryVo category : thirdCategoryList){
					if(category.getName().equals(vo.getThridCategoryName())&&category.getParentId().equals(vo.getSubCategoryId())){
						vo.setThridCategoryId(category.getId());
						break;
					}else{
						continue;
					}
				}
				if(StringUtil.isEmpty(vo.getThridCategoryId())){
					return "基础信息sheet页第"+i+"行系统中不存在三级类目："+vo.getThridCategoryName();
				}
			}
		}
		return null;
	}

	private String checkSubCatgy(List<TGoodsImportVo> listTGoodsEntitys,	List<CategoryVo> subCategoryList) {
		int i=1;
		for(TGoodsImportVo vo : listTGoodsEntitys){
			i++;
			if(StringUtil.isEmpty(vo.getSubCategoryName())){
				return "基础信息sheet页第"+i+"行二级类目不能为空";
			}else{
				for(CategoryVo category : subCategoryList){
					if(category.getName().equals(vo.getSubCategoryName())&&category.getParentId().equals(vo.getTopCategoryId())){
						vo.setSubCategoryId(category.getId());
						break;
					}else{
						continue;
					}
				}
				if(StringUtil.isEmpty(vo.getSubCategoryId())){
					return "基础信息sheet页第"+i+"行系统中不存在二级类目："+vo.getSubCategoryName();
				}
			}
		}
		return null;
	}
	
	
	
	/**校验规格库存（款号必须是前面sheet页中存在的）,并将库存关联到baseList
	 * 款号+色号必须唯一
	 * @param baseList 基础信息
	 * @param storeList 库存明细信息
	 * @param set 款号集合set
	 * @return
	 */
	private String checkStore(List<TGoodsImportVo> baseList,List<TGoodsStoreVo> storeList,HashSet<String> set,TSpecHeadersEntity headers) {
		int row=1;
		HashMap<String,List<TGoodsStoreEntity>> storeMap = new HashMap<String, List<TGoodsStoreEntity>>();//库存明细map<款号,List<库存>>
		HashMap<String,BigDecimal> totalNumMap = new HashMap<String, BigDecimal>();//库存map<款号,总库存>
		HashSet<String> colorCodeSet = new HashSet<String>();//存款号+色号+尺码
		String errMsg = null;
		int n = storeList.size();//库存条数总记录
		if(n>0){
			//校验款号是否存在，同一个款号的颜色尺码是否唯一
			for(TGoodsStoreVo vo : storeList){
				row++;
				if(Utility.isNotEmpty(vo.getGoodsCode())){
					if(!set.contains(vo.getGoodsCode())){
						return "基础信息sheet页中不存在该款号："+vo.getGoodsCode();
					}
					String colorCode = vo.getGoodsCode()+vo.getSpecificationOne()+vo.getSpecificationTwo();
					//检验款号+色号 唯一性
					if(colorCodeSet.contains(colorCode)){
						errMsg = "规格库存sheet页第"+row+"行款号为："+vo.getGoodsCode();
						if(Utility.isNotEmpty(vo.getSpecificationOne())){
							errMsg+="，"+headers.getHeaderOne()+"为："+vo.getSpecificationOne();
						}
						if(Utility.isNotEmpty(vo.getSpecificationTwo())){
							errMsg+="，"+headers.getHeaderTwo()+"为："+vo.getSpecificationTwo();
						}
						errMsg+=" 出现重复";
						return errMsg;
					}else{
						colorCodeSet.add(colorCode);
					}
				}
			}
			BigDecimal total = BigDecimal.ZERO;//同一个款号的总库存
			String currCode = null;//当前商品款号
			List<TGoodsStoreEntity> tGoodsStoreDetails = null;
			for(int i = 0;i<n;i++){
				TGoodsStoreVo vo = storeList.get(i);
				if(currCode==null){
					currCode = vo.getGoodsCode();
				}else if(!currCode.equals(vo.getGoodsCode())){//循环到另一款商品的库存了
					if(storeMap.containsKey(currCode)){
						List<TGoodsStoreEntity> tGoodsStoreList = storeMap.get(currCode);
						tGoodsStoreList.addAll(tGoodsStoreDetails);
						storeMap.put(currCode, tGoodsStoreList);
						BigDecimal total2 = totalNumMap.get(currCode);
						total2 = total2.add(total);
						totalNumMap.put(currCode, total2);
					}else{
						storeMap.put(currCode, tGoodsStoreDetails);
						totalNumMap.put(currCode, total);
					}
					currCode = vo.getGoodsCode();
					total = BigDecimal.ZERO;//初始化库存
					tGoodsStoreDetails = new ArrayList<TGoodsStoreEntity>();
				}
				
				//
				TGoodsStoreEntity store = new TGoodsStoreEntity();
				store.setSpecificationOne(vo.getSpecificationOne());
				store.setSpecificationTwo(vo.getSpecificationTwo());
				store.setStore(vo.getStore());
				store.setBarCode(vo.getBarCode());
				if(tGoodsStoreDetails == null){
					tGoodsStoreDetails = new ArrayList<TGoodsStoreEntity>();
				}
				tGoodsStoreDetails.add(store);
				if(Utility.isNotEmpty(vo.getStore())){
					total = total.add(vo.getStore());//累加该款号的总库存
				}
				if(i==n-1){//库存最后一行，数据要加入map
					if(storeMap.containsKey(currCode)){
						List<TGoodsStoreEntity> tGoodsStoreList = storeMap.get(currCode);
						tGoodsStoreList.addAll(tGoodsStoreDetails);
//						storeMap.put(currCode, tGoodsStoreList);
						BigDecimal total2 = totalNumMap.get(currCode);
						total2 = total2.add(total);
						totalNumMap.put(currCode, total2);
					}else{
						storeMap.put(currCode, tGoodsStoreDetails);
						totalNumMap.put(currCode, total);
					}
				}
			}
			for(TGoodsImportVo vo : baseList){
				if(totalNumMap.containsKey(vo.getGoodsCode())){
					BigDecimal stock = totalNumMap.get(vo.getGoodsCode());//该款号的总库存数
					if(stock.compareTo(BigDecimal.ZERO)>0){
						vo.setGoodsStock(stock);//更新款号库存
					}
				}
				if(storeMap.containsKey(vo.getGoodsCode())){
					vo.settGoodsStoreDetails(storeMap.get(vo.getGoodsCode()));//管理库存明细
				}
			}
			storeList = null;
			storeMap = null;
			totalNumMap = null;
			colorCodeSet = null;
		}
		return null;
	}

	private String checkTopCatgy(List<TGoodsImportVo> listTGoodsEntitys,	HashMap<String, CategoryVo> topCategoryMap) {
		int i=1;
		for(TGoodsImportVo vo : listTGoodsEntitys){
			i++;
			if(StringUtil.isEmpty(vo.getTopCategoryName())){
				return "基础信息sheet页第"+i+"行一级类目不能为空";
			}else{
				if(topCategoryMap.containsKey(vo.getTopCategoryName())){
					vo.setTopCategoryId(topCategoryMap.get(vo.getTopCategoryName()).getId());
				}
				if(StringUtil.isEmpty(vo.getTopCategoryId())){
					return "基础信息sheet页第"+i+"行系统中不存在一级类目："+vo.getTopCategoryName();
				}
			}
		}
		return null;
	}

	
	//校验发布状态是否存在，存在则赋值对应的Code
//	private String checkPubStatus(List<TGoodsVo> listTGoodsEntitys,	List<TSType> pubList) {
//		int i=1;
//		for(TGoodsVo vo : listTGoodsEntitys){
//			i++;
//			if(StringUtil.isEmpty(vo.getPublishStatusName())){
//				return "第"+i+"行发布状态不能为空";
//			}else{
//				for(TSType pub : pubList){
//					if(pub.getTypename().equals(vo.getPublishStatusName())){
//						vo.setPublishStatus(pub.getTypecode());
//						break;
//					}else{
//						continue;
//					}
//				}
//				if(StringUtil.isEmpty(vo.getPublishStatus())){
//					return "第"+i+"行系统中不存在发布状态："+vo.getPublishStatusName();
//				}
//			}
//		}
//		return null;
//	}

	//校验商品类别是否存在，存在则赋值对应的Code
	/*private String checkGoodsType(List<TGoodsVo> listTGoodsEntitys,		List<TSType> goodsTypeList) {
		int i=1;
		for(TGoodsVo vo : listTGoodsEntitys){
			i++;
			if(!StringUtil.isEmpty(vo.getSubGoodsTypeName())){
				for(TSType goodsType : goodsTypeList){
					if(goodsType.getTypename().equals(vo.getSubGoodsTypeName())){
						vo.setSubGoodsType(goodsType.getTypecode());
						break;
					}else{
						continue;
					}
				}
				if(StringUtil.isEmpty(vo.getSubGoodsType())){
					return"第"+i+"行系统中不存在商品类别："+vo.getSubGoodsTypeName();
				}
			}
		}
		return null;
	}*/


	/**校验品牌是否存在，存在则赋值对应的brandId和brandCode,组装商品编码
	 * @param listTGoodsEntitys
	 * @param brandList 品牌list
	 * @param user 所属零售商
	 * @param codeSet 商品编码集合
	 * @return
	 */
	private String checkBrand(List<TGoodsImportVo> listTGoodsEntitys,	List<BaseBrandEntity> brandList,TSUser user ,HashSet<String> codeSet) {
		int i=1;
		for(TGoodsImportVo vo : listTGoodsEntitys){
			i++;
			if(StringUtil.isEmpty(vo.getBrandName())){
				return "第"+i+"行品牌不能为空";
			}else{
				for(BaseBrandEntity brand : brandList){
					if(brand.getBrandName().equals(vo.getBrandName())){
						vo.setBrandCode(brand.getBrandCode());
						vo.setBrandId(brand.getId());
						vo.setCode(user.getUserCode()+brand.getBrandCode()+vo.getGoodsCode());
						codeSet.add(vo.getCode());
						break;
					}else{
						continue;
					}
				}
				if(StringUtil.isEmpty(vo.getBrandId())){
					return "第"+i+"行系统中不存在品牌："+vo.getBrandName();
				}
			}
		}
		return null;
	}
	


	@Override
	public List<TGoodsSellVo> getSellGoodsBySql(HttpServletRequest request) {
		String goodsName = request.getParameter("goodsName");
		String goodsCode = request.getParameter("goodsCode");
		String brandName = request.getParameter("brandName");
		String retailerName = request.getParameter("retailerName");
		String retailerId = null;
		TSUser user = ResourceUtil.getSessionUserName();
		if(GlobalConstants.USER_TYPE_02.equals(user.getUserType())){
			retailerId = user.getId();
		}else if(GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
			retailerId = user.getRetailerId();
		}
		StringBuffer sql = new StringBuffer("SELECT g.brand_name as brandName,g.goods_code as goodsCode,g.goods_name as goodsName,g.original_price as originalPrice,g.current_price as currentPrice,g.lowest_price as lowestPrice,")
					.append("case g.sub_goods_type when 1 then '新品' when 2 then '畅销品' when 3 then '促销品' else '' end as subGoodsType,")
					.append("case g.fare_Type when 0 then '包邮' when 1 then CONCAT('定额',g.fare,'元/件') else '' end as fareType,")
					.append("case g.fare_Preferential_Type when 0 then '无'  when 1 then CONCAT('满',g.goods_Fare_Preferential,'元包邮') ")
					.append("when 2 then CONCAT('递减',g.fare,'+',g.goods_Fare_Preferential)  else '' end as farePref,")
					.append("g.goods_stock as goodsStock,g.sales_volume as salesVolume,CONCAT(c.`name`,c2.`name`,c3.`name`) as category ")
					.append(" from t_goods g LEFT JOIN t_s_category c on g.top_category_id = c.id ")
					.append(" LEFT JOIN t_s_category c2 on g.sub_category_id = c2.id")
					.append(" LEFT JOIN t_s_category c3 on g.sub_category_id = c3.id")
					.append(" where g.`status` ='A' and g.goods_status = '4' and goods_type = '1' ");
		if(StringUtil.isNotEmpty(goodsName)){
			sql.append(" and g.goods_name like '%").append(goodsName).append("%'");
		}
		if(StringUtil.isNotEmpty(goodsCode)){
			sql.append(" and g.goods_code ='").append(goodsCode).append("'");
		}
		if(StringUtil.isNotEmpty(brandName)){
			sql.append(" and g.brand_name ='").append(brandName).append("'");
		}
		if(StringUtil.isNotEmpty(retailerId)){
			sql.append(" and g.retailer_id ='").append(retailerId).append("'");
		}
		if(StringUtil.isNotEmpty(retailerName)){
			sql.append(" and g.retailer_name ='").append(retailerName).append("'");
		}
		sql.append(" order by g.sales_volume desc");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.aliasToBean(TGoodsSellVo.class));
		return query.list();
	}
	
	@Override
	public void doChangeActivityPrice(TGoodsEntity t,BigDecimal activityPrice) {
		if(activityPrice.compareTo(t.getActivityPrice())!=0){
			t.setPreActivityPrice(t.getActivityPrice());
			t.setActivityPrice(activityPrice);
//			t.setGoodsUpdateTime(DateUtils.gettimestamp());
			this.commonDao.updateEntitie(t);
			this.recordPriceChange(t,TPriceChangeEntity.PRICETYPE_2);
		}
	}
	
	@Override
	public void doChangePrice(HttpServletRequest request) {
		String id = request.getParameter("id");
		String goodsCode = request.getParameter("goodsCode");
		String field = request.getParameter("field");
		String prePrice = request.getParameter("prePrice");
		String currPrice = request.getParameter("currPrice");
		BigDecimal pPrice = new BigDecimal(prePrice);
		BigDecimal cPrice = new BigDecimal(currPrice);
		if(pPrice.compareTo(cPrice)!=0){//价格有变动,更新商品价格
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer updateSql = new StringBuffer("update t_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
				.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',");
			if("original_price".equals(field)){
				updateSql.append("current_price = '").append(currPrice).append("',");
			}
			updateSql.append(field).append(" = '").append(currPrice).append("' where id ='").append(id).append("'");
			this.commonDao.updateBySqlString(updateSql.toString());
			if("current_price".equals(field)){//记录现价变动历史
				recordPriceChange( id, goodsCode, pPrice, cPrice, TPriceChangeEntity.PRICETYPE_1);
			}else if("activity_price".equals(field)){
				recordPriceChange( id, goodsCode, pPrice, cPrice, TPriceChangeEntity.PRICETYPE_2);
			}else if("lowest_price".equals(field)){
				recordPriceChange( id, goodsCode, pPrice, cPrice, TPriceChangeEntity.PRICETYPE_3);
			}
			
		}
	}
	
	//现价发生改变则记录(type 1:现价，2：活动价，3：最低价)
	private void recordPriceChange(String id,String goodsCode,BigDecimal pPrice,BigDecimal cPrice,String type) {
			TPriceChangeEntity priceChange = new TPriceChangeEntity();
			priceChange.setGoodsId(id);
			priceChange.setGoodsCode(goodsCode);
			priceChange.setPriceType(type);
			priceChange.setPrePrice(pPrice);
			priceChange.setCurrentPrice(cPrice);
			priceChange.setStatus(common.GlobalConstants.STATUS_ACTIVE);
			priceChange.setRetailerId(ResourceUtil.getRetailerId());
			this.commonDao.save(priceChange);
	}
	
	@Override
	public void doChangeProperty(HttpServletRequest request) {
		String id = request.getParameter("id");
		String field = request.getParameter("field");
		String val = request.getParameter("val");
		TSUser user = ResourceUtil.getSessionUserName();
		StringBuffer updateSql = new StringBuffer("update t_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',").append(field).append(" = '").append(val).append("' where id ='").append(id).append("'");
		this.commonDao.updateBySqlString(updateSql.toString());
	}
	
	@Override
	public void doChangeSort(String id, String type, String sortNum) {
		String retailerId = ResourceUtil.getRetailerId();
		String sortOrder = null;
		String compareTag = null;
		if("U".equals(type)){//up
			sortOrder = "asc";
			compareTag = ">";
			
		}else if("D".equals(type)){//down
			sortOrder = "desc";
			compareTag = "<";
		}
		StringBuffer querySql = new StringBuffer("SELECT id,sort_num FROM t_goods WHERE status = 'A' AND new_goods_type = '2' AND goods_type = '1' and sort_num ")
		.append(compareTag).append(sortNum);
		if(Utility.isNotEmpty(retailerId)){
			querySql.append(" and retailer_id ='").append(retailerId).append("'");
		}
		querySql.append(" and goods_status ='4' ORDER BY sort_num ").append(sortOrder).append(" limit 1");
		List<Map<String,Object>> list = this.commonDao.findForJdbc(querySql.toString(), null);
		if(Utility.isNotEmpty(list)){
			String otherId = list.get(0).get("id")+"";
			String otherSortNum = list.get(0).get("sort_num")+"";
			//更换排序
			this.commonDao.updateBySqlString("update t_goods set sort_num = '"+otherSortNum+"' where id ='"+id+"'");
			this.commonDao.updateBySqlString("update t_goods set sort_num = '"+sortNum+"' where id ='"+otherId+"'");
		}else{
			if("U".equals(type)){//到顶了
				this.commonDao.updateBySqlString("update t_goods set sort_num = sort_num+1 where id ='"+id+"'");
//				result = 2;
				
			}else if("D".equals(type)){//到底了
				this.commonDao.updateBySqlString("update t_goods set sort_num = sort_num-1 where id ='"+id+"'");
//				result = 3;
			}
		}
	}
	
	@Override
	public void updateNewGoodsDetail(TGoodsEntity tGoods) {
		//更新描述(上新零售商商品描述 真特色、真用途、真权威、真服务)
//		List<TGoodsDescEntity> descList = tGoods.getDescList();
//		if(Utility.isNotEmpty(descList)){
//			for(TGoodsDescEntity desc : descList){
//				desc.setGoodsDesc(this.clearFirstEmptyPtag(desc.getGoodsDesc()));
//				desc.setGoodsId(tGoods.getId());
//				if(Utility.isEmpty(desc.getId())){
//					this.commonDao.save(desc);
//				}else{
//					this.commonDao.updateEntitie(desc);
//				}
//			}
//			TSUser user = ResourceUtil.getSessionUserName();
//			StringBuffer sql = new StringBuffer("update t_goods set update_by = '").append(user.getUserName()).append("',update_name = '")
//			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp())
//			.append("',has_zhen = '1' where id ='").append(tGoods.getId()).append("'");
//			this.commonDao.updateBySqlString(sql.toString());
//			sql = null;
//		}
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public String batchSaveGoods(Map<String, Object> map) throws SQLException {
		String errMsg = null;
		List<TGoodsImportVo> baseList = (List<TGoodsImportVo>) map.get("baseList");//基础数据
		List<TGoodsStoreVo> storeList = (List<TGoodsStoreVo>) map.get("storeList");//库存数据
		TSpecHeadersEntity headers = (TSpecHeadersEntity) map.get("headers");//规格头信息
//		Set<String> brandNameSet = (Set<String>) map.get("brandNameSet");//品牌set
		HashSet<String> goodsCodeSet = (HashSet<String>) map.get("goodsCodeSet");//款号set
		HashSet<String> barCodeSet = (HashSet<String>) map.get("barCodeSet");//sku条码set
		errMsg = this.checkAndInitImportBaseInfo(baseList,goodsCodeSet,storeList,headers,barCodeSet);
		return errMsg;
	}

	/**校验并初始化数据
	 * @throws SQLException */
	private String checkAndInitImportBaseInfo(List<TGoodsImportVo> baseList,HashSet<String> goodsCodeSet,List<TGoodsStoreVo> storeList,
			TSpecHeadersEntity headers,HashSet<String> barCodeSet) throws SQLException {
		String msg = null;
		TSUser user = null;
		TSUser nowUser = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_02.equals(nowUser.getUserType())){
			user = nowUser;
		}else if(common.GlobalConstants.USER_TYPE_05.equals(nowUser.getUserType())){
			user = this.commonDao.get(TSUser.class, nowUser.getRetailerId());
		}
		//品牌列表
		List<BaseBrandEntity> brandList = this.commonDao.findHql("from BaseBrandEntity where  retailerId =?",user.getId());
		//所有类目
//		List<TSCategoryEntity> categoryList = this.commonDao.findByProperty(TSCategoryEntity.class, "status", "A");
		List<CategoryVo> categoryList = this.commonDao.findObjForJdbc("select id,parent_id parentId,name,level from t_s_category where status = 'A' and retailer_id in ('admin','"+user.getId()+"')", CategoryVo.class);
		HashMap<String,CategoryVo> topCategoryMap = new HashMap<String, CategoryVo>();//一级类目Map<name,CategoryVo>
		List<CategoryVo> subCategoryList = new ArrayList<CategoryVo>();//二级分类
		List<CategoryVo> thirdCategoryList = new ArrayList<CategoryVo>();//三级分类
		for(CategoryVo category : categoryList){
			CategoryVo vo = new CategoryVo();
			vo.setId(category.getId());
			vo.setLevel(category.getLevel());
			vo.setName(category.getName());
			if(category.getLevel()==1){
				topCategoryMap.put(category.getName(), vo);
			}else if(category.getLevel()==2){
				vo.setParentId(category.getParentId());
				subCategoryList.add(vo);
			}else if(category.getLevel()==3){
				vo.setParentId(category.getParentId());
				thirdCategoryList.add(vo);
			}
		}
		
		//存编号（零售商编码+品牌编码+款号）
		HashSet<String> codeSet = new HashSet<String>();
		//校验品牌（并组装编号存在codeSet中）
		msg = this.checkBrand(baseList,brandList,user,codeSet);
		if(msg!=null){
			brandList = null;
			return msg;
		}
		//校验一级类目
		msg = this.checkTopCatgy(baseList,topCategoryMap);
		if(msg!=null){
			return msg;
		}
		//校验二级类目
		msg = this.checkSubCatgy(baseList,subCategoryList);
		if(msg!=null){
			return msg;
		}
		//校验三级类目 
		msg = this.checkThirdCatgy(baseList,thirdCategoryList);
		if(msg!=null){
			return msg;
		}
		//校验规格库存（款号必须是前面sheet页中存在的，库存可不录，暂不考虑部分颜色录入库存部分没有录入的）,并将库存关联到baseList
		msg = this.checkStore(baseList,storeList,goodsCodeSet,headers);
		
		if(msg!=null){
			return msg;
		}
		//校验编号和条码是否和数据库中的重复
		msg = this.checkDataBaseCodeAndBarCode(codeSet,barCodeSet,user);
		if(msg!=null){
			return msg;
		}
		brandList = null;
		categoryList = null;
		topCategoryMap = null;
		subCategoryList = null;
		thirdCategoryList = null;
		goodsCodeSet = null;
		codeSet = null;
		//校验全部通过，批量保存（明细信息已经关联到基础信息）
		this.batchSaveGoods(baseList,user,headers);
		return null;
	}
	

	/**批量保存商品基础信息和库存及规格等信息
	 * @throws SQLException */
	private void batchSaveGoods(List<TGoodsImportVo> listTGoodsEntitys, TSUser retailer,TSpecHeadersEntity tSpecHeader) throws SQLException {
		List<Map<String, Object>> goodsDatas = new ArrayList<Map<String, Object>>();//商品数据
		List<Map<String, Object>> headerDatas = new ArrayList<Map<String, Object>>();//规格标题数据
		List<Map<String, Object>> storeDatas = new ArrayList<Map<String, Object>>();//库存数据
		Date createDate = Utility.getCurrentTimestamp();
		Long sortNum = Long.valueOf(DateUtils.date2Str(createDate, DateUtils.yyyymmddhhmmss));
		TSUser user = ResourceUtil.getSessionUserName();
		for(TGoodsImportVo vo : listTGoodsEntitys){
			String goodsId = Utility.getUUID();
			//商品map
			Map<String, Object> goodsMap = new HashMap<String, Object>();
			goodsMap.put("id",goodsId);
			goodsMap.put("create_name",user.getRealName());
			goodsMap.put("create_by",user.getUserName());
			goodsMap.put("create_date",createDate);
			goodsMap.put("update_date",createDate);
			goodsMap.put("status",common.GlobalConstants.STATUS_ACTIVE);
			goodsMap.put("top_category_id",vo.getTopCategoryId());
			goodsMap.put("sub_category_id",vo.getSubCategoryId());
			goodsMap.put("thrid_category_id",vo.getThridCategoryId());
			goodsMap.put("goods_name",vo.getGoodsName()==null?"":vo.getGoodsName());
			goodsMap.put("goods_code",vo.getGoodsCode());
			goodsMap.put("code",vo.getCode());
			goodsMap.put("brand_id",vo.getBrandId());
			goodsMap.put("brand_name",vo.getBrandName());
			goodsMap.put("original_price",vo.getOriginalPrice());
			goodsMap.put("current_price",vo.getOriginalPrice());
			goodsMap.put("lowest_price_discount",vo.getLowestPriceDiscount());
			goodsMap.put("lowest_price",vo.getLowestPrice());
			goodsMap.put("sales_volume",0);
			goodsMap.put("goods_stock",vo.getGoodsStock());
			goodsMap.put("retailer_id",retailer.getId());
			goodsMap.put("retailer_code",retailer.getUserCode());
			goodsMap.put("retailer_type",retailer.getRetailerType());
			goodsMap.put("retailer_name",retailer.getRealName());
			goodsMap.put("goods_type",TGoodsEntity.GOODS_TYPE_1);
			goodsMap.put("goods_status",TGoodsEntity.GOODS_STATUS_0);//草稿箱
			goodsMap.put("province_Id",retailer.getProvinceId());
			goodsMap.put("city_id",retailer.getCityId());
			goodsMap.put("goods_collect",0);
			goodsMap.put("no_sense_num",0);
			goodsMap.put("Good_num",0);
			goodsMap.put("goods_star",0);
			goodsMap.put("scores_num",0);
			goodsMap.put("fare_type","0");// 0：免邮
			goodsMap.put("fare_Preferential_Type","0");
			goodsMap.put("is_special","N");
			goodsMap.put("new_goods_type",TGoodsEntity.NEW_GOODS_TYPE_2);
			goodsMap.put("sort_num", sortNum);
			
			//规格表头map
			Map<String, Object> headerMap = new HashMap<String, Object>();
			if(Utility.isNotEmpty(tSpecHeader)){
				headerMap.put("id", Utility.getUUID());
				headerMap.put("header_One", tSpecHeader.getHeaderOne());
				headerMap.put("header_Two", tSpecHeader.getHeaderTwo());
				headerMap.put("goods_Id", goodsId);
				headerDatas.add(headerMap);
			}
			//规格库存map
			List<TGoodsStoreEntity> tGoodsStoreDetails = vo.gettGoodsStoreDetails();
			if(Utility.isNotEmpty(tGoodsStoreDetails)){
				goodsMap.put("goods_status",TGoodsEntity.GOODS_STATUS_3);//待上架
				for(TGoodsStoreEntity store:tGoodsStoreDetails){
					Map<String, Object> storeMap = new HashMap<String, Object>();
					storeMap.put("id",Utility.getUUID());
					storeMap.put("create_name",user.getRealName());
					storeMap.put("create_by",user.getUserName());
					storeMap.put("create_date",createDate);
					storeMap.put("update_date",createDate);
					storeMap.put("status",common.GlobalConstants.STATUS_ACTIVE);
					storeMap.put("goods_id",goodsId);
					storeMap.put("specification_one",store.getSpecificationOne());
					storeMap.put("specification_two",store.getSpecificationTwo());
					storeMap.put("store",store.getStore());
					storeMap.put("bar_code",store.getBarCode());
					storeMap.put("retailer_id",user.getId());
					storeDatas.add(storeMap);
				}
			}
			goodsDatas.add(goodsMap);
		}
		if(Utility.isNotEmpty(goodsDatas)){
			Long tt1 = System.currentTimeMillis();
			 Map<String, Object> resMap= DBUtil.insertAll("t_goods", goodsDatas);
			 Long tt2 = System.currentTimeMillis();
			 System.out.println("批量导入商品====="+(tt2-tt1)+"ms");
			 if((Boolean) resMap.get("success")){
				 int affectRowCount = (Integer) resMap.get("affectRowCount");
				 System.out.println("成功插入"+affectRowCount);
			 }
		}
		if(Utility.isNotEmpty(headerDatas)){
			DBUtil.insertAll("t_spec_headers", headerDatas);
		}
		if(Utility.isNotEmpty(storeDatas)){
			Long tt1 = System.currentTimeMillis();
			DBUtil.insertAll("t_goods_store", storeDatas);
			Long tt2 = System.currentTimeMillis();
			System.out.println("批量导入规格库存====="+(tt2-tt1)+"ms");
		}
		listTGoodsEntitys = null;
		goodsDatas = null;
		headerDatas = null;
		storeDatas = null;
	}
	
	/*@Override
	public void doBatchChangePrice(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String ids = request.getParameter("ids");//商品id，逗号隔开
		String priceField = request.getParameter("priceField");//1：原价，2：最低价
		String priceType = request.getParameter("priceType");//1：统一调价，2：折扣调价
		String price = request.getParameter("price");//价格或者折扣
		BigDecimal p = new BigDecimal(price);
		StringBuffer updateSql = new StringBuffer("update t_goods");
		TSUser user = ResourceUtil.getSessionUserName();
		//目前设置修改后为整数
		if("1".equals(priceField)){
			if("1".equals(priceType)){//统一调价
				p.setScale(0, BigDecimal.ROUND_HALF_UP);
				updateSql.append(" set original_price =").append(p).append(",current_price =").append(p);
			}else if("2".equals(priceType)){//折扣调价
				updateSql.append(" set original_price = ROUND(original_price*").append(p).append("/10,0)").append(",current_price = original_price");
			}
		}else if("2".equals(priceField)){
			if("1".equals(priceType)){//统一调价
				p.setScale(0, BigDecimal.ROUND_HALF_UP);
				updateSql.append(" set lowest_price =").append(p);
			}else if("2".equals(priceType)){//折扣调价
				updateSql.append(" set lowest_price =ROUND(lowest_price*").append(p).append("/10,0)");
			}
		}
		if("1".equals(priceType)){
			updateSql.append(",update_date ='").append(Utility.getCurrentTimestamp()).append("',")
				.append("update_by ='").append(user.getUserName()).append("'")
				.append(" where id in ('").append(ids.replaceAll(",","','")).append("')");
		}else if("2".equals(priceType)){
			updateSql.append(",update_date ='").append(Utility.getCurrentTimestamp()).append("',")
			.append("update_by ='").append(user.getUserName()).append("'")
			.append(" where id in ('").append(ids.replaceAll(",","','")).append("')");
		}
		System.out.println(updateSql.toString());
		this.commonDao.updateBySqlString(updateSql.toString());
	}
	*/
	
	//批量修改最低价
	@Override
	public String batchChangePirce(List<LowestPriceImportVo> lowestPriceList) {
		TSUser user = ResourceUtil.getSessionUserName();
		String retailerId = ResourceUtil.getRetailerId();
		String msg = null;
		//校验款号
		String errMsg = this.checkGoodsCode(lowestPriceList,retailerId);
		if(errMsg!=null){
			return errMsg;
		}
		Timestamp stamp = Utility.getCurrentTimestamp();
		Long sortNum = Long.valueOf(DateUtils.date2Str(stamp, DateUtils.yyyymmddhhmmss));
		StringBuffer updateSql = new StringBuffer("update t_goods set lowest_price_discount = (case goods_code");
		for(LowestPriceImportVo vo : lowestPriceList){
			updateSql.append(" when '").append(vo.getGoodsCode()).append("' then ").append(vo.getLowestDiscount());
		}
		updateSql.append(" end),lowest_price=ROUND(original_price*lowest_price_discount,2),update_date = '")
		.append(stamp).append("',sort_num = ").append(sortNum)
		.append(",update_by = '").append(user.getUserName()).append("'")
		.append(" where status = 'A' and retailer_id = '").append(retailerId).append("' and goods_code in (");
		for(LowestPriceImportVo vo : lowestPriceList){
			updateSql.append("'").append(vo.getGoodsCode()).append("',");
		}
		updateSql = updateSql.deleteCharAt(updateSql.length()-1).append(")");
		int n = this.commonDao.updateBySqlString(updateSql.toString());
		if(n>0){
			msg = "成功修改"+n+"条记录最低价";
		}
		// TODO 保存改价历史
		return msg;
	}

	/**校验款号是否重复，数据库是否存在该款号，最低折扣是否有效
	 * @param lowestPriceList
	 * @param retailerId
	 * @return null则表示通过校验
	 */
	private String checkGoodsCode(List<LowestPriceImportVo> lowestPriceList, String retailerId) {
		String msg = null;
		Set<String> goodsCodeSet  = new HashSet<String>();
		int i = 2;
		for(LowestPriceImportVo vo : lowestPriceList){
			if(Utility.isEmpty(vo.getLowestDiscount())){
				msg = "第"+i+"行最低价折扣不是有效值";
				return msg;
			}else if(Utility.isEmpty(vo.getGoodsCode())){
				msg = "第"+i+"行款号不能为空";
				return msg;
			}else{
				if(goodsCodeSet.contains(vo.getGoodsCode())){
					msg = "款号 "+vo.getGoodsCode()+" 重复";
					return msg;
				}else{
					if(vo.getLowestDiscount().compareTo(BigDecimal.ZERO)<0||vo.getLowestDiscount().compareTo(BigDecimal.ONE)>0){
						msg = "第"+i+"行最低价折扣不是有效值";
						return msg;
					}else{
						String str = String.valueOf(vo.getLowestDiscount());
						int n = str.indexOf(".")+1;
						int l = str.length();
						if(l-n>2){
							msg = "第"+i+"行最低价折扣不能超过2位数字";
							return msg;
						}
					}
					goodsCodeSet.add(vo.getGoodsCode());
				}
			}
			i++;
		}
		List<Map<String, Object>> list = this.commonDao.findForJdbc("select goods_code from t_goods where status = 'A' and retailer_id = ?", retailerId);
		
		if(list.size()>0){
			for(String goodsCode : goodsCodeSet){
				boolean exist = false;
				for(Map map : list){
					if(goodsCode.equals(map.get("goods_code")+"")){
						exist = true;
						break;
					}
				}
				if(!exist){//系统不存在
					msg = "系统不存在款号 "+goodsCode;
					return msg;
				}
			}
		}else{
			msg = "请先录入商品";
			return msg;
		}
		return null;
	}
	
	@Override
	public String batchSaveGoodsWords(List<GoodsWordsImportVo> wordsList) throws Exception {
		String retailerId = ResourceUtil.getRetailerId();
		String msg = null;
		int n=0;
		Map<String,String> goodsCodeMap = new HashMap<String,String>();
		//校验款号
		String errMsg = this.checkGoodsCode(wordsList,retailerId,goodsCodeMap);
		if(errMsg!=null){
			return errMsg;
		}
		//款号对应商品话术排序的最大值
		Map<String, Integer> maxSortNumMap = this.getMaxSortNumMapByGoodsMap(goodsCodeMap);
		List<Map<String, Object>> wordsDatas = new ArrayList<Map<String, Object>>();//话术数据
		Date createDate = Utility.getCurrentTimestamp();
		TSUser user = ResourceUtil.getSessionUserName();
		for(GoodsWordsImportVo vo : wordsList){
			if(Utility.isNotEmpty(vo.getWords())){
				Map<String, Object> wordsMap = new HashMap<String, Object>();
				Integer maxSort = maxSortNumMap.get(goodsCodeMap.get(vo.getGoodsCode()));//最大排序
				if(maxSort==null){
					maxSort = 1;
				}else{
					maxSort+=1;
				}
				wordsMap.put("id",Utility.getUUID());
				wordsMap.put("create_name",user.getRealName());
				wordsMap.put("create_by",user.getUserName());
				wordsMap.put("create_date",createDate);
				wordsMap.put("update_date",createDate);
				wordsMap.put("status",common.GlobalConstants.STATUS_ACTIVE);
				wordsMap.put("fin_act_id",goodsCodeMap.get(vo.getGoodsCode()));
				wordsMap.put("words", vo.getWords());
				wordsMap.put("words_type", TFinActivityWordsEntity.WORDS_TYPE_GOODS);
				wordsMap.put("sort_num", maxSort);
				wordsDatas.add(wordsMap);
				maxSortNumMap.put(goodsCodeMap.get(vo.getGoodsCode()), maxSort);//更新最大排序
			}
		}
		if(Utility.isNotEmpty(wordsDatas)){
			 Map<String, Object> resMap= DBUtil.insertAll("t_fin_activity_words", wordsDatas);
			 if((Boolean) resMap.get("success")){
				 n = (Integer) resMap.get("affectRowCount");
				 System.out.println("成功插入"+n);
			 }
		}
		msg = "成功插入"+n+"条话术";
		return msg;
	}

	/**获取话术排序最大值
	 * @param goodsCodeMap<goodsCode,goodsId>
	 * @return
	 */
	private Map<String, Integer> getMaxSortNumMapByGoodsMap(Map<String, String> goodsCodeMap) {
		StringBuffer goodsIdStr = new StringBuffer();
		for (Map.Entry<String, String> map : goodsCodeMap.entrySet()) {
			goodsIdStr.append(",'").append(map.getValue()).append("'");
		}
		//款号对应商品话术排序的最大值
		List<Map<String, Object>> sortNumMapList = this.commonDao.findForJdbc("select IFNULL(MAX(sort_num),0) maxSort,fin_act_id goodsId  from t_fin_activity_words where status= 'A' and fin_act_id in ("+
										goodsIdStr.deleteCharAt(0)+") GROUP BY fin_act_id ", null);
		Map<String, Integer> maxSortNumMap = new HashMap<String, Integer>();
		
		for (Map<String, Object> map : sortNumMapList) {
			maxSortNumMap.put(map.get("goodsId")+"", Integer.valueOf(map.get("maxSort")+""));
		}
		return maxSortNumMap;
	}

	/**校验款号*/
	private String checkGoodsCode(List<GoodsWordsImportVo> wordsList, String retailerId,Map<String, String> goodsCodeMap) {
		String msg = null;
		Set<String> goodsCodeSet  = new HashSet<String>();
		int i = 2;
		for(GoodsWordsImportVo vo : wordsList){
			if(Utility.isEmpty(vo.getGoodsCode())){
				msg = "第"+i+"行款号不能为空";
				return msg;
			}else{
				goodsCodeSet.add(vo.getGoodsCode());
			}
			i++;
		}
		List<Map<String, Object>> list = this.commonDao.findForJdbc("select id,goods_code from t_goods where status = 'A' and retailer_id = ?", retailerId);
		
		if(list.size()>0){
			for(String goodsCode : goodsCodeSet){
				boolean exist = false;
				for(Map map : list){
					if(goodsCode.equals(map.get("goods_code")+"")){
						goodsCodeMap.put(goodsCode, map.get("id")+"");
						exist = true;
						break;
					}
				}
				if(!exist){//系统不存在
					msg = "系统不存在款号 "+goodsCode;
					return msg;
				}
			}
		}else{
			msg = "请先录入商品";
			return msg;
		}
		return null;
	}
	
	@Override
	public Map<String, String> batchSaveNewGoods(List<TNewGoodsImportVo> importList,TSpecHeadersEntity headers) throws Exception{
		Map<String,String> resultMap = new HashMap<String,String>();
		TSUser retailer = null;
		TSUser nowUser = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_02.equals(nowUser.getUserType())){
			retailer = nowUser;
		}else if(common.GlobalConstants.USER_TYPE_05.equals(nowUser.getUserType())){
			retailer = this.commonDao.get(TSUser.class, nowUser.getRetailerId());
		}
		resultMap.put("result", "BAD");//默认保存失败
		boolean hasError = false;//校验过程中有错误
		//校验品牌
//		Long tt1 = System.currentTimeMillis();
		hasError = this.checkBrands(importList,retailer.getId());
//		Long tt2 = System.currentTimeMillis();
////		System.out.println("校验品牌共耗时====="+(tt2-tt1)+"ms");
		//校验分类
//		Long tt3 = System.currentTimeMillis();
		hasError = this.checkCategories(importList,retailer.getId(),hasError);
//		Long tt4 = System.currentTimeMillis();
//		System.out.println("校验分类共耗时====="+(tt4-tt3)+"ms");
		List<TGoodsImportVo> baseList =  new ArrayList<TGoodsImportVo>();
//		Long tt5 = System.currentTimeMillis();
		hasError = this.checkGoodsCodesAndFillGoodsVoList(baseList,importList,retailer,hasError,resultMap) ;
//		Long tt6 = System.currentTimeMillis();
//		System.out.println("校验款号和规格库存合并共耗时====="+(tt6-tt5)+"ms");
		if(!hasError){//没有错误则保存数据
			//校验全部通过，批量保存（明细信息已经关联到基础信息）
			this.batchSaveGoods(baseList,retailer,headers);
			resultMap.put("successCount", baseList.size()+"");//成功数量按商品款数算
			resultMap.put("result", "OK");
		}
		importList = null;
		baseList = null;
		return resultMap;
	}

	/**校验款号和条码，组装数据列表
	 * @param dataList 组装后的导入数据列表
	 * @param importList excel数据列表
	 * @param retailerId
	 * @param hasError
	 * @param resultMap
	 * @return
	 * @throws Exception
	 */
	private boolean checkGoodsCodesAndFillGoodsVoList(List<TGoodsImportVo> dataList, List<TNewGoodsImportVo> importList,
			TSUser retailer, boolean hasError, Map<String, String> resultMap) throws Exception {
		boolean hasCodeError = false;//是否有错误
		//系统中的商品款号
		String sql = "select goods_code from t_goods where retailer_Id = '"+retailer.getId()+"' and goods_type = '1' and status = 'A'";//所有该零售商的商品（单品）款号
		List<Object> goodsCodeList = this.commonDao.findListbySql(sql);
		//系统中的条码
		String sql2 = "select bar_code from t_goods_store where  retailer_Id ='"+retailer.getId()+"' and status = 'A' and bar_code is not null";//所有该零售商的条码
		List<Object> barCodeList = this.commonDao.findListbySql(sql2);
		Set<String> goodsCodeSpecSet = new HashSet<String>();//款号，颜色，尺码 组合的唯一标识 集合
		Set<String> barCodeSet = new HashSet<String>();//条码集合
		for(TNewGoodsImportVo vo : importList){
			boolean existGoodsCode = false;//是否存在该款号
			if(Utility.isNotEmpty(goodsCodeList)){//判断款号
				for(Object goodsCode:goodsCodeList){
					if(vo.getGoodsCode().equals(goodsCode.toString())){
						existGoodsCode = true;
						break;
					}
				}
			}
			if(existGoodsCode){
				vo.setRemark(vo.getRemark()+"，系统已存在商品款号："+vo.getGoodsCode());
				hasCodeError = true;
			}
			if(Utility.isNotEmpty(vo.getBarCode())){//判断条码
				if(barCodeSet.contains(vo.getBarCode())){
					vo.setRemark(vo.getRemark()+"，表中有重复条码："+vo.getBarCode());
					hasCodeError = true;
				}
				barCodeSet.add(vo.getBarCode());//条码加入集合
				if(Utility.isNotEmpty(barCodeList)){
					for(Object barCode:barCodeList){
						if(vo.getBarCode().equals(barCode.toString())){
							vo.setRemark(vo.getRemark()+"，系统中已存在条码："+vo.getBarCode());
							hasCodeError = true;
							break;
						}
					}
				}
			}
			//款号，颜色，尺码 组合的唯一标识
			String goodsCodeSpec = vo.getGoodsCode()+vo.getSpecificationOne()+(Utility.isEmpty(vo.getSpecificationTwo())?"":vo.getSpecificationTwo());
			if(goodsCodeSpecSet.contains(goodsCodeSpec)){
				vo.setRemark(vo.getRemark()+"，表中有重复款号规格："+goodsCodeSpec);
				hasCodeError = true;
			}
			goodsCodeSpecSet.add(goodsCodeSpec);//条码加入集合
			
		}
		if(!hasError&&!hasCodeError){//没有错误
			HashMap<String,List<TGoodsStoreEntity>> storeMap = new HashMap<String, List<TGoodsStoreEntity>>();//库存明细map<款号,List<库存>>
			HashMap<String,BigDecimal> totalNumMap = new HashMap<String, BigDecimal>();//库存map<款号,总库存>
			List<TGoodsStoreEntity> tGoodsStoreDetails = null;
			int n = importList.size();
			//组装商品及库存明细列表
			for(int i = 0;i<n;i++){
				TNewGoodsImportVo vo = importList.get(i);
				String currCode = vo.getGoodsCode();//当前商品款号
				if(!existGoodsCode(dataList,currCode)){//不存在该款号则加入dataList
					TGoodsImportVo v = new TGoodsImportVo();
					MyBeanUtils.copyBeanNotNull2Bean(vo, v);
					dataList.add(v);
					tGoodsStoreDetails = new ArrayList<TGoodsStoreEntity>();
				}else{
					tGoodsStoreDetails = storeMap.get(currCode);//获取款号对应的库存明细列表
				}
				BigDecimal storeNum = vo.getStore();//库存数量
				TGoodsStoreEntity store = new TGoodsStoreEntity();
				store.setSpecificationOne(vo.getSpecificationOne());
				store.setSpecificationTwo(vo.getSpecificationTwo());
				store.setStore(storeNum);
				store.setBarCode(vo.getBarCode());
				if(tGoodsStoreDetails == null){
					tGoodsStoreDetails = new ArrayList<TGoodsStoreEntity>();
				}
				tGoodsStoreDetails.add(store);//同一个款号的库存明细加到一起
				if(storeMap.containsKey(currCode)){
					if(Utility.isNotEmpty(storeNum)){
						BigDecimal total2 = totalNumMap.get(currCode);
						total2 = total2.add(storeNum);//累加该款号的总库存
						totalNumMap.put(currCode, total2);
					}
				}else{
					if(Utility.isNotEmpty(storeNum)){
						totalNumMap.put(currCode, storeNum);
					}
				}
				storeMap.put(currCode, tGoodsStoreDetails);
			}
			//关联商品和对应的库存明细列表并更新商品库存总数
			for(TGoodsImportVo vo : dataList){
				if(totalNumMap.containsKey(vo.getGoodsCode())){
					BigDecimal stock = totalNumMap.get(vo.getGoodsCode());//该款号的总库存数
					if(stock.compareTo(BigDecimal.ZERO)>0){
						vo.setGoodsStock(stock);//更新款号库存
					}
				}
				if(storeMap.containsKey(vo.getGoodsCode())){
					vo.settGoodsStoreDetails(storeMap.get(vo.getGoodsCode()));//管理库存明细
				}
			}
		}else{//有错误则上传到七牛
			for(int i=0;i<importList.size();i++){
				TNewGoodsImportVo vo = importList.get(i);
				if(Utility.isEmpty(vo.getRemark())){
					importList.remove(vo);//去掉没有错误的记录
					i--;
				}else{
					vo.setRemark(vo.getRemark().substring(1));
				}
			}
			resultMap.put("errCount", importList.size()+"");
			String title = "商品导入错误提示（请在原导入表中修改好后再次导入）";
			String key = uploadExcelFileToQN(TNewGoodsImportVo.class, importList,retailer,"goodsUpload",title);
			resultMap.put("errKey", key);
		}
		if(hasError){
			return true;//有错误
		}else{
			return hasCodeError;
		}
	}


	/**商品列表是否包括当前款号*/
	private boolean existGoodsCode(List<TGoodsImportVo> dataList, String currCode) {
		for(TGoodsImportVo vo : dataList){
			if(vo.getGoodsCode().equals(currCode)){
				return true;
			}
		}
		return false;
	}

	/**校验分类
	 * 如果不存在该分类则在备注中增加提示
	 * @param importList
	 * @param retailerId
	 * @param hasError
	 * @return
	 */
	private boolean checkCategories(List<TNewGoodsImportVo> importList, String retailerId, boolean hasError) {
		boolean hasCategoryError = false;//是否有错误
		//一级分类（正式环境只有一个，所以导入的时候不用录入）
		Map<String,Object> topCategoryMap = this.commonDao.findOneForJdbc("select id,name from t_s_category where status = 'A' and level = ? limit 1", 1);
		//二级分类(从关联行业获取)
		List<CategoryVo> subCategoryList = this.commonDao.findObjForJdbc("select id,parent_id parentId,name,level from t_s_category where status = 'A' and id in "
				+"(SELECT trade_id from t_s_trade_user WHERE status = 'A' and user_id = '"+retailerId+"')", CategoryVo.class);
		//三级分类（二级分类下面平台和自己录入的三级分类）
		List<CategoryVo> thirdCategoryList = this.commonDao.findObjForJdbc("select id,parent_id parentId,name,level from t_s_category where status = 'A' and parent_id in "
				+"(SELECT trade_id from t_s_trade_user WHERE status = 'A' and user_id = '"+retailerId+"') and retailer_id in('admin','"+retailerId+"')", CategoryVo.class);
		String topCategoryId = null;//一级分类id 平台只有一个一级分类（生活真选）
		String topCategoryName = null;//一级分类名称
		if(Utility.isNotEmpty(topCategoryMap)){
			topCategoryId = topCategoryMap.get("id")+"";
			topCategoryName = topCategoryMap.get("name")+"";
		}
		for(TNewGoodsImportVo vo : importList){
			//设置一级分类
			vo.setTopCategoryId(topCategoryId);
			vo.setTopCategoryName(topCategoryName);
			boolean existSubCategoryId = false;
			for(CategoryVo subCat : subCategoryList){
				//设置二级分类
				if(vo.getSubCategoryName().equals(subCat.getName())){
					vo.setSubCategoryId(subCat.getId());
					existSubCategoryId = true;
					break;
				}
			}
			boolean existThridCategoryId = false;
			for(CategoryVo thridCat : thirdCategoryList){
				if(thridCat.getParentId().equals(vo.getSubCategoryId())&&vo.getThridCategoryName().equals(thridCat.getName())){
					vo.setThridCategoryId(thridCat.getId());
					existThridCategoryId = true;
					break;
				}
			}
			if(!existSubCategoryId){
				vo.setRemark(vo.getRemark()+"，系统中不存在二级分类："+vo.getSubCategoryName());
				hasCategoryError = true;
			}
			if(!existThridCategoryId){
				vo.setRemark(vo.getRemark()+"，系统中不存在三级分类："+vo.getThridCategoryName());
				hasCategoryError = true;
			}
		}
		subCategoryList = null;
		thirdCategoryList = null;
		if(hasError){
			return true;//有错误
		}else{
			return hasCategoryError;
		}
	}

	/**校验品牌
	 * 如果不存在该品牌则在备注中增加提示
	 * @param importList
	 * @param retailerId
	 */
	private boolean checkBrands(List<TNewGoodsImportVo> importList, String retailerId) {
		boolean hasError = false;
		List<BaseBrandEntity> brandList = this.findHql("from BaseBrandEntity where retailerId = ?", retailerId);
		for(TNewGoodsImportVo vo : importList){
			boolean exist = false;
			for(BaseBrandEntity brand : brandList){
				if(brand.getBrandName().equals(vo.getBrandName())){
					vo.setBrandId(brand.getId());
					vo.setBrandCode(brand.getBrandCode());
					exist = true;
					break;
				}
			}
			if(!exist){
				hasError = true;
				vo.setRemark("，系统中不存在品牌："+vo.getBrandName()+"");
			}
		}
		brandList = null;
		return hasError;
	}
	
	@Override
	public String uploadExcelFileToQN(Class<?> pojoClass, Collection<?> dataSet,TSUser retailer,String model,String title) throws IOException {
		if(retailer==null){
			TSUser nowUser = ResourceUtil.getSessionUserName();
			if(common.GlobalConstants.USER_TYPE_02.equals(nowUser.getUserType())){
				retailer = nowUser;
			}else if(common.GlobalConstants.USER_TYPE_05.equals(nowUser.getUserType())){
				retailer = this.commonDao.get(TSUser.class, nowUser.getRetailerId());
			}
		}
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title,"导出信息"), pojoClass, dataSet);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream ips = new ByteArrayInputStream(content);
        String fileName="errFile/"+retailer.getUserCode()+"/"+model+"/错误记录"+DateUtils.formatDate("yyyyMMddHHmmss");
        if (workbook instanceof HSSFWorkbook) {
        	fileName += ".xls";
        } else {
        	fileName += ".xlsx";
        }
        String uptoken = redisService.get("QN_UPTOKEN");//获取七牛uptoken
		if(Utility.isEmpty(uptoken)){
			uptoken = Auth.create(ACCESS_KEY, SECRET_KEY).uploadToken(bucket);
			redisService.set("QN_UPTOKEN", uptoken, 3500);//保存七牛uptoken
		}
		//上传七牛
        boolean result = UploadFile.upload(ips, fileName,uptoken);
//        System.out.println("excel上传结果=========="+result);
        return  GlobalConstants.DOMAIN+fileName;
	}
	
	@Override
	public Map<String, String> batchUpdateGoodsStock(List<GoodsStockImportVo> importList) throws Exception{
		Map<String,String> resultMap = new HashMap<String,String>();
		TSUser retailer = null;
		TSUser nowUser = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_02.equals(nowUser.getUserType())){
			retailer = nowUser;
		}else if(common.GlobalConstants.USER_TYPE_05.equals(nowUser.getUserType())){
			retailer = this.commonDao.get(TSUser.class, nowUser.getRetailerId());
		}
		resultMap.put("result", "BAD");//默认保存失败
		boolean hasError = false;//校验过程中有错误
		//校验条码
		hasError = this.checkBarCodes(importList);
		//商品库存变动map
		Map<String,BigDecimal> goodsStockMap = new HashMap<String,BigDecimal>();
		//校验系统是否存在条码并组装库存importList和商品库存goodsStockMap
		hasError = this.checkBarCodesAndFillGoodsStockVoList(goodsStockMap,importList,retailer,hasError,resultMap) ;
		//无错误
		if(!hasError){
			//校验全部通过，批量保存（明细信息已经关联到基础信息）
			this.batchUpdateStock(goodsStockMap,importList,retailer);
			resultMap.put("successCount", importList.size()+"");//成功数量按条码数算
			resultMap.put("result", "OK");
		}
		
		return resultMap;
	}

	private void batchUpdateStock(Map<String, BigDecimal> goodsStockMap, List<GoodsStockImportVo> importList,TSUser retailer) {
		//1.批量更新商品库存
		int total = goodsStockMap.size();//总商品数量
		int i=0,n=0;
		String sqlHead = "update t_goods set goods_stock = goods_stock + (CASE id";
		StringBuffer idStr = new StringBuffer();
		StringBuffer sqlCondition = new StringBuffer();
		for(Entry<String, BigDecimal> e : goodsStockMap.entrySet()){
			i++;
			n++;
			idStr.append(",'").append(e.getKey()).append("'");
			sqlCondition.append(" WHEN '").append(e.getKey()).append("' THEN ").append(e.getValue());
			if(i==500){//500条一批更新
				i=0;
				sqlCondition.append(" END) WHERE id in (").append(idStr.substring(1)).append(")");
				this.commonDao.updateBySqlString(sqlHead+sqlCondition);
				sqlCondition.delete(0, sqlCondition.length());
				idStr.delete(0, idStr.length());
			}else if(n==total){
				sqlCondition.append(" END) WHERE id in (").append(idStr.substring(1)).append(")");
				this.commonDao.updateBySqlString(sqlHead+sqlCondition);
				
			}
		}
		//2.批量更新库存
		total = importList.size();//总规格数量
		i=0;
		n=0;
		String sqlHeadStock = "update t_goods_store set store = (CASE id";
		StringBuffer idStrStock = new StringBuffer();
		StringBuffer sqlConditionStock = new StringBuffer();
		for(GoodsStockImportVo vo : importList){
			i++;
			n++;
			idStrStock.append(",'").append(vo.getId()).append("'");
			sqlConditionStock.append(" WHEN '").append(vo.getId()).append("' THEN ").append(vo.getStock());
			if(i==500){//500条一批更新
				i=0;
				sqlConditionStock.append(" END) WHERE id in (").append(idStrStock.substring(1)).append(")");
				this.commonDao.updateBySqlString(sqlHeadStock+sqlConditionStock);
				sqlConditionStock.delete(0, sqlConditionStock.length());
				idStrStock.delete(0, idStrStock.length());
			}else if(n==total){
				sqlConditionStock.append(" END) WHERE id in (").append(idStrStock.substring(1)).append(")");
				this.commonDao.updateBySqlString(sqlHeadStock+sqlConditionStock);
			}
		}
	}

	/**校验系统是否存在条码并组装库存importList和商品库存goodsStockMap*/
	private boolean checkBarCodesAndFillGoodsStockVoList(Map<String,BigDecimal> goodsStockMap,List<GoodsStockImportVo> importList, 
			TSUser retailer, boolean hasError, Map<String, String> resultMap) throws Exception{
		String retailerId = retailer.getId();
		boolean notExistBarCodeError = false;//不存在条码
		StringBuffer sb = new StringBuffer("select id,goods_id goodsId,bar_code barCode,store stock from t_goods_store where status = 'A' and retailer_id = '")
				.append(retailerId).append("' and bar_code in (");
		for(GoodsStockImportVo vo : importList){
			sb.append("'").append(vo.getBarCode()).append("',");
		}
		sb.deleteCharAt(sb.length()-1).append(") order by goods_id desc");
		//存在的条码库存
		List<GoodsStockImportVo> resList = this.commonDao.findObjForJdbc(sb.toString(),GoodsStockImportVo.class);
		for(GoodsStockImportVo impVo : importList){
			boolean exist = false;
			for(GoodsStockImportVo vo : resList){
				if(impVo.getBarCode().equals(vo.getBarCode())){
					impVo.setId(vo.getId());
					impVo.setGoodsId(vo.getGoodsId());
					try {
						impVo.setStockChanged(impVo.getStock().subtract(vo.getStock()));
					} catch (Exception e) {
						notExistBarCodeError = true;
						impVo.setRemark(impVo.getRemark()+"，该条码对应的库存没有限制");
						e.printStackTrace();
					}
					exist = true;
					break;
				}
			}
			if(!exist){
				notExistBarCodeError = true;
				impVo.setRemark(impVo.getRemark()+"，该条码系统中不存在");
			}
		}
		if(!hasError&&!notExistBarCodeError){//没有错误
			//组装商品库存map
			for(GoodsStockImportVo impVo : importList){
				String goodsId = impVo.getGoodsId();
				BigDecimal stock = impVo.getStockChanged();
				if(goodsStockMap.containsKey(goodsId)){
					goodsStockMap.put(goodsId, stock.add(goodsStockMap.get(goodsId)));
				}else{
					goodsStockMap.put(goodsId, stock);
				}
			}
		}else{
			for(int i=0;i<importList.size();i++){
				GoodsStockImportVo vo = importList.get(i);
				if(Utility.isEmpty(vo.getRemark())){
					importList.remove(vo);//去掉没有错误的记录
					i--;
				}else{
					vo.setRemark(vo.getRemark().substring(1));
				}
			}
			resultMap.put("errCount", importList.size()+"");
			String title = "库存导入错误提示（请在原导入表中修改好后再次导入）";
			String key = uploadExcelFileToQN(GoodsStockImportVo.class, importList,retailer,"goodsStockUpload",title);
			System.out.println(key);
			resultMap.put("errKey", key);
		}
		if(!hasError){
			return notExistBarCodeError;
		}
		return hasError;
	}

	private boolean checkBarCodes(List<GoodsStockImportVo> importList) {
		boolean hasError = false;
		Set<String> barCodes = new HashSet<String>();
		for(GoodsStockImportVo vo : importList){
			if(barCodes.contains(vo.getBarCode())){
				hasError = true;
				vo.setRemark(vo.getRemark()+"，该条码重复");
			}else{
				barCodes.add(vo.getBarCode());
			}
		}
		return hasError;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}