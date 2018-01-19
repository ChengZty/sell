<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="system_function_functionList" class="easyui-layout" fit="true"><%--   update-end--Author:duanql  Date:20130619 for：操作按钮窗口显示控制--%>
<div region="center" style="padding:0px;border:0px">
    <t:datagrid name="functionList" title="菜单管理"
                actionUrl="functionController.do?functionGrid&flag=01" idField="id" treegrid="true" pagination="false">
        <t:dgCol title="编号" field="id" treefield="id" hidden="true"></t:dgCol>
        <t:dgCol title="菜单名称" field="functionName" treefield="text"></t:dgCol>
        <t:dgCol title="图标" field="TSIcon_iconPath" treefield="code" image="true"></t:dgCol>
        <t:dgCol title="菜单类型" field="functionType" treefield="functionType" replace="funcType.page_0,funcType.from_1"></t:dgCol>
        <t:dgCol title="菜单地址" field="functionUrl" treefield="src"></t:dgCol>
        <t:dgCol title="菜单顺序" field="functionOrder" treefield="order"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
        <t:dgDelOpt url="functionController.do?del&id={id}" title="删除"></t:dgDelOpt>
        <t:dgFunOpt funname="operationDetail(id)" title="页面控件权限"></t:dgFunOpt>
<%--         <t:dgFunOpt funname="operationData(id)" title="数据规则"></t:dgFunOpt> --%>
        <t:dgToolBar title="录入" langArg="菜单" icon="icon-add" url="functionController.do?addorupdate&flag=01" funname="addFun"></t:dgToolBar>
        <t:dgToolBar title="编辑" langArg="菜单" icon="icon-edit" url="functionController.do?addorupdate&flag=01" funname="update"></t:dgToolBar>
    </t:datagrid>
</div>
</div>
<div data-options="region:'east',
	title:'精细化权限控制',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 400px; overflow: hidden;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="operationDetailpanel"></div>
</div>
</div>

<script type="text/javascript">
$(function() {
	var li_east = 0;
});
//数据规则权数
function  operationData(fucntionId){
	if(li_east == 0){
	   $('#system_function_functionList').layout('expand','east'); 
	}
	$('#operationDetailpanel').panel("refresh", "functionController.do?dataRule&functionId=" +fucntionId);
}
function operationDetail(functionId)
{
	if(li_east == 0){
	   $('#system_function_functionList').layout('expand','east'); 
	}
	$('#operationDetailpanel').panel("refresh", "functionController.do?operation&functionId=" +functionId);
}
function addFun(title,url, id) {
	var rowData = $('#'+id).datagrid('getSelected');
	if (rowData) {
		url += '&TSFunction.id='+rowData.id;
	}
	add(title,url,'functionList',700,450);
}
</script>

