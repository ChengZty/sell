<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="guideCountList" checkbox="false" fitColumns="false" title="导购统计" actionUrl="guideCountController.do?datagrid" idField="userId" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="userId" hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="导购"  field="guideName" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="所属店铺"  field="storeId" replace="${stores}" query="true"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="查询时间"  field="searchTime" hidden="true"  query="true" formatter="yyyy-MM-dd" queryMode="group"  width="90"></t:dgCol>
   <t:dgCol title="app点击数"  field="appClick" queryMode="single" width="90"></t:dgCol>
   <t:dgCol title="商品点击数"  field="goodsClick" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="商品推送数"  field="goodsPushNum" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="订单推送数"  field="orderPushNum" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="资讯点击数"  field="newClickNum" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="资讯推送数"  field="newsPushNum" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="待付款数"  field="toBePayNum" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="成交单数"  field="dealNum" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="活动成交单数"  field="actOrderNum" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="活动成交总金额"  field="actOrderPrice" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="成交总金额"  field="dealMoney" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="成交总件数"  field="quantityAmount" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="退款总金额"  field="refundAmount" queryMode="single"  width="100"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="guideCountController.do?doDel&userId={userId}" /> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tNewsTypeController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
	$(document).ready(function(){
		//给时间控件加上样式
		$("#guideCountListtb").find("input[name='searchTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","searchTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchTime_end\')}'});});
		$("#guideCountListtb").find("input[name='searchTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","searchTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchTime_begin\')}'});});
			
	});
 
//导出
 function ExportXls() {
	 var total = $('#guideCountList').datagrid('getData').total;
 	JeecgExcelExport("guideCountController.do?exportXls","guideCountList",1,total);
 }
 </script>