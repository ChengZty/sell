package com.buss.words.service.impl;
import com.buss.words.service.TVisibleWordsServiceI;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;

import com.buss.words.entity.TVisibleWordsEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

@Service("tVisibleWordsService")
@Transactional
public class TVisibleWordsServiceImpl extends CommonServiceImpl implements TVisibleWordsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TVisibleWordsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TVisibleWordsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TVisibleWordsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TVisibleWordsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TVisibleWordsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TVisibleWordsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TVisibleWordsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
// 		sql  = sql.replace("#{news_id}",String.valueOf(t.getNewsId()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	

 	/**
 	 * 获取零售商可见平台话术
 	 */
	public void getRetailerVisibleWords(HttpServletRequest request, DataGrid dataGrid){
		try{
			//自定义追加查询条件
			String content = request.getParameter("content");
			String topTypeId = request.getParameter("topTypeId");
			String subTypeId = request.getParameter("subTypeId");
			String thridTypeId = request.getParameter("thridTypeId");
			String templateTrade = request.getParameter("templateTrade"); //行业id
			String type = request.getParameter("type");
			String visible = request.getParameter("visible");
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sql = new StringBuffer("SELECT w.id,w.update_date as updateDate,w.top_type_id topTypeId,ifnull(w.sub_type_id,' ') subTypeId,ifnull(w.thrid_type_id,' ') thridTypeId,")
				.append( "ifnull(w.content,' ') content,v.id wordsId,w.type ,CASE WHEN v.id is NULL THEN '0' ELSE '1' END visible,ifnull(tradeName,' ') tradeName ")
				.append(" from t_cust_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'").append(" and v.retailer_id ='").append(retailerId).append("' ")
				.append("right join ")
				.append("(select template_id,GROUP_CONCAT(trade_name) tradeName,GROUP_CONCAT(trade_id) trade_id ")
				.append("from t_trade_template where status='A' ");
				if(Utility.isNotEmpty(retailerId)){
					sql.append("and trade_id in (select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"' ) ");
				}
				sql.append("group by template_id) ttt ")
				.append("on w.id = ttt.template_id ")
				.append(" WHERE w.`status` = 'A' and w.platform_type = '1'");
			StringBuffer countSql = new StringBuffer("select count(1) from t_cust_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'")
				.append(" and v.retailer_id ='").append(retailerId).append("'")
				.append("right join ")
				.append("(select template_id,GROUP_CONCAT(trade_name) tradeName,GROUP_CONCAT(trade_id) trade_id ")
				.append("from t_trade_template where status='A' ");
				if(Utility.isNotEmpty(retailerId)){
					countSql.append("and trade_id in (select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"' ) ");
				}
				countSql.append("group by template_id) ttt ")
				.append("on w.id = ttt.template_id ")
				.append(" WHERE w.`status` = 'A' and w.platform_type = '1'");
			
			//行业ID是否存在
			if(Utility.isNotEmpty(templateTrade)){
				sql.append("and ttt.trade_id in ('"+templateTrade+"') ");
				countSql.append("and ttt.trade_id in ('"+templateTrade+"') ");
			}/*else{
				if(Utility.isNotEmpty(retailerId)){
					sql.append("and ttt.trade_id in (select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"' ) ");
					countSql.append("and ttt.trade_id in (select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"' ) ");
				}
			}*/
			
			if(!StringUtil.isEmpty(content)){
				sql.append(" and w.content like '%").append(content).append("%'");
				countSql.append(" and w.content like '%").append(content).append("%'");
			}
			
			if(!StringUtil.isEmpty(topTypeId)){
				sql.append(" and w.top_type_id = '").append(topTypeId).append("'");
				countSql.append(" and w.top_type_id = '").append(topTypeId).append("'");
			}
			if(!StringUtil.isEmpty(subTypeId)){
				sql.append(" and w.sub_type_id = '").append(subTypeId).append("'");
				countSql.append(" and w.sub_type_id = '").append(subTypeId).append("'");
			}
			if(!StringUtil.isEmpty(thridTypeId)){
				sql.append(" and w.thrid_type_id = '").append(thridTypeId).append("'");
				countSql.append(" and w.thrid_type_id = '").append(thridTypeId).append("'");
			}
			if(!StringUtil.isEmpty(type)){
				sql.append(" and w.type = '").append(type).append("'");
				countSql.append(" and w.type = '").append(type).append("'");
			}
			if(!StringUtil.isEmpty(visible)){
				if("0".equals(visible)){
					sql.append(" and v.id is null");
					countSql.append(" and v.id is null");
				}else if("1".equals(visible)){
					sql.append(" and v.id is not null");
					countSql.append(" and v.id is not null");
				}
			}
			List<Map<String, Object>> resultList =  this.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.getCountForJdbc(countSql.toString()).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
}