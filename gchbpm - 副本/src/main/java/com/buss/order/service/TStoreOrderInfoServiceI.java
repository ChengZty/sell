package com.buss.order.service;


import java.util.List;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.buss.order.entity.TStoreOrderInfoEntity;

import javax.servlet.http.HttpServletRequest;

public interface TStoreOrderInfoServiceI extends CommonService{
 	
	//线下订单明细列表
	public List<TStoreOrderInfoEntity> getTStoreOrderInfo(HttpServletRequest request,DataGrid dataGrid,String type);
}
