<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script>
</script>
<t:datagrid name="userList" title="操作" actionUrl="userController.do?datagridGuide" 
    fit="true" fitColumns="false" idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名"  field="userName"  width="120"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true" width="120"></t:dgCol>
	<t:dgCol title="手机号" field="mobilePhone" query="true" width="120"></t:dgCol>
</t:datagrid>
