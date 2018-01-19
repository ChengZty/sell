package com.buss.base.service.impl;
import com.buss.base.service.TBrandShowServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;

import com.buss.base.entity.TBrandPicsEntity;
import com.buss.base.entity.TBrandShowEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.io.Serializable;

@Service("tBrandShowService")
@Transactional
public class TBrandShowServiceImpl extends CommonServiceImpl implements TBrandShowServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBrandShowEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBrandShowEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBrandShowEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBrandShowEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBrandShowEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBrandShowEntity t){
	 	return true;
 	}
 	
 
 	@Override
 	public void updateBrandInfo(TBrandShowEntity tBrandShow) {
 		if(Utility.isEmpty(tBrandShow.getFreeAmount())){//改排序
 			this.commonDao.updateBySqlString("update t_brand_show set order_num = "+tBrandShow.getOrderNum()+" where id='"+tBrandShow.getId()+"'" );
 		}else{
 			this.commonDao.updateBySqlString("update t_brand_show set free_amount = "+tBrandShow.getFreeAmount()+",order_num = "+tBrandShow.getOrderNum()
 					+",fare = "+tBrandShow.getFare()+" where id='"+tBrandShow.getId()+"'" );
 		}
 	}
 	
 	@Override
 	public List<TBrandShowEntity> getShowBrands(String retailerId){
 		List<TBrandShowEntity> list = this.commonDao.findHql("from TBrandShowEntity where retailerId = ?", retailerId);
		return list;
 	}
}