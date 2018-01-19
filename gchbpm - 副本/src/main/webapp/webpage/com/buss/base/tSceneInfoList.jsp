<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tSceneInfoList" checkbox="true" fitColumns="false" title="场景" actionUrl="tSceneInfoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="封面图"  field="coverPic"  image="true" imageSize="150,89.2" queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSceneInfoController.do?doDel&id={id}" />
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tSceneInfoController.do?goAdd" funname="add"></t:dgToolBar> --%>
   <t:dgToolBar title="录入" icon="icon-add" url="" funname="goAdd"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="" funname="goUpdate"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSceneInfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="" funname="goView"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tSceneInfoListtb").find("input[name='createDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tSceneInfoListtb").find("input[name='updateDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 function goAdd(){
	 		document.location="tSceneInfoController.do?goAdd";
}
 
 function goUpdate(){
	 	var rowsData = $("#tSceneInfoList").datagrid("getSelections");
	 		if(rowsData==''){
	 			tip('请选择一行记录');
	 			return;
	 		}
	 		if (rowsData.length>1) {
	 			tip('只能选择一条记录');
	 			return;
	 		}
	 		var id=rowsData[0].id;
	 		document.location="tSceneInfoController.do?goUpdate&id="+id;
	 }
 function goView(){
	 	var rowsData = $("#tSceneInfoList").datagrid("getSelections");
	 		if(rowsData==''){
	 			tip('请选择一行记录');
	 			return;
	 		}
	 		if (rowsData.length>1) {
	 			tip('只能选择一条记录');
	 			return;
	 		}
	 		var id=rowsData[0].id;
	 		document.location="tSceneInfoController.do?goView&id="+id;
	 }
 </script>