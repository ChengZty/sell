<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>支付方式</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script src="plug-in/Validform/js/common.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basePayTypeController.do?doAdd" tiptype="4">
					<input id="id" name="id" type="hidden" value="${basePayTypePage.id }">
					<input id="createName" name="createName" type="hidden" value="${basePayTypePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${basePayTypePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${basePayTypePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${basePayTypePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${basePayTypePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${basePayTypePage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付方式:
						</label>
					</td>
					<td class="value">
					     	 <input id="payType" name="payType" type="text" style="width: 150px"   datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">支付方式</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="payCode" name="payCode" type="text" style="width: 150px"   datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">支付编码</label>
						</td>
					</tr>
				<tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付费率:
						</label>
					</td>
					<td class="value">
					     	 <input id="rate" name="rate" type="text" style="width: 50px"  onkeypress="Public.input.moneyInput(this.value)" ><font color="red">‰</font>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">支付费率</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否启用:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="isOpen" type="list"
									typeGroupCode="sf_yn" defaultVal="${basePayTypePage.isOpen}" hasLabel="false" datatype="*" title="是否启用"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否启用</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付图标:
						</label>
					</td>
					<td class="value">
							    <input id="payIcon" name="payIcon" type="hidden" value="${basePayTypePage.payIcon}" datatype="*" errormsg="请上传封面图片" checktip="请上传封面图片">
							    <span id="file_uploadspan">
									<input type="file" name="file_upload" id="file_upload" />
									<img id="prePic" src="${basePayTypePage.payIcon}" style="background-color: rgba(68, 111, 128, 0.67)"  width="200px" height="150px" />
								</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">支付图标</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
 <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
 <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
 <script type="text/javascript" src="plug-in/tools/Map.js"></script>
  <script src = "webpage/com/buss/paytype/basePayType.js"></script>		