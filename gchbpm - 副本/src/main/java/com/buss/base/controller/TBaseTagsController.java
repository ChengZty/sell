package com.buss.base.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.redis.service.RedisService;

import com.buss.base.entity.TBaseTagsEntity;
import com.buss.base.service.TBaseTagsServiceI;



/**   
 * @Title: Controller
 * @Description: 标签
 * @author onlineGenerator
 * @date 2016-12-24 15:07:53
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBaseTagsController")
public class TBaseTagsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBaseTagsController.class);

	@Autowired
	private TBaseTagsServiceI tBaseTagsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RedisService redisService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 标签列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/base/tBaseTagsList");
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
	public void datagrid(TBaseTagsEntity tBaseTags,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBaseTagsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBaseTags, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
			
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBaseTagsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除标签
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "标签删除成功";
		TBaseTagsEntity tBaseTags = systemService.flushEntity(TBaseTagsEntity.class,id);
		try{
			tBaseTags.setStatus("I");
			tBaseTagsService.updateEntitie(tBaseTags);
//			TSUser user = ResourceUtil.getSessionUserName();
//			StringBuffer sql = new StringBuffer("update t_base_tags set update_by = '").append(user.getUserName()).append("',update_name = '")
//			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id = '")
//			.append(id).append("'");
//			tBaseTagsService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "标签删除失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{
			//更新标签的总个数
			checkTagCount(tBaseTags,"D");
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除标签
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "标签删除成功";
		List<TBaseTagsEntity> tagList = new ArrayList<TBaseTagsEntity>();
		try{
			for(String id:ids.split(",")){
				TBaseTagsEntity tBaseTags = systemService.flushEntity(TBaseTagsEntity.class,id);
				tagList.add(tBaseTags);
				tBaseTags.setStatus("I");
				tBaseTagsService.updateEntitie(tBaseTags);
			}
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//			TSUser user = ResourceUtil.getSessionUserName();
//			StringBuffer sql = new StringBuffer("update t_base_tags set update_by = '").append(user.getUserName()).append("',update_name = '")
//			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id in (");
//			for(String id:ids.split(",")){
//				sql.append("'").append(id).append("',");
//			}
//			sql = sql.deleteCharAt(sql.length()-1).append(")");
//			tBaseTagsService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "标签删除失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{
			for(TBaseTagsEntity tBaseTags: tagList){
				//更新问题的总个数
				checkTagCount(tBaseTags,"D");
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加标签
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBaseTagsEntity tBaseTags, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "标签添加成功";
		try{
			tBaseTags.setTagCode(this.generateCode());
			tBaseTags.setTagValues(StringUtil.replaceBlank(tBaseTags.getTagValues()));
			tBaseTagsService.save(tBaseTags);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "标签添加失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{
			//更新reids标签的总个数
			if(TBaseTagsEntity.VALID_Y.equals(tBaseTags.getValid())){
				this.checkTagCount(tBaseTags,"A");
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新标签
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBaseTagsEntity tBaseTags, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "标签更新成功";
		TBaseTagsEntity t = tBaseTagsService.get(TBaseTagsEntity.class, tBaseTags.getId());
		String oldValid = t.getValid();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBaseTags, t);
			t.setTagValues(StringUtil.replaceBlank(tBaseTags.getTagValues()));
			tBaseTagsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "标签更新失败";
			throw new BusinessException(e.getMessage());
		}
		if(!tBaseTags.getValid().equals(oldValid)){//修改了是否可用状态
			if(TBaseTagsEntity.VALID_N.equals(t.getValid())){
				//更新标签的总个数
				this.checkTagCount(t,"D");
			}else if(TBaseTagsEntity.VALID_Y.equals(t.getValid())){
				//更新标签的总个数
				this.checkTagCount(t,"A");
			}
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 停用标签
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doStop")
	@ResponseBody
	public AjaxJson doStop(TBaseTagsEntity tBaseTags, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "标签停用成功";
		try{
			TBaseTagsEntity t = tBaseTagsService.get(TBaseTagsEntity.class, tBaseTags.getId());
			if(TBaseTagsEntity.VALID_Y.equals(t.getValid())){
				t.setValid(TBaseTagsEntity.VALID_N);
				tBaseTagsService.updateEntitie(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}else{
				message = "请刷新列表后再操作";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "标签停用失败";
			throw new BusinessException(e.getMessage());
		}
		try
		{
			//更新标签的总个数
			this.checkTagCount(tBaseTags,"D");
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 获取最大的code+1的结果
	 */
	private String generateCode() {
		Map<String, Object> map = this.systemService.findOneForJdbc("SELECT CONCAT(MAX(tag_code+1),'') tagCode from t_base_tags where `status` = 'A'", null);
		Object tagCode = map.get("tagCode");
		String result = "1";
		if(Utility.isNotEmpty(tagCode)){
			result = tagCode+"";
		}
		return result;
	}

	/**
	 * 标签新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBaseTagsEntity tBaseTags, HttpServletRequest req) {
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isEmpty(retailerId)){
			retailerId = "admin";
		}
		req.setAttribute("retailerId", retailerId);
		return new ModelAndView("com/buss/base/tBaseTags-add");
	}
	/**
	 * 标签编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBaseTagsEntity tBaseTags, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBaseTags.getId())) {
			tBaseTags = tBaseTagsService.flushEntity(TBaseTagsEntity.class, tBaseTags.getId());
			req.setAttribute("tBaseTagsPage", tBaseTags);
		}
		return new ModelAndView("com/buss/base/tBaseTags-update");
	}
	
	/**检查并更新redis顾客标签对应阶段的个数（排除公司自定义标签的个数）
	 * @param questionStage
	 * @param type A:新增，D:删除
	 */
	private void checkTagCount(TBaseTagsEntity tBaseTags,String type) {
		if(Utility.isNotEmpty(tBaseTags.getTagStage())&&!TBaseTagsEntity.TAG_STAGE_4.equals(tBaseTags.getTagStage())
				&&TBaseTagsEntity.TO_USER_TYPE_CUST.equals(tBaseTags.getToUserType())){
			String count = redisService.get(common.GlobalConstants.TAG_COUNT+"_"+tBaseTags.getTagStage());
			if(StringUtil.isEmpty(count)||count=="0"){
				Long num = this.systemService.getCountForJdbc("select count(1) from t_base_tags where status = 'A' and to_user_type = '"+TBaseTagsEntity.TO_USER_TYPE_CUST
						+"' and tag_stage ='"+tBaseTags.getTagStage()+"'");
				redisService.set(common.GlobalConstants.TAG_COUNT+"_"+tBaseTags.getTagStage(),num+"");
			}else{
				if(type=="A"){
					redisService.incr(common.GlobalConstants.TAG_COUNT+"_"+tBaseTags.getTagStage());
				}else if(type=="D"){
					redisService.decr(common.GlobalConstants.TAG_COUNT+"_"+tBaseTags.getTagStage());
				}
			}
		}
		
	}
}
