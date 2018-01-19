package com.buss.template.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.template.entity.TTemplateTypeEntity;
import com.buss.words.entity.TCustWordsEntity;
import com.buss.words.entity.TCustWordsTagsEntity;
import com.buss.words.service.TCustWordsServiceI;

import common.GlobalConstants;


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
@RequestMapping("/templateWordsController")
public class TemplateWordsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TemplateWordsController.class);
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
	 * 平台资讯分类tab页
	 * @return
	 */
	@RequestMapping(params = "templateWordsTabs")
	public ModelAndView tCustWordsTabs(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/template/templateWordsTabs");
	}
	
	/**
	 * 平台话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "platformList")
	public ModelAndView platformList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		String hql = "FROM TTemplateTypeEntity WHERE status='A' and level='1' and platformType='1' and templateType='1' ";
		List<TTemplateTypeEntity> topTypeList = systemService.findByQueryString(hql);

		//获取行业信息
		getTemplateTrade(request);
		
		request.setAttribute("topTypeList", topTypeList);
		String tp = "1";  //平台话术列表
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){
			tp="3";//零售商查询平台话术列表
		}
		request.setAttribute("tp", tp);
		return new ModelAndView("com/buss/template/templateWordsList");
	}
	
	/**
	 * 零售商话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public ModelAndView retailerList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		String hql = "FROM TTemplateTypeEntity WHERE status='A' and level='1' and platformType='1' and templateType='1' ";
		List<TTemplateTypeEntity> topTypeList = systemService.findByQueryString(hql);

		//获取行业信息
		getTemplateTrade(request);
		request.setAttribute("topTypeList", topTypeList);
		return new ModelAndView("com/buss/template/templateWordsListOfRetailer");
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
	 * 商品话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "goodsWordsList")
	public ModelAndView goodsWordsList(HttpServletRequest request) {
		request.setAttribute("wordsId", request.getParameter("wordsId"));
		return new ModelAndView("com/buss/template/templateWordsGoodsList");
	}

	/**
	 * easyui AJAX请求数据       话术列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TCustWordsEntity tCustWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,
			@RequestParam(value = "tp") String platformType) {
		tCustWordsService.getPlatformList(request, dataGrid, platformType);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyui AJAX请求数据      商品话术列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */
	@RequestMapping(params = "datagridWordsGoods")
	public void datagridWordsGoods(TCustWordsEntity tCustWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,
			@RequestParam(value = "tp") String platformType) {
		tCustWordsService.getPlatformList(request, dataGrid,platformType);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除顾客话术
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TCustWordsEntity tCustWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tCustWords = systemService.flushEntity(TCustWordsEntity.class, tCustWords.getId());
		message = "顾客话术删除成功";
		try{
			tCustWords.setStatus("I");
			tCustWordsService.updateEntitie(tCustWords);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除顾客话术
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "顾客话术删除成功";
		try{
			for(String id:ids.split(",")){
				TCustWordsEntity tCustWords = systemService.flushEntity(TCustWordsEntity.class, id);
				tCustWords.setStatus("I");
				tCustWordsService.updateEntitie(tCustWords);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 添加顾客话术
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TCustWordsEntity tCustWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = tCustWordsService.saveCustWords(tCustWords,  request);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新顾客话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TCustWordsEntity tCustWords, HttpServletRequest request,TCustWordsTagsEntity tCustWordsTags) {
		AjaxJson j = new AjaxJson();
		message = tCustWordsService.updateCustWords(tCustWords,request);
		j.setMsg(message);
		return j;
	}

	/**
	 * 顾客话术新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TCustWordsEntity tCustWords, HttpServletRequest req) {
		//一级分类
		String hql = "FROM TTemplateTypeEntity WHERE status='A' and level='1' and platformType='1' and templateType='1' ";
		List<TTemplateTypeEntity> topTypeList = systemService.findByQueryString(hql);
		String retailerId = ResourceUtil.getRetailerId();
		req.setAttribute("retailerId", retailerId);
		req.setAttribute("topTypeList", topTypeList);
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return new ModelAndView("com/buss/template/templateWords-add");
	}
	
	/**
	 * 顾客话术编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TCustWordsEntity tCustWords, HttpServletRequest request,TCustWordsTagsEntity tCustWordsTags) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailerId", retailerId);
		tCustWordsService.goUpdateResult(tCustWords, request);
		return new ModelAndView("com/buss/template/templateWords-update");
	}
	
	/**
	 * 根据二级分类批量编辑行业页面跳转
	 * @return
	 */
	@RequestMapping(params = "goBatchUpdate")
	public ModelAndView goBatchUpdate(TCustWordsEntity tCustWords, HttpServletRequest req) {
		//二级分类
		String hql = "FROM TTemplateTypeEntity WHERE status='A' and level='2' and platformType='1'";
		List<TTemplateTypeEntity> subTypeList = systemService.findByQueryString(hql);
		req.setAttribute("subTypeList", subTypeList);
		return new ModelAndView("com/buss/template/templateWords-batchUpdate");
	}
	
	/**
	 * 批量编辑关联行业（根据话术模版二级分类批量更新）
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doBatchUpdate")
	@ResponseBody
	public AjaxJson doBatchUpdate(String subTypeId,String tradeIds,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "批量编辑关联行业成功";
		try {
			if(Utility.isNotEmpty(subTypeId)&&Utility.isNotEmpty(tradeIds)){
				this.tCustWordsService.batchInsertTradeIds(subTypeId, tradeIds);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "批量编辑关联行业失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
    
    /**
	 * 编辑话术（复制平台话术到自有话术）
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doCopy")
	@ResponseBody
	public AjaxJson doCopy(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "话术模板编辑成功";
		try {
			TCustWordsEntity custWords = this.tCustWordsService.get(TCustWordsEntity.class, id);
			if(Utility.isNotEmpty(custWords)){
				String retailerId = ResourceUtil.getRetailerId();
				tCustWordsService.doCopy(custWords,retailerId);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}else{
				message = "话术模板查询失败，请刷新列表";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "话术模板编辑失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	 /**
		 * 批量编辑话术（复制平台话术到自有话术）
		 * @param ids
		 * @return
		 */
		@RequestMapping(params = "doBatchCopy")
		@ResponseBody
		public AjaxJson doBatchCopy(String ids, HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			message = "话术模板编辑成功";
			try {
				if(Utility.isNotEmpty(ids)){
					String retailerId = ResourceUtil.getRetailerId();
					for(String id : ids.split(",")){
						TCustWordsEntity custWords = this.tCustWordsService.get(TCustWordsEntity.class, id);
						tCustWordsService.doCopy(custWords,retailerId);
					}
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}else{
					message = "话术模板查询失败，请刷新列表";
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "话术模板批量编辑失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
		
		/**
		 * 一键复制g+顾客话术
		 * @param ids
		 * @return
		 */
		@RequestMapping(params = "doCopyPlatformWords")
		@ResponseBody
		public AjaxJson doCopyPlatformWords(HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			message = "一键复制g+顾客话术成功";
			try {
				tCustWordsService.doCopyPlatformWords();
			} catch (Exception e) {
				e.printStackTrace();
				message = "一键复制g+顾客话术失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
		
		/**
		 * 一键删除g+顾客话术
		 * @param ids
		 * @return
		 */
		@RequestMapping(params = "doDelPlatformWords")
		@ResponseBody
		public AjaxJson doDelPlatformWords(HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			message = "一键删除g+顾客话术成功";
			try {
				tCustWordsService.doDelPlatformWords();
			} catch (Exception e) {
				e.printStackTrace();
				message = "一键删除g+顾客话术失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
    
}
