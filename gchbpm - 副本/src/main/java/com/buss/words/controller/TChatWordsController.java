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

import com.buss.words.entity.TChatWordsEntity;
import com.buss.words.entity.TChatWordsTypeEntity;
import com.buss.words.service.TChatWordsServiceI;



/**   
 * @Title: Controller
 * @Description: 撩客话术
 * @author onlineGenerator
 * @date 2017-02-10 17:47:52
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tChatWordsController")
public class TChatWordsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TChatWordsController.class);

	@Autowired
	private TChatWordsServiceI tChatWordsService;
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
	@RequestMapping(params = "tChatWordsTabs")
	public ModelAndView tChatWordsTabs(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/words/tChatWordsTabs");
	}
	
	/**
	 * 平台撩客话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "platformList")
	public ModelAndView platformList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/words/tChatWordsList");
	}
	
	/**
	 * 零售商撩客话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public ModelAndView retailerList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/words/tChatWordsListOfRetailer");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TChatWordsEntity tChatWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,
			@RequestParam(value = "tp") String platformType) {
		CriteriaQuery cq = new CriteriaQuery(TChatWordsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tChatWords, request.getParameterMap());
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
				cq.addOrder("typeId", SortDirection.asc);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tChatWordsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除撩客话术
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TChatWordsEntity tChatWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tChatWords = systemService.flushEntity(TChatWordsEntity.class, tChatWords.getId());
		message = "撩客话术删除成功";
		try{
			tChatWords.setStatus("I");
			tChatWordsService.updateEntitie(tChatWords);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "撩客话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除撩客话术
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "撩客话术删除成功";
		try{
			for(String id:ids.split(",")){
				TChatWordsEntity tChatWords = systemService.flushEntity(TChatWordsEntity.class, id);
				tChatWords.setStatus("I");
				tChatWordsService.updateEntitie(tChatWords);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "撩客话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加撩客话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TChatWordsEntity tChatWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "撩客话术添加成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			String platformType = common.GlobalConstants.PLATFORM_TYPE_2;
			if(StringUtil.isEmpty(retailerId)){
				retailerId = "admin";
				platformType = common.GlobalConstants.PLATFORM_TYPE_1;
			}
			tChatWords.setPlatformType(platformType);
			tChatWords.setRetailerId(retailerId);
			tChatWordsService.save(tChatWords);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "撩客话术添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新撩客话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TChatWordsEntity tChatWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "撩客话术更新成功";
		TChatWordsEntity t = tChatWordsService.get(TChatWordsEntity.class, tChatWords.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tChatWords, t);
			tChatWordsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "撩客话术更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 撩客话术新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TChatWordsEntity tChatWords, HttpServletRequest req) {
		//分类
//		String retailerId = ResourceUtil.getRetailerId();
//		if(Utility.isEmpty(retailerId)){
//			retailerId = "admin";
//		}
		List<TChatWordsTypeEntity> typeList = this.systemService.findByProperty(TChatWordsTypeEntity.class, "retailerId", "admin");
		req.setAttribute("typeList", typeList);
		return new ModelAndView("com/buss/words/tChatWords-add");
	}
	/**
	 * 撩客话术编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TChatWordsEntity tChatWords, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tChatWords.getId())) {
			tChatWords = tChatWordsService.flushEntity(TChatWordsEntity.class, tChatWords.getId());
			req.setAttribute("tChatWordsPage", tChatWords);
		}
//		String retailerId = ResourceUtil.getRetailerId();
//		if(Utility.isEmpty(retailerId)){
//			retailerId = "admin";
//		}
		List<TChatWordsTypeEntity> typeList = this.systemService.findByProperty(TChatWordsTypeEntity.class, "retailerId", "admin");
		req.setAttribute("typeList", typeList);
		return new ModelAndView("com/buss/words/tChatWords-update");
	}
	
}
