<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_sms_sub_account</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
	  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSmsSubAccountController.do?doUpdate" tiptype="4">
			<input id="id" name="id" type="hidden" value="${tSmsSubAccountPage.id }">
			<input id="createName" name="createName" type="hidden" value="${tSmsSubAccountPage.createName }">
			<input id="createBy" name="createBy" type="hidden" value="${tSmsSubAccountPage.createBy }">
			<input id="createDate" name="createDate" type="hidden" value="${tSmsSubAccountPage.createDate }">
			<input id="updateName" name="updateName" type="hidden" value="${tSmsSubAccountPage.updateName }">
			<input id="updateDate" name="updateDate" type="hidden" value="${tSmsSubAccountPage.updateDate }">
			<input id="status" name="status" type="hidden" value="${tSmsSubAccountPage.status }">
			<input id="retailerId" name="retailerId" type="hidden" value="${tSmsSubAccountPage.retailerId }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							公司签名:
						</label>
					</td>
					<td class="value">
				     	 <input id="companyAutographName" name="companyAutographName" type="text" style="width: 150px" class="inputxt"   readonly="readonly"
				     	 value='${tSmsSubAccountPage.companyAutographName }' datatype="*">
						<span class="Validform_checktip">用于短信验证码签名</span>
						<label class="Validform_label" style="display: none;">公司签名</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							短信总条数:
						</label>
					</td>
					<td class="value">
				     	 <input id="smsNumber" name="smsNumber" type="text" style="width: 150px" class="inputxt" readonly="readonly"
				     	 value="${tSmsSubAccountPage.smsNumber }"  datatype="n" >
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">短信条数</label>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							变动条数:
						</label>
					</td>
					<td class="value">
				     	 <input id="modifyNumber" name="modifyNumber" type="text" style="width: 150px" class="inputxt"  datatype="allNumber" >
						<span class="Validform_checktip">正数为增加，负数为扣减</span>
						<label class="Validform_label" style="display: none;">变动条数</label>
					</td>
				</tr>
		</table>
	</t:formvalid>
 </body>
