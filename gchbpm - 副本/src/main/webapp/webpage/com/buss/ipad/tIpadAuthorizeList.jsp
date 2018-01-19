<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
 <div class="easyui-layout" fit="true">
 	 <div region="center" style="padding:1px;">
 		<t:datagrid name="userList" title="零售商列表" actionUrl="userController.do?storeDatagrid" 
		    fit="true" fitColumns="true" idField="id" queryMode="group" >
			<t:dgCol title="ID" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="零售商姓名" sortable="false" field="userName" query="true"></t:dgCol>
			<t:dgCol title="零售商编码" sortable="false" field="userCode" query="true"></t:dgCol>
			<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
			<t:dgCol title="手机号" field="mobilePhone" query="true"></t:dgCol>
			<t:dgCol title="所属区域" field="area" query="true"></t:dgCol>
			<t:dgCol title="操作" field="opt"  ></t:dgCol>
			<t:dgFunOpt title="查看授权码"  funname="toGenCode(id,realName)"  />
		</t:datagrid>
	</div>
 </div>	
	 <div region="east" style="width: 700px;" split="true">
		<div tools="#tt" class="easyui-panel" title='授权码' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
	</div>
<script type="text/javascript">
<!--
function toGenCode(userId,realName) {
	$("#function-panel").panel({
		title: realName + '授权码',
		href : "tIpadAuthorizeController.do?toList&retailerId=" + userId
	});
// 	$('#function-panel').panel("refresh");	
}
//-->
</script>	
