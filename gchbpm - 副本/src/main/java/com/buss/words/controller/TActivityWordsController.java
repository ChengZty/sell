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
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.cms.entity.TContentCategoryEntity;
import com.buss.words.entity.TActivityWordsEntity;
import com.buss.words.service.TActivityWordsServiceI;



/**   
 * @Title: Controller
 * @Description: 活动话术
 * @author onlineGenerator
 * @date 2017-02-10 17:47:52
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tActivityWordsController")
public class TActivityWordsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TActivityWordsController.class);

	@Autowired
	private TActivityWordsServiceI tActivityWordsService;
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
	@RequestMapping(params = "tActivityWordsTabs")
	public ModelAndView tActivityWordsTabs(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/words/tActivityWordsTabs");
	}
	
	/**
	 * 平台活动话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "platformList")
	public ModelAndView platformList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/words/tActivityWordsList");
	}
	
	/**
	 * 零售商活动话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public ModelAndView retailerList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/words/tActivityWordsListOfRetailer");
	}
	
	/**
	 * 零售商活动话术包括可见话术列表 页面跳转  （选择活动话术）
	 * @return
	 */
	@RequestMapping(params = "retailerVisibleList")
	public ModelAndView retailerVisibleList(HttpServletRequest request,String finActId) {
		request.setAttribute("finActId", finActId);
		return new ModelAndView("com/buss/words/tActivityWordsListOfRetailerVisible");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TActivityWordsEntity tActivityWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,
			@RequestParam(value = "tp") String platformType) {
		CriteriaQuery cq = new CriteriaQuery(TActivityWordsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tActivityWords, request.getParameterMap());
		try{
		//自定义追加查询条件
			if(common.GlobalConstants.PLATFORM_TYPE_2.equals(platformType)){
				String retailerId = ResourceUtil.getRetailerId();
				if(StringUtil.isNotEmpty(retailerId)){
					cq.eq("retailerId", retailerId);
				}
			}
			cq.eq("platformType", platformType);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tActivityWordsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * easyui 零售商通过活动选择活动话术列表（包括可见话术）
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */
	
	@RequestMapping(params = "retailerVisibleDatagrid")
	public void retailerVisibleDatagrid(TActivityWordsEntity tActivityWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TActivityWordsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tActivityWords, request.getParameterMap());
		try{
			//自定义追加查询条件
			String finActId = request.getParameter("finActId");//活动ID
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sqlstr = new StringBuffer(" (retailer_id = '").append(retailerId).append("'  OR id in (SELECT v.words_id from t_visible_words v where v.`status` = 'A' and v.retailer_id = '")
				.append(retailerId).append("' and v.words_type='3'))")//可见活动话术
				.append(" and id not in (SELECT f.words_id from t_fin_activity_words f where f.`status` = 'A' and f.words_id is not null and f.fin_act_id = '").append(finActId).append("')");
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tActivityWords, request.getParameterMap(), sqlstr.toString());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tActivityWordsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除活动话术
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TActivityWordsEntity tActivityWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tActivityWords = systemService.flushEntity(TActivityWordsEntity.class, tActivityWords.getId());
		message = "活动话术删除成功";
		try{
			tActivityWords.setStatus("I");
			tActivityWordsService.updateEntitie(tActivityWords);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除活动话术
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "活动话术删除成功";
		try{
			for(String id:ids.split(",")){
				TActivityWordsEntity tActivityWords = systemService.flushEntity(TActivityWordsEntity.class, id);
				tActivityWords.setStatus("I");
				tActivityWordsService.updateEntitie(tActivityWords);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "活动话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加活动话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TActivityWordsEntity tActivityWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动话术添加成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			String platformType = common.GlobalConstants.PLATFORM_TYPE_2;
			if(StringUtil.isEmpty(retailerId)){
				retailerId = "admin";
				platformType = common.GlobalConstants.PLATFORM_TYPE_1;
			}
			tActivityWords.setPlatformType(platformType);
			tActivityWords.setRetailerId(retailerId);
			tActivityWordsService.save(tActivityWords);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动话术添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新活动话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TActivityWordsEntity tActivityWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动话术更新成功";
		TActivityWordsEntity t = tActivityWordsService.get(TActivityWordsEntity.class, tActivityWords.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tActivityWords, t);
			t.setActivityTypeId(tActivityWords.getActivityTypeId());
			tActivityWordsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "活动话术更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 活动话术新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TActivityWordsEntity tActivityWords, HttpServletRequest req) {
		List<TContentCategoryEntity> activityTypeList = this.systemService.findByProperty(TContentCategoryEntity.class, "categoryType", "SPHD");
		req.setAttribute("activityTypeList", activityTypeList);
		return new ModelAndView("com/buss/words/tActivityWords-add");
	}
	/**
	 * 活动话术编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TActivityWordsEntity tActivityWords, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tActivityWords.getId())) {
			tActivityWords = tActivityWordsService.flushEntity(TActivityWordsEntity.class, tActivityWords.getId());
			List<TContentCategoryEntity> activityTypeList = this.systemService.findByProperty(TContentCategoryEntity.class, "categoryType", "SPHD");
			req.setAttribute("activityTypeList", activityTypeList);
			req.setAttribute("tActivityWordsPage", tActivityWords);
		}
		return new ModelAndView("com/buss/words/tActivityWords-update");
	}
	
}
