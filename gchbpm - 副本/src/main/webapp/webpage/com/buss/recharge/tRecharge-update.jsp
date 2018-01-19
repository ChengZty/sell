<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>充值</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tRechargeController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tRechargePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tRechargePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tRechargePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tRechargePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tRechargePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tRechargePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tRechargePage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<c:if test="${not empty tRechargePage.orderNo}">
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								充值单号:
							</label>
						</td>
						<td class="value">
						     	 <input id="orderNo" name="orderNo" type="text" style="width: 150px" class="inputxt" value='${tRechargePage.orderNo}'>
						</td>
					</tr>
				</c:if>
					<tr>
						<td align="right">
							<label class="Validform_label">
								手机号:
							</label>
						</td>
						<td class="value">
						     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px" class="inputxt"   value='${tRechargePage.phoneNo}'>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="money" name="money" type="text" style="width: 150px" class="inputxt" value='${tRechargePage.money}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								冲值时间:
							</label>
						</td>
						<td class="value">
						     	 <input id="payTime" name="payTime" type="text" style="width: 150px" class="inputxt"  
						     	 value='<fmt:formatDate value='${tRechargePage.payTime }' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								支付方式:
							</label>
						</td>
						<td class="value">
						     	 <c:if test="${tRechargePage.payType =='1'}">支付宝</c:if>
						     	 <c:if test="${tRechargePage.payType =='2'}">微信</c:if>
						     	 <c:if test="${tRechargePage.payType =='3'}">平台</c:if>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						     	 <textarea rows="5" name="remark" style="width: 98%" datatype="*" maxlength="300">${tRechargePage.remark}</textarea>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
