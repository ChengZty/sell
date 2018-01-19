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
	<t:tab href="templateNewsController.do?retailerNewsList"  title="自有话题"  id="default"></t:tab>
<%-- 	<t:tab href="tHiddenNewsController.do?list"  title="g+话题" id="default2"></t:tab> --%>
	<t:tab href="templateNewsController.do?platformNewsList"  title="g+话题" id="default2"></t:tab>
</t:tabs>
