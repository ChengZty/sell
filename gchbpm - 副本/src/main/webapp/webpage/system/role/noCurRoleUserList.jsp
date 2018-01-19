<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<%--非当前角色的用户列表--%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_role_list" class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="noCurRoleUserList" title="操作"
                    actionUrl="roleController.do?addUserToRoleList&roleId=${param.roleId}" fit="true" fitColumns="true"
                    idField="id" checkbox="true" queryMode="group">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="用户名" sortable="false" field="userName" query="true"></t:dgCol>
            <t:dgCol title="用户类型" field="userType"  dictionary="user_type"></t:dgCol>
            <t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
            <t:dgCol title="用户状态" sortable="true" field="userStatus" replace="激活_1,未激活_0,超级管理员_-1"></t:dgCol>
        </t:datagrid>
    </div>
</div>

<div style="display: none">
    <t:formvalid formid="formobj" layout="div" dialog="true" action="roleController.do?doAddUserToRole&roleId=${param.roleId}" beforeSubmit="setUserIds">
        <input id="userIds" name="userIds">
    </t:formvalid>
</div>
<script>
    function setUserIds() {
        $("#userIds").val(getUserListSelections('id'));
        return true;
    }

    function getUserListSelections(field) {
        var ids = [];
        var rows = $('#noCurRoleUserList').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i][field]);
        }
        ids.join(',');
        return ids
    }
</script>
