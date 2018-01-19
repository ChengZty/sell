<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>角色信息</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="roleController.do?saveRole">
	<input name="id" type="hidden" value="${role.id}">
	<input id="createName" name="createName" type="hidden" value="${role.createName }">
	<input id="createBy" name="createBy" type="hidden" value="${role.createBy }">
	<input id="createDate" name="createDate" type="hidden" value="${role.createDate }">
	<input id="updateName" name="updateName" type="hidden" value="${role.updateName }">
	<input id="updateBy" name="updateBy" type="hidden" value="${role.updateBy }">
	<input id="updateDate" name="updateDate" type="hidden" value="${role.updateDate }">
	<input id="status" name="status" type="hidden" value="A">
	<input name="retailerId" type="hidden" value="${role.retailerId}">
	<fieldset class="step">
	<div class="form"><label class="Validform_label">角色名称:</label> <input name="roleName" type="text" value="${role.roleName }" datatype="nb2-30"> <span class="Validform_checktip">角色范围在2~30位字符</span>
	</div>
	<div class="form"><label class="Validform_label"> 角色编码: </label> <input name="roleCode" type="text" id="roleCode" ajaxurl="roleController.do?checkRole&code=${role.roleCode }" 
		value="${role.roleCode }" datatype="s2-10"> <span class="Validform_checktip">角色编码范围在2~10位字符</span></div>
		<c:if test="${user_type =='01' }">
	<div class="form"><label class="Validform_label">角色类型:</label> 
		<select name="roleType" id="roleType">
		<option value="01">后台</option>
		<option value="02">零售商</option>
		</select>
	</c:if>
	<c:if test="${user_type !='01' }">
		<input name="roleType" type="hidden" value="02"/>
	</c:if>
	</fieldset>
</t:formvalid>
</body>
<script type="text/javascript">
// 	var opt = $("#roleType option[value='${role.roleType }']").attr("checked","checked");
	var opt = $("#roleType option[value='${role.roleType }']");
		opt.attr("selected",true);
</script>
</html>
