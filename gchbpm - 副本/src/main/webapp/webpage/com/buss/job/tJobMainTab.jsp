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
	<t:tab href="tDayJobDetailController.do?list"  title="每日任务明细" id="default"></t:tab>
	<t:tab href="tDayAwardDetailController.do?list"  title="每日奖励明细"  id="default2"></t:tab>
	<t:tab href="tJobGrowController.do?list"  title="成长任务"  id="default3"></t:tab>
</t:tabs>
