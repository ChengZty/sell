<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>云商实收分配定义表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" btnsub="btn"  layout="table"  callback="backList" action="tCommissionCloudController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${tCommissionCloudPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tCommissionCloudPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tCommissionCloudPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tCommissionCloudPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tCommissionCloudPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tCommissionCloudPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tCommissionCloudPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					
		<table style="width: 99%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr style="height: 40px;">
					<td colspan="2">
						<h3>实收分配定义</h3>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							品牌:
						</label>
					</td>
					<td class="value">
					     	<input id="brandName" name="brandName" type="text" style="width: 150px" type="text" disabled="disabled"  value="${tCommissionCloudPage.brandName}">
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							一级类目:
						</label>
					</td>
					<td class="value">
					 		<input id="topCategoryName" name="topCategoryName" type="text" disabled="disabled" style="width: 150px"  value="${tCommissionCloudPage.topCategoryName}">
						</td>
				</tr>		
				<tr>
					<td align="right">
						<label class="Validform_label">
							二级类目
						</label>
					</td>
					<td class="value">
					 		<input id="subCategoryName" name="subCategoryName"  type="text" disabled="disabled" style="width: 150px"  value="${tCommissionCloudPage.subCategoryName}">
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							三级类目:
						</label>
					</td>
					<td class="value">
							<input id="thridCategoryName" name="thridCategoryName" type="text" disabled="disabled"  style="width: 150px"  value="${tCommissionCloudPage.thridCategoryName}">
						</td>
				</tr>
						
				<tr>
					<td align="right">
						<label class="Validform_label">
							提成占比:
						</label>
					</td>
					<td class="value">
					     	<input id="commission" name="commission" type="text" style="width: 100px" type="text" disabled="disabled" value="${tCommissionCloudPage.commission }"><span style="color: red;">%</span>
					</td>
				</tr>
			</table>		
			<br/>
			
			<table style="width: 99%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">	
				<tr style="height: 30px;">
					<td colspan="6">
						<h4>分配规则</h4>
					</td>
				</tr>
				<tr align="center">
						<td>佣金分配类型</td>
						<td>系统占比<span style="color: red;">%</span></td>
						<td>零售商占比<span style="color: red;">%</span></td>
						<td>导购占比<span style="color: red;">%</span></td>
						<td>找帮手消费占比<span style="color: red;">%</span></td>
						<td>备注</td>
				</tr>
				<c:forEach var="obj" items="${tCommissionCloudPage.cinfoList}" step="1"  varStatus="status">
					<tr align="center">
						<td>
							${obj.ctypeName}
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].systemAccounting" style="width: 80px" value="${obj.systemAccounting}" type="text" disabled="disabled">
						</td>
						<td>
							<input type="text" name="cinfoList[${status.index}].storeAccounting" style="width: 80px" value="${obj.storeAccounting}" type="text" disabled="disabled">
						</td>
						<td>
							<input type="text" name="cinfoList[${status.index}].guideAccounting" style="width: 80px" value="${obj.guideAccounting}" type="text" disabled="disabled">
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].helperAccounting" style="width: 80px" value="${obj.helperAccounting}" type="text" disabled="disabled">
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].remark" value="${obj.remark}" type="text" disabled="disabled">
						</td>
					</tr>	
				</c:forEach>
				<!-- 
				<tr height="40">
					<td  colspan="6" align="center">
						<a href="javascript:back()" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回</a>
					</td>
				</tr>
				 -->
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/bill/tCommissionCloud.js"></script>		