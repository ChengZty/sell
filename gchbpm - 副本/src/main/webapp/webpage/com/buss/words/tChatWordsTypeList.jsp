<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tChatWordsTypeList" checkbox="true" fitColumns="false" title="撩客话术类别" 
  actionUrl="tChatWordsTypeController.do?datagrid" idField="id" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true" treefield="id"  width="120"></t:dgCol>
   <t:dgCol title="类型名称"  field="name" treefield="text"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tChatWordsTypeController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tChatWordsTypeController.do?goAdd" funname="add" height="200" width="400"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tChatWordsTypeController.do?goUpdate" funname="update" height="200" width="400"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tChatWordsTypeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tChatWordsTypeController.do?goUpdate" funname="detail" height="200" width="400"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 
 </script>