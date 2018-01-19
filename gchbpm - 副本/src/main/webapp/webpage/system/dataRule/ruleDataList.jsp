<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="operationList" title="数据权限控制" actionUrl="functionController.do?ruledategrid&functionId=${functionId}" idField="id">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="规则名称" field="ruleName" width="50"></t:dgCol>
    <t:dgCol title="规则字段" field="ruleColumn" width="50"></t:dgCol>
    <t:dgCol title="规则条件" field="ruleConditions" width="50"></t:dgCol>
    <t:dgCol title="规则值" field="ruleValue" width="50"></t:dgCol>
    <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt url="functionController.do?delrule&id={id}" title="删除"></t:dgDelOpt>
	<t:dgFunOpt funname="editoperation(id,operationname)" title="common.edit"></t:dgFunOpt>
	<t:dgToolBar title="录入" langArg="操作" icon="icon-add" url="functionController.do?addorupdaterule&functionId=${functionId}" funname="add"></t:dgToolBar>
	</t:datagrid>
<script type="text/javascript">
function editoperation(operationId,operationname)
{
	createwindow("<t:mutiLang langKey="编辑" langArg="操作"/>","functionController.do?addorupdaterule&functionId=${functionId}&id="+operationId);
}
</script>
