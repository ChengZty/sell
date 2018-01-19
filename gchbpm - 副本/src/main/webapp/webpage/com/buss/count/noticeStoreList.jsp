<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
  <t:datagrid name="noticeStorelist" checkbox="false" fitColumns="true" title="公司通知点击统计" actionUrl="guideCountController.do?noticeStoreDatagrid&noticeId=${noticeId}&noticeLevel=${noticeLevel}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购姓名"  field="guideName"   query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="点击数"  field="clickNum"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="停留时间(s)"  field="stillTime"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="查看日期"  field="clickTime" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
	<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  	</div>
</div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导出
 function ExportXls() {
	 var total = $('#noticeStorelist').datagrid('getData').total;
 	JeecgExcelExport("guideCountController.do?noticeStoreExportXls&noticeId=${noticeId}&noticeLevel=${noticeLevel}","noticeStorelist",1,total);
 }
 </script>
