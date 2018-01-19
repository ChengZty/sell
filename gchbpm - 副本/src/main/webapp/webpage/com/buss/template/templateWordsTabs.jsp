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
		<t:tab href="templateWordsController.do?platformList"  title="g+话术模板" id="default"></t:tab>
		<t:tab href="templateWordsController.do?retailerList"  title="零售商话术模板"  id="default2"></t:tab>
	</c:if>
	<c:if test="${userType != '01' }">
		<t:tab href="templateWordsController.do?retailerList"  title="自有话术模板" id="default"></t:tab>
		<t:tab href="templateWordsController.do?platformList"  title="g+话术模板" id="default2"></t:tab>
<%-- 		<t:tab href="visibleTemplateWordsController.do?retailerVisibleList"  title="g+话术模板"  id="default2"></t:tab> --%>
	</c:if>
</t:tabs>
