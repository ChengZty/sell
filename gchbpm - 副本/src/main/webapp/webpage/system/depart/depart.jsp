<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function() {
		$('#cc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}',
            width: 155,
            onSelect : function(node) {
//                alert(node.text);
                changeOrgType();
            }
        });
        if(!$('#cc').val()) { // 第一级，只显示公司选择项
            var orgTypeSelect = $("#orgType");
            var companyOrgType = '<option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>>公司</option>';
            orgTypeSelect.empty();
            orgTypeSelect.append(companyOrgType);
        } else { // 非第一级，不显示公司选择项
            $("#orgType option:first").remove();
        }
        if($("#id").val()) {
            $('#cc').combotree('disable');
        }
        if('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#cc').combotree('setValue', '${pid}');
        }
	});
    function changeOrgType() { // 处理组织类型，不显示公司选择项
        var orgTypeSelect = $("#orgType");
        var optionNum = orgTypeSelect.get(0).options.length;
        if(optionNum == 1) {
            $("#orgType option:first").remove();
            var bumen = '<option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>>组织机构</option>';
            var gangwei = '<option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>>岗位</option>';
            orgTypeSelect.append(bumen).append(gangwei);
        }
    }
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="systemController.do?saveDepart">
	<input id="id" name="id" type="hidden" value="${depart.id }">
	<fieldset class="step">
        <div class="form">
            <label class="Validform_label"> 组织机构名称: </label>
            <input name="departname" class="inputxt" type="text" value="${depart.departname }"  datatype="s1-20">
            <span class="Validform_checktip">组织机构范围1~20位字符</span>
        </div>
        <div class="form">
            <label class="Validform_label"> 组织机构描述: </label>
            <input name="description" class="inputxt" value="${depart.description }">
        </div>
        <div class="form">
            <label class="Validform_label"> 上级组织机构: </label>
            <input id="cc" name="TSPDepart.id" value="${depart.TSPDepart.id}">
        </div>
        <div class="form">
            <input type="hidden" name="orgCode" value="${depart.orgCode }">
            <label class="Validform_label"> 机构类型: </label>
            <select name="orgType" id="orgType">
                <option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>>公司</option>
                <option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>>组织机构</option>
                <option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>>岗位</option>
            </select>
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.mobile"/>: </label>
            <input name="mobile" class="inputxt" value="${depart.mobile }">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.fax"/>: </label>
            <input name="fax" class="inputxt" value="${depart.fax }">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.address"/>: </label>
            <input name="address" class="inputxt" value="${depart.address }" datatype="s1-50">
            <span class="Validform_checktip"><t:mutiLang langKey="departmentaddress.rang1to50"/></span>
        </div>
	</fieldset>
</t:formvalid>
</body>
</html>
