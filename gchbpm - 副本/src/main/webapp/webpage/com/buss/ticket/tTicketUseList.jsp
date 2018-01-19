<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tTicketUseList" checkbox="true" fitColumns="false" title="优惠券使用" actionUrl="tTicketUseController.do?datagrid" idField="id"
   fit="true" queryMode="group" sortName="addTime" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="addTime" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="优惠券ID"  field="ticketId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="优惠券明细ID"  field="ticketDetailId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单号/退款单号"  field="orderNo"  query="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="子订单号"  field="subOrderNo"  query="true"  queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="完成时间"  field="businessDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="实际优惠额度"  field="ticketPreferential"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="货品所属零售商ID"  field="toGoodsStoreId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="用户ID"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="顾客姓名"  field="userName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购姓名"  field="guideName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购店铺"  field="storeName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="使用类型"  field="useType"  replace="收货_1,退款_2" query="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="tTicketUseController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tTicketUseController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tTicketUseController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tTicketUseController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tTicketUseController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tTicketUseListtb").find("input[name='addTime']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tTicketUseController.do?upload', "tTicketUseList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tTicketUseController.do?exportXls","tTicketUseList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tTicketUseController.do?exportXlsByT","tTicketUseList");
}
 </script>