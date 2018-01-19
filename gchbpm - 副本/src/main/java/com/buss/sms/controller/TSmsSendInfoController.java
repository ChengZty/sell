package com.buss.sms.controller;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
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

import com.buss.goods.entity.TGoodsEntity;
import com.buss.shop.entity.TPosterEntity;
import com.buss.sms.entity.TSmsAutographEntity;
import com.buss.sms.entity.TSmsSendInfoEntity;
import com.buss.sms.entity.TSmsSendTemplateEntity;
import com.buss.sms.entity.TSmsSubAccountEntity;
import com.buss.sms.service.TSmsAutographServiceI;
import com.buss.sms.service.TSmsSendInfoServiceI;
import com.buss.store.entity.TStoreEntity;
import com.buss.store.service.TStoreServiceI;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 短信发送
 * @author onlineGenerator
 * @date 2017-02-18 12:15:41
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSmsSendInfoController")
public class TSmsSendInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSmsSendInfoController.class);

	@Autowired
	private TSmsSendInfoServiceI tSmsSendInfoService;
	@Autowired
	private TSmsAutographServiceI tSmsAutographService;
	@Autowired
	private TStoreServiceI tStoreService;
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
	 * 短信发送列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tSmsSendInfo")
	public ModelAndView tSmsSendInfo(HttpServletRequest request) {
		String retailerId = ResourceUtil.getRetailerId();
//		String surplusNumber = tSmsSendInfoService.balance(retailerId);
		TSmsSubAccountEntity account = this.systemService.findUniqueByProperty(TSmsSubAccountEntity.class, "retailerId", retailerId);
		String surplusNumber = "0";//短信剩余条数
		if(Utility.isNotEmpty(account)){
			if(Utility.isNotEmpty(account.getSmsNumber())&&Utility.isNotEmpty(account.getLockingNumber())){
				surplusNumber = account.getSmsNumber()-account.getLockingNumber()+"";//短信剩余条数;
			}
		}
		request.setAttribute("surplusNumber", surplusNumber);
		return new ModelAndView("com/buss/sms/tSmsSendInfoList");
	}

	/**
	 * 短信推送列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/sms/tSmsSendInfoList2");
	}
	
	/**
	 * 短信推送列表(全部零售商) 页面跳转
	 * @return
	 */
	@RequestMapping(params = "allList")
	public ModelAndView allList(HttpServletRequest request) {
		return new ModelAndView("com/buss/sms/tSmsSendInfoAllList");
	}
	
	/**
	 * easyui AJAX请求数据 零售商查询短信列表数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSmsSendInfoEntity tSmsSendInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		测试模拟短信返回消息ID并保存到redis
//		Map<String,String> msgMap = new HashMap<String, String>();
//		List<TSmsSendEntity> list = this.systemService.findByProperty(TSmsSendEntity.class, "sendInfoId", "2b2e9145a63044f882afd5316059696f");
//		StringBuffer str = new StringBuffer();
//		for(int i=0;i<list.size();i++){
//			TSmsSendEntity send = list.get(i);
//			send.setMsgId(i+"");
//			send.setSendStatus("1");
//			systemService.updateEntitie(send);
//			msgMap.put("msgId-"+i, send.getId());
//			str.append(i).append(",").append(send.getPhone()).append(",1,20170414;");
//		}
//		redisService.batchSet(msgMap, "NX", "EX", 3600*24);
//		String s = str.toString();
//		logger.info(s);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSmsSendInfo, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			StringBuffer sql = new StringBuffer("SELECT i.id,i.create_date createDate,i.batch_no batchNo,i.title,CONCAT('【',i.autograph_name,'】',i.content,i.content_end,i.content_end2) as  content  ,IFNULL(i.send_time,'') sendTime,i.send_time_type sendTimeType,")
			.append("i.send_status sendStatus,i.push_count pushCount,i.push_type pushType,")
			.append("i.reach,i.reach_rate reachRate,i.click_number clickNumber,i.click_rate clickRate,")
//			.append("(SELECT count(1) from t_sms_send s1 where s1.send_info_id = i.id and s1.receive_status='1' and s1.status = 'A') reach,")
//			.append("(SELECT count(1) from t_sms_send s2 where s2.send_info_id = i.id and s2.is_click='1' and s2.status = 'A') clickNumber,")
			.append("buy_single buySingle,buy_rate buyRate")
			.append(" from t_sms_send_info i  where i.retailer_id ='").append(retailerId).append("' and i.status = 'A'");
			StringBuffer countSql = new StringBuffer("select count(1) from t_sms_send_info i where i.retailer_id ='").append(retailerId).append("' and i.status = 'A' ");
			String batchNo = request.getParameter("batchNo");
			String title = request.getParameter("title");
			String sendTime_begin = request.getParameter("sendTime_begin");
			String sendTime_end = request.getParameter("sendTime_end");
			String sendTimeType = request.getParameter("sendTimeType");//发送类型
			String sendStatus = request.getParameter("sendStatus");//发送状态
			String pushType = request.getParameter("pushType");//推送类型
			if(!Utility.isEmpty(batchNo)){
				sql.append(" and i.batch_no like '%").append(batchNo).append("%'");
				countSql.append(" and i.batch_no like '%").append(batchNo).append("%'");
			}
			if(!Utility.isEmpty(title)){
				sql.append(" and i.title like '%").append(title).append("%'");
				countSql.append(" and i.title like '%").append(title).append("%'");
			}
			if(!Utility.isEmpty(sendTimeType)){
				sql.append(" and i.send_time_type = '").append(sendTimeType).append("'");
				countSql.append(" and i.send_time_type = '").append(sendTimeType).append("'");
			}
			if(!Utility.isEmpty(sendStatus)){
				sql.append(" and i.send_status = '").append(sendStatus).append("'");
				countSql.append(" and i.send_status = '").append(sendStatus).append("'");
			}
			if(!Utility.isEmpty(sendTime_begin)){
				sql.append(" and i.send_time >='").append(sendTime_begin).append(" 00:00:00'");
				countSql.append(" and i.send_time >='").append(sendTime_begin).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(sendTime_end)){
				sql.append(" and i.send_time <='").append(sendTime_end).append(" 23:59:59'");
				countSql.append(" and i.send_time <='").append(sendTime_end).append(" 23:59:59'");
			}
			if(!Utility.isEmpty(pushType)){
				sql.append(" and i.push_type = '").append(pushType).append("'");
				countSql.append(" and i.push_type = '").append(pushType).append("'");
			}
			String sort = dataGrid.getSort();
			if(StringUtil.isEmpty(sort)){
				sql.append(" order by i.send_time desc");
			}else{
				sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder());
			}
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			
//			List<TSmsSendInfoEntity> resultList = new ArrayList<TSmsSendInfoEntity>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
//				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),TSmsSendInfoEntity.class);//时间显示有问题
				resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
//				this.setReachAndClickNum(resultList);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
//		cq.add();
//		this.tSmsSendInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	

	/**
	 * easyui AJAX请求数据 平台查询短信列表数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridOfAll")
	public void datagridOfAll(TSmsSendInfoEntity tSmsSendInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
		//自定义追加查询条件
			StringBuffer sql = new StringBuffer("SELECT i.id,i.create_date createDate,(SELECT realname from t_s_user where id = i.retailer_id) realname,")
			.append("i.retailer_id retailerId,i.batch_no batchNo,i.title,CONCAT('【',i.autograph_name,'】',i.content,i.content_end,i.content_end2) as  content,IFNULL(i.send_time,'') sendTime,i.send_time_type sendTimeType,")
			.append("i.send_status sendStatus,i.push_count pushCount,i.push_type pushType,")
			.append("i.reach,i.reach_rate reachRate,i.click_number clickNumber,i.click_rate clickRate,")
//			.append("(SELECT count(1) from t_sms_send s1 where s1.send_info_id = i.id and s1.receive_status='1' and s1.status = 'A') reach,")
//			.append("(SELECT count(1) from t_sms_send s2 where s2.send_info_id = i.id and s2.is_click='1' and s2.status = 'A') clickNumber,")
			.append("buy_single buySingle,buy_rate buyRate")
			.append(" from t_sms_send_info i  where  i.status = 'A'");
			StringBuffer countSql = new StringBuffer("select count(1) from t_sms_send_info i where  i.status = 'A' ");
			String title = request.getParameter("title");
			String sendTimeType = request.getParameter("sendTimeType");
//			String realname = request.getParameter("realname");
			String sendStatus = request.getParameter("sendStatus");
			String sendTime_begin = request.getParameter("sendTime_begin");
			String sendTime_end = request.getParameter("sendTime_end");
			if(!Utility.isEmpty(title)){
				sql.append(" and i.title like '%").append(title).append("%'");
				countSql.append(" and i.title like '%").append(title).append("%'");
			}
			if(!Utility.isEmpty(sendTimeType)){
				sql.append(" and i.send_time_type = '").append(sendTimeType).append("'");
				countSql.append(" and i.send_time_type = '").append(sendTimeType).append("'");
			}
			if(!Utility.isEmpty(sendStatus)){
				sql.append(" and i.send_status = '").append(sendStatus).append("'");
				countSql.append(" and i.send_status = '").append(sendStatus).append("'");
			}
			if(!Utility.isEmpty(sendTime_begin)){
				sql.append(" and i.send_time >='").append(sendTime_begin).append(" 00:00:00'");
				countSql.append(" and i.send_time >='").append(sendTime_begin).append(" 00:00:00'");
			}
			if(!Utility.isEmpty(sendTime_end)){
				sql.append(" and i.send_time <='").append(sendTime_end).append(" 23:59:59'");
				countSql.append(" and i.send_time <='").append(sendTime_end).append(" 23:59:59'");
			}
			String sort = dataGrid.getSort();
			if(StringUtil.isEmpty(sort)){
				sql.append(" order by i.send_time desc");
			}else{
				sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder());
			}
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			int total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			if(total>0){
				resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除短信发送
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSmsSendInfoEntity tSmsSendInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
//		tSmsSendInfo = systemService.flushEntity(TSmsSendInfoEntity.class, tSmsSendInfo.getId());
		message = "短信删除成功";
		try{
			systemService.updateBySqlString("update t_sms_send set status='"+GlobalConstants.STATUS_INACTIVE+"' where send_info_id = '"+tSmsSendInfo.getId()+"'");
			systemService.updateBySqlString("update t_sms_send_info set status='"+GlobalConstants.STATUS_INACTIVE+"' where id = '"+tSmsSendInfo.getId()+"'");
//			tSmsSendInfo.setStatus(GlobalConstants.STATUS_INACTIVE);
//			tSmsSendInfoService.updateEntitie(tSmsSendInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除短信发送
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "短信批量删除成功";
		try{
			this.tSmsSendInfoService.doBatchDel(ids);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "短信批量删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加短信发送或者列表中发送短信并发送MQ消息发短信(短信确认发送)
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSmsSendInfoEntity tSmsSendInfo, HttpServletRequest request) {
		String sendStatus = request.getParameter("sendStatus");//列表中点击发送
		AjaxJson j = new AjaxJson();
		message = "短信发送添加成功";
		try{
			tSmsSendInfo = this.systemService.get(TSmsSendInfoEntity.class, tSmsSendInfo.getId());
			int smsNumber = tSmsSendInfo.getPushCount()*tSmsSendInfo.getMsgNum();
			TSmsSubAccountEntity account = this.systemService.findUniqueByProperty(TSmsSubAccountEntity.class, "retailerId", tSmsSendInfo.getRetailerId());
			if(Utility.isEmpty(account)){
				message = "请联系管理员开通短信帐号";
				j.setSuccess(false);
			}else if(smsNumber > (account.getSmsNumber()-account.getLockingNumber())){
				message = "短信条数不够，请联系g+充值";
				j.setSuccess(false);
			}else{//可以发送
				if(TSmsSendInfoEntity.SEND_STATUS_1.equals(tSmsSendInfo.getSendStatus())||TSmsSendInfoEntity.SEND_STATUS_1.equals(sendStatus)){
					this.tSmsSendInfoService.doAdd(tSmsSendInfo,smsNumber,account);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					message = "短信创建成功";
				}else{//SEND_STATUS_0:未发送，即存草稿(页面已保存过)
					message = "短信保存草稿成功";
				}
				j.setSuccess(true);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			message = "短信创建失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	

	/**
	 * 修改短信发送并发送MQ消息发短信(短信确认发送)
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSmsSendInfoEntity tSmsSendInfo, HttpServletRequest request) {
		String sendStatus = request.getParameter("sendStatus");//列表中点击发送
		AjaxJson j = new AjaxJson();
		message = "短信发送修改成功";
		try{
			int smsNumber = tSmsSendInfo.getPushCount()*tSmsSendInfo.getMsgNum();
			TSmsSubAccountEntity account = this.systemService.findUniqueByProperty(TSmsSubAccountEntity.class, "retailerId", tSmsSendInfo.getRetailerId());
			if(Utility.isEmpty(account)){
				message = "请联系管理员开通短信帐号";
				j.setSuccess(false);
			}else if(smsNumber > (account.getSmsNumber()-account.getLockingNumber())){
				message = "短信条数不够，请联系g+充值";
				j.setSuccess(false);
			}else{//可以发送
				TSmsSendInfoEntity tSmsSendInfoEntity = this.systemService.get(TSmsSendInfoEntity.class, tSmsSendInfo.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tSmsSendInfo, tSmsSendInfoEntity);
				if(TSmsSendInfoEntity.SEND_STATUS_1.equals(tSmsSendInfo.getSendStatus())||TSmsSendInfoEntity.SEND_STATUS_1.equals(sendStatus)){
					this.tSmsSendInfoService.doUpdate(tSmsSendInfoEntity,smsNumber);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					message = "短信发送成功";
				}else{//SEND_STATUS_0:未发送，即存草稿
					this.systemService.updateEntitie(tSmsSendInfoEntity);
					message = "短信发送保存到草稿箱";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "短信发送修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * t_sms_send_info新增页面跳转
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSmsSendInfoEntity tSmsSendInfo, HttpServletRequest req) {
		String sourcePushType = req.getParameter("pushType");//推送类型
		String pushId =req.getParameter("pushId");//推送资讯ID或者商品ID
		String title = req.getParameter("title");//标题
		String source = req.getParameter("source");//来源，从哪里跳转过来，0代表列表跳转，null代表是其他地方
		String retailerId = ResourceUtil.getRetailerId();
		String batchNo = DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+RandomUtils.nextInt(4);
		req.setAttribute("batchNo", batchNo);
		if (StringUtil.isNotEmpty(tSmsSendInfo.getId())) {
			tSmsSendInfo = tSmsSendInfoService.flushEntity(TSmsSendInfoEntity.class, tSmsSendInfo.getId());
			req.setAttribute("tSmsSendInfoPage", tSmsSendInfo);
		}
		List<TSmsSendTemplateEntity> departList = getTemplateList();//模板列表
		List<TSmsAutographEntity> autographList = getAutographList();//签名列表
		List<TStoreEntity> storeList = getTStoreList();//实体店列表
		req.setAttribute("autographList", autographList);
		req.setAttribute("templateList", departList);
		req.setAttribute("storeList", storeList);
		req.setAttribute("id", Utility.getUUID());
		req.setAttribute("retailerId", retailerId);
		req.setAttribute("source", source);
		String  pushUrl = "";
		ResourceBundle env = ResourceBundle.getBundle("env");
		if("0".equals(sourcePushType)){
			pushUrl = env.getObject("CST_REQUEST_PRE_URL")+"tPosterController.do?viewPoster&id=";
			req.setAttribute("pushUrl", pushUrl + pushId);//推送链接
			if(Utility.isNotEmpty(pushId)){
				TPosterEntity tpe = this.systemService.flushEntity(TPosterEntity.class, pushId);
				if(Utility.isNotEmpty(tpe)){
					req.setAttribute("title", tpe.getTitle());//标题
				}
			}
		}else if("1".equals(sourcePushType)){
			pushUrl = env.getObject("CST_REQUEST_PRE_URL")+"tGoodsController.do?goodsDetailPage&goodsId=";
			req.setAttribute("pushUrl", pushUrl + pushId);//推送链接
			if(Utility.isNotEmpty(pushId)){
				TGoodsEntity tge = this.systemService.flushEntity(TGoodsEntity.class, pushId);
				if(Utility.isNotEmpty(tge)){
					req.setAttribute("title", tge.getTitle());//标题
				}
			}
		}
		req.setAttribute("sourcePushType", sourcePushType);//推送类型
		req.setAttribute("SHORT_URL", env.getObject("SHORT_URL"));//短链接前缀
//		String surplusNumber = tSmsSendInfoService.balance(retailerId);
		TSmsSubAccountEntity account = this.systemService.findUniqueByProperty(TSmsSubAccountEntity.class, "retailerId", retailerId);
		String surplusNumber = "0";//短信剩余条数
		if(Utility.isNotEmpty(account)){
			if(Utility.isNotEmpty(account.getSmsNumber())&&Utility.isNotEmpty(account.getLockingNumber())){
				surplusNumber = account.getSmsNumber()-account.getLockingNumber()+"";//短信剩余条数;
			}
		}
		req.setAttribute("surplusNumber", surplusNumber);
		return new ModelAndView("com/buss/sms/tSmsSendInfo-add");
	}
	/**
	 * t_sms_send_info编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSmsSendInfoEntity tSmsSendInfo, HttpServletRequest req) {
		String viewType = req.getParameter("viewType");//是编辑还是查看，hide代表是查看，为空或者show代表是编辑，显示推送，返回按钮
		if (StringUtil.isNotEmpty(tSmsSendInfo.getId())) {
			tSmsSendInfo = tSmsSendInfoService.flushEntity(TSmsSendInfoEntity.class, tSmsSendInfo.getId());
			req.setAttribute("tSmsSendInfoPage", tSmsSendInfo);
		}
		List<TSmsAutographEntity> autographList = getAutographList();
		List<TSmsSendTemplateEntity> departList = getTemplateList();
		List<TStoreEntity> storeList = getTStoreList();//实体店列表
		req.setAttribute("storeList", storeList);
		req.setAttribute("autographList", autographList);
		req.setAttribute("templateList", departList);
		req.setAttribute("viewType", viewType);
		TSmsSubAccountEntity account = this.systemService.findUniqueByProperty(TSmsSubAccountEntity.class, "retailerId", tSmsSendInfo.getRetailerId());
		String surplusNumber = "0";//短信剩余条数
		if(Utility.isNotEmpty(account)){
			if(Utility.isNotEmpty(account.getSmsNumber())&&Utility.isNotEmpty(account.getLockingNumber())){
				surplusNumber = account.getSmsNumber()-account.getLockingNumber()+"";//短信剩余条数;
			}
		}
		ResourceBundle env = ResourceBundle.getBundle("env");
		req.setAttribute("SHORT_URL", env.getObject("SHORT_URL"));//短链接前缀
		req.setAttribute("surplusNumber", surplusNumber);
		return new ModelAndView("com/buss/sms/tSmsSendInfo-update");
	}
	/**
	 * 获取模板列表
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(params = "getTemplateList",produces="application/json;charset=UTF-8")
	public Map<String,Object> getTemplateList(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("success", true);
		try {
			List<TSmsSendTemplateEntity> departList = getTemplateList();
			map.put("templateList", departList);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return map;
	}	
	
	public List<TSmsSendTemplateEntity> getTemplateList(){
		String retailerId = ResourceUtil.getRetailerId();
		CriteriaQuery cq = new CriteriaQuery(TSmsSendTemplateEntity.class);
		cq.eq("retailerId", retailerId);
		cq.add();
		List<TSmsSendTemplateEntity> departList = this.tSmsSendInfoService.getListByCriteriaQuery(cq, false);
		return departList;
		
	}
	
	public List<TSmsAutographEntity> getAutographList(){
		String retailerId = ResourceUtil.getRetailerId();
		CriteriaQuery cq = new CriteriaQuery(TSmsAutographEntity.class);
		cq.eq("retailerId", retailerId);
		cq.eq("autographStatus", TSmsAutographEntity.AUTOGRAPH_STATUS_1);//审核通过的
		cq.add();
		List<TSmsAutographEntity> autographList = this.tSmsAutographService.getListByCriteriaQuery(cq, false);
		return autographList;
		
	}
	/**
	 * 获取实体店列表
	 * @return
	 */
	public List<TStoreEntity> getTStoreList(){
		String retailerId = ResourceUtil.getRetailerId();
		CriteriaQuery cq = new CriteriaQuery(TStoreEntity.class);
		cq.eq("retailerId", retailerId);
		cq.add();
		List<TStoreEntity> storeList = this.tStoreService.getListByCriteriaQuery(cq, false);
		return storeList;
		
	}
	/**
	 * 零售商商品表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tSmsRetailerList")
	public ModelAndView tSmsRetailerList(HttpServletRequest request) {
		String goods_status = request.getParameter("goods_status");
		if(StringUtil.isNotEmpty(goods_status)){
			request.setAttribute("goods_status", goods_status);
			request.setAttribute("rType", ResourceUtil.getRetailerType());//尖刀零售商 的时候用
			ResourceBundle env = ResourceBundle.getBundle("env");
			String  newsUrl = env.getObject("CST_REQUEST_PRE_URL")+"tGoodsController.do?goodsDetailPage&goodsId=";
			request.setAttribute("longUrl",newsUrl );
			return new ModelAndView("com/buss/sms/tSmsNews");
		}
		String retailerType = ResourceUtil.getRetailerType();
		if(TSUser.RETAILER_TYPE_REAL.equals(retailerType)){//零售商
			return new ModelAndView("com/buss/newGoods/tNewGoodsMain");
		}else{//云商
			return new ModelAndView("com/buss/newGoods/tNewGoodsListOfRetailer-all");
		}
	}
	
	/**
	 * 海报列表 页面跳转  (四种状态)
	 * @return
	 */
	@RequestMapping(params = "tPosterList")
	public ModelAndView tPosterList(HttpServletRequest request) {
		String postStatus = request.getParameter("postStatus");//海报状态
		if(StringUtil.isNotEmpty(postStatus)){
			request.setAttribute("postStatus", postStatus);
			ResourceBundle env = ResourceBundle.getBundle("env");
			String  newsUrl = env.getObject("CST_REQUEST_PRE_URL")+"tPosterController.do?viewPoster&id=";
			request.setAttribute("longUrl",newsUrl );
		}
		return new ModelAndView("com/buss/sms/tPosterList");
	}
}
