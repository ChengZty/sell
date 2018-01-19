<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPriceChangeList" checkbox="false" fitColumns="false" sortName="createDate" sortOrder="desc" title="价格变动历史" actionUrl="tPriceChangeController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品ID"  field="goodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品编码"  field="goodsCode"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="prePrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="createName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd hh:mm"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属零售商"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="价格类型"  field="priceType"  replace="现价_1,活动价_2,最低价_3" query="true"  queryMode="single"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tPriceChangeListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tPriceChangeListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 </script>