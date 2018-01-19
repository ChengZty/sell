<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>行业类型集合</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:datagrid  pagination="false"  name="tradeList" title="行业类型选择"  actionUrl="userController.do?datagridTrade&rid=${rid}" idField="id" checkbox="true"  showRefresh="false"  fit="true"  queryMode="group" onLoadSuccess="initCheck">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="行业类型名称" field="tradeName" width="100" query="true" ></t:dgCol>
</t:datagrid>
</body>
</html>
<script type="text/javascript">
function initCheck(data){
	var ids = "${ids}";
	var idArr = ids.split(",");
	for(var i=0;i<idArr.length;i++){
		if(idArr[i]!=""){
			$("#tradeList").datagrid("selectRecord",idArr[i]);
		}
	}
}
</script>
