package com.buss.ticket.service;
import com.buss.ticket.entity.TTicketSendEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TTicketSendServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TTicketSendEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TTicketSendEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TTicketSendEntity t);

	/**批量分配券
	 * @param userIds
	 * @param ticketIds
	 * @param sheetNum
	 */
	public void doBatchGive(String userIds, String ticketIds, String sheetNum);
}
