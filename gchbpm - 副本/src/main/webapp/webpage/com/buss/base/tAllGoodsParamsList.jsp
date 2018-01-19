<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tAllGoodsParamsList" checkbox="false" sortName="sortNum" sortOrder="desc" fitColumns="true" title="产品参数表" actionUrl="tAllGoodsParamsController.do?datagrid&category_Id=${category_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类目ID"  field="categoryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="参数名"  field="paramName"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="参数值"  field="paramValues"    queryMode="single"  width="250"></t:dgCol>
   <t:dgCol title="排序"  field="sortNum"  sortable="true"  queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="类型"  field="multiSelect"  replace="多选_1,单选_0,输入框_2"  queryMode="single"  width="80"></t:dgCol>
   <t:dgToolBar title="录入" icon="icon-add" url="tAllGoodsParamsController.do?goAdd&category_Id=${category_Id }" funname="add" height="200"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tAllGoodsParamsController.do?goUpdate" funname="update" height="200"></t:dgToolBar>
   <t:dgToolBar title="删除"  icon="icon-remove" url="tAllGoodsParamsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 </script>