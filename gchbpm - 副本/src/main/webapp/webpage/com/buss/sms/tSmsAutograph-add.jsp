<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_sms_autograph</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSmsAutographController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tSmsAutographPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSmsAutographPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSmsAutographPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSmsAutographPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSmsAutographPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSmsAutographPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSmsAutographPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="retailerId" name="retailerId" type="hidden" value="${retailerId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							短信签名名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="autographName" name="autographName" type="text" style="width: 150px" class="inputxt">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信签名名称</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							短信签名状态:
						</label>
					</td>
					<td class="value">
					     	 <select id="autographStatus" name="autographStatus" style="width: 150px">
					     	 	<option value="0" selected="selected">审核中</option>
					     	 	<option value="1">审核通过</option>
					     	 	<option value="2">审核不通过</option>
					     	 </select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信签名状态，0审核中，1审核通过，2审核不通过</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
