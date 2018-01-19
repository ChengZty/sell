package com.buss.words.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.words.entity.TCustWordsTypeEntity;
import com.buss.words.service.TCustWordsTypeServiceI;



/**   
 * @Title: Controller
 * @Description: 顾客话术类别
 * @author onlineGenerator
 * @date 2017-02-10 14:31:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tCustWordsTypeController")
public class TCustWordsTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TCustWordsTypeController.class);

	@Autowired
	private TCustWordsTypeServiceI tCustWordsTypeService;
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
	 * 顾客话术类别列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/words/tCustWordsTypeList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "treeGrid")
	@ResponseBody
	public List<TreeGrid> treeGrid(TCustWordsTypeEntity tCustWordsType,HttpServletRequest request, HttpServletResponse response, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(TCustWordsTypeEntity.class);
		if (treegrid.getId() != null) {
			cq.eq("parent.id", Integer.valueOf(treegrid.getId()));
		}
		if (treegrid.getId() == null) {
			cq.eq("level",1);//一级分类
			cq.addOrder("id", SortDirection.desc);
		}
		cq.add();
		List<TCustWordsTypeEntity> cmsList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIdField("id");
		treeGridModel.setSrc("level");
		treeGridModel.setTextField("name");
		treeGridModel.setRemark("hasPics");//是否有图片
		treeGridModel.setChildList("list");
//		treeGridModel.setParentText("parent_name");
//		treeGridModel.setParentId("parent_id");
		treeGrids = systemService.treegrid(cmsList, treeGridModel);
		return treeGrids;
	}

	/**
	 * 删除顾客话术类别(包括下面一级的，暂时未做迭代删除)
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TCustWordsTypeEntity tCustWordsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tCustWordsType = systemService.flushEntity(TCustWordsTypeEntity.class, tCustWordsType.getId());
		message = "顾客话术类别删除成功";
		try{
			tCustWordsTypeService.delType(tCustWordsType);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客话术类别删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除顾客话术类别
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "顾客话术类别删除成功";
		try{
			for(String id:ids.split(",")){
				TCustWordsTypeEntity tCustWordsType = systemService.flushEntity(TCustWordsTypeEntity.class, 
				id
				);
				tCustWordsType.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				tCustWordsTypeService.updateEntitie(tCustWordsType);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客话术类别删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加顾客话术类别
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TCustWordsTypeEntity tCustWordsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客话术类别添加成功";
		try{
//			if(tCustWordsType.getLevel()>2){
//				message = "顾客话术类别暂时不能添加到三级";
//			}else{
				tCustWordsTypeService.save(tCustWordsType);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//			}
		}catch(Exception e){
			e.printStackTrace();
			message = "顾客话术类别添加失败";
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
	public AjaxJson doUpdate(TCustWordsTypeEntity tCustWordsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客话术类别更新成功";
		TCustWordsTypeEntity t = tCustWordsTypeService.get(TCustWordsTypeEntity.class, tCustWordsType.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tCustWordsType, t);
			tCustWordsTypeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "顾客话术类别更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 顾客话术类别新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TCustWordsTypeEntity tCustWordsType, HttpServletRequest req,@RequestParam(value = "pid", defaultValue = "1") String pid) {
		TCustWordsTypeEntity parent = this.tCustWordsTypeService.get(TCustWordsTypeEntity.class, Integer.valueOf(pid));
		req.setAttribute("parent", parent);
		return new ModelAndView("com/buss/words/tCustWordsType-add");
	}
	/**
	 * 顾客话术类别编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TCustWordsTypeEntity tCustWordsType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tCustWordsType.getId())) {
			tCustWordsType = tCustWordsTypeService.flushEntity(TCustWordsTypeEntity.class, tCustWordsType.getId());
			req.setAttribute("tCustWordsTypePage", tCustWordsType);
		}
		return new ModelAndView("com/buss/words/tCustWordsType-update");
	}
	
	/**
	 * 根据父ID取出子分类
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "getSubList")
	@ResponseBody
	public List<TCustWordsTypeEntity> getSubList(String pid) {
		List<TCustWordsTypeEntity> typeList = systemService.findHql("from TCustWordsTypeEntity where parent.id = ?", Integer.valueOf(pid));
//		List<CommonVo> list = new ArrayList<CommonVo>();
//		if(typeList.size()>0)
//		{
//			for (TCustWordsTypeEntity o : typeList) {
//				CommonVo vo = new CommonVo();
//				vo.setId(o.getId()+"");
//				vo.setName(o.getName());
//				list.add(vo);
//			}	
//		}
//		return list;
		return typeList;
	}
}
