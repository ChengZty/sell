<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOrderInfoList" checkbox="true" fitColumns="false" title="订单信息" actionUrl="tOrderInfoController.do?datagrid" idField="id" fit="true" queryMode="group">
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
   <t:dgCol title="收货地址"  field="address"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付方式"  field="payMethod"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付帐号"  field="payAccount"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单状态"  field="orderStatus"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付金额"  field="payAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="运费"  field="fareAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品总额"  field="goodsAmount"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tOrderInfoController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tOrderInfoController.do?goAdd" funname="add" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tOrderInfoController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOrderInfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tOrderInfoController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/order/tOrderInfoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOrderInfoListtb").find("input[name='orderTime_begin']").attr("class","Wdate").attr("style","height:20px;width:120px;")
 			.attr("id","orderTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'orderTime_end\')}'});});
 			$("#tOrderInfoListtb").find("input[name='orderTime_end']").attr("class","Wdate").attr("style","height:20px;width:120px;")
 			.attr("id","orderTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'orderTime_begin\')}'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOrderInfoController.do?upload', "tOrderInfoList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOrderInfoController.do?exportXls","tOrderInfoList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOrderInfoController.do?exportXlsByT","tOrderInfoList");
}
 </script>