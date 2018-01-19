package org.jeecgframework.web.system.controller.core;

import java.io.File;
import java.io.FileWriter;
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
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSIcon;
import org.jeecgframework.web.system.service.CategoryServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.vo.AreaVo;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.GlobalConstants;

/**
 * @Title: Controller
 * @Description: 分类管理
 * @author JueYue
 * @date 2014-09-16 21:50:55
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/categoryController")
public class CategoryController extends BaseController {
	private String message;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CategoryController.class);

	private static final String CATEGORY_LIST = "system/category/categoryList";//平台商品分类列表
	private static final String CATEGORY_LIST_RETAILER = "system/category/categoryListOfRet";//零售商商品分类列表
	private static final String CATEGORY_PARAMS_LIST = "com/buss/base/categoryParamsList";
	private static final String TOP_CATEGORY_PARAMS_LIST = "com/buss/base/topCategoryParamsList";
	private static final String CATEGORY_ADD_OR_UPDATE = "system/category/category";
	

	@Autowired
	private CategoryServiceI categoryService;

	@Autowired
	private SystemService systemService;

	/**
	 * 平台分类管理列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "category")
	public String category(HttpServletRequest request) {
		return CATEGORY_LIST;
	}
	
	/**
	 * 零售商分类管理列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "categoryOfRet")
	public String categoryOfRet(HttpServletRequest request) {
		request.setAttribute("rId", ResourceUtil.getRetailerId());
		return CATEGORY_LIST_RETAILER;
	}
	
	/**
	 *商品参数 
	 * @return
	 */
	@RequestMapping(params = "categoryParams")
	public String categoryParams(HttpServletRequest request) {
		return CATEGORY_PARAMS_LIST;
	}
	/**
	 *商品参数 
	 * @return
	 */
	@RequestMapping(params = "topCategoryParams")
	public String topCategoryParams(HttpServletRequest request) {
		return TOP_CATEGORY_PARAMS_LIST;
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
	public List<TreeGrid> datagrid(TSCategoryEntity category,HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class, dataGrid);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		try {
			List<Map<String,Object>> tradeMap = null;  //行业ID列表
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isEmpty(retailerId)){
				cq.eq("retailerId", "admin");//查询平台的分类
			}else{
				String[] str = {"admin",retailerId};//查询平台和零售商自己的分类
				cq.in("retailerId", str);
				//获取零售商所属行业
				String sqlTrade = "select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"'";
				tradeMap = systemService.findForJdbc(sqlTrade, null);
			}
			if (category.getId() == null || StringUtils.isEmpty(category.getId())) {//第一次进入，初始化界面
				if(Utility.isNotEmpty(retailerId)){ //零售商请求，拼接行业id使用in 过滤
					if(Utility.isNotEmpty(tradeMap)){
						List<Object> keyvalue = new ArrayList<Object>();
						for (Map<String, Object> map : tradeMap) {
							keyvalue.add(map.get("trade_id"));
						}
						cq.in("id", keyvalue.toArray());
					}
				}else{//平台查询
					cq.isNull("parent");
				}
			} else {
				cq.eq("parent.id", category.getId());
				category.setId(null);
			}
			cq.addOrder("sort", SortDirection.asc);
			cq.add();
			// 查询条件组装器
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,category, request.getParameterMap());
			List<TSCategoryEntity> list = this.categoryService.getListByCriteriaQuery(cq, false);
			TreeGridModel treeGridModel = new TreeGridModel();
			treeGridModel.setIdField("id");
			treeGridModel.setSrc("code");
			treeGridModel.setTextField("name");
			treeGridModel.setIcon("imgUrl");
			treeGridModel.setOrder("level");
			treeGridModel.setParentText("parent_name");
			treeGridModel.setParentId("parent_code");
			treeGridModel.setRemark("retailerId");
			treeGridModel.setChildList("list");
			treeGrids = systemService.treegrid(list, treeGridModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treeGrids;
	}
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagridList")
	@ResponseBody
	public List<TreeGrid> datagridList(TSCategoryEntity category,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class, dataGrid);
		if (category.getId() == null || StringUtils.isEmpty(category.getId())) {
			cq.isNull("parent");
		} else {
			cq.eq("parent.code", category.getId());
			category.setId(null);
		}
		cq.addOrder("sort", SortDirection.asc);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				category, request.getParameterMap());
		List<TSCategoryEntity> list = this.categoryService
		.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIdField("code");
		treeGridModel.setSrc("id");
		treeGridModel.setTextField("name");
		treeGridModel.setIcon("icon_iconPath");
		treeGridModel.setParentText("parent_name");
		treeGridModel.setParentId("parent_code");
		treeGridModel.setChildList("list");
		treeGridModel.setOrder("level");
		treeGrids = systemService.treegrid(list, treeGridModel);
		return treeGrids;
	}
	
	/**
	 * easyui 一级类目（产品参数信息）
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "topDatagridList")
	public void topDatagridList(TSCategoryEntity category,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class, dataGrid);
		try{
			//自定义追加查询条件
			cq.eq("level", 2);
			cq.addOrder("sort", SortDirection.asc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "gettopDatagridList")
	@ResponseBody
	public AjaxJson  gettopDatagridList(HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
		AjaxJson j = new AjaxJson();
		List<AreaVo> list = new ArrayList<AreaVo>();
		List<TSCategoryEntity> categoryList = systemService.findByProperty(TSCategoryEntity.class, "level", 1);
		if(categoryList!=null){
			for(TSCategoryEntity cat:categoryList){
				AreaVo vo = new AreaVo();
				vo.setAreaId(cat.getId());
				vo.setAreaName(cat.getName());
				list.add(vo);
			}
		}
		request.setAttribute("catList", list);
		j.setObj(list);
		return j;
	}
	

	/**
	 * 删除分类管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSCategoryEntity tSCategory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSCategory = systemService.flushEntity(TSCategoryEntity.class,	tSCategory.getId());
		if(Utility.isEmpty(tSCategory.getList())){
			j.setMsg("分类管理删除成功");
			tSCategory.setStatus(common.GlobalConstants.STATUS_INACTIVE);
			categoryService.updateEntitie(tSCategory);
		}else{
			j.setSuccess(false);
			j.setMsg("请先删除下级分类");
		}
		categoryService.clearCategoryRedis();
		systemService.addLog(j.getMsg(), Globals.Log_Type_DEL,Globals.Log_Leavel_INFO);
		return j;
	}

	/**
	 * 添加分类管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TSCategoryEntity category, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String retailerId = ResourceUtil.getRetailerId();
		if (StringUtil.isEmpty(category.getParent().getId())){
			category.setLevel(1);
			category.setParent(null);
		}else{
			TSCategoryEntity parent = categoryService.get(TSCategoryEntity.class,category.getParent().getId());
			category.setLevel(parent.getLevel()+1);
			category.setParent(parent);
		}
		if (StringUtil.isNotEmpty(category.getId())) {//修改
			j.setMsg("分类管理更新成功");
			TSCategoryEntity t = categoryService.flushEntity(TSCategoryEntity.class,category.getId());
			try 
			{
				MyBeanUtils.copyBeanNotNull2Bean(category, t);
				categoryService.saveOrUpdate(t);
				systemService.addLog(j.getMsg(), Globals.Log_Type_UPDATE,Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error(e.getMessage(), e.fillInStackTrace());
				j.setMsg("分类管理更新失败");
			}
		} else {//新增
			String sql = "select count(1) from t_s_category where status='A' and parent_id='"+category.getParent().getId()+
					"' and name='"+category.getName()+"' and retailer_id in ('admin','"+retailerId+"')";
			int total = this.systemService.getCountForJdbc(sql).intValue();
			if(total > 0){
				j.setMsg("商品分类已存在");
			}else{
				categoryService.saveCategory(category);
				systemService.addLog(j.getMsg(), Globals.Log_Type_INSERT,Globals.Log_Leavel_INFO);
				j.setMsg("分类管理添加成功");
			}
		}
		return j;
	}

	/**
	 * 分类管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public String addorupdate(ModelMap map, TSCategoryEntity category,HttpServletRequest req) {
		if (StringUtil.isNotEmpty(category.getId())) {
			category = categoryService.get(TSCategoryEntity.class,category.getId());
			map.put("categoryPage", category);
		}
		map.put("iconlist", systemService.findByProperty(TSIcon.class,"iconType", (short) 1));
		if (category.getParent() != null
				&& StringUtil.isNotEmpty(category.getParent().getId())) {
			TSCategoryEntity parent = categoryService.get(TSCategoryEntity.class, category.getParent().getId());
			category.setParent(parent);
			map.put("categoryPage", category);
		}
//		req.setAttribute("domain", domain);//七牛上传 域名
//		String retailerCode = ResourceUtil.getRetailerCode(systemService);
//		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return CATEGORY_ADD_OR_UPDATE;
	}

	@RequestMapping(params = "combotree")
	@ResponseBody
	public List<ComboTree> combotree(ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
		if (StringUtils.isNotEmpty(comboTree.getId())) {
			cq.eq("parent.id", comboTree.getId());
		} else {
			cq.isNull("parent");
		}
		cq.add();
		List<TSCategoryEntity> categoryList = systemService	.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "name", "list");
		comboTrees = systemService.ComboTree(categoryList, comboTreeModel,	null, false);
//		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
	}

	//取一级和二级类目
	@RequestMapping(params = "setVisibleCategs")
	@ResponseBody
	public List<ComboTree> setVisibleCategs(TSCategoryEntity category,HttpServletRequest request, ComboTree comboTree) {
		int level;
//		String goodsId = request.getParameter("goodsId");//可见类目
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
		List<Map<String,Object>> tradeMap = null;   //分配的行业ID
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isEmpty(retailerId)){
			cq.eq("retailerId", "admin");//查询平台的分类
		}else{
			String[] str = {"admin",retailerId};//查询平台和零售商自己的分类
			cq.in("retailerId", str);
//			cq.eq("retailerId", "admin");//查询平台的分类
			//获取行业ID
			String sqlTrade = "select trade_id from t_s_trade_user where status='A' and user_id='"+retailerId+"'";
			tradeMap = systemService.findForJdbc(sqlTrade, null);
		}
		if (StringUtils.isNotEmpty(comboTree.getId())) {
			cq.eq("parent.id", comboTree.getId());
			category = this.categoryService.get(TSCategoryEntity.class, comboTree.getId());
			level = category.getLevel()+1;
		} else {  //第一次请求，初始化界面
			if(Utility.isNotEmpty(tradeMap)){//零售商请求
				level = 2;
				//拼接行业id使用in 加入查询
				List<Object> keyvalue = new ArrayList<Object>();
				for (Map<String, Object> map : tradeMap) {
					keyvalue.add(map.get("trade_id"));
				}
				cq.in("id", keyvalue.toArray());
			}else{ //平台请求
				level = 1;
				cq.isNull("parent.id");
			}
		}
		
		cq.addOrder("sort",SortDirection.asc );
		cq.add();
		List<TSCategoryEntity> allList  = systemService.getListByCriteriaQuery(cq, false);
		//排序
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		
		List<TSCategoryEntity> visiblelist = null;// 已可见类目
//		
//		List<TVisibleCategriesEntity> categryGoodslist = null;
//		if(StringUtil.isNotEmpty(goodsId)){
//			categryGoodslist = systemService.findByProperty(TVisibleCategriesEntity.class, "goodsId",	goodsId);
//		}
//		visiblelist = new ArrayList<TSCategoryEntity>();
//		if (Utility.isNotEmpty(categryGoodslist)) {
//			if (categryGoodslist.size() > 0) {
//				HashSet<String> set = new HashSet<String>();
//				for (TVisibleCategriesEntity visibleCatg : categryGoodslist) {
//					set.add(visibleCatg.getTopCategoryId());
//					set.add(visibleCatg.getSubCategoryId());
//				}
//				for (String s : set) {
//					TSCategoryEntity categry = this.categoryService.get(TSCategoryEntity.class, s);
//					visiblelist.add(categry);
//				}
//			}
//		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id","name", "list",level);
		comboTrees = systemService.comboTreeWithLevel(allList, comboTreeModel,visiblelist, false,3);
//		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
		
	}
	
	/**
	 * 鉴于树的问题,这里自己加载全部数据来做同步树
	 * 
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "tree")
	@ResponseBody
	public List<ComboTree> tree(String selfCode,ComboTree comboTree, boolean isNew) {
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
		if (StringUtils.isNotEmpty(comboTree.getId())) {
			cq.createAlias("parent", "parent");
			cq.eq("parent.code", comboTree.getId());
		} else if (StringUtils.isNotEmpty(selfCode)) {
			cq.eq("code", selfCode);
		} else {
			cq.isNull("parent");
		}
		cq.eq("status", GlobalConstants.STATUS_ACTIVE);
		cq.add();
		List<TSCategoryEntity> categoryList = systemService
				.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		for (int i = 0; i < categoryList.size(); i++) {
			comboTrees.add(categoryConvertToTree(categoryList.get(i)));
		}
		return comboTrees;
	}

	private ComboTree categoryConvertToTree(TSCategoryEntity entity) {
		ComboTree tree = new ComboTree();
		tree.setId(entity.getCode());
		tree.setText(entity.getName());
		tree.setIconCls(entity.getIcon().getIconClas());
		if (entity.getList() != null && entity.getList().size() > 0) {
			List<ComboTree> comboTrees = new ArrayList<ComboTree>();
			for (int i = 0; i < entity.getList().size(); i++) {
				comboTrees.add(categoryConvertToTree(entity.getList().get(
						i)));
			}
			tree.setChildren(comboTrees);
		}
		return tree;
	}
	
	
	/**
	 * 上传图片
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(params = "saveOrUpdatePic", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveOrUpdatePic(TSCategoryEntity category,HttpServletRequest request) throws Exception {
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
			ftpUtil.uploadFileToFtpServer(GlobalConstants.CATEGORY_DIR, myfilename, is);
			j.setMsg(ResourceUtil.getConfigByName("imgWWW")+"/"+ResourceUtil.getConfigByName("imgRootPath")+"/"+GlobalConstants.CATEGORY_DIR+"/"+myfilename);
		}
		return j;
	}	*/
	
	/**
	 * 添加图标样式
	 * 
	 * @param request
	 * @param css
	 */
	protected void write(HttpServletRequest request, String css) {
		try {
			String path = request.getSession().getServletContext().getRealPath("/plug-in/accordion/css/icons.css");
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter out = new FileWriter(file, true);
			out.write("\r\n");
			out.write(css);
			out.close();
		} catch (Exception e) {
		}
	}
	
	/**
	 * 根据父ID取出
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "getSubList")
	@ResponseBody
	public List<CommonVo> getSubList(String pid) {
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
		cq.eq("parent.id", pid);
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isEmpty(retailerId)){
			cq.eq("retailerId", "admin");//查询平台的分类
		}else{
			String[] str = {"admin",retailerId};//查询平台和零售商自己的分类
			cq.in("retailerId", str);
		}
		cq.add();
		List<TSCategoryEntity> categoryList = systemService.getListByCriteriaQuery(cq, false);
		List<CommonVo> list = new ArrayList<CommonVo>();
		if(null != categoryList)
		{
			for (TSCategoryEntity o : categoryList) {
				CommonVo vo = new CommonVo();
				vo.setId(o.getId());
				vo.setName(o.getName());
				list.add(vo);
			}	
		}
		return list;
	}
	
	
	/**
	 * 获取下级分类（二级，三级分类）
	 */
	@RequestMapping(params = "getUnderCategories")
	@ResponseBody
	public AjaxJson getUnderCategories(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		List<AreaVo> list = new ArrayList<AreaVo>();
		String parentId = request.getParameter("parentId");
		String retailerId = ResourceUtil.getRetailerId();
		String hql = "from TSCategoryEntity where parent.id = ? and retailerId in ('admin','"+retailerId+"') order by sort asc";
		List<TSCategoryEntity> categoryList = systemService.findHql(hql,parentId);
//		List<TSCategoryEntity> categoryList = systemService.findByProperty(TSCategoryEntity.class, "parent.id", parentId);
		if(categoryList!=null){
			for(TSCategoryEntity cat:categoryList){
				AreaVo vo = new AreaVo();
				vo.setAreaId(cat.getId());
				vo.setAreaName(cat.getName());
				list.add(vo);
			}
		}
		j.setObj(list);
		return j;
	}
	
	//取一级和二级类目
	@RequestMapping(params = "subCategoryList")
	@ResponseBody
	public List<ComboTree> subCategoryList(TSCategoryEntity category,HttpServletRequest request, ComboTree comboTree) {
		int level;
//		String goodsId = request.getParameter("goodsId");//可见类目
		CriteriaQuery cq = new CriteriaQuery(TSCategoryEntity.class);
		if (StringUtils.isNotEmpty(comboTree.getId())) {
			//cq.createAlias("parent", "parent");
			cq.eq("parent.id", comboTree.getId());
			category = this.categoryService.get(TSCategoryEntity.class, comboTree.getId());
			level = category.getLevel()+1;
		} else {
			level = 1;
		}
		cq.eq("status", GlobalConstants.STATUS_ACTIVE);
		cq.addOrder("sort",SortDirection.asc );
		cq.add();
		List<TSCategoryEntity> allList  = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id","name", "list",level);
		comboTrees = systemService.comboTreeWithLevelOfCategory(allList, comboTreeModel,null, false,2);
//		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
		
	}
	
	
}
