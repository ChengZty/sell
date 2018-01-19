<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="allActGoodsList" checkbox="false" fitColumns="false" title="商品活动管理" actionUrl="tGoodsActController.do?datagridOfAllActGoods" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品id"  field="goodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品图片"  field="smallPic" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="日常最低价"  field="lowestPrice"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="活动最低价"  field="actPrice"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="活动名称"  field="title"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开始时间"  field="beginTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否有效"  field="valid"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="auditor"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="审核时间"  field="auditTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动状态"  field="actStatus" replace="待审核_1,待开始_2,进行中_3,已结束_4" query="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
//导出
function ExportXls() {
	JeecgExcelExport("tGoodsActController.do?exportXls","allActGoodsList");
}
 </script>