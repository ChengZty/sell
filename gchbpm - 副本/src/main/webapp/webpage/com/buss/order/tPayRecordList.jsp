<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPayRecordList" checkbox="false" fitColumns="false" title="付款记录表" sortName="createDate" sortOrder="desc" actionUrl="tPayRecordController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo" query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="付款总额"  field="payAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="用户名"  field="createBy"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="createName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="150"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/order/tPayRecordList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tPayRecordListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 </script>