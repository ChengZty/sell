<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOrderDetailListFh" checkbox="true" fitColumns="false" onLoadSuccess="mergeColumsFun" extendParams="nowrap:false," title="订单明细" 
  actionUrl="tOrderDetailController.do?datagrid&order_status=${order_status }" idField="id" fit="true" queryMode="group" autoLoadData="false">
  <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="主单ID"  field="orderId"  hidden="true"  queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo" query="true"  queryMode="single" width="110" ></t:dgCol>
   <t:dgCol title="子订单号"  field="subOrderNo"  hidden="true" queryMode="single" width="130"></t:dgCol>
   <t:dgCol title="图片"  field="goodsPic"  image="true" imageSize="80,80"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"    queryMode="single" width="150" ></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"  hidden="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="售价"  field="priceNow"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="数量"  field="quantity"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="商品金额"  field="goodsAmount"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="订单总额"  field="orderAmount" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="优惠金额"  field="ticketPreferential"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="实付总额"  field="payAmount"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="买家手机"  field="userPhone"  query="true"  queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="支付方式"  field="payMethod"     queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="发货方式"  field="deliveryType" query="true" replace="顾客自提_0,后台发货_1,店铺发货_2"   queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="收货人姓名"  field="reciverName"     queryMode="single" width="70" ></t:dgCol>
   <t:dgCol title="收货人电话"  field="reciverPhone"     queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="收货人地址"  field="reciverDetailInfo"     queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="买家留言"  field="orderMessage"     queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="导购"  field="guideName"  queryMode="single" width="60"  ></t:dgCol>
   <t:dgCol title="导购手机"  field="guidePhone" query="true"   queryMode="single" width="90" ></t:dgCol>
   <t:dgCol title="店铺"  field="storeName" query="true"   queryMode="single" width="90" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="140" ></t:dgCol>
   <t:dgFunOpt funname="goView(orderId)" title="查看订单"></t:dgFunOpt>
   <t:dgFunOpt funname="goChangeAddress(orderId)" title="修改地址"></t:dgFunOpt>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tOrderDetailController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
   <t:dgToolBar title="批量勾选发货" icon="icon-putout" url="tOrderInfoController.do?goFahuo" funname="fahuoALLSelect" width="700" height="400"></t:dgToolBar>
   <t:dgToolBar title="批量导入发货" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-put" funname="downLoadFahuoT"></t:dgToolBar>
   <t:dgToolBar title="导出发货单" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/order/tOrderDetailList.js?v=1.02"></script>
 <script src = "webpage/com/buss/order/mergeColums.js"></script>	
 <script src = "webpage/com/buss/order/tOrderDetailsList-fahuo.js?v=1.02"></script>	
  <link rel="stylesheet" href="plug-in/html5uploader/html5uploader.css">
 <script src="plug-in/html5uploader/jquery.html5uploader.js?v=1.0"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 			$("#tOrderDetailListFhtb").find("input[name='payTime_begin']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${start_time}")
 			.attr("id","payTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'payTime_end\')}'});});
 			$("#tOrderDetailListFhtb").find("input[name='payTime_end']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${end_time}")
 			.attr("id","payTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'payTime_begin\')}'});});
 			$("#tOrderDetailListFhtb").find("select[name='deliveryType']").val(1);
 			//获取待发货主单数量
 			updateToBeFahuoCount();
 			setTimeout(tOrderDetailListFhsearch,200);
//  			tOrderDetailListFhsearch();
 });
 
function ImportXls() {
	  openuploadwinH5('发货导入', 'tOrderInfoController.do?importExcel', "tOrderDetailListFh",updateToBeFahuoCount);
}

//合并列
 function mergeColumsFun(data){
 	var arr = data.rows;
 	if(arr.length>0){
 		var field = "orderId";//判断合并的字段（连续并且相同则合并）
 		var mergeFields =["ck","orderNo","orderAmount","payAmount","ticketPreferential","userPhone","payTime","payMethod","deliveryType","reciverName","reciverPhone","reciverDetailInfo","orderMessage","guideName","guidePhone","storeName","opt"];//合并的字段
 		gridname='tOrderDetailListFh';
 		mergeColums(arr,field,mergeFields,gridname);//合并列
 	}
	//绑定多行选中事件	
	 dealMultiSelect("orderNo");
 }

 </script>
</body>