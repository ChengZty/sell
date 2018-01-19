package com.buss.user.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
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

import cn.redis.service.RedisService;

import com.alibaba.druid.support.json.JSONUtils;
import com.buss.base.entity.TBaseTagsEntity;
import com.buss.goods.service.TGoodsServiceI;
import com.buss.store.service.TStoreServiceI;
import com.buss.user.datahandler.PersonDataHandler;
import com.buss.user.entity.TFocusCustomerEntity;
import com.buss.user.entity.TUserTagStoreEntity;
import com.buss.user.entity.vo.TFocusCustomerVo;
import com.buss.user.service.TFocusCustomerServiceI;
import com.buss.user.service.TPersonServiceI;
import com.buss.user.service.TVipLevelServiceI;
import com.buss.user.vo.CustTagsVo;
import com.buss.user.vo.TFocusCustomerExportVo;
import com.buss.user.vo.TradeCustomerVo;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 待发展顾客
 * @author onlineGenerator
 * @date 2016-03-22 16:44:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tFocusCustomerController")
public class TFocusCustomerController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TFocusCustomerController.class);

	@Autowired
	private TFocusCustomerServiceI tFocusCustomerService;
	@Autowired
	private SystemService systemService;
	@Resource  
	private RabbitTemplate rabbitTemplate; 
	private String message;
	@Autowired
	private RedisService redisService;
	@Autowired
	private TVipLevelServiceI tVipLevelService;
	@Autowired
	private TStoreServiceI tStoreService;
	@Autowired
	private TPersonServiceI tPersonService;
	@Autowired
	private TGoodsServiceI tGoodsService;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 待发展顾客列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/tFocusCustomerList");
	}
	
	/**
	 * 待发展顾客列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "listOfRetailer")
	public ModelAndView listOfRetailer(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailer_Id", retailerId);
		return new ModelAndView("com/buss/user/tFocusCustomerListOfRetailer");
	}
	
	/**
	 * 顾客管理（新零售商） 页面跳转
	 * @return
	 */
	@RequestMapping(params = "listOfRet")
	public ModelAndView listOfRet(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailer_Id", retailerId);
		return new ModelAndView("com/buss/user/tFocusCustomerListOfRet");
	}
	
	/**
	 * 顾客管理（新零售商） 选择顾客页面跳转
	 * @return
	 */
	@RequestMapping(params = "listOfSelect")
	public ModelAndView listOfSelect(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailer_Id", retailerId);
		String retailerEdition = ResourceUtil.getRetailerEdition();
		request.setAttribute("edition", retailerEdition);
		return new ModelAndView("com/buss/sms/tFocusCustomerListOfSelect");
	}
	
	/**
	 * 交易顾客页面跳转
	 * @return
	 */
	@RequestMapping(params = "tradeCustomerList")
	public ModelAndView tradeCustomerList(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/tradeCustomerList");
	}
	
	/**
	 * d+查询全部待发展顾客（包括交易顾客）
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TFocusCustomerEntity tFocusCustomer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TFocusCustomerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFocusCustomer, request.getParameterMap());
		try{
		//自定义追加查询条件
			String rId = ResourceUtil.getRetailerId();
			String guideName = request.getParameter("guideName");//所属导购
			String dtype = request.getParameter("dtype");//d+查询有此参数，则过滤没有号码的顾客
			if(!StringUtil.isEmpty(dtype)){
				cq.isNotNull("phoneNo");
			}
			if(!StringUtil.isEmpty(guideName)){
	            cq.or(Restrictions.like("addGuideName", guideName), Restrictions.like("toGuideName", guideName));
			}
			String selectType = request.getParameter("types");//短信查询顾客类型多选条件
			if(!StringUtil.isEmpty(selectType)){
				selectType = selectType.substring(0,selectType.length()-1);
				cq.in("type", selectType.split(","));
			}
			String pageSource = request.getParameter("pageSource");//页面来源，1代表来自短信顾客列表选择，否则为其他页面来源
			if(GlobalConstants.SEND_PAGE_SOURCE.equals(pageSource)){
				cq.eq("unOrder", "0");
			}
			if(Utility.isNotEmpty(rId)){
				cq.eq("toRetailerId", rId);
			}
			String sortOrder = dataGrid.getSort();
			if(Utility.isEmpty(sortOrder)){
				Map<String,Object> map = new LinkedHashMap<String, Object>();
				map.put("createDate", SortDirection.desc);
				map.put("phoneNo", SortDirection.asc);
				cq.setOrder(map);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
//		cq.eq("isUseApp", "0");
		cq.add();
		this.tFocusCustomerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * g+查询全部待发展顾客
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagridOfG")
	public void datagridOfG(TFocusCustomerEntity tFocusCustomer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TFocusCustomerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFocusCustomer, request.getParameterMap());
		try{
		//自定义追加查询条件
			String rId = ResourceUtil.getRetailerId();
			String custType = request.getParameter("custType");//顾客类型
			String guideName = request.getParameter("guideName");//所属导购
			String addType = request.getParameter("addType");//添加类型
			String haveGuide = request.getParameter("haveGuide");//是否分配导购
			if(Utility.isNotEmpty(rId)){
				cq.eq("toRetailerId", rId);
			}
			if(Utility.isNotEmpty(custType)){
				if(TFocusCustomerEntity.TYPE_3.equals(custType)){//交易顾客
					cq.eq("type", TFocusCustomerEntity.TYPE_3);
				}else{//非交易顾客
					cq.notEq("type", TFocusCustomerEntity.TYPE_3);
				}
			}
			if(!StringUtil.isEmpty(addType)){
				if("1".equals(addType)){//零售商添加的
					/*cq.isNotNull("addRetailerId");*/
					cq.eq("addRetailerId", rId);
				}else if("2".equals(addType)){//导购添加的
					cq.isNotNull("addGuideId");
				}
			}
			if(!StringUtil.isEmpty(haveGuide)){
				if("1".equals(haveGuide)){//有导购
					Disjunction disjunction = Restrictions.disjunction();  
		            disjunction.add(Restrictions.isNotNull("addGuideId"));  
		            disjunction.add(Restrictions.isNotNull("toGuideId")); 
		            cq.add(disjunction); 
				}else if("2".equals(haveGuide)){//无导购
					cq.isNull("addGuideId");
					cq.isNull("toGuideId");
				}
			}
			if(Utility.isNotEmpty(guideName)){
	            cq.or(Restrictions.like("addGuideName", guideName), Restrictions.like("toGuideName", guideName));
			}
			String sortOrder = dataGrid.getSort();
			if(Utility.isEmpty(sortOrder)){
				Map<String,Object> map = new LinkedHashMap<String, Object>();
				map.put("createDate", SortDirection.desc);
				map.put("phoneNo", SortDirection.asc);
				cq.setOrder(map);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tFocusCustomerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui  获取导购的服务顾客列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagridForGuide")
	public void datagridForGuide(TFocusCustomerEntity tFocusCustomer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TFocusCustomerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFocusCustomer, request.getParameterMap());
		try{
		//自定义追加查询条件
			String guideId =  request.getParameter("guideId");
			cq.or(Restrictions.eq("addGuideId", guideId), Restrictions.eq("toGuideId", guideId));
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tFocusCustomerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
		
	}

	/**
	 * 删除待发展顾客
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TFocusCustomerEntity tFocusCustomer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tFocusCustomer = systemService.flushEntity(TFocusCustomerEntity.class, tFocusCustomer.getId());
		message = "待发展顾客删除成功";
		try{
			tFocusCustomer.setStatus(common.GlobalConstants.STATUS_INACTIVE);
			tFocusCustomer.setUpdateDate(DateUtils.gettimestamp());
			tFocusCustomerService.updateEntitie(tFocusCustomer);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "待发展顾客删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除待发展顾客
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "待发展顾客删除成功";
		try{
			for(String id:ids.split(",")){
				TFocusCustomerEntity tFocusCustomer = systemService.flushEntity(TFocusCustomerEntity.class, id);
				tFocusCustomer.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				tFocusCustomer.setUpdateDate(DateUtils.gettimestamp());
				tFocusCustomerService.updateEntitie(tFocusCustomer);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "待发展顾客删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	 
	 /**
	  * 校验号码是否为公有（导购没有录过）
	  * @return
	  */
	 @RequestMapping(params = "checkPhoneNos")
	 @ResponseBody
	 public AjaxJson checkPhoneNos(String phoneNos,HttpServletRequest request){
		 AjaxJson j = new AjaxJson();
		 message = "号码验证成功";
		 try{
			 if(Utility.isEmpty(phoneNos)){
				 message = "请选择号码";
			 }else{
				 String retailerId = ResourceUtil.getRetailerId();
				 StringBuffer sql = new StringBuffer("select phone_no,add_guide_id,add_retailer_id from t_focus_customer where status='A'");
				 sql.append(" and to_retailer_id = ?")
				 	.append(" and phone_no in (");
				 for(String phoneNo:phoneNos.split(",")){
					 sql.append("'").append(phoneNo).append("',");
				 }
				 sql.deleteCharAt(sql.length()-1).append(" ) ");
				 List<Map<String, Object>> list = systemService.findForJdbc(sql.toString(), retailerId);
				 if(list.size()>0){
					 for(Map<String, Object> map : list){
						 if(Utility.isNotEmpty(map.get("add_guide_id"))){//有导购录入过
							 message = map.get("phone_no")+"已有管家录入，不能分配";
							 j.setSuccess(false);
							 break;
						 }
						 if(Utility.isEmpty(map.get("add_retailer_id"))){//不是公司录入的
							 message = map.get("phone_no")+"不是公司录入，不能分配";
							 j.setSuccess(false);
							 break;
						 }
					 }
				 }
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "号码验证出错";
			 j.setSuccess(false);
			 throw new BusinessException(e.getMessage());
		 }
		 j.setMsg(message);
		 return j;
	 }
	 
	 
	 /**
	  * 批量分配待发展顾客
	  * @return
	  */
	 @RequestMapping(params = "doBatchGive")
	 @ResponseBody
	 public AjaxJson doBatchGive(String ids,String guideId,HttpServletRequest request){
		 AjaxJson j = new AjaxJson();
		 message = "顾客分配成功";
		 try{
			 tFocusCustomerService.doBatchGive(ids,guideId);
			 systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "顾客分配失败";
			 throw new BusinessException(e.getMessage());
		 }
		 j.setMsg(message);
		 return j;
	 }
	 
	 /**
	  * 判断并取消分配待发展顾客
	  * @return
	  */
	 @RequestMapping(params = "doCancelGive")
	 @ResponseBody
	 public AjaxJson doCancelGive(String id,HttpServletRequest request){
		 AjaxJson j = new AjaxJson();
		 message = "顾客取消分配成功";
		 try{
			 	tFocusCustomerService.doCancelGive(id);
				 systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		 }catch(Exception e){
			 e.printStackTrace();
			 message = "顾客取消分配失败";
			 throw new BusinessException(e.getMessage());
		 }
		 j.setMsg(message);
		 return j;
	 }


	/**
	 * g+零售商添加待发展顾客
	 * 更新顾客资料完整度
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TFocusCustomerEntity tFocusCustomer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "待发展顾客添加成功";
		try
		{
//			int flag = this.tFocusCustomerService.checkExist(tFocusCustomer.getPhoneNo());
			String retailerId = ResourceUtil.getRetailerId();
			//改为按手机号和零售商ID去判断
			int flag = this.tFocusCustomerService.checkPhoneAndRetailerExist(tFocusCustomer.getPhoneNo(),retailerId);
			if(flag!=1){
				tFocusCustomer.setSource(TFocusCustomerEntity.SOURCE_4);
				tFocusCustomerService.saveFocusCustm(flag,tFocusCustomer);
			}else{
				message = "待发展顾客添加失败,g+系统已经存在该客户信息";
				j.setSuccess(false);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "待发展顾客添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		//更新手机号归属地
		updateEmptyPhoneArea(ResourceUtil.getRetailerId());
		j.setMsg(message);
		return j;
	}
	
	/**
	 * d+零售商添加待发展顾客
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd2")
	@ResponseBody
	public AjaxJson doAdd2(TFocusCustomerEntity tFocusCustomer, HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		AjaxJson j = new AjaxJson();
		message = "待发展顾客添加成功";
		try
		{
			int flag = this.tFocusCustomerService.checkPhoneAndRetailerExist(tFocusCustomer.getPhoneNo(),retailerId);
			if(flag!=1){
				tFocusCustomer.setSource(TFocusCustomerEntity.SOURCE_4);
				tFocusCustomerService.saveFocusCustm(flag,tFocusCustomer);
			}else{
				message = "待发展顾客添加失败,g+系统已经存在该客户信息";
				j.setSuccess(false);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "待发展顾客添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		//更新手机号归属地
		updateEmptyPhoneArea(retailerId);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新待发展顾客
	 * 更新顾客资料完整度
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TFocusCustomerEntity tFocusCustomer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "待发展顾客更新成功";
		TFocusCustomerEntity t = tFocusCustomerService.get(TFocusCustomerEntity.class, tFocusCustomer.getId());
		try
		{
			boolean isexist = this.tFocusCustomerService.checkPhoneExist(tFocusCustomer.getPhoneNo(),t);
			if(!isexist){
				MyBeanUtils.copyBeanNotNull2Bean(tFocusCustomer, t);
				tFocusCustomerService.updateCustomer(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}else
			{
				message = "待发展顾客修改失败,g+系统已经存在该客户电话号码";
				j.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "待发展顾客更新失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 待发展顾客新增页面跳转g+
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TFocusCustomerEntity tFocusCustomer, HttpServletRequest req) {
		req.setAttribute("storeList", tStoreService.getStoreList());
		req.setAttribute("lvlList", tVipLevelService.getLvlList());
		return new ModelAndView("com/buss/user/tFocusCustomer-add");
	}
	
	/**
	 * 待发展顾客新增页面跳转d+
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd2")
	public ModelAndView goAdd2(TFocusCustomerEntity tFocusCustomer, HttpServletRequest req) {
		req.setAttribute("storeList", tStoreService.getStoreList());
		req.setAttribute("lvlList", tVipLevelService.getLvlList());
		return new ModelAndView("com/buss/user/tFocusCustomer-add2");
	}
	
	/**
	 * 待发展顾客编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TFocusCustomerEntity tFocusCustomer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tFocusCustomer.getId())) {
			tFocusCustomer = tFocusCustomerService.flushEntity(TFocusCustomerEntity.class, tFocusCustomer.getId());
			req.setAttribute("tFocusCustomerPage", tFocusCustomer);
		}
		req.setAttribute("storeList", tStoreService.getStoreList());
		req.setAttribute("lvlList", tVipLevelService.getLvlList());
//		if(TSUser.RETAILER_EDITION_1.equals(ResourceUtil.getRetailerEdition())){//标准版
//			return new ModelAndView("com/buss/user/tFocusCustomer-update");
//		}
		return new ModelAndView("com/buss/user/tFocusCustomer-update");
	}
	
	/**
	 * 待发展顾客查看页面跳转
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TFocusCustomerEntity tFocusCustomer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tFocusCustomer.getId())) {
			tFocusCustomer = tFocusCustomerService.get(TFocusCustomerEntity.class, tFocusCustomer.getId());
			tFocusCustomerService.evictEntity(tFocusCustomer);
			this.setShowContent(tFocusCustomer);//把编码转换成对应文字
			req.setAttribute("tFocusCustomerPage", tFocusCustomer);
		}
		req.setAttribute("storeList", tStoreService.getStoreList());
		return new ModelAndView("com/buss/user/tFocusCustomer-view");
	}
	
	/**
	 * 交易顾客查看详情和标签页面跳转
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "goViewDetailAndTags")
	public ModelAndView goViewDetailAndTags(TFocusCustomerEntity tFocusCustomer, HttpServletRequest req) {
		try {
			tFocusCustomer = tFocusCustomerService.get(TFocusCustomerEntity.class, tFocusCustomer.getId());
			tFocusCustomerService.evictEntity(tFocusCustomer);
			this.setShowContent(tFocusCustomer);//把编码转换成对应文字
			req.setAttribute("tFocusCustomerPage", tFocusCustomer);
			String typegrp = redisService.get("qst_stage");//问题阶段
			if(Utility.isNotEmpty(typegrp)){
				HashMap<String, Object> typegrpMap =  (HashMap<String, Object>) JSONUtils.parse(typegrp);
				List<HashMap<String, String>> typeList =  (List<HashMap<String, String>>) typegrpMap.get("typeList");
				if(Utility.isNotEmpty(typeList)){
					//顾客标签列表的答案
					String hql = "from TUserTagStoreEntity where customerId = ? and toUserType='2'";
					List<TUserTagStoreEntity> userTagList = this.systemService.findHql(hql,	tFocusCustomer.getId());
					//key:标签阶段名称，value:标签列表和对应的答案
					Map<String,List<CustTagsVo>> baseTagMap = new LinkedHashMap<String, List<CustTagsVo>>();
					//顾客所有标签列表，按阶段，排序顺序排列
					List<TBaseTagsEntity> baseTagList = this.systemService.findHql("from TBaseTagsEntity where toUserType= ? and valid = ? and retailer_id in ('admin','"+
							tFocusCustomer.getToRetailerId()+"') order by tagStage asc,tagSort asc",TBaseTagsEntity.TO_USER_TYPE_CUST,TBaseTagsEntity.VALID_Y);
					for(HashMap<String, String> typeVo : typeList){//循环 标签阶段
						String typecode= typeVo.get("typecode");
						String typename= typeVo.get("typename");
						//某阶段 标签列表及对应的答案
						List<CustTagsVo> voList = new ArrayList<CustTagsVo>();
						for(TBaseTagsEntity baseTag:baseTagList){
							if(typecode.equals(baseTag.getTagStage())){
								CustTagsVo vo = new CustTagsVo();
								BeanUtils.copyProperties(baseTag, vo);
								for(TUserTagStoreEntity userTag : userTagList){
									if(baseTag.getTagCode().equals(userTag.getTagCode())){//是同一个问题
										vo.setTagStore(userTag);
										break;
									}
								}
								voList.add(vo);
							}
						}
						baseTagMap.put(typename, voList);
					}
					req.setAttribute("baseTagMap", baseTagMap);
				}
			}
			
		} catch (BeansException e) {
			e.printStackTrace();
		}
		return new ModelAndView("com/buss/user/tFocusCustomerTags");
	}
	
	/**
	 * 查看导购服务的顾客列表页面跳转
	 * @return
	 */
	@RequestMapping(params = "goViewCustomersOfGuide")
	public ModelAndView goViewCustomersOfGuide(String guideId, HttpServletRequest req) {
		req.setAttribute("guideId", guideId);
		req.setAttribute("retailer_Id", ResourceUtil.getRetailerId());
		return new ModelAndView("com/buss/user/tFocusCustomerListOfGuide");
	}
	
	/**设置显示内容，把编码转换成对应文字
	 * @param tFocusCustomer
	 */
	private void setShowContent(TFocusCustomerEntity tFocusCustomer) {
		String sex = tFocusCustomer.getSex();//性别
		String type = tFocusCustomer.getType();//类型
//		String isUseApp = tFocusCustomer.getIsUseApp();//使用APP
		String source = tFocusCustomer.getSource();//来源
		if(Utility.isNotEmpty(sex)){
			if("1".equals(sex)){
				tFocusCustomer.setSex("女");
			}else if("0".equals(sex)){
				tFocusCustomer.setSex("男");
			}
		}
		if(Utility.isNotEmpty(type)){
			switch (Integer.valueOf(type)) {
			case 0:
				tFocusCustomer.setType("无效号码");
				break;
			case 1:
				tFocusCustomer.setType("无反应顾客");
				break;
			case 2:
				tFocusCustomer.setType("点击顾客");
				break;
			case 3:
				tFocusCustomer.setType("交易顾客");
				break;
			default:
				break;
			}
		}
		/*if(Utility.isNotEmpty(isUseApp)){
			if(TFocusCustomerEntity.IS_USE_APP_YES.equals(isUseApp)){
				tFocusCustomer.setIsUseApp("使用APP");
			}else if(TFocusCustomerEntity.IS_USE_APP_NO.equals(isUseApp)){
				tFocusCustomer.setIsUseApp("未使用APP");
			}
		}*/
		if(Utility.isNotEmpty(source)){
			switch (Integer.valueOf(source)) {
			case 1:
				tFocusCustomer.setSource("APP录入");
				break;
			case 2:
				tFocusCustomer.setSource("微信录入");
				break;
			case 3:
				tFocusCustomer.setSource("QQ录入");
				break;
			case 4:
				tFocusCustomer.setSource("后台录入");
				break;
			case 5:
				tFocusCustomer.setSource("其他");
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 导入功能跳转
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tFocusCustomerController");
		req.setAttribute("extraParam",req.getParameter("type"));
		return new ModelAndView("com/buss/user/focus_customer_upload");
	}
	
	/**
	 * 导出待发展excel
	 * g+排除交易顾客，d+不排除交易顾客
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TFocusCustomerEntity tFocusCustomer,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFocusCustomerEntity.class, dataGrid);
		String exportType = request.getParameter("exportType");//导出类型
		String guideName = request.getParameter("guideName");//所属导购
		String rId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(rId)){
			cq.eq("toRetailerId", rId);
		}
		if(Utility.isNotEmpty(guideName)){
            cq.or(Restrictions.like("addGuideName", guideName), Restrictions.like("toGuideName", guideName));
		}
		if("g".equals(exportType)){//g+导出待发展顾客
			cq.notEq("type", TFocusCustomerEntity.TYPE_3);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFocusCustomer, request.getParameterMap());
			List<TFocusCustomerEntity> tFocusCustomers = this.tFocusCustomerService.getListByCriteriaQuery(cq,true);
			//导出前数据处理（导出列新增添加，分配的导购名字）
			List<TFocusCustomerExportVo> list = this.changeCustomerVo(tFocusCustomers);
			modelMap.put(NormalExcelConstants.CLASS,TFocusCustomerExportVo.class);
			modelMap.put(NormalExcelConstants.DATA_LIST,list);
		}else if("d".equals(exportType)){//d+导出待发展顾客
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFocusCustomer, request.getParameterMap());
			List<TFocusCustomerEntity> tFocusCustomers = this.tFocusCustomerService.getListByCriteriaQuery(cq,true);
			modelMap.put(NormalExcelConstants.CLASS,TFocusCustomerEntity.class);
			modelMap.put(NormalExcelConstants.DATA_LIST,tFocusCustomers);
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"待发展顾客");
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("待发展顾客列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息"));
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**导出前数据处理（导出列新增添加，分配的导购名字）*/
	private List<TFocusCustomerExportVo> changeCustomerVo(List<TFocusCustomerEntity> tFocusCustomers) {
		List<TFocusCustomerExportVo> list = new ArrayList<TFocusCustomerExportVo>();
		if(Utility.isNotEmpty(tFocusCustomers)){
				for(TFocusCustomerEntity cust : tFocusCustomers){
					TFocusCustomerExportVo vo = new TFocusCustomerExportVo();
					try {
						MyBeanUtils.copyBean2Bean(vo, cust);
						list.add(vo);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		}
		return list;
	}

	/**
	 * 导出交易顾客excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportTradeXls")
	public String exportTradeXls(TFocusCustomerEntity tFocusCustomer,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TFocusCustomerEntity.class, dataGrid);
		String rId = ResourceUtil.getRetailerId();
		if(Utility.isNotEmpty(rId)){
			cq.eq("toRetailerId", rId);
		}
		cq.eq("type", TFocusCustomerEntity.TYPE_3);//交易顾客
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tFocusCustomer, request.getParameterMap());
		List<TFocusCustomerEntity> tFocusCustomerList = this.tFocusCustomerService.getListByCriteriaQuery(cq,true);
		List<TradeCustomerVo> tradeCustomerList = new ArrayList<TradeCustomerVo>();
		for(TFocusCustomerEntity entity : tFocusCustomerList){
			TradeCustomerVo vo = new TradeCustomerVo();
			BeanUtils.copyProperties(entity, vo);
			vo.setGuideName(Utility.isEmpty(entity.getAddGuideName())?entity.getToGuideName():entity.getAddGuideName());
			tradeCustomerList.add(vo);
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"交易顾客");
		modelMap.put(NormalExcelConstants.CLASS,TradeCustomerVo.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("交易顾客列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tradeCustomerList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	/**
	 * 导出excel 使模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TFocusCustomerEntity tFocusCustomer,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	/*modelMap.put(NormalExcelConstants.FILE_NAME,"待发展顾客表");
    	modelMap.put(NormalExcelConstants.CLASS,TFocusCustomerVo.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("待发展顾客表列表", "导入人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导入信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;*/
    	
    	String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\templates");
		String fileName = "待发展顾客导入模板.xlsx";
		String filePath = path + "\\" + fileName;
		try {
			FileUtils.downLoadFile(filePath, response, request, fileName, "xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedSave(true);
			PersonDataHandler handler = new PersonDataHandler();
			handler.setNeedHandlerFields(new String[]{"生日"});
			params.setDataHanlder(handler);
			try {
				String msg = null;
				//批量导入待发展顾客（不能有重复的号码，并且导购如果已经录过了的，零售商的就要过滤该记录）
				long start = System.currentTimeMillis();
				List<TFocusCustomerEntity> listTFocusCustomerEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TFocusCustomerEntity.class,params);
				long end = System.currentTimeMillis();
				System.out.println("importExcel生成list共耗时" + (end - start)+"ms");
				if(listTFocusCustomerEntitys.size()>10000){
					j.setMsg("单次导入请不要超过10000条记录");
				}else{
					//把手机号的空格和-去掉，并去掉和系统中重复的手机号
					Map<String,List<TFocusCustomerEntity>> map = this.filterPhoneNo(listTFocusCustomerEntitys);
					List<TFocusCustomerEntity> successList = map.get("successList");
					List<TFocusCustomerEntity> failList = map.get("failList");
					if(successList.size()>0){
						//没有错误提示信息
						msg = this.tFocusCustomerService.doBatchSaveInfo(successList);
						if(msg==null){
							msg = "成功导入"+successList.size()+"条记录";
						}
						successList.clear();
					}
					if(failList.size()>0){//导出失败的数据
						List<TFocusCustomerVo> failVoList = new ArrayList<TFocusCustomerVo>();
						for(TFocusCustomerEntity cust : failList){
							TFocusCustomerVo vo = new TFocusCustomerVo();
							BeanUtils.copyProperties(cust, vo);
							failVoList.add(vo);
						}
						if(msg==null){
							msg = failList.size()+"条有问题的记录导出中...";
						}else{
							msg+=","+failList.size()+"条有问题的记录已导出";
						}
						//上传七牛
						String title = "待发展顾客导入错误提示";
						String key = this.tGoodsService.uploadExcelFileToQN(TFocusCustomerVo.class,failVoList,null,"custUpload",title);
						j.setObj(key);
						j.setSuccess(false);
						failList.clear();
						failVoList.clear();
					}
					j.setMsg(msg);
				}
				
//				String type = request.getParameter("extraParam");
//				//校验手机号是否合法,是否重复
//				String result = this.checkPhoneNos(listTFocusCustomerEntitys);
//				if(result!=null){
//					j.setMsg(result);
//					return j;
//				}
				//校验导入的号码是否和系统中的有重复，有则返回重复的号码
//				String existPhoneNo = this.tFocusCustomerService.getExistPhoneNo(listTFocusCustomerEntitys);//存在的号码
//				if("1".equals(type)){//g+导入潜在待发展顾客///					if(listTFocusCustomerEntitys.size()>100000){
////						j.setMsg("单次导入请不要超过100000条记录");
////					}else{
//						if(Utility.isEmpty(existPhoneNo)){//不存在相同号码
////							String msg  = this.tFocusCustomerService.doBatchSave(listTFocusCustomerEntitys);
//							String msg  = this.tFocusCustomerService.doBatchSaveInfo(listTFocusCustomerEntitys);
//							j.setMsg(msg);
//						}else{
//							j.setMsg("系统中已经存在手机号："+existPhoneNo);
//							logger.info("系统中已经存在手机号："+existPhoneNo);
//						}
////					}
//				}else if("2".equals(type)){//d+导入待发展顾客，201702顾客资料导入 暂未做手机号和数据库对比是否重复
////					if(listTFocusCustomerEntitys.size()>100000){
////						j.setMsg("单次导入请不要超过100000条记录");
////					}else{
//					if(Utility.isEmpty(existPhoneNo)){
//						String msg  = this.tFocusCustomerService.doBatchSaveInfo(listTFocusCustomerEntitys);
//						j.setMsg(msg);
//					}else{
//						j.setMsg("系统中已经存在手机号："+existPhoneNo);
//						logger.info("系统中已经存在手机号："+existPhoneNo);
//					}
////					}
//				}
			} catch (Exception e) {
				e.printStackTrace();
				j.setMsg("文件导入失败，请检查单元格格式和模版格式是否一致！");
				logger.error(e.getMessage());
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

	
	
//	protected String ExportXlsxByFailList(HttpServletRequest request,HttpServletResponse response) {
//		List<TFocusCustomerVo> failVoList = new ArrayList<TFocusCustomerVo>();
//		ModelMap modelMap =  new ModelMap();
//		modelMap.put(NormalExcelConstants.FILE_NAME,"待发展顾客问题记录");
//		modelMap.put(NormalExcelConstants.CLASS,TFocusCustomerVo.class);
//		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("待发展顾客表列表有问题的记录(再次导入请删除备注里的错误说明)","导出信息"));
//		modelMap.put(NormalExcelConstants.DATA_LIST,failVoList);
//		return NormalExcelConstants.JEECG_EXCEL_VIEW;
//	}
	
	/**导出有问题的记录
	 * @param failList
	 * @return
	 */
	/*@RequestMapping(params = "exportXlsxByFailList")
	public void exportXlsxByFailList(HttpServletRequest request,HttpServletResponse response,String key)throws Exception {
		String val = redisService.get(key);
		List<Map> mapList = MAPPER.readValue(val,List.class);
		List<TFocusCustomerVo> failVoList = new ArrayList<TFocusCustomerVo>();
		for(Map map : mapList){
			TFocusCustomerVo vo= new TFocusCustomerVo();
			MyBeanUtils.copyMap2Bean(vo, map);
			failVoList.add(vo);
		}
		String codedFileName = "错误记录";
		 Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("待发展顾客表列表有问题的记录","导出信息"), TFocusCustomerVo.class, failVoList);
		 if (workbook instanceof HSSFWorkbook) {
	            codedFileName += ".xls";
	        } else {
	            codedFileName += ".xlsx";
	        }
		 if (isIE(request)) {
	            codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF8");
	        } else {
	            codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
	        }
        response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
	}*/
	
	 protected boolean isIE(HttpServletRequest request) {
	        return (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 || request
	            .getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0) ? true : false;
	    }

	/**处理手机号，去掉空格和-还有重复的号码
	 * @param listTFocusCustomerEntitys
	 * @return map (successList成功的list,failList失败的list)
	 */
	private Map<String,List<TFocusCustomerEntity>> filterPhoneNo(List<TFocusCustomerEntity> listTFocusCustomerEntitys) {
		Map<String,List<TFocusCustomerEntity>>  map = new HashMap<String,List<TFocusCustomerEntity>>();
		List<TFocusCustomerEntity> importList = new ArrayList<TFocusCustomerEntity>();//导入的号码合格的列表
		List<TFocusCustomerEntity> successList = new ArrayList<TFocusCustomerEntity>();//成功的列表
		List<TFocusCustomerEntity> failList = new ArrayList<TFocusCustomerEntity>();//失败的列表
//		String msg = null;
//		int n =2;
		//手机号set集合
		HashSet<String> phoneNoSet = new HashSet<String>();
		for(TFocusCustomerEntity cust : listTFocusCustomerEntitys){
//			n++;
			String phoneNo = cust.getPhoneNo();
			if(Utility.isEmpty(phoneNo)){//手机号为空
				cust.setPhoneNo(null);
				importList.add(cust);
			}else{
				phoneNo = phoneNo.trim().replaceAll("-", "").replaceAll(" ", "");
				cust.setPhoneNo(phoneNo);
				if(!Utility.isMobile(phoneNo)){//不合法
					cust.setRemark("手机号码不合法");
					failList.add(cust);
//					msg = "第"+n+"行手机号不合法，请检查";
				}else{
					if(!phoneNoSet.contains(phoneNo)){
						phoneNoSet.add(phoneNo);
						importList.add(cust);
					}else{
						cust.setRemark("手机号码重复");
						failList.add(cust);
//						msg = "第"+n+"行手机号："+cust.getPhoneNo()+"重复";
					}
				}
			}
		}
		phoneNoSet = null;
		if(importList.size()>0){
			String rId = ResourceUtil.getRetailerId();
			List existCusts = this.tFocusCustomerService.findByQueryString("select c.phoneNo from TFocusCustomerEntity c where c.toRetailerId = '"+rId+"' and c.status = 'A'");
			if(existCusts.size()>0){
				//跟数据库重复的手机号
				Set<String> duplicatePhoneNoSet = new HashSet<String>();
				for(Object cust : existCusts){
					duplicatePhoneNoSet.add(cust+"");
				}
				for(TFocusCustomerEntity impotCust : importList){
					if(duplicatePhoneNoSet.contains(impotCust.getPhoneNo())){
						impotCust.setRemark("手机号码系统中已存在");
						failList.add(impotCust);
					}else{
						successList.add(impotCust);
					}
				}
			}else{//之前没有数据，不需要校验数据库重复的手机号
				for(TFocusCustomerEntity impotCust : importList){
					successList.add(impotCust);
				}
			}
			importList.clear();
		}
		map.put("successList", successList);
		map.put("failList", failList);
		return map;
	}
	
	/**
	 * 更新零售商导入号码的归属地
	 * @return
	 */
	public void updateEmptyPhoneArea(String retailerId) {
		try
		{
			//MQ调用
			Map<String,Object> mqMap = new HashMap<String, Object>();
			mqMap.put("retailerId", retailerId);//零售商ID
			ResourceBundle rabbitmq = ResourceBundle.getBundle("rabbitmq");
			String key = rabbitmq.getString("update.phoneArea.mq.queue.key");
			rabbitTemplate.convertAndSend(key, mqMap);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}
	

	/**
	 * 更新零售商导入号码的归属地
	 * @return
	 */
	@RequestMapping(params = "updateEmptyPhoneArea")
	@ResponseBody
	public AjaxJson updateEmptyPhoneArea(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "更新手机号码归属地成功";
		try
		{
			//MQ调用
			Map<String,Object> mqMap = new HashMap<String, Object>();
			mqMap.put("retailerId", ResourceUtil.getRetailerId());//零售商ID
			ResourceBundle rabbitmq = ResourceBundle.getBundle("rabbitmq");
			String key = rabbitmq.getString("update.phoneArea.mq.queue.key");
			rabbitTemplate.convertAndSend(key, mqMap);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新手机号码归属地失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 *零售商标准版
	 * 顾客资料分析
	 * @return
	 */
	@RequestMapping(params = "goReview")
	public ModelAndView goReview(HttpServletRequest request) {
		request.setAttribute("page", request.getParameter("page"));
		return new ModelAndView("com/buss/user/tFocusCustomerReport");
	}
	
	/**
	 * 删除全部无效顾客
	 * @return
	 */
	@RequestMapping(params = "doBatchDelDeadCust")
	@ResponseBody
	public AjaxJson doBatchDelDeadCust(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "全部无效顾客删除成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			this.tFocusCustomerService.updateBySqlString("update t_focus_customer set status = 'I',update_date = NOW() where status = 'A' and type = '"+TFocusCustomerEntity.TYPE_0+"' and to_retailer_id = '"
					+retailerId+"'");
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "全部无效顾客删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 删除全部非交易顾客
	 * @return
	 */
	@RequestMapping(params = "doBatchDelNotTradedCust")
	@ResponseBody
	public AjaxJson doBatchDelNotTradedCust(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "全部非交易顾客删除成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			this.tFocusCustomerService.updateBySqlString("update t_focus_customer set status = 'I',update_date = NOW() where status = 'A' and type <> '"+TFocusCustomerEntity.TYPE_3+"' and to_retailer_id = '"
					+retailerId+"'");
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "全部非交易顾客删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	
	
	/**
	 * 顾客管理（新零售商） 选择顾客页面跳转
	 * @return
	 */
	@RequestMapping(params = "listOfNewSelect")
	public ModelAndView listOfNewSelect(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailer_Id", retailerId);
		String retailerEdition = ResourceUtil.getRetailerEdition();
		request.setAttribute("edition", retailerEdition);
		
		return new ModelAndView("com/buss/sms/tFocusCustomerListOfNewSelect");
	}
	
	
	
	/**
	 * 导购停用、回收导购录入的顾客资料（包括待发展和交易顾客）
	 * @return msg
	 */
	@RequestMapping(params = "revokeCustInfo")
	@ResponseBody
	public AjaxJson revokeCustInfo(String guideId) {
		AjaxJson j = new AjaxJson();
		message = "回收顾客资料成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			this.tFocusCustomerService.revokeCustInfo(retailerId,guideId);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "回收顾客资料失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
}

