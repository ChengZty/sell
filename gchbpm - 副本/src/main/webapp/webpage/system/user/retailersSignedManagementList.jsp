<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script>
</script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="userList" title="零售商列表" checkbox="true" actionUrl="userController.do?storeDatagrid" fitColumns="false" extendParams="nowrap:false,"
   idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
	<t:dgCol title="手机号" field="mobilePhone" query="true"></t:dgCol>
	<t:dgCol title="所属区域" field="area" ></t:dgCol>
	<t:dgCol title="合约开始时间" field="activeTime" formatter="yyyy-MM-dd" ></t:dgCol>
	<t:dgToolBar title="批量编辑零售商合约时间" langArg="common.user" icon="icon-edit" url="userController.do?goBatchUpdateActiveTime" 
	funname="updateActiveTime" width="450" height="100"></t:dgToolBar>
</t:datagrid>
</div>
</div>
<script>

//批量更新零售商合约开始时间及对应零售商的导购等帐号的激活时间
function updateActiveTime(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑记录');
		return;
	}
	var ids = [];
	for ( var i = 0; i < rowsData.length; i++) {
		ids.push(rowsData[i].id);
	}
	console.log(ids.push);
	url += '&ids='+ids.join(',');
	createwindow(title,url,width,height);
}
</script>