<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>店铺月目标</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tStoreMonTargetController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tStoreMonTargetPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tStoreMonTargetPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tStoreMonTargetPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tStoreMonTargetPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tStoreMonTargetPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tStoreMonTargetPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tStoreMonTargetPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="retailerId" name="retailerId" type="hidden" value="${tStoreMonTargetPage.retailerId }">
					<input id="storeId" name="storeId" type="hidden" value="${tStoreMonTargetPage.storeId }">
					<input id="targetMoney" name="targetMoney" type="hidden" value="${tStoreMonTargetPage.targetMoney }">
					<input id="targetMonth" name="targetMonth" type="hidden" value="${tStoreMonTargetPage.targetMonth}">
	<div onclick="aa()" style="height: 40px;font-weight: bold;font-size: medium;">
		&nbsp;&nbsp;店铺名称:${storeName}，月份:${tStoreMonTargetPage.targetMonth}
	</div>	
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<thead>
						<tr style="height: 40px;font-weight: bold;">
							<td align="right" width="120">
							<label class="Validform_label">
								导购姓名
							</label>
							<td>
							<label class="Validform_label">
								当月目标(元)
							</label>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${tStoreMonTargetPage.guideTargetList }" var="guide" varStatus="sts">
						<tr>
						<input type="hidden" name="guideTargetList[${sts.index}].id" value="${guide.id}"/>
						<input type="hidden" name="guideTargetList[${sts.index}].guideId" value="${guide.guideId}"/>
						<input type="hidden" name="guideTargetList[${sts.index}].guideName" value="${guide.guideName}"/>
						<td class="value" align="right">
								${guide.guideName}
						</td>
						<td class="value">
							<input  name="guideTargetList[${sts.index}].targetMoney" type="text" style="width: 90px" class="inputxt"  
						     	 value='${guide.targetMoney}' datatype="d" maxlength="10">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">当月目标</label>
						</td>
					</tr>
					</c:forEach>	
					</tbody>
			</table>
		</t:formvalid>
 </body>
