<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>用户信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
//获取省市全名
//   function fitName(){
//   	var provinceName = $("#provinceId").find("option:selected").text();
//   	var cityName = $("#cityId").find("option:selected").text();
// 	$("#area").val(provinceName+cityName);
//   }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPersonController.do?doUpdateGuide" tiptype="4">
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
					<input id="industry" name="industry" type="hidden" value="${tPersonPage.industry}">
					<input id="height" name="height" type="hidden" value="${tPersonPage.height}">
					<input id="weight" name="weight" type="hidden" value="${tPersonPage.weight}">
					<input id="favouriteColor" name="favouriteColor" type="hidden" value="${tPersonPage.favouriteColor}">
					<input id="helperType" name="helperType" type="hidden" value="${tPersonPage.helperType}">
					<input id="userType" name="userType" type="hidden" value="${tPersonPage.userType}">
					<input id="remark" name="remark" type="hidden" value="${tPersonPage.remark}">
					<input id="userId" name="userId" type="hidden" value="${tPersonPage.userId}">
					<input id="provinceId" name="provinceId" type="hidden" value="${tPersonPage.provinceId}">
					<input id="cityId" name="cityId" type="hidden" value="${tPersonPage.cityId}">
					<input id="area" name="area" type="hidden" value="${tPersonPage.area}">
					<input id="hasTags" name="hasTags" type="hidden" value="${tPersonPage.hasTags}">
					<input id="canBind" name="canBind" type="hidden" value="${tPersonPage.canBind}">
					<input id="fans" name="fans" type="hidden" value="${tPersonPage.fans}">
		<table style="width: 800px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right"  style="width: 50px">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value" style="width: 350px">
						     	 <input id="realName" name="realName" type="text" style="width: 150px"   datatype="*" value='${tPersonPage.realName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">姓名</label>
						</td>
						<td align="right" style="width: 50px">
							<label class="Validform_label">
								昵称:
							</label>
						</td>
						<td class="value" style="width: 350px">
						     	 <input id="name" name="name" type="text" style="width: 150px"  value='${tPersonPage.name}'>
						     	 <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">昵称</label>
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
					     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px"  readonly="readonly" value='${tPersonPage.phoneNo}'>
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
					<input name="birthday" type="text"  class="Wdate" onClick="WdatePicker()" style="width: 150px" value="<fmt:formatDate value='${tPersonPage.birthday }' type="date" pattern="yyyy-MM-dd"/>"
				errormsg="生日格式不正确!" ignore="ignore"> <span class="Validform_checktip"></span>
						</td>
						<td align="right">
						<label class="Validform_label">
							身份证号:
						</label>
					</td>
					<td class="value">
					     	 <input id="idCard" name="idCard" type="text" style="width: 150px"  value='${tPersonPage.idCard}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">身份证号</label>
						</td>
					
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							登记店铺:
						</label>
					</td>
					<td class="value">
					     	 <select id="storeId" name="storeId" onchange="setSelectVal(this)" datatype="*" style="width: 150px">
								<option value="">---请选择---</option>
								<c:forEach items="${storeList}" var="store">
									<option value="${store.id }">${store.name}</option>
								</c:forEach>
							</select>
<%-- 							  <input id="storeId" name="storeId" hidden="true" value="${tPersonPage.storeId}"> --%>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">登记店铺</label>
						</td>
						<td>
						
						</td>
						<td>
						
						</td>
					</tr>
<!-- 					
				<tr>
				<td align="right">
						<label class="Validform_label">
							所在省市:
						</label>
					</td>
					<td class="value" colspan="3">
			<select id="provinceId" name="provinceId" datatype="*" onchange="getCitys(this.value)" style="width: 100px">
				<c:forEach items="${areaList}" var="area">
					<option value="${area.areaId }" <c:if test="${area.areaId==tPersonPage.provinceId}">selected="selected"</c:if>>${area.areaName}</option>
				</c:forEach>
			</select>
			<select id="cityId" name="cityId" datatype="*"  style="width: 100px" onchange="fitName()">
				<c:forEach items="${citysList}" var="citys">
					<option value="${citys.areaId }" <c:if test="${citys.areaId==tPersonPage.cityId}">selected="selected"</c:if>>${citys.areaName}</option>
				</c:forEach>
			</select>
							<label class="Validform_label" style="display: none;">请选择所在省市</label>
						</td>
					</tr>
 -->					
			</table>
		</t:formvalid>
 </body>
 <script type="text/javascript">
 $("#storeId").val("${tPersonPage.storeId}");
 
//  function setSelectVal(obj){
// 	  var id = $(obj).val();
// 	  var text =  $(obj).find("option[value='"+id+"']").text();
// 	  if(id==""){
// 		  text = "";
// 	  }
// 	  $(obj).next().val(text);
//  }
 </script>
