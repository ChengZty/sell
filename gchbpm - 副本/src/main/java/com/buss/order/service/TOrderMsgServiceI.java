package com.buss.order.service;
import com.buss.param.entity.TSysParameterEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

public interface TOrderMsgServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSysParameterEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSysParameterEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSysParameterEntity t);
 	/**
 	 * 更新订单短信回复信息
 	 * @param tSysParameter
 	 */
//	void updateOrderMsg(TSysParameterEntity tSysParameter);
 	/**
 	 * 保存更新订单短信回复信息
 	 * @param tSysParameter
 	 */
//	void saveOrderMsg(TSysParameterEntity tSysParameter);
 	/**
 	 * 获取客服电话，退货电话和退货地址
 	 * @param retailerId 零售商id
 	 * @return
 	 */
//	List<TSysParameterEntity> getParameters(String retailerId);
}
