<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tJobGrowList" checkbox="true" fitColumns="false" title="成长任务" actionUrl="tJobGrowController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="导购"  field="guideName"  query="true"  queryMode="single"  width="120"></t:dgCol>
  <c:if test="${userType !='01' }">
   <t:dgCol title="所属店铺"  field="storeId" replace="${stores}" query="true"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <c:if test="${userType =='01' }">
   <t:dgCol title="所属店铺"  field="storeName"  queryMode="group"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="商品发布数"  field="recomendGuideSum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="成交总量"  field="payOrderSum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="推送总量"  field="recomendSum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="管家点评总量"  field="goodsSum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="顾客资料完整度达到80%顾客数"  field="bindingSum"    queryMode="group"  width="200"></t:dgCol>
   
    <c:if test="${userType =='01' }">
   <t:dgCol title="零售商"  field="toRetailerId"  query="true" dictionary="t_s_user,id,realname,user_type='02' and status = 'A' and retailer_type = '1' and user_status = '1'"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
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
	JeecgExcelExport("tJobGrowController.do?exportXls","tJobGrowList");
}

 </script>