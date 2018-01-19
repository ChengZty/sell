package com.buss.words.service;
import com.buss.words.entity.TCustWordsEntity;
import com.buss.words.entity.TCustWordsTagsEntity;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface TCustWordsServiceI extends CommonService{

	public String saveCustWords(TCustWordsEntity tCustWords, HttpServletRequest request);

	public String updateCustWords(TCustWordsEntity t, HttpServletRequest request);
	
	public void saveTradeTemplate(TCustWordsEntity tCustWords, String tradeIDs);

	/**通过subTypeId查询零售商二级分类可见的话术总个数*/
	Long getTotalVisibleCountBySubTypeId(String subTypeId);
	
	public void getPlatformList(HttpServletRequest request, DataGrid dataGrid,String platformType);

	public void goUpdateResult(TCustWordsEntity tCustWords,HttpServletRequest request);
	
	//获取商品话术
	public void getGoodsWordsList(HttpServletRequest request, DataGrid dataGrid);

	/**复制平台话术
	 * @param custWords
	 * @param retailerId
	 */
	public void doCopy(TCustWordsEntity custWords,String retailerId) throws Exception;

	/**根据话术模版二级分类批量插入关联行业
	 * @param subTypeId
	 * @param tradeIds
	 */
	public void batchInsertTradeIds(String subTypeId, String tradeIds) throws SQLException;
	/**
	 * 保存话术标签
	 */
	public void saveCustWordsTags(TCustWordsTagsEntity tCustWordsTags) throws SQLException;

	/**一键添加g+顾客话术*/
	public void doCopyPlatformWords() throws SQLException;

	/**一键删除g+顾客话术*/
	public void doDelPlatformWords();
	
}
