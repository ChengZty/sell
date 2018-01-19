<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>短信发送</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!--   <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script> -->
  <script type="text/javascript">
  //编写自定义JS代码
	function sub(){
		$("#formobj").submit();
	}
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSmsSendController.do?doAdd" tiptype="2">
					<input id="id" name="id" type="hidden" value="${tSmsSendPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSmsSendPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSmsSendPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSmsSendPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSmsSendPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSmsSendPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSmsSendPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="templateId" name="templateId" type="hidden" value="${tSmsSendPage.templateId }">
					<input id="batchNo" name=""batchNo"" type="hidden" value="${batchNo }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="3" align="center"><span><p>短信发送</p></span></td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							手机号码
						</label>
					</td>
					<td class="value">
							 <input id="phone" name="phone" type="text" style="width: 300px" class="inputxt" datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">手机号码</label>
						</td>
					<td>
						<a href="#" class="easyui-linkbutton l-btn" onclick="ImportXls()">批量导入号码</a> 
					</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							短信模板
						</label>
					</td>
					<td class="value">
<!-- 					<input type="text" id="templateName"> -->
<%-- 						<t:comboBox url="tSmsSendTemplateController.do?combox" name="templateName" text="templateName" id="id"></t:comboBox> --%>
						<select name="templateName" onchange="changeActType(this.value)" style="width: 300px">
							<option value="0">--请选择模板--</option>
							<c:forEach items="${departList}" var="depart">
								<option value="${depart.id }" <%-- <c:if test="${depart.id==jgDemo.depId}">selected="selected"</c:if> --%>>${depart.templateName}</option>
							</c:forEach>
						</select>
<!-- 					     	 <select name="templateName" onchange="changeActType(this.value)" style="width: 300px"> -->
<!-- 							<option value="1">1</option> -->
<!-- 							<option value="2">2</option> -->
<!-- 							<option value="3">3</option> -->
<!-- 						</select> -->
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信模板</label>
						</td>
						<td>短信模板设置</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发送内容
						</label>
					</td>
					<td class="value">
					     	<textarea class="inputxt" rows="6" id="sendContent" name="sendContent" style="width: 300px" datatype="*"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送内容</label>
						</td>
					<td></td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发送时间
						</label>
					</td>
					<td class="value">
					     	  <label><input id="sendTimeType0" name="sendTimeType" type="radio" checked="checked" value="0" onclick="checkType(this.value)">即时发送</label>
					     	 <label><input id="sendTimeType1" name="sendTimeType" type="radio"  value="1" onclick="checkType(this.value)">定时发送</label>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">时间发送方式，0即时发送，1定时发送</label>
						</td>
						<td></td>
					</tr>
				<tr>
					<td align="right"></td>
					<td class="value" >
							   <input id="sendTime" name="sendTime" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送时间</label>
						</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="3">
						<div style="margin-left:30px;">
						说明：
						<br/>
						您可以选择手动输入号码，也可批量导入手机号码；
						<br/>
						手动输入号码：多个号码请使用","将多个号码分隔开来；
						<br/>
						批量导入号码：支持（.???）文件的导入
						</div>
					</td>
				</tr>
				<tr height="40">
					<td class="upload" colspan="3" align="center">
						<a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="sub()" iconCls="icon-save">发送</a> 
						<a href="javascript:goBack()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">退出</a>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
