package com.buss.activity.service;
import com.buss.activity.entity.TActivityGoodsEntity;
import com.buss.base.entity.BaseActivityEntity;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public interface TActivityGoodsServiceI extends CommonService{
	


	/**保存活动商品，并且把活动商品存入redis ，生存时间为活动结束时间到当前系统时间的秒数
	 * 格式 key: act_goods_retailerId_activityId ,value:List<Map<String,Object>>
	 * *[
	*    {
	*        "startTimeStamp": 1482985420000,
	*        "activityId": "4028819357a8d24b0157a90f773f0029",
	*        "goodsId": "2c928086557fd7ed0155815d6f5e019b",
	*        "endTimeStamp": 1495782470000,
	*        "addTime": "2017-05-11 15:48:17"
	*    },
	*    {
	*        "startTimeStamp": 1482985420000,
	*        "activityId": "4028819357a8d24b0157a90f773f0029",
	*        "goodsId": "40288193581fcc8101581ff8c4460040",
	*        "endTimeStamp": 1495782470000,
	*        "addTime": "2017-05-11 15:48:17"
	*    }
	*]
	 * @param goodsId
	 * @param actEntity
	 * @param contentId
	 * @param retailerId
	 */
	public void doAdd(String goodsId, BaseActivityEntity actEntity,	String contentId, String retailerId) throws Exception;

	/**删除动商品，并且把活动商品从redis中删除
	 * @param tActivityGoods
	 */
	public void doDel(TActivityGoodsEntity tActivityGoods) throws Exception;

	/**刷新所有零售商的活动商品缓存
	 * @throws Exception
	 */
	public void flushRedisAllActivityGoods() throws Exception;

}
