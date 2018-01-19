package com.buss.user.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.user.entity.TPersonTagsEntity;
import com.buss.user.entity.TTagCategoriesEntity;
import com.buss.user.service.TPersonTagsServiceI;

@Service("tPersonTagsService")
@Transactional
public class TPersonTagsServiceImpl extends CommonServiceImpl implements TPersonTagsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPersonTagsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPersonTagsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPersonTagsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPersonTagsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPersonTagsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPersonTagsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPersonTagsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{tag_code}",String.valueOf(t.getTagCode()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void deletePersonTags(TPersonTagsEntity tPersonTags) {
 		String countSql = "select count(1) from t_person_tags where status = 'A' and user_id ='"+tPersonTags.getUserId()+"'";
 		Long num = this.commonDao.getCountForJdbc(countSql);
 		if(num==1){
 			String sql = "update t_person set has_tags = '0' where user_id ='"+tPersonTags.getUserId()+"'";
 			this.commonDao.updateBySqlString(sql);
 		}
 		TSUser user = ResourceUtil.getSessionUserName();
 		//删除标签
 		String updateSql = this.commonDao.getDeleteSqlForUpdate("t_person_tags", "id", tPersonTags.getId(), user);
 		this.commonDao.updateBySqlString(updateSql);
 		
 		
 	}
 	
 	@Override
 	public void updatePersonTags(TPersonTagsEntity tPersonTags) {
 		this.saveOrUpdate(tPersonTags);
 		updateVisibleCategs(tPersonTags);
 		
 	}
 	
 	@Override
 	public void savePersonTags(TPersonTagsEntity tPersonTags) {
 		String sql = "update t_person set has_tags = '1' where user_id ='"+tPersonTags.getUserId()+"'";
 		this.commonDao.updateBySqlString(sql);
 		this.save(tPersonTags);
 		updateVisibleCategs(tPersonTags);
 		
 	}
 	
	/**
	 * 更新标签类目
	 * 
	 * @param request
	 * @return
	 */
	private void updateVisibleCategs(TPersonTagsEntity tPersonTags) {
			String newVisibleCatgs = tPersonTags.getCategries();
			List<TTagCategoriesEntity> visiblelist = this.commonDao.findByProperty(TTagCategoriesEntity.class, "tagId",	tPersonTags.getId());
			Map<String, TTagCategoriesEntity> map = new HashMap<String, TTagCategoriesEntity>();
			for (TTagCategoriesEntity categry : visiblelist) {
				map.put(categry.getCategryId(), categry);
			}
			String[] visibleCategry = newVisibleCatgs.split(",");
			Set<String> set = new HashSet<String>();
			for (String s : visibleCategry) {
				set.add(s);
			}
			updateCompare(set, tPersonTags.getId(), map);
	}
	
	/**
	 * 权限比较
	 * 
	 * @param set
	 *            最新的标签类目
	 * @param tagId
	 *            当前标签ID
	 * @param map
	 *            旧的标签类目
	 */
	private void updateCompare(Set<String> set, String tagId,	Map<String, TTagCategoriesEntity> map) {
		List<TTagCategoriesEntity> entitys = new ArrayList<TTagCategoriesEntity>();
		List<TTagCategoriesEntity> deleteEntitys = new ArrayList<TTagCategoriesEntity>();
		for (String s : set) {
			if (map.containsKey(s)) {
				map.remove(s);
			} else {
				TTagCategoriesEntity tc = new TTagCategoriesEntity();
				tc.setCategryId(s);
				tc.setTagId(tagId);
				entitys.add(tc);
			}
		}
		Collection<TTagCategoriesEntity> collection = map.values();
		Iterator<TTagCategoriesEntity> it = collection.iterator();
		for (; it.hasNext();) {
			deleteEntitys.add(it.next());
		}
		this.commonDao.batchSave(entitys);
		this.commonDao.deleteAllEntitie(deleteEntitys);

	}
	
}