<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsTotalCountList" checkbox="true" fitColumns="false" title="商品浏览统计汇总报表" actionUrl="guideGoodsCountController.do?tGoodsTotalCountDatagrid" idField="id" fit="true" queryMode="group">
  <t:dgCol title="查询时间"  field="searchTime"  hidden="true" query="true"   queryMode="group"  width="150"></t:dgCol>
  
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"  hidden="true"     width="120"></t:dgCol>
   <t:dgCol title="一级类目"  field="topCategoryId" query="true"    dictionary="t_s_category,id,name,level = '1' and status = 'A' " queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="二级类目"  field="subCategoryId" query="true"    dictionary="t_s_category,id,name,level = '2' and status = 'A' " queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="三级类目"  field="thridCategoryId"  hidden="true"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName" query="true"     width="120"></t:dgCol>
   <t:dgCol title="商品款号"  field="code" query="true"    width="180"></t:dgCol>
   <t:dgCol title="点击次数"  field="totalClickNum"   width="120"></t:dgCol>
   <t:dgCol title="商品推送数量"  field="totalPushNum"  width="120"></t:dgCol>
   <t:dgCol title="管家点评数量"  field="totalPublishNum"   width="120"></t:dgCol>
   <t:dgCol title="加代购车数量"  field="totalCartNum"   width="120"></t:dgCol>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  <input type="hidden" name="notInitSearch" value="1"/><!-- 不是初始化查询 -->
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 	//给时间控件加上样式
	$("#tGoodsTotalCountListtb").find("input[name='searchTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_begin}")
		.attr("id","searchTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchTime_end\')}'});});
	$("#tGoodsTotalCountListtb").find("input[name='searchTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_end}")
		.attr("id","searchTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchTime_begin\')}'});});
 			
 });
 

//导出
function ExportXls() {
	JeecgExcelExport("guideGoodsCountController.do?exportGoodsTotalXls","tGoodsTotalCountList");
}
 </script>