<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>退款退货</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tRefundReturnController.do?doNotAgree" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tRefundReturn.id }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
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
							拒绝退款原因:
						</label>
					</td>
					<td class="value">
					     	 <input id="sellerRemark" name="sellerRemark" type="text" maxlength="50" style="width: 250px"  >
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
	