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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPersonController.do?doUpdateCustomer" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPersonPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tPersonPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tPersonPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tPersonPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tPersonPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPersonPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPersonPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tPersonPage.status }">
					<input id="quantity" name="quantity" type="hidden" value="${tPersonPage.quantity }">
					<input id="money" name="money" type="hidden" value="${tPersonPage.money }">
					<input id="toRetailerId" name="toRetailerId" type="hidden" value="${tPersonPage.toRetailerId }">
					<input id="provinceId" name="provinceId" type="hidden" value="${tPersonPage.provinceId}">
					<input id="cityId" name="cityId" type="hidden" value="${tPersonPage.cityId}">
					<input id="industry" name="industry" type="hidden" value="${tPersonPage.industry}">
					<input id="height" name="height" type="hidden" value="${tPersonPage.height}">
					<input id="weight" name="weight" type="hidden" value="${tPersonPage.weight}">
					<input id="favouriteColor" name="favouriteColor" type="hidden" value="${tPersonPage.favouriteColor}">
					<input id="helperType" name="helperType" type="hidden" value="${tPersonPage.helperType}">
					<input id="userType" name="userType" type="hidden" value="${tPersonPage.userType}">
					<input id="remark" name="remark" type="hidden" value="${tPersonPage.remark}">
					<input id="isBind" name="isBind" type="hidden" value="${tPersonPage.isBind}">
					<input id="userId" name="userId" type="hidden" value="${tPersonPage.userId}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								编码:
							</label>
						</td>
						<td class="value">
						<c:if test="${tPersonPage.id!=null }"> ${tPersonPage.code } </c:if>
						<c:if test="${tPersonPage.id==null }">
						     	 <input id="code" name="code" type="text" style="width: 150px"   datatype="*" value='${tPersonPage.code}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">编码</label>
						</c:if>
						</td>
						<td align="right">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px"   datatype="*" value='${tPersonPage.name}'>
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
						     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px" maxlength="11"  datatype="m" value='${tPersonPage.phoneNo}'>
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
									 <input name="birthday" class="Wdate" onClick="WdatePicker()" style="width: 150px" value="<fmt:formatDate value='${tPersonPage.birthday }' type="date" pattern="yyyy-MM-dd"/>"
				errormsg="生日格式不正确!" ignore="ignore"> <span class="Validform_checktip"></span>
						</td>
						<td align="right">
							<label class="Validform_label">
								身份证号:
							</label>
						</td>
						<td class="value">
						     	 <input id="idCard" name="idCard" type="text" style="width: 150px"   value='${tPersonPage.idCard}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">身份证号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								VIP等级:
							</label>
						</td>
						<td class="value">
						     	 <input id="vipLevel" name="vipLevel" type="text" style="width: 150px"   value='${tPersonPage.vipLevel}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">VIP等级</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								职业:
							</label>
						</td>
						<td class="value">
						     	 <input id="profession" name="profession" type="text" style="width: 150px"   value='${tPersonPage.profession}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">职业</label>
						</td>
					</tr>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/user/tPerson.js"></script>		