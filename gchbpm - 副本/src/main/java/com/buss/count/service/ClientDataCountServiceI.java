package com.buss.count.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;


public interface ClientDataCountServiceI extends CommonService{
	public List<Map<String, Object>> getGuideNumList(HttpServletRequest request,String type);
	//获取导购点击APP数信息
	public List<Map<String, Object>> getClickGuideNumList(HttpServletRequest request);
	//获取成交导购数
	public List<Map<String, Object>> getGuideOrderList(HttpServletRequest request);
	//成交单数
	public List<Map<String, Object>> getPayOrderNumList(HttpServletRequest request);
	//非实体营业时间成交单数
	public List<Map<String, Object>> getOutTimeOrderNumList(HttpServletRequest request);

	//获取待发展+已交易顾客数
	List<Map<String ,Object>> countCustomer(HttpServletRequest request,Integer type);
	//获取资料完成度不同的顾客数
	List<Map<String,Object>> countCustomerPercent(HttpServletRequest request ,Integer beginPercent,Integer endPercent);
	//根据需要获取商品数量
	List<Map<String,Object>> countGoods(HttpServletRequest request, Integer type);
	//查询含有话术的商品占比
	List<Map<String,Object>> countHasWordsPercent(HttpServletRequest request);
	//查询已发布的报表话题数
	public List<Map<String, Object>> hasUploadedNumList(HttpServletRequest request);
	//查询报表点击话题数
	public List<Map<String, Object>> hasClickNumList(HttpServletRequest request);
	//查询已推送话题数
	public List<Map<String, Object>> hasIssendList(HttpServletRequest request);
	//查询顾客话术量
	public List<Map<String, Object>> hasCustWordsNumList(HttpServletRequest request);
	//完成任务的导购数
	public List<Map<String, Object>> finishJobGuideNumList(HttpServletRequest request);
	//线上任务数
	public List<Map<String, Object>> onlineJobNumList(HttpServletRequest request);
	//任务完成度统计
	public List<Map<String, Object>> jobFinishCountList(HttpServletRequest request);
	
	
}
