<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>common.role.list</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:datagrid name="roleList" title="角色选择" actionUrl="userController.do?datagridRole" idField="id"  showRefresh="false" >
	<t:dgCol title="编码" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="角色名称" field="roleName" width="50"></t:dgCol>
</t:datagrid>
</body>
</html>
<script type="text/javascript">
function getRole(){
		var rowsData = $("#roleList").datagrid("getChecked");
		var id = "";
		if(rowsData.length==0){
			tip('<t:mutiLang langKey="please.select.role"/>');
			return "";
		}
		return rowsData[0].id;
	}
</script>
