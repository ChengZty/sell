<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>云商实收分配定义表</title>
  <t:base type="jquery,easyui,tools,layer"></t:base>
  <script src="plug-in/Validform/js/common.js"></script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"  action="tCommissionCloudController.do?doUpdate" beforeSubmit="checkAccounting()" tiptype="5" >
					<input id="id" name="id" type="hidden" value="${tCommissionCloudPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tCommissionCloudPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tCommissionCloudPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tCommissionCloudPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tCommissionCloudPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tCommissionCloudPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tCommissionCloudPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
			     	<input id="storeId" name="storeId" type="hidden" style="width: 150px"value="${tCommissionCloudPage.storeId}">
					
		<table style="width: 99%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="2" style="height: 40px">
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
					     	<input id="brandId" name="brandId" type="hidden" style="width: 150px"value="${tCommissionCloudPage.brandId}">
					     	<input id="brandName" name="brandName" style="width: 150px" readonly="readonly" value="${tCommissionCloudPage.brandName}">
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							一级类目:
						</label>
					</td>
					<td class="value">
							<input id="topCategoryId" name="topCategoryId" type="hidden" style="width: 150px"value="${tCommissionCloudPage.topCategoryId}">
					 		<input id="topCategoryName" name="topCategoryName" type="hidden"  style="width: 150px"value="${tCommissionCloudPage.topCategoryName}">${tCommissionCloudPage.topCategoryName}
						</td>
				</tr>		
				<tr>
					<td align="right">
						<label class="Validform_label">
							二级类目
						</label>
					</td>
					<td class="value">
							<input id="subCategoryId" name="subCategoryId" type="hidden" style="width: 150px"value="${tCommissionCloudPage.subCategoryId}">
					 		<input id="subCategoryName" name="subCategoryName" type="hidden"   style="width: 150px"value="${tCommissionCloudPage.subCategoryName}">${tCommissionCloudPage.subCategoryName}
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							三级类目:
						</label>
					</td>
					<td class="value">
							<input id="thridCategoryId" name="thridCategoryId" type="hidden" style="width: 150px"value="${tCommissionCloudPage.thridCategoryId}">
							<input id="thridCategoryName" name="thridCategoryName" type="hidden" readonly="readonly" style="width: 150px"value="${tCommissionCloudPage.thridCategoryName}">${tCommissionCloudPage.thridCategoryName}
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							提成占比:
						</label>
					</td>
					<td class="value">
					     	<input id="commission" name="commission" type="text" style="width: 100px" value="${tCommissionCloudPage.commission }"><span style="color: red;">%</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">提成占比</label>
						</td>
				</tr>
			</table>		
			<br/>
			
			<table style="width: 99%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">	
				<thead>
				<tr>
					<td colspan="6">
						<h>分配规则</h>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">提示:以下占比系数都是必录项（提成占比=系统占比+零售商占比+导购占比,找帮手占比是以导购收入为基础）,没有系数的请补录0</span>
					</td>
				</tr>
					<tr align="center">
						<td>佣金分配类型</td>
						<td >系统占比<span style="color: red;">%</span></td>
						<td>零售商占比<span style="color: red;">%</span></td>
						<td >导购占比<span style="color: red;">%</span></td>
						<td >找帮手占比<span style="color: red;">%</span></td>
						<td >备注</td>
				</tr>
				</thead>
				<tbody id="tb_body">
				<c:forEach var="obj" items="${tCommissionCloudPage.cinfoList}" step="1"  varStatus="status">
					<tr align="center">
						<td>
							<input type="hidden" name="cinfoList[${status.index}].id" value="${obj.id}">
							<input type="hidden" name="cinfoList[${status.index}].cid" value="${obj.cid}">
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
							<input type="text" name="cinfoList[${status.index}].systemAccounting" style="width: 80px" id="systemAccounting${status.index}" onkeypress='Public.input.digitalInput()' value="${obj.systemAccounting}">
						</td>
						<td>
							<input type="text" name="cinfoList[${status.index}].storeAccounting" style="width: 80px" id="storeAccounting${status.index}"  onkeypress='Public.input.digitalInput()' value="${obj.storeAccounting}">
						</td>
						<td>
							<input type="text" name="cinfoList[${status.index}].guideAccounting" style="width: 80px" id="guideAccounting${status.index}" onkeypress='Public.input.digitalInput()' value="${obj.guideAccounting}">
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].helperAccounting" style="width: 80px" id="helperAccounting${status.index}" onkeypress='Public.input.digitalInput()' value="${obj.helperAccounting}">
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].remark" value="${obj.remark}" >
						</td>
					</tr>	
				</c:forEach>
				</tbody>
				<tfoot>
				<!-- 
				<tr height="40">
					<td  colspan="6" align="center">
						<a href="#" class="easyui-linkbutton" id="btn" iconCls="icon-save">保存</a>
						<a href="javascript:back()" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回</a>
					</td>
				</tr>
				 -->
				</tfoot>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/bill/tCommissionCloud.js?v=1.02"></script>		