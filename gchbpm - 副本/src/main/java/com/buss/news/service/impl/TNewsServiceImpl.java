package com.buss.news.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.news.entity.TNewsEntity;
import com.buss.news.service.TNewsServiceI;
import com.buss.template.entity.TTemplateTypeEntity;
import com.buss.template.entity.TTradeTemplateEntity;


@Service("tNewsService")
@Transactional
public class TNewsServiceImpl extends CommonServiceImpl implements TNewsServiceI {
	@Autowired
	private SystemService systemService;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TNewsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TNewsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TNewsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TNewsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TNewsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TNewsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TNewsEntity t){
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
 		sql  = sql.replace("#{news_type}",String.valueOf(t.getNewsType()));
 		sql  = sql.replace("#{cover_pic}",String.valueOf(t.getCoverPic()));
 		sql  = sql.replace("#{news_context}",String.valueOf(t.getNewsContext()));
 		sql  = sql.replace("#{shopkeeper}",String.valueOf(t.getShopkeeper()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void doCopy(TNewsEntity t) throws Exception {
 		String retailerId = ResourceUtil.getRetailerId();
 		String hql = "from TNewsEntity where originalId = ? and shopkeeper = ? ";
 		//查询是否已经复制过该分类
 		List<TNewsEntity> existEntityList = this.commonDao.findHql(hql, t.getId(),retailerId);
 		
 		String tradeId = "";
 		String templateId = "";
 		//获取原模板的行业信息与零售商的行业信息匹配数据
 		String sql =" select template_id, GROUP_CONCAT(ttu.trade_id) tradeId from t_s_trade_user ttu left join t_trade_template ttt on ttt.trade_id=ttu.trade_id "+
 		"where ttt.status='A' and ttu.status='A' and template_id='"+t.getId()+"' and user_id='"+retailerId+"'  GROUP BY template_id";
 		List<Map<String,Object>> list = this.findForJdbc(sql, null);
 		if(list.size() > 0){
 	 		Map<String,Object> map = list.get(0);
 	 		tradeId = map.get("tradeId").toString();
 		}
 		
 		if(existEntityList.size()==0){//新增
 			TNewsEntity tNews = new TNewsEntity();
 			MyBeanUtils.copyBean2Bean(tNews, t);
 			tNews.setId(null);
 			tNews.setCreateDate(Utility.getCurrentTimestamp());
 			tNews.setGoodNum(0);
 			tNews.setClickNum(0);
 			tNews.setNoSenseNum(0);
 			tNews.setShopkeeper(retailerId);
 			tNews.setOriginalId(t.getId());
 			templateId = this.commonDao.save(tNews).toString();
 			
 		}else{//更新
 			TNewsEntity existEntity = existEntityList.get(0);
 			templateId = existEntity.getId();
 			existEntity.setAuthor(t.getAuthor());
 			existEntity.setCoverPic(t.getCoverPic());
 			existEntity.setNewsContext(t.getNewsContext());
 			existEntity.setNewsType(t.getNewsType());
 			existEntity.setTags(t.getTags());
 			existEntity.setTitle(t.getTitle());
 			existEntity.setTitlePic(t.getTitlePic());
 			existEntity.setUpLoaded(t.getUpLoaded());
 			existEntity.setUserId(t.getUserId());
 			existEntity.setUpdateDate(Utility.getCurrentTimestamp());
 			this.commonDao.updateEntitie(existEntity);
 		}
 		
		//保存话题的行业类型
		if (StringUtil.isNotEmpty(tradeId)) {
			this.saveTradeTemplate(templateId, tradeId);
		}
 	}

 	
	//获取话题列表
	public void getNewsDatagrid(HttpServletRequest request, DataGrid dataGrid){
		//1:零售商话题，0：平台话题，A：所有零售商话题
		String isRet = request.getParameter("isRet");//是否是零售商的话题(平台话题管理页面tab页)
		//查询条件组装器
		String newsType = request.getParameter("newsType");  //选择的话题分类
		String templateTrade = request.getParameter("templateTrade");  //选择的行业ID
		String title = request.getParameter("title");  //话题标题
		String author = request.getParameter("author");  //话题作者
		String tags = request.getParameter("tags");  //话题标签
		String upLoaded = request.getParameter("upLoaded");  //是否发布
		String shopkeeper = request.getParameter("shopkeeper");  //零售商id
		String retailerId = ResourceUtil.getRetailerId();

		StringBuffer sql = getTNewsSql("news");
		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("select count(DISTINCT tn.id) from t_news tn ");
		
		/*if(Utility.isNotEmpty(retailerId)){//零售商查平台
			if("1".equals(isRet)){
				sql.append("left join ");
				sqlCount.append("left join ");
			}else{
				sql.append("right join ");
				sqlCount.append("right join ");
			}
		}else{
			sql.append("left join ");
			sqlCount.append("left join ");
		}*/
		
		sql.append(" LEFT JOIN t_trade_template ttt ON tn.id = ttt.template_id and ttt.STATUS = 'A' ")
		.append("left join t_s_trade_user tst on ttt.trade_id = tst.trade_id and tst.STATUS = 'A' where tn.STATUS = 'A' ");
		sqlCount.append(" LEFT JOIN t_trade_template ttt ON tn.id = ttt.template_id and ttt.STATUS = 'A' ")
		.append("left join t_s_trade_user tst on ttt.trade_id = tst.trade_id and tst.STATUS = 'A' where tn.STATUS = 'A' ");
		
		/*if("1".equals(isRet)){//零售商
			sql.append("left join ");
			sqlCount.append("left join ");
		}else if("0".equals(isRet)){//平台
			if(Utility.isNotEmpty(retailerId)){//零售商查平台
				if(TNewsEntity.NEWS_TYPE_COURSE.equals(newsType) || TNewsEntity.NEWS_TYPE_STORY.equals(newsType)){
					//管家故事，管家课堂
					sql.append("left join ");
					sqlCount.append("left join ");
				} else{
					sql.append("right join ");
					sqlCount.append("right join ");
				}
			}else{//平台查看所有话题
				sql.append("left join ");
				sqlCount.append("left join ");
			}
		}else if("A".equals(isRet)){//全部零售商
			sql.append("left join ");
			sqlCount.append("left join ");
		}else{//话题商品列表
			if(Utility.isNotEmpty(retailerId)){//零售商话题
				sql.append("left join ");
				sqlCount.append("left join ");
			}else{//平台查看所有话题
				sql.append("left join ");
				sqlCount.append("left join ");
			}
		}*/
		
		/*sql.append("(select template_id,GROUP_CONCAT(trade_name) tradeName,GROUP_CONCAT(trade_id) trade_id ")
		.append("from t_trade_template where status='A' ");

		sqlCount.append("(select template_id,trade_id ")
		.append("from t_trade_template where status='A' ");
		if(Utility.isNotEmpty(retailerId)){
			sql.append("and trade_id in (select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"') ");
			sqlCount.append("and trade_id in (select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"') ");
		}
		sql.append("group by template_id) ttt on tn.id = ttt.template_id where tn.status='A' ");
		sqlCount.append("group by template_id) ttt on tn.id = ttt.template_id where tn.status='A' ");*/
		
		//行业ID是否存在
		if(Utility.isNotEmpty(templateTrade)){
			sql.append("and ttt.trade_id  like '%"+templateTrade+"%' ");
			sqlCount.append("and ttt.trade_id  like '%"+templateTrade+"%' ");
		}
		//标题是否存在
		if(Utility.isNotEmpty(title)){
			sql.append("and tn.title  like '%"+title+"%' ");
			sqlCount.append("and tn.title  like '%"+title+"%' ");
		}
		//作者是否存在
		if(Utility.isNotEmpty(author)){
			sql.append("and tn.author  like '%"+author+"%' ");
			sqlCount.append("and tn.author  like '%"+author+"%' ");
		}
		//标签是否存在
		if(Utility.isNotEmpty(tags)){
			sql.append("and tn.tags  like '%"+tags+"%' ");
			sqlCount.append("and tn.tags  like '%"+tags+"%' ");
		}
		//话题是否已发布
		if(Utility.isNotEmpty(upLoaded)){
			sql.append("and tn.upLoaded = '"+upLoaded+"' ");
			sqlCount.append("and tn.upLoaded = '"+upLoaded+"' ");
		}
		//平台选择零售商
		if(Utility.isNotEmpty(shopkeeper)){
			sql.append("and tn.shopkeeper = '"+shopkeeper+"' ");
			sqlCount.append("and tn.shopkeeper = '"+shopkeeper+"' ");
		}
		
		if(Utility.isNotEmpty(newsType)){//防止查like '%2%' 的时候把like '%12%' 的结果查出来
			sql.append("and  CONCAT(',',news_type,',') like '%,"+newsType+",%' ");
			sqlCount.append("and  CONCAT(',',news_type,',') like '%,"+newsType+",%' ");
		}else{//过滤管家课堂和管家故事
			sql.append(" and news_type not in('").append(TNewsEntity.NEWS_TYPE_COURSE)//管家课堂
			.append("','").append(TNewsEntity.NEWS_TYPE_STORY).append("') ");//管家故事
			sqlCount.append(" and news_type not in('").append(TNewsEntity.NEWS_TYPE_COURSE)//管家课堂
			.append("','").append(TNewsEntity.NEWS_TYPE_STORY).append("') ");//管家故事
		}
		if("1".equals(isRet)){//零售商
			sql.append("and shopkeeper='"+retailerId+"' ");
			sqlCount.append("and shopkeeper='"+retailerId+"' ");
		}else if("0".equals(isRet)){//平台
			if(Utility.isNotEmpty(retailerId)){
				sql.append("and tst.user_id='"+retailerId+"' ");
				sqlCount.append("and tst.user_id='"+retailerId+"' ");
			}
			sql.append("and shopkeeper='admin' ");
			sqlCount.append("and shopkeeper='admin' ");
		}else if("A".equals(isRet)){//全部零售商
			if(Utility.isNotEmpty(shopkeeper)){
				sql.append("and shopkeeper='"+shopkeeper+"' ");
				sqlCount.append("and shopkeeper='"+shopkeeper+"' ");
			}else{
				sql.append("and shopkeeper <> 'admin' ");
				sqlCount.append("and shopkeeper <> 'admin' ");
			}
		}else{//话题商品列表
			if(Utility.isNotEmpty(retailerId)){//零售商话题
				sql.append("and shopkeeper='"+retailerId+"' ");
				sqlCount.append("and shopkeeper='"+retailerId+"' ");
			}else{//平台查看所有话题
			}
		}
		sql.append(" GROUP BY tn.id ");
		//排序
		String sort = dataGrid.getSort();
		if(StringUtil.isEmpty(sort)){
			sql.append("order by create_date desc ");
		}else{
			sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder());
		}
		int total = this.getCountForJdbc(sqlCount.toString()).intValue();
		List<TNewsEntity> list = new ArrayList<TNewsEntity>();
		if(total > 0){
			list = this.findObjForJdbc(sql.toString(), dataGrid.getPage(),dataGrid.getRows(), TNewsEntity.class);
		}
		dataGrid.setResults(list);
		dataGrid.setTotal(total);
	}
	
	//获取管家课堂，管家故事列表
	public void getGuideNewsDatagrid(HttpServletRequest request, DataGrid dataGrid){
		//0：平台，1:零售商话题，A：所有零售商话题
		String isRet = request.getParameter("isRet");//是否是零售商的话题(平台话题管理页面tab页)
		//查询条件组装器
		String newsType = request.getParameter("newsType");  //选择的话题分类
		String title = request.getParameter("title");  //话题标题
		String author = request.getParameter("author");  //话题作者
		String tags = request.getParameter("tags");  //话题标签
		String upLoaded = request.getParameter("upLoaded");  //是否发布
		String retailerId = ResourceUtil.getRetailerId();

		StringBuffer sql = getTNewsSql("guideNews");
		sql.append("where tn.status='A' ");
		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("select count(1) from t_news tn where status='A' ");
		
		//话题分类
		if(Utility.isNotEmpty(newsType)){
			sql.append("and  news_type = '"+newsType+"' ");
			sqlCount.append("and  news_type = '"+newsType+"' ");
		}
		//是否发布
		if(Utility.isNotEmpty(upLoaded)){
			sql.append("and  upLoaded = '"+upLoaded+"' ");
			sqlCount.append("and  upLoaded = '"+upLoaded+"' ");
		}
		//标题是否存在
		if(Utility.isNotEmpty(title)){
			sql.append("and title  like '%"+title+"%' ");
			sqlCount.append("and title  like '%"+title+"%' ");
		}
		//作者是否存在
		if(Utility.isNotEmpty(author)){
			sql.append("and author  like '%"+author+"%' ");
			sqlCount.append("and author  like '%"+author+"%' ");
		}
		//标签是否存在
		if(Utility.isNotEmpty(tags)){
			sql.append("and tags  like '%"+tags+"%' ");
			sqlCount.append("and tags  like '%"+tags+"%' ");
		}
		
		if("1".equals(isRet)){//零售商
			sql.append("and shopkeeper='"+retailerId+"' ");
			sqlCount.append("and shopkeeper='"+retailerId+"' ");
		}else if("0".equals(isRet)){//平台
			sql.append("and shopkeeper='admin' ");
			sqlCount.append("and shopkeeper='admin' ");
		}else if("A".equals(isRet)){//全部零售商
			sql.append("and shopkeeper <> 'admin' ");
			sqlCount.append("and shopkeeper <> 'admin' ");
		}
		//排序
		String sort = dataGrid.getSort();
		if(StringUtil.isEmpty(sort)){
			sql.append("order by create_date desc ");
		}else{
			sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder());
		}

		int total = this.getCountForJdbc(sqlCount.toString()).intValue();
		List<TNewsEntity> list = new ArrayList<TNewsEntity>();
		if(total > 0){
			list = this.findObjForJdbc(sql.toString(), dataGrid.getPage(),dataGrid.getRows(), TNewsEntity.class);
		}
		dataGrid.setResults(list);
		dataGrid.setTotal(total);
	}
	private StringBuffer getTNewsSql(String newsType){
		StringBuffer sb = new StringBuffer();
		sb.append("select tn.id id, ")
		.append("tn.create_name createName, ")
		.append("tn.create_by createBy, ")
		.append("tn.create_date createDate, ")
		.append("tn.update_name updateName, ")
		.append("tn.update_by updateBy, ")
		.append("tn.update_date updateDate, ")
		.append("tn.status status, ")
		.append("tn.title title, ")
		.append("tn.author author, ")
		.append("tn.news_type newsType, ")
		.append("tn.title_pic titlePic, ")
		.append("CONCAT(tn.cover_pic,'?imageView2/1/w/130/h/80') coverPic, ")
		.append("tn.tags tags, ")
		.append("tn.news_context newsContext, ")
		.append("tn.shopkeeper shopkeeper, ")
		.append("tn.upLoaded upLoaded, ")
		.append("tn.Good_num goodNum, ")
		.append("tn.no_sense_num noSenseNum, ")
		.append("tn.click_Num clickNum, ")
		.append("tn.user_Id userId, ")
		.append("tn.pic_Map_Content picMapContent, ")
		.append("tn.original_id originalId ");
		if("news".equals(newsType)){
			sb.append(",GROUP_CONCAT(DISTINCT ttt.trade_name) tradeName ");
		}
		sb.append("from t_news tn ");
		return sb;
	}
	
	
	
	//保存或修改话题的关联行业
	public void saveTradeTemplate(String tNewsID, String tradeIDs) {
		String[] tradeids = tradeIDs.split(",");
		List<TTradeTemplateEntity> tradeTemplates = this.findByProperty(TTradeTemplateEntity.class, "templateId", tNewsID);
		
		//判断是否要删除
		Boolean flag = true;
		List<String> hasTrade = new ArrayList<String>();
		for (TTradeTemplateEntity tradeTemplate : tradeTemplates) {
			String tradeId = tradeTemplate.getTradeId();
			for (int i = 0; i < tradeids.length; i++) {
				if(tradeId.equals(tradeids[i])){
					flag = false;
				}
			}
			if(flag){
				//将TSTradeUser 标记为删除
				tradeTemplate.setStatus("I");
				this.updateEntitie(tradeTemplate);
			}else{
				hasTrade.add(tradeId);
			}
			flag = true;
		}
		
		for (int i = 0; i < tradeids.length; i++) {
			for (String hasTradeId : hasTrade) {
				if(hasTradeId.equals(tradeids[i])){
					flag = false;
				}
			}
			if(flag){
				//创建新用户行业连接数据
				TTradeTemplateEntity tradeTemplate = new TTradeTemplateEntity();
				TSCategoryEntity tsCategory = this.flushEntity(TSCategoryEntity.class, tradeids[i]);
				tradeTemplate.setTemplateId(tNewsID);
				tradeTemplate.setTradeId(tsCategory.getId());
				tradeTemplate.setTradeName(tsCategory.getName());
				tradeTemplate.setStatus("A");
				systemService.save(tradeTemplate);
			}
			flag = true;
		}
	}
	
	/**获取话题分类并set到request，值用逗号隔开newsTypeArr=热门话题_6003,品牌&商品话题_6004,活动话题_6005,
	 * 排除管家课堂和管家故事
	 * @param request
	 */
	@Override
	public void getNewsTypeArr(HttpServletRequest request) {
		String hql = "FROM TTemplateTypeEntity WHERE status='A' and level='1' and platformType='1' and templateType='2' and id not in('"
						+TNewsEntity.NEWS_TYPE_COURSE+"','"+TNewsEntity.NEWS_TYPE_STORY+"')";
		List<TTemplateTypeEntity> newsTypeList = systemService.findByQueryString(hql);
		String newsTypeArr = "";
		for(TTemplateTypeEntity type : newsTypeList){
			newsTypeArr+=","+type.getName()+"_"+type.getId();
		}
		request.setAttribute("newsTypeArr", newsTypeArr.substring(1));
	}
}