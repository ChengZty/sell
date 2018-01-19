<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>付款记录表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPayRecordController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPayRecordPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tPayRecordPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tPayRecordPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tPayRecordPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单号:
						</label>
					</td>
					<td class="value">
					     	 <input id="orderNo" name="orderNo" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							付款总额:
						</label>
					</td>
					<td class="value">
					     	 <input id="payAmount" name="payAmount" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">付款总额</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/order/tPayRecord.js"></script>		