package com.buss.words.service.impl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.template.entity.TTemplateTypeEntity;
import com.buss.template.entity.TTradeTemplateEntity;
import com.buss.words.entity.TCustWordsEntity;
import com.buss.words.entity.TCustWordsTagsEntity;
import com.buss.words.service.TCustWordsServiceI;

import common.DBUtil;
import common.GlobalConstants;

@Service("tCustWordsService")
@Transactional
public class TCustWordsServiceImpl extends CommonServiceImpl implements TCustWordsServiceI {
	@Autowired
	private SystemService systemService;
	
 	@Override
 	public String saveCustWords (TCustWordsEntity tCustWords, HttpServletRequest request) {
 		String message = "话术模板添加成功";
 		// 话术的的行业信息
		String tradeId = request.getParameter("tradeId");
		String tags = request.getParameter("tags");
 		String retailerId = ResourceUtil.getRetailerId();
		String platformType = common.GlobalConstants.PLATFORM_TYPE_2;
		try{
			tCustWords.setIsShow(TCustWordsEntity.IS_SHOW_N);
			tCustWords.setTags(tags);
			tCustWords.setStatus("A");
			if(StringUtil.isEmpty(retailerId)){
				retailerId = "admin";
				platformType = common.GlobalConstants.PLATFORM_TYPE_1;
			}
			tCustWords.setPlatformType(platformType);
			tCustWords.setRetailerId(retailerId);
			this.commonDao.save(tCustWords);
			//保存用户的行业类型
			if (StringUtil.isNotEmpty(tradeId) || "101".equals(tCustWords.getTopTypeId())) {
				saveTradeTemplate(tCustWords, tradeId);
			}
			//平台才能添加话术标签
			if("admin".equals(retailerId) && StringUtil.isNotEmpty(tags)){
				String[] tag = tags.split(",");
				for (String tab : tag) {
					tab = tab.trim();
					if(Utility.isNotEmpty(tab)){
						TCustWordsTagsEntity tCustWordsTag = new TCustWordsTagsEntity();
						tCustWordsTag.setTag(tab);
						tCustWordsTag.setGoodsWordsId(tCustWords.getId());
						tCustWordsTag.setStatus("A");
						saveCustWordsTags(tCustWordsTag);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "话术模板添加失败";
			throw new BusinessException(e.getMessage());
		}
		
 			
		
		/*List<TCustWordsPicsEntity> picList = tCustWords.getPicList();
 		if(TCustWordsEntity.TYPE_2.equals(tCustWords.getType())&&Utility.isNotEmpty(picList)){
 			for(TCustWordsPicsEntity pic : picList){
 				pic.setCustWordsId(tCustWords.getId());
 				pic.setStatus("A");
 				pic.setRetailerId(retailerId);
 			}
 			this.commonDao.batchSave(picList);
 		}*/
		return message;
 	}
 	
 	@Override
	public String updateCustWords(TCustWordsEntity tCustWords, HttpServletRequest request) {
 		String wordsId =  tCustWords.getId();
 		// 话术的的行业信息
		String tradeId = request.getParameter("tradeId");
		String tags = request.getParameter("tags");
		String message = "话术模板更新成功";
		try {
			TCustWordsEntity t = this.get(TCustWordsEntity.class, wordsId);
			MyBeanUtils.copyBeanNotNull2Bean(tCustWords, t);
			//查询商品话术的标签
			String tagSql = "SELECT * from t_cust_words_tags  where  status = 'A' and goods_words_id='"+wordsId+"'";
			List<TCustWordsTagsEntity> tagList = this.findObjForJdbc(tagSql, TCustWordsTagsEntity.class);
			String[] tagsList = tags.split(",");
			for (String tag : tagsList) {
				boolean flage = true;  //是否添加新标签
				for (TCustWordsTagsEntity wordsTags : tagList) {
					boolean flage2 = true;  //是否删除标签
					String wordsTag=wordsTags.getTag();
					if(wordsTag.equals(tag)){
						flage2 = false;
						flage=false;
						continue;
					}else{
						for (String tag2 : tagsList) {
							if(wordsTag.equals(tag2)){
								flage2 = false;
								continue;
							}
						}
						if(flage2){              //删除原有标签
							this.updateBySqlString("update t_cust_words_tags set status='I' where id='"+wordsTags.getId()+"'");
						}
					}
				}
				if(flage){  //新加标签
 					TCustWordsTagsEntity tWordsTagEntity = new TCustWordsTagsEntity();
 					tWordsTagEntity.setTag(tag);
 					tWordsTagEntity.setGoodsWordsId(wordsId);
 					tWordsTagEntity.setStatus("A");
// 					this.save(tWordsTagEntity);
 					this.commonDao.save(tWordsTagEntity);
				}
			}

			//保存用户的行业类型
			if (StringUtil.isNotEmpty(tradeId) || "101".equals(tCustWords.getTopTypeId())) {
				saveTradeTemplate(tCustWords, tradeId);
			}
	 		this.commonDao.updateEntitie(t);
		} catch (Exception e) {
			e.printStackTrace();
			message = "话术模板更新失败";
			throw new BusinessException(e.getMessage());
		}
// 		List<TCustWordsPicsEntity> picList = tCustWords.getPicList();
 		/*if(TCustWordsEntity.TYPE_2.equals(tCustWords.getType())){
 			List<TCustWordsPicsEntity> oldPicList = commonDao.findByProperty(TCustWordsPicsEntity.class, "custWordsId", tCustWords.getId());
// 			if(Utility.isNotEmpty(picList)){
 				for(TCustWordsPicsEntity pic : picList){
 					if(Utility.isEmpty(pic.getId())){//新增
 						pic.setCustWordsId(tCustWords.getId());
 						pic.setStatus("A");
 						pic.setRetailerId(tCustWords.getRetailerId());
 						this.commonDao.save(pic);
 					}
 				}
 				if(oldPicList.size()>0){
 					for(TCustWordsPicsEntity oldPic : oldPicList){
 						boolean exist = false;//默认不存在
 						for(TCustWordsPicsEntity newPic : picList){
 							if(oldPic.getId().equals(newPic.getId())){
 								exist = true;
 								break;
 							}
 						}
 						if(!exist){//不存在则要删除
 							oldPic.setStatus("I");
 							this.commonDao.updateEntitie(oldPic);
 						}
 					}
 				}	
// 			}
 		}*/
 		return message;
	}
 	//保存话术模板的行业类型
    public void saveTradeTemplate(TCustWordsEntity tCustWords, String tradeIDs) {
    	String topType = tCustWords.getTopTypeId();
    	if("101".equals(topType)){
    		tradeIDs = tCustWords.getSubTypeId()+",";
    	}
		String[] tradeids = tradeIDs.split(",");
		List<TTradeTemplateEntity> tradeTemplates = this.findByProperty(TTradeTemplateEntity.class, "templateId", tCustWords.getId());
		
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
				tradeTemplate.setTemplateId(tCustWords.getId());
				tradeTemplate.setTradeId(tsCategory.getId());
				tradeTemplate.setTradeName(tsCategory.getName());
				tradeTemplate.setStatus("A");
				this.save(tradeTemplate);
			}
			flag = true;
		}
	}
 	@Override
 	public Long getTotalVisibleCountBySubTypeId(String subTypeId){
 		String rId = ResourceUtil.getRetailerId();
 		StringBuffer sql = new StringBuffer("SELECT COUNT(1) from t_cust_words where `status` = 'A' and (retailer_id = '")
	 		.append(rId).append("' or id in (SELECT words_id from t_visible_words where `status` = 'A' and retailer_id = '")
	 		.append(rId).append("' )) and sub_type_id = '").append(subTypeId).append("' and is_show = 'Y'");
 		Long n = this.commonDao.getCountForJdbc(sql.toString());
 		return n;
 	}

 	/**
 	 * 话术列表
 	 */
	@Override
	public void getPlatformList(HttpServletRequest request, DataGrid dataGrid,String platformType) {
		//platformType  1,平台话术， 2,查询零售商话术，3,零售商查平台话术 
		String topTypeId = request.getParameter("topTypeId");
		String subTypeId = request.getParameter("subTypeId");
		String thridTypeId = request.getParameter("thridTypeId");
		String templateTrade = request.getParameter("templateTrade"); //行业ID
		String type = request.getParameter("type");
		String content = request.getParameter("content");

		String retailerId = ResourceUtil.getRetailerId();
		
		StringBuffer sql = getCustWordsSql(true);
		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("select count(DISTINCT tcw.id)  from t_cust_words tcw ");
		//判断查询零售商行业
		/*List<Map<String,Object>> tradeIdList = null;
		if(Utility.isNotEmpty(retailerId)){
			tradeIdList = this.findForJdbc("select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"'", null);
		}
		String tradeIds = "";
		if(Utility.isNotEmpty(tradeIdList)){
			for (Map<String, Object> map : tradeIdList) {
				tradeIds +="'"+ map.get("trade_id") + "',";
			}
			tradeIds  = tradeIds.substring(0, tradeIds.length()-1);
		}*/
		
		//获取话术模板的行业集合
		/*String sqlList = "select template_id,GROUP_CONCAT(trade_name) tradeName,GROUP_CONCAT(trade_id) trade_id "+
		"from t_trade_template where status='A' ";
		if(Utility.isNotEmpty(tradeIds)){
			sqlList += "and trade_id in ("+tradeIds+") ";
		}
		sqlList += "group by template_id";*/
		
		/*if(common.GlobalConstants.PLATFORM_TYPE_3.equals(platformType)){  //零售商查平台话术 ，
			if(Utility.isNotEmpty(retailerId)){//零售商查询
				sql.append("right join ");
				sqlCount.append("right join ");
			}else{
				sql.append("left join ");
				sqlCount.append("left join ");
			}
		}else{//平台查询零售商
			sql.append("left join ");
			sqlCount.append("left join ");
		}*/
		sql.append("LEFT JOIN t_trade_template ttt ON tcw.id = ttt.template_id and ttt.STATUS = 'A' ")
		.append("left join t_s_trade_user tst on ttt.trade_id = tst.trade_id and tst.STATUS = 'A' ");
		sqlCount.append("LEFT JOIN t_trade_template ttt ON tcw.id = ttt.template_id and ttt.STATUS = 'A' ")
		.append("left join t_s_trade_user tst on ttt.trade_id = tst.trade_id and tst.STATUS = 'A' ");
		
		if(common.GlobalConstants.PLATFORM_TYPE_1.equals(platformType)){//平台话术
			sql.append("and tst.user_id = 'admin' ");
			sqlCount.append("and tst.user_id = 'admin' ");
		}else if(common.GlobalConstants.PLATFORM_TYPE_2.equals(platformType)){  //查询零售商话术
			if(Utility.isNotEmpty(retailerId)){//零售商查询
				sql.append("and tst.user_id = tcw.retailer_id ");
				sqlCount.append("and tst.user_id = tcw.retailer_id ");
			}else{//平台查询零售商
				sql.append("and tst.user_id <> 'admin' ");
				sqlCount.append("and tst.user_id <> 'admin' ");
			}
		}
		
		
		
		//拼接查询列表
		/*sql.append("(select template_id,GROUP_CONCAT(trade_name) tradeName,GROUP_CONCAT(trade_id) trade_id ")
		.append("from t_trade_template where status='A' ");
		if(Utility.isNotEmpty(tradeIds)){
			sql.append("and trade_id in ("+tradeIds+") ");
		}*/
		/*if(Utility.isNotEmpty(retailerId)){
			sql.append("and trade_id in (select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"' ) ");
		}*/
		/*sql.append("group by template_id) ttt ")
		.append("on tcw.id = ttt.template_id ");*/
		sql.append("LEFT JOIN t_s_category tsc on tsc.id = tcw.sub_type_id ")
		.append("LEFT JOIN t_template_type tttype on tttype.id = tcw.sub_type_id ")
		.append("LEFT JOIN t_cust_words_tags tcwt ON tcw.id = tcwt.goods_words_id ")
		.append("where tcw. STATUS = 'A' ");
		sqlCount.append("where tcw. STATUS = 'A' ");
		//拼接查询总条数
		/*sqlCount.append("(select template_id,GROUP_CONCAT(trade_name) tradeName,GROUP_CONCAT(trade_id) trade_id ")
		.append("from t_trade_template where status='A' ");
		if(Utility.isNotEmpty(tradeIds)){
			sqlCount.append("and trade_id in ("+tradeIds+") ");
		}
		sqlCount.append("group by template_id) ttt ")
		.append("on tcw.id = ttt.template_id ")
		.append("where tcw.status='A' ");*/

		//行业ID是否存在
		if(Utility.isNotEmpty(templateTrade)){
			sql.append("and ttt.trade_id like '%"+templateTrade+"%' ");
			sqlCount.append("and ttt.trade_id like '%"+templateTrade+"%' ");
		}
		if(common.GlobalConstants.PLATFORM_TYPE_1.equals(platformType)){//平台话术
			sql.append("and tcw.retailer_id = 'admin' ");
			sqlCount.append("and tcw.retailer_id = 'admin' ");
		}else if(common.GlobalConstants.PLATFORM_TYPE_2.equals(platformType)){  //查询零售商话术
			if(Utility.isNotEmpty(retailerId)){//零售商查询
				sql.append("and tcw.retailer_id = '"+retailerId+"' ");
				sqlCount.append("and tcw.retailer_id = '"+retailerId+"' ");
			}else{//平台查询零售商
				sql.append("and tcw.retailer_id <> 'admin' ");
				sqlCount.append("and tcw.retailer_id <> 'admin' ");
			}
		}else if(common.GlobalConstants.PLATFORM_TYPE_3.equals(platformType)){  //零售商查平台话术
			if(Utility.isNotEmpty(retailerId)){
				sql.append("and tst.user_id='"+retailerId+"' ");
				sqlCount.append("and tst.user_id='"+retailerId+"' ");
			}
			sql.append("and tcw.retailer_id = 'admin' ");
			sqlCount.append("and tcw.retailer_id = 'admin' ");
		}
		
		if(Utility.isNotEmpty(topTypeId)){
			sql.append("and top_type_id='"+topTypeId+"' ");
			sqlCount.append("and top_type_id='"+topTypeId+"' ");
		}
		if(Utility.isNotEmpty(subTypeId)){
			sql.append("and sub_type_id='"+subTypeId+"' ");
			sqlCount.append("and sub_type_id='"+subTypeId+"' ");
		}
		if(Utility.isNotEmpty(thridTypeId)){
			sql.append("and thrid_type_id='"+thridTypeId+"' ");
			sqlCount.append("and thrid_type_id='"+thridTypeId+"' ");
		}
		if(Utility.isNotEmpty(type)){
			sql.append("and type='"+type+"' ");
			sqlCount.append("and type='"+type+"' ");
		}
		if(Utility.isNotEmpty(content)){
			sql.append("and content like '%"+content+"%' ");
			sqlCount.append("and content like '%"+content+"%' ");
		}
		sql.append("group by tcw.id ");
		String sortName = dataGrid.getSort();
		if(StringUtil.isEmpty(sortName)){
			sql.append("order by updateDate desc");
		}else{
			sql.append("ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
//		System.out.println("话术查询sql："+sql.toString());
		List<TCustWordsEntity> list = null;
		int total = this.getCountForJdbc(sqlCount.toString()).intValue();
		if(total > 0){
			list = this.findObjForJdbc(sql.toString(), dataGrid.getPage(),dataGrid.getRows(), TCustWordsEntity.class);
			for (TCustWordsEntity tCustWordsEntity : list) {
				String topId = tCustWordsEntity.getTopTypeId();
				if(TTemplateTypeEntity.WORDS_TYPE_1.equals(topId)){
					tCustWordsEntity.setSubTypeId(tCustWordsEntity.getCategoryName());
				}else{
					tCustWordsEntity.setSubTypeId(tCustWordsEntity.getTypeName());
				}
			}
		}else{
			list = new ArrayList<TCustWordsEntity>();
		}
		dataGrid.setResults(list);
		dataGrid.setTotal(total);
	};
	
	
	
	private StringBuffer getCustWordsSql(boolean flag){
		StringBuffer sb = new StringBuffer();
		sb.append("select tcw.id id, ")
		.append("tcw.create_name createName, ")
		.append("tcw.create_by createBy, ")
		.append("tcw.create_date createDate, ")
		.append("tcw.update_name updateName, ")
		.append("tcw.update_by updateBy, ")
		.append("tcw.update_date updateDate, ")
		.append("tcw.status status, ")
		.append("tcw.top_type_id topTypeId, ")
		.append("tcw.sub_type_id subTypeId, ")
		.append("tsc.name categoryName, ")
		.append("GROUP_CONCAT(DISTINCT ttt.trade_name) typeName, ")
		.append("tcw.thrid_type_id thridTypeId, ")
		.append("tcw.content content, ")
		.append("tcw.platform_type platformType, ")
		.append("tcw.retailer_id retailerId, ")
		.append("tcw.type type,tcw.is_show isShow, ")
		.append("GROUP_CONCAT(DISTINCT ttt.trade_name) tradeName ");
		if(flag){  //查询话术列表时显示的字段
			sb.append(",GROUP_CONCAT(tag) tags, ")
			.append("(CASE ")
			.append("WHEN tcw.top_type_id = '101' THEN tsc.name ")
			.append("WHEN tcw.top_type_id = '102' THEN tttype.name ")
			.append("END) subName ");
		}
		sb.append("from t_cust_words tcw ");
		return sb;
	}
	
	//更新话术获取话术的详细信息。
	public void goUpdateResult(TCustWordsEntity tCustWords,HttpServletRequest request){
		String wordsId = tCustWords.getId();
		if (StringUtil.isNotEmpty(wordsId)) {
			tCustWords = this.flushEntity(TCustWordsEntity.class, wordsId);
			String topTypeId = tCustWords.getTopTypeId();
			String subTypeId = tCustWords.getSubTypeId();
			/*String thridTypeId = tCustWords.getthridTypeId();
			
			TTemplateTypeEntity topType = systemService.flushEntity(TTemplateTypeEntity.class, tCustWords.getTopTypeId());
			TTemplateTypeEntity subType = systemService.flushEntity(TTemplateTypeEntity.class, tCustWords.getSubTypeId());
			TTemplateTypeEntity thridType = systemService.flushEntity(TTemplateTypeEntity.class, tCustWords.getthridTypeId());*/
			
			//一级分类
			String hql = "FROM TTemplateTypeEntity WHERE status='A' and level='1' and platformType='1' and templateType='1' ";
			List<TTemplateTypeEntity> topTypeList = this.findByQueryString(hql);
			//二级分类
			List<TTemplateTypeEntity> subTypeList = new ArrayList<TTemplateTypeEntity>();
			//三级分类
			String hql3 = "FROM TTemplateTypeEntity WHERE status='A' and level='3' and platformType='1' and templateType='1' and parent.id='"+subTypeId+"'";
			List<TTemplateTypeEntity> thridTypeList = this.findByQueryString(hql3);
			if(TTemplateTypeEntity.WORDS_TYPE_1.equals(topTypeId)){ //商品话术
				String sqlTrade = "select id,level,name from t_s_category where status='A' and level='2' and retailer_id='admin' ";
				List<Map<String,Object>> tradeMap = this.findForJdbc(sqlTrade, null); //行业列表
				for (Map<String,Object> map : tradeMap) {
					TTemplateTypeEntity tTemplateTypeEntity = new TTemplateTypeEntity();
					tTemplateTypeEntity.setId(map.get("id").toString());
					tTemplateTypeEntity.setName(map.get("name").toString());
					tTemplateTypeEntity.setLevel(map.get("level").toString());
					subTypeList.add(tTemplateTypeEntity);
				}
				//查询商品话术的标签
				String tagSql = "SELECT * from t_cust_words_tags  where  status = 'A' and goods_words_id='"+wordsId+"'";
				List<TCustWordsTagsEntity> tagList = this.findObjForJdbc(tagSql, TCustWordsTagsEntity.class);
				String tags = "";
				for (TCustWordsTagsEntity tCustWordsTagsEntity : tagList) {
					tags += tCustWordsTagsEntity.getTag()+",";
				}
				request.setAttribute("tagList", tagList);
				request.setAttribute("tags", tags);
				request.setAttribute("isGoodsWords", true);
			}else{//其他话术
				//二级分类
				String hql2 = "FROM TTemplateTypeEntity WHERE status='A' and level='2' and platformType='1' and templateType='1' and parent.id='"+topTypeId+"'";
				subTypeList = this.findByQueryString(hql2);
				request.setAttribute("isGoodsWords", false);
			}
			
			request.setAttribute("topTypeList", topTypeList);
			request.setAttribute("subTypeList", subTypeList);
			request.setAttribute("thridTypeList", thridTypeList);
			request.setAttribute("tCustWordsPage", tCustWords);
			//查询行业信息
			String sql = "select trade_id tradeId,trade_name tradeName from t_trade_template where status='A' and template_id='"+wordsId+"'";
			List<Map<String, Object>> resultList =  this.findForJdbc(sql);
			String tradeId="";
			String tradeName="";
			if(resultList.size() > 0){
				for (Map<String, Object> map : resultList) {
					tradeId +=map.get("tradeId")+",";
					tradeName +=map.get("tradeName")+",";
				}
				tradeId = tradeId.substring(0, tradeId.length()-1);
				tradeName = tradeName.substring(0, tradeName.length()-1);
			}
			request.setAttribute("tradeId", tradeId);
			request.setAttribute("tradeName", tradeName);
			
		}
		request.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		request.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		request.setAttribute("load", request.getParameter("load"));
	}
	
	
	//商品详情页面添加话术的选择已有话术
	@Override
	public void getGoodsWordsList(HttpServletRequest request, DataGrid dataGrid) {
		String subTypeId = request.getParameter("subTypeId");
		String thridTypeId = request.getParameter("thridTypeId");
		String templateTrade = request.getParameter("templateTrade"); //行业ID
		String type = request.getParameter("type");//话术类型
		String content = request.getParameter("content");//内容
		//零售商id
		String retailerId = ResourceUtil.getRetailerId();
			
		StringBuffer sql = getCustWordsSql(false);
		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("select count(1) ")
		.append("from t_cust_words tcw ")
		.append("LEFT JOIN t_trade_template ttt ON tcw.id = ttt.template_id AND ttt. STATUS = 'A' ")
		.append("LEFT JOIN t_s_category tsc on tsc.id = tcw.sub_type_id ")
		.append("where tcw. STATUS = 'A' ");
		sql.append("LEFT JOIN t_trade_template ttt ON tcw.id = ttt.template_id AND ttt. STATUS = 'A' ")
		.append("LEFT JOIN t_s_category tsc on tsc.id = tcw.sub_type_id ")
		.append("where tcw. STATUS = 'A' ");
		//行业ID是否存在
		if(Utility.isNotEmpty(templateTrade)){
			sql.append("and ttt.trade_id like '%"+templateTrade+"%' ");
			sqlCount.append("and ttt.trade_id like '%"+templateTrade+"%' ");
		}
		if(Utility.isNotEmpty(retailerId)){//零售商查询
			sql.append("and tcw.retailer_id = '"+retailerId+"' ");
			sqlCount.append("and tcw.retailer_id = '"+retailerId+"' ");
		}
		sql.append("and top_type_id='101' ");
		sqlCount.append("and top_type_id='101' ");
		
		if(Utility.isNotEmpty(subTypeId)){
			sql.append("and sub_type_id='"+subTypeId+"' ");
			sqlCount.append("and sub_type_id='"+subTypeId+"' ");
		}
		if(Utility.isNotEmpty(thridTypeId)){
			sql.append("and thrid_type_id='"+thridTypeId+"' ");
			sqlCount.append("and thrid_type_id='"+thridTypeId+"' ");
		}
		if(Utility.isNotEmpty(type)){
			sql.append("and type='"+type+"' ");
			sqlCount.append("and type='"+type+"' ");
		}
		if(Utility.isNotEmpty(content)){
			sql.append("and tcw.content like '%"+content+"%' ");
			sqlCount.append("and tcw.content like '%"+content+"%' ");
		}
		sql.append(" group by tcw.id order by top_type_id,sub_type_id,thrid_type_id ");
		
		List<TCustWordsEntity> list = this.findObjForJdbc(sql.toString(), dataGrid.getPage(),dataGrid.getRows(), TCustWordsEntity.class);
		int total = 0;
		if(!Utility.isEmpty(list)){
			total = this.getCountForJdbc(sqlCount.toString()).intValue();
		}else{
			list = new ArrayList<TCustWordsEntity>();
		}
		dataGrid.setResults(list);
		dataGrid.setTotal(total);
	};
	
	@Override
	public void doCopy(TCustWordsEntity custWords,String retailerId) throws Exception {
		//查询是否复制过
 		String hql = "from TCustWordsEntity where originalId = '"+custWords.getId()+"' and retailerId = '"+retailerId+"' ";
 		List<TCustWordsEntity> oldCustWordsList = this.findByQueryString(hql);
 		
// 		String tradeId = "";
 		Set<String> tradeIdSet = new HashSet<String>();
 		String templateId = "";
 		//获取原模板的行业信息与零售商的行业信息匹配数据
 		String sql =" select  ttu.trade_id tradeId from t_s_trade_user ttu left join t_trade_template ttt on ttt.trade_id=ttu.trade_id "+
 		"where ttt.status='A' and ttu.status='A' and template_id='"+custWords.getId()+"' and user_id='"+retailerId+"'  ";
 		List<Map<String,Object>> list = this.findForJdbc(sql, null);
 		if(list.size() > 0){
 			for(Map<String,Object> map : list){
 				tradeIdSet.add(map.get("tradeId")+"");
 			}
 		}
 		

 		if(oldCustWordsList.size()==0){//新增
 			TSUser user = ResourceUtil.getSessionUserName();
 			TCustWordsEntity newCustWords = new TCustWordsEntity();
 			MyBeanUtils.copyBeanNotNull2Bean(custWords, newCustWords);
 			newCustWords.setId(null);
 			newCustWords.setCreateName(user.getRealName());
 			newCustWords.setCreateBy(user.getUserName());
 			newCustWords.setCreateDate(Utility.getCurrentTimestamp());
 			newCustWords.setUpdateDate(Utility.getCurrentTimestamp());
 			newCustWords.setRetailerId(retailerId);
 			newCustWords.setPlatformType(GlobalConstants.PLATFORM_TYPE_2);
 			newCustWords.setOriginalId(custWords.getId());

 			templateId = this.commonDao.save(newCustWords).toString();//保存话术模版
 		}else {//更新
 			TCustWordsEntity newCustWords = oldCustWordsList.get(0);
 			templateId = newCustWords.getId();
 		}
 		//保存话题的行业类型
		if (StringUtil.isNotEmpty(tradeIdSet) || "101".equals(custWords.getTopTypeId())) {
			this.saveTradeTemplate(templateId, tradeIdSet);
		}
	}
	
	//保存或修改话题的关联行业
		public void saveTradeTemplate(String tNewsID, Set<String> tradeIdSet) {
			List<TTradeTemplateEntity> tradeTemplates = this.findByProperty(TTradeTemplateEntity.class, "templateId", tNewsID);
			
			//判断是否要删除
			Boolean flag = true;
			List<String> hasTrade = new ArrayList<String>();
			for (TTradeTemplateEntity tradeTemplate : tradeTemplates) {
				String tradeId = tradeTemplate.getTradeId();
				if(tradeIdSet.contains(tradeId)){
					flag = false;
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
			
			for (String tradeId : tradeIdSet) {
				for (String hasTradeId : hasTrade) {
					if(hasTradeId.equals(tradeId)){
						flag = false;
						break;
					}
				}
				if(flag){
					//创建新用户行业连接数据
					TSCategoryEntity tsCategory = this.flushEntity(TSCategoryEntity.class, tradeId);
					if(Utility.isNotEmpty(tsCategory)){
						TTradeTemplateEntity tradeTemplate = new TTradeTemplateEntity();
						tradeTemplate.setTemplateId(tNewsID);
						tradeTemplate.setTradeId(tsCategory.getId());
						tradeTemplate.setTradeName(tsCategory.getName());
						tradeTemplate.setStatus("A");
						this.save(tradeTemplate);
					}else{
						LogUtil.error("TSCategoryEntity找不到数据,id:"+tradeId);
					}
				}
				flag = true;
			}
		}
	
	@Override
	public void batchInsertTradeIds(String subTypeId, String tradeIds) throws SQLException {
		List<TCustWordsEntity> custWordsList = this.commonDao.findHql("from TCustWordsEntity where subTypeId = ? and platformType='1'", subTypeId);
		if(Utility.isNotEmpty(custWordsList)){
			List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
			Map<String,String> tradeMap = this.getTradeMapByIds(tradeIds);//获取行业名称
			for(TCustWordsEntity custWord : custWordsList){
				for(String tradeId : tradeIds.split(",")){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", Utility.getUUID());
					map.put("template_id",custWord.getId());
					map.put("trade_id",tradeId);
					map.put("trade_name", tradeMap.get(tradeId));
					map.put("status", GlobalConstants.STATUS_ACTIVE);
					datas.add(map);
				}
			}
			if(datas.size()>0){
				DBUtil.insertAll("t_trade_template", datas);
			}
		}
	}

	/**获取行业id,name组成的map
	 * @param tradeIds
	 * @return
	 */
	private Map<String, String> getTradeMapByIds(String tradeIds) {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer("select id,name from t_s_category where id in (");
		for(String tradeId : tradeIds.split(",")){
			sql.append("'").append(tradeId).append("',");
		}
		sql = sql.deleteCharAt(sql.length()-1).append(")");
		List<Map<String, Object>> mapList = this.commonDao.findForJdbc(sql.toString(), null);
		for(Map<String, Object> m : mapList){
			map.put(m.get("id")+"", m.get("name")+"");
		}
		return map;
	}

	public void saveCustWordsTags(TCustWordsTagsEntity tCustWordsTags) throws SQLException {
		this.commonDao.save(tCustWordsTags);
	}
	
	@Override
	public void doCopyPlatformWords() throws SQLException {
		String retailerId = ResourceUtil.getRetailerId();
		TSUser user = ResourceUtil.getSessionUserName();
		StringBuffer sql = new StringBuffer("SELECT w.id,w.top_type_id topTypeId,w.sub_type_id subTypeId,w.thrid_type_id thridTypeId,w.content,w.type ")
				.append("from t_cust_words w WHERE w.id in (")
				.append("SELECT tt.template_id from t_trade_template tt WHERE tt.trade_id in (")
				.append("SELECT t.trade_id from t_s_trade_user t WHERE t.user_id = '").append(retailerId).append("' and t.`status` = 'A' ")
				.append(") and tt.`status` = 'A' ) ")
				.append("and w.`status` = 'A' and w.retailer_id = 'admin' ")//平台话术
				.append("and w.top_type_id = '102' ")//顾客话术
				.append("and w.id not in (SELECT original_id from t_cust_words where retailer_id = '").append(retailerId)//过滤已经复制过的话术
				.append("' and `status` = 'A' and original_id is not null)")
				;
		List<TCustWordsEntity> platformWordsList = this.findObjForJdbc(sql.toString(), TCustWordsEntity.class);
		if(Utility.isNotEmpty(platformWordsList)){
			Date d = Utility.getCurrentTimestamp();
			List<Map<String,Object>> dataMapList = new ArrayList<Map<String,Object>>();
			for(TCustWordsEntity entity : platformWordsList){
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", Utility.getUUID());
				dataMap.put("create_name",user.getRealName());
				dataMap.put("create_by",user.getUserName());
				dataMap.put("create_date",d);
				dataMap.put("update_date",d);
				dataMap.put("status",GlobalConstants.STATUS_ACTIVE);
				dataMap.put("top_type_id", entity.getTopTypeId());
				dataMap.put("sub_type_id", entity.getSubTypeId());
				dataMap.put("thrid_type_id", entity.getThridTypeId());
				dataMap.put("content", entity.getContent());
				dataMap.put("platform_type", "2");
				dataMap.put("retailer_id", retailerId);
				dataMap.put("type", entity.getType());
				dataMap.put("is_show", TCustWordsEntity.IS_SHOW_Y);
				dataMap.put("original_id", entity.getId());
				dataMapList.add(dataMap);
			}
			if(Utility.isNotEmpty(dataMapList)){
				Map<String,Object> resultMap= DBUtil.insertAll("t_cust_words", dataMapList);
				int n = (Integer) resultMap.get("affectRowCount");
				System.out.println("成功插入"+n+"条");
			}
		}
	}
	
	@Override
	public void doDelPlatformWords() {
		String retailerId = ResourceUtil.getRetailerId();
		TSUser user = ResourceUtil.getSessionUserName();
		StringBuffer sql = new StringBuffer("update t_cust_words set status ='")
				.append(GlobalConstants.STATUS_INACTIVE).append("',update_date ='")
				.append(Utility.getCurrentTimestamp()).append("',update_by = '")
				.append(user.getUserName()).append("',update_name = '")
				.append(user.getRealName()).append("' where retailer_id='")
				.append(retailerId).append("' and `status` = 'A' and original_id is not null ")
				.append("and top_type_id = '102' ")//顾客话术
				;
		this.updateBySqlString(sql.toString());
	}
	
}