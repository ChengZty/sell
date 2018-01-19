<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="roleList" title="订单短信自动回复列表" actionUrl="tOrderMsgController.do?datagrid" fitColumns="false" extendParams="nowrap:true,"
    idField="id" sortName="createDate" sortOrder="desc">
	<t:dgCol title="编码" field="id" width="100" hidden="true"></t:dgCol>
	<%-- <t:dgCol title="创建人" field="createName" width="100" ></t:dgCol>
	<t:dgCol title="修改人"  field="updateName" width="100"  ></t:dgCol> --%>
	<t:dgCol title="短信类型" field="paraName" width="120" ></t:dgCol>
	<t:dgCol title="短信内容模板" field="paraValue" ></t:dgCol>
	<%-- <t:dgCol title="操作" field="opt" width="180" ></t:dgCol> --%>
	<%-- <t:dgFunOpt funname="delRole(id)" title="删除"></t:dgFunOpt> --%>
	<%-- <t:dgFunOpt funname="userListbyrole(id,roleName)" title="用户"></t:dgFunOpt>
	<t:dgFunOpt funname="setfunbyrole(id,roleName)" title="权限设置"></t:dgFunOpt> --%>
	<t:dgToolBar title="录入" langArg="common.role" icon="icon-add" url="tOrderMsgController.do?goAdd" funname="add"></t:dgToolBar>
	<%-- <t:dgToolBar title="编辑" langArg="common.role" icon="icon-edit" url="tOrderMsgController.do?goUpdate" funname="update"></t:dgToolBar> --%>
</t:datagrid></div>
</div>
<div id="tt"></div>

<script type="text/javascript">

//删除参数
function delRole(id){
	var tabName= 'roleList';
	var url= 'tOrderMsgController.do?doDel&id='+id;
	$.dialog.confirm('确定删除该记录吗', function(){
		doSubmit(url,tabName);
		rowid = '';
	}, function(){
	});
}
</script>
