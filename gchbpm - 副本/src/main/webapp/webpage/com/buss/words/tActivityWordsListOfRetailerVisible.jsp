<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tActivityWordsList" checkbox="true" fitColumns="false" title="活动话术" actionUrl="tActivityWordsController.do?retailerVisibleDatagrid&finActId=${finActId }" 
  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动分类"  field="activityTypeId" dictionary="t_content_category,id,name,status = 'A' and category_type = 'SPHD'" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="内容"  field="content"  query="true"   queryMode="single"  width="320"></t:dgCol>
   <t:dgCol title="平台类别"  field="platformType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="tActivityWordsController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">

 </script>