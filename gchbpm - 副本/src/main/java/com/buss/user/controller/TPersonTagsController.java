package com.buss.user.controller;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.service.CategoryServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.redis.service.RedisService;

import com.buss.user.entity.TPersonTagsEntity;
import com.buss.user.entity.TProfessionalEntity;
import com.buss.user.entity.TTagCategoriesEntity;
import com.buss.user.service.TPersonTagsServiceI;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 导购标签
 * @author onlineGenerator
 * @date 2016-04-18 16:38:48
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPersonTagsController")
public class TPersonTagsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPersonTagsController.class);

	@Autowired
	private TPersonTagsServiceI tPersonTagsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RedisService redisService;
	private String message;

	@Autowired
	private CategoryServiceI categoryService;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 导购标签列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("user_Id", request.getParameter("user_Id"));
		return new ModelAndView("com/buss/user/tPersonTagsList");
	}
	/**
	 * 零售商查看导购标签列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listOfRetailer")
	public ModelAndView listOfRetailer(HttpServletRequest request) {
		request.setAttribute("user_Id", request.getParameter("user_Id"));
		return new ModelAndView("com/buss/user/tPersonTagsList2");
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
	public void datagrid(TPersonTagsEntity tPersonTags,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPersonTagsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPersonTags, request.getParameterMap());
		try{
		//自定义追加查询条件
			cq.eq("userId", request.getParameter("user_Id"));
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPersonTagsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除导购标签
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPersonTagsEntity tPersonTags, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPersonTags = systemService.flushEntity(TPersonTagsEntity.class, tPersonTags.getId());
		message = "导购标签删除成功";
		try{
			tPersonTagsService.deletePersonTags(tPersonTags);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "导购标签删除失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{//更新帮手资讯小红点版本号
			redisService.incr(common.GlobalConstants.GUIDE_HELPER_VERSION);
		}catch (Exception e) {
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除导购标签
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "导购标签删除成功";
		try{
			for(String id:ids.split(",")){
				TPersonTagsEntity tPersonTags = systemService.flushEntity(TPersonTagsEntity.class, 
				id
				);
				tPersonTagsService.delete(tPersonTags);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "导购标签删除失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{//更新帮手资讯小红点版本号
			redisService.incr(common.GlobalConstants.GUIDE_HELPER_VERSION);
		}catch (Exception e) {
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加导购标签
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPersonTagsEntity tPersonTags, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购标签添加成功";
		try{
			tPersonTagsService.savePersonTags(tPersonTags);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "导购标签添加失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{//更新帮手资讯小红点版本号
			redisService.incr(common.GlobalConstants.GUIDE_HELPER_VERSION);
		}catch (Exception e) {
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新导购标签
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPersonTagsEntity tPersonTags, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购标签更新成功";
		TPersonTagsEntity t = tPersonTagsService.get(TPersonTagsEntity.class, tPersonTags.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPersonTags, t);
			tPersonTagsService.updatePersonTags(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "导购标签更新失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{//更新帮手资讯小红点版本号
			redisService.incr(common.GlobalConstants.GUIDE_HELPER_VERSION);
		}catch (Exception e) {
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 选择类目跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "categryList")
	public ModelAndView categryList(HttpServletRequest request) {
		String tagId = request.getParameter("tagId");
		request.setAttribute("tagId", tagId);
		return new ModelAndView("com/buss/user/categryList");
	}
	
	//取一级和二级类目
	@RequestMapping(params = "setVisibleCategs")
	@ResponseBody
	public List<ComboTree> setVisibleCategs(TSCategoryEntity category,HttpServletRequest request, ComboTree comboTree) {
		int level;
		String tagId = request.getParameter("tagId");//标签
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
		if (StringUtils.isNotEmpty(comboTree.getId())) {
			//cq.createAlias("parent", "parent");
			cq.eq("pid", comboTree.getId());
			category = this.categoryService.get(TSCategoryEntity.class, comboTree.getId());
			level = category.getLevel()+1;
		} else {
			level = 1;
			cq.isNull("pid");
		}
		cq.add();
		List<TSCategoryEntity> allList  = systemService.getListByCriteriaQuery(cq, false);
		//排序
//		Collections.sort(allList, new NumberComparator(false));
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		
		List<TSCategoryEntity> visiblelist = null;// 已选类目
		
		List<TTagCategoriesEntity> categryTaglist = null;
		if(StringUtil.isNotEmpty(tagId)){
			categryTaglist = systemService.findByProperty(TTagCategoriesEntity.class, "tagId",	tagId);
		}
		if (Utility.isNotEmpty(categryTaglist)) {
			visiblelist = new ArrayList<TSCategoryEntity>();
			if (categryTaglist.size() > 0) {
				for (TTagCategoriesEntity visibleCatg : categryTaglist) {
					TSCategoryEntity categry = this.categoryService.get(TSCategoryEntity.class, visibleCatg.getCategryId());
					visiblelist.add(categry);
				}
			}
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id","name", "list",level);
		comboTrees = systemService.comboTreeWithLevel(allList, comboTreeModel,visiblelist, false,2);
//		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
		
	}
	
	/**
	 * 导购标签新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TPersonTagsEntity tPersonTags, HttpServletRequest req) {
		req.setAttribute("user_id", req.getParameter("user_id"));
		String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
		List<TSCategoryEntity> categoryList = systemService.findListbySql(sql);
		req.setAttribute("categoryList", categoryList);
		return new ModelAndView("com/buss/user/tPersonTags-add");
	}
	/**
	 * 导购标签编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPersonTagsEntity tPersonTags, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPersonTags.getId())) {
			tPersonTags = tPersonTagsService.flushEntity(TPersonTagsEntity.class, tPersonTags.getId());
			req.setAttribute("tPersonTagsPage", tPersonTags);
			String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
			List<TSCategoryEntity> categoryList = systemService.findListbySql(sql);
			req.setAttribute("categoryList", categoryList);
			String hql ="from TProfessionalEntity where topCategoryId='"+tPersonTags.getTopCategoryId()+"' and helperType='"+tPersonTags.getTagCode()+"'";
			List<TProfessionalEntity> professionList = systemService.findByQueryString(hql);
			req.setAttribute("professionList", professionList);
		}
		return new ModelAndView("com/buss/user/tPersonTags-update");
	}
	
	/**
	 * 上传证书
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadPic", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadPic(TPersonTagsEntity tPersonTags,HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		FtpUtil ftpUtil = FtpUtil.getInstance();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();// 获取上传文件对象
			String fileName = mf.getOriginalFilename();// 获取文件名
			String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
			String myfilename="";
			String noextfilename="";//不带扩展名
			noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+StringUtil.random(8);//自定义文件名称
			myfilename = noextfilename+"."+extend;//自定义文件名称
			InputStream is = mf.getInputStream();
			ftpUtil.uploadFileToFtpServer(GlobalConstants.CERTIFICATE_PICS, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
							 +ResourceUtil.getConfigByName("imgRootPath")+"/"
							 +GlobalConstants.CERTIFICATE_PICS+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}
	
	/**
	 * 查询
	 * @return
	 */
	@RequestMapping(params = "checkTagNum")
	@ResponseBody
	public AjaxJson checkTagNum(HttpServletRequest request,HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		message = "";
		try{
			String userId = request.getParameter("userId");
			try {
				String sqlCount = "select count(1) from t_person_tags where user_id = '"+userId+"' and status = 'A'";
				Long num = this.systemService.getCountForJdbc(sqlCount);
				if(num>1){
					j.setSuccess(false);
					message = "最多只能添加2个标签";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			message = "查询失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
