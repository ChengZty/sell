<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tActivityBillList" checkbox="true" fitColumns="false" title="活动奖励明细" actionUrl="tActivityBillController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="addTime" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="财务结算ID"  field="finBillId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="添加时间"  field="addTime" formatter="yyyy-MM-dd" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="下单时间"  field="orderTime"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务日期"  field="businessDate" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="活动奖励"  field="money"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务类型"  field="businessType"  replace="购物_1,退款_2"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="子单号"  field="subOrderNo" query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品ID"  field="goodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品所属零售商"  field="toStoreName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属零售商类型"  field="storeType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购"  field="guideName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动名称"  field="finActTitle"    queryMode="single"  width="120"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="tActivityBillController.do?goUpdate" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tActivityBillListtb").find("input[name='finBillId']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tActivityBillListtb").find("input[name='businessDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 

//导出
function ExportXls() {
	JeecgExcelExport("tActivityBillController.do?exportXls","tActivityBillList");
}

 </script>