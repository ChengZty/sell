<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <link rel="stylesheet" href="plug-in/login/css/tags.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript">
  var source = "${source}";
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="baseActivityController.do?doAdd" callback="goBack"  tiptype="4">
					<input id="id" name="id" type="hidden" value="${baseActivityPage.id }">
					<input id="createName" name="createName" type="hidden" value="${baseActivityPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${baseActivityPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${baseActivityPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${baseActivityPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${baseActivityPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${baseActivityPage.updateDate }">
					<input id="retailerId" name="retailerId" type="hidden" value="${baseActivityPage.retailerId }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="contentId" name="contentId" type="hidden" value="${contentId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							活动名称:
						</label>
					</td>
					<td class="value" width="620">
				     	 <input id="activityName" name="activityName" type="text" style="width: 250px"  datatype="*" maxlength="30">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">活动名称</label>
					</td>
					<td align="right" rowspan="4" width="120;" >
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value" rowspan="4" >
						<div>
							<input type="file" name="templatePic_u" id="templatePic_u" />
							<span id="templatePicspan">
        					</span>
        				</div>
        				<div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
	        			</div>
					    <img id="prePic" src="" style="background-color: rgba(68, 111, 128, 0.67);"  width="252px" height="150px"  />
					    <input id="coverPic" name="coverPic" type="hidden" value="" datatype="*" errormsg="请上传封面图片" >
					    <div class="Validform_checktip">尺寸（750*446）</div>
					    <label class="Validform_label" style="display: none;">封面图片</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							子标题:
						</label>
					</td>
					<td class="value">
					     	 <input id="subTitle" name="subTitle" type="text" style="width: 250px"  datatype="*" maxlength="30">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">子标题</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							起始时间:
						</label>
					</td>
					<td class="value">
							   <input id="startTime" name="startTime" type="text" style="width: 150px" datatype="*"
					      						class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:00'})" 
>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起始时间</label>
						</td>
					</tr>
					<td align="right">
						<label class="Validform_label">
							结束时间:
						</label>
					</td>
					<td class="value">
							   <input id="endTime" name="endTime" type="text" style="width: 150px" datatype="*"
					      						class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})"
>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束时间</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							活动内容:
						</label>
					</td>
					<td class="value" colspan="3">
<!-- 					<input id="newsContext" name="newsContext" type="hidden" > -->
					<script id="content"   type="text/plain" style="width:99%;"></script>
							<label class="Validform_label" style="display: none;">活动内容</label></td>
				</tr>
				<tr height="40">
					<td class="upload" colspan="6" align="center">
						<a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="sub()" iconCls="icon-save">提交</a> 
						<a href="javascript:goBack()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">返回</a>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?v=1.02"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?v=1.02"> </script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/lang/zh-cn/zh-cn.js?v=1.02"></script>
  <script src = "webpage/com/buss/base/baseActivity.js?v=1.0"></script>		