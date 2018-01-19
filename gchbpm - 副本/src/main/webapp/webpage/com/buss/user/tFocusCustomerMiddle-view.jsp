<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>待发展顾客</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  function setSelectVal(obj){
	  var id = $(obj).val();
	  var text =  $(obj).find("option[value='"+id+"']").text();
	  if(id==""){
		  text = "";
	  }
	  $(obj).next().val(text);
  }
  
  $(document).ready(function(){
	  $("#vipLevelId").val("${tFocusCustomerPage.vipLevelId}");
  });
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tFocusCustomerMiddleController.do?doUpdate" tiptype="4">
		<input id="id" name="id" type="hidden" value="${tFocusCustomerPage.id }">
		<input id="createName" name="createName" type="hidden" value="${tFocusCustomerPage.createName }">
		<input id="createBy" name="createBy" type="hidden" value="${tFocusCustomerPage.createBy }">
		<input id="createDate" name="createDate" type="hidden" value="${tFocusCustomerPage.createDate }">
		<input id="updateName" name="updateName" type="hidden" value="${tFocusCustomerPage.updateName }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tFocusCustomerPage.updateBy }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tFocusCustomerPage.updateDate }">
		<input id="status" name="status" type="hidden" value="${tFocusCustomerPage.status }">
		
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="90">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value" width="265">
						     	 <input id="name" name="name" type="text" style="width: 140px"   datatype="*" value='${tFocusCustomerPage.name}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">姓名</label>
						</td>
						<td align="right" width="70">
							<label class="Validform_label">
								性别:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="sex" type="list"
										typeGroupCode="sex" defaultVal="${tFocusCustomerPage.sex}" hasLabel="false"  title="性别"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">性别</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								手机号:
							</label>
						</td>
						<td class="value">
						     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 140px" maxlength="11"  datatype="m" value='${tFocusCustomerPage.phoneNo}'>
							<span class="Validform_checktip">手机号为11位数字</span>
							<label class="Validform_label" style="display: none;">手机号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								生日:
							</label>
						</td>
						<td class="value">
						     	 <input name="birthday" class="Wdate" onClick="WdatePicker()" style="width: 140px" value="<fmt:formatDate value='${tFocusCustomerPage.birthday }' type="date" pattern="yyyy-MM-dd"/>"
				errormsg="生日格式不正确!" ignore="ignore"> <span class="Validform_checktip"></span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								顾客VIP等级:
							</label>
						</td>
						<td class="value">
						    <input id="zodiac" name="zodiac" type="text"   value='${tFocusCustomerPage.vipLevel}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">顾客VIP等级</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								登记地区:
							</label>
						</td>
						<td class="value">
							<input id="registerArea" name="registerArea" type="text" style="width: 140px"   value='${tFocusCustomerPage.registerArea}'>
							<span class="Validform_checktip">如广东省深圳市</span>
							<label class="Validform_label" style="display: none;">登记地区</label>
						</td>
					</tr>
					<%-- <tr>
						<td align="right">
							<label class="Validform_label">
								星座:
							</label>
						</td>
						<td class="value">
							<input id="constellation" name="constellation" type="text" style="width: 140px"   value='${tFocusCustomerPage.constellation}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">星座</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								生肖:
							</label>
						</td>
						<td class="value">
						     	 <input id="zodiac" name="zodiac" type="text" style="width: 140px"   value='${tFocusCustomerPage.zodiac}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">生肖</label>
						</td>
					</tr> --%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value" colspan="3">
						     	 <textarea id="remark" name="remark"  style="width: 350px;height: 80px;"  >${tFocusCustomerPage.remark}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
					<!-- <tr height="40">
						<td class="upload" colspan="6" align="center">
							<a href="#" class="easyui-linkbutton l-btn" id="btn"  iconCls="icon-save">提交</a> 
							<a href="javascript:backOfRetailer()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">返回</a>
						</td>
					</tr>	 -->
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/user/tFocusCustomer.js"></script>		