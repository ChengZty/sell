<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tJobDetailCountControllerList" checkbox="false" fitColumns="false" title="任务日报表统计"
			actionUrl="tJobDetailCountController.do?dayDatagrid" idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="日期" field="searchTime" formatter="yyyy-MM-dd" query="true" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="任务周期" field="jobDate"   query="false" queryMode="single" width="200"></t:dgCol>
			<t:dgCol title="店铺名称" field="storeId" query="true" replace="${stores}" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="导购名称" field="guideName" query="true" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="导购手机号" field="mobilePhone" query="false"  hidden="true"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="任务组名称" field="modelName" query="true"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="任务名称" field="jobTitle" query="true"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="任务目标数" field="jobNum"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="当日任务完成数" field="pace"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="累计任务完成数" field="pace"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="累计任务完成率" field="myPercent"  width="100"   ></t:dgCol>
			<t:dgCol title="任务完成状态" field="paceType" replace="已完成_1,未完成_0" queryMode="single" width="100"></t:dgCol>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		//给时间控件加上样式
		$("#tJobDetailCountControllerListtb").find("input[name='searchTime']").attr("class","Wdate").attr("style","height:20px;width:90px;")
 					.val("${searchTime}").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'${searchTime}'});});
	});

	//导出
	function ExportXls() {
		JeecgExcelExport("tJobDetailCountController.do?exportDayXls","tJobDetailCountControllerList");
	}
	
	//累计任务完成率
   function getJobStatus(value,row,index){
		if(row.jobNum  <=  row.pace ){
			return "已完成";
		}else if(row.jobNum > row.pace ){
			return "未完成";
		}else{
			return "";
		}
   }
</script>
