package com.buss.job.service.impl;
import com.buss.job.service.TJobModelServiceI;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.service.SystemService;

import com.buss.job.entity.TJobEntity;
import com.buss.job.entity.TJobModelEntity;
import com.buss.job.entity.TJobParamEntity;
import com.buss.store.entity.TStoreEntity;
import com.buss.store.entity.TStoreJobmodelEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("tJobModelService")
@Transactional
public class TJobModelServiceImpl extends CommonServiceImpl implements TJobModelServiceI {
	@Autowired
	private SystemService systemService;

	
	@Override
	public String doAddJobModel(TJobModelEntity tJobModel,Set<String> tStoreJobModel,String storeIdSql) {
		String storeIds = tJobModel.getStoreId();
		String modelName = tJobModel.getName();
		tJobModel.setStoreId("");
		Date endTime = tJobModel.getEndTime();
		//结束时间为 取到的时间+1天-1s的时间
		endTime.setTime(endTime.getTime()+24*60*60*1000-5000);
		tJobModel.setEndTime(endTime);
		String message = "任务组添加成功";
		try{
			String modelId = systemService.save(tJobModel)+"";
			
			//获取任务的参数信息
			List<TJobParamEntity> detailList = tJobModel.getDetailList();
			for (TJobParamEntity jobParam : detailList) {
				
				String paramStatus = jobParam.getStatus();
				if("A".equals(paramStatus)){
					if(Utility.isEmpty(jobParam.getJobNum())){
						message=jobParam.getJobTitle()+"任务的任务完成次数没有设置";
						return message;
					}
					if(Utility.isEmpty(jobParam.getGoldNum())){
						message=jobParam.getJobTitle()+"任务的金币数没有设置";
						return message;
					}
					
					/*if(Utility.isEmpty(jobParam.getGoldTime())){
						message=jobParam.getJobTitle()+"任务的领金币的次数没有设置";
						return message;
					}
					if(Utility.isEmpty(jobParam.getDayTime()) && (Utility.isEmpty(jobParam.getStartTime())||Utility.isEmpty(jobParam.getEndTime()))){
						message=jobParam.getJobTitle()+"任务的任务日期没有设置";
						return message;
					}
					String cycle = jobParam.getCycle();
					if(cycle == TJobParamEntity.JOB_PARAM_CYCLE_NO || TJobParamEntity.JOB_PARAM_CYCLE_NO.equals(cycle)){
						Date endTime = jobParam.getEndTime();
						//结束时间为 取到的时间+1天-1s的时间
						endTime.setTime(endTime.getTime()+24*60*60*1000-1000);
						jobParam.setEndTime(endTime);
					}
					String punish= jobParam.getPunish();
					if(Utility.isEmpty(punish)){
						jobParam.setPunish("0");
					}*/

					jobParam.setModelId(modelId);
					jobParam.setPunish("0");
					
					jobParam.setRetailerId(tJobModel.getRetailerId());
					//保存任务的参数信息
					systemService.save(jobParam);
				}
			}
			
			//店铺，任务组连接表保存
			String[] strs = storeIds.split(",");
			for (String storeId : strs) {
				TStoreJobmodelEntity tStoreJobmodel = new TStoreJobmodelEntity();
				tStoreJobmodel.setStoreId(storeId);
				tStoreJobmodel.setModelId(modelId);
				tStoreJobmodel.setModelName(modelName);
				tStoreJobmodel.setStartTime(tJobModel.getStartTime());
				tStoreJobmodel.setEndTime(endTime);
				//保存任务的参数信息
				systemService.save(tStoreJobmodel);
			}
			//修改店铺的任务模板ID
			if(Utility.isNotEmpty(storeIdSql)){
				String updateStore = "update t_store set job_model_id = '"+modelId+"' where id in ("+storeIdSql+")";
				systemService.updateBySqlString(updateStore);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务模板添加失败";
			throw new BusinessException(e.getMessage());
		}
		return message;
	}


	@Override
	public String doUpdateJobModel(TJobModelEntity tJobModel,Set<String> tStoreJobModel, String storeIdSql) {
		String message = "任务组店铺修改成功";
		try{
			//更新任务模板的信息
			Date endTime = tJobModel.getEndTime();
			if(Utility.isNotEmpty(endTime)){  //设置任务的结束时间为选择时间的23点
				endTime.setTime(endTime.getTime()+24*60*60*1000-1000);
			}
			TJobModelEntity tJobParamOld = this.commonDao.get(TJobModelEntity.class, tJobModel.getId());
			MyBeanUtils.copyBeanNotNull2Bean(tJobModel, tJobParamOld);
			systemService.updateEntitie(tJobParamOld);
			
			//获取任务的参数信息
			/*List<TJobParamEntity> detailList = tJobModel.getDetailList();
			for (TJobParamEntity tJobParam : detailList) {
				String paramId = tJobParam.getId();
				String paramStatus = tJobParam.getStatus();
				if("A".equals(paramStatus)){

					if(Utility.isEmpty(tJobParam.getJobNum())){
						message=tJobParam.getJobTitle()+"任务的任务完成次数没有设置";
						return message;
					}
					if(Utility.isEmpty(tJobParam.getGoldNum())){
						message=tJobParam.getJobTitle()+"任务的金币数没有设置";
						return message;
					}
//					if(Utility.isEmpty(tJobParam.getGoldTime())){
//						message=tJobParam.getJobTitle()+"任务的领金币的次数没有设置";
//						return message;
//					}
//					if(Utility.isEmpty(tJobParam.getDayTime()) && (Utility.isEmpty(tJobParam.getStartTime())||Utility.isEmpty(tJobParam.getEndTime()))){
//						message=tJobParam.getJobTitle()+"任务的任务日期没有设置";
//						return message;
//					}
//					String cycle = tJobParam.getCycle();
//					//判断任务是否循环执行
//					if(cycle == TJobParamEntity.JOB_PARAM_CYCLE_NO || TJobParamEntity.JOB_PARAM_CYCLE_NO.equals(cycle)){
//						Date endTime = tJobParam.getEndTime();
//						//结束时间为 取到的时间+1天-1s的时间
//						endTime.setTime(endTime.getTime()+24*60*60*1000-1000);
//						tJobParam.setEndTime(endTime);
//					}
//					//将处罚金币不录默认为0
//					String punish= tJobParam.getPunish();
//					if(Utility.isEmpty(punish)){
//						tJobParam.setPunish("0");
//					}

					tJobParam.setModelId(tJobModel.getId());
					tJobParam.setPunish("0");
					if(Utility.isNotEmpty(paramId)){
						//更新数据需要先将更新的数据复值到新查询的实体上
						TJobParamEntity jobParamOld = this.commonDao.get(TJobParamEntity.class, paramId);
						MyBeanUtils.copyBeanNotNull2Bean(tJobParam, jobParamOld);
						systemService.updateEntitie(jobParamOld);
					}else{
						tJobParam.setRetailerId(tJobModel.getRetailerId());
						systemService.save(tJobParam);
					}
				}else if("I".equals(paramStatus) && Utility.isNotEmpty(paramId)){
					TJobParamEntity jobParamOld = this.commonDao.get(TJobParamEntity.class, paramId);
					MyBeanUtils.copyBeanNotNull2Bean(tJobParam, jobParamOld);
					systemService.updateEntitie(jobParamOld);
				}
			}*/

			//将任务组的原来的店铺任务组ID删除
			String store = "update t_store set job_model_id = '' where job_model_id = '"+tJobModel.getId()+"'";
			systemService.updateBySqlString(store);

			//修改店铺的任务模板ID
			if(Utility.isNotEmpty(storeIdSql)){
				String updateStore = "update t_store set job_model_id = '"+tJobModel.getId()+"' where id in ("+storeIdSql+")";
				systemService.updateBySqlString(updateStore);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "任务组店铺修改失败";
			throw new BusinessException(e.getMessage());
		}
		
		return message;
	}


	@Override
	public String doDelJobModel(String jobModelId) {
		String message = "任务组删除成功";
		try{
			for(String id:jobModelId.split(",")){
				TJobModelEntity tJobModel = systemService.flushEntity(TJobModelEntity.class, id);
				tJobModel.setStatus("I");
				systemService.updateEntitie(tJobModel);
				//将模板的参数数据状态置为“I”
				String sqlDealParam = "update t_job_param set status='I' where model_id='"+id+"'";
				systemService.updateBySqlString(sqlDealParam);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "任务组删除失败";
			throw new BusinessException(e.getMessage());
		}
		return message;
	}
 	
}