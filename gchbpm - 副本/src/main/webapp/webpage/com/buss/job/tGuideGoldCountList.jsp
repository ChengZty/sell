<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tGuideGoldCountControllerList" checkbox="false" fitColumns="false" title="导购金币统计"
			actionUrl="tJobDetailCountController.do?datagridGold" idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="任务日期" field="createDate" hidden="true" query="true" queryMode="group" width="150"></t:dgCol>
			<t:dgCol title="店铺名称" field="storeId" query="true" replace="${stores}" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="导购名称" field="guideName" query="true" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="导购手机号" field="phoneNo" query="true" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="金币数量" field="goldNum" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="休假天数" field="holiday" queryMode="single" width="100"></t:dgCol>
			
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXlsGold"></t:dgToolBar>
		</t:datagrid>
		<input type="hidden" name="notInitSearch" value="1" />
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		//给时间控件加上样式
		$("#tGuideGoldCountControllerListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_begin}")
 			.attr("id","createDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createDate_end\')}'});});
		$("#tGuideGoldCountControllerListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_end}")
			.attr("id","createDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createDate_begin\')}'});});
	});

	//导出
	function ExportXlsGold() {
		JeecgExcelExport("tJobDetailCountController.do?ExportXlsGold","tGuideGoldCountControllerList");
	}
</script>