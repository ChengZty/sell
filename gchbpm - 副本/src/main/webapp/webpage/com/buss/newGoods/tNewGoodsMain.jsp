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
	<t:tab href="tNewGoodsController.do?retailerAllGoodsList"  title="自有商品" id="default"></t:tab>
	<t:tab href="tNewGoodsController.do?retailerAllVisiableGoodsList"  title="可见商品"  id="default2"></t:tab>
</t:tabs>
