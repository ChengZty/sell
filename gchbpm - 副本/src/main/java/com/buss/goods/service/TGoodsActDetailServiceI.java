package com.buss.goods.service;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.buss.goods.entity.TGoodsActDetailEntity;
import com.buss.goods.entity.TGoodsActEntity;
import com.buss.goods.vo.ActPriceImportVo;

public interface TGoodsActDetailServiceI extends CommonService{

	public Map<String, Object> batchSaveActPrice(String goodsActId, List<ActPriceImportVo> actPriceImportVoList,String retailerId) throws SQLException;

	/**获取其他活动和当前活动冲突的商品列表
	 * @param tGoodsAct
	 * @param retailerId
	 * @return
	 */
	List<TGoodsActDetailEntity> getOtherActDetailList(TGoodsActEntity tGoodsAct, String retailerId);
}
