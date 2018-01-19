<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="system_territory_territoryList" class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px"><t:datagrid name="territoryList" title="地域管理" actionUrl="territoryController.do?territoryGrid" idField="id" treegrid="true" pagination="false">
	<t:dgCol title="编号" field="id" treefield="id" hidden="true"></t:dgCol>
	<t:dgCol title="地域名称" field="territoryName" treefield="text"></t:dgCol>
	<t:dgCol title="地域编码" field="territorySrc" treefield="src"></t:dgCol>
	<t:dgCol title="显示顺序" field="territorySort" treefield="order"></t:dgCol>
	<t:dgCol title="是否显示" field="isShow" treefield="isShow" replace="是_Y,否_N"></t:dgCol>
	<t:dgCol title="备注" field="remark" treefield="remark" width="80"></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgDelOpt url="territoryController.do?del&id={id}" title="删除"></t:dgDelOpt>
	<t:dgToolBar title="录入" langArg="地域" icon="icon-add" url="territoryController.do?addorupdate" funname="addFun"></t:dgToolBar>
	<t:dgToolBar title="编辑" langArg="地域" icon="icon-edit" url="territoryController.do?addorupdate" funname="update"></t:dgToolBar>
</t:datagrid></div>
</div>

<script type="text/javascript">
$(function() {
	var li_east = 0;
});
function addFun(title,url, id) {
	var rowData = $('#'+id).datagrid('getSelected');
	if (rowData) {
		url += '&TSTerritory.id='+rowData.id;
	}
	add(title,url,'territoryList');
}
</script>

