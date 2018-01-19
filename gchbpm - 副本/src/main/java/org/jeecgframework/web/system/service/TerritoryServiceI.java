package org.jeecgframework.web.system.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.vo.AreaVo;

public interface TerritoryServiceI extends CommonService{

	List<AreaVo> getAreaList();

}
