<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="guideActivityTotal" checkbox="true" fitColumns="false" title="活动浏览统计汇总报表" actionUrl="guideActivityCountController.do?tActivityTotalDatagrid" idField="id" fit="true" queryMode="group">
  <t:dgCol title="查询时间"  field="searchTime"  hidden="true" query="true"   queryMode="group"  width="150"></t:dgCol>
  
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="活动标题"  field="title"  query="true"    width="120"></t:dgCol>
   <t:dgCol title="点击次数"  field="totalClickNum"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="220"></t:dgCol>
   <t:dgFunOpt title="明细"  funname="viewClickInfo(id)"  />
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  <input type="hidden" name="notInitSearch" value="1"/><!-- 不是初始化查询 -->
  </div>
 </div>
 <script type="text/javascript">
 var searchTime_begin = ${searchTime_begin};
 var searchTime_end = ${searchTime_end};
 
 $(document).ready(function(){
 	//给时间控件加上样式
	$("#guideActivityTotaltb").find("input[name='searchTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_begin}")
		.attr("id","searchTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchTime_end\')}'});});
	$("#guideActivityTotaltb").find("input[name='searchTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_end}")
		.attr("id","searchTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchTime_begin\')}'});});
 });
 
 //查看导购的顾客列表
 function viewClickInfo(id){
	 searchTime_begin = $("#searchTime_begin").val();
	 searchTime_end = $("#searchTime_end").val();
	 var title = "活动点击明细";
	 var addurl = "guideActivityCountController.do?viewClickInfo&actId="+id+"&searchTime_begin="+searchTime_begin+"&searchTime_end="+searchTime_end;
	 createdetailwindow(title, addurl,500,600) ;
 }

//导出
function ExportXls() {
	JeecgExcelExport("guideActivityCountController.do?exportActivityTotalXls","guideActivityTotal");
}

 </script>