<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>问题列表</title>
  <t:base type="jquery,easyui,tools,layer"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="baseQuestionsController.do?doAdd" tiptype="5" >
					<input id="id" name="id" type="hidden" value="${baseQuestionsPage.id }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="maintanceType" name="maintanceType" type="hidden" value="1"><!-- 导购 -->
		<table cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							问题:
						</label>
					</td>
					<td class="value">
					     	 <input id="questionName" name="questionName" type="text" style="width: 150px" class="inputxt" >
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
							  <t:dictSelect field="answerType" type="list" datatype="*" id="answerType"
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
					<textarea rows="5" cols="20" style="width: 95%" id="answerValues" name="answerValues" ></textarea>
							<span class="Validform_checktip">多个值用逗号隔开</span>
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
						  hasLabel="false"  title="维护类别"></t:dictSelect>  
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">维护类别</label>
						</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排序:
						</label>
					</td>
					<td class="value">
					     	 <input id="questionSort" name="questionSort" type="text" style="width: 150px" class="inputxt" datatype="n" maxlength="3">
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
									typeGroupCode="qest_info_type"  hasLabel="false"  title="资料类型"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">资料类型</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
