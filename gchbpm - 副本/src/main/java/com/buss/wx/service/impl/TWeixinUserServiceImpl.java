package com.buss.wx.service.impl;
import com.buss.wx.service.TWeixinUserServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.wx.entity.TWeixinUserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tWeixinUserService")
@Transactional
public class TWeixinUserServiceImpl extends CommonServiceImpl implements TWeixinUserServiceI {
}