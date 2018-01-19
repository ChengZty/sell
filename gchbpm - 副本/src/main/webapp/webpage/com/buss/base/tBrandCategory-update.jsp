<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>品牌分类</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBrandCategoryController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tBrandCategoryPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tBrandCategoryPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBrandCategoryPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBrandCategoryPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBrandCategoryPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBrandCategoryPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBrandCategoryPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tBrandCategoryPage.status }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								品牌ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="brandId" name="brandId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tBrandCategoryPage.brandId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">品牌ID</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								一级分类ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="topCategoryId" name="topCategoryId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tBrandCategoryPage.topCategoryId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">一级分类ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								二级分类ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="subCategoryId" name="subCategoryId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tBrandCategoryPage.subCategoryId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">二级分类ID</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
