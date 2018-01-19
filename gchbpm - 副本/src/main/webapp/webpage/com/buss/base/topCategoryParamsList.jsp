<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="categoryList" title="产品参数信息" fitColumns="false"  actionUrl="categoryController.do?subCategoryList" idField="id" treegrid="true"  pagination="false">
			<t:dgCol title="id" field="id" treefield="id" hidden="true"></t:dgCol>
			<t:dgCol title="类目名称" field="name" treefield="text" width="300"></t:dgCol>
<%-- 			<t:dgCol title="children" field="children" treefield="children"></t:dgCol> --%>
			<t:dgCol title="类目级别" field="iconCls" treefield="iconCls"></t:dgCol>
<%-- 			<t:dgCol title="state" field="state" treefield="state"></t:dgCol> --%>
<%-- 			<t:dgCol title="checked" field="checked" treefield="checked"></t:dgCol> --%>
			<t:dgCol title="操作" field="opt" width="120"></t:dgCol>
			<t:dgFunOpt title="查看产品参数信息"   funname="addParams(id,text)" exp="iconCls#eq#2" />
		</t:datagrid>
	</div>
</div>
<div region="east" style="width: 1000px;" split="true">
<div tools="#tt" class="easyui-panel" title='产品参数信息设置' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<script type="text/javascript">
	function addParams(topCategoryId,name) {
		$("#function-panel").panel(
			{
				title : '类目:' + name,
				href:"tProductParamsController.do?list&category_Id=" + topCategoryId
			}
		);
// 		$('#function-panel').panel("refresh" );
		
	}
</script>
