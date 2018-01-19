<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="categoryList" title="分类管理" fitColumns="false"  actionUrl="categoryController.do?datagridList" idField="id" treegrid="true" pagination="false">
			<t:dgCol title="id" field="id" treefield="src" hidden="true"></t:dgCol>
			<t:dgCol title="类目名称" field="name" treefield="text" width="300"></t:dgCol>
			<t:dgCol title="图片地址" field="imgUrl"  treefield="imgUrl" width="100"></t:dgCol>
			<t:dgCol title="层级" field="level" treefield="order" hidden="true"></t:dgCol>
			<t:dgCol title="图标" field="TSIcon_iconPath" treefield="code" image="true" width="100"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt title="查看参数" exp="order#eq#3"  funname="addParams(src,text)"  />
		</t:datagrid>
	</div>
</div>
<div region="east" style="width: 500px;" split="true">
<div tools="#tt" class="easyui-panel" title='参数设置' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<script type="text/javascript">
	function addCategory(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&parent.code=' + rowData.id;
		}
		add(title, url, 'categoryList', 500, 400);
	}
	
	function updateCategory(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&code=' + rowData.id;
		}
		update(title, url, 'categoryList', 500, 400);
	}
	
	function addParams(categoryId,name) {
		$("#function-panel").panel(
			{
				title : '类目:' + name,
				href:"tAllGoodsParamsController.do?list&category_Id=" + categoryId
			}
		);
// 		$('#function-panel').panel("refresh" );
		
	}
</script>
