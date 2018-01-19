<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>实体店</title>
  <t:base type="jquery,easyui,tools"></t:base>
<!--   <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link> -->
<!--   <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script> -->
<link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
  	<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
  	<!-- 七牛上传 start -->
  	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
  	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	  <script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	  <script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	  <!-- 七牛上传 end -->
  <script type="text/javascript">
  var m_pic_num = 0;//已经上传的图的张数
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tStoreController.do?doAdd" beforeSubmit="setPicContent()" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tStorePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tStorePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tStorePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tStorePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tStorePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tStorePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tStorePage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							店铺名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 250px" class="inputxt"
								   maxlength="30"   datatype="*"><span class="required">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">店铺名称</label>
						</td>
					</tr>
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							店铺编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="storeCode" name="storeCode" type="text" style="width: 250px" class="inputxt"
								   maxlength="10"  datatype="z"><span class="required">*</span>
							<span class="Validform_checktip">数字或字母</span>
							<label class="Validform_label" style="display: none;">店铺编号</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							地址:
						</label>
					</td>
					<td class="value">
					     	 <input id="address" name="address" type="text" style="width: 250px" class="inputxt"
								           maxlength="50"     datatype="*"><span class="required">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">地址</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							电话:
						</label>
					</td>
					<td class="value">
				     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px" class="inputxt" maxlength="15"  >
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">电话</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排序:
						</label>
					</td>
					<td class="value">
				     	 <input id="sortNum" name="sortNum" type="text" style="width: 150px" class="inputxt" maxlength="5" datatype="n" >
						<span class="Validform_checktip">排序为5位以内整数</span><span class="required">*</span>
						<label class="Validform_label" style="display: none;">排序</label>
					</td>
				</tr>
				<tr>
				<td align="right">
					<label class="Validform_label">
						图片:
					</label>
				</td>
				<td class="value" min-height="200">
        			<div id="container_1" >
                           <a  id="pickfiles_1" href="#" style="width: 200px">
                               <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
                           </a>
                            <span class="Validform_checktip">红框范围内支持拖拽上传(限5张)</span>
                           <div id="container_div_1" style="min-height: 320px;border: 1px dashed red;">
                           
                           </div>
                     	</div>
       				<span class="Validform_checktip">图片尺寸：720*1280</span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/store/tStore.js?v=1.08"></script>		