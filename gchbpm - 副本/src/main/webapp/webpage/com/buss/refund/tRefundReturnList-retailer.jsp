<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tRefundReturnList"  fitColumns="false" extendParams="nowrap:false," title="退款退货" sortName="addTime" sortOrder="desc" actionUrl="tRefundReturnController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="退款编号"  field="refundNo"   query="true" queryMode="single" width="120" ></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"  query="true" queryMode="single"  ></t:dgCol>
   <t:dgCol title="商户订单号"  field="orderId" query="true" queryMode="single"  ></t:dgCol>
   <t:dgCol title="子订单号"  field="subOrderNo" hidden="true"   queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="申请时间"  field="addTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="买家姓名"  field="userName"  query="true" queryMode="single"  ></t:dgCol>
   <t:dgCol title="买家手机"  field="userPhone" query="true"   queryMode="single"  ></t:dgCol>
   <t:dgCol title="导购姓名"  field="guideName"  query="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="导购手机"  field="guidePhone" query="true"   queryMode="single"  ></t:dgCol>
   <t:dgCol title="导购店铺"  field="storeName"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="交易金额"  field="orderAmount"    queryMode="single"  ></t:dgCol>
   <t:dgCol title="付款方式"  field="payMethodName" queryMode="single"  ></t:dgCol>
   <t:dgCol title="付款帐号"  field="payAccount"  queryMode="single"  ></t:dgCol>
<%--    <t:dgCol title="运费"  field="fare"    queryMode="single"  ></t:dgCol> --%>
   <t:dgCol title="申请退款金额"  field="refundAmount"    queryMode="single"  ></t:dgCol>
   <t:dgCol title="实际退款金额"  field="refundAmountReal"    queryMode="single"  ></t:dgCol>
<%--    <t:dgCol title="退G+卡金额"  field="cardAmount"    queryMode="single"  ></t:dgCol> --%>
<%--    <t:dgCol title="退运费"  field="refundSource" replace="否_1,是_2"   queryMode="single"  ></t:dgCol> --%>
   <t:dgCol title="退款状态"  field="refundStatus" query="true" replace="待商家处理_2,商家拒绝_4,退款完成_5"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="卖家处理时间"  field="sellerTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="250"></t:dgCol>
   <t:dgFunOpt funname="goToPayWebPage(payMethodName)" exp="refundStatus#eq#2" title="去退款"></t:dgFunOpt>
   <t:dgFunOpt funname="goToPay(id)" exp="refundStatus#eq#2" title="退款完成"></t:dgFunOpt>
   <t:dgFunOpt funname="disAgree(id)" exp="refundStatus#eq#2" title="拒绝退款"></t:dgFunOpt>
<%--    <t:dgConfOpt title="同意退款" url="tRefundReturnController.do?doAgree&id={id}" exp="refundStatus#eq#2" message="确定同意退款？"></t:dgConfOpt> --%>
<%--    <t:dgConfOpt title="拒绝退款" url="tRefundReturnController.do?doNotAgree&id={id}"  exp="refundStatus#eq#2"  message="确定不同意退款？"></t:dgConfOpt> --%>
   <t:dgFunOpt funname="goView(id)"  title="查看"></t:dgFunOpt>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tRefundReturnListtb").find("input[name='addTime_begin']").attr("class","Wdate").attr("style","height:24px;width:90px;")
 			.attr("id","addTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTime_end\')}'});});
 			$("#tRefundReturnListtb").find("input[name='addTime_end']").attr("class","Wdate").attr("style","height:24px;width:90px;")
 			.attr("id","addTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTime_begin\')}'});});
 });
 
 function goView(id) {
	 var url = "tRefundReturnController.do?goView&id="+id;
	 window.open(url,'new');
}
 function goToPayWebPage(payMethodName) {
	 if(payMethodName.indexOf("微信") >= 0){
		 window.open('https://pay.weixin.qq.com/','w');
	 }else if(payMethodName.indexOf("支付宝")>=0){
		 window.open('https://b.alipay.com/','z');
	 }
}

 //同意退款
 function goToPay(id) {
	 var url = "tRefundReturnController.do?goToPay&id="+id;
	 gridname="tRefundReturnList";
	createwindow("退款", url,500,220);
}
 
 //拒绝退款
 function disAgree(id) {
	 var url = "tRefundReturnController.do?goToPay&operateType=0&id="+id;
	 gridname="tRefundReturnList";
	createwindow("拒绝退款", url,500,220);
}
 </script>