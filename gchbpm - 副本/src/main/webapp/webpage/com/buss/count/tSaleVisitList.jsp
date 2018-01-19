<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSaleVisitList" checkbox="false" fitColumns="false" title="售后回访" actionUrl="tSaleVisitController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="payTime" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购ID"  field="guideId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购姓名"  field="guideName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="顾客Id"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="顾客姓名"  field="userName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单ID"  field="orderId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="回访状态"  field="visitStatus" replace="当天致谢_1,收货关怀_2,注意事项_3,使用关怀_4,再次推荐_5,完成_6"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="当天致谢"  field="visitOne" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收货关怀"  field="visitTwo" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="注意事项"  field="visitThr" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="使用关怀"  field="visitFou" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="再次推荐"  field="visitFiv" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="维护进度"  field="visitProcess" formatterjs="addSuff"  query="true" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="tSaleVisitController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tSaleVisitController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tSaleVisitController.do?goUpdate" funname="update"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tSaleVisitController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tSaleVisitListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tSaleVisitListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tSaleVisitListtb").find("input[name='payTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
	.attr("id","payTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'payTime_end\')}'});});
	$("#tSaleVisitListtb").find("input[name='payTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
	.attr("id","payTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'payTime_begin\')}'});});
 });
 
 function addSuff(value,row,index){
	 if(value!=""){
		 value=value+"%";
	 }
	 return value;
 }

//导出
function ExportXls() {
	JeecgExcelExport("tSaleVisitController.do?exportXls","tSaleVisitList");
}
 </script>