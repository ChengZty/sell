package com.buss.cms.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.cms.entity.TContentCategoryEntity;
import com.buss.cms.service.TContentCategoryServiceI;
import com.buss.cms.service.TContentCustomIndexServiceI;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 内容管理
 * @author onlineGenerator
 * @date 2016-09-22 22:13:54
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/cmsCategory")
public class TContentCategoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TContentCategoryController.class);

	@Autowired
	private TContentCategoryServiceI tContentCategoryService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TContentCustomIndexServiceI tContentCustomIndexService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 内容管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView tContentCategory(HttpServletRequest request) {
		return new ModelAndView("com/buss/cms/tContentCategoryList");
	}

	
    /**
     * 根据父节点id查询列表
     * 
     * @param parentId
     * @return
     */
    @RequestMapping(params = "queryListByParentId")
    @ResponseBody
    public List<TContentCategoryEntity> queryListByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        List<TContentCategoryEntity> list = this.tContentCategoryService.queryListByWhere(parentId);
        return list;
    }
	
	/**
	 * 内容列表
	 */
	@RequestMapping(params = "cmsGrid")
	@ResponseBody
	public List<TreeGrid> cmsGrid(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(TContentCategoryEntity.class);
			if (treegrid.getId() != null) {
				cq.eq("TContentCategoryEntity.id", Long.valueOf(treegrid.getId()));
			}
			if (treegrid.getId() == null) {
				cq.eq("TContentCategoryEntity.id",0l);//这个是最高级
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
		treeGrids = systemService.treegrid(cmsList, treeGridModel);
		return treeGrids;
	}
	
	/**
	 *内容列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TContentCategoryEntity tContentCategoryEntity, HttpServletRequest req,@RequestParam(value = "pid", defaultValue = "1") String pid) 
	{
			TContentCategoryEntity parent = this.tContentCategoryService.get(TContentCategoryEntity.class,Long.valueOf(pid));
			tContentCategoryEntity.setTContentCategoryEntity(parent);
			req.setAttribute("tContentCategoryPage", tContentCategoryEntity);
			return new ModelAndView("com/buss/cms/tContentCategory-add");
	}
    
	/**
	 *内容列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TContentCategoryEntity tContentCategoryEntity, HttpServletRequest req) {
		String functionid = req.getParameter("id");
		if (functionid != null) {
			tContentCategoryEntity = systemService.flushEntity(TContentCategoryEntity.class, Long.valueOf(functionid));
			req.setAttribute("tContentCategoryPage", tContentCategoryEntity);
		}
		return new ModelAndView("com/buss/cms/tContentCategory-update");
	}
	
	/**
	 * 内容保存
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TContentCategoryEntity tContentCategoryEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			message = "内容: " + tContentCategoryEntity.getName() + "新增成功";
			this.tContentCategoryService.myInsert(tContentCategoryEntity);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tContentCustomIndexService.clearHomePageRedis();
        j.setMsg(message);
		return j;
	}
	
	/**
	 * 内容更新
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TContentCategoryEntity tContentCategoryEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			message = "内容: " + tContentCategoryEntity.getName() + "更新成功";
			this.tContentCategoryService.updateEntitie(tContentCategoryEntity);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tContentCustomIndexService.clearHomePageRedis();
        j.setMsg(message);
		return j;
	}

	/**
	 * 内容删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TContentCategoryEntity tContentCategoryEntity, HttpServletRequest request) {
		 AjaxJson j = new AjaxJson();
		 try {
			tContentCategoryEntity = systemService.flushEntity(TContentCategoryEntity.class, tContentCategoryEntity.getId());
			 message = "内容: " + tContentCategoryEntity.getName() + "被删除成功";
			 List<Long> ids = new ArrayList<Long>();
			 ids.add(tContentCategoryEntity.getId());
			 findAllSubNode(tContentCategoryEntity.getId(), ids);
			 if(ids.size() > 0)
			 {
				 StringBuffer sql = new StringBuffer(" update t_content_category set status='I' where id in (");
			     for (Long id : ids) {
			      	 sql.append(id).append(",");
				 }
			     sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
			     this.systemService.executeSql(sql.toString());
			     systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         tContentCustomIndexService.clearHomePageRedis();
        j.setMsg(message);
		return j;
	}
	
	
	private void findAllSubNode(Long id, List<Long> ids) {
		List<TContentCategoryEntity> list = this.tContentCategoryService.queryListByWhere(id);
        for (TContentCategoryEntity tContentCategoryEntity : list) {
            ids.add(tContentCategoryEntity.getId());
            // 判断该节点是否为父节点，如果是，进行递归
            if (tContentCategoryEntity.getIsParent()) {
                findAllSubNode(tContentCategoryEntity.getId(), ids);
            }
        }
	}
}
