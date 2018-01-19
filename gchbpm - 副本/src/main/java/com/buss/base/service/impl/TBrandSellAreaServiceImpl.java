package com.buss.base.service.impl;
import com.buss.base.service.TBrandSellAreaServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.buss.base.entity.TBrandSellAreaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("tBrandSellAreaService")
@Transactional
public class TBrandSellAreaServiceImpl extends CommonServiceImpl implements TBrandSellAreaServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBrandSellAreaEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBrandSellAreaEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBrandSellAreaEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBrandSellAreaEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBrandSellAreaEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBrandSellAreaEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBrandSellAreaEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{brand_id}",String.valueOf(t.getBrandId()));
 		sql  = sql.replace("#{is_all_province}",String.valueOf(t.getIsAllProvince()));
 		sql  = sql.replace("#{province_id}",String.valueOf(t.getProvinceId()));
 		sql  = sql.replace("#{city_id}",String.valueOf(t.getCityId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**保存可售地区
 	 * @param tBrandSellArea
 	 */
 	@Override
 	public void saveSellArea(TBrandSellAreaEntity tBrandSellArea) {
 		String retailerId = ResourceUtil.getRetailerId();
		tBrandSellArea.setStatus(common.GlobalConstants.STATUS_ACTIVE);
		tBrandSellArea.setRetailerId(retailerId);
		commonDao.save(tBrandSellArea);
		List<TBrandSellAreaEntity> list = commonDao.findHql("from TBrandSellAreaEntity where retailerId = ? and isAllProvince = '1' and brandId = ?", retailerId,tBrandSellArea.getBrandId());
		if(list.size()>0){
			TBrandSellAreaEntity brandSell = list.get(0);//全国范围
			LogUtil.info("----------------新增第一条可售地区，删除全国可售的记录，品牌ID:"+tBrandSellArea.getBrandId());
			brandSell.setStatus(common.GlobalConstants.STATUS_INACTIVE);
			commonDao.updateEntitie(brandSell);
		}
 	}

 	/**删除可售地区
 	 * @param tBrandSellArea
 	 */
 	@Override
 	public void delSellArea(TBrandSellAreaEntity tBrandSellArea) {
 		String sql = "select count(1) from t_brand_sell_area where status = 'A' and retailer_id = '"+tBrandSellArea.getRetailerId()+"' and brand_id = '"+tBrandSellArea.getBrandId()+"'";
// 		Long n = commonDao.getCountForJdbc(sql);
 		tBrandSellArea.setStatus(common.GlobalConstants.STATUS_INACTIVE);
 		commonDao.updateEntitie(tBrandSellArea);
 		Long count = commonDao.getCountForJdbc(sql);
 		if(count==0){//全都删除后增加一条默认全国的记录
 			this.initSellArea(tBrandSellArea.getBrandId());
 		}
 	}
 	
 	/**初始化品牌的全国可售地区
 	 * @param brandId
 	 */
 	private void initSellArea(String brandId) {
 		LogUtil.info("----------------可售地区都删除了，增加一条全国可售的记录，品牌ID:"+brandId);
 		String retailerId = ResourceUtil.getRetailerId();
 		TBrandSellAreaEntity sellArea = new TBrandSellAreaEntity();
		sellArea.setStatus(common.GlobalConstants.STATUS_ACTIVE);
		sellArea.setIsAllProvince("1");
		sellArea.setBrandId(brandId);
		sellArea.setRetailerId(retailerId);
		commonDao.save(sellArea);
	}

 	/**批量删除可售地区
 	 * @param ids
 	 * @param brandId
 	 */
	@Override
 	public void batchDelSellArea(String ids, String brandId) {
 		TSUser user = ResourceUtil.getSessionUserName();
 		String retailerId = ResourceUtil.getRetailerId();
 		String sql = "select count(1) from t_brand_sell_area where status = 'A' and retailer_id = '"+retailerId+"' and brand_id = '"+brandId+"'";
		StringBuffer updateSql = new StringBuffer("update t_brand_sell_area set status = 'I', update_by = '").append(user.getUserName()).append("',update_name = '")
		.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("' where id  in(");
		for(String id : ids.split(",")){
			updateSql.append("'").append(id).append("',");
		}
		updateSql = updateSql.deleteCharAt(updateSql.length()-1).append(")");
		commonDao.updateBySqlString(updateSql.toString());
		Long count = commonDao.getCountForJdbc(sql);
		if(count==0){//全都删除后增加一条默认全国的记录
 			this.initSellArea(brandId);
 		}
 	}
}