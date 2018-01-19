<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOrderInfoList" checkbox="true" fitColumns="false" sortName="orderTime" sortOrder="desc" title="待付款订单" actionUrl="tOrderInfoController.do?datagrid&orderStatus=1" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="下单时间"  field="orderTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="总件数"  field="quantityAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收货地址"  field="reciverDetailInfo"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付方式"  field="payMethod"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单状态"  field="orderStatus"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单总额"  field="orderAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="运费"  field="fareAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品总额"  field="goodsAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgConfOpt title="微信付款" url="tOrderInfoController.do?doPay&id={id}&payMethod=3" message="确认付款？"/>
   <t:dgConfOpt title="支付宝付款" url="tOrderInfoController.do?doPay&id={id}&payMethod=4" message="确认付款？"/>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/order/tOrderInfoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOrderInfoListtb").find("input[name='orderTime_begin']").attr("class","Wdate").attr("style","height:24px;width:120px;")
 			.attr("id","orderTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'orderTime_end\')}'});});
 			$("#tOrderInfoListtb").find("input[name='orderTime_end']").attr("class","Wdate").attr("style","height:24px;width:120px;")
 			.attr("id","orderTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'orderTime_begin\')}'});});
 });
 

 </script>