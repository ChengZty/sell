<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>订单信息</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true"  layout="table" tiptype="4" action="tOrderInfoController.do?doUpdateAddress">
		<input id="id" name="id" type="hidden" value="${tOrderInfoPage.id }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="120">
				<label class="Validform_label">收货人姓名:</label>
			</td>
			<td class="value">
		     	${tOrderInfoPage.reciverName}
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">收货人电话:</label>
			</td>
			<td class="value">
		     	 <input id="reciverPhone" name="reciverPhone" type="text" style="width: 150px" value='${tOrderInfoPage.reciverPhone}' datatype="m" maxlength="11">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">收货人电话</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">省:</label>
			</td>
			<td class="value">
		     	 <input id="reciverProvince" name="reciverProvince" type="text" style="width: 150px" value='${tOrderInfoPage.reciverProvince}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">省</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">市:</label>
			</td>
			<td class="value">
		     	 <input id="reciverCity" name="reciverCity" type="text" style="width: 150px" value='${tOrderInfoPage.reciverCity}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">市</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">区:</label>
			</td>
			<td class="value">
		     	 <input id="reciverRegion" name="reciverRegion" type="text" style="width: 150px" value='${tOrderInfoPage.reciverRegion}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">区</label>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label">详细地址:</label>
			</td>
			<td class="value">
		     	 <input id="reciverDetailInfo" name="reciverDetailInfo" type="text" style="width: 350px" value='${tOrderInfoPage.reciverDetailInfo}' datatype="*">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">详细地址</label>
			</td>
		</tr>
	</table>
	</t:formvalid>
 </body>
