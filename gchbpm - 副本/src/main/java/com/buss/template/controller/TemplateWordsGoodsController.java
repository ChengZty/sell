package com.buss.template.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.activity.entity.TFinActivityWordsEntity;
import com.buss.template.entity.TTemplateTypeEntity;
import com.buss.words.entity.TCustWordsEntity;
import com.buss.words.service.TCustWordsServiceI;


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
@RequestMapping("/templateWordsGoodsController")
public class TemplateWordsGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TemplateWordsGoodsController.class);

	@Autowired
	private SystemService systemService;
	@Autowired
	private TCustWordsServiceI tCustWordsService;
	private String message;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	/**
	 * 商品列表(商品话术菜单)
	 * @return
	 */
	@RequestMapping(params = "wordsGoodsList")
	public ModelAndView wordsGoodsList(HttpServletRequest req) {
		//获取行业信息
		getTemplateTrade(req);
		TSUser user =ResourceUtil.getSessionUserName();
		req.setAttribute("userType", user.getUserType());
		return new ModelAndView("com/buss/template/tWordsGoodsList");
	}
	
	/**
	 * 商品话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "goodsWordsList")
	public ModelAndView goodsWordsList(HttpServletRequest request) {
		request.setAttribute("finActId", request.getParameter("fin_act_Id"));
		request.setAttribute("wordsType", TFinActivityWordsEntity.WORDS_TYPE_GOODS);//商品话术
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/template/goodsWordsList2");
	}
	
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TFinActivityWordsEntity tFinActivityWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TFinActivityWordsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinActivityWords, request.getParameterMap());
		try{
		//自定义追加查询条件
			String finActId = request.getParameter("fin_Act_Id");
			if(StringUtil.isNotEmpty(finActId)){
				cq.eq("finActId", finActId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	/**
	 * easyui AJAX请求数据     获取商品话术列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridGoodsWords")
	public void datagridGoodsWords(TCustWordsEntity tCustWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		tCustWordsService.getGoodsWordsList(request, dataGrid);
		
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 零售商活动话术包括可见话术列表 页面跳转  （选择商品话术）
	 * @return
	 */
	@RequestMapping(params = "goodsWordsForGoods")
	public ModelAndView goodsWordsForGoods(HttpServletRequest request) {
//		String hql = "FROM TTemplateTypeEntity WHERE status='A' and parent.id='101' and level='2' and platformType='1' and templateType='1' ";
//		List<TTemplateTypeEntity> subTypeList = systemService.findByQueryString(hql);

		//获取行业信息
		getTemplateTrade(request);
		

		List<TTemplateTypeEntity> typeList = new ArrayList<TTemplateTypeEntity>();
		//是否是零售商登录
		String retailerId = ResourceUtil.getRetailerId();
		List<Map<String,Object>> tradeMap = null;  //行业列表
		String sqlTrade = "";
		//获取零售商所属行业
		if(StringUtil.isNotEmpty(retailerId)){
			sqlTrade = "select tsc.id id,tsc.level level,tsc.name name from t_s_category tsc LEFT JOIN t_s_trade_user tstu "+
					"on tsc.id=tstu.trade_id where tsc.status='A' and tstu.status='A' and level='2' and tsc.retailer_id='admin' "+
					" and tstu.user_id='"+retailerId+"'";
		}else{
			sqlTrade = "select id,level,name from t_s_category where status='A' and level='2' and retailer_id='admin' ";
		}
		tradeMap = systemService.findForJdbc(sqlTrade, null);
		String replaceStr = "";
//		List<TTemplateTypeEntity> list = new ArrayList<TTemplateTypeEntity>();
		for (Map<String,Object> map : tradeMap) {
			String id= map.get("id").toString();
			String name= map.get("name").toString();
			TTemplateTypeEntity tTemplateTypeEntity = new TTemplateTypeEntity();
			tTemplateTypeEntity.setId(id);
			tTemplateTypeEntity.setName(name);
			tTemplateTypeEntity.setLevel(map.get("level").toString());
			typeList.add(tTemplateTypeEntity);
			replaceStr+=name+"_"+id+",";
		}
		
		request.setAttribute("replaceStr", replaceStr.substring(0, replaceStr.length()-1));
		request.setAttribute("subTypeList", typeList);
		return new ModelAndView("com/buss/template/templateWordsForGoods");
	}
	
	
	/**
	 * 商品列表(商品话术菜单)
	 * @return
	 */
	@RequestMapping(params = "wordsAddGoodsList")
	public ModelAndView wordsAddGoodsList(HttpServletRequest req) {
		TSUser user =ResourceUtil.getSessionUserName();
		req.setAttribute("userType", user.getUserType());
		req.setAttribute("wordsId", req.getParameter("wordsId"));
		return new ModelAndView("com/buss/template/wordsAddGoodsList");
	}
	

	//获取行业信息
	private void getTemplateTrade(HttpServletRequest request) {
		//获取行业信息
		String sqlTrade = "select * FROM t_s_category WHERE status='A' and level='2'";
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){
			sqlTrade = "select tsc.* FROM t_s_trade_user ttt left join t_s_category tsc on tsc.id = ttt.trade_id "+
			" WHERE tsc.status='A' and tsc.level='2' and ttt.status='A' and user_id = '"+retailerId+"'";
		}
		List<TSCategoryEntity> templateTrade = systemService.findObjForJdbc(sqlTrade, TSCategoryEntity.class);
		
		request.setAttribute("templateTrade", templateTrade);
	}
	
	
	/**
	 * 活动话术新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TFinActivityWordsEntity tFinActivityWords, HttpServletRequest req) {
		req.setAttribute("finActId", tFinActivityWords.getFinActId());//活动ID/商品ID
		req.setAttribute("wordsType", tFinActivityWords.getWordsType());//话术类型
		return new ModelAndView("com/buss/activity/tFinActivityWords-add");
	}
}
