package com.buss.words.controller;
import java.util.List;

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
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.goods.entity.TGoodsEntity;
import com.buss.template.entity.TTemplateTypeEntity;
import com.buss.words.entity.TGoodsWordsEntity;
import com.buss.words.service.TGoodsWordsServiceI;



/**   
 * @Title: Controller
 * @Description: 商品话术
 * @author onlineGenerator
 * @date 2017-02-10 17:47:52
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGoodsWordsController")
public class TGoodsWordsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGoodsWordsController.class);

	@Autowired
	private TGoodsWordsServiceI tGoodsWordsService;
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
	 * 平台资讯分类tab页
	 * @return
	 */
	@RequestMapping(params = "tGoodsWordsTabs")
	public ModelAndView tGoodsWordsTabs(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/words/tGoodsWordsTabs");
	}
	
	/**
	 * 平台商品话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "platformList")
	public ModelAndView platformList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
		List<TSCategoryEntity> topCategoryList = systemService.findListbySql(sql);
		request.setAttribute("topCategoryList", topCategoryList);
		return new ModelAndView("com/buss/words/tGoodsWordsList");
	}
	
	/**
	 * 零售商商品话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public ModelAndView retailerList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
		List<TSCategoryEntity> topCategoryList = systemService.findListbySql(sql);
		request.setAttribute("topCategoryList", topCategoryList);
		return new ModelAndView("com/buss/words/tGoodsWordsListOfRetailer");
	}

	/**
	 * 零售商活动话术包括可见话术列表 页面跳转  （选择商品话术）
	 * @return
	 */
	@RequestMapping(params = "retailerVisibleList")
	public ModelAndView retailerVisibleList(HttpServletRequest request,String finActId) {
		//获取分类信息
		String hql = "FROM TTemplateTypeEntity WHERE status='A' and parent.id='101' and level='2' and platformType='1' and templateType='1' ";
		List<TTemplateTypeEntity> subTypeList = systemService.findByQueryString(hql);

		request.setAttribute("subTypeList", subTypeList);
		request.setAttribute("finActId", finActId);
		return new ModelAndView("com/buss/words/tGoodsWordsListOfRetailerVisible");
	}
	
	/**
	 * 零售商活动话术包括可见话术列表 页面跳转  （选择商品话术）
	 * @return
	 */
	@RequestMapping(params = "goodsWordsForGoods")
	public ModelAndView goodsWordsForGoods(HttpServletRequest request) {
		String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
		List<TSCategoryEntity> topCategoryList = systemService.findListbySql(sql);
		request.setAttribute("topCategoryList", topCategoryList);
		return new ModelAndView("com/buss/words/tGoodsWordsForGoods");
	}
	
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TGoodsWordsEntity tGoodsWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,
			@RequestParam(value = "tp") String platformType) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsWordsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoodsWords, request.getParameterMap());
		try{
		//自定义追加查询条件
			if(common.GlobalConstants.PLATFORM_TYPE_2.equals(platformType)){
				String retailerId = ResourceUtil.getRetailerId();
				if(StringUtil.isNotEmpty(retailerId)){
					cq.eq("retailerId", retailerId);
				}
			}
			cq.eq("platformType", platformType);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGoodsWordsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui 零售商通过活动选择活动话术列表（包括可见话术）
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param platformType
	 */
	
	@RequestMapping(params = "retailerVisibleDatagrid")
	public void retailerVisibleDatagrid(TGoodsWordsEntity tGoodsWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsWordsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoodsWords, request.getParameterMap());
		try{
			//自定义追加查询条件
			String finActId = request.getParameter("finActId");//活动ID
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sqlstr = new StringBuffer(" (retailer_id = '").append(retailerId).append("'  OR id in (SELECT v.words_id from t_visible_words v where v.`status` = 'A' and v.retailer_id = '")
				.append(retailerId).append("' and v.words_type='2'))")//可见商品话术
				.append(" and id not in (SELECT f.words_id from t_fin_activity_words f where f.`status` = 'A' and f.words_id is not null and f.fin_act_id = '").append(finActId).append("')");
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoodsWords, request.getParameterMap(), sqlstr.toString());
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGoodsWordsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除商品话术
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TGoodsWordsEntity tGoodsWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tGoodsWords = systemService.flushEntity(TGoodsWordsEntity.class, tGoodsWords.getId());
		message = "商品话术删除成功";
		try{
			tGoodsWords.setStatus("I");
			tGoodsWordsService.updateEntitie(tGoodsWords);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除商品话术
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "商品话术删除成功";
		try{
			for(String id:ids.split(",")){
				TGoodsWordsEntity tGoodsWords = systemService.flushEntity(TGoodsWordsEntity.class, id);
				tGoodsWords.setStatus("I");
				tGoodsWordsService.updateEntitie(tGoodsWords);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "商品话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加商品话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TGoodsWordsEntity tGoodsWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品话术添加成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			String platformType = common.GlobalConstants.PLATFORM_TYPE_2;
			if(StringUtil.isEmpty(retailerId)){
				retailerId = "admin";
				platformType = common.GlobalConstants.PLATFORM_TYPE_1;
			}
			tGoodsWords.setPlatformType(platformType);
			tGoodsWords.setRetailerId(retailerId);
			tGoodsWordsService.save(tGoodsWords);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品话术添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新商品话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TGoodsWordsEntity tGoodsWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品话术更新成功";
		TGoodsWordsEntity t = tGoodsWordsService.get(TGoodsWordsEntity.class, tGoodsWords.getId());
		try {
			MyBeanUtils.copyBean2Bean(t, tGoodsWords);
			tGoodsWordsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品话术更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 商品话术新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TGoodsWordsEntity tGoodsWords, HttpServletRequest req) {
		String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
		List<TSCategoryEntity> topCategoryList = systemService.findListbySql(sql);
		req.setAttribute("topCategoryList", topCategoryList);
		return new ModelAndView("com/buss/words/tGoodsWords-add");
	}
	/**
	 * 商品话术编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TGoodsWordsEntity tGoodsWords, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tGoodsWords.getId())) {
			tGoodsWords = tGoodsWordsService.flushEntity(TGoodsWordsEntity.class, tGoodsWords.getId());
			String sql = "select id,name from t_s_category where status = 'A' and level = 1 and parent_code is null order by sort asc";
			List<TSCategoryEntity> topCategoryList = systemService.findListbySql(sql);
			
			List<TSCategoryEntity> subCategoryList = null;
			List<TSCategoryEntity> thirdCategoryList = null;
			if(StringUtil.isNotEmpty(tGoodsWords.getTopCategoryId())){
				subCategoryList = this.systemService.findByProperty(TSCategoryEntity.class, "pid", tGoodsWords.getTopCategoryId());
			}
			if(StringUtil.isNotEmpty(tGoodsWords.getSubCategoryId())){
				thirdCategoryList = this.systemService.findByProperty(TSCategoryEntity.class, "pid", tGoodsWords.getSubCategoryId());
			}
			req.setAttribute("topCategoryList", topCategoryList);
			req.setAttribute("subCategoryList", subCategoryList);
			req.setAttribute("thirdCategoryList", thirdCategoryList);
			req.setAttribute("tGoodsWordsPage", tGoodsWords);
		}
		return new ModelAndView("com/buss/words/tGoodsWords-update");
	}
	
}
