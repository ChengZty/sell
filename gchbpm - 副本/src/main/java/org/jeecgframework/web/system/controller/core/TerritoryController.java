package org.jeecgframework.web.system.controller.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSTerritory;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.vo.AreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import common.GlobalConstants;


/**
 * 地域处理类
 * @author wushu
 */
@Scope("prototype")
@Controller
@RequestMapping("/territoryController")
public class TerritoryController extends BaseController {
	
	private String message = null;

	@Autowired
	private SystemService systemService;
	
	/**
	 * 地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "territory")
	public ModelAndView function() {
		return new ModelAndView("system/territory/territoryList");
	}

	
	/**
	 * 地域列表
	 */
	@RequestMapping(params = "territoryGrid")
	@ResponseBody
	public List<TreeGrid> territoryGrid(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
			if (treegrid.getId() != null) {
				cq.eq("TSTerritory.id", treegrid.getId());
			}
			if (treegrid.getId() == null) {
				cq.eq("TSTerritory.id","1");//这个是全国最高级
			}
		cq.eq("status", GlobalConstants.STATUS_ACTIVE);
		cq.addOrder("territorySort", SortDirection.asc);
		cq.add();
		List<TSTerritory> territoryList = systemService.getListByCriteriaQuery(cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIcon("");
		treeGridModel.setTextField("territoryName");
		treeGridModel.setParentText("TSTerritory_territoryName");
		treeGridModel.setParentId("TSTerritory_id");
		treeGridModel.setSrc("territoryCode");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("TSTerritorys");
		treeGridModel.setOrder("territorySort");
		treeGridModel.setIsShow("isShow");
		treeGridModel.setRemark("remark");
		treeGrids = systemService.treegrid(territoryList, treeGridModel);
		return treeGrids;
	}
	/**
	 *地域列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSTerritory territory, HttpServletRequest req) {
		String functionid = req.getParameter("id");
		if (functionid != null) {
			territory = systemService.flushEntity(TSTerritory.class, functionid);
			req.setAttribute("territory", territory);
		}
		if(territory.getTSTerritory()!=null && territory.getTSTerritory().getId()!=null){
			territory.setTSTerritory((TSTerritory)systemService.flushEntity(TSTerritory.class, territory.getTSTerritory().getId()));
			req.setAttribute("territory", territory);
		}
		return new ModelAndView("system/territory/territory");
	}
	
	/**
	 * 获取省下面所有的市
	 */
	@RequestMapping(params = "getCitys")
	@ResponseBody
	public AjaxJson getCitys(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("areaId");
//		String userType = request.getParameter("userType");//后台录入用户选择地域会出现该参数（如果为零售商的时候需要过滤掉已经录入过的馆）
		List<AreaVo> list = new ArrayList<AreaVo>();
		List<TSTerritory> territoryList = null;
//		if("02".equals(userType)){
//			String sql = "select id,territoryname from t_s_territory where territoryparentid = ? and id not in (SELECT DISTINCT(city_id) from t_s_user where user_type = '02')";
//			List<Map<String, Object>> mapList = systemService.findForJdbc(sql, id);
//			if(mapList.size()>0){
//				for(Map<String, Object> map : mapList){
//					AreaVo vo = new AreaVo();
//					vo.setAreaId(map.get("id")+"");
//					vo.setAreaName(map.get("territoryname")+"");
//					list.add(vo);
//				}
//			}
//		}else{
			TSTerritory territory = systemService.get(TSTerritory.class, id);
			territoryList = systemService.findByProperty(TSTerritory.class, "TSTerritory", territory);
			if(territoryList!=null){
				for(TSTerritory ter:territoryList){
					AreaVo vo = new AreaVo();
					vo.setAreaId(ter.getId());
					vo.setAreaName(ter.getTerritoryName());
					list.add(vo);
				}
			}
//		}
		
		j.setObj(list);
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 地域父级下拉菜单
	 */
	@RequestMapping(params = "setPTerritory")
	@ResponseBody
	public List<ComboTree> setPTerritory(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		if (comboTree.getId() != null) {
			cq.eq("TSTerritory.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSTerritory");
		}
		cq.eq("status", GlobalConstants.STATUS_ACTIVE);
		cq.add();
		List<TSTerritory> territoryList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "territoryName", "TSTerritorys");
		comboTrees = systemService.ComboTree(territoryList, comboTreeModel, null, false);
		return comboTrees;
	}
	/**
	 * 地域保存
	 */
	@RequestMapping(params = "saveTerritory")
	@ResponseBody
	public AjaxJson saveTerritory(TSTerritory territory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String functionOrder = territory.getTerritorySort();
		if(StringUtils.isEmpty(functionOrder)){
			territory.setTerritorySort("0");
		}
		if (territory.getTSTerritory().getId().equals("")) {
			TSTerritory parent = systemService.flushEntity(TSTerritory.class, "1");
			territory.setTerritoryLevel((short)(parent.getTerritoryLevel()+1));
			territory.setTSTerritory(parent);
		}else{
			if((short)0==territory.getTerritoryLevel()){//一级地域
				territory.setTerritoryLevel((short)2);
				TSTerritory parent = systemService.flushEntity(TSTerritory.class, "1");
				territory.setTerritoryLevel((short)(parent.getTerritoryLevel()+1));
				territory.setTSTerritory(parent);
			}else{//下级地域
				TSTerritory parent = systemService.flushEntity(TSTerritory.class, territory.getTSTerritory().getId());
				territory.setTerritoryLevel((short)(parent.getTerritoryLevel()+1));
			}
		}
		if (StringUtil.isNotEmpty(territory.getId())) {
			message = "地域: " + territory.getTerritoryName() + "被更新成功";
			systemService.saveOrUpdate(territory);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
//			territory.setTerritorySort(territory.getTerritorySort());
			message = "地域: " + territory.getTerritoryName() + "被添加成功";
			if((short)2==territory.getTerritoryLevel()){//添加省，默认为不显示
				territory.setIsShow("N");
			}else if(territory.getTerritoryLevel()>(short)2){//添加市，默认为显示
				territory.setIsShow("Y");
				//对应的父级省也为显示
				systemService.updateBySqlString("update t_s_territory set is_show = 'Y' where id ='"+ territory.getTSTerritory().getId()+"'");
			}
			systemService.save(territory);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }

        j.setMsg(message);
		
		return j;
	}

	/**
	 * 地域删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSTerritory territory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		territory = systemService.flushEntity(TSTerritory.class, territory.getId());
		message = "地域: " + territory.getTerritoryName() + "被删除成功";
		territory.setStatus(GlobalConstants.STATUS_INACTIVE);
		territory.setIsShow("N");
		systemService.updateEntitie(territory);
		if(!Utility.isEmpty(territory.getTSTerritory())){//有父菜单
			String sql = "SELECT count(1) from t_s_territory where territoryparentid = '"+territory.getTSTerritory().getId()+"' and status = 'A' ";
			Long childrenCount = this.systemService.getCountForJdbc(sql);
			if(0==childrenCount){//没有子菜单了，父菜单也不显示
				String updateSql = "update t_s_territory set is_show = 'N' where id = '"+territory.getTSTerritory().getId()+"'";
				systemService.updateBySqlString(updateSql);
			}
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        j.setMsg(message);
		return j;
	}

}
