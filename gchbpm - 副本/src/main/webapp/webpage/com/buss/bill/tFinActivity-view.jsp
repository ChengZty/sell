<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动激励</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <style type="text/css">
	.brand_tr{
		display: none;
	}
</style>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  var activityType = "${tFinActivityPage.activityType }";
	  $("#activityType").val(activityType);
	  if(activityType=="2"){
		  $(".brand_tr").show();
	  }
	  $("#formobj input,select,a").attr("disabled","disabled");
  })
  </script>
 </head>
 <body>
 <div style="margin: auto;max-width: 1280px">
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tFinActivityController.do?doUpdate" callback="goBack" beforeSubmit="setContent()" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tFinActivityPage.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="4" height="40px">
						<h2>活动激励</h2>
					</td>
				</tr>
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							活动名称:
						</label>
					</td>
					<td class="value" width="450px;" colspan="3">
						<input id="activityName" name="activityName" type="text" style="width: 400px" class="inputxt" value="${tFinActivityPage.activityName }">
					</td>
				</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								活动类别:
							</label>
						</td>
						<td class="value">
							<select id="activityType" name="activityType">
								<option value="1">单品</option>
								<option value="2">品牌</option>
								<option value="3">全馆</option>
							</select>
						</td>
						<td align="right" width="120">
							<label class="Validform_label">
								<span class="brand_tr">品牌名称:</span>
							</label>
						</td>
						<td class="value" >
							<div class="brand_tr">
						     	 ${tFinActivityPage.brandName}
						     </div>	 
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								开始时间:
							</label>
						</td>
						<td class="value">
							  <input id="startTime" name="startTime" type="text" style="width: 150px" 
				      						 value='<fmt:formatDate value='${tFinActivityPage.startTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>    
						</td>
						<td align="right">
							<label class="Validform_label">
								结束时间:
							</label>
						</td>
						<td class="value">
							  <input id="endTime" name="endTime" type="text" style="width: 150px" 
				      						 value='<fmt:formatDate value='${tFinActivityPage.endTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>    
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							活动规则:
						</label>
					</td>
					<td class="value" colspan="3">
							${tFinActivityPage.newsContext}
					</td>
				</tr>
				<tr height="150">
					<td align="right" width="120">
						<label class="Validform_label">
							话题:
						</label>
					</td>
					<td class="value">
						${tFinActivityPage.newsTitle}
					</td>
					<td align="right" width="120">
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value">
							<img alt="" src="${tFinActivityPage.coverPic}" height="150">
					</td>
				</tr>
			</table>
		</t:formvalid>
</div>
 </body>
