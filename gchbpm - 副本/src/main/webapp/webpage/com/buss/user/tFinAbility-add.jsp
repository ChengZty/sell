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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tFinAbilityController.do?doAdd" tiptype="4">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="finCode" name="finCode" type="text" validType="t_fin_ability,status = 'A' and retailer_Id = '${retailerId }' and fin_code" style="width: 150px" class="inputxt" datetype="n" max="3">
							<span class="Validform_checktip">编码为3位以内的数字</span>
							<label class="Validform_label" style="display: none;">编码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="finName" name="finName" type="text" style="width: 150px" class="inputxt" datetype="*" maxlength="10">
							<span class="Validform_checktip">对经济实力的说明（比如：强，中，弱）</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
