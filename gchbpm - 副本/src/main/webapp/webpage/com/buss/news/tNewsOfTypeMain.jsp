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
 <c:if test="${userType =='01' }">
	<c:if test="${newsType =='6001' }">
		<t:tab href="tNewsController.do?tNewsOfType&newsType=${newsType}&isRet=0"  title="g+管家课堂"  id="default"></t:tab>
		<t:tab href="tNewsController.do?tNewsOfType&newsType=${newsType}&isRet=A"  title="零售商管家课堂" id="default2"></t:tab>
	</c:if>
	<c:if test="${newsType =='6002' }">
		<t:tab href="tNewsController.do?tNewsOfType&newsType=${newsType}&isRet=0"  title="g+管家故事"  id="default"></t:tab>
		<t:tab href="tNewsController.do?tNewsOfType&newsType=${newsType}&isRet=A"  title="零售商管家故事" id="default2"></t:tab>
	</c:if>
</c:if>
<c:if test="${userType !='01' }">
	<c:if test="${newsType =='6001' }">
		<t:tab href="tNewsController.do?tNewsOfType&newsType=${newsType}&isRet=1"  title="自有管家课堂"  id="default"></t:tab>
		<t:tab href="tNewsController.do?tNewsOfType&newsType=${newsType}&isRet=0"  title="g+管家课堂" id="default2"></t:tab>
	</c:if>
	<c:if test="${newsType =='6002' }">
		<t:tab href="tNewsController.do?tNewsOfType&newsType=${newsType}&isRet=1"  title="自有管家故事" id="default"></t:tab>
		<t:tab href="tNewsController.do?tNewsOfType&newsType=${newsType}&isRet=0"  title="g+管家故事"  id="default2"></t:tab>
	</c:if>
</c:if> 
</t:tabs>
