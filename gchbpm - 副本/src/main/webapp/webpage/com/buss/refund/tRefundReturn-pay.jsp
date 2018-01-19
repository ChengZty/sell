<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>退款退货</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  $("#payMethodName").val("${tRefundReturn.payMethodName }");
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tRefundReturnController.do?doPay" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tRefundReturn.id }">
					<input id="subOrderNo" name="subOrderNo" type="hidden" value="${tRefundReturn.subOrderNo }">
					<input id="userId" name="userId" type="hidden" value="${tRefundReturn.userId }">
					<input id="userPhone" name="userPhone" type="hidden" value="${tRefundReturn.userPhone }">
					<input id="refundNo" name="refundNo" type="hidden" value="${tRefundReturn.refundNo }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							退款方式:
						</label>
					</td>
					<td class="value">
						<select name="payMethodName" id="payMethodName" datatype="*">
<!-- 							<option value="">---请选择---</option> -->
<!-- 							<option value="G+卡">G+卡</option> -->
							<option value="微信">微信</option>
							<option value="支付宝">支付宝</option>
						</select>
							<label class="Validform_label" style="display: none;">退款方式</label>
						</td>
					</tr>
					<c:if test="${tRefundReturn.refundAmount > 0}">
					<tr>
					<td align="right">
						<label class="Validform_label">
							申请退款金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="refundAmount" name="refundAmount" type="text" datatype="money" style="width: 150px" 
					     	 value="${tRefundReturn.refundAmount }" readonly="readonly">
							<span class="Validform_checktip"></span>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							实际退款金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="refundAmount" name="refundAmountReal" type="text" datatype="money" style="width: 150px" 
					     	 value="${tRefundReturn.refundAmount }" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">实际退款金额</label>
						</td>
					</tr>
					</c:if>
					<tr>
					<td align="right">
						<label class="Validform_label">
							交易号:
						</label>
					</td>
					<td class="value">
					     	 <input id="payNo" name="payNo" type="text"   style="width: 150px"  >
							<span class="Validform_checktip">微信支付交易单号或支付宝交易号</span>
							<label class="Validform_label" style="display: none;">交易号</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							退款备注:
						</label>
					</td>
					<td class="value">
					     	 <input id="adminRemark" name="adminRemark" type="text" maxlength="50" style="width: 250px"  >
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
	