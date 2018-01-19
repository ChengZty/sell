<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>MQ通知</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tMqErrorController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tMqErrorPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								创建日期:
							</label>
						</td>
						<td class="value">
									  <input id="addTime" name="addTime" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tMqErrorPage.addTime}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								订单ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="orderId" name="orderId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tMqErrorPage.orderId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								订单类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="orderType" name="orderType" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tMqErrorPage.orderType}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								队列名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="queueName" name="queueName" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tMqErrorPage.queueName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">队列名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								方法名:
							</label>
						</td>
						<td class="value">
						     	 <input id="method" name="method" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tMqErrorPage.method}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">方法名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								mq消息:
							</label>
						</td>
						<td class="value">
						     	 <input id="mqMsg" name="mqMsg" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tMqErrorPage.mqMsg}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">mq消息</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								错误信息:
							</label>
						</td>
						<td class="value">
						     	 <input id="errorMsg" name="errorMsg" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tMqErrorPage.errorMsg}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">错误信息</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								处理状态:
							</label>
						</td>
						<td class="value">
						     	 <input id="dealStatus" name="dealStatus" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tMqErrorPage.dealStatus}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处理状态</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
