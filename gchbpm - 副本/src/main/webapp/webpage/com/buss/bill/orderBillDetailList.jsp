<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="orderBillDetailList" checkbox="true" fitColumns="false" title="结算明细" actionUrl="orderBillDetailController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单编号"  field="obOrderSn"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="年份"  field="obYear"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="月份"  field="obMonth" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开始日期"  field="obStartDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结束日期"  field="obEndDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="出账日期"  field="obPayDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单金额"  field="obOrderTotals"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="运费"  field="obShippingTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="佣金金额"  field="obCommisTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="退款金额"  field="obOrderReturnTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="退还佣金"  field="obCommisReturnTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="本期应结"  field="obResultTotals"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="obState"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结算对象ID"  field="obPayerId"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结算对象名称"  field="obPayerName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="orderBillDetailController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="orderBillDetailController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="orderBillDetailController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="orderBillDetailController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="orderBillDetailController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/bill/orderBillDetailList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#orderBillDetailListtb").find("input[name='obMonth']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#orderBillDetailListtb").find("input[name='obPayDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'orderBillDetailController.do?upload', "orderBillDetailList");
}

//导出
function ExportXls() {
	JeecgExcelExport("orderBillDetailController.do?exportXls","orderBillDetailList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("orderBillDetailController.do?exportXlsByT","orderBillDetailList");
}
 </script>