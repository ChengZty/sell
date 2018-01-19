<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsList" checkbox="false" fitColumns="true" title="话题列表"  sortOrder="desc" actionUrl="tGoodsActController.do?actNewsDatagrid&actId=${actId }"
  extendParams="nowrap:false,"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="话题封面图片"  field="coverPic" image="true" imageSize="100,80"  queryMode="single" ></t:dgCol>
   <t:dgCol title="话题标题"  field="title" query="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="发布时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="100"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
