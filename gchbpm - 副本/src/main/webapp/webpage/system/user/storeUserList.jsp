<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body >
		<t:datagrid name="userList" title="零售商列表" actionUrl="userController.do?storeAllDatagrid" 
		    fit="true" fitColumns="true" idField="id" queryMode="group" >
			<t:dgCol title="ID" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="零售商姓名" sortable="false" field="userName" query="true"></t:dgCol>
			<t:dgCol title="零售商编码" sortable="false" field="userCode" query="true"></t:dgCol>
			<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
			<t:dgCol title="手机号" field="mobilePhone" query="true"></t:dgCol>
			<t:dgCol title="所属区域" field="area" query="true"></t:dgCol>
			<t:dgCol title="零售商类型" field="retailerType" query="true" dictionary="t_r"></t:dgCol>
		</t:datagrid>
</body>
</html>