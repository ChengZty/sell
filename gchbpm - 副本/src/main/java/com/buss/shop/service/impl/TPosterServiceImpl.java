package com.buss.shop.service.impl;
import com.buss.shop.service.TPosterServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;

import com.buss.shop.entity.TPosterEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

@Service("tPosterService")
@Transactional
public class TPosterServiceImpl extends CommonServiceImpl implements TPosterServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPosterEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPosterEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPosterEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPosterEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPosterEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPosterEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPosterEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{title}",String.valueOf(t.getTitle()));
 		sql  = sql.replace("#{author}",String.valueOf(t.getAuthor()));
 		sql  = sql.replace("#{cover_pic}",String.valueOf(t.getCoverPic()));
 		sql  = sql.replace("#{context}",String.valueOf(t.getContext()));
 		sql  = sql.replace("#{context_html}",String.valueOf(t.getContextHtml()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{post_status}",String.valueOf(t.getPostStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void doChangeSort(String id, String type, String sortNum,String postStatus) {
 		String retailerId = ResourceUtil.getRetailerId();
		String sortOrder = null;
		String compareTag = null;
		if("U".equals(type)){//up
			sortOrder = "asc";
			compareTag = ">";
			
		}else if("D".equals(type)){//down
			sortOrder = "desc";
			compareTag = "<";
		}
		StringBuffer querySql = new StringBuffer("SELECT id,sort_num FROM t_poster WHERE 1=1");
		if(Utility.isNotEmpty(retailerId)){
			querySql.append(" and retailer_id ='").append(retailerId).append("'");
		}
		querySql.append(" and status = 'A' and post_status = '").append(postStatus).append("' and sort_num ").append(compareTag).append(sortNum);
		querySql.append(" ORDER BY sort_num ").append(sortOrder).append(" limit 1");
		List<Map<String,Object>> list = this.commonDao.findForJdbc(querySql.toString(), null);
		if(Utility.isNotEmpty(list)){
			String otherId = list.get(0).get("id")+"";
			String otherSortNum = list.get(0).get("sort_num")+"";
			//更换排序
			this.commonDao.updateBySqlString("update t_poster set sort_num = '"+otherSortNum+"' where id ='"+id+"'");
			this.commonDao.updateBySqlString("update t_poster set sort_num = '"+sortNum+"' where id ='"+otherId+"'");
		}else{
			if("U".equals(type)){//到顶了
				this.commonDao.updateBySqlString("update t_poster set sort_num = sort_num+1 where id ='"+id+"'");
				
			}else if("D".equals(type)){//到底了
				this.commonDao.updateBySqlString("update t_poster set sort_num = sort_num-1 where id ='"+id+"'");
			}
		}
 		
 	}
}