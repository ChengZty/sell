<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOrderDetailListR" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="订单明细" 
  actionUrl="tOrderDetailController.do?datagrid"  onLoadSuccess="mergeColumsFun"
  idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="主单ID"  field="orderId"  hidden="true"  queryMode="single" width="110" ></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"  query="true"  queryMode="single" width="110"></t:dgCol>
   <t:dgCol title="子订单号"  field="subOrderNo"  hidden="true"  queryMode="single" width="130"></t:dgCol>
   <t:dgCol title="图片"  field="goodsPic"  image="true" imageSize="80,80"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"    queryMode="single" width="120" ></t:dgCol>
   <t:dgCol title="款号"  field="goodsCode"    queryMode="single" width="80" ></t:dgCol>
   <t:dgCol title="规格"  field="specInfo"    queryMode="single" width="80" ></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"  hidden="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="售价"  field="priceNow"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="数量"  field="quantity"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="商品金额"  field="goodsAmount"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="订单总额"  field="orderAmount" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="优惠金额"  field="ticketPreferential"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="实付总额"  field="payAmount"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="买家手机"  field="userPhone"  query="true"  queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="支付方式"  field="payMethod"     queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="导购"  field="guideName" query="true" queryMode="single" width="60"  ></t:dgCol>
   <t:dgCol title="导购手机"  field="guidePhone" query="true"  queryMode="single" width="90" ></t:dgCol>
   <t:dgCol title="店铺"  field="storeName"  queryMode="single" width="90" ></t:dgCol>
<%--    <t:dgCol title="所属馆"  field="area"     queryMode="single"  width="100"  replace=" _null"  ></t:dgCol> --%>
   <t:dgCol title="订单状态"  field="orderStatus" replace="待付款_1,待发货_2,已发货_3,已完成_4,已退款_8,已取消_9" query="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="tOrderInfoController.do?goView" funname="goViewBySelect"></t:dgToolBar>
   <t:dgToolBar title="导出订单" icon="icon-putout" url="tOrderDetailController.do?exportDetailsXls&isTest=0" funname="ExportDetailsXls"></t:dgToolBar>
<%--    <t:dgToolBar title="导出主单" icon="icon-putout" url="tOrderDetailController.do?exportInfoXls&isTest=0" funname="ExportInfoXls"></t:dgToolBar> --%>
   <c:if test="${isGCH == 'Y' }">
   <t:dgToolBar title="导出测试订单" icon="icon-putout" url="tOrderDetailController.do?exportDetailsXls&isTest=1" funname="ExportDetailsXls"></t:dgToolBar>
<%--    <t:dgToolBar title="导出测试主单" icon="icon-putout" url="tOrderDetailController.do?exportDetailsXls&isTest=1" funname="ExportInfoXls"></t:dgToolBar> --%>
   </c:if>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/order/tOrderDetailList.js?v=1.03"></script>		
 <script src = "webpage/com/buss/order/mergeColums.js"></script>	
 <script type="text/javascript">
 $(document).ready(function(){
	//给时间控件加上样式
	$("#tOrderDetailListRtb").find("input[name='payTime_begin']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${start_time}")
		.attr("id","payTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'payTime_end\')}'});});
	$("#tOrderDetailListRtb").find("input[name='payTime_end']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${end_time}")
		.attr("id","payTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'payTime_begin\')}'});});
 });
 
//  function goCancelOrder(id){
// 	 var url = "tOrderDetailController.do?goCancelOrder&id="+id;
// 	 createwindow('取消订单',url,360,150);
//  }

 
 function mergeColumsFun(data){
		var arr = data.rows;//json数组
		if(arr.length>0){
			var field = "orderId";//判断合并的字段（连续并且相同则合并）
			var mergeFields =["ck","orderNo","orderAmount","payAmount","ticketPreferential","userPhone","payTime","payMethod","guideName","guidePhone","storeName","orderStatus"];//合并的字段
			gridname='tOrderDetailListR';
			mergeColums(arr,field,mergeFields,gridname);//合并列
		}
		//绑定多行选中事件	
		 dealMultiSelect("orderNo");
 }	
 </script>