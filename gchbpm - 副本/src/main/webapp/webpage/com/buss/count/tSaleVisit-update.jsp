<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>售后回访</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tSaleVisitController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tSaleVisitPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSaleVisitPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSaleVisitPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSaleVisitPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSaleVisitPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSaleVisitPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSaleVisitPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tSaleVisitPage.status }">
					<input id="guideId" name="guideId" type="hidden" value="${tSaleVisitPage.guideId }">
					<input id="userId" name="userId" type="hidden" value="${tSaleVisitPage.userId }">
					<input id="orderId" name="orderId" type="hidden" value="${tSaleVisitPage.orderId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tSaleVisitPage.retailerId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								导购姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="guideName" name="guideName" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSaleVisitPage.guideName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">导购姓名</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								顾客姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="userName" name="userName" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSaleVisitPage.userName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">顾客姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								支付时间:
							</label>
						</td>
						<td class="value">
									  <input id="payTime" name="payTime" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tSaleVisitPage.payTime}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">支付时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								订单号:
							</label>
						</td>
						<td class="value">
						     	 <input id="orderNo" name="orderNo" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSaleVisitPage.orderNo}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								回访状态:
							</label>
						</td>
						<td class="value">
						     	 <input id="visitStatus" name="visitStatus" type="text" style="width: 150px" class="inputxt"  
									               datatype="*"
										       value='${tSaleVisitPage.visitStatus}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">回访状态</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								当天致谢:
							</label>
						</td>
						<td class="value">
									  <input id="visitOne" name="visitOne" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tSaleVisitPage.visitOne}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">当天致谢</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								收货关怀:
							</label>
						</td>
						<td class="value">
									  <input id="visitTwo" name="visitTwo" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tSaleVisitPage.visitTwo}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">收货关怀</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								注意事项:
							</label>
						</td>
						<td class="value">
									  <input id="visitThr" name="visitThr" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tSaleVisitPage.visitThr}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">注意事项</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								使用关怀:
							</label>
						</td>
						<td class="value">
									  <input id="visitFou" name="visitFou" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tSaleVisitPage.visitFou}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">使用关怀</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								再次推荐:
							</label>
						</td>
						<td class="value">
									  <input id="visitFiv" name="visitFiv" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tSaleVisitPage.visitFiv}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">再次推荐</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								维护进度:
							</label>
						</td>
						<td class="value">
						     	 <input id="visitProcess" name="visitProcess" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSaleVisitPage.visitProcess}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">维护进度</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
