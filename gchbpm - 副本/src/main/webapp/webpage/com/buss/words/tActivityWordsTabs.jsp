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
	<c:if test="${userType == '01' }">
		<t:tab href="tActivityWordsController.do?platformList"  title="g+活动话术" id="default"></t:tab>
		<t:tab href="tActivityWordsController.do?retailerList"  title="零售商活动话术"  id="default2"></t:tab>
	</c:if>
	<c:if test="${userType != '01' }">
		<t:tab href="tActivityWordsController.do?retailerList"  title="自有活动话术" id="default"></t:tab>
		<t:tab href="tVisibleWordsController.do?list&tp=3"  title="g+活动话术"  id="default2"></t:tab>
	</c:if>
</t:tabs>
