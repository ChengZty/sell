<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tRefundReturnList"  fitColumns="false" title="退款退货" extendParams="nowrap:false,"  actionUrl="tRefundReturnController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="订单ID"  field="orderId"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="退款编号"  field="refundNo"   query="true" queryMode="single" width="120" ></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"  query="true"  queryMode="single" width="130" ></t:dgCol>
   <t:dgCol title="申请时间"  field="addTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="零售商"  field="retailerId"  dictionary="t_s_user,id,realname,user_type='02' and status = 'A' "  queryMode="single" width="120" ></t:dgCol>
   <t:dgCol title="零售商手机"  field="retailerPhone"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="买家ID"  field="userId"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="买家姓名"  field="userName"    queryMode="single"  ></t:dgCol>
   <t:dgCol title="买家手机"  field="userPhone"  query="true"  queryMode="single"  ></t:dgCol>
    <t:dgCol title="导购姓名"  field="guideName"  query="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="导购手机"  field="guidePhone" query="true"   queryMode="single"  ></t:dgCol>
   <t:dgCol title="导购店铺"  field="storeName"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="交易金额"  field="orderAmount"    queryMode="single"  ></t:dgCol>
<%--    <t:dgCol title="运费"  field="fare"    queryMode="single"  ></t:dgCol> --%>
   <t:dgCol title="退款金额"  field="refundAmount"    queryMode="single"  ></t:dgCol>
<%--    <t:dgCol title="退G+卡金额"  field="cardAmount"    queryMode="single"  ></t:dgCol> --%>
<%--    <t:dgCol title="退运费"  field="refundSource" replace="否_1,是_2"   queryMode="single"  ></t:dgCol> --%>
   <t:dgCol title="导购处理状态"  field="sellerStatus" dictionary="audit_sts"   queryMode="single"  ></t:dgCol>
<%--    <t:dgCol title="导购处理时间"  field="guideTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="130"></t:dgCol> --%>
   <t:dgCol title="退款状态"  field="refundStatus"  replace="待商家处理_2,商家拒绝_4,退款完成_5" query="true"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="卖家处理时间"  field="sellerTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="130"></t:dgCol>
<%--    <t:dgCol title="管理员备注"  field="adminRemark"  hidden="true"  queryMode="single"  ></t:dgCol> --%>
<%--    <t:dgCol title="快递单号"  field="deliveryNo"    queryMode="single"  ></t:dgCol> --%>
<%--    <t:dgCol title="延迟天数"  field="delayDays"  hidden="true"  queryMode="single"  ></t:dgCol> --%>
<%--    <t:dgCol title="收货备注"  field="receiveRemark"  hidden="true"  queryMode="single"  ></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
<%--    <t:dgDelOpt title="删除" url="tRefundReturnController.do?doDel&id={id}" /> --%>
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
	 window.open(url,'new') 
}

 </script>