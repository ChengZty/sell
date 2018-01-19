<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBalanceList" checkbox="true" fitColumns="false" title="余额" actionUrl="tBalanceController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="顾客昵称"  field="name" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="手机号"  field="phoneNo" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="余额"  field="totalBalance"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="可用余额"  field="availableBalance"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="冻结余额"  field="frozenBalance"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="折扣"  field="discount"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="balanceStatus"  replace="有效_1,失效_2,冻结_3"  queryMode="single"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/balance/tBalanceList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tBalanceListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tBalanceListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 

//导出
function ExportXls() {
	JeecgExcelExport("tBalanceController.do?exportXls","tBalanceList");
}

 </script>