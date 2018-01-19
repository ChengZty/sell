<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tHelperBillList" checkbox="true" fitColumns="false" sortName="addTime" sortOrder="desc"  title="帮手结算明细" actionUrl="tHelperBillController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="财务结算表ID"  field="finBillId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="流水时间"  field="addTime" formatter="yyyyMMdd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单编号"  field="orderNo"    queryMode="single" query="true" width="120"></t:dgCol>
   <t:dgCol title="子订单编号"  field="subOrderNo"    queryMode="single" query="true" width="120"></t:dgCol>
   <t:dgCol title="业务日期"  field="businessDate" formatter="yyyy-MM-dd hh:mm:ss" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帮手实收"  field="helperMoney"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帮手ID"  field="helperId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帮手姓名"  field="helperName"    queryMode="single" dictionary="bpm_status" width="120"></t:dgCol>
   <t:dgCol title="帮手类别"  field="helperType"    queryMode="single" replace="专家_1,达人_2,导购_3"  width="120"></t:dgCol>
   <t:dgCol title="二级分类"  field="subCategoryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购ID"  field="guideId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购所属零售商ID"  field="storeId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="货品所属零售商"  field="toStoreGoodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="货品零售商类别"  field="storeType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="tHelperBillController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tHelperBillController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tHelperBillListtb").find("input[name='addTime']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tHelperBillListtb").find("input[name='businessDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tHelperBillController.do?upload', "tHelperBillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tHelperBillController.do?exportXls","tHelperBillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tHelperBillController.do?exportXlsByT","tHelperBillList");
}
 </script>