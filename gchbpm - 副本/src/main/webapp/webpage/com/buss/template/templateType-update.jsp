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
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tTemplateTypeController.do?doUpdate" tiptype="4">
		<input id="id" name="id" type="hidden" value="${templateType.id }">
		<input id="createName" name="createName" type="hidden" value="${templateType.createName }">
		<input id="createBy" name="createBy" type="hidden" value="${templateType.createBy }">
		<input id="createDate" name="createDate" type="hidden" value="${templateType.createDate }">
		<input id="updateName" name="updateName" type="hidden" value="${templateType.updateName }">
		<input id="updateBy" name="updateBy" type="hidden" value="${templateType.updateBy }">
		<input id="updateDate" name="updateDate" type="hidden" value="${templateType.updateDate }">
		<input id="status" name="status" type="hidden" value="${templateType.status }">
		<input id="templateType" name="templateType" type="hidden" value="${templateType.templateType }">
		<input id="code" name="code" type="hidden" value="${templateType.code }">
		<input id="level" name="level" type="hidden" value="${templateType.level }">
		<input id="parent" name="parent.id" type="hidden" value="${templateType.parent.id}">
		<input id="coverPic" name="coverPic" type="hidden" value="${templateType.coverPic}">
		<input id="smallPic" name="smallPic" type="hidden" value="${templateType.smallPic}">
		<input id="platformType" name="platformType" type="hidden" value="${templateType.platformType}">
		<input id="retailerId" name="retailerId" type="hidden" value="${templateType.retailerId}">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
						<tr>
							<td align="right" width="100">
								<label class="Validform_label">
									上级分类:
								</label>
							</td>
							<td class="value">${templateType.parent.name}
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
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							类型编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="code" name="code" type="text" style="width: 150px" class="inputxt" datatype="*" maxlength="10" value='${templateType.code}'>
							<span class="Validform_checktip">不超过10个字</span>
							<label class="Validform_label" style="display: none;">类型编码</label>
						</td>
				</tr> --%>
					<!-- 节假日非周末需要录入时间 -->
					<c:if test="${templateType.parent.id =='-5' && templateType.name !='周末'}">
					<tr class="festival">
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
					<tr class="festival">
						<td align="right">
							<label class="Validform_label">
								节日日期:
							</label>
						</td>
						<td class="value">
						     	 <input id="monthDay" name="monthDay" datatype="*" type="text" style="width: 120px" class="Wdate" readonly="readonly" 
						     	 onClick="WdatePicker({dateFmt:'MM-dd'})" value='${templateType.monthDay }'>
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
					     	 <t:dictSelect field="hasPics" type="radio"
									typeGroupCode="sf_yn" defaultVal="${templateType.hasPics }" hasLabel="false"  title=""></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否有图片</label>
					</td>
				</tr> --%>
			</table>
		</t:formvalid>
 </body>
