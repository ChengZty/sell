package com.buss.ticket.service;
import com.buss.ticket.entity.TTicketInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface TTicketInfoServiceI extends CommonService{

	public String doBatchDistribute(HttpServletRequest request);

	public void doAdd(TTicketInfoEntity tTicketInfo);
}
