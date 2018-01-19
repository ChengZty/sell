package org.jeecgframework.web.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.pojo.base.TSTerritory;
import org.jeecgframework.web.system.service.TerritoryServiceI;
import org.jeecgframework.web.system.vo.AreaVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("territoryService")
@Transactional
public class TerritoryServiceImpl extends CommonServiceImpl implements TerritoryServiceI {
	/**获取所有省的ID和name
	 * @return
	 */
	@Override
	public List<AreaVo> getAreaList(){
		List<AreaVo> list = new ArrayList<AreaVo>();
		CriteriaQuery cq = new CriteriaQuery(TSTerritory.class);
		cq.eq("territoryLevel", (short)2);
		cq.add();
		List<TSTerritory> territoryList = commonDao.getListByCriteriaQuery(cq, false);
		if(territoryList!=null){
			AreaVo blankVo = new AreaVo();
			blankVo.setAreaId("");
			blankVo.setAreaName("--请选择--");
			list.add(blankVo);
			for(TSTerritory ter:territoryList){
				AreaVo vo = new AreaVo();
				vo.setAreaId(ter.getId());
				vo.setAreaName(ter.getTerritoryName());
				list.add(vo);
			}
		}
		return list;
	}
	
}