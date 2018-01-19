<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_sms_sub_account</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSmsSubAccountController.do?doAdd" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tSmsSubAccountPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSmsSubAccountPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSmsSubAccountPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSmsSubAccountPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSmsSubAccountPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSmsSubAccountPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="retailerId" name="retailerId" type="hidden" value="${retailerId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							短信子账号:
						</label>
					</td>
					<td class="value">
					     	 <input id="smsName" name="smsName" type="text" style="width: 150px" class="inputxt" datatype="*" errormsg="请录入短信子账号" checktip="请录入短信子账号">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信子账号</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							子账号密码:
						</label>
					</td>
					<td class="value">
					     	 <input id="smsPassword" name="smsPassword" type="text" style="width: 150px" class="inputxt" datatype="*" errormsg="请录入密码" checktip="请录入密码">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">子账号密码</label>
						</td>
						</tr> -->
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							公司签名:
						</label>
					</td>
					<td class="value">
					     	 <input id="companyAutographName" name="companyAutographName" type="text" style="width: 150px" class="inputxt"  datatype="*">
							<span class="Validform_checktip">用于短信验证码签名(录入后不可修改)</span>
							<label class="Validform_label" style="display: none;">公司签名</label>
						</td>
					</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							产品ID:
						</label>
					</td>
					<td class="value">
					     	 <input id="productId" name="productId" type="text" style="width: 150px" class="inputxt" datatype="*" value="${productId }">
							<span class="Validform_checktip">默认营销短信产品ID</span>
							<label class="Validform_label" style="display: none;">产品ID</label>
						</td>
					</tr> --%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								短信总条数:
							</label>
						</td>
						<td class="value">
					     	 <input id="smsNumber" name="smsNumber" type="text" style="width: 150px" class="inputxt"  datatype="n" >
							<span class="Validform_checktip">整数</span>
							<label class="Validform_label" style="display: none;">短信条数</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
