package com.buss.order.service.impl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buss.order.service.TOrderLogServiceI;

@Service("tOrderLogService")
@Transactional
public class TOrderLogServiceImpl extends CommonServiceImpl implements TOrderLogServiceI {

}