<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="typeValueList" title="类型列表"
                    actionUrl="systemController.do?typeGrid&typegroupid=${typegroupid}" idField="id"
                    queryMode="group">
            <t:dgCol title="编码" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="类型名称" field="typename"></t:dgCol>
            <t:dgCol title="类型编码" field="typecode"></t:dgCol>
            <t:dgCol title="操作" field="opt"></t:dgCol>
            <t:dgDelOpt url="systemController.do?delType&id={id}" title="删除"></t:dgDelOpt>
            <t:dgToolBar title="录入" langArg="common.type" icon="icon-add" url="systemController.do?addorupdateType&typegroupid=${typegroupid}" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" langArg="common.type" icon="icon-edit" url="systemController.do?addorupdateType&typegroupid=${typegroupid}" funname="update"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script>
    function addType(title,addurl,gname,width,height) {
        alert($("#id").val());
        add(title,addurl,gname,width,height);
    }
</script>

