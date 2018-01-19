<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="roleList" title="角色列表" actionUrl="roleController.do?roleGrid" fitColumns="false" extendParams="nowrap:false,"
    idField="id" sortName="createDate" sortOrder="desc">
	<t:dgCol title="编码" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="角色编码" field="roleCode" width="120" ></t:dgCol>
	<t:dgCol title="角色名称" field="roleName" width="100"  query="true"></t:dgCol>
	<t:dgCol title="创建人" field="createBy" width="100"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="180" ></t:dgCol>
	<t:dgFunOpt funname="delRole(id)" title="删除"></t:dgFunOpt>
	<t:dgFunOpt funname="userListbyrole(id,roleName)" title="用户"></t:dgFunOpt>
	<t:dgFunOpt funname="setfunbyrole(id,roleName)" title="权限设置"></t:dgFunOpt>
	<t:dgToolBar title="录入" langArg="common.role" icon="icon-add" url="roleController.do?addorupdate" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" langArg="common.role" icon="icon-edit" url="roleController.do?addorupdate" funname="update"></t:dgToolBar>
</t:datagrid></div>
</div>
<div region="east" style="width: 500px;" split="true">
<div tools="#tt" class="easyui-panel" title='权限设置' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>

<script type="text/javascript">
function setfunbyrole(id,roleName) {
	$("#function-panel").panel(
		{
			title :roleName+ ':' + '当前权限',
			href:"roleController.do?fun&roleId=" + id
		}
	);
	$('#function-panel').panel("refresh" );
	
}
function userListbyrole(id,roleName) {
	$("#function-panel").panel(
		{
			title :roleName+ ':' + '用户',
			href:"roleController.do?userList&roleId=" + id
		}
	);
	$('#function-panel').panel("refresh" );
	
}
//删除角色
function delRole(id){
	var tabName= 'roleList';
	var url= 'roleController.do?delRole&id='+id;
	$.dialog.confirm('确定删除该记录吗', function(){
		doSubmit(url,tabName);
		rowid = '';
		$("#function-panel").html("");//删除角色后，清空对应的权限
	}, function(){
	});
}
</script>
