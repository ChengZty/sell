<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
    <script>
        $(function() {
			$("#userType").attr("disabled",true);
        });
        
      //获取城市
        function getCitys(areaId){
        	var url = "territoryController.do?getCitys&areaId="+areaId;
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
        				$("#cityId").empty();
        				var ops = "<option value=''>---请选择---</option>";
//        				tip(d.obj.length);
        				for(i=0;i<d.obj.length;i++){
        					ops+="<option value='"+d.obj[i].areaId+"'>"+d.obj[i].areaName+"</option>"
        				}
        				$("#cityId").append(ops);
        			}
        			
        		}
        	});
        }
      
        function fitName(){
          	var provinceName = $("#provinceId").find("option:selected").text();
          	var cityName = $("#cityId").find("option:selected").text();
        	$("#area").val(provinceName+cityName);
          }
        
    </script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="userController.do?saveUser" tiptype="4">
	<input id="id" name="id" type="hidden" value="${user.id }"/>
	<input id="area" name="area" type="hidden" value="${user.area }">
	<input id="status" name="status" type="hidden" value="A"/>
	<input  name="mobilePhone" type="hidden" value="${user.mobilePhone}"/>
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="15%" >
                <label class="Validform_label">用户名: </label>
            </td>
			<td class="value" width="85%">
                 ${user.userName } 
            </td>
		</tr>
		<tr>
			<td align="right" width="10%" nowrap><label class="Validform_label">真实姓名: </label></td>
			<td class="value" width="10%">
                <input id="realName" type="text"  name="realName" value="${user.realName }" datatype="*2-20">
                <span class="Validform_checktip">填写个人真实姓名</span>
            </td>
		</tr>
		<tr>
				<td align="right">
						<label class="Validform_label">
							所在省市:
						</label>
					</td>
					<td class="value" >
			<select id="provinceId" name="provinceId" datatype="*" onchange="getCitys(this.value)" style="width: 100px">
				<c:forEach items="${areaList}" var="province">
					<option value="${province.areaId }" <c:if test="${province.areaId==user.provinceId}">selected="selected"</c:if>>${province.areaName}</option>
				</c:forEach>
			</select>
			<select id="cityId" name="cityId" datatype="*"  style="width: 100px" onchange="fitName()">
				<option value="">---请选择---</option>
				<c:forEach items="${cityList}" var="city">
					<option value="${city.areaId }" <c:if test="${city.areaId==user.cityId}">selected="selected"</c:if>>${city.areaName}</option>
				</c:forEach>
			</select>
							<label class="Validform_label" style="display: none;">所在省市</label>
						</td>
					</tr>
		<c:if test="${not empty roleName }">
		<tr>
			<td align="right"><label class="Validform_label"> 角色: </label></td>
			<td class="value" nowrap>
                <input name="roleid" type="hidden" value="${id}" id="roleid">
                <input name="roleName" type="text" value="${roleName }" id="roleName" readonly="readonly" datatype="*" />
                <t:choose hiddenName="roleid" hiddenid="id" url="userController.do?roles" name="roleList"
                          icon="icon-search" title="角色列表" textname="roleName" isclear="true" isInit="true"></t:choose>
            </td>
		</tr>
		</c:if>
		<tr>
			<td align="right" nowrap><label class="Validform_label">用户类型: </label></td>
		<td class="value">
			<input name="userType"  type="hidden" value="${user.userType}" >
			 <t:dictSelect field="userType" id="userType" type="list" typeGroupCode="user_type" defaultVal="${user.userType}" hasLabel="false"></t:dictSelect>   
			 </td>
		</tr>
	</table>
</t:formvalid>
</body>
