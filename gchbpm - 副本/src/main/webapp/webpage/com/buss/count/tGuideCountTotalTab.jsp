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
	<t:tab href="guideGoodsCountController.do?listGoodsTotalCount"  title="商品浏览统计汇总" id="default"></t:tab>
	<t:tab href="guideNewsCountController.do?listNewsTotalCount"  title="资讯浏览统计汇总"  id="default2"></t:tab>
	<t:tab href="guideActivityCountController.do?listActivityTotalCount"  title="活动浏览统计汇总"  id="default3"></t:tab>
	<t:tab href="tWeixinUserController.do?listWeixinBrowseContent"  title="微信用戶浏览统计汇总"  id="default4"></t:tab>
</t:tabs>
