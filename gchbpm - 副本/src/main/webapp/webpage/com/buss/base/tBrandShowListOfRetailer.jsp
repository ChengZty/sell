<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBrandShowList" checkbox="true" fitColumns="false" title="零售商品牌列表" actionUrl="tBrandShowController.do?datagrid"
   idField="id" fit="true" queryMode="group" sortName="orderNum" sortOrder="asc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌ID"  field="brandId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌图片"  field="brandPic" image="true" imageSize="80,80"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌名称"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌编码"  field="brandCode"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="排序"  field="orderNum"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="满邮金额"  field="freeAmount"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="基础运费"  field="fare"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="是签约品牌"  field="isSelfBrand" replace="是_1,否_0" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
<%--    <t:dgDelOpt title="删除" url="tBrandShowController.do?doDel&id={id}" /> --%>
<%-- 	<t:dgDefOpt title="编辑" url="tBrandShowController.do?goUpdate&id={id}"  exp="isSelfBrand#eq#1"/> --%>
	<t:dgFunOpt funname="goUpdate(id)"  title="编辑" exp="isSelfBrand#eq#1"></t:dgFunOpt>
	<t:dgFunOpt funname="goSort(id)"  title="排序" exp="isSelfBrand#eq#0"></t:dgFunOpt>
<%--    <t:dgDelOpt title="删除" url="tBrandShowController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tBrandShowController.do?goUpdate" funname="update" width="750"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBrandShowController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
  </t:datagrid>
</div>
 </div>
 <script type="text/javascript">
 function goUpdate(id) {
	 var title = "修改品牌";
	 var url = "tBrandShowController.do?goUpdate&id="+id;
	createwindow(title,url,600,400);
}
 function goSort(id) {
	 var title = "修改排序";
	 var url = "tBrandShowController.do?goSort&id="+id;
	createwindow(title,url,600,300);
}
 </script>