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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="cmsCategory.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tContentCategoryPage.id }">
		<input  name="TContentCategoryEntity.id" type="hidden" style="width: 150px" class="inputxt"  value="${ tContentCategoryPage.TContentCategoryEntity.id}">
		<input  name="TContentCategoryEntity.isParent" type="hidden" style="width: 150px" class="inputxt"  value="${ tContentCategoryPage.TContentCategoryEntity.isParent}">
		 <input id="status" name="status" type="hidden" style="width: 150px" class="inputxt"  value="A">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
							<label class="Validform_label">
								分类名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"   value='${tContentCategoryPage.name}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分类名称</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							显示序号:
						</label>
					</td>
					<td class="value">
					     	 <input id="sortOrder" name="sortOrder" type="text" style="width: 150px" class="inputxt">
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
							<label class="Validform_label">
								父分类名称:
							</label>
						</td>
						<td class="value">
								${tContentCategoryPage.TContentCategoryEntity.name}
						     	 <input  name="TContentCategoryEntity.name" type="hidden" style="width: 150px" class="inputxt"   value="${tContentCategoryPage.TContentCategoryEntity.name}"  readonly="readonly">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">父分类名称</label>
						</td>
				</tr>	
			</table>
		</t:formvalid>
 </body>