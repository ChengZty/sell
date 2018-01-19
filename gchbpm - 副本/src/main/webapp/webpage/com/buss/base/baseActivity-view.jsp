<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  $(function(){
	  $("#formobj input,select,a").attr("disabled","disabled");
  })
  </script>
 </head>
 <body>
 <div style="margin: auto;max-width: 1280px">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="baseActivityController.do?doUpdate" callback="goBack" beforeSubmit="setContent()" tiptype="4">
					<input id="id" name="id" type="hidden" value="${baseActivityPage.id }">
					<input id="retailerId" name="retailerId" type="hidden" value="${baseActivityPage.retailerId }">
					<input id="userType" name="userType" type="hidden" value="${baseActivityPage.userType }">
					<input id="contentId" name="contentId" type="hidden" value="${baseActivityPage.contentId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								活动名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="activityName" name="activityName" type="text" style="width: 150px"  datatype="*"  value='${baseActivityPage.activityName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">活动名称</label>
						</td>
						<td align="right" rowspan="4" width="120px;" >
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value" rowspan="4" >
					    <img id="prePic" src="${baseActivityPage.coverPic}" style="background-color: rgba(68, 111, 128, 0.67);"  width="252px" height="150px"  />
					    <input id="coverPic" name="coverPic" type="hidden" value="${baseActivityPage.coverPic}" datatype="*" errormsg="请上传封面图片" >
					</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							子标题:
						</label>
					</td>
					<td class="value">
					     	 <input id="subTitle" name="subTitle" type="text" style="width: 250px"  datatype="*" maxlength="30" value="${baseActivityPage.subTitle }">
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
							   <input id="startTime" name="startTime" type="text" style="width: 150px" datatype="*" value="<fmt:formatDate value='${baseActivityPage.startTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
							   <input id="endTime" name="endTime" type="text" style="width: 150px" datatype="*" value="<fmt:formatDate value='${baseActivityPage.endTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"
					      						class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})"
>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束时间</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							资讯正文:
						</label>
					</td>
					<td class="value" colspan="3">
							${baseActivityPage.newsContext}
					</td>
				</tr>
			</table>
		</t:formvalid>
</div>		
 </body>
