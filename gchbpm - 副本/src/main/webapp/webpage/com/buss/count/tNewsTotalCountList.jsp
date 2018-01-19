<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewsTotalCountList" checkbox="true" fitColumns="false" title="话题浏览统计汇总报表" actionUrl="guideNewsCountController.do?tNewsTotalCountDatagrid" idField="id" fit="true" queryMode="group">
  <t:dgCol title="查询时间"  field="searchTime"  hidden="true" query="true"   queryMode="group"  width="150"></t:dgCol>
  
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="话题分类"  field="newsType" query="true" dictionary="t_news_type,code,name,status = 'A' and platform_type='2' and retailer_id = '${rId }'"   width="200"></t:dgCol>
   <t:dgCol title="话题标题"  field="title"   query="true"    width="200"></t:dgCol>
   <t:dgCol title="点击次数"  field="clickNum"   width="120"></t:dgCol>
   <t:dgCol title="点赞数量"  field="goodNum"  width="120"></t:dgCol>
   <t:dgCol title="无感数量"  field="noSenseNum" width="120"></t:dgCol>
   <t:dgCol title="收藏数量"  field="collectNum"     width="120"></t:dgCol>
   <t:dgCol title="评论数量"  field="commentNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="推送数量"  field="totalPushNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="通讯录推送数量"  field="contPushNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="微信推送数量"  field="wxPushNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="朋友圈推送数量"  field="wcmPushNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="QQ推送数量"  field="qqPushNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  <input type="hidden" name="notInitSearch" value="1"/><!-- 不是初始化查询 -->
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 	//给时间控件加上样式
	$("#tNewsTotalCountListtb").find("input[name='searchTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_begin}")
		.attr("id","searchTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchTime_end\')}'});});
	$("#tNewsTotalCountListtb").find("input[name='searchTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_end}")
		.attr("id","searchTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchTime_begin\')}'});});
 });
 

//导出
function ExportXls() {
	JeecgExcelExport("guideNewsCountController.do?exportNewsTotalXls","tNewsTotalCountList");
}


 </script>