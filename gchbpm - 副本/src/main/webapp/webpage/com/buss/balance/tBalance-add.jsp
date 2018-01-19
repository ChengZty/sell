<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>余额</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBalanceController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tBalancePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tBalancePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBalancePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBalancePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBalancePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBalancePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBalancePage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tBalancePage.status }">
					<input id="userId" name="userId" type="hidden" value="${tBalancePage.userId }">
					<input id="password" name="password" type="hidden" value="${tBalancePage.password }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							余额:
						</label>
					</td>
					<td class="value">
					     	 <input id="totalBalance" name="totalBalance" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">余额</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							可用余额:
						</label>
					</td>
					<td class="value">
					     	 <input id="availableBalance" name="availableBalance" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">可用余额</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							冻结余额:
						</label>
					</td>
					<td class="value">
					     	 <input id="frozenBalance" name="frozenBalance" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">冻结余额</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							状态:
						</label>
					</td>
					<td class="value">
					     	 <input id="balanceStatus" name="balanceStatus" type="text" style="width: 150px" class="inputxt"
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">状态</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/balance/tBalance.js"></script>		