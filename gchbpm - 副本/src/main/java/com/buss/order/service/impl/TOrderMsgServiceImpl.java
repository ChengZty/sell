package com.buss.order.service.impl;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.order.service.TOrderMsgServiceI;
import com.buss.param.entity.TSysParameterEntity;

@Service("tOrderMsgService")
@Transactional
public class TOrderMsgServiceImpl extends CommonServiceImpl implements TOrderMsgServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSysParameterEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSysParameterEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSysParameterEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSysParameterEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSysParameterEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSysParameterEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSysParameterEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{para_code}",String.valueOf(t.getParaCode()));
 		sql  = sql.replace("#{para_value}",String.valueOf(t.getParaValue()));
 		sql  = sql.replace("#{retailer_id}",String.valueOf(t.getRetailerId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
 	 * 保存更新订单短信回复信息
 	 * @param tSysParameter
 	 */
// 	@Override
// 	public void saveOrderMsg(TSysParameterEntity tSysParameter){
// 		String retailerId = tSysParameter.getRetailerId();
// 		StringBuilder sb = new StringBuilder();
// 		//删除之前的
// 		sb.append(" delete from t_sys_parameter where para_code in('").append(GlobalConstants.ORDER_CONFIRM).append("'")
// 		.append(", '").append(GlobalConstants.ORDER_PAY).append("' ")
//		.append(", '").append(GlobalConstants.ORDER_SEND).append("' ")
//		.append(", '").append(GlobalConstants.ORDER_APPLY).append("' ")
//		.append(", '").append(GlobalConstants.ORDER_RETURNSUCC).append("' ")
//		.append(", '").append(GlobalConstants.ORDER_PHONE).append("' ")
//		.append(", '").append(GlobalConstants.ORDER_RETURN_ADDRESS).append("' ")
//		.append(", '").append(GlobalConstants.ORDER_RETURN_PHONE).append("' ")
//		.append(") and retailer_id='").append(retailerId).append("'");
// 		commonDao.updateBySqlString(sb.toString());
// 		
// 		String phone = tSysParameter.getPhone();
// 		String returnPhone = tSysParameter.getReturnPhone();
// 		String returnAddress = tSysParameter.getReturnAddress();
// 		String paraValue1 = this.getParaValue(GlobalConstants.ORDER_CONFIRM, phone, returnPhone, returnAddress);
// 		String paraValue2 = this.getParaValue(GlobalConstants.ORDER_PAY, phone, returnPhone, returnAddress);
// 		String paraValue3 = this.getParaValue(GlobalConstants.ORDER_SEND, phone, returnPhone, returnAddress);
// 		String paraValue4 = this.getParaValue(GlobalConstants.ORDER_APPLY, phone, returnPhone, returnAddress);
// 		String paraValue5 = this.getParaValue(GlobalConstants.ORDER_RETURNSUCC, phone, returnPhone, returnAddress);
//		
// 		//保存最新的
// 		Date createDate = DateUtils.gettimestamp();
// 		TSysParameterEntity orderConfirm = new TSysParameterEntity(createDate, GlobalConstants.ORDER_CONFIRM,"下单短信提醒", paraValue1, retailerId);
// 		TSysParameterEntity orderPay = new TSysParameterEntity(createDate, GlobalConstants.ORDER_PAY,"付完款短信提醒", paraValue2, retailerId);
// 		TSysParameterEntity orderSend = new TSysParameterEntity(createDate, GlobalConstants.ORDER_SEND, "已发货短信提醒",paraValue3, retailerId);
// 		TSysParameterEntity orderApply = new TSysParameterEntity(createDate, GlobalConstants.ORDER_APPLY,"申请退款成功短信提醒", paraValue4, retailerId);
// 		TSysParameterEntity orderReturn = new TSysParameterEntity(createDate, GlobalConstants.ORDER_RETURNSUCC,"退款已打短信提醒", paraValue5, retailerId);
// 		TSysParameterEntity phonePara = new TSysParameterEntity(createDate, GlobalConstants.ORDER_PHONE,"客服电话", phone, retailerId);
// 		TSysParameterEntity returnPhonePara = new TSysParameterEntity(createDate, GlobalConstants.ORDER_RETURN_PHONE,"退货电话", returnPhone, retailerId);
// 		TSysParameterEntity returnAddressPara = new TSysParameterEntity(createDate, GlobalConstants.ORDER_RETURN_ADDRESS,"退货地址", returnAddress, retailerId);
// 		
//		List<TSysParameterEntity> list = new ArrayList<TSysParameterEntity>();
//		list.add(orderConfirm);
//		list.add(orderPay);
//		list.add(orderSend);
//		list.add(orderApply);
//		list.add(orderReturn);
//		list.add(phonePara);
//		list.add(returnPhonePara);
//		list.add(returnAddressPara);
//		
//		commonDao.batchSave(list);
// 	}

// 	private String getParaValue(String typecode,String phone,String returnPhone,String returnAddress){
// 		TSUser user = ResourceUtil.getSessionUserName();
// 		List<String> list = GlobalConstants.orderMsgMap.get(typecode);
//		if(Utility.isNotEmpty(list)){
//			String val = list.get(0);
//			if(val.indexOf("（零售商名称）") != -1){
//				val = val.replaceAll("（零售商名称）", user.getRealName());
//			}
//			if(Utility.isNotEmpty(phone)){
//				val = val.replaceAll("（客服电话）", phone);
//			}
//			if(Utility.isNotEmpty(returnPhone)){
//				val = val.replaceAll("（退货号码）", returnPhone);
//			}
//			if(Utility.isNotEmpty(returnAddress)){
//				val = val.replaceAll("（退货地址）", returnAddress);
//			}
//			return val;
//		}
//		return null;
// 	}
 	/**
 	 * 更新订单短信回复信息
 	 * @param tSysParameter
 	 */
// 	@Override
// 	public void updateOrderMsg(TSysParameterEntity tSysParameter){
// 		String retailerId = tSysParameter.getRetailerId();
// 		StringBuilder sb = new StringBuilder();
// 		sb.append(" update t_sys_parameter set para_value='").append(tSysParameter.getParaValue()).append("' ")
// 		.append(" where id='").append(tSysParameter.getId()).append("'");
// 		commonDao.updateBySqlString(sb.toString());
// 		//更新客服电话
//		updateParam(retailerId,GlobalConstants.ORDER_PHONE,tSysParameter.getPhone());
//		//更新退货电话
//		updateParam(retailerId,GlobalConstants.ORDER_RETURN_PHONE,tSysParameter.getReturnPhone());
//		//更新退货地址
//		updateParam(retailerId,GlobalConstants.ORDER_RETURN_ADDRESS,tSysParameter.getReturnAddress());
// 	}
// 	
 	
//	private void updateParam(String retailerId,String paraCode,String paramValue) {
//		if(Utility.isNotEmpty(paramValue)){
//			StringBuilder sb = new StringBuilder();
//			sb.append("from TSysParameterEntity where paraCode='").append(paraCode).append("' ")
//			.append(" and retailerId='").append(retailerId).append("'");
//			List<TSysParameterEntity> list2 = commonDao.findByQueryString(sb.toString());
//			if(Utility.isNotEmpty(list2)){
//				TSysParameterEntity phoneEntity = list2.get(0);
//				phoneEntity.setParaValue(paramValue);
//				commonDao.updateEntitie(phoneEntity);
//			}else{
//				TSysParameterEntity phoneEntity = new TSysParameterEntity();
//				phoneEntity.setCreateDate(DateUtils.gettimestamp());
//				phoneEntity.setParaCode(GlobalConstants.ORDER_PHONE);
//				phoneEntity.setParaValue(paramValue);
//				phoneEntity.setRetailerId(retailerId);
//				commonDao.save(phoneEntity);
//			}
//		}
//	}
 	
 	/**
 	 * 获取客服电话，退货电话和退货地址
 	 * @param retailerId 零售商id
 	 * @return
 	 */
//	@Override
// 	public List<TSysParameterEntity> getParameters(String retailerId){
// 		StringBuilder sb = new StringBuilder();
//		sb.append("from TSysParameterEntity where paraCode in('").append(GlobalConstants.ORDER_PHONE).append("' ")
//		.append(", '").append(GlobalConstants.ORDER_RETURN_PHONE).append("' ")
//		.append(", '").append(GlobalConstants.ORDER_RETURN_ADDRESS).append("' ")
//		.append(") and retailerId='").append(retailerId).append("'");
//		List<TSysParameterEntity> list = commonDao.findByQueryString(sb.toString());
//		return list;
// 	}
// 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
}