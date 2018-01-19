<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tDayAwardDetailList" checkbox="true" fitColumns="false" title="每日奖励明细" actionUrl="tDayAwardDetailController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="导购"  field="guideName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <c:if test="${userType !='01' }">
   <t:dgCol title="所属店铺"  field="storeId" replace="${stores}" query="true"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <c:if test="${userType =='01' }">
   <t:dgCol title="所属店铺"  field="storeName"  queryMode="group"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="金币总数"  field="goldTotal"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="每日完善"  field="dayCustomer"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="每日推荐"  field="dayFirst"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="每日发布"  field="dayPublish"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="每日推送"  field="dayRecommend"  queryMode="group"  width="100"></t:dgCol>
   <%-- <t:dgCol title="绑定顾客总量"  field="bindingSum"  queryMode="group"  width="100"></t:dgCol> --%>
   <t:dgCol title="管家点评总量"  field="goodsSum"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="商品发布数"  field="recomendGuideSum"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="推送总量"  field="recomendSum"  queryMode="group"  width="100"></t:dgCol>
	<t:dgCol title="日期"  field="jobDate" formatter="yyyy-MM-dd" query="true" queryMode="group"  width="120"></t:dgCol>
   <c:if test="${userType =='01' }">
   <t:dgCol title="零售商"  field="toRetailerId"  query="true" dictionary="t_s_user,id,realname,user_type='02' and status = 'A' and retailer_type = '1' and user_status = '1'"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tDayAwardDetailController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  <input type="hidden" name="notInitSearch" value="1"/>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 	//给时间控件加上样式
	$("#tDayAwardDetailListtb").find("input[name='jobDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${initDate}")
		.attr("id","jobDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'jobDate_end\')}'});});
	$("#tDayAwardDetailListtb").find("input[name='jobDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${initDate}")
		.attr("id","jobDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'jobDate_begin\')}'});});
 });
 
 
//导出
function ExportXls() {
	JeecgExcelExport("tDayAwardDetailController.do?exportXls","tDayAwardDetailList");
}

 </script>