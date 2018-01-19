<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>订单结算表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOrderBillController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tOrderBillPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								结算单编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="orderBillSn" name="orderBillSn" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.orderBillSn}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结算单编号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								开始日期:
							</label>
						</td>
						<td class="value">
									  <input id="startTime" name="startTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tOrderBillPage.startTime}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">开始日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								结束日期:
							</label>
						</td>
						<td class="value">
									  <input id="endTime" name="endTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tOrderBillPage.endTime}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								订单金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="obOrderTotals" name="obOrderTotals" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obOrderTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								运费金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="osShippingTotals" name="osShippingTotals" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.osShippingTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">运费金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								佣金金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="obCommisTotals" name="obCommisTotals" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obCommisTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">佣金金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								退单金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="obOrderReturnTotals" name="obOrderReturnTotals" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obOrderReturnTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">退单金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								退还佣金:
							</label>
						</td>
						<td class="value">
						     	 <input id="obCommisReturnTotals" name="obCommisReturnTotals" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obCommisReturnTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">退还佣金</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								应结金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="obResultTotals" name="obResultTotals" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obResultTotals}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">应结金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								生成结算单日期:
							</label>
						</td>
						<td class="value">
									  <input id="obCreateDate" name="obCreateDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tOrderBillPage.obCreateDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">生成结算单日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								结算单年月份:
							</label>
						</td>
						<td class="value">
						     	 <input id="obCreateMonth" name="obCreateMonth" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obCreateMonth}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结算单年月份</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								结算状态:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="obState" type="list"
										typeGroupCode="ob_status" defaultVal="${tOrderBillPage.obState}" hasLabel="false"  title="结算状态"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结算状态</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								付款日期:
							</label>
						</td>
						<td class="value">
						     	 <input id="obPayDate" name="obPayDate" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obPayDate}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">付款日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								支付备注:
							</label>
						</td>
						<td class="value">
						     	 <input id="obPayContent" name="obPayContent" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obPayContent}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">支付备注</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								结算对象ID:
							</label>
						</td>
						<td class="value">
						<input id="obPayerId" name="obPayerId" type="text" style="width: 150px" class="searchbox-inputtext"  value='${tOrderBillPage.obPayerId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结算对象ID</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								结算对象名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="obPayerName" name="obPayerName" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obPayerName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结算对象名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								手机号:
							</label>
						</td>
						<td class="value">
						     	 <input id="obPayerPhone" name="obPayerPhone" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obPayerPhone}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">手机号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								零售商ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="obStoreId" name="obStoreId" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obStoreId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">零售商ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								零售商名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="obStoreName" name="obStoreName" type="text" style="width: 150px" class="inputxt"  value='${tOrderBillPage.obStoreName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">零售商名称</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/bill/tOrderBill.js"></script>		