package com.buss.shop.controller;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.shop.entity.TPosterEntity;
import com.buss.shop.service.TPosterServiceI;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 海报
 * @author onlineGenerator
 * @date 2017-03-03 12:35:27
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPosterController")
public class TPosterController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPosterController.class);

	@Autowired
	private TPosterServiceI tPosterService;
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
	 * 海报列表 页面跳转  (四种状态)
	 * @return
	 */
	@RequestMapping(params = "tPoster")
	public ModelAndView tPoster(HttpServletRequest request) {
		String postStatus = request.getParameter("postStatus");//海报状态
		if(StringUtil.isNotEmpty(postStatus)){
			request.setAttribute("postStatus", postStatus);
		}
		ResourceBundle env = ResourceBundle.getBundle("env");
		String url = env.getObject("CST_REQUEST_PRE_URL")+"tPosterController.do?viewPoster&rId="+ResourceUtil.getRetailerId()+"&id=";
		request.setAttribute("url", url);
		return new ModelAndView("com/buss/shop/tPosterList");
	}
	
	/**
	 * 海报TAB列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tPosterTabs")
	public ModelAndView tPosterTabs(HttpServletRequest req) {
		return new ModelAndView("com/buss/shop/tPosterTabs");
	}
	
	/**自定义查询列表
	 * @param request
	 * @param response
	 * @param grid
	 * @return
	 */
	@RequestMapping(params = "mydatagrid")
	@ResponseBody
	public DataGridJson mydatagrid(HttpServletRequest request, HttpServletResponse response,DataGridJson grid) {
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();//零售商id
			String fields = "id,title,cover_pic";
			StringBuffer sqlCount = new StringBuffer("select count(1) from t_poster where status = 'A' ");
			sqlCount.append(" and post_status = '3'")//上架中的
			.append(" and retailer_id = '").append(retailerId).append("'");
			String keywords = request.getParameter("keywords");
			if(Utility.isNotEmpty(keywords)){
				sqlCount.append(" and title like '%").append(keywords).append("%'");
			}
			Long total = systemService.getCountForJdbc(sqlCount.toString());
			List list = systemService.findForJdbc(sqlCount.toString().replace("count(1)", fields), grid.getPage(), grid.getRows());
			DataGridJson.setDataGrid(grid,list,total.intValue());
		}catch (Exception e) {
			e.printStackTrace();
			grid.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		return grid;
	}
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TPosterEntity tPoster,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPosterEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPoster, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();//零售商id
			String postStatus = request.getParameter("post_Status");//海报状态
			if(StringUtil.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
			if(StringUtil.isNotEmpty(postStatus)){
				cq.eq("postStatus", postStatus);
			}
			cq.addOrder("sortNum", SortDirection.desc);
//			cq.addOrder("updateDate", SortDirection.desc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPosterService.getDataGridReturn(cq, true);
		List<TPosterEntity> list = dataGrid.getResults();
		if(Utility.isNotEmpty(list)){
			for(TPosterEntity entity : list){
				entity.setCoverPic(entity.getCoverPic()+"?imageView2/1/w/130/h/80");
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据查询 状态为已上架或者已完成的数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridByStatus")
	public void datagridByStatus(TPosterEntity tPoster,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPosterEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPoster, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();//零售商id
			String[] str = {TPosterEntity.POST_STATUS_2,TPosterEntity.POST_STATUS_3};//海报状态，已上架或已完成的
			if(StringUtil.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
			cq.in("postStatus", str);
			cq.addOrder("createDate", SortDirection.desc);
			cq.addOrder("updateDate", SortDirection.desc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPosterService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 海报查看页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "viewPoster")
	public ModelAndView viewPoster(TPosterEntity tPoster, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPoster.getId())) {
			tPoster = tPosterService.flushEntity(TPosterEntity.class, tPoster.getId());
			req.setAttribute("content", tPoster.getContextHtml());
		}
		return new ModelAndView("com/buss/shop/tPoster-view");
	}
	
	/**
	 * 海报预览
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TPosterEntity tPoster, HttpServletRequest req) {
		req.setAttribute("content", tPoster.getContextHtml());
		return new ModelAndView("com/buss/shop/tPoster-view");
	}
	
	/**
	 * 删除海报
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPosterEntity tPoster, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPoster = systemService.flushEntity(TPosterEntity.class, tPoster.getId());
		message = "海报删除成功";
		try{
			StringBuilder sb = new StringBuilder();
			sb.append(" update t_poster set status='").append(GlobalConstants.STATUS_INACTIVE).append("'")
			.append(" where id='").append(tPoster.getId()).append("'");
			systemService.updateBySqlString(sb.toString());
			
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "海报删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除海报
	 * @return
	 */
//	 @RequestMapping(params = "doBatchDel")
//	@ResponseBody
//	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
//		AjaxJson j = new AjaxJson();
//		message = "海报删除成功";
//		try{
//			for(String id:ids.split(",")){
//				TPosterEntity tPoster = systemService.flushEntity(TPosterEntity.class, 
//				id
//				);
//				tPosterService.delete(tPoster);
//				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "海报删除失败";
//			throw new BusinessException(e.getMessage());
//		}
//		j.setMsg(message);
//		return j;
//	}


	/**
	 * 添加海报
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPosterEntity tPoster, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "海报添加成功";
		try{
			Long sortNum = Long.valueOf(DateUtils.date2Str(DateUtils.getTimestamp(), DateUtils.yyyymmddhhmmss));
			tPoster.setSortNum(sortNum);
			tPosterService.save(tPoster);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "海报添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	

	/**
	 * 海报新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TPosterEntity tPoster, HttpServletRequest req) {
		req.setAttribute("domain", GlobalConstants.DOMAIN);
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode
		if (StringUtil.isNotEmpty(tPoster.getId())) {
			tPoster = tPosterService.flushEntity(TPosterEntity.class, tPoster.getId());
			
			req.setAttribute("tPosterPage", tPoster);
		}
		return new ModelAndView("com/buss/shop/tPoster-add");
	}
	/**
	 * 海报编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPosterEntity tPoster, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPoster.getId())) {
			tPoster = tPosterService.flushEntity(TPosterEntity.class, tPoster.getId());
			req.setAttribute("tPosterPage", tPoster);
		}
		return new ModelAndView("com/buss/shop/tPoster-update");
	}
	
	
	/**
	 * 海报上架
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUp")
	@ResponseBody
	public AjaxJson doUp(TPosterEntity tPoster, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "海报上架成功";
		try{
			StringBuilder sb = new StringBuilder();
			sb.append(" update t_poster set post_status='").append(TPosterEntity.POST_STATUS_3).append("'")
			.append(" where id='").append(tPoster.getId()).append("'");
			systemService.updateBySqlString(sb.toString());
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "海报上架失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 海报下架
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doDown")
	@ResponseBody
	public AjaxJson doDown(TPosterEntity tPoster, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "海报下架成功";
		try{
			StringBuilder sb = new StringBuilder();
			sb.append(" update t_poster set post_status='").append(TPosterEntity.POST_STATUS_4).append("'")
			.append(" where id='").append(tPoster.getId()).append("'");
			systemService.updateBySqlString(sb.toString());
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "海报下架失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 获取全部海报列表（上架）
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "getAllPosterList")
	public AjaxJson getAllPosterList(String rId,HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		try {
			List<TPosterEntity> posterList = tPosterService.findByQueryString("from TPosterEntity where postStatus = '3' and retailerId = '"+rId+"'");
			j.setObj(posterList);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		return j;
	}
	
	/**
	 * 批量下架
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doBatchDown")
	@ResponseBody
	public AjaxJson doBatchDown(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "批量下架成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_poster set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',post_status = '")
			.append(TPosterEntity.POST_STATUS_4).append("' where id in (");
			for(String id:ids.split(",")){
				sql.append("'").append(id).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(")");
			systemService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "批量下架失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}	
	
	/**
	 * 选择图片列表
	 * @return
	 */
	@RequestMapping(params = "toChoosePicList")
	public ModelAndView toChoosePicList(HttpServletRequest request) {
		return new ModelAndView("com/dagger/choosePicture");
	}
	
	
	/**
	 * 
	 * 保存海报(保存到草稿箱)
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPosterEntity tPoster, HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		AjaxJson j = new AjaxJson();
		message = "海报信息添加成功";
		try{
			tPoster.setId(Utility.isEmpty(tPoster.getId())?null:tPoster.getId());
			tPoster.setRetailerId(retailerId);
			tPoster.setStatus(GlobalConstants.STATUS_ACTIVE);
			tPoster.setPostStatus(TPosterEntity.POST_STATUS_1);//草稿箱
			if(Utility.isEmpty(tPoster.getId())){
				tPoster.setCreateDate(Utility.getCurrentTimestamp());
			}
			Long sortNum = Long.valueOf(DateUtils.date2Str(DateUtils.getTimestamp(), DateUtils.yyyymmddhhmmss));
			tPoster.setSortNum(sortNum);
			tPosterService.saveOrUpdate(tPoster);
			j.setObj(tPoster.getId());//传入页面(第一次点击保存草稿箱执行save操作,第二次保存草稿箱执行update操作)
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "海报信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 
	 * 保存海报(点击完成)
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doFinish")
	@ResponseBody
	public AjaxJson doFinish(TPosterEntity tPoster, HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		AjaxJson j = new AjaxJson();
		message = "海报添加成功";
		try{
			tPoster.setId(Utility.isEmpty(tPoster.getId())?null:tPoster.getId());
			tPoster.setRetailerId(retailerId);
			if(Utility.isEmpty(tPoster.getId())){
				tPoster.setCreateDate(Utility.getCurrentTimestamp());
			}
			tPoster.setStatus(GlobalConstants.STATUS_ACTIVE);
			tPoster.setPostStatus(TPosterEntity.POST_STATUS_2);//已完成
			Long sortNum = Long.valueOf(DateUtils.date2Str(DateUtils.getTimestamp(), DateUtils.yyyymmddhhmmss));
			tPoster.setSortNum(sortNum);
			tPosterService.saveOrUpdate(tPoster);
			j.setObj(tPoster.getId());//传入页面(第一次点击完成执行save操作,第二次完成执行update操作)
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "海报添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 海报置顶
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doToTop")
	@ResponseBody
	public AjaxJson doToTop(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "置顶成功";
		try{
			Timestamp time = DateUtils.gettimestamp();
			String sortNum = DateUtils.date2Str(time, DateUtils.yyyymmddhhmmss);
			systemService.updateBySqlString("update t_poster set update_date = '"+time+"',sort_num = '"+sortNum+"' where id ='"+id+"'");
//			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "置顶失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 海报排序
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doChangeSort")
	@ResponseBody
	public AjaxJson doChangeSort(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		try{
			String id = request.getParameter("id");
			String type = request.getParameter("type");//类型
			String sortNum = request.getParameter("sortNum");//当前排序
			String postStatus = request.getParameter("postStatus");//状态
			if("U".equals(type)){//上移
				message = "上移成功";
			}else if("D".equals(type)){
				message = "下移成功";
			}
			this.tPosterService.doChangeSort(id,type,sortNum,postStatus);
		}catch(Exception e){
			e.printStackTrace();
			message = "排序失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	
	
	
}
