<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
    <script>
        
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
<!-- 零售商新增帐号 -->
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="userController.do?saveUser" tiptype="4">
	<input id="id" name="id" type="hidden" value="${user.id }">
	<input id="area" name="area" type="hidden" value="${user.area }">
	<input id="status" name="status" type="hidden" value="A">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="15%" >
                <label class="Validform_label">  用户名: </label>
            </td>
			<td class="value" width="85%">
                    <input id="userName" type="text" name="userName"  validType="t_s_user,user_type <> '03' and user_type <> '04' and username" value="${user.userName }" datatype="m" maxlength="11"/>
                    <span class="Validform_checktip"> 请填写手机号码</span>
            </td>
		</tr>
		<tr>
			<td align="right"  ><label class="Validform_label"> 真实姓名: </label></td>
			<td class="value" >
                <input id="realName" type="text"  name="realName" value="${user.realName }" datatype="*2-20" >
                <span class="Validform_checktip">请填写真实姓名！</span>
            </td>
		</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 密码: </label></td>
				<td class="value">
                    <input type="password"  class="password" value="" name="password" plugin="passwordStrength" datatype="*6-18" />
                    <span class="passwordStrength" style="display: none;">
                        <span>弱</span>
                        <span>中</span>
                        <span class="last">强</span>
                    </span>
                    <span class="Validform_checktip"> 密码至少6个字符,最多18个字符</span>
                </td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 重复密码: </label></td>
				<td class="value">
                    <input id="repassword" class="password"  type="password" value="${user.password}" recheck="password" datatype="*6-18" errormsg="两次输入的密码不一致！">
                    <span class="Validform_checktip">重复密码</span>
                </td>
			</tr>
		<c:if test="${user_Type =='01'}">
		<tr>
			<td align="right" ><label class="Validform_label">  手机号码: </label></td>
			<td class="value">
                <input  name="mobilePhone" type="text" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		</c:if>
		<tr>
			<td align="right"><label class="Validform_label"> 角色: </label></td>
			<td class="value" >
                <input name="roleid" type="hidden" value="${id}" id="roleid">
                <input name="roleName" type="text" value="${roleName }" id="roleName" readonly="readonly" datatype="*" />
                <t:choose hiddenName="roleid" hiddenid="id" url="userController.do?roles" name="roleList"
                          icon="icon-search" title="角色列表" textname="roleName" isclear="true" isInit="true"></t:choose>
                <span class="Validform_checktip">导购和顾客从【人员管理】菜单录入</span>
            </td>
		</tr>
		<tr>
			<td align="right" ><label class="Validform_label">用户类型: </label></td>
				<td class="value">
				零售商员工
				<input name="userType"  type="hidden" value="05" >
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
				<c:forEach items="${areaList}" var="area">
					<option value="${area.areaId }" >${area.areaName}</option>
				</c:forEach>
			</select>
			<select id="cityId" name="cityId" datatype="*"  style="width: 100px" onchange="fitName()">
				<option value="">---请选择---</option>
			</select>
							<label class="Validform_label" style="display: none;">所在省市</label>
						</td>
					</tr>
		
	</table>
</t:formvalid>
</body>
