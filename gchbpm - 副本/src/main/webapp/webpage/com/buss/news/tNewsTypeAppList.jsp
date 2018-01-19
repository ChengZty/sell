<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewsTypeList" checkbox="false" fitColumns="false" title="话题分类" actionUrl="tNewsTypeController.do?datagrid&tp=2" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="编码"  field="code"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="名字"  field="name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="coverPic" image="true" imageSize="100,60"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="排序编号"  field="orderNum"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="显示类别"  field="showType"   query="true" queryMode="single" dictionary="newsShowTp" width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 </script>