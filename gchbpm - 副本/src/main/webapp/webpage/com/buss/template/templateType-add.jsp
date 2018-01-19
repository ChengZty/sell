<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>模板分类添加</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tTemplateTypeController.do?doAdd" tiptype="4">
		<input id="status" name="status" type="hidden" value="A">
		<input id="parent" name="parent.id" type="hidden" value="${parent.id}">
		<input id="level" name="level" type="hidden" value="${parent.level+1}">
		<input id="templateType" name="templateType" type="hidden" value="${parent.templateType}">
		<input id="platformType" name="platformType" type="hidden" value="${parent.platformType}">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							上级分类:
						</label>
					</td>
					<td class="value">${parent.name}
					</td>
				</tr>
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
				<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							类型编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="code" name="code" type="text" style="width: 150px" class="inputxt" datatype="*" maxlength="10">
							<span class="Validform_checktip">不超过10个字</span>
							<label class="Validform_label" style="display: none;">类型编码</label>
						</td>
				</tr> -->
				<c:if test="${parent.id =='-5' }">
				<tr>
					<td align="right">
						<label class="Validform_label">
							节日类型:
						</label>
					</td>
					<td class="value">
					     	 <select id="festivalType" name="festivalType" datatype="*">
								 <option value="">-请选择-</option>
								 <option value="1">阳历</option>
								 <option value="2">农历</option>
							</select>
							<span class="Validform_checktip">农历08-15表示农历八月十五</span>
							<label class="Validform_label" style="display: none;">节日类型</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							节日日期:
						</label>
					</td>
					<td class="value">
					     	 <input id="monthDay" name="monthDay" type="text" datatype="*" style="width: 120px" readonly="readonly" class="Wdate" onClick="WdatePicker({dateFmt:'MM-dd'})">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">节日日期</label>
					</td>
				</tr>
				</c:if>
				<%-- <tr class="festival">
					<td align="right">
						<label class="Validform_label">
							是否图片话术:
						</label>
					</td>
					<td class="value">
					     	<t:dictSelect field="hasPics" type="radio" typeGroupCode="sf_yn" defaultVal="N" hasLabel="false"  title=""></t:dictSelect>
<!-- 					     	<input type="radio" name="hasPics" value="Y" >是 -->
<!-- 					     	<input type="radio" name="hasPics" value="N" checked="checked">否 -->
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否有图片</label>
					</td>
				</tr> --%>
			</table>
		</t:formvalid>
 </body>
