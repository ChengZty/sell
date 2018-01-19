package com.buss.goods.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.buss.activity.entity.TGoodsActWordsEntity;
import com.buss.goods.entity.TGoodsActDetailEntity;
import com.buss.goods.entity.TGoodsActEntity;
import com.buss.goods.vo.ActPriceImportVo;

public interface TGoodsActServiceI extends CommonService{

	/**判断是否存在和其他活动冲突的商品，如果有则删掉，并导出错误列表
	 * @param tGoodsAct
	 * @param otherActDetailList 其他活动和当前活动冲突的商品列表
	 * @return 返回删掉的明细列表（用于导出）
	 */
	List<ActPriceImportVo> doCheckAndDel(TGoodsActEntity tGoodsAct,List<TGoodsActDetailEntity> otherActDetailList);

	/**审核并把活动商品信息放入redis
	 * @param tGoodsAct
	 * @return
	 */
	String doAudit(TGoodsActEntity tGoodsAct) throws Exception;

	/**下架并把活动商品信息从redis删除
	 * @param tGoodsAct
	 * @return
	 */
	String doDown(TGoodsActEntity tGoodsAct) throws Exception;

	/**删除活动和活动商品明细
	 * @param tGoodsAct
	 */
	void doDelAct(TGoodsActEntity tGoodsAct);
	/**
	 * 刪除活动关联话术
	 * @return
	 */
	void doDelActWords(TGoodsActWordsEntity tActWords);
	/**
	 *活动话题列表
	 */
	void newsDatagrid(String actId, String title, String sortName,DataGrid dataGrid);
	
	/**
	 * 添加活动话题列表
	 */
	void actNewsDatagrid (String actId, String title, String sortName,DataGrid dataGrid);
	
	/**
	 * 删除活动话题
	 */
	String deleteNews(String Id, String actId);
	
	/**
	 * 添加关联话题
	 */
	String doBatchAdd(String actId,String newsIds);
	
	/**
	 * 促销活动作废
	 * @param actId
	 */
	void doInvalid(String actId) ;
	
	/**
	 * 更新活动
	 * <li>更新活动
	 * <li>更新活动与店铺的关联表：清空该活动关联店铺的所有记录,重新添加活动关联店铺的记录
	 * @param actId
	 */
	void doUpdate(TGoodsActEntity tGoodsAct, HttpServletRequest request) throws Exception  ;
	
	/**
	 * 添加活动
	 * <li>添加活动
	 * <li>添加活动关联店铺的记录
	 * <li>判断是否存在和其他活动冲突的商品，如果有则删掉，并导出错误列表
	 * @param tGoodsAct
	 * @param request
	 * @return 返回删掉的明细列表（用于导出）
	 */
	List<ActPriceImportVo> doAdd(TGoodsActEntity tGoodsAct, HttpServletRequest request)  ;
}
