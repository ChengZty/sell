<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>云商结算明细</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tCloudBillController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tCloudBillPage.id }">
					<input id="finBillId" name="finBillId" type="hidden" value="${tCloudBillPage.finBillId }">
					<input id="addTime" name="addTime" type="hidden" value="${tCloudBillPage.addTime }">
					<input id="orderNo" name="orderNo" type="hidden" value="${tCloudBillPage.orderNo }">
					<input id="subOrderNo" name="subOrderNo" type="hidden" value="${tCloudBillPage.subOrderNo }">
					<input id="businessDate" name="businessDate" type="hidden" value="${tCloudBillPage.businessDate }">
					<input id="cloudMoney" name="cloudMoney" type="hidden" value="${tCloudBillPage.cloudMoney }">
					<input id="storeId" name="storeId" type="hidden" value="${tCloudBillPage.storeId }">
					<input id="toStoreGoodsId" name="toStoreGoodsId" type="hidden" value="${tCloudBillPage.toStoreGoodsId }">
					<input id="toStoreGoodsName" name="toStoreGoodsName" type="hidden" value="${tCloudBillPage.toStoreGoodsName }">
					<input id="topCategoryId" name="topCategoryId" type="hidden" value="${tCloudBillPage.topCategoryId }">
					<input id="subCategoryId" name="subCategoryId" type="hidden" value="${tCloudBillPage.subCategoryId }">
					<input id="thridCategoryId" name="thridCategoryId" type="hidden" value="${tCloudBillPage.thridCategoryId }">
					<input id="brandId" name="brandId" type="hidden" value="${tCloudBillPage.brandId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/bill/tCloudBill.js"></script>		
