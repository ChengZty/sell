<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>零售商品牌分类选择</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tRetailerBrandCategoryController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tRetailerBrandCategoryPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tRetailerBrandCategoryPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tRetailerBrandCategoryPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tRetailerBrandCategoryPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tRetailerBrandCategoryPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tRetailerBrandCategoryPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tRetailerBrandCategoryPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="brandId" name="brandId" type="hidden" >
					<input id="retailerId" name="retailerId" type="hidden" value="${tRetailerBrandCategoryPage.retailerId }">
					<input id="otherRetailerId" name="otherRetailerId" type="hidden" value="${tRetailerBrandCategoryPage.otherRetailerId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
							<label class="Validform_label" width="100">
								品牌名称:
							</label>
					</td>
					<td class="value" colspan="3">
						<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" >
						<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" ><span class="icon-search l-btn-icon-left">选择</span></a>
						<label class="Validform_label" style="display: none;">品牌名称</label>
					</td>
				</tr>
				<tr>
					<td align="right" width="100px">
						<label class="Validform_label">
							一级/二级/三级分类:
						</label>
					</td>
					<td class="value" colspan="3">
					     	<select name="topCategoryId" id="topCategoryId" onchange="getUnderCategories('topCategoryId',this.value,'2','subCategoryId')" >
								<option value="">---请选择---</option>
								<c:forEach var="category" items="${categoryList}">
								<option value="${category[0] }">${category[1]}</option>
								</c:forEach>
							</select>
							<select name="subCategoryId"  id="subCategoryId" onchange="getUnderCategories('subCategoryId',this.value,'3','thridCategoryId')" style="display: none;">
							</select>
							<select name="thridCategoryId"  id="thridCategoryId" onchange="fillThirdCatName('thridCategoryId',this.value)" style="display: none;">
							</select>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/visibleGoods/tRetailerBrandCategory.js?v=1.02"></script>		