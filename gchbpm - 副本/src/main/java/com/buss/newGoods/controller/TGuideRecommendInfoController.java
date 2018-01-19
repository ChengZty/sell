package com.buss.newGoods.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.newGoods.entity.TGuideRecommendDetailEntity;
import com.buss.newGoods.entity.TGuideRecommendInfoEntity;
import com.buss.newGoods.service.TGuideRecommendInfoServiceI;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 管家点评
 * @author onlineGenerator
 * @date 2016-11-03 14:11:55
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGuideRecommendInfoController")
public class TGuideRecommendInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGuideRecommendInfoController.class);

	@Autowired
	private TGuideRecommendInfoServiceI tGuideRecommendInfoService;
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
	 * 管家点评列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("goods_Id", request.getParameter("goods_Id"));
		request.setAttribute("retailer_Id", request.getParameter("retailer_Id"));
		return new ModelAndView("com/buss/newGoods/tGuideRecommendInfoList");
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
	public void datagrid(TGuideRecommendInfoEntity tGuideRecommendInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TGuideRecommendInfoEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGuideRecommendInfo, request.getParameterMap());
		try{
		//自定义追加查询条件
			int total = 0;
			String goodsId = request.getParameter("goods_Id");
			String guideName = request.getParameter("guideName");
			String sql ="select i.id,ifnull(i.description,'') description,i.read_num as readNum,u.realname as guideName,i.flag"
				+" from t_guide_recommend_info i left join t_s_user u on i.guide_id = u.id  where i.status = 'A' ";
			String countSql = "select count(1) from t_guide_recommend_info i left join t_s_user u on i.guide_id = u.id  where i.status = 'A'";
			if(!Utility.isEmpty(goodsId)){
				sql +=" and i.goods_id = '"+goodsId+"'";
				countSql +=" and i.goods_id = '"+goodsId+"'";
			}
			if(!Utility.isEmpty(guideName)){
				sql +=" and u.realname like '%"+guideName+"%'";
				countSql +=" and u.realname like '%"+guideName+"%'";
			}
			sql+=" order by i.create_date desc";
			total = this.systemService.getCountForJdbc(countSql).intValue();
			List<Map<String, Object>> resultList =  new ArrayList<Map<String,Object>>();
			if(total>0){
				resultList = systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
//		cq.add();
//		this.tGuideRecommendInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除管家点评
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TGuideRecommendInfoEntity tGuideRecommendInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
//		tGuideRecommendInfo = systemService.flushEntity(TGuideRecommendInfoEntity.class, tGuideRecommendInfo.getId());
		message = "管家点评删除成功";
		try{
			tGuideRecommendInfoService.deleteRecommendInfo(tGuideRecommendInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "管家点评删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除管家点评
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "管家点评删除成功";
		try{
			for(String id:ids.split(",")){
				TGuideRecommendInfoEntity tGuideRecommendInfo = systemService.flushEntity(TGuideRecommendInfoEntity.class, 	id	);
				tGuideRecommendInfoService.deleteRecommendInfo(tGuideRecommendInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "管家点评删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加管家点评
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TGuideRecommendInfoEntity tGuideRecommendInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "管家点评添加成功";
		try{
			tGuideRecommendInfoService.saveRecommendInfo(tGuideRecommendInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "管家点评添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新管家点评
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TGuideRecommendInfoEntity tGuideRecommendInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "管家点评更新成功";
		TGuideRecommendInfoEntity t = tGuideRecommendInfoService.get(TGuideRecommendInfoEntity.class, tGuideRecommendInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tGuideRecommendInfo, t);
			tGuideRecommendInfoService.updateRecommendInfo(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "管家点评更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 管家点评新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TGuideRecommendInfoEntity tGuideRecommendInfo, HttpServletRequest req) {
		req.setAttribute("retailer_Id", req.getParameter("retailer_Id"));
		req.setAttribute("goods_Id", req.getParameter("goods_Id"));
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return new ModelAndView("com/buss/newGoods/tGuideRecommendInfo-add");
	}
	/**
	 * 管家点评编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TGuideRecommendInfoEntity tGuideRecommendInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGuideRecommendInfo.getId())) {
			tGuideRecommendInfo = tGuideRecommendInfoService.flushEntity(TGuideRecommendInfoEntity.class, tGuideRecommendInfo.getId());
			if(Utility.isNotEmpty(tGuideRecommendInfo)){
				if(Utility.isNotEmpty(tGuideRecommendInfo.getGuideId())){
					TSUser guide = this.systemService.get(TSUser.class, tGuideRecommendInfo.getGuideId());
					req.setAttribute("guideName", guide.getRealName());
				}
				List<TGuideRecommendDetailEntity> recommendDetailsList = this.systemService.findByProperty(TGuideRecommendDetailEntity.class, "recommendId", tGuideRecommendInfo.getId());
				req.setAttribute("recommendDetailsList", recommendDetailsList);
				req.setAttribute("tGuideRecommendInfoPage", tGuideRecommendInfo);
			}
			req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
			String retailerCode = ResourceUtil.getRetailerCode(systemService);
			req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		}
		return new ModelAndView("com/buss/newGoods/tGuideRecommendInfo-update");
	}
	/**
	 * 管家点评编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TGuideRecommendInfoEntity tGuideRecommendInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGuideRecommendInfo.getId())) {
			tGuideRecommendInfo = tGuideRecommendInfoService.flushEntity(TGuideRecommendInfoEntity.class, tGuideRecommendInfo.getId());
			if(Utility.isNotEmpty(tGuideRecommendInfo)){
				if(Utility.isNotEmpty(tGuideRecommendInfo.getGuideId())){
					TSUser guide = this.systemService.get(TSUser.class, tGuideRecommendInfo.getGuideId());
					req.setAttribute("guideName", guide.getRealName());
				}
				List<TGuideRecommendDetailEntity> recommendDetailsList = this.systemService.findByProperty(TGuideRecommendDetailEntity.class, "recommendId", tGuideRecommendInfo.getId());
				req.setAttribute("recommendDetailsList", recommendDetailsList);
				req.setAttribute("tGuideRecommendInfoPage", tGuideRecommendInfo);
			}
		}
		return new ModelAndView("com/buss/newGoods/tGuideRecommendInfo-view");
	}
	

}
