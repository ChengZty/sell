<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>会员资格</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <link rel="stylesheet" href="plug-in/login/css/tags.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript">

  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tMembershipController.do?doAdd" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tMembershipPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tMembershipPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tMembershipPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tMembershipPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tMembershipPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tMembershipPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tMembershipPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"
								       maxlength="30"        datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							头像:
						</label>
					</td>
					<td class="value">
					 		<div>
								<input type="file" name="templatePic_u" id="templatePic_u" />
	        				</div>
					     	 <div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
					     	 <img id="prePic" src="${tMembershipPage.headPic }" style="background-color: rgba(68, 111, 128, 0.67)"  width="100px" height="100px"    />
					     	 <input id="headPic" name="headPic" type="hidden" datatype="*" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">头像</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							折扣:
						</label>
					</td>
					<td class="value">
					     	 <input id="discount" name="discount" type="text" style="width: 150px" class="inputxt"    datatype="d">
							<span class="Validform_checktip">折扣小于1</span>
							<label class="Validform_label" style="display: none;">折扣</label>
						</td>
						</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							面额起始值:
						</label>
					</td>
					<td class="value">
					     	 <input id="faceValueStart" name="faceValueStart" type="text" style="width: 150px" class="inputxt" value="0"
					     	 datatype="n">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">面额起始值</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							面额结束值:
						</label>
					</td>
					<td class="value">
					     	 <input id="faceValueEnd" name="faceValueEnd" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">面额结束值</label>
						</td>
						</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
					     	 <textarea style="width: 98%;height: 100px" id="remark" name="remark" maxlength="300"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
						</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/member/tMembership.js"></script>		