package com.buss.count.service.impl;
import com.buss.count.service.TStoreGuideMonTargetServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.count.entity.TStoreGuideMonTargetEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;
import java.math.BigDecimal;

@Service("tStoreGuideMonTargetService")
@Transactional
public class TStoreGuideMonTargetServiceImpl extends CommonServiceImpl implements TStoreGuideMonTargetServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TStoreGuideMonTargetEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TStoreGuideMonTargetEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TStoreGuideMonTargetEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TStoreGuideMonTargetEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TStoreGuideMonTargetEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TStoreGuideMonTargetEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TStoreGuideMonTargetEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{mon_target_id}",String.valueOf(t.getMonTargetId()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{store_id}",String.valueOf(t.getStoreId()));
 		sql  = sql.replace("#{guide_id}",String.valueOf(t.getGuideId()));
 		sql  = sql.replace("#{target_money}",String.valueOf(t.getTargetMoney()));
 		sql  = sql.replace("#{online_money}",String.valueOf(t.getOnlineMoney()));
 		sql  = sql.replace("#{offline_money}",String.valueOf(t.getOfflineMoney()));
 		sql  = sql.replace("#{reach_rate}",String.valueOf(t.getReachRate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	@Override
 	public void doDel(TStoreGuideMonTargetEntity tStoreGuideMonTarget) {
 		tStoreGuideMonTarget.setStatus(common.GlobalConstants.STATUS_INACTIVE);
 		commonDao.updateEntitie(tStoreGuideMonTarget);
 		//更新店铺月目标
 		String sql ="update t_store_mon_target set target_money = target_money-"+tStoreGuideMonTarget.getTargetMoney()
 					+" where id='"+tStoreGuideMonTarget.getMonTargetId()+"'";
 		commonDao.updateBySqlString(sql);
 	}
 	
 	@Override
 	public void doUpdate(TStoreGuideMonTargetEntity tStoreGuideMonTarget) {
 		TStoreGuideMonTargetEntity gt = commonDao.get(TStoreGuideMonTargetEntity.class, tStoreGuideMonTarget.getId());
 		if(tStoreGuideMonTarget.getTargetMoney().compareTo(gt.getTargetMoney())!=0){//月目标改变
 			BigDecimal changeMoney = tStoreGuideMonTarget.getTargetMoney().subtract(gt.getTargetMoney());
			gt.setTargetMoney(tStoreGuideMonTarget.getTargetMoney());
			commonDao.updateEntitie(gt);
			//更新店铺月目标
	 		String sql ="update t_store_mon_target set target_money = target_money+("+changeMoney
	 					+") where id='"+tStoreGuideMonTarget.getMonTargetId()+"'";
	 		commonDao.updateBySqlString(sql);
		}
 	}
}