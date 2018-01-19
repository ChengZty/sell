package com.buss.user.service;
import com.buss.user.entity.TPersonEntity;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.vo.CommonVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface TPersonServiceI extends CommonService{

	public String saveTPersonAndUser(TPersonEntity tPerson,TSUser users);

	/**保存导购*/
	public void saveGuide(TPersonEntity tPerson,String initPwd);

	public String batchSaveGuides(List<TPersonEntity> listTPersonEntitys);

	public void doChangeArea(HttpServletRequest request);
	/**
	 * 获取用户id和真实名称
	 * @param userType 03导购
	 * @return
	 */
	List<CommonVo> getPersonList(String userType);
	/**
	 * 获取某个店铺下用户person id和真实名称
	 * @param userType 03导购
	 * @param storeId 店铺id
	 * @return
	 */
	List<CommonVo> getPersonListByStoreId(String userType, String storeId);
	
	TPersonEntity getPersonByUserId(String userId);

	/**更新导购，如果有更新店铺ID，则把日销售报表的店铺ID同步更新
	 * @param tPerson
	 */
	public void doUpdateGuide(TPersonEntity tPerson) throws Exception;

	/**初始化一个导购测试帐号*/
	public void doInitGuideAccount(Map<String, Object> map);

	/**
	 平台操作导购金币
	 * @param queryCreateDateEnd 
	 * @param queryCreateDateBegin 
	 */
	public String doGold(String guaidId, String retailerId, Integer doType,
			String description, Integer goldCount);

	public void datagridOfGuideGoldDetail(String guideId, String retailerId, DataGrid dataGrid , String queryCreateDateBegin, String queryCreateDateEnd);
	
}
