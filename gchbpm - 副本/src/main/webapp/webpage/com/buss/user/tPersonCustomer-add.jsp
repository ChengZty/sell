<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>用户信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPersonController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPersonPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tPersonPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tPersonPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tPersonPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tPersonPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPersonPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPersonPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="quantity" name="quantity" type="hidden" value="0">
					<input id="money" name="money" type="hidden" value="0.00">
					<input id="toRetailerId" name="toRetailerId" type="hidden" value="${to_retiailer_id }">
					<input id="provinceId" name="provinceId" type="hidden" value="${tPersonPage.provinceId}">
					<input id="cityId" name="cityId" type="hidden" value="${tPersonPage.cityId}">
					<input id="industry" name="industry" type="hidden" value="${tPersonPage.industry}">
					<input id="height" name="height" type="hidden" value="${tPersonPage.height}">
					<input id="weight" name="weight" type="hidden" value="${tPersonPage.weight}">
					<input id="favouriteColor" name="favouriteColor" type="hidden" value="${tPersonPage.favouriteColor}">
					<input id="helperType" name="helperType" type="hidden" value="${tPersonPage.helperType}">
					<input id="userType" name="userType" type="hidden" value="04">
					<input id="remark" name="remark" type="hidden" value="${tPersonPage.remark}">
					<input id="isBind" name="isBind" type="hidden" value="0">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="code" name="code" type="text" style="width: 150px"   datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">编码</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px"   datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">姓名</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							性别:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="sex" type="list" datatype="*"
									typeGroupCode="sex" defaultVal="${tPersonPage.sex}" hasLabel="false"  title="性别"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">性别</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							手机号码:
						</label>
					</td>
					<td class="value">
					     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px"   datatype="m">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">手机号码</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							生日:
						</label>
					</td>
					<td class="value">
							   <input id="birthday" name="birthday" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">生日</label>
						</td>
					<td align="right">
					</td>
					<td class="value">
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							VIP等级:
						</label>
					</td>
					<td class="value">
					     	 <input id="vipLevel" name="vipLevel" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">VIP等级</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							职业:
						</label>
					</td>
					<td class="value">
					     	 <input id="profession" name="profession" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">职业</label>
						</td>
					</tr>
					<%-- 
					<tr>
					<td align="right">
						<label class="Validform_label">
							生日:
						</label>
					</td>
					<td class="value">
							   <input id="birthday" name="birthday" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">生日</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							所在省:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="province" type="list"
									typeGroupCode="" defaultVal="${tPersonPage.province}" hasLabel="false"  title="所在省"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所在省</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							所在市:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="city" type="list"
									typeGroupCode="" defaultVal="${tPersonPage.city}" hasLabel="false"  title="所在市"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所在市</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							身份证号:
						</label>
					</td>
					<td class="value">
					     	 <input id="idCard" name="idCard" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">身份证号</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							件数:
						</label>
					</td>
					<td class="value">
					     	 <input id="quantity" name="quantity" type="text" style="width: 150px"  readonly="readonly" value="0">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">件数</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="money" name="money" type="text" style="width: 150px"  readonly="readonly" value="0">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">金额</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属导购:
						</label>
					</td>
					<td class="value">
					     	 <input id="toGuideId" name="toGuideId" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属导购</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							所属零售商:
						</label>
					</td>
					<td class="value">
					     	 <input id="toRetailerId" name="toRetailerId" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属零售商</label>
						</td>
					</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							行业:
						</label>
					</td>
					<td class="value">
					     	 <input id="industry" name="industry" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">行业</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							身高:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="height" type="list"
									typeGroupCode="h_type" defaultVal="${tPersonPage.height}" hasLabel="false"  title="身高"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">身高</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							体重:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="weight" type="list"
									typeGroupCode="w_type" defaultVal="${tPersonPage.weight}" hasLabel="false"  title="体重"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">体重</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							喜欢的颜色:
						</label>
					</td>
					<td class="value">
					     	 <input id="favouriteColor" name="favouriteColor" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">喜欢的颜色</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							帮手大类:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="helperType" type="list"
									typeGroupCode="hp_type" defaultVal="${tPersonPage.helperType}" hasLabel="false"  title="帮手大类"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">帮手大类</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							用户类别:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="userType" type="list" datatype="*"
									typeGroupCode="user_type" defaultVal="${tPersonPage.userType}" hasLabel="false"  title="用户类别"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户类别</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
					     	 <input id="remark" name="remark" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
					--%>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/user/tPerson.js"></script>		