<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_sms_send</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSmsSendController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tSmsSendPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								创建人名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="createName" name="createName" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSmsSendPage.createName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建人名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								创建人登录名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="createBy" name="createBy" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSmsSendPage.createBy}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建人登录名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								创建日期:
							</label>
						</td>
						<td class="value">
									  <input id="createDate" name="createDate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tSmsSendPage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								更新人名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="updateName" name="updateName" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSmsSendPage.updateName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">更新人名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								更新人登录名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="updateBy" name="updateBy" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSmsSendPage.updateBy}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">更新人登录名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								更新日期:
							</label>
						</td>
						<td class="value">
									  <input id="updateDate" name="updateDate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tSmsSendPage.updateDate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">更新日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								状态:
							</label>
						</td>
						<td class="value">
						     	 <input id="status" name="status" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSmsSendPage.status}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">状态</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								手机号码:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="phone" style="width:600px;" class="inputxt" rows="6" name="phone">${tSmsSendPage.phone}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">手机号码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								短信模板ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="templateId" name="templateId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSmsSendPage.templateId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信模板ID</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								短信模板名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="templateName" name="templateName" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSmsSendPage.templateName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信模板名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发送内容:
							</label>
						</td>
						<td class="value">
						     	 <input id="sendContent" name="sendContent" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSmsSendPage.sendContent}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送内容</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								发送时间:
							</label>
						</td>
						<td class="value">
									  <input id="sendTime" name="sendTime" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tSmsSendPage.sendTime}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								时间发送方式，0即时发送，1定时发送:
							</label>
						</td>
						<td class="value">
						     	 <input id="sendTimeType" name="sendTimeType" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSmsSendPage.sendTimeType}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">时间发送方式，0即时发送，1定时发送</label>
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
