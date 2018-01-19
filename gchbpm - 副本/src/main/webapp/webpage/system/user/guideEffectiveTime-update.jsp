<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>批量编辑导购有效时间</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="userController.do?batchSaveEffectiveTime" tiptype="4">
	<input id="id" name="ids" type="hidden" value="${ids }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="30%" >
                <label class="Validform_label">有效时间: </label>
            </td>
			<td class="value" width="70%">
                <input name="effectiveTime" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px" dataType="*"> 
				<span class="Validform_checktip"></span>
            </td>
		</tr>
	</table>
</t:formvalid>
</body>
