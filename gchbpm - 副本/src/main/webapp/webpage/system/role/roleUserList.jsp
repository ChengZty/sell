<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%--<t:datagrid name="userList" title="用户管理" actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id">--%>
<%--	<t:dgCol title="编号" field="id" hidden="true" ></t:dgCol>--%>
<%--	<t:dgCol title="用户名" sortable="false" field="userName" width="5"></t:dgCol>--%>
<%--	<t:dgCol title="真实姓名" field="realName" width="5"></t:dgCol>--%>
<%--</t:datagrid>--%>

<t:datagrid name="roleUserList" title="操作"
            actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
	<t:dgCol title="用户类型" field="userType"  dictionary="user_type"></t:dgCol>
	<t:dgCol title="用户状态" sortable="true" field="userStatus" replace="激活_1,未激活_0,超级管理员_-1"></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgDelOpt title="解除绑定" url="roleController.do?doRemove&id={id}&roleId=${roleId}" />
<%-- 	<t:dgToolBar title="录入" langArg="common.user" icon="icon-add" url="userController.do?addorupdate&roleId=${roleId}" funname="add"></t:dgToolBar> --%>
<%-- 	<t:dgToolBar title="编辑" langArg="common.user" icon="icon-edit" url="userController.do?addorupdate&roleId=${roleId}" funname="update"></t:dgToolBar> --%>
<%-- 	<t:dgToolBar title="录入" langArg="common.user" icon="icon-add" url="userController.do?goAdd&roleId=${roleId}" funname="add"></t:dgToolBar> --%>
<%-- 	<t:dgToolBar title="编辑" langArg="common.user" icon="icon-edit" url="userController.do?goUpdate&roleId=${roleId}" funname="update"></t:dgToolBar> --%>
	<t:dgToolBar title="添加已有用户" icon="icon-add" url="roleController.do?goAddUserToRole&roleId=${roleId}" funname="add" width="500" height="600"></t:dgToolBar>
</t:datagrid>

