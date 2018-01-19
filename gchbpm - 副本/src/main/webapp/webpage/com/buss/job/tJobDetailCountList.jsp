<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tJobDetailCountControllerList" checkbox="false" fitColumns="false" title="任务详情统计"
			actionUrl="tJobDetailCountController.do?datagrid" idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="任务日期" field="jobDate" query="true" queryMode="group" width="150"></t:dgCol>
			<t:dgCol title="任务组名称" field="modelName" query="true"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="任务名称" field="jobTitle" query="true"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="任务内容" field="jobDescription" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="店铺名称" field="storeId" query="true" replace="${stores}" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="导购名称" field="guideName" query="true" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="导购手机号" field="mobilePhone" query="true" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="任务完成进度" field="pace"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="任务完成情况" field="paceType" query="true" replace="完成_1,未完成_0" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="领取金币" field="goldTotal" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="罚除金币" field="goldSub" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="金币数量" field="goldPerson" queryMode="single" width="100"></t:dgCol>
			
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
		<input type="hidden" name="notInitSearch" value="1" />
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		//给时间控件加上样式
		$("#tJobDetailCountControllerListtb").find("input[name='jobDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_begin}")
 					.attr("id","jobDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'jobDate_end\')}'});});
 			$("#tJobDetailCountControllerListtb").find("input[name='jobDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_end}")
 					.attr("id","jobDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'jobDate_begin\')}'});});
	});

	//导出
	function ExportXls() {
		JeecgExcelExport("tJobDetailCountController.do?exportXls","tJobDetailCountControllerList");
	}
</script>