<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tTicketInfoList" checkbox="true" fitColumns="true" title="优惠券" actionUrl="tTicketInfoController.do?datagridOfRetailer&ticket_Status=2&userIds=${userIds }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="批次号"  field="batchNo"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商类型"  field="retailerType" hidden="true" queryMode="single" width="60"></t:dgCol>
   <t:dgCol title="券名"  field="ticketName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="面值(元)"  field="faceValue"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="使用条件"  field="leastMoney"  formatterjs="showCondition"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="折扣"  field="discount"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="起始时间"  field="beginTime" formatter="yyyy-MM-dd"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="总张数"  field="sheetTotal"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="已分配张数"  field="sheetSent"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="剩余张数"  field="sheetRemain"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="类型"  field="type"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="适用范围"  field="range"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"    queryMode="single"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/ticket/tTicketInfoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tTicketInfoListtb").find("input[name='createDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tTicketInfoListtb").find("input[name='updateDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tTicketInfoListtb").prepend("<div style='padding-left:20px;margin-bottom:10px;'><div><input type='hidden' name='userIds' value='${userIds}'/>${names}</div><div>人均张数：<input type='text' name='sheetNum' id='sheetNum' style='width:50px;'/></div></div>");
 			$("#tTicketInfoListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>起始时间：</span><input name='begin_Time' type='text' class='Wdate' style='width:90px'/>");
 			$("#tTicketInfoListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>结束时间：</span><input name='end_Time' type='text' class='Wdate' style='width:90px'/>");
 			$("#tTicketInfoListForm").find("input[name='begin_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tTicketInfoListForm").find("input[name='end_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#sheetNum").focus();
 });
 
//获取使用条件
 function showCondition(value,row,index){
 	if(value==0){
 		return "无条件使用";
 	}else{
 		return "满"+value+"元使用";
 	}
 } 
 </script>