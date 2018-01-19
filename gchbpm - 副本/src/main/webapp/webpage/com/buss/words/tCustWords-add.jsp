<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>顾客话术</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
  <!-- 七牛上传 start -->
  	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
  	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	  <script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	  <script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	  <!-- 七牛上传 end -->
  <script type="text/javascript" src="plug-in/sortable/Sortable.min.js?v=1.0.1"></script><!-- 拖拽 -->
  <script type="text/javascript">
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  </script>
 </head>
 <body>
 <div id="temStr" style="display: none;"></div>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tCustWordsController.do?doAdd" tiptype="4" beforeSubmit="checkAndInitPics">
					<input id="status" name="status" type="hidden" value="A">
<!-- 					<input id="type" name="type" type="hidden" > -->
		<table id="tb" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr onclick="initPicsUpload()">
					<td align="right" width="100">
						<label class="Validform_label">
							一级分类:
						</label>
					</td>
					<td class="value">
							<select id="topTypeId" name="topTypeId" onchange="getSubList(this.value)" datatype="*">
								 <option value="">-请选择-</option>
								 <c:forEach var="obj" items="${topTypeList}" >
										<option value="${obj.id}">${obj.name}</option>					 	
					              </c:forEach>
							</select>
<!-- 							<span class="Validform_checktip"></span> -->
							<label class="Validform_label" style="display: none;">一级分类</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							二级分类:
						</label>
					</td>
					<td class="value">
				     	 <select id="subTypeId" name="subTypeId" datatype="*" onchange="changeTypeDisplay(this.value)">
							 <option value="">-请选择-</option>
						</select>
<!-- 						<span class="Validform_checktip"></span> -->
						<label class="Validform_label" style="display: none;">二级分类</label>
						<select id="type" name="type" onchange="changeCotentDisplay(this.value)" style="display: none;">
							<option value="1">文字话术</option>
							<option value="2">图片话术</option>
						</select>
					</td>
				</tr>
		</table>
</t:formvalid>
		<div style="display: none;">
			<table id="div_tb">
			<!--文字输入模版 -->
				<tr class="t">
					<td align="right">
						<label class="Validform_label">
							内容:
						</label>
					</td>
					<td class="value">
						<textarea rows="5" style="width: 98%" name="content" datatype="*" maxlength="300"></textarea>
						<span class="Validform_checktip">最多输入300字</span>
						<label class="Validform_label" style="display: none;">内容</label>
					</td>
				</tr>
				<!--图片输入模版 -->
				<tr class="t">
					<td align="right">
						<label class="Validform_label">
							图片:
						</label>
					</td>
					<td class="value">
						<div id="container_1">
                            <a id="pickfiles_1" href="#" style="width: 200px">
                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
                            </a>
                            <span class="Validform_checktip">红框范围内支持拖拽上传(限一张)</span>
                            <div id="container_div_1" style="min-height: 100px;border: 1px dashed red;">
                            
                            </div>
                        </div>
					</td>
				</tr>
			</table>
			
		</div>
 </body>
<script src = "webpage/com/buss/words/tCustWords.js?v=1.13"></script>