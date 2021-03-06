<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动激励</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
<!-- 七牛上传 start -->
<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
<script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
<script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
<!-- 七牛上传 end -->
<style type="text/css">
	.brand_tr{
		display: none;
	}
</style>
  <script type="text/javascript">
  $(function(){
	  var activityType = "${tFinActivityPage.activityType }";
	  $("#activityType").val(activityType).attr("disabled","disabled");
	  if(activityType=="2"){
		  $(".brand_tr").show();
		  $(".brandName").attr("disabled","disabled");
	  }
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false"  layout="table" action="tFinActivityController.do?doUpdate" callback="goBack"  tiptype="4">
					<input id="id" name="id" type="hidden" value="${tFinActivityPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tFinActivityPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tFinActivityPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tFinActivityPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tFinActivityPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tFinActivityPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tFinActivityPage.updateDate }">
					<input id="activityStatus" name="activityStatus" type="hidden" value="${tFinActivityPage.activityStatus }">
					<input id="status" name="status" type="hidden" value="${tFinActivityPage.status }">
					<input  name="brandId" type="hidden" value="${tFinActivityPage.brandId }">
					<input  name="newsId" type="hidden" value="${tFinActivityPage.newsId }">
					<input  name="brandName" type="hidden" value="${tFinActivityPage.brandName }">
					<input id="toUserType" name="toUserType" type="hidden" value="${tFinActivityPage.toUserType }">
					<input  name="activityType" type="hidden" value="${tFinActivityPage.activityType }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="4" height="40px">
						<h2>活动激励</h2>
					</td>
				</tr>
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							活动名称:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="activityName" name="activityName" type="text" datatype="*" style="width: 400px" class="inputxt" value="${tFinActivityPage.activityName }">
						<span style="color: red">*</span>
						<span class="Validform_checktip">建议不超过16个汉字</span>
						<label class="Validform_label" style="display: none;">活动名称</label>
					</td>
				</tr>
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								活动类别:
							</label>
						</td>
						<td class="value" width="400">
							<select id="activityType">
								<option value="1">单品</option>
								<option value="2">品牌</option>
								<option value="3">全馆</option>
							</select>
							<span style="color: red">*</span>
						</td>
						<td align="right" width="120">
							<label class="Validform_label">
								<span class="brand_tr">品牌名称:</span>
							</label>
						</td>
						<td class="value" >
							<div class="brand_tr">
						     	 ${tFinActivityPage.brandName}
						     </div>	 
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								开始时间:
							</label>
						</td>
						<td class="value">
							 <input id="startTime" name="startTime" type="text" datatype="*" style="width: 150px" class="Wdate"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:00'})"
						      						 value='<fmt:formatDate value='${tFinActivityPage.startTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
      						 <span style="color: red">*</span>    
      						 <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">开始时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								结束时间:
							</label>
						</td>
						<td class="value">
						  	<input id="endTime" name="endTime" type="text" datatype="*" style="width: 150px" class="Wdate"   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})"
				      			value='<fmt:formatDate value='${tFinActivityPage.endTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
			      			<span style="color: red">*</span>    
			      			<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束时间</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							活动规则:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="newsContext" name="newsContext" type="hidden" >
						<%--<script id="content"   type="text/plain" style="width:99%;">${tFinActivityPage.newsContext}</script>
							<label class="Validform_label" style="display: none;">活动内容</label>--%>
							<textarea id="newsContext" name="newsContext" rows="5" style="width: 95%" datatype="*">${tFinActivityPage.newsContext}</textarea>
					     	 <span style="color: red">*</span>
							<span class="Validform_checktip">活动规则说明</span>
							<label class="Validform_label" style="display: none;">活动规则</label>
					</td>
				</tr>
				<tr height="150">
					<td align="right" width="120">
						<label class="Validform_label">
							话题:
						</label>
					</td>
					<td class="value">
							<input id="newsTitle" name="newsTitle" value="${tFinActivityPage.newsTitle}" type="text" style="width: 150px" readonly="readonly" >
							<span style="color: red">*</span>
							<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findNews()" >
							<span class="icon-search l-btn-icon-left">选择</span></a>
							<label class="Validform_label" style="display: none;">话题</label>
					</td>
					<td align="right" width="120">
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value">
							<img alt="" src="${tFinActivityPage.coverPic}" id="coverPic" height="150">
							<label class="Validform_label" style="display: none;">封面图片</label>
					</td>
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
  <script src = "webpage/com/buss/bill/tFinActivity.js?v=1.10"></script>	