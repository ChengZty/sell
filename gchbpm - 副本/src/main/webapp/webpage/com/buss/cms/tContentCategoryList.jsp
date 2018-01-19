<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="system_territory_cmsCategory" class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
		<t:datagrid name="cmsCategory" title="内容管理" actionUrl="cmsCategory.do?cmsGrid" idField="id" treegrid="true" pagination="false" openFirstNode="true">
			<t:dgCol title="内容ID" field="id" treefield="id" hidden="true"></t:dgCol>
			<t:dgCol title="分类名称" field="name" treefield="text"></t:dgCol>
			<t:dgCol title="分类编码" field="code" treefield="src"></t:dgCol>
			<t:dgCol title="内容分类" field="categoryType" treefield="remark" dictionary="category_type"></t:dgCol>
			<t:dgCol title="显示顺序" field="sortOrder" treefield="order"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgDelOpt url="cmsCategory.do?del&id={id}" title="删除"  exp="id#ne#1"></t:dgDelOpt>
			<t:dgToolBar title="内容录入"   icon="icon-add" url="cmsCategory.do?goAdd" funname="addFun"></t:dgToolBar>
			<t:dgToolBar title="内容编辑"   icon="icon-edit" url="cmsCategory.do?goUpdate" funname="updateFun"></t:dgToolBar>
		</t:datagrid>
</div>
</div>

<script type="text/javascript">
$(function() {
	var li_east = 0;
});
function addFun(title,url, id) {
	var rowData = $('#'+id).datagrid('getSelected');
	if (rowData) {
		url += '&pid='+rowData.id;
	}
	add(title,url,'cmsCategory');
}

function updateFun(title,url, id) {
	var rowData = $('#'+id).datagrid('getSelected');
	if (rowData) {
		 if(rowData.id==1){
			tip("顶级目录不能修改");
			return  false;
		}else{
			url += '&id='+rowData.id;
			add(title,url,'cmsCategory');
		}
	}else{
		tip("请选择二级目录及以下目录编辑");
		return  false;
	}
}
</script>

