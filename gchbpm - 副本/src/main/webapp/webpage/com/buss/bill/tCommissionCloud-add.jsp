<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>实收分配定义</title>
  <t:base type="jquery,easyui,tools,layer"></t:base>
  <script src="plug-in/Validform/js/common.js"></script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table"  callback="backList"  action="tCommissionCloudController.do?doAdd" beforeSubmit="checkAccounting()" tiptype="5">
					<input id="id" name="id" type="hidden" value="${tCommissionCloudPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tCommissionCloudPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tCommissionCloudPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tCommissionCloudPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tCommissionCloudPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tCommissionCloudPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tCommissionCloudPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="cid" name="cid" type="hidden" value="${tCommissionCloudPage.cid }">
					<input id="storeId" name="storeId" type="hidden" value="${storeId }">
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
					     	<input id="brandId" name="brandId" type="hidden" style="width: 150px" class="inputxt" >
					     	<input id="brandName" name="brandName" type="text" style="width: 150px" path="4" datatype="*" errormsg="请选择品牌!">
					     	<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()"><span class="l-btn-text icon-search l-btn-icon-left">选择</span></a>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">品牌</label>
						</td>
				</tr>
				<tr>
					<td align="right" width="170px;">
						<label class="Validform_label">
							一级分类:
						</label>
					</td>
					<td class="value">
					 		<input id="topCategoryName" name="topCategoryName" type="hidden" style="width: 150px" class="inputxt" >
					     	 <select id="topCategoryId" name="topCategoryId" onchange="setCatName(this,'topCategoryName'),getSubList(this.value,'subCategoryId')" datatype="*" errormsg="请选择一级分类!"  WIDTH="150px" style="width: 150px;">
								 <option value="">-请选择-</option>
								 <c:forEach var="obj" items="${catList}" >
										<option value="${obj.id}">${obj.name}</option>					 	
					              </c:forEach>
							</select>
					</td>
				</tr>		
				<tr>
					<td align="right">
						<label class="Validform_label">
							二级分类
						</label>
					</td>
					<td class="value">
					 		<input id="subCategoryName" name="subCategoryName" type="hidden" style="width: 150px" class="inputxt" >
					     	<select id="subCategoryId" name="subCategoryId" onchange="setCatName(this,'subCategoryName'),getSubList(this.value,'thridCategoryId')" datatype="*" errormsg="请选择二级分类!"  style="width: 150px;">
								<option value="">-请选择-</option>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">二级分类</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							三级分类:
						</label>
					</td>
					<td class="value">
							<input id="thridCategoryName" name="thridCategoryName" type="hidden" style="width: 150px" class="inputxt" >
					     	<select id="thridCategoryId" name="thridCategoryId"  WIDTH="100" onchange="setCatName(this,'thridCategoryName')"  errormsg="请选择三级级分类!" style="width: 150px;">
								<option value="">-请选择-</option>
							</select> 
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">三级分类</label>
						</td>
				</tr>
						
				<tr>
					<td align="right">
						<label class="Validform_label">
							提成占比:
						</label>
					</td>
					<td class="value">
					     	<input id="commission" name="commission" type="text" style="width: 100px" path="4"  datatype="d" errormsg="非法数字!"><span style="color: red;">%</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">提成占比</label>
						</td>
				</tr>
			</table>		
			<br/>
			
			<table style="width: 99%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<thead>
				<tr style="height: 30px;">
					<td colspan="6" >
						分配规则&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">提示:以下占比系数都是必录项（提成占比=系统占比+零售商占比+导购占比,找帮手占比是以导购收入为基础）,没有系数的请补录0</span>
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
				<c:forEach var="obj" items="${typeList}" step="1"  varStatus="status">
					<tr align="center">
						<td>
							<input type="hidden" name="cinfoList[${status.index}].ctype" value="${obj.typecode}" >
							${obj.typename}
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].systemAccounting" style="width: 80px" id="systemAccounting${status.index}"  onkeypress='Public.input.digitalInput()'>
						</td>
						<td>
							<input type="text" name="cinfoList[${status.index}].storeAccounting" style="width: 80px" id="storeAccounting${status.index}"   onkeypress='Public.input.digitalInput()'>
						</td>
						<td>
							<input type="text" name="cinfoList[${status.index}].guideAccounting" style="width: 80px" id="guideAccounting${status.index}" onkeypress='Public.input.digitalInput()'>
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].helperAccounting" style="width: 80px" id="helperAccounting${status.index}" onkeypress='Public.input.digitalInput()'>
						</td>
						<td >
							<input type="text" name="cinfoList[${status.index}].remark"  >
						</td>
					</tr>	
				</c:forEach>
				</tbody>
				<!-- 
				<tfoot>
				<tr height="40">
					<td  colspan="6" align="center">
						<a href="#" class="easyui-linkbutton" id="btn" iconCls="icon-save">保存</a>
						<a href="javascript:back()" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回</a>
					</td>
				</tr>
				</tfoot>
				 -->
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/bill/tCommissionCloud.js?v=1.03"></script>		