package com.buss.visibleGoods.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.visibleGoods.entity.TRetailerBrandCategoryEntity;
import com.buss.visibleGoods.entity.TRetailerGoodsConditionsEntity;
import com.buss.visibleGoods.entity.TRetailerRelationEntity;
import com.buss.visibleGoods.service.TRetailerRelationServiceI;

@Service("tRetailerRelationService")
@Transactional
public class TRetailerRelationServiceImpl extends CommonServiceImpl implements TRetailerRelationServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TRetailerRelationEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TRetailerRelationEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TRetailerRelationEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TRetailerRelationEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TRetailerRelationEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TRetailerRelationEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TRetailerRelationEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{other_retailer_id}",String.valueOf(t.getOtherRetailerId()));
 		sql  = sql.replace("#{other_retailer_type}",String.valueOf(t.getOtherRetailerType()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void batchSave(HttpServletRequest request) {
 		String retailerId = request.getParameter("retailerId");
 		String otherRetailerType = request.getParameter("otherRetailerType");
 		String otherRetailerIds = request.getParameter("otherRetailerIds");
 		if(Utility.isNotEmpty(otherRetailerIds)){
 			for(String otherRetailerId : otherRetailerIds.split(",")){
 				TRetailerRelationEntity entity = new TRetailerRelationEntity();
 				entity.setOtherRetailerId(otherRetailerId);
 				entity.setRetailerId(retailerId);
 				entity.setOtherRetailerType(otherRetailerType);
 				entity.setStatus("A");
 				this.commonDao.save(entity);
 			}
 		}
 	}

	/**更新零售商商品条件
	 * @param retailerId
	 */
 	@Override
	public void updateRetailerGoodsConditions(String retailerId) {
		TRetailerGoodsConditionsEntity retailerGoods = this.commonDao.findUniqueByProperty(TRetailerGoodsConditionsEntity.class, "retailerId", retailerId);
		List<TRetailerRelationEntity> retailerList = this.commonDao.findByProperty(TRetailerRelationEntity.class, "retailerId", retailerId);
		List<TRetailerRelationEntity> rList = new ArrayList<TRetailerRelationEntity>();//零售商列表
		List<TRetailerRelationEntity> cList = new ArrayList<TRetailerRelationEntity>();//云商列表
		if(Utility.isNotEmpty(retailerList)){
			StringBuffer rCondition = new StringBuffer();//零售商sql条件
			StringBuffer cCondition = new StringBuffer();//云商sql条件
			
			
			for(TRetailerRelationEntity entity : retailerList){
				if("1".equals(entity.getOtherRetailerType())){//零售商（拼接零售商sql条件）
					rList.add(entity);
				}else if("2".equals(entity.getOtherRetailerType())){//云商（拼接云商sql条件）
					cList.add(entity);
				}
			}
			if(Utility.isNotEmpty(rList)){
				//零售商sql条件
				StringBuffer rBrandCategoryHql = new StringBuffer("from TRetailerBrandCategoryEntity where retailerId = ? and otherRetailerId in (");
				for(TRetailerRelationEntity entity : rList){
					rBrandCategoryHql.append("'").append(entity.getOtherRetailerId()).append("',");
				}
				rBrandCategoryHql = rBrandCategoryHql.deleteCharAt(rBrandCategoryHql.length()-1).append(")");
				List<TRetailerBrandCategoryEntity> brandCatList = this.commonDao.findHql(rBrandCategoryHql.toString(), retailerId);
				rBrandCategoryHql = null;
				if(Utility.isNotEmpty(brandCatList)){//有设置零售商的品牌或者分类
					for(TRetailerRelationEntity rEntity : rList){
						boolean hasDetail = false;//是否设置了品牌或者分类
						for(TRetailerBrandCategoryEntity brandCat: brandCatList){
							if(rEntity.getOtherRetailerId().equals(brandCat.getOtherRetailerId())){
								hasDetail = true;
								if(Utility.isEmpty(rCondition)){
									rCondition.append("(retailer_id='").append(rEntity.getOtherRetailerId()).append("' ");
									if(Utility.isNotEmpty(brandCat.getBrandId())){
										rCondition.append("and brand_id='").append(brandCat.getBrandId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getTopCategoryId())){
										rCondition.append("and top_category_id='").append(brandCat.getTopCategoryId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getSubCategoryId())){
										rCondition.append("and sub_category_id='").append(brandCat.getSubCategoryId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getThridCategoryId())){
										rCondition.append("and thrid_category_id='").append(brandCat.getThridCategoryId()).append("' ");
									}
									rCondition.append(")");
								}else{//同一条记录中的品牌和分类用and连接，不同记录用or连接
									rCondition.append("or (retailer_id='").append(rEntity.getOtherRetailerId()).append("' ");
									if(Utility.isNotEmpty(brandCat.getBrandId())){
										rCondition.append("and brand_id='").append(brandCat.getBrandId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getTopCategoryId())){
										rCondition.append("and top_category_id='").append(brandCat.getTopCategoryId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getSubCategoryId())){
										rCondition.append("and sub_category_id='").append(brandCat.getSubCategoryId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getThridCategoryId())){
										rCondition.append("and thrid_category_id='").append(brandCat.getThridCategoryId()).append("' ");
									}
									rCondition.append(") ");
								}
							}
						}
						if(!hasDetail){//未设置品牌或者分类
							if(Utility.isEmpty(rCondition)){
								rCondition.append("retailer_id='").append(rEntity.getOtherRetailerId()).append("' ");
							}else{
								rCondition.append("or retailer_id='").append(rEntity.getOtherRetailerId()).append("' ");
							}
						}
					}
				}else{//全部都没有设置零售商的品牌或者分类
					rCondition.append("retailer_id in (");
					for(TRetailerRelationEntity entity : rList){
						rCondition.append("'").append(entity.getOtherRetailerId()).append("',");
					}
					rCondition = rCondition.deleteCharAt(rCondition.length()-1).append(") ");
				}
			}else{
				rCondition.append("1<>1");
			}
			if(Utility.isNotEmpty(cList)){
				//零售商sql条件
				StringBuffer cBrandCategoryHql = new StringBuffer("from TRetailerBrandCategoryEntity where retailerId = ? and otherRetailerId in (");
				for(TRetailerRelationEntity entity : cList){
					cBrandCategoryHql.append("'").append(entity.getOtherRetailerId()).append("',");
				}
				cBrandCategoryHql = cBrandCategoryHql.deleteCharAt(cBrandCategoryHql.length()-1).append(")");
				List<TRetailerBrandCategoryEntity> brandCatList = this.commonDao.findHql(cBrandCategoryHql.toString(), retailerId);
				cBrandCategoryHql = null;
				if(Utility.isNotEmpty(brandCatList)){//有设置零售商的品牌或者分类
					for(TRetailerRelationEntity rEntity : cList){
						boolean hasDetail = false;//是否设置了品牌或者分类
						for(TRetailerBrandCategoryEntity brandCat: brandCatList){
							if(rEntity.getOtherRetailerId().equals(brandCat.getOtherRetailerId())){
								hasDetail = true;
								if(Utility.isEmpty(cCondition)){
									cCondition.append("(retailer_id='").append(rEntity.getOtherRetailerId()).append("' ");
									if(Utility.isNotEmpty(brandCat.getBrandId())){
										cCondition.append("and brand_id='").append(brandCat.getBrandId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getTopCategoryId())){
										cCondition.append("and top_category_id='").append(brandCat.getTopCategoryId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getSubCategoryId())){
										cCondition.append("and sub_category_id='").append(brandCat.getSubCategoryId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getThridCategoryId())){
										cCondition.append("and thrid_category_id='").append(brandCat.getThridCategoryId()).append("' ");
									}
									cCondition.append(")");
								}else{//同一条记录中的品牌和分类用and连接，不同记录用or连接
									cCondition.append("or (retailer_id='").append(rEntity.getOtherRetailerId()).append("' ");
									if(Utility.isNotEmpty(brandCat.getBrandId())){
										cCondition.append("and brand_id='").append(brandCat.getBrandId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getTopCategoryId())){
										cCondition.append("and top_category_id='").append(brandCat.getTopCategoryId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getSubCategoryId())){
										cCondition.append("and sub_category_id='").append(brandCat.getSubCategoryId()).append("' ");
									}
									if(Utility.isNotEmpty(brandCat.getThridCategoryId())){
										cCondition.append("and thrid_category_id='").append(brandCat.getThridCategoryId()).append("' ");
									}
									cCondition.append(") ");
								}
							}
						}
						if(!hasDetail){//未设置品牌或者分类
							if(Utility.isEmpty(cCondition)){
								cCondition.append("retailer_id='").append(rEntity.getOtherRetailerId()).append("' ");
							}else{
								cCondition.append("or retailer_id='").append(rEntity.getOtherRetailerId()).append("' ");
							}
						}
					}
				}else{//全部都没有设置零售商的品牌或者分类
					cCondition.append("retailer_id in (");
					for(TRetailerRelationEntity entity : cList){
						cCondition.append("'").append(entity.getOtherRetailerId()).append("',");
					}
					cCondition = cCondition.deleteCharAt(cCondition.length()-1).append(") ");
				}
			}else{
				cCondition.append("1<>1");
			}
			retailerGoods.setRetailerConditions(rCondition.toString());
			retailerGoods.setCloudConditions(cCondition.toString());
			this.commonDao.updateEntitie(retailerGoods);
			retailerList = null;
			rList = null;
			cList = null;
			rCondition = null;
			cCondition = null;
		}else{
			retailerGoods.setRetailerConditions("1<>1");
			retailerGoods.setCloudConditions("1<>1");
			this.commonDao.updateEntitie(retailerGoods);
		}
	}
 	
 	@Override
 	public void deleteRelation(TRetailerRelationEntity tRetailerRelation) {
 		tRetailerRelation.setStatus("I");
 		this.commonDao.updateEntitie(tRetailerRelation);
 		//删除该零售商下面对应的所有品牌和分类
 		TSUser user = ResourceUtil.getSessionUserName();
 		StringBuffer sql = new StringBuffer("update t_retailer_brand_category set status = 'I',update_by = '").append(user.getUserName()).append("',update_name = '")
		.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("' where retailer_Id = '")
		.append(tRetailerRelation.getRetailerId()).append("' and other_Retailer_Id = '").append(tRetailerRelation.getOtherRetailerId()).append("'");
 		this.commonDao.updateBySqlString(sql.toString());
 	}
}