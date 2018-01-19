<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>优惠券</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  $(function(){
// 	  	$("#topCategoryId option[value='${tTicketInfoPage.topCategoryId}']").attr("selected",true);
	  	$("#topCategoryId").val('${tTicketInfoPage.topCategoryId}');
	  	$("#subCategoryId").val('${tTicketInfoPage.subCategoryId}');
	  	$("#thridCategoryId").val('${tTicketInfoPage.thridCategoryId}');
		
		var type="${tTicketInfoPage.type}";
		if("1"==type){//红包
			$("#span1").show();
		}else if("2"==type){//优惠券
			$("#span2").show();
		}
		var usingRange = "${tTicketInfoPage.usingRange}";
		if(usingRange=="2"){//品牌
			  $("#brand_tr").show();
		}
		if(usingRange=="4"){//分类
			  $("#cat_tr").show();
		}
});
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tTicketInfoController.do?doUpdate" beforeSubmit="checkUsingRange" tiptype="4" >
					<input id="id" name="id" type="hidden" value="${tTicketInfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tTicketInfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tTicketInfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tTicketInfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tTicketInfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tTicketInfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tTicketInfoPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tTicketInfoPage.status }">
					<input id="batchNo" name="batchNo" type="hidden" value="${tTicketInfoPage.batchNo }">
					<input id="auditorId" name="auditorId" type="hidden" value="${tTicketInfoPage.auditorId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tTicketInfoPage.retailerId }">
					<input id="sheetSent" name="sheetSent" type="hidden" value="${tTicketInfoPage.sheetSent }">
					<input id="sheetUsed" name="sheetUsed" type="hidden" value="${tTicketInfoPage.sheetUsed }">
					<input id="sheetRemain" name="sheetRemain" type="hidden" value="${tTicketInfoPage.sheetRemain }">
					<input id="ticketStatus" name="ticketStatus" type="hidden" value="${tTicketInfoPage.ticketStatus }">
					<input id="discount" name="discount" type="hidden" value="${tTicketInfoPage.discount }">
					<input id="topCategoryName" name="topCategoryName" type="hidden" value="${tTicketInfoPage.topCategoryName }">
					<input id="subCategoryName" name="subCategoryName" type="hidden" value="${tTicketInfoPage.subCategoryName }">
					<input id="thridCategoryName" name="thridCategoryName" type="hidden" value="${tTicketInfoPage.thridCategoryName }">
					<input id="brandId" name="brandId" type="hidden" value="${tTicketInfoPage.brandId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="130px">
							<label class="Validform_label">
								批次号:
							</label>
						</td>
						<td class="value" colspan="3">
						     	 ${tTicketInfoPage.batchNo}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								券名:
							</label>
						</td>
						<td class="value" colspan="3">
						     	 <input id="ticketName" name="ticketName" type="text" style="width: 350px" class="inputxt" maxlength="10"  value='${tTicketInfoPage.ticketName}'>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">券名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
						<label class="Validform_label">
							券类型:
						</label>
						</td>
						<td class="value">
							<t:dictSelect field="type" id="type" datatype="*" defaultVal="${tTicketInfoPage.type }" hasLabel="false" typeGroupCode="tkt_type" type="select"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类型</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								使用范围:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="usingRange" id="usingRange" defaultVal="${tTicketInfoPage.usingRange }" datatype="*" hasLabel="false" typeGroupCode="using_range" type="select" readonly="readonly"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">使用范围</label>
						</td>
					</tr>
					<tr id="brand_tr" style="display: none">
						<td align="right">
							<label class="Validform_label">
								品牌名称:
							</label>
						</td>
						<td class="value" colspan="3">
						     	 <input id="brandName" name="brandName" type="text" style="width: 150px" class="inputxt"  
										       value='${tTicketInfoPage.brandName}'>
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
									<c:forEach var="topCategory" items="${topCategoryList}">
									<option value="${topCategory[0] }">${topCategory[1]}</option>
									</c:forEach>
								</select>
								<select name="subCategoryId"  id="subCategoryId" onchange="getUnderCategories('subCategoryId',this.value,'3','thridCategoryId')" 
								<c:if test="${empty tTicketInfoPage.topCategoryId }"> style="display: none;" </c:if>
								>
									<c:if test="${not empty tTicketInfoPage.topCategoryId }">
										<option value="">---请选择---</option>
										<c:forEach var="subCategory" items="${subCategoryList}">
										<option value="${subCategory[0] }">${subCategory[1]}</option>
										</c:forEach>
									</c:if>
								</select>
								<select name="thridCategoryId"  id="thridCategoryId" onchange="fillThirdCatName('thridCategoryId',this.value)"
								<c:if test="${empty tTicketInfoPage.subCategoryId }"> style="display: none;" </c:if>
								 >
									<c:if test="${not empty tTicketInfoPage.subCategoryId }">
										<option value="">---请选择---</option>
										<c:forEach var="thirdCategory" items="${thirdCategoryList}">
										<option value="${thirdCategory[0] }">${thirdCategory[1]}</option>
										</c:forEach>
									</c:if>
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
					     	 <input id="faceValue" name="faceValue" type="text" value='${tTicketInfoPage.faceValue}' style="width: 50px" class="inputxt" datatype="n">
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
					     	 <input id="leastMoney" name="leastMoney" type="text" value='${tTicketInfoPage.leastMoney}' style="width: 50px" class="inputxt" datatype="n">
					     	 元使用
						</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">满额</label>
					</td>
				</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								起始时间:
							</label>
						</td>
						<td class="value">
									  <input id="beginTime" name="beginTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tTicketInfoPage.beginTime}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起始时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								结束时间:
							</label>
						</td>
						<td class="value">
									  <input id="endTime" name="endTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tTicketInfoPage.endTime}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								总张数:
							</label>
						</td>
						<td class="value" 
						<c:if test="${empty isView}">
						colspan="3"
						</c:if>
						>
						     <input id="sheetTotal" name="sheetTotal" type="text" style="width: 50px" maxlength="5" class="inputxt"  value='${tTicketInfoPage.sheetTotal}'>
							<span class="Validform_checktip">不超过99999张</span>
							<label class="Validform_label" style="display: none;">总张数</label>
						</td>
						<c:if test="${isView =='1'}">
							<td align="right">
								<label class="Validform_label">
									已分配张数:
								</label>
							</td>
							<td>
								<input id="sheetSent" name="sheetSent" type="text" style="width: 50px" class="inputxt"  value='${tTicketInfoPage.sheetSent}'>
							</td>
						</c:if>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								使用说明:
							</label>
						</td>
						<td class="value" colspan="3">
						<textarea style="width: 98%;height: 100px" name="remark" datatype="*">${tTicketInfoPage.remark}</textarea>
<%-- 						     	 <input id="remark" name="remark" type="text" style="width: 250px" class="inputxt"  value='${tTicketInfoPage.remark}'> --%>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">使用说明</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/ticket/tTicketInfo.js?v=1.02"></script>		
