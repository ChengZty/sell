<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="orderBillDetailList" checkbox="true" fitColumns="false" title="结算明细" actionUrl="orderBillDetailController.do?storeDatagrid&obState=${obState}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="账单编号"  field="obOrderSn"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="名称"  field="obPayerName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="本期应结"  field="obResultTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="obState"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开始日期"  field="obStartDate" query="true" formatter="yyyy-MM-dd" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结束日期"  field="obEndDate"  query="true" formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="年份"  field="obYear"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="月份"  field="obMonth" formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="出账日期"  field="obPayDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单金额"  field="obOrderTotals"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="运费"  field="obShippingTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="佣金金额"  field="obCommisTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="退款金额"  field="obOrderReturnTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="退还佣金"  field="obCommisReturnTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结算对象ID"  field="obPayerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/bill/orderBillDetailList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 	//给时间控件加上样式
	$("#orderBillDetailListtb").find("input[name='obYear']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy'});});
	$("#orderBillDetailListtb").find("input[name='obMonth']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'MM'});});
	$("#orderBillDetailListtb").find("input[name='obStartDate']").attr("class","Wdate").attr("style","height:20px;width:90px;")
	.attr("id","obStartDate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'obEndDate\')}'});});
	$("#orderBillDetailListtb").find("input[name='obEndDate']").attr("class","Wdate").attr("style","height:20px;width:90px;")
	.attr("id","obEndDate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'obStartDate\')}'});});
 });

//导出
function ExportXls() {
	JeecgExcelExport("orderBillDetailController.do?exportXls","orderBillDetailList");
}
 </script>