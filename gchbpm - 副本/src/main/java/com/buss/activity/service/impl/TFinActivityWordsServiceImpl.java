package com.buss.activity.service.impl;
import com.buss.activity.service.TFinActivityWordsServiceI;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.service.SystemService;

import com.buss.activity.entity.TFinActivityWordsEntity;
import com.buss.activity.entity.TGoodsActWordsEntity;
import com.buss.goods.entity.TGoodsEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

@Service("tFinActivityWordsService")
@Transactional
public class TFinActivityWordsServiceImpl extends CommonServiceImpl implements TFinActivityWordsServiceI {

 	public void getWordsGoodsList(HttpServletRequest request, DataGrid dataGrid){
 		try{
			String retailerId = ResourceUtil.getRetailerId();
			String wordsId = request.getParameter("wordsId"); //话术id
			String title = request.getParameter("title");//商品短标题
			String goodsName = request.getParameter("goodsName");//商品名称
			String goodsCode = request.getParameter("goodsCode");//款号
			String brandName = request.getParameter("brandName");//品牌
			String goodsStatus = request.getParameter("goodsStatus");//商品状态
			String hasWords = request.getParameter("hasWords");//是否有话术
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			sql.append("SELECT t.* from (SELECT g.id,g.small_pic smallPic,IFNULL(g.title,'') title,g.goods_name goodsName,g.goods_code goodsCode,g.brand_name brandName,")
				.append("g.thrid_category_id thridCategoryId,g.retailer_id retailerId,g.retailer_name retailerName,g.sort_num sortNum,")
				.append("g.update_date updateDate,g.goods_status  goodsStatus,IF(COUNT(w.id)>0,1,0) hasWords")
				.append(" from t_goods g LEFT JOIN t_fin_activity_words w on g.id = w.fin_act_id and w.`status` = 'A' and w.words_type = 2")//商品
				.append(" where g.`status` = 'A'");
			countSql.append("SELECT count(1) from (SELECT g.id,IF(COUNT(w.id)>0,1,0) hasWords")
				.append(" from t_goods g LEFT JOIN t_fin_activity_words w on g.id = w.fin_act_id and w.`status` = 'A' and w.words_type = 2")//商品
				.append(" where g.`status` = 'A'");

			if(Utility.isNotEmpty(wordsId)){
				sql.append(" and g.id in (select fin_act_id id from t_fin_activity_words where status='A' and words_id='").append(wordsId).append("') ");
				countSql.append(" and g.id in (select fin_act_id id from t_fin_activity_words where status='A' and words_id='").append(wordsId).append("') ");
			}
			if(Utility.isNotEmpty(retailerId)){
				sql.append(" and g.retailer_id = '").append(retailerId).append("' ");
				countSql.append(" and g.retailer_id = '").append(retailerId).append("' ");
			}
			if(Utility.isNotEmpty(title)){
				sql.append(" and g.title like '%").append(title).append("%' ");
				countSql.append(" and g.title like '%").append(title).append("%' ");
			}
			if(Utility.isNotEmpty(goodsName)){
				sql.append(" and g.goods_name like '%").append(goodsName).append("%' ");
				countSql.append(" and g.goods_name like '%").append(goodsName).append("%' ");
			}
			if(Utility.isNotEmpty(goodsCode)){
				sql.append(" and g.goods_code like '%").append(goodsCode).append("%' ");
				countSql.append(" and g.goods_code like '%").append(goodsCode).append("%' ");
			}
			if(Utility.isNotEmpty(brandName)){
				sql.append(" and g.brand_name like '%").append(brandName).append("%' ");
				countSql.append(" and g.brand_name like '%").append(brandName).append("%' ");
			}
			if(Utility.isNotEmpty(goodsStatus)){
				sql.append(" and g.goods_status = '").append(goodsStatus).append("' ");
				countSql.append(" and g.goods_status = '").append(goodsStatus).append("' ");
			}
			sql.append(" and g.goods_type = '").append(TGoodsEntity.GOODS_TYPE_1).append("' and g.new_goods_type = '")//单品
				.append(TGoodsEntity.NEW_GOODS_TYPE_2).append("'")//新模块商品
				.append(" GROUP BY g.id ) t where 1=1");
			countSql.append(" and g.goods_type = '").append(TGoodsEntity.GOODS_TYPE_1).append("' and g.new_goods_type = '")//单品
			.append(TGoodsEntity.NEW_GOODS_TYPE_2).append("'")//新模块商品
			.append(" GROUP BY g.id ) t where 1=1");
			if(Utility.isNotEmpty(hasWords)){
				sql.append(" and t.hasWords = ").append(hasWords);
				countSql.append(" and t.hasWords = ").append(hasWords);
			}
			List<Map<String, Object>> resultList = null;
			int total = this.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  this.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
 		
 		
 		
 	}

	@Override
	public void doDelActWords(String id,String wordsId) {
        String sql = "update  t_fin_activity_words tf set tf.status='I'  where tf.fin_act_id='"+id+"' and tf.words_id='" +wordsId+"'";
        this.updateBySqlString(sql);		
	}
}