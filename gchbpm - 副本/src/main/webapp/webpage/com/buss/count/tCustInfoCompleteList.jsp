<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tCustInfoCompleteList" checkbox="true" fitColumns="false" title="顾客资料完成" 
  actionUrl="guideCountController.do?datagridOfCustInfo" idField="guideId" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="guideId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购姓名"  field="guideName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="店铺"  field="storeId" query="true" dictionary="t_store,id,name, status='A' and retailer_id = '${rId}'"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="顾客总数"  field="totalNum"    queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="100%"  field="p100"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="90%"  field="p90"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="80%"  field="p80"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="70%"  field="p70"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="60%"  field="p60"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="50%"  field="p50"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="40%"  field="p40"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="30%"  field="p30"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="20%"  field="p20"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="10%"  field="p10"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="0%"  field="p0"    queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tCustInfoCompleteController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });

//导出
function ExportXls() {
	JeecgExcelExport("guideCountController.do?exportCustInfoCompleteXls","tCustInfoCompleteList");
}

 </script>