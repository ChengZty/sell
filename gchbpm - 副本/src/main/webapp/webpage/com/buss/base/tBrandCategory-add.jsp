<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>添加品牌分类</title>
<t:base type="jquery,easyui,tools,layer"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" layout="table" callback="backList" action="baseBrandController.do?doAddType" tiptype="5">
		<input id="status" name="status" type="hidden" value="A">
		<input id="brandId" name="brandId" type="hidden" value="${brandId }">
		<table style="width: 100%; height: 60%, margin:atuo" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td colspan="2" style="height: 40px">
					<h3>品牌分类</h3>${catList}</td>
			</tr>
			<tr>
				<td align="right" width="120px;"><label class="Validform_label">
						一级分类: </label></td>
				<td class="value"><input id="topCategoryName"
					name="topCategoryName" type="hidden" style="width: 150px"
					class="inputxt"> <select id="topCategoryId"
					name="topCategoryId"
					onchange="setCatName(this,'topCategoryName'),getSubList(this.value,'subCategoryId')"
					datatype="*" errormsg="请选择一级分类!" WIDTH="150px"
					style="width: 150px;">
						<option value="">-请选择-</option>
						<c:forEach var="obj" items="${catList}">
							<option value="${obj.id}">${obj.name}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 二级分类:
				</label></td>
				<td class="value"><input id="subCategoryName"
					name="subCategoryName" type="hidden" style="width: 150px"
					class="inputxt"> <select id="subCategoryId"
					name="subCategoryId" datatype="*" errormsg="请选择二级分类!"
					style="width: 150px;">
						<option value="">-请选择-</option>
				</select> <span class="Validform_checktip"></span></td>
			</tr>
		</table>
		<br />
	</t:formvalid>
</body>

<script type="text/javascript">
	$(function() {
		$.ajax({
			type : 'POST',
			url : 'categoryController.do?gettopDatagridList',
			dataType : 'json',
			success : function(data) {
				data = data.obj;

				$("#topCategoryId_div").empty().append(
						"<option value=''>-请选择-</option>");

				$("#subCategoryId").empty().append(
						"<option value=''>-请选择-</option>");
				$("#subCategoryId_div").empty();
				var htmlStr = "";
				var len = data.length;
				for ( var i = 0; i < len; i++) {
					htmlStr += "<option value='"+data[i].areaId+"'>"
							+ data[i].areaName + "</option>";
				}
				$("#topCategoryId").append(htmlStr);
			}
		});
	});

	function setCatName(obj, descId) {
		var txt = $(obj).find("option:selected").text()
		$("#" + descId).val(txt);
	}

	function getSubList(objId, descId) {
		if (objId != "") {
			$.ajax({
				type : 'POST',
				url : 'categoryController.do?getSubList',
				dataType : 'json',
				data : {
					'pid' : objId
				},
				success : function(data) {

					$("#subCategoryId").empty().append(
							"<option value=''>-请选择-</option>");

					var htmlStr = "";
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						htmlStr += "<option value='"+data[i].id+"'>"
								+ data[i].name + "</option>";
					}
					$("#subCategoryId").append(htmlStr);
				}
			});
		} else {
			if (descId == 'subCategoryId') {
				$("#subCategoryId").empty().append(
						"<option value=''>-请选择-</option>");
			}
			$("#thridCategoryId").empty().append(
					"<option value=''>-请选择-</option>");
			$("#thridCategoryId_div").empty();
		}
	}
</script>