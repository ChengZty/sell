package com.buss.ticket.service.impl;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.ticket.entity.TTicketInfoEntity;
import com.buss.ticket.entity.TTicketRetailersEntity;
import com.buss.ticket.entity.TTicketSendEntity;
import com.buss.ticket.service.TTicketSendServiceI;

@Service("tTicketSendService")
@Transactional
public class TTicketSendServiceImpl extends CommonServiceImpl implements TTicketSendServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TTicketSendEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TTicketSendEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TTicketSendEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TTicketSendEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TTicketSendEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TTicketSendEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TTicketSendEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{ticket_id}",String.valueOf(t.getTicketId()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
 		sql  = sql.replace("#{sender_id}",String.valueOf(t.getSenderId()));
 		sql  = sql.replace("#{add_time}",String.valueOf(t.getAddTime()));
 		sql  = sql.replace("#{sheet}",String.valueOf(t.getSheet()));
 		sql  = sql.replace("#{sheet_give}",String.valueOf(t.getSheetGive()));
 		sql  = sql.replace("#{sheet_remain}",String.valueOf(t.getSheetRemain()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
 	public void doBatchGive(String userIds, String ticketIds, String sheetNum) {
 		String[] u_ids = userIds.split(",");
 		String[] t_ids = ticketIds.split(",");
 		int num = Integer.valueOf(sheetNum);//人均张数
 		int user_num = u_ids.length;//人数
 		TSUser user = ResourceUtil.getSessionUserName();
 		for(String ticket_type : t_ids){
 			String[] arr = ticket_type.split("_");
 			if("1".equals(arr[1])){//零售商自己的券
 				TTicketInfoEntity ticketInfo = this.commonDao.get(TTicketInfoEntity.class, arr[0]);
 	 				if(!(ticketInfo.getSheetRemain()<num*user_num)){
 	 					List<TTicketSendEntity> list = this.commonDao.findHql("from TTicketSendEntity where status = 'A' and ticketId='"+arr[0]+"'");
 	 					for(String userId : u_ids){
 	 						Boolean flag = false;//默认没有发过该券
 	 						TTicketSendEntity ticketSend = null;
 	 						for(TTicketSendEntity entity : list){
 	 							if(entity.getUserId().equals(userId)){
 	 								flag = true;
 	 								ticketSend = entity;
 	 								break;
 	 							}
 	 						}
 	 						if(flag){//已经发过
 	 							ticketSend.setSheet(ticketSend.getSheet()+num);
 	 							ticketSend.setSheetRemain(ticketSend.getSheetRemain()+num);
 	 							this.commonDao.updateEntitie(ticketSend);
 	 						}else{//没发过
 	 							TTicketSendEntity entity = new TTicketSendEntity();
 	 							entity.setSenderId(user.getId());
 	 							entity.setSheet(num);
 	 							entity.setSheetGive(0);
 	 							entity.setSheetRemain(num);
 	 							entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
 	 							entity.setTicketId(arr[0]);
 	 							entity.setUserId(userId);
 	 							entity.setAddTime(Utility.getCurrentTimestamp());
 	 							this.commonDao.save(entity);
 	 						}
 	 						ticketInfo.setSheetSent(ticketInfo.getSheetSent()+num);
 	 						ticketInfo.setSheetRemain(ticketInfo.getSheetRemain()-num);
 	 						this.commonDao.updateEntitie(ticketInfo);
 	 					}
 	 				}else{
 	 					throw new BusinessException("批量分配券失败,批次号为"+ticketInfo.getBatchNo()+"超过剩余张数");
 	 				}
 			}else if("2".equals(arr[1])){//云商自己的券
 				String retailerId = ResourceUtil.getRetailerId();
 				TTicketInfoEntity ticketInfo = this.commonDao.get(TTicketInfoEntity.class, arr[0]);//云商券
 				TTicketRetailersEntity retailerTicketInfo= null;//分给零售商的券
 				List<TTicketRetailersEntity> ticketList = this.commonDao.findHql("from TTicketRetailersEntity where status = 'A' and ticketId='"+arr[0]
 				                 +"' and userId = '"+retailerId+"'");
 				if(ticketList.size()>0){
 					retailerTicketInfo = ticketList.get(0);
 				}
	 				if(!(retailerTicketInfo.getSheetRemain()<num*user_num)){
	 					List<TTicketSendEntity> list = this.commonDao.findHql("from TTicketSendEntity where status = 'A' and ticketId='"+arr[0]+"'");
	 					for(String userId : u_ids){
	 						Boolean flag = false;//默认没有发过该券
	 						TTicketSendEntity ticketSend = null;
	 						for(TTicketSendEntity entity : list){
	 							if(entity.getUserId().equals(userId)){
	 								flag = true;
	 								ticketSend = entity;
	 								break;
	 							}
	 						}
	 						if(flag){//已经发过
	 							ticketSend.setSheet(ticketSend.getSheet()+num);
	 							ticketSend.setSheetRemain(ticketSend.getSheetRemain()+num);
	 							this.commonDao.updateEntitie(ticketSend);
	 						}else{//没发过
	 							TTicketSendEntity entity = new TTicketSendEntity();
	 							entity.setSenderId(user.getId());
	 							entity.setSheet(num);
	 							entity.setSheetGive(0);
	 							entity.setSheetRemain(num);
	 							entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
	 							entity.setTicketId(arr[0]);
	 							entity.setUserId(userId);
	 							entity.setAddTime(Utility.getCurrentTimestamp());
	 							this.commonDao.save(entity);
	 						}
	 						retailerTicketInfo.setSheetGive(retailerTicketInfo.getSheetGive()+num);
	 						retailerTicketInfo.setSheetRemain(retailerTicketInfo.getSheetRemain()-num);
	 						this.commonDao.updateEntitie(retailerTicketInfo);
	 					}
	 				}else{
	 					throw new BusinessException("批量分配券失败,批次号为"+ticketInfo.getBatchNo()+"超过剩余张数");
	 				}
 			}
 			
 		}
 		
 	}
}