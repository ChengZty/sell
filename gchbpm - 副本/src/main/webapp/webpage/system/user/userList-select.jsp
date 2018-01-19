<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="userList" title="选择用户" actionUrl="userController.do?datagridOfWeb" fitColumns="true" idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
			<t:dgCol title="编码" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="用户名" field="userName" query="true" ></t:dgCol>
			<t:dgCol title="真实姓名" field="realName" query="false"></t:dgCol>
			<t:dgCol title="所属区域" field="area" ></t:dgCol>
			<t:dgCol title="用户类型" field="userType" query="true" replace="g+_01,零售商_02,零售商员工_05"></t:dgCol>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
  	function getUserId(){
  		var rowsData = $("#userList").datagrid("getChecked");
  		var id = "";
  		if(rowsData.length==0){
  			tip('请选择用户');
  			return "";
  		}
  		return rowsData[0].id;
  	}
  </script>
