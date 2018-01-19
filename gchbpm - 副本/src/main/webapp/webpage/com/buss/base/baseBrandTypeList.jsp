<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="baseBrandTypeList" checkbox="false" fitColumns="false" title="品牌分类列表" actionUrl="baseBrandController.do?getTypeList&brandId=${brand_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌Id"  field="brandId" hidden="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="一级分类"  field="topCategoryId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="二级分类"  field="subCategoryId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="baseBrandController.do?delType&id={id}" />
   <c:if test="${userType!='01'}">
   <t:dgToolBar title="添加品牌分类" icon="icon-add" url="baseBrandController.do?goAddPage&brandId=${brand_Id }" funname="add" width="500" height="200"></t:dgToolBar>
   </c:if>
  </t:datagrid>
  
		
 <script type="text/javascript">

 </script>