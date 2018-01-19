<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>顾客话术</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  $("#topCategoryId").val("${tGoodsWordsPage.topCategoryId }");
	  $("#subCategoryId").val("${tGoodsWordsPage.subCategoryId }");
	  $("#thridCategoryId").val("${tGoodsWordsPage.thridCategoryId }");
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGoodsWordsController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tGoodsWordsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tGoodsWordsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tGoodsWordsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tGoodsWordsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tGoodsWordsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tGoodsWordsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tGoodsWordsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tGoodsWordsPage.status }">
					<input id="platformType" name="platformType" type="hidden" value="${tGoodsWordsPage.platformType }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tGoodsWordsPage.retailerId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								一级分类:
							</label>
						</td>
						<td class="value">
							<input id="topCategoryName" name="topCategoryName" type="hidden" style="width: 150px" class="inputxt" 
							value="${tGoodsWordsPage.topCategoryName}" datatype="*">
					     	<select id="topCategoryId" name="topCategoryId" onchange="setCatName(this,'topCategoryName'),getSubList(this.value)" >
							 <option value="">-请选择-</option>
							 <c:forEach var="obj" items="${topCategoryList}" >
									<option value="${obj[0]}">${obj[1]}</option>					 	
				              </c:forEach>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">一级分类</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								二级分类:
							</label>
						</td>
						<td class="value">
							<input id="subCategoryName" name="subCategoryName" type="hidden" style="width: 150px" class="inputxt" 
							value="${tGoodsWordsPage.subCategoryName}" datatype="*">
						     <select id="subCategoryId" name="subCategoryId" onchange="setCatName(this,'subCategoryName'),getThirdList(this.value)">
								 <option value="">-请选择-</option>
								 <c:if test="${not empty subCategoryList }">
									 <c:forEach var="obj" items="${subCategoryList}" >
											<option value="${obj.id}">${obj.name}</option>					 	
						              </c:forEach>
								 </c:if>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">二级分类</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								三级分类:
							</label>
						</td>
						<td class="value">
							<input id="thridCategoryName" name="thridCategoryName" type="hidden" style="width: 150px" class="inputxt" value="${tGoodsWordsPage.thridCategoryName}">
						     <select id="thridCategoryId" name="thridCategoryId" onchange="setCatName(this,'thridCategoryName')">
								 <option value="">-请选择-</option>
								 <c:if test="${not empty thirdCategoryList }">
									 <c:forEach var="obj" items="${thirdCategoryList}" >
											<option value="${obj.id}">${obj.name}</option>					 	
						              </c:forEach>
								 </c:if>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">三级分类</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容:
							</label>
						</td>
						<td class="value">
							<textarea rows="5" style="width: 98%" name="content" datatype="*" maxlength="300">${tGoodsWordsPage.content}</textarea>
							<span class="Validform_checktip">最多输入300字</span>
							<label class="Validform_label" style="display: none;">内容</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/words/tGoodsWords.js"></script>		