package com.buss.wx.service.impl;
import com.buss.wx.service.TWeixinUserBindServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.wx.entity.TWeixinUserBindEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tWeixinUserBindService")
@Transactional
public class TWeixinUserBindServiceImpl extends CommonServiceImpl implements TWeixinUserBindServiceI {

}