<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>话题分类</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
<!-- 	<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script> -->
	<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
	<!-- 七牛上传 start -->
  	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
  	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	  <script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	  <script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	  <!-- 七牛上传 end -->
	<script type="text/javascript">
		var domain = '${domain}';//七牛domain
		var directory = '${directory}';//目录
	</script>
</head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tTemplateTypeController.do?doAdd" tiptype="4">
		<input id="status" name="status" type="hidden" value="A">
		<input id="parent" name="parent.id" type="hidden" value="${parent.id}">
		<input id="level" name="level" type="hidden" value="${parent.level+1}">
		<input id="templateType" name="templateType" type="hidden" value="${parent.templateType}">
		<input id="platformType" name="platformType" type="hidden" value="${parent.platformType}">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px" maxlength="10"  datatype="*">
							<span class="Validform_checktip">名称不能超过10个字</span>
							<label class="Validform_label" style="display: none;">名字</label>
						</td>
				</tr>
				<tr>
					<td align="right"  >
					<label class="Validform_label">
						小图:
					</label>
					</td>
					<td class="value" >
						<div id="container_1" >
	                           <a  id="pickfiles_1" href="#" style="width: 200px">
	                               <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
	                           </a>
	                           <span class="Validform_checktip">红框范围内支持拖拽上传(限一张)</span>
	                           <div id="container_div_1" style="min-height: 90px;border: 1px dashed red;">
	                           	<div class='pic_div'>
									<a class='delete' onclick='delPic(this)' href='#'>×</a>
									<img src=''  height='90' width='150'/>
								</div>
	                           </div>
	                     	</div>
						<input id="smallPic" name="smallPic" type="hidden" value="" datatype="*">
					    <span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">小图</label>
					</td>
				</tr>
				<tr>
					<td align="right"  >
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value" >
				     	<div id="container_2" >
	                          <a  id="pickfiles_2" href="#" style="width: 200px">
	                              <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
	                          </a>
	                          <span class="Validform_checktip">红框范围内支持拖拽上传(限一张)</span>
	                          <div id="container_div_2" style="min-height: 100px;border: 1px dashed red;">
	                          	<div class='pic_div'>
								<a class='delete' onclick='delPic(this)' href='#'>×</a>
								<img src=''  height='150' width='252'/>
							</div>
	                          </div>
	                     	</div>
						<input id="coverPic" name="coverPic" type="hidden" value="" datatype="*" >
					    <span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">封面图片</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
 <script src = "webpage/com/buss/template/templateNewsType.js?v=1.11"></script>