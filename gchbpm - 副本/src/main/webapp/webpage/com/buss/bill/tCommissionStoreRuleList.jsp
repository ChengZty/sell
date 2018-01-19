<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,layer"></t:base>
<div id="commUserList" class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="userList" title="结算管理" actionUrl="userController.do?storeDatagrid&retailer_Type=1" 
		    fit="false" fitColumns="true" idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
			<t:dgCol title="ID" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="商户姓名" sortable="false" field="userName" query="true"></t:dgCol>
			<t:dgCol title="商户编码" sortable="false" field="userCode" query="true"></t:dgCol>
			<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
			<t:dgCol title="手机号" field="mobilePhone" query="true"></t:dgCol>
			<t:dgCol title="所属区域" field="area" query="true"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
			<t:dgCol title="修改人" field="updateBy" hidden="true"></t:dgCol>
			<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt funname="addLayout(id,realName)" title="零售商佣金规则设置"></t:dgFunOpt>
		</t:datagrid>
	</div>
</div>
	<div region="south" style="height: 300px" split="true">
		<div tools="#tt" class="easyui-panel" title='零售商佣金规则设置' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
	</div>

<script type="text/javascript">
// function addLayout(objId){
// 	$("#formContenFrame").attr("src","tCommissionStoreRuleController.do?goAdd&sid=" +objId);
// }

function addLayout(id,realName) {
	$("#function-panel").panel(
		{
			title : realName+'佣金规则设置' ,
			href:"tCommissionStoreRuleController.do?goAdd&sid=" + id
		}
	);
// 	$('#function-panel').panel("refresh" );
}

function tipmsg(data) {
	  //$.messager.alert('提示信息', data.msg);
	  layer.alert(data.msg); 
}
</script>
