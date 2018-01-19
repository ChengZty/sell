<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>结算明细</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="orderBillDetailController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${orderBillDetailPage.id }">
					<input id="obOrderSn" name="obOrderSn" type="hidden" value="${orderBillDetailPage.obOrderSn }">
					<input id="obYear" name="obYear" type="hidden" value="${orderBillDetailPage.obYear }">
					<input id="obMonth" name="obMonth" type="hidden" value="${orderBillDetailPage.obMonth }">
					<input id="obStartDate" name="obStartDate" type="hidden" value="${orderBillDetailPage.obStartDate }">
					<input id="obEndDate" name="obEndDate" type="hidden" value="${orderBillDetailPage.obEndDate }">
					<input id="obPayDate" name="obPayDate" type="hidden" value="${orderBillDetailPage.obPayDate }">
					<input id="obOrderTotals" name="obOrderTotals" type="hidden" value="${orderBillDetailPage.obOrderTotals }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								运费:
							</label>
						</td>
						<td class="value">
						     	 <input id="obShippingTotals" name="obShippingTotals" type="text" style="width: 150px" class="inputxt"  value='${orderBillDetailPage.obShippingTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">运费</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								佣金金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="obCommisTotals" name="obCommisTotals" type="text" style="width: 150px" class="inputxt"  value='${orderBillDetailPage.obCommisTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">佣金金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								退款金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="obOrderReturnTotals" name="obOrderReturnTotals" type="text" style="width: 150px" class="inputxt"  value='${orderBillDetailPage.obOrderReturnTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">退款金额</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								退还佣金:
							</label>
						</td>
						<td class="value">
						     	 <input id="obCommisReturnTotals" name="obCommisReturnTotals" type="text" style="width: 150px" class="inputxt"  value='${orderBillDetailPage.obCommisReturnTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">退还佣金</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								本期应结:
							</label>
						</td>
						<td class="value">
						     	 <input id="obResultTotals" name="obResultTotals" type="text" style="width: 150px" class="inputxt"  value='${orderBillDetailPage.obResultTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">本期应结</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								状态:
							</label>
						</td>
						<td class="value">
						     	 <input id="obState" name="obState" type="text" style="width: 150px" class="inputxt"  value='${orderBillDetailPage.obState}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">状态</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								结算对象ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="obPayerId" name="obPayerId" type="text" style="width: 150px" class="inputxt"  value='${orderBillDetailPage.obPayerId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结算对象ID</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								结算对象名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="obPayerName" name="obPayerName" type="text" style="width: 150px" class="inputxt"  value='${orderBillDetailPage.obPayerName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结算对象名称</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/bill/orderBillDetail.js"></script>		