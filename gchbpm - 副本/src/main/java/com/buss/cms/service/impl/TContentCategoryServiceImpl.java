package com.buss.cms.service.impl;
import com.buss.cms.service.TContentCategoryServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;

import com.buss.cms.entity.TContentCategoryEntity;
import common.GlobalConstants;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("tContentCategoryService")
@Transactional
public class TContentCategoryServiceImpl extends CommonServiceImpl implements TContentCategoryServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TContentCategoryEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TContentCategoryEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TContentCategoryEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TContentCategoryEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TContentCategoryEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TContentCategoryEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TContentCategoryEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{parent_id}",String.valueOf(t.getTContentCategoryEntity().getId()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{category_type}",String.valueOf(t.getCategoryType()));
 		sql  = sql.replace("#{sort_order}",String.valueOf(t.getSortOrder()));
 		sql  = sql.replace("#{is_parent}",String.valueOf(t.getIsParent()));
 		sql  = sql.replace("#{code}",String.valueOf(t.getCode()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
	/**
 	 * 根据父节点id查询列表
 	 * @param parentId
 	 * @return
 	 */
	@Override
	public List<TContentCategoryEntity> queryListByWhere(Long parentId) {
		List<TContentCategoryEntity> list = this.findByProperty(TContentCategoryEntity.class, "TContentCategoryEntity.id", parentId);
		if(Utility.isEmpty(list)){
			list = new ArrayList<TContentCategoryEntity>();
		}
		return list;
	}

	@Override
	public void myInsert(TContentCategoryEntity tContentCategoryEntity) {
		Integer functionOrder = tContentCategoryEntity.getSortOrder();
		if(null == functionOrder){
			tContentCategoryEntity.setSortOrder(0);
		}
		tContentCategoryEntity.setIsParent(false);
		tContentCategoryEntity.setStatus(GlobalConstants.STATUS_ACTIVE);
		TContentCategoryEntity parent = this.commonDao.get(TContentCategoryEntity.class,  tContentCategoryEntity.getTContentCategoryEntity().getId());
		String code =  parent.getCode()+(100+tContentCategoryEntity.getSortOrder()) ;
		tContentCategoryEntity.setCode(code);
		this.commonDao.save(tContentCategoryEntity);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            this.commonDao.updateEntitie(parent);
        }
		
	}
}