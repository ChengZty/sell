<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>充值</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tRechargeController.do?doAdd" tiptype="4">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							手机号:
						</label>
					</td>
					<td class="value">
					     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px" class="inputxt" datatype="m" maxlength="11">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">手机号</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="money" name="money" type="text" style="width: 150px" class="inputxt" datatype="ad">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">金额</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
							<textarea rows="5" name="remark" style="width: 98%" datatype="*" maxlength="300"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
