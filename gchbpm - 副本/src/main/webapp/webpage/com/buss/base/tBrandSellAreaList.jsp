<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tBrandSellAreaList" checkbox="true" fitColumns="false" title="品牌可售区域" actionUrl="tBrandSellAreaController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"   hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="省"  field="provinceName"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="市"  field="cityName"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="省ID"  field="provinceId"  hidden="true"   queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="市ID"  field="cityId" hidden="true"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBrandSellAreaController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBrandSellAreaController.do?goAdd&brandId=${brandId}" funname="add" width="400" height="200"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBrandSellAreaController.do?goUpdate" funname="update" width="400" height="200"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBrandSellAreaController.do?doBatchDel&brandId=${brandId}" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tBrandSellAreaListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tBrandSellAreaListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 </script>