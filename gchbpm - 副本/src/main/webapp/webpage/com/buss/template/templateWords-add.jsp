<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>添加话术模板</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
  <style>
  	#tag_div span{
  		margin-right: 10px;
  	}
  </style>
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
  <!-- 七牛上传 start -->
  	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
  	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	  <script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	  <script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	  <!-- 七牛上传 end -->
  <script type="text/javascript" src="plug-in/sortable/Sortable.min.js?v=1.0.1"></script><!-- 拖拽 -->
  <script type="text/javascript">
  var retailerId = "${retailerId}"; //零售商ID
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  </script>
 </head>
 <body>
 <div id="temStr" style="display: none;"></div>
  	<t:formvalid formid="formobj" dialog="true"  layout="table" action="templateWordsController.do?doAdd" tiptype="4" beforeSubmit="checkAndInitPics">
		<input id="status" name="status" type="hidden" value="A">
		<input id="tags" name="tags" type="hidden"  value="">
		<table id="tb" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr onclick="initPicsUpload()">
					<td align="right" width="100">
						<label class="Validform_label">
							一级分类:
						</label>
					</td>
					<td class="value">
						<select id="topTypeId" name="topTypeId" onchange="getSubList(this.value,1)" datatype="*">
							 <option value="">-请选择-</option>
							 <c:forEach var="obj" items="${topTypeList}" >
									<option value="${obj.id}">${obj.name}</option>					 	
				              </c:forEach>
						</select>
						<span class="required">*</span>
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
				     	 <select id="subTypeId" name="subTypeId" onchange="getSubList(this.value,2)" datatype="*" >
							 <option value="">-请选择-</option>
						</select>
						<span class="required">*</span>
						<label class="Validform_label" style="display: none;">二级分类</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							三级分类:
						</label>
					</td>
					<td class="value">
				     	 <select id="thridTypeId" name="thridTypeId" onchange="changeTypeDisplay(this.value)" >
							 <option value="">-请选择-</option>
						</select>
						<span class="required">*</span>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">三级分类</label>
					</td>
				</tr>
				<tr class="w_t">
					<td align="right"><label class="Validform_label"> 话术类型: </label></td>
					<td class="value" >
		                <select id="type" name="type" onchange="changeCotentDisplay(this.value)" datatype="*">
							<option value="">-请选择-</option>
							<option value="1">文字话术</option>
							<option value="2">图片话术</option>
						</select>
						<span class="required">*</span>
		            </td>
				</tr>
				
				<tr id="trade_tr">
					<td align="right"><label class="Validform_label"> 关联行业: </label></td>
					<td class="value" >
		                <input name="tradeId" type="hidden" value="${tradeId}" id="tradeId">
		                <input name="tradeName" type="text" value="${tradeName }" id="tradeName" readonly="readonly" />
		                <span class="required">*</span>
		                <t:choose hiddenName="tradeId" hiddenid="id" url="userController.do?goTradeList" name="tradeList"
		                          icon="icon-search" title="行业列表" textname="tradeName" isclear="true" isInit="true"></t:choose>
		            </td>
				</tr>
		</table>
	</t:formvalid>
		<div style="display: none;">
			<table id="div_tb">
			<!--话术标签 -->
				<tr class="t words_tage" >
					<td align="right"><label class="Validform_label">话术标签:</label></td>
					<td class="value" >
							<input type="text"  id="input-txt"   />
							<a href="#" class="easyui-linkbutton l-btn th-origin-clone" iconCls="icon-edit" >添加标签</a> 
							 <span style="color: red;">提示:双击删除标签</span> 
							<fieldset title="话术标签" style="min-height: 50px;">
								<div class="box" id="tag_div">
								</div>
							</fieldset>
					</td>
				</tr>		
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
<script src = "webpage/com/buss/template/templateWords.js?v=1.22"></script>