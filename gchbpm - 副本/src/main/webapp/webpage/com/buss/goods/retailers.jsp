<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>零售商列表</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:datagrid  pagination="false"  name="retailerList" title="选择零售商"  actionUrl="userController.do?retailerListDatagrid" idField="id" checkbox="false" showRefresh="false"  fit="true"  queryMode="group" onLoadSuccess="initCheck">
	<t:dgCol title="零售商ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" field="userName" width="100" query="true" ></t:dgCol>
<%-- 	<t:dgCol title="零售商编码" field="userCode" width="100" query="true" ></t:dgCol> --%>
	<t:dgCol title="零售商名称" field="realName" width="120" query="true" ></t:dgCol>
<%-- 	<t:dgCol title="角色" field="userKey" ></t:dgCol> --%>
</t:datagrid>
</body>
</html>
<script type="text/javascript">
function initCheck(data){
	var ids = "${ids}";
	var idArr = ids.split(",");
	for(var i=0;i<idArr.length;i++){
		if(idArr[i]!=""){
			$("#retailerList").datagrid("selectRecord",idArr[i]);
		}
	}
}
</script>