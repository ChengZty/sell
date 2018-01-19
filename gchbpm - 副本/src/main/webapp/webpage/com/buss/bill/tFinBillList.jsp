<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tFinBillList" checkbox="false" fitColumns="true" title="财务账单" sortName="addTime" sortOrder="desc"  actionUrl="tFinBillController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="流水时间"  field="addTime" hidden="true"  ></t:dgCol>
   <t:dgCol title="账单编号"  field="billNo"  query="true"  queryMode="single" ></t:dgCol>
   <t:dgCol title="订单编号"  field="orderNo" query="true"   queryMode="single" ></t:dgCol>
   <t:dgCol title="业务日期"  field="businessDate" formatter="yyyy-MM-dd  hh:mm:ss"  query="true" queryMode="group"></t:dgCol>
   <t:dgCol title="业务类型"  field="businessType"   query="true" queryMode="single" replace="购物付款_1,退款_2,G+卡充值_3,退G+卡_4"></t:dgCol>
   <t:dgCol title="订单金额"  field="orderAmount"    queryMode="single" ></t:dgCol>
   <t:dgCol title="实付金额"  field="payAmount"    queryMode="single" ></t:dgCol>
   <t:dgCol title="优惠券"  field="ticketAmount"    queryMode="single" ></t:dgCol>
   <t:dgCol title="导购优惠"  field="guidePrivilegeAmount"    queryMode="single" ></t:dgCol>
   <t:dgCol title="件数"  field="quantityAmount"    queryMode="single" ></t:dgCol>
   <t:dgCol title="顾客实收"  field="custAmount"    queryMode="single" ></t:dgCol>
<%--    <t:dgCol title="导购实收"  field="guideAmount"    queryMode="single" ></t:dgCol> --%>
<%--    <t:dgCol title="活动奖励"  field="activityAmount"    queryMode="single" ></t:dgCol> --%>
<%--    <t:dgCol title="云商实收"  field="cloudAmount"   queryMode="single" ></t:dgCol> --%>
<%--    <t:dgCol title="系统实收"  field="systemAmount"  queryMode="single" ></t:dgCol> --%>
<%--    <t:dgCol title="手续费"  field="serviceCharge"   queryMode="single" ></t:dgCol> --%>
<%--    <t:dgCol title="帮手实收"  field="helperAmount"  hidden="true"  queryMode="single" ></t:dgCol> --%>
   <t:dgCol title="顾客ID"  field="custId" hidden="true"  queryMode="single" ></t:dgCol>
   <t:dgCol title="顾客"  field="custName"    queryMode="single" ></t:dgCol>
   <t:dgCol title="导购ID"  field="guideId" hidden="true" queryMode="single" ></t:dgCol>
   <t:dgCol title="导购"  field="guideName"    queryMode="single" ></t:dgCol>
   <t:dgCol title="店铺ID"  field="storeId" hidden="true" queryMode="single" ></t:dgCol>
   <t:dgCol title="导购所在店铺"  field="storeName" query="true" queryMode="single" ></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId" hidden="true" queryMode="single" ></t:dgCol>
   <t:dgCol title="零售商"  field="retailerName"    queryMode="single" ></t:dgCol>
   <t:dgCol title="状态"  field="tbStatus"    queryMode="single"  hidden="true" ></t:dgCol>
   <t:dgToolBar title="导出明细" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="导出导购统计" icon="icon-putout" funname="ExportGuideXls"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 
 <div id="tempSearchColums" style="display: none">
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="width: 50px; text-align: right; overflow: hidden; vertical-align: middle; display: inline-block; white-space: nowrap; text-overflow: ellipsis; -o-text-overflow: ellipsis;" title="零售商">零售商: </span>
			<input type="text" id="storeName" readonly="readonly" onchange="clearStoreId(this.value)">
			<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findStores()" ><span class="icon-search l-btn-icon-left">选择</span></a>
			<input id="storeId" name="rId" type="hidden" >
        </span>
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="width: 50px; text-align: right; overflow: hidden; vertical-align: middle; display: inline-block; white-space: nowrap; text-overflow: ellipsis; -o-text-overflow: ellipsis;" title="导购">导购: </span>
			<input type="text"  id="guideName" readonly="readonly" onchange="clearGuideId(this.value)">
			<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findGuides()" ><span class="icon-search l-btn-icon-left">选择</span></a>
			<input id="guideId" name="guideId" type="hidden" >
        </span>
</div>
 
 
 <script src = "webpage/com/buss/bill/tFinBillList.js?v=1.02"></script>		
 <script type="text/javascript">
 var rId;//零售商id
 $(document).ready(function(){
 		//给时间控件加上样式
//  $("#tFinBillListtb").find("input[name='addTime_begin']").attr("class","Wdate").attr("style","height:24px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  $("#tFinBillListtb").find("input[name='addTime_end']").attr("class","Wdate").attr("style","height:24px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd '});});
	$("#tFinBillListtb").find("input[name='businessDate_begin']").attr("class","Wdate").attr("style","height:24px;width:90px;")
	.attr("id","businessDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'businessDate_end\')}'});});
	$("#tFinBillListtb").find("input[name='businessDate_end']").attr("class","Wdate").attr("style","height:24px;width:90px;")
	.attr("id","businessDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'businessDate_begin\')}'});});
	//$("#tFinBillListtb").prepend($("#tempSearchColums div[name='searchColums']").parent().html());
	$("#tFinBillListtb").find("div[name='searchColums']").children().last().after($("#tempSearchColums").html());
	$("#tempSearchColums").remove();
 });

//导出
function ExportXls() {
	JeecgExcelExport("tFinBillController.do?exportXls","tFinBillList");
}
//导出
/* function ExportGuideXls() {
	JeecgExcelExport("tFinBillController.do?exportGuideXls","tFinBillList");
} */
</script>