<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="baseBrandList" checkbox="false" fitColumns="false" sortName="createDate" sortOrder="desc" title="品牌" actionUrl="baseBrandController.do?datagrid&retailerId=${retailerId }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌图片"  field="brandPic"  image="true"  queryMode="single"  imageSize="80" width="80"></t:dgCol>
<%--    <t:dgCol title="背景图片"  field="bigPic"  image="true"  queryMode="single"  imageSize="200,80"></t:dgCol> --%>
   <t:dgCol title="品牌编码"  field="brandCode"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌名称"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="品牌介绍"  field="brandSummary"    width="200"></t:dgCol> --%>
<%--    <t:dgCol title="排序"  field="sortNo"    queryMode="single"  width="80"></t:dgCol> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search"  funname="reviewbytab"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>


 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#baseBrandListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#baseBrandListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 

 </script>