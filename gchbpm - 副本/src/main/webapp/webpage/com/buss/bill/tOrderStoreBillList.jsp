<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOrderBillList" checkbox="false" fitColumns="false" title="订单结算表" actionUrl="tOrderBillController.do?datagridStore" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结算单编号"  field="orderBillSn"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结算状态"  field="obState"   query="true" queryMode="single" dictionary="ob_status" width="120"></t:dgCol>
   <t:dgCol title="开始日期"  field="startTime" formatter="yyyy-MM-dd"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结束日期"  field="endTime" formatter="yyyy-MM-dd"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结算对象ID"  field="obPayerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="obPayerName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="手机号"  field="obPayerPhone"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单金额"  field="obOrderTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="运费金额"  field="osShippingTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="佣金金额"  field="obCommisTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="退单金额"  field="obOrderReturnTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="退还佣金"  field="obCommisReturnTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="应结金额"  field="obResultTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="生成结算单日期"  field="obCreateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结算单年月份"  field="obCreateMonth"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="付款日期"  field="obPayDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付备注"  field="obPayContent"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="obStoreId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商名称"  field="obStoreName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/bill/tOrderBillList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOrderBillListtb").find("input[name='startTime']").attr("class","Wdate").attr("style","height:20px;width:90px;")
 			.attr("id","startTime").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'});});
 			$("#tOrderBillListtb").find("input[name='endTime']").attr("class","Wdate").attr("style","height:20px;width:90px;")
 			.attr("id","endTime").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'});});
 			$("#tOrderBillListtb").find("input[name='obCreateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
//导出
function ExportXls() {
	JeecgExcelExport("tOrderBillController.do?exportXlsByStore","tOrderBillList");
}
</script>