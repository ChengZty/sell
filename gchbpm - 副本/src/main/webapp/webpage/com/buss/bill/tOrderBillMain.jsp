<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
.tabs-with-icon{
	padding-left: 8px;
	padding-right: 8px;
}
</style>
<t:tabs id="tt" iframe="true" tabPosition="top" >
	<t:tab  href="tOrderBillController.do?list&obState=1" title="本期待结" id="default"></t:tab>
	<t:tab  href="tOrderBillController.do?list&obState=0"  title="往期结算" id="default1"></t:tab>
</t:tabs>
