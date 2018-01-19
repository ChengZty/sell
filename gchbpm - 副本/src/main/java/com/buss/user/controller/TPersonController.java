package com.buss.user.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.web.system.pojo.base.TSTerritory;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.jeecgframework.web.system.vo.AreaVo;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.BeanUtils;
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
import com.buss.base.entity.BaseQuestionsEntity;
import com.buss.base.entity.TBaseTagsEntity;
import com.buss.count.vo.GuideGoodsCountVo;
import com.buss.kuaidi.util.MD5;
import com.buss.shop.entity.TPosterEntity;
import com.buss.store.entity.TStoreEntity;
import com.buss.user.datahandler.PersonDataHandler;
import com.buss.user.entity.TPersonAnswersEntity;
import com.buss.user.entity.TPersonEntity;
import com.buss.user.entity.TUserTagStoreEntity;
import com.buss.user.entity.vo.QuestionAnswerVo;
import com.buss.user.service.TPersonAnswersServiceI;
import com.buss.user.service.TPersonServiceI;
import com.buss.user.vo.CustTagsVo;

import cn.redis.service.RedisService;



/**   
 * @Title: Controller
 * @Description: 用户信息表
 * @author onlineGenerator
 * @date 2016-03-10 14:29:30
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPersonController")
public class TPersonController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPersonController.class);
	@Autowired
	private TPersonAnswersServiceI tPersonAnswersService;
	@Autowired
	private TPersonServiceI tPersonService;
	@Autowired
	private UserService userService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RedisService redisService;
	private String message;
	private final String TEST_GUIDE_QR_TOKEN = "testGuideQrToken";//导购二维码口令
	private final String TEST_GUIDE_ACCOUNT_TOKEN = "testGuideAccountToken";//导购帐号token
	private final String TEST_GUIDE_PHONE_NO = "testGuidePhoneNo";//测试导购的手机号
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 导购列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "guideList")
	public ModelAndView guideList(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/tPersonGuideList");
	}
	@RequestMapping(params = "guideOfRetailList")
	public ModelAndView guideOfRetailList(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
		request.setAttribute("retailer_Id", retailerId);
		return new ModelAndView("com/buss/user/tPersonGuideOfRetailList");
	}
	/**
	 * 交易顾客列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "customerList")
	public ModelAndView customerList(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/tPersonCustomerList");
	}
	@RequestMapping(params = "customerOfRetailList")
	public ModelAndView customerOfRetailList(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/tPersonCusOfRetailList");
	}
	
	/**
	 * 导购分配优惠券表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "guideOfTicketList")
	public ModelAndView guideOfTicketList(HttpServletRequest request) {
		return new ModelAndView("com/buss/user/tPersonGuideOfTicketList");
	}
	
	/**
	 * 指定零售商导购列表
	 * @return
	 */
	@RequestMapping(params = "guideListOfRetailer")
	public ModelAndView guideListOfRetailer(HttpServletRequest request) {
//		request.setAttribute("retailer_Id", request.getParameter("retailer_Id"));
		return new ModelAndView("com/buss/user/tGuideList");
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
	public void datagrid(TPersonEntity tPerson,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPersonEntity.class, dataGrid);
		String retailerId = ResourceUtil.getRetailerId();
		String userType = request.getParameter("user_Type") ;//03：导购
		String normal = request.getParameter("normal") ;//Y:非专家达人（分配代金券的时候要排除）
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPerson, request.getParameterMap());
		try{
		//自定义追加查询条件
			if(StringUtil.isNotEmpty(userType)){
				cq.eq("userType", userType);
			}
			if("Y".equals(normal)){
				cq.eq("hasTags", "0");
			}
			if(StringUtil.isNotEmpty(retailerId)){
				cq.eq("toRetailerId", retailerId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPersonService.getDataGridReturn(cq, true);
		List<TPersonEntity> list = dataGrid.getResults();
		if(Utility.isNotEmpty(list)){
			for(TPersonEntity entity : list){
				entity.setPhoto(entity.getPhoto()+"?imageView2/1/w/80/h/80");
			}
		}
		TagUtil.datagrid(response, dataGrid);
		/*try{
			StringBuilder countSql = new StringBuilder();
			countSql.append(" select count(1) from t_person p left join t_store s on p.store_id = s.id  and s.status = '").append(GlobalConstants.STATUS_ACTIVE).append("' where p.status = '").append(GlobalConstants.STATUS_ACTIVE).append("'");
			StringBuilder sql = new StringBuilder();
			sql.append(" select ")
			.append(" p.id  as id ,")
			.append(" p.user_id  as userId ,")
			.append(" p.photo  as photo ,")
			.append(" p.real_name  as realName ,")
			.append(" IFNULL(p.name,'')  as name ,")
			.append(" p.sex  as sex ,")
			.append(" p.phone_no  as phoneNo ,")
			.append(" IFNULL(p.id_card,'')  as idCard ,")
			.append(" IFNULL(p.birthday,'') as birthday ,")
			.append(" p.province_id  as provinceId ,")
			.append(" p.city_id  as cityId ,")
			.append(" p.area  as area ,")
			.append(" p.quantity  as quantity ,")
			.append(" p.money  as money ,")
			.append(" p.to_guide_id  as toGuideId ,")
			.append(" p.has_tags  as hasTags ,")
			.append(" p.can_bind  as canBind ,")
			.append(" p.create_date  as createDate ,")//为了根据创建时间排序，加此字段
			.append(" IFNULL(s.name,'')  as storeName ")//实体店铺
			.append(" from t_person p left join t_store s on p.store_id = s.id  and s.status = '").append(GlobalConstants.STATUS_ACTIVE).append("'")
			.append(" where p.status = '").append(GlobalConstants.STATUS_ACTIVE).append("'");
			if(StringUtil.isNotEmpty(userType)){
				sql.append(" and p.user_type = '").append(userType).append("'");
				countSql.append(" and p.user_type = '").append(userType).append("'");
			}
			if("Y".equals(normal)){
				sql.append(" and p.has_tags = '0'");
				countSql.append(" and p.and has_tags = '0'");
			}
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
				sql.append(" and p.to_retailer_id = '").append(user.getId()).append("'");
				countSql.append("  and p.to_retailer_id = '").append(user.getId()).append("'");
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				sql.append(" and p.to_retailer_id = '").append(user.getRetailerId()).append("'");
				countSql.append(" and p.to_retailer_id = '").append(user.getRetailerId()).append("'");
			}
			
			String sort = dataGrid.getSort();
			if(StringUtil.isEmpty(sort)){
				sql.append(" order by p.create_date desc");
			}else{
				sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder());
			}
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
	//			resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TSmsSendInfoEntity.class);//时间显示有问题
				resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
	//			this.setReachAndClickNum(resultList);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);*/
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	
	@RequestMapping(params = "datagridOfGuides")
	public void datagridOfGuides(TPersonEntity tPerson,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPersonEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPerson, request.getParameterMap());
		try{
			//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("toRetailerId", retailerId);
			}
			cq.eq("userType", TSUser.USER_TYPE_03);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPersonService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	/**零售商公司通知选择导购
	 * @param tPerson
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridGuide")
	public void datagridGuide(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
		try{
			Map<String,String> sqlMap = this.getQuerySql(request,dataGrid);
			String sql = sqlMap.get("sql");
			String countSql = sqlMap.get("countSql");
			List<TPersonEntity> resultList = new ArrayList<TPersonEntity>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TPersonEntity.class);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**获取查询的sql和countSql
	 * @param request
	 * @param dataGrid
	 * @return
	 */
	private Map<String,String> getQuerySql(HttpServletRequest request,DataGrid dataGrid) {
		Map<String,String> map = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String noticeId = request.getParameter("noticeId");//通知id
		String realName = request.getParameter("realName");//导购姓名
		String phoneNo = request.getParameter("phoneNo");//手机号
		String storeId = request.getParameter("storeId");//店铺id
		StringBuffer sql = new StringBuffer("select p.id,p.real_Name realName,p.phone_no phoneNo,p.store_id storeId,p.user_id userId ,create_date createDate from t_person p where p.status = 'A' and p.to_retailer_id = '")
				.append(retailerId).append("' and p.user_type = '").append(TSUser.USER_TYPE_03)
				.append("' and p.user_id not in (select user_id from t_s_notice_store_authority_user where notice_id = '").append(noticeId).append("')");
		StringBuffer countSql = new StringBuffer("select count(1) from t_person p where p.status = 'A' and p.to_retailer_id = '")
				.append(retailerId).append("' and p.user_type = '").append(TSUser.USER_TYPE_03)
				.append("' and p.user_id not in (select user_id from t_s_notice_store_authority_user where notice_id = '").append(noticeId).append("')");
		if(Utility.isNotEmpty(storeId)){
			sql.append(" and p.store_id = '").append(storeId).append("'");
			countSql.append(" and p.store_id = '").append(storeId).append("'");
		}
		if(Utility.isNotEmpty(realName)){
			sql.append(" and p.real_Name like '%").append(realName).append("%'");
			countSql.append(" and p.real_Name like '%").append(realName).append("%'");
		}
		if(Utility.isNotEmpty(phoneNo)){
			sql.append(" and p.phone_no like '%").append(phoneNo).append("%'");
			countSql.append(" and p.phone_no like '%").append(phoneNo).append("%'");
		}
		String sortName = dataGrid.getSort();
		if(Utility.isNotEmpty(sortName)){
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		map.put("sql", sql.toString());
		map.put("countSql", countSql.toString());
		return map;
	}

	/**
	 * 删除用户信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPersonEntity tPerson, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPerson = systemService.flushEntity(TPersonEntity.class, tPerson.getId());
		message = "用户信息表删除成功";
		try{
			tPerson.setStatus(common.GlobalConstants.STATUS_INACTIVE );
			tPerson.setUpdateDate(DateUtils.gettimestamp());
			tPersonService.saveOrUpdate(tPerson);
//			tPersonService.delete(tPerson);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除用户信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "用户信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TPersonEntity tPerson = systemService.flushEntity(TPersonEntity.class, id );
				tPerson.setStatus(common.GlobalConstants.STATUS_INACTIVE );
				tPerson.setUpdateDate(DateUtils.gettimestamp());
				tPersonService.saveOrUpdate(tPerson);
//				tPersonService.delete(tPerson);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "用户信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加导购，并同时生成帐号
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAddGuide(TPersonEntity tPerson, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购信息添加成功";
		try{
			//用户表新增导购
			Long num = systemService.getCountForJdbc("select count(1) from t_s_user where status = 'A' and user_type = '03' and user_status <>'4' and username ='"+tPerson.getPhoneNo()+"'");
//			TSUser users = systemService.findUniqueByProperty(TSUser.class, "userName",tPerson.getPhoneNo());
			if(num>0){
				message="导购帐号已存在";
			}else{
				tPersonService.saveGuide(tPerson,null);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "导购信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	
	/**
	 * 更新导购信息
	 * 如果有更新店铺ID，则把日销售报表的店铺ID同步更新
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdateGuide")
	@ResponseBody
	public AjaxJson doUpdateGuide(TPersonEntity tPerson, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "导购信息更新成功";
		try {
			tPersonService.doUpdateGuide(tPerson);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "导购信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "doUpdateCustomer")
	@ResponseBody
	public AjaxJson doUpdateCustomer(TPersonEntity tPerson, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "顾客信息更新成功";
		TPersonEntity t = tPersonService.get(TPersonEntity.class, tPerson.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPerson, t);
			tPersonService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "顾客信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 用户信息表新导购增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAddGuide")
	public ModelAndView goAddGuide(TPersonEntity tPerson, HttpServletRequest req) {
		String retailerId = ResourceUtil.getRetailerId();
		if (StringUtil.isNotEmpty(tPerson.getId())) {
			tPerson = tPersonService.flushEntity(TPersonEntity.class, tPerson.getId());
			req.setAttribute("tPersonPage", tPerson);
		}
		List<AreaVo> list = getAreaList();
		req.setAttribute("areaList", list);
		String storeHql = "from TStoreEntity where retailerId = '"+retailerId+"' and status = 'A'";
		//店铺列表
		List<TStoreEntity> storeList = this.systemService.findByQueryString(storeHql);
		req.setAttribute("storeList", storeList);
		return new ModelAndView("com/buss/user/tPersonGuide-add");
	}
	/**
	 * 用户信息表新增客户页面跳转
	 * 
	 * @return
	 */
//	@RequestMapping(params = "goAddCustomer")
//	public ModelAndView goAddCustomer(TPersonEntity tPerson, HttpServletRequest req) {
//		if (StringUtil.isNotEmpty(tPerson.getId())) {
//			tPerson = tPersonService.getEntity(TPersonEntity.class, tPerson.getId());
//			req.setAttribute("tPersonPage", tPerson);
//		}
//		TSUser user = ResourceUtil.getSessionUserName();
//		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){//零售商或者他录的用户
//				req.setAttribute("to_retiailer_id", user.getId());
//		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
//			req.setAttribute("to_retiailer_id", user.getRetailerId());
//		}
//		return new ModelAndView("com/buss/user/tPersonCustomer-add");
//	}
	/**
	 * 用户信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdateGuide")
	public ModelAndView goUpdateGuide(TPersonEntity tPerson, HttpServletRequest req) {
		String retailerId = ResourceUtil.getRetailerId();
		if (StringUtil.isNotEmpty(tPerson.getId())) {
			tPerson = tPersonService.flushEntity(TPersonEntity.class, tPerson.getId());
			req.setAttribute("tPersonPage", tPerson);
		}
		String storeHql = "from TStoreEntity where retailerId = '"+retailerId+"' and status = 'A'";
		//店铺列表
		List<TStoreEntity> storeList = this.systemService.findByQueryString(storeHql);
		req.setAttribute("storeList", storeList);
		List<AreaVo> list = getAreaList();
		req.setAttribute("areaList", list);
		if(StringUtil.isNotEmpty(tPerson.getProvinceId())){
			List<AreaVo> citysList = getAreaByParentIdList(tPerson.getProvinceId());
			req.setAttribute("citysList", citysList);
		}
		return new ModelAndView("com/buss/user/tPersonGuide-update");
	}
	/**
	 * 查看导购详细信息
	 * @return
	 */
	@RequestMapping(params = "goViewGuide")
	public ModelAndView goViewGuide(TPersonEntity tPerson, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPerson.getId())) {
			tPerson = tPersonService.get(TPersonEntity.class, tPerson.getId());
			String userId = tPerson.getUserId();//userid
			try {
				//获取问题列表
				List<BaseQuestionsEntity> questionList = null;
				//获取答案列表
				List<TPersonAnswersEntity> answerList = null;
				
				String hql = "from BaseQuestionsEntity where  maintanceType='1' order by questionSort asc";
				questionList = this.systemService.findByQueryString(hql);
				//获取答案列表
				answerList = this.tPersonAnswersService.findByProperty(TPersonAnswersEntity.class, "userId", userId);
				//把问题和答案组合成voList
				List<QuestionAnswerVo> voList = this.fillVo(userId,questionList,answerList);
				req.setAttribute("voList", voList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			req.setAttribute("tPersonPage", tPerson);
		}
		return new ModelAndView("com/buss/user/tPersonGuide-view");
	}
	
	/**
	 * 顾客信息修改
	 * @return
	 */
	@RequestMapping(params = "goUpdateCustomer")
	public ModelAndView goUpdateCustomer(TPersonEntity tPerson, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPerson.getId())) {
			tPerson = tPersonService.flushEntity(TPersonEntity.class, tPerson.getId());
			req.setAttribute("tPersonPage", tPerson);
		}
		return new ModelAndView("com/buss/user/tPersonCustomer-update");
	}
	
	/**
	 * 查看顾客详细信息
	 * @return
	 */
	@RequestMapping(params = "goViewCustomer")
	public ModelAndView goViewCustomer(TPersonEntity tPerson, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPerson.getId())) {
			tPerson = tPersonService.get(TPersonEntity.class, tPerson.getId());
			String customerId = tPerson.getUserId();//userid
//			TPersonDetailEntity personDetail = systemService.findUniqueByProperty(TPersonDetailEntity.class, "userId",userId);
//			PersonLongDetailsVO vo = new PersonLongDetailsVO();
			try {
				String userType = "2";
				String q_hql = "from TBaseTagsEntity where toUserType='"+userType+"'  order by tagSort asc";
				List<TBaseTagsEntity> tagList = this.systemService.findByQueryString(q_hql);
				//获取答案列表
				String a_hql = "from TUserTagStoreEntity where  customerId='"+customerId+"' and toUserType='"+userType+"'";
				List<TUserTagStoreEntity> tagStoreList = this.systemService.findByQueryString(a_hql);
				//把问题和答案组合成voList
				List<CustTagsVo> voList = this.fillTagVo(customerId,userType,tagList,tagStoreList);
				req.setAttribute("voList", voList);
//				//获取问题列表
//				List<BaseQuestionsEntity> questionList = null;
//				//获取答案列表
//				List<TPersonAnswersEntity> answerList = null;
//				
//				String hql = "from BaseQuestionsEntity where  maintanceType='2' order by questionSort asc";
//				questionList = this.systemService.findByQueryString(hql);
//				//获取答案列表
//				answerList = this.tPersonAnswersService.findByProperty(TPersonAnswersEntity.class, "userId", userId);
//				//把问题和答案组合成voList
//				List<QuestionAnswerVo> voList = this.fillVo(userId,questionList,answerList);
//				req.setAttribute("voList", voList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			req.setAttribute("tPersonPage", tPerson);
		}
		return new ModelAndView("com/buss/user/tPersonCustomer-view");
	}
	
	private List<CustTagsVo> fillTagVo(String customerId,String toUserType,List<TBaseTagsEntity> tagList,List<TUserTagStoreEntity> tagStoreList) {
		List<CustTagsVo> voList = null;
		if(!Utility.isEmpty(tagList)){
			voList = new ArrayList<CustTagsVo>();
			for(TBaseTagsEntity t : tagList){
				CustTagsVo vo = new CustTagsVo();
				BeanUtils.copyProperties(t, vo);
				if(!Utility.isEmpty(tagStoreList)){//修改标签列表
					for(TUserTagStoreEntity s : tagStoreList){
						if(t.getTagCode().equals(s.getTagCode())){//code
							vo.setTagStore(s);
							break;
						}
					}
				}else{//第一次进入标签列表
					TUserTagStoreEntity store = new TUserTagStoreEntity();
					store.setCustomerId(customerId);
//					store.setToUserType(toUserType);
					store.setTagCode(t.getTagCode());
					vo.setTagStore(store);
				}
				if(Utility.isEmpty(vo.getTagStore())){//新增的标签
					TUserTagStoreEntity store = new TUserTagStoreEntity();
					store.setCustomerId(customerId);
					store.setTagCode(t.getTagCode());
					vo.setTagStore(store);
				}
				voList.add(vo);
			}
		}
		return voList;
	}
	
	/**把问题和答案组合成vo
	 * @param userId
	 * @param questionList  问题
	 * @param answerList	答案
	 * @return
	 */
	private List<QuestionAnswerVo> fillVo(String userId,List<BaseQuestionsEntity> questionList,List<TPersonAnswersEntity> answerList) {
		TPersonEntity person = systemService.findUniqueByProperty(TPersonEntity.class, "userId", userId);
		List<QuestionAnswerVo> voList = null;
		if(!Utility.isEmpty(questionList)){
			voList = new ArrayList<QuestionAnswerVo>();
			for(BaseQuestionsEntity q : questionList){
				QuestionAnswerVo vo = new QuestionAnswerVo();
				BeanUtils.copyProperties(q, vo);
				if(!Utility.isEmpty(answerList)){//修改问题列表
					for(TPersonAnswersEntity a : answerList){
						if(q.getQuestionValue().equals(a.getQuestionValue())){
							vo.setAnswers(a);
							break;
						}
					}
				}else{//第一次进入问题列表
					TPersonAnswersEntity ans = new TPersonAnswersEntity();
					ans.setUserId(userId);
					ans.setQuestionValue(q.getQuestionValue());
					vo.setAnswers(ans);
				}
				if(Utility.isEmpty(vo.getAnswers())){//新增的问题
					TPersonAnswersEntity ans = new TPersonAnswersEntity();
					ans.setUserId(userId);
					ans.setQuestionValue(q.getQuestionValue());
					vo.setAnswers(ans);
				}
				String val = vo.getAnswers().getAnswerValue();
				if(q.getQuestionName().indexOf("昵称") != -1 && Utility.isNotEmpty(person.getName())){
					val = person.getName();
				}
				if(q.getQuestionName().indexOf("姓名") != -1 && Utility.isNotEmpty(person.getRealName())){
					val = person.getRealName();
				}
				if(q.getQuestionName().indexOf("身份证") != -1 && Utility.isNotEmpty(person.getIdCard())){
					val = person.getIdCard();
				}
				if(q.getQuestionName().indexOf("出生日期") != -1 && Utility.isNotEmpty(person.getBirthday())){
					val = DateUtils.date2Str(person.getBirthday(), DateUtils.date_sdf);
				}
				if(q.getQuestionName().indexOf("属相") != -1 && Utility.isNotEmpty(person.getZodiac())){
					val = person.getZodiac();
				}
				if(q.getQuestionName().indexOf("星座") != -1 && Utility.isNotEmpty(person.getConstellation())){
					val = person.getConstellation();
				}
				if(q.getQuestionName().indexOf("送给顾客的一句话") != -1 && Utility.isNotEmpty(person.getSignature())){
					val = person.getSignature();
				}
				vo.getAnswers().setAnswerValue(val);//答案
				voList.add(vo);
			}
		}
		return voList;
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tPersonController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportCustXls")
	public String exportCustXls(TPersonEntity tPerson,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPersonEntity.class, dataGrid);
		TSUser user = ResourceUtil.getSessionUserName();
		if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){
			cq.eq("toRetailerId", user.getId());
		}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
			cq.eq("toRetailerId", user.getRetailerId());
		}
		cq.eq("userType", common.GlobalConstants.USER_TYPE_04);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPerson, request.getParameterMap());
		List<TPersonEntity> tPersons = this.tPersonService.getListByCriteriaQuery(cq,true);
		modelMap.put(NormalExcelConstants.FILE_NAME,"顾客信息表");
		modelMap.put(NormalExcelConstants.CLASS,TPersonEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("顾客列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPersons);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "exportGuideXls")
	public String exportGuideXls(TPersonEntity tPerson,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPersonEntity.class, dataGrid);
		String retailerId = ResourceUtil.getRetailerId();
		List<TStoreEntity> storeList = null;
		if(Utility.isNotEmpty(retailerId)){
			cq.eq("toRetailerId", retailerId);
			storeList = this.systemService.findHql("from TStoreEntity where retailerId = ?", retailerId);
		}else{//平台导出
			storeList = this.systemService.loadAll(TStoreEntity.class);
		}
		cq.eq("userType", common.GlobalConstants.USER_TYPE_03);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPerson, request.getParameterMap());
		List<TPersonEntity> tPersons = this.tPersonService.getListByCriteriaQuery(cq,true);
		if(tPersons.size()>0&&storeList.size()>0){
			for(TPersonEntity entity : tPersons){
				if(Utility.isNotEmpty(entity.getStoreId())){
					for(TStoreEntity store : storeList){
						if(entity.getStoreId().equals(store.getId())){
							entity.setStoreId(store.getName());
							break;
						}
					}
				}
			}
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"导购表");
		modelMap.put(NormalExcelConstants.CLASS,TPersonEntity.class);
		ExportParams pars = new ExportParams("导购列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息");
		pars.setExclusions(new String[]{"所属导购"});
		modelMap.put(NormalExcelConstants.PARAMS,pars);
		modelMap.put(NormalExcelConstants.DATA_LIST,tPersons);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	/**
	 * 导出excel
	 * 导出导购问题列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportGuideQuestionXls")
	public void exportGuideQuestionXls(TPersonEntity tPerson,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		try
		{
			//获取问题列表
			List<BaseQuestionsEntity> questionList = null;
			//maintanceType 1 导购   2顾客
			String hql = " from BaseQuestionsEntity where  maintanceType='1' order by questionSort asc";
			questionList = this.systemService.findByQueryString(hql);
			List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
			if(!Utility.isEmpty(questionList)){
				entity.add(new ExcelExportEntity("用户编码", "userId"));
//				.replaceAll("（填写项）", "").replaceAll("（单选项）", "").replaceAll("（选择日期）", "").replaceAll("（多选项）", "")
				for(BaseQuestionsEntity q : questionList){
					entity.add(new ExcelExportEntity(q.getQuestionName(), q.getQuestionValue()));
				}
			}
			
			CriteriaQuery cq = new CriteriaQuery(TPersonEntity.class, dataGrid);
			TSUser user = ResourceUtil.getSessionUserName();
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){
				cq.eq("toRetailerId", user.getId());
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				cq.eq("toRetailerId", user.getRetailerId());
			}
			cq.eq("userType", common.GlobalConstants.USER_TYPE_03);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPerson, request.getParameterMap());
			List<TPersonEntity> tPersons = this.tPersonService.getListByCriteriaQuery(cq,true);
			//获取答案列表
			List<TPersonAnswersEntity> answerList = null;
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			if(!Utility.isEmpty(tPersons)){
				StringBuffer answerHql = new StringBuffer(" from TPersonAnswersEntity where userId in(");
				for (TPersonEntity personEntity : tPersons) {
					answerHql.append("'").append(personEntity.getUserId()).append("',");
				}
				answerHql.deleteCharAt(answerHql.lastIndexOf(","));
				answerHql.append(")");
				answerList = this.systemService.findHql(answerHql.toString(), null);
				Map<String,String> answerMap = new HashMap<String, String>();
				if(!Utility.isEmpty(answerList))
				{	
					for (TPersonAnswersEntity answer : answerList) {
						answerMap.put(answer.getUserId()+answer.getQuestionValue(), answer.getAnswerValue());
					}
				}
				if(!Utility.isEmpty(questionList)){
					int questionSize = questionList.size();
					int tPersonSize = tPersons.size();
					for(int j = 0;j<tPersonSize;j++)
					{
						TPersonEntity personEntity = tPersons.get(j);
						Map<String, String> map = new HashMap<String, String>();
						map.put("userId", personEntity.getUserId());
						for (int i = 0; i < questionSize; i++) {
							BaseQuestionsEntity q = questionList.get(i);
							String value = answerMap.get(personEntity.getUserId()+q.getQuestionValue());
							value = value ==null?"":value;
					        map.put(q.getQuestionValue(), value);
						}
						list.add(map);
					}
				}
			}
		    String codedFileName = "导购问题表";
		    Workbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(new ExportParams("导购问题列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"问题信息"), entity,list);
	        if (workbook instanceof HSSFWorkbook) {
	            codedFileName += ".xls";
	        } else {
	            codedFileName += ".xlsx";
	        }
	        if ((request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 || request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0) ? true : false) {
	            codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF8");
	        } else {
	            codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
	        }
	        response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
	        ServletOutputStream out;
			out = response.getOutputStream();
			workbook.write(out);
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导出excel
	 * 导出顾客问题列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportCustQuestionXls")
	public void exportCustQuestionXls(TPersonEntity tPerson,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		try
		{
			//获取问题列表
			List<BaseQuestionsEntity> questionList = null;
			//maintanceType 1 导购   2顾客
			String hql = " from BaseQuestionsEntity where  maintanceType='2' order by questionSort asc";
			questionList = this.systemService.findByQueryString(hql);
			List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
			if(!Utility.isEmpty(questionList)){
				entity.add(new ExcelExportEntity("用户编码", "userId"));
//				.replaceAll("（填写项）", "").replaceAll("（单选项）", "").replaceAll("（选择日期）", "").replaceAll("（多选项）", "")
				for(BaseQuestionsEntity q : questionList){
					entity.add(new ExcelExportEntity(q.getQuestionName(), q.getQuestionValue()));
				}
			}

			CriteriaQuery cq = new CriteriaQuery(TPersonEntity.class, dataGrid);
			TSUser user = ResourceUtil.getSessionUserName();
			if(common.GlobalConstants.USER_TYPE_02.equals(user.getUserType())){
				cq.eq("toRetailerId", user.getId());
			}else if(common.GlobalConstants.USER_TYPE_05.equals(user.getUserType())){
				cq.eq("toRetailerId", user.getRetailerId());
			}
			cq.eq("userType", common.GlobalConstants.USER_TYPE_04);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPerson, request.getParameterMap());
			List<TPersonEntity> tPersons = this.tPersonService.getListByCriteriaQuery(cq,true);
			//获取答案列表
			List<TPersonAnswersEntity> answerList = null;
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			if(!Utility.isEmpty(tPersons)){
				StringBuffer answerHql = new StringBuffer(" from TPersonAnswersEntity where userId in(");
				for (TPersonEntity personEntity : tPersons) {
					answerHql.append("'").append(personEntity.getUserId()).append("',");
				}
				answerHql.deleteCharAt(answerHql.lastIndexOf(","));
				answerHql.append(")");
				answerList = this.systemService.findHql(answerHql.toString(), null);
				Map<String,String> answerMap = new HashMap<String, String>();
				if(!Utility.isEmpty(answerList))
				{	
					for (TPersonAnswersEntity answer : answerList) {
						answerMap.put(answer.getUserId()+answer.getQuestionValue(), answer.getAnswerValue());
					}
				}
				if(!Utility.isEmpty(questionList)){
					int questionSize = questionList.size();
					int tPersonSize = tPersons.size();
					for(int j = 0;j<tPersonSize;j++)
					{
						TPersonEntity personEntity = tPersons.get(j);
						Map<String, String> map = new HashMap<String, String>();
						map.put("userId", personEntity.getUserId());
						for (int i = 0; i < questionSize; i++) {
							BaseQuestionsEntity q = questionList.get(i);
							String value = answerMap.get(personEntity.getUserId()+q.getQuestionValue());
							value = value ==null?"":value;
					        map.put(q.getQuestionValue(), value);
						}
						list.add(map);
					}
				}
			}
		    String codedFileName = "顾客问题表";
		    Workbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(new ExportParams("顾客问题列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"问题信息"), entity,list);
	        if (workbook instanceof HSSFWorkbook) {
	            codedFileName += ".xls";
	        } else {
	            codedFileName += ".xlsx";
	        }
	        if ((request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 || request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0) ? true : false) {
	            codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF8");
	        } else {
	            codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
	        }
	        response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
	        ServletOutputStream out;
			out = response.getOutputStream();
			workbook.write(out);
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPersonEntity tPerson,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
//    	modelMap.put(NormalExcelConstants.FILE_NAME,"导购导入表");
//    	modelMap.put(NormalExcelConstants.CLASS,guideTempVo.class);
//    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("导购导入表", "导入人:"+ResourceUtil.getSessionUserName().getRealName(),
//    	"导入信息"));
//    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
//    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
    	
    	String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\templates");
		String fileName = "导购导入模板.xlsx";
		String filePath = path + "\\" + fileName;
		try {
			FileUtils.downLoadFile(filePath, response, request, fileName, "xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**批量导入导购*/
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
//			handler.setNeedHandlerFields(new String[]{"身份证号","手机号码"});
			handler.setNeedHandlerFields(new String[]{"生日"});
			params.setDataHanlder(handler);
			try {
				List<TPersonEntity> listTPersonEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TPersonEntity.class,params);
				if(!Utility.isEmpty(listTPersonEntitys)){
					HashSet<String> set = new HashSet<String>();//导入的excel中的手机号集合
					String msg = checkImportPhoneNo(listTPersonEntitys,set);
					//导入excel列表中有重复的号码
					if(!StringUtil.isEmpty(msg)){
						j.setMsg(msg);
					}else{
						//校验手机号
						msg = checkDatabasePhoneNo(set);
						//数据库中已经存在该导购号码
						if(!StringUtil.isEmpty(msg)){
							j.setMsg("数据库中已经存在手机号："+msg);
//							j.setSuccess(false);
						}else{
							msg = tPersonService.batchSaveGuides(listTPersonEntitys);
							j.setMsg(msg);
						}
					}
				}
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
	

	/**检查导入的手机号是否有重复,返回重复的号码，否则返回 null,并把号码存在set中
	 * 检查登记店铺是否录入
	 * @param listTPersonEntitys
	 * @return
	 */
	private String checkImportPhoneNo(List<TPersonEntity> listTPersonEntitys,HashSet<String> set) {
		int n =3;
		//店铺列表
		List<TStoreEntity> storeList = this.systemService.findHql("from TStoreEntity where retailerId = ?", ResourceUtil.getRetailerId());
		for(TPersonEntity entity:listTPersonEntitys){
			String phoneNo = entity.getPhoneNo();
			String storeName = entity.getStoreId();//登记店铺
			if(Utility.isEmpty(phoneNo)){//手机号为空
				return "第"+n+"行手机号码不能为空";
			}else{
				phoneNo = phoneNo.trim().replaceAll("-", "").replaceAll(" ", "");
				entity.setPhoneNo(phoneNo);
				if(!Utility.isMobile(phoneNo)){//不合法
					return "第"+n+"行手机号码不合法";
				}else{
					if(set.contains(entity.getPhoneNo())){
						return "第"+n+"行手机号码"+phoneNo+"重复";
					}else{
						set.add(entity.getPhoneNo());
					}
				}
			}
			if(Utility.isEmpty(storeName)){
				return "第"+n+"行登记店铺不能为空";
			}else{
				Boolean exist = false;
				//获取登记店铺
					for(TStoreEntity store : storeList){
						if(store.getName().equalsIgnoreCase(storeName.trim())){
							exist = true;
							entity.setStoreId(store.getId());//登记店铺ID
							break;
						}
					}
					if(!exist){
						return "第"+n+"行登记店铺："+storeName+" 系统中不存在";
					}
			}
			n++;
		}
		return null;
	}

	/**校验是否和数据库中的导购手机号有重复的，有则返回重复手机号，没有则返回null
	 * @param set 导入excel中的手机号
	 * @return
	 */
	private String checkDatabasePhoneNo( HashSet<String> set) {
		//所有未注销的导购
		List<Object> list = null;
		String sql = "select username from t_s_user where `status` = 'A' and user_type = '03' and user_status <>'4' and username in (";
		StringBuffer phoneNos = new StringBuffer();
		for(String phoneNo : set){
			phoneNos.append(",'").append(phoneNo).append("'");
		}
		
		list = this.systemService.findListbySql(sql+phoneNos.deleteCharAt(0)+")");
		if(list.size()>0){
			return list.get(0)+"";
		}
		return null;
	}
	
	/**获取所有省的ID和name
	 * @return
	 */
	public List<AreaVo> getAreaList(){
		List<AreaVo> list = new ArrayList<AreaVo>();
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		cq.eq("territoryLevel", (short)2);
		cq.add();
		List<TSTerritory> territoryList = systemService.getListByCriteriaQuery(cq, false);
		if(territoryList!=null){
			AreaVo blankVo = new AreaVo();
			blankVo.setAreaId("");
			blankVo.setAreaName("--请选择--");
			list.add(blankVo);
			for(TSTerritory ter:territoryList){
				AreaVo vo = new AreaVo();
				vo.setAreaId(ter.getId());
				vo.setAreaName(ter.getTerritoryName());
				list.add(vo);
			}
		}
		return list;
	}
	
	/**通过parentId获取所有市的ID和name
	 * @return
	 */
	public List<AreaVo> getAreaByParentIdList(String id){
		List<AreaVo> list = new ArrayList<AreaVo>();
		TSTerritory territory = systemService.get(TSTerritory.class, id);
		List<TSTerritory> territoryList = territory.getTSTerritorys();
		if(territoryList!=null){
			for(TSTerritory ter:territoryList){
				AreaVo vo = new AreaVo();
				vo.setAreaId(ter.getId());
				vo.setAreaName(ter.getTerritoryName());
				list.add(vo);
			}
		}
		return list;
	}
	
	/**
	 * 自动完成查询专家达人请求返回数据
	 * 
	 * @param request
	 * @param responss
	 */
	@RequestMapping(params = "getHelperAutoList")
	public void getHelperAutoList(HttpServletRequest request, HttpServletResponse response, Autocomplete autocomplete) {
		String q = request.getParameter("q");
//		String trem = StringUtil.getEncodePra(request.getParameter("trem"));// 重新解析参数
		autocomplete.setTrem(q);
		TSUser user = ResourceUtil.getSessionUserName();
		String hqlCondition = "  and userType = '"+common.GlobalConstants.USER_TYPE_03+"' and hasTags = '1' ";
		List autoList = this.userService.getAutoList(autocomplete,hqlCondition);
		hqlCondition = null;
//		List autoList = systemService.getAutoList(autocomplete);
		String labelFields = autocomplete.getLabelField();
		String[] fieldArr = labelFields.split(",");
		String valueField = autocomplete.getValueField();
		String[] allFieldArr = null;
		if (StringUtil.isNotEmpty(valueField)) {
			allFieldArr = new String[fieldArr.length+1];
			for (int i=0; i<fieldArr.length; i++) {
				allFieldArr[i] = fieldArr[i];
			}
			allFieldArr[fieldArr.length] = valueField;
		}
		
		try {
			String str = TagUtil.getAutoList(autocomplete, autoList);
			str = "(" + str + ")";
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(JSONHelper.listtojson(allFieldArr,allFieldArr.length,autoList));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * 不让顾客绑
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doCanNotBind")
	@ResponseBody
	public AjaxJson doCanNotBind(TPersonEntity tPerson, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "暂缓顾客绑定成功";
		try{
			tPerson = systemService.flushEntity(TPersonEntity.class, tPerson.getId());
			tPerson.setCanBind("0");
			tPerson.setUpdateDate(DateUtils.gettimestamp());
			tPersonService.saveOrUpdate(tPerson);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "暂缓顾客绑定绑失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 让顾客绑
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doCanBind")
	@ResponseBody
	public AjaxJson doCanBind(TPersonEntity tPerson, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "让顾客绑成功";
		try{
			tPerson = systemService.flushEntity(TPersonEntity.class, tPerson.getId());
			tPerson.setCanBind("1");
			tPerson.setUpdateDate(DateUtils.gettimestamp());
			tPersonService.saveOrUpdate(tPerson);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "让顾客绑失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 导购换馆及对应的基础数据复制
	 * @return
	 */
	@RequestMapping(params = "doChangeArea")
	@ResponseBody
	public AjaxJson doChangeArea(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "换馆成功";
		try{
			tPersonService.doChangeArea(request);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "换馆失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 通过店铺id获取person  id和导购的姓名realName
	 * @return
	 */
	 @RequestMapping(params = "getPersonsByStoreId")
	@ResponseBody
	public AjaxJson getPersonsByStoreId(String storeId,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "用户信息表删除成功";
		try{
			List<CommonVo> list = tPersonService.getPersonListByStoreId(TSUser.USER_TYPE_03, storeId);
			j.setObj(list);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	 /**
		 * 通过口令获取二维码（二维码有效期1天）
		 * @return
		 */
		@RequestMapping(params = "getQRCode")
		@ResponseBody
		public AjaxJson getQRCode(String token,HttpServletRequest request){
			AjaxJson j = new AjaxJson();
			message = "获取二维码成功";
			try{
				String testGuideQrToken = redisService.get(TEST_GUIDE_QR_TOKEN);
				if(Utility.isEmpty(testGuideQrToken)){
					testGuideQrToken = "666";
				}
				if(Utility.isNotEmpty(token)&&token.equals(testGuideQrToken)){
					ResourceBundle env = ResourceBundle.getBundle("env");
					Random ran1 = new Random(666);
					int rdmInt = ran1.nextInt(100000);
					String code = MD5.encode(rdmInt+"");//用于校验获取测试帐号页面是否有效
//					System.out.println(code);
					redisService.set(TEST_GUIDE_ACCOUNT_TOKEN, code,3600*24);//24小时失效
					String qcUrl = env.getObject("HT_URL")+"tPersonController.do?goTestAccountPage&token="+code;
					j.setObj(qcUrl);
				}else{
					j.setSuccess(false);
					message = "口令不正确";
				}
			}catch(Exception e){
				e.printStackTrace();
				message = "获取二维码成功失败";
				j.setSuccess(false);
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
	
		/**
		 * 获取导购测试帐号页面（测试帐号有效期3天）
		 * @return
		 */
		@RequestMapping(params = "goTestAccountPage")
		public ModelAndView goTestAccountPage(String token, HttpServletRequest request) {
			Boolean success = false;
			String msg = "该页面已失效";
			String localToken = redisService.get(TEST_GUIDE_ACCOUNT_TOKEN);
			Map<String,Object> map = new HashMap<String, Object>();
			//通过验证
			if(Utility.isNotEmpty(localToken)&&Utility.isNotEmpty(token)&&localToken.equals(token)){
				msg = "ok";
				success = true;
			}
			map.put("success", success);
			map.put("msg", msg);
			request.setAttribute("resultMap", map);
			request.setAttribute("token", token);
			return new ModelAndView("common/testAccountPage");
		}
	
		/**
		 * 获取测试帐号
		 * @return
		 */
		@RequestMapping(params = "getTestAccount")
		@ResponseBody
		public Map<String,Object> getTestAccount(String token,HttpServletRequest request) {
			Boolean success = false;
			message = "该页面已失效";
			String localToken = redisService.get(TEST_GUIDE_ACCOUNT_TOKEN);
			Map<String,Object> map = new HashMap<String, Object>();
			//通过验证
			if(Utility.isNotEmpty(localToken)&&Utility.isNotEmpty(token)&&localToken.equals(token)){
				try {
					//初始化一个导购测试帐号，666开头的11位数字
					String InitPhoneNo = redisService.get(TEST_GUIDE_PHONE_NO);
					if(Utility.isEmpty(InitPhoneNo)){
						InitPhoneNo = "18910000001";
						redisService.set(TEST_GUIDE_PHONE_NO, InitPhoneNo);
					}
					map.put("phoneNo", InitPhoneNo);
					this.tPersonService.doInitGuideAccount(map);
					redisService.incr(TEST_GUIDE_PHONE_NO);//手机号加1
					message = "导购测试帐号获取成功";
					success = true;
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					message = "导购测试帐号获取失败";
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
			map.put("success", success);
			map.put("msg", message);
			return map;
		}
	
 
	/**
	 * 增加or扣除导购金币数
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping(params = "doGoldToGuaid")
	@ResponseBody
	public AjaxJson doGoldToGuaid(HttpServletRequest request,
			HttpServletResponse response) {

		AjaxJson j = new AjaxJson();
	
		String guideId = request.getParameter("guideId");
		String retailerId = ResourceUtil.getRetailerId();
		Integer doType = Integer.parseInt(request.getParameter("doType"));
		String description = request.getParameter("description");
		Integer goldCount = Integer.parseInt(request.getParameter("goldCount"));
		String message = this.tPersonService.doGold(guideId, retailerId,
				doType, description, goldCount );
		j.setMsg(message);
		return j;
	}

	/**
	 * 查看导购金币流水记录页面跳转
	 */
	@RequestMapping(params = "viewGoldDetail")
	public ModelAndView viewGoldDetail(HttpServletRequest request) {

		request.setAttribute("guideId", request.getParameter("guideId"));
		return new ModelAndView("com/buss/user/tGuideGoldeDetail");
	}

	/**
	 * 操作导购金币 doGuideGold
	 */
	@RequestMapping(params = "doGuideGold")
	public ModelAndView actNewsList(HttpServletRequest request) {
		request.setAttribute("guideId", request.getParameter("guideId"));
		return new ModelAndView("com/buss/user/doGuideGold");
	}

	/**
	 * 查询导购金币流水记录
	 */
	@RequestMapping(params = "datagridOfGuideGoldDetail")
	public void datagridOfGuideGoldDetail(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {

		String queryCreateDateBegin = request.getParameter("createDate_begin");//创建时间
		String queryCreateDateEnd = request.getParameter("createDate_end");
		String guideId = request.getParameter("guideId");
		String retailerId = ResourceUtil.getRetailerId();
		this.tPersonService.datagridOfGuideGoldDetail(guideId, retailerId,
				dataGrid ,queryCreateDateBegin,queryCreateDateEnd);
		TagUtil.datagrid(response, dataGrid);
	}
	

	
}
