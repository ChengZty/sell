<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>经济实力</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tFinAbilityController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tFinAbilityPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tFinAbilityPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tFinAbilityPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tFinAbilityPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tFinAbilityPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tFinAbilityPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tFinAbilityPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tFinAbilityPage.status }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tFinAbilityPage.retailerId }">
					<input id="finCode" name="finCode" type="hidden" value="${tFinAbilityPage.finCode}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								编码:
							</label>
						</td>
						<td class="value">
						     	 ${tFinAbilityPage.finCode}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="finName" name="finName" type="text" style="width: 150px" class="inputxt" datetype="*" maxlength="10" value='${tFinAbilityPage.finName}'>
							<span class="Validform_checktip">对经济实力的说明（比如：强，中，弱）</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
