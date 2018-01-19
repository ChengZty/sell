package com.buss.count.service.impl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.count.entity.TStoreGuideMonTargetEntity;
import com.buss.count.entity.TStoreMonTargetEntity;
import com.buss.count.service.TStoreMonTargetServiceI;
import com.buss.store.entity.TStoreEntity;
import com.buss.user.entity.TPersonEntity;

@Service("tStoreMonTargetService")
@Transactional
public class TStoreMonTargetServiceImpl extends CommonServiceImpl implements TStoreMonTargetServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TStoreMonTargetEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TStoreMonTargetEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TStoreMonTargetEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TStoreMonTargetEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TStoreMonTargetEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TStoreMonTargetEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TStoreMonTargetEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{store_id}",String.valueOf(t.getStoreId()));
 		sql  = sql.replace("#{target_month}",String.valueOf(t.getTargetMonth()));
 		sql  = sql.replace("#{target_money}",String.valueOf(t.getTargetMoney()));
 		sql  = sql.replace("#{sales_money}",String.valueOf(t.getSalesMoney()));
 		sql  = sql.replace("#{reach_rate}",String.valueOf(t.getReachRate()));
 		sql  = sql.replace("#{is_finish}",String.valueOf(t.getIsFinish()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void getGuideTargetList(TStoreMonTargetEntity tStoreMonTarget,HttpServletRequest req) {
 		List<TStoreMonTargetEntity> list = commonDao.findHql("from TStoreMonTargetEntity where storeId = ? and targetMonth = ?", 
 											tStoreMonTarget.getStoreId(),tStoreMonTarget.getTargetMonth());
 		List <TStoreGuideMonTargetEntity> guideTargetList = null;
 		//店铺当前所有导购
 		List<TPersonEntity> guideList = this.commonDao.findByProperty(TPersonEntity.class, "storeId", tStoreMonTarget.getStoreId());
		if (list.size()>0) {//已经设置过
			tStoreMonTarget = list.get(0);
			//之前设置的导购列表（可能会少于店铺当前所有导购，比如突然该店铺加入了导购）
			guideTargetList = this.commonDao.findByProperty(TStoreGuideMonTargetEntity.class, "monTargetId", tStoreMonTarget.getId());
			//新来的导购
			List <TStoreGuideMonTargetEntity> newGuideTargetList = new ArrayList<TStoreGuideMonTargetEntity>();
			
			for(TPersonEntity p : guideList){
				boolean exist = false;//默认不存在
				for(TStoreGuideMonTargetEntity guideTarget:guideTargetList){
					if(p.getUserId().equals(guideTarget.getGuideId())){//存在
						exist = true;
						break;
					}
				}
				if(!exist){//如果不存在则要加入到导购月目标列表中
					TStoreGuideMonTargetEntity guideTarget = new TStoreGuideMonTargetEntity();
					guideTarget.setGuideId(p.getUserId());
					guideTarget.setGuideName(p.getRealName());
					newGuideTargetList.add(guideTarget);
				}
			}
			if(newGuideTargetList.size()>0){
				guideTargetList.addAll(newGuideTargetList);
			}
		}else{//未设置过
			guideTargetList = new ArrayList<TStoreGuideMonTargetEntity>();
			if(guideList.size()>0){
				for(TPersonEntity p : guideList){
					TStoreGuideMonTargetEntity guideTarget = new TStoreGuideMonTargetEntity();
					guideTarget.setGuideId(p.getUserId());
					guideTarget.setGuideName(p.getRealName());
					guideTargetList.add(guideTarget);
				}
			}
		}
		tStoreMonTarget.setGuideTargetList(guideTargetList);
		req.setAttribute("tStoreMonTargetPage", tStoreMonTarget);
		TStoreEntity store = commonDao.get(TStoreEntity.class, tStoreMonTarget.getStoreId());
		req.setAttribute("storeName", store.getName());
 		
 	}
 	
 	@Override
 	public void doUpdate(TStoreMonTargetEntity tStoreMonTarget) {
 		//导购月目标列表
 		List<TStoreGuideMonTargetEntity> guideTargetList = tStoreMonTarget.getGuideTargetList();
 		String retailerId = ResourceUtil.getRetailerId();
 		BigDecimal targetMoney = BigDecimal.ZERO;//月目标金额
 		BigDecimal salesMoney = BigDecimal.ZERO;//实际业绩金额
 		BigDecimal reachRate = BigDecimal.ZERO;
 		if(guideTargetList.size()>0){
 			for(TStoreGuideMonTargetEntity guideTarget : guideTargetList){
 				targetMoney = targetMoney.add(guideTarget.getTargetMoney());//累加目标金额
 			}
 		}
 		
 		if(Utility.isEmpty(tStoreMonTarget.getId())){//新增
 			tStoreMonTarget.setIsFinish(TStoreMonTargetEntity.IS_FINISH_N);
 			tStoreMonTarget.setRetailerId(retailerId);
 			tStoreMonTarget.setTargetMoney(targetMoney);
 			tStoreMonTarget.setSalesMoney(salesMoney);
 			tStoreMonTarget.setReachRate(reachRate);
 			commonDao.save(tStoreMonTarget);//保存店铺月目标
 			for(TStoreGuideMonTargetEntity guideTarget : guideTargetList){
 				guideTarget.setMonTargetId(tStoreMonTarget.getId());
 				guideTarget.setOfflineMoney(BigDecimal.ZERO);
 				guideTarget.setOnlineMoney(BigDecimal.ZERO);
 				guideTarget.setReachRate(BigDecimal.ZERO);
 				guideTarget.setOnlineQuantity(BigDecimal.ZERO);
 				guideTarget.setOnlineSingular(BigDecimal.ZERO);
 				guideTarget.setOfflineQuantity(BigDecimal.ZERO);
 				guideTarget.setOfflineSingular(BigDecimal.ZERO);
 				guideTarget.setRetailerId(tStoreMonTarget.getRetailerId());
 				guideTarget.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 				guideTarget.setStoreId(tStoreMonTarget.getStoreId());
 				guideTarget.setTargetMonth(tStoreMonTarget.getTargetMonth());
 				commonDao.save(guideTarget);//保存导购月目标
 			}
 		}else{//修改
 			for(TStoreGuideMonTargetEntity guideTarget : guideTargetList){
 				if(Utility.isEmpty(guideTarget.getId())){//新增
 					guideTarget.setMonTargetId(tStoreMonTarget.getId());
 	 				guideTarget.setOfflineMoney(BigDecimal.ZERO);
 	 				guideTarget.setOnlineMoney(BigDecimal.ZERO);
 	 				guideTarget.setReachRate(BigDecimal.ZERO);
 	 				guideTarget.setOnlineQuantity(BigDecimal.ZERO);
 	 				guideTarget.setOnlineSingular(BigDecimal.ZERO);
 	 				guideTarget.setOfflineQuantity(BigDecimal.ZERO);
 	 				guideTarget.setOfflineSingular(BigDecimal.ZERO);
 	 				guideTarget.setRetailerId(tStoreMonTarget.getRetailerId());
 	 				guideTarget.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 	 				guideTarget.setStoreId(tStoreMonTarget.getStoreId());
 	 				guideTarget.setTargetMonth(tStoreMonTarget.getTargetMonth());
 	 				commonDao.save(guideTarget);//保存导购月目标
 				}else{
 					TStoreGuideMonTargetEntity gt = commonDao.get(TStoreGuideMonTargetEntity.class, guideTarget.getId());
 					if(guideTarget.getTargetMoney().compareTo(gt.getTargetMoney())!=0){//月目标改变
 						gt.setTargetMoney(guideTarget.getTargetMoney());
 						commonDao.updateEntitie(gt);
 					}
 				}
 			}
 			if(targetMoney.compareTo(tStoreMonTarget.getTargetMoney())!=0){
 				tStoreMonTarget = commonDao.get(TStoreMonTargetEntity.class, tStoreMonTarget.getId());
 				tStoreMonTarget.setTargetMoney(targetMoney);//更新店铺月目标
 				commonDao.updateEntitie(tStoreMonTarget);
 			}
 		}
 		
 	}

}