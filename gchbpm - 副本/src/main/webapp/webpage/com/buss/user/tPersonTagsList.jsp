<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tPersonTagsList" checkbox="true" fitColumns="false" title="导购标签" actionUrl="tPersonTagsController.do?datagrid&user_Id=${user_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类目"  field="topCategoryName"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="标签"  field="tagCode"    queryMode="single" dictionary="hp_type" width="80"></t:dgCol>
   <t:dgCol title="职称"  field="professionalName"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购用户ID"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tPersonTagsController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tPersonTagsController.do?goAdd&user_id=${user_Id }" funname="add" width="680" height="320"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tPersonTagsController.do?goUpdate" funname="update" width="680" height="320"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tPersonTagsController.do?goUpdate" funname="detail" width="680" height="320"></t:dgToolBar>
  </t:datagrid>
 <script src = "webpage/com/buss/user/tPersonTagsList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tPersonTagsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tPersonTagsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 </script>