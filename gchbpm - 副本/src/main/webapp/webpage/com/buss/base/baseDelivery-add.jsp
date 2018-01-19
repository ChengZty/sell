<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>物流</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="baseDeliveryController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${baseDeliveryPage.id }">
					<input id="createName" name="createName" type="hidden" value="${baseDeliveryPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${baseDeliveryPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${baseDeliveryPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${baseDeliveryPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${baseDeliveryPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${baseDeliveryPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							物流编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="deliveryCode" name="deliveryCode" type="text" validType="base_delivery,status = 'A' and delivery_code"  style="width: 150px"   datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" >物流编码为物流公司名字拼音小写</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							物流公司:
						</label>
					</td>
					<td class="value">
					     	 <input id="deliveryName" name="deliveryName" type="text" style="width: 150px"   datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">物流公司</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/base/baseDelivery.js"></script>		