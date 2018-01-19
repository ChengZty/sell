<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>修改活动</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  $("#activity").val("${activity}");
	  $("#activity").change(function(){
			var activityId=$(this).val();
			var name = $("#activity option[value='"+activityId+"']").text();
			if(activityId==""){
				$("#activityName").val("");
			}else{
				$("#activityName").val(name);
			}
		})
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGoodsController.do?doChangeActivity" tiptype="4">
					<input id="id" name="id" type="hidden" value="${id }">
					<input id="retailerId" name="retailerId" type="hidden" value="${retailerId }">
					<input id="activityName" name="activityName" type="hidden" value="${activityName }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							活动:
						</label>
					</td>
					<td class="value">
							<select name="activity" id="activity">
							<option value="">---请选择---</option>
							<c:forEach items="${activityList }" var="atvy" >
								<option value="${atvy.id }">${atvy.activityName }</option>
							</c:forEach>
							</select>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
	