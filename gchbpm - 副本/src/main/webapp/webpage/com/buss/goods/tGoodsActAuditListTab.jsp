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
	<t:tab href="tGoodsActController.do?auditList"  title="促销活动审核" id="default"></t:tab>
	<t:tab href="tGoodsActController.do?invalidList"  title="已作废促销活动"  id="default2"></t:tab>
</t:tabs>