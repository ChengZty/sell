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
  $(function(){
	  $("#topTypeId").val("${tCustWordsPage.topTypeId }");
	  $("#subTypeId").val("${tCustWordsPage.subTypeId }");
	  
	  <c:if test="${empty load && tCustWordsPage.type =='2'}">
		//初始化图片上传七牛
		initPicsUpload();
		//初始化拖拽对象
// 		initSortablePics();
	  </c:if>
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tCustWordsController.do?doUpdate" tiptype="4" beforeSubmit="checkAndInitPics">
					<input id="id" name="id" type="hidden" value="${tCustWordsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tCustWordsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tCustWordsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tCustWordsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tCustWordsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tCustWordsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tCustWordsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tCustWordsPage.status }">
					<input id="platformType" name="platformType" type="hidden" value="${tCustWordsPage.platformType }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tCustWordsPage.retailerId }">
					<input id="type" name="type" type="hidden" value="${tCustWordsPage.type }">
					<input  name="topTypeId" type="hidden" value="${tCustWordsPage.topTypeId }">
					<input  name="subTypeId" type="hidden" value="${tCustWordsPage.subTypeId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								一级分类:
							</label>
						</td>
						<td class="value">
					     	<select id="topTypeId"  disabled="disabled">
							 <option value="">-请选择-</option>
							 <c:forEach var="obj" items="${topTypeList}" >
									<option value="${obj.id}">${obj.name}</option>					 	
				              </c:forEach>
							</select>
							<span class="Validform_checktip"></span>
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
						     <select id="subTypeId"  disabled="disabled">
								 <option value="">-请选择-</option>
								 <c:if test="${not empty subTypeList }">
									 <c:forEach var="obj" items="${subTypeList}" >
											<option value="${obj.id}">${obj.name}</option>					 	
						              </c:forEach>
								 </c:if>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">二级分类</label>
						</td>
					</tr>
					<c:if test="${tCustWordsPage.type =='1'}">
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容:
							</label>
						</td>
						<td class="value">
							<textarea rows="5" style="width: 98%" name="content" datatype="*" maxlength="300">${tCustWordsPage.content}</textarea>
							<span class="Validform_checktip">最多输入300字</span>
							<label class="Validform_label" style="display: none;">内容</label>
						</td>
					</tr>
					</c:if>
					<!-- 图片话术 -->
					<c:if test="${tCustWordsPage.type =='2'}">
					<tr>
						<td align="right">
							<label class="Validform_label">
								图片:
							</label>
						</td>
						<td class="value">
							<c:if test="${empty load}">
							<div id="container_1" >
		                            <a  id="pickfiles_1" href="#" style="width: 200px">
		                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
		                            </a>
		                            <span class="Validform_checktip">红框范围内支持拖拽上传，图片不超过200kb(限一张)</span>
		                            <div id="container_div_1" style="min-height: 100px;border: 1px dashed red;">
		                            		<div class='brick'>
												<a class='delete' onclick='delPic(this)' href='#'>×</a>
												<img src='${tCustWordsPage.content }'  height='100'/>
												<input type='hidden' value='${tCustWordsPage.content }' name='content' class='picUrl'>
<%-- 												<input type='hidden' class='id' value="${poVal.id }"/> --%>
<%-- 												<input type='hidden' class='custWordsId' value="${poVal.custWordsId }"/> --%>
											</div>
<%-- 										<c:forEach var="poVal" items="${tCustWordsPage.picList }"> --%>
<!-- 												<div class='brick'> -->
<!-- 													<a class='delete' onclick='delPic(this)' href='#'>×</a> -->
<%-- 													<img src='${poVal.picUrl }'  height='100'/> --%>
<%-- 													<input type='hidden' value='${poVal.picUrl }' class='picUrl'> --%>
<%-- 													<input type='hidden' class='id' value="${poVal.id }"/> --%>
<%-- 													<input type='hidden' class='custWordsId' value="${poVal.custWordsId }"/> --%>
<!-- 												</div> -->
<%-- 										</c:forEach> --%>
		                            </div>
		                      </div>
		                      </c:if>
		                      <c:if test="${not empty load}">
		                      	<div id="container_div_1" style="min-height: 100px;">
		                      		<img src='${tCustWordsPage.content }'  height='100'/>
<%-- 										<c:forEach var="poVal" items="${tCustWordsPage.picList }"> --%>
<!-- 												<div class='brick'> -->
<%-- 													<img src='${poVal.picUrl }'  height='100'/> --%>
<%-- 													<input type='hidden' value='${poVal.picUrl }' class='picUrl'> --%>
<%-- 													<input type='hidden' class='id' value="${poVal.id }"/> --%>
<%-- 													<input type='hidden' class='custWordsId' value="${poVal.custWordsId }"/> --%>
<!-- 												</div> -->
<%-- 										</c:forEach> --%>
		                      </c:if>
						</td>
					</tr>
					</c:if>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/words/tCustWords.js?v=1.14"></script>		