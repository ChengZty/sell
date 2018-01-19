<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
</script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="userList" title="零售商列表" actionUrl="userController.do?storeDatagrid&retailer_Type=1" fitColumns="false" extendParams="nowrap:false,"
   idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true" width="100"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true" width="100"></t:dgCol>
	<t:dgCol title="所属区域" field="area" width="200"></t:dgCol>
	<t:dgCol title="省" field="provinceId" hidden="true"></t:dgCol>
	<t:dgCol title="馆" field="cityId" hidden="true"></t:dgCol>
<%-- 	<t:dgCol title="零售商类型" field="retailerType" replace="零售商_1,云商_2" query="true"></t:dgCol> --%>
<%-- 	<t:dgCol title="用户状态"  field="userStatus" replace="已激活_1,已停用_0,已锁定_2,待激活_3" ></t:dgCol> --%>
</t:datagrid>
</div>
</div>

<script type="text/javascript">

</script>
