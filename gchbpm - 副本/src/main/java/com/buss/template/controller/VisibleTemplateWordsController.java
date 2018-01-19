package com.buss.template.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSTradeUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.template.entity.TTemplateTypeEntity;
import com.buss.template.entity.TTradeTemplateEntity;
import com.buss.words.entity.TCustWordsEntity;
import com.buss.words.entity.TCustWordsTypeEntity;
import com.buss.words.entity.TVisibleWordsEntity;
import com.buss.words.service.TCustWordsServiceI;
import com.buss.words.service.TVisibleWordsServiceI;


/**
 * @Title: Controller
 * @Description: 资讯信息表
 * @author onlineGenerator
 * @date 2016-03-14 16:00:50
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/visibleTemplateWordsController")
public class VisibleTemplateWordsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VisibleTemplateWordsController.class);

	@Autowired
	private SystemService systemService;

	@Autowired
	private TCustWordsServiceI tCustWordsService;
	@Autowired
	private TVisibleWordsServiceI tVisibleWordsService;
	
	private String message;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 零售商可见话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerVisibleList")
	public ModelAndView retailerVisibleList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		String hql = "FROM TTemplateTypeEntity WHERE status='A' and level='1' and platformType='1' and templateType='1' ";
		List<TTemplateTypeEntity> topTypeList = systemService.findByQueryString(hql);

		String sqlTrade = "select * FROM t_s_category WHERE status='A' and level='2'";
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){
			sqlTrade = "select tsc.* FROM t_s_trade_user ttt left join t_s_category tsc on tsc.id = ttt.trade_id "+
			" WHERE tsc.status='A' and tsc.level='2' and ttt.status='A' and user_id = '"+retailerId+"'";
		}
		List<TSCategoryEntity> templateTrade = systemService.findObjForJdbc(sqlTrade, TSCategoryEntity.class);
		

		request.setAttribute("templateTrade", templateTrade);
		request.setAttribute("topTypeList", topTypeList);
		
		return new ModelAndView("com/buss/template/tVisibleWordsList");
	}
	
	/**
	 * easyui AJAX请求数据 零售商可见话术列表 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagridRetailerVisible")
	public void datagridRetailerVisible(TVisibleWordsEntity tVisibleWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		tVisibleWordsService.getRetailerVisibleWords(request,dataGrid);
		
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
						TCustWordsTypeEntity wordsType = this.systemService.get(TCustWordsTypeEntity.class, subTypeId);
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
