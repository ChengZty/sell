<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tRechargeList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="充值" actionUrl="tRechargeController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="addTime" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帐号ID"  field="userId"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="充值单号"  field="orderNo"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="顾客昵称"  field="name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="手机号"  field="phoneNo"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购ID"  field="guideId"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购姓名"  field="realname"  query="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="充值金额"  field="money"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="支付金额"  field="payMoney"    queryMode="single"  width="80"></t:dgCol>
   <c:if test="${userType == '01' }">
   <t:dgCol title="手续费"  field="serviceCharge"    queryMode="single"  width="80"></t:dgCol>
   </c:if>
   <t:dgCol title="下单时间"  field="addTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="充值时间"  field="payTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group" query="true" width="130"></t:dgCol>
   <t:dgCol title="充值人"  field="createName"   width="100"></t:dgCol>
   <t:dgCol title="支付状态"  field="payStatus" replace="待付款_1,支付成功_2,支付失败_3"  query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="支付方式"  field="payType" replace="支付宝_1,微信_2,平台_3" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="折扣"  field="discount"   queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tRechargeController.do?goUpdate" funname="detail"></t:dgToolBar>
	<c:if test="${userType == '01' }">
	   <t:dgToolBar title="充值" icon="icon-add" url="tRechargeController.do?goAdd" funname="add" height="250"></t:dgToolBar>
	</c:if>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tRechargeListtb").find("input[name='payTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
 			.attr("id","payTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'payTime_end\')}'});});
 			$("#tRechargeListtb").find("input[name='payTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
 			.attr("id","payTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'payTime_begin\')}'});});
 });
 

//导出
function ExportXls() {
	JeecgExcelExport("tRechargeController.do?exportXls","tRechargeList");
}
 </script>