<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
.datagrid-row .datagrid-td-rownumber{
	height:70px;
}
</style>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tStoreOrderInfoList" checkbox="true" fitColumns="false" extendParams="nowrap:false," sortName="payTime" sortOrder="desc" title="线下订单明细" 
  actionUrl="tStoreOrderInfoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"  query="true"  queryMode="single" width="110"></t:dgCol>
   <t:dgCol title="图片"  field="picUrl" image="true" imageSize="80,80" width="80"></t:dgCol>
   <t:dgCol title="顾客姓名"  field="userName" query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="顾客手机"  field="userPhone" query="true" queryMode="single" width="90" ></t:dgCol>
   <t:dgCol title="成交件数"  field="quantityAmount" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="成交金额"  field="payAmount" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="成交时间"  field="payTime" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="导购"  field="guideName" query="true" queryMode="single" width="90"  ></t:dgCol>
   <t:dgCol title="导购手机"  field="guidePhone" query="true"   queryMode="single" width="90" ></t:dgCol>
   <t:dgCol title="店铺"  field="storeId" query="true" replace="${stores}" queryMode="single" width="120"  ></t:dgCol>
   
   <t:dgToolBar title="查看" icon="icon-search" url="tStoreOrderInfoController.do?goView" funname="goViewBySelect"></t:dgToolBar>
   <t:dgToolBar title="导出订单" icon="icon-putout" url="tStoreOrderInfoController.do?exportXls" funname="ExportDetailsXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>	
 <script src = "webpage/com/buss/order/mergeColums.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 	//给时间控件加上样式
	$("#tStoreOrderInfoListtb").find("input[name='payTime_begin']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${payTime_begin}")
	.attr("id","payTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'payTime_end\')}'});});
	$("#tStoreOrderInfoListtb").find("input[name='payTime_end']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${payTime_end}")
	.attr("id","payTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'payTime_begin\')}'});});
 });
 
 /**
  * 查看选中的订单
  */
 function goViewBySelect(title,url,gname){
 	 var rowsData = $('#'+gname).datagrid('getSelections');
 		if (!rowsData || rowsData.length == 0) {
 			tip('请选择查看记录');
 			return;
 		}
 		if (rowsData.length > 1) {
 			var isSame = true;//多条明细的单号相同
 			var orderNo = rowsData[0].orderNo;
 			$.each(rowsData, function (index, rowData){
 				if(orderNo !=rowData.orderNo){
 					isSame = false;
 				}
 			});
 			if(!isSame){
 				tip('请选择一条记录再查看');
 				return;
 			}
 		}
 	    url += '&id='+rowsData[0].id;
 	 window.open(url,'new') 
 }

 /**
  * 导出订单明细
  */
 function ExportDetailsXls(title,url,gname) {
 	JeecgExcelExport(url,gname);
 }
 </script>