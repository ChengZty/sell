package com.buss.words.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.words.entity.TCustWordsTypeEntity;
import com.buss.words.entity.TVisibleWordsEntity;
import com.buss.words.service.TCustWordsServiceI;
import com.buss.words.service.TVisibleWordsServiceI;



/**   
 * @Title: Controller
 * @Description: 可见话术
 * @author onlineGenerator
 * @date 2016-12-06 10:37:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tVisibleWordsController")
public class TVisibleWordsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TVisibleWordsController.class);

	@Autowired
	private TVisibleWordsServiceI tVisibleWordsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TCustWordsServiceI tCustWordsService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 可见话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request,@RequestParam(value = "tp") String wordsType) {
		request.setAttribute("tp", wordsType);
		if(TVisibleWordsEntity.WORDS_TYPE_CUST.equals(wordsType)){//顾客话术
			return new ModelAndView("com/buss/words/tVisibleWordsListOfCust");
		}else if(TVisibleWordsEntity.WORDS_TYPE_GOODS.equals(wordsType)){//商品话术
			return new ModelAndView("com/buss/words/tVisibleWordsListOfGoods");
		}else if(TVisibleWordsEntity.WORDS_TYPE_ACTIVITY.equals(wordsType)){//活动话术
			return new ModelAndView("com/buss/words/tVisibleWordsListOfActivity");
		}else if(TVisibleWordsEntity.WORDS_TYPE_CHAT.equals(wordsType)){//撩客话术
			return new ModelAndView("com/buss/words/tVisibleWordsListOfChat");
		}
		return null;
	}

	/**
	 * easyui AJAX请求数据 顾客可见话术
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagridOfCust")
	public void datagridOfCust(TVisibleWordsEntity tVisibleWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String content = request.getParameter("content");
			String topTypeId = request.getParameter("topTypeId");
			String subTypeId = request.getParameter("subTypeId");
			String type = request.getParameter("type");
			String visible = request.getParameter("visible");
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sql = new StringBuffer("SELECT w.id,w.update_date as updateDate,w.top_type_id as topTypeId,w.sub_type_id as subTypeId,ifnull(w.content,'') content,v.id as wordsId,")
				.append( "w.type ,CASE WHEN v.id is NULL THEN	'0' ELSE '1' END as visible")
				.append(" from t_cust_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'").append(" and v.retailer_id ='").append(retailerId)
				.append("' WHERE w.`status` = 'A' and w.platform_type = '1'");
			StringBuffer countSql = new StringBuffer("select count(1) from t_cust_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'")
				.append(" and v.retailer_id ='").append(retailerId).append("' WHERE w.`status` = 'A' and w.platform_type = '1'");
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
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据 商品可见话术
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridOfGoods")
	public void datagridOfGoods(TVisibleWordsEntity tVisibleWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String content = request.getParameter("content");
//			String newsType = request.getParameter("newsType");
			String visible = request.getParameter("visible");
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sql = new StringBuffer("SELECT w.id,w.update_date as updateDate,w.top_category_name as topCategoryName,w.sub_category_name as subCategoryName,")
				.append( "w.thrid_category_name as thridCategoryName,w.content,v.id  as wordsId,CASE WHEN v.id is NULL THEN	'0' ELSE '1' END as visible")
				.append(" from t_goods_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'").append(" and v.retailer_id ='").append(retailerId)
				.append("' WHERE w.`status` = 'A' and w.platform_type = '1'");
			StringBuffer countSql = new StringBuffer("select count(1) from t_goods_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'")
				.append(" and v.retailer_id ='").append(retailerId).append("' WHERE w.`status` = 'A' and w.platform_type = '1'");
			if(!StringUtil.isEmpty(content)){
				sql.append(" and content like '%").append(content).append("%'");
				countSql.append(" and content like '%").append(content).append("%'");
			}
//			if(!StringUtil.isEmpty(newsType)){//防止查like '%2%' 的时候把like '%12%' 的结果查出来
//				sql.append(" and CONCAT(',',n.news_type,',') like '%,").append(newsType).append(",%'");
//				countSql.append(" and CONCAT(',',n.news_type,',') like '%,").append(newsType).append(",%'");
//			}
			if(!StringUtil.isEmpty(visible)){
				if("0".equals(visible)){
					sql.append(" and v.id is null");
					countSql.append(" and v.id is null");
				}else if("1".equals(visible)){
					sql.append(" and v.id is not null");
					countSql.append(" and v.id is not null");
				}
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据 活动可见话术
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridOfActivity")
	public void datagridOfActivity(TVisibleWordsEntity tVisibleWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String content = request.getParameter("content");
//			String newsType = request.getParameter("newsType");
			String visible = request.getParameter("visible");
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sql = new StringBuffer("SELECT w.id,w.update_date as updateDate,ifnull(w.activity_type_id,'') as activityTypeId,w.content,v.id as wordsId,")
				.append( "CASE WHEN v.id is NULL THEN	'0' ELSE '1' END as visible")
				.append(" from t_activity_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'").append(" and v.retailer_id ='").append(retailerId)
				.append("' WHERE w.`status` = 'A' and w.platform_type = '1'");
			StringBuffer countSql = new StringBuffer("select count(1) from t_activity_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'")
				.append(" and v.retailer_id ='").append(retailerId).append("' WHERE w.`status` = 'A' and w.platform_type = '1'");
			if(!StringUtil.isEmpty(content)){
				sql.append(" and content like '%").append(content).append("%'");
				countSql.append(" and content like '%").append(content).append("%'");
			}
//			if(!StringUtil.isEmpty(newsType)){//防止查like '%2%' 的时候把like '%12%' 的结果查出来
//				sql.append(" and CONCAT(',',n.news_type,',') like '%,").append(newsType).append(",%'");
//				countSql.append(" and CONCAT(',',n.news_type,',') like '%,").append(newsType).append(",%'");
//			}
			if(!StringUtil.isEmpty(visible)){
				if("0".equals(visible)){
					sql.append(" and v.id is null");
					countSql.append(" and v.id is null");
				}else if("1".equals(visible)){
					sql.append(" and v.id is not null");
					countSql.append(" and v.id is not null");
				}
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据 顾客可见话术
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagridOfChat")
	public void datagridOfChat(TVisibleWordsEntity tVisibleWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			String content = request.getParameter("content");
			String typeId = request.getParameter("typeId");
			String visible = request.getParameter("visible");
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sql = new StringBuffer("SELECT w.id,w.update_date as updateDate,w.type_id as typeId,w.content,v.id as wordsId,")
				.append( "CASE WHEN v.id is NULL THEN	'0' ELSE '1' END as visible")
				.append(" from t_chat_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'").append(" and v.retailer_id ='").append(retailerId)
				.append("' WHERE w.`status` = 'A' and w.platform_type = '1'");
			StringBuffer countSql = new StringBuffer("select count(1) from t_chat_words w LEFT JOIN t_visible_words v on w.id = v.words_id and v.`status` = 'A'")
				.append(" and v.retailer_id ='").append(retailerId).append("' WHERE w.`status` = 'A' and w.platform_type = '1'");
			if(!StringUtil.isEmpty(content)){
				sql.append(" and content like '%").append(content).append("%'");
				countSql.append(" and content like '%").append(content).append("%'");
			}
			if(!StringUtil.isEmpty(typeId)){
				sql.append(" and w.type_id = '").append(typeId).append("'");
				countSql.append(" and w.type_id = '").append(typeId).append("'");
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
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 话术不可见设置
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TVisibleWordsEntity tVisibleWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tVisibleWords = systemService.flushEntity(TVisibleWordsEntity.class, tVisibleWords.getId());
		message = "话术不可见设置成功";
		try{
			//TODO 获取可见的总条数，同一个二级分类只能
			tVisibleWords.setStatus("I");
			tVisibleWordsService.updateEntitie(tVisibleWords);
		}catch(Exception e){
			e.printStackTrace();
			message = "话术不可见设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 话术不可见批量设置
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "话术不可见批量设置成功";
		try{
			if(StringUtils.isNotEmpty(ids)){
				TSUser user = ResourceUtil.getSessionUserName();
				StringBuffer sql = new StringBuffer("update t_visible_words set update_by = '").append(user.getUserName()).append("',update_name = '")
				.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id in (");
				for(String id:ids.split(",")){
					sql.append("'").append(id).append("',");
				}
				sql = sql.deleteCharAt(sql.length()-1).append(")");
				systemService.updateBySqlString(sql.toString());
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "话术不可见批量设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 话术可见设置
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TVisibleWordsEntity tVisibleWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "话术可见设置成功";
		try{
			if(TVisibleWordsEntity.WORDS_TYPE_CUST.equals(tVisibleWords.getWordsType())){//顾客话术
				String subTypeId = request.getParameter("subTypeId");//二级分类
				Long n = tCustWordsService.getTotalVisibleCountBySubTypeId(subTypeId);
				if(n+1>10){//获取二级分类可见总数，不能超过10条
					message = "同一个二级分类顾客话术可见设置不能超过10条";
				}else{
					tVisibleWords.setRetailerId(ResourceUtil.getRetailerId());
					tVisibleWords.setStatus("A");
					tVisibleWordsService.save(tVisibleWords);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
			}else{
				tVisibleWords.setRetailerId(ResourceUtil.getRetailerId());
				tVisibleWords.setStatus("A");
				tVisibleWordsService.save(tVisibleWords);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "话术可见设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 话术可见批量设置
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doBatchAdd")
	@ResponseBody
	public AjaxJson doBatchAdd(String wordsIds,String wordsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "话术可见批量设置成功";
		try{
			if(StringUtils.isNotEmpty(wordsIds)){
				String rId = ResourceUtil.getRetailerId();
				for(String wordsId : wordsIds.split(",")){
					TVisibleWordsEntity tVisibleWords = new TVisibleWordsEntity();
					tVisibleWords.setWordsId(wordsId);
					tVisibleWords.setWordsType(wordsType);
					tVisibleWords.setRetailerId(rId);
					tVisibleWords.setStatus("A");
					tVisibleWordsService.save(tVisibleWords);
				}
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "话术可见批量设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 顾客话术可见批量设置（同一个二级分类顾客话术可见设置不能超过10条）
	 * @param ids (格式为id_subTypeId) 
	 * @return
	 */
	@RequestMapping(params = "doBatchAddCustWords")
	@ResponseBody
	public AjaxJson doBatchAddCustWords(String wordsIds, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客话术可见批量设置成功";
		try{
			if(StringUtils.isNotEmpty(wordsIds)){
				//key:subTypeId，value:List<id>
				Map<String,List<String>> map = new HashMap<String, List<String>>();
				//处理合并相同的二级分类
				for(String wordsId : wordsIds.split(",")){
					String[] id_subTypeId = wordsId.split("_");
					if(map.containsKey(id_subTypeId[1])){
						List<String> ids = map.get(id_subTypeId[1]);
						ids.add(id_subTypeId[0]);
					}else{
						List<String> ids = new ArrayList<String>();
						ids.add(id_subTypeId[0]);
						map.put(id_subTypeId[1], ids);
					}
				}
				String rId = ResourceUtil.getRetailerId();
				for(Entry<String, List<String>> entry:map.entrySet()){
					String subTypeId = entry.getKey();
					List<String> ids = entry.getValue();
					Long n = tCustWordsService.getTotalVisibleCountBySubTypeId(subTypeId);
					int n2 = ids.size();
					if(n+n2>10){
						//获取二级分类可见总数，不能超过10条
						TCustWordsTypeEntity wordsType = this.systemService.get(TCustWordsTypeEntity.class, Integer.valueOf(subTypeId));
						message = "同一个二级分类["+wordsType.getName()+"]顾客话术可见设置不能超过10条";
					}else{
						for(String id : ids){
							TVisibleWordsEntity tVisibleWords = new TVisibleWordsEntity();
							tVisibleWords.setWordsId(id);
							tVisibleWords.setWordsType(TVisibleWordsEntity.WORDS_TYPE_CUST);
							tVisibleWords.setRetailerId(rId);
							tVisibleWords.setStatus("A");
							tVisibleWordsService.save(tVisibleWords);
						}
					}
				}
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客话术可见批量设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
}
