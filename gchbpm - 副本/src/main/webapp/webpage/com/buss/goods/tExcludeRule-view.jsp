<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>互斥规则</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tExcludeRuleController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tExcludeRulePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tExcludeRulePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tExcludeRulePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tExcludeRulePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tExcludeRulePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tExcludeRulePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tExcludeRulePage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tExcludeRulePage.status }">
					<input id="topCategoryId" name="topCategoryId" type="hidden" value="${tExcludeRulePage.topCategoryId }">
					<input id="subCategoryId" name="subCategoryId" type="hidden" value="${tExcludeRulePage.subCategoryId }">
					<input id="thridCategoryId" name="thridCategoryId" type="hidden" value="${tExcludeRulePage.thridCategoryId }">
					<input id="brandId" name="brandId" type="hidden" value="${tExcludeRulePage.brandId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tExcludeRulePage.retailerId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								一级分类名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="topCategoryName" name="topCategoryName" type="text" style="width: 150px"   value='${tExcludeRulePage.topCategoryName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">一级分类名称</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								二级分类名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="subCategoryName" name="subCategoryName" type="text" style="width: 150px"   value='${tExcludeRulePage.subCategoryName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">二级分类名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								三级分类名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="thridCategoryName" name="thridCategoryName" type="text" style="width: 150px"   value='${tExcludeRulePage.thridCategoryName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">三级分类名称</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								品牌名称:
							</label>
						</td>
						<td class="value">
						     	<textarea style="width: 95%;height: 50px;" name="brandName" id="brandName" readonly="readonly">${tExcludeRulePage.brandName}</textarea>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/goods/tExcludeRule.js"></script>		