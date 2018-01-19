<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="categoryList" title="分类管理"  actionUrl="categoryController.do?datagrid" idField="id" treegrid="true" pagination="false" fitColumns="false">
			<t:dgCol title="分类名称" field="name" treefield="text" width="300"></t:dgCol>
			<t:dgCol title="id" field="id" hidden="true" treefield="id"  width="60"></t:dgCol>
			<t:dgCol title="code" field="code" hidden="true" treefield="src"></t:dgCol>
			<t:dgCol title="图片" field="imgUrl" treefield="code" image="true" imageSize="80,40" width="80"></t:dgCol>
			<t:dgCol title="分类等级" field="level"  treefield="order"  width="60"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt title="编辑" funname="updateCategory(id)" ></t:dgFunOpt>
			<t:dgDelOpt title="删除" url="categoryController.do?del&id={id}" />
			<t:dgToolBar icon="icon-add" title="录入" url="categoryController.do?addorupdate" funname="addCategory" ></t:dgToolBar>
<%-- 			<t:dgToolBar icon="icon-edit" title="编辑" url="categoryController.do?addorupdate" funname="updateCategory" ></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	function addCategory(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&parent.id=' + rowData.id;
			add(title, url, 'categoryList', 670, 550);
		}else{
			tip("请选择分类");
		}
	}
	
	function updateCategory(id) {
		var title = "编辑";
		var url = "categoryController.do?addorupdate&id="+id;
		gridname="categoryList";
		createwindow(title,url,670, 550);
	}
</script>
