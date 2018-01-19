<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tJobModelList" checkbox="true" fitColumns="false" title="任务组维护" actionUrl="tJobModelController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="任务组名称"  field="name"    queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="店铺名称"  field="storeName"    queryMode="group"  width="320"></t:dgCol>
   <t:dgCol title="有效时间"  field="time"    queryMode="group"  width="150"></t:dgCol>
   
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tJobModelController.do?doDel&id={id}" />
   
   <t:dgToolBar title="录入" icon="icon-add" url="" onclick="addJobModel()" funname="add"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-edit" url="" onclick="updateJobModel()" funname="update"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 function addJobModel() {
		document.location="tJobModelController.do?goAdd";
}
 function updateJobModel() {
	 var rowsData = $("#tJobModelList").datagrid("getSelections");
		if(rowsData==''){
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		var id=rowsData[0].id;
		document.location="tJobModelController.do?goUpdate&id="+ id;
}
 </script>