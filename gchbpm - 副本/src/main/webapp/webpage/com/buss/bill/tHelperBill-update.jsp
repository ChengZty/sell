<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>帮手结算明细</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tHelperBillController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tHelperBillPage.id }">
					<input id="finBillId" name="finBillId" type="hidden" value="${tHelperBillPage.finBillId }">
					<input id="addTime" name="addTime" type="hidden" value="${tHelperBillPage.addTime }">
					<input id="orderNo" name="orderNo" type="hidden" value="${tHelperBillPage.orderNo }">
					<input id="subOrderNo" name="subOrderNo" type="hidden" value="${tHelperBillPage.subOrderNo }">
					<input id="businessDate" name="businessDate" type="hidden" value="${tHelperBillPage.businessDate }">
					<input id="helperMoney" name="helperMoney" type="hidden" value="${tHelperBillPage.helperMoney }">
					<input id="helperId" name="helperId" type="hidden" value="${tHelperBillPage.helperId }">
					<input id="helperName" name="helperName" type="hidden" value="${tHelperBillPage.helperName }">
					<input id="helperType" name="helperType" type="hidden" value="${tHelperBillPage.helperType }">
					<input id="subCategoryId" name="subCategoryId" type="hidden" value="${tHelperBillPage.subCategoryId }">
					<input id="guideId" name="guideId" type="hidden" value="${tHelperBillPage.guideId }">
					<input id="storeId" name="storeId" type="hidden" value="${tHelperBillPage.storeId }">
					<input id="toStoreGoodsId" name="toStoreGoodsId" type="hidden" value="${tHelperBillPage.toStoreGoodsId }">
					<input id="storeType" name="storeType" type="hidden" value="${tHelperBillPage.storeType }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			</table>
		</t:formvalid>
 </body>
