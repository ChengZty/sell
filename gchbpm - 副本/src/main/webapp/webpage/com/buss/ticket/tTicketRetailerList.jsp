<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="userList" title="零售商列表" actionUrl="userController.do?storeDatagrid&retailer_Type=1" fitColumns="false" extendParams="nowrap:false,"
   idField="id" queryMode="group" sortName="createDate" sortOrder="desc" checkbox="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true" width="100"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true" width="100"></t:dgCol>
	<t:dgCol title="所属区域" field="area" width="200"></t:dgCol>
	<t:dgCol title="省" field="provinceId" hidden="true"></t:dgCol>
	<t:dgCol title="馆" field="cityId" hidden="true"></t:dgCol>
</t:datagrid>
</div>
</div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#userListtb").prepend("<div style='padding-left:20px;margin-bottom:10px;'><div>"
 			+"<input type='hidden' name='ticketId' value='${ticketId}'/><input type='hidden' name='sheetRemain' value='${sheetRemain}'/></div>"
 			+"<div>每个零售商分配张数：<input type='text' name='sheetNum' id='sheetNum' style='width:50px;'/></div></div>");
 			$("#sheetNum").focus();
 });
 
 </script>
