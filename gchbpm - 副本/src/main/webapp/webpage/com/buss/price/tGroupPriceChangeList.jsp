<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tGroupPriceChangeList" checkbox="true" fitColumns="false" sortName="createDate" sortOrder="desc" title="组合价调价历史" actionUrl="tGroupPriceChangeController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组合名称"  field="goodsName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="prePrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="createName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd hh:mm"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组合ID"  field="goodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组合来源"  field="groupSource"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="json明细"  field="detailJson"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgToolBar title="查看详情" icon="icon-search" url="tGroupPriceChangeController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tGroupPriceChangeListtb").find("input[name='createDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tGroupPriceChangeListtb").find("input[name='updateDate']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 </script>