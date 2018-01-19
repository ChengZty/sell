<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tProfessionalList" checkbox="true" fitColumns="true" title="职称" actionUrl="tProfessionalController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="一级类目ID"  field="topCategoryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="职称编码"  field="professionalCode"    queryMode="single" dictionary="bpm_status" width="120"></t:dgCol>
<%--    <t:dgCol title="职称名字"  field="professionalName"  query="true"  queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="类目"  field="topCategoryName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帮手类别"  field="helperType"  query="true" dictionary="hp_type"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="职称名称"  field="professionalName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgToolBar title="录入" icon="icon-add" url="tProfessionalController.do?goAdd" funname="add" height="200" width="400"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tProfessionalController.do?goUpdate" funname="update" height="200" width="400"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tProfessionalController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tProfessionalController.do?goUpdate" funname="detail" height="200" width="400"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/user/tProfessionalList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tProfessionalListtb").find("input[name='createDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tProfessionalListtb").find("input[name='updateDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 </script>