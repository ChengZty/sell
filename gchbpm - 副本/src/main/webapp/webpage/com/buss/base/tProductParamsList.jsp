<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tAllGoodsParamsList" checkbox="false" sortName="rowNum" sortOrder="desc" fitColumns="true" title="产品参数信息" actionUrl="tProductParamsController.do?datagrid&category_Id=${category_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="一级类目ID"  field="categoryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类别"  field="type"  replace="尺码指导_1,产品信息_2" query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="参数名"  field="paramName"  query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="参数值"  field="paramValues"    queryMode="single"  width="250"></t:dgCol>
   <t:dgCol title="单位"  field="inputUnit"    queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="行排序"  field="rowNum"  sortable="true"  queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="行内排序"  field="rowIndexNum"  sortable="true"  queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="值类型"  field="inputType"  replace="单选_0,多选_1,输入框_2,下拉框_3,图片_4,多选图片_5"  queryMode="single"  width="80"></t:dgCol>
   <t:dgToolBar title="录入" icon="icon-add" url="tProductParamsController.do?goAdd&category_Id=${category_Id }" funname="add" height="330"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tProductParamsController.do?goUpdate" funname="update" height="330"></t:dgToolBar>
   <t:dgToolBar title="删除"  icon="icon-remove" url="tProductParamsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 </script>