<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>品牌</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
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
  <t:formvalid formid="formobj_u" dialog="false" usePlugin="password" layout="table" action="baseBrandController.do?doUpdate" callback="backList"  tiptype="4">
					<input id="id" name="id" type="hidden" value="${baseBrandPage.id }">
					<input id="brandCode" name="brandCode" type="hidden" value="${baseBrandPage.brandCode }">
					<input id="createName" name="createName" type="hidden" value="${baseBrandPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${baseBrandPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${baseBrandPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${baseBrandPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${baseBrandPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${baseBrandPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${baseBrandPage.status }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								品牌编码:
							</label>
						</td>
						<td class="value">
						     	 ${baseBrandPage.brandCode}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								品牌名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="brandName" name="brandName" type="text" style="width: 150px"   datatype="*" value='${baseBrandPage.brandName}'><span class="required">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">品牌名称</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							品牌介绍:
						</label>
					</td>
					<td class="value">
					     	 <input id="brandSummary" name="brandSummary" type="text" style="width: 450px"   datatype="*" maxlength="12" value='${baseBrandPage.brandSummary}'><span class="required">*</span>
							<span class="Validform_checktip">品牌介绍不超过12字</span>
							<label class="Validform_label" style="display: none;">品牌介绍</label>
						</td>
				</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								排序:
							</label>
						</td>
						<td class="value">
						     	 <input id="sortNo" name="sortNo" type="text" style="width: 150px"   datatype="n" value='${baseBrandPage.sortNo}' maxlength="5"><span class="required">*</span> 
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">排序</label>
						</td>
					</tr>
					<tr id="c_pic"  >
					<td align="right">
						<label class="Validform_label">
							品牌图片:
						</label>
					</td>
					<td class="value">
						<div id="container_1" >
                            <a  id="pickfiles_1" href="#" style="width: 200px">
                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
                            </a>
                            <span class="Validform_checktip">红框范围内支持拖拽上传，图片不超过200kb(限一张)</span>
                            <div id="container_div_1" style="min-height: 100px;border: 1px dashed red;">
                            	<div class='pic_div'>
									<a class='delete' onclick='delPic(this)' href='#'>×</a>
									<img src='${baseBrandPage.brandPic }'  height='100'/>
								</div>
                            </div>
                      	</div>
					    <input id="brandPic" name="brandPic" type="hidden" value='${baseBrandPage.brandPic }' datatype="*" errormsg="请上传品牌图片">
					    <span class="Validform_checktip">品牌图片(100*100)</span>
					    <label class="Validform_label" style="display: none;">品牌图片</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							背景图片:
						</label>
					</td>
					<td class="value">
					    <div id="container_2" >
                            <a  id="pickfiles_2" href="#" style="width: 200px">
                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
                            </a>
                            <span class="Validform_checktip">红框范围内支持拖拽上传，图片不超过200kb(限一张)</span>
                            <div id="container_div_2" style="min-height: 100px;border: 1px dashed red;">
                            	<div class='pic_div'>
									<a class='delete' onclick='delPic(this)' href='#'>×</a>
									<img src='${baseBrandPage.bigPic }'  height='100'/>
								</div>
                            </div>
                      	</div>
					    <input id="bigPic" name="bigPic" type="hidden" value='${baseBrandPage.bigPic }' >
					    <span class="Validform_checktip">背景图片(720*250)</span>
					    <label class="Validform_label" style="display: none;">背景图片</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							品牌故事:
						</label>
					</td>
					<td class="value">
					<input  name="brandDesc.id" type="hidden" value="${brandDesc.id }">
					<input  name="brandDesc.brandId" type="hidden" value="${brandDesc.brandId }">
<!-- 					<input id="content" name="brandDesc.content" type="hidden" > -->
					<script id="content_ue"   type="text/plain" style="width:99%;">${brandDesc.content}</script>
						    <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">品牌故事</label></td>
					</tr>
					<tr height="40">
						<td class="upload" colspan="2" align="center">
							<a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="subUpdate()" iconCls="icon-save">提交</a>
							<a href="javascript:back()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">返回</a>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
<script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?20160607"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?20160607"> </script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/lang/zh-cn/zh-cn.js?20160607"></script>
<script src = "webpage/com/buss/base/baseBrand.js?v=1.06"></script>