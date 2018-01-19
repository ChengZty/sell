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
	<t:tab href="tNewsTypeController.do?list"  title="g+话题分类" id="default"></t:tab>
	<t:tab href="tNewsTypeController.do?retailerList"  title="零售商话题分类"  id="default2"></t:tab>
</t:tabs>
