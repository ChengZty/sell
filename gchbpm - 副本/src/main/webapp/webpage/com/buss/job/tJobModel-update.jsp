<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>任务组修改</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <style type="text/css">	
  	input{
		text-align: center;
	}
  </style>
 </head>
 <body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tJobModelController.do?doUpdate" tiptype="4">
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td width="100">
				<label class="Validform_label">
					任务组名称设置 :
				</label>
			</td>
			<td class="value" width="1300">
			     <input class="modelid" name="id" type="hidden" value="${tJobModel.id}">
			     <input name="createName" type="hidden" value="${tJobModel.createName}">
			     <input name="createBy" type="hidden" value="${tJobModel.createBy}">
			     <input name="createDate" type="hidden" value="${tJobModel.createDate}">
			     <input name="updateName" type="hidden" value="${tJobModel.updateName}">
			     <input name="updateBy" type="hidden" value="${tJobModel.updateBy}">
			     <input name="updateDate" type="hidden" value="${tJobModel.updateDate}">
			     <input name="status" type="hidden" value="${tJobModel.status}">
			     <input class="name" name="name" type="text" style="width: 150px" class="inputxt" disabled="disabled" value="${tJobModel.name}" datatype="*">
			</td>
		</tr>
		<tr>
			<td width="100">
				<label class="Validform_label">
					任务组有效时间:
				</label>
			</td>
			<td class="value" width="1300">
			    <input class="startTime" name="startTime" type="text" value="${tJobModel.startTime}" style="width: 90px" class="Wdate" disabled="disabled" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">~
				<input class="endTime" name="endTime" type="text" value="${tJobModel.endTime}" style="width: 90px" class="Wdate" disabled="disabled" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
			</td>
		</tr>
								   
		<tr>
			<td>
				<label class="Validform_label">
					任务组内容设置:
				</label>
			</td>
			<td class="value" width="1300">
				<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<thead>
						<tr>
							<th align="center" width="50"><input id="jobidall" name="jobidall" type="checkbox" value="all" disabled="disabled" onclick="selectAllJob()" />全选</th>
							<th align="center" colspan="2" width="80">任务名称</th>
							<th align="center" width="300">任务完成标准</th>
							<th align="center" width="200">任务完成奖励标准</th>
							<th align="center" width="250">任务说明</th>
						</tr>
					</thead>
					<c:forEach var="jobParam" items="${jobParams.preSalesJobs}" varStatus="status">
						<tr class="th-row">
							<td align="center">
								<c:if test="${empty jobParam.id}"><input class='th-job-id' name="detailList[${status.index}].jobId" type="checkbox" value="${jobParam.jobId}" onclick="selectJob()" disabled="disabled"/></c:if>
								<c:if test="${not empty jobParam.id}"><input class='th-job-id' name="detailList[${status.index}].jobId" type="checkbox" value="${jobParam.jobId}" onclick="selectJob()" checked="checked" disabled="disabled"/></c:if>
								<input class="jobParam" name="detailList[${status.index}].id" type="hidden" value="${jobParam.id}">
								<input name="detailList[${status.index}].status" type="hidden" value="${jobParam.status}"/>
								<input name="detailList[${status.index}].cycle" type="hidden" value="${jobParam.cycle}" />
								<input name="detailList[${status.index}].dayTime" type="hidden" value="${jobParam.dayTime}" />
								<input name="detailList[${status.index}].jobDescription" type="hidden" value="${jobParam.jobDescription}">
								<input name="detailList[${status.index}].goldTime" type="hidden" value="${jobParam.goldTime}" >
			     			</td>
							<c:if test="${status.index == 0}"><td rowspan="${fn:length(jobParams.preSalesJobs)}" width="30" class="task-title">售前</td></c:if>
							<td  class="task-title">${jobParam.jobTitle} 
								<input class="jobTitle" width="80" name="detailList[${status.index}].jobTitle" type="hidden" value="${jobParam.jobTitle}" readonly="readonly"></td>
							<td align="center" class="task-finished-standard">
								${jobParam.regulation}
								<input class="jobNum" name="detailList[${status.index}].jobNum" value="${jobParam.jobNum}" type="text" style="width: 25px" class="inputxt" readonly="readonly">次
							</td>
							<td class="task-reward-standard">
								每完成1次任务，可领<input class="goldNum" name="detailList[${status.index}].goldNum" value="${jobParam.goldNum}" type="text" style="width: 25px" class="inputxt" readonly="readonly">个金币<br/>
							</td>
							<td class="task-punish-standard">
								${jobParam.jobDescription}
							</td>
						</tr>
					</c:forEach>
					<c:forEach var="jobParam" items="${jobParams.inSalesJobs}" varStatus="status">
						<tr class="th-row">
							<td align="center">
								<c:if test="${empty jobParam.id}"><input class='th-job-id' name="detailList[${status.index+4}].jobId" type="checkbox" value="${jobParam.jobId}" onclick="selectJob()" disabled="disabled"/></c:if>
								<c:if test="${not empty jobParam.id}"><input class='th-job-id' name="detailList[${status.index+4}].jobId" type="checkbox" value="${jobParam.jobId}" onclick="selectJob()" checked="checked" disabled="disabled"/></c:if>
								<input class="jobParam" name="detailList[${status.index+4}].id" type="hidden" value="${jobParam.id}">
								<input name="detailList[${status.index+4}].status" type="hidden" value="${jobParam.status}"/>
								<input name="detailList[${status.index+4}].cycle" type="hidden" value="${jobParam.cycle}" />
								<input name="detailList[${status.index+4}].dayTime" type="hidden" value="${jobParam.dayTime}" />
								<input name="detailList[${status.index+4}].jobDescription" type="hidden" value="${jobParam.jobDescription}">
								<input name="detailList[${status.index+4}].goldTime" type="hidden" value="${jobParam.goldTime}" >
			     			</td>
							<c:if test="${status.index == 0}"><td rowspan="${fn:length(jobParams.inSalesJobs)}" width="30" class="task-title">售中</td></c:if>
							<td  class="task-title">${jobParam.jobTitle} 
								<input class="jobTitle" width="80" name="detailList[${status.index+4}].jobTitle" type="hidden" value="${jobParam.jobTitle}" readonly="readonly"></td>
							<td align="center" class="task-finished-standard">
								${jobParam.regulation}
								<input class="jobNum" name="detailList[${status.index+4}].jobNum" value="${jobParam.jobNum}" type="text" style="width: 25px" class="inputxt" readonly="readonly">次
							</td>
							<td class="task-reward-standard">
								每完成1次任务，可领<input class="goldNum" name="detailList[${status.index+4}].goldNum" value="${jobParam.goldNum}" type="text" style="width: 25px" class="inputxt" readonly="readonly">个金币<br/>
							</td>
							<td class="task-punish-standard">
								${jobParam.jobDescription}
							</td>
						</tr>
					</c:forEach>
					<c:forEach var="jobParam" items="${jobParams.afterSalesJobs}" varStatus="status">
						<tr class="th-row">
							<td align="center">
								<c:if test="${empty jobParam.id}"><input class='th-job-id' name="detailList[${status.index+8}].jobId" type="checkbox" value="${jobParam.jobId}" onclick="selectJob()" disabled="disabled"/></c:if>
								<c:if test="${not empty jobParam.id}"><input class='th-job-id' name="detailList[${status.index+8}].jobId" type="checkbox" value="${jobParam.jobId}" onclick="selectJob()" checked="checked" disabled="disabled"/></c:if>
								<input class="jobParam" name="detailList[${status.index+8}].id" type="hidden" value="${jobParam.id}">
								<input name="detailList[${status.index+8}].status" type="hidden" value="${jobParam.status}"/>
								<input name="detailList[${status.index+8}].cycle" type="hidden" value="${jobParam.cycle}" />
								<input name="detailList[${status.index+8}].dayTime" type="hidden" value="${jobParam.dayTime}" />
								<input name="detailList[${status.index+8}].jobDescription" type="hidden" value="${jobParam.jobDescription}">
								<input name="detailList[${status.index+8}].goldTime" type="hidden" value="${jobParam.goldTime}" >
			     			</td>
							<c:if test="${status.index == 0}"><td rowspan="${fn:length(jobParams.afterSalesJobs)}" width="30" class="task-title">售后</td></c:if>
							<td  class="task-title">${jobParam.jobTitle} 
								<input class="jobTitle" width="80" name="detailList[${status.index+8}].jobTitle" type="hidden" value="${jobParam.jobTitle}" readonly="readonly"></td>
							<td align="center" class="task-finished-standard">
								${jobParam.regulation}
								<input class="jobNum" name="detailList[${status.index+8}].jobNum" value="${jobParam.jobNum}" type="text" style="width: 25px" class="inputxt" readonly="readonly">次
							</td>
							<td class="task-reward-standard">
								每完成1次任务，可领<input class="goldNum" name="detailList[${status.index+8}].goldNum" value="${jobParam.goldNum}" type="text" style="width: 25px" class="inputxt" readonly="readonly">个金币<br/>
							</td>
							<td class="task-punish-standard">
								${jobParam.jobDescription}
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<label class="Validform_label">
					任务组店铺设置:
				</label>
			</td>
			<td >
				<div style="padding: 15px">
					<c:choose>
						<c:when test="${jobParam.punish == '0'}">  
							   <input id="selectStoreAll" name="selectStore" checked="checked" type="checkbox" value="0" disabled="disabled" onclick="selectAllStore()"/>
						</c:when>
						<c:otherwise> 
							   <input id="selectStoreAll" name="selectStore" type="checkbox" value="0" disabled="disabled" onclick="selectAllStore()"/>
						</c:otherwise>
					</c:choose>
					全部店铺     共有${storeNum}个店铺<br/>
					<!-- <input name="selectStore" type="radio" value="1" />部分店铺    <br/> -->
					<span width="90%">
						<c:forEach var="store" items="${storeList}" varStatus="status">
							<c:choose>
							<c:when test="${store.jobModelId == '0'}">  
								   <span><input class="stores" name="stores" type="checkbox" value="${store.id}__${store.name}" disabled="disabled" checked="checked"/>${store.name} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</c:when>
							<c:otherwise> 
								   <span><input class="stores" name="stores" type="checkbox" value="${store.id}__${store.name}" disabled="disabled"/>${store.name} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</c:otherwise>
							</c:choose>
						</c:forEach> 
					</span>
				</div>
			</td>
		</tr>
		<tr></tr>
		<tr id="btntr">
			<td class="value" align="center" colspan="2">
				<!-- <a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="submit()" iconCls="icon-save">确定</a>  -->
				<a href="javascript:goBack()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">返回</a>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>

<script src="plug-in/job/t_job_model.js?v=1.0.1"></script>
<script type="text/javascript">
//编辑初始化
initUpdateData();
//保存
function submit(){
	if(!checkData()){
		return;
	}
	$.ajax({
	       cache: true,
	       type: "POST",
	       url: "tJobModelController.do?doUpdate",
	       data:$('#formobj').serialize(),
	       datatype: 'json',
	       async: false,
	       error: function(request) {
		       	tip("服务器连接错误！");
	       },
	       success: function(data) {
				var d = $.parseJSON(data);
				tip(d.msg);
				goBack();
	       }
	});
	/* $("#formobj").submit(); */
}
</script>
