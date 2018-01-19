package com.buss.base.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jodd.util.StringUtil;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.base.entity.BaseBrandEntity;
import com.buss.base.entity.TBrandDescEntity;
import com.buss.base.entity.TBrandPicsEntity;
import com.buss.base.entity.TBrandSellAreaEntity;
import com.buss.base.service.BaseBrandServiceI;

@Service("baseBrandService")
@Transactional
public class BaseBrandServiceImpl extends CommonServiceImpl implements BaseBrandServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((BaseBrandEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((BaseBrandEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((BaseBrandEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(BaseBrandEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(BaseBrandEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(BaseBrandEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,BaseBrandEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{brand_name}",String.valueOf(t.getBrandName()));
// 		sql  = sql.replace("#{brand_type}",String.valueOf(t.getBrandType()));
 		sql  = sql.replace("#{brand_pic}",String.valueOf(t.getBrandPic()));
 		sql  = sql.replace("#{sort_no}",String.valueOf(t.getSortNo()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

 	@Override
 	public void saveBrand(BaseBrandEntity baseBrand) {
 		this.commonDao.save(baseBrand);
 		TBrandDescEntity brandDesc = baseBrand.getBrandDesc();
 		if(Utility.isNotEmpty(brandDesc)){
 			brandDesc.setBrandId(baseBrand.getId());
 			this.commonDao.save(brandDesc);
 		}
 		this.updateBrandPics(baseBrand);
 		//保存品牌可售区域（默认的全国可售）
// 		TBrandSellAreaEntity sellArea = new TBrandSellAreaEntity();
//		sellArea.setStatus(common.GlobalConstants.STATUS_ACTIVE);
//		sellArea.setIsAllProvince("1");
//		sellArea.setBrandId(baseBrand.getId());
//		sellArea.setRetailerId(baseBrand.getRetailerId());
//		commonDao.save(sellArea);
 	}
 	
 	@Override
 	public void updateBrand(BaseBrandEntity baseBrand) {
 		this.commonDao.updateEntitie(baseBrand);
 		TBrandDescEntity brandDesc = baseBrand.getBrandDesc();
 		if(Utility.isNotEmpty(brandDesc)){
// 			TBrandDescEntity brandDesc = this.commonDao.get(TBrandDescEntity.class, baseBrand.getBrandDesc().getId());
// 			brandDesc.setContent(baseBrand.getBrandDesc().getContent());
 			if(StringUtil.isEmpty(brandDesc.getId())){
 				brandDesc.setBrandId(baseBrand.getId());
 				this.commonDao.save(brandDesc);
 			}else{
 				this.commonDao.updateEntitie(baseBrand.getBrandDesc());
 			}
 		}
 		this.updateBrandPics(baseBrand);
 	}

 	/**更新广告图片
 	 * @param baseBrand
 	 */
 	private void updateBrandPics(BaseBrandEntity baseBrand) {
 		List<TBrandPicsEntity> oldDetailPics = this.commonDao.findHql("from TBrandPicsEntity where  brandId =? and status = 'A' order by orderNum asc", baseBrand.getId());
 		List<TBrandPicsEntity> newDetailPics = baseBrand.getDetailPics();
 		if(Utility.isEmpty(oldDetailPics)){//以前没有广告图
 			if(!Utility.isEmpty(newDetailPics)){//新增
 	 			int n=newDetailPics.size();
 	 			for(int i=0;i<n;i++){
 	 				TBrandPicsEntity entity = newDetailPics.get(i);
 	 				entity.setBrandId(baseBrand.getId());
 	 				entity.setOrderNum(i);
 	 				entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 	 				this.commonDao.save(entity);
 	 			}
 	 		}
 		}else{//以前有广告图
 			if(Utility.isEmpty(newDetailPics)){
 				this.commonDao.updateBySqlString("update t_brand_pics set status = 'I',update_date = '"+Utility.getCurrentTimestamp()
 	 					+"' where brand_id ='"+baseBrand.getId()+"'");
 			}else{
 				int n=newDetailPics.size();
 				for(int i=0;i<n;i++){
 					TBrandPicsEntity entity = newDetailPics.get(i);
 					if(Utility.isEmpty(entity.getId())){//新增
 						entity.setBrandId(baseBrand.getId());
// 	 	 				entity.setRetailerId(tBrandShow.getRetailerId());
 						entity.setOrderNum(i);
 						entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 						this.commonDao.save(entity);
 					}else{//修改
 						this.commonDao.updateBySqlString("update t_brand_pics set update_date = '"+Utility.getCurrentTimestamp()
 		 	 					+"',order_num ="+i+"  where id ='"+entity.getId()+"'");
 						for(TBrandPicsEntity old : oldDetailPics){
 							if(entity.getId().equals(old.getId())){//把有的都移除，剩余的都要是删除的
 								oldDetailPics.remove(old);
 								break;
 							}
 						}
 					}
 				}
 				if(!Utility.isEmpty(oldDetailPics)){//删除
 					for(TBrandPicsEntity entity : oldDetailPics){
 						this.commonDao.updateBySqlString("update t_brand_pics set status = 'I',update_date = '"+Utility.getCurrentTimestamp()
 	 					+"' where id ='"+entity.getId()+"'");
					}
 				}
 			}
 		}
	}
 	
 	
	@Override
	public void saveBatch(List<BaseBrandEntity> entitys) {
		for (int i = 0; i < entitys.size(); i++) {
			entitys.get(i).setStatus("A");
			getSession().save(entitys.get(i));
			if (i % 20 == 0) {
				// 20个对象后才清理缓存，写入数据库
				getSession().flush();
				getSession().clear();
			}
		}
		// 最后清理一下----防止大于20小于40的不保存
		getSession().flush();
		getSession().clear();
		
	}
}