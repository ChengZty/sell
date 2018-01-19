<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>顾客话术类别</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  $("#festivalType").val("${templateType.festivalType}");
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tTemplateTypeController.do?doUpdateGoods" tiptype="4">
	<input id="id" name="id" type="hidden" value="${templateType.id }">
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="100">
				<label class="Validform_label">
					上级分类:
				</label>
			</td>
			<td class="value">${parentName}
			</td>
		</tr>
		<tr>
			<td align="right" width="100">
				<label class="Validform_label">
					类型名称:
				</label>
			</td>
			<td class="value">
			<input id="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*" maxlength="10" value='${templateType.name}'>
				<span class="Validform_checktip">不超过10个字</span>
				<label class="Validform_label" style="display: none;">类型名称</label>
			</td>
		</tr>
		</table>
	</t:formvalid>
 </body>
