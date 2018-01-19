<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>内容管理</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="cmsCategory.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tContentCategoryPage.id }">
		<input  name="TContentCategoryEntity.id" type="hidden" style="width: 150px" class="inputxt"  value="${ tContentCategoryPage.TContentCategoryEntity.id}">
		<input  name="TContentCategoryEntity.isParent" type="hidden" style="width: 150px" class="inputxt"  value="${ tContentCategoryPage.TContentCategoryEntity.isParent}">
		<input  name="isParent" type="hidden" style="width: 150px" class="inputxt"  value="${ tContentCategoryPage.isParent}">
		<input id="createName" name="createName" type="hidden" value="${ tContentCategoryPage.createName }">
		<input id="createBy" name="createBy" type="hidden" value="${ tContentCategoryPage.createBy }">
		<input id="createDate" name="createDate" type="hidden" value="${ tContentCategoryPage.createDate }">
		<input id="updateName" name="updateName" type="hidden" value="${ tContentCategoryPage.updateName }">
		<input id="updateBy" name="updateBy" type="hidden" value="${ tContentCategoryPage.updateBy }">
		<input id="updateDate" name="updateDate" type="hidden" value="${ tContentCategoryPage.updateDate }">
		<input id="code" name="code" type="hidden" value="${ tContentCategoryPage.code }">
		<input id="status" name="status" type="hidden" style="width: 150px" class="inputxt"    value='${tContentCategoryPage.status}'>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								分类名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"    value='${tContentCategoryPage.name}'  maxlength="50">  
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分类名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								显示序号:
							</label>
						</td>
						<td class="value">
						     	 <input id="sortOrder" name="sortOrder" type="text" style="width: 150px" class="inputxt"  datatype="n" value='${tContentCategoryPage.sortOrder}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">显示序号</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							内容类别:
						</label>
					</td>
					<td class="value">
							<t:dictSelect field="categoryType"   datatype="*"  typeGroupCode="category_type" defaultVal="${tContentCategoryPage.categoryType}" hasLabel="false"  title="内容类别"></t:dictSelect>   		 
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">内容类别</label>
					 </td>
					<td align="right">
							
						</td>
						<td class="value">
								
						</td>
				</tr>	
			</table>
		</t:formvalid>
 </body>