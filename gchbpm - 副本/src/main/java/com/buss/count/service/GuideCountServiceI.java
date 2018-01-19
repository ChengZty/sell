package com.buss.count.service;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.buss.count.vo.GuideCountVo;

public interface GuideCountServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(GuideCountVo t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(GuideCountVo t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(GuideCountVo t);
 	/**
 	 * 获取初始化数据
 	 * @param request
 	 */
	void getInitData(HttpServletRequest request);

	String getActivitySql(String retailerId, String storeId, String personId,
			String title, String startTime, String endTime);

	String getActivityCountSql(String retailerId, String storeId,
			String personId, String title,String startTime,String endTime);

	String getGoodsSql(String retailerId, String storeId, String personId,
			String code, String startTime, String endTime, String goodsName,
			String brandName);

	String getGoodsCountSql(String retailerId, String storeId, String personId,
			String code, String goodsName, String brandName,String startTime,String endTime);

	String getNewsSql(String retailerId, String storeId, String personId,
			String title, String startTime, String endTime);

	String getNewsCountSql(String retailerId, String storeId, String personId,
			String title,String startTime,String endTime);

	String getNoticeSql(String retailerId, String storeId, String personId,
			String title, String startTime, String endTime);

	String getNoticeCountSql(String retailerId, String storeId,
			String personId, String title,String startTime,String endTime);

	String getNewsTotalSql(HttpServletRequest request,DataGrid dataGrid);

	String getNewsTotalCountSql(HttpServletRequest request);

	public String getGoodsTotalSql(HttpServletRequest request,DataGrid dataGrid);

	public String getGoodsTotalCountSql(HttpServletRequest request);

	public String getActivityTotalSql(HttpServletRequest request,DataGrid dataGrid);

	public String getActivityTotalCountSql(HttpServletRequest request);
//	String getActivityTotalSql(String retailerId, String startTime, String endTime, String title);

	/**初始化初次查询的时间范围，本月1号（searchTime_begin）到当天（searchTime_end）
	 * 
	 * @param request
	 */
	public void initFirstSearchTimeRange(HttpServletRequest request);
	public void getGuideGoldCountSql(HttpServletRequest request, DataGrid dataGrid);
}
