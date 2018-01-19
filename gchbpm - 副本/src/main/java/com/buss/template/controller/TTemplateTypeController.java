package com.buss.template.controller;

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
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
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
@RequestMapping("/tTemplateTypeController")
public class TTemplateTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TTemplateTypeController.class);

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
	 * 模板分类管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "templateTypeList")
	public ModelAndView category(HttpServletRequest request) {
		return new ModelAndView("com/buss/template/templateTypeList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	@ResponseBody
	public List<TreeGrid> datagrid(TTemplateTypeEntity templateType,HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid) {
		//是否是零售商登录
		String retailerId = ResourceUtil.getRetailerId();
		//获取父分类ID
		String parentId = templateType.getId();

		//返回树形结构
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIdField("id");
		treeGridModel.setSrc("level");
		treeGridModel.setCode("code");
		treeGridModel.setTextField("name");
		treeGridModel.setRemark("templateType");
		treeGridModel.setParentText("parent_name");
		treeGridModel.setParentId("parent_id");
		treeGridModel.setChildList("list");
//		treeGridModel.setSubTitle("smallPic");
		
		//获取话术分类的二级分类，就是行业信息
		if(TTemplateTypeEntity.WORDS_TYPE_1.equals(parentId)){//商品话术
			List<Map<String,Object>> tradeMap = null;  //行业列表
			String sqlTrade = "";
			//获取零售商所属行业
			if(StringUtil.isNotEmpty(retailerId)){
				sqlTrade = "select tsc.id id,tsc.level level,tsc.code code,tsc.name name,'1' templateType from t_s_category tsc LEFT JOIN t_s_trade_user tstu "+
						"on tsc.id=tstu.trade_id where tsc.status='A' and tstu.status='A' and level='2' and tsc.retailer_id='admin' "+
						" and tstu.user_id='"+retailerId+"'";
			}else{
				sqlTrade = "select  id,level,code,name,'1' templateType from t_s_category where status='A' and level='2' and retailer_id='admin' ";
			}
			tradeMap = systemService.findForJdbc(sqlTrade, null);
			TTemplateTypeEntity parent = systemService.findUniqueByProperty(TTemplateTypeEntity.class, "id", TTemplateTypeEntity.WORDS_TYPE_1);

			List<TTemplateTypeEntity> list = new ArrayList<TTemplateTypeEntity>();
			for (Map<String,Object> map : tradeMap) {
				TTemplateTypeEntity tTemplateTypeEntity = new TTemplateTypeEntity();
				tTemplateTypeEntity.setId(map.get("id").toString());
				tTemplateTypeEntity.setLevel(map.get("level").toString());
				tTemplateTypeEntity.setCode(map.get("code").toString());
				tTemplateTypeEntity.setName(map.get("name").toString());
				tTemplateTypeEntity.setTemplateType(map.get("templateType").toString());
				tTemplateTypeEntity.setParent(parent);
				List<TTemplateTypeEntity> subType = systemService.findByProperty(TTemplateTypeEntity.class, "parent.id", map.get("id").toString());
				tTemplateTypeEntity.setList(subType);
				list.add(tTemplateTypeEntity);
			}
			
			treeGrids = systemService.treegrid(list, treeGridModel);
		}else{//顾客话术
			CriteriaQuery cq = new CriteriaQuery(TTemplateTypeEntity.class, dataGrid);
			if (parentId == null || StringUtils.isEmpty(parentId)) {
				cq.isNull("parent");
			} else {
				cq.eq("parent.id", parentId);
				templateType.setId(null);
			}
			if (StringUtil.isNotEmpty(retailerId)) {
				cq.eq("retailerId", "admin");
			}else{
				cq.in("retailerId", new String[]{"admin",retailerId});
			}

			// 查询条件组装器
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,templateType, request.getParameterMap());
			List<TTemplateTypeEntity> list = systemService.getListByCriteriaQuery(cq, false);
			treeGrids = systemService.treegrid(list, treeGridModel);
		}
		
		return treeGrids;
	}

	/**
	 * 顾客话术类别新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TTemplateTypeEntity templateType, HttpServletRequest req,@RequestParam(value = "pid", defaultValue = "1") String pid) {
		String tradeId = req.getParameter("tradeId");
		
		if(TTemplateTypeEntity.WORDS_TYPE_1.equals(tradeId)){ //商品话术
			TSCategoryEntity parent = this.systemService.flushEntity(TSCategoryEntity.class, pid);
			TTemplateTypeEntity tTemplateTypeEntity = new TTemplateTypeEntity();
			tTemplateTypeEntity.setId(parent.getId());
			tTemplateTypeEntity.setLevel(parent.getLevel()+"");
			tTemplateTypeEntity.setName(parent.getName());
			tTemplateTypeEntity.setTemplateType("1");   //话术类型
			tTemplateTypeEntity.setPlatformType("1");   //g+添加
			req.setAttribute("parent", tTemplateTypeEntity);
			return new ModelAndView("com/buss/template/templateType-add");
		}else{
			TTemplateTypeEntity  parent = this.systemService.flushEntity(TTemplateTypeEntity.class, pid);
			req.setAttribute("parent", parent);
			//判断模板类型
			if(TTemplateTypeEntity.TEMPLATE_TYPE_1.equals(parent.getTemplateType())){//话术类型
				return new ModelAndView("com/buss/template/templateType-add");
			}else {//话题类型
				req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
				String retailerCode = ResourceUtil.getRetailerCode(systemService);
				req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
				return new ModelAndView("com/buss/template/templateNewsType-add");
			}
		}
	}
	/**
	 * 顾客话术类别编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TTemplateTypeEntity templateType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(templateType.getId())) {
			templateType = systemService.flushEntity(TTemplateTypeEntity.class, templateType.getId());
			req.setAttribute("templateType", templateType);
			if(TTemplateTypeEntity.TEMPLATE_TYPE_1.equals(templateType.getTemplateType()) && "3".equals(templateType.getLevel())){ //商品话术
				String sqlTrade = "select id,level,code,name,'1' templateType from t_s_category where status='A' and level='2' and id='"+templateType.getParent().getId()+"' ";
				Map<String,Object> map = systemService.findOneForJdbc(sqlTrade, null);
				if(Utility.isNotEmpty(map)){  //存在就是商品话术
					String parentName = map.get("name").toString();
					req.setAttribute("parentName", parentName);

					return new ModelAndView("com/buss/template/templateTypeGoods-update");
				}
			}
		}
		req.setAttribute("load", req.getParameter("load"));//是否是查看
		//判断模板类型
		if(TTemplateTypeEntity.TEMPLATE_TYPE_1.equals(templateType.getTemplateType())){//话术类型
			return new ModelAndView("com/buss/template/templateType-update");
		}else {//话题类型
			return new ModelAndView("com/buss/template/templateNewsType-update");
		}
	}
	
	/**
	 * 添加顾客话术类别
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TTemplateTypeEntity templateType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "模板分类添加成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isEmpty(retailerId)){
				retailerId = "admin";
			}
			templateType.setRetailerId(retailerId);
				systemService.save(templateType);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "模板分类添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新顾客话术类别
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TTemplateTypeEntity templateType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "模板分类更新成功";
		TTemplateTypeEntity t = systemService.get(TTemplateTypeEntity.class, templateType.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(templateType, t);
			systemService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "模板分类更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新顾客话术类别
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdateGoods")
	@ResponseBody
	public AjaxJson doUpdateGoods(TTemplateTypeEntity templateType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "模板分类更新成功";
		String sql="update t_template_type set name='"+templateType.getName()+"' where id='"+templateType.getId()+"'";
		try {
			systemService.updateBySqlString(sql);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "模板分类更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 删除顾客话术类别(包括下面一级的，暂时未做迭代删除)
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TTemplateTypeEntity templateType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		templateType = systemService.flushEntity(TTemplateTypeEntity.class, templateType.getId());
		message = "模板分类删除成功";
		if(templateType.getList().size() == 0){//没有子分类，直接删除
			try{
				templateType.setStatus("I");
				systemService.updateEntitie(templateType);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
				message = "模板分类删除失败";
				throw new BusinessException(e.getMessage());
			}
		}else{
			message = "该分类下还有子分类，不能删除";
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 根据父ID取出子分类
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "getSubTypeList")
	@ResponseBody
	public AjaxJson getSubList(String pid) {
		AjaxJson j = new AjaxJson();		
		//是否是零售商登录
		String retailerId = ResourceUtil.getRetailerId();
		List<TTemplateTypeEntity> typeList = new ArrayList<TTemplateTypeEntity>();
		if(TTemplateTypeEntity.WORDS_TYPE_1.equals(pid)){
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
			TTemplateTypeEntity parent = systemService.findUniqueByProperty(TTemplateTypeEntity.class, "id", TTemplateTypeEntity.WORDS_TYPE_1);

//			List<TTemplateTypeEntity> list = new ArrayList<TTemplateTypeEntity>();
			for (Map<String,Object> map : tradeMap) {
				TTemplateTypeEntity tTemplateTypeEntity = new TTemplateTypeEntity();
				tTemplateTypeEntity.setId(map.get("id").toString());
				tTemplateTypeEntity.setName(map.get("name").toString());
				tTemplateTypeEntity.setLevel(map.get("level").toString());
				typeList.add(tTemplateTypeEntity);
			}
		}else{
			typeList = systemService.findByProperty(TTemplateTypeEntity.class, "parent.id", pid);
//			typeList = systemService.findByQueryString("from TTemplateTypeEntity where parent.id = '"+pid+"'");
			for (TTemplateTypeEntity tTemplateTypeEntity : typeList) {
				tTemplateTypeEntity.setList(null);
				tTemplateTypeEntity.setParent(null);
			}
		}
		if(typeList.size() > 0){
			TTemplateTypeEntity templateType= typeList.get(0);
			j.setMsg(templateType.getLevel());
			j.setObj(typeList);
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
		}
		
		return j;
	}
}
