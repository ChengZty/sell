package com.buss.words.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.words.entity.TCustWordsEntity;
import com.buss.words.entity.TCustWordsTypeEntity;
import com.buss.words.service.TCustWordsServiceI;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 顾客话术
 * @author onlineGenerator
 * @date 2017-02-10 17:47:52
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tCustWordsController")
public class TCustWordsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TCustWordsController.class);

	@Autowired
	private TCustWordsServiceI tCustWordsService;
	@Autowired
	private SystemService systemService;
	private String message;
	
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
	@RequestMapping(params = "tCustWordsTabs")
	public ModelAndView tCustWordsTabs(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/words/tCustWordsTabs");
	}
	
	/**
	 * 平台顾客话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "platformList")
	public ModelAndView platformList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		List<TCustWordsTypeEntity> topTypeList = this.systemService.findByProperty(TCustWordsTypeEntity.class, "level", 1);
		request.setAttribute("topTypeList", topTypeList);
		return new ModelAndView("com/buss/words/tCustWordsList");
	}
	
	/**
	 * 零售商顾客话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public ModelAndView retailerList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		List<TCustWordsTypeEntity> topTypeList = this.systemService.findByProperty(TCustWordsTypeEntity.class, "level", 1);
		request.setAttribute("topTypeList", topTypeList);
		return new ModelAndView("com/buss/words/tCustWordsListOfRetailer");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TCustWordsEntity tCustWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,
			@RequestParam(value = "tp") String platformType) {
		CriteriaQuery cq = new CriteriaQuery(TCustWordsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tCustWords, request.getParameterMap());
		try{
		//自定义追加查询条件
			if(common.GlobalConstants.PLATFORM_TYPE_2.equals(platformType)){
				String retailerId = ResourceUtil.getRetailerId();
				if(StringUtil.isNotEmpty(retailerId)){
					cq.eq("retailerId", retailerId);
				}
			}
			cq.eq("platformType", platformType);
			String sort = dataGrid.getSort();
			if(Utility.isEmpty(sort)){
				cq.addOrder("topTypeId", SortDirection.asc);
				cq.addOrder("subTypeId", SortDirection.asc);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tCustWordsService.getDataGridReturn(cq, true);
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
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TCustWordsEntity tCustWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客话术添加成功";
		try{
			tCustWords.setIsShow(TCustWordsEntity.IS_SHOW_N);
			tCustWordsService.saveCustWords(tCustWords,request);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客话术添加失败";
			throw new BusinessException(e.getMessage());
		}
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
	public AjaxJson doUpdate(TCustWordsEntity tCustWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客话术更新成功";
		try {
			TCustWordsEntity t = tCustWordsService.get(TCustWordsEntity.class, tCustWords.getId());
			MyBeanUtils.copyBeanNotNull2Bean(tCustWords, t);
			tCustWordsService.updateEntitie(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "顾客话术更新失败";
			throw new BusinessException(e.getMessage());
		}
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
		List<TCustWordsTypeEntity> topTypeList = this.systemService.findByProperty(TCustWordsTypeEntity.class, "level", 1);
		req.setAttribute("topTypeList", topTypeList);
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return new ModelAndView("com/buss/words/tCustWords-add");
	}
	/**
	 * 顾客话术编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TCustWordsEntity tCustWords, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tCustWords.getId())) {
			tCustWords = tCustWordsService.flushEntity(TCustWordsEntity.class, tCustWords.getId());
			//一级分类
			List<TCustWordsTypeEntity> topTypeList = this.systemService.findByProperty(TCustWordsTypeEntity.class, "level", 1);
			List<TCustWordsTypeEntity> subTypeList = null;
			if(StringUtil.isNotEmpty(tCustWords.getTopTypeId())){
				subTypeList = this.systemService.findByProperty(TCustWordsTypeEntity.class, "parent.id", tCustWords.getTopTypeId());
			}
			req.setAttribute("topTypeList", topTypeList);
			req.setAttribute("subTypeList", subTypeList);
			req.setAttribute("tCustWordsPage", tCustWords);
//			if(TCustWordsEntity.TYPE_2.equals(tCustWords.getType())){
//				List<TCustWordsPicsEntity> picList = this.tCustWordsService.findByProperty(TCustWordsPicsEntity.class, "custWordsId", tCustWords.getId());
//				tCustWords.setPicList(picList);
//			}
		}
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		req.setAttribute("load", req.getParameter("load"));
		return new ModelAndView("com/buss/words/tCustWords-update");
	}
	
	/**
	 * 顾客话术设置可见
	 * @return
	 */
	@RequestMapping(params = "doShow")
	@ResponseBody
	public AjaxJson doShow(TCustWordsEntity tCustWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客话术可见设置成功";
		try{
			Long n = tCustWordsService.getTotalVisibleCountBySubTypeId(tCustWords.getSubTypeId()+"");
			if(n>10-1){//获取二级分类可见总数，不能超过10条
				message = "同一个二级分类顾客话术可见设置不能超过10条";
			}else{
				this.tCustWordsService.updateBySqlString("update t_cust_words set is_show = '"+TCustWordsEntity.IS_SHOW_Y
						+"' where id='"+tCustWords.getId()+"'");
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客话术可见设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 顾客话术设置
	 * @return
	 */
	@RequestMapping(params = "doHide")
	@ResponseBody
	public AjaxJson doHide(TCustWordsEntity tCustWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客话术不可见设置成功";
		try{
			this.tCustWordsService.updateBySqlString("update t_cust_words set is_show = '"+TCustWordsEntity.IS_SHOW_N
					+"' where id='"+tCustWords.getId()+"'");
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客话术不可见设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
