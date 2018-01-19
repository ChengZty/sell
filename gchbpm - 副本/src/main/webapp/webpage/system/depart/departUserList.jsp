<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="departUserList" title="操作"
            actionUrl="departController.do?userDatagrid&departid=${departid}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户name" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
	<t:dgCol title="用户状态" sortable="true" field="status" replace="激活_1,未激活_0,超级管理员_-1"></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgDelOpt title="删除" url="userController.do?del&id={id}&userName={userName}" />
	<t:dgToolBar title="录入" langArg="用户" icon="icon-add" url="userController.do?addorupdate&departid=${departid}" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" langArg="用户" icon="icon-edit" url="userController.do?addorupdate&departid=${departid}" funname="update"></t:dgToolBar>
    <%--update-start--Author:zhangguoming  Date:20140826 for：添加有客户--%>
	<t:dgToolBar title="添加已有客户" icon="icon-add" url="departController.do?goAddUserToOrg&orgId=${departid}" funname="add" width="500"></t:dgToolBar>
    <%--update-end--Author:zhangguoming  Date:20140826 for：添加有客户--%>
</t:datagrid>
