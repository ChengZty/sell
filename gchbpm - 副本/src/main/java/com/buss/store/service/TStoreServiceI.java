package com.buss.store.service;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.vo.CommonVo;

import com.buss.store.entity.TStoreEntity;
import com.buss.store.vo.StoreImportVo;

public interface TStoreServiceI extends CommonService{
	
	public void saveStore(TStoreEntity tStore);

	public String deleteStore(String storeId);

	public void updateStore(TStoreEntity t);
	
	/**获取零售商店铺列表（id，name）
	 * @return
	 */
	List<CommonVo> getStoreList();

	/**批量保存实体店
	 * @param importList
	 * @return
	 */
	public Map<String, String> batchSaveStores(List<StoreImportVo> importList) throws Exception;
}
