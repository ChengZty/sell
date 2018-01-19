<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>优惠券</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tTicketInfoController.do?doAdd" tiptype="4" beforeSubmit="checkUsingRange">
					<input id="id" name="id" type="hidden" value="${tTicketInfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tTicketInfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tTicketInfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tTicketInfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tTicketInfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tTicketInfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tTicketInfoPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="batchNo" name="batchNo" type="hidden" value="${tTicketInfoPage.batchNo }">
					<input id="auditorId" name="auditorId" type="hidden" value="${tTicketInfoPage.auditorId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${retailerId }">
					<input id="retailerType" name="retailerType" type="hidden" value="${retailerType }">
					<input id="ticketStatus" name="ticketStatus" type="hidden" value="1">
					<input id="discount" name="discount" type="hidden" value="1">
					<input id="topCategoryName" name="topCategoryName" type="hidden" >
					<input id="subCategoryName" name="subCategoryName" type="hidden" >
					<input id="thridCategoryName" name="thridCategoryName" type="hidden" >
					<input id="brandId" name="brandId" type="hidden" >
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" >
				<tr>
					<td align="right" width="130px">
						<label class="Validform_label">
							券名:
						</label>
					</td>
					<td class="value" colspan="3">
					     	 <input id="ticketName" name="ticketName" type="text" style="width: 350px" class="inputxt" maxlength="10" datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">券名</label>
						</td>
				</tr>
				<c:if test="${empty retailerId }">
				<tr>
					<td align="right">
						<label class="Validform_label">
							云商:
						</label>
					</td>
					<td class="value" colspan="3">
					     	<input id="retailerName"  type="text" style="width: 150px" readonly="readonly" datatype="*">
						<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findClouds()" ><span class="icon-search l-btn-icon-left">选择</span></a>
						<label class="Validform_label" style="display: none;">云商</label>
						</td>
				</tr>
				</c:if>
				<tr>
					<td align="right">
						<label class="Validform_label">
							券类型:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="type" id="type" datatype="*" hasLabel="false" typeGroupCode="tkt_type" type="select"></t:dictSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">类型</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							使用范围:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="usingRange" id="usingRange" datatype="*" hasLabel="false" typeGroupCode="using_range" type="select"></t:dictSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">使用范围</label>
					</td>
				</tr>
				<tr id="brand_tr" style="display: none">
					<td align="right">
							<label class="Validform_label" width="100">
								品牌名称:
							</label>
					</td>
					<td class="value" colspan="3">
						<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" >
						<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" ><span class="icon-search l-btn-icon-left">选择</span></a>
						<label class="Validform_label" style="display: none;">品牌名称</label>
					</td>
				</tr>
				<%-- <tr id="cat_tr" style="display: none">
					<td align="right">
						<label class="Validform_label">
							一级/二级/三级分类:
						</label>
					</td>
					<td class="value" colspan="3">
					     	<select name="topCategoryId" id="topCategoryId" onchange="getUnderCategories('topCategoryId',this.value,'2','subCategoryId')" >
								<option value="">---请选择---</option>
								<c:forEach var="category" items="${categoryList}">
								<option value="${category[0] }">${category[1]}</option>
								</c:forEach>
							</select>
							<select name="subCategoryId"  id="subCategoryId" onchange="getUnderCategories('subCategoryId',this.value,'3','thridCategoryId')" style="display: none;">
							</select>
							<select name="thridCategoryId"  id="thridCategoryId" onchange="fillThirdCatName('thridCategoryId',this.value)" style="display: none;">
							</select>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							面值:
						</label>
					</td>
					<td class="value" width="300px">
					     	 <input id="faceValue" name="faceValue" type="text" style="width: 50px" class="inputxt" datatype="n">元
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">面值</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							使用条件:
						</label>
					</td>
					<td class="value">
						<span id="span1" style="display: none">无条件使用</span>
						<span id="span2" style="display: none">
					     	 满
					     	 <input id="leastMoney" name="leastMoney" type="text" style="width: 50px" class="inputxt" datatype="n">
					     	 元使用
						</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">满减金额</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							起始时间:
						</label>
					</td>
					<td class="value">
							   <input id="beginTime" name="beginTime" type="text" style="width: 150px" datatype="*"
					      						class="Wdate" onClick="WdatePicker()"
>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起始时间</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							结束时间:
						</label>
					</td>
					<td class="value">
							   <input id="endTime" name="endTime" type="text" style="width: 150px" datatype="*"
					      						class="Wdate" onClick="WdatePicker()" 
>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束时间</label>
						</td>
					</tr>
				<tr>
					<td align="right" >
						<label class="Validform_label">
							总张数:
						</label>
					</td>
					<td class="value" colspan="3">
					     	 <input id="sheetTotal" name="sheetTotal" type="text" style="width: 50px" maxlength="5" class="inputxt" datatype="n">
							<span class="Validform_checktip">不超过99999张</span>
							<label class="Validform_label" style="display: none;">总张数</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							使用说明:
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea style="width: 98%;height: 100px" name="remark" datatype="*" maxlength="500"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">使用说明</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/ticket/tTicketInfo.js?v=1.02"></script>		
