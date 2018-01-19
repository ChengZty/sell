<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>标签</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  $(function(){
	  <c:if test="${tBaseTagsPage.retailerId == 'admin' }">
		$("#tagStage option:last").remove();
	</c:if>
  })
  
    function checkContent(){
	  var tagType = $("#tagType").val();
	  var tagValues = $("#tagValues").val().trim();
	  if(tagType!="3"&&tagValues==""){
		  tip("请输入标签值");
		  $("#tagValues").focus();
		  return false;
	  }else{
		  return true;
	  }
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBaseTagsController.do?doUpdate" tiptype="4" beforeSubmit="checkContent">
					<input id="id" name="id" type="hidden" value="${tBaseTagsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tBaseTagsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBaseTagsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBaseTagsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBaseTagsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBaseTagsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBaseTagsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tBaseTagsPage.status }">
					<input id="tagCode" name="tagCode" type="hidden" value="${tBaseTagsPage.tagCode }">
					<input id="toUserType" name="toUserType" type="hidden" value="${tBaseTagsPage.toUserType }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tBaseTagsPage.retailerId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							标题:
						</label>
					</td>
					<td class="value">
					     	 <input id="tagTitle" name="tagTitle" type="text" style="width: 300px" class="inputxt" maxlength="20" datatype="*" value="${tBaseTagsPage.tagTitle}">
					     	 <span style="color: red">*</span>
							<span class="Validform_checktip">20字以内</span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							标签类别:
						</label>
					</td>
					<td class="value">
							<c:if test="${tBaseTagsPage.retailerId == 'admin' }">
								<t:dictSelect field="tagStage" type="list" id="tagStage" datatype="*"	typeGroupCode="qst_stage" defaultVal="${tBaseTagsPage.tagStage}"  hasLabel="false"  title="标签类别"></t:dictSelect>
							</c:if>
							<c:if test="${tBaseTagsPage.retailerId != 'admin' }">
								<select name="tagStage">
									<option value="4">公司自定义标签</option>
								</select>
							</c:if>
									<span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标签类别</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							标签类型:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="tagType" type="list" datatype="*"
									typeGroupCode="tag_type" defaultVal="${tBaseTagsPage.tagType}" hasLabel="false"  title="类型"></t:dictSelect>     
									<span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标签类型</label>
						</td>
						</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							标签值:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:99%;"  rows="8" id="tagValues" name="tagValues">${tBaseTagsPage.tagValues}</textarea>
							<span class="Validform_checktip">多个值用逗号隔开</span>
							<label class="Validform_label" style="display: none;">标签值</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排序:
						</label>
					</td>
					<td class="value">
					     	 <input id="tagSort" name="tagSort" type="text" style="width: 50px" class="inputxt" datatype="n" maxlength="3" value="${tBaseTagsPage.tagSort}">
					     	 <span style="color: red">*</span>
							<span class="Validform_checktip">不超过3位数字</span>
							<label class="Validform_label" style="display: none;">排序</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							是否可用:
						</label>
					</td>
					<td class="value">
					     	 <t:dictSelect field="valid" type="list" datatype="*" typeGroupCode="sf_yn" defaultVal="${tBaseTagsPage.valid}" hasLabel="false"  title="是否可用"></t:dictSelect>     
									<span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否可用</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
