package com.buss.cms.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.cms.entity.TContentCategoryEntity;
import com.buss.cms.entity.TContentCustomIndexEntity;
import com.buss.cms.service.TContentCustomIndexServiceI;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 零售商自定义栏目
 * @author onlineGenerator
 * @date 2016-09-23 11:32:03
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/cmsCustom")
public class TContentCustomIndexController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TContentCustomIndexController.class);

	@Autowired
	private TContentCustomIndexServiceI tContentCustomIndexService;
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
	 * 零售商自定义栏目列表 页面跳转
	 * 补录零售商的栏目
	 * @return
	 */
	@RequestMapping(params = "index")
	public ModelAndView index(HttpServletRequest requestt) {
			TSUser user = ResourceUtil.getSessionUserName();
			String retailerId = null;
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
				retailerId = user.getId();
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				retailerId = user.getRetailerId();
			}
		    this.tContentCustomIndexService.insterCmsItems(retailerId);
			return new ModelAndView("com/buss/cms/tContentCustomIndexList");
	}

	
	/**
	 * 内容列表
	 */
	@RequestMapping(params = "cmsGrid")
	@ResponseBody
	public List<TreeGrid> cmsGrid(HttpServletRequest request, TreeGrid treegrid) {
		TSUser user = ResourceUtil.getSessionUserName();
		String retailerId = null;
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
			retailerId = user.getId();
		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
			retailerId = user.getRetailerId();
		}
		CriteriaQuery cq = new CriteriaQuery(TContentCategoryEntity.class);
			if (treegrid.getId() != null) {
				cq.eq("TContentCategoryEntity.id", Long.valueOf(treegrid.getId()));
			}
			if (treegrid.getId() == null) {
				cq.eq("TContentCategoryEntity.id",1l);//这个是最高级
			}
		cq.eq("status", GlobalConstants.STATUS_ACTIVE);
		cq.addOrder("sortOrder", SortDirection.asc);
		cq.add();
		List<TContentCategoryEntity> cmsList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("name");
		treeGridModel.setSrc("code");
		treeGridModel.setParentText("TContentCategoryEntity_name");
		treeGridModel.setParentId("TContentCategoryEntity_id");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("tscms");
		treeGridModel.setOrder("sortOrder");
		treeGridModel.setRemark("categoryType");
		treeGridModel.setSubTitle("subTitle");
		treeGrids = systemService.treegrid(cmsList, treeGridModel);
		if(!Utility.isEmpty(cmsList))
		{
			TContentCategoryEntity   cat = cmsList.get(0);
			//非目录
			if(!"ML".equals(cat.getCategoryType())){
				List<TContentCustomIndexEntity> indexList = this.systemService.findByProperty(TContentCustomIndexEntity.class,"retailerId", retailerId);
				if(!Utility.isEmpty(indexList))
				{
					for (TreeGrid treeGrid : treeGrids) {
						boolean isexist = false;
			 			for (TContentCustomIndexEntity item : indexList) {
			 					if(item.getContentId() == Long.valueOf(treeGrid.getId()) ){
			 						isexist= true;
			 						treeGrid.setOrder(item.getSortOrder()+"");
			 						treeGrid.setIsShow(item.getIsShow()+"");
			 						treeGrid.setSubTitle(Utility.isEmpty(item.getSubTitle())?"":item.getSubTitle()+"");
			 						break;
			 					}
			 			}
			 			if(!isexist){
			 				treeGrid.setIsShow("false");
			 			}
					}
				}else{
					for (TreeGrid treeGrid : treeGrids) {
						treeGrid.setIsShow("false");
					}
				}
			}
		}
		return treeGrids;
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
	public void datagrid(TContentCustomIndexEntity tContentCustomIndex,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TContentCustomIndexEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tContentCustomIndex, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tContentCustomIndexService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除零售商自定义栏目
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TContentCustomIndexEntity tContentCustomIndex, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tContentCustomIndex = systemService.flushEntity(TContentCustomIndexEntity.class, tContentCustomIndex.getId());
		message = "零售商自定义栏目删除成功";
		try{
			tContentCustomIndexService.delete(tContentCustomIndex);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商自定义栏目删除失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除零售商自定义栏目
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "零售商自定义栏目删除成功";
		try{
			for(String id:ids.split(",")){
				TContentCustomIndexEntity tContentCustomIndex = systemService.flushEntity(TContentCustomIndexEntity.class, 
				Integer.parseInt(id)
				);
				tContentCustomIndexService.delete(tContentCustomIndex);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商自定义栏目删除失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加零售商自定义栏目
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TContentCustomIndexEntity tContentCustomIndex, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商自定义栏目添加成功";
		try{
			tContentCustomIndexService.save(tContentCustomIndex);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "零售商自定义栏目添加失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新零售商自定义栏目
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TContentCustomIndexEntity tContentCustomIndex, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商自定义栏目更新成功";
		TContentCustomIndexEntity t = tContentCustomIndexService.get(TContentCustomIndexEntity.class, tContentCustomIndex.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tContentCustomIndex, t);
			tContentCustomIndexService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "零售商自定义栏目更新失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}

	/**
	 * 零售商自定义栏目编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdateOrder")
	public ModelAndView goUpdateOrder(@RequestParam(value = "id") Long contentId, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(contentId)) {
			CriteriaQuery cq = new CriteriaQuery(TContentCustomIndexEntity.class);
			cq.eq("contentId", contentId);
			cq.eq("retailerId", ResourceUtil.getRetailerId());
			cq.add();
			List<TContentCustomIndexEntity> indexList = systemService.getListByCriteriaQuery(cq, false);
			if(!Utility.isEmpty(indexList)){
				req.setAttribute("tContentCustomIndexPage", indexList.get(0));
			}
		}
		return new ModelAndView("com/buss/cms/tContentCustomIndex");
	}
	
	/**
	 * 零售商子标题编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdateSubTitle")
	public ModelAndView goUpdateSubTitle(@RequestParam(value = "id") Long contentId, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(contentId)) {
			CriteriaQuery cq = new CriteriaQuery(TContentCustomIndexEntity.class);
			cq.eq("contentId", contentId);
			cq.eq("retailerId", ResourceUtil.getRetailerId());
			cq.add();
			List<TContentCustomIndexEntity> indexList = systemService.getListByCriteriaQuery(cq, false);
			if(!Utility.isEmpty(indexList)){
				req.setAttribute("tContentCustomIndexPage", indexList.get(0));
			}
		}
		return new ModelAndView("com/buss/cms/tContentCustomSubTitle");
	}
	/**
	 * 更新零售商子标题
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdateSubTitle")
	@ResponseBody
	public AjaxJson doUpdateSubTitle(TContentCustomIndexEntity tContentCustomIndex, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "零售商自定义栏目更新成功";
		TContentCustomIndexEntity t = tContentCustomIndexService.get(TContentCustomIndexEntity.class, tContentCustomIndex.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tContentCustomIndex, t);
			tContentCustomIndexService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "零售商自定义栏目更新失败";
			throw new BusinessException(e.getMessage());
		}
		tContentCustomIndexService.clearHomePageRedis();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 是否显示
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "show")
	@ResponseBody
	public AjaxJson show(HttpServletRequest request,@RequestParam(value = "id") Long contentId,@RequestParam(value = "isShow") Boolean isShow) {
		 AjaxJson j = new AjaxJson();
		 message = "无内容修改";
		 if (StringUtil.isNotEmpty(contentId)) {
				CriteriaQuery cq = new CriteriaQuery(TContentCustomIndexEntity.class);
				cq.eq("contentId", contentId);
				cq.eq("retailerId", ResourceUtil.getRetailerId());
				cq.add();
				List<TContentCustomIndexEntity> indexList = systemService.getListByCriteriaQuery(cq, false);
				if(!Utility.isEmpty(indexList)){
					 TContentCustomIndexEntity  tContentCustomIndex =   indexList.get(0);
					 tContentCustomIndex.setIsShow(isShow);
					 this.tContentCustomIndexService.updateEntitie(tContentCustomIndex);
					 message = "内容"+(isShow?"已隐藏":"已显示");
					 systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
				}
		 }
		 tContentCustomIndexService.clearHomePageRedis();
         j.setMsg(message);
		 return j;
	}

	/**
	 * 零售商栏目设置内容页面
	 * @return
	 */
	@RequestMapping(params = "contentList")
	public ModelAndView contentList(HttpServletRequest request) {
			String categoryType = request.getParameter("categoryType");
			String contentId = request.getParameter("contentId");
			request.setAttribute("content_Id", contentId);
			if("SPHD".equals(categoryType)){//真选私惠  零售商活动列表
				String rId = ResourceUtil.getRetailerId();
				request.setAttribute("rId", rId);
				return new ModelAndView("com/buss/base/baseActivityListOfRetailer2");
			}else if("ZX".equals(categoryType)){//时节热点资讯，4篇不含商品资讯，产品资讯，达人说资讯
				return new ModelAndView("com/buss/cms/tContentNewsList");
			}else if("FLZXSP".equals(categoryType)){//资讯分类商品
				return new ModelAndView("com/buss/cms/tContentNewsTypeList");
			}else if("SP".equals(categoryType)){//当红人气，多多益善促销，推荐，当季热选,一键齐全,推荐
				return new ModelAndView("com/buss/cms/tHotGoodsList");
			}else if("CJ".equals(categoryType)){//场景
				return new ModelAndView("com/buss/cms/tContentSceneList");
			}
			return new ModelAndView("com/buss/cms/tContentCustomIndexList");
	}
	
	/**
	 * 零售商商品类栏目设置内容资讯（当红人气/热销商品,一键齐全,推荐）
	 * @return
	 */
	@RequestMapping(params = "contentNewsList")
	public ModelAndView contentNewsList(HttpServletRequest request) {
		String contentId = request.getParameter("contentId");
		request.setAttribute("content_Id", contentId);
		return new ModelAndView("com/buss/cms/tContentNewsList");
	}
}
