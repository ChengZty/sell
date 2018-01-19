package com.buss.job.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
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

import com.buss.job.entity.TJobEntity;
import com.buss.job.entity.TJobModelEntity;
import com.buss.job.entity.TJobParamEntity;
import com.buss.job.service.TJobModelServiceI;
import com.buss.store.entity.TStoreEntity;



/**   
 * @Title: Controller
 * @Description: 任务模板维护
 * @author onlineGenerator
 * @date 2016-11-05 11:54:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tJobModelController")
public class TJobModelController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TJobModelController.class);

	@Autowired
	private TJobModelServiceI tJobModelService;
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
	 * 任务维护列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/job/tJobModelList");
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
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String retailerId = ResourceUtil.getRetailerId();
		
		String sql = "select tjm.id id,tjm.name name,CONCAT(DATE_FORMAT(start_time,'%Y-%m-%d'),'~',DATE_FORMAT(end_time,'%Y-%m-%d')) time,"+
		"ifnull(ts.storeId,'') storeId,ifnull(ts.storeName,'') storeName  from t_job_model tjm left join "+
		"( select job_model_id modelId, GROUP_CONCAT(id separator ',') storeId, GROUP_CONCAT(name separator ',') storeName "+
		"from t_store where status ='A' group by job_model_id ) ts on tjm.id = ts.modelId "+
		"where tjm.status = 'A'  and tjm.retailer_id='"+retailerId+"'";
		
		String countSql = "select count(*) from t_job_model where status = 'A'  and retailer_id='"+retailerId+"'";
		
		
		int total = 0;
		try {
			total = this.systemService.getCountForJdbc(countSql).intValue();
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
			if(total > 0){
				resultList = this.systemService.findForJdbcParam(sql, dataGrid.getPage(),dataGrid.getRows(), null);
			}
			dataGrid.setResults(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage());
		}

		dataGrid.setTotal(total);
		
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除任务维护
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String jobModelID = request.getParameter("id");
		TJobModelEntity tJobModel = systemService.flushEntity(TJobModelEntity.class, jobModelID);
		message = "任务组删除成功";
		try{
			//将任务组的原来的店铺删除
			List<TStoreEntity> tStores = systemService.findByProperty(TStoreEntity.class, "jobModelId", jobModelID);
			for (TStoreEntity tStore : tStores) {
				tStore.setJobModelId("");
				systemService.updateEntitie(tStore);
			}
			tJobModel.setStatus("I");
			systemService.updateEntitie(tJobModel);

			//将模板的参数数据状态置为“I”
			String sqlDealParam = "update t_job_param set status='I' where model_id='"+jobModelID+"'";
			systemService.updateBySqlString(sqlDealParam);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务组删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除任务维护
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message  = tJobModelService.doDelJobModel(ids);
		
		j.setMsg(message);
		return j;
	}

		

	/**
	 * 任务维护新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest req) {
		String retailerId = ResourceUtil.getRetailerId();
		List<TJobEntity> jobs = systemService.findHql("from TJobEntity where jobType='3' and status='A' order by complexity asc", null);
//		List<TJobEntity> jobs = systemService.findByPropertyisOrder(TJobEntity.class, "jobType", "3", true);
//		List<TJobEntity> jobs = systemService.findByProperty(TJobEntity.class, "jobType", "3");
		List<TStoreEntity> tStore = systemService.findByProperty(TStoreEntity.class, "retailerId", retailerId);
		List<TJobEntity> preSalesJobs = new ArrayList<TJobEntity>();//售前任务
		List<TJobEntity> inSalesJobs = new ArrayList<TJobEntity>();//售中任务
		List<TJobEntity> afterSalesJobs = new ArrayList<TJobEntity>();//售后任务
		for (TJobEntity jobVO : jobs) {
			String jobSceneCode = jobVO.getJobSceneCode();
			if("1".equals(jobSceneCode)){
				preSalesJobs.add(jobVO);
			}else if("2".equals(jobSceneCode)){
				inSalesJobs.add(jobVO);
			}else if("3".equals(jobSceneCode)){
				afterSalesJobs.add(jobVO);
			}
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("preSalesJobs", preSalesJobs);//售前任务
		returnMap.put("inSalesJobs", inSalesJobs);//售中任务
		returnMap.put("afterSalesJobs", afterSalesJobs);//售后任务
		
		req.setAttribute("jobs", returnMap);
		req.setAttribute("storeList", tStore);
		req.setAttribute("storeNum", tStore.size());
		return new ModelAndView("com/buss/job/tJobModel-add");
	}
	/**
	 * 添加任务维护
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TJobModelEntity jobModel, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String retailerId = ResourceUtil.getRetailerId();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		//获取选择店铺的方式  0所有店铺，1 部分店铺 
		String selectStore= request.getParameter("selectStore");
		String[] stores= request.getParameterValues("stores");
		Set<String> tStoreJobModel = new HashSet<String>();  //任务组的新店铺ID
		String storeIdSql = "";
		String storeId = "";
		String storeName = "";
		if(Utility.isNotEmpty(stores)){
			for (String store : stores) {
				String[] idName = store.split("__");
				storeIdSql += "'"+idName[0]+"',";
				storeId += idName[0]+",";
				tStoreJobModel.add(idName[0]);
//				storeName += idName[1]+",";
	 		}
			storeIdSql = storeIdSql.substring(0, storeIdSql.length()-1);
			storeId = storeId.substring(0, storeId.length()-1);
//			storeName = storeName.substring(0, storeName.length()-1);
		}
		
		if("0".equals(selectStore)){
			storeName = "全部店铺";
		}

		jobModel.setStatus("A");
		jobModel.setStoreId(storeId);
		jobModel.setStoreName(storeName);
		jobModel.setRetailerId(retailerId);
		
		//添加任务模板
		String message = tJobModelService.doAddJobModel(jobModel,tStoreJobModel,storeIdSql);
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 任务模板编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TJobModelEntity tJobModel, HttpServletRequest req) {
		String jobModelID = req.getParameter("id");
		//获取任务组
		tJobModel = systemService.flushEntity(TJobModelEntity.class, jobModelID);
		//获取所有的任务
		List<TJobEntity> jobs = systemService.findHql("from TJobEntity where jobType='3' and status='A' order by complexity asc", null);
//		List<TJobEntity> jobs = systemService.findByProperty(TJobEntity.class, "jobType", "3");
		List<TJobParamEntity> jobParamEntitys = new ArrayList<TJobParamEntity>();
		//获取任务组的任务参数
		List<TJobParamEntity> jobParams = systemService.findByProperty(TJobParamEntity.class, "modelId", jobModelID);
		for (TJobEntity job : jobs) {
			String jobId = job.getId();
			Boolean flage = false;
			for (TJobParamEntity tJobParamEntity : jobParams) {
				String paramJobId = tJobParamEntity.getJobId();

				//判断任务组中是否存在任务
				if(jobId.equals(paramJobId)){
					tJobParamEntity.setJobSceneCode(job.getJobSceneCode());
					tJobParamEntity.setRegulation(job.getRegulation());
					jobParamEntitys.add(tJobParamEntity);
					flage = true;
				}
			}

			//将不存在的任务加入任务组列表中
			if(!flage){
				TJobParamEntity tJobParam = new TJobParamEntity();
				tJobParam.setJobId(jobId);
				tJobParam.setJobTitle(job.getTitle());
				tJobParam.setJobDescription(job.getDescription());
				tJobParam.setRegulation(job.getRegulation());
				tJobParam.setJobSceneCode(job.getJobSceneCode());
				tJobParam.setStatus("");
				tJobParam.setModelId("");
				tJobParam.setDayTime("");
				tJobParam.setJobNum("");
				tJobParam.setGoldNum("");
				tJobParam.setGoldTime("");
				tJobParam.setPunish("");
				
				jobParamEntitys.add(tJobParam);
			}
		}

		//获取任务组的店铺信息
		String retailerId = ResourceUtil.getRetailerId();
		List<TStoreEntity> tStores = systemService.findByProperty(TStoreEntity.class, "retailerId", retailerId);
		int count = 0;
		for (TStoreEntity tStore : tStores) {
			if(jobModelID.equals(tStore.getJobModelId())){
				tStore.setJobModelId("0");
				count++;
			}
		}
		if(count == tStores.size()){
			req.setAttribute("allStore", "0");
		}else{
			req.setAttribute("allStore", "-1");
		}
		
		List<TJobParamEntity> preSalesJobs = new ArrayList<TJobParamEntity>();//售前任务
		List<TJobParamEntity> inSalesJobs = new ArrayList<TJobParamEntity>();//售中任务
		List<TJobParamEntity> afterSalesJobs = new ArrayList<TJobParamEntity>();//售后任务
		for (TJobParamEntity jobVO : jobParamEntitys) {
			String jobSceneCode = jobVO.getJobSceneCode();
			if("1".equals(jobSceneCode)){
				preSalesJobs.add(jobVO);
			}else if("2".equals(jobSceneCode)){
				inSalesJobs.add(jobVO);
			}else if("3".equals(jobSceneCode)){
				afterSalesJobs.add(jobVO);
			}
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("preSalesJobs", preSalesJobs);//售前任务
		returnMap.put("inSalesJobs", inSalesJobs);//售中任务
		returnMap.put("afterSalesJobs", afterSalesJobs);//售后任务
		
		req.setAttribute("tJobModel", tJobModel);
		req.setAttribute("jobParams", returnMap);
		req.setAttribute("storeList", tStores);
		req.setAttribute("storeNum", tStores.size());
		return new ModelAndView("com/buss/job/tJobModel-update");
	}
	/**
	 * 更新任务模板
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TJobModelEntity jobModel, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String retailerId = ResourceUtil.getRetailerId();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		//获取选择店铺的方式  0所有店铺，1 部分店铺 
		String selectStore= request.getParameter("selectStore");
		String[] stores= request.getParameterValues("stores");
		Set<String> tStoreJobModel = new HashSet<String>();  //任务组的新店铺ID
		String storeIdSql = "";
		String storeId = "";
		String storeName = "";
		if(Utility.isNotEmpty(stores)){
			for (String store : stores) {
				String[] idName = store.split("__");
				storeIdSql += "'"+idName[0]+"',";
//				storeId += idName[0]+",";
				tStoreJobModel.add(idName[0]);
//				storeName += idName[1]+",";
	 		}
			storeIdSql = storeIdSql.substring(0, storeIdSql.length()-1);
//			storeId = storeId.substring(0, storeId.length()-1);
//			storeName = storeName.substring(0, storeName.length()-1);
		}
		
		if("0".equals(selectStore)){
			storeName = "全部店铺";
		}
		

		jobModel.setStatus("A");
		jobModel.setStoreId(storeId);
		jobModel.setStoreName(storeName);
		jobModel.setRetailerId(retailerId);

		//修改任务模板
		String message = tJobModelService.doUpdateJobModel(jobModel,tStoreJobModel,storeIdSql);

		j.setMsg(message);
		return j;
	}

	
}
