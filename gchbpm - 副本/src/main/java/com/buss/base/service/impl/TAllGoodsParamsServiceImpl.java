package com.buss.base.service.impl;
import com.buss.base.service.TAllGoodsParamsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.base.entity.TAllGoodsParamsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tAllGoodsParamsService")
@Transactional
public class TAllGoodsParamsServiceImpl extends CommonServiceImpl implements TAllGoodsParamsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TAllGoodsParamsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TAllGoodsParamsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TAllGoodsParamsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TAllGoodsParamsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TAllGoodsParamsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TAllGoodsParamsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TAllGoodsParamsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{category_id}",String.valueOf(t.getCategoryId()));
 		sql  = sql.replace("#{param_name}",String.valueOf(t.getParamName()));
 		sql  = sql.replace("#{param_values}",String.valueOf(t.getParamValues()));
 		sql  = sql.replace("#{multi_select}",String.valueOf(t.getMultiSelect()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}