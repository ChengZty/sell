package com.buss.refund.service;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.buss.refund.entity.TRefundReturnEntity;

public interface TRefundReturnServiceI extends CommonService{

	public void doNotAgree(TRefundReturnEntity tRefundReturn);

	/**退款(记录退款方式和流水号以及时间)
	 * @param id
	 */
	public Map<String,Object> doPay(TRefundReturnEntity tRefundReturn);

	public Long getToBeRefundCount(TSUser user);
}
