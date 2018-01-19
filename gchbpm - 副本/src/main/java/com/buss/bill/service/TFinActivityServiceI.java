package com.buss.bill.service;
import com.buss.activity.entity.TFinActivityGoodsEntity;
import com.buss.bill.entity.TFinActivityEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface TFinActivityServiceI extends CommonService{

	/**如果类型为品牌或者全馆商品，则把对应的销售中的商品查询出来并保存到明细表
	 * 校验并删除存在冲突的商品
	 * @param tFinActivity
	 * @throws SQLException
	 */
	public String saveFinActivity(TFinActivityEntity tFinActivity) throws SQLException;

	/**获取活动时间冲突的商品列表
 	 * @param tFinActivity
 	 * @param retailerId
 	 * @return
 	 */
	public List<TFinActivityGoodsEntity> getOtherFinActDetailList(TFinActivityEntity tFinActivity, String retailerId);
}
