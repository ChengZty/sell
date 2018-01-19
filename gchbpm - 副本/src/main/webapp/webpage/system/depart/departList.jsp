<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="departList" title="组织机构列表"
                    actionUrl="departController.do?departgrid"
                    treegrid="true" idField="departid" pagination="false">
            <t:dgCol title="编号" field="id" treefield="id" hidden="true"></t:dgCol>
            <t:dgCol title="组织机构名称" field="departname" treefield="text"></t:dgCol>
            <t:dgCol title="组织机构描述" field="description" treefield="src"></t:dgCol>
            <t:dgCol title="机构编码" field="orgCode" treefield="fieldMap.orgCode"></t:dgCol>
            <t:dgCol title="机构类型" field="orgType" dictionary="orgtype" treefield="fieldMap.orgType"></t:dgCol>
            <t:dgCol title="common.mobile" field="mobile" treefield="fieldMap.mobile"></t:dgCol>
            <t:dgCol title="common.fax" field="fax" treefield="fieldMap.fax"></t:dgCol>
            <t:dgCol title="common.address" field="address" treefield="fieldMap.address"></t:dgCol>
            <t:dgCol title="操作" field="opt"></t:dgCol>
            <t:dgDelOpt url="departController.do?del&id={id}" title="删除"></t:dgDelOpt>
            <t:dgFunOpt funname="queryUsersByDepart(id)" title="查看成员"></t:dgFunOpt>
            <t:dgFunOpt funname="setRoleByDepart(id,text)" title="角色设置"></t:dgFunOpt>
        </t:datagrid>
        <div id="departListtb" style="padding: 3px; height: 25px">
            <div style="float: left;">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="addOrg()"><t:mutiLang langKey="录入" langArg="common.department"/></a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('<t:mutiLang langKey="编辑" langArg="common.department"/>','departController.do?update','departList')"><t:mutiLang langKey="编辑" langArg="common.department"/></a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-put" onclick="ImportXls()"><t:mutiLang langKey="excelImport" langArg="common.department"/></a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-putout" onclick="ExportXls()"><t:mutiLang langKey="excelOutput" langArg="common.department"/></a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-putout" onclick="ExportXlsByT()"><t:mutiLang langKey="templateDownload" langArg="common.department"/></a>
            </div>
        </div>
    </div>
</div>
<div data-options="region:'east',
	title:'成员列表',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="userListpanel"></div>
</div>

<script type="text/javascript">
<!--
//update-start--Author:zhangguoming  Date:20140821 for：为组织机构设置角色
    $(function() {
        var li_east = 0;
    });
    function addOrg() {
        var id = "";
        var rowsData = $('#departList').datagrid('getSelections');
        if (rowsData.length == 1) {
            id = rowsData[0].id;
        }
        var url = "departController.do?add&id=" + id;
        add('<t:mutiLang langKey="录入" langArg="common.department"/>', url, "departList");
    }

    function queryUsersByDepart(departid){
        var title = '成员列表';
        if(li_east == 0 || $('#main_depart_list').layout('panel','east').panel('options').title != title){
            $('#main_depart_list').layout('expand','east');
        }
        <%--$('#eastPanel').panel('setTitle','成员列表');--%>
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 500});
        $('#userListpanel').panel("refresh", "departController.do?userList&departid=" + departid);
    }
    /**
     * 为 组织机构 设置 角色
     * @param departid 组织机构主键
     * @param departname 组织机构名称
     */
    function setRoleByDepart(departid, departname){
        var currentTitle = $('#main_depart_list').layout('panel', 'east').panel('options').title;
        if(li_east == 0 || currentTitle.indexOf("当前机构") < 0){
            $('#main_depart_list').layout('expand','east');
        }
        var title = departname + ':当前机构';
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 200});
        var url = {
            <%--title :"test",--%>
            href:"roleController.do?roleTree&orgId=" + departid
        }
        $('#userListpanel').panel(url);
        $('#userListpanel').panel("refresh");
    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'departController.do?upload', "departList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("departController.do?exportXls","departList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("departController.do?exportXlsByT","departList");
    }
//update-end--Author:zhangguoming  Date:20140821 for：为组织机构设置角色
//-->
</script>
