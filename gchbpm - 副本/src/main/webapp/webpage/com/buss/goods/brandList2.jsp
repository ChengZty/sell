<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>品牌列表</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:datagrid   name="brandList" title="选择品牌"  actionUrl="baseBrandController.do?datagrid" idField="id" checkbox="false" showRefresh="false"  fit="true"  queryMode="group" >
	<t:dgCol title="品牌ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="品牌编码" field="brandCode" width="100" query="true" ></t:dgCol>
	<t:dgCol title="品牌名称" field="brandName" width="120" query="true" ></t:dgCol>
</t:datagrid>
</body>
</html>
