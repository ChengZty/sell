package com.buss.user.service;
import com.buss.user.entity.TFocusCustomerEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface TFocusCustomerServiceI extends CommonService{
	

	public String doBatchSave(List<TFocusCustomerEntity> listTFocusCustomerEntitys) throws Exception;
	/**
	 * 验证电话号码在平台是否存在
	 * @param phoneNo
	 * @return
	 */
	public int checkExist(String phoneNo);
	/**
	 * 验证电话号码在零售商平台是否存在
	 * @param phoneNo 手机号
	 * @param retailerId 零售商
	 * @return
	 */
	public int checkPhoneAndRetailerExist(String phoneNo,String retailerId);
	/**
	 * 验证电话号码在平台是否存在，并且不是当前记录
	 * @param phoneNo
	 * @param id
	 * @return
	 */
	public boolean checkPhoneExist(String phoneNo, TFocusCustomerEntity t);

	public void saveFocusCustm(int flag, TFocusCustomerEntity tFocusCustomer);

	/**批量保存潜在顾客信息
	 * @param listTFocusCustomerEntitys
	 * @return
	 * @throws Exception
	 */
	public String doBatchSaveInfo (List<TFocusCustomerEntity> listTFocusCustomerEntitys) throws Exception;

//	/**
//	 * 更新手机号码归属地
//	 */
//	public void updateEmptyPhoneArea();

	/**校验导入的号码是否和系统中的有重复，有则返回重复的号码
	 * @param listTFocusCustomerEntitys
	 * @return
	 */
	public String getExistPhoneNo(List<TFocusCustomerEntity> listTFocusCustomerEntitys);

	public void updateCustomer(TFocusCustomerEntity t);
	/**
	 * 根据条件查询潜在顾客
	 * @param request
	 * @param rId 零售商id
	 * @return
	 */
	List<TFocusCustomerEntity> getListByConditions(HttpServletRequest request,
			String rId);

	/**判断并取消分配潜在顾客
	 * @param id
	 */
	public void doCancelGive(String id);
	
	/**
	 * 导购停用、回收导购录入的顾客资料（包括待发展和交易顾客）
	 * @param retailerId
	 * @param guideId 
	 */
	public void revokeCustInfo(String retailerId, String guideId);
	/**
	 * 
	 * 批量分配待发展顾客
	 * @param ids
	 * @param guideId
	 */
	public void doBatchGive(String ids, String guideId);
}
