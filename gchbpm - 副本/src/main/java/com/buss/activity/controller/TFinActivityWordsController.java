package com.buss.activity.controller;
import java.io.IOException;
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
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.buss.activity.entity.TFinActivityWordsEntity;
import com.buss.activity.entity.TGoodsActWordsEntity;
import com.buss.activity.service.TFinActivityWordsServiceI;
import com.buss.bill.service.TFinActivityServiceI;
import com.buss.template.controller.TemplateWordsController;
import com.buss.template.entity.TTemplateTypeEntity;
import com.buss.template.entity.TTradeTemplateEntity;
import com.buss.words.entity.TActivityWordsEntity;
import com.buss.words.entity.TCustWordsEntity;
import com.buss.words.service.TCustWordsServiceI;



/**   
 * @Title: Controller
 * @Description: 活动话术
 * @author onlineGenerator
 * @date 2016-12-22 21:21:07
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tFinActivityWordsController")
public class TFinActivityWordsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TFinActivityWordsController.class);

	@Autowired
	private TFinActivityWordsServiceI tFinActivityWordsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TCustWordsServiceI tCustWordsService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 活动话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("finActId", request.getParameter("fin_act_Id"));
		request.setAttribute("wordsType", TFinActivityWordsEntity.WORDS_TYPE_ACTIVITY);//活动话术
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
//		String platformType = request.getParameter("pType");
//		String isSame = "0";//默认添加活动奖励和查看话术的不是同一个角色
//		TSUser user = ResourceUtil.getSessionUserName();
//		if(user.getUserType().equals(common.GlobalConstants.USER_TYPE_01)&&TFinActivityEntity.PLATFORM_TYPE_1.equals(platformType)){
//			isSame = "1";
//		}else if(!user.getUserType().equals(common.GlobalConstants.USER_TYPE_01)&&TFinActivityEntity.PLATFORM_TYPE_2.equals(platformType)){
//			isSame = "1";
//		}
//		request.setAttribute("activityStatus", request.getParameter("aSts"));
//		request.setAttribute("isSame", isSame);
		return new ModelAndView("com/buss/activity/tFinActivityWordsList");
	}
	
	/**
	 * 商品话术列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "goodsWordsList")
	public ModelAndView goodsWordsList(HttpServletRequest request) {
		request.setAttribute("finActId", request.getParameter("fin_act_Id"));
		request.setAttribute("wordsType", TFinActivityWordsEntity.WORDS_TYPE_GOODS);//商品话术
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/activity/tFinActivityWordsList2");
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
	public void datagrid(TFinActivityWordsEntity tFinActivityWords,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TFinActivityWordsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinActivityWords, request.getParameterMap());
		try{
		//自定义追加查询条件
			String finActId = request.getParameter("fin_Act_Id");
			if(StringUtil.isNotEmpty(finActId)){
				cq.eq("finActId", finActId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tFinActivityWordsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridWordsGoods")
	public void datagridWordsGoods(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		this.tFinActivityWordsService.getWordsGoodsList(request, dataGrid);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除活动话术
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动话术删除成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_fin_activity_words set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id = '")
			.append(id).append("'");
			tFinActivityWordsService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 删除商品话术关联的商品
	 * @return
	 */
	@RequestMapping(params = "doDelWordsGoods")
	@ResponseBody
	public AjaxJson doDelWordsGoods(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "关联的商品删除成功";
		try{
			String id = request.getParameter("id");
			String wordsId = request.getParameter("wordsId");
			tFinActivityWordsService.doDelActWords(id,wordsId);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "关联的商品删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除活动话术
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "活动话术删除成功";
		try{
				TSUser user = ResourceUtil.getSessionUserName();
				StringBuffer sql = new StringBuffer("update t_fin_activity_words set update_by = '").append(user.getUserName()).append("',update_name = '")
				.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp()).append("',status = 'I' where id in (");
				for(String id:ids.split(",")){
					sql.append("'").append(id).append("',");
				}
				sql = sql.deleteCharAt(sql.length()-1).append(")");
				tFinActivityWordsService.updateBySqlString(sql.toString());
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "活动话术删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加活动话术
	 * @param tFinActivityWords
	 * @param addToWordsStore 是否加入话术库 1：是 ，0：否
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TFinActivityWordsEntity tFinActivityWords,String addToWordsStore, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动话术添加成功";
//		try{
//			tFinActivityWordsService.save(tFinActivityWords);
//			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//		}catch(Exception e){
//			e.printStackTrace();
//			message = "活动话术添加失败";
//			throw new BusinessException(e.getMessage());
//		}
		try {
			//保存话术到话术库
			this.saveToWordsStore(tFinActivityWords,addToWordsStore, request);
		} catch (Exception e) {
			e.printStackTrace();
			message = "动话术添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**保存话术并同时到话术库
	 * @param tFinActivityWords
	 * @param addToWordsStore
	 */
	private void saveToWordsStore(TFinActivityWordsEntity tFinActivityWords,String addToWordsStore, HttpServletRequest request) {
		if("1".equals(addToWordsStore)){
			String retailerId = ResourceUtil.getRetailerId();
			if(TFinActivityWordsEntity.WORDS_TYPE_ACTIVITY.equals(tFinActivityWords.getWordsType())){
				TActivityWordsEntity actWords = new TActivityWordsEntity();
				actWords.setContent(tFinActivityWords.getWords());
				actWords.setPlatformType(common.GlobalConstants.PLATFORM_TYPE_2);
				actWords.setRetailerId(retailerId);
				actWords.setStatus("A");
				this.systemService.save(actWords);
			}else if(TFinActivityWordsEntity.WORDS_TYPE_GOODS.equals(tFinActivityWords.getWordsType())){
				TCustWordsEntity goodsWords = tFinActivityWords.getGoodsWords();
				goodsWords.setContent(tFinActivityWords.getWords());
				goodsWords.setPlatformType(common.GlobalConstants.PLATFORM_TYPE_2);
				goodsWords.setRetailerId(retailerId);
				goodsWords.setStatus("A");
				// 话术的的行业信息
				String tradeId = oConvertUtils.getString(request.getParameter("tradeId"));
				message = "话术模板添加成功";
				try{
					goodsWords.setIsShow(TCustWordsEntity.IS_SHOW_N);
					tCustWordsService.saveCustWords(goodsWords,request);

					//保存用户的行业类型
					if (StringUtil.isNotEmpty(tradeId)) {
						saveTradeTemplate(goodsWords, tradeId);
					}
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}catch(Exception e){
					e.printStackTrace();
					message = "话术模板添加失败";
					throw new BusinessException(e.getMessage());
				}
				
				tFinActivityWords.setWordsId(goodsWords.getId());
			}
		}
		this.tFinActivityWordsService.save(tFinActivityWords);
	}

	/**
	 * 批量添加选中的活动话术
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doBatchAddWords")
	@ResponseBody
	public AjaxJson doBatchAddWords(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动话术添加成功";
		try{
			String finActId = request.getParameter("finActId");
			String wordsType = request.getParameter("wordsType");
			String wordsIds = request.getParameter("wordsIds");
			if(StringUtil.isNotEmpty(wordsIds)){
				StringBuffer hql = new StringBuffer("from TActivityWordsEntity where id in (");
				for(String wordsId:wordsIds.split(",")){
					hql.append("'").append(wordsId).append("',");
				}
				hql = hql.deleteCharAt(hql.length()-1).append(")");
				List<TActivityWordsEntity> wordsList = this.systemService.findHql(hql.toString(), null);
				if(wordsList.size()>0){
					List<TFinActivityWordsEntity> list = new ArrayList<TFinActivityWordsEntity>();
					for(TActivityWordsEntity activityWords : wordsList){
						TFinActivityWordsEntity tFinActivityWords = new TFinActivityWordsEntity();
						tFinActivityWords.setFinActId(finActId);
						tFinActivityWords.setStatus("A");
						tFinActivityWords.setWordsType(wordsType);
						tFinActivityWords.setWordsId(activityWords.getId());
						tFinActivityWords.setWords(activityWords.getContent());
						list.add(tFinActivityWords);
					}
					this.systemService.batchSave(list);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "活动话术添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量添加选中的商品话术
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doBatchAddGoodsWords")
	@ResponseBody
	public AjaxJson doBatchAddGoodsWords(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "商品话术添加成功";
		try{
			String finActId = request.getParameter("finActId");
			String wordsType = request.getParameter("wordsType");
			String wordsIds = request.getParameter("wordsIds");
			if(StringUtil.isNotEmpty(wordsIds)){
				StringBuffer hql = new StringBuffer("from TCustWordsEntity where id in (");
				for(String wordsId:wordsIds.split(",")){
					hql.append("'").append(wordsId).append("',");
				}
				hql = hql.deleteCharAt(hql.length()-1).append(")");
				List<TCustWordsEntity> wordsList = this.systemService.findHql(hql.toString(), null);
				if(wordsList.size()>0){
					List<TFinActivityWordsEntity> list = new ArrayList<TFinActivityWordsEntity>();
					for(TCustWordsEntity activityWords : wordsList){
						TFinActivityWordsEntity tFinActivityWords = new TFinActivityWordsEntity();
						tFinActivityWords.setFinActId(finActId);
						tFinActivityWords.setStatus("A");
						tFinActivityWords.setWordsType(wordsType);
						tFinActivityWords.setWordsId(activityWords.getId());
						tFinActivityWords.setWords(activityWords.getContent());
						list.add(tFinActivityWords);
					}
					this.systemService.batchSave(list);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "商品话术添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量添加选中的商品话术
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doBatchAddWordsGoods")
	@ResponseBody
	public AjaxJson doBatchAddWordsGoods(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "话术商品关联成功";
		try{
			String goodsIds = request.getParameter("goodsIds");
			String wordsId = request.getParameter("wordsId");
			TCustWordsEntity tCustWordsEntity = systemService.findUniqueByProperty(TCustWordsEntity.class, "id", wordsId);
			
			for(String goodsId:goodsIds.split(",")){
				TFinActivityWordsEntity tFinActivityWords = new TFinActivityWordsEntity();
				tFinActivityWords.setStatus("A");
				tFinActivityWords.setFinActId(goodsId);
				tFinActivityWords.setWordsId(wordsId);
				tFinActivityWords.setWords(tCustWordsEntity.getContent());
				tFinActivityWords.setWordsType(TFinActivityWordsEntity.WORDS_TYPE_GOODS);
				
				tFinActivityWordsService.save(tFinActivityWords);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "话术商品关联失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新活动话术
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TFinActivityWordsEntity tFinActivityWords, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "活动话术更新成功";
		TFinActivityWordsEntity t = tFinActivityWordsService.get(TFinActivityWordsEntity.class, tFinActivityWords.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tFinActivityWords, t);
			tFinActivityWordsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "活动话术更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 活动话术新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TFinActivityWordsEntity tFinActivityWords, HttpServletRequest req) {
		req.setAttribute("finActId", tFinActivityWords.getFinActId());//活动ID/商品ID
		//二级分类
		String hql = "FROM TTemplateTypeEntity WHERE status='A' and parent.id='101' and level='2' and platformType='1' and templateType='1' ";
		List<TTemplateTypeEntity> subTypeList = systemService.findByQueryString(hql);
		//获取行业信息
		getTemplateTrade(req);
		req.setAttribute("subTypeList", subTypeList);
		
		
		req.setAttribute("wordsType", tFinActivityWords.getWordsType());//话术类型
		return new ModelAndView("com/buss/activity/tFinActivityWords-add");
	}
	
	//获取行业信息
	private void getTemplateTrade(HttpServletRequest request) {
		//获取行业信息
		String sqlTrade = "select * FROM t_s_category WHERE status='A' and level='2'";
		String retailerId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(retailerId)){
			sqlTrade = "select tsc.* FROM t_s_trade_user ttt left join t_s_category tsc on tsc.id = ttt.trade_id "+
			" WHERE tsc.status='A' and tsc.level='2' and ttt.status='A' and user_id = '"+retailerId+"'";
		}
		List<TSCategoryEntity> templateTrade = systemService.findObjForJdbc(sqlTrade, TSCategoryEntity.class);
		
		request.setAttribute("templateTrade", templateTrade);
	}
	/**
	 * 活动话术编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TFinActivityWordsEntity tFinActivityWords, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tFinActivityWords.getId())) {
			tFinActivityWords = tFinActivityWordsService.flushEntity(TFinActivityWordsEntity.class, tFinActivityWords.getId());
			req.setAttribute("tFinActivityWordsPage", tFinActivityWords);
		}
		return new ModelAndView("com/buss/activity/tFinActivityWords-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tFinActivityWordsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TFinActivityWordsEntity tFinActivityWords,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFinActivityWordsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFinActivityWords, request.getParameterMap());
		List<TFinActivityWordsEntity> tFinActivityWordss = this.tFinActivityWordsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"活动话术");
		modelMap.put(NormalExcelConstants.CLASS,TFinActivityWordsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("活动话术列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tFinActivityWordss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TFinActivityWordsEntity tFinActivityWords,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "活动话术");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TFinActivityWordsEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TFinActivityWordsEntity> listTFinActivityWordsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TFinActivityWordsEntity.class,params);
				for (TFinActivityWordsEntity tFinActivityWords : listTFinActivityWordsEntitys) {
					tFinActivityWordsService.save(tFinActivityWords);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	/**
	 * 加载明细列表[商品话术]
	 * @return
	 */
	@RequestMapping(params = "tGoodsWordsDetails")
	public ModelAndView tGoodsWordsDetails(String goodsId,String isView, HttpServletRequest req) {
		String hql0 = "from TFinActivityWordsEntity where finActId = ? and wordsType = ? order by sortNum asc";
		try{
			List<TFinActivityWordsEntity> tgoodsWordsDetailsList = systemService.findHql(hql0,goodsId,TFinActivityWordsEntity.WORDS_TYPE_GOODS);
			req.setAttribute("tGoodsWordsDetails", tgoodsWordsDetailsList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		req.setAttribute("isView", isView);
		return new ModelAndView("com/buss/newGoods/tGoodsWordsDetails");
	}
	
	
	//保存话术模板的行业类型
    protected void saveTradeTemplate(TCustWordsEntity tCustWords, String tradeIDs) {
		String[] tradeids = tradeIDs.split(",");
		List<TTradeTemplateEntity> tradeTemplates = systemService.findByProperty(TTradeTemplateEntity.class, "templateId", tCustWords.getId());
		
		//判断是否要删除
		Boolean flag = true;
		List<String> hasTrade = new ArrayList<String>();
		for (TTradeTemplateEntity tradeTemplate : tradeTemplates) {
			String tradeId = tradeTemplate.getTradeId();
			for (int i = 0; i < tradeids.length; i++) {
				if(tradeId.equals(tradeids[i])){
					flag = false;
				}
			}
			if(flag){
				//将TSTradeUser 标记为删除
				tradeTemplate.setStatus("I");
				systemService.updateEntitie(tradeTemplate);
			}else{
				hasTrade.add(tradeId);
			}
			flag = true;
		}
		
		for (int i = 0; i < tradeids.length; i++) {
			for (String hasTradeId : hasTrade) {
				if(hasTradeId.equals(tradeids[i])){
					flag = false;
				}
			}
			if(flag){
				//创建新用户行业连接数据
				TTradeTemplateEntity tradeTemplate = new TTradeTemplateEntity();
				TSCategoryEntity tsCategory = systemService.flushEntity(TSCategoryEntity.class, tradeids[i]);
				tradeTemplate.setTemplateId(tCustWords.getId());
				tradeTemplate.setTradeId(tsCategory.getId());
				tradeTemplate.setTradeName(tsCategory.getName());
				tradeTemplate.setStatus("A");
				systemService.save(tradeTemplate);
			}
			flag = true;
		}
	}
    
}
