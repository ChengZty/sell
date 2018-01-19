<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>互斥规则</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script src = "webpage/com/buss/goods/tExcludeRule.js"></script>	
  <script type="text/javascript">
  //编写自定义JS代码
 
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" btnsub="btn" layout="table" action="tExcludeRuleController.do?doAdd" tiptype="4" callback="goback">
					<input id="id" name="id" type="hidden" value="${tExcludeRulePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tExcludeRulePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tExcludeRulePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tExcludeRulePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tExcludeRulePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tExcludeRulePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tExcludeRulePage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="topCategoryId" name="topCategoryId" type="hidden" value="${tExcludeRulePage.topCategoryId }">
					<input id="subCategoryId" name="subCategoryId" type="hidden" value="${tExcludeRulePage.subCategoryId }">
					<input id="thridCategoryId" name="thridCategoryId" type="hidden" value="${tExcludeRulePage.thridCategoryId }">
					<input id="brandId" name="brandId" type="hidden" value="${tExcludeRulePage.brandId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tExcludeRulePage.retailerId }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							分类:
						</label>
					</td>
					<td class="value">
					     	 一级：<input id="topCategoryName" name="topCategoryName" type="text" style="width: 80px"  value="${tExcludeRulePage.topCategoryName}" >
					     	 二级：<input id="subCategoryName" name="subCategoryName" type="text" style="width: 80px"  value="${tExcludeRulePage.subCategoryName}" >
					     	 三级：<input id="thridCategoryName" name="thridCategoryName" type="text" style="width: 80px"  value="${tExcludeRulePage.thridCategoryName}" >
					     	 <a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findCategorys()" id=""><span class="icon-search l-btn-icon-left">选择</span></a>
					</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							品牌名称:
						</label>
					</td>
					<td class="value">
					<div>
					<textarea style="width: 95%;height: 50px;" name="brandName" id="brandName" readonly="readonly">${tExcludeRulePage.brandName}</textarea>
					</div>
					<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="chooseBrands()">
					<span class="l-btn-text icon-search l-btn-icon-left">选择</span></a>
					
<%-- 						<t:choose hiddenName="brandId" hiddenid="id" url="tExcludeRuleController.do?findBrands" name="brandList" height="550px" width="500px" --%>
<%--                           icon="icon-search" title="品牌列表" textname="brandName" isclear="false" isInit="true"></t:choose> --%>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" >可选多个品牌</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							零售商:
						</label>
					</td>
					<td class="value">
					<div>
						<input name="retailerName" type="text"  value="${tExcludeRulePage.retailerName }" id="realName" readonly="readonly" datatype="*"/>
						<t:choose hiddenName="retailerId" hiddenid="id" url="tExcludeRuleController.do?findRetailers" name="retailerList" height="550px" width="500px"
                          icon="icon-search" title="零售商列表" textname="realName" isclear="false" isInit="true"></t:choose>
					</div>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none">零售商</label>
						</td>
					</tr>
			</table>
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
			<a href="#" class="easyui-linkbutton" id="btn" iconCls="icon-save" >提交</a> 
			<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a>
		</div>
		</t:formvalid>
 </body>
  	