<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>问题列表</title>
  <t:base type="jquery,easyui,tools,layer"></t:base>
  <script type="text/javascript">
  $(function(){
	  $("#answerType").change(function(){
		  var type = $(this).val();
		  if(!("2"==type||"3"==type)){//非单选和多选
			  $("#ans_values").hide();
			  $("#answerValues").val("");
		  }else{
			  $("#ans_values").show();
		  }
	  })
  })
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="baseQuestionsController.do?doUpdate" tiptype="4" >
					<input id="id" name="id" type="hidden" value="${baseQuestionsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${baseQuestionsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${baseQuestionsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${baseQuestionsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${baseQuestionsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${baseQuestionsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${baseQuestionsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${baseQuestionsPage.status }">
					<input id="questionValue" name="questionValue" type="hidden" value="${baseQuestionsPage.questionValue }">
					<input id="maintanceType" name="maintanceType" type="hidden" value="${baseQuestionsPage.maintanceType }">
		<table cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								问题:
							</label>
						</td>
						<td class="value">
						     	 <input id="questionName" name="questionName" type="text" style="width: 150px" class="inputxt"  value='${baseQuestionsPage.questionName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">问题</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								答案类型:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="answerType" type="list" id="answerType"
										typeGroupCode="ans_type" defaultVal="${baseQuestionsPage.answerType}" hasLabel="false"  title="答案类型"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">答案类型</label>
						</td>
					</tr>
					<tr id="ans_values">
						<td align="right">
							<label class="Validform_label">
								答案:
							</label>
						</td>
						<td class="value">
						<textarea rows="5" cols="20" style="width: 95%" id="answerValues" name="answerValues" >${baseQuestionsPage.answerValues}</textarea>
<%-- 						     	 <input id="answerValues" name="answerValues" type="text" style="width: 150px" class="inputxt"  value='${baseQuestionsPage.answerValues}'> --%>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">答案</label>
						</td>
					</tr>
					<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							维护类别:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="maintanceType" id="maintanceType" type="list" datatype="*" typeGroupCode="matce_type" defaultVal="${baseQuestionsPage.maintanceType}"
						   hasLabel="false"  title="答案类型"></t:dictSelect>  
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">答案</label>
						</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排序:
						</label>
					</td>
					<td class="value">
					     	 <input id="questionSort" name="questionSort" type="text" style="width: 150px" value="${baseQuestionsPage.questionSort}" class="inputxt" datatype="n" maxlength="3">
							<span class="Validform_checktip">位数不超过3位</span>
							<label class="Validform_label" style="display: none;">排序</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							资料类型:
						</label>
					</td>
					<td class="value">
					     	 <t:dictSelect field="infoType" type="list" datatype="*" id="infoType"
									typeGroupCode="qest_info_type" defaultVal="${baseQuestionsPage.infoType}" hasLabel="false"  title="资料类型"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">资料类型</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
 <script type="text/javascript">
 var type = "${baseQuestionsPage.answerType}";
 if(!("2"==type||"3"==type)){//非单选和多选
	  $("#ans_values").hide();
// 	  $("#answerValues").val("");
 }
 </script>
