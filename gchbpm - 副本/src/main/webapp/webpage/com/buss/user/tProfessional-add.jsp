<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>职称</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tProfessionalController.do?doAdd" beforeSubmit="setTopCategoryName()" tiptype="4" >
					<input id="id" name="id" type="hidden" value="${tProfessionalPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tProfessionalPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tProfessionalPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tProfessionalPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tProfessionalPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tProfessionalPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tProfessionalPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="topCategoryName" name="topCategoryName" type="hidden" value="${tProfessionalPage.topCategoryName }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100px">
						<label class="Validform_label">
							类目:
						</label>
					</td>
					<td class="value">
							  <select name="topCategoryId" id="topCategoryId" datatype="*">
									<c:forEach var="category" items="${categoryList}">
									<option value="${category[0] }">${category[1]}</option>
									</c:forEach>
								</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类目</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							帮手类别:
						</label>
					</td>
					<td class="value">
					<t:dictSelect field="helperType" typeGroupCode="hp_type" hasLabel="false" type="list" defaultVal="${tProfessionalPage.helperType }" datatype="*"></t:dictSelect>
<!-- 					     	 <input id="helperType" name="helperType" type="text" style="width: 150px" class="inputxt" > -->
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">帮手类别</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							职称编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="professionalCode" name="professionalCode" validType="t_professional,professional_code" datatype="s2-10" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">职称编码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							职称名字:
						</label>
					</td>
					<td class="value">
					     	 <input id="professionalName" name="professionalName" type="text" style="width: 150px" class="inputxt" datatype="s2-30">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">职称名字</label>
						</td>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/user/tProfessional.js"></script>		
