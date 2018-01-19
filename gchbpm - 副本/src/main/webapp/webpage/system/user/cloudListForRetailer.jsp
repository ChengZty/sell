<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script>
</script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="cloudList" title="云商列表" actionUrl="userController.do?retaileRelationDatagrid&retailer_Type=2&rId=${rId }" fitColumns="false" extendParams="nowrap:false,"
   idField="id" queryMode="group" sortName="createDate" sortOrder="desc" checkbox="true">
	<t:dgCol title="ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true" width="100"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true" width="100"></t:dgCol>
	<t:dgCol title="所属区域" field="area" width="200"></t:dgCol>
	<t:dgCol title="省" field="provinceId" hidden="true"></t:dgCol>
	<t:dgCol title="馆" field="cityId" hidden="true"></t:dgCol>
</t:datagrid>
</div>
