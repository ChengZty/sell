<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>零售商实收分配定义表</title>
<%--   <t:base type="jquery,easyui,tools"></t:base> --%>
<!--   <script type="text/javascript"> -->
<!--   function tipmsg(data) { -->
<!-- 	  //$.messager.alert('提示信息', data.msg); -->
<!-- 	  layer.alert(data.msg);  -->
<!--   } -->
<!--   </script> -->
 </head>
 <body>
<t:formvalid formid="formobj" dialog="false"  btnsub="btn" layout="table"	 callback="tipmsg" action="tCommissionStoreRuleController.do?doAddOrUpdate" tiptype="5">
					
		<table style="width: 99%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">	
				<tr>
					<td colspan="6">
						<span style="color:red">提示:以下占比系数都是必录项,没有系数的请补录0</span>
					</td>
				</tr>
			    <tr align="center">
						<td>类型</td>
						<td >系统占比<span style="color: red;">%</span></td>
						<td>零售商占比<span style="color: red;">%</span></td>
						<td >导购占比<span style="color: red;">%</span></td>
						<td >找帮手消费占比<span style="color: red;">%</span></td>
						<td >备注</td>
				</tr>
				<c:forEach var="obj" items="${dataList}" step="1"  varStatus="status">
					<tr align="center">
						<td>
							<input type="hidden" name="cinfoList[${status.index}].id" value="${obj.id}">
							<input type="hidden" name="cinfoList[${status.index}].sid" value="${obj.sid}">
							<input id="createName" name="cinfoList[${status.index}].createName" type="hidden" value="${obj.createName }">
							<input id="createBy" name="cinfoList[${status.index}].createBy" type="hidden" value="${obj.createBy }">
							<input id="createDate" name="cinfoList[${status.index}].createDate" type="hidden" value="${obj.createDate }">
							<input id="updateName" name="cinfoList[${status.index}].updateName" type="hidden" value="${obj.updateName }">
							<input id="updateBy" name="cinfoList[${status.index}].updateBy" type="hidden" value="${obj.updateBy }">
							<input id="updateDate" name="cinfoList[${status.index}].updateDate" type="hidden" value="${obj.updateDate }">
							<input id="status" name="cinfoList[${status.index}].status" type="hidden" value="${obj.status}">
							<input type="hidden" name="cinfoList[${status.index}].ctype" value="${obj.ctype}">
							${obj.ctypeName}
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].systemAccounting" id="systemAccounting${status.index}"  value="${obj.systemAccounting}" datatype="d" errormsg="非法数字!">
						</td>
						<td>
							<input type="text" name="cinfoList[${status.index}].storeAccounting" id="storeAccounting${status.index}" value="${obj.storeAccounting}" datatype="d" errormsg="非法数字!">
						</td>
						<td>
							<input type="text" name="cinfoList[${status.index}].guideAccounting" id="guideAccounting${status.index}" value="${obj.guideAccounting}" datatype="d" errormsg="非法数字!">
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].helperAccounting" id="helperAccounting${status.index}" value="${obj.helperAccounting}" datatype="d" errormsg="非法数字!">
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].remark" value="${obj.remark}">
						</td>
					</tr>	
				</c:forEach>
				<tr height="40">
					<td  colspan="6" align="center">
						<a href="#" class="easyui-linkbutton" id="btn" iconCls="icon-save">保存</a>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
