<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<style>
.tabs-with-icon{
	padding-left: 8px;
	padding-right: 8px;
}
</style>
<t:tabs id="tt" iframe="true" tabPosition="top">
	<t:tab href="tJobDetailCountController.do?dayList"  title="任务统计日报表" id="default"></t:tab>
	<t:tab href="tJobDetailCountController.do?rangeList"  title="任务统计汇总"  id="default2"></t:tab>
	<t:tab href="tJobDetailCountController.do?rangeGoldList"  title="导购金币统计汇总"  id="default3"></t:tab>
</t:tabs>