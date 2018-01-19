<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div id="commUserList" class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="userList" title="零售商列表" actionUrl="userController.do?storeDatagrid" 
		    fitColumns="false" idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
			<t:dgCol title="ID" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="用户名" sortable="false" field="userName" query="true" width="100"></t:dgCol>
			<t:dgCol title="真实姓名" field="realName" query="true" width="70"></t:dgCol>
<%-- 			<t:dgCol title="手机号" field="mobilePhone" query="true" width="70"></t:dgCol> --%>
			<t:dgCol title="所属区域" field="area" query="true" width="150"></t:dgCol>
			<t:dgCol title="零售商类型" field="retailerType" replace="零售商_1,云商_2" query="true"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="120"></t:dgCol>
			<t:dgFunOpt funname="addLayout(id,realName)" title="佣金规则设置"></t:dgFunOpt>
		</t:datagrid>
	</div>
</div>
	<div region="east" style="width: 700px" split="true">
		<div tools="#tt" class="easyui-panel" title='佣金规则设置' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
	</div>
<!-- 
	<div region="south" style="height: 300px" split="true">
		<div tools="#tt" class="easyui-panel" title='云商佣金规则设置' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
	</div>
 -->
<script type="text/javascript">

function addLayout(id,realName) {
	$("#function-panel").panel(
		{
			title : realName+' 佣金规则设置' ,
			href:"tCommissionCloudController.do?cloudRuleList&store_Id=" + id
		}
	);
// 	$('#function-panel').panel("refresh" );
}
</script>
