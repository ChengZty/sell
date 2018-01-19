package com.buss.goods.service.impl;

import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.goods.entity.TGoodsActNewsEntity;
import com.buss.goods.service.TGoodsActNewsServiceI;


@Transactional
@Service("tGoodsActNewsServiceI")
public class TGoodsActNewsService extends CommonServiceImpl implements
		TGoodsActNewsServiceI {

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TGoodsActNewsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TGoodsActNewsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TGoodsActNewsEntity)entity);
 	}
 	

	@Override
	public boolean doAddSql(TGoodsActNewsEntity t) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean doUpdateSql(TGoodsActNewsEntity t) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean doDelSql(TGoodsActNewsEntity t) {
		// TODO Auto-generated method stub
		return true;
	}
 	
 	public String replaceVal(String sql,TGoodsActNewsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{news_id}",String.valueOf(t.getNewsId()));
 		sql  = sql.replace("#{goods_act_id}",String.valueOf(t.getGoodsActId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}



}