<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBillList" checkbox="true" fitColumns="false" title="账单" actionUrl="tBillController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="流水号"  field="billNo"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单/充值单号"  field="orderNo"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帐号ID"  field="userId"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="顾客昵称"  field="name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="手机号"  field="phoneNo"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="对方手机号"  field="otherPhoneNo"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="交易金额"  field="billAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="交易时间"  field="businessDate" formatter="yyyy-MM-dd hh:mm:ss" query="true"  queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="交易类型"  field="billType"  replace="充值_1,购物_2,g+充值_3,其他支付_4" query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="订单ID/充值卡ID"  field="orderId"    queryMode="single"  width="250"></t:dgCol> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 	//给时间控件加上样式
	$("#tBillListtb").find("input[name='businessDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
	.attr("id","businessDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'businessDate_end\')}'});});
	$("#tBillListtb").find("input[name='businessDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
	.attr("id","businessDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'businessDate_begin\')}'});});
 });
 

//导出
function ExportXls() {
	JeecgExcelExport("tBillController.do?exportXls","tBillList");
}
 </script>