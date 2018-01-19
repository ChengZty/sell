<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsList" checkbox="true" fitColumns="true" title="话术列表" sortName="sortNum" sortOrder="desc" actionUrl="tGoodsActController.do?actWordsOfGrid&actId=${actId }"
  extendParams="nowrap:false,"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话术内容"  field="content" query="true"  queryMode="single"  width="150"></t:dgCol>
<%--    <t:dgCol title="活动价"  field="activityPrice" queryMode="single"  width="50"></t:dgCol> --%>
  </t:datagrid>
  </div>
 </div>
