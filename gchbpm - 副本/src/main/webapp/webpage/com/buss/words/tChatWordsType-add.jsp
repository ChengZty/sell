<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>撩客话术类别</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
   $(function(){
	   $("#name").focus();
   })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tChatWordsTypeController.do?doAdd" tiptype="4">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							类型名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*" maxlength="10">
							<span class="Validform_checktip">不超过10个字</span>
							<label class="Validform_label" style="display: none;">类型名称</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
