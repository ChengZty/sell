package com.buss.shop.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.shop.entity.TSmsPicClassEntity;
import com.buss.shop.entity.TSmsPicInfoEntity;
import com.buss.shop.service.TSmsPicInfoServiceI;



@Service("tSmsPicInfoService")
@Transactional
public class TSmsPicInfoServiceImpl extends CommonServiceImpl implements TSmsPicInfoServiceI {

	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSmsPicInfoEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSmsPicInfoEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSmsPicInfoEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSmsPicInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSmsPicInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSmsPicInfoEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSmsPicInfoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{class_id}",String.valueOf(t.getClassId()));
 		sql  = sql.replace("#{pic_url}",String.valueOf(t.getPicUrl()));
 		return sql;
 	}
 	
 	public List<TSmsPicInfoEntity> getPicByClassAndPage(String retailerId,String classId,int page,int rows){
 		List<TSmsPicInfoEntity> picClass = new ArrayList<TSmsPicInfoEntity>();
 		
 		String sql = "select * from t_sms_pic_info where  status='A' order by update_date ";
 		picClass = this.findObjForJdbc(sql, page, rows, TSmsPicInfoEntity.class);
 		//picClass = this.findListbySql(sql);
 		
 		
 		return picClass;
 	}
 	public int updatePicStatusByClass(String classId,String unclassified){
 		String sql = "update t_sms_pic_info set class_id = '" + unclassified + "', update_date = '"+Utility.getCurrentTimestamp() +"' where class_id = '" + classId + "'";
 		int count = this.commonDao.updateBySqlString(sql);
 		return count;
 	}
	
 	@Override
	public int delPicStatusByClass(String classId) {
 		String sql = "update t_sms_pic_info set status='I', update_date = '"+Utility.getCurrentTimestamp() +"' where class_id = '" + classId + "'";
 		int count = this.commonDao.updateBySqlString(sql);
 		return count;
	}

 	@Override
 	public void doBatchDel(String ids) {
 		for(String id : ids.split(",")){
 			TSmsPicInfoEntity picInfo = this.commonDao.get(TSmsPicInfoEntity.class, id);
 			picInfo.setStatus(common.GlobalConstants.STATUS_INACTIVE);
 			this.commonDao.updateEntitie(picInfo);
 			TSmsPicClassEntity picClass = this.commonDao.get(TSmsPicClassEntity.class, picInfo.getClassId());
 			picClass.setCount(picClass.getCount()-1);
 			this.commonDao.updateEntitie(picClass);
 		}
 	}
}