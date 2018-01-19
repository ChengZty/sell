<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
</script>
<t:datagrid name="userList" title="操作" actionUrl="userController.do?datagrid" 
    fit="true" fitColumns="true" idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true"></t:dgCol>
<%-- 	<t:dgCol title="用户编码" sortable="false" field="userCode" query="true"></t:dgCol> --%>
	<%--<t:dgCol title="common.department" field="TSDepart_id" query="true" replace="${departsReplace}"></t:dgCol>--%>
<%-- 	<t:dgCol title="common.department" sortable="false" field="userOrgList.tsDepart.departname" query="false"></t:dgCol> --%>
	<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
	<t:dgCol title="手机号" field="mobilePhone" ></t:dgCol>
<%-- 	<t:dgCol title="角色" field="userKey" ></t:dgCol> --%>
	<t:dgCol title="用户类型" field="userType" query="true" dictionary="user_type"></t:dgCol>
	<t:dgCol title="所属零售商" field="retailerId" query="false" dictionary="t_s_user,id,realname,user_type='02' and status = 'A' "></t:dgCol>
<%-- 	<t:dgCol title="零售商类型" field="retailerType" replace="零售商 人货_1,零售商 货_2,零售商 人_3"></t:dgCol> --%>
	<t:dgCol title="所属区域" field="area" ></t:dgCol>
	<t:dgCol title="创建人" field="createBy" hidden="true"></t:dgCol>
	<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="修改人" field="updateBy" hidden="true"></t:dgCol>
	<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="用户状态" sortable="true" field="userStatus" replace="已激活_1,已停用_0,已锁定_2,待激活_3,已注销_4" ></t:dgCol>
	<t:dgCol title="激活时间" field="activeTime" formatter="yyyy-MM-dd" ></t:dgCol>
	<t:dgToolBar title="录入" langArg="common.user" icon="icon-add" url="userController.do?goAdd" funname="add" height="420"></t:dgToolBar>
	<t:dgToolBar title="编辑" langArg="common.user" icon="icon-edit" url="userController.do?goUpdate" funname="update"></t:dgToolBar>
	<t:dgToolBar title="密码重置" icon="icon-edit" url="userController.do?changepasswordforuser" funname="update" width="450" height="100"></t:dgToolBar>
	<t:dgToolBar title="锁定" icon="icon-edit" url="userController.do?lock&lockvalue=2" funname="lockObj"></t:dgToolBar>
	<t:dgToolBar title="激活" icon="icon-edit" url="userController.do?unlock" funname="unlockObj"></t:dgToolBar>
	<t:dgToolBar title="停用" icon="icon-edit" url="userController.do?lock&lockvalue=0" funname="forbidObj"></t:dgToolBar>
	<t:dgToolBar title="注销" icon="icon-remove" url="userController.do?destroy" funname="destroyObj"></t:dgToolBar>
	
</t:datagrid>
<script type="text/javascript">
//锁定
function lockObj(title,url, gname) {
	gridname=gname;
	var rowsData = $('#'+gname).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择用户');
		return;
	}
		url += '&id='+rowsData[0].id;

	$.dialog.confirm('确定锁定用户吗', function(){
		lockuploadify(url);
	}, function(){
	});
}

//激活
function unlockObj(title,url, gname) {
	gridname=gname;
	var rowsData = $('#'+gname).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择用户');
		return;
	}
		url += '&id='+rowsData[0].id;

	$.dialog.confirm('确定激活用户吗', function(){
		lockuploadify(url);
	}, function(){
	});
}

//停用
function forbidObj(title,url, gname) {
	gridname=gname;
	var rowsData = $('#'+gname).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	}
		url += '&id='+rowsData[0].id;

	$.dialog.confirm('确定停用用户吗', function(){
		lockuploadify(url);
	}, function(){
	});
}

//注销
function destroyObj(title,url, gname) {
	gridname=gname;
	var rowsData = $('#'+gname).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择用户');
		return;
	}
		url += '&id='+rowsData[0].id;

	$.dialog.confirm('确定注销用户吗', function(){
		lockuploadify(url);
	}, function(){
	});
}

function lockuploadify(url) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		
		
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
			var msg = d.msg;
				tip(msg);
				reloadTable();
			}
			
		}
	});
}
</script>
<%--<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onClick="choose_297e201048183a730148183ad85c0001()">选择</a>--%>
<%--<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo" onClick="clearAll_297e201048183a730148183ad85c0001();">清空</a>--%>
<script type="text/javascript">
//    var windowapi = frameElement.api, W = windowapi.opener;
/**
    function choose_297e201048183a730148183ad85c0001() {
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: 'url:departController.do?departSelect', zIndex: 2100, title: '组织机构列表', lock: true, width: 400, height: 350, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '确定', callback: clickcallback_297e201048183a730148183ad85c0001, focus: true},
                {name: '取消', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: 'url:departController.do?departSelect', zIndex: 2100, title: '组织机构列表', lock: true, parent: windowapi, width: 400, height: 350, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '确定', callback: clickcallback_297e201048183a730148183ad85c0001, focus: true},
                {name: '取消', callback: function () {
                }}
            ]});
        }
    }
    function clearAll_297e201048183a730148183ad85c0001() {
        if ($('#departname').length >= 1) {
            $('#departname').val('');
            $('#departname').blur();
        }
        if ($("input[name='departname']").length >= 1) {
            $("input[name='departname']").val('');
            $("input[name='departname']").blur();
        }
        $('#orgIds').val("");
    }
    function clickcallback_297e201048183a730148183ad85c0001() {
        iframe = this.iframe.contentWindow;
        var departname = iframe.getdepartListSelections('departname');
        if ($('#departname').length >= 1) {
            $('#departname').val(departname);
            $('#departname').blur();
        }
        if ($("input[name='departname']").length >= 1) {
            $("input[name='departname']").val(departname);
            $("input[name='departname']").blur();
        }
        var id = iframe.getdepartListSelections('id');
        if (id !== undefined && id != "") {
            $('#orgIds').val(id);
            $("input[name='orgIds']").val(id);
        }
    }
    */
</script>
