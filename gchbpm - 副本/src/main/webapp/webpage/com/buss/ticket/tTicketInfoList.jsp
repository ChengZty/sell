<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tTicketInfoList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="优惠券列表" sortName="updateDate" sortOrder="desc" 
  actionUrl="tTicketInfoController.do?datagrid" idField="id"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="批次号"  field="batchNo" hidden="true"  queryMode="single"  width="110"></t:dgCol>
   <t:dgCol title="券名"  field="ticketName" query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="类型"  field="type" query="true" dictionary="tkt_type" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="面值/折扣"  field="faceValue"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="使用条件"  field="leastMoney"  formatterjs="showCondition"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="使用类型"  field="useType" hidden="true"   queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="起始时间"  field="beginTime" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="零售商"  field="retailerId"  dictionary="t_s_user,id,realname,user_type='02' and status = 'A' "   width="90"></t:dgCol>
<%--    <t:dgCol title="零售商类型"  field="retailerType" hidden="true" queryMode="single" width="60"></t:dgCol> --%>
   <t:dgCol title="审核状态"  field="ticketStatus" query="true" replace="待审核_1,已审核_2,已停用_3"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="操作人"  field="auditor"  width="80"></t:dgCol>
   <t:dgCol title="总张数"  field="sheetTotal"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="已领取张数"  field="sheetSent"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="剩余领取张数"  field="sheetRemain"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="使用张数"  field="sheetUsed"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="适用范围"  field="usingRange"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="使用说明"  field="remark"    queryMode="single"  width="150"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="90" align="center"></t:dgCol>
   <t:dgFunOpt title="详情"  funname="viewDetail(id)" exp="ticketStatus#ne#1"/>
<%--    <t:dgConfOpt title="审核" url="tTicketInfoController.do?doAudit&id={id}" exp="ticketStatus#eq#1" message="确认审核通过？"/> --%>
<%--    <t:dgFunOpt funname="goUpdate(id)"  title="编辑" exp="ticketStatus#eq#1" ></t:dgFunOpt> --%>
<%--    <t:dgFunOpt funname="getTicketGoodsList(id,ticketStatus,ticketName,retailerId)" title="券商品" exp="usingRange#eq#3"></t:dgFunOpt> --%>
<%--    <t:dgToolBar title="批量审核" icon="icon-edit" url="" funname="doBatchAudit"></t:dgToolBar> --%>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tTicketInfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
<%--    <t:dgToolBar title="分配优惠券" icon="icon-add" funname="goDistribute" width="800" height="500"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tTicketInfoController.do?goUpdate2&load=detail" funname="goViewBySelect" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>

<div data-options="region:'east',
	title:'券使用详情',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 420px; overflow: hidden;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="subListpanel"></div>
</div>
 <script src = "webpage/com/buss/ticket/tTicketInfoList.js?v=1.12"></script>		
 <script type="text/javascript">
 gridname="tTicketInfoList";
 $(document).ready(function(){
 		//给时间控件加上样式
	    $("#tTicketInfoListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>起始时间：</span><input name='begin_Time' type='text' class='Wdate' style='width:90px'/>");
		$("#tTicketInfoListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>结束时间：</span><input name='end_Time' type='text' class='Wdate' style='width:90px'/>");
		$("#tTicketInfoListForm").find("input[name='begin_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tTicketInfoListForm").find("input[name='end_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

 //查看详情
 function viewDetail(id){
	 if(li_east == 0){
		   $('#main_depart_list').layout('expand','east'); 
	}
// 	$("div.layout-panel-east div.panel-title").text("券使用详情");
	$('#subListpanel').panel("refresh", 'tTicketInfoController.do?viewDetail&id='+id);
 } 


 </script>