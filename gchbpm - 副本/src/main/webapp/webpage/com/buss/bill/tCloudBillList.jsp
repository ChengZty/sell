<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tCloudBillList" checkbox="true" fitColumns="false" title="零售商账单明细" sortName="addTime" sortOrder="desc" actionUrl="tCloudBillController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="财务结算表ID"  field="finBillId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="流水时间"  field="addTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="订/退款单号"  field="orderNo"   query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="子订单编号"  field="subOrderNo"   query="true" queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="业务日期"  field="businessDate" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="金额"  field="money"    queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="零售商实收"  field="cloudMoney"    queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="运费"  field="fareMoney"    queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="零售商ID"  field="storeId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="货品所属零售商"  field="toStoreGoodsId"  hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商名字"  field="toStoreGoodsName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务类型"  field="businessType" hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="零售商类型"  field="toStoreType" replace="零售商_1,云商_2"  queryMode="single"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="品牌ID"  field="brandId"  hidden="true"  queryMode="single"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgFunOpt funname="goView(orderNo,subOrderNo,businessType)" title="查看订单" exp="subOrderNo#empty#false"></t:dgFunOpt> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tCloudBillController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/bill/tCloudBillList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 	//给时间控件加上样式
//  $("#tCloudBillListtb").find("input[name='addTime']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tCloudBillListtb").find("input[name='addTime_begin']").attr("class","Wdate")
	.attr("id","addTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTime_end\')}'});});
	$("#tCloudBillListtb").find("input[name='addTime_end']").attr("class","Wdate")
	.attr("id","addTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTime_begin\')}'});});
	$("#tCloudBillListtb").find("input[name='businessDate_begin']").attr("class","Wdate")
	.attr("id","businessDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'businessDate_end\')}'});});
	$("#tCloudBillListtb").find("input[name='businessDate_end']").attr("class","Wdate")
	.attr("id","businessDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'businessDate_begin\')}'});});
 });
 
 function goView(orderNo,subOrderNo,businessType){
	 if(businessType=="1"){//收货
		 var url = "tOrderDetailController.do?goView&subOrderNo="+subOrderNo;
		 window.open(url,'new') 
	 }else if(businessType=="2"){//退款
		 var url = "tRefundReturnController.do?goView&subOrderNo="+subOrderNo;
		 window.open(url,'new')
	 }
 }
 
//导出
function ExportXls() {
	JeecgExcelExport("tCloudBillController.do?exportXls","tCloudBillList");
}

//不显示前面6位单号
function renameOrderNo(value,rec,index){
//		return value;
		return value.substring(6,value.length);
}
 </script>