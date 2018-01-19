<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_job</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
   $(function(){
	  $("#jobCode").change(function(){
		  if(""!=$(this).val()){
			  $("#title").val($(this).find("option:checked").text());
		  }
	  })
   })
  </script>
 </head>
 <body>
 	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tJobController.do?doAdd" tiptype="4">
		<input id="id" name="id" type="hidden" value="${tJobPage.id }">
		<input id="createName" name="createName" type="hidden" value="${tJobPage.createName }">
		<input id="createBy" name="createBy" type="hidden" value="${tJobPage.createBy }">
		<input id="createDate" name="createDate" type="hidden" value="${tJobPage.createDate }">
		<input id="updateName" name="updateName" type="hidden" value="${tJobPage.updateName }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tJobPage.updateBy }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tJobPage.updateDate }">
		<input id="status" name="status" type="hidden" value="A">
		<input id="jobType" name="jobType" type="hidden" value="3">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right" width="120">
					<label class="Validform_label">
						任务场景 :
					</label>
				</td>
				<td class="value">
					<select name="jobSceneCode">
						<option value="1">售前任务</option>
						<option value="2">售中任务</option>
						<option value="3">售后任务</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						任务名称:
					</label>
				</td>
				<td class="value">
				    <input id="title" name="title" type="text" style="width: 150px" class="inputxt" datatype="*">
					<%-- <t:dictSelect field="title" type="list" hasLabel="false" datatype="*" typeGroupCode="jobcode" id="jobCode"></t:dictSelect> --%>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">任务名称</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						任务编码:
					</label>
				</td>
				<td class="value">
				    <input id="jobCode" name="jobCode" type="text" style="width: 150px" class="inputxt" datatype="*">
					<%-- <t:dictSelect field="jobCode" type="list" hasLabel="false" datatype="*" typeGroupCode="jobcode" id="jobCode"></t:dictSelect> --%>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">任务名称</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						任务描述:
					</label>
				</td>
				<td class="value">
					<textarea rows="5" style="width: 100%" name="description" datatype="*"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">任务描述</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						APP端jobType:
					</label>
				</td>
				<td class="value">
			     	<input id="rate" name="rate" type="text" style="width: 150px" class="inputxt">
					<span class="Validform_checktip">与APP端jobType相同</span>
					<label class="Validform_label" style="display: none;">APP端jobType</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						目标数量:
					</label>
				</td>
				<td class="value">
			     	<input id="targetNum" name="targetNum" type="text" style="width: 150px" class="inputxt" datatype="n">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">目标数量</label>
				</td>
			<tr>
				<td align="right">
					<label class="Validform_label">
						金币数:
					</label>
				</td>
				<td class="value">
			     	<input id="goldNum" name="goldNum" type="text" style="width: 150px" class="inputxt" datatype="n">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">金币数</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
 </body>
