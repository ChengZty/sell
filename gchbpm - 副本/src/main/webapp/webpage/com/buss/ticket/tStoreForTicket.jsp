<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>店铺列表</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:datagrid   name="storeList" title="店铺列表"  actionUrl="tStoreController.do?datagrid" idField="id" checkbox="true" showRefresh="false"  fit="true" onClick="getID"  queryMode="group" onLoadSuccess="initCheck">
	<t:dgCol title="店铺ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="名称"  field="name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   	<t:dgCol title="地址"  field="address"    queryMode="single"  width="320"></t:dgCol>
</t:datagrid>
<input id="checkedIds" type="hidden" value="${ids}"/>
</body>
</html>
<script type="text/javascript">
function initCheck(data){
	var ids = "${ids}";
	var idArr = ids.split(",");
	for(var i=0;i<idArr.length;i++){
		if(idArr[i]!=""){
			$("#storeList").datagrid("selectRecord",idArr[i]);
		}
	}
}

</script>