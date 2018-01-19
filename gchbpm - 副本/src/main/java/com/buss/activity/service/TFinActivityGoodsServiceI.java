package com.buss.activity.service;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

public interface TFinActivityGoodsServiceI extends CommonService{

	/**校验并批量添加商品，过滤冲突的商品
	 * @param request
	 * @return
	 * @throws SQLException 
	 */
	public String doCheckAndBatchAdd(HttpServletRequest request) throws SQLException;
}
