<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<style>
.tabs-with-icon{
	padding-left: 8px;
	padding-right: 8px;
}
</style>
<t:tabs id="tt" iframe="true" tabPosition="top">
	<t:tab href="guideGoodsCountController.do?listGoodsCount"  title="商品导购浏览统计" id="default"></t:tab>
	<t:tab href="guideNewsCountController.do?listNewsCount"  title="资讯导购浏览统计"  id="default2"></t:tab>
	<t:tab href="guideActivityCountController.do?listActivityCount"  title="活动导购浏览统计"  id="default3"></t:tab>
	<t:tab href="guideNoticeCountController.do?listNoticeCount"  title="公司通知导购浏览统计"  id="default4"></t:tab>
</t:tabs>
