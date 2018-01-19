<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tTicketSendList" checkbox="true" fitColumns="false" title="分配券列表" actionUrl="tTicketSendController.do?datagrid&user_Id=${user_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="券名"  field="ticketName"    queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="导购ID"  field="userId" hidden="true"   queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="分配人"  field="realname"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分配时间"  field="addTime" formatter="yyyy-MM-dd"   queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="总张数"  field="sheet"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="发放张数"  field="sheetGive"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="剩余张数"  field="sheetRemain"    queryMode="single"  width="60"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="tTicketSendController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tTicketSendController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tTicketSendController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tTicketSendController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tTicketSendController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tTicketSendListtb").find("input[name='createDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tTicketSendListtb").find("input[name='updateDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tTicketSendListtb").find("input[name='addTime']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 </script>