<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="ticketBrandList" checkbox="true" fitColumns="true" title="品牌列表" sortName="sortNo" sortOrder="desc" actionUrl="baseBrandController.do?datagridOfTicket&ticketId=${ticketId }&rId=${rId }"
  extendParams="nowrap:false,"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="brandPic" image="true" imageSize="60,60"  queryMode="single" ></t:dgCol>
   <t:dgCol title="品牌名称"  field="brandName" query="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="品牌编码"  field="brandCode" query="true" queryMode="single"  width="100"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
