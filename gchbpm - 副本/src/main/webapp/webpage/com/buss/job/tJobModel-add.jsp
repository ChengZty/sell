<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>任务组添加</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <style type="text/css">
	input{
		text-align: center;
	}
  </style>
 </head>
 <body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tJobModelController.do?doAdd" tiptype="4">
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td width="100">
				<label class="Validform_label">
					任务组名称设置 :
				</label>
			</td>
			<td class="value" width="1300">
			     <input class="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*">
			</td>
		</tr>
		<tr>
			<td width="100">
				<label class="Validform_label">
					任务组有效时间 :
				</label>
			</td>
			<td class="value" width="1300">
			    <input id="startTime" name="startTime" type="text" style="width: 90px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">~
				<input id="endTime" name="endTime" type="text" style="width: 90px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
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
							<th align="center" width="50"><input id="jobidall" name="jobidall" type="checkbox" value="all" onclick="selectAllJob()" />全选</th>
							<th align="center" colspan="2" width="80">任务名称</th>
							<th align="center" width="300">任务完成标准</th>
							<th align="center" width="200">任务完成奖励标准</th>
							<th align="center" width="250">任务说明</th>
						</tr>
					</thead>
					<c:forEach var="job" items="${jobs.preSalesJobs}" varStatus="status">
						<tr class="th-row">
							<td align="center">
								<input class="th-job-id" name="jobid" type="checkbox" value="${job.id}" onclick="selectJob()" />
								<input name="detailList[${status.index}].jobId" type="hidden" value="${job.id}"/>
								<input name="detailList[${status.index}].status" type="hidden" />
								<input name="detailList[${status.index}].cycle" type="hidden" value="1" />
								<input name="detailList[${status.index}].dayTime" type="hidden" value="1" />
								<input name="detailList[${status.index}].jobDescription" type="hidden" value="${job.description}">
								<input name="detailList[${status.index}].goldTime" type="hidden" value="1" >
							</td>
							<c:if test="${status.index == 0}"><td rowspan="${fn:length(jobs.preSalesJobs)}" width="30" class="task-title">售前</td></c:if>
							<td  class="task-title" width="80" >${job.title} <input class="jobTitle" name="detailList[${status.index}].jobTitle" type="hidden" style="width: 25px" class="inputxt" value="${job.title}"></td>
							<td align="center" class="task-finished-standard">
								${job.regulation}
								<input class="jobNum" name="detailList[${status.index}].jobNum" type="text" style="width: 25px" class="inputxt" >次
							</td>
							<td class="task-reward-standard">
								每完成1次任务，可领<input class="goldNum" name="detailList[${status.index}].goldNum" type="text" style="width: 25px" class="inputxt" >个金币<br/>
							</td>
							<td class="task-punish-standard">
								${job.description}
							</td>
						</tr>
					</c:forEach> 
					<c:forEach var="job" items="${jobs.inSalesJobs}" varStatus="status">
						<tr class="th-row">
							<td align="center">
								<input class="th-job-id" name="jobid" type="checkbox" value="${job.id}" onclick="selectJob()" />
								<input name="detailList[${status.index+4}].jobId" type="hidden" value="${job.id}"/>
								<input name="detailList[${status.index+4}].status" type="hidden" />
								<input name="detailList[${status.index+4}].cycle" type="hidden" value="1" />
								<input name="detailList[${status.index+4}].dayTime" type="hidden" value="1" />
								<input name="detailList[${status.index+4}].jobDescription" type="hidden" value="${job.description}">
								<input name="detailList[${status.index+4}].goldTime" type="hidden" value="1" >
							</td>
							<c:if test="${status.index == 0}"><td rowspan="${fn:length(jobs.inSalesJobs)}" class="task-title">售中</td></c:if>
							<td  class="task-title">${job.title} <input class="jobTitle" name="detailList[${status.index+4}].jobTitle" type="hidden" style="width: 25px" class="inputxt" value="${job.title}"></td>
							<td align="center" class="task-finished-standard">
								${job.regulation}
								<input class="jobNum" name="detailList[${status.index+4}].jobNum" type="text" style="width: 25px" class="inputxt" >次
							</td>
							<td class="task-reward-standard">
								每完成1次任务，可领<input class="goldNum" name="detailList[${status.index+4}].goldNum" type="text" style="width: 25px" class="inputxt" >个金币<br/>
							</td>
							<td class="task-punish-standard">
								${job.description}
							</td>
						</tr>
					</c:forEach> 
					<c:forEach var="job" items="${jobs.afterSalesJobs}" varStatus="status">
						<tr class="th-row">
							<td align="center">
								<input class="th-job-id" name="jobid" type="checkbox" value="${job.id}" onclick="selectJob()" />
								<input name="detailList[${status.index+8}].jobId" type="hidden" value="${job.id}"/>
								<input name="detailList[${status.index+8}].status" type="hidden" />
								<input name="detailList[${status.index+8}].cycle" type="hidden" value="1" />
								<input name="detailList[${status.index+8}].dayTime" type="hidden" value="1" />
								<input name="detailList[${status.index+8}].jobDescription" type="hidden" value="${job.description}">
								<input name="detailList[${status.index+8}].goldTime" type="hidden" value="1" >
							</td>
							<c:if test="${status.index == 0}"><td  rowspan="${fn:length(jobs.afterSalesJobs)}" class="task-title">售后</td></c:if>
							<td  class="task-title">${job.title} <input class="jobTitle" name="detailList[${status.index+8}].jobTitle" type="hidden" style="width: 25px" class="inputxt" value="${job.title}"></td>
							<td align="center" class="task-finished-standard">
								${job.regulation}
								<input class="jobNum" name="detailList[${status.index+8}].jobNum" type="text" style="width: 25px" class="inputxt" >次
							</td>
							<td class="task-reward-standard">
								每完成1次任务，可领<input class="goldNum" name="detailList[${status.index+8}].goldNum" type="text" style="width: 25px" class="inputxt" >个金币<br/>
							</td>
							<td class="task-punish-standard">
								${job.description}
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
					<input id="selectStoreAll" name="selectStore" type="checkbox" value="0" onclick="selectAllStore()"/>全部店铺     共有${storeNum}个店铺<br/>
					<!-- <input name="selectStore" type="radio" value="1" />部分店铺    <br/> -->
					<span width="90%">
						<c:forEach var="store" items="${storeList}" varStatus="status">
							<span><input class="stores" name="stores" type="checkbox" value="${store.id}__${store.name}" />${store.name} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						</c:forEach> 
					</span>
				</div>
			</td>
		</tr>
		<tr></tr>
		<tr id="btntr">
			<td class="value" align="center" colspan="2">
				<a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="submit()" iconCls="icon-save">确定</a> 
				<a href="javascript:goBack()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">返回</a>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>


<script src="plug-in/job/t_job_model.js?v=1.0.1"></script>
<script type="text/javascript">
	//初始化数据
	initData();
	//保存
	function submit(){
		if(!checkData()){
			return;
		}
		$.ajax({
			cache: true,
			type: "POST",
			url: "tJobModelController.do?doAdd",
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