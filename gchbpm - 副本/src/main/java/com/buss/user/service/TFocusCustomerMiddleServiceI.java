package com.buss.user.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

public interface TFocusCustomerMiddleServiceI extends CommonService{
	/**
	 * 更新临时表顾客记录
	 */
	int updateAndAddTFocusCustomerMiddle(HttpServletRequest request,String rId);
	/**
	 * 删除临时表顾客记录
	 * @param request
	 */
	int updateAndDelTFocusCustomerMiddle(HttpServletRequest request,String rId);
	/**
	 * 删除临时顾客
	 * @param ids  逗号拼接
	 */
	int updateAndDelCst(String ids);
	/**
	 * 根据条件查询潜在顾客
	 * @param batchNo 批次号
	 * @return   <phoneNo,phoneNo>
	 */
	Map<String, String> getPhoneMapByBatchNo(String batchNo);
	/**
	 * 根据条件查询潜在顾客
	 * @param batchNo 批次号
	 * @return   List<phoneNo>
	 */
	List<String> getPhoneListByBatchNo(String batchNo);
	
}
