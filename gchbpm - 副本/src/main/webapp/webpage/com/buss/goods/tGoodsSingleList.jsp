<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsDetailList" checkbox="true" fitColumns="true" title="商品列表" actionUrl="tGoodsController.do?datagridOfsingle&retailer_Id=${retailer_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="smallPic"  image="true" imageSize="100,80"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品编码"  field="goodsCode"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="最低价"  field="lowestPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="库存"  field="goodsStock"    queryMode="single"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">

 </script>