<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPersonController.do?doGoldToGuaid&guideId=${guideId}" tiptype="4" >
		<table  cellpadding="0" cellspacing="1"
			class="formtable">

			<tr>
				<td align="right" width="70px"><label class="Validform_label">
						操作类型: </label>
				</td>
				<td class="value"><select name="doType"
					style="width: 100px">
						<option value="1" selected="selected">增加</option>
						<option value="0">扣减</option>
				</select>
				</td>


				</td>
			</tr>
			<tr>
				<td align="right" width="70px"><label class="Validform_label">
						金币数量: </label>
				</td>
				<td class="value" ><input id="operateMoney"
					name="goldCount" type="text"  class="inputxt"
					maxlength="20" datatype="n"> <span
					class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right" width="70px"><label class="Validform_label">备注:
				</label>
				</td>
				<td class="value" ><input id="remark"
					name="description" type="text" class="inputxt"  style="width: 75%;"
					maxlength="20" datatype="*"> <span
					class="Validform_checktip"></span>
				</td>
			</tr>
		</table>
	</t:formvalid>

</body>



